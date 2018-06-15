package com.mrbt.lingmoney.admin.service.bank.impl;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import net.sourceforge.pinyin4j.PinyinHelper;
import net.sourceforge.pinyin4j.format.HanyuPinyinCaseType;
import net.sourceforge.pinyin4j.format.HanyuPinyinOutputFormat;
import net.sourceforge.pinyin4j.format.HanyuPinyinToneType;
import net.sourceforge.pinyin4j.format.exception.BadHanyuPinyinOutputFormatCombination;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.dom4j.Document;
import org.dom4j.Element;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import com.mrbt.lingmoney.admin.exception.DataUnCompleteException;
import com.mrbt.lingmoney.admin.service.bank.HxBiddingLossService;
import com.mrbt.lingmoney.admin.service.base.CommonMethodService;
import com.mrbt.lingmoney.bank.HxBaseData;
import com.mrbt.lingmoney.bank.biddingloss.HxBiddingLoss;
import com.mrbt.lingmoney.bank.biddingloss.HxInitiativeBiddingLoss;
import com.mrbt.lingmoney.bank.biddingloss.HxQueryBiddingLossResult;
import com.mrbt.lingmoney.mapper.AccountFlowMapper;
import com.mrbt.lingmoney.mapper.EmployeeRelationsMappingMapper;
import com.mrbt.lingmoney.mapper.HxBiddingLossRecordMapper;
import com.mrbt.lingmoney.mapper.HxBiddingMapper;
import com.mrbt.lingmoney.mapper.HxUsersAccountRepaymentRecordMapper;
import com.mrbt.lingmoney.mapper.ProductMapper;
import com.mrbt.lingmoney.mapper.TradingFixInfoMapper;
import com.mrbt.lingmoney.mapper.TradingMapper;
import com.mrbt.lingmoney.mapper.UsersRedPacketMapper;
import com.mrbt.lingmoney.model.AccountFlow;
import com.mrbt.lingmoney.model.AccountFlowExample;
import com.mrbt.lingmoney.model.EmployeeRelationsMapping;
import com.mrbt.lingmoney.model.HxBidding;
import com.mrbt.lingmoney.model.HxBiddingExample;
import com.mrbt.lingmoney.model.HxBiddingLossRecord;
import com.mrbt.lingmoney.model.HxBiddingLossRecordExample;
import com.mrbt.lingmoney.model.HxUsersAccountRepaymentRecord;
import com.mrbt.lingmoney.model.Product;
import com.mrbt.lingmoney.model.ProductWithBLOBs;
import com.mrbt.lingmoney.model.Trading;
import com.mrbt.lingmoney.model.TradingExample;
import com.mrbt.lingmoney.model.TradingFixInfo;
import com.mrbt.lingmoney.model.UsersRedPacket;
import com.mrbt.lingmoney.model.UsersRedPacketExample;
import com.mrbt.lingmoney.utils.AppCons;
import com.mrbt.lingmoney.utils.MailUtil;
import com.mrbt.lingmoney.utils.PageInfo;
import com.mrbt.lingmoney.utils.PropertiesUtil;
import com.mrbt.lingmoney.utils.ResultParame;
import com.mrbt.lingmoney.utils.ResultParame.ResultInfo;
import com.mrbt.lingmoney.utils.ResultParame.ResultNumber;

/**
 * @author syb
 * @date 2017年6月5日 下午4:27:21
 * @version 1.0
 * @description
 **/
@Service
public class HxBiddingLossServiceImpl implements HxBiddingLossService {
	private static final Logger LOGGER = LogManager.getLogger(HxBiddingLossServiceImpl.class);

	@Autowired
	private HxBiddingLoss hxBiddingLoss;
	@Autowired
	private HxInitiativeBiddingLoss hxInitiativeBiddingLoss;
	@Autowired
	private HxQueryBiddingLossResult hxCheckBiddingLossResult;
	@Autowired
	private HxBiddingMapper hxBiddingMapper;
	@Autowired
	private HxBiddingLossRecordMapper hxBiddingLossRecordMapper;
	@Autowired
	private TradingMapper tradingMapper;
	@Autowired
	private ProductMapper productMapper;
	@Autowired
	private HxUsersAccountRepaymentRecordMapper hxUsersAccountRepaymentRecordMapper;
	@Autowired
	private CommonMethodService commonMethodService;
	@Autowired
	private TradingFixInfoMapper tradingFixInfoMapper;
	@Autowired
	private AccountFlowMapper accountFlowMapper;
	@Autowired
	private EmployeeRelationsMappingMapper employeeRelationsMappingMapper;
	@Autowired
	private UsersRedPacketMapper usersRedPacketMapper;

	@Transactional
	@Override
	public PageInfo applyBiddingLoss(String appId, String loanNo, String cancelReason, String logGroup) {
		PageInfo pageInfo = new PageInfo();
		if (StringUtils.isEmpty(loanNo)) {
			pageInfo.setCode(ResultInfo.PARAM_MISS.getCode());
			pageInfo.setMsg("参数缺失");
			return pageInfo;
		}
		HxBiddingExample example = new HxBiddingExample();
		example.createCriteria().andLoanNoEqualTo(loanNo);
		List<HxBidding> hxBiddingList = hxBiddingMapper.selectByExample(example);
		// 验证标的信息
		if (hxBiddingList != null && hxBiddingList.size() > 0) {
			HxBidding hxBidding = hxBiddingList.get(0);

            // 如果还有支付中的记录，不可申请流标
            TradingExample tradingExmp = new TradingExample();
            tradingExmp.createCriteria().andPIdEqualTo(hxBidding.getpId()).andStatusEqualTo(AppCons.BUY_TRADING);
            int tradingCount = (int) tradingMapper.countByExample(tradingExmp);
            if (tradingCount > 0) {
				pageInfo.setCode(ResultParame.ResultInfo.MODIFY_REJECT.getCode());
                pageInfo.setMsg("还有支付中记录，请处理完成后再申请流标");
                return pageInfo;
            }

			// 验证产品信息：
			Product product = productMapper.selectByPrimaryKey(hxBidding.getpId());
			int productStatus = product.getStatus();

			// 产品筹集期/产品未达标/流标申请中 才可申请流标
			if (productStatus == AppCons.PRODUCT_STATUS_READY || productStatus == AppCons.PRODUCT_STATUS_COLLECT_FAIL
					|| productStatus == AppCons.PRODUCT_STATUS_APPLY_BIDDINGLOSS) {
				HxBiddingLossRecordExample biddingLossExample = new HxBiddingLossRecordExample();
				biddingLossExample.createCriteria().andLoanNoEqualTo(loanNo);
				biddingLossExample.setOrderByClause("apply_time desc");
				List<HxBiddingLossRecord> biddingLossList = hxBiddingLossRecordMapper
						.selectByExample(biddingLossExample);

				// 如果没有流标记录，或者流标失败，则可以申请流标
				HxBiddingLossRecord biddingLoss = null;
				if (biddingLossList == null || biddingLossList.isEmpty()
						|| (biddingLoss = biddingLossList.get(0)).getState() == ResultParame.ResultNumber.TWO
								.getNumber()) {
					// 分析银行响应报文
					Document resDoc = hxBiddingLoss.requestBiddingLoss(appId, loanNo, cancelReason, logGroup);

					if (resDoc != null) {
						// 银行受理 只有errorCode =0时才返回，即正常响应时才返回
						if ("0".equals(resDoc.selectSingleNode("//errorCode").getText())) {
							LOGGER.info(logGroup + "银行受理流标请求成功，返回信息：\n" + resDoc.asXML());

							pageInfo.setCode(ResultInfo.SUCCESS.getCode());
							pageInfo.setMsg("银行受理流标请求成功");

							Map<String, Object> map = HxBaseData.xmlToMap(resDoc);
							// 银行交易流水号
							String resjnlNo = (String) map.get("RESJNLNO");
							// 渠道流水
							String channelFlow = (String) map.get("channelFlow");

							// 如果是二次申请流标，更新原数据，只保存一条流标记录
							boolean firstApply = false;
							if (biddingLoss == null) {
								firstApply = true;
								biddingLoss = new HxBiddingLossRecord();
								biddingLoss.setId(UUID.randomUUID().toString().replace("-", ""));
							}

							biddingLoss.setAppId(appId);
							biddingLoss.setApplyTime(new Date());
							biddingLoss.setBankFlow(resjnlNo);
							biddingLoss.setChannelFlow(channelFlow);
							biddingLoss.setState(0);
							biddingLoss.setCancelReason(cancelReason);
							biddingLoss.setLoanNo(loanNo);
							biddingLoss.setType(0);

							// 数据操作标识
							int result = 0;

							if (firstApply) {
								result = hxBiddingLossRecordMapper.insertSelective(biddingLoss);

								if (result > 0) {
									LOGGER.info(logGroup + "初次申请，保存流标记录成功.\n" + biddingLoss.toString());
								
									// 初次申请 更新产品状态 申请流标
									try {
										updateProductAndTradingAfterBiddingLoss(logGroup, hxBidding,
												AppCons.PRODUCT_STATUS_APPLY_BIDDINGLOSS);
									} catch (Exception e) {
										e.printStackTrace();

										pageInfo.setCode(ResultInfo.SERVER_ERROR.getCode());
										pageInfo.setMsg("系统错误");
										return pageInfo;
									}
								
								} else {
									LOGGER.error(logGroup + "初次申请，保存流标记录失败.\n" + biddingLoss.toString());
								}

							} else {
								result = hxBiddingLossRecordMapper.updateByPrimaryKeySelective(biddingLoss);

								if (result > 0) {
									LOGGER.info(logGroup + "二次申请，更新流标记录成功.\n" + biddingLoss.toString());
								} else {
									LOGGER.error(logGroup + "二次申请，更新流标记录失败.\n" + biddingLoss.toString());
								}
							}

						} else {
							String errorMsg = resDoc.selectSingleNode("//errorMsg").getText();
							LOGGER.info("{}银行受理流标请求失败：{}", logGroup, errorMsg);

							pageInfo.setCode(ResultInfo.BANK_NOT_SUCCESS.getCode());
							pageInfo.setMsg(errorMsg);
						}

					} else {
						LOGGER.info(logGroup + "银行受理流标请求失败，请求失败");

						pageInfo.setCode(ResultInfo.BANK_REQUEST_NOT_SUCCESS.getCode());
						pageInfo.setMsg("请求失败");
					}
					
				} else {
					// 流标状态为 处理中或者成功时，直接返回提示信息
					if (biddingLoss.getState() == 0) {
						pageInfo.setCode(ResultInfo.BANK_ING.getCode());
						pageInfo.setMsg("流标请求处理中,请勿再次申请");
						
					} else if (biddingLoss.getState() == 1) {
						pageInfo.setCode(ResultInfo.SUCCESS.getCode());
						pageInfo.setMsg("该标的已流标");
					}
				}

			} else if (productStatus == AppCons.PRODUCT_STATUS_INIT) {
				pageInfo.setCode(ResultInfo.BIAODI_NOT_FOUND.getCode());
				pageInfo.setMsg("该标的还未成立");
				
			} else if (productStatus == AppCons.PRODUCT_STATUS_RUNNING) {
				pageInfo.setCode(ResultInfo.BIAODI_SUCCESS.getCode());
				pageInfo.setMsg("该标已成立");

			} else if (productStatus == AppCons.PRODUCT_STATUS_BIDDINGLOSSED) {
				pageInfo.setCode(ResultInfo.BIAODI_RETURN.getCode());
				pageInfo.setMsg("该标已是流标状态,不可再次流标");

			} else {
				pageInfo.setCode(ResultInfo.EMPTY_DATA.getCode());
				pageInfo.setMsg("标的状态错误：" + productStatus);
			}
			
		} else {
			LOGGER.info(logGroup + "流标失败，未查询到有效标的信息");

			pageInfo.setCode(ResultInfo.NO_DATA.getCode());
			pageInfo.setMsg("数据有误,请刷新后重试");
		}

		return pageInfo;
	}

	@Transactional
	@Override
	public String handleHxInitiativeBiddingLoss(String message, String logGroup) {
		String errorCode = "0"; // 错误码 默认0：成功
		String errorMsg = "success"; // 错误信息，默认success
		String loanNo = ""; // 借款编号
		String channelFlow = "";
		String status = "0"; // 业务状态 0：受理成功 1：受理失败 2：受理中 3：受理超时，不确定

		if (!StringUtils.isEmpty(message)) {

			Document doc = HxBaseData.analysisAsyncMsg(message, logGroup);

			if (doc != null) {
				Map<String, Object> map = HxBaseData.xmlToMap(doc);
				loanNo = (String) map.get("LOANNO");
				channelFlow = (String) map.get("channelFlow");

				// 只处理标的状态为0（正常）的数据
				HxBiddingExample example = new HxBiddingExample();
				example.createCriteria().andLoanNoEqualTo(loanNo);
				List<HxBidding> hxBiddingList = hxBiddingMapper.selectByExample(example);
				if (hxBiddingList != null && hxBiddingList.size() > 0) {
					HxBidding hxBidding = hxBiddingList.get(0);

					if ("0".equals(hxBidding.getInvestObjState())) {
						// 流标理由
						String cancelReason = (String) map.get("CANCELREASON");

						HxBidding biddingRecord = new HxBidding();
						biddingRecord.setId(hxBidding.getId());
						biddingRecord.setInvestObjState("2");
						int result = hxBiddingMapper.updateByPrimaryKeySelective(biddingRecord);

						if (result > 0) {
							LOGGER.info("{}银行主动流标，更新标的{}状态成功。", logGroup, biddingRecord.getId());
						} else {
							LOGGER.info("{}银行主动流标，更新标的{}状态失败。", logGroup, biddingRecord.getId());
						}

						// 流标记录，如果存在 则更新，否则插入一条数据。
						HxBiddingLossRecordExample biddingLossExample = new HxBiddingLossRecordExample();
						biddingLossExample.createCriteria().andLoanNoEqualTo(loanNo);
						biddingLossExample.setOrderByClause("apply_time desc");
						List<HxBiddingLossRecord> biddingLossList = hxBiddingLossRecordMapper
								.selectByExample(biddingLossExample);

						// 流标入库信息
						HxBiddingLossRecord biddingLossRecord;
						if (biddingLossList != null && biddingLossList.size() > 0) {
							// 二次流标
							biddingLossRecord = biddingLossList.get(0);
							biddingLossRecord.setState(1);
							biddingLossRecord.setCancelReason(cancelReason);
							biddingLossRecord.setType(1);
							biddingLossRecord.setResponseTime(new Date());
							biddingLossRecord.setErrorMsg(null);
							result = hxBiddingLossRecordMapper.updateByPrimaryKeySelective(biddingLossRecord);

							if (result > 0) {
								LOGGER.info("{}银行主动流标,更新流标记录成功。\n{}", logGroup, biddingLossRecord.toString());
							} else {
								LOGGER.error("{}银行主动流标,更新流标记录失败。\n{}", logGroup, biddingLossRecord.toString());
							}
						} else {
							// 首次流标
							biddingLossRecord = new HxBiddingLossRecord();
							biddingLossRecord.setId(UUID.randomUUID().toString().replace("-", ""));
							biddingLossRecord.setAppId("PC");
							biddingLossRecord.setApplyTime(new Date());
							biddingLossRecord.setChannelFlow(channelFlow);
							biddingLossRecord.setState(1);
							biddingLossRecord.setCancelReason(cancelReason);
							biddingLossRecord.setLoanNo(loanNo);
							biddingLossRecord.setType(1);
							biddingLossRecord.setResponseTime(new Date());
							result = hxBiddingLossRecordMapper.insertSelective(biddingLossRecord);

							if (result > 0) {
								LOGGER.info("{}银行主动流标,插入流标记录成功。\n{}", logGroup, biddingLossRecord.toString());
							} else {
								LOGGER.error("{}银行主动流标,插入流标记录失败。\n{}", logGroup, biddingLossRecord.toString());
							}
						}

						try {
							// 更新产品状态为7（已流标）
							updateProductAndTradingAfterBiddingLoss(logGroup, hxBidding,
									AppCons.PRODUCT_STATUS_BIDDINGLOSSED);

							// 初始化回款记录
							initHxRepaymentRecord(loanNo, logGroup);

						} catch (Exception e) {
							e.printStackTrace();
							LOGGER.error("{}银行主动流标，数据更新失败。", logGroup);
						}

						LOGGER.info(logGroup + "处理银行主动流标成功");
					} else {
						errorCode = "100004";
						errorMsg = "标的状态异常";
						status = "1";

						LOGGER.info(logGroup + "处理银行主动流标失败,标的非正常状态：state \t" + hxBidding.getInvestObjState());
					}
				} else {
					errorCode = "100003";
					errorMsg = "未查询到指定借款信息";
					status = "1";

					LOGGER.info(logGroup + "处理银行主动流标失败,未查询到标的信息。loanNo:" + loanNo);
				}
			} else {
				errorCode = "100002";
				errorMsg = "解析数据失败";
				status = "1";

				LOGGER.info(logGroup + "处理银行主动流标失败,解析数据失败");
			}
		} else {
			errorCode = "100001";
			errorMsg = "请求信息为空";
			status = "1";
		}

		return hxInitiativeBiddingLoss.responseInitiativeBiddingLoss(channelFlow, loanNo, errorCode, errorMsg, status,
				logGroup);
	}

	@Transactional
	@Override
	public PageInfo queryBiddingLossResult(String appId, String loanNo, String logGroup) {
		PageInfo pi = new PageInfo();

		if (StringUtils.isEmpty(loanNo)) {
			pi.setCode(ResultInfo.PARAM_MISS.getCode());
			pi.setMsg("参数缺失");
			return pi;
		}

		HxBiddingLossRecordExample biddingLossExample = new HxBiddingLossRecordExample();
		biddingLossExample.createCriteria().andLoanNoEqualTo(loanNo);
		biddingLossExample.setOrderByClause("apply_time desc");
		List<HxBiddingLossRecord> biddingLossList = hxBiddingLossRecordMapper.selectByExample(biddingLossExample);

		if (biddingLossList != null && biddingLossList.size() > 0) {
			HxBiddingLossRecord biddingLossRecord = biddingLossList.get(0);

			// 流标处理中才请求银行查询结果
			if (biddingLossRecord.getState() == 0) {

				// 查询原投标流水号
				String oldTTJNL = "";
				HxBiddingExample biddingExmp = new HxBiddingExample();
				biddingExmp.createCriteria().andLoanNoEqualTo(loanNo);
				List<HxBidding> biddingList = hxBiddingMapper.selectByExample(biddingExmp);

				HxBidding hxBidding = null;
				if (biddingList != null && biddingList.size() > 0) {
					hxBidding = biddingList.get(0);
					oldTTJNL = hxBidding.getOldReqseq();
				} else {
					pi.setCode(ResultInfo.EMPTY_DATA.getCode());
					pi.setMsg("未查询到标的信息");
					return pi;
				}

				Document resDoc = hxCheckBiddingLossResult.requestQueryBiddingLossResult(appId,
						biddingLossRecord.getChannelFlow(), oldTTJNL, logGroup);

				if (resDoc != null) {
					// 银行受理 只有errorCode =0时才返回，即正常响应时才返回
					if ("0".equals(resDoc.selectSingleNode("//errorCode").getText())) {
						LOGGER.info(logGroup + "银行受理流标查询请求成功，返回信息：\n" + resDoc.asXML());

						// 交易状态 L 交易处理中 F 失败 S完成
						String returnStatus = resDoc.selectSingleNode("//RETURN_STATUS").getText();
						if ("F".equals(returnStatus)) {
							// 失败原因
							String errorMsg = resDoc.selectSingleNode("//ERRORMSG").getText();

							pi.setCode(ResultInfo.TRAD_NOT_SUCCESS.getCode());
							pi.setMsg("流标失败：" + errorMsg);

							HxBiddingLossRecord record = new HxBiddingLossRecord();
							record.setId(biddingLossRecord.getId());
							record.setState(ResultNumber.TWO.getNumber());
							record.setErrorMsg(errorMsg);
							record.setResponseTime(new Date());
							int result = hxBiddingLossRecordMapper.updateByPrimaryKeySelective(record);

							if (result > 0) {
								LOGGER.info(logGroup + "流标申请失败，修改流标记录信息成功。\n" + record.toString());
							} else {
								LOGGER.error(logGroup + "流标申请成功，修改流标记录信息失败。\n" + record.toString());
							}

						} else if ("L".equals(returnStatus)) {
							pi.setCode(ResultInfo.TRAD_ING.getCode());
							pi.setMsg("流标请求处理中");
							
						} else if ("S".equals(returnStatus)) {
							// 保存流标记录
							HxBiddingLossRecord record = new HxBiddingLossRecord();
							record.setId(biddingLossRecord.getId());
							record.setState(1);
							record.setResponseTime(new Date());
							int result = hxBiddingLossRecordMapper.updateByPrimaryKeySelective(record);

							if (result > 0) {
								LOGGER.info(logGroup + "流标成功，流标记录数据入库成功。\n" + record.toString());

								pi.setCode(ResultInfo.TRAD_COMPLATE.getCode());
								pi.setMsg("流标成功");

								try {
									// 更新产品状态为（已流标）
									updateProductAndTradingAfterBiddingLoss(logGroup, hxBidding,
											AppCons.PRODUCT_STATUS_BIDDINGLOSSED);

									// 初始化回款记录
									initHxRepaymentRecord(loanNo, logGroup);
								} catch (Exception e) {
									e.printStackTrace();

									pi.setCode(ResultInfo.SERVER_ERROR.getCode());
									pi.setMsg("系统错误");
									return pi;
								}

							} else {
								LOGGER.error(logGroup + "流标成功，流标记录数据入库失败。\n" + record.toString());
							}
							
							// 将银行返回投标人信息存入mongo备用
							commonMethodService.saveBanklendingInfoToMongo(resDoc, 1);

							// 该投资人的推荐者为公司内部员工，通过邮件将推荐者发送给公司相关同事, 2018-01-07查询语句有问题，延后处理
//							sendEmailToColleagues(resDoc);
						}

					} else {
						String errorMsg = resDoc.selectSingleNode("//errorMsg").getText();

						pi.setCode(ResultInfo.BANK_NOT_SUCCESS.getCode());
						pi.setMsg(errorMsg);
					}
					
				} else {
					pi.setCode(ResultInfo.BANK_REQUEST_NOT_SUCCESS.getCode());
					pi.setMsg("请求失败");
				}
				
			} else if (biddingLossRecord.getState() == 1) {
				pi.setCode(ResultInfo.SUCCESS.getCode());
				pi.setMsg("流标成功");
				
			} else if (biddingLossRecord.getState() == ResultNumber.TWO.getNumber()) {
				pi.setCode(ResultInfo.SUCCESS.getCode());
				pi.setMsg("流标失败");
			}
			
		} else {
			pi.setCode(ResultInfo.EMPTY_DATA.getCode());
			pi.setMsg("请先申请流标");
		}

		return pi;
	}

	/**
	 * 发送邮件
	 * @param resDoc	resDoc
	 */
	private void sendEmailToColleagues(Document resDoc) {
		@SuppressWarnings("unchecked")
		List<Element> rsList = resDoc.selectNodes("//RSLIST");
		if (rsList != null && rsList.size() > 0) {
			for (Element rsElem : rsList) {
				String acNo = rsElem.selectSingleNode("//ACNO").getText(); // 投资人账号
				String acName = rsElem.selectSingleNode("//ACNAME").getText(); // 投资人账号户名
				String amount = rsElem.selectSingleNode("//AMOUNT").getText(); // 投标金额

				// 通过投资人账号、投资人账号户名查询出投资人的推荐人为公司员工的信息
				Map<String, Object> params = new HashMap<>();
				params.put("acNo", acNo);
				params.put("acName", acName);
				EmployeeRelationsMapping erp = employeeRelationsMappingMapper
						.selectReferee(params);

				if (null != erp) {
					// 发邮件

					// 邮件发送者信息
					String smtpHost = PropertiesUtil.getPropertiesByKey("config/email.properties", "smtpHost");
					String sender = PropertiesUtil.getPropertiesByKey("config/email.properties", "sender");
					String userName = PropertiesUtil.getPropertiesByKey("config/email.properties", "user");
					String pwd = PropertiesUtil.getPropertiesByKey("config/email.properties", "pwd");

					String name = erp.getEmployeeName(); // 推荐人姓名
					String pinyinName = getPinyinByHanzi(name);
					// 邮件接收者
					String receiver = pinyinName + "@wdzggroup.com";
					// 邮件抄送者
					String copyReceiver = PropertiesUtil.getPropertiesByKey("COPY_RECEIVER");
					// 邮件内容
					String context = "领钱儿" + name + "同事流标通知";

					try {
						MailUtil.sendEmail(smtpHost, receiver, copyReceiver, "领钱儿流标通知", context, sender,
								"领钱儿(lingmoney.cn)", userName, pwd, null, null);
					} catch (Exception e) {
						e.printStackTrace();
					}
				}
			}

		}
	}

	/**
	 * 根据汉字名获取拼音名
	 * 
	 * @param name	name
	 * @return	return
	 */
	private static String getPinyinByHanzi(String name) {

		Map<String, List<String>> pinyinMap = initPinyin("/duoyinzi.txt");
	    
		StringBuffer pinyin = new StringBuffer();    
	    
        HanyuPinyinOutputFormat defaultFormat = new HanyuPinyinOutputFormat();    
        defaultFormat.setCaseType(HanyuPinyinCaseType.LOWERCASE);    
        defaultFormat.setToneType(HanyuPinyinToneType.WITHOUT_TONE);    
    
        char[] arr = name.toCharArray();    
        for (int i = 0; i < arr.length; i++) {    
            char ch = arr[i];    
            if (ch > ResultNumber.ONE_HUN_TWEN_TIGHT.getNumber()) { // 非ASCII码    
                // 取得当前汉字的所有全拼    
                try {    
                    String[] results = PinyinHelper.toHanyuPinyinStringArray(ch, defaultFormat);       
                    if (results == null) {  //非中文    
                        return "";    
                    } else {    
                        int len = results.length;    
                        if (len == 1) { // 不是多音字    
//                          pinyin.append(results[0]);    
                            String py = results[0];         
                            if (py.contains("u:")) {  //过滤 u:    
                                py = py.replace("u:", "v");    
                                LOGGER.info("filter u:" + py);    
                            }    
                            pinyin.append(py);            
                        } else if (results[0].equals(results[1])) {    //非多音字 有多个音，取第一个       
//                          pinyin.append(results[0]);    
                            pinyin.append(results[0]);           
                        } else { // 多音字        
                        	LOGGER.info("多音字：" + ch);       
                            int length = name.length();       
                            boolean flag = false;       
                            String s = null;      
                            List<String> keyList = null;       
                            for (int x = 0; x < len; x++) {       
                                String py = results[x];      
                                if (py.contains("u:")) {  //过滤 u:    
                                    py = py.replace("u:", "v");    
                                    LOGGER.info("filter u:" + py);    
                                } 
                                
                                keyList = pinyinMap.get(py); 
                                
                                if (i + ResultNumber.THREE.getNumber() <= length) {   //后向匹配2个汉字  大西洋     
                                    s = name.substring(i, i + ResultNumber.THREE.getNumber());    
                                    if (keyList != null && (keyList.contains(s))) {    
//                                  if (value != null && value.contains(s)) {    
                                    	LOGGER.info("last 2 > " + py);    
//                                      pinyin.append(results[x]);    
                                        pinyin.append(py);    
                                        flag = true;    
                                        break;    
                                    }    
                                }    
                                    
                                if (i + ResultNumber.TWO.getNumber() <= length) {   //后向匹配 1个汉字  大西    
                                    s = name.substring(i, i + ResultNumber.TWO.getNumber());    
                                    if (keyList != null && (keyList.contains(s))) {    
                                    	LOGGER.info("last 1 > " + py);    
//                                      pinyin.append(results[x]);    
                                        pinyin.append(py);    
                                        flag = true;    
                                        break;    
                                    }    
                                }    
                                    
                                if ((i - ResultNumber.TWO.getNumber() >= 0) && (i + 1 <= length)) {  // 前向匹配2个汉字 龙固大    
                                    s = name.substring(i - ResultNumber.TWO.getNumber(), i + 1);    
                                    if (keyList != null && (keyList.contains(s))) {         
                                    	LOGGER.info("before 2 < " + py);    
//                                      pinyin.append(results[x]);    
                                        pinyin.append(py);    
                                        flag = true;    
                                        break;    
                                    }    
                                }    
                                    
                                if ((i - 1 >= 0) && (i + 1 <= length)) {  // 前向匹配1个汉字   固大    
									s = name.substring(i - 1, i + 1);
									if (keyList != null && (keyList.contains(s))) {
										LOGGER.info("before 1 < " + py);    
//                                      pinyin.append(results[x]);    
										pinyin.append(py);
                                        flag = true;    
                                        break;    
                                    }    
                                }    
                                    
                                if ((i - 1 >= 0) && (i + ResultNumber.TWO.getNumber() <= length)) {  //前向1个，后向1个      固大西    
									s = name.substring(i - 1, i + ResultNumber.TWO.getNumber());
									if (keyList != null && (keyList.contains(s))) {
										LOGGER.info("before last 1 <> " + py);    
//                                      pinyin.append(results[x]);    
										pinyin.append(py);
                                        flag = true;    
                                        break;    
                                    }    
                                }    
                            }    
                                
							if (!flag) { // 都没有找到，匹配默认的 读音 大
								s = String.valueOf(ch);
								for (int x = 0; x < len; x++) {
									String py = results[x];
                                    if (py.contains("u:")) {  //过滤 u:    
                                        py = py.replace("u:", "v");    
                                        LOGGER.info("filter u:");    
									}
                                    keyList = pinyinMap.get(py);    
                                        
									if (keyList != null && (keyList.contains(s))) {
										pinyin.append(py);
                                        break;    
                                    }    
                                }    
                            }    
                        }    
                    }    
    
                } catch (BadHanyuPinyinOutputFormatCombination e) {    
                    e.printStackTrace();    
                }    
            } else {    
                pinyin.append(arr[i]);    
            }    
        }    
		return pinyin.toString();
	}


	/**
	 * 所有的多音字词组
	 * @param fileName	fileName
	 * @return 	return
	 */
	private static Map<String, List<String>> initPinyin(String fileName) {
		Map<String, List<String>> pinyinMap = new HashMap<String, List<String>>();
		// 读取多音字的全部拼音表;
		InputStream file = PinyinHelper.class.getResourceAsStream(fileName);

		BufferedReader br = new BufferedReader(new InputStreamReader(file));

		String s = null;
		try {
			while ((s = br.readLine()) != null) {

				if (s != null) {
					String[] arr = s.split("#");
					String pinyin = arr[0];
					String chinese = arr[1];

					if (chinese != null) {
						String[] strs = chinese.split(" ");
						List<String> list = Arrays.asList(strs);
						pinyinMap.put(pinyin, list);
					}
				}
			}

		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			try {
				br.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		return pinyinMap;
	}

	/**
	 * 初始化回款记录，因为涉及数据库事物操作，需要查询交易状态为（冻结中，已流标，支付中）的数据
	 * 
	 * @param loanNo	loanNo
	 * @param logGroup	logGroup
	 * @throws Exception	Exception
	 */
	@Transactional
	public void initHxRepaymentRecord(String loanNo, String logGroup) throws Exception {
		List<Integer> paramlist = new ArrayList<Integer>();
		paramlist.add(AppCons.BUY_FROKEN);
		paramlist.add(AppCons.BUY_LOSS_BID);
		// 查询支付成功/已流标 记录,
		TradingExample example = new TradingExample();
		example.createCriteria().andPCodeEqualTo(loanNo).andStatusIn(paramlist);
		List<Trading> tradingList = tradingMapper.selectByExample(example);

		if (tradingList != null && tradingList.size() > 0) {
			for (Trading trading : tradingList) {
				HxUsersAccountRepaymentRecord repaymentRecord = new HxUsersAccountRepaymentRecord();
				repaymentRecord.setId(UUID.randomUUID().toString().replaceAll("-", ""));
				repaymentRecord.setLoanNo(loanNo);
				repaymentRecord.setInitTime(new Date());
				repaymentRecord.setType(1);
				repaymentRecord.setState(0);
				repaymentRecord.settId(trading.getId());
				repaymentRecord.setAmount(trading.getBuyMoney());

				int result = hxUsersAccountRepaymentRecordMapper.insertSelective(repaymentRecord);

				if (result > 0) {
					LOGGER.info("{}产品流标，借款编号{}，初始化交易号{}回款记录成功", logGroup, loanNo, trading.getId());

				} else {
					LOGGER.error("{}产品流标，借款编号{}，初始化交易号{}回款记录失败", logGroup, loanNo, trading.getId());
					throw new DataUnCompleteException(logGroup + "初始化回款记录失败");
				}
			}
		}

	}

	/**
	 * 流标操作，相关产品状态更新、交易状态更新
	 * 
	 * @param logGroup	logGroup
	 * @param hxBidding	hxBidding
	 * @param status	status
	 * @throws Exception	Exception
	 */
	public void updateProductAndTradingAfterBiddingLoss(String logGroup, HxBidding hxBidding, int status)
			throws Exception {
		int result = 0;
		Product product = productMapper.selectByPrimaryKey(hxBidding.getpId());
		if (product != null) {
			ProductWithBLOBs productRecord = new ProductWithBLOBs();
			productRecord.setId(product.getId());
			productRecord.setStatus(status);
			result = productMapper.updateByPrimaryKeySelective(productRecord);

			if (result > 0) {
				LOGGER.info("{}流标,更新产品{}状态为{}成功。", logGroup, productRecord.getId(), status);

			} else {
				LOGGER.error("{}流标,更新产品{}状态为{}失败。", logGroup, productRecord.getId(), status);
				throw new DataUnCompleteException(logGroup + "更新产品状态失败");
			}

			// 已流标时需要更新该产品下交易冻结状态为流标状态
			if (status == AppCons.PRODUCT_STATUS_BIDDINGLOSSED) {
				// 更新投标人交易状态为 已流标
				TradingExample tradingExmp = new TradingExample();
				tradingExmp.createCriteria().andPIdEqualTo(product.getId()).andStatusEqualTo(AppCons.BUY_FROKEN);

				List<Trading> tradingList = tradingMapper.selectByExample(tradingExmp);
				if (tradingList != null && tradingList.size() > 0) {
					for (Trading trading : tradingList) {
						Trading tradingRecord = new Trading();
						tradingRecord.setId(trading.getId());
						tradingRecord.setStatus(AppCons.BUY_LOSS_BID);
						result = tradingMapper.updateByPrimaryKeySelective(tradingRecord);

						if (result > 0) {
							LOGGER.info("{}流标,更新交易{}状态为已流标成功。", logGroup, tradingRecord.getId());

						} else {
							LOGGER.error("{}流标,更新交易{}状态为已流标失败。", logGroup, tradingRecord.getId());
							throw new DataUnCompleteException(logGroup + "更新trading数据失败");
						}

						TradingFixInfo tradingFixInfo = tradingFixInfoMapper.selectFixInfoByTid(trading.getId());

						if (tradingFixInfo != null) {
							TradingFixInfo tradingFixInfoRecord = new TradingFixInfo();
							tradingFixInfoRecord.setId(tradingFixInfo.getId());
							tradingFixInfoRecord.setStatus(AppCons.BUY_LOSS_BID);
							result = tradingFixInfoMapper.updateByPrimaryKeySelective(tradingFixInfoRecord);

							if (result > 0) {
								LOGGER.info("{}流标,更新交易流水{}状态为已流标成功。", logGroup, tradingFixInfoRecord.getId());

							} else {
								LOGGER.error("{}流标,更新交易流水{}状态为已流标失败。", logGroup, tradingFixInfoRecord.getId());
								throw new DataUnCompleteException(logGroup + "更新trading_fix_info数据失败");
							}

						}

						// 更新账户流水状态为已流标
						AccountFlowExample accountFlowExmp = new AccountFlowExample();
						accountFlowExmp.createCriteria().andNumberEqualTo(trading.getBizCode());

						List<AccountFlow> accountFlowList = accountFlowMapper.selectByExample(accountFlowExmp);
						if (accountFlowList != null && accountFlowList.size() > 0) {
							AccountFlow accountFlow = accountFlowList.get(0);

							AccountFlow accountFlowRecord = new AccountFlow();
							accountFlowRecord.setId(accountFlow.getId());
							accountFlowRecord.setStatus(ResultNumber.FOUR.getNumber());
							result = accountFlowMapper.updateByPrimaryKeySelective(accountFlowRecord);

							if (result > 0) {
								LOGGER.info("{}流标,更新账户流水{}状态为已流标成功。", logGroup, accountFlowRecord.getId());

							} else {
								LOGGER.error("{}流标,更新账户流水{}状态为已流标失败。", logGroup, accountFlowRecord.getId());
								throw new DataUnCompleteException(logGroup + "更新账户流水失败");
							}
						}
						
						// 如果使用过优惠券，恢复优惠券
                        UsersRedPacketExample usersRedPacketExmp = new UsersRedPacketExample();
                        usersRedPacketExmp.createCriteria().andUIdEqualTo(trading.getuId()).andTIdEqualTo(trading.getId()).andStatusEqualTo(1);

                        List<UsersRedPacket> usersRedPacketList = usersRedPacketMapper.selectByExample(usersRedPacketExmp);
                        if (usersRedPacketList != null && usersRedPacketList.size() > 0) {
                            for (UsersRedPacket usersRedPacket : usersRedPacketList) {
                                usersRedPacket.setStatus(0);
                                usersRedPacket.settId(null);
                                usersRedPacket.setActualAmount(null);
                                usersRedPacket.setUsedTime(null);
                                result = usersRedPacketMapper.updateByPrimaryKey(usersRedPacket);

                                if (result > 0) {
                                    LOGGER.info("{}恢复加息券信息成功。", logGroup);

                                } else {
                                    LOGGER.error("{}恢复加息券信息失败。", logGroup);
                                    throw new DataUnCompleteException("更新user_red_packet数据失败，id:" + usersRedPacket.getId());
                                }
                            }

                        } else {
                            LOGGER.info("{}无加息券使用记录");
                        }

					}
				}
			}

		} else {
			LOGGER.error("{}未查询到产品id为{}的产品信息", logGroup, hxBidding.getpId());
			throw new IllegalArgumentException(logGroup + "未查询到产品id为：" + hxBidding.getpId() + "的产品");
		}

	}

}
