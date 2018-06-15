package com.mrbt.lingmoney.admin.service.festival.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.mrbt.lingmoney.admin.service.festival.BayWindowService;
import com.mrbt.lingmoney.mapper.BayWindowMapper;
import com.mrbt.lingmoney.model.BayWindow;
import com.mrbt.lingmoney.model.BayWindowExample;
import com.mrbt.lingmoney.utils.PageInfo;
import com.mrbt.lingmoney.utils.ResultParame;

/**
 * 活动飘窗设置
 *
 */
@Service
public class BayWindowServiceImpl implements BayWindowService {
	@Autowired
	private BayWindowMapper bayWindowMapper;

	@Override
	public BayWindow findById(int id) {
		return bayWindowMapper.selectByPrimaryKey(id);
	}

	@Override
	@Transactional
	public void delete(int id) {
		bayWindowMapper.deleteByPrimaryKey(id);
	}

	@Override
	@Transactional()
	public void save(BayWindow vo) {
		bayWindowMapper.insert(vo);
	}

	@Override
	@Transactional()
	public void update(BayWindow vo) {
		bayWindowMapper.updateByPrimaryKeySelective(vo);
	}

	@Override
	public PageInfo getList(BayWindowExample example) {
		PageInfo pageInfo = new PageInfo();
		long resSize = bayWindowMapper.countByExample(example);
		List<BayWindow> list = bayWindowMapper.selectByExample(example);
		pageInfo.setRows(list);
		pageInfo.setTotal((int) resSize);
		return pageInfo;
	}

	@Override
	public PageInfo changeStatus(Integer id) {
		PageInfo pageInfo = new PageInfo();
		try {
			if (id != null && id > 0) {
				BayWindow record = bayWindowMapper.selectByPrimaryKey(id);
				if (record != null) {
					if (record.getStatus() == null || record.getStatus() == 0) { // 不可用，设置为可用
						record.setStatus(1);
					} else { // 可用，设置为不可用。
						record.setStatus(0);
					}
					bayWindowMapper.updateByPrimaryKeySelective(record);
					pageInfo.setCode(ResultParame.ResultInfo.SUCCESS.getCode());
					return pageInfo;
				}
			}
			pageInfo.setCode(ResultParame.ResultInfo.NOT_FOUND.getCode());
			pageInfo.setMsg("参数错误，未找到记录");
		} catch (Exception e) {
			e.printStackTrace();
			pageInfo.setCode(ResultParame.ResultInfo.SERVER_ERROR.getCode());
			pageInfo.setMsg("服务器内部错误");
		}
		return pageInfo;
	}
}
