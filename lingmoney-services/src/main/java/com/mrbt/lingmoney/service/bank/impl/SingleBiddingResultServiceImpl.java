package com.mrbt.lingmoney.service.bank.impl;

import java.text.MessageFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import com.mrbt.lingmoney.bank.bid.HxSingleBiddingResult;
import com.mrbt.lingmoney.mapper.ProductCustomerMapper;
import com.mrbt.lingmoney.mapper.ProductMapper;
import com.mrbt.lingmoney.mapper.TradingFixInfoMapper;
import com.mrbt.lingmoney.mapper.TradingMapper;
import com.mrbt.lingmoney.mapper.UsersMapper;
import com.mrbt.lingmoney.mapper.UsersPropertiesMapper;
import com.mrbt.lingmoney.model.Product;
import com.mrbt.lingmoney.model.Trading;
import com.mrbt.lingmoney.model.TradingFixInfo;
import com.mrbt.lingmoney.model.Users;
import com.mrbt.lingmoney.model.UsersProperties;
import com.mrbt.lingmoney.model.trading.SmsMessage;
import com.mrbt.lingmoney.service.bank.SingleBiddingResultService;
import com.mrbt.lingmoney.service.bank.SingleBiddingService;
import com.mrbt.lingmoney.service.common.impl.SmsServiceImpl;
import com.mrbt.lingmoney.service.redPacket.RedPacketService;
import com.mrbt.lingmoney.service.trading.TradingFixRuleBuyService;
import com.mrbt.lingmoney.service.trading.impl.TradingFixRuleBuyServiceImpl.CallbackType;
import com.mrbt.lingmoney.utils.AppCons;
import com.mrbt.lingmoney.utils.DateUtils;
import com.mrbt.lingmoney.utils.PageInfo;
import com.mrbt.lingmoney.utils.PropertiesUtil;
import com.mrbt.lingmoney.utils.ResultParame.ResultInfo;

/**
 * 单笔投标结果处理
 */
@Service
public class SingleBiddingResultServiceImpl implements SingleBiddingResultService {

	@Autowired
	private HxSingleBiddingResult hxSingleBiddingResult;

	@Autowired
	private TradingMapper tradingMapper;

	@Autowired
	private ProductMapper productMapper;

	@Autowired
	private TradingFixInfoMapper tradingFixInfoMapper;

	@Autowired
	private TradingFixRuleBuyService tradingFixRuleBuyService;

	@Autowired
	private SingleBiddingService singleBiddingService;

	@Autowired
	private ProductCustomerMapper productCustomerMapper;

	@Autowired
	private SmsServiceImpl smsService;

	@Autowired
	private UsersPropertiesMapper usersPropertiesMapper;

	@Autowired
	private UsersMapper usersMapper;

	@Autowired
	private RedPacketService redPacketService;

	private static final Logger LOGGER = LogManager.getLogger(SingleBiddingResultServiceImpl.class);

	private static final String LOGHEARD = "\nHxSingleBiddingResult_";

	private static final String BANKURL = PropertiesUtil.getPropertiesByKey("BANK_POST_URL");

	@Override
	public PageInfo requestSingleBiddingResult(int clientType, String appId, String uId, String oldReqseqno)
			throws Exception {
		// 生成日志头
		String logGroup = LOGHEARD + System.currentTimeMillis() + "_";

		LOGGER.info("单笔投标结果查询请求：" + logGroup);

		PageInfo pageInfo = new PageInfo();

		if (StringUtils.isEmpty(oldReqseqno)) {
			pageInfo.setMsg("交易不存在");
            pageInfo.setCode(ResultInfo.ORDER_NOT_EXIST.getCode());
			return pageInfo;
		}

		Trading trading = tradingMapper.selectByBizCode(oldReqseqno);
		TradingFixInfo tradingFixInfo = tradingFixInfoMapper.selectByBizCode(oldReqseqno);
		if (StringUtils.isEmpty(trading) || StringUtils.isEmpty(tradingFixInfo)) {
			pageInfo.setMsg("交易不存在");
            pageInfo.setCode(ResultInfo.ORDER_NOT_EXIST.getCode());
			return pageInfo;
		}

		// 2、只有订单状态为支付中12的需要查询
		Integer returnStatus = trading.getStatus();
        if (returnStatus != AppCons.BUY_TRADING) {
			pageInfo.setMsg("只提供支付中的订单查询");
            pageInfo.setCode(ResultInfo.ORDER_STATUS_ERROR.getCode());
			return pageInfo;
		}

		// 3、生成报文
		Map<String, Object> resMap = hxSingleBiddingResult.querySingleBiddingResult(appId, trading.getBizCode(),
				logGroup, BANKURL);

		if (resMap != null) {
			String errorCode = resMap.get("errorCode").toString();
            if (errorCode.equals("0")) { // 以下信息，只有errorCode =0时才返回，即正常响应时才返回
				String status = resMap.get("status").toString();
                if (status.equals("0")) { // 受理成功，不代表操作成功
					/**
					 * S 成功 F 失败 R 处理中（客户仍停留在页面操作，25分钟仍R状态的可置为交易失败。） N
					 * 未知（已提交后台，需再次发查询接口。）
					 * 
					 */
                    String resultStatus = resMap.get("RETURN_STATUS").toString();
                    if (resultStatus.equals("S")) {
						// 处理成功
                        boolean b = tradingFixRuleBuyService.handleBuyProduct(trading.getId(), trading.getBizCode(),
                                uId, CallbackType.Frozen);
						if (b) {
                            singleBiddingService.buyWeiyingbaoGetLingbao(uId, trading.getId());
                            pageInfo.setCode(ResultInfo.SUCCESS.getCode());
							pageInfo.setMsg("购买成功");
							Map<String, Object> map = new HashMap<String, Object>();
							map.put("name", productMapper.selectByPrimaryKey(trading.getpId()).getName());
							map.put("money", trading.getFinancialMoney());
							pageInfo.setObj(map);

							// 购买成功执行短信提醒
							try {
								Users user = usersMapper.selectByPrimaryKey(uId);
								Product pro = productCustomerMapper.selectProductByPrimaryKey(trading.getpId());
								UsersProperties usersProperties = usersPropertiesMapper.selectByuId(uId);
								String userName = "";
								if (usersProperties != null) {
									userName = usersProperties.getName();
								}
								String content = AppCons.buy_content;
								content = MessageFormat.format(content, userName, pro.getName(), pro.getfTime(),
										trading.getFinancialMoney());

								// 发送短信修改为放入redis统一发送
								SmsMessage message = new SmsMessage();
								message.setTelephone(user.getTelephone());
								message.setContent(content);
								smsService.saveSmsMessage(message);

							} catch (Exception e) {
								LOGGER.error("发送购买成功短信失败，用户id{}，交易id：{}，错误信息：{}", uId, trading.getId(), e.toString());
								e.printStackTrace();
							}
						} else {
                            pageInfo.setCode(ResultInfo.UPDATE_FAIL.getCode());
							pageInfo.setMsg("更新数据失败");
						}
                    } else if (resultStatus.equals("F")) {
						// 处理失败
                        String errormsg = resMap.get("ERRORMSG").toString();
                        tradingFixRuleBuyService.handleBuyProduct(trading.getId(), trading.getBizCode(), uId,
                                CallbackType.failure);
                        pageInfo.setCode(ResultInfo.NO_SUCCESS.getCode());
                        pageInfo.setMsg(errormsg);
                    } else if (resultStatus.equals("R")) {
						// 处理中（客户仍停留在页面操作，25分钟仍R状态的可置为交易失败。）
						long minuteDiff = DateUtils.dateDiffMins(trading.getBuyDt(), new Date());
						if (minuteDiff >= 25) {
                            tradingFixRuleBuyService.handleBuyProduct(trading.getId(), trading.getBizCode(), uId,
                                    CallbackType.failure);
						}
                        pageInfo.setCode(ResultInfo.ING.getCode());
						pageInfo.setMsg("处理中");
					} else {
						// 未知（已提交后台，需再次发查询接口。）
                        pageInfo.setCode(ResultInfo.ING.getCode());
						pageInfo.setMsg("处理中");
					}
                } else if (status.equals("1")) { // 受理失败，交易可置为失败
                    tradingFixRuleBuyService.handleBuyProduct(trading.getId(), trading.getBizCode(), uId,
                            CallbackType.failure);
                    pageInfo.setCode(ResultInfo.TRADING_NOT_SUCCESS.getCode());
					pageInfo.setMsg("银行受理失败");
				} else {
                    pageInfo.setCode(ResultInfo.ING.getCode());
					pageInfo.setMsg("处理中");
				}
            } else { // 错误，返回具体错误原因
				// 如果返回code不为访问频繁
				if (!resMap.get("errorCode").equals("OGW100200009")) {
					// 错误，返回具体错误原因
					String errorMsg = resMap.get("errorMsg").toString();
                    tradingFixRuleBuyService.handleBuyProduct(trading.getId(), trading.getBizCode(), uId,
                            CallbackType.failure);
                    pageInfo.setCode(ResultInfo.TRADING_NOT_SUCCESS.getCode());
					pageInfo.setMsg(errorMsg);
				} else {
                    pageInfo.setCode(ResultInfo.ING.getCode());
					pageInfo.setMsg("处理中");
				}
			}
		} else {
            pageInfo.setCode(ResultInfo.ING.getCode());
			pageInfo.setMsg("处理中");
		}

		return pageInfo;

	}
	
	@Override
	public PageInfo requestSingleBiddingResult(String appId, String oldReqseqno)
			throws Exception {
		// 生成日志头
		String logGroup = LOGHEARD + System.currentTimeMillis() + "_";

		LOGGER.info("单笔投标结果查询请求：" + logGroup);

		PageInfo pageInfo = new PageInfo();
 
		if (StringUtils.isEmpty(oldReqseqno)) {
			pageInfo.setMsg("交易不存在");
            pageInfo.setCode(ResultInfo.ORDER_NOT_EXIST.getCode());
			return pageInfo;
		}

		Trading trading = tradingMapper.selectByBizCode(oldReqseqno);
		TradingFixInfo tradingFixInfo = tradingFixInfoMapper.selectByBizCode(oldReqseqno);
		if (StringUtils.isEmpty(trading) || StringUtils.isEmpty(tradingFixInfo)) {
			pageInfo.setMsg("交易不存在");
            pageInfo.setCode(ResultInfo.ORDER_NOT_EXIST.getCode());
			return pageInfo;
		}
		// 2、只有订单状态为支付中12的需要查询
		Integer returnStatus = trading.getStatus();
        if (returnStatus != AppCons.BUY_TRADING && returnStatus != AppCons.BUY_FAIL) {
			pageInfo.setMsg("只提供支付中的订单查询");
            pageInfo.setCode(ResultInfo.ORDER_STATUS_ERROR.getCode());
			return pageInfo;
		}
        String uId = trading.getuId();
		// 3、生成报文
		Map<String, Object> resMap = hxSingleBiddingResult.querySingleBiddingResult(appId, trading.getBizCode(),
				logGroup, BANKURL);

		if (resMap != null) {
			// resMap.put("OLDREQSEQNO", oldReqseqno);// TODO 测试用，开发删除
			String errorCode = resMap.get("errorCode").toString();
            if (errorCode.equals("0")) { // 以下信息，只有errorCode =0时才返回，即正常响应时才返回
				String status = resMap.get("status").toString();
                if (status.equals("0")) { // 受理成功，不代表操作成功
					/**
					 * S 成功 F 失败 R 处理中（客户仍停留在页面操作，25分钟仍R状态的可置为交易失败。） N
					 * 未知（已提交后台，需再次发查询接口。）
					 * 
					 */
                    String resultStatus = resMap.get("RETURN_STATUS").toString();
                    if (resultStatus.equals("S")) {
						// 处理成功
                        boolean b = tradingFixRuleBuyService.handleBuyProduct(trading.getId(), trading.getBizCode(),
                                uId, CallbackType.Frozen);
						if (b) {
                            singleBiddingService.buyWeiyingbaoGetLingbao(uId, trading.getId());
                            pageInfo.setCode(ResultInfo.SUCCESS.getCode());
							pageInfo.setMsg("购买成功");
							Map<String, Object> map = new HashMap<String, Object>();
							map.put("name", productMapper.selectByPrimaryKey(trading.getpId()).getName());
							map.put("money", trading.getFinancialMoney());
							pageInfo.setObj(map);

							// 购买成功执行短信提醒
							try {
								Users user = usersMapper.selectByPrimaryKey(uId);
								Product pro = productCustomerMapper.selectProductByPrimaryKey(trading.getpId());
								UsersProperties usersProperties = usersPropertiesMapper.selectByuId(uId);
								String userName = "";
								if (usersProperties != null) {
									userName = usersProperties.getName();
								}
								String content = AppCons.buy_content;
								content = MessageFormat.format(content, userName, pro.getName(), pro.getfTime(),
										trading.getFinancialMoney());

								// 发送短信修改为放入redis统一发送
								SmsMessage message = new SmsMessage();
								message.setTelephone(user.getTelephone());
								message.setContent(content);
								smsService.saveSmsMessage(message);

							} catch (Exception e) {
								LOGGER.error("发送购买成功短信失败，用户id{}，交易id：{}，错误信息：{}", uId, trading.getId(), e.toString());
								e.printStackTrace();
							}
						} else {
                            pageInfo.setCode(ResultInfo.UPDATE_FAIL.getCode());
							pageInfo.setMsg("更新数据失败");
						}
                    } else if (resultStatus.equals("F")) {
						// 处理失败
                        String errormsg = resMap.get("ERRORMSG").toString();
                        tradingFixRuleBuyService.handleBuyProduct(trading.getId(), trading.getBizCode(), uId,
                                CallbackType.failure);
                        pageInfo.setCode(ResultInfo.NO_SUCCESS.getCode());
                        pageInfo.setMsg(errormsg);
                    } else if (resultStatus.equals("R")) {
						// 处理中（客户仍停留在页面操作，25分钟仍R状态的可置为交易失败。）
						long minuteDiff = DateUtils.dateDiffMins(trading.getBuyDt(), new Date());
						if (minuteDiff >= 25) {
                            tradingFixRuleBuyService.handleBuyProduct(trading.getId(), trading.getBizCode(), uId,
                                    CallbackType.failure);
						}
                        pageInfo.setCode(ResultInfo.ING.getCode());
						pageInfo.setMsg("处理中");
					} else {
						// 未知（已提交后台，需再次发查询接口。）
                        pageInfo.setCode(ResultInfo.ING.getCode());
						pageInfo.setMsg("处理中");
					}
                } else if (status.equals("1")) { // 受理失败，交易可置为失败
                    tradingFixRuleBuyService.handleBuyProduct(trading.getId(), trading.getBizCode(), uId,
                            CallbackType.failure);
                    pageInfo.setCode(ResultInfo.TRADING_NOT_SUCCESS.getCode());
					pageInfo.setMsg("银行受理失败");
				} else {
                    pageInfo.setCode(ResultInfo.ING.getCode());
					pageInfo.setMsg("处理中");
				}
            } else { // 错误，返回具体错误原因
				// 如果返回code不为访问频繁
				if (!resMap.get("errorCode").equals("OGW100200009")) {
					// 错误，返回具体错误原因
					String errorMsg = resMap.get("errorMsg").toString();
                    tradingFixRuleBuyService.handleBuyProduct(trading.getId(), trading.getBizCode(), uId,
                            CallbackType.failure);
                    pageInfo.setCode(ResultInfo.TRADING_NOT_SUCCESS.getCode());
					pageInfo.setMsg(errorMsg);
				} else {
                    pageInfo.setCode(ResultInfo.ING.getCode());
					pageInfo.setMsg("处理中");
				}
			}
		} else {
            pageInfo.setCode(ResultInfo.ING.getCode());
			pageInfo.setMsg("处理中");
		}

		return pageInfo;

	}
	

}
