package com.mrbt.lingmoney.admin.service.app;

import org.springframework.web.multipart.MultipartFile;

import com.mrbt.lingmoney.model.UiIconStyle;
import com.mrbt.lingmoney.utils.PageInfo;

/**
 * ui 样式管理
 *
 * @author syb
 * @date 2017年9月13日 下午2:58:07
 * @version 1.0
 **/
public interface UiStyleService {
	/**
	 * 根据条件分页查询
	 * 
	 * @author syb
	 * @date 2017年9月13日 下午3:01:00
	 * @version 1.0
	 * @param page
	 *            当前页
	 * @param row
	 *            每页条数
	 * @param batchNo
	 *            批次号
	 * @param status
	 *            状态
	 * @param desc
	 *            描述
	 * @return ret
	 *
	 */
	PageInfo listUiStyle(Integer page, Integer row, String batchNo,
			Integer status, String desc);
	/**
	 * 插入or更新
	 * 
	 * @param uiIconStyle	uiIconStyle
	 * @param file	fileinput
	 * @return	ret
	 */
	PageInfo saveOrUpdate(UiIconStyle uiIconStyle, MultipartFile file);

	/**
	 * 批量更新状态
	 * 
	 * @author syb
	 * @date 2017年9月13日 下午5:04:13
	 * @version 1.0
	 * @param ids
	 *            id组，多个用英文逗号分隔
	 * @param status
	 *            状态 0禁用 1启用
	 * @return	ret
	 *
	 */
	PageInfo batchUpdateStatus(String ids, Integer status);
}
