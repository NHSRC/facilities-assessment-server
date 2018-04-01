package org.nhsrc.config.quartz;

import java.util.Collection;
import java.util.UUID;

public class AppUtil {
    public static String getUUID() {
        return UUID.randomUUID().toString();
    }

    private static boolean isCollectionEmpty(Collection<?> collection) {
        if (collection == null || collection.isEmpty()) {
            return true;
        }
        return false;
    }

    public static boolean isObjectEmpty(Object object) {
        if(object == null) return true;
        else if(object instanceof String) {
            if (((String)object).trim().length() == 0) {
                return true;
            }
        } else if(object instanceof Collection) {
            return isCollectionEmpty((Collection<?>)object);
        }
        return false;
    }
}