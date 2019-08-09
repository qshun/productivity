package com.seriousplay.productitity.jdbc.query;

import org.apache.commons.lang3.StringUtils;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.function.BiFunction;

/**
 * 单表删除
 *
 * @param <T>
 * @param <C>
 */
public class DeleteQuery<T, C> extends AbstractQuery<DeleteQuery<T, C>, T, C> {
    public DeleteQuery(BiFunction<T, Boolean, String> tableToStringFunction, BiFunction<C, Boolean, String> columnToStringFunction) {
        super(tableToStringFunction, columnToStringFunction);
    }

    @Override
    public DeleteQuery<T, C> from(String table, String tableAlias) {
        if (tables == null) {
            tables = new ArrayList<>();
        } else {
            tables.clear();
        }
        if (StringUtils.isNotBlank(table)) {
            if (StringUtils.isNotBlank(tableAlias)) {
                this.tables.add(table + " as " + tableAlias);
            } else {
                this.tables.add(table);
            }
        }
        return getSelf();
    }

    @Override
    public DeleteQuery<T, C> getSelf() {
        return this;
    }

    @Override
    public String toSQL() {
        StringBuilder appendable = new StringBuilder();
        SafeAppendable builder = new SafeAppendable(appendable);
        paramNameSeq = new AtomicInteger();
        this.paramNameValuePairs = new LinkedHashMap<>();
        sqlClause(builder, "delete ", getAllTableAlias(), "", "", ",");
        sqlClause(builder, "from ", tables, "", "", ",");
        joins(builder);
        sqlClause(builder, "where", where);
        sqlClause(builder, "order by", orderBy, "", "", ", ");
        if (limit != null) {
            appendable.append(" limit ?");
            addParameter(limit);
        }
        return appendable.toString();
    }
}
