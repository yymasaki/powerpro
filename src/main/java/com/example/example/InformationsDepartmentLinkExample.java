package com.example.example;

import java.util.ArrayList;
import java.util.List;

public class InformationsDepartmentLinkExample {
    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database table informations_department_link
     *
     * @mbggenerated Tue May 26 13:09:57 JST 2020
     */
    protected String orderByClause;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database table informations_department_link
     *
     * @mbggenerated Tue May 26 13:09:57 JST 2020
     */
    protected boolean distinct;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database table informations_department_link
     *
     * @mbggenerated Tue May 26 13:09:57 JST 2020
     */
    protected List<Criteria> oredCriteria;

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table informations_department_link
     *
     * @mbggenerated Tue May 26 13:09:57 JST 2020
     */
    public InformationsDepartmentLinkExample() {
        oredCriteria = new ArrayList<Criteria>();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table informations_department_link
     *
     * @mbggenerated Tue May 26 13:09:57 JST 2020
     */
    public void setOrderByClause(String orderByClause) {
        this.orderByClause = orderByClause;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table informations_department_link
     *
     * @mbggenerated Tue May 26 13:09:57 JST 2020
     */
    public String getOrderByClause() {
        return orderByClause;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table informations_department_link
     *
     * @mbggenerated Tue May 26 13:09:57 JST 2020
     */
    public void setDistinct(boolean distinct) {
        this.distinct = distinct;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table informations_department_link
     *
     * @mbggenerated Tue May 26 13:09:57 JST 2020
     */
    public boolean isDistinct() {
        return distinct;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table informations_department_link
     *
     * @mbggenerated Tue May 26 13:09:57 JST 2020
     */
    public List<Criteria> getOredCriteria() {
        return oredCriteria;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table informations_department_link
     *
     * @mbggenerated Tue May 26 13:09:57 JST 2020
     */
    public void or(Criteria criteria) {
        oredCriteria.add(criteria);
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table informations_department_link
     *
     * @mbggenerated Tue May 26 13:09:57 JST 2020
     */
    public Criteria or() {
        Criteria criteria = createCriteriaInternal();
        oredCriteria.add(criteria);
        return criteria;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table informations_department_link
     *
     * @mbggenerated Tue May 26 13:09:57 JST 2020
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
     * This method corresponds to the database table informations_department_link
     *
     * @mbggenerated Tue May 26 13:09:57 JST 2020
     */
    protected Criteria createCriteriaInternal() {
        Criteria criteria = new Criteria();
        return criteria;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table informations_department_link
     *
     * @mbggenerated Tue May 26 13:09:57 JST 2020
     */
    public void clear() {
        oredCriteria.clear();
        orderByClause = null;
        distinct = false;
    }

    /**
     * This class was generated by MyBatis Generator.
     * This class corresponds to the database table informations_department_link
     *
     * @mbggenerated Tue May 26 13:09:57 JST 2020
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

        public Criteria andInformationsDepartmentLinkIdIsNull() {
            addCriterion("informations_department_link_id is null");
            return (Criteria) this;
        }

        public Criteria andInformationsDepartmentLinkIdIsNotNull() {
            addCriterion("informations_department_link_id is not null");
            return (Criteria) this;
        }

        public Criteria andInformationsDepartmentLinkIdEqualTo(Integer value) {
            addCriterion("informations_department_link_id =", value, "informationsDepartmentLinkId");
            return (Criteria) this;
        }

        public Criteria andInformationsDepartmentLinkIdNotEqualTo(Integer value) {
            addCriterion("informations_department_link_id <>", value, "informationsDepartmentLinkId");
            return (Criteria) this;
        }

        public Criteria andInformationsDepartmentLinkIdGreaterThan(Integer value) {
            addCriterion("informations_department_link_id >", value, "informationsDepartmentLinkId");
            return (Criteria) this;
        }

        public Criteria andInformationsDepartmentLinkIdGreaterThanOrEqualTo(Integer value) {
            addCriterion("informations_department_link_id >=", value, "informationsDepartmentLinkId");
            return (Criteria) this;
        }

        public Criteria andInformationsDepartmentLinkIdLessThan(Integer value) {
            addCriterion("informations_department_link_id <", value, "informationsDepartmentLinkId");
            return (Criteria) this;
        }

        public Criteria andInformationsDepartmentLinkIdLessThanOrEqualTo(Integer value) {
            addCriterion("informations_department_link_id <=", value, "informationsDepartmentLinkId");
            return (Criteria) this;
        }

        public Criteria andInformationsDepartmentLinkIdIn(List<Integer> values) {
            addCriterion("informations_department_link_id in", values, "informationsDepartmentLinkId");
            return (Criteria) this;
        }

        public Criteria andInformationsDepartmentLinkIdNotIn(List<Integer> values) {
            addCriterion("informations_department_link_id not in", values, "informationsDepartmentLinkId");
            return (Criteria) this;
        }

        public Criteria andInformationsDepartmentLinkIdBetween(Integer value1, Integer value2) {
            addCriterion("informations_department_link_id between", value1, value2, "informationsDepartmentLinkId");
            return (Criteria) this;
        }

        public Criteria andInformationsDepartmentLinkIdNotBetween(Integer value1, Integer value2) {
            addCriterion("informations_department_link_id not between", value1, value2, "informationsDepartmentLinkId");
            return (Criteria) this;
        }

        public Criteria andInformationIdIsNull() {
            addCriterion("information_id is null");
            return (Criteria) this;
        }

        public Criteria andInformationIdIsNotNull() {
            addCriterion("information_id is not null");
            return (Criteria) this;
        }

        public Criteria andInformationIdEqualTo(Integer value) {
            addCriterion("information_id =", value, "informationId");
            return (Criteria) this;
        }

        public Criteria andInformationIdNotEqualTo(Integer value) {
            addCriterion("information_id <>", value, "informationId");
            return (Criteria) this;
        }

        public Criteria andInformationIdGreaterThan(Integer value) {
            addCriterion("information_id >", value, "informationId");
            return (Criteria) this;
        }

        public Criteria andInformationIdGreaterThanOrEqualTo(Integer value) {
            addCriterion("information_id >=", value, "informationId");
            return (Criteria) this;
        }

        public Criteria andInformationIdLessThan(Integer value) {
            addCriterion("information_id <", value, "informationId");
            return (Criteria) this;
        }

        public Criteria andInformationIdLessThanOrEqualTo(Integer value) {
            addCriterion("information_id <=", value, "informationId");
            return (Criteria) this;
        }

        public Criteria andInformationIdIn(List<Integer> values) {
            addCriterion("information_id in", values, "informationId");
            return (Criteria) this;
        }

        public Criteria andInformationIdNotIn(List<Integer> values) {
            addCriterion("information_id not in", values, "informationId");
            return (Criteria) this;
        }

        public Criteria andInformationIdBetween(Integer value1, Integer value2) {
            addCriterion("information_id between", value1, value2, "informationId");
            return (Criteria) this;
        }

        public Criteria andInformationIdNotBetween(Integer value1, Integer value2) {
            addCriterion("information_id not between", value1, value2, "informationId");
            return (Criteria) this;
        }

        public Criteria andDepartmentIdIsNull() {
            addCriterion("department_id is null");
            return (Criteria) this;
        }

        public Criteria andDepartmentIdIsNotNull() {
            addCriterion("department_id is not null");
            return (Criteria) this;
        }

        public Criteria andDepartmentIdEqualTo(Integer value) {
            addCriterion("department_id =", value, "departmentId");
            return (Criteria) this;
        }

        public Criteria andDepartmentIdNotEqualTo(Integer value) {
            addCriterion("department_id <>", value, "departmentId");
            return (Criteria) this;
        }

        public Criteria andDepartmentIdGreaterThan(Integer value) {
            addCriterion("department_id >", value, "departmentId");
            return (Criteria) this;
        }

        public Criteria andDepartmentIdGreaterThanOrEqualTo(Integer value) {
            addCriterion("department_id >=", value, "departmentId");
            return (Criteria) this;
        }

        public Criteria andDepartmentIdLessThan(Integer value) {
            addCriterion("department_id <", value, "departmentId");
            return (Criteria) this;
        }

        public Criteria andDepartmentIdLessThanOrEqualTo(Integer value) {
            addCriterion("department_id <=", value, "departmentId");
            return (Criteria) this;
        }

        public Criteria andDepartmentIdIn(List<Integer> values) {
            addCriterion("department_id in", values, "departmentId");
            return (Criteria) this;
        }

        public Criteria andDepartmentIdNotIn(List<Integer> values) {
            addCriterion("department_id not in", values, "departmentId");
            return (Criteria) this;
        }

        public Criteria andDepartmentIdBetween(Integer value1, Integer value2) {
            addCriterion("department_id between", value1, value2, "departmentId");
            return (Criteria) this;
        }

        public Criteria andDepartmentIdNotBetween(Integer value1, Integer value2) {
            addCriterion("department_id not between", value1, value2, "departmentId");
            return (Criteria) this;
        }
    }

    /**
     * This class was generated by MyBatis Generator.
     * This class corresponds to the database table informations_department_link
     *
     * @mbggenerated do_not_delete_during_merge Tue May 26 13:09:57 JST 2020
     */
    public static class Criteria extends GeneratedCriteria {

        protected Criteria() {
            super();
        }
    }

    /**
     * This class was generated by MyBatis Generator.
     * This class corresponds to the database table informations_department_link
     *
     * @mbggenerated Tue May 26 13:09:57 JST 2020
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