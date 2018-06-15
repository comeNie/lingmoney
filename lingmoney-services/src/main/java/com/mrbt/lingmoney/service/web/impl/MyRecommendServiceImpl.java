package com.mrbt.lingmoney.service.web.impl;

import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.imageio.ImageIO;
import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.ui.ModelMap;

import com.mrbt.lingmoney.mapper.ActivityProductMapper;
import com.mrbt.lingmoney.mapper.GiftExchangeInfoMapper;
import com.mrbt.lingmoney.mapper.UserUnionInfoMapper;
import com.mrbt.lingmoney.mapper.UsersPropertiesMapper;
import com.mrbt.lingmoney.model.ActivityProduct;
import com.mrbt.lingmoney.model.ActivityProductExample;
import com.mrbt.lingmoney.model.GiftExchangeInfo;
import com.mrbt.lingmoney.model.GiftExchangeInfoExample;
import com.mrbt.lingmoney.model.UserUnionInfo;
import com.mrbt.lingmoney.model.UsersProperties;
import com.mrbt.lingmoney.service.product.ActivityService;
import com.mrbt.lingmoney.service.web.MyRecommendService;
import com.mrbt.lingmoney.utils.AppCons;
import com.mrbt.lingmoney.utils.FtpUtils;
import com.mrbt.lingmoney.utils.QRCodeUtil;

/**
 * @author syb
 * @date 2017年5月11日 上午11:37:33
 * @version 1.0
 * @description
 **/
@Service
public class MyRecommendServiceImpl implements MyRecommendService {
	@Autowired
	private UsersPropertiesMapper usersPropertiesMapper;
	@Autowired
	private FtpUtils ftpUtils;
	@Autowired
	private UserUnionInfoMapper userUnionInfoMapper;
	@Autowired
	private ActivityProductMapper activityProductMapper;
	@Autowired
	private GiftExchangeInfoMapper giftExchangeInfoMapper;
	@Autowired
	private ActivityService activityService;

	@Override
	public UserUnionInfo packageRecommednInfo(HttpServletRequest request, ModelMap modelMap, String uid) {
		UserUnionInfo userInfo = null;
		// 查询我推荐的人
		List<Map<String, Object>> recomUserList = usersPropertiesMapper.queryRecomUsersByUid(uid);
		modelMap.addAttribute("UsersPropertiesList", recomUserList);
		// 查询谁推荐的我
		Map<String, Object> recomder = usersPropertiesMapper.queryMyrecommender(uid);
		if (recomder != null) {
			modelMap.addAttribute("UsersProperties", recomder);
		} else {
			modelMap.addAttribute("UsersProperties", null);
		}
		// 是否存在二维码，不存在则添加
		UsersProperties up = usersPropertiesMapper.selectByuId(uid);
		try {
			if (up.getCodePath() == null || up.getCodePath().trim().equals("")) {
				String path = request.getSession().getServletContext().getRealPath("");
				String text = AppCons.REFERRAL_URL + "?referralTel=" + up.getReferralCode();
				String format = "gif";
                String fileName = "referralCode" + uid + ".gif";
				BufferedImage buffer = QRCodeUtil.encodeBufferedImage(text, path + "/resource/images/lingqian.gif",
						false);
				ByteArrayOutputStream os = new ByteArrayOutputStream();
				ImageIO.write(buffer, format, os);
				InputStream is = new ByteArrayInputStream(os.toByteArray());
                ftpUtils.upload(is, AppCons.USER_PICTURE_DIR + "/" + uid, fileName);
                String basePath = ftpUtils.getUrl() + "TUPAN" + "/" + uid + "/" + fileName;
				// 保存用户属性信息
				UsersProperties usersProperties = new UsersProperties();
                usersProperties.setCodePath(basePath);
				usersProperties.setId(up.getId());
				usersPropertiesMapper.updateByPrimaryKeySelective(usersProperties);
				// 刷新session信息
				userInfo = userUnionInfoMapper.selectByUid(uid);
			}
		} catch (Exception e) {
			e.printStackTrace();
			throw new RuntimeException();
		}
		// 查询拉新活动第二季信息
		Map<String, Object> mapNew = activityService.activityRecommend(uid, 1);
        if (mapNew == null || mapNew.isEmpty()) {// 拉新活动第二季是否可用0不可用
			modelMap.addAttribute("recommendNewAvailable", "0");
		} else {
            modelMap.addAttribute("recommendNewAvailable", "1"); // 拉新活动第二季是否可用1可用
            modelMap.addAttribute("validList", mapNew.get("validList")); // 拉新活动满足条件用户信息 list<GiftExchangeInfoVo>
            modelMap.addAttribute("personNum", mapNew.get("personNum")); // 有效总人数
            modelMap.addAttribute("sumInvest", mapNew.get("sumInvest")); // 有效投资总金额
            modelMap.addAttribute("prizeNum", mapNew.get("prizeNum")); // 获得大奖次数 
            modelMap.addAttribute("heartNum", mapNew.get("heartNum")); // 当前心个数
            modelMap.addAttribute("newPrize", mapNew.get("newPrize")); // 是否新获得大奖 -1是，需要弹框 0不是，不需要弹框
            modelMap.addAttribute("exchangeList", mapNew.get("exchangeList")); // 大奖对应的兑换记录 id,giftName,status
		}
		return userInfo;
	}

	/**
	 * 查询拉新活动信息
	 *
	 * @param uid 用户id
	 * @return 拉新活动信息map
	 */
	public Map<String, Object> queryGetNewActivityInfo(String uid) {
		// 保存结果
		Map<String, Object> result = new HashMap<String, Object>();
		try {
			// 随心取产品ID
			String pid = "391";
			// 查询拉新活动
			ActivityProductExample apExample = new ActivityProductExample();
			apExample.createCriteria().andStatusEqualTo(1).andPriorityEqualTo(1).andActUrlEqualTo("recommend");
			apExample.setOrderByClause("create_time desc");
			List<ActivityProduct> aplist = activityProductMapper.selectByExample(apExample);
			ActivityProduct ap = null;
			if (null != aplist && aplist.size() > 0) {
				ap = aplist.get(0);
			} else {
				return null;
			}
			System.out.println("拉新活动：" + ap.getStartDate() + "--" + ap.getEndDate());
			Map<String, Object> params = new HashMap<String, Object>();
			params.put("uid", uid);
			params.put("startDate", ap.getStartDate());
			params.put("endDate", ap.getEndDate());
			params.put("pid", pid);

			List<Map<String, Object>> list = usersPropertiesMapper.queryGetNewInfoByUid(params);
			// 总投资金额
			BigDecimal sumMoney = new BigDecimal(0);
			int count = 1; // 计数
			int prizeGetType = 0; // 达到大奖类型
			// 保存有效结果
			List<Map<String, Object>> validInfo = new ArrayList<Map<String, Object>>();
			for (Map<String, Object> info : list) {
				// map 字段buyMoney 该用户购买总额, uId 用户ID, nickName 用户昵称（没有取登录账号）,
				// referralCode （推荐码）
				// 购买金额
				BigDecimal buyMoney = (BigDecimal) info.get("buyMoney");
				// 如果购买时间在活动时间内,并且达到有效金额才显示
				if (buyMoney.compareTo(new BigDecimal(5000)) != -1) {
					validInfo.add(info);
					sumMoney = sumMoney.add(buyMoney);
					if (sumMoney.compareTo(new BigDecimal(100000)) != -1) {
                        if (count < 10 && count >= 5) { // 未达到十人获奖
							prizeGetType = 1;
						}
					}
					count += 1;
				}
			}
			// 有效用户信息列表
			result.put("userList", validInfo);
			// 有效总人数
			result.put("personNum", validInfo.size());
			// 有效总金额
			result.put("sumInvest", sumMoney);
			// 人数达到五人并且购买金额达到十万，或者人数达到十人即可领取奖品
			if (validInfo.size() >= 5 && sumMoney.compareTo(new BigDecimal(100000)) != -1 || validInfo.size() >= 10) {
				// 达到中奖标准
				if (prizeGetType == 0) {
                    prizeGetType = 2; // 十人获奖
				}
				// 查询用户兑奖情况(大奖）
				GiftExchangeInfoExample gifExam = new GiftExchangeInfoExample();
				GiftExchangeInfoExample.Criteria cria = gifExam.createCriteria();
				cria.andReferralIdEqualTo(uid);
				cria.andActivityIdEqualTo(ap.getId());
				cria.andTypeEqualTo(1);

				// 判断礼品兑换提示是否显示
				List<GiftExchangeInfo> listGifo = giftExchangeInfoMapper.selectByExample(gifExam);
                if (null != listGifo && listGifo.size() > 0) { // 已存在不提示
					GiftExchangeInfo gei = listGifo.get(0);
					result.put("isCheckedPrize", gei.getStatus());
                } else { // 插入提示
					// 如果没有向中奖记录表插入一条数据
					GiftExchangeInfo gei = new GiftExchangeInfo();
					gei.setActivityId(ap.getId());
					gei.setExchangeTime(new Date());
					gei.setNum(1);
					gei.setReferralId(uid);
					gei.setType(1);
					giftExchangeInfoMapper.insertSelective(gei);
					result.put("isCheckedPrize", -1);
				}
			}
			// 是否达到获奖资格判断
			result.put("isGetPrize", prizeGetType);
		} catch (Exception e) {
			result.put("userList", null);
			result.put("isGetPrize", null);
			result.put("personNum", null);
			result.put("sumInvest", null);
			result.put("isCheckedPrize", null);
			e.printStackTrace();
		}
		return result;
	}

}
