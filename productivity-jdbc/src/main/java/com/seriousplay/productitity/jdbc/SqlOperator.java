package com.seriousplay.productitity.jdbc;

/**
 *
 */
public enum SqlOperator {
    OR("or"),
    AND("and"),
    ISNULL("is null"),
    ISNOTNULL("is not null"),
    EQ("="),
    NE("<>"),
    GT(">"),
    GTE(">="),
    LT("<"),
    LTE("<="),
    IN("in"),
    NOTIN("not in"),
    LIKE("like"),
    NOTLIKE("not like"),
    BETWEEN("between"),
    NOT_BETWEEN("not between"),
    REGEXP("regexp"),
    NOT_REGEXP("not regexp");

    private final String condition;

    SqlOperator(String condition) {
        this.condition = condition;
    }

    public String getCondition() {
        return condition;
    }

    public String format(String column, String column1) {
        return format(column) + " " + column1;
    }

    public String format(String column) {
        return column + " " + condition;
    }
}
