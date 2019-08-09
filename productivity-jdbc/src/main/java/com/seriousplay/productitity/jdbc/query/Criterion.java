package com.seriousplay.productitity.jdbc.query;

import java.util.List;

public class Criterion {
    protected String condition;
    protected Object value;
    protected Object secondValue;
    protected boolean noValue;
    protected boolean singleValue;
    protected boolean betweenValue;
    protected boolean listValue;
    protected String typeHandler;

    protected Criterion() {
        super();
    }

    Criterion(String condition) {
        this();
        this.condition = condition;
        this.typeHandler = null;
        this.noValue = true;
    }

    Criterion(String condition, Object value) {
        this(condition, value, null);
    }

    Criterion(String condition, Object value, String typeHandler) {
        this();
        this.condition = condition;
        this.value = value;
        this.typeHandler = typeHandler;
        if (value instanceof List<?>) {
            this.listValue = true;
        } else {
            this.singleValue = true;
        }
    }

    Criterion(String condition, Object value, Object secondValue) {
        this(condition, value, secondValue, null);
    }

    Criterion(String condition, Object value, Object secondValue, String typeHandler) {
        this();
        this.condition = condition;
        this.value = value;
        this.secondValue = secondValue;
        this.typeHandler = typeHandler;
        this.betweenValue = true;
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
        return noValue;
    }

    public boolean isSingleValue() {
        return singleValue;
    }

    public boolean isBetweenValue() {
        return betweenValue;
    }

    public boolean isListValue() {
        return listValue;
    }

}
