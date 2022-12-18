package com.zx.bilibili.domain;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class VideoBinaryPictureExample {
    protected String orderByClause;

    protected boolean distinct;

    protected List<Criteria> oredCriteria;

    public VideoBinaryPictureExample() {
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

        public Criteria andVideoIdIsNull() {
            addCriterion("videoId is null");
            return (Criteria) this;
        }

        public Criteria andVideoIdIsNotNull() {
            addCriterion("videoId is not null");
            return (Criteria) this;
        }

        public Criteria andVideoIdEqualTo(Long value) {
            addCriterion("videoId =", value, "videoId");
            return (Criteria) this;
        }

        public Criteria andVideoIdNotEqualTo(Long value) {
            addCriterion("videoId <>", value, "videoId");
            return (Criteria) this;
        }

        public Criteria andVideoIdGreaterThan(Long value) {
            addCriterion("videoId >", value, "videoId");
            return (Criteria) this;
        }

        public Criteria andVideoIdGreaterThanOrEqualTo(Long value) {
            addCriterion("videoId >=", value, "videoId");
            return (Criteria) this;
        }

        public Criteria andVideoIdLessThan(Long value) {
            addCriterion("videoId <", value, "videoId");
            return (Criteria) this;
        }

        public Criteria andVideoIdLessThanOrEqualTo(Long value) {
            addCriterion("videoId <=", value, "videoId");
            return (Criteria) this;
        }

        public Criteria andVideoIdIn(List<Long> values) {
            addCriterion("videoId in", values, "videoId");
            return (Criteria) this;
        }

        public Criteria andVideoIdNotIn(List<Long> values) {
            addCriterion("videoId not in", values, "videoId");
            return (Criteria) this;
        }

        public Criteria andVideoIdBetween(Long value1, Long value2) {
            addCriterion("videoId between", value1, value2, "videoId");
            return (Criteria) this;
        }

        public Criteria andVideoIdNotBetween(Long value1, Long value2) {
            addCriterion("videoId not between", value1, value2, "videoId");
            return (Criteria) this;
        }

        public Criteria andFrameNoIsNull() {
            addCriterion("frameNo is null");
            return (Criteria) this;
        }

        public Criteria andFrameNoIsNotNull() {
            addCriterion("frameNo is not null");
            return (Criteria) this;
        }

        public Criteria andFrameNoEqualTo(Integer value) {
            addCriterion("frameNo =", value, "frameNo");
            return (Criteria) this;
        }

        public Criteria andFrameNoNotEqualTo(Integer value) {
            addCriterion("frameNo <>", value, "frameNo");
            return (Criteria) this;
        }

        public Criteria andFrameNoGreaterThan(Integer value) {
            addCriterion("frameNo >", value, "frameNo");
            return (Criteria) this;
        }

        public Criteria andFrameNoGreaterThanOrEqualTo(Integer value) {
            addCriterion("frameNo >=", value, "frameNo");
            return (Criteria) this;
        }

        public Criteria andFrameNoLessThan(Integer value) {
            addCriterion("frameNo <", value, "frameNo");
            return (Criteria) this;
        }

        public Criteria andFrameNoLessThanOrEqualTo(Integer value) {
            addCriterion("frameNo <=", value, "frameNo");
            return (Criteria) this;
        }

        public Criteria andFrameNoIn(List<Integer> values) {
            addCriterion("frameNo in", values, "frameNo");
            return (Criteria) this;
        }

        public Criteria andFrameNoNotIn(List<Integer> values) {
            addCriterion("frameNo not in", values, "frameNo");
            return (Criteria) this;
        }

        public Criteria andFrameNoBetween(Integer value1, Integer value2) {
            addCriterion("frameNo between", value1, value2, "frameNo");
            return (Criteria) this;
        }

        public Criteria andFrameNoNotBetween(Integer value1, Integer value2) {
            addCriterion("frameNo not between", value1, value2, "frameNo");
            return (Criteria) this;
        }

        public Criteria andVideoTimestampIsNull() {
            addCriterion("videoTimestamp is null");
            return (Criteria) this;
        }

        public Criteria andVideoTimestampIsNotNull() {
            addCriterion("videoTimestamp is not null");
            return (Criteria) this;
        }

        public Criteria andVideoTimestampEqualTo(Long value) {
            addCriterion("videoTimestamp =", value, "videoTimestamp");
            return (Criteria) this;
        }

        public Criteria andVideoTimestampNotEqualTo(Long value) {
            addCriterion("videoTimestamp <>", value, "videoTimestamp");
            return (Criteria) this;
        }

        public Criteria andVideoTimestampGreaterThan(Long value) {
            addCriterion("videoTimestamp >", value, "videoTimestamp");
            return (Criteria) this;
        }

        public Criteria andVideoTimestampGreaterThanOrEqualTo(Long value) {
            addCriterion("videoTimestamp >=", value, "videoTimestamp");
            return (Criteria) this;
        }

        public Criteria andVideoTimestampLessThan(Long value) {
            addCriterion("videoTimestamp <", value, "videoTimestamp");
            return (Criteria) this;
        }

        public Criteria andVideoTimestampLessThanOrEqualTo(Long value) {
            addCriterion("videoTimestamp <=", value, "videoTimestamp");
            return (Criteria) this;
        }

        public Criteria andVideoTimestampIn(List<Long> values) {
            addCriterion("videoTimestamp in", values, "videoTimestamp");
            return (Criteria) this;
        }

        public Criteria andVideoTimestampNotIn(List<Long> values) {
            addCriterion("videoTimestamp not in", values, "videoTimestamp");
            return (Criteria) this;
        }

        public Criteria andVideoTimestampBetween(Long value1, Long value2) {
            addCriterion("videoTimestamp between", value1, value2, "videoTimestamp");
            return (Criteria) this;
        }

        public Criteria andVideoTimestampNotBetween(Long value1, Long value2) {
            addCriterion("videoTimestamp not between", value1, value2, "videoTimestamp");
            return (Criteria) this;
        }

        public Criteria andCreateTimeIsNull() {
            addCriterion("createTime is null");
            return (Criteria) this;
        }

        public Criteria andCreateTimeIsNotNull() {
            addCriterion("createTime is not null");
            return (Criteria) this;
        }

        public Criteria andCreateTimeEqualTo(Date value) {
            addCriterion("createTime =", value, "createTime");
            return (Criteria) this;
        }

        public Criteria andCreateTimeNotEqualTo(Date value) {
            addCriterion("createTime <>", value, "createTime");
            return (Criteria) this;
        }

        public Criteria andCreateTimeGreaterThan(Date value) {
            addCriterion("createTime >", value, "createTime");
            return (Criteria) this;
        }

        public Criteria andCreateTimeGreaterThanOrEqualTo(Date value) {
            addCriterion("createTime >=", value, "createTime");
            return (Criteria) this;
        }

        public Criteria andCreateTimeLessThan(Date value) {
            addCriterion("createTime <", value, "createTime");
            return (Criteria) this;
        }

        public Criteria andCreateTimeLessThanOrEqualTo(Date value) {
            addCriterion("createTime <=", value, "createTime");
            return (Criteria) this;
        }

        public Criteria andCreateTimeIn(List<Date> values) {
            addCriterion("createTime in", values, "createTime");
            return (Criteria) this;
        }

        public Criteria andCreateTimeNotIn(List<Date> values) {
            addCriterion("createTime not in", values, "createTime");
            return (Criteria) this;
        }

        public Criteria andCreateTimeBetween(Date value1, Date value2) {
            addCriterion("createTime between", value1, value2, "createTime");
            return (Criteria) this;
        }

        public Criteria andCreateTimeNotBetween(Date value1, Date value2) {
            addCriterion("createTime not between", value1, value2, "createTime");
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