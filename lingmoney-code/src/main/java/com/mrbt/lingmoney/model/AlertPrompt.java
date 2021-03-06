package com.mrbt.lingmoney.model;

import java.io.Serializable;

public class AlertPrompt implements Serializable {
    /**
     * 主键
     * 表字段 : alert_prompt.id
     */
    private String id;

    /**
     * 名称
     * 表字段 : alert_prompt.name
     */
    private String name;

    /**
     * 按钮文字
     * 表字段 : alert_prompt.button_title
     */
    private String buttonTitle;

    /**
     * 按钮点击类型。0关闭 1跳转页面
     * 表字段 : alert_prompt.button_type
     */
    private Integer buttonType;

    /**
     * 状态。0，不可用；1，可用；-1，删除；2，开户激活不可用
     * 表字段 : alert_prompt.status
     */
    private Integer status;

    /**
     * iOS调用类名 小图
     * 表字段 : alert_prompt.class_ios
     */
    private String classIos;

    /**
     * iOS属性key 多个key以英文逗号分隔 小图
     * 表字段 : alert_prompt.property_key_ios
     */
    private String propertyKeyIos;

    /**
     * iOS属性value 多个value以英文逗号分隔 小图
     * 表字段 : alert_prompt.property_value_ios
     */
    private String propertyValueIos;

    /**
     * android调用类名 小图
     * 表字段 : alert_prompt.class_android
     */
    private String classAndroid;

    /**
     * android属性key 多个key以英文逗号分隔 小图
     * 表字段 : alert_prompt.property_key_android
     */
    private String propertyKeyAndroid;

    /**
     * android属性value 多个value以英文逗号分隔 小图
     * 表字段 : alert_prompt.property_value_android
     */
    private String propertyValueAndroid;

    /**
     * 图片路径 大图
     * 表字段 : alert_prompt.big_img
     */
    private String bigImg;

    /**
     * 图片路径 小图
     * 表字段 : alert_prompt.button_img
     */
    private String buttonImg;

    /**
     * 图片间距
     * 表字段 : alert_prompt.distance
     */
    private Double distance;

    /**
     * 安卓跳转类型。0，activity 1，fragment 2，webview
     * 表字段 : alert_prompt.android_jump_type
     */
    private Integer androidJumpType;

    /**
     * 弹框类型。0开户 1激活 2普通
     * 表字段 : alert_prompt.alert_type
     */
    private Integer alertType;

    /**
     * 优先级。0最大，由低到高
     * 表字段 : alert_prompt.priority
     */
    private Integer priority;

    /**
     * 与y轴中心距离
     * 表字段 : alert_prompt.center_y
     */
    private Double centerY;

    /**
     * 大图是否全屏显示。0否 1是 2中
     * 表字段 : alert_prompt.is_full_screen
     */
    private Integer isFullScreen;

    /**
     * 右上角是否有关闭图标。0否 1是
     * 表字段 : alert_prompt.is_close_show
     */
    private Integer isCloseShow;

    /**
     * iOS调用类名 大图
     * 表字段 : alert_prompt.class_ios_big
     */
    private String classIosBig;

    /**
     * iOS属性key 多个key以英文逗号分隔 大图
     * 表字段 : alert_prompt.property_key_ios_big
     */
    private String propertyKeyIosBig;

    /**
     * iOS属性value 多个value以英文逗号分隔 大图
     * 表字段 : alert_prompt.property_value_ios_big
     */
    private String propertyValueIosBig;

    /**
     * android调用类名 大图
     * 表字段 : alert_prompt.class_android_big
     */
    private String classAndroidBig;

    /**
     * android属性key 多个key以英文逗号分隔 大图
     * 表字段 : alert_prompt.property_key_android_big
     */
    private String propertyKeyAndroidBig;

    /**
     * android属性value 多个value以英文逗号分隔 大图
     * 表字段 : alert_prompt.property_value_android_big
     */
    private String propertyValueAndroidBig;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database table alert_prompt
     *
     * @mbggenerated
     */
    private static final long serialVersionUID = 1L;

    /**
     * 获取 主键 字段:alert_prompt.id
     *
     * @return alert_prompt.id, 主键
     */
    public String getId() {
        return id;
    }

    /**
     * 设置 主键 字段:alert_prompt.id
     *
     * @param id the value for alert_prompt.id, 主键
     */
    public void setId(String id) {
        this.id = id == null ? null : id.trim();
    }

    /**
     * 获取 名称 字段:alert_prompt.name
     *
     * @return alert_prompt.name, 名称
     */
    public String getName() {
        return name;
    }

    /**
     * 设置 名称 字段:alert_prompt.name
     *
     * @param name the value for alert_prompt.name, 名称
     */
    public void setName(String name) {
        this.name = name == null ? null : name.trim();
    }

    /**
     * 获取 按钮文字 字段:alert_prompt.button_title
     *
     * @return alert_prompt.button_title, 按钮文字
     */
    public String getButtonTitle() {
        return buttonTitle;
    }

    /**
     * 设置 按钮文字 字段:alert_prompt.button_title
     *
     * @param buttonTitle the value for alert_prompt.button_title, 按钮文字
     */
    public void setButtonTitle(String buttonTitle) {
        this.buttonTitle = buttonTitle == null ? null : buttonTitle.trim();
    }

    /**
     * 获取 按钮点击类型。0关闭 1跳转页面 字段:alert_prompt.button_type
     *
     * @return alert_prompt.button_type, 按钮点击类型。0关闭 1跳转页面
     */
    public Integer getButtonType() {
        return buttonType;
    }

    /**
     * 设置 按钮点击类型。0关闭 1跳转页面 字段:alert_prompt.button_type
     *
     * @param buttonType the value for alert_prompt.button_type, 按钮点击类型。0关闭 1跳转页面
     */
    public void setButtonType(Integer buttonType) {
        this.buttonType = buttonType;
    }

    /**
     * 获取 状态。0，不可用；1，可用；-1，删除；2，开户激活不可用 字段:alert_prompt.status
     *
     * @return alert_prompt.status, 状态。0，不可用；1，可用；-1，删除；2，开户激活不可用
     */
    public Integer getStatus() {
        return status;
    }

    /**
     * 设置 状态。0，不可用；1，可用；-1，删除；2，开户激活不可用 字段:alert_prompt.status
     *
     * @param status the value for alert_prompt.status, 状态。0，不可用；1，可用；-1，删除；2，开户激活不可用
     */
    public void setStatus(Integer status) {
        this.status = status;
    }

    /**
     * 获取 iOS调用类名 小图 字段:alert_prompt.class_ios
     *
     * @return alert_prompt.class_ios, iOS调用类名 小图
     */
    public String getClassIos() {
        return classIos;
    }

    /**
     * 设置 iOS调用类名 小图 字段:alert_prompt.class_ios
     *
     * @param classIos the value for alert_prompt.class_ios, iOS调用类名 小图
     */
    public void setClassIos(String classIos) {
        this.classIos = classIos == null ? null : classIos.trim();
    }

    /**
     * 获取 iOS属性key 多个key以英文逗号分隔 小图 字段:alert_prompt.property_key_ios
     *
     * @return alert_prompt.property_key_ios, iOS属性key 多个key以英文逗号分隔 小图
     */
    public String getPropertyKeyIos() {
        return propertyKeyIos;
    }

    /**
     * 设置 iOS属性key 多个key以英文逗号分隔 小图 字段:alert_prompt.property_key_ios
     *
     * @param propertyKeyIos the value for alert_prompt.property_key_ios, iOS属性key 多个key以英文逗号分隔 小图
     */
    public void setPropertyKeyIos(String propertyKeyIos) {
        this.propertyKeyIos = propertyKeyIos == null ? null : propertyKeyIos.trim();
    }

    /**
     * 获取 iOS属性value 多个value以英文逗号分隔 小图 字段:alert_prompt.property_value_ios
     *
     * @return alert_prompt.property_value_ios, iOS属性value 多个value以英文逗号分隔 小图
     */
    public String getPropertyValueIos() {
        return propertyValueIos;
    }

    /**
     * 设置 iOS属性value 多个value以英文逗号分隔 小图 字段:alert_prompt.property_value_ios
     *
     * @param propertyValueIos the value for alert_prompt.property_value_ios, iOS属性value 多个value以英文逗号分隔 小图
     */
    public void setPropertyValueIos(String propertyValueIos) {
        this.propertyValueIos = propertyValueIos == null ? null : propertyValueIos.trim();
    }

    /**
     * 获取 android调用类名 小图 字段:alert_prompt.class_android
     *
     * @return alert_prompt.class_android, android调用类名 小图
     */
    public String getClassAndroid() {
        return classAndroid;
    }

    /**
     * 设置 android调用类名 小图 字段:alert_prompt.class_android
     *
     * @param classAndroid the value for alert_prompt.class_android, android调用类名 小图
     */
    public void setClassAndroid(String classAndroid) {
        this.classAndroid = classAndroid == null ? null : classAndroid.trim();
    }

    /**
     * 获取 android属性key 多个key以英文逗号分隔 小图 字段:alert_prompt.property_key_android
     *
     * @return alert_prompt.property_key_android, android属性key 多个key以英文逗号分隔 小图
     */
    public String getPropertyKeyAndroid() {
        return propertyKeyAndroid;
    }

    /**
     * 设置 android属性key 多个key以英文逗号分隔 小图 字段:alert_prompt.property_key_android
     *
     * @param propertyKeyAndroid the value for alert_prompt.property_key_android, android属性key 多个key以英文逗号分隔 小图
     */
    public void setPropertyKeyAndroid(String propertyKeyAndroid) {
        this.propertyKeyAndroid = propertyKeyAndroid == null ? null : propertyKeyAndroid.trim();
    }

    /**
     * 获取 android属性value 多个value以英文逗号分隔 小图 字段:alert_prompt.property_value_android
     *
     * @return alert_prompt.property_value_android, android属性value 多个value以英文逗号分隔 小图
     */
    public String getPropertyValueAndroid() {
        return propertyValueAndroid;
    }

    /**
     * 设置 android属性value 多个value以英文逗号分隔 小图 字段:alert_prompt.property_value_android
     *
     * @param propertyValueAndroid the value for alert_prompt.property_value_android, android属性value 多个value以英文逗号分隔 小图
     */
    public void setPropertyValueAndroid(String propertyValueAndroid) {
        this.propertyValueAndroid = propertyValueAndroid == null ? null : propertyValueAndroid.trim();
    }

    /**
     * 获取 图片路径 大图 字段:alert_prompt.big_img
     *
     * @return alert_prompt.big_img, 图片路径 大图
     */
    public String getBigImg() {
        return bigImg;
    }

    /**
     * 设置 图片路径 大图 字段:alert_prompt.big_img
     *
     * @param bigImg the value for alert_prompt.big_img, 图片路径 大图
     */
    public void setBigImg(String bigImg) {
        this.bigImg = bigImg == null ? null : bigImg.trim();
    }

    /**
     * 获取 图片路径 小图 字段:alert_prompt.button_img
     *
     * @return alert_prompt.button_img, 图片路径 小图
     */
    public String getButtonImg() {
        return buttonImg;
    }

    /**
     * 设置 图片路径 小图 字段:alert_prompt.button_img
     *
     * @param buttonImg the value for alert_prompt.button_img, 图片路径 小图
     */
    public void setButtonImg(String buttonImg) {
        this.buttonImg = buttonImg == null ? null : buttonImg.trim();
    }

    /**
     * 获取 图片间距 字段:alert_prompt.distance
     *
     * @return alert_prompt.distance, 图片间距
     */
    public Double getDistance() {
        return distance;
    }

    /**
     * 设置 图片间距 字段:alert_prompt.distance
     *
     * @param distance the value for alert_prompt.distance, 图片间距
     */
    public void setDistance(Double distance) {
        this.distance = distance;
    }

    /**
     * 获取 安卓跳转类型。0，activity 1，fragment 2，webview 字段:alert_prompt.android_jump_type
     *
     * @return alert_prompt.android_jump_type, 安卓跳转类型。0，activity 1，fragment 2，webview
     */
    public Integer getAndroidJumpType() {
        return androidJumpType;
    }

    /**
     * 设置 安卓跳转类型。0，activity 1，fragment 2，webview 字段:alert_prompt.android_jump_type
     *
     * @param androidJumpType the value for alert_prompt.android_jump_type, 安卓跳转类型。0，activity 1，fragment 2，webview
     */
    public void setAndroidJumpType(Integer androidJumpType) {
        this.androidJumpType = androidJumpType;
    }

    /**
     * 获取 弹框类型。0开户 1激活 2普通 字段:alert_prompt.alert_type
     *
     * @return alert_prompt.alert_type, 弹框类型。0开户 1激活 2普通
     */
    public Integer getAlertType() {
        return alertType;
    }

    /**
     * 设置 弹框类型。0开户 1激活 2普通 字段:alert_prompt.alert_type
     *
     * @param alertType the value for alert_prompt.alert_type, 弹框类型。0开户 1激活 2普通
     */
    public void setAlertType(Integer alertType) {
        this.alertType = alertType;
    }

    /**
     * 获取 优先级。0最大，由低到高 字段:alert_prompt.priority
     *
     * @return alert_prompt.priority, 优先级。0最大，由低到高
     */
    public Integer getPriority() {
        return priority;
    }

    /**
     * 设置 优先级。0最大，由低到高 字段:alert_prompt.priority
     *
     * @param priority the value for alert_prompt.priority, 优先级。0最大，由低到高
     */
    public void setPriority(Integer priority) {
        this.priority = priority;
    }

    /**
     * 获取 与y轴中心距离 字段:alert_prompt.center_y
     *
     * @return alert_prompt.center_y, 与y轴中心距离
     */
    public Double getCenterY() {
        return centerY;
    }

    /**
     * 设置 与y轴中心距离 字段:alert_prompt.center_y
     *
     * @param centerY the value for alert_prompt.center_y, 与y轴中心距离
     */
    public void setCenterY(Double centerY) {
        this.centerY = centerY;
    }

    /**
     * 获取 大图是否全屏显示。0否 1是 2中 字段:alert_prompt.is_full_screen
     *
     * @return alert_prompt.is_full_screen, 大图是否全屏显示。0否 1是 2中
     */
    public Integer getIsFullScreen() {
        return isFullScreen;
    }

    /**
     * 设置 大图是否全屏显示。0否 1是 2中 字段:alert_prompt.is_full_screen
     *
     * @param isFullScreen the value for alert_prompt.is_full_screen, 大图是否全屏显示。0否 1是 2中
     */
    public void setIsFullScreen(Integer isFullScreen) {
        this.isFullScreen = isFullScreen;
    }

    /**
     * 获取 右上角是否有关闭图标。0否 1是 字段:alert_prompt.is_close_show
     *
     * @return alert_prompt.is_close_show, 右上角是否有关闭图标。0否 1是
     */
    public Integer getIsCloseShow() {
        return isCloseShow;
    }

    /**
     * 设置 右上角是否有关闭图标。0否 1是 字段:alert_prompt.is_close_show
     *
     * @param isCloseShow the value for alert_prompt.is_close_show, 右上角是否有关闭图标。0否 1是
     */
    public void setIsCloseShow(Integer isCloseShow) {
        this.isCloseShow = isCloseShow;
    }

    /**
     * 获取 iOS调用类名 大图 字段:alert_prompt.class_ios_big
     *
     * @return alert_prompt.class_ios_big, iOS调用类名 大图
     */
    public String getClassIosBig() {
        return classIosBig;
    }

    /**
     * 设置 iOS调用类名 大图 字段:alert_prompt.class_ios_big
     *
     * @param classIosBig the value for alert_prompt.class_ios_big, iOS调用类名 大图
     */
    public void setClassIosBig(String classIosBig) {
        this.classIosBig = classIosBig == null ? null : classIosBig.trim();
    }

    /**
     * 获取 iOS属性key 多个key以英文逗号分隔 大图 字段:alert_prompt.property_key_ios_big
     *
     * @return alert_prompt.property_key_ios_big, iOS属性key 多个key以英文逗号分隔 大图
     */
    public String getPropertyKeyIosBig() {
        return propertyKeyIosBig;
    }

    /**
     * 设置 iOS属性key 多个key以英文逗号分隔 大图 字段:alert_prompt.property_key_ios_big
     *
     * @param propertyKeyIosBig the value for alert_prompt.property_key_ios_big, iOS属性key 多个key以英文逗号分隔 大图
     */
    public void setPropertyKeyIosBig(String propertyKeyIosBig) {
        this.propertyKeyIosBig = propertyKeyIosBig == null ? null : propertyKeyIosBig.trim();
    }

    /**
     * 获取 iOS属性value 多个value以英文逗号分隔 大图 字段:alert_prompt.property_value_ios_big
     *
     * @return alert_prompt.property_value_ios_big, iOS属性value 多个value以英文逗号分隔 大图
     */
    public String getPropertyValueIosBig() {
        return propertyValueIosBig;
    }

    /**
     * 设置 iOS属性value 多个value以英文逗号分隔 大图 字段:alert_prompt.property_value_ios_big
     *
     * @param propertyValueIosBig the value for alert_prompt.property_value_ios_big, iOS属性value 多个value以英文逗号分隔 大图
     */
    public void setPropertyValueIosBig(String propertyValueIosBig) {
        this.propertyValueIosBig = propertyValueIosBig == null ? null : propertyValueIosBig.trim();
    }

    /**
     * 获取 android调用类名 大图 字段:alert_prompt.class_android_big
     *
     * @return alert_prompt.class_android_big, android调用类名 大图
     */
    public String getClassAndroidBig() {
        return classAndroidBig;
    }

    /**
     * 设置 android调用类名 大图 字段:alert_prompt.class_android_big
     *
     * @param classAndroidBig the value for alert_prompt.class_android_big, android调用类名 大图
     */
    public void setClassAndroidBig(String classAndroidBig) {
        this.classAndroidBig = classAndroidBig == null ? null : classAndroidBig.trim();
    }

    /**
     * 获取 android属性key 多个key以英文逗号分隔 大图 字段:alert_prompt.property_key_android_big
     *
     * @return alert_prompt.property_key_android_big, android属性key 多个key以英文逗号分隔 大图
     */
    public String getPropertyKeyAndroidBig() {
        return propertyKeyAndroidBig;
    }

    /**
     * 设置 android属性key 多个key以英文逗号分隔 大图 字段:alert_prompt.property_key_android_big
     *
     * @param propertyKeyAndroidBig the value for alert_prompt.property_key_android_big, android属性key 多个key以英文逗号分隔 大图
     */
    public void setPropertyKeyAndroidBig(String propertyKeyAndroidBig) {
        this.propertyKeyAndroidBig = propertyKeyAndroidBig == null ? null : propertyKeyAndroidBig.trim();
    }

    /**
     * 获取 android属性value 多个value以英文逗号分隔 大图 字段:alert_prompt.property_value_android_big
     *
     * @return alert_prompt.property_value_android_big, android属性value 多个value以英文逗号分隔 大图
     */
    public String getPropertyValueAndroidBig() {
        return propertyValueAndroidBig;
    }

    /**
     * 设置 android属性value 多个value以英文逗号分隔 大图 字段:alert_prompt.property_value_android_big
     *
     * @param propertyValueAndroidBig the value for alert_prompt.property_value_android_big, android属性value 多个value以英文逗号分隔 大图
     */
    public void setPropertyValueAndroidBig(String propertyValueAndroidBig) {
        this.propertyValueAndroidBig = propertyValueAndroidBig == null ? null : propertyValueAndroidBig.trim();
    }

    /**
     * ,alert_prompt
     */
    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(getClass().getSimpleName());
        sb.append(" [");
        sb.append("Hash = ").append(hashCode());
        sb.append(", id=").append(id);
        sb.append(", name=").append(name);
        sb.append(", buttonTitle=").append(buttonTitle);
        sb.append(", buttonType=").append(buttonType);
        sb.append(", status=").append(status);
        sb.append(", classIos=").append(classIos);
        sb.append(", propertyKeyIos=").append(propertyKeyIos);
        sb.append(", propertyValueIos=").append(propertyValueIos);
        sb.append(", classAndroid=").append(classAndroid);
        sb.append(", propertyKeyAndroid=").append(propertyKeyAndroid);
        sb.append(", propertyValueAndroid=").append(propertyValueAndroid);
        sb.append(", bigImg=").append(bigImg);
        sb.append(", buttonImg=").append(buttonImg);
        sb.append(", distance=").append(distance);
        sb.append(", androidJumpType=").append(androidJumpType);
        sb.append(", alertType=").append(alertType);
        sb.append(", priority=").append(priority);
        sb.append(", centerY=").append(centerY);
        sb.append(", isFullScreen=").append(isFullScreen);
        sb.append(", isCloseShow=").append(isCloseShow);
        sb.append(", classIosBig=").append(classIosBig);
        sb.append(", propertyKeyIosBig=").append(propertyKeyIosBig);
        sb.append(", propertyValueIosBig=").append(propertyValueIosBig);
        sb.append(", classAndroidBig=").append(classAndroidBig);
        sb.append(", propertyKeyAndroidBig=").append(propertyKeyAndroidBig);
        sb.append(", propertyValueAndroidBig=").append(propertyValueAndroidBig);
        sb.append("]");
        return sb.toString();
    }
}