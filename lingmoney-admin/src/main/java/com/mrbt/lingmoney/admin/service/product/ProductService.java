package com.mrbt.lingmoney.admin.service.product;

import java.io.IOException;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.apache.ibatis.session.RowBounds;

import com.mrbt.lingmoney.model.Product;
import com.mrbt.lingmoney.model.ProductWithBLOBs;
import com.mrbt.lingmoney.utils.GridPage;
import com.mrbt.lingmoney.utils.PageInfo;

/**
 * 
 * 产品设置——》产品表
 *
 */
public interface ProductService {

	/**
	 * 查询今天成立的产品
	 * 
	 * @return 数据返回
	 */
	List<Product> getNnSetUpProduct();

	/**
	 * 生成datagrid表格需要的结果
	 * 
	 * @param vo
	 *            Product实体bean
	 * @param rowBounds
	 *            翻页信息
	 * @return GridPage<Product>
	 */
	GridPage<Product> listGrid(Product vo, RowBounds rowBounds);

	/**
	 * 查找，根据Product
	 * 
	 * @param fIXFLAG
	 *            固定类和浮动类标志
	 * @param record
	 *            Product
	 * @return Product的集合
	 */
	List<Product> listType(int fIXFLAG, Product record);

	/**
	 * 保存产品 初始化产品状态status为0（AppCons.PRODUCT_STATUS_INIT）
	 * 审批状态approval为0（AppCons.PRODUCT_APPROVAL_INIT）
	 * 
	 * @param vo
	 *            ProductWithBLOBs
	 * @param pageInfo
	 *            包装
	 */
	void save(ProductWithBLOBs vo, PageInfo pageInfo);

	/**
	 * 更新产品
	 * 
	 * @param vo
	 *            ProductWithBLOBs
	 * @param pageInfo
	 *            包装
	 */
	void update(ProductWithBLOBs vo, PageInfo pageInfo);

	/**
	 * 复制产品
	 * 
	 * @param vo
	 *            ProductWithBLOBs
	 * @param pageInfo
	 *            包装
	 */
	void copy(ProductWithBLOBs vo, PageInfo pageInfo);

	/**
	 * 
	 * @param id
	 *            id
	 * @return 数据返回
	 */
	List<String> getBlobContent(int id);

	/**
	 * 删除产品
	 * 
	 * @param id
	 *            id
	 * @return 数据返回
	 */
	int deleteProduct(int id);

	/**
	 * 产品提交
	 * 
	 * @param id
	 *            id
	 * @param session
	 *            session
	 * @param request
	 *            请求
	 * @return 返回数据
	 * @throws IOException
	 *             异常
	 */
	int updateApproval(int id, HttpSession session, HttpServletRequest request) throws IOException;

	/**
	 * 查询产品描述
	 * 
	 * @param parseInt
	 *            parseInt
	 * @return 数据返回
	 */
	String listDescription(int parseInt);

	/**
	 * 项目成立
	 * 
	 * @param id
	 *            id
	 * @return 数据返回
	 */
	int raiseOpertion(int id);

	/**
	 * 查询产品推荐表可用产品
	 * 
	 * @param pageInfo
	 *            PageInfo
	 */
	void selectProductList(PageInfo pageInfo);

	/**
	 * 查询活动产品列
	 * 
	 * @param product
	 *            Product
	 * @return 数据返回
	 */
	List<Product> selectByActivity(Product product);

	/**
	 * 根据主键查找
	 * 
	 * @param id
	 *            id
	 * @return 数据返回
	 */
	Product findByPk(int id);

	/**
	 * 计算领宝个数
	 * 
	 * @param buyMoney
	 *            金额
	 * @param unitTime
	 *            unitTime
	 * @param number
	 *            number
	 * @return 数据返回
	 */
	int getLingBaoNum(double buyMoney, int unitTime, int number);

	/**
	 * 分页查询
	 * 
	 * @param vo
	 *            Product
	 * @param pageInfo
	 *            PageInfo
	 * @return 数据返回
	 */
	PageInfo getList(Product vo, PageInfo pageInfo);

	/**
	 * 修改产品大字段
	 * 
	 * @param pId
	 *            pId
	 * @param vo
	 *            ProductWithBLOBs
	 * @param type
	 *            type
	 * @param pageInfo
	 *            PageInfo
	 */
	void saveDetail(Integer pId, ProductWithBLOBs vo, Integer type, PageInfo pageInfo);

	/**
	 * 修改产品详情
	 * 
	 * @param id
	 *            id
	 * @param title1
	 *            title1
	 * @param content1
	 *            content1
	 * @param title2
	 *            title2
	 * @param content2
	 *            content2
	 * @param title3
	 *            title3
	 * @param content3
	 *            content3
	 * @param title4
	 *            title4
	 * @param content4
	 *            content4
	 * @param title5
	 *            title5
	 * @param content5
	 *            content5
	 * @param title6
	 *            title6
	 * @param content6
	 *            content6
	 * @param title7
	 *            title7
	 * @param content7
	 *            content7
	 * @param title8
	 *            title8
	 * @param content8
	 *            content8
	 * @param description
	 *            description
	 * @param insertType
	 *            insertType
	 */
	void saveDesc(Integer id, String title1, String content1, String title2, String content2, String title3,
			String content3, String title4, String content4, String title5, String content5, String title6,
			String content6, String title7, String content7, String title8, String content8, String description,
			Integer insertType);

	/**
	 * 查询到期自动还款产品
	 * 
	 * @param pageNo
	 *            当前页数
	 * @param pageSize
	 *            行数
	 * @return 数据返回
	 */
	PageInfo listAutoRepaymentProduct(Integer pageNo, Integer pageSize);

	/**
	 * 自动还款产品确认/取消确认
	 * 
	 * @param productIds
	 *            产品id，多个用逗号分割
	 * @param status
	 *            状态
	 * @return 数据返回
	 */
	PageInfo productAutoRepaymentConfirm(String productIds, Integer status);

    /**
	 * 产品排序
	 * 
	 * @author yiban
	 * @date 2017年11月6日 下午2:33:02
	 * @version 1.0
	 * @param id
	 *            id
	 * @param sort
	 *            序号，从小到大 最大99
	 * @return 数据返回
	 *
	 */
    PageInfo sortProduct(Integer id, Byte sort);

	/**
	 * 产品募集列表
	 * 
	 * @return
	 */
	PageInfo festivalBuyDetailList();

    /**
     * 修改项目详情
     * 
     * @author yiban
     * @date 2018年3月13日 下午4:36:41
     * @version 1.0
     * @param id 产品id
     * @param productDetailLonnerInfo 借款人信息
     * @param productDetailNormalQues 常见问题
     * @return
     *
     */
    PageInfo updateProductDetailInfo(Integer id, String productDetailLonnerInfo, String productDetailNormalQues);

    /**
     * 获取项目详情
     * 
     * @author yiban
     * @date 2018年3月13日 下午4:44:06
     * @version 1.0
     * @param pId
     * @return
     *
     */
    PageInfo getProductDetailInfo(Integer pId);
}
