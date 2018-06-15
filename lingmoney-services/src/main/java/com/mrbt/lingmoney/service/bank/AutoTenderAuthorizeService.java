package com.mrbt.lingmoney.service.bank;

import org.dom4j.Document;

import com.mrbt.lingmoney.utils.PageInfo;

/**
 * 自动投标授权
 * @author luox
 * @Date 2017年6月8日
 */
public interface AutoTenderAuthorizeService {

	/**
	 * 自动投标授权
	 * 
	 * @param uId 用户id
	 * @param clientType 客户端类型 0 PC 1APP
	 * @param appId 应用标识
	 * @param remark 备注
	 * @return pageinfo
	 * @throws Exception 
	 *
	 */
	PageInfo autoTenderAuthorize(String uId, Integer clientType, String appId, String remark)
			throws Exception;

	/**
	 * 字段投标授权返回
	 * 
	 * @param document 结果报文
	 * @return 应答报文
	 * @throws Exception 
	 *
	 */
	String autoTenderAuthorizeCallBack(Document document) throws Exception;
}
