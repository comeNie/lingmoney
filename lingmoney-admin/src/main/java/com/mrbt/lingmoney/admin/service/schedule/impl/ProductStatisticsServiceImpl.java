package com.mrbt.lingmoney.admin.service.schedule.impl;

import java.io.File;
import java.io.FileOutputStream;
import java.math.BigDecimal;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.mrbt.lingmoney.admin.service.schedule.ProductStatisticsService;
import com.mrbt.lingmoney.mapper.ProductStatisticsMapper;
import com.mrbt.lingmoney.model.ProductStatistics;
import com.mrbt.lingmoney.utils.DateUtils;
import com.mrbt.lingmoney.utils.ResultParame;

/**
 *@author syb
 *@date 2017年5月18日 下午2:38:54
 *@version 1.0
 *@description 
 **/
@Service
public class ProductStatisticsServiceImpl implements ProductStatisticsService {
	@Autowired
	private ProductStatisticsMapper productStatisticsMapper;

	@Override
	public List<ProductStatistics> recommendLineStatistics() {
		Date date = new Date();
		Calendar cd = Calendar.getInstance();
		cd.setTime(date);
		cd.set(Calendar.DATE, cd.get(Calendar.DATE) - 1);
		// 获取所有产品统计记录
		List<ProductStatistics> productStatisticsList = productStatisticsMapper.findProductStatistics();
		for (int i = 0; i < productStatisticsList.size(); i++) {
			ProductStatistics productStatistics = productStatisticsList.get(i);
			productStatistics.setStatisticsDate(cd.getTime());
			// 查询昨日统计记录，计算余额
			ProductStatistics yesterdayProductStatistics = productStatisticsMapper.findYesterdayRecommendLineStatistics(productStatistics
							.getpId());
			// 第一日
			if (yesterdayProductStatistics == null) {
				productStatistics.setYesterdayBalance(new BigDecimal(0));
				// 当日总额为,当日买的金额-当日卖的金额
				productStatistics.setBalance(productStatistics.getRoseMoney().subtract(productStatistics.getDropMoney()));
			} else {
				// 前日记录的总额
				productStatistics.setYesterdayBalance(yesterdayProductStatistics.getBalance());
				// 今日总额是昨日总额+今日买的金额-今日卖的金额
				productStatistics.setBalance(yesterdayProductStatistics.getBalance().add(productStatistics.getRoseMoney())
						.subtract(productStatistics.getDropMoney()));
			}
			productStatisticsMapper.insertSelective(productStatistics);
		}
		return productStatisticsList;
	}

	@Override
	public void exportProductStatistics(List<ProductStatistics> productStatisticsList) {
		Date date = new Date();
		Calendar cd = Calendar.getInstance();
		cd.setTime(date);
		cd.set(Calendar.DATE, cd.get(Calendar.DATE) - 1);

		String currDate = DateUtils.getDtStr(cd.getTime(), "yyyyMMdd");
		String batchStr = "product_excel";
		String fileName = currDate + ".xlsx";
		String fileSavePath = batchStr + File.separator + fileName;
		FileOutputStream fo = null;
		XSSFWorkbook wb = null;
		try {
			File fileRootPath = new File(batchStr);
			if (!fileRootPath.exists()) {
				fileRootPath.mkdirs();
			}
			wb = new XSSFWorkbook();
			XSSFSheet sheet = wb.createSheet("统计");
			sheet.setColumnWidth(ResultParame.ResultNumber.ZERO.getNumber(),
					ResultParame.ResultNumber.TWO_THOUSAND.getNumber());
			sheet.setColumnWidth(ResultParame.ResultNumber.ONE.getNumber(),
					ResultParame.ResultNumber.THREE_THOUSAND_AND_FIVE_HUNDRED.getNumber());
			sheet.setColumnWidth(ResultParame.ResultNumber.TWO.getNumber(),
					ResultParame.ResultNumber.THREE_THOUSAND.getNumber());
			sheet.setColumnWidth(ResultParame.ResultNumber.THREE.getNumber(),
					ResultParame.ResultNumber.THREE_THOUSAND.getNumber());
			sheet.setColumnWidth(ResultParame.ResultNumber.FOUR.getNumber(),
					ResultParame.ResultNumber.FOUR_THOUSAND_AND_FIVE_HUNDRED.getNumber());
			sheet.setColumnWidth(ResultParame.ResultNumber.FIVE.getNumber(),
					ResultParame.ResultNumber.FOUR_THOUSAND_AND_FIVE_HUNDRED.getNumber());
			sheet.setColumnWidth(ResultParame.ResultNumber.SIX.getNumber(),
					ResultParame.ResultNumber.THREE_THOUSAND.getNumber());
			sheet.setColumnWidth(ResultParame.ResultNumber.SEVEN.getNumber(),
					ResultParame.ResultNumber.FIVE_THOUSAND.getNumber());
			XSSFRow row = sheet.createRow((short) ResultParame.ResultNumber.ZERO.getNumber());
			XSSFCell cell = row.createCell((short) ResultParame.ResultNumber.ZERO.getNumber());
			cell.setCellValue("产品编码");
			cell = row.createCell((short) ResultParame.ResultNumber.ONE.getNumber());
			cell.setCellValue("产品名称");
			cell = row.createCell((short) ResultParame.ResultNumber.TWO.getNumber());
			cell.setCellValue("上日余额");
			cell = row.createCell((short) ResultParame.ResultNumber.THREE.getNumber());
			cell.setCellValue("本日增额");
			cell = row.createCell((short) ResultParame.ResultNumber.FOUR.getNumber());
			cell.setCellValue("本日减额");
			cell = row.createCell((short) ResultParame.ResultNumber.FIVE.getNumber());
			cell.setCellValue("本日余额");
			cell = row.createCell((short) ResultParame.ResultNumber.SIX.getNumber());
			cell.setCellValue("当前日期");
			cell = row.createCell((short) ResultParame.ResultNumber.SEVEN.getNumber());
			cell.setCellValue("备注");
			for (int i = 0; i < productStatisticsList.size(); i++) {
				ProductStatistics roductStatistics = productStatisticsList.get(i);
				row = sheet.createRow(i + 1);
				row.createCell((short) ResultParame.ResultNumber.ZERO.getNumber()).setCellValue(
						roductStatistics.getpId());
				row.createCell((short) ResultParame.ResultNumber.ONE.getNumber()).setCellValue(
						roductStatistics.getpName());
				row.createCell((short) ResultParame.ResultNumber.TWO.getNumber()).setCellValue(
						roductStatistics.getYesterdayBalance().toString());
				row.createCell((short) ResultParame.ResultNumber.THREE.getNumber()).setCellValue(
						roductStatistics.getRoseMoney().toString());
				row.createCell((short) ResultParame.ResultNumber.FOUR.getNumber()).setCellValue(
						roductStatistics.getDropMoney().toString());
				row.createCell((short) ResultParame.ResultNumber.FIVE.getNumber()).setCellValue(
						roductStatistics.getBalance().toString());
				row.createCell((short) ResultParame.ResultNumber.SIX.getNumber()).setCellValue(
								DateUtils.getDtStr(roductStatistics
										.getStatisticsDate()));
				row.createCell((short) ResultParame.ResultNumber.SEVEN.getNumber())
						.setCellValue(
						roductStatistics.getRemark());
			}
			fo = new FileOutputStream(new File(fileSavePath));
			wb.write(fo);
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			try {
				if (fo != null) {
					fo.close();
				}
				if (wb != null) {
					wb.close();
				}
			} catch (Exception ex) {
				ex.printStackTrace();
			}
		}
	}

}
