package com.mrbt.lingmoney.service.common.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.alibaba.fastjson.JSONObject;
import com.mrbt.lingmoney.bank.utils.DateUtils;
import com.mrbt.lingmoney.commons.cache.RedisDao;
import com.mrbt.lingmoney.mapper.HxRedPacketMapper;
import com.mrbt.lingmoney.mapper.UsersMapper;
import com.mrbt.lingmoney.mapper.UsersPropertiesMapper;
import com.mrbt.lingmoney.mapper.UsersRedPacketMapper;
import com.mrbt.lingmoney.mapper.WorldCupProblemMapper;
import com.mrbt.lingmoney.mapper.WorldCupScoreMapper;
import com.mrbt.lingmoney.model.HxRedPacket;
import com.mrbt.lingmoney.model.HxRedPacketExample;
import com.mrbt.lingmoney.model.Users;
import com.mrbt.lingmoney.model.UsersProperties;
import com.mrbt.lingmoney.model.UsersRedPacket;
import com.mrbt.lingmoney.model.WorldCupGuessing;
import com.mrbt.lingmoney.model.WorldCupProblem;
import com.mrbt.lingmoney.model.WorldCupProblemExample;
import com.mrbt.lingmoney.model.WorldCupScore;
import com.mrbt.lingmoney.model.WorldCupScoreExample;
import com.mrbt.lingmoney.service.common.WorldCupActivityService;
import com.mrbt.lingmoney.utils.Common;
import com.mrbt.lingmoney.utils.PageInfo;
import com.mrbt.lingmoney.utils.Validation;
import com.mrbt.lingmoney.utils.ResultParame.ResultInfo;
import com.mrbt.lingmoney.utils.ResultParame.ResultNumber;

@Service
public class WorldCupActivityServiceImpl implements WorldCupActivityService {

	@Autowired
	private WorldCupProblemMapper worldCupProblemMapper;

	@Autowired
	private WorldCupScoreMapper worldCupScoreMapper;
	
	@Autowired
	private UsersPropertiesMapper usersPropertiesMapper;
	
	@Autowired
	private UsersMapper usersMapper;
	
	@Autowired
	private RedisDao redisDao;
	
	@Autowired
	private HxRedPacketMapper hxRedPacketMapper;
	
	@Autowired
	private UsersRedPacketMapper usersRedPacketMapper;

	private static final String WORLD_CUP_PROBLEM = "WORLD_CUP_PROBLEM";

	@Override
	public void getAnswersList(PageInfo pageInfo) {
		List<WorldCupProblem> wcpsRes = new ArrayList<WorldCupProblem>();

		List<WorldCupProblem> wcps = queryAnswersList();

		// 随机取10条数据，没有增加更多的判断，数据必须保证10条以上，少了出错自己解决
		if (wcps != null && wcps.size() > 0) {

			int[] rs = Common.generatingRandomNumber(wcps.size(),
					ResultNumber.TEN.getNumber());
			for (int i = 0; i < rs.length; i++) {
				WorldCupProblem wcp = wcps.get(rs[i]);
				wcpsRes.add(wcp);
			}

			pageInfo.setCode(ResultInfo.SUCCESS.getCode());
			pageInfo.setMsg(ResultInfo.SUCCESS.getMsg());
			pageInfo.setRows(wcpsRes);

		} else {
			pageInfo.setCode(ResultInfo.NO_DATA.getCode());
			pageInfo.setMsg(ResultInfo.NO_DATA.getMsg());
		}
	}

	/**
	 * 查询问答数据公共方法
	 * 
	 * @return
	 */
	private List<WorldCupProblem> queryAnswersList() {

		List<WorldCupProblem> wcps = new ArrayList<WorldCupProblem>();

		if (redisDao.get(WORLD_CUP_PROBLEM) != null) {
			wcps = (List<WorldCupProblem>) redisDao.get(WORLD_CUP_PROBLEM);
		} else {
			WorldCupProblemExample example = new WorldCupProblemExample();
			wcps = worldCupProblemMapper.selectByExample(example);
			if (wcps != null && wcps.size() > 0) {
				redisDao.set(WORLD_CUP_PROBLEM, wcps);
			}
		}

		return wcps;
	}

	@Override
	public void calculatedFraction(String sheet, PageInfo pageInfo) {
		
		if (sheet != null && !sheet.equals("")) {
			
			sheet = sheet.replace("\"", "");
			
			List<WorldCupProblem> wcps = queryAnswersList();

			Map<String, String> map = new HashMap<String, String>();
			String[] sheets = sheet.split(";");
			for (int i = 0; i < sheets.length; i++) {
				String str = sheets[i];
				String[] strings = str.split(":");
				map.put(strings[0], strings[1]);
			}

			int sumAnswer = 0;
			for (WorldCupProblem wcp : wcps) {
				if (map.containsKey(wcp.getId() + "")) {
					if (map.get(wcp.getId() + "").equals(wcp.getAnswer())) {
						sumAnswer++;
					}
				}
			}

			String fenStr = calculated(sumAnswer);

			JSONObject jsonObject = new JSONObject();
			jsonObject.put("score", sumAnswer);
			jsonObject.put("scoreMsg", fenStr);

			pageInfo.setCode(ResultInfo.SUCCESS.getCode());
			pageInfo.setMsg(ResultInfo.SUCCESS.getMsg());
			pageInfo.setObj(jsonObject);
		} else {
			pageInfo.setCode(ResultInfo.PARAM_MISS.getCode());
			pageInfo.setMsg(ResultInfo.PARAM_MISS.getMsg());
		}
	}

	/**
	 * 根据得分返回级别信息
	 * @param sumAnswer
	 * @return
	 */
	private String calculated(int sumAnswer) {
		String fenStr = "";
		if (sumAnswer > 9) {
			fenStr = "骨灰级铁杆粉儿——“电视之星”在手，天下人无我有";
		} else if (sumAnswer > 7) {
			fenStr = "真爱粉——带球跑，不掉队";
		} else if (sumAnswer > 4) {
			fenStr = "酱油粉——球迷界的泥石流";
		} else if (sumAnswer > 1) {
			fenStr = "闪耀伪球迷——理不直气也壮";
		} else {
			fenStr = "万毒之王伪球迷——做点理财去买药";
		}
		return fenStr;
	}

	@Override
	public void saveScoreByPhone(String phone, Integer score, PageInfo pageInfo) {
		if (StringUtils.isBlank(phone) || score == null) {
			pageInfo.setCode(ResultInfo.PARAM_MISS.getCode());
			pageInfo.setMsg(ResultInfo.PARAM_MISS.getMsg());
		} else {
			
			if (Validation.MatchMobile(phone)) {
				//查询phone是否有分数
				WorldCupScoreExample example = new WorldCupScoreExample();
				example.createCriteria().andPhoneEqualTo(phone);
				
				WorldCupScore wcs = new WorldCupScore();
				wcs.setCreateTime(new Date());
				wcs.setPhone(phone);
				wcs.setReceive(0);
				wcs.setScore(score);
				
				//查询用户是否注册
				UsersProperties uProperties = usersPropertiesMapper.selectByTelephone(phone);
				if (uProperties != null) {
					wcs.setuId(uProperties.getuId());
				}
				
				List<WorldCupScore> list = worldCupScoreMapper.selectByExample(example);
				
				if (list != null && list.size() > 0) {
					WorldCupScore worldCupScore = list.get(0);
					
					if (worldCupScore.getReceive() == 1) {
						pageInfo.setCode(ResultInfo.EXCHANGE_COUNT_ERROR.getCode());
						pageInfo.setMsg("请勿重复领取！");
					} else {
						worldCupScoreMapper.updateByExampleSelective(wcs, example);
						pageInfo.setCode(ResultInfo.SUCCESS.getCode());
						pageInfo.setMsg(ResultInfo.SUCCESS.getMsg());
					}
				} else {
					worldCupScoreMapper.insertSelective(wcs);
					pageInfo.setCode(ResultInfo.SUCCESS.getCode());
					pageInfo.setMsg(ResultInfo.SUCCESS.getMsg());
				}
			} else {
				pageInfo.setCode(ResultInfo.PARAM_MISS.getCode());
				pageInfo.setMsg("手机号格式错误！");
			}
		}
	}

	@Override
	public void queryUsersScore(String uId, PageInfo pageInfo) {
		WorldCupScoreExample example = new WorldCupScoreExample();
		example.createCriteria().andUIdEqualTo(uId);
		List<WorldCupScore> wCupScores = worldCupScoreMapper.selectByExample(example);
		
		//如果通过uId查询到分数，返回数据，如果没有，查询到用户的手机号，进行查询
		if (wCupScores != null && wCupScores.size() > 0) {
			
			WorldCupScore wcs = wCupScores.get(0);
			
			wrapperReturn(wcs, pageInfo);
			
		} else {
			
			Users users = usersMapper.selectByPrimaryKey(uId);
			if (users != null) {
				WorldCupScoreExample example2 = new WorldCupScoreExample();
				example2.createCriteria().andPhoneEqualTo(users.getTelephone());
				
				List<WorldCupScore> wCupScores2 = worldCupScoreMapper.selectByExample(example2);
				
				if (wCupScores2 != null && wCupScores2.size() > 0) {
					WorldCupScore wcs = wCupScores2.get(0);

					//更新用户分数中的uId
					WorldCupScore upadateWcs = new WorldCupScore();
					upadateWcs.setId(wcs.getId());
					upadateWcs.setuId(uId);
					
					//更新uId
					worldCupScoreMapper.updateByPrimaryKeySelective(upadateWcs);
					
					//包装返回数据
					wrapperReturn(wcs, pageInfo);
				} else {
					pageInfo.setCode(ResultInfo.NO_DATA.getCode());
					pageInfo.setMsg(ResultInfo.NO_DATA.getMsg());
				}
				
			} else {
				pageInfo.setCode(ResultInfo.ACCOUNT_NUMBER_NON_EXIST.getCode());
				pageInfo.setMsg(ResultInfo.ACCOUNT_NUMBER_NON_EXIST.getMsg());
			}
		}
	}

	/**
	 * 包装查询用户分数数据
	 * @param wcs
	 * @param pageInfo
	 */
	private void wrapperReturn(WorldCupScore wcs, PageInfo pageInfo) {
		JSONObject jsonObject = new JSONObject();
		jsonObject.put("score", wcs.getScore());
		jsonObject.put("scoreMsg", calculated(wcs.getScore()));
		
		pageInfo.setCode(ResultInfo.SUCCESS.getCode());
		pageInfo.setMsg(ResultInfo.SUCCESS.getMsg());
		pageInfo.setObj(jsonObject);
	}

	@Override
	public void receiveRedEnve(String uId, PageInfo pageInfo) {
		//判断是否进行了答题
		WorldCupScoreExample example = new WorldCupScoreExample();
		example.createCriteria().andUIdEqualTo(uId);
		List<WorldCupScore> wCupScores = worldCupScoreMapper.selectByExample(example);
		
		//如果通过uId查询到分数，返回数据，如果没有，查询到用户的手机号，进行查询
		if (wCupScores != null && wCupScores.size() > 0) {
			WorldCupScore wcs = wCupScores.get(0);
			
			//判断是否领取
			if (wcs.getReceive() == 0) {
				//领取
				receiveRedEnveOper(wcs, pageInfo);
				
			} else {
				pageInfo.setCode(ResultInfo.PARAMETER_ERROR.getCode());
				pageInfo.setMsg("您已经领取了复投红包，不能重复领取!");
			}
		} else {
			pageInfo.setCode(ResultInfo.NO_DATA.getCode());
			pageInfo.setMsg("您还没有参加答题活动!,请先去答题吧!");
		}
	}

	/**
	 * 领取复投红包
	 * @param wcs
	 * @param pageInfo
	 */
	private void receiveRedEnveOper(WorldCupScore wcs, PageInfo pageInfo) {
		Date date = new Date();
		Double redAmount = calculationAmount(wcs.getScore());
		HxRedPacketExample example = new HxRedPacketExample();
		example.createCriteria().andHrpTypeEqualTo(ResultNumber.THREE.getNumber()).andHrpNumberEqualTo(redAmount)
			.andAStartTimeLessThan(date).andAEndTimeGreaterThan(date).andStatusEqualTo(ResultNumber.ONE.getNumber());
		List<HxRedPacket> hRedPackets = hxRedPacketMapper.selectByExample(example);
		
		if (hRedPackets != null && hRedPackets.size() > 0) {
			HxRedPacket hrp = hRedPackets.get(0);
			
			//添加用户红包数据
			UsersRedPacket uPacket = new UsersRedPacket();
			uPacket.setuId(wcs.getuId());
			uPacket.setRpId(hrp.getId());
			uPacket.setStatus(ResultNumber.ZERO.getNumber());
			uPacket.setCreateTime(new Date());
			uPacket.setIsCheck(ResultNumber.ZERO.getNumber());
			
			if (hrp.getDelayed() != null && hrp.getDelayed() > 0) {
				uPacket.setValidityTime(DateUtils.addDay(new Date(), hrp.getDelayed()));
			} else {
				uPacket.setValidityTime(hrp.getValidityTime());
			}
			
		 	long res = usersRedPacketMapper.insertSelective(uPacket);
			if (res > 0) {
				
				//更加分数表中的领取状态为已领取
				WorldCupScore updateReceive = new WorldCupScore();
				updateReceive.setId(wcs.getId());
				updateReceive.setReceive(ResultNumber.ONE.getNumber());
				
				worldCupScoreMapper.updateByPrimaryKeySelective(updateReceive);
				
				pageInfo.setCode(ResultInfo.SUCCESS.getCode());
				pageInfo.setMsg(hrp.getHrpName());
			} else {
				pageInfo.setCode(ResultInfo.NO_SUCCESS.getCode());
				pageInfo.setMsg("添加用户复投红包失败,请与客服联系!");
			}
		} else {
			pageInfo.setCode(ResultInfo.NO_SUCCESS.getCode());
			pageInfo.setMsg("不在活动时间内！");
		}
	}
	
	/**
	 * 通过分数返回应该获取到的复投红包的金额，用户查询条件
	 * @param sumAnswer
	 * @return
	 */
	private Double calculationAmount(int sumAnswer) {
		Double fenStr = 10.00;
		if (sumAnswer > 9) {
			fenStr = 50.00;
		} else if (sumAnswer > 7) {
			fenStr = 40.00;
		} else if (sumAnswer > 4) {
			fenStr = 30.00;
		} else if (sumAnswer > 1) {
			fenStr = 20.00;
		} else {
			fenStr = 10.00;
		}
		return fenStr;
	}

}
