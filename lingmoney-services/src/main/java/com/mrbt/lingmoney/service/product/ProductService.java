package com.mrbt.lingmoney.service.product;

import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpSession;

import net.sf.json.JSONObject;

import org.springframework.ui.ModelMap;

import com.mrbt.lingmoney.model.Product;
import com.mrbt.lingmoney.model.ProductDescVo;
import com.mrbt.lingmoney.model.ProductQuery;
import com.mrbt.lingmoney.model.Trading;
import com.mrbt.lingmoney.model.UserFinance;
import com.mrbt.lingmoney.utils.GridPage;
import com.mrbt.lingmoney.utils.PageInfo;

/**
 * 产品
 * 
 * @author lihq
 * @date 2017年4月13日 下午2:46:36
 * @description
 * @version 1.0
 * @since 2017-04-12
 */
public interface ProductService {

	/**
	 * 首页推荐产品
	 * 
	 * @description 1、当用户已注册未投资，可显示新手专享产品
	 *              2、当用户已注册并投资，可显示推荐产品（例如长期主推产品），不显示新手专享产品。（具体应根据运营需求）
	 *              3、当用户未注册（未登录），可显示新手专享 4、点击区域跳转到相应的产品详情页面
	 * @param token token
	 * @param cityCode 城市代码
	 * @param needLogin 是否需要登录
	 * @param type 0 ios  1 android
	 * @return pageinfo
	 */
	PageInfo selectHomeProduct(String token, String cityCode, String needLogin, Integer type);

	/**
	 * 产品列表
	 * 
	 * @description 需求说明：
	 * 
	 *              1、定期产品分为：车贷宝、稳盈宝
	 *              起投金额：100元起投，以100元整数倍递增；5000元起投，以100元整数倍递增（起投额度从数据库读取且可配置）
	 * 
	 *              2、项目期限，根据不同的项目进行配置
	 * 
	 *              3、产品排序规则 1）按照产品发起时间倒叙 2）按照产品是否成立：投资——已成立顺序
	 * 
	 *              4、抢购 点击抢购后，需判断用户是否登录 未登录—登录页面 已登录—理财详情页面
	 * 
	 *              5、默认最多显示十个产品，超出部分显示“加载更多”
	 * @param proId 产品id
	 * @param pageInfo pageinfo
	 * @param status 状态
	 * @param cityCode 城市代码
	 */
	PageInfo selectProductList(Integer proId, PageInfo pageInfo, Integer status, String cityCode);

	/**
	 * 产品详情
	 * 
	 * @description
	 * @param proId 产品id
	 * @return 产品详情列表
	 */
	List<ProductDescVo> selectProductDesc(Integer proId);

	/**
	 * 查询产品详情
	 * 
	 * @param proId 产品id
	 * @return 产品描述
	 */
	String selectProductDescStr(Integer proId);

	/**
	 * 查询项目信息
	 * 
	 * @param proId 差评id
	 * @param clientType
	 *            0PC 1APP
	 * @return 项目信息
	 */
	String selectProductInfo(Integer proId, Integer clientType);

	/**
	 * 产品图片
	 * 
	 * @description 仅限车贷宝
	 * @param proId 产品id
	 * @return 产品图片列表
	 */
	List<String> selectProductPic(Integer proId);

	/**
	 * 单个产品理财记录
	 * 
	 * @description
	 * @param proId 产品id
	 * @param pageInfo pageinfo
	 */
	void selectTradingRecordList(Integer proId, PageInfo pageInfo);

	/**
	 * 产品列表 分页查询
	 * 
	 * @param query query
	 * @return 分页数据
	 */
	GridPage<Product> listGrid(ProductQuery query);

	/**
	 * 包装产品列表页信息
	 * 
	 * @param pageNo 当前页
	 * @param isRecommend 是否推荐
	 * @param cType 产品类型
	 * @param cRate 利率
	 * @param cCycle 投资期限
	 * @param minMenoy 最小投资金额
	 * @param pcStatus 产品状态
	 * @param key 产品名（模糊查询）
	 * @param cityCode 城市代码
	 * @param modelMap modelmap
	 */
	void packageListModel(Integer pageNo, Integer isRecommend, Integer cType, Integer cRate, Integer cCycle,
			Integer minMenoy, Integer pcStatus, String key, String cityCode, ModelMap modelMap);

	/**
	 * 包装产品购买页信息
	 * 
	 * @param modelMap modelmap
	 * @param uid 用户id
	 * @param changTab 选中的tab页
	 * @param pageNo 页码
	 * @param code 产品code
	 * @return 跳转页面
	 */
	String packageProductBuyDetail(ModelMap modelMap, String changTab, Integer pageNo, String code, String uid);

	/**
	 * 根据产品代码查询产品介绍
	 * 
	 * @param code 产品code
	 * @return 产品介绍
	 */
	String queryIntroduction(String code);

	/**
	 * 根据产品代码，和购买金额计算理财金额
	 *
	 * @param code 产品代码
	 * @param buyMoney 购买金额
	 * @return 理财金额
	 */
	double getFinancialMoney(String code, Double buyMoney);

	/**
	 * 查询最新产品（杭州站首页展示）按时间倒序，取第一条数据
	 * 
	 * @return 最新产品信息
	 */
	JSONObject queryNewproduct();

	/**
	 * 处理我的理财
	 * 
	 * @param userFinance 用户理财信息
	 */
	void handleUserFinance(UserFinance userFinance);

	/**
	 * 通过批次号查询最新一条产品的id和code，按ID倒序取第一个
	 * 
	 * @param batch 批次号
	 * @return 产品信息
	 */
	Map<String, Object> getFirstPidAndPcodeByBatch(String batch);

	/**
	 * 赠送螃蟹个数
	 * 
	 * @return 个数
	 */
	int showCrabCount();

	/**
	 * 通过批次号查询指定条数的产品基本信息
	 * @param batch    产品批次号
	 * @param size	查询条数
	 * @return 产品信息列表
	 */
	List<Map<String, Object>> queryProductByBatch(String batch, int size);

	/**
	 * TradingFixInfo
	 * 
	 * @param tid
	 */
	void getTradingFixInfoByTid(Trading trading);

	/**
	 * 京东支付
	 * 
	 * @param i
	 * @param string
	 * @param tId
	 * @param uId
	 * @return
	 */
	PageInfo jdBuyProduct(Integer tId, String uId);

	/**
     * 展示支付结果
     * 
     * @param requestk
     *            request
     * @param modelMap
     *            modelMap
     * @return return
     * @throws Exception
     *             Exception
     */
	String showPayResult(HttpSession session, ModelMap modelMap);
	/**
	 * 产品分类
	 *  @param pageInfo pageInfo
	 */
	void productCatgoryList(PageInfo pageInfo);

    /**
     * 查询新手产品
     * 
     * @author yiban
     * @date 2018年3月8日 下午3:58:13
     * @version 1.0
     * @param pageNo 页码
     * @param pageSize 每页条数
     * @return
     *
     */
    PageInfo listNewerProduct(Integer pageNo, Integer pageSize);

    /**
     * 查询优选产品（所有产品）
     * 排除新手产品
     * @author yiban
     * @date 2018年3月9日 上午9:17:05
     * @version 1.0
     * @param pageSize 每页条数 默认四条
     * @param pageNo 第几页
     * @param status 产品状态 1 运行中（默认） 2 运行中（往期） 如果为空，查询状态1或者2
     * @param startTime 时间段开始（天） 可以为空
     * @param endTime 时间段结束（天） 可以为空
     * @return pageinfo pageinfo
     *
     */
    PageInfo listRecommendProduct(Integer pageNo, Integer pageSize, Integer status, Integer startTime, Integer endTime);

    /**
     * 查询项目详情
     * 
     * @author yiban
     * @date 2018年3月12日 上午9:13:48
     * @version 1.0
     * @param pId 产品id
     * @return pageinfo （项目详情 description、借款人信息 lonnerInfo、常见问题 normalProblem）
     *
     */
    PageInfo getProductDetailInfo(Integer pId);

    /**
     * PC端产品列表
     * @param pageInfo	包装返回，分页
     * @param cityCode	所属城市
     * @param pcId	产品类型
     * @param status	状态
     * @param startTime	产品期限开始
     * @param endTime	产品期间结束
     */
	void queryPcHomeProductList(PageInfo pageInfo, String cityCode, Integer pcId, Integer status, Integer startTime, Integer endTime);

	Product queryBaseInfo(Integer proId);

}
