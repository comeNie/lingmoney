package com.mrbt.lingmoney.admin.service.product.impl;

import java.util.List;

import org.apache.ibatis.session.RowBounds;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.mrbt.lingmoney.admin.service.product.GiftProductService;
import com.mrbt.lingmoney.mapper.GiftProductMapper;
import com.mrbt.lingmoney.model.GiftProduct;
import com.mrbt.lingmoney.model.GiftProductExample;
import com.mrbt.lingmoney.utils.GridPage;

/**
 * 产品设置——》活动产品关联礼品
 * 
 */
@Service
public class GiftProductServiceImpl implements GiftProductService {

	@Autowired
	private GiftProductMapper giftProductMapper;

	/**
	 * 生成辅助类根据实体bean
	 * 
	 * @param vo
	 *            实体bean
	 * @return ProductGiftExample辅助类
	 */
	private GiftProductExample createGiftProductExample(GiftProduct vo) {
		GiftProductExample example = new GiftProductExample();
		if (vo.getpName() != null) {
			example.createCriteria().andPNameLike("%" + vo.getpName() + "%");
		}
		return example;
	}

	/**
	 * 删除
	 * 
	 * @param id
	 */
	@Transactional
	@Override
	public void delete(int id) {
		giftProductMapper.deleteByPrimaryKey(id);
	}

	/**
	 * 查找，根据ProductGift
	 * 
	 * @param vo
	 *            ProductGift的实例
	 * @param offset
	 *            起始位置
	 * @param limit
	 *            记录条数
	 * @return ProductGift的集合
	 */
	@Override
	public List<GiftProduct> list(GiftProduct vo, int offset, int limit) {
		return null;
		
	}

	/**
	 * 生成datagrid表格需要的结果
	 * 
	 * @param vo
	 *            ProductGift实体bean
	 * @param page
	 *            翻页信息
	 * @return GridPage<ProductGift>
	 */
	@Override
	public GridPage<GiftProduct> listGrid(GiftProduct vo, RowBounds page) {
		GiftProductExample example = createGiftProductExample(vo);
		example.setLimitStart(page.getOffset());
		example.setLimitEnd(page.getLimit());
		GridPage<GiftProduct> result = new GridPage<GiftProduct>();
		result.setRows(giftProductMapper.selectByExample(example));
		result.setTotal(giftProductMapper.countByExample(example));
		return result;
	}

	/**
	 * 保存实体bean
	 * 
	 * @param vo
	 *            GiftProduct
	 */
	@Transactional
	@Override
	public void save(GiftProduct vo) {
		giftProductMapper.insertSelective(vo);
	}

	/**
	 * 更新实体bean
	 * 
	 * @param vo
	 */
	@Transactional
	@Override
	public void update(GiftProduct vo) {
		giftProductMapper.updateByPrimaryKeyWithBLOBs(vo);
	}
	@Override
	public List<GiftProduct> selectByPid(GiftProduct vo) {
		
		GiftProductExample example = new GiftProductExample();
		GiftProductExample.Criteria cri = example.createCriteria();
		
		cri.andPIdEqualTo(vo.getpId());

		return giftProductMapper.selectByExample(example);
	}
	
}
