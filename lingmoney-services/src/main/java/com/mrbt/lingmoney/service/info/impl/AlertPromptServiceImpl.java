package com.mrbt.lingmoney.service.info.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import com.mrbt.lingmoney.mapper.AlertPromptMapper;
import com.mrbt.lingmoney.mapper.UsersPropertiesMapper;
import com.mrbt.lingmoney.model.AlertPrompt;
import com.mrbt.lingmoney.model.AlertPromptExample;
import com.mrbt.lingmoney.model.UsersProperties;
import com.mrbt.lingmoney.service.info.AlertPromptService;
import com.mrbt.lingmoney.utils.PageInfo;
import com.mrbt.lingmoney.utils.ResultParame.ResultInfo;

/**
 * 提示框
 *
 */
@Service
public class AlertPromptServiceImpl implements AlertPromptService {

	private static final Logger LOGGER = LogManager.getLogger(AlertPromptServiceImpl.class);

	@Autowired
	private AlertPromptMapper alertPromptMapper;

	@Autowired
	private UsersPropertiesMapper usersPropertiesMapper;

	@Override
	public PageInfo alertPromptInfo(Integer type, String uId, String needLogin) {
		PageInfo pageInfo = new PageInfo();

		// 查找优先级为0的状态是否可用
		AlertPromptExample alertPromptExample = new AlertPromptExample();
		alertPromptExample.createCriteria().andPriorityEqualTo(0);
		List<AlertPrompt> prompts = alertPromptMapper.selectByExample(alertPromptExample);
		if (prompts != null && prompts.size() > 0) {
			AlertPrompt alertPrompt = prompts.get(0);
			Integer status = alertPrompt.getStatus();
			LOGGER.info("第一条弹框提示存在，且状态为：" + status);

			// 初始化弹框类型
            Integer alertType = 2; // 弹框类型默认为普通
			if (status == 1 || status == 2) {
                if (status == 2) { // 开户激活不可用
					LOGGER.info("第一条弹框提示存在，且状态为：开户激活不可用。当前弹框类型为：普通");
                    alertType = 2; // 弹框类型为普通
                } else if (status == 1) { // 全部可用，判断登录状态
					// 用户需要登录
					if ("Y".equals(needLogin)) {
						// 从redis中获取用户id
                        if (!StringUtils.isEmpty(uId)) { // uId不为空
							LOGGER.info("用户" + uId + "，已登录，查询弹框提示");
							// 判断用户开户激活状态
							UsersProperties usersProperties = usersPropertiesMapper.selectByuId(uId);
							// 是否开通华兴E账户
							if (usersProperties.getCertification() == 0) {
								LOGGER.info("第一条弹框提示存在，且状态为：全部可用。当前弹框类型为：开户");
                                alertType = 0; // 开户
                            } else if (usersProperties.getBank() == 0) { // 已开通未激活绑卡
								LOGGER.info("第一条弹框提示存在，且状态为：全部可用。当前弹框类型为：激活");
                                alertType = 1; // 激活
							} else {
								LOGGER.info("第一条弹框提示存在，且状态为：全部可用。当前弹框类型为：普通");
                                alertType = 2; // 普通
							}
						} else {
							// 未登录，暂不弹框
							// pageInfo.setCode(2001);
							// pageInfo.setMsg("暂无数据");
							// return pageInfo;
						}
					} else {
						// 未登录，暂不弹框
						// pageInfo.setCode(2001);
						// pageInfo.setMsg("暂无数据");
						// return pageInfo;
					}

				}
				AlertPromptExample example = new AlertPromptExample();
				example.createCriteria().andStatusEqualTo(1).andAlertTypeEqualTo(alertType).andPriorityNotEqualTo(0);
				example.setOrderByClause("priority");
				List<AlertPrompt> list = alertPromptMapper.selectByExample(example);
				if (list != null && list.size() > 0) {
					// 查询状态为可用的弹框提示bean
					alertPrompt = list.get(0);
					LOGGER.info("当前首页弹框提示数据：" + alertPrompt.toString());
					// 封装到map返回
					Map<String, Object> resMap = new HashMap<String, Object>();
					// 名称
					resMap.put("name", alertPrompt.getName());
					// 大图
					resMap.put("bigImg", alertPrompt.getBigImg());
					// 按钮图片
					resMap.put("buttonImg", alertPrompt.getButtonImg());
					// 二图间距
					resMap.put("distance", alertPrompt.getDistance() == null ? 0 : alertPrompt.getDistance());
					// 与y轴中心距离
					resMap.put("centerY", alertPrompt.getCenterY() == null ? 0 : alertPrompt.getCenterY());
					// 按钮文字
					resMap.put("buttonTitle", alertPrompt.getButtonTitle());
					// 按钮点击类型 0关闭 1跳转页面
					resMap.put("buttonType", alertPrompt.getButtonType());
					// 大图是否全屏显示。0否 1是 2中
					resMap.put("isFullScreen", alertPrompt.getIsFullScreen());
					// 右上角是否有关闭图标。0否 1是
					resMap.put("isCloseShow", alertPrompt.getIsCloseShow());

					// 弹框类型。0开户 1激活 2普通
					resMap.put("alertType", alertPrompt.getAlertType());
                    if (type == 0) { // ios
						// iOS封装对象 小图
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

						// iOS封装对象 大图
						Map<String, Object> iOSObjBig = new HashMap<String, Object>();
						// iOS调用类名
						iOSObjBig.put("class", alertPrompt.getClassIosBig());
						// iOS属性，put(K,V)
						// K->propertyKeyIosBig按英文逗号分隔数组遍历
						// V->propertyValueIosBig按英文逗号分隔数组遍历
						Map<String, Object> iOSPropertyBig = new HashMap<String, Object>();
						String propertyKeyIosBig = alertPrompt.getPropertyKeyIosBig();
						String propertyValueIosBig = alertPrompt.getPropertyValueIosBig();
                        String[] pkiBig = propertyKeyIosBig.split(",");
                        String[] pviBig = propertyValueIosBig.split(",");
						for (int i = 0; i < pkiBig.length; i++) {
							iOSPropertyBig.put(pkiBig[i], pviBig[i]);
						}
						// iOS属性封装
						iOSObjBig.put("property", iOSPropertyBig);
						// iOS封装对象
						resMap.put("iOSObjBig", iOSObjBig);
                    } else if (type == 1) { // android
						// 安卓跳转类型。0，activity 1，fragment 2，webview
						resMap.put("androidJumpType", alertPrompt.getAndroidJumpType());
						// android封装对象 小图
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

						// android封装对象 大图
						Map<String, Object> androidObjBig = new HashMap<String, Object>();
						// android调用类名
						androidObjBig.put("classAndroid", alertPrompt.getClassAndroidBig());
						// android属性，put(K,V)
						// K->propertyKeyIos按英文逗号分隔数组遍历
						// V->propertyValueIos按英文逗号分隔数组遍历
						Map<String, Object> androidPropertyBig = new HashMap<String, Object>();
						String propertyKeyAndroidBig = alertPrompt.getPropertyKeyAndroidBig();
						String propertyValueAndroidBig = alertPrompt.getPropertyValueAndroidBig();
                        String[] pkaBig = propertyKeyAndroidBig.split(",");
                        String[] pvaBig = propertyValueAndroidBig.split(",");
						for (int i = 0; i < pka.length; i++) {
							androidPropertyBig.put(pkaBig[i], pvaBig[i]);
						}
						// android属性封装
						androidObjBig.put("property", androidPropertyBig);
						// android封装对象
						resMap.put("androidObjBig", androidObjBig);
					}
                    pageInfo.setCode(ResultInfo.SUCCESS.getCode());
					pageInfo.setObj(resMap);
					return pageInfo;
				}
			}
		}
		// 未登录，暂不弹框
        pageInfo.setCode(ResultInfo.RETURN_EMPTY_DATA.getCode());
		pageInfo.setMsg("暂无数据");
		return pageInfo;
	}

}
