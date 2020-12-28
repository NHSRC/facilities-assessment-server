package org.nhsrc.web.framework;

import org.nhsrc.domain.metadata.EntityType;
import org.nhsrc.domain.metadata.EntityTypeMetadata;
import org.nhsrc.repository.metadata.EntityTypeMetadataRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.time.temporal.TemporalAccessor;
import java.util.Date;

@Component
public class RequestInterceptor extends HandlerInterceptorAdapter {
    private EntityTypeMetadataRepository entityTypeMetadataRepository;

    @Autowired
    public RequestInterceptor(EntityTypeMetadataRepository entityTypeMetadataRepository) {
        this.entityTypeMetadataRepository = entityTypeMetadataRepository;
    }

    @Override
    public boolean preHandle(HttpServletRequest request,
                             HttpServletResponse response, Object object) {
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