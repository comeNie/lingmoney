package com.mrbt.lingmoney.model;

import java.util.ArrayList;
import java.util.List;

public class NewUserRewardExample {
    /**
     * 排序字段,new_user_reward
     */
    protected String orderByClause;

    /**
     * 是否过滤重复数据,new_user_reward
     */
    protected boolean distinct;

    /**
     * 查询条件实例,new_user_reward
     */
    protected List<Criteria> oredCriteria;

    /**
     * 第一个返回记录行的偏移量,new_user_reward
     */
    protected int limitStart = -1;

    /**
     * 返回记录行的最大数目,new_user_reward
     */
    protected int limitEnd = -1;

    /**
     *  构造查询条件,new_user_reward
     */
    public NewUserRewardExample() {
        oredCriteria = new ArrayList<Criteria>();
    }

    /**
     *  设置排序字段,new_user_reward
     * @param orderByClause 排序字段
     */
    public void setOrderByClause(String orderByClause) {
        this.orderByClause = orderByClause;
    }

    /**
     *  获取排序字段,new_user_reward
     */
    public String getOrderByClause() {
        return orderByClause;
    }

    /**
     *  设置过滤重复数据,new_user_reward
     * @param distinct 是否过滤重复数据
     */
    public void setDistinct(boolean distinct) {
        this.distinct = distinct;
    }

    /**
     *  是否过滤重复数据,new_user_reward
     */
    public boolean isDistinct() {
        return distinct;
    }

    /**
     *  获取当前的查询条件实例,new_user_reward
     */
    public List<Criteria> getOredCriteria() {
        return oredCriteria;
    }

    /**
     *  增加或者的查询条件,用于构建或者查询,new_user_reward
     * @param criteria 过滤条件实例
     */
    public void or(Criteria criteria) {
        oredCriteria.add(criteria);
    }

    /**
     *  创建一个新的或者查询条件,new_user_reward
     */
    public Criteria or() {
        Criteria criteria = createCriteriaInternal();
        oredCriteria.add(criteria);
        return criteria;
    }

    /**
     *  创建一个查询条件,new_user_reward
     */
    public Criteria createCriteria() {
        Criteria criteria = createCriteriaInternal();
        if (oredCriteria.size() == 0) {
            oredCriteria.add(criteria);
        }
        return criteria;
    }

    /**
     *  内部构建查询条件对象,new_user_reward
     */
    protected Criteria createCriteriaInternal() {
        Criteria criteria = new Criteria();
        return criteria;
    }

    /**
     *  清除查询条件,new_user_reward
     */
    public void clear() {
        oredCriteria.clear();
        orderByClause = null;
        distinct = false;
    }

    /**
     *  设置第一个返回记录行的偏移量,new_user_reward
     * @param limitStart 第一个返回记录行的偏移量
     */
    public void setLimitStart(int limitStart) {
        this.limitStart=limitStart;
    }

    /**
     *  获取第一个返回记录行的偏移量,new_user_reward
     */
    public int getLimitStart() {
        return limitStart;
    }

    /**
     *  设置返回记录行的最大数目,new_user_reward
     * @param limitEnd 返回记录行的最大数目
     */
    public void setLimitEnd(int limitEnd) {
        this.limitEnd=limitEnd;
    }

    /**
     *  获取返回记录行的最大数目,new_user_reward
     */
    public int getLimitEnd() {
        return limitEnd;
    }

    /**
     * 类注释
     * GeneratedCriteria
     * 数据库表：new_user_reward
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

        public Criteria andMarkedWordsIsNull() {
            addCriterion("marked_words is null");
            return (Criteria) this;
        }

        public Criteria andMarkedWordsIsNotNull() {
            addCriterion("marked_words is not null");
            return (Criteria) this;
        }

        public Criteria andMarkedWordsEqualTo(String value) {
            addCriterion("marked_words =", value, "markedWords");
            return (Criteria) this;
        }

        public Criteria andMarkedWordsNotEqualTo(String value) {
            addCriterion("marked_words <>", value, "markedWords");
            return (Criteria) this;
        }

        public Criteria andMarkedWordsGreaterThan(String value) {
            addCriterion("marked_words >", value, "markedWords");
            return (Criteria) this;
        }

        public Criteria andMarkedWordsGreaterThanOrEqualTo(String value) {
            addCriterion("marked_words >=", value, "markedWords");
            return (Criteria) this;
        }

        public Criteria andMarkedWordsLessThan(String value) {
            addCriterion("marked_words <", value, "markedWords");
            return (Criteria) this;
        }

        public Criteria andMarkedWordsLessThanOrEqualTo(String value) {
            addCriterion("marked_words <=", value, "markedWords");
            return (Criteria) this;
        }

        public Criteria andMarkedWordsLike(String value) {
            addCriterion("marked_words like", value, "markedWords");
            return (Criteria) this;
        }

        public Criteria andMarkedWordsNotLike(String value) {
            addCriterion("marked_words not like", value, "markedWords");
            return (Criteria) this;
        }

        public Criteria andMarkedWordsIn(List<String> values) {
            addCriterion("marked_words in", values, "markedWords");
            return (Criteria) this;
        }

        public Criteria andMarkedWordsNotIn(List<String> values) {
            addCriterion("marked_words not in", values, "markedWords");
            return (Criteria) this;
        }

        public Criteria andMarkedWordsBetween(String value1, String value2) {
            addCriterion("marked_words between", value1, value2, "markedWords");
            return (Criteria) this;
        }

        public Criteria andMarkedWordsNotBetween(String value1, String value2) {
            addCriterion("marked_words not between", value1, value2, "markedWords");
            return (Criteria) this;
        }

        public Criteria andProPicIsNull() {
            addCriterion("pro_pic is null");
            return (Criteria) this;
        }

        public Criteria andProPicIsNotNull() {
            addCriterion("pro_pic is not null");
            return (Criteria) this;
        }

        public Criteria andProPicEqualTo(String value) {
            addCriterion("pro_pic =", value, "proPic");
            return (Criteria) this;
        }

        public Criteria andProPicNotEqualTo(String value) {
            addCriterion("pro_pic <>", value, "proPic");
            return (Criteria) this;
        }

        public Criteria andProPicGreaterThan(String value) {
            addCriterion("pro_pic >", value, "proPic");
            return (Criteria) this;
        }

        public Criteria andProPicGreaterThanOrEqualTo(String value) {
            addCriterion("pro_pic >=", value, "proPic");
            return (Criteria) this;
        }

        public Criteria andProPicLessThan(String value) {
            addCriterion("pro_pic <", value, "proPic");
            return (Criteria) this;
        }

        public Criteria andProPicLessThanOrEqualTo(String value) {
            addCriterion("pro_pic <=", value, "proPic");
            return (Criteria) this;
        }

        public Criteria andProPicLike(String value) {
            addCriterion("pro_pic like", value, "proPic");
            return (Criteria) this;
        }

        public Criteria andProPicNotLike(String value) {
            addCriterion("pro_pic not like", value, "proPic");
            return (Criteria) this;
        }

        public Criteria andProPicIn(List<String> values) {
            addCriterion("pro_pic in", values, "proPic");
            return (Criteria) this;
        }

        public Criteria andProPicNotIn(List<String> values) {
            addCriterion("pro_pic not in", values, "proPic");
            return (Criteria) this;
        }

        public Criteria andProPicBetween(String value1, String value2) {
            addCriterion("pro_pic between", value1, value2, "proPic");
            return (Criteria) this;
        }

        public Criteria andProPicNotBetween(String value1, String value2) {
            addCriterion("pro_pic not between", value1, value2, "proPic");
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

        public Criteria andCreateTimeEqualTo(String value) {
            addCriterion("create_time =", value, "createTime");
            return (Criteria) this;
        }

        public Criteria andCreateTimeNotEqualTo(String value) {
            addCriterion("create_time <>", value, "createTime");
            return (Criteria) this;
        }

        public Criteria andCreateTimeGreaterThan(String value) {
            addCriterion("create_time >", value, "createTime");
            return (Criteria) this;
        }

        public Criteria andCreateTimeGreaterThanOrEqualTo(String value) {
            addCriterion("create_time >=", value, "createTime");
            return (Criteria) this;
        }

        public Criteria andCreateTimeLessThan(String value) {
            addCriterion("create_time <", value, "createTime");
            return (Criteria) this;
        }

        public Criteria andCreateTimeLessThanOrEqualTo(String value) {
            addCriterion("create_time <=", value, "createTime");
            return (Criteria) this;
        }

        public Criteria andCreateTimeLike(String value) {
            addCriterion("create_time like", value, "createTime");
            return (Criteria) this;
        }

        public Criteria andCreateTimeNotLike(String value) {
            addCriterion("create_time not like", value, "createTime");
            return (Criteria) this;
        }

        public Criteria andCreateTimeIn(List<String> values) {
            addCriterion("create_time in", values, "createTime");
            return (Criteria) this;
        }

        public Criteria andCreateTimeNotIn(List<String> values) {
            addCriterion("create_time not in", values, "createTime");
            return (Criteria) this;
        }

        public Criteria andCreateTimeBetween(String value1, String value2) {
            addCriterion("create_time between", value1, value2, "createTime");
            return (Criteria) this;
        }

        public Criteria andCreateTimeNotBetween(String value1, String value2) {
            addCriterion("create_time not between", value1, value2, "createTime");
            return (Criteria) this;
        }

        public Criteria andIndexPicIsNull() {
            addCriterion("index_pic is null");
            return (Criteria) this;
        }

        public Criteria andIndexPicIsNotNull() {
            addCriterion("index_pic is not null");
            return (Criteria) this;
        }

        public Criteria andIndexPicEqualTo(String value) {
            addCriterion("index_pic =", value, "indexPic");
            return (Criteria) this;
        }

        public Criteria andIndexPicNotEqualTo(String value) {
            addCriterion("index_pic <>", value, "indexPic");
            return (Criteria) this;
        }

        public Criteria andIndexPicGreaterThan(String value) {
            addCriterion("index_pic >", value, "indexPic");
            return (Criteria) this;
        }

        public Criteria andIndexPicGreaterThanOrEqualTo(String value) {
            addCriterion("index_pic >=", value, "indexPic");
            return (Criteria) this;
        }

        public Criteria andIndexPicLessThan(String value) {
            addCriterion("index_pic <", value, "indexPic");
            return (Criteria) this;
        }

        public Criteria andIndexPicLessThanOrEqualTo(String value) {
            addCriterion("index_pic <=", value, "indexPic");
            return (Criteria) this;
        }

        public Criteria andIndexPicLike(String value) {
            addCriterion("index_pic like", value, "indexPic");
            return (Criteria) this;
        }

        public Criteria andIndexPicNotLike(String value) {
            addCriterion("index_pic not like", value, "indexPic");
            return (Criteria) this;
        }

        public Criteria andIndexPicIn(List<String> values) {
            addCriterion("index_pic in", values, "indexPic");
            return (Criteria) this;
        }

        public Criteria andIndexPicNotIn(List<String> values) {
            addCriterion("index_pic not in", values, "indexPic");
            return (Criteria) this;
        }

        public Criteria andIndexPicBetween(String value1, String value2) {
            addCriterion("index_pic between", value1, value2, "indexPic");
            return (Criteria) this;
        }

        public Criteria andIndexPicNotBetween(String value1, String value2) {
            addCriterion("index_pic not between", value1, value2, "indexPic");
            return (Criteria) this;
        }
    }

    /**
     * 类注释
     * Criteria
     * 数据库表：new_user_reward
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
     * 数据库表：new_user_reward
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