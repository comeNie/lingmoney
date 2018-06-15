package com.mrbt.lingmoney.service.info;

import com.mrbt.lingmoney.youmengpush.UmengNotification;
import com.mrbt.lingmoney.youmengpush.android.AndroidListcast;
import com.mrbt.lingmoney.youmengpush.ios.IOSListcast;

/**
 * 消息推送
 * @author yiban 2018年3月22日 下午3:24:21
 *
 */
public interface AppMsgPushService {

    /**
     * 推送消息
     * 
     * @author yiban
     * @date 2018年3月23日 上午9:33:43
     * @version 1.0
     * @param listcast
     * @throws Exception
     *
     */
    void pushMsg(UmengNotification listcast) throws Exception;

    /**
     * 获取ios推送实例
     * 
     * @author yiban
     * @date 2018年3月23日 上午9:30:18
     * @version 1.0
     * @return
     *
     */
    IOSListcast getIOSListcast();

    /**
     * 获取安卓推送实例
     * 
     * @author yiban
     * @date 2018年3月23日 上午9:30:34
     * @version 1.0
     * @return
     *
     */
    AndroidListcast getAndroidListcast();

}
