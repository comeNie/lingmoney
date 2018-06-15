package com.mrbt.lingmoney.service.bank;

import org.dom4j.Document;

import com.mrbt.lingmoney.utils.PageInfo;

/**
 * 银行账户
 */
public interface BankAccountService {
	
	
	/**
	 * 请求开通华兴账号数据验证
	 * @param uId	用户ID
	 * @param acName	姓名
	 * @param idNo	身份证号
	 * @param i 
	 * @param string 
	 * @return pageinfo
	 */
	PageInfo requestAccountOpenVery(String uId, String acName, String idNo, int i, String string);

	/**
	 * 请求开通华兴账号
	 * @param uId	用户ID
	 * @param acName	姓名
	 * @param idNo	身份证号
	 * @param mobile 手机号
	 * @param clientType	客户端类型，用来选择交易码
	 * @param appId	应用标识
	 * @return pageinfo
	 */
	PageInfo requestAccountOpen(String uId, String acName, String idNo, String mobile, int clientType, String appId);

	/**
	 * 查询账户开立结果
	 * @param uId 用户id
	 * @param seqNo 原请求流水号
	 * @return pageinfo
	 */
	PageInfo queryAccountOpen(String uId, String seqNo);
	
	/**
	 * 处理银行异步回调
	 * @param document doc
	 * @return 应答报文
	 */
	String opertionAccountOpen(Document document);

	/**
	 * 查询用户开户状态
	 * @param uId 用户id
	 * @return pageinfo
	 */
	PageInfo accountOpenStatus(String uId);

	/**
	 * 实名认证查询,可通过该接口查询客户是否完成了实名认证及账户的开户状态及联 网核查状态 TATUSID 账户状态
	 * 0：未激活 1：正常 2：自助冻结 3：销户 4: 开户失败 5：强制冻结 6：预销户 7：只收不付（即联网核查无结
	 * 果状态） NETCHECKRESULT 联网核查结果 0：无结果 1：通过 2：不通过 3:无需联网核查
	 * CERTIFICATIONRESULT 实名认证结果 0：未实名认证 1：已实名认证 2：实名认证审批中
	 * 3：无需实名认证 4：实名认证等待
	 * 
	 * @author syb
	 * @date 2017年8月14日 下午2:39:41
	 * @version 1.0
	 * @param acNo e账号
	 * @param type 查询类型 1 账户状态 2联网核查结果 3实名认证结果
	 * @param logGroup 日志头
	 * @return 查询状态
	 *
	 */
	String queryRealNameAuthenticationResult(Integer type, String acNo, String logGroup);

}
