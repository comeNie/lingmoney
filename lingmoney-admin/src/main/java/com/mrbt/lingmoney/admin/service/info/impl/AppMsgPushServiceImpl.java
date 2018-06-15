package com.mrbt.lingmoney.admin.service.info.impl;

import java.util.ArrayList;
import java.util.List;

import net.sf.json.JSONObject;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.BadSqlGrammarException;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;

import com.mrbt.lingmoney.admin.exception.ServiceException;
import com.mrbt.lingmoney.admin.service.info.AppMsgPushService;
import com.mrbt.lingmoney.commons.exception.PageInfoException;
import com.mrbt.lingmoney.mapper.AppPushMsgMapper;
import com.mrbt.lingmoney.model.AppPushMsg;
import com.mrbt.lingmoney.model.AppPushMsgExample;
import com.mrbt.lingmoney.model.AppPushMsgExample.Criteria;
import com.mrbt.lingmoney.model.youmengpush.UserDeivceVo;
import com.mrbt.lingmoney.utils.PageInfo;
import com.mrbt.lingmoney.utils.PropertiesUtil;
import com.mrbt.lingmoney.utils.ResultParame;
import com.mrbt.lingmoney.youmengpush.PushClient;
import com.mrbt.lingmoney.youmengpush.UmengNotification;
import com.mrbt.lingmoney.youmengpush.android.AndroidBroadcast;
import com.mrbt.lingmoney.youmengpush.android.AndroidListcast;
import com.mrbt.lingmoney.youmengpush.android.AndroidNotification;
import com.mrbt.lingmoney.youmengpush.ios.IOSBroadcast;
import com.mrbt.lingmoney.youmengpush.ios.IOSListcast;

/**
 * @author luox
 * @Date 2017年5月24日
 */
@Service
public class AppMsgPushServiceImpl implements AppMsgPushService {

	@Autowired
	private AppPushMsgMapper appPushMsgMapper;
	@Autowired
	private JdbcTemplate jdbcTemplate;
	@Autowired
	private PushClient pushClient;
	
	private static String ardroidAppkey;
	private static String ardroidAppMasterSecret;
	private static String iosAppkey;
	private static String iosAppMasterSecret;
	
	static {
		ardroidAppkey = PropertiesUtil.getPropertiesByKey("youmengpush.properties", "ardroid_appkey");
		ardroidAppMasterSecret = PropertiesUtil.getPropertiesByKey("youmengpush.properties",
				"ardroid_appMasterSecret");
		iosAppkey = PropertiesUtil.getPropertiesByKey("youmengpush.properties", "IOS_appkey");
		iosAppMasterSecret = PropertiesUtil.getPropertiesByKey("youmengpush.properties", "IOS_appMasterSecret");
	}

	/**
	 * 发送安卓所有用户
	 * 
	 * @param msg
	 *            msg
	 * @throws Exception
	 *             异常
	 */
	private void publishAndroidBroadcast(AppPushMsg msg) throws Exception {
		AndroidBroadcast broadcast = new AndroidBroadcast(ardroidAppkey, ardroidAppMasterSecret);
		broadcast.setTicker(msg.getMsgTitle());
		broadcast.setTitle(msg.getMsgTitle());
		broadcast.setText(msg.getMsgContent());
		if (msg.getOpenAction() == 0) {
			broadcast.goAppAfterOpen();
		} else if (msg.getOpenAction() == 1) {
			broadcast.goActivityAfterOpen(msg.getOpenPage());
		} else if (msg.getOpenAction() == ResultParame.ResultNumber.TWO.getNumber()) {
			broadcast.goUrlAfterOpen(msg.getOpenUrl());
		}
		broadcast.setProductionMode();
		boolean f = pushClient.send(broadcast);
		if (!f) {
			throw new ServiceException("推送失败");
		}
	}

	/**
	 * 发送安卓部分用户
	 * 
	 * @param msg
	 *            msg
	 * @param androidDeviceTokens
	 *            androidDeviceTokens
	 * @throws Exception
	 *             Exception
	 */
    @Override
    public void publishAndroidListcast(AppPushMsg msg, List<String> androidDeviceTokens) throws Exception {
		if (androidDeviceTokens == null || androidDeviceTokens.size() == 0) {
			return;
		}
		AndroidListcast listcast = new AndroidListcast(ardroidAppkey, ardroidAppMasterSecret);
		listcast.setTitle(msg.getMsgTitle());
		listcast.setTicker(msg.getMsgTitle());
		listcast.setText(msg.getMsgContent());
		if (msg.getOpenAction() == 0) {
			listcast.goAppAfterOpen();
		} else if (msg.getOpenAction() == 1) {
			listcast.goActivityAfterOpen(msg.getOpenPage());
		} else if (msg.getOpenAction() == ResultParame.ResultNumber.TWO.getNumber()) {
			listcast.goUrlAfterOpen(msg.getOpenUrl());
		}
		listcast.setDisplayType(AndroidNotification.DisplayType.NOTIFICATION);
		listcast.setProductionMode();

		List<List<String>> list = splitDeviceTokenList(androidDeviceTokens);
		StringBuilder sb = null;
		int size = 0;
		for (List<String> deviceTokenList : list) {
			sb = new StringBuilder();
			size = deviceTokenList.size();
			for (int i = 0; i < size; i++) {
				sb.append(deviceTokenList.get(i));
				if (i < size - 1) {
					sb.append(",");
				}
			}
			listcast.setDeviceToken(sb.toString());
			boolean f = pushClient.send(listcast);
			if (!f) {
				throw new ServiceException("推送失败");
			}
		}
	}

	/**
	 * 发送ios所有用户
	 * 
	 * @param msg
	 *            msg
	 * @throws Exception
	 *             Exception
	 */
	private void publishIOSBroadcast(AppPushMsg msg) throws Exception {
		IOSBroadcast broadcast = new IOSBroadcast(iosAppkey, iosAppMasterSecret);
        JSONObject json = new JSONObject();
        json.put("title", msg.getMsgTitle());
        json.put("body", msg.getMsgContent());
        broadcast.setPredefinedKeyValue("alert", json);
        broadcast.setProductionMode();
        boolean f = pushClient.send(broadcast);
        if (!f) {
            throw new ServiceException("推送失败");
        }
	}

	/**
	 * 发送ios部分用户
	 * 
	 * @param msg
	 *            msg
	 * @param iosDeviceTokens
	 *            iosDeviceTokens
	 * @throws Exception
	 *             Exception
	 */
    @Override
    public void publishIOSListcast(AppPushMsg msg, List<String> iosDeviceTokens) throws Exception {
		if (iosDeviceTokens == null || iosDeviceTokens.size() == 0) {
			return;
		}
		IOSListcast listcast = new IOSListcast(iosAppkey, iosAppMasterSecret);
        JSONObject json = new JSONObject();
        json.put("title", msg.getMsgTitle());
        json.put("subtitle", msg.getMsgTitle());
        json.put("body", msg.getMsgContent());
        listcast.setPredefinedKeyValue("alert", json);
        listcast.setProductionMode();
		
        List<List<String>> list = splitDeviceTokenList(iosDeviceTokens);
        StringBuilder sb = null;
        int size = 0;
        for (List<String> deviceTokenList : list) {
            sb = new StringBuilder();
            size = deviceTokenList.size();
            for (int i = 0; i < size; i++) {
                sb.append(deviceTokenList.get(i));
                if (i < size - 1) {
                    sb.append(",");
                }
            }
            listcast.setDeviceToken(sb.toString());
            boolean f = pushClient.send(listcast);
            if (!f) {
                throw new ServiceException("推送失败");
            }
        }
	}

	/**
	 * 数据返回
	 * 
	 * @param deviceTokens
	 *            deviceTokens
	 * @return 数据返回
	 */
	private List<List<String>> splitDeviceTokenList(List<String> deviceTokens) {
		if (deviceTokens == null) {
			return null;
		}
		List<List<String>> returnList = new ArrayList<>();
		int size = deviceTokens.size();
		List<String> list = null;
		for (int i = 0, a = 0; i < size; i += ResultParame.ResultNumber.FIVE_HUNDRED.getNumber()) {
			a++;
			list = new ArrayList<>();
			for (int j = i; j < size && j < a * ResultParame.ResultNumber.FIVE_HUNDRED.getNumber(); j++) {
				list.add(deviceTokens.get(j));
			}
			returnList.add(list);
		}
		return returnList;
	}
	
	@Override
	public void publish(Integer id) throws Exception {
		AppPushMsg msg = appPushMsgMapper.selectByPrimaryKey(id);

		if (msg.getUserGroup() == 0) {
			// 发送给所有用户
			publishAndroidBroadcast(msg);
			// ios
			publishIOSBroadcast(msg);
		} else {
			// 发送给指定用户
			String sql = msg.getUserGroupSql();
			List<UserDeivceVo> userList = jdbcTemplate.query(sql,
					new BeanPropertyRowMapper<UserDeivceVo>(UserDeivceVo.class));
			List<String> androidDeviceTokens = new ArrayList<>();
			List<String> iosDeviceTokens = new ArrayList<>();
			for (UserDeivceVo vo : userList) {
				if (vo.getDeviceType() == 1) {
					androidDeviceTokens.add(vo.getYoumengToken());
				} else if (vo.getDeviceType() == ResultParame.ResultNumber.TWO.getNumber()) {
					iosDeviceTokens.add(vo.getYoumengToken());
				}
			}

			// 安卓
			publishAndroidListcast(msg, androidDeviceTokens);
			// IOS
			publishIOSListcast(msg, iosDeviceTokens);
			
		}
		// 修改消息状态
		msg.setStatus(1);
		appPushMsgMapper.updateByPrimaryKeySelective(msg);
	}

	@Override
	public void getList(String msgTitle, Integer status, PageInfo pageInfo) {
		AppPushMsgExample example = new AppPushMsgExample();
		example.setLimitStart(pageInfo.getFrom());
		example.setLimitEnd(pageInfo.getPagesize());
		Criteria criteria = example.createCriteria().andStatusNotEqualTo(ResultParame.ResultNumber.TWO.getNumber());
		if (msgTitle != null) {
			criteria.andMsgTitleLike("%" + msgTitle + "%");
		}
		if (status != null) {
			criteria.andStatusEqualTo(status);
		}
		pageInfo.setRows(appPushMsgMapper.selectByExample(example));
		pageInfo.setTotal((int) appPushMsgMapper.countByExample(example));
	}

	@Override
	public void delete(Integer id) {
		AppPushMsg msg = new AppPushMsg();
		msg.setId(id);
		msg.setStatus(ResultParame.ResultNumber.TWO.getNumber());
		appPushMsgMapper.updateByPrimaryKeySelective(msg);
	}

	@Override
	public void saveOrEdit(AppPushMsg msg) {
		String sql = msg.getUserGroupSql();
		try {
			if (sql != null) {
				jdbcTemplate.execute(sql);
			}
		} catch (BadSqlGrammarException e) {
			e.printStackTrace();
			throw new ServiceException("sql语法有误！");
		}
		if (msg.getId() == null) {
			msg.setStatus(0);
			appPushMsgMapper.insertSelective(msg);
		} else {
			if (msg.getUserGroup() == 0) {
				msg.setUserGroupSql("");
			}
			if (msg.getOpenAction() == 0) {
				msg.setOpenPage("");
				msg.setOpenUrl("");
			} else if (msg.getOpenAction() == 1) {
				msg.setOpenUrl("");
			} else if (msg.getOpenAction() == ResultParame.ResultNumber.TWO.getNumber()) {
				msg.setOpenPage("");
			}
			appPushMsgMapper.updateByPrimaryKeySelective(msg);
		}
	}

    @Override
    public IOSListcast getIOSListcast() {
        try {
            return new IOSListcast(iosAppkey, iosAppMasterSecret);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public AndroidListcast getAndroidListcast() {
        try {
            return new AndroidListcast(ardroidAppkey, ardroidAppMasterSecret);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public void pushMsg(UmengNotification listcast) throws Exception {
        PushClient pushClient = new PushClient();
        boolean f = pushClient.send(listcast);
        if (!f) {
            throw new PageInfoException("推送失败", 500);
        }
    }

}
