package com.mrbt.lingmoney.model;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

/**
 * 自定义产品bean
 * 
 * @author lihq
 * @date 2017年4月19日 下午3:44:42
 * @description 产品名称 预期年化收益率 剩余可够 项目规模 项目期限 起投金额 产品状态 到期时间 还款方式 截止时间
 * 
 *              被占用金额 已购买人数 产品类型 项目进度
 * @version 1.0
 * @since 2017-04-12
 */
public class ProductCustomer implements Serializable {

	private static final long serialVersionUID = 1L;

	private Integer id;// 产品id
	private String code;// 产品code
	private String name;// 产品名称
	private BigDecimal remainMoney;// 剩余可够
	private BigDecimal fYield;// 固定预期年化收益率
	private BigDecimal lYield;// 最低预期年化收益率
	private BigDecimal hYield;// 最高预期年化收益率
	private Integer unitTime;// 项目单位时间，0天，1周，2月，3年，4无
	private Integer fTime;// 固定项目期限
	private Integer lTime;// 最低项目期限
	private Integer hTime;// 最高项目期限
	private Integer status;// 状态
	private Integer rule;// 规则
	private Date valueDt;// 计息日
	private Integer minMoney;// 起投金额
	private Integer increaMoney;// 递增金额
	private Integer buyCount;// 已购买人数
	private Integer runningDays;// 运行中产品运行天数
	private BigDecimal occupyMoney;// 被占用金额
	private String proType;// 产品类型
	private BigDecimal priorMoney;// 准备筹备的金额
	private BigDecimal reachMoney;// 已经筹到的金额
	private BigDecimal successMoney;// 交易成功的金额
	private String reWay;// 还款方式
	private Date stDt;// 计息日
	private Date edDt;// 计息日
	private Date expireDt;// 到期时间（预计）
	private String backgroundUrl;// 手机端产品背景图
	private String backgroundUrlNav;// 手机端产品背景图
	private String backgroundUrlIos;// 手机端产品背景图
	private Integer backgroundFrosted;// 手机端产品背景图是否有磨砂效果 0是 1否
	private String titlePic;// 标题图片（如新手福利）
	private String activityPic;// 活动图片（如赠话费）
	private String cityCode;// 产品code
	private Integer actId; // 关联activity_product主键，首页产品活动专用
    private Integer pType; // 产品所属 0，领钱儿    2.华兴
    private Integer pcId; //产品分类ID
	private BigDecimal buyLimit; // 每个用户购买限额。默认0无限额
	private Integer insuranceTrust; // 保险增信0：无 1：有
	private BigDecimal addYield; // 加息率
	private Integer isDebt; // 是否可债权转让 0：不可转让 1：可转让

	public Integer getPcId() {
		return pcId;
	}

	public void setPcId(Integer pcId) {
		this.pcId = pcId;
	}

	public Integer getActId() {
		return actId;
	}

	public void setActId(Integer actId) {
		this.actId = actId;
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public BigDecimal getRemainMoney() {
		return remainMoney;
	}

	public void setRemainMoney(BigDecimal remainMoney) {
		this.remainMoney = remainMoney;
	}

	public BigDecimal getfYield() {
		return fYield;
	}

	public void setfYield(BigDecimal fYield) {
		this.fYield = fYield;
	}

	public BigDecimal getlYield() {
		return lYield;
	}

	public void setlYield(BigDecimal lYield) {
		this.lYield = lYield;
	}

	public BigDecimal gethYield() {
		return hYield;
	}

	public void sethYield(BigDecimal hYield) {
		this.hYield = hYield;
	}

	public Integer getUnitTime() {
		return unitTime;
	}

	public void setUnitTime(Integer unitTime) {
		this.unitTime = unitTime;
	}

	public Integer getfTime() {
		return fTime;
	}

	public void setfTime(Integer fTime) {
		this.fTime = fTime;
	}

	public Integer getlTime() {
		return lTime;
	}

	public void setlTime(Integer lTime) {
		this.lTime = lTime;
	}

	public Integer gethTime() {
		return hTime;
	}

	public void sethTime(Integer hTime) {
		this.hTime = hTime;
	}

	public Integer getStatus() {
		return status;
	}

	public void setStatus(Integer status) {
		this.status = status;
	}

	public Date getValueDt() {
		return valueDt;
	}

	public void setValueDt(Date valueDt) {
		this.valueDt = valueDt;
	}

	public Integer getMinMoney() {
		return minMoney;
	}

	public void setMinMoney(Integer minMoney) {
		this.minMoney = minMoney;
	}

	public Integer getBuyCount() {
		return buyCount;
	}

	public void setBuyCount(Integer buyCount) {
		this.buyCount = buyCount;
	}

	public Integer getRunningDays() {
		return runningDays;
	}

	public void setRunningDays(Integer runningDays) {
		this.runningDays = runningDays;
	}

	public BigDecimal getOccupyMoney() {
		return occupyMoney;
	}

	public void setOccupyMoney(BigDecimal occupyMoney) {
		this.occupyMoney = occupyMoney;
	}

	public String getProType() {
		return proType;
	}

	public void setProType(String proType) {
		this.proType = proType;
	}

	public BigDecimal getPriorMoney() {
		return priorMoney;
	}

	public void setPriorMoney(BigDecimal priorMoney) {
		this.priorMoney = priorMoney;
	}

	public BigDecimal getReachMoney() {
		return reachMoney;
	}

	public void setReachMoney(BigDecimal reachMoney) {
		this.reachMoney = reachMoney;
	}

	public BigDecimal getSuccessMoney() {
		return successMoney;
	}

	public void setSuccessMoney(BigDecimal successMoney) {
		this.successMoney = successMoney;
	}

	public String getReWay() {
		return reWay;
	}

	public void setReWay(String reWay) {
		this.reWay = reWay;
	}

	public Date getStDt() {
		return stDt;
	}

	public void setStDt(Date stDt) {
		this.stDt = stDt;
	}

	public Date getEdDt() {
		return edDt;
	}

	public void setEdDt(Date edDt) {
		this.edDt = edDt;
	}

	public Date getExpireDt() {
		return expireDt;
	}

	public void setExpireDt(Date expireDt) {
		this.expireDt = expireDt;
	}

	public Integer getRule() {
		return rule;
	}

	public void setRule(Integer rule) {
		this.rule = rule;
	}

	public String getBackgroundUrl() {
		return backgroundUrl;
	}

	public void setBackgroundUrl(String backgroundUrl) {
		this.backgroundUrl = backgroundUrl;
	}

	public String getBackgroundUrlNav() {
		return backgroundUrlNav;
	}

	public void setBackgroundUrlNav(String backgroundUrlNav) {
		this.backgroundUrlNav = backgroundUrlNav;
	}

	public String getBackgroundUrlIos() {
		return backgroundUrlIos;
	}

	public void setBackgroundUrlIos(String backgroundUrlIos) {
		this.backgroundUrlIos = backgroundUrlIos;
	}

	public Integer getIncreaMoney() {
		return increaMoney;
	}

	public void setIncreaMoney(Integer increaMoney) {
		this.increaMoney = increaMoney;
	}

	public Integer getBackgroundFrosted() {
		return backgroundFrosted;
	}

	public void setBackgroundFrosted(Integer backgroundFrosted) {
		this.backgroundFrosted = backgroundFrosted;
	}

	public String getTitlePic() {
		return titlePic;
	}

	public void setTitlePic(String titlePic) {
		this.titlePic = titlePic;
	}

	public String getActivityPic() {
		return activityPic;
	}

	public void setActivityPic(String activityPic) {
		this.activityPic = activityPic;
	}

	public String getCityCode() {
		return cityCode;
	}

	public void setCityCode(String cityCode) {
		this.cityCode = cityCode;
	}

    public Integer getpType() {
        return pType;
    }

    public void setpType(Integer pType) {
        this.pType = pType;
    }

	public BigDecimal getBuyLimit() {
		return buyLimit;
	}

	public void setBuyLimit(BigDecimal buyLimit) {
		this.buyLimit = buyLimit;
	}

	public Integer getInsuranceTrust() {
		return insuranceTrust;
	}

	public void setInsuranceTrust(Integer insuranceTrust) {
		this.insuranceTrust = insuranceTrust;
	}

	public BigDecimal getAddYield() {
		return addYield;
	}

	public void setAddYield(BigDecimal addYield) {
		this.addYield = addYield;
	}

	public Integer getIsDebt() {
		return isDebt;
	}

	public void setIsDebt(Integer isDebt) {
		this.isDebt = isDebt;
	}

}