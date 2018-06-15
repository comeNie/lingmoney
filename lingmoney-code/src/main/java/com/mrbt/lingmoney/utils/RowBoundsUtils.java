package com.mrbt.lingmoney.utils;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.lang.math.NumberUtils;
import org.apache.ibatis.session.RowBounds;

public class RowBoundsUtils {
	/**
	 * 获取查询时的页码绑定类
	 * 
	 * @param page
	 * @param rows
	 * @return
	 */
	public static RowBounds getRowBounds(String page, String rows) {
		int start = 0, limit = AppCons.DEFAULT_PAGE_SIZE;
		if (StringUtils.isNotBlank(rows) && NumberUtils.isNumber(rows)) {
			limit = NumberUtils.toInt(rows);
		}
		if (StringUtils.isNotBlank(page) && NumberUtils.isNumber(page)) {
			start = (NumberUtils.toInt(page) - 1) * limit;
		}
		return new RowBounds(start > 0 ? start : 0, limit);
	}
	
	//查询所有
	/**
	 * 获取查询时的页码绑定类
	 * 
	 * @param page
	 * @param rows
	 * @return
	 */
	public static RowBounds getRowBounds(String page, String rows,Integer limit) {
		int start = 0;
		if (StringUtils.isNotBlank(rows) && NumberUtils.isNumber(rows)) {
			limit = NumberUtils.toInt(rows);
		}
		if (StringUtils.isNotBlank(page) && NumberUtils.isNumber(page)) {
			start = (NumberUtils.toInt(page) - 1) * limit;
		}
		return new RowBounds(start > 0 ? start : 0, limit);
	}
}
