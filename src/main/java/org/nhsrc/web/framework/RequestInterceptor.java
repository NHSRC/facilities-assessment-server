package org.nhsrc.web.framework;

import org.nhsrc.domain.metadata.EntityType;
import org.nhsrc.domain.metadata.EntityTypeMetadata;
import org.nhsrc.repository.metadata.EntityTypeMetadataRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;
import org.springframework.web.util.UriComponentsBuilder;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.time.temporal.TemporalAccessor;
import java.util.*;

@Component
public class RequestInterceptor extends HandlerInterceptorAdapter {
    private final EntityTypeMetadataRepository entityTypeMetadataRepository;

    @Autowired
    public RequestInterceptor(EntityTypeMetadataRepository entityTypeMetadataRepository) {
        this.entityTypeMetadataRepository = entityTypeMetadataRepository;
    }

    @Override
    public boolean preHandle(HttpServletRequest request,
                             HttpServletResponse response, Object object) throws IOException {
        MultiValueMap<String, String> queryParams = UriComponentsBuilder.fromUriString(String.format("%s?%s", request.getRequestURL(), request.getQueryString())).build().getQueryParams();
        Set<String> uniqueRequestParams = queryParams.keySet();
        for (String requestParam : uniqueRequestParams) {
            if (queryParams.get(requestParam).size() > 1) {
                response.sendError(HttpServletResponse.SC_BAD_REQUEST, "Parameter pollution detected");
                return false;
            }
        }
        if (!request.getMethod().equals(RequestMethod.GET.name()) || request.getParameter("lastModifiedDate") == null) {
            return true;
        }

        EntityType entityType = null;
        if (request.getRequestURI().contains(EntityType.District.name().toLowerCase())) {
            entityType = EntityType.District;
        } else if (request.getRequestURI().contains(EntityType.Facility.name().toLowerCase())) {
            entityType = EntityType.Facility;
        }
        if (entityType != null) {
            LocalDateTime lastModifiedDate = LocalDateTime.parse(request.getParameter("lastModifiedDate"), DateTimeFormatter.ISO_DATE_TIME);
            EntityTypeMetadata entityTypeMetadata = entityTypeMetadataRepository.findByTypeAndName(entityType, EntityTypeMetadata.BULK_MODIFICATION_DATE_NAME);
            String effectiveLastModifiedDateTime;
            if (lastModifiedDate.getYear() == 1900 && entityTypeMetadata != null) {
                LocalDateTime localDateTime = LocalDateTime.ofInstant(entityTypeMetadata.getDateValue().toInstant(), ZoneId.systemDefault());
                effectiveLastModifiedDateTime = localDateTime.format(DateTimeFormatter.ISO_DATE_TIME) + ".000Z";
            }  else
                effectiveLastModifiedDateTime = request.getParameter("lastModifiedDate");
            ((MutableRequestWrapper) request).addParameter("effectiveLastModifiedDate", effectiveLastModifiedDateTime);
        }
        return true;
    }
}
