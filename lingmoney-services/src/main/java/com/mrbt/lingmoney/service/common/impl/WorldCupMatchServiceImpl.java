package com.mrbt.lingmoney.service.common.impl;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.apache.commons.beanutils.BeanUtils;
import org.apache.commons.lang.StringUtils;
import org.jaxen.function.SumFunction;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.mrbt.lingmoney.mapper.GiftDetailMapper;
import com.mrbt.lingmoney.mapper.HxRedPacketMapper;
import com.mrbt.lingmoney.mapper.LingbaoGiftMapper;
import com.mrbt.lingmoney.mapper.LingbaoLotteryRatioMapper;
import com.mrbt.lingmoney.mapper.LingbaoLotteryTypeMapper;
import com.mrbt.lingmoney.mapper.UsersMapper;
import com.mrbt.lingmoney.mapper.UsersRedPacketMapper;
import com.mrbt.lingmoney.mapper.WorldCupGuessingMapper;
import com.mrbt.lingmoney.mapper.WorldCupMatchInfoMapper;
import com.mrbt.lingmoney.model.ActivityRecord;
import com.mrbt.lingmoney.model.GiftDetail;
import com.mrbt.lingmoney.model.HxRedPacket;
import com.mrbt.lingmoney.model.LingbaoExchangeInfo;
import com.mrbt.lingmoney.model.LingbaoGift;
import com.mrbt.lingmoney.model.LingbaoGiftCart;
import com.mrbt.lingmoney.model.LingbaoGiftWithBLOBs;
import com.mrbt.lingmoney.model.LingbaoLotteryType;
import com.mrbt.lingmoney.model.Users;
import com.mrbt.lingmoney.model.UsersAccount;
import com.mrbt.lingmoney.model.UsersRedPacket;
import com.mrbt.lingmoney.model.WorldCupGuessing;
import com.mrbt.lingmoney.model.WorldCupGuessingExample;
import com.mrbt.lingmoney.model.WorldCupMatchGuessVo;
import com.mrbt.lingmoney.model.WorldCupMatchInfo;
import com.mrbt.lingmoney.model.WorldCupMatchInfoExample;
import com.mrbt.lingmoney.service.common.WorldCupMatchService;
import com.mrbt.lingmoney.utils.DateUtils;
import com.mrbt.lingmoney.utils.LotteryUtil;
import com.mrbt.lingmoney.utils.PageInfo;
import com.mrbt.lingmoney.utils.ResultParame.ResultInfo;
import com.mrbt.lingmoney.utils.ResultParame.ResultNumber;

import net.sf.json.JSONObject;



@Service
public class WorldCupMatchServiceImpl implements WorldCupMatchService {
	
	@Autowired
	private WorldCupMatchInfoMapper worldCupMatchInfoMapper;
	
	@Autowired
	private WorldCupGuessingMapper worldCupGuessingMapper;
	
	@Autowired
	private LingbaoLotteryTypeMapper lingbaoLotteryTypeMapper;
	
	@Autowired
	private LingbaoLotteryRatioMapper lingbaoLotteryRatioMapper;
	
	@Autowired
	private UsersMapper usersMapper;
	
	@Autowired
	private LingbaoGiftMapper lingbaoGiftMapper;
	
	@Autowired
	private HxRedPacketMapper hxRedPacketMapper;
	
	@Autowired
	private UsersRedPacketMapper usersRedPacketMapper;
	
	@Autowired
	private GiftDetailMapper giftDetailMapper;

	@Override
	public void queryMatchInfo(PageInfo pageInfo, String uId) {
		//查询开始竞猜，并且比赛时间大于当前时间2小时的数据
		WorldCupMatchInfoExample example = new WorldCupMatchInfoExample();
		example.createCriteria().andMatchDateEqualTo(DateUtils.getDay(new Date())).andMatchTimeGreaterThan(DateUtils.addMinute(new Date(), 120));
		
		List<WorldCupMatchInfo> wInfos = worldCupMatchInfoMapper.selectByExample(example);
		
		if (wInfos != null && wInfos.size() > 0) {
			
			pageInfo.setCode(ResultInfo.SUCCESS.getCode());
			pageInfo.setMsg(ResultInfo.SUCCESS.getMsg());
			
			//存储用户竞猜的比赛场次ID，竞猜选项 1,平，2left， 3，right
			Map<Integer, Integer> guessing = new HashMap<Integer, Integer>();
			if (uId != null && !uId.equals("")) {
				//获取用户竞猜的数据
				WorldCupGuessingExample example2 = new WorldCupGuessingExample();
				example2.createCriteria().andUIdEqualTo(uId);
				
				List<WorldCupGuessing> lCupGuessings = worldCupGuessingMapper.selectByExample(example2);
				
				if (lCupGuessings != null && lCupGuessings.size() > 0) {
					for (WorldCupGuessing wcg : lCupGuessings) {
						guessing.put(wcg.getMatchId(), wcg.getGameChoice());
					}
				}
			}
			
			//处理比赛场次数据
			List<WorldCupMatchGuessVo> resList = new ArrayList<WorldCupMatchGuessVo>();
			
			for (WorldCupMatchInfo wcmi : wInfos) {
				WorldCupMatchGuessVo wcMatchGuessVo = new WorldCupMatchGuessVo();
				
				try {
					BeanUtils.copyProperties(wcMatchGuessVo, wcmi);
				} catch (Exception e) {
					e.printStackTrace();
				}
				
				//查询每个队的竞猜人数
				//查询竞猜了指定比赛场次，并且竞猜为2（队伍左胜利）的竞猜数据
				WorldCupGuessingExample leftExample = new WorldCupGuessingExample();
				leftExample.createCriteria().andMatchIdEqualTo(wcmi.getId()).andGameChoiceEqualTo(ResultNumber.TWO.getNumber());
				
				long leftCount = worldCupGuessingMapper.countByExample(leftExample);
					
				//查询竞猜了指定比赛场次，并且竞猜为3（队伍右胜利）的竞猜数据
				WorldCupGuessingExample rightExample = new WorldCupGuessingExample();
				rightExample.createCriteria().andMatchIdEqualTo(wcmi.getId()).andGameChoiceEqualTo(ResultNumber.THREE.getNumber());
				long rightCount = worldCupGuessingMapper.countByExample(rightExample);
				
				if (leftCount > 0 && rightCount > 0) {
					
					BigDecimal leftBig = new BigDecimal(leftCount + ".00");
					BigDecimal rightBig = new BigDecimal(rightCount + ".00");
					
					BigDecimal sum = leftBig.add(rightBig);
					
					wcMatchGuessVo.setLeftSupportRate(leftBig.divide(sum, BigDecimal.ROUND_HALF_UP).multiply(new BigDecimal("100")) + "%");
					wcMatchGuessVo.setRightSupportRate(rightBig.divide(sum, BigDecimal.ROUND_HALF_UP).multiply(new BigDecimal("100")) + "%");
					
				} else {
					wcMatchGuessVo.setLeftSupportRate("50.00%");
					wcMatchGuessVo.setRightSupportRate("50.00%");
				}
				
				
				//赋值用户竞猜选项
				if (guessing != null && guessing.size() > 0) {
					if (guessing.containsKey(wcMatchGuessVo.getId())) {
						wcMatchGuessVo.setGameChoice(guessing.get(wcMatchGuessVo.getId()));
					}
				}
				
				resList.add(wcMatchGuessVo);
			}
			
			pageInfo.setRows(resList);
			
		} else {
			pageInfo.setCode(ResultInfo.NO_DATA.getCode());
			pageInfo.setMsg(ResultInfo.NO_DATA.getMsg());
		}
		
		
	}

	@Override
	public void guessingCompet(PageInfo pageInfo, String uId, Integer matchId, Integer gameChoice) {
		if (uId != null && !uId.equals("")) {
			if (matchId != null && matchId > 0 && gameChoice != null && gameChoice > 0) {
				//验证是否已竞猜
				WorldCupGuessingExample worldCupGuessingExample = new WorldCupGuessingExample();
				worldCupGuessingExample.createCriteria().andUIdEqualTo(uId).andMatchIdEqualTo(matchId);
				List<WorldCupGuessing> lCupGuessings = worldCupGuessingMapper.selectByExample(worldCupGuessingExample);
				
				if (lCupGuessings != null && lCupGuessings.size() > 0) {
					pageInfo.setCode(ResultInfo.DATA_EXISTED.getCode());
					pageInfo.setMsg("您已经竞猜了该场比赛，请不要重复竞猜!");
				} else {
					//查询场次数据,验证是否可以进行竞猜
					WorldCupMatchInfoExample example = new WorldCupMatchInfoExample();
					example.createCriteria().andMatchDateEqualTo(DateUtils.getDay(new Date()))
						.andMatchTimeGreaterThan(DateUtils.addMinute(new Date(), 120)).andIdEqualTo(matchId);
					
					List<WorldCupMatchInfo> wInfos = worldCupMatchInfoMapper.selectByExample(example);
					if (wInfos != null && wInfos.size() > 0) {
						//添加用户竞猜数据
						WorldCupGuessing wcg = new WorldCupGuessing();
						wcg.setCreateTime(new Date());
						wcg.setGameChoice(gameChoice);
						wcg.setMatchId(matchId);
						wcg.setuId(uId);
						
					    int xLong = worldCupGuessingMapper.insertSelective(wcg);
					    if (xLong > 0) {
					    	pageInfo.setCode(ResultInfo.SUCCESS.getCode());
							pageInfo.setMsg("竞猜成功！");
						} else {
							pageInfo.setCode(ResultInfo.SERVER_ERROR.getCode());
							pageInfo.setMsg(ResultInfo.SERVER_ERROR.getMsg());
						}
					} else {
						pageInfo.setCode(ResultInfo.PARAM_MISS.getCode());
						pageInfo.setMsg("该场比赛不在竞猜时间内!");
					}
				}
			} else {
				pageInfo.setCode(ResultInfo.PARAM_MISS.getCode());
				pageInfo.setMsg("请选择竞猜选项!");
			}
			
		} else {
			pageInfo.setCode(ResultInfo.LOGIN_TIMEOUT.getCode());
			pageInfo.setMsg(ResultInfo.LOGIN_TIMEOUT.getMsg());
		}
	}

	@Override
	public void queryUserGuess(PageInfo pageInfo, String uId) {
		if (uId != null && !uId.equals("")) {
			//查询用户竞猜记录
			WorldCupGuessingExample example = new WorldCupGuessingExample();
			example.createCriteria().andUIdEqualTo(uId);
			
			List<WorldCupGuessing> wCupGuessings = worldCupGuessingMapper.selectByExample(example);
			if (wCupGuessings != null && wCupGuessings.size() > 0) {
				//查询比赛信息
				
				//处理比赛场次数据
				List<WorldCupMatchGuessVo> resList = new ArrayList<WorldCupMatchGuessVo>();
				
				for (WorldCupGuessing wcg : wCupGuessings) {
					WorldCupMatchInfo wCupMatchInfo = worldCupMatchInfoMapper.selectByPrimaryKey(wcg.getMatchId());
					
					if (wCupMatchInfo != null) {
						WorldCupMatchGuessVo wcmgVo = new WorldCupMatchGuessVo();
						
						try {
							BeanUtils.copyProperties(wcmgVo, wCupMatchInfo);
						} catch (Exception e) {
							e.printStackTrace();
						}
						
						wcmgVo.setGameChoice(wcg.getGameChoice());
						
						resList.add(wcmgVo);
					}
				}
				
				pageInfo.setCode(ResultInfo.SUCCESS.getCode());
				pageInfo.setMsg(ResultInfo.SUCCESS.getMsg());
				pageInfo.setRows(resList);
				
			} else {
				pageInfo.setCode(ResultInfo.NO_DATA.getCode());
				pageInfo.setMsg(ResultInfo.NO_DATA.getMsg());
			}
		} else {
			pageInfo.setCode(ResultInfo.LOGIN_FAILURE.getCode());
			pageInfo.setMsg(ResultInfo.LOGIN_FAILURE.getMsg());
		}
	}

	@Override
	public PageInfo validateChoujiang(String uid, int typeID, PageInfo pageInfo) {
		
		if (uid == null || uid.equals("")) {
			pageInfo.setCode(ResultInfo.LOGIN_FAILURE.getCode());
			pageInfo.setMsg(ResultInfo.LOGIN_FAILURE.getMsg());
			return pageInfo;
		}
		
		// 验证抽奖活动
		LingbaoLotteryType llt = lingbaoLotteryTypeMapper.selectByPrimaryKey(typeID);
		long now = new Date().getTime();
		if (null == llt || llt.getStatus() == 0 || llt.getStartTime().getTime() > now
				|| llt.getEndTime().getTime() < now) {
			
			pageInfo.setCode(ResultInfo.ACTIVITY_NOT_LINE.getCode());
			pageInfo.setMsg("该活动已下线");
			return pageInfo;
		}
		
		Users users = usersMapper.selectByPrimaryKey(uid);
		if (users == null) {
			pageInfo.setCode(ResultInfo.ACCOUNT_NUMBER_NON_EXIST.getCode());
			pageInfo.setMsg(ResultInfo.ACCOUNT_NUMBER_NON_EXIST.getMsg());
			return pageInfo;
			
		}

		//验证抽奖次数
		WorldCupGuessingExample example = new WorldCupGuessingExample();
		example.createCriteria().andUIdEqualTo(uid).andLuckStatusEqualTo(ResultNumber.ZERO.getNumber());
		List<WorldCupGuessing> wcgList = worldCupGuessingMapper.selectByExample(example);
		if (wcgList == null || wcgList.size() == 0) {
			pageInfo.setCode(ResultInfo.NO_DATA.getCode());
			pageInfo.setMsg("您的抽奖次数已经用完!");
			return pageInfo;
		}

		// 验证通过，进行抽奖
		List<Map<String, Object>> list = lingbaoLotteryRatioMapper.queryByTypeIdForCount(llt.getId());

		List<Double> ratioList = new ArrayList<Double>();
		for (Map<String, Object> map : list) {
			ratioList.add((Double) map.get("ratio"));
		}

		LotteryUtil lu = new LotteryUtil(ratioList);
		int item = lu.randomColunmIndex();
		if (item == -1) {
			throw new IllegalArgumentException("概率集合设置不合理！");
		}
		
		Map<String, Object> giftInfo = list.get(item);
		
		// 如果中奖
		if (giftInfo.containsKey("giftId") && giftInfo.get("giftId") != null) {
			Integer giftId = (Integer) giftInfo.get("giftId");//礼品id
			LingbaoGift lingbaoGift = lingbaoGiftMapper.selectByPrimaryKey(giftId);
			if (lingbaoGift != null) {
				// 插入一条中奖纪录
				GiftDetail giftDetail = new GiftDetail();
				giftDetail.setuId(uid);
				giftDetail.setShare(1);
				giftDetail.setState(3);
				giftDetail.setCreateTime(new Date());
				giftDetail.setgName((String) giftInfo.get("name"));
				giftDetail.setLingbaoGiftId(giftId);
				giftDetail.setgDesc("世界杯竞猜抽奖活动");

				// 如果是加息券，直接添加到用户账户
				if (giftInfo.containsKey("typename")) {
					// 类型。0实物礼品 1虚拟礼品 2领宝 3加息券
					Integer typename = (Integer) giftInfo.get("typename");
					
					pageInfo.setObj(giftInfo.get("id"));
					
					if (typename == 3) {
						giftDetail.setState(4);
						
						// 获取加息券数据
						String redpacketId = lingbaoGift.getRedpacketId();
						if (!StringUtils.isEmpty(redpacketId)) {
							// 验证加息券状态是否有效
							HxRedPacket hxRedPacket = hxRedPacketMapper.selectByPrimaryKey(redpacketId);
							if (hxRedPacket != null && hxRedPacket.getStatus() == 1) {
								UsersRedPacket usersRedPacket = new UsersRedPacket();
								usersRedPacket.setuId(uid);
								usersRedPacket.setCreateTime(new Date());
								usersRedPacket.setRpId(redpacketId);
								usersRedPacket.setStatus(0);
								
								if (hxRedPacket.getDelayed() != null && hxRedPacket.getDelayed() > 0) {
									usersRedPacket.setValidityTime(DateUtils.addDay(new Date(), hxRedPacket.getDelayed()));
								} else {
									usersRedPacket.setValidityTime(hxRedPacket.getValidityTime());
								}
								
								usersRedPacketMapper.insertSelective(usersRedPacket);
							}
						}
						pageInfo.setMsg("恭喜您抽中了" + giftDetail.getgName() + "请在我的-优惠券中查看。");
					} else {
						pageInfo.setMsg("恭喜您抽中了" + giftDetail.getgName() + "已存入您的兑换列表中。活动结束后客户会与您联系！");
					}
					
					//减少抽奖次数
					
					WorldCupGuessing sCupGuessing = wcgList.get(0);
					
					WorldCupGuessing uCupGuessing = new WorldCupGuessing();
					uCupGuessing.setId(sCupGuessing.getId());
					uCupGuessing.setLuckStatus(ResultNumber.ONE.getNumber());
					uCupGuessing.setDrawInfo(giftDetail.getgName());
					
					worldCupGuessingMapper.updateByPrimaryKeySelective(uCupGuessing);
					
					giftDetailMapper.insertSelective(giftDetail);
					pageInfo.setCode(ResultInfo.SUCCESS.getCode());
				} else {
					pageInfo.setCode(ResultInfo.NO_DATA.getCode());
					pageInfo.setMsg("没有抽中，再接再厉！");
				}
			} else {
				pageInfo.setCode(ResultInfo.NO_DATA.getCode());
				pageInfo.setMsg("没有抽中，再接再厉！");
			}
		} else {
			pageInfo.setCode(ResultInfo.NO_DATA.getCode());
			pageInfo.setMsg("没有抽中，再接再厉！");
		}
		
		return pageInfo;
	}
	
	@Override
	public void validateChoujiangCount(String uid, int typeID, PageInfo pageInfo) {
		if (StringUtils.isBlank(uid)) {
			pageInfo.setCode(ResultInfo.LOGIN_FAILURE.getCode());
			pageInfo.setMsg(ResultInfo.LOGIN_FAILURE.getMsg());
		} else {
			if (typeID == 4) {
				//验证抽奖次数
				WorldCupGuessingExample example = new WorldCupGuessingExample();
				example.createCriteria().andUIdEqualTo(uid).andLuckStatusEqualTo(ResultNumber.ZERO.getNumber());
				
				long count = worldCupGuessingMapper.countByExample(example);
				
				if (count > 0) {
					pageInfo.setCode(ResultInfo.SUCCESS.getCode());
					pageInfo.setMsg(ResultInfo.SUCCESS.getMsg());
					pageInfo.setObj(count);
				} else {
					pageInfo.setCode(ResultInfo.NO_DATA.getCode());
					pageInfo.setMsg("您的抽奖次数已经用完!");
				}
				
			} else {
				pageInfo.setCode(ResultInfo.PARAM_MISS.getCode());
				pageInfo.setMsg(ResultInfo.PARAM_MISS.getMsg());
			}
		}
	}

	@Override
	public void winningCount(PageInfo pageInfo, String uId) {
		if (uId != null && !uId.equals("")) {
			//查询用户获奖记录
			WorldCupGuessingExample example = new WorldCupGuessingExample();
			example.createCriteria().andUIdEqualTo(uId).andLuckStatusEqualTo(ResultNumber.ONE.getNumber());
			
			List<WorldCupGuessing> list = worldCupGuessingMapper.selectByExample(example);
			
			if (list != null && list.size() > 0) {
				//累计每个奖项的个数
				Map<String, Integer> countMap = new HashMap<String, Integer>();
				for (WorldCupGuessing worldCupGuessing : list) {
					if (countMap.containsKey(worldCupGuessing.getDrawInfo())) {
						countMap.put(worldCupGuessing.getDrawInfo(), countMap.get(worldCupGuessing.getDrawInfo()) + 1);
					} else {
						countMap.put(worldCupGuessing.getDrawInfo(), 1);
					}
				}
				
				List<Map<String, String>> resList = new ArrayList<Map<String, String>>();
				for (String key: countMap.keySet()) {
					Map<String, String> resMap = new HashMap<String, String>();
					resMap.put("name", key);
					resMap.put("num", countMap.get(key) + "");
					resList.add(resMap);
				}
				pageInfo.setCode(ResultInfo.SUCCESS.getCode());
				pageInfo.setMsg(ResultInfo.SUCCESS.getMsg());
				pageInfo.setRows(resList);
			} else {
				pageInfo.setCode(ResultInfo.NO_DATA.getCode());
				pageInfo.setMsg("没有进行抽奖");
			}
		} else {
			pageInfo.setCode(ResultInfo.LOGIN_TIMEOUT.getCode());
			pageInfo.setMsg(ResultInfo.LOGIN_TIMEOUT.getMsg());
		}
	}

}
