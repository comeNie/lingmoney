package com.mrbt.lingmoney.service.product.impl;

import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.ui.ModelMap;

import com.mrbt.lingmoney.mapper.ActivityProductCustomerMapper;
import com.mrbt.lingmoney.mapper.ActivityPropertyMapper;
import com.mrbt.lingmoney.mapper.AlertPromptMapper;
import com.mrbt.lingmoney.mapper.GiftExchangeInfoNewMapper;
import com.mrbt.lingmoney.mapper.TradingMapper;
import com.mrbt.lingmoney.mapper.UsersPropertiesMapper;
import com.mrbt.lingmoney.model.ActivityProductCustomer;
import com.mrbt.lingmoney.model.ActivityProperty;
import com.mrbt.lingmoney.model.ActivityPropertyExample;
import com.mrbt.lingmoney.model.AlertPrompt;
import com.mrbt.lingmoney.model.AlertPromptExample;
import com.mrbt.lingmoney.model.GiftExchangeInfoNew;
import com.mrbt.lingmoney.model.GiftExchangeInfoNewExample;
import com.mrbt.lingmoney.model.GiftExchangeInfoVo1;
import com.mrbt.lingmoney.model.ProductCustomer;
import com.mrbt.lingmoney.model.TradingExample;
import com.mrbt.lingmoney.model.UsersProperties;
import com.mrbt.lingmoney.service.product.ActivityService;
import com.mrbt.lingmoney.utils.AppCons;
import com.mrbt.lingmoney.utils.DateUtils;
import com.mrbt.lingmoney.utils.PageInfo;
import com.mrbt.lingmoney.utils.ResultParame.ResultInfo;
import com.mrbt.lingmoney.utils.ResultParame.ResultNumber;

/**
 * 活动
 *
 * @author lihq
 * @date 2017年4月24日 上午9:46:14
 * @description
 * @version 1.0
 * @since 2017-04-12
 */
@Service
public class ActivityServiceImpl implements ActivityService {

	private static final Logger LOGGER = LogManager.getLogger(ActivityServiceImpl.class);

	@Autowired
	private ActivityProductCustomerMapper activityProductCustomerMapper;

	@Autowired
	private GiftExchangeInfoNewMapper giftExchangeInfoNewMapper;

	@Autowired
	private UsersPropertiesMapper usersPropertiesMapper;

	@Autowired
	private TradingMapper tradingMapper;

	@Autowired
	private AlertPromptMapper alertPromptMapper;
	
	@Autowired
	private ActivityPropertyMapper activityPropertyMapper;

	@Override
	public void selectActivityList(PageInfo pageInfo) {
		// 总条数
		Integer total = activityProductCustomerMapper.selectActivityCount(pageInfo);

		if (total > 0) {
			pageInfo.setCode(ResultInfo.SUCCESS.getCode());
			pageInfo.setMsg("查询成功");
			// 结果集
			List<ActivityProductCustomer> list = activityProductCustomerMapper.selectActivityList(pageInfo);
			if (list != null && list.size() > 0) {
				for (ActivityProductCustomer activityProductCustomer : list) {
					// 查询活动关联产品
					this.selectRelatedProduct(activityProductCustomer, false);

					// 查询活动产品参加人次
					int sellCount = this.findActivityTradingCount(activityProductCustomer);
					activityProductCustomer.setSellCount(sellCount);
					//查询标题图片
					this.selectActTitleImage(activityProductCustomer);
				}
			}
			pageInfo.setRows(list);
			pageInfo.setTotal(total);
		} else {
			pageInfo.setCode(ResultInfo.RETURN_DATA_EMPTY_DATA.getCode());
			pageInfo.setMsg("暂无数据");
		}
	}
	
	public void selectActTitleImage(ActivityProductCustomer activityProductCustomer){
		 if(activityProductCustomer.getId()!=null){
			 ActivityPropertyExample  example=new ActivityPropertyExample();
			 example.createCriteria().andActIdEqualTo(activityProductCustomer.getId().toString());
			 List<ActivityProperty> list=activityPropertyMapper.selectByExample(example);
			 if(list!=null && list.size()!=ResultNumber.ZERO.getNumber()){
				 activityProductCustomer.setActTitleImage(list.get(0).getActTitleImage());
			 }
		 }
	}

	@Override
	public ActivityProductCustomer selectActivityInfo(PageInfo pageInfo) {
		ActivityProductCustomer activityProductCustomer = activityProductCustomerMapper.selectActivityInfo(pageInfo);
		if (activityProductCustomer != null) {

			// 查询活动关联产品
			this.selectRelatedProduct(activityProductCustomer, true);

			// 早点8活动需要显示开始时间和结束时间
			if ("earlyeight".equals(activityProductCustomer.getActUrl())) {
				// 开始时间
				Date startDate = activityProductCustomer.getStartDate();
				// 结束时间
				Date endDate = activityProductCustomer.getEndDate();
				// 当前时间
				Date currentDate = new Date();
				// 距离活动开始时间
				long distanceStartTime = 0;
				// 距离活动结束时间
				long distanceEndTime = 0;
				// 如果活动未开始，开始时间大于当前时间
				if (startDate.compareTo(currentDate) == 1) {
					// 距离活动开始时间
					distanceStartTime = startDate.getTime() - currentDate.getTime();
				}
				activityProductCustomer.setDistanceStartTime(distanceStartTime);
				// 如果活动开始了，还未结束，结束时间大于当前时间，开始时间小于当前时间
				if (endDate.compareTo(currentDate) == 1 && currentDate.compareTo(startDate) == 1) {
					// 距离活动结束时间
					distanceEndTime = endDate.getTime() - currentDate.getTime();
				}
				activityProductCustomer.setDistanceEndTime(distanceEndTime);
				activityProductCustomer.setCurrentDate(currentDate.getTime());
			}
		}
		return activityProductCustomer;
	}

	@Override
	public String selectActivityDesc(Integer activityId, Integer productIndex) {
		productIndex = productIndex == null ? 1 : productIndex;
		String activityDesc = "act_pro_desc" + productIndex;
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("activityId", activityId);
		map.put("activityDesc", activityDesc);
		return activityProductCustomerMapper.selectActivityDesc(map);
	}

	@Override
	public Map<String, Object> activityRecommend(String uId, Integer judgeStatus) {
		// 保存结果
		Map<String, Object> result = new HashMap<String, Object>();
		try {
			// 随心取产品ID
			String pid = "391";
			// 查询拉新活动
			PageInfo pageInfo = new PageInfo();
			Map<String, Object> condition = new HashMap<String, Object>();
			condition.put("actUrl", "invitation"); // 活动关键字
			condition.put("status", 1); // 状态可用
			pageInfo.setCondition(condition);
			ActivityProductCustomer activityProductCustomer = activityProductCustomerMapper
					.selectActivityInfo(pageInfo);
			if (judgeStatus == 0) {
				if (activityProductCustomer == null) {
					result.put("recommendNewAvailable", 0);
					return result;
				} else {
					result.put("recommendNewAvailable", 1);
					return result;
				}
			}
			if (activityProductCustomer == null) {
				return result;
			}
			System.out.println("拉新活动：" + DateUtils.sft.format(activityProductCustomer.getStartDate()) + "--"
					+ DateUtils.sft.format(activityProductCustomer.getEndDate()));
			Map<String, Object> params = new HashMap<String, Object>();
			params.put("uid", uId);
			params.put("startDate", activityProductCustomer.getStartDate());

			// 如果是员工，从2017-05-27 00:00:00开始
			// TODO 视图无权限，需要发给运维创建，暂且禁用
			// if (usersPropertiesMapper.queryIsEmployeeUsers(uId) > 0) {
			// // 拉新活动第二季员工活动开始时间
			// String empStartDate = "2017-05-27 00:00:00";
			// params.put("startDate", new SimpleDateFormat("yyyy-MM-dd
			// HH:mm:ss").parse(empStartDate));
			// }
			params.put("endDate", activityProductCustomer.getEndDate());
			params.put("pid", pid);

			// 保存有效结果
			List<GiftExchangeInfoVo1> validList = activityProductCustomerMapper.queryRecommendersByuId(params);
			// 首投总额
			BigDecimal sumInvest = new BigDecimal(0);
			if (validList != null && validList.size() > 0) {
				for (GiftExchangeInfoVo1 giftExchangeInfoVo : validList) {
					sumInvest = sumInvest.add(giftExchangeInfoVo.getBuyMoney());
				}
			}
			result.put("sumInvest", sumInvest);
			// 有效用户信息列表
			result.put("validList", validList);
			// 有效总人数
			int personNum = validList.size();
			result.put("personNum", personNum);
			// 获得大奖次数
			final int groupTen = 10; // 十个为一组
			int prizeNum = personNum / groupTen;
			result.put("prizeNum", prizeNum);
			// 当前心个数
			int heartNum = personNum % groupTen;
			result.put("heartNum", heartNum);
			// 大奖对应的兑换记录列表 id,giftName,status
			List<Map<String, Object>> exchangeList = new ArrayList<Map<String, Object>>();
			// 人数达到十人即可领取大奖
			if (prizeNum >= 1) {
				// 查询用户兑换大奖情况
				GiftExchangeInfoNewExample gifExam = new GiftExchangeInfoNewExample();
				GiftExchangeInfoNewExample.Criteria cria = gifExam.createCriteria();
				cria.andReferralIdIn(usersPropertiesMapper.queryEmployeeUsersId(uId));
				cria.andActivityIdEqualTo(activityProductCustomer.getId());
				cria.andTypeEqualTo(1);

				// 判断大奖是否已插入库中
				long count = giftExchangeInfoNewMapper.countByExample(gifExam);
				if (count < prizeNum) {
					// 库中不存在或者库中大奖个数小于应得大奖个数,循环添加记录
					GiftExchangeInfoNew gei = null;
					for (; count < prizeNum; count++) {
						gei = new GiftExchangeInfoNew();
						gei.setActivityId(activityProductCustomer.getId());
						gei.setExchangeTime(new Date());
						gei.setNum(1);
						gei.setStatus(0);
						gei.setReferralId(uId);
						gei.setType(1);
						gei.setCategory(2);
						giftExchangeInfoNewMapper.insert(gei);
					}
					result.put("newPrize", -1); // 新获得大奖，并插入库中，弹框提示
				} else {
					result.put("newPrize", 0); // 不需弹框
				}

				// 返回大奖信息
				List<GiftExchangeInfoNew> listGifo = giftExchangeInfoNewMapper.selectByExample(gifExam);

				if (listGifo != null && listGifo.size() > 0) {
					// 大奖对应的兑换记录 id,giftName,status
					Map<String, Object> exchangeRecord = null;
					for (GiftExchangeInfoNew giftExchangeInfoNew : listGifo) {
						exchangeRecord = new HashMap<String, Object>();
						exchangeRecord.put("id", giftExchangeInfoNew.getId());
						exchangeRecord.put("giftName", giftExchangeInfoNew.getGiftName());
						exchangeRecord.put("status", giftExchangeInfoNew.getStatus());
						exchangeList.add(exchangeRecord);
					}
				}
			}
			result.put("exchangeList", exchangeList);
		} catch (Exception e) {
			result.put("sumInvest", null);
			result.put("userList", null);
			result.put("personNum", null);
			result.put("prizeNum", null);
			result.put("heartNum", null);
			result.put("newPrize", null);
			result.put("exchangeList", null);
			e.printStackTrace();
		}
		return result;
	}

	/**
	 * 查询活动产品参加人次
	 * 
	 * @param activityProductCustomer 活动产品
	 * @return 参加人次
	 */
	private int findActivityTradingCount(ActivityProductCustomer activityProductCustomer) {
		int count = 0;
		String pid = activityProductCustomer.getpId();
		if (StringUtils.isNotBlank(pid)) {
			String[] pids = pid.split(",");
			if (pids.length > 0) {
				count = activityProductCustomerMapper.selectActivityTradingCount(pids);
			}
		}
		return count;
	}

    /**
     * 查看活动产品状态，如果产品都已成立，则设置活动为结束
     * 
     * @param activityProductCustomer 产品活动
     * @param setRelatedProduct 是否设置关联产品
     */
	private void selectRelatedProduct(ActivityProductCustomer activityProductCustomer, Boolean setRelatedProduct) {
        // 查询活动产品
		String pid = activityProductCustomer.getpId();
		if (StringUtils.isNotBlank(pid)) {

			String[] pids = pid.split(",");

			List<ProductCustomer> productList = activityProductCustomerMapper.selectActivityProductList(pids);

			// 查看活动内页时设置关联产品，活动列表不需要
			if (setRelatedProduct) {
				activityProductCustomer.setProductList(productList);
			}

			// 是否全部活动产品已结束 false=未结束 true=全部结束
			Boolean isAllOver = true;
			for (ProductCustomer productCustomer : productList) {
				if (productCustomer.getStatus() == 1) {
					isAllOver = false;
				}
			}
			// 如果产品都已成立，则设置活动为结束
			if (isAllOver) {
				activityProductCustomer.setBuyState(2);
			}
			
			Date date = new Date();
			//判断产品活动区间  0表示活动中，1表示还未开始，2表示已结束
            if (date.getTime() > activityProductCustomer.getStartDate().getTime()) {
				activityProductCustomer.setBuyState(1);
            } else if (date.getTime() < activityProductCustomer.getEndDate().getTime()) {
				activityProductCustomer.setBuyState(2);
            } else {
				activityProductCustomer.setBuyState(0);
			}
		}
        int newFlag = 0; // 默认无new标志
        if (activityProductCustomer.getBuyState() == 0) { // 活动中
			int dateDiff = DateUtils.dateDiff(activityProductCustomer.getStartDate(), new Date());
            if (dateDiff <= 3) { // 开始时间距离当前时间小于等于3天
				newFlag = 1;
			}
		}
		activityProductCustomer.setNewFlag(newFlag);

		String timeInterval = "长期有效";
		Date startDate = activityProductCustomer.getStartDate();
		Date endDate = activityProductCustomer.getEndDate();
		String actUrl = activityProductCustomer.getActUrl();
        if ("earlyeight".equals(actUrl)) { // 早点8
			timeInterval = new SimpleDateFormat("yyyy.MM.dd HH:mm").format(startDate) + "-"
					+ new SimpleDateFormat("HH:mm").format(endDate);
		} else {
			timeInterval = new SimpleDateFormat("yyyy.MM.dd").format(startDate) + "-"
					+ new SimpleDateFormat("yyyy.MM.dd").format(endDate);
		}
		activityProductCustomer.setTimeInterval(timeInterval);

	}

	@Override
	public boolean activityShow(String activityKey, Integer isLogin, String uId, Integer clientType,
			ModelMap modelMap) {
		// 查询条件
		Map<String, Object> condition = new HashMap<String, Object>();
        condition.put("activityKey", activityKey); // 活动关键字
        condition.put("clientType", clientType); // 客户端类型 0pc 1app

		ActivityProductCustomer activityProductCustomer = activityProductCustomerMapper.queryActivityInfoH5(condition);

		if (activityProductCustomer != null) {
			LOGGER.info("通过banner进入活动页面，查询活动成功：" + activityProductCustomer.getActName());

            if (activityProductCustomer.getPreheat() == 0) { // 不可预热，活动状态不为进行中
				if (activityProductCustomer.getBuyState() != 0) {
					LOGGER.info("通过banner进入活动页面，该活动不可预热，活动状态不为进行中");
					return false;
				}
			}

			String projectInfo = null;
            if (clientType == 0) { // pc
				projectInfo = activityProductCustomer.getActDesc();
            } else if (clientType == 1) { // app
				projectInfo = activityProductCustomer.getActDescMobile();
			}
			modelMap.addAttribute("actUrl", activityKey);
			modelMap.addAttribute("projectInfo", projectInfo);

			// 判断有无登录，如果登录，则根据用户ID查出用户推荐码，否则，显示立即登录
            if (isLogin == 0) { // 已登录
				UsersProperties usersProperties = usersPropertiesMapper.selectByuId(uId);
                if (usersProperties == null) { // 如果没查到用户信息
					isLogin = 1;
				} else {
					modelMap.addAttribute("referralCode", usersProperties.getReferralCode());
				}
			}
			modelMap.addAttribute("isLogin", isLogin);

			return true;
		} else {

			return false;
		}

	}

	@Override
	public PageInfo selectActivityNovice(String uId, String needLogin, Integer priority, Integer type) {
		PageInfo pageInfo = new PageInfo();

		// 用户理财状态 默认新手标0 推荐产品1
		Integer userState = 0;
		// 用户需要登录
		if ("Y".equals(needLogin)) {
            if (!StringUtils.isEmpty(uId)) { // uId不为空
				TradingExample tradingExample = new TradingExample();
                tradingExample
                        .createCriteria()
                        .andUIdEqualTo(uId)
                        .andStatusIn(
                                Arrays.asList(AppCons.BUY_OK, AppCons.SELL_STATUS, AppCons.SELL_OK, AppCons.BUY_FROKEN,
                                        AppCons.SELL_WAITING));
				long buyCount = tradingMapper.countByExample(tradingExample);
                if (buyCount > 0) { // 购买过产品
					userState = 1;
				}
			}
		}
		/**
		 * 未登录用户：显示赠送50元话费标识，点击后跳转至对应的活动页面； 登录未投资用户：显示赠送50元话费标识，点击后跳转至对应的活动页面；
		 * 登录并投资用户：则不赠送；
		 */
		if (userState == 0) {
			LOGGER.info("查询新手特供活动用户未登录，或者未投资，查询新手特供活动");
			PageInfo info = new PageInfo();
			// 查询条件
			Map<String, Object> condition = new HashMap<String, Object>();
            condition.put("status", 1); // 状态可用
            condition.put("priority", priority); // 新手特供活动
			info.setCondition(condition);

			ActivityProductCustomer activityProductCustomer = activityProductCustomerMapper.selectActivityInfo(info);
			if (activityProductCustomer != null) {
				LOGGER.info("查询新手特供活动成功：" + activityProductCustomer.getActName());
				Map<String, Object> resMap = new HashMap<String, Object>();
				switch (priority) {
                case 1: // 首页
					resMap.put("activityPic", activityProductCustomer.getActivityPic());
					break;
                case 2: // 列表
					resMap.put("activityWord", activityProductCustomer.getActivityWord());
					break;
                case 3: // 详情
					resMap.put("activityGift", activityProductCustomer.getActivityGift());
					resMap.put("activityWord", activityProductCustomer.getActivityWord());
					break;
				default:
					break;
				}
				// 如果新手活动关联弹框提示表，则取关联提示信息，否则取h5url
				if (StringUtils.isNotBlank(activityProductCustomer.getApId())) {
					LOGGER.info("新手活动关联弹框提示表，urlType=0");
                    resMap.put("urlType", 0); // 链接跳转类型 0 app

					AlertPromptExample example = new AlertPromptExample();
					example.createCriteria().andStatusEqualTo(1).andIdEqualTo(activityProductCustomer.getApId());
					List<AlertPrompt> list = alertPromptMapper.selectByExample(example);
					if (list != null && list.size() > 0) {
						// 查询状态为可用的弹框提示bean
						AlertPrompt alertPrompt = list.get(0);
						LOGGER.info("当前弹框提示数据：" + alertPrompt.toString());
                        final int iosType = 0, androidType = 1;
                        if (type == iosType) { // ios
							// iOS封装对象
							Map<String, Object> iOSObj = new HashMap<String, Object>();
							// iOS调用类名
							iOSObj.put("class", alertPrompt.getClassIos());
							// iOS属性，put(K,V) K->propertyKeyIos按英文逗号分隔数组遍历
							// V->propertyValueIos按英文逗号分隔数组遍历
							Map<String, Object> iOSProperty = new HashMap<String, Object>();
							String propertyKeyIos = alertPrompt.getPropertyKeyIos();
							String propertyValueIos = alertPrompt.getPropertyValueIos();
                            String[] pki = propertyKeyIos.split(",");
                            String[] pvi = propertyValueIos.split(",");
							for (int i = 0; i < pki.length; i++) {
								iOSProperty.put(pki[i], pvi[i]);
							}
							// iOS属性封装
							iOSObj.put("property", iOSProperty);
							// iOS封装对象
							resMap.put("iOSObj", iOSObj);
                        } else if (type == androidType) { // android
							// 安卓跳转类型。0，activity 1，fragment 2，webview
							resMap.put("androidJumpType", alertPrompt.getAndroidJumpType());
							// android封装对象
							Map<String, Object> androidObj = new HashMap<String, Object>();
							// android调用类名
							androidObj.put("classAndroid", alertPrompt.getClassAndroid());
							// android属性，put(K,V)
							// K->propertyKeyIos按英文逗号分隔数组遍历
							// V->propertyValueIos按英文逗号分隔数组遍历
							Map<String, Object> androidProperty = new HashMap<String, Object>();
							String propertyKeyAndroid = alertPrompt.getPropertyKeyAndroid();
							String propertyValueAndroid = alertPrompt.getPropertyValueAndroid();
                            String[] pka = propertyKeyAndroid.split(",");
                            String[] pva = propertyValueAndroid.split(",");
							for (int i = 0; i < pka.length; i++) {
								androidProperty.put(pka[i], pva[i]);
							}
							// android属性封装
							androidObj.put("property", androidProperty);
							// android封装对象
							resMap.put("androidObj", androidObj);
						}
					}

				} else {
					LOGGER.info("新手活动跳转链接为h5，urlType=1");
                    resMap.put("urlType", 1); // 链接跳转类型 1 h5
					resMap.put("linkUrlApp", activityProductCustomer.getLinkUrlApp());
				}
				pageInfo.setObj(resMap);
                pageInfo.setCode(ResultInfo.SUCCESS.getCode());
				return pageInfo;
			}
		}
        pageInfo.setCode(ResultInfo.RETURN_EMPTY_DATA.getCode());
		pageInfo.setMsg("暂无数据");
		return pageInfo;
	}
}