package org.nhsrc.web.framework;

import org.nhsrc.domain.metadata.EntityTypeMetadata;
import org.nhsrc.repository.metadata.EntityTypeMetadataRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.ArrayList;

@Component
public class RequestInterceptor extends HandlerInterceptorAdapter {
    private EntityTypeMetadataRepository entityTypeMetadataRepository;

    @Autowired
    public RequestInterceptor(EntityTypeMetadataRepository entityTypeMetadataRepository) {
        this.entityTypeMetadataRepository = entityTypeMetadataRepository;
    }

    @Override
    public boolean preHandle(HttpServletRequest request,
                             HttpServletResponse response, Object object) throws Exception {
        ArrayList<EntityTypeMetadata> allEntityMetadata = new ArrayList<EntityTypeMetadata>();
//        entityTypeMetadataRepository.findAll().forEach(allEntityMetadata::add);
        if (request.getMethod().equals(RequestMethod.GET.name())) {
            ((MutableRequestWrapper) request).addParameter("now", "foo");
        }
        return true;
    }
}