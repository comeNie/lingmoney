package com.mrbt.lingmoney.mapper;

import java.util.Date;
import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;

import com.mrbt.lingmoney.model.SellBatch;

public interface SellCustomerMapper {
	/**
	 * 查找固定类产品需要赎回的信息
	 * 
	 * @param platUserNo
	 * @return
	 */
	List<Map<String, Object>> listByFix(Map<String, Object> map);

	/**
	 * 查找随心取赎回的信息
	 * 
	 * @param map
	 * @return
	 */
	List<Map<String, Object>> listTakeHeart(@Param("platUserNo") String platUserNo, @Param("date") Date date);

	/**
	 * 数据库的批量插入,添加batchInfo表
	 * 
	 * @param batchStr
	 * @param platUserNo
	 * @param listInfo
	 * @return
	 */
	int bacthInsert(final List<Map<String, Object>> listInfo);

	/**
	 * 添加batch表
	 * 
	 * @param exp_path
	 * @param batchStr
	 * @param platUserNo
	 * @return
	 */
	int insertBatch(@Param("exp_path") String exp_path, @Param("batchStr") String batchStr,
			@Param("platUserNo") String platUserNo);

	/**
	 * 查找当前是否还有导出的记录
	 * 
	 * @param batch
	 * @return
	 */
	Integer listExcelExport(@Param("batch") String batch, @Param("status") Integer status);

	/**
	 * 更新状态通过批次号
	 * 
	 * @param batch
	 * @param ids
	 * @param inAndError
	 * @return
	 */
	int updateStatusByBatch(@Param("batch") String batch, @Param("ids") String ids,
			@Param("statusDes") Integer statusDes, @Param("statusSrc") Integer statusSrc, @Param("msg") String msg);

	/**
	 * 更新总的批次表的实际金额和平台费用
	 * 
	 * @param sellBatch
	 * @return
	 */
	int updateSellBatch(@Param("sellBatch") SellBatch sellBatch, @Param("statusDes") Integer statusDes,
			@Param("statusSrc") Integer statusSrc);

	/**
	 * 更新详细交易表
	 * 
	 * @param batch
	 * @return
	 */
	int updateFixInfo(@Param("batch") String batch, @Param("statusDes") Integer statusDes,
			@Param("statusSrc") Integer statusSrc);

	/**
	 * 修改随心取赎回的信息状态
	 * 
	 * @param condition
	 * @param batch
	 * @return
	 */
	int updateTakeHeartList(@Param("condition") String condition, @Param("batch") String batch);

	/**
	 * 查询失败的产品id
	 * 
	 * @param batch
	 * @param status
	 * @return
	 */
	List<Integer> listFailPid(@Param("batch") String batch, @Param("status") Integer status);

	/**
	 * 更新状态为还款失败通过失败的pid
	 * 
	 * @param batch
	 * @param ids
	 * @param statusDes=4
	 * @param statusSrc=1
	 * @param msg
	 * @return
	 */
	int updateStatusByFailPid(@Param("batch") String batch, @Param("ids") String ids,
			@Param("statusDes") Integer statusDes, @Param("statusSrc") Integer statusSrc, @Param("msg") String msg);

	/**
	 * 更新状态为还款中通过成功导入的批次
	 * 
	 * @param batch
	 * @param statusDes=2
	 * @param statusSrc=1
	 * @return
	 */
	int updateStatusBySuccessExcel(@Param("batch") String batch, @Param("statusDes") Integer statusDes,
			@Param("statusSrc") Integer statusSrc);

}