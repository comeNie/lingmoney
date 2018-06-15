package com.mrbt.lingmoney.admin.service.festival;

import java.io.IOException;

import org.springframework.web.multipart.MultipartHttpServletRequest;

import com.mrbt.lingmoney.model.WinningCount;
import com.mrbt.lingmoney.model.WinningCountExample;
import com.mrbt.lingmoney.utils.PageInfo;

/**
 * 中奖统计/限制表
 *
 */
public interface WinningCountService {
	/**
	 * 根据主键查询中奖统计/限制表
	 * 
	 * @param id
	 *            主键
	 * @return 中奖统计/限制表
	 */
	WinningCount findById(int id);

	/**
	 * 删除中奖统计/限制表
	 * 
	 * @param id
	 *            主键
	 */
	void delete(int id);

	/**
	 * 保存中奖统计/限制表
	 * 
	 * @param vo
	 *            中奖统计/限制表
	 */
	void save(WinningCount vo);

	/**
	 * 更新中奖统计/限制表,字段选择修改
	 * 
	 * @param vo
	 *            中奖统计/限制表
	 */
	void update(WinningCount vo);

	/**
	 * 根据条件帮助类查询
	 * 
	 * @param example
	 *            条件帮助类
	 * @return 分页实体类
	 */
	PageInfo getList(WinningCountExample example);

	/**
	 * 更改中奖统计/限制表状态
	 * 
	 * @param id
	 *            主键
	 * @return 分页实体类
	 */
	PageInfo changeStatus(Integer id);

	/**
	 * 导入限制手机号
	 * 
	 * @param pageInfo
	 *            pageInfo
	 * @param request
	 *            request
	 * @return 数据返回
	 * @throws IOException
	 *             IOException
	 */
	PageInfo upload(PageInfo pageInfo, MultipartHttpServletRequest request) throws IOException;
}
