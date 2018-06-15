package com.mrbt.lingmoney.admin.service.newuserreward;

import java.io.IOException;
import java.util.List;
import java.util.Map;

import org.springframework.web.multipart.MultipartFile;

import com.mrbt.lingmoney.utils.PageInfo;

/**
 * 新手福利
 * @author luox
 * @Date 2017年6月26日
 */
public interface NewUserRewardService {

	/**
	 * 列表
	 * 
	 * @param markedWords
	 *            markedWords
	 * @param status
	 *            status
	 * @param pageInfo
	 *            pageInfo
	 */
	void getList(String markedWords, Integer status, PageInfo pageInfo);

	/**
	 * 删除
	 * 
	 * @param id
	 *            id
	 */
	void delete(Integer id);

	/**
	 * 更新
	 * 
	 * @param id
	 *            id
	 */
	void updateStatus(Integer id);

	/**
	 * 上传
	 * 
	 * @param file
	 *            file
	 * @return 数据返回
	 * @throws IOException
	 *             io异常
	 */
	String upload(MultipartFile file) throws IOException;

	/**
	 * 保存或更新
	 * 
	 * @param markedWords
	 *            markedWords
	 * @param id
	 *            id
	 * @param map
	 *            map
	 * @throws IOException
	 *             io异常
	 */
	void saveOrEdit(String markedWords, Integer id, Map<String, List<MultipartFile>> map) throws IOException;

}
