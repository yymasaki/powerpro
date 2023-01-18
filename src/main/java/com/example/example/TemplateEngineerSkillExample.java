package com.example.example;

import java.util.ArrayList;
import java.util.List;

/**
 * template_engineer_skillsテーブルでwhere句として使用するExampleクラス.
 * 
 * @author yuuki
 *
 */
public class TemplateEngineerSkillExample {

    protected String orderByClause;

    protected boolean distinct;

    protected List<Criteria> oredCriteria;

    public TemplateEngineerSkillExample() {
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

        public Criteria andTemplateEngineerSkillIdIsNull() {
            addCriterion("template_engineer_skill_id is null");
            return (Criteria) this;
        }

        public Criteria andTemplateEngineerSkillIdIsNotNull() {
            addCriterion("template_engineer_skill_id is not null");
            return (Criteria) this;
        }

        public Criteria andTemplateEngineerSkillIdEqualTo(Integer value) {
            addCriterion("template_engineer_skill_id =", value, "templateEngineerSkillId");
            return (Criteria) this;
        }

        public Criteria andTemplateEngineerSkillIdNotEqualTo(Integer value) {
            addCriterion("template_engineer_skill_id <>", value, "templateEngineerSkillId");
            return (Criteria) this;
        }

        public Criteria andTemplateEngineerSkillIdGreaterThan(Integer value) {
            addCriterion("template_engineer_skill_id >", value, "templateEngineerSkillId");
            return (Criteria) this;
        }

        public Criteria andTemplateEngineerSkillIdGreaterThanOrEqualTo(Integer value) {
            addCriterion("template_engineer_skill_id >=", value, "templateEngineerSkillId");
            return (Criteria) this;
        }

        public Criteria andTemplateEngineerSkillIdLessThan(Integer value) {
            addCriterion("template_engineer_skill_id <", value, "templateEngineerSkillId");
            return (Criteria) this;
        }

        public Criteria andTemplateEngineerSkillIdLessThanOrEqualTo(Integer value) {
            addCriterion("template_engineer_skill_id <=", value, "templateEngineerSkillId");
            return (Criteria) this;
        }

        public Criteria andTemplateEngineerSkillIdIn(List<Integer> values) {
            addCriterion("template_engineer_skill_id in", values, "templateEngineerSkillId");
            return (Criteria) this;
        }

        public Criteria andTemplateEngineerSkillIdNotIn(List<Integer> values) {
            addCriterion("template_engineer_skill_id not in", values, "templateEngineerSkillId");
            return (Criteria) this;
        }

        public Criteria andTemplateEngineerSkillIdBetween(Integer value1, Integer value2) {
            addCriterion("template_engineer_skill_id between", value1, value2, "templateEngineerSkillId");
            return (Criteria) this;
        }

        public Criteria andTemplateEngineerSkillIdNotBetween(Integer value1, Integer value2) {
            addCriterion("template_engineer_skill_id not between", value1, value2, "templateEngineerSkillId");
            return (Criteria) this;
        }

        public Criteria andTemplateIdIsNull() {
            addCriterion("template_id is null");
            return (Criteria) this;
        }

        public Criteria andTemplateIdIsNotNull() {
            addCriterion("template_id is not null");
            return (Criteria) this;
        }

        public Criteria andTemplateIdEqualTo(Integer value) {
            addCriterion("template_id =", value, "templateId");
            return (Criteria) this;
        }

        public Criteria andTemplateIdNotEqualTo(Integer value) {
            addCriterion("template_id <>", value, "templateId");
            return (Criteria) this;
        }

        public Criteria andTemplateIdGreaterThan(Integer value) {
            addCriterion("template_id >", value, "templateId");
            return (Criteria) this;
        }

        public Criteria andTemplateIdGreaterThanOrEqualTo(Integer value) {
            addCriterion("template_id >=", value, "templateId");
            return (Criteria) this;
        }

        public Criteria andTemplateIdLessThan(Integer value) {
            addCriterion("template_id <", value, "templateId");
            return (Criteria) this;
        }

        public Criteria andTemplateIdLessThanOrEqualTo(Integer value) {
            addCriterion("template_id <=", value, "templateId");
            return (Criteria) this;
        }

        public Criteria andTemplateIdIn(List<Integer> values) {
            addCriterion("template_id in", values, "templateId");
            return (Criteria) this;
        }

        public Criteria andTemplateIdNotIn(List<Integer> values) {
            addCriterion("template_id not in", values, "templateId");
            return (Criteria) this;
        }

        public Criteria andTemplateIdBetween(Integer value1, Integer value2) {
            addCriterion("template_id between", value1, value2, "templateId");
            return (Criteria) this;
        }

        public Criteria andTemplateIdNotBetween(Integer value1, Integer value2) {
            addCriterion("template_id not between", value1, value2, "templateId");
            return (Criteria) this;
        }

        public Criteria andEngineerSkillIdIsNull() {
            addCriterion("engineer_skill_id is null");
            return (Criteria) this;
        }

        public Criteria andEngineerSkillIdIsNotNull() {
            addCriterion("engineer_skill_id is not null");
            return (Criteria) this;
        }

        public Criteria andEngineerSkillIdEqualTo(Integer value) {
            addCriterion("engineer_skill_id =", value, "engineerSkillId");
            return (Criteria) this;
        }

        public Criteria andEngineerSkillIdNotEqualTo(Integer value) {
            addCriterion("engineer_skill_id <>", value, "engineerSkillId");
            return (Criteria) this;
        }

        public Criteria andEngineerSkillIdGreaterThan(Integer value) {
            addCriterion("engineer_skill_id >", value, "engineerSkillId");
            return (Criteria) this;
        }

        public Criteria andEngineerSkillIdGreaterThanOrEqualTo(Integer value) {
            addCriterion("engineer_skill_id >=", value, "engineerSkillId");
            return (Criteria) this;
        }

        public Criteria andEngineerSkillIdLessThan(Integer value) {
            addCriterion("engineer_skill_id <", value, "engineerSkillId");
            return (Criteria) this;
        }

        public Criteria andEngineerSkillIdLessThanOrEqualTo(Integer value) {
            addCriterion("engineer_skill_id <=", value, "engineerSkillId");
            return (Criteria) this;
        }

        public Criteria andEngineerSkillIdIn(List<Integer> values) {
            addCriterion("engineer_skill_id in", values, "engineerSkillId");
            return (Criteria) this;
        }

        public Criteria andEngineerSkillIdNotIn(List<Integer> values) {
            addCriterion("engineer_skill_id not in", values, "engineerSkillId");
            return (Criteria) this;
        }

        public Criteria andEngineerSkillIdBetween(Integer value1, Integer value2) {
            addCriterion("engineer_skill_id between", value1, value2, "engineerSkillId");
            return (Criteria) this;
        }

        public Criteria andEngineerSkillIdNotBetween(Integer value1, Integer value2) {
            addCriterion("engineer_skill_id not between", value1, value2, "engineerSkillId");
            return (Criteria) this;
        }

        public Criteria andScoreIsNull() {
            addCriterion("score is null");
            return (Criteria) this;
        }

        public Criteria andScoreIsNotNull() {
            addCriterion("score is not null");
            return (Criteria) this;
        }

        public Criteria andScoreEqualTo(Integer value) {
            addCriterion("score =", value, "score");
            return (Criteria) this;
        }

        public Criteria andScoreNotEqualTo(Integer value) {
            addCriterion("score <>", value, "score");
            return (Criteria) this;
        }

        public Criteria andScoreGreaterThan(Integer value) {
            addCriterion("score >", value, "score");
            return (Criteria) this;
        }

        public Criteria andScoreGreaterThanOrEqualTo(Integer value) {
            addCriterion("score >=", value, "score");
            return (Criteria) this;
        }

        public Criteria andScoreLessThan(Integer value) {
            addCriterion("score <", value, "score");
            return (Criteria) this;
        }

        public Criteria andScoreLessThanOrEqualTo(Integer value) {
            addCriterion("score <=", value, "score");
            return (Criteria) this;
        }

        public Criteria andScoreIn(List<Integer> values) {
            addCriterion("score in", values, "score");
            return (Criteria) this;
        }

        public Criteria andScoreNotIn(List<Integer> values) {
            addCriterion("score not in", values, "score");
            return (Criteria) this;
        }

        public Criteria andScoreBetween(Integer value1, Integer value2) {
            addCriterion("score between", value1, value2, "score");
            return (Criteria) this;
        }

        public Criteria andScoreNotBetween(Integer value1, Integer value2) {
            addCriterion("score not between", value1, value2, "score");
            return (Criteria) this;
        }
    }

    /**
     * This class was generated by MyBatis Generator.
     * This class corresponds to the database table template_engineer_skills
     *
     * @mbggenerated do_not_delete_during_merge Tue May 26 13:24:31 JST 2020
     */
    public static class Criteria extends GeneratedCriteria {

        protected Criteria() {
            super();
        }
    }

    /**
     * This class was generated by MyBatis Generator.
     * This class corresponds to the database table template_engineer_skills
     *
     * @mbggenerated Tue May 26 13:24:31 JST 2020
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