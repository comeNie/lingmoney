package com.mrbt.lingmoney.admin.controller.excel;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.StringUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import com.mrbt.lingmoney.admin.service.pay.PaySellSubmitService;
import com.mrbt.lingmoney.mapper.UsersRedPacketMapper;
import com.mrbt.lingmoney.model.SellBatch;
import com.mrbt.lingmoney.model.UsersRedPacket;
import com.mrbt.lingmoney.model.UsersRedPacketExample;
import com.mrbt.lingmoney.utils.AppCons;
import com.mrbt.lingmoney.utils.DateUtils;
import com.mrbt.lingmoney.utils.ExcelUtils;
import com.mrbt.lingmoney.utils.ResponseUtils;
import com.mrbt.lingmoney.utils.ResultParame.ResultNumber;

/**
 * 导出导入excel，卖出产品确认
 * 
 * @author lihq
 * @date 2017年5月17日 下午1:39:38
 * @description
 * @version 1.0
 * @since 2017-04-12
 */
@Controller
@RequestMapping("/excel")
public class ExcelController {
	
	private static final Logger LOGGER = LogManager.getLogger(ExcelController.class);
	
	@Autowired
	private PaySellSubmitService paySellSubmitService;
	@Autowired
	private UsersRedPacketMapper usersRedPacketMapper;
	
	private int startRow = ResultNumber.TWO.getNumber(), startSheet = ResultNumber.ZERO.getNumber();

	/**
	 * 导出对账
	 * @param platUserNo	对账用户ID
	 * @param pType	产品类型
	 * @param request	req
	 * @param response	res
	 */
	@RequestMapping("export")
	public void export(String platUserNo, Integer pType, HttpServletRequest request, HttpServletResponse response) {
		response.setCharacterEncoding("utf-8");
		response.setContentType("text/html");
		Date cd = new Date();
		// 判断时间如果小于4点无法结账
		// if (DateUtils.isToDay(cd)) {
		// try {
		// response.getWriter().write("导出错误，未到16点，不能导出。");
		// return;
		// } catch (IOException e) {
		// log.error(e.getMessage());
		// }
		// }

		String currDate = DateUtils.getDtStr(cd, "yyyyMMdd");
		String batchStr = platUserNo + currDate;
		String fileName = "ACCOUNT_" + platUserNo + "_" + currDate + ".xlsx";
		response.setCharacterEncoding("utf-8");
		response.setContentType("application/vnd.openxmlformats-officedocument.spreadsheetml.sheet");
		response.setHeader("content-disposition", "attachment;filename=\"" + fileName);
		SellBatch sellBatch = paySellSubmitService.findBatchByBatch(batchStr);
		String redirectPath = sellBatch != null ? sellBatch.getExpPath() : "";
		if (sellBatch == null) {
			// model存在的路径
			String modelPath = request.getSession().getServletContext().getRealPath("/model/sell_submit_mopdel.xlsx");
			String saveRootPath = request.getSession().getServletContext()
					.getRealPath("/exp_excel/" + platUserNo + "/");
			String fileSavePath = saveRootPath.endsWith(File.separator) ? saveRootPath
					: (saveRootPath + File.separator) + fileName;
			File fileRootPath = new File(saveRootPath);
			if (!fileRootPath.exists()) {
				fileRootPath.mkdirs();
			}
			// FileOutputStream fo = null;
			XSSFWorkbook wb = null;
			try {
				HashMap<String, Object> params = new HashMap<String, Object>();
				params.put("platUserNo", platUserNo);
				params.put("pType", pType);
				List<Map<String, Object>> infoList = paySellSubmitService.listByFix(params);
				if (infoList.size() <= 0) {
					response.getWriter().write("数据错误");
					return;
				}

				// 如果是华兴产品，需要验证是否存在红包
				if (pType == ResultNumber.TWO.getNumber()) {
					LOGGER.info("===============华兴产品，计算红包==================");
					for (Map<String, Object> map : infoList) {
						String uid = (String) map.get("u_id");
						Integer tid = (Integer) map.get("t_id");
						// 查询用户是否有红包/加息券
						UsersRedPacketExample usersRedPacketExmp = new UsersRedPacketExample();
						usersRedPacketExmp.createCriteria().andUIdEqualTo(uid).andTIdEqualTo(tid);
						List<UsersRedPacket> usersRedPacketList = usersRedPacketMapper
								.selectByExample(usersRedPacketExmp);
						if (usersRedPacketList != null && usersRedPacketList.size() > 0) {
							// 加息金额
							UsersRedPacket usersRedPacket = usersRedPacketList.get(0);
							map.put("redPacketMoney", usersRedPacket.getActualAmount());
						}
					}
				}

				wb = paySellSubmitService.exportSellBatch(modelPath, startSheet, startRow, infoList);
				XSSFSheet sheet = wb.getSheetAt(startSheet);
				int lastRow = sheet.getLastRowNum() - ResultNumber.TWO.getNumber(), startCell = sheet.getRow(ResultNumber.TWO.getNumber()).getLastCellNum() - ResultNumber.ONE.getNumber();
				// 添加数据有效性验证
				ExcelUtils.setValidationData(sheet, startRow, lastRow, startCell, startCell, new String[] {"是", "否"});
				sheet.getRow(sheet.getLastRowNum() - 1).getCell(1).setCellValue(batchStr);
				redirectPath = "/exp_excel/" + platUserNo + "/" + fileName;
				// 保存到数据库
				paySellSubmitService.saveBathAndInfo(redirectPath, batchStr, platUserNo, infoList);
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
			} catch (Exception ex) {
				LOGGER.error(ex);
				ex.printStackTrace();
			}
		}
		// try {
		// response.sendRedirect(redirectPath);
		// } catch (IOException e) {
		// LOGGER.error("文件错误：" + e);
		// e.printStackTrace();
		// }
	}

	/**
	 * 导入
	 * @param request	req
	 * @return	返回
	 * @throws Exception	异常
	 */
	@RequestMapping("impExcel")
	public @ResponseBody Object impExcel(MultipartHttpServletRequest request) throws Exception {

		MultipartFile file = request.getFile("excelFile");
		Map<String, Object> reMap = paySellSubmitService.impSellBatch(file.getInputStream(), startSheet, startRow);
		if (reMap == null) {
			return ResponseUtils.failure(ResponseUtils.ERROR_PARAM, "excel读取错误，请重新上传");
		}
		SellBatch sellBatch = paySellSubmitService.findBatchByBatch(reMap.get("batch").toString());
		// 是否存在导出的信息
		if (sellBatch == null) {
			return ResponseUtils.failure(ResponseUtils.ERROR_PARAM, "未找到该批次的信息，批次号：" + reMap.get("batch").toString());
		}
		// 查找batch是否存在
		if (StringUtils.isNotBlank(sellBatch.getImpPath()) && sellBatch.getStatus() != null
				&& sellBatch.getStatus() > AppCons.SELL_SUBMIT_EXCEL_EXPORT) {
			return ResponseUtils.failure(ResponseUtils.ERROR_PARAM, "已经上传");
		}

		String saveRootPath = request.getSession().getServletContext()
				.getRealPath("/imp_excel/" + reMap.get("platUserNo").toString() + "/");
		String fileName = reMap.get("batch").toString() + ".xlsx";
		String fileSavePath = saveRootPath.endsWith(File.separator) ? saveRootPath
				: (saveRootPath + File.separator) + fileName;
		File fileRootPath = new File(saveRootPath);
		if (!fileRootPath.exists()) {
			fileRootPath.mkdirs();
		}
		sellBatch.setImpPath("/imp_excel/" + reMap.get("platUserNo").toString() + "/" + fileName);
		sellBatch.setStatus(AppCons.SELL_SUBMIT_EXCEL_IMPORT);
		paySellSubmitService.updateStatus(sellBatch, (List<Map<String, Object>>) reMap.get("datalist"));
		file.transferTo(new File(fileSavePath));
		return ResponseUtils.success();
	}

	/**
	 * 导出随心取
	 * 
	 * @param platUserNo xx
	 * @param request	req
	 * @param response	res
	 */
	@RequestMapping("exportTakeHeart")
	@ResponseBody
	public void exportTakeHeart(String platUserNo, HttpServletRequest request, HttpServletResponse response) {
		response.setCharacterEncoding("utf-8");
		response.setContentType("text/html");
		Date cd = new Date();
		String redirectPath = "";
		// 判断时间如果小于4点无法结账
		// if (DateUtils.isToDay(cd)) {
		// try {
		// response.getWriter().write("导出错误，未到16点，不能导出。");
		// return;
		// } catch (IOException e) {
		// log.error(e.getMessage());
		// }
		// }

		String currDate = DateUtils.getDtStr(cd, "yyyyMMdd");
		String fileName = "TAKEHEART_" + platUserNo + "_" + currDate + ".xlsx";
		response.setCharacterEncoding("utf-8");
		response.setContentType("application/vnd.openxmlformats-officedocument.spreadsheetml.sheet");
		response.setHeader("content-disposition", "attachment;filename=\"" + fileName);

		// model存在的路径
		String modelPath = request.getSession().getServletContext().getRealPath("/model/sell_take_heart_submit_mopdel.xlsx");
		String saveRootPath = request.getSession().getServletContext().getRealPath("/exp_excel/" + platUserNo + "/");
		String fileSavePath = saveRootPath.endsWith(File.separator) ? saveRootPath : (saveRootPath + File.separator) + fileName;
		File fileRootPath = new File(saveRootPath);
		if (!fileRootPath.exists()) {
			fileRootPath.mkdirs();
		}
		FileOutputStream fo = null;
		XSSFWorkbook wb = null;
		try {

			java.util.Calendar calendar = Calendar.getInstance();

			calendar.set(Calendar.HOUR_OF_DAY, ResultNumber.SIXTEEN.getNumber());
			calendar.set(Calendar.MINUTE, 0);
			calendar.set(Calendar.SECOND, 0);

			List<Map<String, Object>> infoList = paySellSubmitService.listTakeHeart(platUserNo, calendar.getTime());
			if (infoList.size() <= 0) {
				response.getWriter().write("数据错误");
				return;
			}
			wb = paySellSubmitService.exportSellBatch(modelPath, startSheet, startRow, infoList);
			XSSFSheet sheet = wb.getSheetAt(startSheet);
			/*
			 * int lastRow = sheet.getLastRowNum() - 2, startCell = sheet
			 * .getRow(2).getLastCellNum() - 1;
			 */
			// 添加数据有效性验证
			/*
			 * Util.setValidationData(sheet, startRow, lastRow, startCell,
			 * startCell, new String[] { "是", "否" });
			 */
			/*
			 * sheet.getRow(sheet.getLastRowNum() - 1).getCell(1)
			 * .setCellValue(saveRootPath);
			 */
			redirectPath = "/exp_excel/" + platUserNo + "/" + fileName;
			// 保存到数据库
			/*
			 * paySellSubmitService.saveBathAndInfo(redirectPath, batchStr,
			 * platUserNo, infoList);
			 */
			fo = new FileOutputStream(new File(fileSavePath));
			wb.write(fo);
		} catch (Exception ex) {
			LOGGER.error(ex);
		} finally {
			try {
				if (fo != null) {
					fo.close();
				}
				if (wb != null) {
					wb.close();
				}
			} catch (Exception ex) {
				LOGGER.error("资源关闭错误：" + ex);
				ex.printStackTrace();
			}
		}

		try {
			response.sendRedirect(redirectPath);
		} catch (IOException e) {
			LOGGER.error("文件错误：" + e);
			e.printStackTrace();
		}
	}

}
