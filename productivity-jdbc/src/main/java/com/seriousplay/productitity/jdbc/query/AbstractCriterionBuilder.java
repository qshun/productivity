package com.seriousplay.productitity.jdbc.query;

import com.seriousplay.productitity.jdbc.SqlOperator;
import org.apache.commons.lang3.StringUtils;
import org.springframework.util.CollectionUtils;

import java.util.Collection;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;

import static com.seriousplay.productitity.jdbc.SqlOperator.*;

/**
 * @param <T>
 */
public abstract class AbstractCriterionBuilder<T extends AbstractCriterionBuilder> {
    protected StringBuffer sql;
    protected List<Object> parameters;

    public AbstractCriterionBuilder() {
        super();
        this.sql = new StringBuffer();
        this.parameters = new LinkedList<>();
    }

    protected void appendParameters(Collection<Object> parameters) {
        Iterator<Object> iterator = parameters.iterator();
        int i = 0;
        while (iterator.hasNext()) {
            if (i > 0) {
                sql.append(",");
            }
            Object value = iterator.next();
            appendParameter(value);
            i++;
        }
    }

    protected void appendParameters(Object[] parameters) {
        for (int i = 0; i < parameters.length; i++) {
            Object value = parameters[i];
            if (i > 0) {
                sql.append(",");
            }
            appendParameter(value);
        }

    }

    protected void appendParameter(Object parameter) {
        if (parameter instanceof Collection) {
            Collection collection = (Collection) parameter;
            if (!CollectionUtils.isEmpty(collection)) {
                appendParameters(collection);
            }
        } else if (parameter instanceof Object[]) {
            Object[] array = (Object[]) parameter;
            if (array.length > 0) {
                appendParameters(array);
            }
        } else {
            sql.append("?");
            parameters.add(parameter);
        }
    }

    public StringBuffer getSql() {
        return sql;
    }

    public T setSql(StringBuffer sql) {
        this.sql = sql;
        return getSelf();
    }


    /**
     * @param column
     * @return
     */
    public T column(String column) {
        return this.column(column, null);
    }

    /**
     * @param column
     * @param tableAilas
     * @return
     */
    public T column(String column, String tableAilas) {
        String condition = StringUtils.isNotBlank(tableAilas) ? tableAilas + "." + column : column;
        this.append(condition);
        return getSelf();
    }

    /**
     * @param value
     * @return
     */
    public T value(Object value) {
        this.appendParameter(value);
        return getSelf();
    }

    /**
     * @param operator
     * @return
     */
    public T operator(String operator) {
        this.append(operator);
        return getSelf();
    }

    public T comma() {
        return operator(",");
    }

    /**
     * @param operator
     * @return
     */
    public T operator(SqlOperator operator) {
        this.append(operator.getOperator());
        return getSelf();
    }

    public T isNull() {
        return operator(ISNULL);
    }

    public T isNotNull() {
        return operator(ISNOTNULL);
    }

    public T eq() {
        return operator(EQ);
    }

    public T eq(Object value) {
        this.operator(EQ).value(value);
        return getSelf();
    }

    public T ne() {
        return operator(NE);
    }

    public T ne(Object value) {
        this.operator(NE).value(value);
        return getSelf();
    }

    public T gt() {
        return operator(GT);
    }

    public T gt(Object value) {
        this.operator(GT).value(value);
        return this.getSelf();
    }

    public T gte() {
        return operator(GTE);
    }

    public T gte(Object value) {
        this.operator(GTE).value(value);
        return getSelf();
    }

    public T lt() {
        return operator(LT);
    }

    public T lt(Object value) {
        this.operator(LT).value(value);
        return getSelf();
    }

    public T lte() {
        return operator(LTE);
    }

    public T lte(Object value) {
        this.operator(LTE).value(value);
        return getSelf();
    }

    public T like() {
        return operator(LIKE);
    }

    public T like(String value) {
        this.operator(LIKE).value(value);
        return getSelf();
    }

    public T nlike() {
        return operator(NOTLIKE);
    }

    public T nlike(String value) {
        this.operator(NOTLIKE).value(value);
        return getSelf();
    }

    public T regexp() {
        return operator(REGEXP);
    }

    public T regexp(String pattern) {
        this.operator(REGEXP).value(pattern);
        return getSelf();
    }

    public T nregexp() {
        return operator(NOT_REGEXP);
    }

    public T nregexp(String pattern) {
        this.operator(NOT_REGEXP).value(pattern);
        return getSelf();
    }

    public T between() {
        return operator(BETWEEN);
    }

    public T between(Object value, Object value1) {
        this.operator(BETWEEN).value(value1).operator(AND).value(value1);
        return getSelf();
    }

    public T nbetween() {
        return operator(NOT_BETWEEN);
    }

    public T and() {
        return operator(OR);
    }

    public T or() {
        return operator(OR);
    }

    public T not() {
        return operator("not");
    }

    public T exists() {
        return operator(EXISTS);
    }

    public T nbetween(Object value, Object value1) {
        this.operator(NOT_BETWEEN).value(value(value).operator(AND).value(value1));
        return getSelf();
    }

    public T in(List<Object> value) {
        this.operator(SqlOperator.IN).value(value);
        return getSelf();
    }

    public T nin(List<Object> value) {
        this.operator(SqlOperator.NOTIN).value(value);
        return getSelf();
    }

    protected T leftParentheses() {
        this.operator("(");
        return getSelf();
    }

    protected T rightParentheses() {
        this.operator(")");
        return getSelf();
    }

    protected void append(String content) {
        sql.append(content).append(" ");
    }

    public List<Object> getParameters() {
        return parameters;
    }

    public void setParameters(List<Object> parameters) {
        this.parameters = parameters;
    }

    public Criterion build() {
        return new Criterion(sql.toString(), parameters);
    }

    public abstract T getSelf();
}
