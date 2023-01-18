package com.example.example;

import java.util.ArrayList;
import java.util.List;

/**
 * template_basic_skillsテーブルでwhere句として使用するExampleクラス.
 * 
 * @author yuuki
 *
 */
public class TemplateBasicSkillExample {

    protected String orderByClause;
    
    protected boolean distinct;

    protected List<Criteria> oredCriteria;

    public TemplateBasicSkillExample() {
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

        public Criteria andTemplateBasicSkillIdIsNull() {
            addCriterion("template_basic_skill_id is null");
            return (Criteria) this;
        }

        public Criteria andTemplateBasicSkillIdIsNotNull() {
            addCriterion("template_basic_skill_id is not null");
            return (Criteria) this;
        }

        public Criteria andTemplateBasicSkillIdEqualTo(Integer value) {
            addCriterion("template_basic_skill_id =", value, "templateBasicSkillId");
            return (Criteria) this;
        }

        public Criteria andTemplateBasicSkillIdNotEqualTo(Integer value) {
            addCriterion("template_basic_skill_id <>", value, "templateBasicSkillId");
            return (Criteria) this;
        }

        public Criteria andTemplateBasicSkillIdGreaterThan(Integer value) {
            addCriterion("template_basic_skill_id >", value, "templateBasicSkillId");
            return (Criteria) this;
        }

        public Criteria andTemplateBasicSkillIdGreaterThanOrEqualTo(Integer value) {
            addCriterion("template_basic_skill_id >=", value, "templateBasicSkillId");
            return (Criteria) this;
        }

        public Criteria andTemplateBasicSkillIdLessThan(Integer value) {
            addCriterion("template_basic_skill_id <", value, "templateBasicSkillId");
            return (Criteria) this;
        }

        public Criteria andTemplateBasicSkillIdLessThanOrEqualTo(Integer value) {
            addCriterion("template_basic_skill_id <=", value, "templateBasicSkillId");
            return (Criteria) this;
        }

        public Criteria andTemplateBasicSkillIdIn(List<Integer> values) {
            addCriterion("template_basic_skill_id in", values, "templateBasicSkillId");
            return (Criteria) this;
        }

        public Criteria andTemplateBasicSkillIdNotIn(List<Integer> values) {
            addCriterion("template_basic_skill_id not in", values, "templateBasicSkillId");
            return (Criteria) this;
        }

        public Criteria andTemplateBasicSkillIdBetween(Integer value1, Integer value2) {
            addCriterion("template_basic_skill_id between", value1, value2, "templateBasicSkillId");
            return (Criteria) this;
        }

        public Criteria andTemplateBasicSkillIdNotBetween(Integer value1, Integer value2) {
            addCriterion("template_basic_skill_id not between", value1, value2, "templateBasicSkillId");
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

        public Criteria andBasicSkillIdIsNull() {
            addCriterion("basic_skill_id is null");
            return (Criteria) this;
        }

        public Criteria andBasicSkillIdIsNotNull() {
            addCriterion("basic_skill_id is not null");
            return (Criteria) this;
        }

        public Criteria andBasicSkillIdEqualTo(Integer value) {
            addCriterion("basic_skill_id =", value, "basicSkillId");
            return (Criteria) this;
        }

        public Criteria andBasicSkillIdNotEqualTo(Integer value) {
            addCriterion("basic_skill_id <>", value, "basicSkillId");
            return (Criteria) this;
        }

        public Criteria andBasicSkillIdGreaterThan(Integer value) {
            addCriterion("basic_skill_id >", value, "basicSkillId");
            return (Criteria) this;
        }

        public Criteria andBasicSkillIdGreaterThanOrEqualTo(Integer value) {
            addCriterion("basic_skill_id >=", value, "basicSkillId");
            return (Criteria) this;
        }

        public Criteria andBasicSkillIdLessThan(Integer value) {
            addCriterion("basic_skill_id <", value, "basicSkillId");
            return (Criteria) this;
        }

        public Criteria andBasicSkillIdLessThanOrEqualTo(Integer value) {
            addCriterion("basic_skill_id <=", value, "basicSkillId");
            return (Criteria) this;
        }

        public Criteria andBasicSkillIdIn(List<Integer> values) {
            addCriterion("basic_skill_id in", values, "basicSkillId");
            return (Criteria) this;
        }

        public Criteria andBasicSkillIdNotIn(List<Integer> values) {
            addCriterion("basic_skill_id not in", values, "basicSkillId");
            return (Criteria) this;
        }

        public Criteria andBasicSkillIdBetween(Integer value1, Integer value2) {
            addCriterion("basic_skill_id between", value1, value2, "basicSkillId");
            return (Criteria) this;
        }

        public Criteria andBasicSkillIdNotBetween(Integer value1, Integer value2) {
            addCriterion("basic_skill_id not between", value1, value2, "basicSkillId");
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

        public Criteria andScoreEqualTo(String value) {
            addCriterion("score =", value, "score");
            return (Criteria) this;
        }

        public Criteria andScoreNotEqualTo(String value) {
            addCriterion("score <>", value, "score");
            return (Criteria) this;
        }

        public Criteria andScoreGreaterThan(String value) {
            addCriterion("score >", value, "score");
            return (Criteria) this;
        }

        public Criteria andScoreGreaterThanOrEqualTo(String value) {
            addCriterion("score >=", value, "score");
            return (Criteria) this;
        }

        public Criteria andScoreLessThan(String value) {
            addCriterion("score <", value, "score");
            return (Criteria) this;
        }

        public Criteria andScoreLessThanOrEqualTo(String value) {
            addCriterion("score <=", value, "score");
            return (Criteria) this;
        }

        public Criteria andScoreLike(String value) {
            addCriterion("score like", value, "score");
            return (Criteria) this;
        }

        public Criteria andScoreNotLike(String value) {
            addCriterion("score not like", value, "score");
            return (Criteria) this;
        }

        public Criteria andScoreIn(List<String> values) {
            addCriterion("score in", values, "score");
            return (Criteria) this;
        }

        public Criteria andScoreNotIn(List<String> values) {
            addCriterion("score not in", values, "score");
            return (Criteria) this;
        }

        public Criteria andScoreBetween(String value1, String value2) {
            addCriterion("score between", value1, value2, "score");
            return (Criteria) this;
        }

        public Criteria andScoreNotBetween(String value1, String value2) {
            addCriterion("score not between", value1, value2, "score");
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