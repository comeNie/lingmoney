package com.mrbt.lingmoney.utils.session;

import java.io.Serializable;
import java.util.HashMap;

import com.mrbt.lingmoney.model.AdminUser;

/**
 * 后台登录用户session
 * 
 * @date 2017年5月3日 下午3:18:37
 * @description
 * @version 1.0
 * @since 2017-04-12
 */
public class UserSession implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	/**
	 * user信息
	 */
	public AdminUser user;
	/**
	 * 导航条的所有信息
	 */
	public HashMap<String, Object> menuMap;

	public UserSession() {
		super();
	}

	public AdminUser getUser() {
		return user;
	}

	public void setUser(AdminUser user) {
		this.user = user;
	}

	public HashMap<String, Object> getMenuMap() {
		return menuMap;
	}

	public void setMenuMap(HashMap<String, Object> menuMap) {
		this.menuMap = menuMap;
	}

}
