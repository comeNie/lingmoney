package com.mrbt.lingmoney.service.web.impl;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Hashtable;
import java.util.List;
import java.util.Map;

import net.sf.json.JSONObject;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.ui.ModelMap;
import org.springframework.util.StringUtils;

import com.mrbt.lingmoney.mapper.AccountFlowMapper;
import com.mrbt.lingmoney.mapper.ActivityRecordMapper;
import com.mrbt.lingmoney.mapper.GiftDetailMapper;
import com.mrbt.lingmoney.mapper.HxAccountMapper;
import com.mrbt.lingmoney.mapper.HxCardMapper;
import com.mrbt.lingmoney.mapper.PrefixBankMapper;
import com.mrbt.lingmoney.mapper.SupportBankMapper;
import com.mrbt.lingmoney.mapper.UsersAccountMapper;
import com.mrbt.lingmoney.mapper.UsersBankMapper;
import com.mrbt.lingmoney.mapper.UsersPropertiesMapper;
import com.mrbt.lingmoney.model.AccountFlow;
import com.mrbt.lingmoney.model.AccountFlowExample;
import com.mrbt.lingmoney.model.ActivityRecord;
import com.mrbt.lingmoney.model.ActivityRecordExample;
import com.mrbt.lingmoney.model.HxAccount;
import com.mrbt.lingmoney.model.HxAccountExample;
import com.mrbt.lingmoney.model.HxCard;
import com.mrbt.lingmoney.model.HxCardExample;
import com.mrbt.lingmoney.model.PrefixBank;
import com.mrbt.lingmoney.model.PrefixBankExample;
import com.mrbt.lingmoney.model.SupportBank;
import com.mrbt.lingmoney.model.SupportBankExample;
import com.mrbt.lingmoney.model.UsersAccount;
import com.mrbt.lingmoney.model.UsersBank;
import com.mrbt.lingmoney.model.UsersBankExample;
import com.mrbt.lingmoney.model.UsersProperties;
import com.mrbt.lingmoney.model.UsersPropertiesExample;
import com.mrbt.lingmoney.service.web.MyAccountService;
import com.mrbt.lingmoney.utils.AppCons;
import com.mrbt.lingmoney.utils.DateUtils;
import com.mrbt.lingmoney.utils.PageInfo;
import com.mrbt.lingmoney.utils.ResultParame.ResultInfo;
import com.mrbt.lingmoney.utils.Validation;

/**
 * @author syb
 * @date 2017年5月10日 下午4:05:05
 * @version 1.0
 * @description
 **/
@Service
public class MyAccountServiceImpl implements MyAccountService {
	@Autowired
	private ActivityRecordMapper activityRecordMapper;
	@Autowired
	private UsersPropertiesMapper usersPropertiesMapper;
	@Autowired
	private UsersBankMapper usersBankMapper;
	@Autowired
	private SupportBankMapper supportBankMapper;
	@Autowired
	private UsersAccountMapper usersAccountMapper;
	@Autowired
	private AccountFlowMapper accountFlowMapper;
	@Autowired
	private GiftDetailMapper giftDetailMapper;
	@Autowired
	private PrefixBankMapper prefixBankMapper;
	@Autowired
	private HxAccountMapper hxAccountMapper;
	@Autowired
	private HxCardMapper hxCardMapper;

	@Override
	public PageInfo listLingbaoRecord(Integer pageNo, String uid, Integer pageSize) {
		ActivityRecordExample arExample = new ActivityRecordExample();
		arExample.createCriteria().andTypeEqualTo(0).andUIdEqualTo(uid);
		if (StringUtils.isEmpty(pageNo)) {
			pageNo = 1;
		}
		if (StringUtils.isEmpty(pageSize)) {
			pageSize = AppCons.DEFAULT_PAGE_SIZE;
		}
		PageInfo pi = new PageInfo(pageNo, pageSize);
		UsersAccount account = usersAccountMapper.selectByUid(uid);
		if (account != null) {
			pi.setObj(account.getCountLingbao());
		}
		arExample.setLimitStart(pi.getFrom());
		arExample.setLimitEnd(pi.getSize());
		arExample.setOrderByClause("use_date desc, id desc");
		List<ActivityRecord> list = activityRecordMapper.selectByExample(arExample);

		if (list != null && list.size() > 0) {
            pi.setResultInfo(ResultInfo.SUCCESS);
			pi.setNowpage(pageNo);
			pi.setRows(list);
			pi.setTotal(activityRecordMapper.countByExample(arExample));
		} else {
            pi.setResultInfo(ResultInfo.EMPTY_DATA);
		}

		return pi;
	}

	@Override
	public void packageBindBankCardInfo(ModelMap modelMap, String uid) {
		UsersBankExample ubExample = new UsersBankExample();
		ubExample.createCriteria().andUIdEqualTo(uid).andStatusEqualTo("1");
		ubExample.setOrderByClause("isdefault desc,bindtime desc");
		List<UsersBank> usersBank = usersBankMapper.selectByExample(ubExample);

		if (usersBank != null && usersBank.size() > 0) {
			List<Map<String, Object>> list = new ArrayList<Map<String, Object>>();
			for (UsersBank ub : usersBank) {
				ub.setTel(ub.getTel().substring(0, 3) + "****"
						+ ub.getTel().substring(ub.getTel().length() - 4, ub.getTel().length()));
				ub.setNumber(ub.getNumber().substring(ub.getNumber().length() - 4, ub.getNumber().length()));
				SupportBank sb = supportBankMapper.selectBankInfoByCode(ub.getBankcode());
				Map<String, Object> map = new HashMap<String, Object>();
				map.put("userBank", ub);
				map.put("supportBank", sb);
				list.add(map);
			}

			modelMap.addAttribute("list", list);
		} else {
			modelMap.addAttribute("list", null);
		}

		UsersProperties userProperties = usersPropertiesMapper.selectByuId(uid);
		if (userProperties != null && userProperties.getIdCard() != null && userProperties.getName() != null) {
			modelMap.addAttribute("idCard", userProperties.getIdCard().substring(0, 3) + "*********" + userProperties.getIdCard().substring(userProperties.getIdCard().length() - 3,
									userProperties.getIdCard().length()));
			modelMap.addAttribute("realname", "*"
							+ userProperties.getName().substring(userProperties.getName().length() - 2,
									userProperties.getName().length()));
		} else {
			modelMap.addAttribute("idCard", "");
			modelMap.addAttribute("realname", "");
		}

	}

	@Override
	public JSONObject queryStaticMonth(String dateTime, String uid) {
		Date begin = null;
		Date end = null;
		if (dateTime == null || "".equals(dateTime)) {
			dateTime = DateUtils.getDateStr(new Date(), DateUtils.sf);
		}
		String[] dateStr = dateTime.split("-");
		int syear = Integer.parseInt(dateStr[0]);
		int smonth = Integer.parseInt(dateStr[1]);
		begin = DateUtils.getFirstDayofMonth(syear, smonth - 1);
		end = DateUtils.getEndDayofMonth(syear, smonth - 1);

		UsersAccount usersAccount = usersAccountMapper.selectByUid(uid);
		Hashtable<String, List<Map<String, Object>>> map = getAccountFlow(usersAccount.getId(), begin, end);
		JSONObject jsonObject = new JSONObject();
		jsonObject.put("Data", map);
		return jsonObject;
	}

	/**
     * 查询账户流水(日历展示用，只查询状态为操作成功的记录1或者5)
     *
     * @Description
     * @param aId
     *            账户ID
     * @param beginDate
     *            开始时间
     * @param endTime
     *            结束时间
     * @return key:日 value:map
     * 以天作为key，value中存放展示记录的map信息：type 类型，0充值 1提现 2理财 3赎回、operateTimeStr 格式化后的日期字符串 yyyy-MM-dd HH:mm:ss
     * moneyFormat 格式化后的金额，#,##0.00
     */
	public Hashtable<String, List<Map<String, Object>>> getAccountFlow(int aId, Date beginDate, Date endTime) {
		Hashtable<String, List<Map<String, Object>>> table = new Hashtable<String, List<Map<String, Object>>>();
		AccountFlowExample example = new AccountFlowExample();
		AccountFlowExample.Criteria criteria = example.createCriteria();
		criteria.andAIdEqualTo(aId);
		if (endTime != null) {
			criteria.andOperateTimeLessThanOrEqualTo(endTime);
		}
		if (beginDate != null) {
			criteria.andOperateTimeGreaterThanOrEqualTo(beginDate);
		}
		List<Integer> statusList = new ArrayList<Integer>();
        statusList.add(AppCons.ACCOUNT_FLOW_FINISH); // 已完成，支付成功，标的运行中
        statusList.add(AppCons.ACCOUNT_FLOW_FROZEN); // 冻结中，支付成功，标的未运行
		criteria.andStatusIn(statusList);

		List<AccountFlow> result = accountFlowMapper.selectByExample(example);
		if (null != result && result.size() > 0) {
			DecimalFormat df = new DecimalFormat("#,##0.00");
			for (int i = 0; i < result.size(); i++) {
				Map<String, Object> data = new HashMap<String, Object>();

				AccountFlow accountFlow = result.get(i);
				data.put("type", accountFlow.getType());
				data.put("operateTimeStr", DateUtils.getDateStr(accountFlow.getOperateTime(), DateUtils.sft));
				data.put("moneyFormat", df.format(accountFlow.getMoney().doubleValue()));

				Date operateTime = accountFlow.getOperateTime();
				Calendar cal = Calendar.getInstance();
				cal.setTime(operateTime);
				String day = cal.get(Calendar.DAY_OF_MONTH) + "";
				if (table.get(day) != null) {
					List<Map<String, Object>> dayAccountFlowList = table.get(day);
					dayAccountFlowList.add(data);

				} else {
                    List<Map<String, Object>> dayAccountFlowList = new ArrayList<Map<String, Object>>();
                    dayAccountFlowList.add(data);
                    table.put(day, dayAccountFlowList);
				}
			}
		}

		return table;
	}

	@Override
	public PageInfo listGiftInfo(String uid, Integer pageNo) {
		if (StringUtils.isEmpty(pageNo)) {
			pageNo = 1;
		}
		PageInfo pi = new PageInfo(pageNo, AppCons.DEFAULT_PAGE_SIZE);

		Map<String, Object> params = new HashMap<String, Object>();
		params.put("start", pi.getFrom());
		params.put("number", pi.getSize());
		params.put("uid", uid);
		List<Map<String, Object>> list = giftDetailMapper.selectGiftDetailByUid(params);

		if (null != list && list.size() > 0) {
            pi.setResultInfo(ResultInfo.SUCCESS);
			pi.setRows(list);
			pi.setTotal(giftDetailMapper.selectGiftDetailByUidCount(uid));
			pi.setNowpage(pageNo);

		} else {
            pi.setResultInfo(ResultInfo.EMPTY_DATA);
		}

		return pi;
	}

	@Override
	public PageInfo listQuickPaymentBank() {
		PageInfo pi = new PageInfo();

		SupportBankExample example = new SupportBankExample();
		example.createCriteria().andBankTypeNotEqualTo(1);
		List<SupportBank> list = supportBankMapper.selectByExample(example);
		if (list != null && list.size() > 0) {
            pi.setResultInfo(ResultInfo.SUCCESS);
			pi.setObj(list);
		} else {
            pi.setResultInfo(ResultInfo.EMPTY_DATA);
		}

		return pi;
	}

	@Override
	public PageInfo isCardNumberBeBinded(String number) {
		PageInfo pi = new PageInfo();

		UsersBankExample example = new UsersBankExample();
		example.createCriteria().andNumberEqualTo(number).andStatusEqualTo("1");
		List<UsersBank> list = usersBankMapper.selectByExample(example);

		if (list == null || list.isEmpty()) {
            pi.setCode(ResultInfo.SUCCESS.getCode());
			pi.setMsg("验证通过");
		} else {
            pi.setResultInfo(ResultInfo.BANK_CARD_ALREADY_BIND);
		}

		return pi;
	}

	@Override
	public PageInfo queryBankInfoByTopSix(String number) {
		PageInfo pi = new PageInfo();
        pi.setResultInfo(ResultInfo.EMPTY_DATA);

		PrefixBankExample prefixBankExmp = new PrefixBankExample();
		prefixBankExmp.createCriteria().andPrefixNumberEqualTo(number);
		List<PrefixBank> list = prefixBankMapper.selectByExample(prefixBankExmp);
		if (null != list && list.size() > 0) {
			PrefixBank prefixBank = list.get(0);

			SupportBankExample supBankExample = new SupportBankExample();
			supBankExample.createCriteria().andBankShortEqualTo(prefixBank.getBankShort());
			List<SupportBank> sbList = supportBankMapper.selectByExample(supBankExample);
			if (null != sbList && sbList.size() > 0) {
                pi.setResultInfo(ResultInfo.SUCCESS);
				pi.setObj(sbList.get(0));
			}

		}

		return pi;
	}

	@Override
	public PageInfo testBindCardId(String uid, String idCard) {
		PageInfo pi = new PageInfo();

		if (StringUtils.isEmpty(uid)) {
            pi.setResultInfo(ResultInfo.LOGIN_OVERTIME);
			return pi;
		}

		if (StringUtils.isEmpty(idCard)) {
            pi.setCode(ResultInfo.PARAM_MISS.getCode());
			pi.setMsg("请先填写身份证号");
			return pi;
		}

		idCard = idCard.toUpperCase();

		UsersPropertiesExample example = new UsersPropertiesExample();
		example.createCriteria().andIdCardEqualTo(idCard);
		List<UsersProperties> list = usersPropertiesMapper.selectByExample(example);

		//如果身份证号已经存在，判断查询到的UID和用户UID是否一致，如果一致，继续绑定，如果不一致提示身份证号已被绑定
		if (null != list && list.size() > 0) {
            if (!list.get(0).getuId().equals(uid)) {
                pi.setResultInfo(ResultInfo.ID_CARD_ALREADY_BIND);
				return pi;
			}
		}

		if (!Validation.IdCardNo(idCard)) {
            pi.setResultInfo(ResultInfo.ID_CRAD_ERROR);
			return pi;
		}

        pi.setResultInfo(ResultInfo.SUCCESS);
		return pi;
	}

	@Override
	public PageInfo deleteBankCard(String uid, Integer id) {
		PageInfo pi = new PageInfo();

		if (StringUtils.isEmpty(id)) {
            pi.setResultInfo(ResultInfo.PARAM_MISS);
			return pi;
		}

		UsersBank bank = usersBankMapper.selectByPrimaryKey(id);
		if (bank != null && bank.getStatus().equals("1")) {
			if (bank.getuId().equals(uid)) {
				if (bank.getIsdefault().equals("0")) {
					UsersBank record = new UsersBank();
					record.setId(bank.getId());
					record.setStatus("0");
					int result = usersBankMapper.updateByPrimaryKeySelective(record);

					if (result > 0) {
                        pi.setResultInfo(ResultInfo.SUCCESS);
					} else {
                        pi.setCode(ResultInfo.DO_FAIL.getCode());
						pi.setMsg("操作失败,请刷新后重试");
					}

				} else {
                    pi.setCode(ResultInfo.JINGDONG_CRAD_ERROR.getCode());
					pi.setMsg("默认银行卡不可删除");
				}

			} else {
                pi.setCode(ResultInfo.EMPTY_DATA.getCode());
				pi.setMsg("用户信息不匹配");
			}

		} else {
            pi.setCode(ResultInfo.EMPTY_DATA.getCode());
			pi.setMsg("未查询到有效银行卡信息");
		}

		return pi;
	}

	@Override
	public PageInfo queryHxAccountBindCardInfo(String uId) {
		PageInfo pi = new PageInfo();

		HxAccountExample example = new HxAccountExample();
		example.createCriteria().andUIdEqualTo(uId);
		example.setOrderByClause("tied_date desc");
		List<HxAccount> accountList = hxAccountMapper.selectByExample(example);
		if (accountList != null && accountList.size() > 0) {
			HxAccount account = accountList.get(0);
			HxCardExample cardExmp = new HxCardExample();
			cardExmp.createCriteria().andAccIdEqualTo(account.getId());
			List<HxCard> cardList = hxCardMapper.selectByExample(cardExmp);

			if (cardList != null && cardList.size() > 0) {
				HxCard hxCard = cardList.get(0);
                pi.setResultInfo(ResultInfo.SUCCESS);
				pi.setObj(hxCard.getStatus());
			} else {
                pi.setCode(ResultInfo.EMPTY_DATA.getCode());
				pi.setMsg("未查询到绑卡激活信息");
			}

		} else {
            pi.setCode(ResultInfo.EMPTY_DATA.getCode());
			pi.setMsg("未查询到有效账户信息");
		}

		return pi;
	}

}
