package com.example.example;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class AppSpecsheetExample {
    protected String orderByClause;

    protected boolean distinct;

    protected List<Criteria> oredCriteria;

    public AppSpecsheetExample() {
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

        public Criteria andSpecsheetIdIsNull() {
            addCriterion("specsheet_id is null");
            return (Criteria) this;
        }

        public Criteria andSpecsheetIdIsNotNull() {
            addCriterion("specsheet_id is not null");
            return (Criteria) this;
        }

        public Criteria andSpecsheetIdEqualTo(Integer value) {
            addCriterion("specsheet_id =", value, "specsheetId");
            return (Criteria) this;
        }

        public Criteria andSpecsheetIdNotEqualTo(Integer value) {
            addCriterion("specsheet_id <>", value, "specsheetId");
            return (Criteria) this;
        }

        public Criteria andSpecsheetIdGreaterThan(Integer value) {
            addCriterion("specsheet_id >", value, "specsheetId");
            return (Criteria) this;
        }

        public Criteria andSpecsheetIdGreaterThanOrEqualTo(Integer value) {
            addCriterion("specsheet_id >=", value, "specsheetId");
            return (Criteria) this;
        }

        public Criteria andSpecsheetIdLessThan(Integer value) {
            addCriterion("specsheet_id <", value, "specsheetId");
            return (Criteria) this;
        }

        public Criteria andSpecsheetIdLessThanOrEqualTo(Integer value) {
            addCriterion("specsheet_id <=", value, "specsheetId");
            return (Criteria) this;
        }

        public Criteria andSpecsheetIdIn(List<Integer> values) {
            addCriterion("specsheet_id in", values, "specsheetId");
            return (Criteria) this;
        }

        public Criteria andSpecsheetIdNotIn(List<Integer> values) {
            addCriterion("specsheet_id not in", values, "specsheetId");
            return (Criteria) this;
        }

        public Criteria andSpecsheetIdBetween(Integer value1, Integer value2) {
            addCriterion("specsheet_id between", value1, value2, "specsheetId");
            return (Criteria) this;
        }

        public Criteria andSpecsheetIdNotBetween(Integer value1, Integer value2) {
            addCriterion("specsheet_id not between", value1, value2, "specsheetId");
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

        public Criteria andEmployeeIdIsNull() {
            addCriterion("employee_id is null");
            return (Criteria) this;
        }

        public Criteria andEmployeeIdIsNotNull() {
            addCriterion("employee_id is not null");
            return (Criteria) this;
        }

        public Criteria andEmployeeIdEqualTo(Integer value) {
            addCriterion("employee_id =", value, "employeeId");
            return (Criteria) this;
        }

        public Criteria andEmployeeIdNotEqualTo(Integer value) {
            addCriterion("employee_id <>", value, "employeeId");
            return (Criteria) this;
        }

        public Criteria andEmployeeIdGreaterThan(Integer value) {
            addCriterion("employee_id >", value, "employeeId");
            return (Criteria) this;
        }

        public Criteria andEmployeeIdGreaterThanOrEqualTo(Integer value) {
            addCriterion("employee_id >=", value, "employeeId");
            return (Criteria) this;
        }

        public Criteria andEmployeeIdLessThan(Integer value) {
            addCriterion("employee_id <", value, "employeeId");
            return (Criteria) this;
        }

        public Criteria andEmployeeIdLessThanOrEqualTo(Integer value) {
            addCriterion("employee_id <=", value, "employeeId");
            return (Criteria) this;
        }

        public Criteria andEmployeeIdIn(List<Integer> values) {
            addCriterion("employee_id in", values, "employeeId");
            return (Criteria) this;
        }

        public Criteria andEmployeeIdNotIn(List<Integer> values) {
            addCriterion("employee_id not in", values, "employeeId");
            return (Criteria) this;
        }

        public Criteria andEmployeeIdBetween(Integer value1, Integer value2) {
            addCriterion("employee_id between", value1, value2, "employeeId");
            return (Criteria) this;
        }

        public Criteria andEmployeeIdNotBetween(Integer value1, Integer value2) {
            addCriterion("employee_id not between", value1, value2, "employeeId");
            return (Criteria) this;
        }

        public Criteria andGenerationIsNull() {
            addCriterion("generation is null");
            return (Criteria) this;
        }

        public Criteria andGenerationIsNotNull() {
            addCriterion("generation is not null");
            return (Criteria) this;
        }

        public Criteria andGenerationEqualTo(String value) {
            addCriterion("generation =", value, "generation");
            return (Criteria) this;
        }

        public Criteria andGenerationNotEqualTo(String value) {
            addCriterion("generation <>", value, "generation");
            return (Criteria) this;
        }

        public Criteria andGenerationGreaterThan(String value) {
            addCriterion("generation >", value, "generation");
            return (Criteria) this;
        }

        public Criteria andGenerationGreaterThanOrEqualTo(String value) {
            addCriterion("generation >=", value, "generation");
            return (Criteria) this;
        }

        public Criteria andGenerationLessThan(String value) {
            addCriterion("generation <", value, "generation");
            return (Criteria) this;
        }

        public Criteria andGenerationLessThanOrEqualTo(String value) {
            addCriterion("generation <=", value, "generation");
            return (Criteria) this;
        }

        public Criteria andGenerationLike(String value) {
            addCriterion("generation like", value, "generation");
            return (Criteria) this;
        }

        public Criteria andGenerationNotLike(String value) {
            addCriterion("generation not like", value, "generation");
            return (Criteria) this;
        }

        public Criteria andGenerationIn(List<String> values) {
            addCriterion("generation in", values, "generation");
            return (Criteria) this;
        }

        public Criteria andGenerationNotIn(List<String> values) {
            addCriterion("generation not in", values, "generation");
            return (Criteria) this;
        }

        public Criteria andGenerationBetween(String value1, String value2) {
            addCriterion("generation between", value1, value2, "generation");
            return (Criteria) this;
        }

        public Criteria andGenerationNotBetween(String value1, String value2) {
            addCriterion("generation not between", value1, value2, "generation");
            return (Criteria) this;
        }

        public Criteria andGenderIsNull() {
            addCriterion("gender is null");
            return (Criteria) this;
        }

        public Criteria andGenderIsNotNull() {
            addCriterion("gender is not null");
            return (Criteria) this;
        }

        public Criteria andGenderEqualTo(String value) {
            addCriterion("gender =", value, "gender");
            return (Criteria) this;
        }

        public Criteria andGenderNotEqualTo(String value) {
            addCriterion("gender <>", value, "gender");
            return (Criteria) this;
        }

        public Criteria andGenderGreaterThan(String value) {
            addCriterion("gender >", value, "gender");
            return (Criteria) this;
        }

        public Criteria andGenderGreaterThanOrEqualTo(String value) {
            addCriterion("gender >=", value, "gender");
            return (Criteria) this;
        }

        public Criteria andGenderLessThan(String value) {
            addCriterion("gender <", value, "gender");
            return (Criteria) this;
        }

        public Criteria andGenderLessThanOrEqualTo(String value) {
            addCriterion("gender <=", value, "gender");
            return (Criteria) this;
        }

        public Criteria andGenderLike(String value) {
            addCriterion("gender like", value, "gender");
            return (Criteria) this;
        }

        public Criteria andGenderNotLike(String value) {
            addCriterion("gender not like", value, "gender");
            return (Criteria) this;
        }

        public Criteria andGenderIn(List<String> values) {
            addCriterion("gender in", values, "gender");
            return (Criteria) this;
        }

        public Criteria andGenderNotIn(List<String> values) {
            addCriterion("gender not in", values, "gender");
            return (Criteria) this;
        }

        public Criteria andGenderBetween(String value1, String value2) {
            addCriterion("gender between", value1, value2, "gender");
            return (Criteria) this;
        }

        public Criteria andGenderNotBetween(String value1, String value2) {
            addCriterion("gender not between", value1, value2, "gender");
            return (Criteria) this;
        }

        public Criteria andNearestStationIsNull() {
            addCriterion("nearest_station is null");
            return (Criteria) this;
        }

        public Criteria andNearestStationIsNotNull() {
            addCriterion("nearest_station is not null");
            return (Criteria) this;
        }

        public Criteria andNearestStationEqualTo(String value) {
            addCriterion("nearest_station =", value, "nearestStation");
            return (Criteria) this;
        }

        public Criteria andNearestStationNotEqualTo(String value) {
            addCriterion("nearest_station <>", value, "nearestStation");
            return (Criteria) this;
        }

        public Criteria andNearestStationGreaterThan(String value) {
            addCriterion("nearest_station >", value, "nearestStation");
            return (Criteria) this;
        }

        public Criteria andNearestStationGreaterThanOrEqualTo(String value) {
            addCriterion("nearest_station >=", value, "nearestStation");
            return (Criteria) this;
        }

        public Criteria andNearestStationLessThan(String value) {
            addCriterion("nearest_station <", value, "nearestStation");
            return (Criteria) this;
        }

        public Criteria andNearestStationLessThanOrEqualTo(String value) {
            addCriterion("nearest_station <=", value, "nearestStation");
            return (Criteria) this;
        }

        public Criteria andNearestStationLike(String value) {
            addCriterion("nearest_station like", value, "nearestStation");
            return (Criteria) this;
        }

        public Criteria andNearestStationNotLike(String value) {
            addCriterion("nearest_station not like", value, "nearestStation");
            return (Criteria) this;
        }

        public Criteria andNearestStationIn(List<String> values) {
            addCriterion("nearest_station in", values, "nearestStation");
            return (Criteria) this;
        }

        public Criteria andNearestStationNotIn(List<String> values) {
            addCriterion("nearest_station not in", values, "nearestStation");
            return (Criteria) this;
        }

        public Criteria andNearestStationBetween(String value1, String value2) {
            addCriterion("nearest_station between", value1, value2, "nearestStation");
            return (Criteria) this;
        }

        public Criteria andNearestStationNotBetween(String value1, String value2) {
            addCriterion("nearest_station not between", value1, value2, "nearestStation");
            return (Criteria) this;
        }

        public Criteria andStartBusinessDateIsNull() {
            addCriterion("start_business_date is null");
            return (Criteria) this;
        }

        public Criteria andStartBusinessDateIsNotNull() {
            addCriterion("start_business_date is not null");
            return (Criteria) this;
        }

        public Criteria andStartBusinessDateEqualTo(String value) {
            addCriterion("start_business_date =", value, "startBusinessDate");
            return (Criteria) this;
        }

        public Criteria andStartBusinessDateNotEqualTo(String value) {
            addCriterion("start_business_date <>", value, "startBusinessDate");
            return (Criteria) this;
        }

        public Criteria andStartBusinessDateGreaterThan(String value) {
            addCriterion("start_business_date >", value, "startBusinessDate");
            return (Criteria) this;
        }

        public Criteria andStartBusinessDateGreaterThanOrEqualTo(String value) {
            addCriterion("start_business_date >=", value, "startBusinessDate");
            return (Criteria) this;
        }

        public Criteria andStartBusinessDateLessThan(String value) {
            addCriterion("start_business_date <", value, "startBusinessDate");
            return (Criteria) this;
        }

        public Criteria andStartBusinessDateLessThanOrEqualTo(String value) {
            addCriterion("start_business_date <=", value, "startBusinessDate");
            return (Criteria) this;
        }

        public Criteria andStartBusinessDateLike(String value) {
            addCriterion("start_business_date like", value, "startBusinessDate");
            return (Criteria) this;
        }

        public Criteria andStartBusinessDateNotLike(String value) {
            addCriterion("start_business_date not like", value, "startBusinessDate");
            return (Criteria) this;
        }

        public Criteria andStartBusinessDateIn(List<String> values) {
            addCriterion("start_business_date in", values, "startBusinessDate");
            return (Criteria) this;
        }

        public Criteria andStartBusinessDateNotIn(List<String> values) {
            addCriterion("start_business_date not in", values, "startBusinessDate");
            return (Criteria) this;
        }

        public Criteria andStartBusinessDateBetween(String value1, String value2) {
            addCriterion("start_business_date between", value1, value2, "startBusinessDate");
            return (Criteria) this;
        }

        public Criteria andStartBusinessDateNotBetween(String value1, String value2) {
            addCriterion("start_business_date not between", value1, value2, "startBusinessDate");
            return (Criteria) this;
        }

        public Criteria andEngineerPeriodIsNull() {
            addCriterion("engineer_period is null");
            return (Criteria) this;
        }

        public Criteria andEngineerPeriodIsNotNull() {
            addCriterion("engineer_period is not null");
            return (Criteria) this;
        }

        public Criteria andEngineerPeriodEqualTo(Integer value) {
            addCriterion("engineer_period =", value, "engineerPeriod");
            return (Criteria) this;
        }

        public Criteria andEngineerPeriodNotEqualTo(Integer value) {
            addCriterion("engineer_period <>", value, "engineerPeriod");
            return (Criteria) this;
        }

        public Criteria andEngineerPeriodGreaterThan(Integer value) {
            addCriterion("engineer_period >", value, "engineerPeriod");
            return (Criteria) this;
        }

        public Criteria andEngineerPeriodGreaterThanOrEqualTo(Integer value) {
            addCriterion("engineer_period >=", value, "engineerPeriod");
            return (Criteria) this;
        }

        public Criteria andEngineerPeriodLessThan(Integer value) {
            addCriterion("engineer_period <", value, "engineerPeriod");
            return (Criteria) this;
        }

        public Criteria andEngineerPeriodLessThanOrEqualTo(Integer value) {
            addCriterion("engineer_period <=", value, "engineerPeriod");
            return (Criteria) this;
        }

        public Criteria andEngineerPeriodIn(List<Integer> values) {
            addCriterion("engineer_period in", values, "engineerPeriod");
            return (Criteria) this;
        }

        public Criteria andEngineerPeriodNotIn(List<Integer> values) {
            addCriterion("engineer_period not in", values, "engineerPeriod");
            return (Criteria) this;
        }

        public Criteria andEngineerPeriodBetween(Integer value1, Integer value2) {
            addCriterion("engineer_period between", value1, value2, "engineerPeriod");
            return (Criteria) this;
        }

        public Criteria andEngineerPeriodNotBetween(Integer value1, Integer value2) {
            addCriterion("engineer_period not between", value1, value2, "engineerPeriod");
            return (Criteria) this;
        }

        public Criteria andSePeriodIsNull() {
            addCriterion("se_period is null");
            return (Criteria) this;
        }

        public Criteria andSePeriodIsNotNull() {
            addCriterion("se_period is not null");
            return (Criteria) this;
        }

        public Criteria andSePeriodEqualTo(Integer value) {
            addCriterion("se_period =", value, "sePeriod");
            return (Criteria) this;
        }

        public Criteria andSePeriodNotEqualTo(Integer value) {
            addCriterion("se_period <>", value, "sePeriod");
            return (Criteria) this;
        }

        public Criteria andSePeriodGreaterThan(Integer value) {
            addCriterion("se_period >", value, "sePeriod");
            return (Criteria) this;
        }

        public Criteria andSePeriodGreaterThanOrEqualTo(Integer value) {
            addCriterion("se_period >=", value, "sePeriod");
            return (Criteria) this;
        }

        public Criteria andSePeriodLessThan(Integer value) {
            addCriterion("se_period <", value, "sePeriod");
            return (Criteria) this;
        }

        public Criteria andSePeriodLessThanOrEqualTo(Integer value) {
            addCriterion("se_period <=", value, "sePeriod");
            return (Criteria) this;
        }

        public Criteria andSePeriodIn(List<Integer> values) {
            addCriterion("se_period in", values, "sePeriod");
            return (Criteria) this;
        }

        public Criteria andSePeriodNotIn(List<Integer> values) {
            addCriterion("se_period not in", values, "sePeriod");
            return (Criteria) this;
        }

        public Criteria andSePeriodBetween(Integer value1, Integer value2) {
            addCriterion("se_period between", value1, value2, "sePeriod");
            return (Criteria) this;
        }

        public Criteria andSePeriodNotBetween(Integer value1, Integer value2) {
            addCriterion("se_period not between", value1, value2, "sePeriod");
            return (Criteria) this;
        }

        public Criteria andPgPeriodIsNull() {
            addCriterion("pg_period is null");
            return (Criteria) this;
        }

        public Criteria andPgPeriodIsNotNull() {
            addCriterion("pg_period is not null");
            return (Criteria) this;
        }

        public Criteria andPgPeriodEqualTo(Integer value) {
            addCriterion("pg_period =", value, "pgPeriod");
            return (Criteria) this;
        }

        public Criteria andPgPeriodNotEqualTo(Integer value) {
            addCriterion("pg_period <>", value, "pgPeriod");
            return (Criteria) this;
        }

        public Criteria andPgPeriodGreaterThan(Integer value) {
            addCriterion("pg_period >", value, "pgPeriod");
            return (Criteria) this;
        }

        public Criteria andPgPeriodGreaterThanOrEqualTo(Integer value) {
            addCriterion("pg_period >=", value, "pgPeriod");
            return (Criteria) this;
        }

        public Criteria andPgPeriodLessThan(Integer value) {
            addCriterion("pg_period <", value, "pgPeriod");
            return (Criteria) this;
        }

        public Criteria andPgPeriodLessThanOrEqualTo(Integer value) {
            addCriterion("pg_period <=", value, "pgPeriod");
            return (Criteria) this;
        }

        public Criteria andPgPeriodIn(List<Integer> values) {
            addCriterion("pg_period in", values, "pgPeriod");
            return (Criteria) this;
        }

        public Criteria andPgPeriodNotIn(List<Integer> values) {
            addCriterion("pg_period not in", values, "pgPeriod");
            return (Criteria) this;
        }

        public Criteria andPgPeriodBetween(Integer value1, Integer value2) {
            addCriterion("pg_period between", value1, value2, "pgPeriod");
            return (Criteria) this;
        }

        public Criteria andPgPeriodNotBetween(Integer value1, Integer value2) {
            addCriterion("pg_period not between", value1, value2, "pgPeriod");
            return (Criteria) this;
        }

        public Criteria andItPeriodIsNull() {
            addCriterion("it_period is null");
            return (Criteria) this;
        }

        public Criteria andItPeriodIsNotNull() {
            addCriterion("it_period is not null");
            return (Criteria) this;
        }

        public Criteria andItPeriodEqualTo(Integer value) {
            addCriterion("it_period =", value, "itPeriod");
            return (Criteria) this;
        }

        public Criteria andItPeriodNotEqualTo(Integer value) {
            addCriterion("it_period <>", value, "itPeriod");
            return (Criteria) this;
        }

        public Criteria andItPeriodGreaterThan(Integer value) {
            addCriterion("it_period >", value, "itPeriod");
            return (Criteria) this;
        }

        public Criteria andItPeriodGreaterThanOrEqualTo(Integer value) {
            addCriterion("it_period >=", value, "itPeriod");
            return (Criteria) this;
        }

        public Criteria andItPeriodLessThan(Integer value) {
            addCriterion("it_period <", value, "itPeriod");
            return (Criteria) this;
        }

        public Criteria andItPeriodLessThanOrEqualTo(Integer value) {
            addCriterion("it_period <=", value, "itPeriod");
            return (Criteria) this;
        }

        public Criteria andItPeriodIn(List<Integer> values) {
            addCriterion("it_period in", values, "itPeriod");
            return (Criteria) this;
        }

        public Criteria andItPeriodNotIn(List<Integer> values) {
            addCriterion("it_period not in", values, "itPeriod");
            return (Criteria) this;
        }

        public Criteria andItPeriodBetween(Integer value1, Integer value2) {
            addCriterion("it_period between", value1, value2, "itPeriod");
            return (Criteria) this;
        }

        public Criteria andItPeriodNotBetween(Integer value1, Integer value2) {
            addCriterion("it_period not between", value1, value2, "itPeriod");
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

        public Criteria andCreatedAtEqualTo(Date value) {
            addCriterion("created_at =", value, "createdAt");
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

        public Criteria andUpdatedAtEqualTo(Date value) {
            addCriterion("updated_at =", value, "updatedAt");
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

        public Criteria andUpdatedAtGreaterThanOrEqualTo(LocalDateTime value) {
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