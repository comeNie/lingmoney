package com.mrbt.lingmoney.utils;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * tree需要返回的内容
 * 
 * @author lzp
 *
 */
public class MenuTree implements Serializable {
	public int id;
	/**
	 * 显示内容
	 */
	public String text;
	/**
	 * 子节点
	 */
	public List<MenuTree> children;
	/**
	 * icon图标
	 */
	public String iconCls;
	/**
	 * url地址
	 */
	public String url;

	public MenuTree() {
	}

	public MenuTree(int id, String text, List<MenuTree> children,
			String iconCls, String url) {
		super();
		this.id = id;
		this.text = text;
		this.children = children;
		this.iconCls = iconCls;
		this.url = url;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getText() {
		return text;
	}

	public void setText(String text) {
		this.text = text;
	}

	public List<MenuTree> getChildren() {
		if (children == null) {
			children = new ArrayList<MenuTree>();
		}
		return children;
	}

	public void setChildren(List<MenuTree> children) {
		this.children = children;
	}

	public String getIconCls() {
		return iconCls;
	}

	public void setIconCls(String iconCls) {
		this.iconCls = iconCls;
	}

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

}
