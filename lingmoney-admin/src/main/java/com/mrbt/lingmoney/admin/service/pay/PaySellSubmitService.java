package com.mrbt.lingmoney.admin.service.pay;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.Date;
import java.util.List;
import java.util.Map;

import org.apache.ibatis.session.RowBounds;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import com.mrbt.lingmoney.model.SellBatch;
import com.mrbt.lingmoney.model.UsersFinancing;
import com.mrbt.lingmoney.utils.GridPage;
/**
 * 产品赎回确认
 * @author lihq
 * @date 2017年5月18日 下午2:56:51
 * @description 
 * @version 1.0
 * @since 2017-04-12
 */
public interface PaySellSubmitService {

	/**
	 * 查询融资人列表
	 * 
	 * @param vo
	 *            UsersFinancing
	 * @param rowBounds
	 *            分页信息
	 * @return 数据返回
	 */
	GridPage<UsersFinancing> listUsers(UsersFinancing vo, RowBounds rowBounds);

	/**
	 * 查询固定类卖出信息表
	 * 
	 * @param map
	 *            数据包装
	 * @return 数据返回
	 */
	List<Map<String, Object>> listByFix(Map<String, Object> map);

	/**
	 * 导出确认bacth表
	 * 
	 * @param modelPath
	 *            modelPath
	 * @param infoList
	 *            infoList
	 * @param sheetAt
	 *            sheetAt
	 * @param startRow
	 *            startRow
	 * @throws FileNotFoundException
	 *             文件未找到异常
	 * @throws IOException
	 *             io异常
	 * @return 数据返回
	 */
	XSSFWorkbook exportSellBatch(String modelPath, int sheetAt, int startRow, List<Map<String, Object>> infoList)
			throws FileNotFoundException, IOException;

	/**
	 * 按照商户号查找批次
	 * 
	 * @param batchStr
	 *            batchStr
	 * @return 数据异常
	 */
	SellBatch findBatchByBatch(String batchStr);

	/**
	 * 读取确认表
	 * 
	 * @param is
	 *            输入流
	 * @param sheetAt
	 *            sheetAt
	 * @param startRow
	 *            startRow
	 * @throws Exception
	 *             异常
	 * @throws IOException
	 *             异常
	 * @return 数据返回
	 */
	Map<String, Object> impSellBatch(InputStream is, int sheetAt, int startRow) throws Exception;

	/**
	 * 保存batch信息
	 * 
	 * @param expPath
	 *            expPath
	 * @param batchStr
	 *            batchStr
	 * @param platUserNo
	 *            platUserNo
	 * @param infoList
	 *            infoList
	 * @return 数据返回
	 */
	boolean saveBathAndInfo(String expPath, String batchStr, String platUserNo, List<Map<String, Object>> infoList);

	/**
	 * 更新状态
	 * 
	 * @param sellBatch
	 *            sellBatch
	 * @param datalist
	 *            datalist
	 */
	void updateStatus(SellBatch sellBatch, List<Map<String, Object>> datalist);

	/**
	 * 查找随心取赎回的信息
	 * 
	 * @param platUserNo
	 *            platUserNo
	 * @param date
	 *            date
	 * @return 数据返回
	 */
	List<Map<String, Object>> listTakeHeart(String platUserNo, Date date);

	/**
	 * 修改随心取赎回的信息状态
	 * 
	 * @param condition
	 *            condition
	 * @param batch
	 *            batch
	 * @return 数据返回
	 */
	boolean updateTakeHeartList(String condition, String batch);
}
