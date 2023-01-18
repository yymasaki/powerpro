package com.example.example;

import java.util.ArrayList;
import java.util.List;

public class UsedTechnicalSkillExample {
    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database table used_technical_skills
     *
     * @mbggenerated Tue May 26 13:23:22 JST 2020
     */
    protected String orderByClause;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database table used_technical_skills
     *
     * @mbggenerated Tue May 26 13:23:22 JST 2020
     */
    protected boolean distinct;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database table used_technical_skills
     *
     * @mbggenerated Tue May 26 13:23:22 JST 2020
     */
    protected List<Criteria> oredCriteria;

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table used_technical_skills
     *
     * @mbggenerated Tue May 26 13:23:22 JST 2020
     */
    public UsedTechnicalSkillExample() {
        oredCriteria = new ArrayList<Criteria>();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table used_technical_skills
     *
     * @mbggenerated Tue May 26 13:23:22 JST 2020
     */
    public void setOrderByClause(String orderByClause) {
        this.orderByClause = orderByClause;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table used_technical_skills
     *
     * @mbggenerated Tue May 26 13:23:22 JST 2020
     */
    public String getOrderByClause() {
        return orderByClause;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table used_technical_skills
     *
     * @mbggenerated Tue May 26 13:23:22 JST 2020
     */
    public void setDistinct(boolean distinct) {
        this.distinct = distinct;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table used_technical_skills
     *
     * @mbggenerated Tue May 26 13:23:22 JST 2020
     */
    public boolean isDistinct() {
        return distinct;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table used_technical_skills
     *
     * @mbggenerated Tue May 26 13:23:22 JST 2020
     */
    public List<Criteria> getOredCriteria() {
        return oredCriteria;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table used_technical_skills
     *
     * @mbggenerated Tue May 26 13:23:22 JST 2020
     */
    public void or(Criteria criteria) {
        oredCriteria.add(criteria);
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table used_technical_skills
     *
     * @mbggenerated Tue May 26 13:23:22 JST 2020
     */
    public Criteria or() {
        Criteria criteria = createCriteriaInternal();
        oredCriteria.add(criteria);
        return criteria;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table used_technical_skills
     *
     * @mbggenerated Tue May 26 13:23:22 JST 2020
     */
    public Criteria createCriteria() {
        Criteria criteria = createCriteriaInternal();
        if (oredCriteria.size() == 0) {
            oredCriteria.add(criteria);
        }
        return criteria;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table used_technical_skills
     *
     * @mbggenerated Tue May 26 13:23:22 JST 2020
     */
    protected Criteria createCriteriaInternal() {
        Criteria criteria = new Criteria();
        return criteria;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table used_technical_skills
     *
     * @mbggenerated Tue May 26 13:23:22 JST 2020
     */
    public void clear() {
        oredCriteria.clear();
        orderByClause = null;
        distinct = false;
    }

    /**
     * This class was generated by MyBatis Generator.
     * This class corresponds to the database table used_technical_skills
     *
     * @mbggenerated Tue May 26 13:23:22 JST 2020
     */
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

        public Criteria andUsedTechnicalSkillIdIsNull() {
            addCriterion("used_technical_skill_id is null");
            return (Criteria) this;
        }

        public Criteria andUsedTechnicalSkillIdIsNotNull() {
            addCriterion("used_technical_skill_id is not null");
            return (Criteria) this;
        }

        public Criteria andUsedTechnicalSkillIdEqualTo(Integer value) {
            addCriterion("used_technical_skill_id =", value, "usedTechnicalSkillId");
            return (Criteria) this;
        }

        public Criteria andUsedTechnicalSkillIdNotEqualTo(Integer value) {
            addCriterion("used_technical_skill_id <>", value, "usedTechnicalSkillId");
            return (Criteria) this;
        }

        public Criteria andUsedTechnicalSkillIdGreaterThan(Integer value) {
            addCriterion("used_technical_skill_id >", value, "usedTechnicalSkillId");
            return (Criteria) this;
        }

        public Criteria andUsedTechnicalSkillIdGreaterThanOrEqualTo(Integer value) {
            addCriterion("used_technical_skill_id >=", value, "usedTechnicalSkillId");
            return (Criteria) this;
        }

        public Criteria andUsedTechnicalSkillIdLessThan(Integer value) {
            addCriterion("used_technical_skill_id <", value, "usedTechnicalSkillId");
            return (Criteria) this;
        }

        public Criteria andUsedTechnicalSkillIdLessThanOrEqualTo(Integer value) {
            addCriterion("used_technical_skill_id <=", value, "usedTechnicalSkillId");
            return (Criteria) this;
        }

        public Criteria andUsedTechnicalSkillIdIn(List<Integer> values) {
            addCriterion("used_technical_skill_id in", values, "usedTechnicalSkillId");
            return (Criteria) this;
        }

        public Criteria andUsedTechnicalSkillIdNotIn(List<Integer> values) {
            addCriterion("used_technical_skill_id not in", values, "usedTechnicalSkillId");
            return (Criteria) this;
        }

        public Criteria andUsedTechnicalSkillIdBetween(Integer value1, Integer value2) {
            addCriterion("used_technical_skill_id between", value1, value2, "usedTechnicalSkillId");
            return (Criteria) this;
        }

        public Criteria andUsedTechnicalSkillIdNotBetween(Integer value1, Integer value2) {
            addCriterion("used_technical_skill_id not between", value1, value2, "usedTechnicalSkillId");
            return (Criteria) this;
        }

        public Criteria andDevExperienceIdIsNull() {
            addCriterion("dev_experience_id is null");
            return (Criteria) this;
        }

        public Criteria andDevExperienceIdIsNotNull() {
            addCriterion("dev_experience_id is not null");
            return (Criteria) this;
        }

        public Criteria andDevExperienceIdEqualTo(Integer value) {
            addCriterion("dev_experience_id =", value, "devExperienceId");
            return (Criteria) this;
        }

        public Criteria andDevExperienceIdNotEqualTo(Integer value) {
            addCriterion("dev_experience_id <>", value, "devExperienceId");
            return (Criteria) this;
        }

        public Criteria andDevExperienceIdGreaterThan(Integer value) {
            addCriterion("dev_experience_id >", value, "devExperienceId");
            return (Criteria) this;
        }

        public Criteria andDevExperienceIdGreaterThanOrEqualTo(Integer value) {
            addCriterion("dev_experience_id >=", value, "devExperienceId");
            return (Criteria) this;
        }

        public Criteria andDevExperienceIdLessThan(Integer value) {
            addCriterion("dev_experience_id <", value, "devExperienceId");
            return (Criteria) this;
        }

        public Criteria andDevExperienceIdLessThanOrEqualTo(Integer value) {
            addCriterion("dev_experience_id <=", value, "devExperienceId");
            return (Criteria) this;
        }

        public Criteria andDevExperienceIdIn(List<Integer> values) {
            addCriterion("dev_experience_id in", values, "devExperienceId");
            return (Criteria) this;
        }

        public Criteria andDevExperienceIdNotIn(List<Integer> values) {
            addCriterion("dev_experience_id not in", values, "devExperienceId");
            return (Criteria) this;
        }

        public Criteria andDevExperienceIdBetween(Integer value1, Integer value2) {
            addCriterion("dev_experience_id between", value1, value2, "devExperienceId");
            return (Criteria) this;
        }

        public Criteria andDevExperienceIdNotBetween(Integer value1, Integer value2) {
            addCriterion("dev_experience_id not between", value1, value2, "devExperienceId");
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
    }

    /**
     * This class was generated by MyBatis Generator.
     * This class corresponds to the database table used_technical_skills
     *
     * @mbggenerated do_not_delete_during_merge Tue May 26 13:23:22 JST 2020
     */
    public static class Criteria extends GeneratedCriteria {

        protected Criteria() {
            super();
        }
    }

    /**
     * This class was generated by MyBatis Generator.
     * This class corresponds to the database table used_technical_skills
     *
     * @mbggenerated Tue May 26 13:23:22 JST 2020
     */
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