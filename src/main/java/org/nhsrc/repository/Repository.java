package org.nhsrc.repository;

import java.util.UUID;

public class Repository {
    public static <T> T findByUuidOrId(String uuid, int id, BaseRepository<T> baseRepository) {
        if (uuid == null) {
            return baseRepository.findOne(id);
        } else {
            return baseRepository.findByUuid(UUID.fromString(uuid));
        }
    }
}