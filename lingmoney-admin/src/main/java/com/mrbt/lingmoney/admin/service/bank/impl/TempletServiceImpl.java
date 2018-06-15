package com.mrbt.lingmoney.admin.service.bank.impl;

import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.mrbt.lingmoney.admin.service.bank.TempletService;
import com.mrbt.lingmoney.mapper.TempletMapper;
import com.mrbt.lingmoney.model.Templet;

/**
 * 
 * @author Administrator
 *
 */
@Service
public class TempletServiceImpl implements TempletService {
	@Autowired
	private TempletMapper templetMapper;
	
	@Override
	public void uploadFile(MultipartFile multfile) {
		try {
			// 获取文件类型
			String fileName = multfile.getOriginalFilename();
			String[] fileArr = fileName.split("\\.");
			String fileType = fileArr[fileArr.length - 1];
			byte[] file = multfile.getBytes();
			Templet te = new Templet();
			te.setFile(file);
			te.setFileType(fileType);
			te.setModiDate(new Date());
			te.setStatus(1);
			templetMapper.insertSelective(te);

		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
