package com.mrbt.lingmoney.web.testNg;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import com.mrbt.lingmoney.mapper.UsersPropertiesMapper;
import com.mrbt.lingmoney.mapper.WorldCupScoreMapper;
import com.mrbt.lingmoney.model.WorldCupScore;
import com.mrbt.lingmoney.model.WorldCupScoreExample;
import com.mrbt.lingmoney.service.common.WorldCupActivityService;
import com.mrbt.lingmoney.utils.PageInfo;

/**
 * 查询用户绑卡情况计划任务
 * @author Administrator
 *
 */
public class QueryAccountBindCard extends SpringTestNG {
	
	@Autowired
	private WorldCupActivityService worldCupActivityService;
	
	@Autowired
	private WorldCupScoreMapper worldCupScoreMapper;

	/**
	 * 初始化
	 */
	@BeforeClass
	public void setUp() {
		LOGGER.info("初始化, 查询用户绑卡情况计划任务");
	}
	
	/**
	 * 执行测试
	 */
	@Test
	public void testAdd2() {
		PageInfo pageInfo = new PageInfo();
//		String sheet = "1:A;2:A;3:A;4:A;5:A;6:A;7:A;8:A;9:A;10:A";
//		worldCupActivityService.calculatedFraction(sheet, pageInfo);
		
		String phone = "13683173295";
		Integer score = 0;
//		worldCupActivityService.saveScoreByPhone(phone, score, pageInfo);
		
		WorldCupScoreExample example = new WorldCupScoreExample();
		example.createCriteria().andPhoneEqualTo(phone);
		
		List<WorldCupScore> aCupScores = worldCupScoreMapper.selectByExample(example);
		System.out.println(aCupScores);
	}

}
