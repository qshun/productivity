package com.seriousplay.productitity.jdbc.query;

import com.seriousplay.productitity.jdbc.SqlOperator;
import org.apache.commons.lang3.StringUtils;
import org.springframework.jdbc.core.PreparedStatementCreator;
import org.springframework.jdbc.core.SqlTypeValue;
import org.springframework.jdbc.core.StatementCreatorUtils;
import org.springframework.util.CollectionUtils;

import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.*;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.function.BiFunction;
import java.util.function.Function;
import java.util.stream.Collectors;

public abstract class AbstractQuery<Q, T, C> implements PreparedStatementCreator {
    private static final String AND = ") \nAND (";
    private static final String OR = ") \nOR (";
    protected BiFunction<T, Boolean, String> tableToStringFunction;
    protected BiFunction<C, Boolean, String> columnToStringFunction;
    protected List<String> tables;
    protected List<Join> joins;
    protected Criteria<C> where;
    protected List<String> orderBy;
    protected Integer limit;
    protected AtomicInteger paramNameSeq;
    protected Map<String, Object> paramNameValuePairs;

    public AbstractQuery(BiFunction<T, Boolean, String> tableToStringFunction, BiFunction<C, Boolean, String> columnToStringFunction) {
        this.tableToStringFunction = tableToStringFunction;
        this.columnToStringFunction = columnToStringFunction;
    }

    public abstract String toSQL();

    /**
     * @param table
     * @param tableAlias
     * @return
     */
    public Q from(T table, String tableAlias) {
        if (table != null) {
            String tableName = tableToStringFunction.apply(table, false);
            from(tableName, tableAlias);

        }

        return getSelf();
    }

    /**
     * @param table
     * @param tableAilas
     * @return
     */
    public Q from(String table, String tableAilas) {
        if (tables == null) {
            tables = new ArrayList<>();
        }
        if (StringUtils.isNotBlank(table)) {
            if (StringUtils.isNotBlank(tableAilas)) {
                this.tables.add(table + " as " + tableAilas);
            } else {
                this.tables.add(table);
            }
        }

        return getSelf();
    }

    public abstract Q getSelf();

    public Q from(T table) {
        if (table != null) {
            String tableName = tableToStringFunction.apply(table, true);
            from(tableName, null);

        }

        return getSelf();
    }

    /**
     * @param table
     * @return
     */
    public Q from(String table) {
        return from(table, null);
    }

    /**
     * @return
     */
    public Criteria<C> where() {
        if (this.where == null) {
            this.where = Criteria.where(columnToStringFunction);
        }
        return this.where;
    }

    /**
     * @param table
     * @param criteria
     * @return
     */
    public Q join(T table, Function<Criteria<C>, Criteria<C>> criteria) {
        addJoin(Join.JoinType.JOIN, table, criteria);
        return getSelf();
    }

    /**
     * @param joinType
     * @param table
     * @param criteria
     */
    void addJoin(Join.JoinType joinType, T table, Function<Criteria<C>, Criteria<C>> criteria) {
        Criteria criteria1 = createCriteriaInternal();
        if (joinType != null && table != null && criteria != null) {
            if (criteria.apply(criteria1).isValid()) {
                if (joins == null) {
                    joins = new ArrayList<>();
                }
                joins.add(new Join(joinType, tableToStringFunction.apply(table, true), criteria1));
            }

        }
    }

    public Criteria<C> createCriteriaInternal() {
        return Criteria.where(columnToStringFunction);
    }

    /**
     * @param table
     * @param criteria
     * @return
     */
    public Q join(T table, Criteria<C> criteria) {
        addJoin(Join.JoinType.JOIN, table, criteria);
        return getSelf();
    }

    /**
     * 添加join子句
     *
     * @param joinType
     * @param table
     * @param criteria
     */
    void addJoin(Join.JoinType joinType, T table, Criteria<C> criteria) {
        if (joinType != null && table != null && criteria.isValid()) {
            if (joins == null) {
                joins = new ArrayList<>();
            }
            joins.add(new Join(joinType, tableToStringFunction.apply(table, true), criteria));
        }
    }

    /**
     * @param table
     * @param criteria
     * @return
     */
    public Q innerJoin(T table, Criteria<C> criteria) {
        addJoin(Join.JoinType.INNER_JOIN, table, criteria);
        return getSelf();
    }

    /**
     * @param table
     * @param criteria
     * @return
     */
    public Q innerJoin(T table, Function<Criteria<C>, Criteria<C>> criteria) {
        addJoin(Join.JoinType.INNER_JOIN, table, criteria);
        return getSelf();
    }

    /**
     * @param table
     * @param criteria
     * @return
     */
    public Q outerJoin(T table, Criteria<C> criteria) {
        addJoin(Join.JoinType.OUTER_JOIN, table, criteria);
        return getSelf();
    }

    /**
     * @param table
     * @param criteria
     * @return
     */
    public Q outerJoin(T table, Function<Criteria<C>, Criteria<C>> criteria) {
        addJoin(Join.JoinType.OUTER_JOIN, table, criteria);
        return getSelf();
    }

    /**
     * @param table
     * @param criteria
     * @return
     */
    public Q leftOuterJoin(T table, Criteria<C> criteria) {
        addJoin(Join.JoinType.LEFT_OUTER_JOIN, table, criteria);
        return getSelf();
    }

    /**
     * @param table
     * @param criteria
     * @return
     */
    public Q leftOuterJoin(T table, Function<Criteria<C>, Criteria<C>> criteria) {
        addJoin(Join.JoinType.LEFT_OUTER_JOIN, table, criteria);
        return getSelf();
    }

    /**
     * @param table
     * @param criteria
     * @return
     */
    public Q rightOuterJoin(T table, Criteria<C> criteria) {
        addJoin(Join.JoinType.RIGHT_OUTER_JOIN, table, criteria);
        return getSelf();
    }

    /**
     * @param table
     * @param criteria
     * @return
     */
    public Q rightOuterJoin(T table, Function<Criteria<C>, Criteria<C>> criteria) {
        addJoin(Join.JoinType.RIGHT_OUTER_JOIN, table, criteria);
        return getSelf();
    }

    /**
     * @param column
     * @return
     */
    public Q orderby(C column) {
        return orderby(column, null);
    }

    /**
     * @param column
     * @param direction
     * @return
     */
    public Q orderby(C column, OrderDirection direction) {
        String col = columnToStringFunction.apply(column, true);
        if (this.orderBy == null) {
            orderBy = new LinkedList<>();
        }
        if (direction == null) {
            orderBy.add(col);
        } else {
            orderBy.add(col + " " + direction.name());
        }

        return getSelf();
    }

    /**
     * @param limit
     * @return
     */
    public Q limit(Integer limit) {
        this.limit = limit;
        return getSelf();
    }

    protected List<String> getAllTableAlias() {
        if (CollectionUtils.isEmpty(tables)) {
            return null;
        }
        return tables.stream().map(table -> {
            String[] t = table.split(" as ");
            if (t.length == 1) {
                t = table.split(" ");
            }
            if (t.length == 1) {
                return table;
            } else {
                String alias = null;
                for (int i = 1; i < t.length; i++) {
                    alias = t[i];
                    if (StringUtils.isNotBlank(alias)) {
                        break;
                    }
                }
                if (StringUtils.isNotBlank(alias)) {
                    return alias;
                } else {
                    return table;
                }
            }
        }).collect(Collectors.toList());
    }

    protected void sqlClause(SafeAppendable builder, String keyword, List<String> parts, String open, String close, String conjunction) {
        if (!CollectionUtils.isEmpty(parts)) {
            if (!builder.isEmpty()) {
                builder.append(" ");
            }
            builder.append(keyword);
            builder.append(" ");
            builder.append(open);
            String last = "________";
            for (int i = 0, n = parts.size(); i < n; i++) {
                String part = parts.get(i);
                if (i > 0 && !part.equals(AND) && !part.equals(OR) && !last.equals(AND) && !last.equals(OR)) {
                    builder.append(conjunction);
                }
                builder.append(part);
                last = part;
            }
            builder.append(close);
        }
    }

    protected void joins(SafeAppendable builder) {
        if (CollectionUtils.isEmpty(joins)) {
            return;
        }
        for (Join one : joins) {
            sqlClause(builder, one.getKeyword(), one.getCriteria());
        }
    }

    protected void sqlClause(SafeAppendable builder, String keyword, Criteria<C> criteria) {
        if (criteria == null || !criteria.isValid()) {
            return;
        }
        if (!builder.isEmpty()) {
            builder.append(" ");
        }
        builder.append(keyword).append(" ");
        int j = 0;
        String lastCondition = null;
        for (Criteria.Criterion criterion : criteria.getCriteria()) {
            String curCondition = criterion.getCondition();
            if (j > 0 && j < criteria.getCriteria().size()) {
                if ("(".equals(lastCondition)) {
                    if (SqlOperator.OR.getOperator().equals(curCondition) || SqlOperator.AND.getOperator().equals(curCondition)) {
                        lastCondition = curCondition;
                        continue;
                    }
                }
                boolean appendAnd = !("(".equals(lastCondition)
                        || SqlOperator.OR.getOperator().equals(lastCondition)
                        || SqlOperator.AND.getOperator().equals(lastCondition)
                        || SqlOperator.OR.getOperator().equals(curCondition)
                        || SqlOperator.AND.getOperator().equals(curCondition)
                        || ")".equals(curCondition));
                if (appendAnd) {
                    builder.append(" ").append(SqlOperator.AND.getOperator()).append(" ");
                }
            } else if (SqlOperator.OR.getOperator().equals(curCondition) || SqlOperator.AND.getOperator().equals(curCondition)) {
                lastCondition = curCondition;
                continue;
            }
            switch (criterion.valueType) {
                case NO_VALUE: {
                    if (SqlOperator.OR.getOperator().equals(curCondition) || SqlOperator.AND.getOperator().equals(curCondition)) {
                        builder.append(" ");
                    }
                    builder.append(criterion.getCondition());
                    if (SqlOperator.OR.getOperator().equals(curCondition) || SqlOperator.AND.getOperator().equals(curCondition)) {
                        builder.append(" ");
                    }
                    break;
                }
                case SINGLE_VALUE: {
                    builder.append(criterion.getCondition()).append(" ?");
                    addParameter(criterion.getValue());
                    break;
                }
                case DOUBLE_VALUE: {
                    builder.append(criterion.getCondition()).append(" ?").append(" and ?");
                    addParameter(criterion.getValue());
                    addParameter(criterion.getSecondValue());
                    break;
                }
                case LIST_VALUE: {
                    List<Object> value = (List<Object>) criterion.getValue();
                    boolean inCondition = SqlOperator.IN.getOperator().equals(curCondition);
                    builder.append(criterion.getCondition());
                    if (inCondition) {
                        if (CollectionUtils.isEmpty(value)) {
                            throw new RuntimeException("Value for condition cannot be null");
                        }
                        builder.append("(");
                        int i = 0;
                        for (Object one : value) {
                            if (i++ > 0) {
                                builder.append(",");
                            }
                            builder.append("?");
                            addParameter(one);
                        }
                        builder.append(")");
                    } else {
                        for (Object o : value) {
                            addParameter(o);
                        }
                    }
                }
                break;
                default: {
                    break;
                }
            }
            j++;
            lastCondition = criterion.getCondition();
        }
    }

    void addParameter(Object value) {
        paramNameValuePairs.put("_p_" + paramNameSeq.incrementAndGet(), value);
    }

    public Object[] getParameters() {
        return paramNameValuePairs != null ? getParamNameValuePairs().values().toArray() : null;
    }

    public Map<String, Object> getParamNameValuePairs() {
        return paramNameValuePairs;
    }

    @Override
    public PreparedStatement createPreparedStatement(Connection con) throws SQLException {
        PreparedStatement ps = con.prepareStatement(toSQL());
        if (CollectionUtils.isEmpty(paramNameValuePairs)) {
            return ps;
        }
        Iterator<Map.Entry<String, Object>> iterator = paramNameValuePairs.entrySet().iterator();
        for (int i = 1; iterator.hasNext(); i++) {
            Map.Entry<String, Object> next = iterator.next();
            StatementCreatorUtils.setParameterValue(ps, i, SqlTypeValue.TYPE_UNKNOWN, next.getKey(), next.getValue());
        }
        return ps;
    }

    protected static class SafeAppendable {
        private final Appendable a;
        private boolean empty = true;

        public SafeAppendable(Appendable a) {
            super();
            this.a = a;
        }

        @Override
        public String toString() {
            return a.toString();
        }

        public SafeAppendable append(CharSequence s) {
            try {
                if (empty && s.length() > 0) {
                    empty = false;
                }
                a.append(s);
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
            return this;
        }

        public boolean isEmpty() {
            return empty;
        }

    }
}
