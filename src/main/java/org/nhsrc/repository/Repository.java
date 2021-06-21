package org.nhsrc.repository;

import org.nhsrc.domain.AbstractEntity;
import org.nhsrc.domain.BaseEntity;
import org.springframework.data.repository.CrudRepository;

import java.util.*;
import java.util.function.Consumer;
import java.util.stream.Collectors;

public class Repository {
    public static <T> Set<T> findByIds(List<Integer> ids, BaseRepository<T> baseRepository) {
        return ids.stream().map(id -> Repository.findById(id, baseRepository)).collect(Collectors.toSet());
    }

    public static <T> T findByUuidOrId(UUID uuid, Integer id, BaseRepository<T> baseRepository) {
        // Simplifying based IntelliJ's suggestion could lead to recursive loop
        return Repository.findByUuidOrId(uuid == null ? null : uuid.toString(), id, baseRepository);
    }

    public static <T> T findByUuidOrId(String uuid, Integer id, BaseRepository<T> baseRepository) {
        if (uuid == null) {
            return findById(id, baseRepository);
        } else {
            return baseRepository.findByUuid(UUID.fromString(uuid));
        }
    }

    public static <T> T findById(Integer id, CrudRepository<T, Integer> crudRepository) {
        return (id == null || id == 0) ? null : crudRepository.findOne(id);
    }

    public static <T extends BaseEntity> T findByIdOrCreate(Integer id, CrudRepository<T, Integer> repository, T newEntity) {
        if (id == null || id == 0) {
            return newEntity;
        }
        T t = findById(id, repository);
        if (t == null) {
            if (newEntity instanceof AbstractEntity)
                ((AbstractEntity)newEntity).setInactive(false);
            return newEntity;
        }
        return t;
    }

    public static <T extends AbstractEntity> T findByUuidOrCreate(String uuid, BaseRepository<T> baseRepository, T newEntity) {
        if (uuid == null || uuid.isEmpty()) {
            newEntity.setUuid(UUID.randomUUID());
            return newEntity;
        }
        T entity = baseRepository.findByUuid(UUID.fromString(uuid));
        if (entity == null) {
            newEntity.setUuid(UUID.fromString(uuid));
            newEntity.setInactive(false);
            entity = newEntity;
        }
        return entity;
    }

    public static <T extends BaseEntity> void mergeChildren(List<Integer> proposedChildrenIds, List<Integer> existingChildrenIds, CrudRepository<T, Integer> childRepository, Consumer<BaseEntity> removeChild, Consumer<BaseEntity> addChild) {
        Set<Integer> proposedChildrenIdSet = new HashSet<>(proposedChildrenIds);
        HashSet<Integer> toRemoveChildrenIds = new HashSet<>(existingChildrenIds);
        toRemoveChildrenIds.removeAll(proposedChildrenIdSet);
        toRemoveChildrenIds.forEach(existingChildId -> removeChild.accept(childRepository.findOne(existingChildId)));

        for (Integer proposedChildId : proposedChildrenIdSet) {
            addChild.accept(childRepository.findOne(proposedChildId));
        }
    }

    public static <T extends BaseEntity> T delete(Integer id, CrudRepository<T, Integer> repository) {
        T t = repository.findOne(id);
        repository.delete(id);
        return t;
    }
}
