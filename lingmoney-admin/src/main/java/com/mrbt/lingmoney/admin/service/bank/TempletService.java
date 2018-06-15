package com.mrbt.lingmoney.admin.service.bank;

import org.springframework.web.multipart.MultipartFile;

/**
 * 
 * @author Administrator
 *
 */
public interface TempletService {

	/**
	 * 上传文件
	 * @author YJQ  2017年8月4日
	 * @param multfile	multfile
	 */
	void uploadFile(MultipartFile multfile);

}
