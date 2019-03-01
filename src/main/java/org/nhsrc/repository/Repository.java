package org.nhsrc.repository;

import org.nhsrc.domain.AbstractEntity;
import org.nhsrc.domain.BaseEntity;
import org.nhsrc.domain.Checklist;
import org.springframework.data.repository.CrudRepository;

import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.UUID;
import java.util.function.Consumer;

public class Repository {
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

    public static <T> T findById(Integer id, BaseRepository<T> baseRepository) {
        return (id == null || id == 0) ? null : baseRepository.findOne(id);
    }

    public static <T extends BaseEntity> T findByIdOrCreate(Integer id, BaseRepository<T> baseRepository, T newEntity) {
        if (id == null || id == 0) {
            return newEntity;
        }
        T t = findById(id, baseRepository);
        if (t == null) {
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
            entity = newEntity;
        }
        return entity;
    }

    public static <T extends BaseEntity> void mergeChildren(List<Integer> proposedChildrenIds, List<Integer> existingChildrenIds, CrudRepository<T, Integer> childRepository, Consumer<BaseEntity> childRemover, Consumer<BaseEntity> childAdder) {
        Set<Integer> proposedChildrenIdSet = new HashSet<>(proposedChildrenIds);
        HashSet<Integer> toRemoveChildrenIds = new HashSet<>(existingChildrenIds);
        toRemoveChildrenIds.removeAll(proposedChildrenIdSet);
        toRemoveChildrenIds.forEach(existingChildId -> childRemover.accept(childRepository.findOne(existingChildId)));

        for (Integer proposedChildId : proposedChildrenIdSet) {
            childAdder.accept(childRepository.findOne(proposedChildId));
        }
    }
}