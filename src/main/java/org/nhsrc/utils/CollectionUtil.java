package org.nhsrc.utils;

import org.nhsrc.web.contract.ext.AssessmentResponse;

import java.util.Collection;
import java.util.function.Predicate;

public class CollectionUtil {
    public static <T> T find(Collection<T> collection, Predicate<T> predicate) {
        return collection.stream().filter(predicate).findFirst().orElse(null);
    }

    public static <T> T addIfNotExists(Collection<T> collection, Predicate<T> predicate, T t) {
        T existing = CollectionUtil.find(collection, predicate);
        if (existing == null) {
            collection.add(t);
            return t;
        }
        return existing;
    }
}