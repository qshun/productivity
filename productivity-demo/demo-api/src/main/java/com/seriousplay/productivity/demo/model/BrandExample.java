package com.seriousplay.productivity.demo.model;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class BrandExample {
    protected String orderByClause;

    protected boolean distinct;

    protected List<Criteria> oredCriteria;

    public BrandExample() {
        oredCriteria = new ArrayList<Criteria>();
    }

    public void setOrderByClause(String orderByClause) {
        this.orderByClause = orderByClause;
    }

    public String getOrderByClause() {
        return orderByClause;
    }

    public void setDistinct(boolean distinct) {
        this.distinct = distinct;
    }

    public boolean isDistinct() {
        return distinct;
    }

    public List<Criteria> getOredCriteria() {
        return oredCriteria;
    }

    public void or(Criteria criteria) {
        oredCriteria.add(criteria);
    }

    public Criteria or() {
        Criteria criteria = createCriteriaInternal();
        oredCriteria.add(criteria);
        return criteria;
    }

    public Criteria createCriteria() {
        Criteria criteria = createCriteriaInternal();
        if (oredCriteria.size() == 0) {
            oredCriteria.add(criteria);
        }
        return criteria;
    }

    protected Criteria createCriteriaInternal() {
        Criteria criteria = new Criteria();
        return criteria;
    }

    public void clear() {
        oredCriteria.clear();
        orderByClause = null;
        distinct = false;
    }

    protected abstract static class GeneratedCriteria {
        protected List<Criterion> criteria;

        protected GeneratedCriteria() {
            super();
            criteria = new ArrayList<Criterion>();
        }

        public boolean isValid() {
            return criteria.size() > 0;
        }

        public List<Criterion> getAllCriteria() {
            return criteria;
        }

        public List<Criterion> getCriteria() {
            return criteria;
        }

        protected void addCriterion(String condition) {
            if (condition == null) {
                throw new RuntimeException("Value for condition cannot be null");
            }
            criteria.add(new Criterion(condition));
        }

        protected void addCriterion(String condition, Object value, String property) {
            if (value == null) {
                throw new RuntimeException("Value for " + property + " cannot be null");
            }
            criteria.add(new Criterion(condition, value));
        }

        protected void addCriterion(String condition, Object value1, Object value2, String property) {
            if (value1 == null || value2 == null) {
                throw new RuntimeException("Between values for " + property + " cannot be null");
            }
            criteria.add(new Criterion(condition, value1, value2));
        }

        public Criteria andId_IsNull() {
            addCriterion("id_ is null");
            return (Criteria) this;
        }

        public Criteria andId_IsNotNull() {
            addCriterion("id_ is not null");
            return (Criteria) this;
        }

        public Criteria andId_EqualTo(String value) {
            addCriterion("id_ =", value, "id_");
            return (Criteria) this;
        }

        public Criteria andId_NotEqualTo(String value) {
            addCriterion("id_ <>", value, "id_");
            return (Criteria) this;
        }

        public Criteria andId_GreaterThan(String value) {
            addCriterion("id_ >", value, "id_");
            return (Criteria) this;
        }

        public Criteria andId_GreaterThanOrEqualTo(String value) {
            addCriterion("id_ >=", value, "id_");
            return (Criteria) this;
        }

        public Criteria andId_LessThan(String value) {
            addCriterion("id_ <", value, "id_");
            return (Criteria) this;
        }

        public Criteria andId_LessThanOrEqualTo(String value) {
            addCriterion("id_ <=", value, "id_");
            return (Criteria) this;
        }

        public Criteria andId_Like(String value) {
            addCriterion("id_ like", value, "id_");
            return (Criteria) this;
        }

        public Criteria andId_NotLike(String value) {
            addCriterion("id_ not like", value, "id_");
            return (Criteria) this;
        }

        public Criteria andId_In(List<String> values) {
            addCriterion("id_ in", values, "id_");
            return (Criteria) this;
        }

        public Criteria andId_NotIn(List<String> values) {
            addCriterion("id_ not in", values, "id_");
            return (Criteria) this;
        }

        public Criteria andId_Between(String value1, String value2) {
            addCriterion("id_ between", value1, value2, "id_");
            return (Criteria) this;
        }

        public Criteria andId_NotBetween(String value1, String value2) {
            addCriterion("id_ not between", value1, value2, "id_");
            return (Criteria) this;
        }

        public Criteria andName_IsNull() {
            addCriterion("name_ is null");
            return (Criteria) this;
        }

        public Criteria andName_IsNotNull() {
            addCriterion("name_ is not null");
            return (Criteria) this;
        }

        public Criteria andName_EqualTo(String value) {
            addCriterion("name_ =", value, "name_");
            return (Criteria) this;
        }

        public Criteria andName_NotEqualTo(String value) {
            addCriterion("name_ <>", value, "name_");
            return (Criteria) this;
        }

        public Criteria andName_GreaterThan(String value) {
            addCriterion("name_ >", value, "name_");
            return (Criteria) this;
        }

        public Criteria andName_GreaterThanOrEqualTo(String value) {
            addCriterion("name_ >=", value, "name_");
            return (Criteria) this;
        }

        public Criteria andName_LessThan(String value) {
            addCriterion("name_ <", value, "name_");
            return (Criteria) this;
        }

        public Criteria andName_LessThanOrEqualTo(String value) {
            addCriterion("name_ <=", value, "name_");
            return (Criteria) this;
        }

        public Criteria andName_Like(String value) {
            addCriterion("name_ like", value, "name_");
            return (Criteria) this;
        }

        public Criteria andName_NotLike(String value) {
            addCriterion("name_ not like", value, "name_");
            return (Criteria) this;
        }

        public Criteria andName_In(List<String> values) {
            addCriterion("name_ in", values, "name_");
            return (Criteria) this;
        }

        public Criteria andName_NotIn(List<String> values) {
            addCriterion("name_ not in", values, "name_");
            return (Criteria) this;
        }

        public Criteria andName_Between(String value1, String value2) {
            addCriterion("name_ between", value1, value2, "name_");
            return (Criteria) this;
        }

        public Criteria andName_NotBetween(String value1, String value2) {
            addCriterion("name_ not between", value1, value2, "name_");
            return (Criteria) this;
        }

        public Criteria andDescription_IsNull() {
            addCriterion("description_ is null");
            return (Criteria) this;
        }

        public Criteria andDescription_IsNotNull() {
            addCriterion("description_ is not null");
            return (Criteria) this;
        }

        public Criteria andDescription_EqualTo(String value) {
            addCriterion("description_ =", value, "description_");
            return (Criteria) this;
        }

        public Criteria andDescription_NotEqualTo(String value) {
            addCriterion("description_ <>", value, "description_");
            return (Criteria) this;
        }

        public Criteria andDescription_GreaterThan(String value) {
            addCriterion("description_ >", value, "description_");
            return (Criteria) this;
        }

        public Criteria andDescription_GreaterThanOrEqualTo(String value) {
            addCriterion("description_ >=", value, "description_");
            return (Criteria) this;
        }

        public Criteria andDescription_LessThan(String value) {
            addCriterion("description_ <", value, "description_");
            return (Criteria) this;
        }

        public Criteria andDescription_LessThanOrEqualTo(String value) {
            addCriterion("description_ <=", value, "description_");
            return (Criteria) this;
        }

        public Criteria andDescription_Like(String value) {
            addCriterion("description_ like", value, "description_");
            return (Criteria) this;
        }

        public Criteria andDescription_NotLike(String value) {
            addCriterion("description_ not like", value, "description_");
            return (Criteria) this;
        }

        public Criteria andDescription_In(List<String> values) {
            addCriterion("description_ in", values, "description_");
            return (Criteria) this;
        }

        public Criteria andDescription_NotIn(List<String> values) {
            addCriterion("description_ not in", values, "description_");
            return (Criteria) this;
        }

        public Criteria andDescription_Between(String value1, String value2) {
            addCriterion("description_ between", value1, value2, "description_");
            return (Criteria) this;
        }

        public Criteria andDescription_NotBetween(String value1, String value2) {
            addCriterion("description_ not between", value1, value2, "description_");
            return (Criteria) this;
        }

        public Criteria andCreate_time_IsNull() {
            addCriterion("create_time_ is null");
            return (Criteria) this;
        }

        public Criteria andCreate_time_IsNotNull() {
            addCriterion("create_time_ is not null");
            return (Criteria) this;
        }

        public Criteria andCreate_time_EqualTo(Date value) {
            addCriterion("create_time_ =", value, "create_time_");
            return (Criteria) this;
        }

        public Criteria andCreate_time_NotEqualTo(Date value) {
            addCriterion("create_time_ <>", value, "create_time_");
            return (Criteria) this;
        }

        public Criteria andCreate_time_GreaterThan(Date value) {
            addCriterion("create_time_ >", value, "create_time_");
            return (Criteria) this;
        }

        public Criteria andCreate_time_GreaterThanOrEqualTo(Date value) {
            addCriterion("create_time_ >=", value, "create_time_");
            return (Criteria) this;
        }

        public Criteria andCreate_time_LessThan(Date value) {
            addCriterion("create_time_ <", value, "create_time_");
            return (Criteria) this;
        }

        public Criteria andCreate_time_LessThanOrEqualTo(Date value) {
            addCriterion("create_time_ <=", value, "create_time_");
            return (Criteria) this;
        }

        public Criteria andCreate_time_In(List<Date> values) {
            addCriterion("create_time_ in", values, "create_time_");
            return (Criteria) this;
        }

        public Criteria andCreate_time_NotIn(List<Date> values) {
            addCriterion("create_time_ not in", values, "create_time_");
            return (Criteria) this;
        }

        public Criteria andCreate_time_Between(Date value1, Date value2) {
            addCriterion("create_time_ between", value1, value2, "create_time_");
            return (Criteria) this;
        }

        public Criteria andCreate_time_NotBetween(Date value1, Date value2) {
            addCriterion("create_time_ not between", value1, value2, "create_time_");
            return (Criteria) this;
        }

        public Criteria andStatus_IsNull() {
            addCriterion("status_ is null");
            return (Criteria) this;
        }

        public Criteria andStatus_IsNotNull() {
            addCriterion("status_ is not null");
            return (Criteria) this;
        }

        public Criteria andStatus_EqualTo(String value) {
            addCriterion("status_ =", value, "status_");
            return (Criteria) this;
        }

        public Criteria andStatus_NotEqualTo(String value) {
            addCriterion("status_ <>", value, "status_");
            return (Criteria) this;
        }

        public Criteria andStatus_GreaterThan(String value) {
            addCriterion("status_ >", value, "status_");
            return (Criteria) this;
        }

        public Criteria andStatus_GreaterThanOrEqualTo(String value) {
            addCriterion("status_ >=", value, "status_");
            return (Criteria) this;
        }

        public Criteria andStatus_LessThan(String value) {
            addCriterion("status_ <", value, "status_");
            return (Criteria) this;
        }

        public Criteria andStatus_LessThanOrEqualTo(String value) {
            addCriterion("status_ <=", value, "status_");
            return (Criteria) this;
        }

        public Criteria andStatus_Like(String value) {
            addCriterion("status_ like", value, "status_");
            return (Criteria) this;
        }

        public Criteria andStatus_NotLike(String value) {
            addCriterion("status_ not like", value, "status_");
            return (Criteria) this;
        }

        public Criteria andStatus_In(List<String> values) {
            addCriterion("status_ in", values, "status_");
            return (Criteria) this;
        }

        public Criteria andStatus_NotIn(List<String> values) {
            addCriterion("status_ not in", values, "status_");
            return (Criteria) this;
        }

        public Criteria andStatus_Between(String value1, String value2) {
            addCriterion("status_ between", value1, value2, "status_");
            return (Criteria) this;
        }

        public Criteria andStatus_NotBetween(String value1, String value2) {
            addCriterion("status_ not between", value1, value2, "status_");
            return (Criteria) this;
        }
    }

    public static class Criteria extends GeneratedCriteria {

        protected Criteria() {
            super();
        }
    }

    public static class Criterion {
        private String condition;

        private Object value;

        private Object secondValue;

        private boolean noValue;

        private boolean singleValue;

        private boolean betweenValue;

        private boolean listValue;

        private String typeHandler;

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

        public String getTypeHandler() {
            return typeHandler;
        }

        protected Criterion(String condition) {
            super();
            this.condition = condition;
            this.typeHandler = null;
            this.noValue = true;
        }

        protected Criterion(String condition, Object value, String typeHandler) {
            super();
            this.condition = condition;
            this.value = value;
            this.typeHandler = typeHandler;
            if (value instanceof List<?>) {
                this.listValue = true;
            } else {
                this.singleValue = true;
            }
        }

        protected Criterion(String condition, Object value) {
            this(condition, value, null);
        }

        protected Criterion(String condition, Object value, Object secondValue, String typeHandler) {
            super();
            this.condition = condition;
            this.value = value;
            this.secondValue = secondValue;
            this.typeHandler = typeHandler;
            this.betweenValue = true;
        }

        protected Criterion(String condition, Object value, Object secondValue) {
            this(condition, value, secondValue, null);
        }
    }
}