package org.nhsrc.decorator;

import org.hibernate.SessionFactory;
import org.hibernate.engine.spi.SessionFactoryImplementor;
import org.hibernate.metadata.ClassMetadata;
import org.hibernate.type.CollectionType;
import org.hibernate.type.Type;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;

@Component
public class EntityMetadata {
    private SessionFactory sessionFactory;
    private Map<String, Map<String, String>> entityTypeMap = new HashMap<>();

    @Autowired
    public EntityMetadata(SessionFactory sessionFactory) throws ClassNotFoundException {
        for (Map.Entry<String, ClassMetadata> entry : sessionFactory.getAllClassMetadata().entrySet()) {
            ClassMetadata entityMetadata = entry.getValue();
            String[] fieldNames = entityMetadata.getPropertyNames();
            Map<String, String> propertyMapping = new HashMap<>();
            for (String fieldName : fieldNames) {
                Type propertyType = entityMetadata.getPropertyType(fieldName);
                String propertyTypeName = propertyType.getReturnedClass().getSimpleName().toLowerCase();
                if (propertyType.isCollectionType()) {
                    propertyTypeName = getSimpleName(((CollectionType) propertyType).getAssociatedEntityName((SessionFactoryImplementor) sessionFactory));
                }
                propertyMapping.put(fieldName.toLowerCase(), propertyTypeName);
            }
            entityTypeMap.put(
                    getSimpleName(entityMetadata.getEntityName()),
                    propertyMapping);
        }
    }

    private String getSimpleName(String fullyQualifiedName) throws ClassNotFoundException {
        return Class.forName(fullyQualifiedName).getSimpleName().toLowerCase();
    }

    public Map<String, Map<String, String>> getEntityTypeMap() {
        return entityTypeMap;
    }
}
