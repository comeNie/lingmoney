package com.mrbt.lingmoney.admin.service.info.impl;

import java.util.Date;
import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.apache.ibatis.session.RowBounds;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.mrbt.lingmoney.admin.service.info.InfoMediaService;
import com.mrbt.lingmoney.commons.cache.RedisDao;
import com.mrbt.lingmoney.commons.cache.annotation.OperType;
import com.mrbt.lingmoney.commons.cache.annotation.RedisCache;
import com.mrbt.lingmoney.commons.cache.annotation.RedisOper;
import com.mrbt.lingmoney.mapper.InfoMediaMapper;
import com.mrbt.lingmoney.model.InfoMedia;
import com.mrbt.lingmoney.model.InfoMediaExample;
import com.mrbt.lingmoney.utils.AppCons;
import com.mrbt.lingmoney.utils.GridPage;
import com.mrbt.lingmoney.utils.PageInfo;
import com.mrbt.lingmoney.utils.ResultParame;

/**
 * 页面设置——》媒体资讯
 * 
 */
@Service
public class InfoMediaServiceImpl implements InfoMediaService {

	@Autowired
	private RedisDao redisDao;

	@Autowired
	private InfoMediaMapper infoMediaMapper;

	/**
	 * 生成辅助类根据实体bean
	 * 
	 * @param vo
	 *            实体bean
	 * @return InfoMediaExample辅助类
	 */
	@Override
	public InfoMediaExample createInfoMediaExample(InfoMedia vo) {
		InfoMediaExample example = new InfoMediaExample();
		InfoMediaExample.Criteria cri = example.createCriteria()
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
		InfoMedia infoMedia = new InfoMedia();
		infoMedia.setId(id);
		infoMedia.setStatus(ResultParame.ResultNumber.MINUS_ONE.getNumber());
		infoMediaMapper.updateByPrimaryKeySelective(infoMedia);
		if (infoMedia.getpDt() != null) {
			redisDao.ZREMRANGEBYSCORE("mrbt.info.media",
					infoMedia.getpDt().getTime() / ResultParame.ResultNumber.ONE_THOUSAND.getNumber(),
					infoMedia.getpDt().getTime() / ResultParame.ResultNumber.ONE_THOUSAND.getNumber());
		}
	}

	@Override
	public List<InfoMedia> list(InfoMedia vo, int offset, int limit) {
		InfoMediaExample example = createInfoMediaExample(vo);
		example.setLimitStart(offset);
		example.setLimitEnd(limit);

		return infoMediaMapper.selectByExample(example);
	}

	/**
	 * 生成datagrid表格需要的结果
	 * 
	 * @param vo
	 *            InfoMedia实体bean
	 * @param page
	 *            翻页信息
	 * @return GridPage<AdminBanner>
	 */
	@Override
	public GridPage<InfoMedia> listGrid(InfoMedia vo, RowBounds page) {
		GridPage<InfoMedia> result = new GridPage<InfoMedia>();
		InfoMediaExample example = createInfoMediaExample(vo);
		example.setLimitStart(page.getOffset());
		example.setLimitEnd(page.getLimit());
		result.setRows(infoMediaMapper.selectByExample(example));
		result.setTotal(infoMediaMapper.countByExample(example));

		return result;
	}

	/**
	 * 返回text的内容
	 * 
	 * @param id
	 * @return
	 */
	@Override
	public String listContent(int id) {
		InfoMedia media = infoMediaMapper.selectByPrimaryKey(id);
		if (media != null) {
			return media.getContent();
		}
		return "";
	}

	/**
	 * 根据id取数据，不带blob字段
	 * 
	 * @param id
	 * @return
	 */
	@Override
	public InfoMedia listByIdNotBlob(int id) {
		return infoMediaMapper.selectByPrimaryKeyNotBlob(id);
	}

	/**
	 * 发布媒体新闻 <br/>
	 * 分数取到秒，因为后面的三位毫秒级数据库不存
	 * 
	 * @param id
	 */
	@Override
	@RedisCache(key = "'mrbt.info.media'", oper = @RedisOper(cmd = OperType.ZADD, score = "#record.pDt.getTime()/1000"))
	@Transactional
	public boolean publish(int id) {
		try {
			InfoMedia record = infoMediaMapper.selectByPrimaryKeyNotBlob(id);
			if (record != null && record.getStatus() != AppCons.PUBLIST_STATUS) {
				record.setpDt(new Date());
				record.setStatus(AppCons.PUBLIST_STATUS);
				infoMediaMapper.updateByPrimaryKeySelective(record);
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
			InfoMedia record = infoMediaMapper.selectByPrimaryKeyNotBlob(id);
			if (record != null) {
				if (record.getStatus() == null || record.getStatus() == 0) {
					record.setStatus(1);
					record.setpDt(new Date());
				} else {
					record.setStatus(0);
				}
				infoMediaMapper.updateByPrimaryKeySelective(record);
				flag = true;
			}
		}
		return flag;
	}

	/**
	 * 保存实体bean
	 * 
	 * @param vo
	 */
	@Override
	@Transactional()
	public void save(InfoMedia vo) {
		vo.setCtDt(new Date());
		infoMediaMapper.insertSelective(vo);
	}

	/**
	 * 更新实体bean
	 * 
	 * @param vo
	 */
	@Override
	@Transactional()
	public void update(InfoMedia vo) {
		infoMediaMapper.updateByPrimaryKeySelective(vo);
	}

	@SuppressWarnings({ "rawtypes", "unchecked" })
	@Override
	public void reload(List infoMediaList) {
		redisDao.refreshKeyValueList("HOMR_MEDIA", infoMediaList);
	}

	@Override
	public PageInfo list(InfoMedia vo, PageInfo pageInfo) {
		InfoMediaExample example = createInfoMediaExample(vo);
		example.setLimitStart(pageInfo.getFrom());
		example.setLimitEnd(pageInfo.getSize());

		int resSize = (int) infoMediaMapper.countByExample(example);
		List<InfoMedia> list = infoMediaMapper.selectByExample(example);
		pageInfo.setRows(list);
		pageInfo.setTotal(resSize);
		return pageInfo;
	}
}
