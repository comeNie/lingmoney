package com.mrbt.lingmoney.mobile.vo.info;

import java.io.Serializable;
/**
 * 
 * @author ruochen.yu
 *
 */
public class AppBootImageVo implements Serializable {

	private static final long serialVersionUID = 1L;

	/**
     * 数据ID
     * 表字段 : app_boot_image.id
     */
    private Integer id;

    /**
     * url
     * 表字段 : app_boot_image.img_url
     */
    private String imgUrl;


    /**
     * 尺寸，IOS使用
     * 表字段 : app_boot_image.size_code
     */
    private Integer sizeCode;

	/**
	 * 对应类型数据： 如果是产品为产品id 如果是活动为活动URL 表字段 : app_boot_image.data
	 */
	private String data;

	/**
	 * 色值信息，多个用英文逗号分隔 表字段 : app_boot_image.color_info
	 */
	private String colorInfo;

	/**
	 * 设置 类型： 0 无 1产品 2活动 字段:app_boot_image.type
	 *
	 * @param type
	 *            the value for app_boot_image.type, 类型： 0 无 1产品 2活动
	 */
	public void setType(Integer type) {
	}

	/**
	 * 获取 对应类型数据： 如果是产品为产品id 如果是活动为活动URL 字段:app_boot_image.data
	 *
	 * @return app_boot_image.data, 对应类型数据： 如果是产品为产品id 如果是活动为活动URL
	 */
	public String getData() {
		return data;
	}

	/**
	 * 设置 对应类型数据： 如果是产品为产品id 如果是活动为活动URL 字段:app_boot_image.data
	 *
	 * @param data
	 *            the value for app_boot_image.data, 对应类型数据： 如果是产品为产品id
	 *            如果是活动为活动URL
	 */
	public void setData(String data) {
		this.data = data == null ? null : data.trim();
	}

	/**
	 * 获取 色值信息，多个用英文逗号分隔 字段:app_boot_image.color_info
	 *
	 * @return app_boot_image.color_info, 色值信息，多个用英文逗号分隔
	 */
	public String getColorInfo() {
		return colorInfo;
	}

	/**
	 * 设置 色值信息，多个用英文逗号分隔 字段:app_boot_image.color_info
	 *
	 * @param colorInfo
	 *            the value for app_boot_image.color_info, 色值信息，多个用英文逗号分隔
	 */
	public void setColorInfo(String colorInfo) {
		this.colorInfo = colorInfo == null ? null : colorInfo.trim();
	}


	public Integer getId() {
		return id;
	}


	public void setId(Integer id) {
		this.id = id;
	}


	public String getImgUrl() {
		return imgUrl;
	}


	public void setImgUrl(String imgUrl) {
		this.imgUrl = imgUrl;
	}


	public Integer getSizeCode() {
		return sizeCode;
	}


	public void setSizeCode(Integer sizeCode) {
		this.sizeCode = sizeCode;
	}


	public static long getSerialversionuid() {
		return serialVersionUID;
	}
}
