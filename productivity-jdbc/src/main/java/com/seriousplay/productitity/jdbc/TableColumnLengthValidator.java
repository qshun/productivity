package com.seriousplay.productitity.jdbc;

import org.springframework.validation.Errors;

/**
 * 表字段长度验证器
 */
@FunctionalInterface
public interface TableColumnLengthValidator {
    /**
     * 验证表字段值长度
     *
     * @param objectName
     * @param target
     * @return
     */
    Errors validate(TableMetaData tableMetaData, String objectName, Object target);
}
