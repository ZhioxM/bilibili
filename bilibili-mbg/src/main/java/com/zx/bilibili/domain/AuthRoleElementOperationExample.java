package com.zx.bilibili.domain;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class AuthRoleElementOperationExample {
    protected String orderByClause;

    protected boolean distinct;

    protected List<Criteria> oredCriteria;

    public AuthRoleElementOperationExample() {
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

        public Criteria andIdIsNull() {
            addCriterion("id is null");
            return (Criteria) this;
        }

        public Criteria andIdIsNotNull() {
            addCriterion("id is not null");
            return (Criteria) this;
        }

        public Criteria andIdEqualTo(Long value) {
            addCriterion("id =", value, "id");
            return (Criteria) this;
        }

        public Criteria andIdNotEqualTo(Long value) {
            addCriterion("id <>", value, "id");
            return (Criteria) this;
        }

        public Criteria andIdGreaterThan(Long value) {
            addCriterion("id >", value, "id");
            return (Criteria) this;
        }

        public Criteria andIdGreaterThanOrEqualTo(Long value) {
            addCriterion("id >=", value, "id");
            return (Criteria) this;
        }

        public Criteria andIdLessThan(Long value) {
            addCriterion("id <", value, "id");
            return (Criteria) this;
        }

        public Criteria andIdLessThanOrEqualTo(Long value) {
            addCriterion("id <=", value, "id");
            return (Criteria) this;
        }

        public Criteria andIdIn(List<Long> values) {
            addCriterion("id in", values, "id");
            return (Criteria) this;
        }

        public Criteria andIdNotIn(List<Long> values) {
            addCriterion("id not in", values, "id");
            return (Criteria) this;
        }

        public Criteria andIdBetween(Long value1, Long value2) {
            addCriterion("id between", value1, value2, "id");
            return (Criteria) this;
        }

        public Criteria andIdNotBetween(Long value1, Long value2) {
            addCriterion("id not between", value1, value2, "id");
            return (Criteria) this;
        }

        public Criteria andRoleIdIsNull() {
            addCriterion("roleId is null");
            return (Criteria) this;
        }

        public Criteria andRoleIdIsNotNull() {
            addCriterion("roleId is not null");
            return (Criteria) this;
        }

        public Criteria andRoleIdEqualTo(Long value) {
            addCriterion("roleId =", value, "roleId");
            return (Criteria) this;
        }

        public Criteria andRoleIdNotEqualTo(Long value) {
            addCriterion("roleId <>", value, "roleId");
            return (Criteria) this;
        }

        public Criteria andRoleIdGreaterThan(Long value) {
            addCriterion("roleId >", value, "roleId");
            return (Criteria) this;
        }

        public Criteria andRoleIdGreaterThanOrEqualTo(Long value) {
            addCriterion("roleId >=", value, "roleId");
            return (Criteria) this;
        }

        public Criteria andRoleIdLessThan(Long value) {
            addCriterion("roleId <", value, "roleId");
            return (Criteria) this;
        }

        public Criteria andRoleIdLessThanOrEqualTo(Long value) {
            addCriterion("roleId <=", value, "roleId");
            return (Criteria) this;
        }

        public Criteria andRoleIdIn(List<Long> values) {
            addCriterion("roleId in", values, "roleId");
            return (Criteria) this;
        }

        public Criteria andRoleIdNotIn(List<Long> values) {
            addCriterion("roleId not in", values, "roleId");
            return (Criteria) this;
        }

        public Criteria andRoleIdBetween(Long value1, Long value2) {
            addCriterion("roleId between", value1, value2, "roleId");
            return (Criteria) this;
        }

        public Criteria andRoleIdNotBetween(Long value1, Long value2) {
            addCriterion("roleId not between", value1, value2, "roleId");
            return (Criteria) this;
        }

        public Criteria andElementOperationIdIsNull() {
            addCriterion("elementOperationId is null");
            return (Criteria) this;
        }

        public Criteria andElementOperationIdIsNotNull() {
            addCriterion("elementOperationId is not null");
            return (Criteria) this;
        }

        public Criteria andElementOperationIdEqualTo(Long value) {
            addCriterion("elementOperationId =", value, "elementOperationId");
            return (Criteria) this;
        }

        public Criteria andElementOperationIdNotEqualTo(Long value) {
            addCriterion("elementOperationId <>", value, "elementOperationId");
            return (Criteria) this;
        }

        public Criteria andElementOperationIdGreaterThan(Long value) {
            addCriterion("elementOperationId >", value, "elementOperationId");
            return (Criteria) this;
        }

        public Criteria andElementOperationIdGreaterThanOrEqualTo(Long value) {
            addCriterion("elementOperationId >=", value, "elementOperationId");
            return (Criteria) this;
        }

        public Criteria andElementOperationIdLessThan(Long value) {
            addCriterion("elementOperationId <", value, "elementOperationId");
            return (Criteria) this;
        }

        public Criteria andElementOperationIdLessThanOrEqualTo(Long value) {
            addCriterion("elementOperationId <=", value, "elementOperationId");
            return (Criteria) this;
        }

        public Criteria andElementOperationIdIn(List<Long> values) {
            addCriterion("elementOperationId in", values, "elementOperationId");
            return (Criteria) this;
        }

        public Criteria andElementOperationIdNotIn(List<Long> values) {
            addCriterion("elementOperationId not in", values, "elementOperationId");
            return (Criteria) this;
        }

        public Criteria andElementOperationIdBetween(Long value1, Long value2) {
            addCriterion("elementOperationId between", value1, value2, "elementOperationId");
            return (Criteria) this;
        }

        public Criteria andElementOperationIdNotBetween(Long value1, Long value2) {
            addCriterion("elementOperationId not between", value1, value2, "elementOperationId");
            return (Criteria) this;
        }

        public Criteria andCreateTImeIsNull() {
            addCriterion("createTIme is null");
            return (Criteria) this;
        }

        public Criteria andCreateTImeIsNotNull() {
            addCriterion("createTIme is not null");
            return (Criteria) this;
        }

        public Criteria andCreateTImeEqualTo(Date value) {
            addCriterion("createTIme =", value, "createTIme");
            return (Criteria) this;
        }

        public Criteria andCreateTImeNotEqualTo(Date value) {
            addCriterion("createTIme <>", value, "createTIme");
            return (Criteria) this;
        }

        public Criteria andCreateTImeGreaterThan(Date value) {
            addCriterion("createTIme >", value, "createTIme");
            return (Criteria) this;
        }

        public Criteria andCreateTImeGreaterThanOrEqualTo(Date value) {
            addCriterion("createTIme >=", value, "createTIme");
            return (Criteria) this;
        }

        public Criteria andCreateTImeLessThan(Date value) {
            addCriterion("createTIme <", value, "createTIme");
            return (Criteria) this;
        }

        public Criteria andCreateTImeLessThanOrEqualTo(Date value) {
            addCriterion("createTIme <=", value, "createTIme");
            return (Criteria) this;
        }

        public Criteria andCreateTImeIn(List<Date> values) {
            addCriterion("createTIme in", values, "createTIme");
            return (Criteria) this;
        }

        public Criteria andCreateTImeNotIn(List<Date> values) {
            addCriterion("createTIme not in", values, "createTIme");
            return (Criteria) this;
        }

        public Criteria andCreateTImeBetween(Date value1, Date value2) {
            addCriterion("createTIme between", value1, value2, "createTIme");
            return (Criteria) this;
        }

        public Criteria andCreateTImeNotBetween(Date value1, Date value2) {
            addCriterion("createTIme not between", value1, value2, "createTIme");
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