package com.mrbt.lingmoney.admin.controller.userincome;

import java.io.IOException;
import java.io.OutputStream;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.HorizontalAlignment;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.xssf.usermodel.XSSFCellStyle;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.mrbt.lingmoney.admin.service.userincome.SellBatchIncomeService;
import com.mrbt.lingmoney.utils.DateUtils;
import com.mrbt.lingmoney.utils.PageInfo;
import com.mrbt.lingmoney.utils.ResultParame.ResultInfo;
import com.mrbt.lingmoney.utils.ResultParame.ResultNumber;

/** 
 * @author  suyibo 
 * @date 创建时间：2017年12月19日
 */
@Controller
@RequestMapping("/sellBatchIncome")
public class SellBatchIncomeController {
	@Autowired
	private SellBatchIncomeService sellBatchIncomeService;

	private SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");

	/**
	 * 查询用户回签列表
	 * 
	 * @param sellDate
	 *            兑付时间
	 * @param dayCount
	 *            回签天数
	 * @return return 
	 */
	@RequestMapping("/findSellBatchIncomeList")
	public @ResponseBody Object findUserSellBatchIncome(String sellDate, Integer dayCount) {
		PageInfo pageInfo = new PageInfo();
		Date sellTime = null;
		try {
			if (null != sellDate) {
				sellTime = sdf.parse(sellDate);
			} else {
				long beforeDate = sdf.parse(DateUtils.giveDay(ResultNumber.MINUS_ONE.getNumber())).getTime(); // 前一天凌晨时间戳
				sellTime = sdf.parse(sdf.format(beforeDate)); // 前一天日期
			}
			Date nowDate = new Date();
			if (sellTime.getTime() > nowDate.getTime()) {
				pageInfo.setCode(ResultInfo.PARAMS_ERROR.getCode());
				pageInfo.setMsg("所选时间不得大于今天");
			} else {
				try {
					sellBatchIncomeService.findSellBatchIncomeList(sellTime, dayCount, pageInfo);
					pageInfo.setCode(ResultInfo.SUCCESS.getCode());
					pageInfo.setMsg(ResultInfo.SUCCESS.getMsg());
				} catch (Exception e) {
					e.printStackTrace();
					pageInfo.setCode(ResultInfo.SERVER_ERROR.getCode());
					pageInfo.setMsg(ResultInfo.SERVER_ERROR.getMsg());
				}
			}
		} catch (ParseException e1) {
			e1.printStackTrace();
		}


		return pageInfo;
	}

	/**
	 * 导出兑付回签列表
	 * 
	 * @param sellDate
	 *            兑付时间
	 * @param dayCount
	 *            回签天数
	 * @param request request
	 * @param response response
	 */
	@RequestMapping("/exportSellBatchIncomeList")
	public void exportSellBatchIncomeList(String sellDate, Integer dayCount, HttpServletRequest request,
			HttpServletResponse response) {
		Date sellTime = new Date();
		try {
			if (null != sellDate) {
				sellTime = sdf.parse(sellDate);
			} else {
				long beforeDate = sdf.parse(DateUtils.giveDay(ResultNumber.MINUS_ONE.getNumber())).getTime(); // 前一天凌晨时间戳
				sellTime = sdf.parse(sdf.format(beforeDate)); // 前一天日期
			}
		} catch (ParseException e1) {
			e1.printStackTrace();
		}
		String currDate = DateUtils.getDtStr(sellTime, "yyyyMMdd");
		// String currDate = "20171220";
		String fileName = currDate + "cash_back.xlsx";
		response.setCharacterEncoding("utf-8");
		response.setContentType("application/vnd.openxmlformats-officedocument.spreadsheetml.sheet");
		response.setHeader("content-disposition", "attachment;filename=\"" + fileName);
		// model存在的路径
		String modelPath = request.getSession().getServletContext().getRealPath("/model/sell_batch_income.xlsx");
		// FileSystemView fsv = FileSystemView.getFileSystemView();
		// File com = fsv.getHomeDirectory(); // 获取桌面路径
		// String createFile = com.getPath() + "/" + fileName;
		List<Map<String, Object>> listMap = sellBatchIncomeService.sellBatchIncomeList(sellTime, dayCount);
		XSSFWorkbook wb = null;
		try {
			wb = new XSSFWorkbook(modelPath);
		} catch (IOException e1) {
			e1.printStackTrace();
		}
		XSSFCellStyle cellStyle = wb.createCellStyle();
		cellStyle.setWrapText(true);
		cellStyle.setBorderBottom(XSSFCellStyle.BORDER_THIN); // 下边框
		cellStyle.setBorderLeft(XSSFCellStyle.BORDER_THIN); // 左边框
		cellStyle.setBorderTop(XSSFCellStyle.BORDER_THIN); // 上边框
		cellStyle.setBorderRight(XSSFCellStyle.BORDER_THIN); // 右边框
		cellStyle.setAlignment(XSSFCellStyle.ALIGN_CENTER);
		cellStyle.setAlignment(HorizontalAlignment.LEFT);
		// 读取excel模板
		Sheet sh = wb.getSheetAt(0);
		int lastCellNum = sh.getRow(0).getPhysicalNumberOfCells();
		for (int rownum = 2; rownum <= listMap.size() + 1; rownum++) {
			Map<String, Object> map = listMap.get(rownum - 2);
			Row row = sh.createRow(rownum);
			for (int cellnum = 0; cellnum < lastCellNum; cellnum++) {
				Cell cell = row.createCell(cellnum);
				cell.setCellStyle(cellStyle);
				String value = "";
				if (cellnum == 0) {
					// value = rownum + "";
					value = map.get("sellDate") == null ? "" : String.valueOf(map.get("sellDate"));
				} else if (cellnum == 1) {
					value = map.get("sellMoney") == null ? "" : String.valueOf(map.get("sellMoney"));
				} else if (cellnum == 2) {
					value = map.get("username") == null ? "" : String.valueOf(map.get("username"));
				} else if (cellnum == 3) {
					value = map.get("phoneNumber") == null ? "" : String.valueOf(map.get("phoneNumber"));
				} else if (cellnum == 4) {
					value = map.get("orderName") == null ? "" : String.valueOf(map.get("orderName"));
				} else if (cellnum == 5) {
					value = map.get("orderPhone") == null ? "" : String.valueOf(map.get("orderPhone"));
				} else if (cellnum == 6) {
					value = map.get("orderOrg") == null ? "" : String.valueOf(map.get("orderOrg"));
				} else if (cellnum == 7) {
					value = map.get("orderCompany") == null ? "" : String.valueOf(map.get("orderCompany"));
				} else if (cellnum == 8) {
					value = map.get("oneMoney") == null ? "" : String.valueOf(map.get("oneMoney"));
				} else if (cellnum == 9) {
					value = map.get("twoMoney") == null ? "" : String.valueOf(map.get("twoMoney"));
				} else if (cellnum == 10) {
					value = map.get("threeMoney") == null ? "" : String.valueOf(map.get("threeMoney"));
				} else if (cellnum == 11) {
					value = map.get("fourMoney") == null ? "" : String.valueOf(map.get("fourMoney"));
				} else if (cellnum == 12) {
					value = map.get("fiveMoney") == null ? "" : String.valueOf(map.get("fiveMoney"));
				} else if (cellnum == 13) {
					value = map.get("incomeDate") == null ? "" : String.valueOf(map.get("incomeDate"));
				} else if (cellnum == 14) {
					value = map.get("changeMoney") == null ? "" : String.valueOf(map.get("changeMoney"));
				} else if (cellnum == 15) {
					value = map.get("incomeEfficiency") == null ? "" : String.valueOf(map.get("incomeEfficiency"));
				}
				cell.setCellValue(value);
			}

		}

		OutputStream out = null;
		try {
			try {
				out = response.getOutputStream();
				wb.write(out);
			} catch (IOException e1) {
				e1.printStackTrace();
			}
		} finally {
			try {
				// out.flush();
				out.close();
			} catch (IOException e) {
				e.printStackTrace();
			}

		}

	}

}
