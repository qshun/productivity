package com.seriousplay.productitity.jdbc.query;

import com.seriousplay.productitity.jdbc.SqlOperator;
import com.seriousplay.productitity.jdbc.query.functions.SqlFunction;
import org.apache.commons.lang3.StringUtils;
import org.springframework.util.CollectionUtils;

import java.util.Collection;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;

public abstract class AbstractSqlBuilder<T> {
    protected StringBuffer sql;
    protected List<Object> parameters;

    public AbstractSqlBuilder() {
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
     * @param function
     * @return
     */
    protected T func(SqlFunction function) {
        this.sql.append(function.getFunction()).append("(");
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

    /**
     * @param operator
     * @return
     */
    public T operator(SqlOperator operator) {
        this.append(operator.getCondition());
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

    public Criterion toCriterion() {
        return new Criterion(sql.toString(), parameters);
    }

    public abstract T getSelf();
}
