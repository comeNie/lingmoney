package com.mrbt.lingmoney.admin.service.gift.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.mrbt.lingmoney.admin.service.gift.LingbaoGiftTypeService;
import com.mrbt.lingmoney.mapper.LingbaoGiftMapper;
import com.mrbt.lingmoney.mapper.LingbaoGiftTypeMapper;
import com.mrbt.lingmoney.model.LingbaoGiftExample;
import com.mrbt.lingmoney.model.LingbaoGiftType;
import com.mrbt.lingmoney.model.LingbaoGiftTypeExample;
import com.mrbt.lingmoney.utils.PageInfo;
import com.mrbt.lingmoney.utils.ResultParame;

/**
 * 我的领地礼品类型
 *
 */
@Service
public class LingbaoGiftTypeServiceImpl implements LingbaoGiftTypeService {
	@Autowired
	private LingbaoGiftTypeMapper lingbaoGiftTypeMapper;
	@Autowired
	private LingbaoGiftMapper lingbaoGiftMapper;

	@Override
	public LingbaoGiftType findById(int id) {
		return lingbaoGiftTypeMapper.selectByPrimaryKey(id);
	}

	@Override
	@Transactional
	public void delete(int id) {
		lingbaoGiftTypeMapper.deleteByPrimaryKey(id);
	}

	@Override
	@Transactional()
	public void save(LingbaoGiftType vo) {
		lingbaoGiftTypeMapper.insert(vo);
	}

	@Override
	@Transactional()
	public void update(LingbaoGiftType vo) {
		lingbaoGiftTypeMapper.updateByPrimaryKeySelective(vo);
	}

	@Override
	public PageInfo getList(LingbaoGiftTypeExample example) {
		PageInfo pageInfo = new PageInfo();
		int resSize = (int) lingbaoGiftTypeMapper.countByExample(example);
		List<LingbaoGiftType> list = lingbaoGiftTypeMapper
				.selectByExample(example);
		pageInfo.setRows(list);
		pageInfo.setTotal(resSize);
		return pageInfo;
	}

	@Override
	public PageInfo changeStatus(Integer id) {
		PageInfo pageInfo = new PageInfo();
		try {
			if (id != null && id > 0) {
				LingbaoGiftType record = lingbaoGiftTypeMapper
						.selectByPrimaryKey(id);
				if (record != null) {
					if (record.getStatus() == null || record.getStatus() == 0) { // 不可用，设置为可用
						record.setStatus(1);
						lingbaoGiftTypeMapper
								.updateByPrimaryKeySelective(record);
						pageInfo.setCode(ResultParame.ResultInfo.SUCCESS.getCode());
					} else { // 可用，设置为不可用。前提是判断该类型下是否有礼品。
						LingbaoGiftExample example = new LingbaoGiftExample();
						LingbaoGiftExample.Criteria cri = example
								.createCriteria();
						cri.andTypeIdEqualTo(record.getId());
						int count = (int) lingbaoGiftMapper.countByExample(example);
						if (count > 0) {
							pageInfo.setCode(ResultParame.ResultInfo.UPDATE_FAIL.getCode());
							pageInfo.setMsg("该分类下有礼品，不可更改");
						} else {
							record.setStatus(0);
							lingbaoGiftTypeMapper
									.updateByPrimaryKeySelective(record);
							pageInfo.setCode(ResultParame.ResultInfo.SUCCESS.getCode());
						}
					}
					return pageInfo;
				}
			}
			pageInfo.setCode(ResultParame.ResultInfo.RETURN_DATA_EMPTY_DATA.getCode());
			pageInfo.setMsg("找不到记录");
		} catch (Exception e) {
			e.printStackTrace();
			pageInfo.setCode(ResultParame.ResultInfo.SERVER_ERROR.getCode());
			pageInfo.setMsg("服务器出错");
		}
		return pageInfo;
	}

}
