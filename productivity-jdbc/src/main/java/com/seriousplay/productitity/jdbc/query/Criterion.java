package com.seriousplay.productitity.jdbc.query;

import java.util.Collection;
import java.util.StringJoiner;

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

    public Criterion(String condition) {
        this();
        this.condition = condition;
        this.typeHandler = null;
        this.noValue = true;
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
            this.listValue = true;

        } else {
            this.singleValue = true;
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

    @Override
    public String toString() {
        return new StringJoiner(", ", Criterion.class.getSimpleName() + "[", "]")
                .add("condition='" + condition + "'")
                .add("value=" + value)
                .add("secondValue=" + secondValue)
                .add("noValue=" + noValue)
                .add("singleValue=" + singleValue)
                .add("betweenValue=" + betweenValue)
                .add("listValue=" + listValue)
                .add("typeHandler='" + typeHandler + "'")
                .toString();
    }
}
