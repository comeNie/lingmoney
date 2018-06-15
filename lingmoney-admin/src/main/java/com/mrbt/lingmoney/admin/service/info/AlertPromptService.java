package com.mrbt.lingmoney.admin.service.info;

import org.springframework.web.multipart.MultipartFile;

import com.mrbt.lingmoney.model.AlertPrompt;
import com.mrbt.lingmoney.model.AlertPromptExample;
import com.mrbt.lingmoney.utils.PageInfo;

/**
 * 页面设置--》弹框提示
 * 
 */
public interface AlertPromptService {
	/**
	 * 根据主键查询
	 * 
	 * @param id
	 *            主键
	 * @return 数据返回
	 */
	AlertPrompt findById(String id);

	/**
	 * 删除
	 * 
	 * @param id
	 *            主键
	 */
	void delete(String id);

	/**
	 * 保存
	 * 
	 * @param vo
	 *            AlertPrompt
	 * @param file1
	 *            file1
	 * @param file2
	 *            file2
	 * @param bannerRootPath
	 *            bannerRootPath
	 */
	void save(AlertPrompt vo, MultipartFile file1, MultipartFile file2, String bannerRootPath);

	/**
	 * 更新,字段选择修改
	 * 
	 * @param vo
	 *            AlertPrompt
	 * @param file1
	 *            file1
	 * @param file2
	 *            file2
	 * @param bannerRootPath
	 *            bannerRootPath
	 */
	void update(AlertPrompt vo, MultipartFile file1, MultipartFile file2, String bannerRootPath);

	/**
	 * 根据条件帮助类查询
	 * 
	 * @param example
	 *            条件帮助类
	 * @return 分页实体类
	 */
	PageInfo getList(AlertPromptExample example);

	/**
	 * 更改状态
	 * 
	 * @param vo
	 *            AlertPrompt
	 * @return 分页实体类
	 */
	boolean changeStatus(AlertPrompt vo);
}
