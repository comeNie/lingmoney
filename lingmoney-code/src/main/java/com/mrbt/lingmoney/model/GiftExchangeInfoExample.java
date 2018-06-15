package com.mrbt.lingmoney.model;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;

public class GiftExchangeInfoExample {
    /**
     * 排序字段,gift_exchange_info
     */
    protected String orderByClause;

    /**
     * 是否过滤重复数据,gift_exchange_info
     */
    protected boolean distinct;

    /**
     * 查询条件实例,gift_exchange_info
     */
    protected List<Criteria> oredCriteria;

    /**
     * 第一个返回记录行的偏移量,gift_exchange_info
     */
    protected int limitStart = -1;

    /**
     * 返回记录行的最大数目,gift_exchange_info
     */
    protected int limitEnd = -1;

    /**
     *  构造查询条件,gift_exchange_info
     */
    public GiftExchangeInfoExample() {
        oredCriteria = new ArrayList<Criteria>();
    }

    /**
     *  设置排序字段,gift_exchange_info
     * @param orderByClause 排序字段
     */
    public void setOrderByClause(String orderByClause) {
        this.orderByClause = orderByClause;
    }

    /**
     *  获取排序字段,gift_exchange_info
     */
    public String getOrderByClause() {
        return orderByClause;
    }

    /**
     *  设置过滤重复数据,gift_exchange_info
     * @param distinct 是否过滤重复数据
     */
    public void setDistinct(boolean distinct) {
        this.distinct = distinct;
    }

    /**
     *  是否过滤重复数据,gift_exchange_info
     */
    public boolean isDistinct() {
        return distinct;
    }

    /**
     *  获取当前的查询条件实例,gift_exchange_info
     */
    public List<Criteria> getOredCriteria() {
        return oredCriteria;
    }

    /**
     *  增加或者的查询条件,用于构建或者查询,gift_exchange_info
     * @param criteria 过滤条件实例
     */
    public void or(Criteria criteria) {
        oredCriteria.add(criteria);
    }

    /**
     *  创建一个新的或者查询条件,gift_exchange_info
     */
    public Criteria or() {
        Criteria criteria = createCriteriaInternal();
        oredCriteria.add(criteria);
        return criteria;
    }

    /**
     *  创建一个查询条件,gift_exchange_info
     */
    public Criteria createCriteria() {
        Criteria criteria = createCriteriaInternal();
        if (oredCriteria.size() == 0) {
            oredCriteria.add(criteria);
        }
        return criteria;
    }

    /**
     *  内部构建查询条件对象,gift_exchange_info
     */
    protected Criteria createCriteriaInternal() {
        Criteria criteria = new Criteria();
        return criteria;
    }

    /**
     *  清除查询条件,gift_exchange_info
     */
    public void clear() {
        oredCriteria.clear();
        orderByClause = null;
        distinct = false;
    }

    /**
     *  设置第一个返回记录行的偏移量,gift_exchange_info
     * @param limitStart 第一个返回记录行的偏移量
     */
    public void setLimitStart(int limitStart) {
        this.limitStart=limitStart;
    }

    /**
     *  获取第一个返回记录行的偏移量,gift_exchange_info
     */
    public int getLimitStart() {
        return limitStart;
    }

    /**
     *  设置返回记录行的最大数目,gift_exchange_info
     * @param limitEnd 返回记录行的最大数目
     */
    public void setLimitEnd(int limitEnd) {
        this.limitEnd=limitEnd;
    }

    /**
     *  获取返回记录行的最大数目,gift_exchange_info
     */
    public int getLimitEnd() {
        return limitEnd;
    }

    /**
     * 类注释
     * GeneratedCriteria
     * 数据库表：gift_exchange_info
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

        protected void addCriterionForJDBCDate(String condition, Date value, String property) {
            if (value == null) {
                throw new RuntimeException("Value for " + property + " cannot be null");
            }
            addCriterion(condition, new java.sql.Date(value.getTime()), property);
        }

        protected void addCriterionForJDBCDate(String condition, List<Date> values, String property) {
            if (values == null || values.size() == 0) {
                throw new RuntimeException("Value list for " + property + " cannot be null or empty");
            }
            List<java.sql.Date> dateList = new ArrayList<java.sql.Date>();
            Iterator<Date> iter = values.iterator();
            while (iter.hasNext()) {
                dateList.add(new java.sql.Date(iter.next().getTime()));
            }
            addCriterion(condition, dateList, property);
        }

        protected void addCriterionForJDBCDate(String condition, Date value1, Date value2, String property) {
            if (value1 == null || value2 == null) {
                throw new RuntimeException("Between values for " + property + " cannot be null");
            }
            addCriterion(condition, new java.sql.Date(value1.getTime()), new java.sql.Date(value2.getTime()), property);
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

        public Criteria andReferralIdIsNull() {
            addCriterion("referral_id is null");
            return (Criteria) this;
        }

        public Criteria andReferralIdIsNotNull() {
            addCriterion("referral_id is not null");
            return (Criteria) this;
        }

        public Criteria andReferralIdEqualTo(String value) {
            addCriterion("referral_id =", value, "referralId");
            return (Criteria) this;
        }

        public Criteria andReferralIdNotEqualTo(String value) {
            addCriterion("referral_id <>", value, "referralId");
            return (Criteria) this;
        }

        public Criteria andReferralIdGreaterThan(String value) {
            addCriterion("referral_id >", value, "referralId");
            return (Criteria) this;
        }

        public Criteria andReferralIdGreaterThanOrEqualTo(String value) {
            addCriterion("referral_id >=", value, "referralId");
            return (Criteria) this;
        }

        public Criteria andReferralIdLessThan(String value) {
            addCriterion("referral_id <", value, "referralId");
            return (Criteria) this;
        }

        public Criteria andReferralIdLessThanOrEqualTo(String value) {
            addCriterion("referral_id <=", value, "referralId");
            return (Criteria) this;
        }

        public Criteria andReferralIdLike(String value) {
            addCriterion("referral_id like", value, "referralId");
            return (Criteria) this;
        }

        public Criteria andReferralIdNotLike(String value) {
            addCriterion("referral_id not like", value, "referralId");
            return (Criteria) this;
        }

        public Criteria andReferralIdIn(List<String> values) {
            addCriterion("referral_id in", values, "referralId");
            return (Criteria) this;
        }

        public Criteria andReferralIdNotIn(List<String> values) {
            addCriterion("referral_id not in", values, "referralId");
            return (Criteria) this;
        }

        public Criteria andReferralIdBetween(String value1, String value2) {
            addCriterion("referral_id between", value1, value2, "referralId");
            return (Criteria) this;
        }

        public Criteria andReferralIdNotBetween(String value1, String value2) {
            addCriterion("referral_id not between", value1, value2, "referralId");
            return (Criteria) this;
        }

        public Criteria andUIdIsNull() {
            addCriterion("u_id is null");
            return (Criteria) this;
        }

        public Criteria andUIdIsNotNull() {
            addCriterion("u_id is not null");
            return (Criteria) this;
        }

        public Criteria andUIdEqualTo(String value) {
            addCriterion("u_id =", value, "uId");
            return (Criteria) this;
        }

        public Criteria andUIdNotEqualTo(String value) {
            addCriterion("u_id <>", value, "uId");
            return (Criteria) this;
        }

        public Criteria andUIdGreaterThan(String value) {
            addCriterion("u_id >", value, "uId");
            return (Criteria) this;
        }

        public Criteria andUIdGreaterThanOrEqualTo(String value) {
            addCriterion("u_id >=", value, "uId");
            return (Criteria) this;
        }

        public Criteria andUIdLessThan(String value) {
            addCriterion("u_id <", value, "uId");
            return (Criteria) this;
        }

        public Criteria andUIdLessThanOrEqualTo(String value) {
            addCriterion("u_id <=", value, "uId");
            return (Criteria) this;
        }

        public Criteria andUIdLike(String value) {
            addCriterion("u_id like", value, "uId");
            return (Criteria) this;
        }

        public Criteria andUIdNotLike(String value) {
            addCriterion("u_id not like", value, "uId");
            return (Criteria) this;
        }

        public Criteria andUIdIn(List<String> values) {
            addCriterion("u_id in", values, "uId");
            return (Criteria) this;
        }

        public Criteria andUIdNotIn(List<String> values) {
            addCriterion("u_id not in", values, "uId");
            return (Criteria) this;
        }

        public Criteria andUIdBetween(String value1, String value2) {
            addCriterion("u_id between", value1, value2, "uId");
            return (Criteria) this;
        }

        public Criteria andUIdNotBetween(String value1, String value2) {
            addCriterion("u_id not between", value1, value2, "uId");
            return (Criteria) this;
        }

        public Criteria andActivityIdIsNull() {
            addCriterion("activity_id is null");
            return (Criteria) this;
        }

        public Criteria andActivityIdIsNotNull() {
            addCriterion("activity_id is not null");
            return (Criteria) this;
        }

        public Criteria andActivityIdEqualTo(Integer value) {
            addCriterion("activity_id =", value, "activityId");
            return (Criteria) this;
        }

        public Criteria andActivityIdNotEqualTo(Integer value) {
            addCriterion("activity_id <>", value, "activityId");
            return (Criteria) this;
        }

        public Criteria andActivityIdGreaterThan(Integer value) {
            addCriterion("activity_id >", value, "activityId");
            return (Criteria) this;
        }

        public Criteria andActivityIdGreaterThanOrEqualTo(Integer value) {
            addCriterion("activity_id >=", value, "activityId");
            return (Criteria) this;
        }

        public Criteria andActivityIdLessThan(Integer value) {
            addCriterion("activity_id <", value, "activityId");
            return (Criteria) this;
        }

        public Criteria andActivityIdLessThanOrEqualTo(Integer value) {
            addCriterion("activity_id <=", value, "activityId");
            return (Criteria) this;
        }

        public Criteria andActivityIdIn(List<Integer> values) {
            addCriterion("activity_id in", values, "activityId");
            return (Criteria) this;
        }

        public Criteria andActivityIdNotIn(List<Integer> values) {
            addCriterion("activity_id not in", values, "activityId");
            return (Criteria) this;
        }

        public Criteria andActivityIdBetween(Integer value1, Integer value2) {
            addCriterion("activity_id between", value1, value2, "activityId");
            return (Criteria) this;
        }

        public Criteria andActivityIdNotBetween(Integer value1, Integer value2) {
            addCriterion("activity_id not between", value1, value2, "activityId");
            return (Criteria) this;
        }

        public Criteria andBuyMoneyIsNull() {
            addCriterion("buy_money is null");
            return (Criteria) this;
        }

        public Criteria andBuyMoneyIsNotNull() {
            addCriterion("buy_money is not null");
            return (Criteria) this;
        }

        public Criteria andBuyMoneyEqualTo(BigDecimal value) {
            addCriterion("buy_money =", value, "buyMoney");
            return (Criteria) this;
        }

        public Criteria andBuyMoneyNotEqualTo(BigDecimal value) {
            addCriterion("buy_money <>", value, "buyMoney");
            return (Criteria) this;
        }

        public Criteria andBuyMoneyGreaterThan(BigDecimal value) {
            addCriterion("buy_money >", value, "buyMoney");
            return (Criteria) this;
        }

        public Criteria andBuyMoneyGreaterThanOrEqualTo(BigDecimal value) {
            addCriterion("buy_money >=", value, "buyMoney");
            return (Criteria) this;
        }

        public Criteria andBuyMoneyLessThan(BigDecimal value) {
            addCriterion("buy_money <", value, "buyMoney");
            return (Criteria) this;
        }

        public Criteria andBuyMoneyLessThanOrEqualTo(BigDecimal value) {
            addCriterion("buy_money <=", value, "buyMoney");
            return (Criteria) this;
        }

        public Criteria andBuyMoneyIn(List<BigDecimal> values) {
            addCriterion("buy_money in", values, "buyMoney");
            return (Criteria) this;
        }

        public Criteria andBuyMoneyNotIn(List<BigDecimal> values) {
            addCriterion("buy_money not in", values, "buyMoney");
            return (Criteria) this;
        }

        public Criteria andBuyMoneyBetween(BigDecimal value1, BigDecimal value2) {
            addCriterion("buy_money between", value1, value2, "buyMoney");
            return (Criteria) this;
        }

        public Criteria andBuyMoneyNotBetween(BigDecimal value1, BigDecimal value2) {
            addCriterion("buy_money not between", value1, value2, "buyMoney");
            return (Criteria) this;
        }

        public Criteria andProductNameIsNull() {
            addCriterion("product_name is null");
            return (Criteria) this;
        }

        public Criteria andProductNameIsNotNull() {
            addCriterion("product_name is not null");
            return (Criteria) this;
        }

        public Criteria andProductNameEqualTo(String value) {
            addCriterion("product_name =", value, "productName");
            return (Criteria) this;
        }

        public Criteria andProductNameNotEqualTo(String value) {
            addCriterion("product_name <>", value, "productName");
            return (Criteria) this;
        }

        public Criteria andProductNameGreaterThan(String value) {
            addCriterion("product_name >", value, "productName");
            return (Criteria) this;
        }

        public Criteria andProductNameGreaterThanOrEqualTo(String value) {
            addCriterion("product_name >=", value, "productName");
            return (Criteria) this;
        }

        public Criteria andProductNameLessThan(String value) {
            addCriterion("product_name <", value, "productName");
            return (Criteria) this;
        }

        public Criteria andProductNameLessThanOrEqualTo(String value) {
            addCriterion("product_name <=", value, "productName");
            return (Criteria) this;
        }

        public Criteria andProductNameLike(String value) {
            addCriterion("product_name like", value, "productName");
            return (Criteria) this;
        }

        public Criteria andProductNameNotLike(String value) {
            addCriterion("product_name not like", value, "productName");
            return (Criteria) this;
        }

        public Criteria andProductNameIn(List<String> values) {
            addCriterion("product_name in", values, "productName");
            return (Criteria) this;
        }

        public Criteria andProductNameNotIn(List<String> values) {
            addCriterion("product_name not in", values, "productName");
            return (Criteria) this;
        }

        public Criteria andProductNameBetween(String value1, String value2) {
            addCriterion("product_name between", value1, value2, "productName");
            return (Criteria) this;
        }

        public Criteria andProductNameNotBetween(String value1, String value2) {
            addCriterion("product_name not between", value1, value2, "productName");
            return (Criteria) this;
        }

        public Criteria andBuyDtIsNull() {
            addCriterion("buy_dt is null");
            return (Criteria) this;
        }

        public Criteria andBuyDtIsNotNull() {
            addCriterion("buy_dt is not null");
            return (Criteria) this;
        }

        public Criteria andBuyDtEqualTo(Date value) {
            addCriterionForJDBCDate("buy_dt =", value, "buyDt");
            return (Criteria) this;
        }

        public Criteria andBuyDtNotEqualTo(Date value) {
            addCriterionForJDBCDate("buy_dt <>", value, "buyDt");
            return (Criteria) this;
        }

        public Criteria andBuyDtGreaterThan(Date value) {
            addCriterionForJDBCDate("buy_dt >", value, "buyDt");
            return (Criteria) this;
        }

        public Criteria andBuyDtGreaterThanOrEqualTo(Date value) {
            addCriterionForJDBCDate("buy_dt >=", value, "buyDt");
            return (Criteria) this;
        }

        public Criteria andBuyDtLessThan(Date value) {
            addCriterionForJDBCDate("buy_dt <", value, "buyDt");
            return (Criteria) this;
        }

        public Criteria andBuyDtLessThanOrEqualTo(Date value) {
            addCriterionForJDBCDate("buy_dt <=", value, "buyDt");
            return (Criteria) this;
        }

        public Criteria andBuyDtIn(List<Date> values) {
            addCriterionForJDBCDate("buy_dt in", values, "buyDt");
            return (Criteria) this;
        }

        public Criteria andBuyDtNotIn(List<Date> values) {
            addCriterionForJDBCDate("buy_dt not in", values, "buyDt");
            return (Criteria) this;
        }

        public Criteria andBuyDtBetween(Date value1, Date value2) {
            addCriterionForJDBCDate("buy_dt between", value1, value2, "buyDt");
            return (Criteria) this;
        }

        public Criteria andBuyDtNotBetween(Date value1, Date value2) {
            addCriterionForJDBCDate("buy_dt not between", value1, value2, "buyDt");
            return (Criteria) this;
        }

        public Criteria andGiftNameIsNull() {
            addCriterion("gift_name is null");
            return (Criteria) this;
        }

        public Criteria andGiftNameIsNotNull() {
            addCriterion("gift_name is not null");
            return (Criteria) this;
        }

        public Criteria andGiftNameEqualTo(String value) {
            addCriterion("gift_name =", value, "giftName");
            return (Criteria) this;
        }

        public Criteria andGiftNameNotEqualTo(String value) {
            addCriterion("gift_name <>", value, "giftName");
            return (Criteria) this;
        }

        public Criteria andGiftNameGreaterThan(String value) {
            addCriterion("gift_name >", value, "giftName");
            return (Criteria) this;
        }

        public Criteria andGiftNameGreaterThanOrEqualTo(String value) {
            addCriterion("gift_name >=", value, "giftName");
            return (Criteria) this;
        }

        public Criteria andGiftNameLessThan(String value) {
            addCriterion("gift_name <", value, "giftName");
            return (Criteria) this;
        }

        public Criteria andGiftNameLessThanOrEqualTo(String value) {
            addCriterion("gift_name <=", value, "giftName");
            return (Criteria) this;
        }

        public Criteria andGiftNameLike(String value) {
            addCriterion("gift_name like", value, "giftName");
            return (Criteria) this;
        }

        public Criteria andGiftNameNotLike(String value) {
            addCriterion("gift_name not like", value, "giftName");
            return (Criteria) this;
        }

        public Criteria andGiftNameIn(List<String> values) {
            addCriterion("gift_name in", values, "giftName");
            return (Criteria) this;
        }

        public Criteria andGiftNameNotIn(List<String> values) {
            addCriterion("gift_name not in", values, "giftName");
            return (Criteria) this;
        }

        public Criteria andGiftNameBetween(String value1, String value2) {
            addCriterion("gift_name between", value1, value2, "giftName");
            return (Criteria) this;
        }

        public Criteria andGiftNameNotBetween(String value1, String value2) {
            addCriterion("gift_name not between", value1, value2, "giftName");
            return (Criteria) this;
        }

        public Criteria andNumIsNull() {
            addCriterion("num is null");
            return (Criteria) this;
        }

        public Criteria andNumIsNotNull() {
            addCriterion("num is not null");
            return (Criteria) this;
        }

        public Criteria andNumEqualTo(Integer value) {
            addCriterion("num =", value, "num");
            return (Criteria) this;
        }

        public Criteria andNumNotEqualTo(Integer value) {
            addCriterion("num <>", value, "num");
            return (Criteria) this;
        }

        public Criteria andNumGreaterThan(Integer value) {
            addCriterion("num >", value, "num");
            return (Criteria) this;
        }

        public Criteria andNumGreaterThanOrEqualTo(Integer value) {
            addCriterion("num >=", value, "num");
            return (Criteria) this;
        }

        public Criteria andNumLessThan(Integer value) {
            addCriterion("num <", value, "num");
            return (Criteria) this;
        }

        public Criteria andNumLessThanOrEqualTo(Integer value) {
            addCriterion("num <=", value, "num");
            return (Criteria) this;
        }

        public Criteria andNumIn(List<Integer> values) {
            addCriterion("num in", values, "num");
            return (Criteria) this;
        }

        public Criteria andNumNotIn(List<Integer> values) {
            addCriterion("num not in", values, "num");
            return (Criteria) this;
        }

        public Criteria andNumBetween(Integer value1, Integer value2) {
            addCriterion("num between", value1, value2, "num");
            return (Criteria) this;
        }

        public Criteria andNumNotBetween(Integer value1, Integer value2) {
            addCriterion("num not between", value1, value2, "num");
            return (Criteria) this;
        }

        public Criteria andExpressNumberIsNull() {
            addCriterion("express_number is null");
            return (Criteria) this;
        }

        public Criteria andExpressNumberIsNotNull() {
            addCriterion("express_number is not null");
            return (Criteria) this;
        }

        public Criteria andExpressNumberEqualTo(String value) {
            addCriterion("express_number =", value, "expressNumber");
            return (Criteria) this;
        }

        public Criteria andExpressNumberNotEqualTo(String value) {
            addCriterion("express_number <>", value, "expressNumber");
            return (Criteria) this;
        }

        public Criteria andExpressNumberGreaterThan(String value) {
            addCriterion("express_number >", value, "expressNumber");
            return (Criteria) this;
        }

        public Criteria andExpressNumberGreaterThanOrEqualTo(String value) {
            addCriterion("express_number >=", value, "expressNumber");
            return (Criteria) this;
        }

        public Criteria andExpressNumberLessThan(String value) {
            addCriterion("express_number <", value, "expressNumber");
            return (Criteria) this;
        }

        public Criteria andExpressNumberLessThanOrEqualTo(String value) {
            addCriterion("express_number <=", value, "expressNumber");
            return (Criteria) this;
        }

        public Criteria andExpressNumberLike(String value) {
            addCriterion("express_number like", value, "expressNumber");
            return (Criteria) this;
        }

        public Criteria andExpressNumberNotLike(String value) {
            addCriterion("express_number not like", value, "expressNumber");
            return (Criteria) this;
        }

        public Criteria andExpressNumberIn(List<String> values) {
            addCriterion("express_number in", values, "expressNumber");
            return (Criteria) this;
        }

        public Criteria andExpressNumberNotIn(List<String> values) {
            addCriterion("express_number not in", values, "expressNumber");
            return (Criteria) this;
        }

        public Criteria andExpressNumberBetween(String value1, String value2) {
            addCriterion("express_number between", value1, value2, "expressNumber");
            return (Criteria) this;
        }

        public Criteria andExpressNumberNotBetween(String value1, String value2) {
            addCriterion("express_number not between", value1, value2, "expressNumber");
            return (Criteria) this;
        }

        public Criteria andExpressCompanyIsNull() {
            addCriterion("express_company is null");
            return (Criteria) this;
        }

        public Criteria andExpressCompanyIsNotNull() {
            addCriterion("express_company is not null");
            return (Criteria) this;
        }

        public Criteria andExpressCompanyEqualTo(String value) {
            addCriterion("express_company =", value, "expressCompany");
            return (Criteria) this;
        }

        public Criteria andExpressCompanyNotEqualTo(String value) {
            addCriterion("express_company <>", value, "expressCompany");
            return (Criteria) this;
        }

        public Criteria andExpressCompanyGreaterThan(String value) {
            addCriterion("express_company >", value, "expressCompany");
            return (Criteria) this;
        }

        public Criteria andExpressCompanyGreaterThanOrEqualTo(String value) {
            addCriterion("express_company >=", value, "expressCompany");
            return (Criteria) this;
        }

        public Criteria andExpressCompanyLessThan(String value) {
            addCriterion("express_company <", value, "expressCompany");
            return (Criteria) this;
        }

        public Criteria andExpressCompanyLessThanOrEqualTo(String value) {
            addCriterion("express_company <=", value, "expressCompany");
            return (Criteria) this;
        }

        public Criteria andExpressCompanyLike(String value) {
            addCriterion("express_company like", value, "expressCompany");
            return (Criteria) this;
        }

        public Criteria andExpressCompanyNotLike(String value) {
            addCriterion("express_company not like", value, "expressCompany");
            return (Criteria) this;
        }

        public Criteria andExpressCompanyIn(List<String> values) {
            addCriterion("express_company in", values, "expressCompany");
            return (Criteria) this;
        }

        public Criteria andExpressCompanyNotIn(List<String> values) {
            addCriterion("express_company not in", values, "expressCompany");
            return (Criteria) this;
        }

        public Criteria andExpressCompanyBetween(String value1, String value2) {
            addCriterion("express_company between", value1, value2, "expressCompany");
            return (Criteria) this;
        }

        public Criteria andExpressCompanyNotBetween(String value1, String value2) {
            addCriterion("express_company not between", value1, value2, "expressCompany");
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

        public Criteria andExchangeTimeIsNull() {
            addCriterion("exchange_time is null");
            return (Criteria) this;
        }

        public Criteria andExchangeTimeIsNotNull() {
            addCriterion("exchange_time is not null");
            return (Criteria) this;
        }

        public Criteria andExchangeTimeEqualTo(Date value) {
            addCriterion("exchange_time =", value, "exchangeTime");
            return (Criteria) this;
        }

        public Criteria andExchangeTimeNotEqualTo(Date value) {
            addCriterion("exchange_time <>", value, "exchangeTime");
            return (Criteria) this;
        }

        public Criteria andExchangeTimeGreaterThan(Date value) {
            addCriterion("exchange_time >", value, "exchangeTime");
            return (Criteria) this;
        }

        public Criteria andExchangeTimeGreaterThanOrEqualTo(Date value) {
            addCriterion("exchange_time >=", value, "exchangeTime");
            return (Criteria) this;
        }

        public Criteria andExchangeTimeLessThan(Date value) {
            addCriterion("exchange_time <", value, "exchangeTime");
            return (Criteria) this;
        }

        public Criteria andExchangeTimeLessThanOrEqualTo(Date value) {
            addCriterion("exchange_time <=", value, "exchangeTime");
            return (Criteria) this;
        }

        public Criteria andExchangeTimeIn(List<Date> values) {
            addCriterion("exchange_time in", values, "exchangeTime");
            return (Criteria) this;
        }

        public Criteria andExchangeTimeNotIn(List<Date> values) {
            addCriterion("exchange_time not in", values, "exchangeTime");
            return (Criteria) this;
        }

        public Criteria andExchangeTimeBetween(Date value1, Date value2) {
            addCriterion("exchange_time between", value1, value2, "exchangeTime");
            return (Criteria) this;
        }

        public Criteria andExchangeTimeNotBetween(Date value1, Date value2) {
            addCriterion("exchange_time not between", value1, value2, "exchangeTime");
            return (Criteria) this;
        }

        public Criteria andSendTimeIsNull() {
            addCriterion("send_time is null");
            return (Criteria) this;
        }

        public Criteria andSendTimeIsNotNull() {
            addCriterion("send_time is not null");
            return (Criteria) this;
        }

        public Criteria andSendTimeEqualTo(Date value) {
            addCriterion("send_time =", value, "sendTime");
            return (Criteria) this;
        }

        public Criteria andSendTimeNotEqualTo(Date value) {
            addCriterion("send_time <>", value, "sendTime");
            return (Criteria) this;
        }

        public Criteria andSendTimeGreaterThan(Date value) {
            addCriterion("send_time >", value, "sendTime");
            return (Criteria) this;
        }

        public Criteria andSendTimeGreaterThanOrEqualTo(Date value) {
            addCriterion("send_time >=", value, "sendTime");
            return (Criteria) this;
        }

        public Criteria andSendTimeLessThan(Date value) {
            addCriterion("send_time <", value, "sendTime");
            return (Criteria) this;
        }

        public Criteria andSendTimeLessThanOrEqualTo(Date value) {
            addCriterion("send_time <=", value, "sendTime");
            return (Criteria) this;
        }

        public Criteria andSendTimeIn(List<Date> values) {
            addCriterion("send_time in", values, "sendTime");
            return (Criteria) this;
        }

        public Criteria andSendTimeNotIn(List<Date> values) {
            addCriterion("send_time not in", values, "sendTime");
            return (Criteria) this;
        }

        public Criteria andSendTimeBetween(Date value1, Date value2) {
            addCriterion("send_time between", value1, value2, "sendTime");
            return (Criteria) this;
        }

        public Criteria andSendTimeNotBetween(Date value1, Date value2) {
            addCriterion("send_time not between", value1, value2, "sendTime");
            return (Criteria) this;
        }

        public Criteria andReceiveTimeIsNull() {
            addCriterion("receive_time is null");
            return (Criteria) this;
        }

        public Criteria andReceiveTimeIsNotNull() {
            addCriterion("receive_time is not null");
            return (Criteria) this;
        }

        public Criteria andReceiveTimeEqualTo(Date value) {
            addCriterion("receive_time =", value, "receiveTime");
            return (Criteria) this;
        }

        public Criteria andReceiveTimeNotEqualTo(Date value) {
            addCriterion("receive_time <>", value, "receiveTime");
            return (Criteria) this;
        }

        public Criteria andReceiveTimeGreaterThan(Date value) {
            addCriterion("receive_time >", value, "receiveTime");
            return (Criteria) this;
        }

        public Criteria andReceiveTimeGreaterThanOrEqualTo(Date value) {
            addCriterion("receive_time >=", value, "receiveTime");
            return (Criteria) this;
        }

        public Criteria andReceiveTimeLessThan(Date value) {
            addCriterion("receive_time <", value, "receiveTime");
            return (Criteria) this;
        }

        public Criteria andReceiveTimeLessThanOrEqualTo(Date value) {
            addCriterion("receive_time <=", value, "receiveTime");
            return (Criteria) this;
        }

        public Criteria andReceiveTimeIn(List<Date> values) {
            addCriterion("receive_time in", values, "receiveTime");
            return (Criteria) this;
        }

        public Criteria andReceiveTimeNotIn(List<Date> values) {
            addCriterion("receive_time not in", values, "receiveTime");
            return (Criteria) this;
        }

        public Criteria andReceiveTimeBetween(Date value1, Date value2) {
            addCriterion("receive_time between", value1, value2, "receiveTime");
            return (Criteria) this;
        }

        public Criteria andReceiveTimeNotBetween(Date value1, Date value2) {
            addCriterion("receive_time not between", value1, value2, "receiveTime");
            return (Criteria) this;
        }

        public Criteria andTypeIsNull() {
            addCriterion("type is null");
            return (Criteria) this;
        }

        public Criteria andTypeIsNotNull() {
            addCriterion("type is not null");
            return (Criteria) this;
        }

        public Criteria andTypeEqualTo(Integer value) {
            addCriterion("type =", value, "type");
            return (Criteria) this;
        }

        public Criteria andTypeNotEqualTo(Integer value) {
            addCriterion("type <>", value, "type");
            return (Criteria) this;
        }

        public Criteria andTypeGreaterThan(Integer value) {
            addCriterion("type >", value, "type");
            return (Criteria) this;
        }

        public Criteria andTypeGreaterThanOrEqualTo(Integer value) {
            addCriterion("type >=", value, "type");
            return (Criteria) this;
        }

        public Criteria andTypeLessThan(Integer value) {
            addCriterion("type <", value, "type");
            return (Criteria) this;
        }

        public Criteria andTypeLessThanOrEqualTo(Integer value) {
            addCriterion("type <=", value, "type");
            return (Criteria) this;
        }

        public Criteria andTypeIn(List<Integer> values) {
            addCriterion("type in", values, "type");
            return (Criteria) this;
        }

        public Criteria andTypeNotIn(List<Integer> values) {
            addCriterion("type not in", values, "type");
            return (Criteria) this;
        }

        public Criteria andTypeBetween(Integer value1, Integer value2) {
            addCriterion("type between", value1, value2, "type");
            return (Criteria) this;
        }

        public Criteria andTypeNotBetween(Integer value1, Integer value2) {
            addCriterion("type not between", value1, value2, "type");
            return (Criteria) this;
        }

        public Criteria andRechargeAccountIsNull() {
            addCriterion("recharge_account is null");
            return (Criteria) this;
        }

        public Criteria andRechargeAccountIsNotNull() {
            addCriterion("recharge_account is not null");
            return (Criteria) this;
        }

        public Criteria andRechargeAccountEqualTo(String value) {
            addCriterion("recharge_account =", value, "rechargeAccount");
            return (Criteria) this;
        }

        public Criteria andRechargeAccountNotEqualTo(String value) {
            addCriterion("recharge_account <>", value, "rechargeAccount");
            return (Criteria) this;
        }

        public Criteria andRechargeAccountGreaterThan(String value) {
            addCriterion("recharge_account >", value, "rechargeAccount");
            return (Criteria) this;
        }

        public Criteria andRechargeAccountGreaterThanOrEqualTo(String value) {
            addCriterion("recharge_account >=", value, "rechargeAccount");
            return (Criteria) this;
        }

        public Criteria andRechargeAccountLessThan(String value) {
            addCriterion("recharge_account <", value, "rechargeAccount");
            return (Criteria) this;
        }

        public Criteria andRechargeAccountLessThanOrEqualTo(String value) {
            addCriterion("recharge_account <=", value, "rechargeAccount");
            return (Criteria) this;
        }

        public Criteria andRechargeAccountLike(String value) {
            addCriterion("recharge_account like", value, "rechargeAccount");
            return (Criteria) this;
        }

        public Criteria andRechargeAccountNotLike(String value) {
            addCriterion("recharge_account not like", value, "rechargeAccount");
            return (Criteria) this;
        }

        public Criteria andRechargeAccountIn(List<String> values) {
            addCriterion("recharge_account in", values, "rechargeAccount");
            return (Criteria) this;
        }

        public Criteria andRechargeAccountNotIn(List<String> values) {
            addCriterion("recharge_account not in", values, "rechargeAccount");
            return (Criteria) this;
        }

        public Criteria andRechargeAccountBetween(String value1, String value2) {
            addCriterion("recharge_account between", value1, value2, "rechargeAccount");
            return (Criteria) this;
        }

        public Criteria andRechargeAccountNotBetween(String value1, String value2) {
            addCriterion("recharge_account not between", value1, value2, "rechargeAccount");
            return (Criteria) this;
        }

        public Criteria andRechargeCodeIsNull() {
            addCriterion("recharge_code is null");
            return (Criteria) this;
        }

        public Criteria andRechargeCodeIsNotNull() {
            addCriterion("recharge_code is not null");
            return (Criteria) this;
        }

        public Criteria andRechargeCodeEqualTo(String value) {
            addCriterion("recharge_code =", value, "rechargeCode");
            return (Criteria) this;
        }

        public Criteria andRechargeCodeNotEqualTo(String value) {
            addCriterion("recharge_code <>", value, "rechargeCode");
            return (Criteria) this;
        }

        public Criteria andRechargeCodeGreaterThan(String value) {
            addCriterion("recharge_code >", value, "rechargeCode");
            return (Criteria) this;
        }

        public Criteria andRechargeCodeGreaterThanOrEqualTo(String value) {
            addCriterion("recharge_code >=", value, "rechargeCode");
            return (Criteria) this;
        }

        public Criteria andRechargeCodeLessThan(String value) {
            addCriterion("recharge_code <", value, "rechargeCode");
            return (Criteria) this;
        }

        public Criteria andRechargeCodeLessThanOrEqualTo(String value) {
            addCriterion("recharge_code <=", value, "rechargeCode");
            return (Criteria) this;
        }

        public Criteria andRechargeCodeLike(String value) {
            addCriterion("recharge_code like", value, "rechargeCode");
            return (Criteria) this;
        }

        public Criteria andRechargeCodeNotLike(String value) {
            addCriterion("recharge_code not like", value, "rechargeCode");
            return (Criteria) this;
        }

        public Criteria andRechargeCodeIn(List<String> values) {
            addCriterion("recharge_code in", values, "rechargeCode");
            return (Criteria) this;
        }

        public Criteria andRechargeCodeNotIn(List<String> values) {
            addCriterion("recharge_code not in", values, "rechargeCode");
            return (Criteria) this;
        }

        public Criteria andRechargeCodeBetween(String value1, String value2) {
            addCriterion("recharge_code between", value1, value2, "rechargeCode");
            return (Criteria) this;
        }

        public Criteria andRechargeCodeNotBetween(String value1, String value2) {
            addCriterion("recharge_code not between", value1, value2, "rechargeCode");
            return (Criteria) this;
        }

        public Criteria andMobileIsNull() {
            addCriterion("mobile is null");
            return (Criteria) this;
        }

        public Criteria andMobileIsNotNull() {
            addCriterion("mobile is not null");
            return (Criteria) this;
        }

        public Criteria andMobileEqualTo(String value) {
            addCriterion("mobile =", value, "mobile");
            return (Criteria) this;
        }

        public Criteria andMobileNotEqualTo(String value) {
            addCriterion("mobile <>", value, "mobile");
            return (Criteria) this;
        }

        public Criteria andMobileGreaterThan(String value) {
            addCriterion("mobile >", value, "mobile");
            return (Criteria) this;
        }

        public Criteria andMobileGreaterThanOrEqualTo(String value) {
            addCriterion("mobile >=", value, "mobile");
            return (Criteria) this;
        }

        public Criteria andMobileLessThan(String value) {
            addCriterion("mobile <", value, "mobile");
            return (Criteria) this;
        }

        public Criteria andMobileLessThanOrEqualTo(String value) {
            addCriterion("mobile <=", value, "mobile");
            return (Criteria) this;
        }

        public Criteria andMobileLike(String value) {
            addCriterion("mobile like", value, "mobile");
            return (Criteria) this;
        }

        public Criteria andMobileNotLike(String value) {
            addCriterion("mobile not like", value, "mobile");
            return (Criteria) this;
        }

        public Criteria andMobileIn(List<String> values) {
            addCriterion("mobile in", values, "mobile");
            return (Criteria) this;
        }

        public Criteria andMobileNotIn(List<String> values) {
            addCriterion("mobile not in", values, "mobile");
            return (Criteria) this;
        }

        public Criteria andMobileBetween(String value1, String value2) {
            addCriterion("mobile between", value1, value2, "mobile");
            return (Criteria) this;
        }

        public Criteria andMobileNotBetween(String value1, String value2) {
            addCriterion("mobile not between", value1, value2, "mobile");
            return (Criteria) this;
        }

        public Criteria andCategoryIsNull() {
            addCriterion("category is null");
            return (Criteria) this;
        }

        public Criteria andCategoryIsNotNull() {
            addCriterion("category is not null");
            return (Criteria) this;
        }

        public Criteria andCategoryEqualTo(Integer value) {
            addCriterion("category =", value, "category");
            return (Criteria) this;
        }

        public Criteria andCategoryNotEqualTo(Integer value) {
            addCriterion("category <>", value, "category");
            return (Criteria) this;
        }

        public Criteria andCategoryGreaterThan(Integer value) {
            addCriterion("category >", value, "category");
            return (Criteria) this;
        }

        public Criteria andCategoryGreaterThanOrEqualTo(Integer value) {
            addCriterion("category >=", value, "category");
            return (Criteria) this;
        }

        public Criteria andCategoryLessThan(Integer value) {
            addCriterion("category <", value, "category");
            return (Criteria) this;
        }

        public Criteria andCategoryLessThanOrEqualTo(Integer value) {
            addCriterion("category <=", value, "category");
            return (Criteria) this;
        }

        public Criteria andCategoryIn(List<Integer> values) {
            addCriterion("category in", values, "category");
            return (Criteria) this;
        }

        public Criteria andCategoryNotIn(List<Integer> values) {
            addCriterion("category not in", values, "category");
            return (Criteria) this;
        }

        public Criteria andCategoryBetween(Integer value1, Integer value2) {
            addCriterion("category between", value1, value2, "category");
            return (Criteria) this;
        }

        public Criteria andCategoryNotBetween(Integer value1, Integer value2) {
            addCriterion("category not between", value1, value2, "category");
            return (Criteria) this;
        }
    }

    /**
     * 类注释
     * Criteria
     * 数据库表：gift_exchange_info
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
     * 数据库表：gift_exchange_info
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