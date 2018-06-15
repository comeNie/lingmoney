package com.mrbt.lingmoney.admin.service.trading;

import java.util.List;

import org.apache.ibatis.session.RowBounds;

import com.mrbt.lingmoney.model.RedeemFailFlow;
import com.mrbt.lingmoney.model.RedeemFailFlowExample;
import com.mrbt.lingmoney.utils.GridPage;
import com.mrbt.lingmoney.utils.PageInfo;

/**
 * 
 * 企业管理--》赎回失败流水
 *
 */
public interface RedeemFailFlowService {

	/**
	 * 生成辅助类根据实体bean
	 * 
	 * @param vo
	 *            实体bean
	 * @return ProductRedeemFailFlowExample辅助类
	 */
	RedeemFailFlowExample createRedeemFailFlowExample(RedeemFailFlow vo);

	/**
	 * 删除
	 * 
	 * @param id
	 *            主键
	 */
	void delete(int id);

	/**
	 * 查找，根据ProductRedeemFailFlow
	 * 
	 * @param vo
	 *            ProductRedeemFailFlow的实例
	 * @param offset
	 *            起始位置
	 * @param limit
	 *            记录条数
	 * @return ProductRedeemFailFlow的集合
	 */
	List<RedeemFailFlow> list(RedeemFailFlow vo, int offset, int limit);

	/**
	 * 主键查询
	 * 
	 * @param id
	 *            id
	 * @return 数据返回
	 */
	RedeemFailFlow findByPk(int id);

	/**
	 * 生成datagrid表格需要的结果
	 * 
	 * @param vo
	 *            ProductRedeemFailFlow实体bean
	 * @param page
	 *            翻页信息
	 * @return GridPage<ProductRedeemFailFlow>
	 */
	GridPage<RedeemFailFlow> listGrid(RedeemFailFlow vo, RowBounds page);

    /**
	 * 保存实体bean
	 * 
	 * @param vo
	 *            RedeemFailFlow
	 */
    void save(RedeemFailFlow vo);

    /**
	 * 更新实体bean
	 * 
	 * @param vo
	 *            RedeemFailFlow
	 */
    void update(RedeemFailFlow vo);

    /**
	 * 赎回
	 * 
	 * @author yiban
	 * @date 2017年11月15日 下午3:18:01
	 * @version 1.0
	 * @param id
	 *            id
	 * @return 数据返回
	 *
	 */
    PageInfo redeem(Integer id);
}
