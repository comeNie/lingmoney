package com.mrbt.lingmoney.mapper;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

import com.mrbt.lingmoney.model.EmployeeRelationsMapping;
import com.mrbt.lingmoney.model.schedule.SellTrading;
import com.mrbt.lingmoney.model.schedule.TakeHeartRedeem;
import com.mrbt.lingmoney.model.schedule.TradingObject;
import com.mrbt.lingmoney.model.schedule.WenYingBaoRedeem;
import com.mrbt.lingmoney.mongo.UsersInfoMysql;

public interface CustomQueryMapper {

	int updateProductByRun(Map<String, Object> prmap);

	List<Map<String, Object>> customQuery(List<Object> list);

	List<UsersInfoMysql> queryUsersInfo(Map<String, Object> paraMap);

	/**
	 * 查询条件： 收益固定不变，状态为支付成功，ftime不为0，卖出时间<当前时间
	 *
	 * @Description
	 * @return
	 */
	List<TradingObject> listWenYingBaoAutoRedeemTrading();

	/**
	 * 查询卖出所需交易信息
	 * 
	 * @param tid
	 * @return
	 */
	SellTrading querySellTradingInfo(int tid);

	/**
	 * 稳赢宝卖出 数据库操作
	 * 
	 * @param sellTrading
	 * @return
	 */
	int updateTradingInfoAftersellWenYing(SellTrading sellTrading);

	/**
	 * 查询还款中数据
	 * 
	 * @param status
	 *            2 还款中 15 等待中
	 * @param pType
	 *            产品所属，0领钱儿，1中粮，2华兴银行
	 * @return
	 */
	List<WenYingBaoRedeem> listAutoPlayMoney(Map<String, Object> parMap);

	/**
	 * 根据用户ID查询还款所需银行信息，（status = 1, order by is_default desc)
	 * 
	 * @param string
	 * @return bankShort 银行简称 number 卡号 tel 手机号
	 */
	List<Map<String, Object>> queryPaymentBankInfoByUid(String uId);

	/**
	 * 自动还款后更新数据操作
	 * 
	 * @param params
	 * @return
	 */
	int updateDataAfterSell(Map<String, Object> params);

	/**
	 * 自动还款第二步完成后更新sell_batch状态
	 * 
	 * @return
	 */
	int updateSellBatchAfterRepayment();

	/**
	 * 查询还款中的随心取产品信息
	 * 
	 * @param status
	 *            4 还款中 15等待中
	 * 
	 * @return
	 */
	List<TakeHeartRedeem> listTakeHeartPlayMoney(int status);

	/**
	 * 查询到期的自动还款产品
	 * 
	 * @param time
	 * @return
	 */
	List<Map<String, Object>> listAutoRepaymentProduct(Map<String, Object> param);

	int countAutoRepaymentProduct();

	/**
	 * 根据产品id查询借款人相关信息（目前默认查询一个借款人，如有多个借款人此处SQL需要变更）
	 * 
	 * @param id
	 * @return
	 */
	Map<String, Object> queryBorrowerInfoByPid(Integer pid);

	/**
	 * 根据产品ID 查询产品下使用加息券的总额
	 * 
	 * @param id
	 * @return
	 */
	BigDecimal sumRedPacketMoneyByPid(Integer pid);

	/**
	 * @Description 根据产品id购买状态查询该产品的购买记录
	 * @param pid
	 *            产品id
	 * @param status
	 *            产品状态
	 * @param pageNo
	 *            当前页
	 * @param pageSize
	 *            每页条数
	 * @return
	 */
	List<Map<String, Object>> queryBuyerInfoByPidAndStatus(Map<String, Object> paramMap);

	/**
	 * @Description 总条数
	 * @param paramMap
	 * @return
	 */
	int countBuyerInfoByPidAndStatus(Map<String, Object> paramMap);

	/**
	 * @Description 统计产品的购买信息
	 * @param pid
	 * @return
	 */
	Map<String, Object> sumProductSellInfoByPid(Integer pid);

	/**
	 * 根据uid查询员工信息
	 * 
	 * @author syb
	 * @date 2017年7月26日 下午2:02:02
	 * @version 1.0
	 * @description
	 * @param uid
	 * @return
	 *
	 */
	EmployeeRelationsMapping queryEmployeeInfoByUid(String uid);

	/**
	 * 根据产品id查询还款总金额 = 总理财金额 + 总利息
	 * 
	 * @author syb
	 * @date 2017年7月31日 下午5:45:58
	 * @version 1.0
	 * @description 关联trading表查询status=1（理财成功）的数据。所有计算数据都用trading_fix_info表
	 * @param id
	 *            产品id
	 * @return
	 *
	 */
	BigDecimal sumAllRepaymentMoney(Integer pid);

	/**
	 * 根据还款信息id查询产品id
	 * 
	 * @description
	 * @author syb
	 * @date 2017年8月15日 上午10:42:31
	 * @version 1.0
	 * @param id
	 *            hx_payemnt.id
	 * @return
	 *
	 */
	Integer queryPidByHxPaymentId(String id);

    /**
     * 查询需要 《自动查询绑卡结果》 的数据
     * 
     * @author yiban
     * @date 2017年10月31日 下午6:47:44
     * @version 1.0
     * @return aId 华兴E账户id, acNo 华兴e账号, acName 用户名, cardId 绑卡id
     *
     */
    List<Map<String, Object>> listAutoQueryBindCardUsers();

    /**
     * 查询某产品有效购买金额
     * 
     * @author yiban
     * @date 2017年11月7日 下午5:19:49
     * @version 1.0
     * @param id
     * @return
     *
     */
    BigDecimal queryValidTradingMoney(Integer id);

    /**
     * 根据E账号查询用户账户id
     * 
     * @author yiban
     * @date 2017年11月8日 上午11:48:32
     * @version 1.0
     * @param bwAcno
     * @return
     *
     */
    Integer queryUsersAccountByAcno(String bwAcno);

    // 日终对账查询返回字段统一为 ： type 类型标识, acNo E账号, bizCode 交易流水, money 金额, state 状态
    /**
     * 日终对账查询投标记录
     * 
     * @author yiban
     * @date 2018年3月27日 下午2:50:35
     * @version 1.0
     * @param fileDate
     * @return
     *
     */
    List<Map<String, Object>> listDailyReconciliateBidding(String fileDate);

    /**
     * 日终对账查询撤标记录
     * 
     * @author yiban
     * @date 2018年3月27日 下午2:50:35
     * @version 1.0
     * @param fileDate
     * @return
     *
     */
    List<Map<String, Object>> listDailyReconciliateBiddingCancel(String fileDate);

    /**
     * 日终对账查询流标记录
     * 
     * @author yiban
     * @date 2018年3月27日 下午2:50:35
     * @version 1.0
     * @param fileDate
     * @return
     *
     */
    List<Map<String, Object>> listDailyReconciliateBiddingLoss(String fileDate);

    /**
     * 日终对账查询放款记录
     * 
     * @author yiban
     * @date 2018年3月27日 下午2:50:35
     * @version 1.0
     * @param fileDate
     * @return
     *
     */
    List<Map<String, Object>> listDailyReconciliateBankLendding(String fileDate);

    /**
     * 日终对账查询还款记录
     * 
     * @author yiban
     * @date 2018年3月27日 下午2:50:35
     * @version 1.0
     * @param fileDate
     * @return
     *
     */
    List<Map<String, Object>> listDailyReconciliateRePayment(String fileDate);

    /**
     * 日终对账查询充值，提现，奖励记录
     * 
     * @author yiban
     * @date 2018年3月27日 下午2:50:35
     * @version 1.0
     * @param fileDate
     * @return
     *
     */
    List<Map<String, Object>> listDailyReconciliateAccountFlow(String fileDate);

    /**
     * 查询累计交易金额
     * @return
     */
    BigDecimal queryTotalSumMoney();

	/**
	 * 查询累计收益
	 * @return
	 */
    BigDecimal queryTotalInterest();

}
