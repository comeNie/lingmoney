package com.mrbt.lingmoney.utils;

import java.util.List;
import java.util.Map;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.mrbt.lingmoney.utils.ResultParame.ResultInfo;

/**
 * @description：分页实体类 (结合jqery easyui)
 * @author：ruochen.yu
 * @date：2016年4月23日 上午1:41:46
 */
public class PageInfo {

	private final static int PAGESIZE = 10; // 默认显示的记录数

	private int code; //返回码
	
	private String msg; //返回说明

	private int total; // 总记录
	
	private List rows; // 显示的记录
	
	private Object obj;

	@JsonIgnore
	private int from;
	
	@JsonIgnore
	private int size;
	
	@JsonIgnore
	private int nowpage; // 当前页
	
	@JsonIgnore
	private int pagesize; // 每页显示的记录数
	
	@JsonIgnore
	private Map<String, Object> condition; // 查询条件

	@JsonIgnore
	private String sort = "seq";// 排序字段
	
	@JsonIgnore
	private String order = "asc";// asc，desc mybatis Order 关键字

	public PageInfo() {
		
	}

	// 构造方法
	public PageInfo(Integer nowpage, Integer pagesize) {
		// 计算当前页
		if (nowpage == null || nowpage <= 0) {
			this.nowpage = 1;
		} else {
			// 当前页
			this.nowpage = nowpage;
		}
		// 记录每页显示的记录数
		if (pagesize == null || pagesize <= 0) {
			this.pagesize = PAGESIZE;
		} else {
			this.pagesize = pagesize;
		}
		
		// 计算开始的记录和结束的记录
		this.from = (this.nowpage - 1) * this.pagesize;
		this.size = this.pagesize;
	}

	// 构造方法
	public PageInfo(int nowpage, int pagesize, String sort, String order) {
		// 计算当前页
		if (nowpage < 0) {
			this.nowpage = 1;
		} else {
			// 当前页
			this.nowpage = nowpage;
		}
		// 记录每页显示的记录数
		if (pagesize < 0) {
			this.pagesize = PAGESIZE;
		} else {
			this.pagesize = pagesize;
		}
		
		// 计算开始的记录和结束的记录
		this.from = (this.nowpage - 1) * this.pagesize;
		this.size = this.pagesize;
		
		// 排序字段，正序还是反序
		this.sort = sort;
		this.order = order;
	}

	public int getCode() {
		return code;
	}

	/**
	 * code	说明
	 * 通用code
		200 	服务器已成功处理了请求。
		400 	服务器不理解请求的语法。
		404 	服务器找不到请求的网页。
		500 	服务器遇到错误，无法完成请求。
		1001 	参数缺失
		1002 	请求json格式有误
		1003	请求的数据不存在
		1004	请求参数格式错误
		1005	跨域请求失败
		1006	重复请求
		1007  	京东绑卡不可用
		1008	操作失败
	 * 网页信息相关
		2001	返回数据为空
		.
		.
	 * 用户相关
	    3001	提交参数有误
	    3002	用户账号已存在
	    3003	推荐码不存在
	    3004	账号密码不匹配
	    3005	账号已作废
	    3006	用户账号不存在
	    3007	验证码错误或失效
	    3008	本日验证次数超出限定
	    3009	用户未登录
	    3010	用户未实名
	    3011	返回的数据为空
	    3012	未开通华兴E账户
	    3013	已开通未激活
	    3014	已开通已激活
	    3015	用户输错密码次数超过限制
	    3016	邮箱重复
	    3017	身份证错误
	    3018	身份证已经存在
	    3019	银行账号格式错误
	    3020	银行账号已经存在
	    3021	账号户名格式有误
	    3022	处理中
	    3023	失败
	    3024	账户余额不足
	    3030	还有待支付产品
	    3031	账户与户名不匹配
	    3032	开通E账户，正在处理中
	 * 产品相关
	    4001	产品不存在
	    
	 * 交易相关
	    5001 	随心取停止申购
	    5002 	订单不允许重复提交
	    5003 	订单不存在
	    5004 	订单状态有误
	    5005 	交易成功
	    5006 	交易失败
	    5007 	交易异常，后续在对账文件中确认交易状态或线下人工处理
	    5008 	订单已失效
	    5009 	支付成功
	    5010 	支付失败
	    5011 	更新出错
	    5012	交易信息不匹配
	    5013	取消订单失败
	    5100	随心取卖出失败
	 
	 * 华兴E账户后台操作相关
	    6001	借款编号不存在
	    6002	标的不存在
	    6003	交易流水号不存在
	    6004	生成报文失败
	    6005	银行返回报文失败
	    6006	银行受理成功
	    6007	银行受理失败
	    6008	银行受理中
	    6009	银行受理超时
	    
	    6020   	请求银行处理失败
		6021	标的被撤销
		6022	标的被流标
		6023	标的已成立
		6024	交易失败
		6025	交易处理中
		6026	交易完成
		6027	自动还款授权失败
		6028	未开通自动还款
		6029	未到指定查询时间
		6030	交易结果未明
		6031	标的未成立
		6032	应答了错误页面
		6033	接口访问过于频繁，请稍后再访问
		6034	银行正在处理上一次的请求，请稍候再试
		6035	自动还款已授权
	 * 我的领地相关 
	 *  251  今日已签到
		252  签到失败
		253  原购物车数量大于现有库存
		254  领宝不足
		255  该活动已下线
		256  抽奖信息有误
		257  请选择收货地址
		258  兑换失败
		259  产品已下架
		260  请勿连续点击
		261  兑换超过限额
		262  产品库存不足
		263  购物车已满
		264  非兑换类产品
		265  中奖礼品不可删除
	 *  购买验证相关
		270  当前产品暂未开放认购
		271  当前产品暂已停止认购
		272  购买金额小于最低金额
		273  购买金额有误
		274  购买金额超过剩余可购金额
		275  该用户未实名认证
		276  该用户未绑卡
		277  账户余额不足
		280  购买失败（生成trading失败）
		
	 * @param code
	 */
	public void setCode(int code) {
		this.code = code;
	}

	public String getMsg() {
		return msg;
	}

	public void setMsg(String msg) {
		this.msg = msg;
	}

	public int getTotal() {
		return total;
	}

	public void setTotal(int total) {
		this.total = total;
	}

	public List getRows() {
		return rows;
	}

	public void setRows(List rows) {
		this.rows = rows;
	}

	public int getFrom() {
		return from;
	}

	public void setFrom(int from) {
		this.from = from;
	}

	public int getSize() {
		return size;
	}

	public void setSize(int size) {
		this.size = size;
	}

	public int getNowpage() {
		return nowpage;
	}

	public void setNowpage(int nowpage) {
		this.nowpage = nowpage;
	}

	public int getPagesize() {
		return pagesize;
	}

	public void setPagesize(int pagesize) {
		this.pagesize = pagesize;
	}

	public Map<String, Object> getCondition() {
		return condition;
	}

	public void setCondition(Map<String, Object> condition) {
		this.condition = condition;
	}

	public String getSort() {
		return sort;
	}

	public void setSort(String sort) {
		this.sort = sort;
	}

	public String getOrder() {
		return order;
	}

	public void setOrder(String order) {
		this.order = order;
	}

	public Object getObj() {
		return obj;
	}

	public void setObj(Object obj) {
		this.obj = obj;
	}

	/**
	 * 对应ResultInfo使用，直接设置类型，对应匹配code和msg
	 */
    public void setResultInfo(ResultInfo ri) {
        this.code = ri.getCode();
        this.msg = ri.getMsg();
    }

}
