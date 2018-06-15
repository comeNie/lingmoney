package com.mrbt.lingmoney.model;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

/**
 * 封装用户值对象
 *
 * @author lihq
 * @date 2017年7月25日 下午5:17:46
 * @description
 * @version 1.0
 * @since 2017-04-12
 */
public class UsersVo implements Serializable {

	private static final long serialVersionUID = -6761029149016660370L;

	private String id;// 用户id
	private String name;// 用户姓名
	private String telephone;// 用户手机号
	private String referralCode;// 用户注册推荐码
	private String recommendedLevel;// 用户推荐层级
	private Date regDate;// 注册日期
	private Integer platformType;// 平台类型
	private String registChannel;// 注册渠道

	private Integer isHaveManager;// 是否有理财经理
	private Integer isBindCard;// 是否绑卡激活
	private Integer isFinancialProduct;// 是否买过产品
	private Integer isHoldProduct;// 是否持有产品

	private String managerId;// 理财经理员工号
	private String managerName;// 理财经理姓名
	private String managerCom;// 理财经理所属公司
	private String managerDept;// 理财经理所属部门

	private BigDecimal financialMoney;// 理财金额
	private BigDecimal holdMoney;// 持有金额

    private String sex;//性别
    private Integer age;//年龄

    public String getSex() {
        return sex;
    }

    public void setSex(String sex) {
        this.sex = sex;
    }

    public Integer getAge() {
        return age;
    }

    public void setAge(Integer age) {
        this.age = age;
    }

    public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getTelephone() {
		return telephone;
	}

	public void setTelephone(String telephone) {
		this.telephone = telephone;
	}

	public String getReferralCode() {
		return referralCode;
	}

	public void setReferralCode(String referralCode) {
		this.referralCode = referralCode;
	}

	public String getRecommendedLevel() {
		return recommendedLevel;
	}

	public void setRecommendedLevel(String recommendedLevel) {
		this.recommendedLevel = recommendedLevel;
	}

	public Date getRegDate() {
		return regDate;
	}

	public void setRegDate(Date regDate) {
		this.regDate = regDate;
	}

	public Integer getPlatformType() {
		return platformType;
	}

	public void setPlatformType(Integer platformType) {
		this.platformType = platformType;
	}

	public String getRegistChannel() {
		return registChannel;
	}

	public void setRegistChannel(String registChannel) {
		this.registChannel = registChannel;
	}

	public Integer getIsHaveManager() {
		return isHaveManager;
	}

	public void setIsHaveManager(Integer isHaveManager) {
		this.isHaveManager = isHaveManager;
	}

	public Integer getIsBindCard() {
		return isBindCard;
	}

	public void setIsBindCard(Integer isBindCard) {
		this.isBindCard = isBindCard;
	}

	public Integer getIsFinancialProduct() {
		return isFinancialProduct;
	}

	public void setIsFinancialProduct(Integer isFinancialProduct) {
		this.isFinancialProduct = isFinancialProduct;
	}

	public Integer getIsHoldProduct() {
		return isHoldProduct;
	}

	public void setIsHoldProduct(Integer isHoldProduct) {
		this.isHoldProduct = isHoldProduct;
	}

	public String getManagerId() {
		return managerId;
	}

	public void setManagerId(String managerId) {
		this.managerId = managerId;
	}

	public String getManagerName() {
		return managerName;
	}

	public void setManagerName(String managerName) {
		this.managerName = managerName;
	}

	public String getManagerCom() {
		return managerCom;
	}

	public void setManagerCom(String managerCom) {
		this.managerCom = managerCom;
	}

	public String getManagerDept() {
		return managerDept;
	}

	public void setManagerDept(String managerDept) {
		this.managerDept = managerDept;
	}

	public BigDecimal getFinancialMoney() {
		return financialMoney;
	}

	public void setFinancialMoney(BigDecimal financialMoney) {
		this.financialMoney = financialMoney;
	}

	public BigDecimal getHoldMoney() {
		return holdMoney;
	}

	public void setHoldMoney(BigDecimal holdMoney) {
		this.holdMoney = holdMoney;
	}

}
