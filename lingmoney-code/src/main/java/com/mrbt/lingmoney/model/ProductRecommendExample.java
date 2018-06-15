package com.mrbt.lingmoney.model;

import java.util.ArrayList;
import java.util.List;

public class ProductRecommendExample {
    /**
     * 排序字段,product_recommend
     */
    protected String orderByClause;

    /**
     * 是否过滤重复数据,product_recommend
     */
    protected boolean distinct;

    /**
     * 查询条件实例,product_recommend
     */
    protected List<Criteria> oredCriteria;

    /**
     * 第一个返回记录行的偏移量,product_recommend
     */
    protected int limitStart = -1;

    /**
     * 返回记录行的最大数目,product_recommend
     */
    protected int limitEnd = -1;

    /**
     *  构造查询条件,product_recommend
     */
    public ProductRecommendExample() {
        oredCriteria = new ArrayList<Criteria>();
    }

    /**
     *  设置排序字段,product_recommend
     * @param orderByClause 排序字段
     */
    public void setOrderByClause(String orderByClause) {
        this.orderByClause = orderByClause;
    }

    /**
     *  获取排序字段,product_recommend
     */
    public String getOrderByClause() {
        return orderByClause;
    }

    /**
     *  设置过滤重复数据,product_recommend
     * @param distinct 是否过滤重复数据
     */
    public void setDistinct(boolean distinct) {
        this.distinct = distinct;
    }

    /**
     *  是否过滤重复数据,product_recommend
     */
    public boolean isDistinct() {
        return distinct;
    }

    /**
     *  获取当前的查询条件实例,product_recommend
     */
    public List<Criteria> getOredCriteria() {
        return oredCriteria;
    }

    /**
     *  增加或者的查询条件,用于构建或者查询,product_recommend
     * @param criteria 过滤条件实例
     */
    public void or(Criteria criteria) {
        oredCriteria.add(criteria);
    }

    /**
     *  创建一个新的或者查询条件,product_recommend
     */
    public Criteria or() {
        Criteria criteria = createCriteriaInternal();
        oredCriteria.add(criteria);
        return criteria;
    }

    /**
     *  创建一个查询条件,product_recommend
     */
    public Criteria createCriteria() {
        Criteria criteria = createCriteriaInternal();
        if (oredCriteria.size() == 0) {
            oredCriteria.add(criteria);
        }
        return criteria;
    }

    /**
     *  内部构建查询条件对象,product_recommend
     */
    protected Criteria createCriteriaInternal() {
        Criteria criteria = new Criteria();
        return criteria;
    }

    /**
     *  清除查询条件,product_recommend
     */
    public void clear() {
        oredCriteria.clear();
        orderByClause = null;
        distinct = false;
    }

    /**
     *  设置第一个返回记录行的偏移量,product_recommend
     * @param limitStart 第一个返回记录行的偏移量
     */
    public void setLimitStart(int limitStart) {
        this.limitStart=limitStart;
    }

    /**
     *  获取第一个返回记录行的偏移量,product_recommend
     */
    public int getLimitStart() {
        return limitStart;
    }

    /**
     *  设置返回记录行的最大数目,product_recommend
     * @param limitEnd 返回记录行的最大数目
     */
    public void setLimitEnd(int limitEnd) {
        this.limitEnd=limitEnd;
    }

    /**
     *  获取返回记录行的最大数目,product_recommend
     */
    public int getLimitEnd() {
        return limitEnd;
    }

    /**
     * 类注释
     * GeneratedCriteria
     * 数据库表：product_recommend
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

        public Criteria andPIdEqualTo(Integer value) {
            addCriterion("p_id =", value, "pId");
            return (Criteria) this;
        }

        public Criteria andPIdNotEqualTo(Integer value) {
            addCriterion("p_id <>", value, "pId");
            return (Criteria) this;
        }

        public Criteria andPIdGreaterThan(Integer value) {
            addCriterion("p_id >", value, "pId");
            return (Criteria) this;
        }

        public Criteria andPIdGreaterThanOrEqualTo(Integer value) {
            addCriterion("p_id >=", value, "pId");
            return (Criteria) this;
        }

        public Criteria andPIdLessThan(Integer value) {
            addCriterion("p_id <", value, "pId");
            return (Criteria) this;
        }

        public Criteria andPIdLessThanOrEqualTo(Integer value) {
            addCriterion("p_id <=", value, "pId");
            return (Criteria) this;
        }

        public Criteria andPIdIn(List<Integer> values) {
            addCriterion("p_id in", values, "pId");
            return (Criteria) this;
        }

        public Criteria andPIdNotIn(List<Integer> values) {
            addCriterion("p_id not in", values, "pId");
            return (Criteria) this;
        }

        public Criteria andPIdBetween(Integer value1, Integer value2) {
            addCriterion("p_id between", value1, value2, "pId");
            return (Criteria) this;
        }

        public Criteria andPIdNotBetween(Integer value1, Integer value2) {
            addCriterion("p_id not between", value1, value2, "pId");
            return (Criteria) this;
        }

        public Criteria andIndexXIsNull() {
            addCriterion("index_x is null");
            return (Criteria) this;
        }

        public Criteria andIndexXIsNotNull() {
            addCriterion("index_x is not null");
            return (Criteria) this;
        }

        public Criteria andIndexXEqualTo(Integer value) {
            addCriterion("index_x =", value, "indexX");
            return (Criteria) this;
        }

        public Criteria andIndexXNotEqualTo(Integer value) {
            addCriterion("index_x <>", value, "indexX");
            return (Criteria) this;
        }

        public Criteria andIndexXGreaterThan(Integer value) {
            addCriterion("index_x >", value, "indexX");
            return (Criteria) this;
        }

        public Criteria andIndexXGreaterThanOrEqualTo(Integer value) {
            addCriterion("index_x >=", value, "indexX");
            return (Criteria) this;
        }

        public Criteria andIndexXLessThan(Integer value) {
            addCriterion("index_x <", value, "indexX");
            return (Criteria) this;
        }

        public Criteria andIndexXLessThanOrEqualTo(Integer value) {
            addCriterion("index_x <=", value, "indexX");
            return (Criteria) this;
        }

        public Criteria andIndexXIn(List<Integer> values) {
            addCriterion("index_x in", values, "indexX");
            return (Criteria) this;
        }

        public Criteria andIndexXNotIn(List<Integer> values) {
            addCriterion("index_x not in", values, "indexX");
            return (Criteria) this;
        }

        public Criteria andIndexXBetween(Integer value1, Integer value2) {
            addCriterion("index_x between", value1, value2, "indexX");
            return (Criteria) this;
        }

        public Criteria andIndexXNotBetween(Integer value1, Integer value2) {
            addCriterion("index_x not between", value1, value2, "indexX");
            return (Criteria) this;
        }

        public Criteria andIndexYIsNull() {
            addCriterion("index_y is null");
            return (Criteria) this;
        }

        public Criteria andIndexYIsNotNull() {
            addCriterion("index_y is not null");
            return (Criteria) this;
        }

        public Criteria andIndexYEqualTo(Integer value) {
            addCriterion("index_y =", value, "indexY");
            return (Criteria) this;
        }

        public Criteria andIndexYNotEqualTo(Integer value) {
            addCriterion("index_y <>", value, "indexY");
            return (Criteria) this;
        }

        public Criteria andIndexYGreaterThan(Integer value) {
            addCriterion("index_y >", value, "indexY");
            return (Criteria) this;
        }

        public Criteria andIndexYGreaterThanOrEqualTo(Integer value) {
            addCriterion("index_y >=", value, "indexY");
            return (Criteria) this;
        }

        public Criteria andIndexYLessThan(Integer value) {
            addCriterion("index_y <", value, "indexY");
            return (Criteria) this;
        }

        public Criteria andIndexYLessThanOrEqualTo(Integer value) {
            addCriterion("index_y <=", value, "indexY");
            return (Criteria) this;
        }

        public Criteria andIndexYIn(List<Integer> values) {
            addCriterion("index_y in", values, "indexY");
            return (Criteria) this;
        }

        public Criteria andIndexYNotIn(List<Integer> values) {
            addCriterion("index_y not in", values, "indexY");
            return (Criteria) this;
        }

        public Criteria andIndexYBetween(Integer value1, Integer value2) {
            addCriterion("index_y between", value1, value2, "indexY");
            return (Criteria) this;
        }

        public Criteria andIndexYNotBetween(Integer value1, Integer value2) {
            addCriterion("index_y not between", value1, value2, "indexY");
            return (Criteria) this;
        }

        public Criteria andRecommendIsNull() {
            addCriterion("recommend is null");
            return (Criteria) this;
        }

        public Criteria andRecommendIsNotNull() {
            addCriterion("recommend is not null");
            return (Criteria) this;
        }

        public Criteria andRecommendEqualTo(Integer value) {
            addCriterion("recommend =", value, "recommend");
            return (Criteria) this;
        }

        public Criteria andRecommendNotEqualTo(Integer value) {
            addCriterion("recommend <>", value, "recommend");
            return (Criteria) this;
        }

        public Criteria andRecommendGreaterThan(Integer value) {
            addCriterion("recommend >", value, "recommend");
            return (Criteria) this;
        }

        public Criteria andRecommendGreaterThanOrEqualTo(Integer value) {
            addCriterion("recommend >=", value, "recommend");
            return (Criteria) this;
        }

        public Criteria andRecommendLessThan(Integer value) {
            addCriterion("recommend <", value, "recommend");
            return (Criteria) this;
        }

        public Criteria andRecommendLessThanOrEqualTo(Integer value) {
            addCriterion("recommend <=", value, "recommend");
            return (Criteria) this;
        }

        public Criteria andRecommendIn(List<Integer> values) {
            addCriterion("recommend in", values, "recommend");
            return (Criteria) this;
        }

        public Criteria andRecommendNotIn(List<Integer> values) {
            addCriterion("recommend not in", values, "recommend");
            return (Criteria) this;
        }

        public Criteria andRecommendBetween(Integer value1, Integer value2) {
            addCriterion("recommend between", value1, value2, "recommend");
            return (Criteria) this;
        }

        public Criteria andRecommendNotBetween(Integer value1, Integer value2) {
            addCriterion("recommend not between", value1, value2, "recommend");
            return (Criteria) this;
        }

        public Criteria andCityCodeIsNull() {
            addCriterion("city_code is null");
            return (Criteria) this;
        }

        public Criteria andCityCodeIsNotNull() {
            addCriterion("city_code is not null");
            return (Criteria) this;
        }

        public Criteria andCityCodeEqualTo(String value) {
            addCriterion("city_code =", value, "cityCode");
            return (Criteria) this;
        }

        public Criteria andCityCodeNotEqualTo(String value) {
            addCriterion("city_code <>", value, "cityCode");
            return (Criteria) this;
        }

        public Criteria andCityCodeGreaterThan(String value) {
            addCriterion("city_code >", value, "cityCode");
            return (Criteria) this;
        }

        public Criteria andCityCodeGreaterThanOrEqualTo(String value) {
            addCriterion("city_code >=", value, "cityCode");
            return (Criteria) this;
        }

        public Criteria andCityCodeLessThan(String value) {
            addCriterion("city_code <", value, "cityCode");
            return (Criteria) this;
        }

        public Criteria andCityCodeLessThanOrEqualTo(String value) {
            addCriterion("city_code <=", value, "cityCode");
            return (Criteria) this;
        }

        public Criteria andCityCodeLike(String value) {
            addCriterion("city_code like", value, "cityCode");
            return (Criteria) this;
        }

        public Criteria andCityCodeNotLike(String value) {
            addCriterion("city_code not like", value, "cityCode");
            return (Criteria) this;
        }

        public Criteria andCityCodeIn(List<String> values) {
            addCriterion("city_code in", values, "cityCode");
            return (Criteria) this;
        }

        public Criteria andCityCodeNotIn(List<String> values) {
            addCriterion("city_code not in", values, "cityCode");
            return (Criteria) this;
        }

        public Criteria andCityCodeBetween(String value1, String value2) {
            addCriterion("city_code between", value1, value2, "cityCode");
            return (Criteria) this;
        }

        public Criteria andCityCodeNotBetween(String value1, String value2) {
            addCriterion("city_code not between", value1, value2, "cityCode");
            return (Criteria) this;
        }

        public Criteria andHomeShowIsNull() {
            addCriterion("home_show is null");
            return (Criteria) this;
        }

        public Criteria andHomeShowIsNotNull() {
            addCriterion("home_show is not null");
            return (Criteria) this;
        }

        public Criteria andHomeShowEqualTo(Integer value) {
            addCriterion("home_show =", value, "homeShow");
            return (Criteria) this;
        }

        public Criteria andHomeShowNotEqualTo(Integer value) {
            addCriterion("home_show <>", value, "homeShow");
            return (Criteria) this;
        }

        public Criteria andHomeShowGreaterThan(Integer value) {
            addCriterion("home_show >", value, "homeShow");
            return (Criteria) this;
        }

        public Criteria andHomeShowGreaterThanOrEqualTo(Integer value) {
            addCriterion("home_show >=", value, "homeShow");
            return (Criteria) this;
        }

        public Criteria andHomeShowLessThan(Integer value) {
            addCriterion("home_show <", value, "homeShow");
            return (Criteria) this;
        }

        public Criteria andHomeShowLessThanOrEqualTo(Integer value) {
            addCriterion("home_show <=", value, "homeShow");
            return (Criteria) this;
        }

        public Criteria andHomeShowIn(List<Integer> values) {
            addCriterion("home_show in", values, "homeShow");
            return (Criteria) this;
        }

        public Criteria andHomeShowNotIn(List<Integer> values) {
            addCriterion("home_show not in", values, "homeShow");
            return (Criteria) this;
        }

        public Criteria andHomeShowBetween(Integer value1, Integer value2) {
            addCriterion("home_show between", value1, value2, "homeShow");
            return (Criteria) this;
        }

        public Criteria andHomeShowNotBetween(Integer value1, Integer value2) {
            addCriterion("home_show not between", value1, value2, "homeShow");
            return (Criteria) this;
        }

        public Criteria andNewShowIsNull() {
            addCriterion("new_show is null");
            return (Criteria) this;
        }

        public Criteria andNewShowIsNotNull() {
            addCriterion("new_show is not null");
            return (Criteria) this;
        }

        public Criteria andNewShowEqualTo(Integer value) {
            addCriterion("new_show =", value, "newShow");
            return (Criteria) this;
        }

        public Criteria andNewShowNotEqualTo(Integer value) {
            addCriterion("new_show <>", value, "newShow");
            return (Criteria) this;
        }

        public Criteria andNewShowGreaterThan(Integer value) {
            addCriterion("new_show >", value, "newShow");
            return (Criteria) this;
        }

        public Criteria andNewShowGreaterThanOrEqualTo(Integer value) {
            addCriterion("new_show >=", value, "newShow");
            return (Criteria) this;
        }

        public Criteria andNewShowLessThan(Integer value) {
            addCriterion("new_show <", value, "newShow");
            return (Criteria) this;
        }

        public Criteria andNewShowLessThanOrEqualTo(Integer value) {
            addCriterion("new_show <=", value, "newShow");
            return (Criteria) this;
        }

        public Criteria andNewShowIn(List<Integer> values) {
            addCriterion("new_show in", values, "newShow");
            return (Criteria) this;
        }

        public Criteria andNewShowNotIn(List<Integer> values) {
            addCriterion("new_show not in", values, "newShow");
            return (Criteria) this;
        }

        public Criteria andNewShowBetween(Integer value1, Integer value2) {
            addCriterion("new_show between", value1, value2, "newShow");
            return (Criteria) this;
        }

        public Criteria andNewShowNotBetween(Integer value1, Integer value2) {
            addCriterion("new_show not between", value1, value2, "newShow");
            return (Criteria) this;
        }

        public Criteria andTitlePicIsNull() {
            addCriterion("title_pic is null");
            return (Criteria) this;
        }

        public Criteria andTitlePicIsNotNull() {
            addCriterion("title_pic is not null");
            return (Criteria) this;
        }

        public Criteria andTitlePicEqualTo(String value) {
            addCriterion("title_pic =", value, "titlePic");
            return (Criteria) this;
        }

        public Criteria andTitlePicNotEqualTo(String value) {
            addCriterion("title_pic <>", value, "titlePic");
            return (Criteria) this;
        }

        public Criteria andTitlePicGreaterThan(String value) {
            addCriterion("title_pic >", value, "titlePic");
            return (Criteria) this;
        }

        public Criteria andTitlePicGreaterThanOrEqualTo(String value) {
            addCriterion("title_pic >=", value, "titlePic");
            return (Criteria) this;
        }

        public Criteria andTitlePicLessThan(String value) {
            addCriterion("title_pic <", value, "titlePic");
            return (Criteria) this;
        }

        public Criteria andTitlePicLessThanOrEqualTo(String value) {
            addCriterion("title_pic <=", value, "titlePic");
            return (Criteria) this;
        }

        public Criteria andTitlePicLike(String value) {
            addCriterion("title_pic like", value, "titlePic");
            return (Criteria) this;
        }

        public Criteria andTitlePicNotLike(String value) {
            addCriterion("title_pic not like", value, "titlePic");
            return (Criteria) this;
        }

        public Criteria andTitlePicIn(List<String> values) {
            addCriterion("title_pic in", values, "titlePic");
            return (Criteria) this;
        }

        public Criteria andTitlePicNotIn(List<String> values) {
            addCriterion("title_pic not in", values, "titlePic");
            return (Criteria) this;
        }

        public Criteria andTitlePicBetween(String value1, String value2) {
            addCriterion("title_pic between", value1, value2, "titlePic");
            return (Criteria) this;
        }

        public Criteria andTitlePicNotBetween(String value1, String value2) {
            addCriterion("title_pic not between", value1, value2, "titlePic");
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

        public Criteria andActIdIsNull() {
            addCriterion("act_id is null");
            return (Criteria) this;
        }

        public Criteria andActIdIsNotNull() {
            addCriterion("act_id is not null");
            return (Criteria) this;
        }

        public Criteria andActIdEqualTo(Integer value) {
            addCriterion("act_id =", value, "actId");
            return (Criteria) this;
        }

        public Criteria andActIdNotEqualTo(Integer value) {
            addCriterion("act_id <>", value, "actId");
            return (Criteria) this;
        }

        public Criteria andActIdGreaterThan(Integer value) {
            addCriterion("act_id >", value, "actId");
            return (Criteria) this;
        }

        public Criteria andActIdGreaterThanOrEqualTo(Integer value) {
            addCriterion("act_id >=", value, "actId");
            return (Criteria) this;
        }

        public Criteria andActIdLessThan(Integer value) {
            addCriterion("act_id <", value, "actId");
            return (Criteria) this;
        }

        public Criteria andActIdLessThanOrEqualTo(Integer value) {
            addCriterion("act_id <=", value, "actId");
            return (Criteria) this;
        }

        public Criteria andActIdIn(List<Integer> values) {
            addCriterion("act_id in", values, "actId");
            return (Criteria) this;
        }

        public Criteria andActIdNotIn(List<Integer> values) {
            addCriterion("act_id not in", values, "actId");
            return (Criteria) this;
        }

        public Criteria andActIdBetween(Integer value1, Integer value2) {
            addCriterion("act_id between", value1, value2, "actId");
            return (Criteria) this;
        }

        public Criteria andActIdNotBetween(Integer value1, Integer value2) {
            addCriterion("act_id not between", value1, value2, "actId");
            return (Criteria) this;
        }
    }

    /**
     * 类注释
     * Criteria
     * 数据库表：product_recommend
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
     * 数据库表：product_recommend
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