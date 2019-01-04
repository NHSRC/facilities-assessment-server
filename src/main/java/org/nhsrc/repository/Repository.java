package org.nhsrc.repository;

import org.nhsrc.domain.AbstractEntity;
import java.util.UUID;

public class Repository {
    public static <T> T findByUuidOrId(UUID uuid, int id, BaseRepository<T> baseRepository) {
        // Simplifying based IntelliJ's suggestion could lead to recursive loop
        return Repository.findByUuidOrId(uuid == null ? null : uuid, id, baseRepository);
    }

    public static <T> T findByUuidOrId(String uuid, int id, BaseRepository<T> baseRepository) {
        if (uuid == null) {
            return id == 0 ? null : baseRepository.findOne(id);
        } else {
            return baseRepository.findByUuid(UUID.fromString(uuid));
        }
    }

    public static <T extends AbstractEntity> T findByUuidOrCreate(String uuid, BaseRepository<T> baseRepository, T newEntity) {
        if (uuid == null || uuid.isEmpty()) {
            newEntity.setUuid(UUID.randomUUID());
            return newEntity;
        }
        T entity = baseRepository.findByUuid(UUID.fromString(uuid));
        if (entity == null) {
            newEntity.setUuid(UUID.fromString(uuid));
            entity = newEntity;
        }
        return entity;
    }
}