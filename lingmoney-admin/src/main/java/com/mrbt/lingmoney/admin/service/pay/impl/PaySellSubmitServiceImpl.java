package com.mrbt.lingmoney.admin.service.pay.impl;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.lang.math.NumberUtils;
import org.apache.ibatis.session.RowBounds;
import org.apache.log4j.Logger;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.mrbt.lingmoney.admin.service.pay.PaySellSubmitService;
import com.mrbt.lingmoney.mapper.SellBatchInfoMapper;
import com.mrbt.lingmoney.mapper.SellBatchMapper;
import com.mrbt.lingmoney.mapper.SellCustomerMapper;
import com.mrbt.lingmoney.mapper.UsersFinancingMapper;
import com.mrbt.lingmoney.model.SellBatch;
import com.mrbt.lingmoney.model.SellBatchExample;
import com.mrbt.lingmoney.model.SellBatchInfo;
import com.mrbt.lingmoney.model.SellBatchInfoExample;
import com.mrbt.lingmoney.model.UsersFinancing;
import com.mrbt.lingmoney.model.UsersFinancingExample;
import com.mrbt.lingmoney.utils.AppCons;
import com.mrbt.lingmoney.utils.ExcelUtils;
import com.mrbt.lingmoney.utils.GridPage;
import com.mrbt.lingmoney.utils.MyUtils;
import com.mrbt.lingmoney.utils.ResultParame;

/**
 * 赎回信息
 *
 */
@Service
public class PaySellSubmitServiceImpl implements PaySellSubmitService {

	@Autowired
	private UsersFinancingMapper usersFinancingMapper;
	@Autowired
	private SellCustomerMapper sellCustomerMapper;
	@Autowired
	private SellBatchMapper sellBatchMapper;
	
	@Autowired
	private SellBatchInfoMapper sellBatchInfoMapper;

	/**
	 * 生成辅助类根据实体bean
	 * 
	 * @param vo
	 *            实体bean
	 * @return ProductExample辅助类
	 */
	private UsersFinancingExample createUsersFinancingExample(UsersFinancing vo) {
		UsersFinancingExample example = new UsersFinancingExample();
		UsersFinancingExample.Criteria cri = example.createCriteria();
		if (StringUtils.isNotBlank(vo.getName())) {
			cri.andNameLike("%" + vo.getName() + "%");
		}
		if (vo.getType() != null
				&& (vo.getType() == AppCons.USER_FINANCING_P || vo.getType() == AppCons.USER_FINANCING_E)) {
			cri.andTypeEqualTo(vo.getType());
		}
		cri.andStatusEqualTo(AppCons.USER_FINANCING_STATUS_OK);
		return example;
	}

	@Override
	public GridPage<UsersFinancing> listUsers(UsersFinancing vo, RowBounds page) {

		UsersFinancingExample example = createUsersFinancingExample(vo);
		example.setLimitStart(page.getOffset());
		example.setLimitEnd(page.getLimit());

		GridPage<UsersFinancing> result = new GridPage<UsersFinancing>();
		result.setRows(usersFinancingMapper.selectByExample(example));
		result.setTotal(usersFinancingMapper.countByExample(example));
		return result;
	}

	@Override
	public List<Map<String, Object>> listByFix(Map<String, Object> map) {
		return sellCustomerMapper.listByFix(map);
	}

	@Override
	public XSSFWorkbook exportSellBatch(String modelPath, int sheetAt, int startRow, List<Map<String, Object>> infoList)
			throws FileNotFoundException, IOException {
		File file = new File(modelPath);
		XSSFWorkbook wb = new XSSFWorkbook(new FileInputStream(file));
		XSSFSheet sheet = wb.getSheetAt(sheetAt);

		for (int i = 0; i < infoList.size(); i++) {
			if (i < (infoList.size() - 1)) {
				sheet.shiftRows(startRow + 1, sheet.getLastRowNum(), 1, true, true);
				ExcelUtils.copyRow(wb, sheet, startRow, startRow + 1);
			}
			XSSFRow dataRow = sheet.getRow(startRow);
			int j = 0;
			for (Entry<String, Object> entr : infoList.get(i).entrySet()) {
				dataRow.getCell(j).setCellValue(entr.getValue() != null ? entr.getValue().toString() : "");
				j++;
				if (j >= ResultParame.ResultNumber.FOURTEEN.getNumber()) {
					break;
				}
			}
			startRow++;
		}
		return wb;
	}

	@Override
	public SellBatch findBatchByBatch(String batchStr) {
		SellBatchExample example = new SellBatchExample();
		SellBatchExample.Criteria cri = example.createCriteria();
		cri.andBatchEqualTo(batchStr);
		List<SellBatch> list = sellBatchMapper.selectByExample(example);
		if (list.size() > 0) {
			return list.get(0);
		}
		return null;
	}

	@Override
	public Map<String, Object> impSellBatch(InputStream is, int sheetAt, int startRow) throws Exception {
		Logger log = MyUtils.getLogger(PaySellSubmitServiceImpl.class);
		HashMap<String, Object> reMap = null;
		List<Map<String, Object>> list = new ArrayList<Map<String, Object>>();
		XSSFWorkbook wb = null;
		try {
			wb = new XSSFWorkbook(is);
			reMap = new HashMap<String, Object>();
			String platUserNo = null;
			XSSFSheet sheet = wb.getSheetAt(sheetAt);
			if (sheet != null) {
				for (int i = startRow; i <= sheet.getLastRowNum() - ResultParame.ResultNumber.TWO.getNumber(); i++) {
					HashMap<String, Object> map = new HashMap<String, Object>();
					XSSFRow row = sheet.getRow(i);
					String id = row.getCell(0).getRawValue();

					String sellSubmit = row.getCell(row.getLastCellNum() - ResultParame.ResultNumber.ONE.getNumber())
							.getStringCellValue();
					platUserNo = row.getCell(ResultParame.ResultNumber.TWELVE.getNumber()).getStringCellValue();
					// 验证确认信息和id是否有值
					if (StringUtils.isBlank(sellSubmit) || StringUtils.isBlank(id) || StringUtils.isBlank(platUserNo)
							|| !NumberUtils.isNumber(id)) {
						list.clear();
						reMap = null;
						break;
					}
					// 确认信息错误
					if (!sellSubmit.equals("是") && !sellSubmit.equals("否")) {
						list.clear();
						reMap = null;
						break;
					}
					map.put("id", row.getCell(0).getStringCellValue());
					map.put("sell_submit", sellSubmit);
					list.add(map);
				}
			}

			String batch = sheet.getRow(sheet.getLastRowNum() - 1).getCell(1).getStringCellValue();

			if (StringUtils.isBlank(batch)) {
				list.clear();
				reMap = null;
			}

			if (reMap != null) {
				reMap.put("datalist", list);
				reMap.put("batch", batch);
				reMap.put("platUserNo", platUserNo);
			}
			
		} catch (Exception ex) {
			log.error("读取导入的excel错误：" + ex);
			throw ex;
		} finally {
			try {
				if (wb != null) {
					wb.close();
				}
			} catch (Exception ex) {
				log.error("关闭excel错误：" + ex);
			}
		}

		return reMap;
	}

	@Override
	@Transactional
	public boolean saveBathAndInfo(String expPath, String batchStr, String platUserNo,
			List<Map<String, Object>> infoList) {
		for (Map<String, Object> map : infoList) {
			map.put("batchStr", batchStr);
			map.put("platUserNo", platUserNo);
		}
		int ok = sellCustomerMapper.bacthInsert(infoList);
		if (ok <= 0) {
			throw new RuntimeException("批量插入错误");
		}
		ok = sellCustomerMapper.insertBatch(expPath, batchStr, platUserNo);
		return true;
	}

	@Override
	@Transactional
	public void updateStatus(SellBatch sellBatch, List<Map<String, Object>> datalist) {
		List<Integer> ok = new ArrayList<Integer>();
		List<Integer> fail = new ArrayList<Integer>();
		for (Map<String, Object> map : datalist) {
			if (map.get("sell_submit").toString().equals("是")) {
				ok.add(Integer.parseInt(map.get("id").toString()));
			} else {
				fail.add(Integer.parseInt(map.get("id").toString()));
			}
		}
		if (ok.size() > 0) {
			updateStatusByBatch(sellBatch.getBatch(), ok, true);
		}
		if (fail.size() > 0) {
			updateStatusByBatch(sellBatch.getBatch(), fail, false);
		}
		if (sellCustomerMapper.listExcelExport(sellBatch.getBatch(), AppCons.SELL_SUBMIT_EXCEL_EXPORT) > 0) {
			throw new RuntimeException("导入的数据量与导出的数据量不符，请重新导入");
		}
		if (!updateStatusByProduct(sellBatch.getBatch())) {
			throw new RuntimeException("更新产品状态错误，请重新导入");
		}
		if (sellCustomerMapper.updateSellBatch(sellBatch, AppCons.SELL_SUBMIT_RETURN_PROCESS,
				AppCons.SELL_SUBMIT_RETURN_PROCESS) <= 0) {
			throw new RuntimeException("更新批次表状态错误，请重新导入");
		}
		if (sellCustomerMapper.updateFixInfo(sellBatch.getBatch(), AppCons.SELL_SUBMIT_EXCEL_IMPORT,
				AppCons.SELL_SUBMIT_RETURN_PROCESS) <= 0) {
			throw new RuntimeException("更新交易表状态错误，请重新导入");
		}
	}

	/**
	 * updateStatusByBatch
	 * 
	 * @param batch
	 *            batch
	 * @param ids
	 *            ids
	 * @param inAndError
	 *            inAndError
	 * @return 数据返回
	 * 
	 * update
		sell_batch_info set status = #{statusDes},msg=#{msg} where info_id in
		(#{ids}) and batch=#{batch} and status=#{statusSrc}
		
		int updateStatusByBatch(@Param("batch") String batch, @Param("ids") String ids,
			@Param("statusDes") Integer statusDes, @Param("statusSrc") Integer statusSrc, @Param("msg") String msg);
	 */
	public boolean updateStatusByBatch(String batch, List<Integer> ids, boolean inAndError) {
		
		
		SellBatchInfoExample example = new SellBatchInfoExample();
		example.createCriteria().andBatchEqualTo(batch).andStatusEqualTo(0).andInfoIdIn(ids);
		
		SellBatchInfo sellBatchInfo = new SellBatchInfo();
		sellBatchInfo.setMsg("");
		
		if (inAndError) {
			sellBatchInfo.setStatus(AppCons.SELL_SUBMIT_EXCEL_IMPORT);
			sellBatchInfo.setMsg("");
		} else {
			sellBatchInfo.setStatus(AppCons.SELL_SUBMIT_RETURN_FAIL);
			sellBatchInfo.setMsg(AppCons.SELL_SUBMIT_RETURN_FAIL_MSG);
		}
		
		int ok = sellBatchInfoMapper.updateByExampleSelective(sellBatchInfo, example);
		
//		int ok = sellCustomerMapper.updateStatusByBatch(batch,
//				Arrays.deepToString(ids.toArray()).replace("[", " ").replace("]", " "),
//				inAndError ? AppCons.SELL_SUBMIT_EXCEL_IMPORT : AppCons.SELL_SUBMIT_RETURN_FAIL,
//				AppCons.SELL_SUBMIT_EXCEL_EXPORT, inAndError ? "" : AppCons.SELL_SUBMIT_RETURN_FAIL_MSG);
		if (ok > 0) {
			return true;
		}
		return false;
	}

	/**
	 * 根据产品信息，修改状态值
	 * 
	 * @param batch
	 *            batch
	 * @return 数据返回
	 */
	public boolean updateStatusByProduct(final String batch) {
		final List<Integer> failPid = sellCustomerMapper.listFailPid(batch, AppCons.SELL_SUBMIT_RETURN_FAIL);
		if (failPid.size() > 0) {
			sellCustomerMapper.updateStatusByFailPid(batch,
					Arrays.deepToString(failPid.toArray()).replace("[", " ").replace("]", " "),
					AppCons.SELL_SUBMIT_RETURN_FAIL, AppCons.SELL_SUBMIT_EXCEL_IMPORT,
					AppCons.SELL_SUBMIT_RETURN_FAIL_MSG);
		}
		return sellCustomerMapper.updateStatusBySuccessExcel(batch, AppCons.SELL_SUBMIT_RETURN_PROCESS,
				AppCons.SELL_SUBMIT_EXCEL_IMPORT) > 0 ? true : false;
	}

	@Override
	public List<Map<String, Object>> listTakeHeart(String platUserNo, Date date) {
		return sellCustomerMapper.listTakeHeart(platUserNo, date);
	}

	@Override
	@Transactional
	public boolean updateTakeHeartList(String condition, String batch) {
		return sellCustomerMapper.updateTakeHeartList(condition, batch) > 0 ? true : false;
	}

}
