package com.mrbt.lingmoney.model;

import java.util.ArrayList;
import java.util.List;

public class AlertPromptExample {
    /**
     * 排序字段,alert_prompt
     */
    protected String orderByClause;

    /**
     * 是否过滤重复数据,alert_prompt
     */
    protected boolean distinct;

    /**
     * 查询条件实例,alert_prompt
     */
    protected List<Criteria> oredCriteria;

    /**
     * 第一个返回记录行的偏移量,alert_prompt
     */
    protected int limitStart = -1;

    /**
     * 返回记录行的最大数目,alert_prompt
     */
    protected int limitEnd = -1;

    /**
     *  构造查询条件,alert_prompt
     */
    public AlertPromptExample() {
        oredCriteria = new ArrayList<Criteria>();
    }

    /**
     *  设置排序字段,alert_prompt
     * @param orderByClause 排序字段
     */
    public void setOrderByClause(String orderByClause) {
        this.orderByClause = orderByClause;
    }

    /**
     *  获取排序字段,alert_prompt
     */
    public String getOrderByClause() {
        return orderByClause;
    }

    /**
     *  设置过滤重复数据,alert_prompt
     * @param distinct 是否过滤重复数据
     */
    public void setDistinct(boolean distinct) {
        this.distinct = distinct;
    }

    /**
     *  是否过滤重复数据,alert_prompt
     */
    public boolean isDistinct() {
        return distinct;
    }

    /**
     *  获取当前的查询条件实例,alert_prompt
     */
    public List<Criteria> getOredCriteria() {
        return oredCriteria;
    }

    /**
     *  增加或者的查询条件,用于构建或者查询,alert_prompt
     * @param criteria 过滤条件实例
     */
    public void or(Criteria criteria) {
        oredCriteria.add(criteria);
    }

    /**
     *  创建一个新的或者查询条件,alert_prompt
     */
    public Criteria or() {
        Criteria criteria = createCriteriaInternal();
        oredCriteria.add(criteria);
        return criteria;
    }

    /**
     *  创建一个查询条件,alert_prompt
     */
    public Criteria createCriteria() {
        Criteria criteria = createCriteriaInternal();
        if (oredCriteria.size() == 0) {
            oredCriteria.add(criteria);
        }
        return criteria;
    }

    /**
     *  内部构建查询条件对象,alert_prompt
     */
    protected Criteria createCriteriaInternal() {
        Criteria criteria = new Criteria();
        return criteria;
    }

    /**
     *  清除查询条件,alert_prompt
     */
    public void clear() {
        oredCriteria.clear();
        orderByClause = null;
        distinct = false;
    }

    /**
     *  设置第一个返回记录行的偏移量,alert_prompt
     * @param limitStart 第一个返回记录行的偏移量
     */
    public void setLimitStart(int limitStart) {
        this.limitStart=limitStart;
    }

    /**
     *  获取第一个返回记录行的偏移量,alert_prompt
     */
    public int getLimitStart() {
        return limitStart;
    }

    /**
     *  设置返回记录行的最大数目,alert_prompt
     * @param limitEnd 返回记录行的最大数目
     */
    public void setLimitEnd(int limitEnd) {
        this.limitEnd=limitEnd;
    }

    /**
     *  获取返回记录行的最大数目,alert_prompt
     */
    public int getLimitEnd() {
        return limitEnd;
    }

    /**
     * 类注释
     * GeneratedCriteria
     * 数据库表：alert_prompt
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

        public Criteria andIdEqualTo(String value) {
            addCriterion("id =", value, "id");
            return (Criteria) this;
        }

        public Criteria andIdNotEqualTo(String value) {
            addCriterion("id <>", value, "id");
            return (Criteria) this;
        }

        public Criteria andIdGreaterThan(String value) {
            addCriterion("id >", value, "id");
            return (Criteria) this;
        }

        public Criteria andIdGreaterThanOrEqualTo(String value) {
            addCriterion("id >=", value, "id");
            return (Criteria) this;
        }

        public Criteria andIdLessThan(String value) {
            addCriterion("id <", value, "id");
            return (Criteria) this;
        }

        public Criteria andIdLessThanOrEqualTo(String value) {
            addCriterion("id <=", value, "id");
            return (Criteria) this;
        }

        public Criteria andIdLike(String value) {
            addCriterion("id like", value, "id");
            return (Criteria) this;
        }

        public Criteria andIdNotLike(String value) {
            addCriterion("id not like", value, "id");
            return (Criteria) this;
        }

        public Criteria andIdIn(List<String> values) {
            addCriterion("id in", values, "id");
            return (Criteria) this;
        }

        public Criteria andIdNotIn(List<String> values) {
            addCriterion("id not in", values, "id");
            return (Criteria) this;
        }

        public Criteria andIdBetween(String value1, String value2) {
            addCriterion("id between", value1, value2, "id");
            return (Criteria) this;
        }

        public Criteria andIdNotBetween(String value1, String value2) {
            addCriterion("id not between", value1, value2, "id");
            return (Criteria) this;
        }

        public Criteria andNameIsNull() {
            addCriterion("name is null");
            return (Criteria) this;
        }

        public Criteria andNameIsNotNull() {
            addCriterion("name is not null");
            return (Criteria) this;
        }

        public Criteria andNameEqualTo(String value) {
            addCriterion("name =", value, "name");
            return (Criteria) this;
        }

        public Criteria andNameNotEqualTo(String value) {
            addCriterion("name <>", value, "name");
            return (Criteria) this;
        }

        public Criteria andNameGreaterThan(String value) {
            addCriterion("name >", value, "name");
            return (Criteria) this;
        }

        public Criteria andNameGreaterThanOrEqualTo(String value) {
            addCriterion("name >=", value, "name");
            return (Criteria) this;
        }

        public Criteria andNameLessThan(String value) {
            addCriterion("name <", value, "name");
            return (Criteria) this;
        }

        public Criteria andNameLessThanOrEqualTo(String value) {
            addCriterion("name <=", value, "name");
            return (Criteria) this;
        }

        public Criteria andNameLike(String value) {
            addCriterion("name like", value, "name");
            return (Criteria) this;
        }

        public Criteria andNameNotLike(String value) {
            addCriterion("name not like", value, "name");
            return (Criteria) this;
        }

        public Criteria andNameIn(List<String> values) {
            addCriterion("name in", values, "name");
            return (Criteria) this;
        }

        public Criteria andNameNotIn(List<String> values) {
            addCriterion("name not in", values, "name");
            return (Criteria) this;
        }

        public Criteria andNameBetween(String value1, String value2) {
            addCriterion("name between", value1, value2, "name");
            return (Criteria) this;
        }

        public Criteria andNameNotBetween(String value1, String value2) {
            addCriterion("name not between", value1, value2, "name");
            return (Criteria) this;
        }

        public Criteria andButtonTitleIsNull() {
            addCriterion("button_title is null");
            return (Criteria) this;
        }

        public Criteria andButtonTitleIsNotNull() {
            addCriterion("button_title is not null");
            return (Criteria) this;
        }

        public Criteria andButtonTitleEqualTo(String value) {
            addCriterion("button_title =", value, "buttonTitle");
            return (Criteria) this;
        }

        public Criteria andButtonTitleNotEqualTo(String value) {
            addCriterion("button_title <>", value, "buttonTitle");
            return (Criteria) this;
        }

        public Criteria andButtonTitleGreaterThan(String value) {
            addCriterion("button_title >", value, "buttonTitle");
            return (Criteria) this;
        }

        public Criteria andButtonTitleGreaterThanOrEqualTo(String value) {
            addCriterion("button_title >=", value, "buttonTitle");
            return (Criteria) this;
        }

        public Criteria andButtonTitleLessThan(String value) {
            addCriterion("button_title <", value, "buttonTitle");
            return (Criteria) this;
        }

        public Criteria andButtonTitleLessThanOrEqualTo(String value) {
            addCriterion("button_title <=", value, "buttonTitle");
            return (Criteria) this;
        }

        public Criteria andButtonTitleLike(String value) {
            addCriterion("button_title like", value, "buttonTitle");
            return (Criteria) this;
        }

        public Criteria andButtonTitleNotLike(String value) {
            addCriterion("button_title not like", value, "buttonTitle");
            return (Criteria) this;
        }

        public Criteria andButtonTitleIn(List<String> values) {
            addCriterion("button_title in", values, "buttonTitle");
            return (Criteria) this;
        }

        public Criteria andButtonTitleNotIn(List<String> values) {
            addCriterion("button_title not in", values, "buttonTitle");
            return (Criteria) this;
        }

        public Criteria andButtonTitleBetween(String value1, String value2) {
            addCriterion("button_title between", value1, value2, "buttonTitle");
            return (Criteria) this;
        }

        public Criteria andButtonTitleNotBetween(String value1, String value2) {
            addCriterion("button_title not between", value1, value2, "buttonTitle");
            return (Criteria) this;
        }

        public Criteria andButtonTypeIsNull() {
            addCriterion("button_type is null");
            return (Criteria) this;
        }

        public Criteria andButtonTypeIsNotNull() {
            addCriterion("button_type is not null");
            return (Criteria) this;
        }

        public Criteria andButtonTypeEqualTo(Integer value) {
            addCriterion("button_type =", value, "buttonType");
            return (Criteria) this;
        }

        public Criteria andButtonTypeNotEqualTo(Integer value) {
            addCriterion("button_type <>", value, "buttonType");
            return (Criteria) this;
        }

        public Criteria andButtonTypeGreaterThan(Integer value) {
            addCriterion("button_type >", value, "buttonType");
            return (Criteria) this;
        }

        public Criteria andButtonTypeGreaterThanOrEqualTo(Integer value) {
            addCriterion("button_type >=", value, "buttonType");
            return (Criteria) this;
        }

        public Criteria andButtonTypeLessThan(Integer value) {
            addCriterion("button_type <", value, "buttonType");
            return (Criteria) this;
        }

        public Criteria andButtonTypeLessThanOrEqualTo(Integer value) {
            addCriterion("button_type <=", value, "buttonType");
            return (Criteria) this;
        }

        public Criteria andButtonTypeIn(List<Integer> values) {
            addCriterion("button_type in", values, "buttonType");
            return (Criteria) this;
        }

        public Criteria andButtonTypeNotIn(List<Integer> values) {
            addCriterion("button_type not in", values, "buttonType");
            return (Criteria) this;
        }

        public Criteria andButtonTypeBetween(Integer value1, Integer value2) {
            addCriterion("button_type between", value1, value2, "buttonType");
            return (Criteria) this;
        }

        public Criteria andButtonTypeNotBetween(Integer value1, Integer value2) {
            addCriterion("button_type not between", value1, value2, "buttonType");
            return (Criteria) this;
        }

        public Criteria andStatusIsNull() {
            addCriterion("status is null");
            return (Criteria) this;
        }

        public Criteria andStatusIsNotNull() {
            addCriterion("status is not null");
            return (Criteria) this;
        }

        public Criteria andStatusEqualTo(Integer value) {
            addCriterion("status =", value, "status");
            return (Criteria) this;
        }

        public Criteria andStatusNotEqualTo(Integer value) {
            addCriterion("status <>", value, "status");
            return (Criteria) this;
        }

        public Criteria andStatusGreaterThan(Integer value) {
            addCriterion("status >", value, "status");
            return (Criteria) this;
        }

        public Criteria andStatusGreaterThanOrEqualTo(Integer value) {
            addCriterion("status >=", value, "status");
            return (Criteria) this;
        }

        public Criteria andStatusLessThan(Integer value) {
            addCriterion("status <", value, "status");
            return (Criteria) this;
        }

        public Criteria andStatusLessThanOrEqualTo(Integer value) {
            addCriterion("status <=", value, "status");
            return (Criteria) this;
        }

        public Criteria andStatusIn(List<Integer> values) {
            addCriterion("status in", values, "status");
            return (Criteria) this;
        }

        public Criteria andStatusNotIn(List<Integer> values) {
            addCriterion("status not in", values, "status");
            return (Criteria) this;
        }

        public Criteria andStatusBetween(Integer value1, Integer value2) {
            addCriterion("status between", value1, value2, "status");
            return (Criteria) this;
        }

        public Criteria andStatusNotBetween(Integer value1, Integer value2) {
            addCriterion("status not between", value1, value2, "status");
            return (Criteria) this;
        }

        public Criteria andClassIosIsNull() {
            addCriterion("class_ios is null");
            return (Criteria) this;
        }

        public Criteria andClassIosIsNotNull() {
            addCriterion("class_ios is not null");
            return (Criteria) this;
        }

        public Criteria andClassIosEqualTo(String value) {
            addCriterion("class_ios =", value, "classIos");
            return (Criteria) this;
        }

        public Criteria andClassIosNotEqualTo(String value) {
            addCriterion("class_ios <>", value, "classIos");
            return (Criteria) this;
        }

        public Criteria andClassIosGreaterThan(String value) {
            addCriterion("class_ios >", value, "classIos");
            return (Criteria) this;
        }

        public Criteria andClassIosGreaterThanOrEqualTo(String value) {
            addCriterion("class_ios >=", value, "classIos");
            return (Criteria) this;
        }

        public Criteria andClassIosLessThan(String value) {
            addCriterion("class_ios <", value, "classIos");
            return (Criteria) this;
        }

        public Criteria andClassIosLessThanOrEqualTo(String value) {
            addCriterion("class_ios <=", value, "classIos");
            return (Criteria) this;
        }

        public Criteria andClassIosLike(String value) {
            addCriterion("class_ios like", value, "classIos");
            return (Criteria) this;
        }

        public Criteria andClassIosNotLike(String value) {
            addCriterion("class_ios not like", value, "classIos");
            return (Criteria) this;
        }

        public Criteria andClassIosIn(List<String> values) {
            addCriterion("class_ios in", values, "classIos");
            return (Criteria) this;
        }

        public Criteria andClassIosNotIn(List<String> values) {
            addCriterion("class_ios not in", values, "classIos");
            return (Criteria) this;
        }

        public Criteria andClassIosBetween(String value1, String value2) {
            addCriterion("class_ios between", value1, value2, "classIos");
            return (Criteria) this;
        }

        public Criteria andClassIosNotBetween(String value1, String value2) {
            addCriterion("class_ios not between", value1, value2, "classIos");
            return (Criteria) this;
        }

        public Criteria andPropertyKeyIosIsNull() {
            addCriterion("property_key_ios is null");
            return (Criteria) this;
        }

        public Criteria andPropertyKeyIosIsNotNull() {
            addCriterion("property_key_ios is not null");
            return (Criteria) this;
        }

        public Criteria andPropertyKeyIosEqualTo(String value) {
            addCriterion("property_key_ios =", value, "propertyKeyIos");
            return (Criteria) this;
        }

        public Criteria andPropertyKeyIosNotEqualTo(String value) {
            addCriterion("property_key_ios <>", value, "propertyKeyIos");
            return (Criteria) this;
        }

        public Criteria andPropertyKeyIosGreaterThan(String value) {
            addCriterion("property_key_ios >", value, "propertyKeyIos");
            return (Criteria) this;
        }

        public Criteria andPropertyKeyIosGreaterThanOrEqualTo(String value) {
            addCriterion("property_key_ios >=", value, "propertyKeyIos");
            return (Criteria) this;
        }

        public Criteria andPropertyKeyIosLessThan(String value) {
            addCriterion("property_key_ios <", value, "propertyKeyIos");
            return (Criteria) this;
        }

        public Criteria andPropertyKeyIosLessThanOrEqualTo(String value) {
            addCriterion("property_key_ios <=", value, "propertyKeyIos");
            return (Criteria) this;
        }

        public Criteria andPropertyKeyIosLike(String value) {
            addCriterion("property_key_ios like", value, "propertyKeyIos");
            return (Criteria) this;
        }

        public Criteria andPropertyKeyIosNotLike(String value) {
            addCriterion("property_key_ios not like", value, "propertyKeyIos");
            return (Criteria) this;
        }

        public Criteria andPropertyKeyIosIn(List<String> values) {
            addCriterion("property_key_ios in", values, "propertyKeyIos");
            return (Criteria) this;
        }

        public Criteria andPropertyKeyIosNotIn(List<String> values) {
            addCriterion("property_key_ios not in", values, "propertyKeyIos");
            return (Criteria) this;
        }

        public Criteria andPropertyKeyIosBetween(String value1, String value2) {
            addCriterion("property_key_ios between", value1, value2, "propertyKeyIos");
            return (Criteria) this;
        }

        public Criteria andPropertyKeyIosNotBetween(String value1, String value2) {
            addCriterion("property_key_ios not between", value1, value2, "propertyKeyIos");
            return (Criteria) this;
        }

        public Criteria andPropertyValueIosIsNull() {
            addCriterion("property_value_ios is null");
            return (Criteria) this;
        }

        public Criteria andPropertyValueIosIsNotNull() {
            addCriterion("property_value_ios is not null");
            return (Criteria) this;
        }

        public Criteria andPropertyValueIosEqualTo(String value) {
            addCriterion("property_value_ios =", value, "propertyValueIos");
            return (Criteria) this;
        }

        public Criteria andPropertyValueIosNotEqualTo(String value) {
            addCriterion("property_value_ios <>", value, "propertyValueIos");
            return (Criteria) this;
        }

        public Criteria andPropertyValueIosGreaterThan(String value) {
            addCriterion("property_value_ios >", value, "propertyValueIos");
            return (Criteria) this;
        }

        public Criteria andPropertyValueIosGreaterThanOrEqualTo(String value) {
            addCriterion("property_value_ios >=", value, "propertyValueIos");
            return (Criteria) this;
        }

        public Criteria andPropertyValueIosLessThan(String value) {
            addCriterion("property_value_ios <", value, "propertyValueIos");
            return (Criteria) this;
        }

        public Criteria andPropertyValueIosLessThanOrEqualTo(String value) {
            addCriterion("property_value_ios <=", value, "propertyValueIos");
            return (Criteria) this;
        }

        public Criteria andPropertyValueIosLike(String value) {
            addCriterion("property_value_ios like", value, "propertyValueIos");
            return (Criteria) this;
        }

        public Criteria andPropertyValueIosNotLike(String value) {
            addCriterion("property_value_ios not like", value, "propertyValueIos");
            return (Criteria) this;
        }

        public Criteria andPropertyValueIosIn(List<String> values) {
            addCriterion("property_value_ios in", values, "propertyValueIos");
            return (Criteria) this;
        }

        public Criteria andPropertyValueIosNotIn(List<String> values) {
            addCriterion("property_value_ios not in", values, "propertyValueIos");
            return (Criteria) this;
        }

        public Criteria andPropertyValueIosBetween(String value1, String value2) {
            addCriterion("property_value_ios between", value1, value2, "propertyValueIos");
            return (Criteria) this;
        }

        public Criteria andPropertyValueIosNotBetween(String value1, String value2) {
            addCriterion("property_value_ios not between", value1, value2, "propertyValueIos");
            return (Criteria) this;
        }

        public Criteria andClassAndroidIsNull() {
            addCriterion("class_android is null");
            return (Criteria) this;
        }

        public Criteria andClassAndroidIsNotNull() {
            addCriterion("class_android is not null");
            return (Criteria) this;
        }

        public Criteria andClassAndroidEqualTo(String value) {
            addCriterion("class_android =", value, "classAndroid");
            return (Criteria) this;
        }

        public Criteria andClassAndroidNotEqualTo(String value) {
            addCriterion("class_android <>", value, "classAndroid");
            return (Criteria) this;
        }

        public Criteria andClassAndroidGreaterThan(String value) {
            addCriterion("class_android >", value, "classAndroid");
            return (Criteria) this;
        }

        public Criteria andClassAndroidGreaterThanOrEqualTo(String value) {
            addCriterion("class_android >=", value, "classAndroid");
            return (Criteria) this;
        }

        public Criteria andClassAndroidLessThan(String value) {
            addCriterion("class_android <", value, "classAndroid");
            return (Criteria) this;
        }

        public Criteria andClassAndroidLessThanOrEqualTo(String value) {
            addCriterion("class_android <=", value, "classAndroid");
            return (Criteria) this;
        }

        public Criteria andClassAndroidLike(String value) {
            addCriterion("class_android like", value, "classAndroid");
            return (Criteria) this;
        }

        public Criteria andClassAndroidNotLike(String value) {
            addCriterion("class_android not like", value, "classAndroid");
            return (Criteria) this;
        }

        public Criteria andClassAndroidIn(List<String> values) {
            addCriterion("class_android in", values, "classAndroid");
            return (Criteria) this;
        }

        public Criteria andClassAndroidNotIn(List<String> values) {
            addCriterion("class_android not in", values, "classAndroid");
            return (Criteria) this;
        }

        public Criteria andClassAndroidBetween(String value1, String value2) {
            addCriterion("class_android between", value1, value2, "classAndroid");
            return (Criteria) this;
        }

        public Criteria andClassAndroidNotBetween(String value1, String value2) {
            addCriterion("class_android not between", value1, value2, "classAndroid");
            return (Criteria) this;
        }

        public Criteria andPropertyKeyAndroidIsNull() {
            addCriterion("property_key_android is null");
            return (Criteria) this;
        }

        public Criteria andPropertyKeyAndroidIsNotNull() {
            addCriterion("property_key_android is not null");
            return (Criteria) this;
        }

        public Criteria andPropertyKeyAndroidEqualTo(String value) {
            addCriterion("property_key_android =", value, "propertyKeyAndroid");
            return (Criteria) this;
        }

        public Criteria andPropertyKeyAndroidNotEqualTo(String value) {
            addCriterion("property_key_android <>", value, "propertyKeyAndroid");
            return (Criteria) this;
        }

        public Criteria andPropertyKeyAndroidGreaterThan(String value) {
            addCriterion("property_key_android >", value, "propertyKeyAndroid");
            return (Criteria) this;
        }

        public Criteria andPropertyKeyAndroidGreaterThanOrEqualTo(String value) {
            addCriterion("property_key_android >=", value, "propertyKeyAndroid");
            return (Criteria) this;
        }

        public Criteria andPropertyKeyAndroidLessThan(String value) {
            addCriterion("property_key_android <", value, "propertyKeyAndroid");
            return (Criteria) this;
        }

        public Criteria andPropertyKeyAndroidLessThanOrEqualTo(String value) {
            addCriterion("property_key_android <=", value, "propertyKeyAndroid");
            return (Criteria) this;
        }

        public Criteria andPropertyKeyAndroidLike(String value) {
            addCriterion("property_key_android like", value, "propertyKeyAndroid");
            return (Criteria) this;
        }

        public Criteria andPropertyKeyAndroidNotLike(String value) {
            addCriterion("property_key_android not like", value, "propertyKeyAndroid");
            return (Criteria) this;
        }

        public Criteria andPropertyKeyAndroidIn(List<String> values) {
            addCriterion("property_key_android in", values, "propertyKeyAndroid");
            return (Criteria) this;
        }

        public Criteria andPropertyKeyAndroidNotIn(List<String> values) {
            addCriterion("property_key_android not in", values, "propertyKeyAndroid");
            return (Criteria) this;
        }

        public Criteria andPropertyKeyAndroidBetween(String value1, String value2) {
            addCriterion("property_key_android between", value1, value2, "propertyKeyAndroid");
            return (Criteria) this;
        }

        public Criteria andPropertyKeyAndroidNotBetween(String value1, String value2) {
            addCriterion("property_key_android not between", value1, value2, "propertyKeyAndroid");
            return (Criteria) this;
        }

        public Criteria andPropertyValueAndroidIsNull() {
            addCriterion("property_value_android is null");
            return (Criteria) this;
        }

        public Criteria andPropertyValueAndroidIsNotNull() {
            addCriterion("property_value_android is not null");
            return (Criteria) this;
        }

        public Criteria andPropertyValueAndroidEqualTo(String value) {
            addCriterion("property_value_android =", value, "propertyValueAndroid");
            return (Criteria) this;
        }

        public Criteria andPropertyValueAndroidNotEqualTo(String value) {
            addCriterion("property_value_android <>", value, "propertyValueAndroid");
            return (Criteria) this;
        }

        public Criteria andPropertyValueAndroidGreaterThan(String value) {
            addCriterion("property_value_android >", value, "propertyValueAndroid");
            return (Criteria) this;
        }

        public Criteria andPropertyValueAndroidGreaterThanOrEqualTo(String value) {
            addCriterion("property_value_android >=", value, "propertyValueAndroid");
            return (Criteria) this;
        }

        public Criteria andPropertyValueAndroidLessThan(String value) {
            addCriterion("property_value_android <", value, "propertyValueAndroid");
            return (Criteria) this;
        }

        public Criteria andPropertyValueAndroidLessThanOrEqualTo(String value) {
            addCriterion("property_value_android <=", value, "propertyValueAndroid");
            return (Criteria) this;
        }

        public Criteria andPropertyValueAndroidLike(String value) {
            addCriterion("property_value_android like", value, "propertyValueAndroid");
            return (Criteria) this;
        }

        public Criteria andPropertyValueAndroidNotLike(String value) {
            addCriterion("property_value_android not like", value, "propertyValueAndroid");
            return (Criteria) this;
        }

        public Criteria andPropertyValueAndroidIn(List<String> values) {
            addCriterion("property_value_android in", values, "propertyValueAndroid");
            return (Criteria) this;
        }

        public Criteria andPropertyValueAndroidNotIn(List<String> values) {
            addCriterion("property_value_android not in", values, "propertyValueAndroid");
            return (Criteria) this;
        }

        public Criteria andPropertyValueAndroidBetween(String value1, String value2) {
            addCriterion("property_value_android between", value1, value2, "propertyValueAndroid");
            return (Criteria) this;
        }

        public Criteria andPropertyValueAndroidNotBetween(String value1, String value2) {
            addCriterion("property_value_android not between", value1, value2, "propertyValueAndroid");
            return (Criteria) this;
        }

        public Criteria andBigImgIsNull() {
            addCriterion("big_img is null");
            return (Criteria) this;
        }

        public Criteria andBigImgIsNotNull() {
            addCriterion("big_img is not null");
            return (Criteria) this;
        }

        public Criteria andBigImgEqualTo(String value) {
            addCriterion("big_img =", value, "bigImg");
            return (Criteria) this;
        }

        public Criteria andBigImgNotEqualTo(String value) {
            addCriterion("big_img <>", value, "bigImg");
            return (Criteria) this;
        }

        public Criteria andBigImgGreaterThan(String value) {
            addCriterion("big_img >", value, "bigImg");
            return (Criteria) this;
        }

        public Criteria andBigImgGreaterThanOrEqualTo(String value) {
            addCriterion("big_img >=", value, "bigImg");
            return (Criteria) this;
        }

        public Criteria andBigImgLessThan(String value) {
            addCriterion("big_img <", value, "bigImg");
            return (Criteria) this;
        }

        public Criteria andBigImgLessThanOrEqualTo(String value) {
            addCriterion("big_img <=", value, "bigImg");
            return (Criteria) this;
        }

        public Criteria andBigImgLike(String value) {
            addCriterion("big_img like", value, "bigImg");
            return (Criteria) this;
        }

        public Criteria andBigImgNotLike(String value) {
            addCriterion("big_img not like", value, "bigImg");
            return (Criteria) this;
        }

        public Criteria andBigImgIn(List<String> values) {
            addCriterion("big_img in", values, "bigImg");
            return (Criteria) this;
        }

        public Criteria andBigImgNotIn(List<String> values) {
            addCriterion("big_img not in", values, "bigImg");
            return (Criteria) this;
        }

        public Criteria andBigImgBetween(String value1, String value2) {
            addCriterion("big_img between", value1, value2, "bigImg");
            return (Criteria) this;
        }

        public Criteria andBigImgNotBetween(String value1, String value2) {
            addCriterion("big_img not between", value1, value2, "bigImg");
            return (Criteria) this;
        }

        public Criteria andButtonImgIsNull() {
            addCriterion("button_img is null");
            return (Criteria) this;
        }

        public Criteria andButtonImgIsNotNull() {
            addCriterion("button_img is not null");
            return (Criteria) this;
        }

        public Criteria andButtonImgEqualTo(String value) {
            addCriterion("button_img =", value, "buttonImg");
            return (Criteria) this;
        }

        public Criteria andButtonImgNotEqualTo(String value) {
            addCriterion("button_img <>", value, "buttonImg");
            return (Criteria) this;
        }

        public Criteria andButtonImgGreaterThan(String value) {
            addCriterion("button_img >", value, "buttonImg");
            return (Criteria) this;
        }

        public Criteria andButtonImgGreaterThanOrEqualTo(String value) {
            addCriterion("button_img >=", value, "buttonImg");
            return (Criteria) this;
        }

        public Criteria andButtonImgLessThan(String value) {
            addCriterion("button_img <", value, "buttonImg");
            return (Criteria) this;
        }

        public Criteria andButtonImgLessThanOrEqualTo(String value) {
            addCriterion("button_img <=", value, "buttonImg");
            return (Criteria) this;
        }

        public Criteria andButtonImgLike(String value) {
            addCriterion("button_img like", value, "buttonImg");
            return (Criteria) this;
        }

        public Criteria andButtonImgNotLike(String value) {
            addCriterion("button_img not like", value, "buttonImg");
            return (Criteria) this;
        }

        public Criteria andButtonImgIn(List<String> values) {
            addCriterion("button_img in", values, "buttonImg");
            return (Criteria) this;
        }

        public Criteria andButtonImgNotIn(List<String> values) {
            addCriterion("button_img not in", values, "buttonImg");
            return (Criteria) this;
        }

        public Criteria andButtonImgBetween(String value1, String value2) {
            addCriterion("button_img between", value1, value2, "buttonImg");
            return (Criteria) this;
        }

        public Criteria andButtonImgNotBetween(String value1, String value2) {
            addCriterion("button_img not between", value1, value2, "buttonImg");
            return (Criteria) this;
        }

        public Criteria andDistanceIsNull() {
            addCriterion("distance is null");
            return (Criteria) this;
        }

        public Criteria andDistanceIsNotNull() {
            addCriterion("distance is not null");
            return (Criteria) this;
        }

        public Criteria andDistanceEqualTo(Double value) {
            addCriterion("distance =", value, "distance");
            return (Criteria) this;
        }

        public Criteria andDistanceNotEqualTo(Double value) {
            addCriterion("distance <>", value, "distance");
            return (Criteria) this;
        }

        public Criteria andDistanceGreaterThan(Double value) {
            addCriterion("distance >", value, "distance");
            return (Criteria) this;
        }

        public Criteria andDistanceGreaterThanOrEqualTo(Double value) {
            addCriterion("distance >=", value, "distance");
            return (Criteria) this;
        }

        public Criteria andDistanceLessThan(Double value) {
            addCriterion("distance <", value, "distance");
            return (Criteria) this;
        }

        public Criteria andDistanceLessThanOrEqualTo(Double value) {
            addCriterion("distance <=", value, "distance");
            return (Criteria) this;
        }

        public Criteria andDistanceIn(List<Double> values) {
            addCriterion("distance in", values, "distance");
            return (Criteria) this;
        }

        public Criteria andDistanceNotIn(List<Double> values) {
            addCriterion("distance not in", values, "distance");
            return (Criteria) this;
        }

        public Criteria andDistanceBetween(Double value1, Double value2) {
            addCriterion("distance between", value1, value2, "distance");
            return (Criteria) this;
        }

        public Criteria andDistanceNotBetween(Double value1, Double value2) {
            addCriterion("distance not between", value1, value2, "distance");
            return (Criteria) this;
        }

        public Criteria andAndroidJumpTypeIsNull() {
            addCriterion("android_jump_type is null");
            return (Criteria) this;
        }

        public Criteria andAndroidJumpTypeIsNotNull() {
            addCriterion("android_jump_type is not null");
            return (Criteria) this;
        }

        public Criteria andAndroidJumpTypeEqualTo(Integer value) {
            addCriterion("android_jump_type =", value, "androidJumpType");
            return (Criteria) this;
        }

        public Criteria andAndroidJumpTypeNotEqualTo(Integer value) {
            addCriterion("android_jump_type <>", value, "androidJumpType");
            return (Criteria) this;
        }

        public Criteria andAndroidJumpTypeGreaterThan(Integer value) {
            addCriterion("android_jump_type >", value, "androidJumpType");
            return (Criteria) this;
        }

        public Criteria andAndroidJumpTypeGreaterThanOrEqualTo(Integer value) {
            addCriterion("android_jump_type >=", value, "androidJumpType");
            return (Criteria) this;
        }

        public Criteria andAndroidJumpTypeLessThan(Integer value) {
            addCriterion("android_jump_type <", value, "androidJumpType");
            return (Criteria) this;
        }

        public Criteria andAndroidJumpTypeLessThanOrEqualTo(Integer value) {
            addCriterion("android_jump_type <=", value, "androidJumpType");
            return (Criteria) this;
        }

        public Criteria andAndroidJumpTypeIn(List<Integer> values) {
            addCriterion("android_jump_type in", values, "androidJumpType");
            return (Criteria) this;
        }

        public Criteria andAndroidJumpTypeNotIn(List<Integer> values) {
            addCriterion("android_jump_type not in", values, "androidJumpType");
            return (Criteria) this;
        }

        public Criteria andAndroidJumpTypeBetween(Integer value1, Integer value2) {
            addCriterion("android_jump_type between", value1, value2, "androidJumpType");
            return (Criteria) this;
        }

        public Criteria andAndroidJumpTypeNotBetween(Integer value1, Integer value2) {
            addCriterion("android_jump_type not between", value1, value2, "androidJumpType");
            return (Criteria) this;
        }

        public Criteria andAlertTypeIsNull() {
            addCriterion("alert_type is null");
            return (Criteria) this;
        }

        public Criteria andAlertTypeIsNotNull() {
            addCriterion("alert_type is not null");
            return (Criteria) this;
        }

        public Criteria andAlertTypeEqualTo(Integer value) {
            addCriterion("alert_type =", value, "alertType");
            return (Criteria) this;
        }

        public Criteria andAlertTypeNotEqualTo(Integer value) {
            addCriterion("alert_type <>", value, "alertType");
            return (Criteria) this;
        }

        public Criteria andAlertTypeGreaterThan(Integer value) {
            addCriterion("alert_type >", value, "alertType");
            return (Criteria) this;
        }

        public Criteria andAlertTypeGreaterThanOrEqualTo(Integer value) {
            addCriterion("alert_type >=", value, "alertType");
            return (Criteria) this;
        }

        public Criteria andAlertTypeLessThan(Integer value) {
            addCriterion("alert_type <", value, "alertType");
            return (Criteria) this;
        }

        public Criteria andAlertTypeLessThanOrEqualTo(Integer value) {
            addCriterion("alert_type <=", value, "alertType");
            return (Criteria) this;
        }

        public Criteria andAlertTypeIn(List<Integer> values) {
            addCriterion("alert_type in", values, "alertType");
            return (Criteria) this;
        }

        public Criteria andAlertTypeNotIn(List<Integer> values) {
            addCriterion("alert_type not in", values, "alertType");
            return (Criteria) this;
        }

        public Criteria andAlertTypeBetween(Integer value1, Integer value2) {
            addCriterion("alert_type between", value1, value2, "alertType");
            return (Criteria) this;
        }

        public Criteria andAlertTypeNotBetween(Integer value1, Integer value2) {
            addCriterion("alert_type not between", value1, value2, "alertType");
            return (Criteria) this;
        }

        public Criteria andPriorityIsNull() {
            addCriterion("priority is null");
            return (Criteria) this;
        }

        public Criteria andPriorityIsNotNull() {
            addCriterion("priority is not null");
            return (Criteria) this;
        }

        public Criteria andPriorityEqualTo(Integer value) {
            addCriterion("priority =", value, "priority");
            return (Criteria) this;
        }

        public Criteria andPriorityNotEqualTo(Integer value) {
            addCriterion("priority <>", value, "priority");
            return (Criteria) this;
        }

        public Criteria andPriorityGreaterThan(Integer value) {
            addCriterion("priority >", value, "priority");
            return (Criteria) this;
        }

        public Criteria andPriorityGreaterThanOrEqualTo(Integer value) {
            addCriterion("priority >=", value, "priority");
            return (Criteria) this;
        }

        public Criteria andPriorityLessThan(Integer value) {
            addCriterion("priority <", value, "priority");
            return (Criteria) this;
        }

        public Criteria andPriorityLessThanOrEqualTo(Integer value) {
            addCriterion("priority <=", value, "priority");
            return (Criteria) this;
        }

        public Criteria andPriorityIn(List<Integer> values) {
            addCriterion("priority in", values, "priority");
            return (Criteria) this;
        }

        public Criteria andPriorityNotIn(List<Integer> values) {
            addCriterion("priority not in", values, "priority");
            return (Criteria) this;
        }

        public Criteria andPriorityBetween(Integer value1, Integer value2) {
            addCriterion("priority between", value1, value2, "priority");
            return (Criteria) this;
        }

        public Criteria andPriorityNotBetween(Integer value1, Integer value2) {
            addCriterion("priority not between", value1, value2, "priority");
            return (Criteria) this;
        }

        public Criteria andCenterYIsNull() {
            addCriterion("center_y is null");
            return (Criteria) this;
        }

        public Criteria andCenterYIsNotNull() {
            addCriterion("center_y is not null");
            return (Criteria) this;
        }

        public Criteria andCenterYEqualTo(Double value) {
            addCriterion("center_y =", value, "centerY");
            return (Criteria) this;
        }

        public Criteria andCenterYNotEqualTo(Double value) {
            addCriterion("center_y <>", value, "centerY");
            return (Criteria) this;
        }

        public Criteria andCenterYGreaterThan(Double value) {
            addCriterion("center_y >", value, "centerY");
            return (Criteria) this;
        }

        public Criteria andCenterYGreaterThanOrEqualTo(Double value) {
            addCriterion("center_y >=", value, "centerY");
            return (Criteria) this;
        }

        public Criteria andCenterYLessThan(Double value) {
            addCriterion("center_y <", value, "centerY");
            return (Criteria) this;
        }

        public Criteria andCenterYLessThanOrEqualTo(Double value) {
            addCriterion("center_y <=", value, "centerY");
            return (Criteria) this;
        }

        public Criteria andCenterYIn(List<Double> values) {
            addCriterion("center_y in", values, "centerY");
            return (Criteria) this;
        }

        public Criteria andCenterYNotIn(List<Double> values) {
            addCriterion("center_y not in", values, "centerY");
            return (Criteria) this;
        }

        public Criteria andCenterYBetween(Double value1, Double value2) {
            addCriterion("center_y between", value1, value2, "centerY");
            return (Criteria) this;
        }

        public Criteria andCenterYNotBetween(Double value1, Double value2) {
            addCriterion("center_y not between", value1, value2, "centerY");
            return (Criteria) this;
        }

        public Criteria andIsFullScreenIsNull() {
            addCriterion("is_full_screen is null");
            return (Criteria) this;
        }

        public Criteria andIsFullScreenIsNotNull() {
            addCriterion("is_full_screen is not null");
            return (Criteria) this;
        }

        public Criteria andIsFullScreenEqualTo(Integer value) {
            addCriterion("is_full_screen =", value, "isFullScreen");
            return (Criteria) this;
        }

        public Criteria andIsFullScreenNotEqualTo(Integer value) {
            addCriterion("is_full_screen <>", value, "isFullScreen");
            return (Criteria) this;
        }

        public Criteria andIsFullScreenGreaterThan(Integer value) {
            addCriterion("is_full_screen >", value, "isFullScreen");
            return (Criteria) this;
        }

        public Criteria andIsFullScreenGreaterThanOrEqualTo(Integer value) {
            addCriterion("is_full_screen >=", value, "isFullScreen");
            return (Criteria) this;
        }

        public Criteria andIsFullScreenLessThan(Integer value) {
            addCriterion("is_full_screen <", value, "isFullScreen");
            return (Criteria) this;
        }

        public Criteria andIsFullScreenLessThanOrEqualTo(Integer value) {
            addCriterion("is_full_screen <=", value, "isFullScreen");
            return (Criteria) this;
        }

        public Criteria andIsFullScreenIn(List<Integer> values) {
            addCriterion("is_full_screen in", values, "isFullScreen");
            return (Criteria) this;
        }

        public Criteria andIsFullScreenNotIn(List<Integer> values) {
            addCriterion("is_full_screen not in", values, "isFullScreen");
            return (Criteria) this;
        }

        public Criteria andIsFullScreenBetween(Integer value1, Integer value2) {
            addCriterion("is_full_screen between", value1, value2, "isFullScreen");
            return (Criteria) this;
        }

        public Criteria andIsFullScreenNotBetween(Integer value1, Integer value2) {
            addCriterion("is_full_screen not between", value1, value2, "isFullScreen");
            return (Criteria) this;
        }

        public Criteria andIsCloseShowIsNull() {
            addCriterion("is_close_show is null");
            return (Criteria) this;
        }

        public Criteria andIsCloseShowIsNotNull() {
            addCriterion("is_close_show is not null");
            return (Criteria) this;
        }

        public Criteria andIsCloseShowEqualTo(Integer value) {
            addCriterion("is_close_show =", value, "isCloseShow");
            return (Criteria) this;
        }

        public Criteria andIsCloseShowNotEqualTo(Integer value) {
            addCriterion("is_close_show <>", value, "isCloseShow");
            return (Criteria) this;
        }

        public Criteria andIsCloseShowGreaterThan(Integer value) {
            addCriterion("is_close_show >", value, "isCloseShow");
            return (Criteria) this;
        }

        public Criteria andIsCloseShowGreaterThanOrEqualTo(Integer value) {
            addCriterion("is_close_show >=", value, "isCloseShow");
            return (Criteria) this;
        }

        public Criteria andIsCloseShowLessThan(Integer value) {
            addCriterion("is_close_show <", value, "isCloseShow");
            return (Criteria) this;
        }

        public Criteria andIsCloseShowLessThanOrEqualTo(Integer value) {
            addCriterion("is_close_show <=", value, "isCloseShow");
            return (Criteria) this;
        }

        public Criteria andIsCloseShowIn(List<Integer> values) {
            addCriterion("is_close_show in", values, "isCloseShow");
            return (Criteria) this;
        }

        public Criteria andIsCloseShowNotIn(List<Integer> values) {
            addCriterion("is_close_show not in", values, "isCloseShow");
            return (Criteria) this;
        }

        public Criteria andIsCloseShowBetween(Integer value1, Integer value2) {
            addCriterion("is_close_show between", value1, value2, "isCloseShow");
            return (Criteria) this;
        }

        public Criteria andIsCloseShowNotBetween(Integer value1, Integer value2) {
            addCriterion("is_close_show not between", value1, value2, "isCloseShow");
            return (Criteria) this;
        }

        public Criteria andClassIosBigIsNull() {
            addCriterion("class_ios_big is null");
            return (Criteria) this;
        }

        public Criteria andClassIosBigIsNotNull() {
            addCriterion("class_ios_big is not null");
            return (Criteria) this;
        }

        public Criteria andClassIosBigEqualTo(String value) {
            addCriterion("class_ios_big =", value, "classIosBig");
            return (Criteria) this;
        }

        public Criteria andClassIosBigNotEqualTo(String value) {
            addCriterion("class_ios_big <>", value, "classIosBig");
            return (Criteria) this;
        }

        public Criteria andClassIosBigGreaterThan(String value) {
            addCriterion("class_ios_big >", value, "classIosBig");
            return (Criteria) this;
        }

        public Criteria andClassIosBigGreaterThanOrEqualTo(String value) {
            addCriterion("class_ios_big >=", value, "classIosBig");
            return (Criteria) this;
        }

        public Criteria andClassIosBigLessThan(String value) {
            addCriterion("class_ios_big <", value, "classIosBig");
            return (Criteria) this;
        }

        public Criteria andClassIosBigLessThanOrEqualTo(String value) {
            addCriterion("class_ios_big <=", value, "classIosBig");
            return (Criteria) this;
        }

        public Criteria andClassIosBigLike(String value) {
            addCriterion("class_ios_big like", value, "classIosBig");
            return (Criteria) this;
        }

        public Criteria andClassIosBigNotLike(String value) {
            addCriterion("class_ios_big not like", value, "classIosBig");
            return (Criteria) this;
        }

        public Criteria andClassIosBigIn(List<String> values) {
            addCriterion("class_ios_big in", values, "classIosBig");
            return (Criteria) this;
        }

        public Criteria andClassIosBigNotIn(List<String> values) {
            addCriterion("class_ios_big not in", values, "classIosBig");
            return (Criteria) this;
        }

        public Criteria andClassIosBigBetween(String value1, String value2) {
            addCriterion("class_ios_big between", value1, value2, "classIosBig");
            return (Criteria) this;
        }

        public Criteria andClassIosBigNotBetween(String value1, String value2) {
            addCriterion("class_ios_big not between", value1, value2, "classIosBig");
            return (Criteria) this;
        }

        public Criteria andPropertyKeyIosBigIsNull() {
            addCriterion("property_key_ios_big is null");
            return (Criteria) this;
        }

        public Criteria andPropertyKeyIosBigIsNotNull() {
            addCriterion("property_key_ios_big is not null");
            return (Criteria) this;
        }

        public Criteria andPropertyKeyIosBigEqualTo(String value) {
            addCriterion("property_key_ios_big =", value, "propertyKeyIosBig");
            return (Criteria) this;
        }

        public Criteria andPropertyKeyIosBigNotEqualTo(String value) {
            addCriterion("property_key_ios_big <>", value, "propertyKeyIosBig");
            return (Criteria) this;
        }

        public Criteria andPropertyKeyIosBigGreaterThan(String value) {
            addCriterion("property_key_ios_big >", value, "propertyKeyIosBig");
            return (Criteria) this;
        }

        public Criteria andPropertyKeyIosBigGreaterThanOrEqualTo(String value) {
            addCriterion("property_key_ios_big >=", value, "propertyKeyIosBig");
            return (Criteria) this;
        }

        public Criteria andPropertyKeyIosBigLessThan(String value) {
            addCriterion("property_key_ios_big <", value, "propertyKeyIosBig");
            return (Criteria) this;
        }

        public Criteria andPropertyKeyIosBigLessThanOrEqualTo(String value) {
            addCriterion("property_key_ios_big <=", value, "propertyKeyIosBig");
            return (Criteria) this;
        }

        public Criteria andPropertyKeyIosBigLike(String value) {
            addCriterion("property_key_ios_big like", value, "propertyKeyIosBig");
            return (Criteria) this;
        }

        public Criteria andPropertyKeyIosBigNotLike(String value) {
            addCriterion("property_key_ios_big not like", value, "propertyKeyIosBig");
            return (Criteria) this;
        }

        public Criteria andPropertyKeyIosBigIn(List<String> values) {
            addCriterion("property_key_ios_big in", values, "propertyKeyIosBig");
            return (Criteria) this;
        }

        public Criteria andPropertyKeyIosBigNotIn(List<String> values) {
            addCriterion("property_key_ios_big not in", values, "propertyKeyIosBig");
            return (Criteria) this;
        }

        public Criteria andPropertyKeyIosBigBetween(String value1, String value2) {
            addCriterion("property_key_ios_big between", value1, value2, "propertyKeyIosBig");
            return (Criteria) this;
        }

        public Criteria andPropertyKeyIosBigNotBetween(String value1, String value2) {
            addCriterion("property_key_ios_big not between", value1, value2, "propertyKeyIosBig");
            return (Criteria) this;
        }

        public Criteria andPropertyValueIosBigIsNull() {
            addCriterion("property_value_ios_big is null");
            return (Criteria) this;
        }

        public Criteria andPropertyValueIosBigIsNotNull() {
            addCriterion("property_value_ios_big is not null");
            return (Criteria) this;
        }

        public Criteria andPropertyValueIosBigEqualTo(String value) {
            addCriterion("property_value_ios_big =", value, "propertyValueIosBig");
            return (Criteria) this;
        }

        public Criteria andPropertyValueIosBigNotEqualTo(String value) {
            addCriterion("property_value_ios_big <>", value, "propertyValueIosBig");
            return (Criteria) this;
        }

        public Criteria andPropertyValueIosBigGreaterThan(String value) {
            addCriterion("property_value_ios_big >", value, "propertyValueIosBig");
            return (Criteria) this;
        }

        public Criteria andPropertyValueIosBigGreaterThanOrEqualTo(String value) {
            addCriterion("property_value_ios_big >=", value, "propertyValueIosBig");
            return (Criteria) this;
        }

        public Criteria andPropertyValueIosBigLessThan(String value) {
            addCriterion("property_value_ios_big <", value, "propertyValueIosBig");
            return (Criteria) this;
        }

        public Criteria andPropertyValueIosBigLessThanOrEqualTo(String value) {
            addCriterion("property_value_ios_big <=", value, "propertyValueIosBig");
            return (Criteria) this;
        }

        public Criteria andPropertyValueIosBigLike(String value) {
            addCriterion("property_value_ios_big like", value, "propertyValueIosBig");
            return (Criteria) this;
        }

        public Criteria andPropertyValueIosBigNotLike(String value) {
            addCriterion("property_value_ios_big not like", value, "propertyValueIosBig");
            return (Criteria) this;
        }

        public Criteria andPropertyValueIosBigIn(List<String> values) {
            addCriterion("property_value_ios_big in", values, "propertyValueIosBig");
            return (Criteria) this;
        }

        public Criteria andPropertyValueIosBigNotIn(List<String> values) {
            addCriterion("property_value_ios_big not in", values, "propertyValueIosBig");
            return (Criteria) this;
        }

        public Criteria andPropertyValueIosBigBetween(String value1, String value2) {
            addCriterion("property_value_ios_big between", value1, value2, "propertyValueIosBig");
            return (Criteria) this;
        }

        public Criteria andPropertyValueIosBigNotBetween(String value1, String value2) {
            addCriterion("property_value_ios_big not between", value1, value2, "propertyValueIosBig");
            return (Criteria) this;
        }

        public Criteria andClassAndroidBigIsNull() {
            addCriterion("class_android_big is null");
            return (Criteria) this;
        }

        public Criteria andClassAndroidBigIsNotNull() {
            addCriterion("class_android_big is not null");
            return (Criteria) this;
        }

        public Criteria andClassAndroidBigEqualTo(String value) {
            addCriterion("class_android_big =", value, "classAndroidBig");
            return (Criteria) this;
        }

        public Criteria andClassAndroidBigNotEqualTo(String value) {
            addCriterion("class_android_big <>", value, "classAndroidBig");
            return (Criteria) this;
        }

        public Criteria andClassAndroidBigGreaterThan(String value) {
            addCriterion("class_android_big >", value, "classAndroidBig");
            return (Criteria) this;
        }

        public Criteria andClassAndroidBigGreaterThanOrEqualTo(String value) {
            addCriterion("class_android_big >=", value, "classAndroidBig");
            return (Criteria) this;
        }

        public Criteria andClassAndroidBigLessThan(String value) {
            addCriterion("class_android_big <", value, "classAndroidBig");
            return (Criteria) this;
        }

        public Criteria andClassAndroidBigLessThanOrEqualTo(String value) {
            addCriterion("class_android_big <=", value, "classAndroidBig");
            return (Criteria) this;
        }

        public Criteria andClassAndroidBigLike(String value) {
            addCriterion("class_android_big like", value, "classAndroidBig");
            return (Criteria) this;
        }

        public Criteria andClassAndroidBigNotLike(String value) {
            addCriterion("class_android_big not like", value, "classAndroidBig");
            return (Criteria) this;
        }

        public Criteria andClassAndroidBigIn(List<String> values) {
            addCriterion("class_android_big in", values, "classAndroidBig");
            return (Criteria) this;
        }

        public Criteria andClassAndroidBigNotIn(List<String> values) {
            addCriterion("class_android_big not in", values, "classAndroidBig");
            return (Criteria) this;
        }

        public Criteria andClassAndroidBigBetween(String value1, String value2) {
            addCriterion("class_android_big between", value1, value2, "classAndroidBig");
            return (Criteria) this;
        }

        public Criteria andClassAndroidBigNotBetween(String value1, String value2) {
            addCriterion("class_android_big not between", value1, value2, "classAndroidBig");
            return (Criteria) this;
        }

        public Criteria andPropertyKeyAndroidBigIsNull() {
            addCriterion("property_key_android_big is null");
            return (Criteria) this;
        }

        public Criteria andPropertyKeyAndroidBigIsNotNull() {
            addCriterion("property_key_android_big is not null");
            return (Criteria) this;
        }

        public Criteria andPropertyKeyAndroidBigEqualTo(String value) {
            addCriterion("property_key_android_big =", value, "propertyKeyAndroidBig");
            return (Criteria) this;
        }

        public Criteria andPropertyKeyAndroidBigNotEqualTo(String value) {
            addCriterion("property_key_android_big <>", value, "propertyKeyAndroidBig");
            return (Criteria) this;
        }

        public Criteria andPropertyKeyAndroidBigGreaterThan(String value) {
            addCriterion("property_key_android_big >", value, "propertyKeyAndroidBig");
            return (Criteria) this;
        }

        public Criteria andPropertyKeyAndroidBigGreaterThanOrEqualTo(String value) {
            addCriterion("property_key_android_big >=", value, "propertyKeyAndroidBig");
            return (Criteria) this;
        }

        public Criteria andPropertyKeyAndroidBigLessThan(String value) {
            addCriterion("property_key_android_big <", value, "propertyKeyAndroidBig");
            return (Criteria) this;
        }

        public Criteria andPropertyKeyAndroidBigLessThanOrEqualTo(String value) {
            addCriterion("property_key_android_big <=", value, "propertyKeyAndroidBig");
            return (Criteria) this;
        }

        public Criteria andPropertyKeyAndroidBigLike(String value) {
            addCriterion("property_key_android_big like", value, "propertyKeyAndroidBig");
            return (Criteria) this;
        }

        public Criteria andPropertyKeyAndroidBigNotLike(String value) {
            addCriterion("property_key_android_big not like", value, "propertyKeyAndroidBig");
            return (Criteria) this;
        }

        public Criteria andPropertyKeyAndroidBigIn(List<String> values) {
            addCriterion("property_key_android_big in", values, "propertyKeyAndroidBig");
            return (Criteria) this;
        }

        public Criteria andPropertyKeyAndroidBigNotIn(List<String> values) {
            addCriterion("property_key_android_big not in", values, "propertyKeyAndroidBig");
            return (Criteria) this;
        }

        public Criteria andPropertyKeyAndroidBigBetween(String value1, String value2) {
            addCriterion("property_key_android_big between", value1, value2, "propertyKeyAndroidBig");
            return (Criteria) this;
        }

        public Criteria andPropertyKeyAndroidBigNotBetween(String value1, String value2) {
            addCriterion("property_key_android_big not between", value1, value2, "propertyKeyAndroidBig");
            return (Criteria) this;
        }

        public Criteria andPropertyValueAndroidBigIsNull() {
            addCriterion("property_value_android_big is null");
            return (Criteria) this;
        }

        public Criteria andPropertyValueAndroidBigIsNotNull() {
            addCriterion("property_value_android_big is not null");
            return (Criteria) this;
        }

        public Criteria andPropertyValueAndroidBigEqualTo(String value) {
            addCriterion("property_value_android_big =", value, "propertyValueAndroidBig");
            return (Criteria) this;
        }

        public Criteria andPropertyValueAndroidBigNotEqualTo(String value) {
            addCriterion("property_value_android_big <>", value, "propertyValueAndroidBig");
            return (Criteria) this;
        }

        public Criteria andPropertyValueAndroidBigGreaterThan(String value) {
            addCriterion("property_value_android_big >", value, "propertyValueAndroidBig");
            return (Criteria) this;
        }

        public Criteria andPropertyValueAndroidBigGreaterThanOrEqualTo(String value) {
            addCriterion("property_value_android_big >=", value, "propertyValueAndroidBig");
            return (Criteria) this;
        }

        public Criteria andPropertyValueAndroidBigLessThan(String value) {
            addCriterion("property_value_android_big <", value, "propertyValueAndroidBig");
            return (Criteria) this;
        }

        public Criteria andPropertyValueAndroidBigLessThanOrEqualTo(String value) {
            addCriterion("property_value_android_big <=", value, "propertyValueAndroidBig");
            return (Criteria) this;
        }

        public Criteria andPropertyValueAndroidBigLike(String value) {
            addCriterion("property_value_android_big like", value, "propertyValueAndroidBig");
            return (Criteria) this;
        }

        public Criteria andPropertyValueAndroidBigNotLike(String value) {
            addCriterion("property_value_android_big not like", value, "propertyValueAndroidBig");
            return (Criteria) this;
        }

        public Criteria andPropertyValueAndroidBigIn(List<String> values) {
            addCriterion("property_value_android_big in", values, "propertyValueAndroidBig");
            return (Criteria) this;
        }

        public Criteria andPropertyValueAndroidBigNotIn(List<String> values) {
            addCriterion("property_value_android_big not in", values, "propertyValueAndroidBig");
            return (Criteria) this;
        }

        public Criteria andPropertyValueAndroidBigBetween(String value1, String value2) {
            addCriterion("property_value_android_big between", value1, value2, "propertyValueAndroidBig");
            return (Criteria) this;
        }

        public Criteria andPropertyValueAndroidBigNotBetween(String value1, String value2) {
            addCriterion("property_value_android_big not between", value1, value2, "propertyValueAndroidBig");
            return (Criteria) this;
        }
    }

    /**
     * 类注释
     * Criteria
     * 数据库表：alert_prompt
     *
     * @mbggenerated
     */
    public static class Criteria extends GeneratedCriteria {

        protected Criteria() {
            super();
        }
    }

    /**
     * 类注释
     * Criterion
     * 数据库表：alert_prompt
     */
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