package com.lucky.util;

import java.util.Collection;

/**
 * @author: lucky
 * @date: 2019/6/18 11:40
 */
public class CollectionUtil {

    /**
     * 获取集合第一个元素
     *
     * @param collection
     * @param <T>
     * @return
     */
    public <T> T first(Collection<T> collection) {
        if (collection == null || collection.isEmpty()) {
            return null;
        }
        return collection.iterator().next();
    }
}
