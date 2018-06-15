package com.mrbt.lingmoney.admin.service.bank.impl;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.text.DecimalFormat;
import java.text.ParseException;
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
import org.springframework.util.StringUtils;

import com.mrbt.lingmoney.admin.service.bank.HxAutoRepaymentService;
import com.mrbt.lingmoney.bank.HxBaseData;
import com.mrbt.lingmoney.bank.repayment.HxAutoRepaymentAuthorization;
import com.mrbt.lingmoney.bank.repayment.HxAutoSingleRepayment;
import com.mrbt.lingmoney.bank.repayment.HxQueryAutoRepaymentAuthResult;
import com.mrbt.lingmoney.bank.repayment.HxRepealAutoRepaymentAuthorization;
import com.mrbt.lingmoney.mapper.AccountFlowMapper;
import com.mrbt.lingmoney.mapper.EnterpriseAccountMapper;
import com.mrbt.lingmoney.mapper.HxAccountMapper;
import com.mrbt.lingmoney.mapper.HxAutoRepaymentApplyInfoMapper;
import com.mrbt.lingmoney.mapper.HxBiddingMapper;
import com.mrbt.lingmoney.mapper.HxBorrowerInfoMapper;
import com.mrbt.lingmoney.mapper.HxBorrowerMapper;
import com.mrbt.lingmoney.mapper.HxPaymentMapper;
import com.mrbt.lingmoney.mapper.UsersAccountMapper;
import com.mrbt.lingmoney.model.AccountFlow;
import com.mrbt.lingmoney.model.EnterpriseAccount;
import com.mrbt.lingmoney.model.HxAccount;
import com.mrbt.lingmoney.model.HxAutoRepaymentApplyInfo;
import com.mrbt.lingmoney.model.HxAutoRepaymentApplyInfoExample;
import com.mrbt.lingmoney.model.HxBidding;
import com.mrbt.lingmoney.model.HxBiddingExample;
import com.mrbt.lingmoney.model.HxBorrower;
import com.mrbt.lingmoney.model.HxBorrowerInfo;
import com.mrbt.lingmoney.model.HxBorrowerInfoExample;
import com.mrbt.lingmoney.model.HxPayment;
import com.mrbt.lingmoney.model.UsersAccount;
import com.mrbt.lingmoney.utils.DateUtils;
import com.mrbt.lingmoney.utils.PageInfo;
import com.mrbt.lingmoney.utils.PropertiesUtil;
import com.mrbt.lingmoney.utils.ResultParame;
import com.mrbt.lingmoney.utils.ResultParame.ResultBigDecimal;
import com.mrbt.lingmoney.utils.ResultParame.ResultInfo;

/**
 * @author syb
 * @date 2017年6月6日 下午5:14:26
 * @version 1.0
 * @description
 **/
@Service
public class HxAutoRepaymentServiceImpl implements HxAutoRepaymentService {
	
	private static final Logger LOGGER = LogManager.getLogger(HxAutoRepaymentServiceImpl.class);

	private static final String BANKURL = PropertiesUtil.getPropertiesByKey("BANK_POST_URL");
	
	private static final String AUTO_REPAYMENT_AUTH_URL = PropertiesUtil.getPropertiesByKey("AUTO_REPAYMENT_AUTH_URL");

	@Autowired
	private HxAutoRepaymentAuthorization hxAutoRepaymentAuthorization;
	@Autowired
	private HxQueryAutoRepaymentAuthResult hxQueryAutoRepaymentAuthResult;
	@Autowired
	private HxRepealAutoRepaymentAuthorization hxRepealAutoRepaymentAuthorization;
	@Autowired
	private HxAutoSingleRepayment hxAutoSingleRepayment;
	@Autowired
	private HxBorrowerMapper hxBorrowerMapper;
	@Autowired
	private HxBiddingMapper hxBiddingMapper;
	@Autowired
	private HxBorrowerInfoMapper hxBorrowerInfoMapper;
	@Autowired
	private HxAutoRepaymentApplyInfoMapper hxAutoRepaymentApplyInfoMapper;
	@Autowired
	private HxPaymentMapper hxPaymentMapper;
	@Autowired
	private HxAccountMapper hxAccountMapper;
	@Autowired
	private AccountFlowMapper accountFlowMapper;
	@Autowired
	private UsersAccountMapper usersAccountMapper;
	@Autowired
	private EnterpriseAccountMapper enterpriseAccountMapper;
	
	@Override
	public PageInfo askForAutoRepaymentAuthorization(Integer clientType, String appId, String loanNo, String borrowerId, String remark, String logGroup) {
		PageInfo pi = new PageInfo();

		if (StringUtils.isEmpty(loanNo) || StringUtils.isEmpty(borrowerId)) {
			pi.setCode(ResultInfo.PARAM_MISS.getCode());
			pi.setMsg(ResultInfo.PARAM_MISS.getMsg());
			return pi;
		}

		HxBorrower borrower = hxBorrowerMapper.selectByPrimaryKey(borrowerId);
		if (borrower != null) {

			String acName = null;
			String acNo = null;
			// 个人账户
			if ("1010".equals(borrower.getBwIdtype())) {
				HxAccount hxAccount = hxAccountMapper.selectByPrimaryKey(borrower.getAccId());
				if (hxAccount != null) {
					acName = hxAccount.getAcName();
					acNo = hxAccount.getAcNo();
				} else {
					pi.setCode(ResultInfo.EMPTY_DATA.getCode());
					pi.setMsg("未查询到借款人E账户信息");
				}
			} else {
				// 企业账户
				EnterpriseAccount enterpriseAccount = enterpriseAccountMapper.selectByPrimaryKey(borrower.getAccId());
				if (enterpriseAccount != null) {
					acName = enterpriseAccount.getEnterpriseName();
					acNo = enterpriseAccount.getBankNo();
				}
			}

			if (acName != null && acNo != null) {
				// 跳转银行页面请求参数
				Map<String, String> contentMap = hxAutoRepaymentAuthorization.requestAutoRepaymentAuthorization(clientType, appId, loanNo, acName, acNo, remark, AUTO_REPAYMENT_AUTH_URL, logGroup);
				contentMap.put("bankUrl", BANKURL);

				pi.setCode(ResultInfo.SUCCESS.getCode());
				pi.setMsg("生成报文成功");
				pi.setObj(contentMap);

				// 如果是二次申请自动还款，更新数据，不再插入新数据
				HxAutoRepaymentApplyInfoExample example = new HxAutoRepaymentApplyInfoExample();
				example.createCriteria().andLoanNoEqualTo(loanNo).andBorrowerIdEqualTo(borrowerId);
				List<HxAutoRepaymentApplyInfo> applyInfoList = hxAutoRepaymentApplyInfoMapper.selectByExample(example);

				HxAutoRepaymentApplyInfo applyInfo = null;
				boolean firstApply = false;

				if (applyInfoList == null || applyInfoList.isEmpty()
						|| (applyInfo = applyInfoList.get(0)).getState() == ResultParame.ResultNumber.TWO.getNumber()
						|| (applyInfo = applyInfoList.get(0)).getState() == ResultParame.ResultNumber.THREE
								.getNumber()) {

					if (applyInfo == null) {
						firstApply = true;
						applyInfo = new HxAutoRepaymentApplyInfo();
						applyInfo.setId(UUID.randomUUID().toString().replace("-", ""));
					} else {
						applyInfo.setErrorMsg(null);
						applyInfo.setResponseTime(null);
					}

					applyInfo.setBorrowerId(borrowerId);
					applyInfo.setLoanNo(loanNo);
					applyInfo.setApplyTime(new Date());
					applyInfo.setChannelFlow(contentMap.get("channelFlow"));
					applyInfo.setRemark(remark);
					applyInfo.setState(0);

					if (firstApply) {
						int i = hxAutoRepaymentApplyInfoMapper.insertSelective(applyInfo);

						if (i > 0) {
							LOGGER.info("{}初次请求自动还款授权信息入库成功。\n{}", logGroup, applyInfo.toString());
						} else {
							LOGGER.error("{}初次请求自动还款授权信息入库失败。\n{}", logGroup, applyInfo.toString());
						}

					} else {
						int i = hxAutoRepaymentApplyInfoMapper.updateByPrimaryKey(applyInfo);

						if (i > 0) {
							LOGGER.info("{}二次请求自动还款授权信息入库成功。\n{}", logGroup, applyInfo.toString());
						} else {
							LOGGER.error("{}二次请求自动还款授权信息入库失败。\n{}", logGroup, applyInfo.toString());
						}
					}

				} else {
					if (applyInfo.getState() == 0) {
						pi.setCode(ResultParame.ResultInfo.BANK_ING.getCode());
						pi.setMsg("请求受理中,请勿再次申请");
					} else if (applyInfo.getState() == 1) {
						pi.setCode(ResultParame.ResultInfo.AUTOMATIC_BORROW.getCode());
						pi.setMsg("自动还款已授权");
					}
				}

			} else {
				pi.setCode(ResultParame.ResultInfo.EMPTY_DATA.getCode());
				pi.setMsg("未查询到借款人E账户信息");
			}

		} else {
			pi.setCode(ResultParame.ResultInfo.EMPTY_DATA.getCode());
			pi.setMsg("未查询到借款人信息");
		}

		return pi;
	}

	@Override
	public String autoRepaymentAuthCallBack(String xml, String note, String logGroup) {
		Document document = HxBaseData.analysisAsyncMsg(xml, logGroup);

		// 有数据则表示成功
		if (document != null) {
			LOGGER.info("{}解密后银行异步通知：\n{}", logGroup, document.asXML());

			Map<String, Object> map = HxBaseData.xmlToMap(document);

			// 银行交易流水号
			String bankFlow = (String) map.get("RESJNLNO");
			// 原交易流水号
			String transFlow = (String) map.get("OLDREQSEQNO");

			HxAutoRepaymentApplyInfoExample autoRepaymentExample = new HxAutoRepaymentApplyInfoExample();
			autoRepaymentExample.createCriteria().andLoanNoEqualTo(note).andChannelFlowEqualTo(transFlow);
			autoRepaymentExample.setOrderByClause("apply_time desc");
			List<HxAutoRepaymentApplyInfo> autoRepaymentList = hxAutoRepaymentApplyInfoMapper
					.selectByExample(autoRepaymentExample);

			// 更新自动还款申请信息表状态为成功
			if (autoRepaymentList != null && autoRepaymentList.size() > 0) {
				HxAutoRepaymentApplyInfo applyInfo = autoRepaymentList.get(0);

				HxAutoRepaymentApplyInfo record = new HxAutoRepaymentApplyInfo();
				record.setId(applyInfo.getId());
				record.setState(1);
				record.setBankFlow(bankFlow);
				record.setResponseTime(new Date());
				int i = hxAutoRepaymentApplyInfoMapper.updateByPrimaryKeySelective(record);

				if (i > 0) {
					LOGGER.info("{}更新自动还款记录成功。\n{}", logGroup, record.toString());
				} else {
					LOGGER.error("{}更新自动还款记录失败。\n{}", logGroup, record.toString());
				}
			}

			HxBiddingExample biddingExmaple = new HxBiddingExample();
			biddingExmaple.createCriteria().andLoanNoEqualTo(note);
			List<HxBidding> hxBiddingList = hxBiddingMapper.selectByExample(biddingExmaple);

			// 更新借款人信息表：自动还款
			if (hxBiddingList != null && hxBiddingList.size() > 0) {
				HxBidding hxBidding = hxBiddingList.get(0);
				HxBorrowerInfoExample borrowInfoExample = new HxBorrowerInfoExample();
				borrowInfoExample.createCriteria().andBiddingIdEqualTo(hxBidding.getId());
				List<HxBorrowerInfo> borrowInfoList = hxBorrowerInfoMapper.selectByExample(borrowInfoExample);

				if (borrowInfoList != null && borrowInfoList.size() > 0) {
					HxBorrowerInfo borrower = borrowInfoList.get(0);

					HxBorrowerInfo record = new HxBorrowerInfo();
					record.setId(borrower.getId());
					int i = hxBorrowerInfoMapper.updateByPrimaryKeySelective(record);

					if (i > 0) {
						LOGGER.info("{}更新借款人信息表成功。\n{}", logGroup, record.toString());
					} else {
						LOGGER.error("{}更新借款人信息记录表失败。\n{}", logGroup, record.toString());
					}
				}
			}

			// 生成应答返回报文
			Map<String, Object> resMap = HxBaseData.xmlToMap(document);
			String resMsg = hxAutoRepaymentAuthorization.responseAutoRepaymentAuthorization(resMap, logGroup);
			if (resMsg != null) {
				return resMsg;
			}

		}

		return "";
	}

	@Override
	public PageInfo queryAutoRepaymentAuthResult(String appId, String loanNo, String logGroup) {
		PageInfo pi = new PageInfo();

		if (StringUtils.isEmpty(loanNo)) {
			pi.setCode(ResultParame.ResultInfo.PARAM_MISS.getCode());
			pi.setMsg("参数缺失");
			return pi;
		}

		HxAutoRepaymentApplyInfoExample autoRepaymentExample = new HxAutoRepaymentApplyInfoExample();
		autoRepaymentExample.createCriteria().andLoanNoEqualTo(loanNo);
		autoRepaymentExample.setOrderByClause("apply_time desc");
		List<HxAutoRepaymentApplyInfo> autoRepaymentList = hxAutoRepaymentApplyInfoMapper
				.selectByExample(autoRepaymentExample);

		// 只用状态为 0(处理中) 的数据才提交银行请求
		if (autoRepaymentList != null && autoRepaymentList.size() > 0) {
			HxAutoRepaymentApplyInfo applyInfo = autoRepaymentList.get(0);

			if (applyInfo.getState() == 0) {
				// 原交易流水号
				String oldReqSeqNo = applyInfo.getChannelFlow();

				Document resDoc = hxQueryAutoRepaymentAuthResult.requestQueryAutoRepaymentAuthResult(appId,
						oldReqSeqNo, logGroup);

				if (resDoc != null) {
					// 返回的错误码
					String errorCode = resDoc.selectSingleNode("//errorCode").getText();

					// 入库信息
					HxAutoRepaymentApplyInfo record = new HxAutoRepaymentApplyInfo();
					record.setId(applyInfo.getId());
					record.setResponseTime(new Date());
					// 数据库更新结果
					int result = 0;

					// 银行受理 只有errorCode =0时才返回，即正常响应时才返回
					if ("0".equals(errorCode)) {
						LOGGER.info(logGroup + "查询银行自动还款授权结果请求成功，返回信息：\n" + resDoc);

						Map<String, Object> map = HxBaseData.xmlToMap(resDoc);

						/*
						 * 交易状态 S 成功 F 失败 R 处理中（客户仍停留在页面操作，25分钟仍R状态的可置为交易失败。） N
						 * 未知（已提交后台，需再次发查询接口。）
						 */
						String status = (String) map.get("RETURN_STATUS");
						if ("S".equals(status)) {
							pi.setCode(ResultParame.ResultInfo.SUCCESS.getCode());
							pi.setMsg("授权成功");

							record.setState(1);

							result = hxAutoRepaymentApplyInfoMapper.updateByPrimaryKeySelective(record);
						} else if ("F".equals(status)) {
							pi.setCode(ResultParame.ResultInfo.AUTOMATIC_BORROW_NOT_SUCCESS.getCode());
							pi.setMsg("授权失败");

							String errorMsg = (String) map.get("ERRORMSG");
							record.setState(ResultParame.ResultNumber.TWO.getNumber());
							record.setErrorMsg(errorMsg);

							result = hxAutoRepaymentApplyInfoMapper.updateByPrimaryKeySelective(record);

							LOGGER.info("{}申请自动还款授权失败。 loanNo : {}", logGroup, loanNo);
						} else if ("R".equals(status)) {
							Date responseTime = applyInfo.getApplyTime();
							Date now = new Date();
							// 接口中25分钟超时，此处加5分钟误差时间，按30分钟超时处理
							if (DateUtils.dateDiffMins(responseTime, now) > ResultParame.ResultNumber.THIRTY
									.getNumber()) {
								pi.setCode(ResultParame.ResultInfo.AUTOMATIC_BORROW_NOT_SUCCESS.getCode());
								pi.setMsg("授权失败");

								record.setState(ResultParame.ResultNumber.TWO.getNumber());
								record.setErrorMsg("请求已超过25分钟");

								result = hxAutoRepaymentApplyInfoMapper.updateByPrimaryKeySelective(record);

							} else {
								pi.setCode(ResultParame.ResultInfo.BANK_ING.getCode());
								pi.setMsg("处理中,请稍后再试");
							}

						} else if ("N".equals(status)) {
							pi.setCode(ResultParame.ResultInfo.BANK_TIMEOUT.getCode());
							pi.setMsg("请求未知,请与管理员联系");
							
							LOGGER.info("{}自动还款授权结果查询，返回未知状态。借款编号：{}", logGroup, loanNo);
						}

					} else {
						String errorMsg = resDoc.selectSingleNode("//errorMsg").getText();
						if ("OGWERR999997".equals(errorCode)) {
							// 无此交易流水 ， 设置失败，重新申请
							record.setState(ResultParame.ResultNumber.TWO.getNumber());
							record.setErrorMsg(errorMsg);

							result = hxAutoRepaymentApplyInfoMapper.updateByPrimaryKeySelective(record);
						}

						pi.setCode(ResultParame.ResultInfo.BANK_NOT_SUCCESS.getCode());
						pi.setMsg(errorMsg);

					}

					if (result > 0) {
						LOGGER.info("{}自动还款授权数据入库成功。\n{}", logGroup, record.toString());
					} else {
						LOGGER.error("{}自动还款授权数据入库失败。\n{}", logGroup, record.toString());
					}

				} else {
					pi.setCode(ResultParame.ResultInfo.EMPTY_DATA.getCode());
					pi.setMsg("银行无响应");
				}

			} else if (applyInfo.getState() == 1) {
				pi.setCode(ResultParame.ResultInfo.SUCCESS.getCode());
				pi.setMsg("自动还款授权成功");

			} else if (applyInfo.getState() == ResultParame.ResultNumber.TWO.getNumber()) {
				pi.setCode(ResultParame.ResultInfo.AUTOMATIC_BORROW_NOT_SUCCESS.getCode());
				pi.setMsg("自动还款授权失败");

			} else if (applyInfo.getState() == ResultParame.ResultNumber.THREE.getNumber()) {
				pi.setCode(ResultParame.ResultInfo.SUCCESS.getCode());
				pi.setMsg("自动还款授权已撤销,请重新申请");
			}

		} else {
			pi.setCode(ResultParame.ResultInfo.EMPTY_DATA.getCode());
			pi.setMsg("您还未申请自动还款授权");

		}

		return pi;
	}

	@Override
	public PageInfo repealAutoRepaymentAuthorization(String appId, String otpSeqNo, String otpNo, String loanNo,
			String remark, String logGroup) {
		PageInfo pi = new PageInfo();

		if (StringUtils.isEmpty(otpSeqNo) || StringUtils.isEmpty(otpNo) || StringUtils.isEmpty(loanNo)) {
			pi.setCode(ResultParame.ResultInfo.PARAM_MISS.getCode());
			pi.setMsg("参数缺失");
			return pi;
		}

		// 验证自动还款授权信息
		HxAutoRepaymentApplyInfoExample autoRepaymentExample = new HxAutoRepaymentApplyInfoExample();
		autoRepaymentExample.createCriteria().andLoanNoEqualTo(loanNo);
		autoRepaymentExample.setOrderByClause("apply_time desc");
		List<HxAutoRepaymentApplyInfo> autoRepaymentList = hxAutoRepaymentApplyInfoMapper
				.selectByExample(autoRepaymentExample);

		if (autoRepaymentList != null && autoRepaymentList.size() > 0) {
			HxAutoRepaymentApplyInfo applyInfo = autoRepaymentList.get(0);

			// 自动还款授权成功用户才能撤销
			if (applyInfo.getState() == 1) {

				HxBorrower hxBorrower = hxBorrowerMapper.selectByPrimaryKey(applyInfo.getBorrowerId());
				if (hxBorrower != null) {
					HxAccount hxAccount = hxAccountMapper.selectByPrimaryKey(hxBorrower.getAccId());
					if (hxAccount != null) {
						Document resDoc = hxRepealAutoRepaymentAuthorization
								.requestRepealAutoRepaymentAuthorization(appId, otpSeqNo, otpNo, loanNo,
										hxAccount.getAcName(), hxAccount.getAcNo(), remark, logGroup);

						if (resDoc != null) {
							// 有数据表示成功
							if ("0".equals(resDoc.selectSingleNode("//errorCode").getText())) {
								LOGGER.info("{}撤销自动还款授权成功。\n{}", logGroup, resDoc.asXML());

								pi.setCode(ResultParame.ResultInfo.SUCCESS.getCode());
								pi.setMsg("操作成功");

								Map<String, Object> resMap = HxBaseData.xmlToMap(resDoc);

								HxAutoRepaymentApplyInfo record = new HxAutoRepaymentApplyInfo();
								record.setId(applyInfo.getId());
								record.setState(ResultParame.ResultNumber.THREE.getNumber());
								record.setRemark(remark);
								record.setBankFlow((String) resMap.get("RESJNLNO"));
								try {
									String transDt = (String) resMap.get("TRANSDT");
									String transTm = (String) resMap.get("TRANSTM");
									record.setResponseTime(new SimpleDateFormat("yyyyMMddHHmmss").parse(transDt + transTm));
								} catch (ParseException e) {
									e.printStackTrace();
								}
								int i = hxAutoRepaymentApplyInfoMapper.updateByPrimaryKeySelective(record);

								if (i > 0) {
									LOGGER.info("{}撤销自动还款授权数据入库成功。\n{}", logGroup, record.toString());
								} else {
									LOGGER.error("{}撤销自动还款授权数据入库失败。\n{}", logGroup, record.toString());
								}

							} else {
								String errorMsg = resDoc.selectSingleNode("//errorMsg").getText();
								LOGGER.info("{}撤销自动还款授权失败。 {}", logGroup, errorMsg);

								pi.setCode(ResultParame.ResultInfo.BANK_NOT_SUCCESS.getCode());
								pi.setMsg("操作失败：" + errorMsg);
							}
						} else {
							LOGGER.info("{}撤销自动还款授权失败。请求银行处理失败", logGroup);

							pi.setCode(ResultParame.ResultInfo.BANK_REQUEST_NOT_SUCCESS.getCode());
							pi.setMsg("请求失败");
						}
					} else {
						pi.setCode(ResultParame.ResultInfo.EMPTY_DATA.getCode());
						pi.setMsg("未查询到指定借款人E账户信息");
					}
					
				} else {
					pi.setCode(ResultParame.ResultInfo.EMPTY_DATA.getCode());
					pi.setMsg("未查询到指定借款人信息");
				}

			} else if (applyInfo.getState() == 0) {
				pi.setCode(ResultParame.ResultInfo.BANK_ING.getCode());
				pi.setMsg("自动还款授权处理中");

			} else if (applyInfo.getState() == ResultParame.ResultNumber.TWO.getNumber()) {
				pi.setCode(ResultParame.ResultInfo.AUTOMATIC_BORROW_NOT_SUCCESS.getCode());
				pi.setMsg("自动还款授权失败");

			} else if (applyInfo.getState() == ResultParame.ResultNumber.THREE.getNumber()) {
				pi.setCode(ResultParame.ResultInfo.NOT_AUTOMATIC_BORROW.getCode());
				pi.setMsg("自动还款授权已撤销");
			}

		} else {
			pi.setCode(ResultParame.ResultInfo.NOT_AUTOMATIC_BORROW.getCode());
			pi.setMsg("未开通自动还款");

		}

		return pi;
	}

	@Override
	public PageInfo requestAutoSingleRepayment(String appId, String biddingId, double feeAmt, double amount,
			String remark, String logGroup) {
		PageInfo pi = new PageInfo();
		DecimalFormat df = new DecimalFormat("0.00");
		df.setRoundingMode(RoundingMode.HALF_UP);

		if (StringUtils.isEmpty(biddingId) || StringUtils.isEmpty(amount)) {
			pi.setCode(ResultInfo.PARAM_MISS.getCode());
			pi.setMsg(ResultInfo.PARAM_MISS.getMsg());
			return pi;
		}
		// 银行要求两位小数格式 0.00, 管理费不能为0，可以为空，数据库保存为4位小数，所以此处需要判断大于等于0.005
		String feeAmtStr = null;
		if (!StringUtils.isEmpty(feeAmt) && feeAmt >= ResultBigDecimal.ZPZZF.getBigDec().doubleValue()) {
			feeAmtStr = df.format(feeAmt);
		}
		HxBidding bidding = hxBiddingMapper.selectByPrimaryKey(biddingId);
		if (bidding != null) {
			String loanNo = bidding.getLoanNo();
			// 申请自动还款必须先开通授权
			HxAutoRepaymentApplyInfoExample autoRepaymentExample = new HxAutoRepaymentApplyInfoExample();
			autoRepaymentExample.createCriteria().andLoanNoEqualTo(loanNo);
			autoRepaymentExample.setOrderByClause("apply_time desc");
			List<HxAutoRepaymentApplyInfo> autoRepaymentList = hxAutoRepaymentApplyInfoMapper.selectByExample(autoRepaymentExample);

			if (autoRepaymentList != null && autoRepaymentList.size() > 0) {
				HxAutoRepaymentApplyInfo applyInfo = autoRepaymentList.get(0);
				// 还款授权成功用户才能自动还款
				if (applyInfo.getState() == 1) {
					HxBorrower hxBorrower = hxBorrowerMapper.selectByPrimaryKey(applyInfo.getBorrowerId());

					if (hxBorrower != null) {
                        String acName = null;
                        String acNo = null;
                        String uid = null;

                        if (hxBorrower.getBwIdtype().equals("1010")) {
                            // 个人账户
                            HxAccount hxAccount = hxAccountMapper.selectByPrimaryKey(hxBorrower.getAccId());
                            acName = hxAccount.getAcName();
                            acNo = hxAccount.getAcNo();
                            uid = hxAccount.getuId();

                        } else if (hxBorrower.getBwIdtype().equals("2020")) {
                            // 企业账户
                            EnterpriseAccount enterpriseAccount = enterpriseAccountMapper.selectByPrimaryKey(hxBorrower
                                    .getAccId());
                            acName = enterpriseAccount.getEnterpriseName();
                            acNo = enterpriseAccount.getBankNo();

                        } else {
							pi.setCode(ResultParame.ResultInfo.SERVER_ERROR.getCode());
                            pi.setMsg("账户信息有误，身份类型无效");
                            return pi;
                        }

						if (!StringUtils.isEmpty(acName) && !StringUtils.isEmpty(acNo)) {
							Map<String, Document> resultMap = hxAutoSingleRepayment.requestAutoSingleRepayment(appId,
                                    loanNo, acName, acNo, feeAmtStr, df.format(amount),
									remark, logGroup);

							if (resultMap.containsKey("success")) {
								Document resDoc = resultMap.get("success");

								// 响应正常
								if ("0".equals(resDoc.selectSingleNode("//errorCode").getText())) {
									
									pi.setCode(ResultParame.ResultInfo.SUCCESS.getCode());
									pi.setMsg("还款成功");

									LOGGER.info("{}单笔自动还款成功。\n{}", logGroup, resDoc.asXML());

									saveRepaymentInfo(biddingId, feeAmt, amount, logGroup, hxBorrower, resDoc, 1);

									Map<String, Object> resMap = HxBaseData.xmlToMap(resDoc);
									String channelFlow = (String) resMap.get("channelFlow");
									// 还款成功操作
                                    operateAfterRepaymentSuccess(amount, logGroup, channelFlow, uid, acNo, loanNo);

								} else {
									String errorMsg = resDoc.selectSingleNode("//errorMsg").getText();
									LOGGER.info("{}单笔自动还款失败:{}", logGroup, errorMsg);

									pi.setCode(ResultParame.ResultInfo.BANK_NOT_SUCCESS.getCode());
									pi.setMsg(errorMsg);

									saveRepaymentInfo(biddingId, feeAmt, amount, logGroup, hxBorrower, resDoc,
											ResultParame.ResultNumber.THREE.getNumber());
								}
								
							} else {
								// 未收到返回结果时，保存请求信息，用于查询结果
								LOGGER.info("{}单笔自动还款，请求银行处理，结果未知。" + logGroup);

								Document reqDoc = resultMap.get("fail");

								saveRepaymentInfo(biddingId, feeAmt, amount, logGroup, hxBorrower, reqDoc,
										ResultParame.ResultNumber.TWO.getNumber());

								pi.setCode(ResultParame.ResultInfo.NOT_TRADING_RESULT.getCode());
								pi.setMsg("自动还款，结果未知，请通过查询接口查询");
							}

						} else {
							LOGGER.info("{}单笔自动还款失败，未查询到指定借款人E账户信息", logGroup);

							pi.setCode(ResultParame.ResultInfo.EMPTY_DATA.getCode());
							pi.setMsg("还款失败,未查询到指定借款人E账户信息");
						}
						
						
					} else {
						LOGGER.info("{}单笔自动还款失败，未查询到指定借款人信息", logGroup);

						pi.setCode(ResultParame.ResultInfo.EMPTY_DATA.getCode());
						pi.setMsg("还款失败,未查询到指定借款人信息");
					}
					
				} else if (applyInfo.getState() == 0) {
					LOGGER.info("{}单笔自动还款失败，自动还款授权处理中", logGroup);

					pi.setCode(ResultParame.ResultInfo.BANK_ING.getCode());
					pi.setMsg("还款失败,自动还款授权处理中");
				} else if (applyInfo.getState() == ResultParame.ResultNumber.TWO.getNumber()) {
					LOGGER.info("{}单笔自动还款失败，自动还款授权失败", logGroup);

					pi.setCode(ResultParame.ResultInfo.AUTOMATIC_BORROW_NOT_SUCCESS.getCode());
					pi.setMsg("还款失败,自动还款授权失败");
				} else if (applyInfo.getState() == ResultParame.ResultNumber.THREE.getNumber()) {
					LOGGER.info("{}单笔自动还款失败，自动还款授权已撤销", logGroup);

					pi.setCode(ResultParame.ResultInfo.NOT_AUTOMATIC_BORROW.getCode());
					pi.setMsg("还款失败,自动还款授权已撤销");
				} else {
					LOGGER.info("{}单笔自动还款失败，未知状态。{}", logGroup, applyInfo.getState());
				}
				
			} else {
				LOGGER.info("{}单笔自动还款失败，未开通自动还款", logGroup);

				pi.setCode(ResultParame.ResultInfo.NOT_AUTOMATIC_BORROW.getCode());
				pi.setMsg("未开通自动还款");
			}
			
		} else {
			LOGGER.info("{}单笔自动还款失败，未查询到标的信息", logGroup);

			pi.setCode(ResultParame.ResultInfo.EMPTY_DATA.getCode());
			pi.setMsg("未查询到标的信息");
		}

		return pi;
	}

	@Override
	public void operateAfterRepaymentSuccess(Double amount, String logGroup, String channelFlow,
			String uid, String acNO, String loanNo) {

        // 如果是个人用户
        if (!StringUtils.isEmpty(uid)) {
            BigDecimal bdAmount = new BigDecimal(amount.toString());

            UsersAccount usersAccount = usersAccountMapper.selectByUid(uid);
            UsersAccount usersAccountRecord = new UsersAccount();
            usersAccountRecord.setId(usersAccount.getId());
            // 还款成功后，还款金额与手续费会冻结在借款人账户中，直到 P2P商户提交还款收益明细成功后才会扣除
            usersAccountRecord.setBalance(usersAccount.getBalance().subtract(bdAmount));
            usersAccountRecord.setFrozenMoney(usersAccount.getFrozenMoney().add(bdAmount));
            int result = usersAccountMapper.updateByPrimaryKeySelective(usersAccountRecord);

            if (result > 0) {
                LOGGER.info("{}自动还款成功，扣除借款人账户余额成功，uid：{}，金额：{}", logGroup, uid, amount);

                // 保存一条账户流水
                AccountFlow accountFlow = new AccountFlow();
                accountFlow.setaId(usersAccountRecord.getId());
                accountFlow.setBalance(usersAccountRecord.getBalance());
                accountFlow.setMoney(bdAmount);
                accountFlow.setNote("自动还款");
                accountFlow.setCardPan(acNO);
                accountFlow.setNumber(channelFlow);
                accountFlow.setOperateTime(new Date());
                accountFlow.setPlatform(0);
                accountFlow.setStatus(ResultParame.ResultNumber.FIVE.getNumber());
				accountFlow.setType(ResultParame.ResultNumber.FIVE.getNumber());
                result = accountFlowMapper.insertSelective(accountFlow);

                if (result > 0) {
                    LOGGER.info("{}自动还款成功，插入账户流水记录成功。还款流水：{}", logGroup, accountFlow.getNumber());
                } else {
                    LOGGER.error("{}自动还款成功，插入账户流水记录失败。还款流水：{}", logGroup, accountFlow.getNumber());
                }

            } else {
                LOGGER.error("{}自动还款成功，扣除借款人账户余额失败，uid：{}，金额：{}", logGroup, uid, amount);
            }
        }
		
	}

	/**
	 * 保存还款记录
	 * 
	 * @param biddingId
	 *            biddingId
	 * @param feeAmt
	 *            feeAmt
	 * @param amount
	 *            amount
	 * @param logGroup
	 *            logGroup
	 * @param hxBorrower
	 *            hxBorrower
	 * @param resDoc
	 *            resDoc
	 * @param status
	 *            1-还款成功 2- 还款中 3-还款失败 4-超时
	 */
	private void saveRepaymentInfo(String biddingId, Double feeAmt, Double amount, String logGroup,
			HxBorrower hxBorrower, Document resDoc, Integer status) {
		Map<String, Object> resMap = HxBaseData.xmlToMap(resDoc);

		HxPayment record = new HxPayment();
		record.setId(UUID.randomUUID().toString().replace("-", ""));
		record.setChannelFlow((String) resMap.get("channelFlow"));
		record.setPaymentType(ResultParame.ResultNumber.FOUR.getNumber());
		record.setStatus(status);
        record.setAmount(new BigDecimal(amount.toString()));
		record.setPaymentDate(new Date());
		if (resMap.containsKey("RESJNLNO")) {
			record.setBankFlow((String) resMap.get("RESJNLNO"));
		}

		if (!StringUtils.isEmpty(feeAmt)) {
            record.setFeeAmt(new BigDecimal(feeAmt.toString()));
		}

		// 查询借款信息
		HxBorrowerInfoExample borrowerInfoExample = new HxBorrowerInfoExample();
		borrowerInfoExample.createCriteria().andBiddingIdEqualTo(biddingId).andBwIdEqualTo(hxBorrower.getId());
		List<HxBorrowerInfo> borrowerInfoList = hxBorrowerInfoMapper.selectByExample(borrowerInfoExample);

		if (borrowerInfoList != null && borrowerInfoList.size() > 0) {
			record.setBwId(borrowerInfoList.get(0).getId());
		} else {
			LOGGER.error("{}未查询到借款信息。biddingId: {}, borrowerId: {}", logGroup, biddingId, hxBorrower.getId());
		}

		int i = hxPaymentMapper.insertSelective(record);

		if (i > 0) {
			LOGGER.info("{}保存还款记录成功。\n{}", logGroup, record.toString());
		} else {
			LOGGER.error("{}保存还款记录失败。\n{}", logGroup, record.toString());
		}

	}

}
