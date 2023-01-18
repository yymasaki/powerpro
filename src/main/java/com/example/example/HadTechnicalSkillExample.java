package com.example.example;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class HadTechnicalSkillExample {

    protected String orderByClause;

    protected boolean distinct;

    protected List<Criteria> oredCriteria;

    public HadTechnicalSkillExample() {
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

        public Criteria andHadTechnicalSkillIdIsNull() {
            addCriterion("had_technical_skill_id is null");
            return (Criteria) this;
        }

        public Criteria andHadTechnicalSkillIdIsNotNull() {
            addCriterion("had_technical_skill_id is not null");
            return (Criteria) this;
        }

        public Criteria andHadTechnicalSkillIdEqualTo(Integer value) {
            addCriterion("had_technical_skill_id =", value, "hadTechnicalSkillId");
            return (Criteria) this;
        }

        public Criteria andHadTechnicalSkillIdNotEqualTo(Integer value) {
            addCriterion("had_technical_skill_id <>", value, "hadTechnicalSkillId");
            return (Criteria) this;
        }

        public Criteria andHadTechnicalSkillIdGreaterThan(Integer value) {
            addCriterion("had_technical_skill_id >", value, "hadTechnicalSkillId");
            return (Criteria) this;
        }

        public Criteria andHadTechnicalSkillIdGreaterThanOrEqualTo(Integer value) {
            addCriterion("had_technical_skill_id >=", value, "hadTechnicalSkillId");
            return (Criteria) this;
        }

        public Criteria andHadTechnicalSkillIdLessThan(Integer value) {
            addCriterion("had_technical_skill_id <", value, "hadTechnicalSkillId");
            return (Criteria) this;
        }

        public Criteria andHadTechnicalSkillIdLessThanOrEqualTo(Integer value) {
            addCriterion("had_technical_skill_id <=", value, "hadTechnicalSkillId");
            return (Criteria) this;
        }

        public Criteria andHadTechnicalSkillIdIn(List<Integer> values) {
            addCriterion("had_technical_skill_id in", values, "hadTechnicalSkillId");
            return (Criteria) this;
        }

        public Criteria andHadTechnicalSkillIdNotIn(List<Integer> values) {
            addCriterion("had_technical_skill_id not in", values, "hadTechnicalSkillId");
            return (Criteria) this;
        }

        public Criteria andHadTechnicalSkillIdBetween(Integer value1, Integer value2) {
            addCriterion("had_technical_skill_id between", value1, value2, "hadTechnicalSkillId");
            return (Criteria) this;
        }

        public Criteria andHadTechnicalSkillIdNotBetween(Integer value1, Integer value2) {
            addCriterion("had_technical_skill_id not between", value1, value2, "hadTechnicalSkillId");
            return (Criteria) this;
        }

        public Criteria andUserIdIsNull() {
            addCriterion("user_id is null");
            return (Criteria) this;
        }

        public Criteria andUserIdIsNotNull() {
            addCriterion("user_id is not null");
            return (Criteria) this;
        }

        public Criteria andUserIdEqualTo(Integer value) {
            addCriterion("user_id =", value, "userId");
            return (Criteria) this;
        }

        public Criteria andUserIdNotEqualTo(Integer value) {
            addCriterion("user_id <>", value, "userId");
            return (Criteria) this;
        }

        public Criteria andUserIdGreaterThan(Integer value) {
            addCriterion("user_id >", value, "userId");
            return (Criteria) this;
        }

        public Criteria andUserIdGreaterThanOrEqualTo(Integer value) {
            addCriterion("user_id >=", value, "userId");
            return (Criteria) this;
        }

        public Criteria andUserIdLessThan(Integer value) {
            addCriterion("user_id <", value, "userId");
            return (Criteria) this;
        }

        public Criteria andUserIdLessThanOrEqualTo(Integer value) {
            addCriterion("user_id <=", value, "userId");
            return (Criteria) this;
        }

        public Criteria andUserIdIn(List<Integer> values) {
            addCriterion("user_id in", values, "userId");
            return (Criteria) this;
        }

        public Criteria andUserIdNotIn(List<Integer> values) {
            addCriterion("user_id not in", values, "userId");
            return (Criteria) this;
        }

        public Criteria andUserIdBetween(Integer value1, Integer value2) {
            addCriterion("user_id between", value1, value2, "userId");
            return (Criteria) this;
        }

        public Criteria andUserIdNotBetween(Integer value1, Integer value2) {
            addCriterion("user_id not between", value1, value2, "userId");
            return (Criteria) this;
        }

        public Criteria andTechnicalSkillIdIsNull() {
            addCriterion("technical_skill_id is null");
            return (Criteria) this;
        }

        public Criteria andTechnicalSkillIdIsNotNull() {
            addCriterion("technical_skill_id is not null");
            return (Criteria) this;
        }

        public Criteria andTechnicalSkillIdEqualTo(Integer value) {
            addCriterion("technical_skill_id =", value, "technicalSkillId");
            return (Criteria) this;
        }

        public Criteria andTechnicalSkillIdNotEqualTo(Integer value) {
            addCriterion("technical_skill_id <>", value, "technicalSkillId");
            return (Criteria) this;
        }

        public Criteria andTechnicalSkillIdGreaterThan(Integer value) {
            addCriterion("technical_skill_id >", value, "technicalSkillId");
            return (Criteria) this;
        }

        public Criteria andTechnicalSkillIdGreaterThanOrEqualTo(Integer value) {
            addCriterion("technical_skill_id >=", value, "technicalSkillId");
            return (Criteria) this;
        }

        public Criteria andTechnicalSkillIdLessThan(Integer value) {
            addCriterion("technical_skill_id <", value, "technicalSkillId");
            return (Criteria) this;
        }

        public Criteria andTechnicalSkillIdLessThanOrEqualTo(Integer value) {
            addCriterion("technical_skill_id <=", value, "technicalSkillId");
            return (Criteria) this;
        }

        public Criteria andTechnicalSkillIdIn(List<Integer> values) {
            addCriterion("technical_skill_id in", values, "technicalSkillId");
            return (Criteria) this;
        }

        public Criteria andTechnicalSkillIdNotIn(List<Integer> values) {
            addCriterion("technical_skill_id not in", values, "technicalSkillId");
            return (Criteria) this;
        }

        public Criteria andTechnicalSkillIdBetween(Integer value1, Integer value2) {
            addCriterion("technical_skill_id between", value1, value2, "technicalSkillId");
            return (Criteria) this;
        }

        public Criteria andTechnicalSkillIdNotBetween(Integer value1, Integer value2) {
            addCriterion("technical_skill_id not between", value1, value2, "technicalSkillId");
            return (Criteria) this;
        }

        public Criteria andUsingPeriodIsNull() {
            addCriterion("using_period is null");
            return (Criteria) this;
        }

        public Criteria andUsingPeriodIsNotNull() {
            addCriterion("using_period is not null");
            return (Criteria) this;
        }

        public Criteria andUsingPeriodEqualTo(Integer value) {
            addCriterion("using_period =", value, "usingPeriod");
            return (Criteria) this;
        }

        public Criteria andUsingPeriodNotEqualTo(Integer value) {
            addCriterion("using_period <>", value, "usingPeriod");
            return (Criteria) this;
        }

        public Criteria andUsingPeriodGreaterThan(Integer value) {
            addCriterion("using_period >", value, "usingPeriod");
            return (Criteria) this;
        }

        public Criteria andUsingPeriodGreaterThanOrEqualTo(Integer value) {
            addCriterion("using_period >=", value, "usingPeriod");
            return (Criteria) this;
        }

        public Criteria andUsingPeriodLessThan(Integer value) {
            addCriterion("using_period <", value, "usingPeriod");
            return (Criteria) this;
        }

        public Criteria andUsingPeriodLessThanOrEqualTo(Integer value) {
            addCriterion("using_period <=", value, "usingPeriod");
            return (Criteria) this;
        }

        public Criteria andUsingPeriodIn(List<Integer> values) {
            addCriterion("using_period in", values, "usingPeriod");
            return (Criteria) this;
        }

        public Criteria andUsingPeriodNotIn(List<Integer> values) {
            addCriterion("using_period not in", values, "usingPeriod");
            return (Criteria) this;
        }

        public Criteria andUsingPeriodBetween(Integer value1, Integer value2) {
            addCriterion("using_period between", value1, value2, "usingPeriod");
            return (Criteria) this;
        }

        public Criteria andUsingPeriodNotBetween(Integer value1, Integer value2) {
            addCriterion("using_period not between", value1, value2, "usingPeriod");
            return (Criteria) this;
        }

        public Criteria andStageIsNull() {
            addCriterion("stage is null");
            return (Criteria) this;
        }

        public Criteria andStageIsNotNull() {
            addCriterion("stage is not null");
            return (Criteria) this;
        }

        public Criteria andStageEqualTo(String value) {
            addCriterion("stage =", value, "stage");
            return (Criteria) this;
        }

        public Criteria andStageNotEqualTo(String value) {
            addCriterion("stage <>", value, "stage");
            return (Criteria) this;
        }

        public Criteria andStageGreaterThan(String value) {
            addCriterion("stage >", value, "stage");
            return (Criteria) this;
        }

        public Criteria andStageGreaterThanOrEqualTo(String value) {
            addCriterion("stage >=", value, "stage");
            return (Criteria) this;
        }

        public Criteria andStageLessThan(String value) {
            addCriterion("stage <", value, "stage");
            return (Criteria) this;
        }

        public Criteria andStageLessThanOrEqualTo(String value) {
            addCriterion("stage <=", value, "stage");
            return (Criteria) this;
        }

        public Criteria andStageLike(String value) {
            addCriterion("stage like", value, "stage");
            return (Criteria) this;
        }

        public Criteria andStageNotLike(String value) {
            addCriterion("stage not like", value, "stage");
            return (Criteria) this;
        }

        public Criteria andStageIn(List<String> values) {
            addCriterion("stage in", values, "stage");
            return (Criteria) this;
        }

        public Criteria andStageNotIn(List<String> values) {
            addCriterion("stage not in", values, "stage");
            return (Criteria) this;
        }

        public Criteria andStageBetween(String value1, String value2) {
            addCriterion("stage between", value1, value2, "stage");
            return (Criteria) this;
        }

        public Criteria andStageNotBetween(String value1, String value2) {
            addCriterion("stage not between", value1, value2, "stage");
            return (Criteria) this;
        }

        public Criteria andCreatorIsNull() {
            addCriterion("creator is null");
            return (Criteria) this;
        }

        public Criteria andCreatorIsNotNull() {
            addCriterion("creator is not null");
            return (Criteria) this;
        }

        public Criteria andCreatorEqualTo(String value) {
            addCriterion("creator =", value, "creator");
            return (Criteria) this;
        }

        public Criteria andCreatorNotEqualTo(String value) {
            addCriterion("creator <>", value, "creator");
            return (Criteria) this;
        }

        public Criteria andCreatorGreaterThan(String value) {
            addCriterion("creator >", value, "creator");
            return (Criteria) this;
        }

        public Criteria andCreatorGreaterThanOrEqualTo(String value) {
            addCriterion("creator >=", value, "creator");
            return (Criteria) this;
        }

        public Criteria andCreatorLessThan(String value) {
            addCriterion("creator <", value, "creator");
            return (Criteria) this;
        }

        public Criteria andCreatorLessThanOrEqualTo(String value) {
            addCriterion("creator <=", value, "creator");
            return (Criteria) this;
        }

        public Criteria andCreatorLike(String value) {
            addCriterion("creator like", value, "creator");
            return (Criteria) this;
        }

        public Criteria andCreatorNotLike(String value) {
            addCriterion("creator not like", value, "creator");
            return (Criteria) this;
        }

        public Criteria andCreatorIn(List<String> values) {
            addCriterion("creator in", values, "creator");
            return (Criteria) this;
        }

        public Criteria andCreatorNotIn(List<String> values) {
            addCriterion("creator not in", values, "creator");
            return (Criteria) this;
        }

        public Criteria andCreatorBetween(String value1, String value2) {
            addCriterion("creator between", value1, value2, "creator");
            return (Criteria) this;
        }

        public Criteria andCreatorNotBetween(String value1, String value2) {
            addCriterion("creator not between", value1, value2, "creator");
            return (Criteria) this;
        }

        public Criteria andCreatedAtIsNull() {
            addCriterion("created_at is null");
            return (Criteria) this;
        }

        public Criteria andCreatedAtIsNotNull() {
            addCriterion("created_at is not null");
            return (Criteria) this;
        }

        public Criteria andCreatedAtEqualTo(LocalDateTime createdAt) {
            addCriterion("created_at =", createdAt, "createdAt");
            return (Criteria) this;
        }

        public Criteria andCreatedAtNotEqualTo(Date value) {
            addCriterion("created_at <>", value, "createdAt");
            return (Criteria) this;
        }

        public Criteria andCreatedAtGreaterThan(Date value) {
            addCriterion("created_at >", value, "createdAt");
            return (Criteria) this;
        }

        public Criteria andCreatedAtGreaterThanOrEqualTo(Date value) {
            addCriterion("created_at >=", value, "createdAt");
            return (Criteria) this;
        }

        public Criteria andCreatedAtLessThan(Date value) {
            addCriterion("created_at <", value, "createdAt");
            return (Criteria) this;
        }

        public Criteria andCreatedAtLessThanOrEqualTo(Date value) {
            addCriterion("created_at <=", value, "createdAt");
            return (Criteria) this;
        }

        public Criteria andCreatedAtIn(List<Date> values) {
            addCriterion("created_at in", values, "createdAt");
            return (Criteria) this;
        }

        public Criteria andCreatedAtNotIn(List<Date> values) {
            addCriterion("created_at not in", values, "createdAt");
            return (Criteria) this;
        }

        public Criteria andCreatedAtBetween(Date value1, Date value2) {
            addCriterion("created_at between", value1, value2, "createdAt");
            return (Criteria) this;
        }

        public Criteria andCreatedAtNotBetween(Date value1, Date value2) {
            addCriterion("created_at not between", value1, value2, "createdAt");
            return (Criteria) this;
        }

        public Criteria andUpdaterIsNull() {
            addCriterion("updater is null");
            return (Criteria) this;
        }

        public Criteria andUpdaterIsNotNull() {
            addCriterion("updater is not null");
            return (Criteria) this;
        }

        public Criteria andUpdaterEqualTo(String value) {
            addCriterion("updater =", value, "updater");
            return (Criteria) this;
        }

        public Criteria andUpdaterNotEqualTo(String value) {
            addCriterion("updater <>", value, "updater");
            return (Criteria) this;
        }

        public Criteria andUpdaterGreaterThan(String value) {
            addCriterion("updater >", value, "updater");
            return (Criteria) this;
        }

        public Criteria andUpdaterGreaterThanOrEqualTo(String value) {
            addCriterion("updater >=", value, "updater");
            return (Criteria) this;
        }

        public Criteria andUpdaterLessThan(String value) {
            addCriterion("updater <", value, "updater");
            return (Criteria) this;
        }

        public Criteria andUpdaterLessThanOrEqualTo(String value) {
            addCriterion("updater <=", value, "updater");
            return (Criteria) this;
        }

        public Criteria andUpdaterLike(String value) {
            addCriterion("updater like", value, "updater");
            return (Criteria) this;
        }

        public Criteria andUpdaterNotLike(String value) {
            addCriterion("updater not like", value, "updater");
            return (Criteria) this;
        }

        public Criteria andUpdaterIn(List<String> values) {
            addCriterion("updater in", values, "updater");
            return (Criteria) this;
        }

        public Criteria andUpdaterNotIn(List<String> values) {
            addCriterion("updater not in", values, "updater");
            return (Criteria) this;
        }

        public Criteria andUpdaterBetween(String value1, String value2) {
            addCriterion("updater between", value1, value2, "updater");
            return (Criteria) this;
        }

        public Criteria andUpdaterNotBetween(String value1, String value2) {
            addCriterion("updater not between", value1, value2, "updater");
            return (Criteria) this;
        }

        public Criteria andUpdatedAtIsNull() {
            addCriterion("updated_at is null");
            return (Criteria) this;
        }

        public Criteria andUpdatedAtIsNotNull() {
            addCriterion("updated_at is not null");
            return (Criteria) this;
        }

        public Criteria andUpdatedAtEqualTo(LocalDateTime updatedTime) {
            addCriterion("updated_at =", updatedTime, "updatedAt");
            return (Criteria) this;
        }

        public Criteria andUpdatedAtNotEqualTo(Date value) {
            addCriterion("updated_at <>", value, "updatedAt");
            return (Criteria) this;
        }

        public Criteria andUpdatedAtGreaterThan(Date value) {
            addCriterion("updated_at >", value, "updatedAt");
            return (Criteria) this;
        }

        public Criteria andUpdatedAtGreaterThanOrEqualTo(Date value) {
            addCriterion("updated_at >=", value, "updatedAt");
            return (Criteria) this;
        }

        public Criteria andUpdatedAtLessThan(Date value) {
            addCriterion("updated_at <", value, "updatedAt");
            return (Criteria) this;
        }

        public Criteria andUpdatedAtLessThanOrEqualTo(Date value) {
            addCriterion("updated_at <=", value, "updatedAt");
            return (Criteria) this;
        }

        public Criteria andUpdatedAtIn(List<Date> values) {
            addCriterion("updated_at in", values, "updatedAt");
            return (Criteria) this;
        }

        public Criteria andUpdatedAtNotIn(List<Date> values) {
            addCriterion("updated_at not in", values, "updatedAt");
            return (Criteria) this;
        }

        public Criteria andUpdatedAtBetween(Date value1, Date value2) {
            addCriterion("updated_at between", value1, value2, "updatedAt");
            return (Criteria) this;
        }

        public Criteria andUpdatedAtNotBetween(Date value1, Date value2) {
            addCriterion("updated_at not between", value1, value2, "updatedAt");
            return (Criteria) this;
        }

        public Criteria andVersionIsNull() {
            addCriterion("version is null");
            return (Criteria) this;
        }

        public Criteria andVersionIsNotNull() {
            addCriterion("version is not null");
            return (Criteria) this;
        }

        public Criteria andVersionEqualTo(Integer value) {
            addCriterion("version =", value, "version");
            return (Criteria) this;
        }

        public Criteria andVersionNotEqualTo(Integer value) {
            addCriterion("version <>", value, "version");
            return (Criteria) this;
        }

        public Criteria andVersionGreaterThan(Integer value) {
            addCriterion("version >", value, "version");
            return (Criteria) this;
        }

        public Criteria andVersionGreaterThanOrEqualTo(Integer value) {
            addCriterion("version >=", value, "version");
            return (Criteria) this;
        }

        public Criteria andVersionLessThan(Integer value) {
            addCriterion("version <", value, "version");
            return (Criteria) this;
        }

        public Criteria andVersionLessThanOrEqualTo(Integer value) {
            addCriterion("version <=", value, "version");
            return (Criteria) this;
        }

        public Criteria andVersionIn(List<Integer> values) {
            addCriterion("version in", values, "version");
            return (Criteria) this;
        }

        public Criteria andVersionNotIn(List<Integer> values) {
            addCriterion("version not in", values, "version");
            return (Criteria) this;
        }

        public Criteria andVersionBetween(Integer value1, Integer value2) {
            addCriterion("version between", value1, value2, "version");
            return (Criteria) this;
        }

        public Criteria andVersionNotBetween(Integer value1, Integer value2) {
            addCriterion("version not between", value1, value2, "version");
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

        protected Criterion(String condition) {
            super();
            this.condition = condition;
            this.noValue = true;
        }

        protected Criterion(String condition, Object value) {
            super();
            this.condition = condition;
            this.value = value;
            if (value instanceof List<?>) {
                this.listValue = true;
            } else {
                this.singleValue = true;
            }
        }

        protected Criterion(String condition, Object value, Object secondValue) {
            super();
            this.condition = condition;
            this.value = value;
            this.secondValue = secondValue;
            this.betweenValue = true;
        }
    }
}