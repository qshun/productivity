package com.seriousplay.productitity.jdbc;


import java.lang.reflect.Array;
import java.util.Collection;
import java.util.Map;
import java.util.Optional;

/**
 * 字段策略
 */
@FunctionalInterface
public interface FieldStrategy {

    <T> boolean accept(T value);

    /**
     * 忽略此字段
     *
     * @param value
     * @param <T>
     * @return
     */
    static <T> boolean ignored(T value) {
        return false;
    }

    /**
     * 非空字段
     *
     * @param value
     * @param <T>
     * @return
     */
    static <T> boolean isNotNull(T value) {
        return value != null;
    }

    /**
     * 非空串字段
     *
     * @param value
     * @param <T>
     * @return
     */
    static <T> boolean isNotEmpty(T value) {
        return !isEmpty(value);
    }

    /**
     * 判断字段是否为空
     *
     * @param obj
     * @param <T>
     * @return
     */
    static <T> boolean isEmpty(T obj) {
        if (obj == null) {
            return true;
        }

        if (obj instanceof Optional) {
            return !((Optional) obj).isPresent();
        }
        if (obj instanceof CharSequence) {
            return ((CharSequence) obj).length() == 0;
        }
        if (obj.getClass().isArray()) {
            return Array.getLength(obj) == 0;
        }
        if (obj instanceof Collection) {
            return ((Collection) obj).isEmpty();
        }
        if (obj instanceof Map) {
            return ((Map) obj).isEmpty();
        }

        // else
        return false;
    }
}