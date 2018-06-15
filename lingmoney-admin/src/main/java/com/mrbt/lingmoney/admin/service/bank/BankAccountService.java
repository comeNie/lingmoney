package com.mrbt.lingmoney.admin.service.bank;

/**
 * E账户server类
 * @author Administrator
 *
 */
public interface BankAccountService {

	/**
	 * 查询开户状态
	 */
	void queryAccountOpen();

	/**
	 * 查询是否有用户已绑卡成功
	 * @author YJQ  2017年7月20日
	 */
	void pollingTiedCardResult();

	/**
	 * 
	 * @description 实名认证查询,可通过该接口查询客户是否完成了实名认证及账户的开户状态及联 网核查状态
	 * TATUSID 账户状态
	 * 0：未激活 1：正常 2：自助冻结 3：销户 4: 开户失败 5：强制冻结 6：预销户 7：只收不付（即联网核查无结 果状态）
	 * NETCHECKRESULT 联网核查结果 
	 * 0：无结果 1：通过 2：不通过 3:无需联网核查 
	 * CERTIFICATIONRESULT 实名认证结果 
	 * 0：未实名认证 1：已实名认证 2：实名认证审批中 3：无需实名认证 4：实名认证等待
	 * @author syb
	 * @date 2017年8月14日 下午2:39:41
	 * @version 1.0
	 * @param acNo
	 *            e账号
	 * @param type
	 * 			  查询类型  1 账户状态  2联网核查结果  3实名认证结果
	 * @param logGroup 同意日志头
	 * @return 查询状态
	 *
	 */
	String queryRealNameAuthenticationResult(Integer type, String acNo, String logGroup);
}
