package com.seriousplay.productitity.jdbc.query;

import java.util.Collection;
import java.util.StringJoiner;

/**
 *
 */
public class Criterion {
    protected String condition;
    protected Object value;
    protected Object secondValue;
    protected SqlValueType valueType;
    protected String typeHandler;

    protected Criterion() {
        super();
    }

    public Criterion(String condition) {
        this();
        this.condition = condition;
        this.typeHandler = null;
        this.valueType = SqlValueType.NO_VALUE;
    }

    public Criterion(String condition, Object value) {
        this(condition, value, null);
    }

    public Criterion(String condition, Object value, String typeHandler) {
        this();
        this.condition = condition;
        this.value = value;
        this.typeHandler = typeHandler;
        if (value instanceof Collection) {
            this.valueType = SqlValueType.LIST_VALUE;
        } else if (value instanceof Object[]) {
            this.valueType = SqlValueType.LIST_VALUE;
        } else {
            this.valueType = SqlValueType.SINGLE_VALUE;
        }
    }

    public Criterion(String condition, Object value, Object secondValue) {
        this(condition, value, secondValue, null);
    }

    public Criterion(String condition, Object value, Object secondValue, String typeHandler) {
        this();
        this.condition = condition;
        this.value = value;
        this.secondValue = secondValue;
        this.typeHandler = typeHandler;
        this.valueType = SqlValueType.DOUBLE_VALUE;
    }

    public String getCondition() {
        return condition;
    }

    public Object getValue() {
        return value;
    }

    public Object getSecondValue() {
        return secondValue;
    }

    public boolean isNoValue() {
        return SqlValueType.NO_VALUE.equals(valueType);
    }

    public boolean isSingleValue() {
        return SqlValueType.SINGLE_VALUE.equals(valueType);
    }

    public boolean isBetweenValue() {
        return SqlValueType.DOUBLE_VALUE.equals(valueType);
    }

    public boolean isListValue() {
        return SqlValueType.LIST_VALUE.equals(valueType);
    }

    @Override
    public String toString() {
        return new StringJoiner(", ", Criterion.class.getSimpleName() + "[", "]")
                .add("condition='" + condition + "'")
                .add("value=" + value)
                .add("secondValue=" + secondValue)
                .add("valueType=" + valueType)
                .add("typeHandler='" + typeHandler + "'")
                .toString();
    }
}
