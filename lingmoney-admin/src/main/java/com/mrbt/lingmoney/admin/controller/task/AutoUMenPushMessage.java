package com.mrbt.lingmoney.admin.controller.task;

import java.math.BigDecimal;
import java.text.MessageFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.alibaba.fastjson.JSONException;
import com.alibaba.fastjson.JSONObject;
import com.mrbt.lingmoney.admin.service.info.AppMsgPushService;
import com.mrbt.lingmoney.commons.cache.RedisDao;
import com.mrbt.lingmoney.mapper.UsersPropertiesMapper;
import com.mrbt.lingmoney.mapper.UsersRedPacketMapper;
import com.mrbt.lingmoney.model.AppPushMsg;
import com.mrbt.lingmoney.model.UsersProperties;
import com.mrbt.lingmoney.model.UsersRedPacket;
import com.mrbt.lingmoney.model.UsersRedPacketExample;
import com.mrbt.lingmoney.youmengpush.android.AndroidListcast;
import com.mrbt.lingmoney.youmengpush.android.AndroidNotification.DisplayType;
import com.mrbt.lingmoney.youmengpush.ios.IOSListcast;

/**
  *红包消息推送
  *@author yiban
  *@author suyibo
  *@date 2018年3月22日 上午11:50:33
  *@version 1.0
  *
  *2、红包/加息券还有27/3天到期时，消息提示内容如下：
  *1）您有【变量1】个红包/张加息券还有【变量1】天到期，不要让高收益与你擦肩而过，赶紧使用吧！
  *2）您有【变量1】个红包、【变量1】张加息券还有【变量1】天到期，不要让高收益与你擦肩而过，赶紧使用吧！
  *
  *3、红包/加息券当天到期日，消息提示内容如下：
  *1）您有【变量1】个红包/张加息券今天到期，不用就白白浪费咯，赶紧使用吧！
  *2）您有【变量1】个红包、【变量1】张加息券今天到期，不用就白白浪费咯，赶紧使用吧！
  *
 **/
@Component("autoUMenPushMessage")
public class AutoUMenPushMessage {
    private static final Logger LOG = LogManager.getLogger(AutoUMenPushMessage.class);
    @Autowired
    private UsersRedPacketMapper usersRedPacketMapper;
    @Autowired
    private AppMsgPushService appMsgPushService;
    
    @Autowired
    private UsersPropertiesMapper usersPropertiesMapper;
    
    @Autowired
    private RedisDao redisDao;
    
    private static final String HAVE_NEW_REDPACKET_PUSH_DATE = "HAVE_NEW_REDPACKET_PUSH_DATE";
    
    private static final Map<String, String> uidDis = new HashMap<String, String>();
    
    public void handle() {
        //1.查询所有三天内到期的未使用红包加息券
        List<Map<String, Object>> list = usersRedPacketMapper.listRedPacketPushMessageInfo();
        if (list != null && list.size() > 0) {
            for (Map<String, Object> map : list) {
                try {
                    int giftType = 0; // 礼品类型 0 both 1加息券 2红包
					int days = (int) map.get("days");// 天数 27 or 3
                    BigDecimal ratenum = (BigDecimal) map.get("ratenum");//红包数量
                    BigDecimal packetnum = (BigDecimal) map.get("packetnum");//加息券数量
                    int iratenum = ratenum.intValue();
                    int ipacketnum = packetnum.intValue();
                    String token = (String) map.get("umentoken");
                    AppPushMsg msg = new AppPushMsg();
                    msg.setMsgTitle("领钱儿");
//                    if (days == 0) {
//                        if (iratenum == 0 || ipacketnum == 0) {
//                            if (iratenum > 0 || ipacketnum > 0) {
//                                String content = "您有{0}{1}今天到期，不用就白白浪费咯，赶紧使用吧！";
//                                msg.setMsgContent(MessageFormat.format(content, iratenum > 0 ? ratenum : packetnum,
//                                        iratenum > 0 ? "张加息券" : "个红包"));
//                                giftType = iratenum > 0 ? 1 : 2;
//                            }
//                        } else {
//                            String content = "您有{0}个红包、{1}张加息券今天到期，不用就白白浪费咯，赶紧使用吧！";
//                            msg.setMsgContent(MessageFormat.format(content, packetnum, ratenum));
//                        }
//                    }

					if (days == 3) {
                        if (iratenum == 0 || ipacketnum == 0) {
                            if (iratenum > 0 || ipacketnum > 0) {
                                String content = "您有{0}{1}还有3天到期，不要让高收益与你擦肩而过，赶紧使用吧！";
                                msg.setMsgContent(MessageFormat.format(content, iratenum > 0 ? ratenum : packetnum,
                                        iratenum > 0 ? "张加息券" : "个红包"));
                                giftType = iratenum > 0 ? 1 : 2;
                            }
                        } else {
                            String content = "您有{0}个红包、{1}张加息券还有3天到期，不要让高收益与你擦肩而过，赶紧使用吧！";
                            msg.setMsgContent(MessageFormat.format(content, packetnum, ratenum));
                        }
					} else if (days == 27) {
						if (iratenum == 0 || ipacketnum == 0) {
                            if (iratenum > 0 || ipacketnum > 0) {
                                String content = "您有{0}{1}还有27天到期，不要让高收益与你擦肩而过，赶紧使用吧！";
                                msg.setMsgContent(MessageFormat.format(content, iratenum > 0 ? ratenum : packetnum,
                                        iratenum > 0 ? "张加息券" : "个红包"));
                                giftType = iratenum > 0 ? 1 : 2;
                            }
                        } else {
							String content = "您有{0}个红包、{1}张加息券还有27天到期，不要让高收益与你擦肩而过，赶紧使用吧！";
                            msg.setMsgContent(MessageFormat.format(content, packetnum, ratenum));
                        }
                    }

                    int deviceType = (int) map.get("deviceType");
                    distinguishPushDevice(deviceType, giftType, msg.getMsgContent(), token);

                } catch (Exception e) {
                    LOG.error("用户{}的红包消息推送失败。{}", map.get("uid"), e.toString());
                    e.printStackTrace();
                }
            }
        }

    }
    
    /**
     * 有新优惠券推送提醒
     */
    public void haveNewCouponPush() {
    	//获取上一次查询时间
    	Date date = null;
    	if (redisDao.get(HAVE_NEW_REDPACKET_PUSH_DATE) != null) {
			date = (Date) redisDao.get(HAVE_NEW_REDPACKET_PUSH_DATE);
		} else {
			date = new Date();
		}
    	
    	//查询获取了新优惠券的用户信息
    	UsersRedPacketExample example = new UsersRedPacketExample();
    	example.createCriteria().andCreateTimeGreaterThan(date);
    	example.setOrderByClause(" id");
    	
    	List<UsersRedPacket> urpList = usersRedPacketMapper.selectByExample(example);
    	
    	for(UsersRedPacket urp : urpList) {
    		if (uidDis.containsKey(urp.getuId())) {
				//已推送过，跳过
    			continue;
			} else {
				//获取用户信息
				//调用推送
				UsersProperties uProperties = usersPropertiesMapper.selectByuId(urp.getuId());
				if (uProperties.getDeviceType() != null && uProperties.getYoumengToken() != null && !uProperties.getYoumengToken().equals("")) {
					String content = "尊敬的领钱儿用户，您获得了新的优惠券，请查收!";
					distinguishPushDevice(uProperties.getDeviceType(), 0, content, uProperties.getYoumengToken());
				}
			}
    		redisDao.set(HAVE_NEW_REDPACKET_PUSH_DATE, urp.getCreateTime());
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
                JSONObject object = new JSONObject();
				try {
					JSONObject object2 = new JSONObject();
					JSONObject object3 = new JSONObject();
					object3.put("status", 1);
					object3.put("money", "");
					object3.put("code", "0");
					object3.put("couponId", -1);
					object2.put("pushClassName", "mrbt.lingqian.main.mine.Coupon.CouponActivity");
					object2.put("androidJumpType", "5");
					object2.put("pushClassPropertys", object3);
					object.put("imgUrl", "https://ss0.bdstatic.com/94oJfD_bAAcT8t7mm9GUKT-xh_/timg?image&quality=100&size=b4000_4000&sec=1521774965&di=cbba08a55fdbbac63df17a69148dad38&src=http://img.zcool.cn/community/0175ac57c83b160000018c1b520d0f.jpg");
					object.put("androidObj", object2);

					// customjson.put("imgUrl",
					// "https://ss0.bdstatic.com/94oJfD_bAAcT8t7mm9GUKT-xh_/timg?image&quality=100&size=b4000_4000&sec=1521774965&di=cbba08a55fdbbac63df17a69148dad38&src=http://img.zcool.cn/community/0175ac57c83b160000018c1b520d0f.jpg");
					// customjson.put("androidObj",
					// "{\"pushClassName\":\"mrbt.lingqian.main.mine.Coupon.CouponActivity\",\"androidJumpType\":\"5\",\"pushClassPropertys\":{\"status\":0,\"money\":\"1\",\"couponId\":0,\"code\":\"0\"}}");
					listcast.goCustomAfterOpen(object);
					listcast.setDisplayType(DisplayType.NOTIFICATION);
					listcast.setProductionMode();
					listcast.setDeviceToken(token);
					appMsgPushService.pushMsg(listcast);
				} catch (JSONException e) {
					e.printStackTrace();
				}
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

}
