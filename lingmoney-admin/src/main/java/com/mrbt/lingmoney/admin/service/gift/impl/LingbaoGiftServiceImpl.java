package com.mrbt.lingmoney.admin.service.gift.impl;

import java.awt.image.BufferedImage;
import java.io.IOException;
import java.io.InputStream;
import java.lang.reflect.Field;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.UUID;

import javax.imageio.ImageIO;

import org.apache.commons.beanutils.BeanUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import com.mrbt.lingmoney.admin.service.gift.LingbaoGiftService;
import com.mrbt.lingmoney.mapper.LingbaoGiftMapper;
import com.mrbt.lingmoney.model.LingbaoGift;
import com.mrbt.lingmoney.model.LingbaoGiftExample;
import com.mrbt.lingmoney.model.LingbaoGiftWithBLOBs;
import com.mrbt.lingmoney.utils.FtpUtils;
import com.mrbt.lingmoney.utils.PageInfo;

/**
 * 
 * 领地礼品
 *
 */
@Service
public class LingbaoGiftServiceImpl implements LingbaoGiftService {

	@Autowired
	private LingbaoGiftMapper lingbaoGiftMapper;

	/**
	 * 我的领地礼品图片保存的根目录
	 */
	private String indexPic = "lingbaoGift";
	@Autowired
	private FtpUtils ftpUtils;

	@Override
	public LingbaoGiftWithBLOBs findById(int id) {
		return lingbaoGiftMapper.selectByPrimaryKey(id);
	}

	@Override
	@Transactional
	public void delete(int id) {
		lingbaoGiftMapper.deleteByPrimaryKey(id);
	}

	@Override
	@Transactional()
	public void save(LingbaoGiftWithBLOBs vo) {
		lingbaoGiftMapper.insert(vo);
	}

	@Override
	@Transactional()
	public void updateByPrimaryKeySelective(LingbaoGiftWithBLOBs vo) {
		lingbaoGiftMapper.updateByPrimaryKeySelective(vo);
	}

	@Override
	@Transactional()
	public void updateByPrimaryKey(LingbaoGiftWithBLOBs vo) {
		lingbaoGiftMapper.updateByPrimaryKey(vo);
	}

	/**
	 * 导入excel
	 * 
	 * @author 罗鑫
	 * @param excelFile
	 *            文件流
	 * @param fileName
	 *            文件名
	 * @param userName
	 *            操作人
	 * @throws IOException
	 */
	@Override
	public void importExcel(InputStream excelFile, String fileName,
			String userName) throws Exception {
		boolean is03Excel = fileName.matches("^.+\\.(?i)(xls)$");
		// 1、读取工作簿
		@SuppressWarnings("resource")
		Workbook workbook = is03Excel ? new HSSFWorkbook(excelFile)
				: new XSSFWorkbook(excelFile);
		// 2、读取工作表
		Sheet sheet = workbook.getSheetAt(0);
		// 3、判断是否有数据
		if (sheet.getPhysicalNumberOfRows() > 1) {
			List<LingbaoGift> data = new ArrayList<>();
			String url = ftpUtils.getUrl() + "lingbaoGift/";
			Row row = null;
			Cell cell = null;
			String name = null;
			Object fieldType = null;
			LingbaoGift lingbaoGift = null;
			Entry<String, Object> entry = null;
			List<Entry<String, Object>> list = null;

			Map<String, Object> map = new LinkedHashMap<>();
			Field[] fields = LingbaoGift.class.getDeclaredFields();
			// 遍历行
			for (int i = 1; i < sheet.getPhysicalNumberOfRows(); i++) {
				// 读取行
				row = sheet.getRow(i);
				lingbaoGift = new LingbaoGift();
				for (int k = 0; k < fields.length; k++) {
					name = fields[k].getName();
					if (!name.equals("id") && !name.equals("operateName")
							&& !name.equals("operateTime")) {
						map.put(name, fields[k].getType().getSimpleName());
					}
				}
				list = new ArrayList<>(map.entrySet());
				// 遍历单元格
				for (int j = 0; j < row.getPhysicalNumberOfCells(); j++) {
					cell = row.getCell(j);
					entry = list.get(j);
					if (cell != null) {
						fieldType = entry.getValue();
						if (fieldType.equals("String")) {
							try {
								entry.setValue(cell.getStringCellValue());
							} catch (Exception e) {
								entry.setValue(cell.getNumericCellValue());
							}
						} else if (fieldType.equals("Integer")) {
							entry.setValue(cell.getNumericCellValue());
						} else if (fieldType.equals("Date")) {
							entry.setValue(cell.getDateCellValue());
						} else if (fieldType.equals("BigDecimal")) {
							entry.setValue(BigDecimal.valueOf(cell
									.getNumericCellValue()));
						}
					} else {
						entry.setValue(null);
					}
				}
				BeanUtils.populate(lingbaoGift, map);
				lingbaoGift.setOperateTime(new Date());
				lingbaoGift.setOperateName(userName);

				// 设置图片路径
				lingbaoGift.setPictureBig(url + lingbaoGift.getPictureBig());
				lingbaoGift.setPictureMiddle(url + lingbaoGift.getPictureMiddle());
				lingbaoGift.setPictureSmall(url + lingbaoGift.getPictureSmall());
						
				data.add(lingbaoGift);
			}
			lingbaoGiftMapper.insertBeach(data);
		}
	}

	@Override
	public PageInfo getList(LingbaoGiftExample example) {
		PageInfo pageInfo = new PageInfo();
		int resSize = (int) lingbaoGiftMapper.countByExample(example);
		List<LingbaoGift> list = lingbaoGiftMapper.selectByExampleVo(example);
		pageInfo.setRows(list);
		pageInfo.setTotal(resSize);
		return pageInfo;
	}

	@Override
	public boolean changeShelfStatus(Integer id, String operateName) {
		boolean flag = false;
		if (id != null && id > 0) {
			LingbaoGiftWithBLOBs record = lingbaoGiftMapper.selectByPrimaryKey(id);
			if (record != null) {
				if (record.getShelfStatus() == null || record.getShelfStatus() == 0) {
					record.setShelfStatus(1);
				} else {
					record.setShelfStatus(0);
				}
				record.setOperateName(operateName);
				record.setOperateTime(new Date());
				lingbaoGiftMapper.updateByPrimaryKeySelective(record);
				flag = true;
			}
		}
		return flag;
	}

	@Override
	public boolean changeNewStatus(Integer id, String operateName) {
		boolean flag = false;
		if (id != null && id > 0) {
			LingbaoGiftWithBLOBs record = lingbaoGiftMapper.selectByPrimaryKey(id);
			if (record != null) {
				if (record.getNewStatus() == null || record.getNewStatus() == 0) {
					record.setNewStatus(1);
				} else {
					record.setNewStatus(0);
				}
				record.setOperateName(operateName);
				record.setOperateTime(new Date());
				lingbaoGiftMapper.updateByPrimaryKeySelective(record);
				flag = true;
			}
		}
		return flag;
	}
	@Override
	public boolean changeRecommend(Integer id, String operateName) {
		boolean flag = false;
		if (id != null && id > 0) {
			LingbaoGiftWithBLOBs record = lingbaoGiftMapper.selectByPrimaryKey(id);
			if (record != null) {
				if (record.getRecommend() == null || record.getRecommend() == 0) {
					record.setRecommend(1);
				} else {
					record.setRecommend(0);
				}
				record.setOperateName(operateName);
				record.setOperateTime(new Date());
				lingbaoGiftMapper.updateByPrimaryKeySelective(record);
				flag = true;
			}
		}
		return flag;
	}

	@Override
	public String uploadPicture(MultipartFile file) {
		String url = null;
		try {
			if (file.getSize() > 0) {
				BufferedImage img = ImageIO.read(file.getInputStream());
				if (img != null) {
					String fileName = UUID.randomUUID().toString();
					String type = file.getOriginalFilename().substring(
							file.getOriginalFilename().indexOf("."));
					if (StringUtils.isNotBlank(type)) {
						fileName += type;
					} else {
						fileName += ".jpg";
					}
					ftpUtils.upload(file.getInputStream(), indexPic, fileName);

					url = ftpUtils.getUrl() + indexPic + "/" + fileName;
				}
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
		return url;
	}

}
