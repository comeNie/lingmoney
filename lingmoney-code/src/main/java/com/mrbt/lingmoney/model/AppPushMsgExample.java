package com.mrbt.lingmoney.model;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class AppPushMsgExample {
    /**
     * 排序字段,app_push_msg
     */
    protected String orderByClause;

    /**
     * 是否过滤重复数据,app_push_msg
     */
    protected boolean distinct;

    /**
     * 查询条件实例,app_push_msg
     */
    protected List<Criteria> oredCriteria;

    /**
     * 第一个返回记录行的偏移量,app_push_msg
     */
    protected int limitStart = -1;

    /**
     * 返回记录行的最大数目,app_push_msg
     */
    protected int limitEnd = -1;

    /**
     *  构造查询条件,app_push_msg
     */
    public AppPushMsgExample() {
        oredCriteria = new ArrayList<Criteria>();
    }

    /**
     *  设置排序字段,app_push_msg
     * @param orderByClause 排序字段
     */
    public void setOrderByClause(String orderByClause) {
        this.orderByClause = orderByClause;
    }

    /**
     *  获取排序字段,app_push_msg
     */
    public String getOrderByClause() {
        return orderByClause;
    }

    /**
     *  设置过滤重复数据,app_push_msg
     * @param distinct 是否过滤重复数据
     */
    public void setDistinct(boolean distinct) {
        this.distinct = distinct;
    }

    /**
     *  是否过滤重复数据,app_push_msg
     */
    public boolean isDistinct() {
        return distinct;
    }

    /**
     *  获取当前的查询条件实例,app_push_msg
     */
    public List<Criteria> getOredCriteria() {
        return oredCriteria;
    }

    /**
     *  增加或者的查询条件,用于构建或者查询,app_push_msg
     * @param criteria 过滤条件实例
     */
    public void or(Criteria criteria) {
        oredCriteria.add(criteria);
    }

    /**
     *  创建一个新的或者查询条件,app_push_msg
     */
    public Criteria or() {
        Criteria criteria = createCriteriaInternal();
        oredCriteria.add(criteria);
        return criteria;
    }

    /**
     *  创建一个查询条件,app_push_msg
     */
    public Criteria createCriteria() {
        Criteria criteria = createCriteriaInternal();
        if (oredCriteria.size() == 0) {
            oredCriteria.add(criteria);
        }
        return criteria;
    }

    /**
     *  内部构建查询条件对象,app_push_msg
     */
    protected Criteria createCriteriaInternal() {
        Criteria criteria = new Criteria();
        return criteria;
    }

    /**
     *  清除查询条件,app_push_msg
     */
    public void clear() {
        oredCriteria.clear();
        orderByClause = null;
        distinct = false;
    }

    /**
     *  设置第一个返回记录行的偏移量,app_push_msg
     * @param limitStart 第一个返回记录行的偏移量
     */
    public void setLimitStart(int limitStart) {
        this.limitStart=limitStart;
    }

    /**
     *  获取第一个返回记录行的偏移量,app_push_msg
     */
    public int getLimitStart() {
        return limitStart;
    }

    /**
     *  设置返回记录行的最大数目,app_push_msg
     * @param limitEnd 返回记录行的最大数目
     */
    public void setLimitEnd(int limitEnd) {
        this.limitEnd=limitEnd;
    }

    /**
     *  获取返回记录行的最大数目,app_push_msg
     */
    public int getLimitEnd() {
        return limitEnd;
    }

    /**
     * 类注释
     * GeneratedCriteria
     * 数据库表：app_push_msg
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

        public Criteria andMsgTitleIsNull() {
            addCriterion("msg_title is null");
            return (Criteria) this;
        }

        public Criteria andMsgTitleIsNotNull() {
            addCriterion("msg_title is not null");
            return (Criteria) this;
        }

        public Criteria andMsgTitleEqualTo(String value) {
            addCriterion("msg_title =", value, "msgTitle");
            return (Criteria) this;
        }

        public Criteria andMsgTitleNotEqualTo(String value) {
            addCriterion("msg_title <>", value, "msgTitle");
            return (Criteria) this;
        }

        public Criteria andMsgTitleGreaterThan(String value) {
            addCriterion("msg_title >", value, "msgTitle");
            return (Criteria) this;
        }

        public Criteria andMsgTitleGreaterThanOrEqualTo(String value) {
            addCriterion("msg_title >=", value, "msgTitle");
            return (Criteria) this;
        }

        public Criteria andMsgTitleLessThan(String value) {
            addCriterion("msg_title <", value, "msgTitle");
            return (Criteria) this;
        }

        public Criteria andMsgTitleLessThanOrEqualTo(String value) {
            addCriterion("msg_title <=", value, "msgTitle");
            return (Criteria) this;
        }

        public Criteria andMsgTitleLike(String value) {
            addCriterion("msg_title like", value, "msgTitle");
            return (Criteria) this;
        }

        public Criteria andMsgTitleNotLike(String value) {
            addCriterion("msg_title not like", value, "msgTitle");
            return (Criteria) this;
        }

        public Criteria andMsgTitleIn(List<String> values) {
            addCriterion("msg_title in", values, "msgTitle");
            return (Criteria) this;
        }

        public Criteria andMsgTitleNotIn(List<String> values) {
            addCriterion("msg_title not in", values, "msgTitle");
            return (Criteria) this;
        }

        public Criteria andMsgTitleBetween(String value1, String value2) {
            addCriterion("msg_title between", value1, value2, "msgTitle");
            return (Criteria) this;
        }

        public Criteria andMsgTitleNotBetween(String value1, String value2) {
            addCriterion("msg_title not between", value1, value2, "msgTitle");
            return (Criteria) this;
        }

        public Criteria andMsgContentIsNull() {
            addCriterion("msg_content is null");
            return (Criteria) this;
        }

        public Criteria andMsgContentIsNotNull() {
            addCriterion("msg_content is not null");
            return (Criteria) this;
        }

        public Criteria andMsgContentEqualTo(String value) {
            addCriterion("msg_content =", value, "msgContent");
            return (Criteria) this;
        }

        public Criteria andMsgContentNotEqualTo(String value) {
            addCriterion("msg_content <>", value, "msgContent");
            return (Criteria) this;
        }

        public Criteria andMsgContentGreaterThan(String value) {
            addCriterion("msg_content >", value, "msgContent");
            return (Criteria) this;
        }

        public Criteria andMsgContentGreaterThanOrEqualTo(String value) {
            addCriterion("msg_content >=", value, "msgContent");
            return (Criteria) this;
        }

        public Criteria andMsgContentLessThan(String value) {
            addCriterion("msg_content <", value, "msgContent");
            return (Criteria) this;
        }

        public Criteria andMsgContentLessThanOrEqualTo(String value) {
            addCriterion("msg_content <=", value, "msgContent");
            return (Criteria) this;
        }

        public Criteria andMsgContentLike(String value) {
            addCriterion("msg_content like", value, "msgContent");
            return (Criteria) this;
        }

        public Criteria andMsgContentNotLike(String value) {
            addCriterion("msg_content not like", value, "msgContent");
            return (Criteria) this;
        }

        public Criteria andMsgContentIn(List<String> values) {
            addCriterion("msg_content in", values, "msgContent");
            return (Criteria) this;
        }

        public Criteria andMsgContentNotIn(List<String> values) {
            addCriterion("msg_content not in", values, "msgContent");
            return (Criteria) this;
        }

        public Criteria andMsgContentBetween(String value1, String value2) {
            addCriterion("msg_content between", value1, value2, "msgContent");
            return (Criteria) this;
        }

        public Criteria andMsgContentNotBetween(String value1, String value2) {
            addCriterion("msg_content not between", value1, value2, "msgContent");
            return (Criteria) this;
        }

        public Criteria andUserGroupIsNull() {
            addCriterion("user_group is null");
            return (Criteria) this;
        }

        public Criteria andUserGroupIsNotNull() {
            addCriterion("user_group is not null");
            return (Criteria) this;
        }

        public Criteria andUserGroupEqualTo(Integer value) {
            addCriterion("user_group =", value, "userGroup");
            return (Criteria) this;
        }

        public Criteria andUserGroupNotEqualTo(Integer value) {
            addCriterion("user_group <>", value, "userGroup");
            return (Criteria) this;
        }

        public Criteria andUserGroupGreaterThan(Integer value) {
            addCriterion("user_group >", value, "userGroup");
            return (Criteria) this;
        }

        public Criteria andUserGroupGreaterThanOrEqualTo(Integer value) {
            addCriterion("user_group >=", value, "userGroup");
            return (Criteria) this;
        }

        public Criteria andUserGroupLessThan(Integer value) {
            addCriterion("user_group <", value, "userGroup");
            return (Criteria) this;
        }

        public Criteria andUserGroupLessThanOrEqualTo(Integer value) {
            addCriterion("user_group <=", value, "userGroup");
            return (Criteria) this;
        }

        public Criteria andUserGroupIn(List<Integer> values) {
            addCriterion("user_group in", values, "userGroup");
            return (Criteria) this;
        }

        public Criteria andUserGroupNotIn(List<Integer> values) {
            addCriterion("user_group not in", values, "userGroup");
            return (Criteria) this;
        }

        public Criteria andUserGroupBetween(Integer value1, Integer value2) {
            addCriterion("user_group between", value1, value2, "userGroup");
            return (Criteria) this;
        }

        public Criteria andUserGroupNotBetween(Integer value1, Integer value2) {
            addCriterion("user_group not between", value1, value2, "userGroup");
            return (Criteria) this;
        }

        public Criteria andUserGroupSqlIsNull() {
            addCriterion("user_group_sql is null");
            return (Criteria) this;
        }

        public Criteria andUserGroupSqlIsNotNull() {
            addCriterion("user_group_sql is not null");
            return (Criteria) this;
        }

        public Criteria andUserGroupSqlEqualTo(String value) {
            addCriterion("user_group_sql =", value, "userGroupSql");
            return (Criteria) this;
        }

        public Criteria andUserGroupSqlNotEqualTo(String value) {
            addCriterion("user_group_sql <>", value, "userGroupSql");
            return (Criteria) this;
        }

        public Criteria andUserGroupSqlGreaterThan(String value) {
            addCriterion("user_group_sql >", value, "userGroupSql");
            return (Criteria) this;
        }

        public Criteria andUserGroupSqlGreaterThanOrEqualTo(String value) {
            addCriterion("user_group_sql >=", value, "userGroupSql");
            return (Criteria) this;
        }

        public Criteria andUserGroupSqlLessThan(String value) {
            addCriterion("user_group_sql <", value, "userGroupSql");
            return (Criteria) this;
        }

        public Criteria andUserGroupSqlLessThanOrEqualTo(String value) {
            addCriterion("user_group_sql <=", value, "userGroupSql");
            return (Criteria) this;
        }

        public Criteria andUserGroupSqlLike(String value) {
            addCriterion("user_group_sql like", value, "userGroupSql");
            return (Criteria) this;
        }

        public Criteria andUserGroupSqlNotLike(String value) {
            addCriterion("user_group_sql not like", value, "userGroupSql");
            return (Criteria) this;
        }

        public Criteria andUserGroupSqlIn(List<String> values) {
            addCriterion("user_group_sql in", values, "userGroupSql");
            return (Criteria) this;
        }

        public Criteria andUserGroupSqlNotIn(List<String> values) {
            addCriterion("user_group_sql not in", values, "userGroupSql");
            return (Criteria) this;
        }

        public Criteria andUserGroupSqlBetween(String value1, String value2) {
            addCriterion("user_group_sql between", value1, value2, "userGroupSql");
            return (Criteria) this;
        }

        public Criteria andUserGroupSqlNotBetween(String value1, String value2) {
            addCriterion("user_group_sql not between", value1, value2, "userGroupSql");
            return (Criteria) this;
        }

        public Criteria andOpenPageIsNull() {
            addCriterion("open_page is null");
            return (Criteria) this;
        }

        public Criteria andOpenPageIsNotNull() {
            addCriterion("open_page is not null");
            return (Criteria) this;
        }

        public Criteria andOpenPageEqualTo(String value) {
            addCriterion("open_page =", value, "openPage");
            return (Criteria) this;
        }

        public Criteria andOpenPageNotEqualTo(String value) {
            addCriterion("open_page <>", value, "openPage");
            return (Criteria) this;
        }

        public Criteria andOpenPageGreaterThan(String value) {
            addCriterion("open_page >", value, "openPage");
            return (Criteria) this;
        }

        public Criteria andOpenPageGreaterThanOrEqualTo(String value) {
            addCriterion("open_page >=", value, "openPage");
            return (Criteria) this;
        }

        public Criteria andOpenPageLessThan(String value) {
            addCriterion("open_page <", value, "openPage");
            return (Criteria) this;
        }

        public Criteria andOpenPageLessThanOrEqualTo(String value) {
            addCriterion("open_page <=", value, "openPage");
            return (Criteria) this;
        }

        public Criteria andOpenPageLike(String value) {
            addCriterion("open_page like", value, "openPage");
            return (Criteria) this;
        }

        public Criteria andOpenPageNotLike(String value) {
            addCriterion("open_page not like", value, "openPage");
            return (Criteria) this;
        }

        public Criteria andOpenPageIn(List<String> values) {
            addCriterion("open_page in", values, "openPage");
            return (Criteria) this;
        }

        public Criteria andOpenPageNotIn(List<String> values) {
            addCriterion("open_page not in", values, "openPage");
            return (Criteria) this;
        }

        public Criteria andOpenPageBetween(String value1, String value2) {
            addCriterion("open_page between", value1, value2, "openPage");
            return (Criteria) this;
        }

        public Criteria andOpenPageNotBetween(String value1, String value2) {
            addCriterion("open_page not between", value1, value2, "openPage");
            return (Criteria) this;
        }

        public Criteria andOpenUrlIsNull() {
            addCriterion("open_url is null");
            return (Criteria) this;
        }

        public Criteria andOpenUrlIsNotNull() {
            addCriterion("open_url is not null");
            return (Criteria) this;
        }

        public Criteria andOpenUrlEqualTo(String value) {
            addCriterion("open_url =", value, "openUrl");
            return (Criteria) this;
        }

        public Criteria andOpenUrlNotEqualTo(String value) {
            addCriterion("open_url <>", value, "openUrl");
            return (Criteria) this;
        }

        public Criteria andOpenUrlGreaterThan(String value) {
            addCriterion("open_url >", value, "openUrl");
            return (Criteria) this;
        }

        public Criteria andOpenUrlGreaterThanOrEqualTo(String value) {
            addCriterion("open_url >=", value, "openUrl");
            return (Criteria) this;
        }

        public Criteria andOpenUrlLessThan(String value) {
            addCriterion("open_url <", value, "openUrl");
            return (Criteria) this;
        }

        public Criteria andOpenUrlLessThanOrEqualTo(String value) {
            addCriterion("open_url <=", value, "openUrl");
            return (Criteria) this;
        }

        public Criteria andOpenUrlLike(String value) {
            addCriterion("open_url like", value, "openUrl");
            return (Criteria) this;
        }

        public Criteria andOpenUrlNotLike(String value) {
            addCriterion("open_url not like", value, "openUrl");
            return (Criteria) this;
        }

        public Criteria andOpenUrlIn(List<String> values) {
            addCriterion("open_url in", values, "openUrl");
            return (Criteria) this;
        }

        public Criteria andOpenUrlNotIn(List<String> values) {
            addCriterion("open_url not in", values, "openUrl");
            return (Criteria) this;
        }

        public Criteria andOpenUrlBetween(String value1, String value2) {
            addCriterion("open_url between", value1, value2, "openUrl");
            return (Criteria) this;
        }

        public Criteria andOpenUrlNotBetween(String value1, String value2) {
            addCriterion("open_url not between", value1, value2, "openUrl");
            return (Criteria) this;
        }

        public Criteria andOpenActionIsNull() {
            addCriterion("open_action is null");
            return (Criteria) this;
        }

        public Criteria andOpenActionIsNotNull() {
            addCriterion("open_action is not null");
            return (Criteria) this;
        }

        public Criteria andOpenActionEqualTo(Integer value) {
            addCriterion("open_action =", value, "openAction");
            return (Criteria) this;
        }

        public Criteria andOpenActionNotEqualTo(Integer value) {
            addCriterion("open_action <>", value, "openAction");
            return (Criteria) this;
        }

        public Criteria andOpenActionGreaterThan(Integer value) {
            addCriterion("open_action >", value, "openAction");
            return (Criteria) this;
        }

        public Criteria andOpenActionGreaterThanOrEqualTo(Integer value) {
            addCriterion("open_action >=", value, "openAction");
            return (Criteria) this;
        }

        public Criteria andOpenActionLessThan(Integer value) {
            addCriterion("open_action <", value, "openAction");
            return (Criteria) this;
        }

        public Criteria andOpenActionLessThanOrEqualTo(Integer value) {
            addCriterion("open_action <=", value, "openAction");
            return (Criteria) this;
        }

        public Criteria andOpenActionIn(List<Integer> values) {
            addCriterion("open_action in", values, "openAction");
            return (Criteria) this;
        }

        public Criteria andOpenActionNotIn(List<Integer> values) {
            addCriterion("open_action not in", values, "openAction");
            return (Criteria) this;
        }

        public Criteria andOpenActionBetween(Integer value1, Integer value2) {
            addCriterion("open_action between", value1, value2, "openAction");
            return (Criteria) this;
        }

        public Criteria andOpenActionNotBetween(Integer value1, Integer value2) {
            addCriterion("open_action not between", value1, value2, "openAction");
            return (Criteria) this;
        }

        public Criteria andAddTimeIsNull() {
            addCriterion("add_time is null");
            return (Criteria) this;
        }

        public Criteria andAddTimeIsNotNull() {
            addCriterion("add_time is not null");
            return (Criteria) this;
        }

        public Criteria andAddTimeEqualTo(Date value) {
            addCriterion("add_time =", value, "addTime");
            return (Criteria) this;
        }

        public Criteria andAddTimeNotEqualTo(Date value) {
            addCriterion("add_time <>", value, "addTime");
            return (Criteria) this;
        }

        public Criteria andAddTimeGreaterThan(Date value) {
            addCriterion("add_time >", value, "addTime");
            return (Criteria) this;
        }

        public Criteria andAddTimeGreaterThanOrEqualTo(Date value) {
            addCriterion("add_time >=", value, "addTime");
            return (Criteria) this;
        }

        public Criteria andAddTimeLessThan(Date value) {
            addCriterion("add_time <", value, "addTime");
            return (Criteria) this;
        }

        public Criteria andAddTimeLessThanOrEqualTo(Date value) {
            addCriterion("add_time <=", value, "addTime");
            return (Criteria) this;
        }

        public Criteria andAddTimeIn(List<Date> values) {
            addCriterion("add_time in", values, "addTime");
            return (Criteria) this;
        }

        public Criteria andAddTimeNotIn(List<Date> values) {
            addCriterion("add_time not in", values, "addTime");
            return (Criteria) this;
        }

        public Criteria andAddTimeBetween(Date value1, Date value2) {
            addCriterion("add_time between", value1, value2, "addTime");
            return (Criteria) this;
        }

        public Criteria andAddTimeNotBetween(Date value1, Date value2) {
            addCriterion("add_time not between", value1, value2, "addTime");
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
     * 数据库表：app_push_msg
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
     * 数据库表：app_push_msg
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