package com.seriousplay.productitity.jdbc.query;

import com.seriousplay.productitity.jdbc.SqlOperator;
import com.seriousplay.productitity.jdbc.query.functions.SqlFunction;
import org.apache.commons.lang3.StringUtils;
import org.springframework.util.CollectionUtils;

import java.util.Collection;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.function.Function;

import static com.seriousplay.productitity.jdbc.SqlOperator.*;

/**
 * @param <T>
 */
public abstract class AbstractCriterionBuilder<T extends AbstractCriterionBuilder> {
    protected StringBuffer sql;
    protected List<Object> parameters;
    private String lastContent;

    public AbstractCriterionBuilder() {
        super();
        this.sql = new StringBuffer();
        this.parameters = new LinkedList<>();
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
     * is null
     *
     * @return
     */
    public T isNull() {
        return operator(ISNULL);
    }

    /**
     * is not null
     *
     * @return
     */
    public T isNotNull() {
        return operator(ISNOTNULL);
    }

    /**
     * =
     *
     * @return
     */
    public T eq() {
        return operator(EQ);
    }

    /**
     * =?
     *
     * @param value
     * @return
     */
    public T eq(Object value) {
        this.operator(EQ).value(value);
        return getSelf();
    }

    /**
     * <>
     *
     * @return
     */
    public T ne() {
        return operator(NE);
    }

    /**
     * <> value
     *
     * @param value
     * @return
     */
    public T ne(Object value) {
        this.operator(NE).value(value);
        return getSelf();
    }

    /**
     * >
     *
     * @return
     */
    public T gt() {
        return operator(GT);
    }

    /**
     * > ?
     *
     * @param value
     * @return
     */
    public T gt(Object value) {
        this.operator(GT).value(value);
        return this.getSelf();
    }

    /**
     * >=
     *
     * @return
     */
    public T gte() {
        return operator(GTE);
    }

    /**
     * >= ?
     *
     * @param value
     * @return
     */
    public T gte(Object value) {
        this.operator(GTE).value(value);
        return getSelf();
    }

    /**
     * <
     *
     * @return
     */
    public T lt() {
        return operator(LT);
    }

    /**
     * < value
     *
     * @param value
     * @return
     */
    public T lt(Object value) {
        this.operator(LT).value(value);
        return getSelf();
    }

    /**
     * <=
     *
     * @return
     */
    public T lte() {
        return operator(LTE);
    }

    /**
     * <=?
     *
     * @param value
     * @return
     */
    public T lte(Object value) {
        this.operator(LTE).value(value);
        return getSelf();
    }

    /**
     * like
     *
     * @return
     */
    public T like() {
        return operator(LIKE);
    }

    /**
     * like ?
     *
     * @param value
     * @return
     */
    public T like(String value) {
        this.operator(LIKE).value(value);
        return getSelf();
    }

    /**
     * not like
     *
     * @return
     */
    public T nlike() {
        return operator(NOTLIKE);
    }

    /**
     * not like value
     *
     * @param value
     * @return
     */
    public T nlike(String value) {
        this.operator(NOTLIKE).value(value);
        return getSelf();
    }

    /**
     * regexp
     *
     * @return
     */
    public T regexp() {
        return operator(REGEXP);
    }

    /**
     * regexp ?
     *
     * @param pattern
     * @return
     */
    public T regexp(String pattern) {
        this.operator(REGEXP).value(pattern);
        return getSelf();
    }

    /**
     * not regexp
     *
     * @return
     */
    public T nregexp() {
        return operator(NOT_REGEXP);
    }

    /**
     * not regexp ?
     *
     * @param pattern
     * @return
     */
    public T nregexp(String pattern) {
        this.operator(NOT_REGEXP).value(pattern);
        return getSelf();
    }

    /**
     * between
     *
     * @return
     */
    public T between() {
        return operator(BETWEEN);
    }

    /**
     * between xx and xx
     *
     * @param value
     * @param value1
     * @return
     */
    public T between(Object value, Object value1) {
        this.operator(BETWEEN).value(value1).operator(AND).value(value1);
        return getSelf();
    }

    /**
     * not between
     *
     * @return
     */
    public T nbetween() {
        return operator(NOT_BETWEEN);
    }

    /**
     * not between
     *
     * @param value
     * @param value1
     * @return
     */
    public T nbetween(Object value, Object value1) {
        this.operator(NOT_BETWEEN).value(value(value).operator(AND).value(value1));
        return getSelf();
    }

    /**
     * and
     *
     * @return
     */
    public T and() {
        return operator(AND);
    }

    /**
     * or
     *
     * @return
     */
    public T or() {
        return operator(OR);
    }

    /**
     * not
     *
     * @return
     */
    public T not() {
        return operator("not");
    }

    /**
     * 添加sql函数开始并补齐左括号
     * sql.apppend(function).append("(")
     *
     * @param function
     * @return
     */
    public T func(SqlFunction function, Function<T, T>... args) {
        this.operator(function.getFunction())
                .leftParentheses();
        if (args != null && args.length > 0) {
            for (int i = 0; i < args.length; i++) {
                if (i > 0) {
                    comma();
                }
                args[i].apply(getSelf());
            }
        }
        this.rightParentheses();
        return getSelf();
    }

    /**
     * exists
     *
     * @return
     */
    public T exists() {
        return operator(EXISTS);
    }

    /**
     * @return
     */
    public T in() {
        this.operator(IN);
        return getSelf();
    }

    /**
     * in
     *
     * @param value
     * @return
     */
    public T in(List<Object> value) {
        this.operator(IN).value(value);
        return getSelf();
    }

    /**
     * not in
     *
     * @return
     */
    public T nin() {
        this.operator(NOTIN);
        return getSelf();
    }

    /**
     * not in
     *
     * @param value
     * @return
     */
    public T nin(List<Object> value) {
        this.operator(NOTIN).value(value);
        return getSelf();
    }

    T comma() {
        return operator(",");
    }

    /**
     * (
     *
     * @return
     */
    protected T leftParentheses() {
        this.operator("(");
        return getSelf();
    }

    /**
     * ）
     *
     * @return
     */
    protected T rightParentheses() {
        this.operator(")");
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
        this.append(operator.getOperator());
        return getSelf();
    }

    protected void append(String content) {
        if (sql.length() > 0) {
            if (!("(".equals(content) || "(".equals(lastContent))) {
                sql.append(" ");
            }

        }
        sql.append(content);
        lastContent = content;
    }

    protected void appendParameters(Collection<Object> parameters) {
        Iterator<Object> iterator = parameters.iterator();
        int i = 0;
        leftParentheses();
        while (iterator.hasNext()) {
            if (i > 0) {
                comma();
            }
            Object value = iterator.next();
            appendParameter(value);
            i++;
        }
        rightParentheses();
    }

    protected void appendParameters(Object[] parameters) {
        leftParentheses();
        for (int i = 0; i < parameters.length; i++) {
            Object value = parameters[i];
            if (i > 0) {
                this.comma();
            }
            appendParameter(value);
        }
        rightParentheses();
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
            this.operator("?");
            this.parameters.add(parameter);
        }
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
