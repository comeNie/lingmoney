package com.mrbt.lingmoney.admin.service.info.impl;

import java.util.List;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import com.mrbt.lingmoney.admin.service.info.AlertPromptService;
import com.mrbt.lingmoney.mapper.AlertPromptMapper;
import com.mrbt.lingmoney.model.AlertPrompt;
import com.mrbt.lingmoney.model.AlertPromptExample;
import com.mrbt.lingmoney.utils.FtpUtils;
import com.mrbt.lingmoney.utils.PageInfo;
import com.mrbt.lingmoney.utils.ResultParame;

/**
 * 页面设置--》弹框提示
 * 
 */
@Service
public class AlertPromptServiceImpl implements AlertPromptService {

	@Autowired
	private AlertPromptMapper alertPromptMapper;

	@Autowired
	private FtpUtils ftpUtils;

	@Override
	public AlertPrompt findById(String id) {
		return alertPromptMapper.selectByPrimaryKey(id);
	}

	@Override
	@Transactional
	public void delete(String id) {
		AlertPrompt alertPrompt = new AlertPrompt();
		alertPrompt.setId(id);
		alertPrompt.setStatus(ResultParame.ResultNumber.MINUS_ONE.getNumber());
		alertPromptMapper.updateByPrimaryKeySelective(alertPrompt);
	}

	/**
	 * 上传文件
	 * 
	 * @param bannerRootPath
	 *            bannerRootPath
	 * @param file
	 *            file
	 * @return 数据返回
	 */
	public String uploadFile(String bannerRootPath, MultipartFile file) {
		String imgUrl = ftpUtils.uploadImages(file, bannerRootPath, ftpUtils);
		if (imgUrl != null && !imgUrl.equals("300") && !imgUrl.equals("500")) {
			return imgUrl;
		}
		return null;
	}

	/**
	 * 上传
	 * 
	 * @param vo
	 *            AlertPrompt
	 * @param file1
	 *            file1
	 * @param file2
	 *            file1
	 * @param bannerRootPath
	 *            bannerRootPath
	 */
	public void upload(AlertPrompt vo, MultipartFile file1, MultipartFile file2, String bannerRootPath) {
		String codePath1 = uploadFile(bannerRootPath, file1);
		if (codePath1 != null) {
			vo.setBigImg(codePath1);
		}
		String codePath2 = uploadFile(bannerRootPath, file2);
		if (codePath2 != null) {
			vo.setButtonImg(codePath2);
		}
	}

	@Override
	@Transactional()
	public void save(AlertPrompt vo, MultipartFile file1, MultipartFile file2, String bannerRootPath) {
		upload(vo, file1, file2, bannerRootPath);
		vo.setId(UUID.randomUUID().toString().replace("-", ""));
		vo.setStatus(0);
		alertPromptMapper.insert(vo);
	}

	@Override
	@Transactional()
	public void update(AlertPrompt vo, MultipartFile file1, MultipartFile file2, String bannerRootPath) {
		upload(vo, file1, file2, bannerRootPath);
		alertPromptMapper.updateByPrimaryKeySelective(vo);
	}

	@Override
	public PageInfo getList(AlertPromptExample example) {
		PageInfo pageInfo = new PageInfo();
		int resSize = (int) alertPromptMapper.countByExample(example);
		List<AlertPrompt> list = alertPromptMapper.selectByExample(example);
		pageInfo.setRows(list);
		pageInfo.setTotal(resSize);
		return pageInfo;
	}

	@Override
	public boolean changeStatus(AlertPrompt vo) {
		boolean flag = false;
		if (vo != null && vo.getId() != null) {
			AlertPrompt record = alertPromptMapper.selectByPrimaryKey(vo.getId());
			if (record != null) {
				record.setStatus(vo.getStatus());
				alertPromptMapper.updateByPrimaryKeySelective(record);
				flag = true;
			}
		}
		return flag;
	}

}
