package com.mrbt.lingmoney.service.redPacket.impl;

import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import org.eclipse.jetty.util.ajax.JSON;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import com.mrbt.lingmoney.commons.cache.RedisDao;
import com.mrbt.lingmoney.mapper.AccountFlowMapper;
import com.mrbt.lingmoney.mapper.AlertPromptMapper;
import com.mrbt.lingmoney.mapper.HxRedPacketMapper;
import com.mrbt.lingmoney.mapper.UsersPropertiesMapper;
import com.mrbt.lingmoney.mapper.UsersRedPacketMapper;
import com.mrbt.lingmoney.model.AlertPrompt;
import com.mrbt.lingmoney.model.AlertPromptExample;
import com.mrbt.lingmoney.model.HxRedPacket;
import com.mrbt.lingmoney.model.UsersProperties;
import com.mrbt.lingmoney.model.UsersRedPacket;
import com.mrbt.lingmoney.service.info.AppMsgPushService;
import com.mrbt.lingmoney.service.info.impl.AlertPromptServiceImpl;
import com.mrbt.lingmoney.service.redPacket.RedPacketService;
import com.mrbt.lingmoney.utils.AppCons;
import com.mrbt.lingmoney.utils.DateUtils;
import com.mrbt.lingmoney.utils.PageInfo;
import com.mrbt.lingmoney.utils.ResultParame.ResultInfo;
import com.mrbt.lingmoney.utils.ResultParame.ResultNumber;
import com.mrbt.lingmoney.youmengpush.android.AndroidListcast;
import com.mrbt.lingmoney.youmengpush.android.AndroidNotification.DisplayType;
import com.mrbt.lingmoney.youmengpush.ios.IOSListcast;

/**
 * 红包/加息劵服务类
 *
 */
@Service
public class RedPacketServiceImpl implements RedPacketService {

	private static final Logger LOGGER = LogManager.getLogger(AlertPromptServiceImpl.class);
	@Autowired
	private HxRedPacketMapper hxRedPacketMapper;
	@Autowired
	private UsersRedPacketMapper usersRedPacketMapper;
	@Autowired
	private AlertPromptMapper alertPromptMapper;
	@Autowired
    private RedisDao redisDao;
	@Autowired
	private AccountFlowMapper accountFlowMapper;
    @Autowired
    private AppMsgPushService appMsgPushService;
    @Autowired
    private UsersPropertiesMapper usersPropertiesMapper;

	@Override
	public void rewardRedPackage(String userId, Integer actType, Double amount) {
		PageInfo pageInfo = findUserIsGetRedPacket(userId, actType, amount);
        //pageInfo.getCode() == ResultInfo.SUCCESS.getCode()
        if (true) {
			Map<String, Object> map = new HashMap<>();
			map.put("actType", actType);
			map.put("amount", amount);
			List<HxRedPacket> redPacketList = hxRedPacketMapper.selectRedPacketByParams(map);

			// 奖励用户
			if (!redPacketList.isEmpty()) {
                //时间冲突判断：如果一次赠送两个红包/加息券，到期时间不同，不提示到期天数。
                boolean rateDayConflict = false, redPacketConflict = false;
                int countRate = 0, countRedPacket = 0, rateDay = 0, redPacketDay = 0;
				for (HxRedPacket redPacket : redPacketList) {
					saveUserRedPacket(userId, redPacket);
                    if (redPacket.getHrpType() == 1) {
                        Date validDate = null;
                        if (redPacket.getDelayed() != null) {
                            validDate = DateUtils.addDay2(new Date(), redPacket.getDelayed() + 1);
                        } else {
                            validDate = redPacket.getValidityTime();
                        }
                        int vDay = DateUtils.dateDiff(new Date(), validDate);
                        if (vDay != rateDay && rateDay != 0) {
                            rateDayConflict = true;
                        } else {
                            rateDay = vDay;
                        }
                        countRate++;

                    } else if (redPacket.getHrpType() == 2) {
                        Date validDate = null;
                        if (redPacket.getDelayed() != null) {
                            validDate = DateUtils.addDay2(new Date(), redPacket.getDelayed() + 1);
                        } else {
                            validDate = redPacket.getValidityTime();
                        }
                        int vDay = DateUtils.dateDiff(new Date(), validDate);
                        if (vDay != redPacketDay && redPacketDay != 0) {
                            redPacketConflict = true;
                        } else {
                            redPacketDay = vDay;
                        }
                        countRedPacket++;
                    }
				}

                /*红包消息推送
                1、  红包/加息券发放，消息提示内容如下：
                1）  【变量1】个红包已经送至您的账户，有效期【变量】天，超值福利不容错过。
                2）  【变量1】张加息券已经送至您的账户，有效期【变量】天，超值福利不容错过。*/
                try {
                    UsersProperties up = usersPropertiesMapper.selectByuId(userId);
                    if (up != null && up.getDeviceType() != null && up.getYoumengToken() != null) {
                        String content = "";
                        if (countRate > 0) {
                            if (rateDayConflict) {
                                content = countRate + "张加息券已经送至您的账户，超值福利不容错过。";
                            } else {
                                content = countRate + "张加息券已经送至您的账户，有效期" + rateDay + "天，超值福利不容错过。";
                            }

                        } else if (countRedPacket > 0) {
                            if (redPacketConflict) {
                                content = countRedPacket + "个红包已经送至您的账户，超值福利不容错过。";
                            } else {
                                content = countRedPacket + "个红包已经送至您的账户，有效期" + redPacketDay + "天，超值福利不容错过。";
                            }
                        }

                        distinguishPushDevice(up.getDeviceType(), 1, content, up.getYoumengToken());
                    }

                } catch (Exception e) {
                    e.printStackTrace();
                }
			}
		}

	}

    /**
     * 区分设备，推送消息
     * 
     * @author yiban
     * @date 2018年3月23日 上午9:52:46
     * @version 1.0
     * @param deviceType 设备类型 1android 2 ios
     * @param giftType 1 加息券 2红包
     * @param content 消息内容
     * @param token 设备token
     *
     */
	private void distinguishPushDevice(int deviceType, int giftType, String content, String token) {
        try {
            com.alibaba.fastjson.JSONObject customjson = new com.alibaba.fastjson.JSONObject();
            if (deviceType == 1) {
                // android
                AndroidListcast listcast = appMsgPushService.getAndroidListcast();
                listcast.setTitle("领钱儿");
                listcast.setTicker("领钱儿");
                listcast.setText(content);
                customjson
                        .put("imgUrl",
                                "https://ss0.bdstatic.com/94oJfD_bAAcT8t7mm9GUKT-xh_/timg?image&quality=100&size=b4000_4000&sec=1521774965&di=cbba08a55fdbbac63df17a69148dad38&src=http://img.zcool.cn/community/0175ac57c83b160000018c1b520d0f.jpg");
                customjson
                        .put("androidObj",
                                "{\"pushClassName\":\"mrbt.lingqian.main.mine.Coupon.CouponActivity\",\"androidJumpType\":\"1\",\"pushClassPropertys\":{\"status\":0,\"money\":\"1\",\"couponId\":0,\"code\":\"0\"}}");
                listcast.goCustomAfterOpen(customjson);
                listcast.setDisplayType(DisplayType.NOTIFICATION);
                listcast.setProductionMode();
                listcast.setDeviceToken(token);
                appMsgPushService.pushMsg(listcast);

            } else if (deviceType == 2) {
                // ios
                IOSListcast listcast = appMsgPushService.getIOSListcast();
                JSONObject json = new JSONObject();
                json.put("title", "领钱儿");
                json.put("body", content);
                customjson.put("pushClassName", "ConcessionsListVC"); //点击图片要前往Class
                customjson.put("pushClassPropertys", new com.alibaba.fastjson.JSONObject()); // 点击图片要前往Class传递的参数
                listcast.setCustomizedField(
                        "imgUrl",
                        "https://ss0.bdstatic.com/94oJfD_bAAcT8t7mm9GUKT-xh_/timg?image&quality=100&size=b4000_4000&sec=1521774965&di=cbba08a55fdbbac63df17a69148dad38&src=http://img.zcool.cn/community/0175ac57c83b160000018c1b520d0f.jpg");
                listcast.setCustomizedField("iosObj", customjson.toJSONString());
                listcast.setPredefinedKeyValue("alert", json);
                listcast.setProductionMode();
                listcast.setDeviceToken(token);
                appMsgPushService.pushMsg(listcast);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
	}
	
	/**
	 * 奖励用户红包
	 * 
	 * @param userId 用户id
	 * @param redPacket 红包信息
	 */
	private void saveUserRedPacket(String userId, HxRedPacket redPacket) {
		UsersRedPacket usersRedPacket = new UsersRedPacket();
		usersRedPacket.setuId(userId);
		usersRedPacket.setRpId(redPacket.getId());
		usersRedPacket.setStatus(0);
		usersRedPacket.setCreateTime(new Date());
		usersRedPacket.setIsCheck(0);
		if (redPacket.getDelayed() != null) {
			usersRedPacket.setValidityTime(DateUtils.addDay2(new Date(), redPacket.getDelayed() + 1));
		} else {
			usersRedPacket.setValidityTime(redPacket.getValidityTime());
		}
		usersRedPacketMapper.insert(usersRedPacket);
	}

	/**
	 * 展示注册完成后赠送优惠券内容提示
	 * 
	 * @author suyibo 2017年10月27日
	 * @return pageinfo
	 */
	@Override
	public PageInfo getParamsInfo() {
		PageInfo pageInfo = new PageInfo();
		// 注册弹框类型
		AlertPromptExample example = new AlertPromptExample();
		example.createCriteria().andStatusEqualTo(ResultNumber.ONE.getNumber()).andAlertTypeEqualTo(ResultNumber.THREE.getNumber()).andPriorityNotEqualTo(ResultNumber.ZERO.getNumber());
		example.setOrderByClause("priority");
		List<AlertPrompt> list = alertPromptMapper.selectByExample(example);
		if (list != null && list.size() > 0) {
			// 查询状态为可用的弹框提示bean
			AlertPrompt alertPrompt = list.get(0);
			LOGGER.info("当前首页弹框提示数据：" + alertPrompt.toString());
			// 封装到map返回
			Map<String, Object> resMap = new HashMap<String, Object>();
			// 大图
			resMap.put("bigImg", alertPrompt.getBigImg());
			// 按钮图片
			resMap.put("buttonImg", alertPrompt.getButtonImg());
			// 二图间距
			resMap.put("distance", alertPrompt.getDistance() == null ? 0 : alertPrompt.getDistance());
			// 弹框类型。0开户 1激活 2普通3注册
			resMap.put("alertType", alertPrompt.getAlertType());
			// 是否显示，默认1显示，0不显示
			resMap.put("isDisplay", alertPrompt.getStatus());

            pageInfo.setCode(ResultInfo.SUCCESS.getCode());
			pageInfo.setObj(resMap);
		} else {
			pageInfo.setCode(ResultInfo.RETURN_EMPTY_DATA.getCode());
			pageInfo.setMsg("暂无数据");
		}
		return pageInfo;
	}

	@Override
	public PageInfo getRegistRedPacketInfoList(String token) {
		PageInfo pageInfo = new PageInfo();

        if (StringUtils.isEmpty(token) || !redisDao.hasKey(AppCons.TOKEN_VERIFY + token)) {
			pageInfo.setCode(ResultInfo.PARAM_MISS.getCode());
			pageInfo.setMsg("参数错误");
			return pageInfo;
		}

        String userId = (String) redisDao.get(AppCons.TOKEN_VERIFY + token);
		// 获取所有符合本次活动红包集合
		Map<String, Object> params = new HashMap<>();
		params.put("userId", userId);
		List<Map<String, Object>> redPacketList = hxRedPacketMapper.getHxRedPacketList(params);
		if (!redPacketList.isEmpty()) {
			JSONArray jsonArray = new JSONArray();
			for (Map<String, Object> redPacket : redPacketList) {
				Map<String, Object> map = packageRedPacketViewFieldToMap(redPacket);
				jsonArray.add(map);

			}
			pageInfo.setRows(jsonArray);
			pageInfo.setCode(ResultInfo.SUCCESS.getCode());
		}

		return pageInfo;
	}

	/**
	 * 包装加息券页面展示数据
	 * 
	 * @param redPacket 红包信息map
	 * @return 展示数据map
	 */
	private Map<String, Object> packageRedPacketViewFieldToMap(Map<String, Object> redPacket) {
		// 页面展示数据
		Map<String, Object> resultMap = new HashMap<String, Object>();
		// ID
		resultMap.put("id", redPacket.get("urpId"));
		// 名称
		resultMap.put("hrName", (int) redPacket.get("hrp_type") == 1 ? "加息券" : "红包");
		// 使用加息券的金额下限
		resultMap.put("useLimit",
				redPacket.get("a_invest_pro_amount") != null ? redPacket.get("a_invest_pro_amount") : "");
		// 金额/加息率
		if ((int) redPacket.get("hrp_type") == 1) {
			// 加息券，格式化展示数据
			Double rate = (Double) redPacket.get("hrp_number") * 100;
			DecimalFormat decimalFormat = new DecimalFormat("0.0");
			resultMap.put("amount", decimalFormat.format(rate));
		} else {
			resultMap.put("amount", redPacket.get("hrp_number"));
		}
		// 活动备注 格式为json数组
		if (redPacket.get("hrp_remarks") != null) {
			resultMap.put("activityRemark", JSON.parse((String) redPacket.get("hrp_remarks")));
		} else {
			resultMap.put("activityRemark", "");
		}
		// 获取方式
		if (redPacket.get("activity_type") != null && (int) redPacket.get("activity_type") == 0) {
			resultMap.put("obtianWay", "手动");
		} else if (redPacket.get("activity_type") != null && (int) redPacket.get("activity_type") == 1) {
			resultMap.put("obtianWay", "注册后");
		} else if (redPacket.get("activity_type") != null && (int) redPacket.get("activity_type") == 2) {
			resultMap.put("obtianWay", "开通E账户后");
		} else if (redPacket.get("activity_type") != null && (int) redPacket.get("activity_type") == 3) {
			resultMap.put("obtianWay", "激活E账户后");
		} else if (redPacket.get("activity_type") != null && (int) redPacket.get("activity_type") == 4) {
			resultMap.put("obtianWay", "首次理财后");
		} else if (redPacket.get("activity_type") != null && (int) redPacket.get("activity_type") == 4) {
			resultMap.put("obtianWay", "理财后");
		}
		// 距离过期天数
		resultMap.put("dayOfOvertime", DateUtils.dateDiff(new Date(),
				(Date) (redPacket.get("validity_time") != null ? redPacket.get("validity_time") : "")));
		// 到期日期
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		resultMap.put("overtimeDate",
				sdf.format(redPacket.get("validity_time") != null ? redPacket.get("validity_time") : ""));

		return resultMap;
	}

	/**
	 * (未查看)优惠券-》已查看
	 * 
	 * @author suyibo 2017年10月30日
	 * @param token token
	 * @return pageinfo
	 */
	@Override
	public PageInfo getCheckRedPacketList(String token) {
		PageInfo pageInfo = new PageInfo();
        if (StringUtils.isEmpty(token) || !redisDao.hasKey(AppCons.TOKEN_VERIFY + token)) {
            pageInfo.setCode(ResultInfo.PARAM_MISS.getCode());
            pageInfo.setMsg("参数错误");
            return pageInfo;
        }

        String userId = (String) redisDao.get(AppCons.TOKEN_VERIFY + token);

		// 获取用户所有符合类型的优惠券集合
		Map<String, Object> map = new HashMap<>();
		map.put("userId", userId);
		List<UsersRedPacket> usersRedPacketList = usersRedPacketMapper.getCheckRedPacketList(map);
		if (!usersRedPacketList.isEmpty()) {
			for (UsersRedPacket usersRedPacket : usersRedPacketList) {
				usersRedPacket.setIsCheck(1);
				usersRedPacketMapper.updateByPrimaryKey(usersRedPacket);
			}
            pageInfo.setCode(ResultInfo.SUCCESS.getCode());
		}

		return pageInfo;
	}

	/**
	 * 查询用户是否领取过，未领取过才能领取，不能重复领取
	 * 
	 * @param userId 用户id
	 * @param actType 活动类型
	 * @param amount 金额
	 * @return pageinfo
	 */
	public PageInfo findUserIsGetRedPacket(String userId, Integer actType, Double amount) {
		PageInfo pageInfo = new PageInfo();
		// 查询用户是否已经领取过活动优惠券
		Map<String, Object> map = new HashMap<>();
		map.put("userId", userId);
		map.put("actType", actType);
		map.put("amount", amount);
		List<String> getRedPacketIdList = usersRedPacketMapper.findUserIsGetRedPacket(map);
		// 如果actType=4（首次理财），判断是否为首次理财
		if (actType == 4) {
			// 查询用户是否有理财记录
			Integer count = accountFlowMapper.selectAccountFlowOfRechargeByUserId(map);
			if (getRedPacketIdList.isEmpty() && count == 0) {
				pageInfo.setCode(ResultInfo.SUCCESS.getCode());
			}
		} else {
			if (getRedPacketIdList.isEmpty()) {
				pageInfo.setCode(ResultInfo.SUCCESS.getCode());
			}
		}
		return pageInfo;
	}

}
