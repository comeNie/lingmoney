package com.mrbt.lingmoney.admin.service.newuserreward.impl;

import java.io.IOException;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.UUID;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.mrbt.lingmoney.admin.exception.ServiceException;
import com.mrbt.lingmoney.admin.service.newuserreward.NewUserRewardService;
import com.mrbt.lingmoney.admin.utils.DateFormatUtils;
import com.mrbt.lingmoney.mapper.NewUserRewardMapper;
import com.mrbt.lingmoney.model.NewUserReward;
import com.mrbt.lingmoney.model.NewUserRewardExample;
import com.mrbt.lingmoney.model.NewUserRewardExample.Criteria;
import com.mrbt.lingmoney.utils.Common;
import com.mrbt.lingmoney.utils.FtpUtils;
import com.mrbt.lingmoney.utils.PageInfo;
import com.mrbt.lingmoney.utils.ResultParame;

/**
 * @author luox
 * @Date 2017年6月26日
 */
@Service
public class NewUserRewardServiceImpl implements NewUserRewardService {

	@Autowired
	private NewUserRewardMapper newUserRewardMapper;
	@Autowired
	private FtpUtils ftpUtils;
	
	@Override
	public void getList(String markedWords, Integer status, PageInfo pageInfo) {
		NewUserRewardExample example = new NewUserRewardExample();
		example.setLimitStart(pageInfo.getFrom());
		example.setLimitEnd(pageInfo.getPagesize());
		
		Criteria criteria = example.createCriteria();
		if (status != null) {
			criteria.andStatusEqualTo(status);
		}
		if (StringUtils.isNotBlank(markedWords)) {
			criteria.andMarkedWordsLike("%" + markedWords + "%");
		}
		
		pageInfo.setRows(newUserRewardMapper.selectByExample(example));
		pageInfo.setTotal((int) newUserRewardMapper.countByExample(example));
	}

	@Override
	public void saveOrEdit(String markedWords, Integer id, Map<String, List<MultipartFile>> map) throws IOException {
		if (markedWords == null) {
			throw new ServiceException("参数错误");
		}
		
		String proPic = null;
		String indexPic = null;
		for (Entry<String, List<MultipartFile>> set : map.entrySet()) {
			List<MultipartFile> pics = set.getValue();
			if (pics != null && pics.size() > 0) {
				MultipartFile file = pics.get(0);
				if (file.getSize() == 0) {
					continue;
				}
//				if (file.getSize() > 20480) {
//					throw new ServiceException("图片不能超过20k");
//				}
				
				if (!verify(file.getOriginalFilename())) {
					throw new ServiceException("请选择有效的图片格式");
				}
				
				
				String fileName = UUID.randomUUID().toString() + ".jpg";
				
				String savePath = "lingmoney/newuserreward/";
				
				ftpUtils.upload(file.getInputStream(), savePath, fileName);
				
				String url = ftpUtils.getUrl() + savePath + fileName;
				if (set.getKey().equals("proPic")) {
					proPic = url;
				} else if (set.getKey().equals("indexPic")) {
					indexPic = url;
				}
			}
		}
		
		NewUserReward newUserReward = new NewUserReward();
		newUserReward.setMarkedWords(markedWords);
		newUserReward.setIndexPic(indexPic);
		newUserReward.setProPic(proPic);
		
		if (id != null) {
			newUserReward.setId(id);
			newUserRewardMapper.updateByPrimaryKeySelective(newUserReward);
		} else {
			newUserReward.setStatus(0);
			newUserReward.setCreateTime(DateFormatUtils.getFormatDateString(new Date()));
			newUserRewardMapper.insertSelective(newUserReward);
		}
		
	}

	/**
	 * verify
	 * 
	 * @param filename
	 *            filename
	 * @return 数据返回
	 */
	private boolean verify(String filename) {
		if (filename.endsWith(".jpg") 
				|| filename.endsWith(".JPG") 
				|| filename.endsWith(".bmp") 
				|| filename.endsWith(".BMP") 
				|| filename.endsWith(".png") 
				|| filename.endsWith(".PNG")) {
			return true;
		}
        return false;
	}

	@Override
	public void delete(Integer id) {
		if (id == null) {
			throw new ServiceException("参数错误");
		}
		NewUserReward reward = new NewUserReward();
		reward.setId(id);
		reward.setStatus(ResultParame.ResultNumber.TWO.getNumber());
		newUserRewardMapper.updateByPrimaryKeySelective(reward);
	}

	@Override
	public void updateStatus(Integer id) {
		if (id == null) {
			throw new ServiceException("参数错误");
		}
		NewUserReward newUserReward = new NewUserReward();
		newUserReward.setId(id);
		newUserReward.setStatus(1);
		newUserRewardMapper.updateByPrimaryKeySelective(newUserReward);
		
		// 之前发布的，失效
		NewUserRewardExample example = new NewUserRewardExample();
		example.createCriteria().andStatusEqualTo(1);
		List<NewUserReward> list = newUserRewardMapper.selectByExample(example);
		if (Common.isNotBlank(list)) {
			for (NewUserReward reward : list) {
				if (reward.getId().equals(id)) {
					continue;
				}
				this.delete(reward.getId());
			}
		}
	}

	@Override
	public String upload(MultipartFile file) throws IOException {
		if (file == null || file.getSize() == 0) {
			throw new ServiceException("请选择有效的图片");
		}
		
		String fileName = UUID.randomUUID().toString() + ".jpg";
		
		String savePath = "/lingmoney/newuserreward/";
		
		ftpUtils.upload(file.getInputStream(), savePath, fileName);
		
		return ftpUtils.getUrl() + savePath + fileName;
	}

}
