package com.mrbt.lingmoney.model;

import java.util.ArrayList;
import java.util.List;

public class SupportBankExample {
    /**
     * 排序字段,support_bank
     */
    protected String orderByClause;

    /**
     * 是否过滤重复数据,support_bank
     */
    protected boolean distinct;

    /**
     * 查询条件实例,support_bank
     */
    protected List<Criteria> oredCriteria;

    /**
     * 第一个返回记录行的偏移量,support_bank
     */
    protected int limitStart = -1;

    /**
     * 返回记录行的最大数目,support_bank
     */
    protected int limitEnd = -1;

    /**
     *  构造查询条件,support_bank
     */
    public SupportBankExample() {
        oredCriteria = new ArrayList<Criteria>();
    }

    /**
     *  设置排序字段,support_bank
     * @param orderByClause 排序字段
     */
    public void setOrderByClause(String orderByClause) {
        this.orderByClause = orderByClause;
    }

    /**
     *  获取排序字段,support_bank
     */
    public String getOrderByClause() {
        return orderByClause;
    }

    /**
     *  设置过滤重复数据,support_bank
     * @param distinct 是否过滤重复数据
     */
    public void setDistinct(boolean distinct) {
        this.distinct = distinct;
    }

    /**
     *  是否过滤重复数据,support_bank
     */
    public boolean isDistinct() {
        return distinct;
    }

    /**
     *  获取当前的查询条件实例,support_bank
     */
    public List<Criteria> getOredCriteria() {
        return oredCriteria;
    }

    /**
     *  增加或者的查询条件,用于构建或者查询,support_bank
     * @param criteria 过滤条件实例
     */
    public void or(Criteria criteria) {
        oredCriteria.add(criteria);
    }

    /**
     *  创建一个新的或者查询条件,support_bank
     */
    public Criteria or() {
        Criteria criteria = createCriteriaInternal();
        oredCriteria.add(criteria);
        return criteria;
    }

    /**
     *  创建一个查询条件,support_bank
     */
    public Criteria createCriteria() {
        Criteria criteria = createCriteriaInternal();
        if (oredCriteria.size() == 0) {
            oredCriteria.add(criteria);
        }
        return criteria;
    }

    /**
     *  内部构建查询条件对象,support_bank
     */
    protected Criteria createCriteriaInternal() {
        Criteria criteria = new Criteria();
        return criteria;
    }

    /**
     *  清除查询条件,support_bank
     */
    public void clear() {
        oredCriteria.clear();
        orderByClause = null;
        distinct = false;
    }

    /**
     *  设置第一个返回记录行的偏移量,support_bank
     * @param limitStart 第一个返回记录行的偏移量
     */
    public void setLimitStart(int limitStart) {
        this.limitStart=limitStart;
    }

    /**
     *  获取第一个返回记录行的偏移量,support_bank
     */
    public int getLimitStart() {
        return limitStart;
    }

    /**
     *  设置返回记录行的最大数目,support_bank
     * @param limitEnd 返回记录行的最大数目
     */
    public void setLimitEnd(int limitEnd) {
        this.limitEnd=limitEnd;
    }

    /**
     *  获取返回记录行的最大数目,support_bank
     */
    public int getLimitEnd() {
        return limitEnd;
    }

    /**
     * 类注释
     * GeneratedCriteria
     * 数据库表：support_bank
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

        public Criteria andBankNameIsNull() {
            addCriterion("bank_name is null");
            return (Criteria) this;
        }

        public Criteria andBankNameIsNotNull() {
            addCriterion("bank_name is not null");
            return (Criteria) this;
        }

        public Criteria andBankNameEqualTo(String value) {
            addCriterion("bank_name =", value, "bankName");
            return (Criteria) this;
        }

        public Criteria andBankNameNotEqualTo(String value) {
            addCriterion("bank_name <>", value, "bankName");
            return (Criteria) this;
        }

        public Criteria andBankNameGreaterThan(String value) {
            addCriterion("bank_name >", value, "bankName");
            return (Criteria) this;
        }

        public Criteria andBankNameGreaterThanOrEqualTo(String value) {
            addCriterion("bank_name >=", value, "bankName");
            return (Criteria) this;
        }

        public Criteria andBankNameLessThan(String value) {
            addCriterion("bank_name <", value, "bankName");
            return (Criteria) this;
        }

        public Criteria andBankNameLessThanOrEqualTo(String value) {
            addCriterion("bank_name <=", value, "bankName");
            return (Criteria) this;
        }

        public Criteria andBankNameLike(String value) {
            addCriterion("bank_name like", value, "bankName");
            return (Criteria) this;
        }

        public Criteria andBankNameNotLike(String value) {
            addCriterion("bank_name not like", value, "bankName");
            return (Criteria) this;
        }

        public Criteria andBankNameIn(List<String> values) {
            addCriterion("bank_name in", values, "bankName");
            return (Criteria) this;
        }

        public Criteria andBankNameNotIn(List<String> values) {
            addCriterion("bank_name not in", values, "bankName");
            return (Criteria) this;
        }

        public Criteria andBankNameBetween(String value1, String value2) {
            addCriterion("bank_name between", value1, value2, "bankName");
            return (Criteria) this;
        }

        public Criteria andBankNameNotBetween(String value1, String value2) {
            addCriterion("bank_name not between", value1, value2, "bankName");
            return (Criteria) this;
        }

        public Criteria andBankCodeIsNull() {
            addCriterion("bank_code is null");
            return (Criteria) this;
        }

        public Criteria andBankCodeIsNotNull() {
            addCriterion("bank_code is not null");
            return (Criteria) this;
        }

        public Criteria andBankCodeEqualTo(String value) {
            addCriterion("bank_code =", value, "bankCode");
            return (Criteria) this;
        }

        public Criteria andBankCodeNotEqualTo(String value) {
            addCriterion("bank_code <>", value, "bankCode");
            return (Criteria) this;
        }

        public Criteria andBankCodeGreaterThan(String value) {
            addCriterion("bank_code >", value, "bankCode");
            return (Criteria) this;
        }

        public Criteria andBankCodeGreaterThanOrEqualTo(String value) {
            addCriterion("bank_code >=", value, "bankCode");
            return (Criteria) this;
        }

        public Criteria andBankCodeLessThan(String value) {
            addCriterion("bank_code <", value, "bankCode");
            return (Criteria) this;
        }

        public Criteria andBankCodeLessThanOrEqualTo(String value) {
            addCriterion("bank_code <=", value, "bankCode");
            return (Criteria) this;
        }

        public Criteria andBankCodeLike(String value) {
            addCriterion("bank_code like", value, "bankCode");
            return (Criteria) this;
        }

        public Criteria andBankCodeNotLike(String value) {
            addCriterion("bank_code not like", value, "bankCode");
            return (Criteria) this;
        }

        public Criteria andBankCodeIn(List<String> values) {
            addCriterion("bank_code in", values, "bankCode");
            return (Criteria) this;
        }

        public Criteria andBankCodeNotIn(List<String> values) {
            addCriterion("bank_code not in", values, "bankCode");
            return (Criteria) this;
        }

        public Criteria andBankCodeBetween(String value1, String value2) {
            addCriterion("bank_code between", value1, value2, "bankCode");
            return (Criteria) this;
        }

        public Criteria andBankCodeNotBetween(String value1, String value2) {
            addCriterion("bank_code not between", value1, value2, "bankCode");
            return (Criteria) this;
        }

        public Criteria andBankShortIsNull() {
            addCriterion("bank_short is null");
            return (Criteria) this;
        }

        public Criteria andBankShortIsNotNull() {
            addCriterion("bank_short is not null");
            return (Criteria) this;
        }

        public Criteria andBankShortEqualTo(String value) {
            addCriterion("bank_short =", value, "bankShort");
            return (Criteria) this;
        }

        public Criteria andBankShortNotEqualTo(String value) {
            addCriterion("bank_short <>", value, "bankShort");
            return (Criteria) this;
        }

        public Criteria andBankShortGreaterThan(String value) {
            addCriterion("bank_short >", value, "bankShort");
            return (Criteria) this;
        }

        public Criteria andBankShortGreaterThanOrEqualTo(String value) {
            addCriterion("bank_short >=", value, "bankShort");
            return (Criteria) this;
        }

        public Criteria andBankShortLessThan(String value) {
            addCriterion("bank_short <", value, "bankShort");
            return (Criteria) this;
        }

        public Criteria andBankShortLessThanOrEqualTo(String value) {
            addCriterion("bank_short <=", value, "bankShort");
            return (Criteria) this;
        }

        public Criteria andBankShortLike(String value) {
            addCriterion("bank_short like", value, "bankShort");
            return (Criteria) this;
        }

        public Criteria andBankShortNotLike(String value) {
            addCriterion("bank_short not like", value, "bankShort");
            return (Criteria) this;
        }

        public Criteria andBankShortIn(List<String> values) {
            addCriterion("bank_short in", values, "bankShort");
            return (Criteria) this;
        }

        public Criteria andBankShortNotIn(List<String> values) {
            addCriterion("bank_short not in", values, "bankShort");
            return (Criteria) this;
        }

        public Criteria andBankShortBetween(String value1, String value2) {
            addCriterion("bank_short between", value1, value2, "bankShort");
            return (Criteria) this;
        }

        public Criteria andBankShortNotBetween(String value1, String value2) {
            addCriterion("bank_short not between", value1, value2, "bankShort");
            return (Criteria) this;
        }

        public Criteria andBankLogoIsNull() {
            addCriterion("bank_logo is null");
            return (Criteria) this;
        }

        public Criteria andBankLogoIsNotNull() {
            addCriterion("bank_logo is not null");
            return (Criteria) this;
        }

        public Criteria andBankLogoEqualTo(String value) {
            addCriterion("bank_logo =", value, "bankLogo");
            return (Criteria) this;
        }

        public Criteria andBankLogoNotEqualTo(String value) {
            addCriterion("bank_logo <>", value, "bankLogo");
            return (Criteria) this;
        }

        public Criteria andBankLogoGreaterThan(String value) {
            addCriterion("bank_logo >", value, "bankLogo");
            return (Criteria) this;
        }

        public Criteria andBankLogoGreaterThanOrEqualTo(String value) {
            addCriterion("bank_logo >=", value, "bankLogo");
            return (Criteria) this;
        }

        public Criteria andBankLogoLessThan(String value) {
            addCriterion("bank_logo <", value, "bankLogo");
            return (Criteria) this;
        }

        public Criteria andBankLogoLessThanOrEqualTo(String value) {
            addCriterion("bank_logo <=", value, "bankLogo");
            return (Criteria) this;
        }

        public Criteria andBankLogoLike(String value) {
            addCriterion("bank_logo like", value, "bankLogo");
            return (Criteria) this;
        }

        public Criteria andBankLogoNotLike(String value) {
            addCriterion("bank_logo not like", value, "bankLogo");
            return (Criteria) this;
        }

        public Criteria andBankLogoIn(List<String> values) {
            addCriterion("bank_logo in", values, "bankLogo");
            return (Criteria) this;
        }

        public Criteria andBankLogoNotIn(List<String> values) {
            addCriterion("bank_logo not in", values, "bankLogo");
            return (Criteria) this;
        }

        public Criteria andBankLogoBetween(String value1, String value2) {
            addCriterion("bank_logo between", value1, value2, "bankLogo");
            return (Criteria) this;
        }

        public Criteria andBankLogoNotBetween(String value1, String value2) {
            addCriterion("bank_logo not between", value1, value2, "bankLogo");
            return (Criteria) this;
        }

        public Criteria andBankTypeIsNull() {
            addCriterion("bank_type is null");
            return (Criteria) this;
        }

        public Criteria andBankTypeIsNotNull() {
            addCriterion("bank_type is not null");
            return (Criteria) this;
        }

        public Criteria andBankTypeEqualTo(Integer value) {
            addCriterion("bank_type =", value, "bankType");
            return (Criteria) this;
        }

        public Criteria andBankTypeNotEqualTo(Integer value) {
            addCriterion("bank_type <>", value, "bankType");
            return (Criteria) this;
        }

        public Criteria andBankTypeGreaterThan(Integer value) {
            addCriterion("bank_type >", value, "bankType");
            return (Criteria) this;
        }

        public Criteria andBankTypeGreaterThanOrEqualTo(Integer value) {
            addCriterion("bank_type >=", value, "bankType");
            return (Criteria) this;
        }

        public Criteria andBankTypeLessThan(Integer value) {
            addCriterion("bank_type <", value, "bankType");
            return (Criteria) this;
        }

        public Criteria andBankTypeLessThanOrEqualTo(Integer value) {
            addCriterion("bank_type <=", value, "bankType");
            return (Criteria) this;
        }

        public Criteria andBankTypeIn(List<Integer> values) {
            addCriterion("bank_type in", values, "bankType");
            return (Criteria) this;
        }

        public Criteria andBankTypeNotIn(List<Integer> values) {
            addCriterion("bank_type not in", values, "bankType");
            return (Criteria) this;
        }

        public Criteria andBankTypeBetween(Integer value1, Integer value2) {
            addCriterion("bank_type between", value1, value2, "bankType");
            return (Criteria) this;
        }

        public Criteria andBankTypeNotBetween(Integer value1, Integer value2) {
            addCriterion("bank_type not between", value1, value2, "bankType");
            return (Criteria) this;
        }

        public Criteria andBankOrderIsNull() {
            addCriterion("bank_order is null");
            return (Criteria) this;
        }

        public Criteria andBankOrderIsNotNull() {
            addCriterion("bank_order is not null");
            return (Criteria) this;
        }

        public Criteria andBankOrderEqualTo(Integer value) {
            addCriterion("bank_order =", value, "bankOrder");
            return (Criteria) this;
        }

        public Criteria andBankOrderNotEqualTo(Integer value) {
            addCriterion("bank_order <>", value, "bankOrder");
            return (Criteria) this;
        }

        public Criteria andBankOrderGreaterThan(Integer value) {
            addCriterion("bank_order >", value, "bankOrder");
            return (Criteria) this;
        }

        public Criteria andBankOrderGreaterThanOrEqualTo(Integer value) {
            addCriterion("bank_order >=", value, "bankOrder");
            return (Criteria) this;
        }

        public Criteria andBankOrderLessThan(Integer value) {
            addCriterion("bank_order <", value, "bankOrder");
            return (Criteria) this;
        }

        public Criteria andBankOrderLessThanOrEqualTo(Integer value) {
            addCriterion("bank_order <=", value, "bankOrder");
            return (Criteria) this;
        }

        public Criteria andBankOrderIn(List<Integer> values) {
            addCriterion("bank_order in", values, "bankOrder");
            return (Criteria) this;
        }

        public Criteria andBankOrderNotIn(List<Integer> values) {
            addCriterion("bank_order not in", values, "bankOrder");
            return (Criteria) this;
        }

        public Criteria andBankOrderBetween(Integer value1, Integer value2) {
            addCriterion("bank_order between", value1, value2, "bankOrder");
            return (Criteria) this;
        }

        public Criteria andBankOrderNotBetween(Integer value1, Integer value2) {
            addCriterion("bank_order not between", value1, value2, "bankOrder");
            return (Criteria) this;
        }

        public Criteria andUpperLimitIsNull() {
            addCriterion("upper_limit is null");
            return (Criteria) this;
        }

        public Criteria andUpperLimitIsNotNull() {
            addCriterion("upper_limit is not null");
            return (Criteria) this;
        }

        public Criteria andUpperLimitEqualTo(String value) {
            addCriterion("upper_limit =", value, "upperLimit");
            return (Criteria) this;
        }

        public Criteria andUpperLimitNotEqualTo(String value) {
            addCriterion("upper_limit <>", value, "upperLimit");
            return (Criteria) this;
        }

        public Criteria andUpperLimitGreaterThan(String value) {
            addCriterion("upper_limit >", value, "upperLimit");
            return (Criteria) this;
        }

        public Criteria andUpperLimitGreaterThanOrEqualTo(String value) {
            addCriterion("upper_limit >=", value, "upperLimit");
            return (Criteria) this;
        }

        public Criteria andUpperLimitLessThan(String value) {
            addCriterion("upper_limit <", value, "upperLimit");
            return (Criteria) this;
        }

        public Criteria andUpperLimitLessThanOrEqualTo(String value) {
            addCriterion("upper_limit <=", value, "upperLimit");
            return (Criteria) this;
        }

        public Criteria andUpperLimitLike(String value) {
            addCriterion("upper_limit like", value, "upperLimit");
            return (Criteria) this;
        }

        public Criteria andUpperLimitNotLike(String value) {
            addCriterion("upper_limit not like", value, "upperLimit");
            return (Criteria) this;
        }

        public Criteria andUpperLimitIn(List<String> values) {
            addCriterion("upper_limit in", values, "upperLimit");
            return (Criteria) this;
        }

        public Criteria andUpperLimitNotIn(List<String> values) {
            addCriterion("upper_limit not in", values, "upperLimit");
            return (Criteria) this;
        }

        public Criteria andUpperLimitBetween(String value1, String value2) {
            addCriterion("upper_limit between", value1, value2, "upperLimit");
            return (Criteria) this;
        }

        public Criteria andUpperLimitNotBetween(String value1, String value2) {
            addCriterion("upper_limit not between", value1, value2, "upperLimit");
            return (Criteria) this;
        }

        public Criteria andBankNoIsNull() {
            addCriterion("bank_no is null");
            return (Criteria) this;
        }

        public Criteria andBankNoIsNotNull() {
            addCriterion("bank_no is not null");
            return (Criteria) this;
        }

        public Criteria andBankNoEqualTo(Integer value) {
            addCriterion("bank_no =", value, "bankNo");
            return (Criteria) this;
        }

        public Criteria andBankNoNotEqualTo(Integer value) {
            addCriterion("bank_no <>", value, "bankNo");
            return (Criteria) this;
        }

        public Criteria andBankNoGreaterThan(Integer value) {
            addCriterion("bank_no >", value, "bankNo");
            return (Criteria) this;
        }

        public Criteria andBankNoGreaterThanOrEqualTo(Integer value) {
            addCriterion("bank_no >=", value, "bankNo");
            return (Criteria) this;
        }

        public Criteria andBankNoLessThan(Integer value) {
            addCriterion("bank_no <", value, "bankNo");
            return (Criteria) this;
        }

        public Criteria andBankNoLessThanOrEqualTo(Integer value) {
            addCriterion("bank_no <=", value, "bankNo");
            return (Criteria) this;
        }

        public Criteria andBankNoIn(List<Integer> values) {
            addCriterion("bank_no in", values, "bankNo");
            return (Criteria) this;
        }

        public Criteria andBankNoNotIn(List<Integer> values) {
            addCriterion("bank_no not in", values, "bankNo");
            return (Criteria) this;
        }

        public Criteria andBankNoBetween(Integer value1, Integer value2) {
            addCriterion("bank_no between", value1, value2, "bankNo");
            return (Criteria) this;
        }

        public Criteria andBankNoNotBetween(Integer value1, Integer value2) {
            addCriterion("bank_no not between", value1, value2, "bankNo");
            return (Criteria) this;
        }

        public Criteria andColorValueIsNull() {
            addCriterion("color_value is null");
            return (Criteria) this;
        }

        public Criteria andColorValueIsNotNull() {
            addCriterion("color_value is not null");
            return (Criteria) this;
        }

        public Criteria andColorValueEqualTo(String value) {
            addCriterion("color_value =", value, "colorValue");
            return (Criteria) this;
        }

        public Criteria andColorValueNotEqualTo(String value) {
            addCriterion("color_value <>", value, "colorValue");
            return (Criteria) this;
        }

        public Criteria andColorValueGreaterThan(String value) {
            addCriterion("color_value >", value, "colorValue");
            return (Criteria) this;
        }

        public Criteria andColorValueGreaterThanOrEqualTo(String value) {
            addCriterion("color_value >=", value, "colorValue");
            return (Criteria) this;
        }

        public Criteria andColorValueLessThan(String value) {
            addCriterion("color_value <", value, "colorValue");
            return (Criteria) this;
        }

        public Criteria andColorValueLessThanOrEqualTo(String value) {
            addCriterion("color_value <=", value, "colorValue");
            return (Criteria) this;
        }

        public Criteria andColorValueLike(String value) {
            addCriterion("color_value like", value, "colorValue");
            return (Criteria) this;
        }

        public Criteria andColorValueNotLike(String value) {
            addCriterion("color_value not like", value, "colorValue");
            return (Criteria) this;
        }

        public Criteria andColorValueIn(List<String> values) {
            addCriterion("color_value in", values, "colorValue");
            return (Criteria) this;
        }

        public Criteria andColorValueNotIn(List<String> values) {
            addCriterion("color_value not in", values, "colorValue");
            return (Criteria) this;
        }

        public Criteria andColorValueBetween(String value1, String value2) {
            addCriterion("color_value between", value1, value2, "colorValue");
            return (Criteria) this;
        }

        public Criteria andColorValueNotBetween(String value1, String value2) {
            addCriterion("color_value not between", value1, value2, "colorValue");
            return (Criteria) this;
        }

        public Criteria andBackgroundIsNull() {
            addCriterion("background is null");
            return (Criteria) this;
        }

        public Criteria andBackgroundIsNotNull() {
            addCriterion("background is not null");
            return (Criteria) this;
        }

        public Criteria andBackgroundEqualTo(String value) {
            addCriterion("background =", value, "background");
            return (Criteria) this;
        }

        public Criteria andBackgroundNotEqualTo(String value) {
            addCriterion("background <>", value, "background");
            return (Criteria) this;
        }

        public Criteria andBackgroundGreaterThan(String value) {
            addCriterion("background >", value, "background");
            return (Criteria) this;
        }

        public Criteria andBackgroundGreaterThanOrEqualTo(String value) {
            addCriterion("background >=", value, "background");
            return (Criteria) this;
        }

        public Criteria andBackgroundLessThan(String value) {
            addCriterion("background <", value, "background");
            return (Criteria) this;
        }

        public Criteria andBackgroundLessThanOrEqualTo(String value) {
            addCriterion("background <=", value, "background");
            return (Criteria) this;
        }

        public Criteria andBackgroundLike(String value) {
            addCriterion("background like", value, "background");
            return (Criteria) this;
        }

        public Criteria andBackgroundNotLike(String value) {
            addCriterion("background not like", value, "background");
            return (Criteria) this;
        }

        public Criteria andBackgroundIn(List<String> values) {
            addCriterion("background in", values, "background");
            return (Criteria) this;
        }

        public Criteria andBackgroundNotIn(List<String> values) {
            addCriterion("background not in", values, "background");
            return (Criteria) this;
        }

        public Criteria andBackgroundBetween(String value1, String value2) {
            addCriterion("background between", value1, value2, "background");
            return (Criteria) this;
        }

        public Criteria andBackgroundNotBetween(String value1, String value2) {
            addCriterion("background not between", value1, value2, "background");
            return (Criteria) this;
        }

        public Criteria andEbankUpperlimitIsNull() {
            addCriterion("ebank_upperlimit is null");
            return (Criteria) this;
        }

        public Criteria andEbankUpperlimitIsNotNull() {
            addCriterion("ebank_upperlimit is not null");
            return (Criteria) this;
        }

        public Criteria andEbankUpperlimitEqualTo(String value) {
            addCriterion("ebank_upperlimit =", value, "ebankUpperlimit");
            return (Criteria) this;
        }

        public Criteria andEbankUpperlimitNotEqualTo(String value) {
            addCriterion("ebank_upperlimit <>", value, "ebankUpperlimit");
            return (Criteria) this;
        }

        public Criteria andEbankUpperlimitGreaterThan(String value) {
            addCriterion("ebank_upperlimit >", value, "ebankUpperlimit");
            return (Criteria) this;
        }

        public Criteria andEbankUpperlimitGreaterThanOrEqualTo(String value) {
            addCriterion("ebank_upperlimit >=", value, "ebankUpperlimit");
            return (Criteria) this;
        }

        public Criteria andEbankUpperlimitLessThan(String value) {
            addCriterion("ebank_upperlimit <", value, "ebankUpperlimit");
            return (Criteria) this;
        }

        public Criteria andEbankUpperlimitLessThanOrEqualTo(String value) {
            addCriterion("ebank_upperlimit <=", value, "ebankUpperlimit");
            return (Criteria) this;
        }

        public Criteria andEbankUpperlimitLike(String value) {
            addCriterion("ebank_upperlimit like", value, "ebankUpperlimit");
            return (Criteria) this;
        }

        public Criteria andEbankUpperlimitNotLike(String value) {
            addCriterion("ebank_upperlimit not like", value, "ebankUpperlimit");
            return (Criteria) this;
        }

        public Criteria andEbankUpperlimitIn(List<String> values) {
            addCriterion("ebank_upperlimit in", values, "ebankUpperlimit");
            return (Criteria) this;
        }

        public Criteria andEbankUpperlimitNotIn(List<String> values) {
            addCriterion("ebank_upperlimit not in", values, "ebankUpperlimit");
            return (Criteria) this;
        }

        public Criteria andEbankUpperlimitBetween(String value1, String value2) {
            addCriterion("ebank_upperlimit between", value1, value2, "ebankUpperlimit");
            return (Criteria) this;
        }

        public Criteria andEbankUpperlimitNotBetween(String value1, String value2) {
            addCriterion("ebank_upperlimit not between", value1, value2, "ebankUpperlimit");
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
    }

    /**
     * 类注释
     * Criteria
     * 数据库表：support_bank
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
     * 数据库表：support_bank
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