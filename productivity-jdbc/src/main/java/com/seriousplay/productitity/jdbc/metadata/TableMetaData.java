package com.seriousplay.productitity.jdbc.metadata;

import java.util.StringJoiner;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.function.BiFunction;
/**
 *
 */
public interface TableMetaData {
    BiFunction<TableMetaData, Boolean, String> TABLE_META_DATA_STRING_FUNCTION = (tableMetaData, userAlias) -> userAlias ? tableMetaData.getTableAlias() :
            tableMetaData.getTableName();

    AtomicInteger TABLE_NO = new AtomicInteger();

    /**
     * @return
     */
    String getSchemaName();

    /**
     * 获取表名
     *
     * @return
     */
    String getTableName();

    /**
     * 获取表别名ex:xxx as t1
     *
     * @return
     */
    String getTableAlias();

    /**
     * @return
     */
    String getAlias();

    /**
     * 获取表字段
     *
     * @return
     */
    TableColumnMetaData[] getColumns();

    /**
     * 获取逻辑删除字段
     *
     * @return
     */
    String getLogicField();

    default String selectAllColumns(boolean useAlias) {
        StringJoiner joiner = new StringJoiner(",");
        for (TableColumnMetaData column : getColumns()) {
            if (column.select()) {
                joiner.add(useAlias ? column.getColumnAlias() : column.getColumn());
            }
        }
        return joiner.toString();
    }
}
