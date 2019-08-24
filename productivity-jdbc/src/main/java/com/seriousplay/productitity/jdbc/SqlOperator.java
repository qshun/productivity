package com.seriousplay.productitity.jdbc;

/**
 *
 */
public enum SqlOperator {
    //Logical Operators
    /**
     *
     */
    OR("or"),
    /**
     *
     */
    AND("and"),
    /**
     *
     */
    /**
     *
     */
    NOT("not"),
    /**
     *
     */
    XOR("xor"),
    //Comparison  Operators
    ISNULL("is null"),
    /**
     *
     */
    ISNOTNULL("is not null"),
    /**
     *
     */
    EQ("="),
    /**
     *
     */
    NE("<>"),
    /**
     *
     */
    GT(">"),
    /**
     *
     */
    GTE(">="),
    /**
     *
     */
    LT("<"),
    /**
     *
     */
    LTE("<="),
    /**
     *
     */
    IN("in"),
    /**
     *
     */
    NOTIN("not in"),
    /**
     *
     */
    LIKE("like"),
    /**
     *
     */
    NOTLIKE("not like"),
    /**
     *
     */
    BETWEEN("between"),
    /***
     *
     */
    NOT_BETWEEN("not between"),
    /**
     *
     */
    REGEXP("regexp"),
    /**
     *
     */
    NOT_REGEXP("not regexp"),
    /**
     *
     */
    EXISTS("exists"),
    // Arithmetic Operators
    /**
     * Addition operator
     */
    ADDITION("+"),
    /**
     * Minus operator
     */
    SUBTRACTION("-"),
    /**
     * Modulo operator
     */
    MOD("%"),
    /**
     * Multiplication operator
     */
    MULTIPLICATION("*"),
    /**
     * Division operator*
     */
    DIVISION("/"),
    /**
     * Integer division
     */
    INTEGER_DIVISION("DIV"),
    // Bit  Operators start
    /**
     * &	Bitwise AND
     */
    BITWISE_AND("&"),
    /**
     * ~	Bitwise inversion
     */
    BITWISE_INVERSION("~"),
    /**
     * |	Bitwise OR
     */
    BITWISE_OR("|"),
    /**
     * ^	Bitwise XOR
     */
    BITWISE_XOR("^"),
    /**
     * <<	Left shift
     */
    LEFT_SHIFT("<<"),
    /**
     * >>	Right shift
     */
    RIGHT_SHIFT(">>");
    // Bit  Operators end
    private final String operator;

    SqlOperator(String operator) {
        this.operator = operator;
    }

    public String getOperator() {
        return operator;
    }

    public String format(String column, String column1) {
        return format(column) + " " + column1;
    }

    public String format(String column) {
        return column + " " + operator;
    }
}
