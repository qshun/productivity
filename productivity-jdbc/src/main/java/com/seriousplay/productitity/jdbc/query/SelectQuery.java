package com.seriousplay.productitity.jdbc.query;

import org.apache.commons.lang3.StringUtils;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.function.BiFunction;

/**
 *
 */
public class SelectQuery<T, C> extends AbstractQuery<SelectQuery<T, C>, T, C> {
    protected Criteria<C> select;
    protected Boolean distinct;
    protected List<String> groupBy;
    protected Criteria<C> having;
    protected Integer offset;

    public SelectQuery(BiFunction<T, Boolean, String> tableToStringFunction,
                       BiFunction<C, Boolean, String> columnToStringFunction) {
        super(tableToStringFunction, columnToStringFunction);
        select = createCriteriaInternal();
    }

    public Map<String, Object> getParamNameValuePairs() {
        return paramNameValuePairs;
    }

    public void distinct(boolean distinct) {
        this.distinct = distinct;
    }

    /**
     * @param columns
     * @return
     */
    public SelectQuery<T, C> select(C... columns) {
        if (columns != null && columns.length > 0) {
            for (C col : columns) {
                select(columnToStringFunction.apply(col, true));
            }
        }
        return getSelf();
    }

    public SelectQuery<T, C> select(C column, String tableAlias) {
        if (column != null) {
            select(columnToStringFunction.apply(column, false), tableAlias);
        }
        return getSelf();
    }

    /**
     * @param column
     * @param tableAlias
     * @param as
     * @return
     */
    public SelectQuery<T, C> select(C column, String tableAlias, String as) {
        if (column != null) {
            select(columnToStringFunction.apply(column, false), tableAlias, as);
        }
        return getSelf();
    }


    /**
     * @param column
     * @return
     */
    public SelectQuery<T, C> select(String column) {
        select(column, null);
        return getSelf();
    }

    /**
     * @param column
     * @param tableAlias
     * @return
     */
    public SelectQuery<T, C> select(String column, String tableAlias) {
        return select(column, tableAlias, null);
    }

    /**
     * @param column
     * @param tableAlias
     * @param as
     * @return
     */
    public SelectQuery<T, C> select(String column, String tableAlias, String as) {
        if (StringUtils.isNotBlank(column)) {
            if (select == null) {
                this.select = createCriteriaInternal();
            }
            StringBuffer buffer = new StringBuffer();

            if (StringUtils.isNotBlank(tableAlias)) {
                buffer.append(tableAlias).append(".");
            }
            buffer.append(column);
            if (StringUtils.isNotBlank(as)) {
                buffer.append(" as ").append(as);

            }
            select.addCriterion(buffer.toString());
        }
        return getSelf();
    }

    /***
     *
     * @param sql
     * @param parameters
     * @return
     */
    public SelectQuery<T, C> selectSql(String sql, Object... parameters) {
        select.sql(sql, parameters);
        return getSelf();
    }

    /***
     *
     * @param tables
     * @return
     */
    public SelectQuery<T, C> from(T... tables) {
        if (tables == null) {
            this.tables = new ArrayList<>();
        }
        if (tables != null && tables.length > 0) {
            for (T table : tables) {
                from(tableToStringFunction.apply(table, true));
            }
        }

        return this;
    }


    /**
     * @param columns
     * @return
     */
    SelectQuery<T, C> groupBy(C... columns) {
        if (columns != null && columns.length > 0) {
            for (C column : columns) {
                groupBy(columnToStringFunction.apply(column, true));
            }
        }
        return getSelf();
    }

    /**
     * @param column
     * @return
     */
    SelectQuery<T, C> groupBy(String column) {
        if (StringUtils.isNotBlank(column)) {
            if (groupBy == null) {
                groupBy = new ArrayList<>();
            }
            groupBy.add(column);
        }
        return getSelf();
    }

    /**
     * @return
     */
    public Criteria<C> having() {
        if (this.having == null) {
            this.having = createCriteriaInternal();
        }
        return this.having;
    }

    public SelectQuery<T, C> offset(Integer offset) {
        this.offset = offset;
        return getSelf();
    }

    /**
     *
     */
    public void clear() {
        select = null;
        distinct = null;
        joins = null;
        where = null;
        having = null;
        groupBy = null;
        orderBy = null;
        offset = null;
        limit = null;

    }

    @Override
    public SelectQuery<T, C> getSelf() {
        return this;
    }

    @Override
    public String toSQL() {
        StringBuilder appendable = new StringBuilder();
        SafeAppendable builder = new SafeAppendable(appendable);
        paramNameSeq = new AtomicInteger();
        this.paramNameValuePairs = new LinkedHashMap<>();
        if (distinct != null && distinct) {
            sqlClause(builder, "select distinct", select);
        } else {
            sqlClause(builder, "select", select);
        }
        sqlClause(builder, "from", tables, "", "", ", ");
        joins(builder);
        sqlClause(builder, "where", where);
        sqlClause(builder, "group by", groupBy, "", "", ", ");
        sqlClause(builder, "having", having);
        sqlClause(builder, "order by", orderBy, "", "", ", ");
        if (offset != null || limit != null) {
            appendable.append(" limit");
            if (offset != null) {
                if (limit != null) {
                    appendable.append("?,?");
                    addParameter(offset);
                    addParameter(limit);
                }
            } else if (limit != null) {
                appendable.append(" ?");
                addParameter(limit);
            }
        }
        return appendable.toString();
    }
}
