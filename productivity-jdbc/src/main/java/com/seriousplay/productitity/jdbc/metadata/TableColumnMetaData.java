package com.seriousplay.productitity.jdbc.metadata;

import com.seriousplay.productitity.jdbc.FieldStrategy;
import com.seriousplay.productitity.jdbc.SqlOperator;

import java.util.function.BiFunction;

/**
 * table
 */
public interface TableColumnMetaData {
    BiFunction<TableColumnMetaData, Boolean, String> TABLE_COLUMN_META_DATA_STRING_FUNCTION = (columnMetaData, userAlias) -> userAlias ? columnMetaData.getColumnAlias() : columnMetaData.getColumn();

    /**
     * 获取数据库字段名称
     *
     * @return
     */
    String getColumn();

    /**
     * 获取字段别名
     */
    String getColumnAlias();

    /**
     * 获取数据类型
     *
     * @return
     */
    String getColumnType();

    /**
     * 列注释
     *
     * @return
     */
    String getComment();
    /**
     * 实体对象字段名
     *
     * @return
     */
    /**
     * 字段值是否非空
     *
     * @return
     */
    boolean isNullable();

    /**
     * 实体对象属性
     *
     * @return
     */
    String getProperty();

    /**
     * 是否是主键
     *
     * @return
     */
    boolean isKey();

    /**
     * @return
     */
    default boolean select() {
        return true;
    }

    /**
     * 字段插入策略,默认非空
     *
     * @return
     */
    default FieldStrategy getInsertStrategy() {
        return FieldStrategy::isNotNull;
    }

    /**
     * 字段更新策略，默认非空
     *
     * @return
     */
    default FieldStrategy getUpdateStrategy() {
        return FieldStrategy::isNotNull;
    }

    /**
     * where子句策略，默认非空
     *
     * @return
     */
    default FieldStrategy getWhereStrategy() {
        return FieldStrategy::isNotNull;
    }

    /**
     * 更新条件
     *
     * @return
     */
    default String getUpdateCondition() {
        return SqlOperator.EQ.getOperator();
    }

    /**
     * where条件
     *
     * @return
     */
    default String getCondition() {
        return SqlOperator.EQ.getOperator();
    }

    /**
     * 是否是生成字段
     *
     * @return
     */
    boolean isGeneratedValue();
}
