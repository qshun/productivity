package com.seriousplay.productitity.jdbc.query;

import com.seriousplay.productitity.jdbc.TableColumnMetaData;
import com.seriousplay.productitity.jdbc.TableMetaData;

import java.util.function.BiFunction;

/**
 *
 */
public class TableMetaDataSelectQuery extends SelectQuery<TableMetaData, TableColumnMetaData> {
    public TableMetaDataSelectQuery() {
        super(TableMetaData.TABLE_META_DATA_STRING_FUNCTION, TableColumnMetaData.TABLE_COLUMN_META_DATA_STRING_FUNCTION);
    }

    public TableMetaDataSelectQuery(BiFunction<TableMetaData, Boolean, String> tableToStringFunction,
                                    BiFunction<TableColumnMetaData, Boolean, String> columnToStringFunction) {
        super(tableToStringFunction, columnToStringFunction);
    }

}
