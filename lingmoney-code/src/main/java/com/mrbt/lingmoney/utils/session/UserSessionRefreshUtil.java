package com.mrbt.lingmoney.utils.session;

import javax.servlet.http.HttpSession;

import com.mrbt.lingmoney.model.Users;
import com.mrbt.lingmoney.model.UsersProperties;
import com.mrbt.lingmoney.model.UsresDegree;
import com.mrbt.lingmoney.utils.AppCons;

/**
 * 刷新session中的当前用户信息
 * 
 * @author Administrator
 *
 */
public class UserSessionRefreshUtil {
	public static void sessoinRefresh(HttpSession session, Users users,
			UsersProperties usersProperties, UsresDegree usresDegree) {
		UsersSession usession = new UsersSession();
		usession.setUsers(users);
		usession.setUsersProperties(usersProperties);
		usession.setUsresDegree(usresDegree);
		session.removeAttribute(AppCons.SESSION_USER);
		session.setAttribute(AppCons.SESSION_USER, usession);
	}

}
