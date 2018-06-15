package com.mrbt.lingmoney.service.commonweal.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.ui.Model;

import com.alibaba.druid.util.StringUtils;
import com.alibaba.fastjson.JSONObject;
import com.mrbt.lingmoney.commons.cache.RedisDao;
import com.mrbt.lingmoney.mapper.PublicBenefitActivitiesMapper;
import com.mrbt.lingmoney.mapper.UsersLoveLogMapper;
import com.mrbt.lingmoney.mapper.UsersLoveValueMapper;
import com.mrbt.lingmoney.model.PublicBenefitActivities;
import com.mrbt.lingmoney.model.PublicBenefitActivitiesExample;
import com.mrbt.lingmoney.model.PublicBenefitActivitiesWithBLOBs;
import com.mrbt.lingmoney.model.UsersLoveLog;
import com.mrbt.lingmoney.model.UsersLoveLogExample;
import com.mrbt.lingmoney.model.UsersLoveValue;
import com.mrbt.lingmoney.service.commonweal.CommonwealService;
import com.mrbt.lingmoney.utils.DateUtils;
import com.mrbt.lingmoney.utils.PageInfo;

@Service
public class CommonwealServiceImpl implements CommonwealService {
	
	@Autowired
	private RedisDao redisDao;
	
	@Autowired
	private UsersLoveValueMapper usersLoveValueMapper;
	
	@Autowired
	private PublicBenefitActivitiesMapper publicBenefitActivitiesMapper;
	
	@Autowired
	private UsersLoveLogMapper usersLoveLogMapper;
	
	private static final String SET_LOVE_NUMBER = "SET_LOVE_NUMBER";

	@Override
	public PageInfo achieveLove(String uId) {
		PageInfo pageInfo = new PageInfo();
		//验证今天是否已经获取
		String key = "ACHIEVE_LOVE_" + uId;
		if (redisDao.get(key) == null) {
			if (redisDao.get(SET_LOVE_NUMBER) != null){
				
				UsersLoveValue ulv = usersLoveValueMapper.selectByPrimaryKey(uId);
				Integer loveNum = (Integer) redisDao.get(SET_LOVE_NUMBER);
				
				if(ulv != null) {
					ulv.setLoveNumber(ulv.getLoveNumber() + loveNum);
					usersLoveValueMapper.updateByPrimaryKey(ulv);
				}else {
					ulv = new UsersLoveValue();
					ulv.setuId(uId);
					ulv.setLoveNumber(loveNum);
					usersLoveValueMapper.insert(ulv);
				}
				redisDao.set(key, "");
				redisDao.expire(key, DateUtils.parseDate(DateUtils.giveDay(1) + " 00:00:00"));
				pageInfo.setCode(200);
				pageInfo.setMsg("获取成功");
			}else {
				pageInfo.setCode(6007);
				pageInfo.setMsg("没有公益值要获取");
			}
		} else {
			pageInfo.setCode(6007);
			pageInfo.setMsg("今天已经获取了爱心值");
		}
		return pageInfo;
	}

	@Override
	public JSONObject queryList(String uId, Integer type, Integer page, Integer rows) {
		JSONObject jsonObject = new JSONObject();
		PageInfo pageInfo = new PageInfo(page, rows);
		if (type == 1) {
			
			PublicBenefitActivitiesExample example = new PublicBenefitActivitiesExample();
			example.setLimitStart(pageInfo.getFrom());
			example.setLimitEnd(pageInfo.getSize());
			example.createCriteria().andStatusGreaterThan(0);
			example.setOrderByClause(" status, sort, create_time");
			
			List<PublicBenefitActivities> resList = publicBenefitActivitiesMapper.selectByExample(example);
			if(resList != null && resList.size() > 0) {
				jsonObject.put("code", 200);
				jsonObject.put("msg", "查询公益项目成功");
				jsonObject.put("data", resList);
			}else {
				jsonObject.put("code", 1006);
				jsonObject.put("msg", "没有公益项目");
			}
		} else if (StringUtils.isEmpty(uId)) {
			jsonObject.put("code", 1003);
			jsonObject.put("msg", "用户ID为空");
		} else {
			//查询用户已赞助的公益ID
			UsersLoveLogExample example = new UsersLoveLogExample();
			example.createCriteria().andUIdEqualTo(uId);
			List<UsersLoveLog> usersLogList = usersLoveLogMapper.selectByExample(example);
			if (usersLogList != null && usersLogList.size() > 0) {
				
				List<Integer> pbaList = new ArrayList<Integer>(); 
				Map<Integer, Object> map = new HashMap<Integer, Object>();
				for (UsersLoveLog ull : usersLogList) {
					if (map.containsKey(ull.getPbaId())) {
						continue;
					} else {
						map.put(ull.getPbaId(), "");
						pbaList.add(ull.getPbaId());
					}
				}
				
				PublicBenefitActivitiesExample example2 = new PublicBenefitActivitiesExample();
				example2.setLimitStart(pageInfo.getFrom());
				example2.setLimitEnd(pageInfo.getSize());
				example2.createCriteria().andIdIn(pbaList);
				example2.setOrderByClause(" sort, create_time");
				
				List<PublicBenefitActivities> resList = publicBenefitActivitiesMapper.selectByExample(example2);
				if (resList != null && resList.size() > 0) {
					jsonObject.put("code", 200);
					jsonObject.put("msg", "查询已赞助成功");
					jsonObject.put("data", resList);
				} else {
					jsonObject.put("code", 1006);
					jsonObject.put("msg", "用户没有赞助公益");
				}
			} else {
				jsonObject.put("code", 1006);
				jsonObject.put("msg", "用户没有赞助公益");
			}
		}
		return jsonObject;
	}

	@Override
	public JSONObject getUserLoveNum(String uId) {
		JSONObject jsonObject = new JSONObject();
		//查询用户当前爱心值
		UsersLoveValue ulv = usersLoveValueMapper.selectByPrimaryKey(uId);
		if (ulv != null) {
			jsonObject.put("code", 200);
			jsonObject.put("msg", "查询成功");
			jsonObject.put("obj", ulv.getLoveNumber());
		}else {
			jsonObject.put("code", 1006);
			jsonObject.put("msg", "爱心值为空");
		}
		
		//查询用户已使用爱心值
		UsersLoveLogExample example = new UsersLoveLogExample();
		example.createCriteria().andUIdEqualTo(uId);
		List<UsersLoveLog> ullList = usersLoveLogMapper.selectByExample(example);
		int sumLove = 0;
		for (UsersLoveLog ull : ullList) {
			sumLove = sumLove + ull.getDonationNumber();
		}
		jsonObject.put("obj2", sumLove);
		return jsonObject;
	}

	@Override
	public JSONObject sponsorProject(String uId, Integer loveNumber, Integer commonwealId) {
		JSONObject jsonObject = new JSONObject();
		//验证公益项目是否在期限内
		PublicBenefitActivities pba = publicBenefitActivitiesMapper.selectByPrimaryKey(commonwealId);
		if ((pba.getSumPraise() + loveNumber) > pba.getMaxPraise()) {
			jsonObject.put("code", 1008);
			jsonObject.put("msg", "此爱心公益活动即将成立，您最多可捐赠" + (pba.getMaxPraise() - pba.getSumPraise()) + "颗爱心呦~");
			jsonObject.put("obj", pba.getMaxPraise() - pba.getSumPraise());
			return jsonObject;
		}
		
		Date date = new Date();
		if (pba != null) {
			if (pba.getStatus() != 1 || pba.getStateTime().getTime() >= date.getTime() || pba.getEndTime().getTime() <= date.getTime()) {
				jsonObject.put("code", 1007);
				jsonObject.put("msg", "项目已结束或不在赞助期内");
			} else {
				//扣除用户爱心
				UsersLoveValue queryUlv = usersLoveValueMapper.selectByPrimaryKey(uId);
				if (queryUlv.getLoveNumber() >= loveNumber) {
					queryUlv.setLoveNumber(queryUlv.getLoveNumber() - loveNumber);
					usersLoveValueMapper.updateByPrimaryKey(queryUlv);
					//增加用户赞助日志
					UsersLoveLog ull = new UsersLoveLog();
					ull.setDonationLog("赞助：" + commonwealId + "爱心数量：" + loveNumber);
					ull.setDonationNumber(loveNumber);
					ull.setOperTime(new Date());
					ull.setPbaId(commonwealId);
					ull.setuId(uId);
					usersLoveLogMapper.insertSelective(ull);
					//增加公益表中已赞助数量
					PublicBenefitActivitiesWithBLOBs pba2 = new PublicBenefitActivitiesWithBLOBs();
					pba2.setId(pba.getId());
					if (pba.getSumPraise() == null) {
						pba2.setSumPraise(loveNumber);
						jsonObject.put("obj", loveNumber);
					} else {
						pba2.setSumPraise(pba.getSumPraise() + loveNumber);
						if (pba.getMaxPraise() == (pba.getSumPraise() + loveNumber)) {
							pba2.setStatus(2);
						}
						jsonObject.put("obj", pba.getSumPraise() + loveNumber);
					}
					
					publicBenefitActivitiesMapper.updateByPrimaryKeySelective(pba2);
					jsonObject.put("code", 200);
					jsonObject.put("msg", "赞助完成");
				} else {
					jsonObject.put("code", 1007);
					jsonObject.put("msg", "爱心值不足!");
				}
			}
		}
		return jsonObject;
	}

	@Override
	public void queryDetailsHtml(Model model, int i, Integer commonwealId) {
		PublicBenefitActivitiesExample example = new PublicBenefitActivitiesExample();
		example.createCriteria().andIdEqualTo(commonwealId);
		List<PublicBenefitActivitiesWithBLOBs> pbabList = publicBenefitActivitiesMapper.selectByExampleWithBLOBs(example);
		if (pbabList != null && pbabList.size() > 0) {
			//获取公益信息
			PublicBenefitActivitiesWithBLOBs pbab = pbabList.get(0);
			model.addAttribute("pbaName", pbab.getPbaName());
			model.addAttribute("pbaDesc", pbab.getPbaDesc());
			model.addAttribute("sumPraise", pbab.getSumPraise());
			model.addAttribute("maxPraise", pbab.getMaxPraise());
			model.addAttribute("pbaPicture", pbab.getPbaPicture());
			if (i == 1) {
				model.addAttribute("pbaHtml", pbab.getPbaHtmlMobile());
			} else {
				model.addAttribute("pbaHtml", pbab.getPbaHtmlWeb());
			}
		}
	}

}
