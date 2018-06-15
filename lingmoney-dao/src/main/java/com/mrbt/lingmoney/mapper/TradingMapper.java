package com.mrbt.lingmoney.mapper;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;

import com.mrbt.lingmoney.model.BuyerVo;
import com.mrbt.lingmoney.model.Trading;
import com.mrbt.lingmoney.model.TradingExample;
import com.mrbt.lingmoney.model.webView.TradingView;

public interface TradingMapper {
	/**
	 * 根据指定的条件获取数据库记录数,trading
	 *
	 * @param example
	 */
	long countByExample(TradingExample example);

	/**
	 * 根据指定的条件删除数据库符合条件的记录,trading
	 *
	 * @param example
	 */
	int deleteByExample(TradingExample example);

	/**
	 * 根据主键删除数据库的记录,trading
	 *
	 * @param id
	 */
	int deleteByPrimaryKey(Integer id);

	/**
	 * 新写入数据库记录,trading
	 *
	 * @param record
	 */
	int insert(Trading record);

	/**
	 * 动态字段,写入数据库记录,trading
	 *
	 * @param record
	 */
	int insertSelective(Trading record);

	/**
	 * 根据指定的条件查询符合条件的数据库记录,trading
	 *
	 * @param example
	 */
	List<Trading> selectByExample(TradingExample example);

	/**
	 * 根据指定主键获取一条数据库记录,trading
	 *
	 * @param id
	 */
	Trading selectByPrimaryKey(Integer id);

	/**
	 * 动态根据指定的条件来更新符合条件的数据库记录,trading
	 *
	 * @param record
	 * @param example
	 */
	int updateByExampleSelective(@Param("record") Trading record, @Param("example") TradingExample example);

	/**
	 * 根据指定的条件来更新符合条件的数据库记录,trading
	 *
	 * @param record
	 * @param example
	 */
	int updateByExample(@Param("record") Trading record, @Param("example") TradingExample example);

	/**
	 * 动态字段,根据主键来更新符合条件的数据库记录,trading
	 *
	 * @param record
	 */
	int updateByPrimaryKeySelective(Trading record);

	/**
	 * 根据主键来更新符合条件的数据库记录,trading
	 *
	 * @param record
	 */
	int updateByPrimaryKey(Trading record);

	/**
	 * 查询用户已购金额,根据产品CODE关联交易表查询状态为4（冻结中），12（支付中）的数据。 因为产品在成立前，支付成功状态为4（冻结中）。
	 * 
	 * @param map
	 *            uId：用户ID prCode：产品代码
	 * @return
	 */
	BigDecimal queryUserBoughtMoney(Map<String, Object> map);

	/**
	 * 动态字段,写入数据库记录,trading，返回主键
	 *
	 * @param record
	 */
	int insertSelectiveReturnId(Trading record);

	/**
	 * 查询用户理财总额
	 * 
	 * @param uid
	 * @return
	 */
	Double selectALLFinancialMoney(String uid);

	/**
	 * 根据产品ID查询用户交易记录
	 * 
	 * @param pid
	 * @return 总条数
	 */
	int selectTrUserCount(int pid);

	/**
	 * 根据产品id查询用户交易记录
	 * 
	 * @param params
	 * @return
	 */
	List<Map<String, Object>> selectTRUserList(Map<String, Object> params);

	/**
	 * 查询用户理财记录(分页数据)
	 * 
	 * @param params
	 *            排除新手标和随心取数据
	 * @return 相关交易信息&产品信息
	 */
	List<TradingView> queryFinancialRecord(Map<String, Object> params);

	int countFinancialRecord(Map<String, Object> params);

	/**
	 * 查询稳盈宝数据
	 * 
	 * @param map
	 * @return
	 */
	List<Map<String, Object>> selectWenYingBao(Map<String, Object> map);

	/**
	 * 查询稳盈数据数量
	 * 
	 * @param map
	 * @return
	 */
	long countWenYingBao(Map<String, Object> map);

	/**
	 * 提前兑付操作
	 * 
	 * @param propMap
	 * @return
	 */
	boolean cashAhead(Map<String, Object> propMap);

	/**
	 * 查询购买时间大于等于十五分钟，并且状态为0（买入）的交易记录
	 * 
	 * @Description
	 * @return
	 */
	List<Trading> selectPayFailList();

	/**
	 * 浮动产品的计算
	 * 
	 * @param tid
	 * @return
	 */
	BigDecimal selectTradingFloatfeesRate(int tid);

	/**
	 * 查询每月第一个购买的用户交易信息
	 * 
	 * @param dateStr
	 * @return
	 */
	Trading selectMonthlyFirstBuyer(String dateStr);

	/**
	 * 查询每月最后一个购买的用户交易信息
	 * 
	 * @param dateStr
	 * @return
	 */
	Trading selectMonthlyLastBuyer(String dateStr);

	/**
	 * 查询每月理财前三名用户
	 * 
	 * @return
	 */
	List<Trading> selectMonthlyBuyTopThree(String dateStr);

	/**
	 * 根据订单号查询交易
	 * 
	 * @param bizCode
	 * @return
	 */
	Trading selectByBizCode(String bizCode);

	/**
	 * 验证撤标请求数据：根据用户名，账户，借款编号查询用户信息
	 * 
	 * @param param
	 *            loanNo 借款编号 acNo 账户 acName 户名
	 */
	Map<String, Object> confirmRecallInfo(Map<String, String> param);

	/**
	 * 查询用户理财中的 理财总金额&预期总收益 仅限固定收益理财
	 * 
	 * @param uid
	 * @return financialMoney 理财总额 interest 总利息
	 */
	Map<String, BigDecimal> queryUserPresentFinancialMoneyAndIncome(String uid);

	List<BuyerVo> findBuyerByProId(Integer pId);

	/**
	 * 查询该用户第一笔成功交易金额
	 *
	 * @Description
	 * @param uid
	 *            用户id
	 * @return
	 */
	BigDecimal queryFirstTradingAmount(String uid);

	/**
	 * 查询该用户当天交易总金额
	 *
	 * @Description
	 * @param uid
	 *            用户id
	 * @return
	 */
	BigDecimal sumTodayBuyMoney(String uid);

	/**
	 * 查询该用户当期产品购买总金额
	 *
	 * @Description
	 * @param uid
	 * @return
	 */
	BigDecimal sumSameProductBuyMoney(String uid);

	/**
	 * 查询用户最后一笔交易id
	 * 
	 * @param uid
	 * @return
	 */
	Integer getLastTradingIdByUserId(String uid);
	/**
	 *根据用户id查询订单Id;
	 * @param uid
	 * @return
	 */
	 Integer getTidByUserId(String uid);

	/**
	 * 查询用户理财记录(分页数据)
	 * 
	 * @param params
	 *            排除新手标和随心取数据
	 * @return 相关交易信息&产品信息
	 */
	List<TradingView> findFinancialRecord(Map<String, Object> params);

	/**
	 * 根据交易ID查询所有P_TYPE=0（领钱儿）的产品和交易记录
	 * 
	 * @param tId
	 *            tId
	 * @return 数据返回
	 */
	Trading selectTradingWithProduect(Integer tId);

	/**
	 * 获取金额
	 * 
	 * @param vo
	 *            vo
	 * @return return
	 */
	BigDecimal queryAlreadyBuy(Trading vo);

	/**
	 * 根据用户ID 产品代码查询交易记录
	 *@Description  status为0:买入，1支付成功，2卖出，3卖出成功, 4 资金冻结
	 *@param p_code 产品代码
	 *@param u_id 用户ID
	 *@return
	 */
	List selectTradingByPCode(Map map);

	/**
	 * 查询产品交易类型
	 * 
	 * @param number
	 *            订单号
	 * @return return
	 */
	Integer selectPtypeByNumber(String number);

	/**
	 * 查询产品交易类型
	 * 
	 * @param tId
	 *            tId
	 * @return return
	 */
	Integer selectPtypeByTid(Integer tId);

    /**
     * 获取大于224天的随心取记录
     * 
     * @author yiban
     * @date 2018年1月10日 上午11:16:34
     * @version 1.0
     * @return
     *
     */
    List<Trading> findTakeheartValueDtTrading();
    
    /**
     * 查询回款数据（待回款 状态 1或者2； 已回款 状态3）
     * 
     * @author yiban
     * @date 2018年3月15日 上午10:09:12
     * @version 1.0
     * @param uId 用户id
     * @param date 时间
     * @return list map: money 金额， state 状态 ， day 日
     *
     */
    List<Map<String, Object>> listRepaymentData(String uId, String date);

    /**
     * 查詢用戶首投信息
     * @param uId uId
     * @return return
     */
	Trading findUserFirstTradingInfo(String uId);
	
}