package com.mrbt.lingmoney.service.users;

import com.mrbt.lingmoney.model.UsresDegree;

/**
 *@author syb
 *@date 2017年5月9日 上午9:24:11
 *@version 1.0
 *@description 
 **/
public interface UsresDegreeService {
	/**
	 * 根据ID查询
	 * @param id 
	 * @return usersDegree
	 */
	UsresDegree queryById(int id);
}
