package com.mrbt.lingmoney.admin.controller.task;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.alibaba.fastjson.JSONObject;
import com.mrbt.lingmoney.admin.service.bank.BankAccountBalanceService;
import com.mrbt.lingmoney.admin.service.schedule.ScheduleService;
import com.mrbt.lingmoney.mapper.HxAccountMapper;
import com.mrbt.lingmoney.mapper.HxUsersAccountRepaymentRecordMapper;
import com.mrbt.lingmoney.mapper.UsersAccountMapper;
import com.mrbt.lingmoney.model.HxAccount;
import com.mrbt.lingmoney.model.HxAccountExample;
import com.mrbt.lingmoney.model.UsersAccount;
import com.mrbt.lingmoney.utils.ResultParame.ResultNumber;

/**
 * @author syb
 * @date 2017年7月4日 上午9:16:18
 * @version 1.0
 * @description 流标，或撤标后，恢复账户余额
 **/
@Component
public class AutoRebalanceTask {
	private static final Logger LOGGER = LogManager.getLogger(AutoRebalanceTask.class);

	@Autowired
	private ScheduleService scheduleService;
	@Autowired
	private BankAccountBalanceService bankAccountBalanceService;
	@Autowired
	private HxUsersAccountRepaymentRecordMapper hxUsersAccountRepaymentRecordMapper;
	@Autowired
	private HxAccountMapper hxAccountMapper;
	@Autowired
	private UsersAccountMapper usersAccountMapper;

	/**
	 * 
	 */
	public void handleRebalance() {
		List<Map<String, Object>> list = hxUsersAccountRepaymentRecordMapper.selectSumMoneyGroupByUser();

        if (list != null && list.size() > 0) {
            for (Map<String, Object> repayment : list) {
                String uid = (String) repayment.get("uid");
                BigDecimal money = (BigDecimal) repayment.get("money");

                try {
                    
                	HxAccountExample example = new HxAccountExample();
                	example.createCriteria().andUIdEqualTo(uid).andStatusEqualTo(ResultNumber.ONE.getNumber());
                	
                	List<HxAccount> hxAccounts = hxAccountMapper.selectByExample(example);
                	
                	if (hxAccounts != null && hxAccounts.size() > 0) {
                		HxAccount hxAccount = hxAccounts.get(0);
                        // 请求银行接口查询用户可用余额
                        JSONObject json = bankAccountBalanceService.findUserBanlance("PC", hxAccount.getAcName(), hxAccount.getAcNo());
                        // 可用余额
                        String availableBalance = json.getString("availableBalance");

                        LOGGER.info("用户" + uid + "可以余额availableBalance:" + availableBalance);

                        // 查询本地余额和购买金额
                        UsersAccount userAccount = usersAccountMapper.selectByUid(uid);

                        LOGGER.info("orgiBalance:" + userAccount.getBalance() + ";orgiBuyMoney:" + money);

                        // 如果 本地余额+回款金额 = 银行可用余额
                        if (money.add(userAccount.getBalance()).compareTo(new BigDecimal(availableBalance)) == 0) {
                            // 支付成功的才修改
                            UsersAccount accountRecord = new UsersAccount();
                            // 恢复账户余额
                            accountRecord.setId(userAccount.getId());
                            accountRecord.setBalance(userAccount.getBalance().add(money));
                            accountRecord.setFrozenMoney(userAccount.getFrozenMoney().subtract(money));

                            int result = usersAccountMapper.updateByPrimaryKeySelective(accountRecord);

                            if (result > 0) {
                                LOGGER.info("恢复用户{}账户余额成功。金额：{}", uid, money);
                                scheduleService.saveScheduleLog(null, "恢复用户" + uid + "账户余额成功。金额：" + money, null);

                                // 修改回款记录表状态成功
                                result = hxUsersAccountRepaymentRecordMapper.updateAfterRepaymentSuccess(uid);

                                if (result > 0) {
                                    LOGGER.info("恢复用户{}账户余额成功，更新回款记录成功。", uid);
                                    scheduleService.saveScheduleLog(null, "恢复用户" + uid + "账户余额成功，更新回款记录成功。", null);

                                } else {
                                    LOGGER.error("恢复用户{}账户余额成功，更新回款记录失败。", uid);
                                    scheduleService.saveScheduleLog(null, "恢复用户" + uid + "账户余额成功，更新{}号回款记录失败。",
                                            "更新数据失败");
                                }

                            } else {
                                LOGGER.error("恢复用户{}账户余额失败。金额：{}", uid, money);
                                scheduleService.saveScheduleLog(null, "恢复用户" + uid + "账户余额失败。金额：" + money, "更新数据失败");
                            }

                        } else {
                            LOGGER.info("对账失败，暂未恢复用户{}账户余额，等待下一次执行。", uid);
                        }
					}
                } catch (Exception e) {
                    e.printStackTrace();
                    LOGGER.error("流标/撤标后恢复用户账户余额定时任务执行失败,用户id：{}", uid);
                    scheduleService.saveScheduleLog(null, "流标/撤标后恢复用户账户余额定时任务执行失败,用户id：" + uid, e.toString());
                }

			}
		}
	}
}
