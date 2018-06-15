package com.mrbt.lingmoney.mapper;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

import com.mrbt.lingmoney.model.Product;
import com.mrbt.lingmoney.model.ProductCustomer;
import com.mrbt.lingmoney.model.ProductExample;
import com.mrbt.lingmoney.model.ProductQuery;
import com.mrbt.lingmoney.model.ProductRecommendCustomer;
import com.mrbt.lingmoney.model.ProductWithBLOBs;
import com.mrbt.lingmoney.model.TakeheartTransactionFlow;
import com.mrbt.lingmoney.model.TakeheartTransactionFlowVo;
import com.mrbt.lingmoney.model.Trading;
import com.mrbt.lingmoney.model.TradingRecordCustomer;
import com.mrbt.lingmoney.model.webView.ProductView;
import com.mrbt.lingmoney.utils.PageInfo;

/**
 * 
 * @author lihq
 * @date 2017年4月24日 上午11:37:02
 * @description
 * @version 1.0
 * @since 2017-04-12
 */
public interface ProductCustomerMapper {

	/**
	 * 查询礼品详情
	 * 
	 * @param userState
	 *            0查询新手特享 1查询首页推荐
	 * @return
	 */
	List<ProductCustomer> selectHomeProduct(Map<String, Object> map);

	/**
	 * 我要理财产品列表
	 * 
	 * @description
	 * @param pageInfo
	 * @return
	 */
	List<ProductCustomer> selectProductList(PageInfo pageInfo);

	/**
	 * 我要理财产品个数
	 * 
	 * @description
	 * @param pageInfo
	 * @return
	 */
	Integer selectProductCount(PageInfo pageInfo);

	/**
	 * 单个产品理财记录
	 * 
	 * @description
	 * @param pageInfo
	 * @return
	 */
	List<TradingRecordCustomer> selectTradingRecordList(PageInfo pageInfo);

	/**
	 * 单个产品理财记录个数
	 * 
	 * @description
	 * @param pageInfo
	 * @return
	 */
	Integer selectTradingRecordCount(PageInfo pageInfo);

	/**
	 * 查询满足条件的最后一条交易记录
	 * 
	 * @param map
	 * @return
	 */
	Trading selectLastTrading(Map<String, Object> map);

	/**
	 * 更新产品为运行状态，并更新详细交易表中的结息日
	 * 
	 * @param map
	 * @return
	 */
	int updateProductByRun(Map<String, Object> map);

	/**
	 * 根据产品代码查询
	 * 
	 * @param prCode
	 * @return
	 */
	Product selectByCode(String prCode);

	/**
	 * 根据产品代码查询
	 * 
	 * @param prCode
	 * @return
	 */
	ProductWithBLOBs selectByCodeWithBlob(String prCode);

	/**
	 * 根据ID查询 （无BLOB数据）
	 * 
	 * @param id
	 * @return
	 */
	Product selectProductByPrimaryKey(Integer id);

	/**
	 * 交易后更新产品信息，已购金额和募集金额
	 * 
	 * @param trading
	 * @return
	 */
	int updateProductAfterTrading(Trading trading);

	/**
	 * 产品列表页查询
	 * 
	 * @param productQuery
	 * @return
	 */
	List<Product> selectProductListWeb(ProductQuery productQuery);

	int selectProductListWebCount(ProductQuery productQuery);

	/**
	 * 根据产品代码查询
	 * 
	 * @param prCode
	 * @return 包括产品推荐，介绍
	 */
	ProductView selectProductViewByCode(String code);

	List<Product> selectByFixType(ProductExample example);

	List<Product> selectByFloatType(ProductExample example);

	/**
	 * 查询产品推荐
	 * 
	 * @return
	 */
	List<Product> selectByCondition();

	/**
	 * 查询产品推荐个数
	 * 
	 * @return
	 */
	List<Product> getCondition();

	/**
	 * 获取今天需要成立的产品 rule != 3 & end_dt < sysdate & status = 1
	 * 
	 * @return
	 */
	List<Product> getNnSetUpProduct();

	/**
	 * 更新产品已购金额
	 * 
	 * @param map
	 */
	void updateProductReachMoney(Map<String, Object> map);

	/**
	 * 修改成品为结束状态
	 * 
	 * @param pId
	 */
	int updateProductEndStatus(int pId);

	/**
	 * 查询明星产品
	 * 
	 * @param cityCode
	 * @return
	 */
	List<Product> selectProductByIndexX(String cityCode);

	/**
	 * 查询产品系列
	 * 
	 * @param cityCode
	 * @return
	 */
	List<Product> selectProductByIndexY(String cityCode);

	/**
	 * 后台查询产品推荐列表
	 * 
	 * @param map
	 * @return
	 */
	List<ProductRecommendCustomer> selectProductRecommendList(Map<String, Object> map);

	/**
	 * 后台查询产品推荐列表个数
	 * 
	 * @param map
	 * @return
	 */
	long selectProductRecommendListCount(Map<String, Object> map);

	/**
	 * 后台查询pc端明星产品
	 * 
	 * @return
	 */
	List<Product> selectProductByIndexXadmin();

	/**
	 * 后台查询pc端产品系列
	 * 
	 * @return
	 */
	List<Product> selectProductByIndexYadmin();

	/**
	 * 查询approval=1(已提交审核)status=1(募集中)根据batch(批次号)分组的List<String>，所有需要审核通过的批次
	 * 
	 * @return
	 */
	List<String> queryNeedToApprovalBatch();

	/**
	 * 根据批次号查询已发布approval=2(审核通过)status=1(募集中)的产品个数
	 * 
	 * @param batch
	 * @return
	 */
	int queryAlreadyApprovalProCount(String batch);

	/**
	 * 根据批次号查询approval=1(已提交审核)status=1(募集中)的第一个产品id
	 * 
	 * @param batch
	 * @return
	 */
	Integer queryNeedToApprovalFirstPid(String batch);

	/**
	 * 查询随心取交易流水
	 * 
	 * @param pageInfo
	 * @return
	 */
	List<TakeheartTransactionFlow> queryTakeheartTransactionFlow(PageInfo pageInfo);

	/**
	 * 随心取交易流水记录数
	 * 
	 * @param pageInfo
	 * @return
	 */
	int queryTakeheartTransactionFlowCount(PageInfo pageInfo);

	/**
	 * 根据年月查询随心取交易流水结果类，如果年月为空则是查全部
	 * 
	 * @param map
	 * @return
	 */
	List<TakeheartTransactionFlowVo> queryVoByYearmonth(Map<String, Object> map);

	/**
	 * 根据年月查询随心取交易流水
	 * 
	 * @param map
	 * @return
	 */
	List<TakeheartTransactionFlow> queryByYearmonth(Map<String, Object> map);

	/**
	 * 根据年月查询当月新增理财金额
	 * 
	 * @param map
	 * @return
	 */
	BigDecimal queryMoneyByYearmonth(Map<String, Object> map);

	/**
	 * 通过批次号查询最新一条产品的id和code，按ID倒序取第一个
	 * 
	 * @param batch
	 * @return
	 */
	Map<String, Object> getFirstPidAndPcodeByBatch(String batch);


	/**
	 * 通过批次号查询指定条数的产品基本信息
	 * @param batch    产品批次号
	 * @param size	查询条数
	 * @return
	 */
	List<Map<String, Object>> queryProductByBatch(Map<String, Object> resMap);

    /**
     * 根据条件查询优选产品（推荐产品）
     * 查询排序规则：产品状态（status）正序, 排序（sort）正序, 发行时间（st_dt）正序
     * 
     * @author yiban
     * @date 2018年3月9日 上午10:18:42
     * @version 1.0
     * @param paramMap 
     * start 开始条数， number 每页条数
     * 期限（天）：startTime - endTime 可为空; status 状态 可空
     * @return map
     *
     */
    List<Map<String, Object>> listRecommendProduct(Map<String, Object> paramMap);

    /**
     * 统计 根据条件查询优选产品（推荐产品）
     * 
     * @author yiban
     * @date 2018年3月9日 上午10:19:45
     * @version 1.0
     * @param paramMap
     * 期限（天）：startTime - endTime; status 状态
     * @return integer
     *
     */
    Integer countListRecommendProduct(Map<String, Object> paramMap);

}
