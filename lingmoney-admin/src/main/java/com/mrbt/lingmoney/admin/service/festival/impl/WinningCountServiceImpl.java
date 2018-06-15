package com.mrbt.lingmoney.admin.service.festival.impl;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.commons.io.FilenameUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import com.mrbt.lingmoney.admin.service.festival.WinningCountService;
import com.mrbt.lingmoney.mapper.WinningCountMapper;
import com.mrbt.lingmoney.model.WinningCount;
import com.mrbt.lingmoney.model.WinningCountExample;
import com.mrbt.lingmoney.utils.PageInfo;
import com.mrbt.lingmoney.utils.ResultParame;

/**
 * 中奖统计/限制表
 *
 */
@Service
public class WinningCountServiceImpl implements WinningCountService {

	private static String uploadFilePath = "/home/www/temp_upload_file/";

	@Autowired
	private WinningCountMapper winningCountMapper;

	@Override
	public WinningCount findById(int id) {
		return winningCountMapper.selectByPrimaryKey(id);
	}

	@Override
	@Transactional
	public void delete(int id) {
		winningCountMapper.deleteByPrimaryKey(id);
	}

	@Override
	@Transactional()
	public void save(WinningCount vo) {
		winningCountMapper.insert(vo);
	}

	@Override
	@Transactional()
	public void update(WinningCount vo) {
		winningCountMapper.updateByPrimaryKeySelective(vo);
	}

	@Override
	public PageInfo getList(WinningCountExample example) {
		PageInfo pageInfo = new PageInfo();
		long resSize = winningCountMapper.countByExample(example);
		List<WinningCount> list = winningCountMapper.selectByExample(example);
		pageInfo.setRows(list);
		pageInfo.setTotal((int) resSize);
		return pageInfo;
	}

	@Override
	public PageInfo changeStatus(Integer id) {
		PageInfo pageInfo = new PageInfo();
		try {
			if (id != null && id > 0) {
				WinningCount record = winningCountMapper.selectByPrimaryKey(id);
				if (record != null) {
					if (record.getStatus() == null || record.getStatus() == 0) { // 不可用，设置为可用
						record.setStatus(1);
					} else { // 可用，设置为不可用。
						record.setStatus(0);
					}
					winningCountMapper.updateByPrimaryKeySelective(record);
					pageInfo.setCode(ResultParame.ResultInfo.SUCCESS.getCode());
					return pageInfo;
				}
			}
			pageInfo.setCode(ResultParame.ResultInfo.NOT_FOUND.getCode());
			pageInfo.setMsg("参数错误，未找到记录");
		} catch (Exception e) {
			e.printStackTrace();
			pageInfo.setCode(ResultParame.ResultInfo.SERVER_ERROR.getCode());
			pageInfo.setMsg("系统错误");
		}
		return pageInfo;
	}

	@Override
	public PageInfo upload(PageInfo pageInfo, MultipartHttpServletRequest request) throws IOException {
		MultipartFile file = request.getFile("txtFile");
		if (file == null || file.getSize() <= 0) {
			pageInfo.setCode(ResultParame.ResultInfo.NOT_FOUND.getCode());
			pageInfo.setMsg("参数错误，未找到记录");
			return pageInfo;
		}
		String fileName = file.getOriginalFilename();
		String prefix = FilenameUtils.getExtension(fileName);
		if (file.getSize() <= 0 || !"txt".equals(prefix)) {
			pageInfo.setCode(ResultParame.ResultInfo.CHOOSE_TXT.getCode());
			pageInfo.setMsg(ResultParame.ResultInfo.CHOOSE_TXT.getMsg());
			return pageInfo;
		}

		InputStream is = file.getInputStream();
		String path = request.getSession().getServletContext().getRealPath("");
		uploadFilePath = path + File.separator + "temp";
		// 如果不存在该文件夹，则创建
		File tempFileDir = new File(uploadFilePath);
		if (!tempFileDir.exists()) {
			tempFileDir.mkdirs();
		}
		// 如果服务器已经存在和上传文件同名的文件，则输出提示信息
		File tempFile = new File(uploadFilePath + File.separator + fileName);
		if (tempFile.exists()) {
			boolean delResult = tempFile.delete();
		}
		// 开始保存文件到服务器
		if (!fileName.equals("")) {

			FileOutputStream fos = new FileOutputStream(uploadFilePath + File.separator + fileName);

			byte[] buffer = new byte[ResultParame.ResultNumber.EIGHT_ONE_NINETY_TWO.getNumber()]; // 每次读8K字节
			int count = 0;
			// 开始读取上传文件的字节，并将其输出到服务端的上传文件输出流中
			while ((count = is.read(buffer)) > 0) {
				fos.write(buffer, 0, count); // 向服务端文件写入字节流
			}
			fos.close(); // 关闭FileOutputStream对象
			is.close(); // InputStream对象
		}

		File files = new File(uploadFilePath + File.separator + fileName);
		BufferedReader reader = null;
		reader = new BufferedReader(new InputStreamReader(new FileInputStream(files), "gbk"));
		String tempString = null; // 手机号
		int line = 1;
		List<String> list = new ArrayList<String>(); // 未添加的手机号
		while ((tempString = reader.readLine()) != null) {
			// 显示行号
			line++;
			// 判断该手机号格式
			String mobileregex = "(^1(3|4|7|5|8)([0-9]{9}))";
			Pattern regex = Pattern.compile(mobileregex, Pattern.CASE_INSENSITIVE | Pattern.DOTALL);
			Matcher matcher = regex.matcher(tempString);
			if (!matcher.matches()) {
				continue;
			}
			// 判断该手机号是否已经存在
			WinningCountExample example = new WinningCountExample();
			example.createCriteria().andTelephoneEqualTo(tempString);
			long size = winningCountMapper.countByExample(example);
			if (size > 0) {
				continue;
			}
			list.add(tempString);
		}
		// 批量插入
		if (list.size() > 0) {
			winningCountMapper.batchInsertByTelephone(list);
		}
		reader.close();
		pageInfo.setCode(ResultParame.ResultInfo.SUCCESS.getCode());
		return pageInfo;
	}
}
