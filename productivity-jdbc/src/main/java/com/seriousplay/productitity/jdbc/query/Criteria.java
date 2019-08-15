package com.seriousplay.productitity.jdbc.query;

import com.seriousplay.productitity.jdbc.PositionParameterSql;
import com.seriousplay.productitity.jdbc.SqlOperator;
import org.apache.commons.lang3.StringUtils;

import java.util.ArrayList;
import java.util.List;
import java.util.function.BiFunction;
import java.util.function.Function;

public class Criteria<T> {

    public static <T> Criteria<T> where(BiFunction<T, Boolean, String> columnToString) {
        return new Criteria<>(columnToString);
    }

    public Criteria(BiFunction<T, Boolean, String> columnToString) {
        super();
        this.columnToString = columnToString;
        criteria = new ArrayList<>();
    }

    protected List<Criterion> criteria;
    private BiFunction<T, Boolean, String> columnToString;

    public BiFunction<T, Boolean, String> getColumnToString() {
        return columnToString;
    }

    public boolean isValid() {
        return criteria.size() > 0;
    }

    public Criterion<T> column(T column) {
        return new Criterion(this, columnToString.apply(column, true));

    }

    public Criterion<T> column(T column, String tableAlias) {
        String columnName = columnToString.apply(column, false);
        columnName = StringUtils.isNotBlank(tableAlias) ? tableAlias + "." + columnName : columnName;
        return new Criterion(this, columnName);

    }

    protected Criterion<T> addCriterion(String condition) {
        if (condition == null) {
            throw new RuntimeException("Value for condition cannot be null");
        }
        Criterion criterion = new Criterion(this, condition);
        criteria.add(criterion);
        return criterion;
    }

    public Criteria<T> condition(Criterion<T> condition) {
        if (condition == null) {
            throw new RuntimeException("Value for condition cannot be null");
        }
        criteria.add(condition);
        return this;
    }

    /**
     * 添加条件
     *
     * @param sql        查询条件，id=? and name=? and age=?
     * @param parameters 参数
     * @return
     */
    public Criteria<T> sql(String sql, Object... parameters) {
        PositionParameterSql positionParameterSql = PositionParameterSql.parseSql(sql, parameters);
        Criterion criterion = new Criterion(this, positionParameterSql.getSql().toString(),
                positionParameterSql.getParameters());
        this.getAllCriteria().add(criterion);
        return this;
    }

    /**
     * @param sql
     * @param parameters
     * @return
     */
    public Criteria<T> sql(String sql, List<Object> parameters) {
        PositionParameterSql positionParameterSql = PositionParameterSql.parseSql(sql, parameters);
        Criterion criterion = new Criterion(this, positionParameterSql.getSql().toString(), positionParameterSql.getParameters());
        this.getAllCriteria().add(criterion);
        return this;
    }

    protected void addCriterion(T column, SqlOperator condition, Object value) {
        if (column == null || condition == null || value == null) {
            throw new RuntimeException("Value for condition cannot be null");
        }
        String columnName = columnToString.apply(column, true);
        addCriterion(condition.format(columnName), value);
    }

    protected void addCriterion(String condition, Object value) {
        criteria.add(new Criterion(this, condition, value));
    }


    /**
     * @param function
     * @return
     */
    public Criteria<T> or(Function<Criteria<T>, Criteria<T>> function) {
        return condition(function, SqlOperator.OR);
    }

    private Criteria condition(Function<Criteria<T>, Criteria<T>> function, SqlOperator SqlOperator) {
        if (SqlOperator.OR.equals(SqlOperator) || SqlOperator.AND.equals(SqlOperator)) {
            Criteria newCriteria = new Criteria(this.columnToString);
            function.apply(newCriteria);
            condition(newCriteria, SqlOperator);
        }
        return this;
    }

    private Criteria condition(Criteria<T> criteria, SqlOperator SqlOperator) {
        if (SqlOperator.OR.equals(SqlOperator) || SqlOperator.AND.equals(SqlOperator)) {
            if (criteria != this && criteria.getCriteria().size() > 0) {
                addCriterion(SqlOperator.getCondition());
                boolean appendParentheses = criteria.getCriteria().size() > 1;
                if (appendParentheses) {
                    addCriterion("(");
                }
                this.criteria.addAll(criteria.getAllCriteria());
                if (appendParentheses) {
                    addCriterion(")");
                }
            }

        }
        return this;
    }

    public List<Criterion> getCriteria() {
        return criteria;
    }

    public List<Criterion> getAllCriteria() {
        return criteria;
    }

    /**
     * and
     *
     * @param function
     * @return
     */
    public Criteria<T> and(Function<Criteria<T>, Criteria<T>> function) {
        return condition(function, SqlOperator.AND);
    }

    /***
     * 用括号吧条件包起来
     * @param function
     * @return
     */
    public Criteria<T> parentheses(Function<Criteria<T>, Criteria<T>> function) {
        Criteria newCriteria = new Criteria(this.columnToString);
        function.apply(newCriteria);
        if (newCriteria.getCriteria().size() > 0) {
            boolean appendParentheses = newCriteria.getCriteria().size() > 1;
            if (appendParentheses) {
                addCriterion("(");
            }
            this.criteria.addAll(newCriteria.getAllCriteria());
            if (appendParentheses) {
                addCriterion(")");
            }
        }
        return this;
    }

    public static class Criterion<T> extends com.seriousplay.productitity.jdbc.query.Criterion {
        private Criteria<T> criteria;

        protected Criterion(Criteria<T> criteria, String condition) {
            super(condition);
            this.criteria = criteria;
        }

        protected Criterion(Criteria<T> criteria, String condition, Object value) {
            this(criteria, condition, value, null);
        }

        protected Criterion(Criteria<T> criteria, String condition, Object value, String typeHandler) {
            super(condition, value, typeHandler);
            this.criteria = criteria;
        }

        protected Criterion(Criteria<T> criteria, String condition, Object value, Object secondValue) {
            this(criteria, condition, value, secondValue, null);
        }

        protected Criterion(Criteria<T> criteria, String condition, Object value, Object secondValue, String typeHandler) {
            super(condition, value, secondValue, typeHandler);
            this.criteria = criteria;
        }


        public Criteria<T> isNull() {
            return addCondition(SqlOperator.ISNULL);
        }

        public Criteria<T> isNotNull() {
            return addCondition(SqlOperator.ISNOTNULL);
        }

        public Criteria<T> eq(Object value) {
            return addCondition(SqlOperator.EQ, value);
        }

        public Criteria<T> eqColumn(T column) {
            return addColumnCondition(SqlOperator.EQ, column);
        }

        public Criteria<T> eqColumn(T column, String tableAlias) {
            return addColumnCondition(SqlOperator.EQ, column, tableAlias);
        }

        public Criteria<T> ne(Object value) {
            return addCondition(SqlOperator.NE, value);
        }

        public Criteria<T> neColumn(T column, String tableAlias) {
            return addColumnCondition(SqlOperator.NE, column, tableAlias);
        }

        public Criteria<T> neColumn(T column) {
            return addColumnCondition(SqlOperator.NE, column);
        }


        public Criteria<T> gt(Object value) {
            return addCondition(SqlOperator.GT, value);
        }

        public Criteria<T> gtColumn(T column) {
            return addColumnCondition(SqlOperator.GT, column);
        }

        public Criteria<T> gtColumn(T column, String tableAlias) {
            return addColumnCondition(SqlOperator.GT, column, tableAlias);
        }

        public Criteria<T> gte(Object value) {
            return addCondition(SqlOperator.GTE, value);
        }

        public Criteria<T> gteColumn(T column) {
            return addColumnCondition(SqlOperator.GTE, column);
        }

        public Criteria<T> gteColumn(T column, String tableAlias) {
            return addColumnCondition(SqlOperator.GTE, column, tableAlias);
        }

        public Criteria<T> lt(Object value) {
            return addCondition(SqlOperator.LT, value);
        }

        public Criteria<T> ltColumn(T column) {
            return addColumnCondition(SqlOperator.LT, column);
        }

        public Criteria<T> ltColumn(T column, String tableAlias) {
            return addColumnCondition(SqlOperator.LT, column, tableAlias);
        }

        public Criteria<T> lte(Object value) {
            return addCondition(SqlOperator.LTE, value);
        }

        public Criteria<T> lteColumn(T column) {
            return addColumnCondition(SqlOperator.LTE, column);
        }

        public Criteria<T> lteColumn(T column, String tableAlias) {
            return addColumnCondition(SqlOperator.LTE, column, tableAlias);
        }

        public Criteria<T> in(List<Object> value) {
            return addCondition(SqlOperator.IN, value);
        }

        public Criteria<T> nin(List<Object> value) {
            return addCondition(SqlOperator.NOTIN, value);
        }

        public Criteria<T> like(String value) {
            return addCondition(SqlOperator.LIKE, value);
        }

        public Criteria<T> likeColumn(T column) {
            return addColumnCondition(SqlOperator.LIKE, column);
        }

        public Criteria<T> likeColumn(T column, String tableAlias) {
            return addColumnCondition(SqlOperator.LIKE, column, tableAlias);
        }

        public Criteria<T> nlike(String value) {
            return addCondition(SqlOperator.NOTLIKE, value);
        }

        public Criteria<T> nlikeColumn(T column) {
            return addColumnCondition(SqlOperator.NOTLIKE, column, null);
        }

        public Criteria<T> nlikeColumn(T column, String tableAlias) {
            return addColumnCondition(SqlOperator.NOTLIKE, column, tableAlias);
        }

        public Criteria<T> regexp(String pattern) {
            return addCondition(SqlOperator.REGEXP, pattern);
        }

        public Criteria<T> regexpColumn(T column) {
            return addColumnCondition(SqlOperator.REGEXP, column);
        }

        public Criteria<T> regexpColumn(T column, String tableAlias) {
            return addColumnCondition(SqlOperator.REGEXP, column, tableAlias);
        }

        public Criteria<T> nregexp(String pattern) {
            return addCondition(SqlOperator.NOT_REGEXP, pattern);
        }

        public Criteria<T> nregexpColumn(T column) {
            return addColumnCondition(SqlOperator.NOT_REGEXP, column);
        }

        public Criteria<T> nregexpColumn(T column, String tableAlias) {
            return addColumnCondition(SqlOperator.NOT_REGEXP, column, tableAlias);
        }

        public Criteria<T> between(Object value1, Object value2) {
            return addCondition(SqlOperator.BETWEEN, value1, value2);
        }

        public Criteria<T> nbetween(Object value1, Object value2) {
            return addCondition(SqlOperator.NOT_BETWEEN, value1, value2);
        }

        protected Criteria<T> addColumnCondition(SqlOperator operator, T column, String tableAlias) {
            if (column == null) {
                throw new RuntimeException("Value for condition cannot be null");
            }
            String columnName = criteria.getColumnToString().apply(column, false);
            String actualColumn = StringUtils.isNotBlank(tableAlias) ? tableAlias + "." + columnName : columnName;
            this.condition = operator.format(this.condition, actualColumn);
            return criteria.condition(this);

        }

        protected Criteria<T> addColumnCondition(SqlOperator operator, T column) {
            if (column == null) {
                throw new RuntimeException("Value for condition cannot be null");
            }
            this.condition = operator.format(this.condition, criteria.getColumnToString().apply(column, true));
            return criteria.condition(this);

        }

        protected Criteria<T> addCondition(SqlOperator operator) {
            this.condition = operator.format(this.condition);
            this.noValue = true;
            return criteria.condition(this);

        }

        protected Criteria<T> addCondition(SqlOperator operator, Object value) {
            if (value == null) {
                throw new RuntimeException("Value for condition cannot be null");
            }
            this.condition = operator.format(this.condition);
            this.value = value;
            this.noValue = false;
            if (value instanceof List<?>) {
                this.listValue = true;
            } else {
                this.singleValue = true;
            }
            return criteria.condition(this);

        }

        protected Criteria<T> addCondition(SqlOperator operator, Object value1, Object value2) {
            if (value1 == null || value2 == null) {
                throw new RuntimeException("Between values for " + condition + " cannot be null");
            }
            this.condition = operator.format(this.condition);
            this.value = value1;
            this.secondValue = value2;
            this.noValue = false;
            this.betweenValue = true;
            return criteria.condition(this);

        }
    }
}
