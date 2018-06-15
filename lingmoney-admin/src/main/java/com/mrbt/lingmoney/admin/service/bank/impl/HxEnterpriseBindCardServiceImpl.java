package com.mrbt.lingmoney.admin.service.bank.impl;

import java.util.List;
import java.util.Map;
import java.util.UUID;

import org.dom4j.Document;
import org.dom4j.Element;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import com.mrbt.lingmoney.admin.service.bank.HxEnterpriseBindCardService;
import com.mrbt.lingmoney.bank.enterprise.HxEnterpriseBindCard;
import com.mrbt.lingmoney.mapper.EnterpriseBindcardMapper;
import com.mrbt.lingmoney.mapper.EnterpriseRechargeMapper;
import com.mrbt.lingmoney.model.EnterpriseBindcard;
import com.mrbt.lingmoney.model.EnterpriseBindcardExample;
import com.mrbt.lingmoney.model.EnterpriseRecharge;
import com.mrbt.lingmoney.model.EnterpriseRechargeExample;
import com.mrbt.lingmoney.utils.PageInfo;
import com.mrbt.lingmoney.utils.PropertiesUtil;
import com.mrbt.lingmoney.utils.ResultParame.ResultInfo;
import com.mrbt.lingmoney.utils.ResultParame.ResultNumber;

/**
 *
 *@author syb
 *@date 2017年9月29日 下午1:59:18
 *@version 1.0
 **/
@Service
public class HxEnterpriseBindCardServiceImpl implements HxEnterpriseBindCardService {
	
	private static final String BANK_URL = PropertiesUtil.getPropertiesByKey("BANK_POST_URL");

	@Autowired
	private HxEnterpriseBindCard hxEnterpriseBindCard;
	@Autowired
	private EnterpriseRechargeMapper enterpriseRechargeMapper;
	@Autowired
	private EnterpriseBindcardMapper enterpriseBindcardMapper;

	@Override
	public Map<String, String> requestUnBindCard(String appId, Integer clientType, String bankNo, String logGroup) {
		if (StringUtils.isEmpty(bankNo)) {
			return null;
		}

		Map<String, String> resultMap = hxEnterpriseBindCard.requestEnterpriseUnbindCard(clientType, appId, bankNo, "",
				logGroup);
		resultMap.put("bankUrl", BANK_URL);

		return resultMap;
	}

	@Override
	public PageInfo listEnterpriseRechargeRecord(String date, String bankNo, String transType, Integer page,
			Integer rows) {
		PageInfo pi = new PageInfo(page, rows);

		EnterpriseRechargeExample example = new EnterpriseRechargeExample();
		EnterpriseRechargeExample.Criteria criteria = example.createCriteria();
		if (!StringUtils.isEmpty(date)) {
			criteria.andTransDateLike(date.replaceAll("-", "") + "%");
		}
		if (!StringUtils.isEmpty(bankNo)) {
			criteria.andBankAccountNoEqualTo(bankNo);
		}
		if (!StringUtils.isEmpty(transType)) {
			criteria.andTransTypeEqualTo(transType);
		}
		example.setLimitStart(pi.getFrom());
		example.setLimitEnd(pi.getSize());

		List<EnterpriseRecharge> list = enterpriseRechargeMapper.selectByExample(example);
		pi.setRows(list);

		if (list != null && list.size() > 0) {
			pi.setCode(ResultInfo.SUCCESS.getCode());
			pi.setMsg(ResultInfo.SUCCESS.getMsg());
			pi.setTotal(enterpriseRechargeMapper.countByExample(example));
		} else {
			pi.setTotal(0);
		}

		return pi;
	}

	@Override
	public PageInfo listEnterpriseBindCard(String acNo, Integer page, Integer rows) {
		if (StringUtils.isEmpty(acNo)) {
			return null;
		}
		String logGroup = "\nhxEnterpriseBindCardQueryInfo_" + System.currentTimeMillis() + "_";
		// 分页条件写死为 第一页 每页40条
		queryBindCardResult(acNo, "1", "40", logGroup);

		PageInfo pi = new PageInfo(page, rows);
		EnterpriseBindcardExample example = new EnterpriseBindcardExample();
		example.createCriteria().andEAccBanknoEqualTo(acNo);
		example.setLimitStart(pi.getFrom());
		example.setLimitEnd(pi.getSize());

		List<EnterpriseBindcard> list = enterpriseBindcardMapper.selectByExample(example);
		pi.setRows(list);

		if (list != null && list.size() > 0) {
			pi.setTotal(enterpriseBindcardMapper.countByExample(example));
		} else {
			pi.setTotal(0);
		}

		return pi;
	}

	/**
	 * 处理企业绑卡信息查询
	 * @author yiban
	 * @date 2017年10月20日 下午6:46:12
	 * @version 1.0
	 * @param acNo	E账户
	 * @param currentPage	currentPage
	 * @param pageNumber	pageNumber
	 * @param logGroup	logGroup
	 * @param doc		XML文档
	 */
	private void handleEnterpriseBindCardRecord(String acNo, String currentPage, String pageNumber, String logGroup,
			Document doc) {
		// doc为空表示有多页，所以需要再次查询
		if (doc == null) {
			doc = hxEnterpriseBindCard.queryEnterpriseBindCardInfo(acNo, currentPage, pageNumber, logGroup);
		}
		if (doc != null && doc.selectNodes("//CARDS") != null) {
			@SuppressWarnings("unchecked")
			List<Element> rsList = doc.selectNodes("//CARDS");
			if (rsList != null && rsList.size() > 0) {
				for (Element rsElem : rsList) {
					EnterpriseBindcard enterpriseBindCard = new EnterpriseBindcard();

					String acno = rsElem.selectSingleNode("//ACNO").getText(); // 企业银行账户
					String cardno = rsElem.selectSingleNode("//CARDNO").getText(); // 绑定卡
																					// 加密处理;
					String statusid = rsElem.selectSingleNode("//STATUSID").getText(); // 0绑定
																						// 2预绑定
					EnterpriseBindcardExample example = new EnterpriseBindcardExample();
					example.createCriteria().andEAccBanknoEqualTo(acno).andCardNoEqualTo(cardno);

					List<EnterpriseBindcard> cardList = enterpriseBindcardMapper.selectByExample(example);
					if (cardList != null && cardList.size() > 0) {
						enterpriseBindCard = cardList.get(0);
						if (!enterpriseBindCard.getState().equals(Integer.parseInt(statusid))) {
							enterpriseBindCard.setState(Integer.parseInt(statusid));
							enterpriseBindcardMapper.updateByPrimaryKeySelective(enterpriseBindCard);
						}

					} else {
						String cardtype = rsElem.selectSingleNode("//CARDTYPE").getText();
						String binddate = rsElem.selectSingleNode("//BINDDATE").getText();
						String bindtime = rsElem.selectSingleNode("//BINDTIME").getText();

						String otherbankflag = rsElem.selectSingleNode("//OTHERBANKFLAG").getText();
						String banknumber = rsElem.selectSingleNode("//BANKNUMBER").getText();
						String bankname = rsElem.selectSingleNode("//BANKNAME").getText();

						String uuid = UUID.randomUUID().toString().replaceAll("-", "");
						enterpriseBindCard.setId(uuid);
						enterpriseBindCard.seteAccBankno(acno);
						enterpriseBindCard.setCardNo(cardno);
						enterpriseBindCard.setState(Integer.parseInt(statusid));
						enterpriseBindCard.setCardType(cardtype);
						enterpriseBindCard.setBindDate(formatBankDate(binddate, bindtime));
						enterpriseBindCard.setOtherBankFlag(Integer.parseInt(otherbankflag));
						enterpriseBindCard.setBankNumber(banknumber);
						enterpriseBindCard.setBankName(bankname);
						enterpriseBindcardMapper.insertSelective(enterpriseBindCard);
					}
				}
			}
		}
	}

	/**
	 * 查询企业绑卡记录
	 * 
	 * @author syb
	 * @date 2017年9月29日 下午1:57:13
	 * @version 1.0
	 * @param acNo
	 *            银行存管账号
	 * @param currentPage
	 *            当前页
	 * @param pageNumber
	 *            每页条数
	 * @param logGroup
	 *            日志头
	 * @return
	 *
	 */
	private void queryBindCardResult(String acNo, String currentPage, String pageNumber, String logGroup) {
		Document doc = hxEnterpriseBindCard.queryEnterpriseBindCardInfo(acNo, currentPage, pageNumber, logGroup);

		if (doc != null && doc.selectNodes("//CARDS") != null) {
			String errorcode = doc.selectSingleNode("//errorCode").getText();

			if (errorcode.equals("0")) {
				// 页码总数
				String pagecount = doc.selectSingleNode("//PAGECOUNT").getText();
				// 如果页数大于1，从第二页开始循环查询
				if (Integer.parseInt(pagecount) > 1) {
					for (int i = ResultNumber.TWO.getNumber(); i <= Integer.parseInt(pagecount); i++) {
						handleEnterpriseBindCardRecord(acNo, i + "", pageNumber, logGroup, null);
					}

				} else {
					handleEnterpriseBindCardRecord(acNo, currentPage, pageNumber, logGroup, doc);
				}
			}
		}
	}

	/**
	 * 格式化银行返回时间
	 * 
	 * @author yiban
	 * @date 2017年10月23日 上午9:17:16
	 * @version 1.0
	 * @param date
	 *            yyyymmdd
	 * @param time
	 *            hhssmmSSS
	 * @return	返回时间
	 *
	 */
	
	public String formatBankDate(String date, String time) {
		try {
			if (!StringUtils.isEmpty(date)) {
				return date.substring(0, ResultNumber.FOUR.getNumber()) + "-" + date.substring(ResultNumber.FOUR.getNumber(), ResultNumber.SIX.getNumber()) + "-" + date.substring(ResultNumber.SIX.getNumber(), ResultNumber.EIGHT.getNumber()) + " "
						+ time.substring(0, ResultNumber.TWO.getNumber()) + ":" + time.substring(ResultNumber.TWO.getNumber(), ResultNumber.FOUR.getNumber()) + ":" + time.substring(ResultNumber.FOUR.getNumber(), ResultNumber.SIX.getNumber());
			} else {
				return "";
			}
		} catch (Exception e) {
			e.printStackTrace();
			return "";
		}
	}

}
