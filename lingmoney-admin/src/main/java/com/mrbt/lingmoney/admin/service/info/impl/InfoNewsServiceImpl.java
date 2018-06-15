package com.mrbt.lingmoney.admin.service.info.impl;

import java.util.Date;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.mrbt.lingmoney.admin.service.info.InfoNewsService;
import com.mrbt.lingmoney.commons.cache.RedisDao;
import com.mrbt.lingmoney.mapper.InfoNewsMapper;
import com.mrbt.lingmoney.model.InfoNews;
import com.mrbt.lingmoney.model.InfoNewsExample;
import com.mrbt.lingmoney.utils.AppCons;
import com.mrbt.lingmoney.utils.PageInfo;
import com.mrbt.lingmoney.utils.ResultParame;

/**
 * 公司新闻
 */
@Service
public class InfoNewsServiceImpl implements InfoNewsService {
	@Autowired
	private RedisDao redisDao;

	@Autowired
	private InfoNewsMapper infoNewsMapper;

	/**
	 * 更新实体bean
	 * 
	 * @param vo
	 *            InfoNews
	 */
	@Override
	@Transactional
	public void update(InfoNews vo) {
		infoNewsMapper.updateByPrimaryKeySelective(vo);
	}

	/**
	 * 保存实体bean
	 * 
	 * @param vo
	 *            InfoNews
	 * @throws Exception
	 *             异常
	 */
	@Override
	@Transactional
	public void save(InfoNews vo) {
		vo.setCtDt(new Date());
		infoNewsMapper.insertSelective(vo);
	}

	@Override
	@Transactional
	public void publish(Integer id) {
		InfoNews record = infoNewsMapper.selectByPrimaryKey(id);
		if (record != null && record.getStatus() != AppCons.PUBLIST_STATUS) {
			record.setpDt(new Date());
			record.setStatus(AppCons.PUBLIST_STATUS);
			infoNewsMapper.updateByPrimaryKeySelective(record);
		}
	}

	@Override
	@Transactional
	public void delete(Integer id) {
		InfoNews infoNews = new InfoNews();
		infoNews.setId(id);
		infoNews.setStatus(ResultParame.ResultNumber.MINUS_ONE.getNumber());
		infoNewsMapper.updateByPrimaryKeySelective(infoNews);
		if (infoNews.getpDt() != null) {
			redisDao.ZREMRANGEBYSCORE("mrbt.info.news",
					infoNews.getpDt().getTime() / ResultParame.ResultNumber.ONE_THOUSAND.getNumber(),
					infoNews.getpDt().getTime() / ResultParame.ResultNumber.ONE_THOUSAND.getNumber());
		}

	}

	@Override
	public String listContent(Integer id) {
		InfoNews news = infoNewsMapper.selectByPrimaryKey(id);
		if (news != null) {
			return news.getContent();
		}
		return "";
	}

	@Override
	public void listGrid(InfoNews vo, PageInfo pageInfo) {
		InfoNewsExample example = createInfoNewsExample(vo);
		example.setLimitStart(pageInfo.getFrom());
		example.setLimitEnd(pageInfo.getPagesize());
		pageInfo.setRows(infoNewsMapper.selectByExample(example));
		pageInfo.setTotal((int) infoNewsMapper.countByExample(example));
	}

	/**
	 * 生成辅助类根据实体bean
	 * 
	 * @param vo
	 *            实体bean
	 * @return InfoNewsExample辅助类
	 */
	private InfoNewsExample createInfoNewsExample(InfoNews vo) {
		InfoNewsExample example = new InfoNewsExample();
		InfoNewsExample.Criteria cri = example.createCriteria()
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
	public boolean changeStatus(Integer id) {
		boolean flag = false;
		if (id != null && id > 0) {
			InfoNews record = infoNewsMapper.selectByPrimaryKey(id);
			if (record != null) {
				if (record.getStatus() == null || record.getStatus() == 0) {
					record.setStatus(1);
					record.setpDt(new Date());
				} else {
					record.setStatus(0);
				}
				infoNewsMapper.updateByPrimaryKeySelective(record);
				flag = true;
			}
		}
		return flag;
	}
}
