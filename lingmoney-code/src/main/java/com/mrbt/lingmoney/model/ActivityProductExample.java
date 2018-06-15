package com.mrbt.lingmoney.model;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class ActivityProductExample {
    /**
     * 排序字段,activity_product
     */
    protected String orderByClause;

    /**
     * 是否过滤重复数据,activity_product
     */
    protected boolean distinct;

    /**
     * 查询条件实例,activity_product
     */
    protected List<Criteria> oredCriteria;

    /**
     * 第一个返回记录行的偏移量,activity_product
     */
    protected int limitStart = -1;

    /**
     * 返回记录行的最大数目,activity_product
     */
    protected int limitEnd = -1;

    /**
     *  构造查询条件,activity_product
     */
    public ActivityProductExample() {
        oredCriteria = new ArrayList<Criteria>();
    }

    /**
     *  设置排序字段,activity_product
     * @param orderByClause 排序字段
     */
    public void setOrderByClause(String orderByClause) {
        this.orderByClause = orderByClause;
    }

    /**
     *  获取排序字段,activity_product
     */
    public String getOrderByClause() {
        return orderByClause;
    }

    /**
     *  设置过滤重复数据,activity_product
     * @param distinct 是否过滤重复数据
     */
    public void setDistinct(boolean distinct) {
        this.distinct = distinct;
    }

    /**
     *  是否过滤重复数据,activity_product
     */
    public boolean isDistinct() {
        return distinct;
    }

    /**
     *  获取当前的查询条件实例,activity_product
     */
    public List<Criteria> getOredCriteria() {
        return oredCriteria;
    }

    /**
     *  增加或者的查询条件,用于构建或者查询,activity_product
     * @param criteria 过滤条件实例
     */
    public void or(Criteria criteria) {
        oredCriteria.add(criteria);
    }

    /**
     *  创建一个新的或者查询条件,activity_product
     */
    public Criteria or() {
        Criteria criteria = createCriteriaInternal();
        oredCriteria.add(criteria);
        return criteria;
    }

    /**
     *  创建一个查询条件,activity_product
     */
    public Criteria createCriteria() {
        Criteria criteria = createCriteriaInternal();
        if (oredCriteria.size() == 0) {
            oredCriteria.add(criteria);
        }
        return criteria;
    }

    /**
     *  内部构建查询条件对象,activity_product
     */
    protected Criteria createCriteriaInternal() {
        Criteria criteria = new Criteria();
        return criteria;
    }

    /**
     *  清除查询条件,activity_product
     */
    public void clear() {
        oredCriteria.clear();
        orderByClause = null;
        distinct = false;
    }

    /**
     *  设置第一个返回记录行的偏移量,activity_product
     * @param limitStart 第一个返回记录行的偏移量
     */
    public void setLimitStart(int limitStart) {
        this.limitStart=limitStart;
    }

    /**
     *  获取第一个返回记录行的偏移量,activity_product
     */
    public int getLimitStart() {
        return limitStart;
    }

    /**
     *  设置返回记录行的最大数目,activity_product
     * @param limitEnd 返回记录行的最大数目
     */
    public void setLimitEnd(int limitEnd) {
        this.limitEnd=limitEnd;
    }

    /**
     *  获取返回记录行的最大数目,activity_product
     */
    public int getLimitEnd() {
        return limitEnd;
    }

    /**
     * 类注释
     * GeneratedCriteria
     * 数据库表：activity_product
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

        public Criteria andIdEqualTo(Integer value) {
            addCriterion("id =", value, "id");
            return (Criteria) this;
        }

        public Criteria andIdNotEqualTo(Integer value) {
            addCriterion("id <>", value, "id");
            return (Criteria) this;
        }

        public Criteria andIdGreaterThan(Integer value) {
            addCriterion("id >", value, "id");
            return (Criteria) this;
        }

        public Criteria andIdGreaterThanOrEqualTo(Integer value) {
            addCriterion("id >=", value, "id");
            return (Criteria) this;
        }

        public Criteria andIdLessThan(Integer value) {
            addCriterion("id <", value, "id");
            return (Criteria) this;
        }

        public Criteria andIdLessThanOrEqualTo(Integer value) {
            addCriterion("id <=", value, "id");
            return (Criteria) this;
        }

        public Criteria andIdIn(List<Integer> values) {
            addCriterion("id in", values, "id");
            return (Criteria) this;
        }

        public Criteria andIdNotIn(List<Integer> values) {
            addCriterion("id not in", values, "id");
            return (Criteria) this;
        }

        public Criteria andIdBetween(Integer value1, Integer value2) {
            addCriterion("id between", value1, value2, "id");
            return (Criteria) this;
        }

        public Criteria andIdNotBetween(Integer value1, Integer value2) {
            addCriterion("id not between", value1, value2, "id");
            return (Criteria) this;
        }

        public Criteria andPIdIsNull() {
            addCriterion("p_id is null");
            return (Criteria) this;
        }

        public Criteria andPIdIsNotNull() {
            addCriterion("p_id is not null");
            return (Criteria) this;
        }

        public Criteria andPIdEqualTo(String value) {
            addCriterion("p_id =", value, "pId");
            return (Criteria) this;
        }

        public Criteria andPIdNotEqualTo(String value) {
            addCriterion("p_id <>", value, "pId");
            return (Criteria) this;
        }

        public Criteria andPIdGreaterThan(String value) {
            addCriterion("p_id >", value, "pId");
            return (Criteria) this;
        }

        public Criteria andPIdGreaterThanOrEqualTo(String value) {
            addCriterion("p_id >=", value, "pId");
            return (Criteria) this;
        }

        public Criteria andPIdLessThan(String value) {
            addCriterion("p_id <", value, "pId");
            return (Criteria) this;
        }

        public Criteria andPIdLessThanOrEqualTo(String value) {
            addCriterion("p_id <=", value, "pId");
            return (Criteria) this;
        }

        public Criteria andPIdLike(String value) {
            addCriterion("p_id like", value, "pId");
            return (Criteria) this;
        }

        public Criteria andPIdNotLike(String value) {
            addCriterion("p_id not like", value, "pId");
            return (Criteria) this;
        }

        public Criteria andPIdIn(List<String> values) {
            addCriterion("p_id in", values, "pId");
            return (Criteria) this;
        }

        public Criteria andPIdNotIn(List<String> values) {
            addCriterion("p_id not in", values, "pId");
            return (Criteria) this;
        }

        public Criteria andPIdBetween(String value1, String value2) {
            addCriterion("p_id between", value1, value2, "pId");
            return (Criteria) this;
        }

        public Criteria andPIdNotBetween(String value1, String value2) {
            addCriterion("p_id not between", value1, value2, "pId");
            return (Criteria) this;
        }

        public Criteria andActNameIsNull() {
            addCriterion("act_name is null");
            return (Criteria) this;
        }

        public Criteria andActNameIsNotNull() {
            addCriterion("act_name is not null");
            return (Criteria) this;
        }

        public Criteria andActNameEqualTo(String value) {
            addCriterion("act_name =", value, "actName");
            return (Criteria) this;
        }

        public Criteria andActNameNotEqualTo(String value) {
            addCriterion("act_name <>", value, "actName");
            return (Criteria) this;
        }

        public Criteria andActNameGreaterThan(String value) {
            addCriterion("act_name >", value, "actName");
            return (Criteria) this;
        }

        public Criteria andActNameGreaterThanOrEqualTo(String value) {
            addCriterion("act_name >=", value, "actName");
            return (Criteria) this;
        }

        public Criteria andActNameLessThan(String value) {
            addCriterion("act_name <", value, "actName");
            return (Criteria) this;
        }

        public Criteria andActNameLessThanOrEqualTo(String value) {
            addCriterion("act_name <=", value, "actName");
            return (Criteria) this;
        }

        public Criteria andActNameLike(String value) {
            addCriterion("act_name like", value, "actName");
            return (Criteria) this;
        }

        public Criteria andActNameNotLike(String value) {
            addCriterion("act_name not like", value, "actName");
            return (Criteria) this;
        }

        public Criteria andActNameIn(List<String> values) {
            addCriterion("act_name in", values, "actName");
            return (Criteria) this;
        }

        public Criteria andActNameNotIn(List<String> values) {
            addCriterion("act_name not in", values, "actName");
            return (Criteria) this;
        }

        public Criteria andActNameBetween(String value1, String value2) {
            addCriterion("act_name between", value1, value2, "actName");
            return (Criteria) this;
        }

        public Criteria andActNameNotBetween(String value1, String value2) {
            addCriterion("act_name not between", value1, value2, "actName");
            return (Criteria) this;
        }

        public Criteria andActTitleIsNull() {
            addCriterion("act_title is null");
            return (Criteria) this;
        }

        public Criteria andActTitleIsNotNull() {
            addCriterion("act_title is not null");
            return (Criteria) this;
        }

        public Criteria andActTitleEqualTo(String value) {
            addCriterion("act_title =", value, "actTitle");
            return (Criteria) this;
        }

        public Criteria andActTitleNotEqualTo(String value) {
            addCriterion("act_title <>", value, "actTitle");
            return (Criteria) this;
        }

        public Criteria andActTitleGreaterThan(String value) {
            addCriterion("act_title >", value, "actTitle");
            return (Criteria) this;
        }

        public Criteria andActTitleGreaterThanOrEqualTo(String value) {
            addCriterion("act_title >=", value, "actTitle");
            return (Criteria) this;
        }

        public Criteria andActTitleLessThan(String value) {
            addCriterion("act_title <", value, "actTitle");
            return (Criteria) this;
        }

        public Criteria andActTitleLessThanOrEqualTo(String value) {
            addCriterion("act_title <=", value, "actTitle");
            return (Criteria) this;
        }

        public Criteria andActTitleLike(String value) {
            addCriterion("act_title like", value, "actTitle");
            return (Criteria) this;
        }

        public Criteria andActTitleNotLike(String value) {
            addCriterion("act_title not like", value, "actTitle");
            return (Criteria) this;
        }

        public Criteria andActTitleIn(List<String> values) {
            addCriterion("act_title in", values, "actTitle");
            return (Criteria) this;
        }

        public Criteria andActTitleNotIn(List<String> values) {
            addCriterion("act_title not in", values, "actTitle");
            return (Criteria) this;
        }

        public Criteria andActTitleBetween(String value1, String value2) {
            addCriterion("act_title between", value1, value2, "actTitle");
            return (Criteria) this;
        }

        public Criteria andActTitleNotBetween(String value1, String value2) {
            addCriterion("act_title not between", value1, value2, "actTitle");
            return (Criteria) this;
        }

        public Criteria andActUrlIsNull() {
            addCriterion("act_url is null");
            return (Criteria) this;
        }

        public Criteria andActUrlIsNotNull() {
            addCriterion("act_url is not null");
            return (Criteria) this;
        }

        public Criteria andActUrlEqualTo(String value) {
            addCriterion("act_url =", value, "actUrl");
            return (Criteria) this;
        }

        public Criteria andActUrlNotEqualTo(String value) {
            addCriterion("act_url <>", value, "actUrl");
            return (Criteria) this;
        }

        public Criteria andActUrlGreaterThan(String value) {
            addCriterion("act_url >", value, "actUrl");
            return (Criteria) this;
        }

        public Criteria andActUrlGreaterThanOrEqualTo(String value) {
            addCriterion("act_url >=", value, "actUrl");
            return (Criteria) this;
        }

        public Criteria andActUrlLessThan(String value) {
            addCriterion("act_url <", value, "actUrl");
            return (Criteria) this;
        }

        public Criteria andActUrlLessThanOrEqualTo(String value) {
            addCriterion("act_url <=", value, "actUrl");
            return (Criteria) this;
        }

        public Criteria andActUrlLike(String value) {
            addCriterion("act_url like", value, "actUrl");
            return (Criteria) this;
        }

        public Criteria andActUrlNotLike(String value) {
            addCriterion("act_url not like", value, "actUrl");
            return (Criteria) this;
        }

        public Criteria andActUrlIn(List<String> values) {
            addCriterion("act_url in", values, "actUrl");
            return (Criteria) this;
        }

        public Criteria andActUrlNotIn(List<String> values) {
            addCriterion("act_url not in", values, "actUrl");
            return (Criteria) this;
        }

        public Criteria andActUrlBetween(String value1, String value2) {
            addCriterion("act_url between", value1, value2, "actUrl");
            return (Criteria) this;
        }

        public Criteria andActUrlNotBetween(String value1, String value2) {
            addCriterion("act_url not between", value1, value2, "actUrl");
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

        public Criteria andStartDateIsNull() {
            addCriterion("start_date is null");
            return (Criteria) this;
        }

        public Criteria andStartDateIsNotNull() {
            addCriterion("start_date is not null");
            return (Criteria) this;
        }

        public Criteria andStartDateEqualTo(Date value) {
            addCriterion("start_date =", value, "startDate");
            return (Criteria) this;
        }

        public Criteria andStartDateNotEqualTo(Date value) {
            addCriterion("start_date <>", value, "startDate");
            return (Criteria) this;
        }

        public Criteria andStartDateGreaterThan(Date value) {
            addCriterion("start_date >", value, "startDate");
            return (Criteria) this;
        }

        public Criteria andStartDateGreaterThanOrEqualTo(Date value) {
            addCriterion("start_date >=", value, "startDate");
            return (Criteria) this;
        }

        public Criteria andStartDateLessThan(Date value) {
            addCriterion("start_date <", value, "startDate");
            return (Criteria) this;
        }

        public Criteria andStartDateLessThanOrEqualTo(Date value) {
            addCriterion("start_date <=", value, "startDate");
            return (Criteria) this;
        }

        public Criteria andStartDateIn(List<Date> values) {
            addCriterion("start_date in", values, "startDate");
            return (Criteria) this;
        }

        public Criteria andStartDateNotIn(List<Date> values) {
            addCriterion("start_date not in", values, "startDate");
            return (Criteria) this;
        }

        public Criteria andStartDateBetween(Date value1, Date value2) {
            addCriterion("start_date between", value1, value2, "startDate");
            return (Criteria) this;
        }

        public Criteria andStartDateNotBetween(Date value1, Date value2) {
            addCriterion("start_date not between", value1, value2, "startDate");
            return (Criteria) this;
        }

        public Criteria andEndDateIsNull() {
            addCriterion("end_date is null");
            return (Criteria) this;
        }

        public Criteria andEndDateIsNotNull() {
            addCriterion("end_date is not null");
            return (Criteria) this;
        }

        public Criteria andEndDateEqualTo(Date value) {
            addCriterion("end_date =", value, "endDate");
            return (Criteria) this;
        }

        public Criteria andEndDateNotEqualTo(Date value) {
            addCriterion("end_date <>", value, "endDate");
            return (Criteria) this;
        }

        public Criteria andEndDateGreaterThan(Date value) {
            addCriterion("end_date >", value, "endDate");
            return (Criteria) this;
        }

        public Criteria andEndDateGreaterThanOrEqualTo(Date value) {
            addCriterion("end_date >=", value, "endDate");
            return (Criteria) this;
        }

        public Criteria andEndDateLessThan(Date value) {
            addCriterion("end_date <", value, "endDate");
            return (Criteria) this;
        }

        public Criteria andEndDateLessThanOrEqualTo(Date value) {
            addCriterion("end_date <=", value, "endDate");
            return (Criteria) this;
        }

        public Criteria andEndDateIn(List<Date> values) {
            addCriterion("end_date in", values, "endDate");
            return (Criteria) this;
        }

        public Criteria andEndDateNotIn(List<Date> values) {
            addCriterion("end_date not in", values, "endDate");
            return (Criteria) this;
        }

        public Criteria andEndDateBetween(Date value1, Date value2) {
            addCriterion("end_date between", value1, value2, "endDate");
            return (Criteria) this;
        }

        public Criteria andEndDateNotBetween(Date value1, Date value2) {
            addCriterion("end_date not between", value1, value2, "endDate");
            return (Criteria) this;
        }

        public Criteria andCreateTimeIsNull() {
            addCriterion("create_time is null");
            return (Criteria) this;
        }

        public Criteria andCreateTimeIsNotNull() {
            addCriterion("create_time is not null");
            return (Criteria) this;
        }

        public Criteria andCreateTimeEqualTo(Date value) {
            addCriterion("create_time =", value, "createTime");
            return (Criteria) this;
        }

        public Criteria andCreateTimeNotEqualTo(Date value) {
            addCriterion("create_time <>", value, "createTime");
            return (Criteria) this;
        }

        public Criteria andCreateTimeGreaterThan(Date value) {
            addCriterion("create_time >", value, "createTime");
            return (Criteria) this;
        }

        public Criteria andCreateTimeGreaterThanOrEqualTo(Date value) {
            addCriterion("create_time >=", value, "createTime");
            return (Criteria) this;
        }

        public Criteria andCreateTimeLessThan(Date value) {
            addCriterion("create_time <", value, "createTime");
            return (Criteria) this;
        }

        public Criteria andCreateTimeLessThanOrEqualTo(Date value) {
            addCriterion("create_time <=", value, "createTime");
            return (Criteria) this;
        }

        public Criteria andCreateTimeIn(List<Date> values) {
            addCriterion("create_time in", values, "createTime");
            return (Criteria) this;
        }

        public Criteria andCreateTimeNotIn(List<Date> values) {
            addCriterion("create_time not in", values, "createTime");
            return (Criteria) this;
        }

        public Criteria andCreateTimeBetween(Date value1, Date value2) {
            addCriterion("create_time between", value1, value2, "createTime");
            return (Criteria) this;
        }

        public Criteria andCreateTimeNotBetween(Date value1, Date value2) {
            addCriterion("create_time not between", value1, value2, "createTime");
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

        public Criteria andActCenterIsNull() {
            addCriterion("act_center is null");
            return (Criteria) this;
        }

        public Criteria andActCenterIsNotNull() {
            addCriterion("act_center is not null");
            return (Criteria) this;
        }

        public Criteria andActCenterEqualTo(Integer value) {
            addCriterion("act_center =", value, "actCenter");
            return (Criteria) this;
        }

        public Criteria andActCenterNotEqualTo(Integer value) {
            addCriterion("act_center <>", value, "actCenter");
            return (Criteria) this;
        }

        public Criteria andActCenterGreaterThan(Integer value) {
            addCriterion("act_center >", value, "actCenter");
            return (Criteria) this;
        }

        public Criteria andActCenterGreaterThanOrEqualTo(Integer value) {
            addCriterion("act_center >=", value, "actCenter");
            return (Criteria) this;
        }

        public Criteria andActCenterLessThan(Integer value) {
            addCriterion("act_center <", value, "actCenter");
            return (Criteria) this;
        }

        public Criteria andActCenterLessThanOrEqualTo(Integer value) {
            addCriterion("act_center <=", value, "actCenter");
            return (Criteria) this;
        }

        public Criteria andActCenterIn(List<Integer> values) {
            addCriterion("act_center in", values, "actCenter");
            return (Criteria) this;
        }

        public Criteria andActCenterNotIn(List<Integer> values) {
            addCriterion("act_center not in", values, "actCenter");
            return (Criteria) this;
        }

        public Criteria andActCenterBetween(Integer value1, Integer value2) {
            addCriterion("act_center between", value1, value2, "actCenter");
            return (Criteria) this;
        }

        public Criteria andActCenterNotBetween(Integer value1, Integer value2) {
            addCriterion("act_center not between", value1, value2, "actCenter");
            return (Criteria) this;
        }

        public Criteria andActTypeIsNull() {
            addCriterion("act_type is null");
            return (Criteria) this;
        }

        public Criteria andActTypeIsNotNull() {
            addCriterion("act_type is not null");
            return (Criteria) this;
        }

        public Criteria andActTypeEqualTo(Integer value) {
            addCriterion("act_type =", value, "actType");
            return (Criteria) this;
        }

        public Criteria andActTypeNotEqualTo(Integer value) {
            addCriterion("act_type <>", value, "actType");
            return (Criteria) this;
        }

        public Criteria andActTypeGreaterThan(Integer value) {
            addCriterion("act_type >", value, "actType");
            return (Criteria) this;
        }

        public Criteria andActTypeGreaterThanOrEqualTo(Integer value) {
            addCriterion("act_type >=", value, "actType");
            return (Criteria) this;
        }

        public Criteria andActTypeLessThan(Integer value) {
            addCriterion("act_type <", value, "actType");
            return (Criteria) this;
        }

        public Criteria andActTypeLessThanOrEqualTo(Integer value) {
            addCriterion("act_type <=", value, "actType");
            return (Criteria) this;
        }

        public Criteria andActTypeIn(List<Integer> values) {
            addCriterion("act_type in", values, "actType");
            return (Criteria) this;
        }

        public Criteria andActTypeNotIn(List<Integer> values) {
            addCriterion("act_type not in", values, "actType");
            return (Criteria) this;
        }

        public Criteria andActTypeBetween(Integer value1, Integer value2) {
            addCriterion("act_type between", value1, value2, "actType");
            return (Criteria) this;
        }

        public Criteria andActTypeNotBetween(Integer value1, Integer value2) {
            addCriterion("act_type not between", value1, value2, "actType");
            return (Criteria) this;
        }

        public Criteria andLinkUrlPcIsNull() {
            addCriterion("link_url_pc is null");
            return (Criteria) this;
        }

        public Criteria andLinkUrlPcIsNotNull() {
            addCriterion("link_url_pc is not null");
            return (Criteria) this;
        }

        public Criteria andLinkUrlPcEqualTo(String value) {
            addCriterion("link_url_pc =", value, "linkUrlPc");
            return (Criteria) this;
        }

        public Criteria andLinkUrlPcNotEqualTo(String value) {
            addCriterion("link_url_pc <>", value, "linkUrlPc");
            return (Criteria) this;
        }

        public Criteria andLinkUrlPcGreaterThan(String value) {
            addCriterion("link_url_pc >", value, "linkUrlPc");
            return (Criteria) this;
        }

        public Criteria andLinkUrlPcGreaterThanOrEqualTo(String value) {
            addCriterion("link_url_pc >=", value, "linkUrlPc");
            return (Criteria) this;
        }

        public Criteria andLinkUrlPcLessThan(String value) {
            addCriterion("link_url_pc <", value, "linkUrlPc");
            return (Criteria) this;
        }

        public Criteria andLinkUrlPcLessThanOrEqualTo(String value) {
            addCriterion("link_url_pc <=", value, "linkUrlPc");
            return (Criteria) this;
        }

        public Criteria andLinkUrlPcLike(String value) {
            addCriterion("link_url_pc like", value, "linkUrlPc");
            return (Criteria) this;
        }

        public Criteria andLinkUrlPcNotLike(String value) {
            addCriterion("link_url_pc not like", value, "linkUrlPc");
            return (Criteria) this;
        }

        public Criteria andLinkUrlPcIn(List<String> values) {
            addCriterion("link_url_pc in", values, "linkUrlPc");
            return (Criteria) this;
        }

        public Criteria andLinkUrlPcNotIn(List<String> values) {
            addCriterion("link_url_pc not in", values, "linkUrlPc");
            return (Criteria) this;
        }

        public Criteria andLinkUrlPcBetween(String value1, String value2) {
            addCriterion("link_url_pc between", value1, value2, "linkUrlPc");
            return (Criteria) this;
        }

        public Criteria andLinkUrlPcNotBetween(String value1, String value2) {
            addCriterion("link_url_pc not between", value1, value2, "linkUrlPc");
            return (Criteria) this;
        }

        public Criteria andLinkUrlAppIsNull() {
            addCriterion("link_url_app is null");
            return (Criteria) this;
        }

        public Criteria andLinkUrlAppIsNotNull() {
            addCriterion("link_url_app is not null");
            return (Criteria) this;
        }

        public Criteria andLinkUrlAppEqualTo(String value) {
            addCriterion("link_url_app =", value, "linkUrlApp");
            return (Criteria) this;
        }

        public Criteria andLinkUrlAppNotEqualTo(String value) {
            addCriterion("link_url_app <>", value, "linkUrlApp");
            return (Criteria) this;
        }

        public Criteria andLinkUrlAppGreaterThan(String value) {
            addCriterion("link_url_app >", value, "linkUrlApp");
            return (Criteria) this;
        }

        public Criteria andLinkUrlAppGreaterThanOrEqualTo(String value) {
            addCriterion("link_url_app >=", value, "linkUrlApp");
            return (Criteria) this;
        }

        public Criteria andLinkUrlAppLessThan(String value) {
            addCriterion("link_url_app <", value, "linkUrlApp");
            return (Criteria) this;
        }

        public Criteria andLinkUrlAppLessThanOrEqualTo(String value) {
            addCriterion("link_url_app <=", value, "linkUrlApp");
            return (Criteria) this;
        }

        public Criteria andLinkUrlAppLike(String value) {
            addCriterion("link_url_app like", value, "linkUrlApp");
            return (Criteria) this;
        }

        public Criteria andLinkUrlAppNotLike(String value) {
            addCriterion("link_url_app not like", value, "linkUrlApp");
            return (Criteria) this;
        }

        public Criteria andLinkUrlAppIn(List<String> values) {
            addCriterion("link_url_app in", values, "linkUrlApp");
            return (Criteria) this;
        }

        public Criteria andLinkUrlAppNotIn(List<String> values) {
            addCriterion("link_url_app not in", values, "linkUrlApp");
            return (Criteria) this;
        }

        public Criteria andLinkUrlAppBetween(String value1, String value2) {
            addCriterion("link_url_app between", value1, value2, "linkUrlApp");
            return (Criteria) this;
        }

        public Criteria andLinkUrlAppNotBetween(String value1, String value2) {
            addCriterion("link_url_app not between", value1, value2, "linkUrlApp");
            return (Criteria) this;
        }

        public Criteria andPreheatIsNull() {
            addCriterion("preheat is null");
            return (Criteria) this;
        }

        public Criteria andPreheatIsNotNull() {
            addCriterion("preheat is not null");
            return (Criteria) this;
        }

        public Criteria andPreheatEqualTo(Integer value) {
            addCriterion("preheat =", value, "preheat");
            return (Criteria) this;
        }

        public Criteria andPreheatNotEqualTo(Integer value) {
            addCriterion("preheat <>", value, "preheat");
            return (Criteria) this;
        }

        public Criteria andPreheatGreaterThan(Integer value) {
            addCriterion("preheat >", value, "preheat");
            return (Criteria) this;
        }

        public Criteria andPreheatGreaterThanOrEqualTo(Integer value) {
            addCriterion("preheat >=", value, "preheat");
            return (Criteria) this;
        }

        public Criteria andPreheatLessThan(Integer value) {
            addCriterion("preheat <", value, "preheat");
            return (Criteria) this;
        }

        public Criteria andPreheatLessThanOrEqualTo(Integer value) {
            addCriterion("preheat <=", value, "preheat");
            return (Criteria) this;
        }

        public Criteria andPreheatIn(List<Integer> values) {
            addCriterion("preheat in", values, "preheat");
            return (Criteria) this;
        }

        public Criteria andPreheatNotIn(List<Integer> values) {
            addCriterion("preheat not in", values, "preheat");
            return (Criteria) this;
        }

        public Criteria andPreheatBetween(Integer value1, Integer value2) {
            addCriterion("preheat between", value1, value2, "preheat");
            return (Criteria) this;
        }

        public Criteria andPreheatNotBetween(Integer value1, Integer value2) {
            addCriterion("preheat not between", value1, value2, "preheat");
            return (Criteria) this;
        }

        public Criteria andActivityPicIsNull() {
            addCriterion("activity_pic is null");
            return (Criteria) this;
        }

        public Criteria andActivityPicIsNotNull() {
            addCriterion("activity_pic is not null");
            return (Criteria) this;
        }

        public Criteria andActivityPicEqualTo(String value) {
            addCriterion("activity_pic =", value, "activityPic");
            return (Criteria) this;
        }

        public Criteria andActivityPicNotEqualTo(String value) {
            addCriterion("activity_pic <>", value, "activityPic");
            return (Criteria) this;
        }

        public Criteria andActivityPicGreaterThan(String value) {
            addCriterion("activity_pic >", value, "activityPic");
            return (Criteria) this;
        }

        public Criteria andActivityPicGreaterThanOrEqualTo(String value) {
            addCriterion("activity_pic >=", value, "activityPic");
            return (Criteria) this;
        }

        public Criteria andActivityPicLessThan(String value) {
            addCriterion("activity_pic <", value, "activityPic");
            return (Criteria) this;
        }

        public Criteria andActivityPicLessThanOrEqualTo(String value) {
            addCriterion("activity_pic <=", value, "activityPic");
            return (Criteria) this;
        }

        public Criteria andActivityPicLike(String value) {
            addCriterion("activity_pic like", value, "activityPic");
            return (Criteria) this;
        }

        public Criteria andActivityPicNotLike(String value) {
            addCriterion("activity_pic not like", value, "activityPic");
            return (Criteria) this;
        }

        public Criteria andActivityPicIn(List<String> values) {
            addCriterion("activity_pic in", values, "activityPic");
            return (Criteria) this;
        }

        public Criteria andActivityPicNotIn(List<String> values) {
            addCriterion("activity_pic not in", values, "activityPic");
            return (Criteria) this;
        }

        public Criteria andActivityPicBetween(String value1, String value2) {
            addCriterion("activity_pic between", value1, value2, "activityPic");
            return (Criteria) this;
        }

        public Criteria andActivityPicNotBetween(String value1, String value2) {
            addCriterion("activity_pic not between", value1, value2, "activityPic");
            return (Criteria) this;
        }

        public Criteria andActivityGiftIsNull() {
            addCriterion("activity_gift is null");
            return (Criteria) this;
        }

        public Criteria andActivityGiftIsNotNull() {
            addCriterion("activity_gift is not null");
            return (Criteria) this;
        }

        public Criteria andActivityGiftEqualTo(String value) {
            addCriterion("activity_gift =", value, "activityGift");
            return (Criteria) this;
        }

        public Criteria andActivityGiftNotEqualTo(String value) {
            addCriterion("activity_gift <>", value, "activityGift");
            return (Criteria) this;
        }

        public Criteria andActivityGiftGreaterThan(String value) {
            addCriterion("activity_gift >", value, "activityGift");
            return (Criteria) this;
        }

        public Criteria andActivityGiftGreaterThanOrEqualTo(String value) {
            addCriterion("activity_gift >=", value, "activityGift");
            return (Criteria) this;
        }

        public Criteria andActivityGiftLessThan(String value) {
            addCriterion("activity_gift <", value, "activityGift");
            return (Criteria) this;
        }

        public Criteria andActivityGiftLessThanOrEqualTo(String value) {
            addCriterion("activity_gift <=", value, "activityGift");
            return (Criteria) this;
        }

        public Criteria andActivityGiftLike(String value) {
            addCriterion("activity_gift like", value, "activityGift");
            return (Criteria) this;
        }

        public Criteria andActivityGiftNotLike(String value) {
            addCriterion("activity_gift not like", value, "activityGift");
            return (Criteria) this;
        }

        public Criteria andActivityGiftIn(List<String> values) {
            addCriterion("activity_gift in", values, "activityGift");
            return (Criteria) this;
        }

        public Criteria andActivityGiftNotIn(List<String> values) {
            addCriterion("activity_gift not in", values, "activityGift");
            return (Criteria) this;
        }

        public Criteria andActivityGiftBetween(String value1, String value2) {
            addCriterion("activity_gift between", value1, value2, "activityGift");
            return (Criteria) this;
        }

        public Criteria andActivityGiftNotBetween(String value1, String value2) {
            addCriterion("activity_gift not between", value1, value2, "activityGift");
            return (Criteria) this;
        }

        public Criteria andActivityWordIsNull() {
            addCriterion("activity_word is null");
            return (Criteria) this;
        }

        public Criteria andActivityWordIsNotNull() {
            addCriterion("activity_word is not null");
            return (Criteria) this;
        }

        public Criteria andActivityWordEqualTo(String value) {
            addCriterion("activity_word =", value, "activityWord");
            return (Criteria) this;
        }

        public Criteria andActivityWordNotEqualTo(String value) {
            addCriterion("activity_word <>", value, "activityWord");
            return (Criteria) this;
        }

        public Criteria andActivityWordGreaterThan(String value) {
            addCriterion("activity_word >", value, "activityWord");
            return (Criteria) this;
        }

        public Criteria andActivityWordGreaterThanOrEqualTo(String value) {
            addCriterion("activity_word >=", value, "activityWord");
            return (Criteria) this;
        }

        public Criteria andActivityWordLessThan(String value) {
            addCriterion("activity_word <", value, "activityWord");
            return (Criteria) this;
        }

        public Criteria andActivityWordLessThanOrEqualTo(String value) {
            addCriterion("activity_word <=", value, "activityWord");
            return (Criteria) this;
        }

        public Criteria andActivityWordLike(String value) {
            addCriterion("activity_word like", value, "activityWord");
            return (Criteria) this;
        }

        public Criteria andActivityWordNotLike(String value) {
            addCriterion("activity_word not like", value, "activityWord");
            return (Criteria) this;
        }

        public Criteria andActivityWordIn(List<String> values) {
            addCriterion("activity_word in", values, "activityWord");
            return (Criteria) this;
        }

        public Criteria andActivityWordNotIn(List<String> values) {
            addCriterion("activity_word not in", values, "activityWord");
            return (Criteria) this;
        }

        public Criteria andActivityWordBetween(String value1, String value2) {
            addCriterion("activity_word between", value1, value2, "activityWord");
            return (Criteria) this;
        }

        public Criteria andActivityWordNotBetween(String value1, String value2) {
            addCriterion("activity_word not between", value1, value2, "activityWord");
            return (Criteria) this;
        }

        public Criteria andApIdIsNull() {
            addCriterion("ap_id is null");
            return (Criteria) this;
        }

        public Criteria andApIdIsNotNull() {
            addCriterion("ap_id is not null");
            return (Criteria) this;
        }

        public Criteria andApIdEqualTo(String value) {
            addCriterion("ap_id =", value, "apId");
            return (Criteria) this;
        }

        public Criteria andApIdNotEqualTo(String value) {
            addCriterion("ap_id <>", value, "apId");
            return (Criteria) this;
        }

        public Criteria andApIdGreaterThan(String value) {
            addCriterion("ap_id >", value, "apId");
            return (Criteria) this;
        }

        public Criteria andApIdGreaterThanOrEqualTo(String value) {
            addCriterion("ap_id >=", value, "apId");
            return (Criteria) this;
        }

        public Criteria andApIdLessThan(String value) {
            addCriterion("ap_id <", value, "apId");
            return (Criteria) this;
        }

        public Criteria andApIdLessThanOrEqualTo(String value) {
            addCriterion("ap_id <=", value, "apId");
            return (Criteria) this;
        }

        public Criteria andApIdLike(String value) {
            addCriterion("ap_id like", value, "apId");
            return (Criteria) this;
        }

        public Criteria andApIdNotLike(String value) {
            addCriterion("ap_id not like", value, "apId");
            return (Criteria) this;
        }

        public Criteria andApIdIn(List<String> values) {
            addCriterion("ap_id in", values, "apId");
            return (Criteria) this;
        }

        public Criteria andApIdNotIn(List<String> values) {
            addCriterion("ap_id not in", values, "apId");
            return (Criteria) this;
        }

        public Criteria andApIdBetween(String value1, String value2) {
            addCriterion("ap_id between", value1, value2, "apId");
            return (Criteria) this;
        }

        public Criteria andApIdNotBetween(String value1, String value2) {
            addCriterion("ap_id not between", value1, value2, "apId");
            return (Criteria) this;
        }
    }

    /**
     * 类注释
     * Criteria
     * 数据库表：activity_product
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
     * 数据库表：activity_product
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