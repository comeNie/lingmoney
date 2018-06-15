package com.mrbt.lingmoney.admin.controller.festival;

import java.io.IOException;
import java.io.OutputStream;
import java.net.URLDecoder;
import java.util.Date;
import java.util.List;
import java.util.Map;

import javax.print.DocFlavor.URL;
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

import com.mrbt.lingmoney.admin.service.festival.FestivalBuyService;
import com.mrbt.lingmoney.utils.DateUtils;
import com.mrbt.lingmoney.utils.PageInfo;
import com.mrbt.lingmoney.utils.ResultParame.ResultInfo;

/** 
 * @author  suyibo 
 * @date 创建时间：2018年1月18日
 */
@Controller
@RequestMapping("festival/festivalBuy")
public class FestivalBuyController {

	@Autowired
	private FestivalBuyService festivalBuyService;

	/**
	 * 查询活动产品购买明细
	 * 
	 * @param vo
	 *            实体类
	 * @return 分页实体类
	 */
	@RequestMapping("festivalBuyDetailList")
	@ResponseBody
	public Object festivalBuyDetailList(String pactName, String startDate,
			String endDate, String activityStartDate, String activityEndDate) {
		PageInfo pageInfo = new PageInfo();
		try {
			pageInfo = festivalBuyService.festivalBuyDetailList(pactName, pageInfo, startDate, endDate,
					activityStartDate, activityEndDate);
		} catch (Exception e) {
			e.printStackTrace();
			pageInfo.setCode(ResultInfo.SERVER_ERROR.getCode());
			pageInfo.setMsg(ResultInfo.SERVER_ERROR.getMsg());
		}
		return pageInfo;
	}

	/**
	 * 导出活动产品购买明细
	 * 
	 * @param pactName
	 *            活动名称
	 * @param startDate
	 *            认购开始时间
	 * @param endDate
	 *            认购结束时间
	 * @param request
	 *            request
	 * @param response
	 *            response
	 */
	@RequestMapping("/exportSellBatchIncomeList")
	public void exportSellBatchIncomeList(String pactName, String startDate, String endDate, HttpServletRequest request,
			HttpServletResponse response, String activityStartDate, String activityEndDate) {
		String currDate = DateUtils.getDtStr(new Date(), "yyyyMMdd");
		// String currDate = "20171220";
		String fileName = currDate + "cash_back.xlsx";
		response.setCharacterEncoding("utf-8");
		response.setContentType("application/vnd.openxmlformats-officedocument.spreadsheetml.sheet");
		response.setHeader("content-disposition", "attachment;filename=\"" + fileName);
		// model存在的路径
		
		pactName = URLDecoder.decode(pactName);
		
		String modelPath = request.getSession().getServletContext().getRealPath("/model/activity_buy_detail.xlsx");
		List<Map<String, Object>> listMap = festivalBuyService.exportFestivalBuyDetailList(pactName, startDate,
				endDate, activityStartDate, activityEndDate);
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
		for (int rownum = 1; rownum <= listMap.size(); rownum++) {
			Map<String, Object> map = listMap.get(rownum - 1);
			Row row = sh.createRow(rownum);
			for (int cellnum = 0; cellnum < lastCellNum; cellnum++) {
				Cell cell = row.createCell(cellnum);
				cell.setCellStyle(cellStyle);
				String value = "";
				if (cellnum == 0) {
					// value = rownum + "";
					value = map.get("usernmae") == null ? "" : String.valueOf(map.get("usernmae"));
				} else if (cellnum == 1) {
					value = map.get("telephone") == null ? "" : String.valueOf(map.get("telephone"));
				} else if (cellnum == 2) {
					value = map.get("ordername") == null ? "" : String.valueOf(map.get("ordername"));
				} else if (cellnum == 3) {
					value = map.get("department") == null ? "" : String.valueOf(map.get("department"));
				} else if (cellnum == 4) {
					value = map.get("productname") == null ? "" : String.valueOf(map.get("productname"));
				} else if (cellnum == 5) {
					value = map.get("ftime") == null ? "" : String.valueOf(map.get("ftime"));
				} else if (cellnum == 6) {
					value = map.get("financialMoney") == null ? "" : String.valueOf(map.get("financialMoney"));
				} else if (cellnum == 7) {
					value = map.get("buyDate") == null ? "" : String.valueOf(map.get("buyDate"));
				} else if (cellnum == 8) {
					value = map.get("moneyForThree") == null ? "" : String.valueOf(map.get("moneyForThree"));
				} else if (cellnum == 9) {
					value = map.get("moneyForSix") == null ? "" : String.valueOf(map.get("moneyForSix"));
				} else if (cellnum == 10) {
					value = map.get("moneyForTwelve") == null ? "" : String.valueOf(map.get("moneyForTwelve"));
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
