package com.CP.KPCOS.utils;

import java.util.Collection;

public class CommonUtils {
    public static boolean isEmptyString(String str) {
        return str == null || str.trim().isEmpty();
    }
    public static boolean isEmptyCollection(Collection<?> collection) {
        return collection == null || collection.isEmpty();
    }
}
