package com.seriousplay.productitity.jdbc.query;

public class SqlParameter {


    private SqlParameterType type;
    private Object value;

    public SqlParameter(SqlParameterType type, Object value) {
        this.type = type;
        this.value = value;
    }

    public SqlParameter(Object value) {
        this.type = SqlParameterType.PERPARED_VALUE;
        this.value = value;
    }

    public SqlParameterType getType() {
        return type;
    }

    public SqlParameter setType(SqlParameterType type) {
        this.type = type;
        return this;
    }

    public Object getValue() {
        return value;
    }

    public SqlParameter setValue(Object value) {
        this.value = value;
        return this;
    }
}
