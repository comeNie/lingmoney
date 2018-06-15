package com.mrbt.lingmoney.admin.controller.redPacket;

import java.math.BigDecimal;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.mrbt.lingmoney.admin.service.redPacket.RedPacketService;
import com.mrbt.lingmoney.mapper.TradingMapper;
import com.mrbt.lingmoney.model.Trading;
import com.mrbt.lingmoney.model.TradingExample;
import com.mrbt.lingmoney.utils.PageInfo;
import com.mrbt.lingmoney.utils.ResultParame.ResultNumber;

/**
 * @author suyibo
 * @date 创建时间：2017年11月2日
 */
@Controller
@RequestMapping("/redPacket")
public class RedPacketController {

	@Autowired
	private RedPacketService redPacketService;
	
	@Autowired
	private TradingMapper tradingMapper;

	/**
	 * 红包奖励
	 * @param userId	用户ID
	 * @param actType	活动类型
	 * @param amount	金额或百分百
	 */
	public void rewardRedPackage(String userId, Integer actType, Double amount) {

		try {
			// 存在执行红包奖励操作
			redPacketService.rewardRedPackage(userId, actType, amount);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * 产品成立后赠送红包或加息券
	 * @param productId	产品ID
	 */
	public void rewardRedPackageForProduct(Integer productId) {

		try {
			TradingExample example = new TradingExample();
			example.createCriteria().andStatusEqualTo(ResultNumber.TWO.getNumber()).andPIdEqualTo(productId);
			List<Trading> listTrd =  tradingMapper.selectByExample(example);
			
			for (Trading trading : listTrd) {
				//送加息券
				redPacketService.rewardRedPackage(trading.getuId(), ResultNumber.FOUR.getNumber(), null);
				//送返现红包
				redPacketService.rewardRedPackage(trading.getuId(), ResultNumber.FOUR.getNumber(), null);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * 活动红包专属接口
	 * @param userId 推荐人id
	 * @param money 赠送金额
	 * @return 信息返回
	 * 
	 */
	public PageInfo activityRedPacketReward(String userId, BigDecimal money) {
		PageInfo pageInfo = new PageInfo();
		try {
			pageInfo = redPacketService.activityRedPacketReward(userId, money);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return pageInfo;
	}

}
