package com.mrbt.lingmoney.admin.service.info;

import java.util.Map;

import com.mrbt.lingmoney.model.AreaDomain;
import com.mrbt.lingmoney.utils.PageInfo;

import net.sf.json.JSONArray;

/**
 * 地区域名
 * 
 * @author lihq
 * @date 2017年5月4日 下午5:31:41
 * @description
 * @version 1.0
 * @since 2017-04-12
 */
public interface AreaDomainService {

	/**
	 * 分页条件查询
	 * 
	 * @Description
	 * @param map
	 *            map
	 * @return 返回列表
	 */
	PageInfo listGrid(Map<String, Object> map);

	/**
	 * 添加
	 * 
	 * @Description
	 * @param ad
	 *            ad
	 * @return 数据返回
	 */
	boolean insert(AreaDomain ad);

	/**
	 * 修改
	 * 
	 * @Description
	 * @param ad
	 *            ad
	 * @return 数据返回
	 */
	boolean update(AreaDomain ad);

	/**
	 * 查询所有城市代码，城市名
	 * 
	 * @Description 下拉列表使用
	 * @return code:name
	 */
	JSONArray queryCodeName();

	/**
	 * 查询所用城市信息
	 * 
	 * @param pageInfo
	 *            pageInfo
	 * @return 数据返回
	 */
	PageInfo queryCityInfo(PageInfo pageInfo);
}
