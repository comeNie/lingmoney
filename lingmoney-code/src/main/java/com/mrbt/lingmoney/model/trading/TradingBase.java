package com.mrbt.lingmoney.model.trading;

public class TradingBase {
	/**
	 * 产品分类id
	 */
	private Integer pc_id;
	/**
	 * 产品分类类型
	 */
	private Integer pc_type;
	/**
	 * 产品分类子类型
	 */
	private Integer pc_fix_type;
	/**
	 * 产品id
	 */
	private Integer p_id;

	private Integer rule;
	/**
	 * 时长类型
	 */
	private Integer unit_time;

	public TradingBase(Integer pc_id, Integer pc_type, Integer pc_fix_type,
			Integer p_id) {
		super();
		this.pc_id = pc_id;
		this.pc_type = pc_type;
		this.pc_fix_type = pc_fix_type;
		this.p_id = p_id;
	}

	public Integer getPc_id() {
		return pc_id;
	}

	public void setPc_id(Integer pc_id) {
		this.pc_id = pc_id;
	}

	public Integer getPc_type() {
		return pc_type;
	}

	public void setPc_type(Integer pc_type) {
		this.pc_type = pc_type;
	}

	public Integer getPc_fix_type() {
		return pc_fix_type;
	}

	public void setPc_fix_type(Integer pc_fix_type) {
		this.pc_fix_type = pc_fix_type;
	}

	public Integer getP_id() {
		return p_id;
	}

	public void setP_id(Integer p_id) {
		this.p_id = p_id;
	}

	public Integer getRule() {
		return rule;
	}

	public void setRule(Integer rule) {
		this.rule = rule;
	}

	public Integer getUnit_time() {
		return unit_time;
	}

	public void setUnit_time(Integer unit_time) {
		this.unit_time = unit_time;
	}

}
