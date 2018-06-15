package com.mrbt.lingmoney.service.discover.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.mrbt.lingmoney.mapper.CartoonCategoryMapper;
import com.mrbt.lingmoney.mapper.CartoonContentMapper;
import com.mrbt.lingmoney.mapper.FinancialManagementMapper;
import com.mrbt.lingmoney.model.CartoonCategory;
import com.mrbt.lingmoney.model.CartoonCategoryExample;
import com.mrbt.lingmoney.model.CartoonContent;
import com.mrbt.lingmoney.model.CartoonContentExample;
import com.mrbt.lingmoney.model.FinancialManagement;
import com.mrbt.lingmoney.model.FinancialManagementExample;
import com.mrbt.lingmoney.service.discover.FinancialClassService;
import com.mrbt.lingmoney.utils.PageInfo;
import com.mrbt.lingmoney.utils.ResultParame.ResultInfo;

/**
 * @author syb
 * @date 2017年4月12日 下午3:27:14
 * @version 1.0
 * @description
 **/
@Service
public class FinancialClassServiceImpl implements FinancialClassService {

	@Autowired
	private FinancialManagementMapper financialManagementMapper;
	@Autowired
	private CartoonCategoryMapper cartoonCategoryMapper;
	@Autowired
	private CartoonContentMapper cartoonContentMapper;

	@Override
	public PageInfo getIndexInfo(Integer pageNo, Integer pageSize) {
		
		PageInfo pageInfo = new PageInfo(pageNo, pageSize);
		// 所需信息：理财课堂列表
		FinancialManagementExample example = new FinancialManagementExample();
		example.createCriteria().andStatusEqualTo(1);
		example.setLimitStart(pageInfo.getFrom());
		example.setLimitEnd(pageInfo.getSize());
		example.setOrderByClause("pub_date desc");
		List<FinancialManagement> list = financialManagementMapper.selectByExample(example);
		
		if (null != list && list.size() > 0) {
			int total = (int) financialManagementMapper.countByExample(example);
            pageInfo.setCode(ResultInfo.SUCCESS.getCode());
			pageInfo.setMsg("success");
			pageInfo.setTotal(total);
			pageInfo.setRows(list);
		} else {
            pageInfo.setCode(ResultInfo.EMPTY_DATA.getCode());
			pageInfo.setMsg("暂无数据");
		}
		
		return pageInfo;
	}

	@Override
	public FinancialManagement getDetailById(Integer id) {
		return financialManagementMapper.selectByPrimaryKey(id);
	}

	@Override
	public List<CartoonCategory> listCartoonCategory() {
		CartoonCategoryExample example = new CartoonCategoryExample();
		example.createCriteria().andStatusEqualTo(1);
		example.setOrderByClause("id desc");
		return cartoonCategoryMapper.selectByExample(example);
	}

	@Override
	public List<CartoonContent> listCartoonContentByTypeId(Integer id) {
		CartoonContentExample example = new CartoonContentExample();
		example.createCriteria().andCIdEqualTo(id).andStatusEqualTo(1);
		example.setOrderByClause("pub_date desc");
		return cartoonContentMapper.selectByExample(example);
	}

	@Override
	public CartoonCategory findCartoonCategoryById(Integer id) {
		return cartoonCategoryMapper.selectByPrimaryKey(id);
	}

	@Override
	public CartoonContent findCartoonContentById(Integer id) {
		return cartoonContentMapper.selectByPrimaryKey(id);
	}

	@Override
	public List<Integer> listCartoonContentId(Integer cid) {
		return cartoonContentMapper.listCartoonContentId(cid);
	}

	@Override
	public List<Integer> listFinancialManagementId() {
		return financialManagementMapper.listFinancialManagementId();
	}

}
