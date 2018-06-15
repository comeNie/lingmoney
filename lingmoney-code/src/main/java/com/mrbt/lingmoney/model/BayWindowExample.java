package com.mrbt.lingmoney.model;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class BayWindowExample {
    /**
     * 排序字段,bay_window
     */
    protected String orderByClause;

    /**
     * 是否过滤重复数据,bay_window
     */
    protected boolean distinct;

    /**
     * 查询条件实例,bay_window
     */
    protected List<Criteria> oredCriteria;

    /**
     * 第一个返回记录行的偏移量,bay_window
     */
    protected int limitStart = -1;

    /**
     * 返回记录行的最大数目,bay_window
     */
    protected int limitEnd = -1;

    /**
     *  构造查询条件,bay_window
     */
    public BayWindowExample() {
        oredCriteria = new ArrayList<Criteria>();
    }

    /**
     *  设置排序字段,bay_window
     * @param orderByClause 排序字段
     */
    public void setOrderByClause(String orderByClause) {
        this.orderByClause = orderByClause;
    }

    /**
     *  获取排序字段,bay_window
     */
    public String getOrderByClause() {
        return orderByClause;
    }

    /**
     *  设置过滤重复数据,bay_window
     * @param distinct 是否过滤重复数据
     */
    public void setDistinct(boolean distinct) {
        this.distinct = distinct;
    }

    /**
     *  是否过滤重复数据,bay_window
     */
    public boolean isDistinct() {
        return distinct;
    }

    /**
     *  获取当前的查询条件实例,bay_window
     */
    public List<Criteria> getOredCriteria() {
        return oredCriteria;
    }

    /**
     *  增加或者的查询条件,用于构建或者查询,bay_window
     * @param criteria 过滤条件实例
     */
    public void or(Criteria criteria) {
        oredCriteria.add(criteria);
    }

    /**
     *  创建一个新的或者查询条件,bay_window
     */
    public Criteria or() {
        Criteria criteria = createCriteriaInternal();
        oredCriteria.add(criteria);
        return criteria;
    }

    /**
     *  创建一个查询条件,bay_window
     */
    public Criteria createCriteria() {
        Criteria criteria = createCriteriaInternal();
        if (oredCriteria.size() == 0) {
            oredCriteria.add(criteria);
        }
        return criteria;
    }

    /**
     *  内部构建查询条件对象,bay_window
     */
    protected Criteria createCriteriaInternal() {
        Criteria criteria = new Criteria();
        return criteria;
    }

    /**
     *  清除查询条件,bay_window
     */
    public void clear() {
        oredCriteria.clear();
        orderByClause = null;
        distinct = false;
    }

    /**
     *  设置第一个返回记录行的偏移量,bay_window
     * @param limitStart 第一个返回记录行的偏移量
     */
    public void setLimitStart(int limitStart) {
        this.limitStart=limitStart;
    }

    /**
     *  获取第一个返回记录行的偏移量,bay_window
     */
    public int getLimitStart() {
        return limitStart;
    }

    /**
     *  设置返回记录行的最大数目,bay_window
     * @param limitEnd 返回记录行的最大数目
     */
    public void setLimitEnd(int limitEnd) {
        this.limitEnd=limitEnd;
    }

    /**
     *  获取返回记录行的最大数目,bay_window
     */
    public int getLimitEnd() {
        return limitEnd;
    }

    /**
     * 类注释
     * GeneratedCriteria
     * 数据库表：bay_window
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

        public Criteria andTitleIsNull() {
            addCriterion("title is null");
            return (Criteria) this;
        }

        public Criteria andTitleIsNotNull() {
            addCriterion("title is not null");
            return (Criteria) this;
        }

        public Criteria andTitleEqualTo(String value) {
            addCriterion("title =", value, "title");
            return (Criteria) this;
        }

        public Criteria andTitleNotEqualTo(String value) {
            addCriterion("title <>", value, "title");
            return (Criteria) this;
        }

        public Criteria andTitleGreaterThan(String value) {
            addCriterion("title >", value, "title");
            return (Criteria) this;
        }

        public Criteria andTitleGreaterThanOrEqualTo(String value) {
            addCriterion("title >=", value, "title");
            return (Criteria) this;
        }

        public Criteria andTitleLessThan(String value) {
            addCriterion("title <", value, "title");
            return (Criteria) this;
        }

        public Criteria andTitleLessThanOrEqualTo(String value) {
            addCriterion("title <=", value, "title");
            return (Criteria) this;
        }

        public Criteria andTitleLike(String value) {
            addCriterion("title like", value, "title");
            return (Criteria) this;
        }

        public Criteria andTitleNotLike(String value) {
            addCriterion("title not like", value, "title");
            return (Criteria) this;
        }

        public Criteria andTitleIn(List<String> values) {
            addCriterion("title in", values, "title");
            return (Criteria) this;
        }

        public Criteria andTitleNotIn(List<String> values) {
            addCriterion("title not in", values, "title");
            return (Criteria) this;
        }

        public Criteria andTitleBetween(String value1, String value2) {
            addCriterion("title between", value1, value2, "title");
            return (Criteria) this;
        }

        public Criteria andTitleNotBetween(String value1, String value2) {
            addCriterion("title not between", value1, value2, "title");
            return (Criteria) this;
        }

        public Criteria andAppImgUrlIsNull() {
            addCriterion("app_img_url is null");
            return (Criteria) this;
        }

        public Criteria andAppImgUrlIsNotNull() {
            addCriterion("app_img_url is not null");
            return (Criteria) this;
        }

        public Criteria andAppImgUrlEqualTo(String value) {
            addCriterion("app_img_url =", value, "appImgUrl");
            return (Criteria) this;
        }

        public Criteria andAppImgUrlNotEqualTo(String value) {
            addCriterion("app_img_url <>", value, "appImgUrl");
            return (Criteria) this;
        }

        public Criteria andAppImgUrlGreaterThan(String value) {
            addCriterion("app_img_url >", value, "appImgUrl");
            return (Criteria) this;
        }

        public Criteria andAppImgUrlGreaterThanOrEqualTo(String value) {
            addCriterion("app_img_url >=", value, "appImgUrl");
            return (Criteria) this;
        }

        public Criteria andAppImgUrlLessThan(String value) {
            addCriterion("app_img_url <", value, "appImgUrl");
            return (Criteria) this;
        }

        public Criteria andAppImgUrlLessThanOrEqualTo(String value) {
            addCriterion("app_img_url <=", value, "appImgUrl");
            return (Criteria) this;
        }

        public Criteria andAppImgUrlLike(String value) {
            addCriterion("app_img_url like", value, "appImgUrl");
            return (Criteria) this;
        }

        public Criteria andAppImgUrlNotLike(String value) {
            addCriterion("app_img_url not like", value, "appImgUrl");
            return (Criteria) this;
        }

        public Criteria andAppImgUrlIn(List<String> values) {
            addCriterion("app_img_url in", values, "appImgUrl");
            return (Criteria) this;
        }

        public Criteria andAppImgUrlNotIn(List<String> values) {
            addCriterion("app_img_url not in", values, "appImgUrl");
            return (Criteria) this;
        }

        public Criteria andAppImgUrlBetween(String value1, String value2) {
            addCriterion("app_img_url between", value1, value2, "appImgUrl");
            return (Criteria) this;
        }

        public Criteria andAppImgUrlNotBetween(String value1, String value2) {
            addCriterion("app_img_url not between", value1, value2, "appImgUrl");
            return (Criteria) this;
        }

        public Criteria andPcImgUrlIsNull() {
            addCriterion("pc_img_url is null");
            return (Criteria) this;
        }

        public Criteria andPcImgUrlIsNotNull() {
            addCriterion("pc_img_url is not null");
            return (Criteria) this;
        }

        public Criteria andPcImgUrlEqualTo(String value) {
            addCriterion("pc_img_url =", value, "pcImgUrl");
            return (Criteria) this;
        }

        public Criteria andPcImgUrlNotEqualTo(String value) {
            addCriterion("pc_img_url <>", value, "pcImgUrl");
            return (Criteria) this;
        }

        public Criteria andPcImgUrlGreaterThan(String value) {
            addCriterion("pc_img_url >", value, "pcImgUrl");
            return (Criteria) this;
        }

        public Criteria andPcImgUrlGreaterThanOrEqualTo(String value) {
            addCriterion("pc_img_url >=", value, "pcImgUrl");
            return (Criteria) this;
        }

        public Criteria andPcImgUrlLessThan(String value) {
            addCriterion("pc_img_url <", value, "pcImgUrl");
            return (Criteria) this;
        }

        public Criteria andPcImgUrlLessThanOrEqualTo(String value) {
            addCriterion("pc_img_url <=", value, "pcImgUrl");
            return (Criteria) this;
        }

        public Criteria andPcImgUrlLike(String value) {
            addCriterion("pc_img_url like", value, "pcImgUrl");
            return (Criteria) this;
        }

        public Criteria andPcImgUrlNotLike(String value) {
            addCriterion("pc_img_url not like", value, "pcImgUrl");
            return (Criteria) this;
        }

        public Criteria andPcImgUrlIn(List<String> values) {
            addCriterion("pc_img_url in", values, "pcImgUrl");
            return (Criteria) this;
        }

        public Criteria andPcImgUrlNotIn(List<String> values) {
            addCriterion("pc_img_url not in", values, "pcImgUrl");
            return (Criteria) this;
        }

        public Criteria andPcImgUrlBetween(String value1, String value2) {
            addCriterion("pc_img_url between", value1, value2, "pcImgUrl");
            return (Criteria) this;
        }

        public Criteria andPcImgUrlNotBetween(String value1, String value2) {
            addCriterion("pc_img_url not between", value1, value2, "pcImgUrl");
            return (Criteria) this;
        }

        public Criteria andAppOpenUrlIsNull() {
            addCriterion("app_open_url is null");
            return (Criteria) this;
        }

        public Criteria andAppOpenUrlIsNotNull() {
            addCriterion("app_open_url is not null");
            return (Criteria) this;
        }

        public Criteria andAppOpenUrlEqualTo(String value) {
            addCriterion("app_open_url =", value, "appOpenUrl");
            return (Criteria) this;
        }

        public Criteria andAppOpenUrlNotEqualTo(String value) {
            addCriterion("app_open_url <>", value, "appOpenUrl");
            return (Criteria) this;
        }

        public Criteria andAppOpenUrlGreaterThan(String value) {
            addCriterion("app_open_url >", value, "appOpenUrl");
            return (Criteria) this;
        }

        public Criteria andAppOpenUrlGreaterThanOrEqualTo(String value) {
            addCriterion("app_open_url >=", value, "appOpenUrl");
            return (Criteria) this;
        }

        public Criteria andAppOpenUrlLessThan(String value) {
            addCriterion("app_open_url <", value, "appOpenUrl");
            return (Criteria) this;
        }

        public Criteria andAppOpenUrlLessThanOrEqualTo(String value) {
            addCriterion("app_open_url <=", value, "appOpenUrl");
            return (Criteria) this;
        }

        public Criteria andAppOpenUrlLike(String value) {
            addCriterion("app_open_url like", value, "appOpenUrl");
            return (Criteria) this;
        }

        public Criteria andAppOpenUrlNotLike(String value) {
            addCriterion("app_open_url not like", value, "appOpenUrl");
            return (Criteria) this;
        }

        public Criteria andAppOpenUrlIn(List<String> values) {
            addCriterion("app_open_url in", values, "appOpenUrl");
            return (Criteria) this;
        }

        public Criteria andAppOpenUrlNotIn(List<String> values) {
            addCriterion("app_open_url not in", values, "appOpenUrl");
            return (Criteria) this;
        }

        public Criteria andAppOpenUrlBetween(String value1, String value2) {
            addCriterion("app_open_url between", value1, value2, "appOpenUrl");
            return (Criteria) this;
        }

        public Criteria andAppOpenUrlNotBetween(String value1, String value2) {
            addCriterion("app_open_url not between", value1, value2, "appOpenUrl");
            return (Criteria) this;
        }

        public Criteria andPcOpenUrlIsNull() {
            addCriterion("pc_open_url is null");
            return (Criteria) this;
        }

        public Criteria andPcOpenUrlIsNotNull() {
            addCriterion("pc_open_url is not null");
            return (Criteria) this;
        }

        public Criteria andPcOpenUrlEqualTo(String value) {
            addCriterion("pc_open_url =", value, "pcOpenUrl");
            return (Criteria) this;
        }

        public Criteria andPcOpenUrlNotEqualTo(String value) {
            addCriterion("pc_open_url <>", value, "pcOpenUrl");
            return (Criteria) this;
        }

        public Criteria andPcOpenUrlGreaterThan(String value) {
            addCriterion("pc_open_url >", value, "pcOpenUrl");
            return (Criteria) this;
        }

        public Criteria andPcOpenUrlGreaterThanOrEqualTo(String value) {
            addCriterion("pc_open_url >=", value, "pcOpenUrl");
            return (Criteria) this;
        }

        public Criteria andPcOpenUrlLessThan(String value) {
            addCriterion("pc_open_url <", value, "pcOpenUrl");
            return (Criteria) this;
        }

        public Criteria andPcOpenUrlLessThanOrEqualTo(String value) {
            addCriterion("pc_open_url <=", value, "pcOpenUrl");
            return (Criteria) this;
        }

        public Criteria andPcOpenUrlLike(String value) {
            addCriterion("pc_open_url like", value, "pcOpenUrl");
            return (Criteria) this;
        }

        public Criteria andPcOpenUrlNotLike(String value) {
            addCriterion("pc_open_url not like", value, "pcOpenUrl");
            return (Criteria) this;
        }

        public Criteria andPcOpenUrlIn(List<String> values) {
            addCriterion("pc_open_url in", values, "pcOpenUrl");
            return (Criteria) this;
        }

        public Criteria andPcOpenUrlNotIn(List<String> values) {
            addCriterion("pc_open_url not in", values, "pcOpenUrl");
            return (Criteria) this;
        }

        public Criteria andPcOpenUrlBetween(String value1, String value2) {
            addCriterion("pc_open_url between", value1, value2, "pcOpenUrl");
            return (Criteria) this;
        }

        public Criteria andPcOpenUrlNotBetween(String value1, String value2) {
            addCriterion("pc_open_url not between", value1, value2, "pcOpenUrl");
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

        public Criteria andStartTimeIsNull() {
            addCriterion("start_time is null");
            return (Criteria) this;
        }

        public Criteria andStartTimeIsNotNull() {
            addCriterion("start_time is not null");
            return (Criteria) this;
        }

        public Criteria andStartTimeEqualTo(Date value) {
            addCriterion("start_time =", value, "startTime");
            return (Criteria) this;
        }

        public Criteria andStartTimeNotEqualTo(Date value) {
            addCriterion("start_time <>", value, "startTime");
            return (Criteria) this;
        }

        public Criteria andStartTimeGreaterThan(Date value) {
            addCriterion("start_time >", value, "startTime");
            return (Criteria) this;
        }

        public Criteria andStartTimeGreaterThanOrEqualTo(Date value) {
            addCriterion("start_time >=", value, "startTime");
            return (Criteria) this;
        }

        public Criteria andStartTimeLessThan(Date value) {
            addCriterion("start_time <", value, "startTime");
            return (Criteria) this;
        }

        public Criteria andStartTimeLessThanOrEqualTo(Date value) {
            addCriterion("start_time <=", value, "startTime");
            return (Criteria) this;
        }

        public Criteria andStartTimeIn(List<Date> values) {
            addCriterion("start_time in", values, "startTime");
            return (Criteria) this;
        }

        public Criteria andStartTimeNotIn(List<Date> values) {
            addCriterion("start_time not in", values, "startTime");
            return (Criteria) this;
        }

        public Criteria andStartTimeBetween(Date value1, Date value2) {
            addCriterion("start_time between", value1, value2, "startTime");
            return (Criteria) this;
        }

        public Criteria andStartTimeNotBetween(Date value1, Date value2) {
            addCriterion("start_time not between", value1, value2, "startTime");
            return (Criteria) this;
        }

        public Criteria andEndTimeIsNull() {
            addCriterion("end_time is null");
            return (Criteria) this;
        }

        public Criteria andEndTimeIsNotNull() {
            addCriterion("end_time is not null");
            return (Criteria) this;
        }

        public Criteria andEndTimeEqualTo(Date value) {
            addCriterion("end_time =", value, "endTime");
            return (Criteria) this;
        }

        public Criteria andEndTimeNotEqualTo(Date value) {
            addCriterion("end_time <>", value, "endTime");
            return (Criteria) this;
        }

        public Criteria andEndTimeGreaterThan(Date value) {
            addCriterion("end_time >", value, "endTime");
            return (Criteria) this;
        }

        public Criteria andEndTimeGreaterThanOrEqualTo(Date value) {
            addCriterion("end_time >=", value, "endTime");
            return (Criteria) this;
        }

        public Criteria andEndTimeLessThan(Date value) {
            addCriterion("end_time <", value, "endTime");
            return (Criteria) this;
        }

        public Criteria andEndTimeLessThanOrEqualTo(Date value) {
            addCriterion("end_time <=", value, "endTime");
            return (Criteria) this;
        }

        public Criteria andEndTimeIn(List<Date> values) {
            addCriterion("end_time in", values, "endTime");
            return (Criteria) this;
        }

        public Criteria andEndTimeNotIn(List<Date> values) {
            addCriterion("end_time not in", values, "endTime");
            return (Criteria) this;
        }

        public Criteria andEndTimeBetween(Date value1, Date value2) {
            addCriterion("end_time between", value1, value2, "endTime");
            return (Criteria) this;
        }

        public Criteria andEndTimeNotBetween(Date value1, Date value2) {
            addCriterion("end_time not between", value1, value2, "endTime");
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

        public Criteria andDisPosIsNull() {
            addCriterion("dis_pos is null");
            return (Criteria) this;
        }

        public Criteria andDisPosIsNotNull() {
            addCriterion("dis_pos is not null");
            return (Criteria) this;
        }

        public Criteria andDisPosEqualTo(Integer value) {
            addCriterion("dis_pos =", value, "disPos");
            return (Criteria) this;
        }

        public Criteria andDisPosNotEqualTo(Integer value) {
            addCriterion("dis_pos <>", value, "disPos");
            return (Criteria) this;
        }

        public Criteria andDisPosGreaterThan(Integer value) {
            addCriterion("dis_pos >", value, "disPos");
            return (Criteria) this;
        }

        public Criteria andDisPosGreaterThanOrEqualTo(Integer value) {
            addCriterion("dis_pos >=", value, "disPos");
            return (Criteria) this;
        }

        public Criteria andDisPosLessThan(Integer value) {
            addCriterion("dis_pos <", value, "disPos");
            return (Criteria) this;
        }

        public Criteria andDisPosLessThanOrEqualTo(Integer value) {
            addCriterion("dis_pos <=", value, "disPos");
            return (Criteria) this;
        }

        public Criteria andDisPosIn(List<Integer> values) {
            addCriterion("dis_pos in", values, "disPos");
            return (Criteria) this;
        }

        public Criteria andDisPosNotIn(List<Integer> values) {
            addCriterion("dis_pos not in", values, "disPos");
            return (Criteria) this;
        }

        public Criteria andDisPosBetween(Integer value1, Integer value2) {
            addCriterion("dis_pos between", value1, value2, "disPos");
            return (Criteria) this;
        }

        public Criteria andDisPosNotBetween(Integer value1, Integer value2) {
            addCriterion("dis_pos not between", value1, value2, "disPos");
            return (Criteria) this;
        }
    }

    /**
     * 类注释
     * Criteria
     * 数据库表：bay_window
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
     * 数据库表：bay_window
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