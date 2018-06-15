package com.mrbt.lingmoney.admin.service.bank.impl;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.dom4j.Document;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.interceptor.TransactionAspectSupport;
import org.springframework.util.StringUtils;

import com.mrbt.lingmoney.admin.controller.redPacket.RedPacketController;
import com.mrbt.lingmoney.admin.exception.DataUnCompleteException;
import com.mrbt.lingmoney.admin.service.bank.HxBankLendingService;
import com.mrbt.lingmoney.admin.service.base.CommonMethodService;
import com.mrbt.lingmoney.bank.HxBaseData;
import com.mrbt.lingmoney.bank.deal.HxBankLending;
import com.mrbt.lingmoney.bank.deal.HxQueryBankLendingResult;
import com.mrbt.lingmoney.mapper.AccountFlowMapper;
import com.mrbt.lingmoney.mapper.CustomQueryMapper;
import com.mrbt.lingmoney.mapper.EnterpriseAccountMapper;
import com.mrbt.lingmoney.mapper.HxAccountMapper;
import com.mrbt.lingmoney.mapper.HxBanklenddingInfoMapper;
import com.mrbt.lingmoney.mapper.HxBiddingMapper;
import com.mrbt.lingmoney.mapper.HxBorrowerInfoMapper;
import com.mrbt.lingmoney.mapper.HxBorrowerMapper;
import com.mrbt.lingmoney.mapper.ProductMapper;
import com.mrbt.lingmoney.mapper.TradingFixInfoMapper;
import com.mrbt.lingmoney.mapper.TradingMapper;
import com.mrbt.lingmoney.mapper.UsersAccountMapper;
import com.mrbt.lingmoney.model.AccountFlow;
import com.mrbt.lingmoney.model.AccountFlowExample;
import com.mrbt.lingmoney.model.EnterpriseAccount;
import com.mrbt.lingmoney.model.HxAccount;
import com.mrbt.lingmoney.model.HxBanklenddingInfo;
import com.mrbt.lingmoney.model.HxBanklenddingInfoExample;
import com.mrbt.lingmoney.model.HxBidding;
import com.mrbt.lingmoney.model.HxBiddingExample;
import com.mrbt.lingmoney.model.HxBorrower;
import com.mrbt.lingmoney.model.HxBorrowerInfo;
import com.mrbt.lingmoney.model.HxBorrowerInfoExample;
import com.mrbt.lingmoney.model.Product;
import com.mrbt.lingmoney.model.ProductWithBLOBs;
import com.mrbt.lingmoney.model.Trading;
import com.mrbt.lingmoney.model.TradingExample;
import com.mrbt.lingmoney.model.TradingFixInfo;
import com.mrbt.lingmoney.model.UsersAccount;
import com.mrbt.lingmoney.utils.AppCons;
import com.mrbt.lingmoney.utils.DateUtils;
import com.mrbt.lingmoney.utils.PageInfo;
import com.mrbt.lingmoney.utils.ProductUtils;
import com.mrbt.lingmoney.utils.ResultParame;
import com.mrbt.lingmoney.utils.ResultParame.ResultInfo;
import com.mrbt.lingmoney.utils.ResultParame.ResultNumber;
import com.mrbt.lingmoney.utils.Validation;

/**
 * @author syb
 * @date 2017年6月6日 上午11:47:31
 * @version 1.0
 * @description
 **/
@Service
public class HxBankLendingServiceImpl implements HxBankLendingService {
	private static final Logger LOGGER = LogManager.getLogger(HxBankLendingServiceImpl.class);

	@Autowired
	private HxBankLending hxBankLendding;
	@Autowired
	private HxQueryBankLendingResult hxQueryBankLenddingResult;
	@Autowired
	private HxBiddingMapper hxBiddingMapper;
	@Autowired
	private HxBorrowerInfoMapper hxBorrowerInfoMapper;
	@Autowired
	private HxBorrowerMapper hxBorrowerMapper;
	@Autowired
	private HxBanklenddingInfoMapper hxBanklenddingInfoMapper;
	@Autowired
	private ProductMapper productMapper;
	@Autowired
	private TradingMapper tradingMapper;
	@Autowired
	private HxAccountMapper hxAccountMapper;
	@Autowired
	private CommonMethodService commonMethodService;
	@Autowired
	private TradingFixInfoMapper tradingFixInfoMapper;
	@Autowired
	private UsersAccountMapper usersAccountMapper;
	@Autowired
	private AccountFlowMapper accountFlowMapper;
    @Autowired
    private CustomQueryMapper customQueryMapper;
    @Autowired
    private EnterpriseAccountMapper enterpriseAccountMapper;
	@Autowired
	private RedPacketController redPacketController;
	
	/**
	 * askForBankLending
	 */
	@Override
    public PageInfo askForBankLending(String appId, String biddingId, Double guarantAmt,
			String remark, String logGroup) {
		PageInfo pi = new PageInfo();

		if (StringUtils.isEmpty(biddingId)) {
			pi.setCode(ResultInfo.PARAM_MISS.getCode());
			pi.setMsg(ResultInfo.PARAM_MISS.getMsg());
			return pi;
		}

		HxBidding hxBidding = hxBiddingMapper.selectByPrimaryKey(biddingId);
		Product product = productMapper.selectByPrimaryKey(hxBidding.getpId());
		// 只有产品状态为募集成功/放款申请中 才可申请放款
		if (hxBidding != null && product != null && (product.getStatus() == AppCons.PRODUCT_STATUS_COLLECT_SUCCESS || product.getStatus() == AppCons.PRODUCT_STATUS_APPLY_LENDDING)) {

            // 如果还有支付中的记录，不可申请放款
            TradingExample tradingExmp = new TradingExample();
            tradingExmp.createCriteria().andPIdEqualTo(product.getId()).andStatusEqualTo(AppCons.BUY_TRADING);
            int tradingCount = (int) tradingMapper.countByExample(tradingExmp);
            if (tradingCount > 0) {
                pi.setCode(ResultInfo.DO_FAIL.getCode());
                pi.setMsg("还有支付中记录，请处理完成后再申请放款");
                return pi;
            }

		    // 根据标的ID获取借款人列表，目前只有单个借款人，保留多个借款人处理逻辑
			HxBorrowerInfoExample hbiExample = new HxBorrowerInfoExample();
			hbiExample.createCriteria().andBiddingIdEqualTo(biddingId);
			List<HxBorrowerInfo> hxBorrowerInfoList = hxBorrowerInfoMapper.selectByExample(hbiExample);

			if (hxBorrowerInfoList != null && hxBorrowerInfoList.size() > 0) {
				// 初始化请求金额为null
				String acmngAmtStr = null;
				String guarantAmtStr = null;
				// 银行接受金额格式：整数15位，小数点后2位。例：3.00
				DecimalFormat df = new DecimalFormat("0.00");
				df.setRoundingMode(RoundingMode.HALF_UP);
//				if (!StringUtils.isEmpty(acmngAmt) && Validation.MatchMoney(acmngAmt + "")) {
//					acmngAmtStr = df.format(acmngAmt);
//				}
                // 账户管理费 = 产品有效购买金额 * 平台佣金 * 产品周期 / 365
                BigDecimal validBuyMoney = customQueryMapper.queryValidTradingMoney(product.getId());
                acmngAmtStr = ProductUtils.countInterest(validBuyMoney, product.getfTime(), product.getCostValue())
                        .toString();
                LOGGER.info("{}计算所得平台佣金为：{}", logGroup, acmngAmtStr);

                if (!StringUtils.isEmpty(guarantAmt) && Validation.MatchMoney(guarantAmt + "")) {
					guarantAmtStr = df.format(guarantAmt);
				}

				// 页面展示用 处理结果
				StringBuffer resultInfoStr = new StringBuffer();
				String loanNo = hxBidding.getLoanNo();

				for (HxBorrowerInfo hxBorrowerInfo : hxBorrowerInfoList) {
					HxBorrower hxBorrower = hxBorrowerMapper.selectByPrimaryKey(hxBorrowerInfo.getBwId());
					// 借款人状态为1（可用）才处理请求
					if (hxBorrower.getStatus() == 1) {
						// 是否初次请求放款
						boolean firstApply = false;

                        String bwacName = null;
                        String bwacNo = null;
                        if (hxBorrower.getBwIdtype().equals("1010")) {
                            // 个人账户
                            HxAccount hxAccount = hxAccountMapper.selectByPrimaryKey(hxBorrower.getAccId());
                            bwacName = hxAccount.getAcName();
                            bwacNo = hxAccount.getAcNo();

                        } else if (hxBorrower.getBwIdtype().equals("2020")) {
                            // 企业账户
                            EnterpriseAccount enterpriseAccount = enterpriseAccountMapper.selectByPrimaryKey(hxBorrower
                                    .getAccId());
                            bwacName = enterpriseAccount.getEnterpriseName();
                            bwacNo = enterpriseAccount.getBankNo();

                        } else {
                            pi.setCode(ResultInfo.SERVER_ERROR.getCode());
                            pi.setMsg("账户信息有误，身份类型无效");
                            return pi;
                        }

						// 根据条件查询放款申请记录
						HxBanklenddingInfoExample lenddingInfoExam = new HxBanklenddingInfoExample();
						lenddingInfoExam.createCriteria().andLoanNoEqualTo(loanNo).andBwacNameEqualTo(bwacName)
								.andBwacNoEqualTo(bwacNo);
						List<HxBanklenddingInfo> lenddingInfoList = hxBanklenddingInfoMapper
								.selectByExample(lenddingInfoExam);
						// 只有放款记录为空或者状态为2（失败）时，才请求银行处理
						// 放款记录入库数据
						HxBanklenddingInfo infoRecord = null;
						if (lenddingInfoList == null || lenddingInfoList.isEmpty()
								|| (infoRecord = lenddingInfoList.get(0)).getState() == ResultParame.ResultNumber.TWO
										.getNumber()) {
							pi = requestBankLendding(appId, loanNo, bwacName, bwacNo, acmngAmtStr, guarantAmtStr,
									remark, logGroup);
							/*
							 * 保存放款申请信息 此处响应仅代表受理成功，不代表明细成功，
							 * 明细处理结果可通过对账或者放款结果查询获得
							 * 请求成功，插入数据库:如果是二次请求放款，更新原数据，放款记录只保存一条
							 */
							if (pi.getCode() == ResultInfo.SUCCESS.getCode()) {
								if (infoRecord == null) {
									firstApply = true;
									infoRecord = new HxBanklenddingInfo();
									infoRecord.setId(UUID.randomUUID().toString().replace("-", ""));
									
								} else {
									// 二次申请时 恢复错误信息、响应时间字段 为null
									infoRecord.setErrorMsg(null);
									infoRecord.setResponseTime(null);
								}

								@SuppressWarnings("unchecked")
								Map<String, Object> map = (Map<String, Object>) pi.getObj();
								// 银行交易流水号
								String resJNLNo = (String) map.get("RESJNLNO");
								// 渠道流水号
								String channelFlow = (String) map.get("channelFlow");

								infoRecord.setAppId(appId);
								infoRecord.setChannelFlow(channelFlow);
								infoRecord.setApplyTime(new Date());
								infoRecord.setLoanNo(loanNo);
								infoRecord.setBwacName(bwacName);
								infoRecord.setBwacNo(bwacNo);
								infoRecord.setRemark(remark);
								infoRecord.setState(0);
								infoRecord.setResJnlNo(resJNLNo);
								if (acmngAmtStr != null) {
									infoRecord.setAcMngAmt(new BigDecimal(acmngAmtStr));
								}
								if (guarantAmtStr != null) {
									infoRecord.setGuarantAmt(new BigDecimal(guarantAmtStr));
								}

								resultInfoStr.append("借款人：" + baseViewInfo(infoRecord) + " 申请放款成功。</br>");

								if (firstApply) {
									int i = hxBanklenddingInfoMapper.insertSelective(infoRecord);

									if (i > 0) {
										LOGGER.info("{}请求银行放款信息入库成功，初次申请: \n{}", logGroup, infoRecord.toString());
									} else {
										LOGGER.error("{}请求银行放款信息入库失败，初次申请: \n{}", logGroup, infoRecord.toString());
									}

									// 申请提交成功，修改产品状态为放款申请中，不可购买,此操作只在初次申请进行，再次申请不做更改
									ProductWithBLOBs productRecord = new ProductWithBLOBs();
									productRecord.setId(hxBidding.getpId());
									productRecord.setStatus(AppCons.PRODUCT_STATUS_APPLY_LENDDING);
									// 产品成立日和计息日为申请放款时间，计息日如果是节假日顺延 （此逻辑经：武毅鹏确认）
									productRecord.setEstablishDt(new Date());
									Date valueDt = DateUtils.getTradeDate(new Date(), commonMethodService.findHoliday());
									productRecord.setValueDt(valueDt);
									int result = productMapper.updateByPrimaryKeySelective(productRecord);

									if (result > 0) {
										LOGGER.info("{}请求银行放款成功，修改产品{}状态为放款申请中成功: \n", logGroup,
												productRecord.getId());
									} else {
										LOGGER.error("{}请求银行放款成功，修改产品{}状态为放款申请中失败: \n", logGroup,
												productRecord.getId());
									}

								} else {
									int i = hxBanklenddingInfoMapper.updateByPrimaryKey(infoRecord);

									if (i > 0) {
										LOGGER.info("{}请求银行放款信息入库成功，二次申请: \n{}", logGroup, infoRecord.toString());
									} else {
										LOGGER.error("{}请求银行放款信息入库失败，二次申请: \n{}", logGroup, infoRecord.toString());
									}

								}
								
							} else {
								resultInfoStr.append("借款人：" + processViewInfo(bwacName, 1) + ","
										+ processViewInfo(bwacNo, ResultNumber.TWO.getNumber()) + " 申请放款失败," + pi.getMsg() + "</br>");
							}

						} else if (infoRecord.getState() == 1) {
							resultInfoStr.append("借款人：" + baseViewInfo(infoRecord) + " 已放款</br>");

						} else if (infoRecord.getState() == 0) {
							resultInfoStr.append("借款人：" + baseViewInfo(infoRecord) + " 放款请求处理中,请勿再次申请</br>");
						}

					} else {
						pi.setCode(ResultInfo.EMPTY_DATA.getCode());
						pi.setMsg("借款人无效");
						LOGGER.info("{}查询到该标的下无效借款人 id：{}", logGroup, hxBorrower.getId());
					}

				}

				pi.setCode(ResultInfo.SUCCESS.getCode());
				pi.setMsg(resultInfoStr.toString());
			} else {
				LOGGER.info("{}请求银行放款失败,未查询到借款人信息", logGroup);

				pi.setCode(ResultInfo.EMPTY_DATA.getCode());
				pi.setMsg("未查询到借款人信息");
			}
		} else {
			LOGGER.info("{}请求银行放款失败,未查询到标的信息/标的状态无效", logGroup);
			pi.setCode(ResultInfo.EMPTY_DATA.getCode());
			pi.setMsg("标的信息有误");
		}

		return pi;
	}

    @Transactional
	@Override
	public PageInfo queryBankLendingResult(String appId, String loanNo, String logGroup) {
		PageInfo pi = new PageInfo();

		if (StringUtils.isEmpty(loanNo)) {
			pi.setCode(ResultInfo.PARAM_MISS.getCode());
			pi.setMsg("参数缺失");
			return pi;
		}

		HxBanklenddingInfoExample lenddingInfoExam = new HxBanklenddingInfoExample();
		lenddingInfoExam.createCriteria().andLoanNoEqualTo(loanNo);
		List<HxBanklenddingInfo> lenddingInfoList = hxBanklenddingInfoMapper.selectByExample(lenddingInfoExam);

		if (lenddingInfoList != null && lenddingInfoList.size() > 0) {
			// 页面展示用 处理结果
			StringBuffer resultInfoStr = new StringBuffer();

			for (HxBanklenddingInfo hxBanklenddingInfo : lenddingInfoList) {
				// 只有状态为0（处理中）数据才请求银行查询，否则直接返回结果
				if (hxBanklenddingInfo.getState() == 0) {
					// 5-10分钟后才可查询放款结果
					Date now = new Date();
					Date applyTime = hxBanklenddingInfo.getApplyTime();
					if (applyTime != null && DateUtils.dateDiffMins(applyTime, now) > ResultNumber.FIVE.getNumber()) {
						// 原交易流水号
						String oldReqseqNo = hxBanklenddingInfo.getChannelFlow();
						Document resDoc = hxQueryBankLenddingResult.requestQueryBankLenddingResult(appId, oldReqseqNo,
								loanNo, null, logGroup);
						if (resDoc != null) {
							String errorCode = resDoc.selectSingleNode("//errorCode").getText();
							// 更新记录
							HxBanklenddingInfo updateBanklenddingRecord = new HxBanklenddingInfo();
							updateBanklenddingRecord.setId(hxBanklenddingInfo.getId());
							updateBanklenddingRecord.setResponseTime(new Date());
							
							// 银行受理 只有errorCode =0时才返回，即正常响应时才返回
							if ("0".equals(errorCode)) {
								LOGGER.info("{}查询银行放款结果请求成功，返回信息：\n{}", logGroup, resDoc.asXML());

								pi.setCode(ResultInfo.SUCCESS.getCode());

								// 交易状态 L 交易处理中 F 失败 S完成
								String returnStatus = resDoc.selectSingleNode("//RETURN_STATUS").getText();

								if ("F".equals(returnStatus)) {
									String errorMsg = resDoc.selectSingleNode("//ERRORMSG").getText();
									updateBanklenddingRecord.setErrorMsg(errorMsg);
									updateBanklenddingRecord.setState(ResultNumber.TWO.getNumber());

									resultInfoStr.append("借款人：" + baseViewInfo(hxBanklenddingInfo) + "申请放款失败，"
											+ errorMsg + "</br>");

								} else if ("L".equals(returnStatus)) {
									resultInfoStr.append("借款人：" + baseViewInfo(hxBanklenddingInfo)
											+ "放款申请处理中，请稍后再试。</br>");

								} else if ("S".equals(returnStatus)) {
									resultInfoStr.append("借款人：" + baseViewInfo(hxBanklenddingInfo) + "申请放款成功。</br>");
									// 放款成功需要刷新数据
									pi.setObj(1);
									updateBanklenddingRecord.setState(1);

									// 当错误信息不为空时 清空错误信息
									if (!StringUtils.isEmpty(hxBanklenddingInfo.getErrorMsg())) {
										updateBanklenddingRecord.setErrorMsg(null);
									}
									
									// 放款成功 相应数据更新
									try {
										dataUpdateAfterBankLenddingSuccess(loanNo, hxBanklenddingInfo, logGroup);
									} catch (Exception e) {
										e.printStackTrace();

                                        TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
										pi.setCode(ResultInfo.SERVER_ERROR.getCode());
										pi.setMsg("系统错误，请联系管理员");
										return pi;
									}

									// 将银行返回投标人信息存入mongo备用
									commonMethodService.saveBanklendingInfoToMongo(resDoc, ResultNumber.TWO.getNumber());
									
								}

							} else {
								String errorMsg = resDoc.selectSingleNode("//errorMsg").getText();
								LOGGER.info("{}请求查询银行放款结果失败：{}", logGroup, errorMsg);

								if ("EAS020400001".equals(errorCode)) {
									// 放款总记录不存在，放款失败
									updateBanklenddingRecord.setErrorMsg(errorMsg);
									updateBanklenddingRecord.setState(ResultNumber.TWO.getNumber());
								}

								resultInfoStr.append("查询借款人：" + baseViewInfo(hxBanklenddingInfo) + "申请放款结果失败，"
										+ errorMsg + "。 </br>");
							}

							// 更新放款申请信息
							int i = hxBanklenddingInfoMapper.updateByPrimaryKeySelective(updateBanklenddingRecord);

							if (i > 0) {
								LOGGER.info("{}更新银行放款信息成功。\n{}", logGroup, updateBanklenddingRecord.toString());
							} else {
                                TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
								LOGGER.error("{}更新银行放款信息失败。\n{}", logGroup, updateBanklenddingRecord.toString());
							}

						} else {
							LOGGER.info("{}请求查询银行放款结果失败：请求失败", logGroup);

							resultInfoStr.append("借款人：" + baseViewInfo(hxBanklenddingInfo) + "申请放款失败，请求银行处理失败。</br>");
						}

					} else {
						resultInfoStr.append("借款人：" + baseViewInfo(hxBanklenddingInfo) + "申请结果5-10分钟后才可查询。 </br>");
					}

				} else if (hxBanklenddingInfo.getState() == ResultNumber.ONE.getNumber()) {
					resultInfoStr.append("借款人：" + baseViewInfo(hxBanklenddingInfo) + "申请放款成功。</br>");

				} else if (hxBanklenddingInfo.getState() == ResultNumber.TWO.getNumber()) {
					resultInfoStr.append("借款人：" + baseViewInfo(hxBanklenddingInfo) + "申请放款失败，请重新申请。</br>");
				}

			}

			pi.setCode(ResultInfo.SUCCESS.getCode());
			pi.setMsg(resultInfoStr.toString());

		} else {
			pi.setCode(ResultInfo.PARAM_MISS.getCode());
			pi.setMsg("请先申请放款");
		}

		return pi;
	}

	/**
	 * 放款成功后的所有数据更新操作
	 * 
	 * @param loanNo
	 *            loanNo
	 * @param hxBanklenddingInfo
	 *            hxBanklenddingInfo
	 * @param logGroup
	 *            logGroup
	 * @throws Exception
	 *             Exception
	 */
	@Transactional
    public void dataUpdateAfterBankLenddingSuccess(String loanNo, HxBanklenddingInfo hxBanklenddingInfo,
			String logGroup) throws Exception {
		HxBiddingExample hxBiddingExmp = new HxBiddingExample();
		hxBiddingExmp.createCriteria().andLoanNoEqualTo(loanNo);
		List<HxBidding> biddingList = hxBiddingMapper.selectByExample(hxBiddingExmp);
		if (biddingList != null && biddingList.size() > 0) {
			HxBidding bidding = biddingList.get(0);
			Product product = productMapper.selectByPrimaryKey(bidding.getpId());
			if (product != null) {
                // 修改借款人账户余额 = 账户余额 + 募集成功金额 - 账户管理费 - 风险保证金
				HxBorrowerInfoExample hxBorrowerInfoExmp = new HxBorrowerInfoExample();
				hxBorrowerInfoExmp.createCriteria().andBiddingIdEqualTo(bidding.getId());
				List<HxBorrowerInfo> hxBorrowerInfoList = hxBorrowerInfoMapper.selectByExample(hxBorrowerInfoExmp);
				if (hxBorrowerInfoList != null && hxBorrowerInfoList.size() > 0) {
					HxBorrowerInfo hxBorrowerInfo = hxBorrowerInfoList.get(0);
					HxBorrower hxBorrower = hxBorrowerMapper.selectByPrimaryKey(hxBorrowerInfo.getBwId());
					if (hxBorrower != null) {
						HxAccount hxAccount = hxAccountMapper.selectByPrimaryKey(hxBorrower.getAccId());
						if (hxAccount != null) {
							UsersAccount usersAccount = usersAccountMapper.selectByUid(hxAccount.getuId());

							UsersAccount usersAccountRecord = new UsersAccount();
							usersAccountRecord.setId(usersAccount.getId());
                            BigDecimal money = customQueryMapper.queryValidTradingMoney(product.getId());
							if (hxBanklenddingInfo.getAcMngAmt() != null) {
								money = money.subtract(hxBanklenddingInfo.getAcMngAmt());
							}
							if (hxBanklenddingInfo.getGuarantAmt() != null) {
								money = money.subtract(hxBanklenddingInfo.getGuarantAmt());
							}

							usersAccountRecord.setBalance(usersAccount.getBalance().add(money));
							int result = usersAccountMapper.updateByPrimaryKeySelective(usersAccountRecord);
							
							if (result > 0) {
								LOGGER.info("{}放款成功，更新借款人余额成功。借款人id：{}；金额：{}", logGroup, hxBorrower.getId(), money);

								// 插入一条账户流水
								AccountFlow accountFlowRecord = new AccountFlow();
								accountFlowRecord.setaId(usersAccount.getId());
								accountFlowRecord.setOperateTime(new Date());
								accountFlowRecord.setMoney(money);
								accountFlowRecord.setNumber(hxBanklenddingInfo.getChannelFlow());
								accountFlowRecord.setStatus(1);
								accountFlowRecord.setNote("放款成功");
								accountFlowRecord.setType(ResultParame.ResultNumber.SIX.getNumber());
								accountFlowRecord.setBalance(usersAccountRecord.getBalance());
								accountFlowRecord.setFrozenMoney(usersAccount.getFrozenMoney());
								accountFlowRecord.setPlatform(0);
								accountFlowRecord.setCardPan(hxAccount.getAcNo());

								result = accountFlowMapper.insertSelective(accountFlowRecord);
								if (result > 0) {
									LOGGER.info("{}放款成功，插入账户流水成功。", logGroup);

								} else {
									LOGGER.error("{}放款成功，插入账户流水失败。", logGroup);
									throw new DataUnCompleteException(logGroup + "插入账户流水失败");
								}

							} else {
								LOGGER.error("{}放款成功，更新借款人余额失败。借款人账户id：{}；金额：{}", logGroup, usersAccount.getId(), money);
								throw new DataUnCompleteException(logGroup + "更新借款人账户信息失败， id:" + usersAccount.getId());
							}

						}
					}
				}

                // 修改产品状态为运行中
				ProductWithBLOBs productRecord = new ProductWithBLOBs();
				productRecord.setId(product.getId());
				productRecord.setStatus(AppCons.PRODUCT_STATUS_RUNNING);
				int result = productMapper.updateByPrimaryKeySelective(productRecord);

				if (result > 0) {
					LOGGER.info("{}更新产品状态为运行中成功。\n{}", logGroup, productRecord.toString());

				} else {
					LOGGER.error("{}更新产品状态为运行中失败。\n{}", logGroup, productRecord.toString());
					throw new DataUnCompleteException(logGroup + "更新产品状态为运行中失败");
				}

                // 产品运行天数
                Integer days = ProductUtils.getFinancialDays(product);
                // 赎回日期
                Date minSellDt = DateUtils.addDay(product.getValueDt(), days);

                // 修改标的还款日期
                HxBidding hxBiddingRecord = new HxBidding();
                hxBiddingRecord.setId(bidding.getId());
                hxBiddingRecord.setRepayDate(new SimpleDateFormat("yyyyMMdd").format(minSellDt));
                hxBiddingMapper.updateByPrimaryKeySelective(hxBiddingRecord);

				// 修改交易成功 交易信息
				TradingExample tradingExmp = new TradingExample();
				tradingExmp.createCriteria().andPIdEqualTo(product.getId()).andStatusEqualTo(AppCons.BUY_FROKEN);
				List<Trading> tradingList = tradingMapper.selectByExample(tradingExmp);
				if (tradingList != null && tradingList.size() > 0) {
					for (Trading trading : tradingList) {
						Trading tradingRecord = new Trading();
						tradingRecord.setId(trading.getId());
						tradingRecord.setStatus(AppCons.BUY_OK);
						tradingRecord.setValueDt(product.getValueDt());
                        tradingRecord.setMinSellDt(minSellDt);
                        BigDecimal interest = ProductUtils.countInterest(trading.getFinancialMoney(), days,
                                product.getfYield());
                        tradingRecord.setInterest(interest);
						result = tradingMapper.updateByPrimaryKeySelective(tradingRecord);

						if (result > 0) {
							LOGGER.error("{}借款编号{}产品放款成功，更新第{}号交易状态成功。", logGroup, loanNo, trading.getId());

						} else {
							LOGGER.error("{}借款编号{}产品放款成功，更新第{}号交易状态失败。", logGroup, loanNo, trading.getId());
							throw new DataUnCompleteException(logGroup + "更新trading信息失败");
						}

						// 修改 交易流水 信息
						TradingFixInfo tradingFixInfo = tradingFixInfoMapper.selectFixInfoByTid(trading.getId());
						if (tradingFixInfo != null) {
							TradingFixInfo tradingFixInfoRecord = new TradingFixInfo();
							tradingFixInfoRecord.setId(tradingFixInfo.getId());
							tradingFixInfoRecord.setStatus(AppCons.BUY_OK);
							tradingFixInfoRecord.setExpiryDt(tradingRecord.getMinSellDt());
							tradingFixInfoRecord.setwTime(DateUtils.dateDiff(trading.getwValueDt(),
									product.getValueDt()));
							tradingFixInfoRecord.setInterest(interest);

							result = tradingFixInfoMapper.updateByPrimaryKeySelective(tradingFixInfoRecord);
							if (result > 0) {
								LOGGER.info("{}产品{}申请放款成功，更新{}号交易流水信息成功。\n{}", logGroup, product.getId(),
										tradingFixInfoRecord.getId(), tradingFixInfoRecord.toString());

							} else {
								LOGGER.error("{}产品{}申请放款成功，更新{}号交易流水信息失败。\n{}", logGroup, product.getId(),
										tradingFixInfoRecord.getId(), tradingFixInfoRecord.toString());
								throw new DataUnCompleteException(logGroup + "更新交易流水信息失败");
							}

						}

						// 修改用户账户表 frozen_money↓ total_finance↑
						UsersAccount userAccount = usersAccountMapper.selectByUid(trading.getuId());
						if (userAccount != null) {
							UsersAccount accountRecord = new UsersAccount();
							accountRecord.setId(userAccount.getId());
							accountRecord.setFrozenMoney(userAccount.getFrozenMoney().subtract(trading.getBuyMoney()));
							accountRecord.setTotalFinance(userAccount.getTotalFinance().add(trading.getBuyMoney()));
							result = usersAccountMapper.updateByPrimaryKeySelective(accountRecord);
							if (result > 0) {
								LOGGER.info("{}产品{}申请放款成功，更新{}号用户账户信息成功。\n{}", logGroup, product.getId(),
										accountRecord.getId(), accountRecord.toString());

							} else {
								LOGGER.error("{}产品{}申请放款成功，更新{}号用户账户信息失败。\n{}", logGroup, product.getId(),
										accountRecord.getId(), accountRecord.toString());
								throw new DataUnCompleteException(logGroup + "更新账户信息失败");
							}

						} else {
							LOGGER.error("{}产品{}申请放款成功，未查询到编号{}的用户账户信息。", logGroup, product.getId(), trading.getuId());
						}
						// 修改账户流水表 status = 1
						AccountFlowExample accountFlowExample = new AccountFlowExample();
						accountFlowExample.createCriteria().andNumberEqualTo(trading.getBizCode()).andTypeEqualTo(ResultNumber.TWO.getNumber());
						AccountFlow accountFlowRecord = new AccountFlow();
						accountFlowRecord.setFrozenMoney(new BigDecimal(0));
						accountFlowRecord.setStatus(AppCons.BUY_OK);
						result = accountFlowMapper.updateByExampleSelective(accountFlowRecord, accountFlowExample);
						if (result > 0) {
							LOGGER.info("{}产品{}申请放款成功，更新编号{}用户账户流水信息成功。\n{}", logGroup, product.getId(),
									trading.getBizCode(), accountFlowRecord.toString());
							// 首笔理财成功，赠送0.025加息卷
							redPacketController.rewardRedPackage(trading.getuId(), 4, null);

						} else {
							LOGGER.error("{}产品{}申请放款成功，更新编号{}用户账户流水信息失败。\n{}", logGroup, product.getId(),
									trading.getBizCode(), accountFlowRecord.toString());
							throw new DataUnCompleteException(logGroup + "更新用户账户流水信息失败");
						}
					}
					LOGGER.info("{}借款编号{}产品放款成功，更新其下投标状态完毕。", logGroup, loanNo);
				}
			} else {
				LOGGER.error("{}未查询到产品id为{}的产品信息", logGroup, bidding.getpId());
				throw new IllegalArgumentException(logGroup + "未查询到产品id为" + bidding.getpId() + "的产品信息");
			}
		} else {
			LOGGER.error("{}申请放款成功，未查询到借款编号为：{}的标的信息。", logGroup, loanNo);
			throw new IllegalArgumentException(logGroup + "未查询到借款编号为" + loanNo + "的标的信息");
		}
	}

	/**
	 * 请求银行放款
	 * 
	 * @param appId	appId
	 * @param loanNo	loanNo
	 * @param bwacName	bwacName
	 * @param bwacNo	bwacNo
	 * @param acmngAmtStr	acmngAmtStr
	 * @param guarantAmtStr	guarantAmtStr
	 * @param remark	remark
	 * @param logGroup	logGroup
	 * @return	return
	 */
	private PageInfo requestBankLendding(String appId, String loanNo, String bwacName, String bwacNo,
			String acmngAmtStr, String guarantAmtStr, String remark, String logGroup) {
		PageInfo pi = new PageInfo();

		Document resDoc = hxBankLendding.requestHxBankLending(appId, loanNo, bwacName, bwacNo, acmngAmtStr,
				guarantAmtStr, remark, logGroup);

		if (resDoc != null) {
			if ("0".equals(resDoc.selectSingleNode("//errorCode").getText())) {
				LOGGER.info("{}银行受理放款请求成功，返回信息：\n{}", logGroup, resDoc.asXML());

				Map<String, Object> map = HxBaseData.xmlToMap(resDoc);
				String channelFlow = resDoc.selectSingleNode("//channelFlow").getText();
				map.put("channelFlow", channelFlow);

				pi.setObj(map);
				pi.setCode(ResultInfo.SUCCESS.getCode());
				pi.setMsg("申请成功");

			} else {
				String errorMsg = resDoc.selectSingleNode("//errorMsg").getText();
				LOGGER.info("{}银行受理放款请求失败:{}", logGroup, errorMsg);

				pi.setCode(ResultInfo.BANK_NOT_SUCCESS.getCode());
				pi.setMsg(errorMsg);
			}
			
		} else {
			LOGGER.info("{}请求银行放款失败，请求失败", logGroup);

			pi.setCode(ResultInfo.BANK_REQUEST_NOT_SUCCESS.getCode());
			pi.setMsg("请求失败");
		}

		return pi;
	}

	/**
	 * 处理页面展示信息
	 * 
	 * @param str 需要加工字符串
	 * @param type 1 姓名，格式 姓氏+**；2 银行卡号，尾号4位
	 * @return	return
	 */
	private String processViewInfo(String str, Integer type) {
		String result = "";
		switch (type) {
		case 1:
			result = str.substring(0, 1);
			for (int i = 0; i < str.length() - 1; i++) {
				result += "*";
			}
			break;
		case 2:
			result = "尾号:" + str.substring(str.length() - ResultNumber.FOUR.getNumber(), str.length());
			break;
		default:
			break;
		}
		return result;
	}

	/**
	 * @param hxBanklenddingInfo	hxBanklenddingInfo
	 * @return	姓名+卡号
	 */
	private String baseViewInfo(HxBanklenddingInfo hxBanklenddingInfo) {
		return processViewInfo(hxBanklenddingInfo.getBwacName(), 1) + ","
				+ processViewInfo(hxBanklenddingInfo.getBwacNo(), ResultNumber.TWO.getNumber());
	}
}
