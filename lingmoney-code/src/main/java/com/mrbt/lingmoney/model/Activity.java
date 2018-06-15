package com.mrbt.lingmoney.model;

import java.io.Serializable;
import java.util.Date;

public class Activity implements Serializable {
    /**
     * 主键
     * 表字段 : activity.id
     */
    private Integer id;

    /**
     * 标题
     * 表字段 : activity.name
     */
    private String name;

    /**
     * 内容
     * 表字段 : activity.content
     */
    private String content;

    /**
     * 0，领宝；1，抵用券
     * 表字段 : activity.type
     */
    private Integer type;

    /**
     * 有效期
     * 表字段 : activity.validity
     */
    private Date validity;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database table activity
     *
     * @mbg.generated
     */
    private static final long serialVersionUID = 1L;

    /**
     * 获取 主键 字段:activity.id
     *
     * @return activity.id, 主键
     */
    public Integer getId() {
        return id;
    }

    /**
     * 设置 主键 字段:activity.id
     *
     * @param id the value for activity.id, 主键
     */
    public void setId(Integer id) {
        this.id = id;
    }

    /**
     * 获取 标题 字段:activity.name
     *
     * @return activity.name, 标题
     */
    public String getName() {
        return name;
    }

    /**
     * 设置 标题 字段:activity.name
     *
     * @param name the value for activity.name, 标题
     */
    public void setName(String name) {
        this.name = name == null ? null : name.trim();
    }

    /**
     * 获取 内容 字段:activity.content
     *
     * @return activity.content, 内容
     */
    public String getContent() {
        return content;
    }

    /**
     * 设置 内容 字段:activity.content
     *
     * @param content the value for activity.content, 内容
     */
    public void setContent(String content) {
        this.content = content == null ? null : content.trim();
    }

    /**
     * 获取 0，领宝；1，抵用券 字段:activity.type
     *
     * @return activity.type, 0，领宝；1，抵用券
     */
    public Integer getType() {
        return type;
    }

    /**
     * 设置 0，领宝；1，抵用券 字段:activity.type
     *
     * @param type the value for activity.type, 0，领宝；1，抵用券
     */
    public void setType(Integer type) {
        this.type = type;
    }

    /**
     * 获取 有效期 字段:activity.validity
     *
     * @return activity.validity, 有效期
     */
    public Date getValidity() {
        return validity;
    }

    /**
     * 设置 有效期 字段:activity.validity
     *
     * @param validity the value for activity.validity, 有效期
     */
    public void setValidity(Date validity) {
        this.validity = validity;
    }

    /**
     * ,activity
     */
    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(getClass().getSimpleName());
        sb.append(" [");
        sb.append("Hash = ").append(hashCode());
        sb.append(", id=").append(id);
        sb.append(", name=").append(name);
        sb.append(", content=").append(content);
        sb.append(", type=").append(type);
        sb.append(", validity=").append(validity);
        sb.append("]");
        return sb.toString();
    }
}