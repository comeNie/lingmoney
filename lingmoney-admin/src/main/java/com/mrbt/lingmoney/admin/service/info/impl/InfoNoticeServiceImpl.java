package com.mrbt.lingmoney.admin.service.info.impl;

import java.util.Date;
import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.mrbt.lingmoney.admin.service.info.InfoNoticeService;
import com.mrbt.lingmoney.commons.cache.RedisDao;
import com.mrbt.lingmoney.commons.cache.annotation.OperType;
import com.mrbt.lingmoney.commons.cache.annotation.RedisCache;
import com.mrbt.lingmoney.commons.cache.annotation.RedisOper;
import com.mrbt.lingmoney.mapper.InfoNoticeMapper;
import com.mrbt.lingmoney.model.InfoNotice;
import com.mrbt.lingmoney.model.InfoNoticeExample;
import com.mrbt.lingmoney.utils.AppCons;
import com.mrbt.lingmoney.utils.PageInfo;
import com.mrbt.lingmoney.utils.ResultParame;

/**
 * 站内公告
 * 
 */
@Service
public class InfoNoticeServiceImpl implements InfoNoticeService {

	@Autowired
	private RedisDao redisDao;

	@Autowired
	private InfoNoticeMapper infoNoticeMapper;

	
	@Override
	public InfoNoticeExample createInfoNoticeExample(InfoNotice vo) {
		InfoNoticeExample example = new InfoNoticeExample();
		InfoNoticeExample.Criteria cri = example.createCriteria()
				.andStatusNotEqualTo(ResultParame.ResultNumber.MINUS_ONE.getNumber());
		if (vo.getStatus() != null) {
			cri.andStatusEqualTo(vo.getStatus().intValue());
		}
		if (StringUtils.isNotBlank(vo.getTitle())) {
			cri.andTitleLike("%" + vo.getTitle() + "%");
		}
		example.setOrderByClause("id desc");
		return example;
	}

	
	@Override
	@Transactional
	public void delete(int id) {
		InfoNotice infoNotice = new InfoNotice();
		infoNotice.setId(id);
		infoNotice.setStatus(ResultParame.ResultNumber.MINUS_ONE.getNumber());
		infoNoticeMapper.updateByPrimaryKeySelective(infoNotice);
		if (infoNotice.getpDt() != null) {
			redisDao.ZREMRANGEBYSCORE("mrbt.info.media",
					infoNotice.getpDt().getTime() / ResultParame.ResultNumber.ONE_THOUSAND.getNumber(),
					infoNotice.getpDt().getTime() / ResultParame.ResultNumber.ONE_THOUSAND.getNumber());
		}
	}

	@Override
	public List<InfoNotice> list(InfoNotice vo, int offset, int limit) {
		InfoNoticeExample example = createInfoNoticeExample(vo);
		example.setLimitStart(offset);
		example.setLimitEnd(limit);

		return infoNoticeMapper.selectByExample(example);
	}


	
	@Override
	public String listContent(int id) {
		InfoNotice media = infoNoticeMapper.selectByPrimaryKey(id);
		if (media != null) {
			return media.getContent();
		}
		return "";
	}

	
	@Override
	public InfoNotice listByIdNotBlob(int id) {
		return infoNoticeMapper.selectByPrimaryKeyNotBlob(id);
	}

	/**
	 * 发布
	 * 
	 * @param id
	 */
	@Override
	@RedisCache(key = "'mrbt.info.media'", oper = @RedisOper(cmd = OperType.ZADD, score = "#record.pDt.getTime()/1000"))
	@Transactional
	public boolean publish(int id) {
		try {
			InfoNotice record = infoNoticeMapper.selectByPrimaryKeyNotBlob(id);
			if (record != null && record.getStatus() != AppCons.PUBLIST_STATUS) {
				record.setpDt(new Date());
				record.setStatus(AppCons.PUBLIST_STATUS);
				infoNoticeMapper.updateByPrimaryKeySelective(record);
			} else {
				return false;
			}
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}
		return true;
	}

	@Override
	@Transactional
	public boolean changeStatus(Integer id) {
		boolean flag = false;
		if (id != null && id > 0) {
			InfoNotice record = infoNoticeMapper.selectByPrimaryKeyNotBlob(id);
			if (record != null) {
				if (record.getStatus() == null || record.getStatus() == 0) {
					record.setStatus(1);
					record.setpDt(new Date());
				} else {
					record.setStatus(0);
				}
				infoNoticeMapper.updateByPrimaryKeySelective(record);
				flag = true;
			}
		}
		return flag;
	}


	@Override
	@Transactional
	public void save(InfoNotice vo) {
		vo.setCtDt(new Date());
		infoNoticeMapper.insertSelective(vo);
	}

	@Override
	@Transactional
	public void update(InfoNotice vo) {
		infoNoticeMapper.updateByPrimaryKeySelective(vo);
	}

	@SuppressWarnings({ "rawtypes", "unchecked" })
	@Override
	public void reload(List infoNoticeList) {
		redisDao.refreshKeyValueList("HOMR_MEDIA", infoNoticeList);
	}

	@Override
	public PageInfo list(InfoNotice vo, PageInfo pageInfo) {
		InfoNoticeExample example = createInfoNoticeExample(vo);
		example.setLimitStart(pageInfo.getFrom());
		example.setLimitEnd(pageInfo.getSize());

		int resSize = (int) infoNoticeMapper.countByExample(example);
		List<InfoNotice> list = infoNoticeMapper.selectByExample(example);
		pageInfo.setRows(list);
		pageInfo.setTotal(resSize);
		return pageInfo;
	}
}
