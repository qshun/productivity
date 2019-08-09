package com.seriousplay.productitity.jdbc.query;

public class Join {
    public static enum JoinType {
        JOIN("join"),
        INNER_JOIN("inner join"),
        OUTER_JOIN("outer join"),
        LEFT_OUTER_JOIN("left outer join"),
        RIGHT_OUTER_JOIN("right outer join ");
        private String keyword;

        JoinType(String keyword) {
            this.keyword = keyword;
        }

        public String getKeyword() {
            return keyword;
        }
    }

    private JoinType joinType;
    private String table;
    private Criteria criteria;

    public String getKeyword() {
        return joinType.getKeyword() + " " + table + " on";
    }

    public Join(JoinType joinType, String table, Criteria criteria) {
        super();
        this.joinType = joinType;
        this.table = table;
        this.criteria = criteria;
    }

    public JoinType getJoinType() {
        return joinType;
    }

    public Join setJoinType(JoinType joinType) {
        this.joinType = joinType;
        return this;
    }

    public String getTable() {
        return table;
    }

    public Join setTable(String table) {
        this.table = table;
        return this;
    }

    public Criteria getCriteria() {
        return criteria;
    }

    public Join setCriteria(Criteria criteria) {
        this.criteria = criteria;
        return this;
    }
}
