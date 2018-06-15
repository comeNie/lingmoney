package com.mrbt.lingmoney.admin.service.worldCup.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.mrbt.lingmoney.admin.service.worldCup.WorldCupMatchService;
import com.mrbt.lingmoney.mapper.WorldCupGuessingMapper;
import com.mrbt.lingmoney.mapper.WorldCupMatchInfoMapper;
import com.mrbt.lingmoney.model.WorldCupGuessing;
import com.mrbt.lingmoney.model.WorldCupGuessingExample;
import com.mrbt.lingmoney.model.WorldCupMatchInfo;
import com.mrbt.lingmoney.model.WorldCupMatchInfoExample;
import com.mrbt.lingmoney.utils.GridPage;
import com.mrbt.lingmoney.utils.PageInfo;
import com.mrbt.lingmoney.utils.ResultParame.ResultNumber;

@Service
public class WorldCupMatchServiceImpl implements WorldCupMatchService {
	
	@Autowired
	private WorldCupMatchInfoMapper worldCupMatchInfoMapper;
	
	@Autowired
	private WorldCupGuessingMapper worldCupGuessingMapper;

	@Override
	public GridPage<WorldCupMatchInfo> listGrid(PageInfo pageInfo) {
		WorldCupMatchInfoExample example = new WorldCupMatchInfoExample();
		example.setLimitStart(pageInfo.getFrom());
		example.setLimitEnd(pageInfo.getSize());
		GridPage<WorldCupMatchInfo> result = new GridPage<WorldCupMatchInfo>();
		result.setRows(worldCupMatchInfoMapper.selectByExample(example));
		result.setTotal(worldCupMatchInfoMapper.countByExample(example));
		return result;
	}

	@Override
	public int push(Integer id) {
		WorldCupMatchInfo worldCupMatchInfo = new WorldCupMatchInfo();
		worldCupMatchInfo.setId(id);
		worldCupMatchInfo.setGuessStatus(ResultNumber.ONE.getNumber());
		return worldCupMatchInfoMapper.updateByPrimaryKeySelective(worldCupMatchInfo);
	}

	@Override
	public int saveAndUpdate(WorldCupMatchInfo vo) {
		if (vo.getLeftScore() == vo.getRightScore()) {
			vo.setGameResult(1);//平局
		} else if (vo.getLeftScore() > vo.getRightScore()) {
			vo.setGameResult(2);//left队胜利
		} else {
			vo.setGameResult(3);//right队胜利
		}
		
		//查询竞猜了该场比赛的信息
		WorldCupGuessingExample example = new WorldCupGuessingExample();
		example.createCriteria().andMatchIdEqualTo(vo.getId()).andGameChoiceEqualTo(vo.getGameResult());
		List<WorldCupGuessing> wCupGuessings = worldCupGuessingMapper.selectByExample(example);
		for (WorldCupGuessing wcg : wCupGuessings) {
			WorldCupGuessing upWcg = new WorldCupGuessing();
			upWcg.setId(wcg.getId());
			upWcg.setLuckStatus(ResultNumber.ZERO.getNumber());
			
			worldCupGuessingMapper.updateByPrimaryKeySelective(upWcg);
		}
		
		return worldCupMatchInfoMapper.updateByPrimaryKeySelective(vo);
	}

}
