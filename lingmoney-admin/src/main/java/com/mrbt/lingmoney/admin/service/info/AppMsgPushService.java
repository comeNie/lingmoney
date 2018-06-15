package com.mrbt.lingmoney.admin.service.info;

import java.util.List;

import com.mrbt.lingmoney.model.AppPushMsg;
import com.mrbt.lingmoney.utils.PageInfo;
import com.mrbt.lingmoney.youmengpush.UmengNotification;
import com.mrbt.lingmoney.youmengpush.android.AndroidListcast;
import com.mrbt.lingmoney.youmengpush.ios.IOSListcast;

/**
 * @author luox
 * @Date 2017年5月24日
 */
public interface AppMsgPushService {

	/**
	 * 查询列表
	 * 
	 * @param msgTitle
	 *            msgTitle
	 * @param status
	 *            status
	 * @param pageInfo
	 *            pageInfo
	 */
	void getList(String msgTitle, Integer status, PageInfo pageInfo);

	/**
	 * 删除
	 * 
	 * @param id
	 *            id
	 */
	void delete(Integer id);

	/**
	 * 添加
	 * 
	 * @param msg
	 *            msg
	 */
	void saveOrEdit(AppPushMsg msg);

	/**
	 * 推送消息
	 * 
	 * @param id
	 *            id
	 * @throws Exception
	 *             异常
	 */
	void publish(Integer id) throws Exception;

    /**
     * 向指定ios设备推送消息
     * 
     * @author yiban
     * @date 2018年3月22日 下午12:04:57
     * @version 1.0
     * @param msg
     * @param iosDeviceTokens 设备tokenlist
     * @throws Exception
     *
     */
    void publishIOSListcast(AppPushMsg msg, List<String> iosDeviceTokens) throws Exception;

    /**
     * 向指定android设备推送消息
     * 
     * @author yiban
     * @date 2018年3月22日 下午12:05:15
     * @version 1.0
     * @param msg
     * @param androidDeviceTokens 设备tokenlist
     * @throws Exception
     *
     */
    void publishAndroidListcast(AppPushMsg msg, List<String> androidDeviceTokens) throws Exception;

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
