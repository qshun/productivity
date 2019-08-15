package com.seriousplay.productitity.jdbc.query;

import com.seriousplay.productitity.jdbc.PositionParameterSql;

import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.function.BiFunction;

/**
 * @param <T>
 * @param <C>
 */
public class UpdateQuery<T, C> extends AbstractQuery<UpdateQuery<T, C>, T, C> {

    private List<Criterion> sets;

    public UpdateQuery(BiFunction<T, Boolean, String> tableToStringFunction, BiFunction<C, Boolean, String> columnToStringFunction) {
        super(tableToStringFunction, columnToStringFunction);
    }

    public UpdateQuery<T, C> set(C column, Object value) {
        String col = columnToStringFunction.apply(column, true);
        addCriterion(new Criterion(col + "=", value));
        return getSelf();
    }


    /**
     * @param sql
     * @param parameters
     * @return
     */
    public UpdateQuery<T, C> set(String sql, Object... parameters) {
        PositionParameterSql parameterSql = PositionParameterSql.parseSql(sql, parameters);
        Criterion criterion = new Criterion(parameterSql.getSql().toString(), parameterSql.getParameters());
        addCriterion(criterion);
        return getSelf();
    }

    /**
     * @param sql
     * @param parameters
     * @return
     */
    public UpdateQuery<T, C> set(String sql, List<Object> parameters) {
        PositionParameterSql parameterSql = PositionParameterSql.parseSql(sql, parameters);
        Criterion criterion = new Criterion(parameterSql.getSql().toString(), parameterSql.getParameters());
        addCriterion(criterion);
        return getSelf();
    }

    public UpdateQuery<T, C> inc(C column) {
        return inc(column, 1);
    }

    public UpdateQuery<T, C> inc(C column, Number delta) {
        this.addCriterion(column, "+", delta);
        return getSelf();
    }

    /**
     * -
     *
     * @param column
     * @param value
     * @return
     */
    public UpdateQuery<T, C> subtraction(C column, Number value) {
        this.addCriterion(column, "-", value);
        return getSelf();
    }

    /**
     * *
     *
     * @param column
     * @param value
     * @return
     */
    public UpdateQuery<T, C> multiplication(C column, Number value) {
        this.addCriterion(column, "*", value);
        return getSelf();
    }

    /**
     * /
     *
     * @param column
     * @param value
     * @return
     */
    public UpdateQuery<T, C> division(C column, Number value) {
        this.addCriterion(column, "/", value);
        return getSelf();
    }

    /**
     * Integer division
     *
     * @param column
     * @param value
     * @return
     */
    public UpdateQuery<T, C> div(C column, Number value) {
        this.addCriterion(column, "DIV", value);
        return getSelf();
    }

    /**
     * @param column
     * @param value
     * @return
     */
    public UpdateQuery<T, C> mod(C column, Number value) {
        this.addCriterion(column, "%", value);
        return getSelf();
    }

    protected void addCriterion(C column, String operator, Object value) {
        String col = columnToStringFunction.apply(column, true);
        Criterion criterion = new Criterion(String.format("%s=%s %s", col, col, operator), value);
        this.addCriterion(criterion);
    }

    protected void addCriterion(Criterion criterion) {
        if (sets == null) {
            sets = new LinkedList<>();
        }
        sets.add(criterion);
    }

    @Override
    public UpdateQuery<T, C> getSelf() {
        return this;
    }

    void sets(SafeAppendable builder) {
        if (!builder.isEmpty()) {
            builder.append(" ");
        }
        builder.append("set");
        if (!sets.isEmpty()) {
            if (!builder.isEmpty()) {
                builder.append(" ");
            }
            int i = 0;
            Iterator<Criterion> it = sets.iterator();
            while (it.hasNext()) {
                if (i++ > 0) {
                    builder.append(",");
                }
                Criterion criterion = it.next();
                builder.append(criterion.getCondition());
                if (criterion.isSingleValue()) {
                    builder.append(" ?");
                    addParameter(criterion.getValue());
                } else if (criterion.isBetweenValue()) {
                    builder.append(" ?").append(" and ?");
                    addParameter(criterion.getValue());
                    addParameter(criterion.getSecondValue());
                } else if (criterion.isListValue()) {
                    List<Object> value = (List<Object>) criterion.getValue();
                    for (Object o : value) {
                        addParameter(o);
                    }
                }
            }
        }

    }

    @Override
    public String toSQL() {
        StringBuilder appendable = new StringBuilder();
        SafeAppendable builder = new SafeAppendable(appendable);
        paramNameSeq = new AtomicInteger();
        this.paramNameValuePairs = new LinkedHashMap<>();
        sqlClause(builder, "update", tables, "", "", ",");
        joins(builder);
        sets(builder);
        sqlClause(builder, "where", where);
        sqlClause(builder, "order by", orderBy, "", "", ", ");
        if (limit != null) {
            appendable.append(" limit ?");
            addParameter(limit);
        }
        return appendable.toString();
    }
}
