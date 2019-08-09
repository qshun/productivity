package com.seriousplay.productitity.jdbc;

/**
 *
 */
@FunctionalInterface
public interface FieldLengthValidator {
    /**
     * @param value
     * @param length
     * @return
     */
    boolean validate(Object value, int... length);

}
