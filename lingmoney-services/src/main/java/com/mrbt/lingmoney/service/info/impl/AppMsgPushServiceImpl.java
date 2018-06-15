package com.mrbt.lingmoney.service.info.impl;

import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import com.mrbt.lingmoney.commons.exception.PageInfoException;
import com.mrbt.lingmoney.service.info.AppMsgPushService;
import com.mrbt.lingmoney.utils.PropertiesUtil;
import com.mrbt.lingmoney.youmengpush.PushClient;
import com.mrbt.lingmoney.youmengpush.UmengNotification;
import com.mrbt.lingmoney.youmengpush.android.AndroidListcast;
import com.mrbt.lingmoney.youmengpush.ios.IOSListcast;

/**
  *
  *@author yiban
  *@date 2018年3月22日 下午3:12:20
  *@version 1.0
 **/
@Service
public class AppMsgPushServiceImpl implements AppMsgPushService {

    private static String ardroidAppkey;
    private static String ardroidAppMasterSecret;
    private static String iosAppkey;
    private static String iosAppMasterSecret;

    static {
        ardroidAppkey = PropertiesUtil.getPropertiesByKey("youmengpush.properties", "ardroid_appkey");
        ardroidAppMasterSecret = PropertiesUtil.getPropertiesByKey("youmengpush.properties", "ardroid_appMasterSecret");
        iosAppkey = PropertiesUtil.getPropertiesByKey("youmengpush.properties", "IOS_appkey");
        iosAppMasterSecret = PropertiesUtil.getPropertiesByKey("youmengpush.properties", "IOS_appMasterSecret");
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
    private void publishAndroidListcast(AndroidListcast listcast, String androidDeviceTokens) throws Exception {
        if (StringUtils.isEmpty(androidDeviceTokens)) {
            return;
        }
        //        AndroidListcast listcast = new AndroidListcast(ardroidAppkey, ardroidAppMasterSecret);
        //        listcast.setTitle(msg.getMsgTitle());
        //        listcast.setTicker(msg.getMsgTitle());
        //        listcast.setText(msg.getMsgContent());
        //        if (msg.getOpenAction() == null || msg.getOpenAction() == 0) {
        //            listcast.goAppAfterOpen();
        //        } else if (msg.getOpenAction() == 1) {
        //            listcast.goActivityAfterOpen(msg.getOpenPage());
        //        } else if (msg.getOpenAction() == ResultParame.ResultNumber.TWO.getNumber()) {
        //            listcast.goUrlAfterOpen(msg.getOpenUrl());
        //        }
        //        listcast.setDisplayType(AndroidNotification.DisplayType.NOTIFICATION);
        //        listcast.setProductionMode();
        //        listcast.setDeviceToken(androidDeviceTokens);
        PushClient pushClient = new PushClient();
        boolean f = pushClient.send(listcast);
        if (!f) {
            throw new PageInfoException("推送失败", 500);
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
    private void publishIOSListcast(IOSListcast listcast, String iosDeviceTokens) throws Exception {
        if (StringUtils.isEmpty(iosDeviceTokens)) {
            return;
        }
        //        IOSListcast listcast = new IOSListcast(iosAppkey, iosAppMasterSecret);
        //        JSONObject json = new JSONObject();
        //        json.put("title", msg.getMsgTitle());
        //        json.put("body", msg.getMsgContent());
        //        listcast.setCustomizedField("redPacket", "1");
        //        listcast.setPredefinedKeyValue("alert", json);
        //        listcast.setProductionMode();
        //        listcast.setDeviceToken(iosDeviceTokens);
        PushClient pushClient = new PushClient();
        boolean f = pushClient.send(listcast);
        if (!f) {
            throw new PageInfoException("推送失败", 500);
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
