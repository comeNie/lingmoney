package com.mrbt.lingmoney.activity.service.impl;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.mrbt.lingmoney.activity.service.RedeemCode2017Service;
import com.mrbt.lingmoney.mapper.ActivityRecordMapper;
import com.mrbt.lingmoney.mapper.RedeemCode20171225Mapper;
import com.mrbt.lingmoney.model.ActivityRecord;
import com.mrbt.lingmoney.model.RedeemCode20171225;
import com.mrbt.lingmoney.model.RedeemCode20171225Example;
import com.mrbt.lingmoney.utils.PageInfo;
import com.mrbt.lingmoney.utils.ResultParame.ResultInfo;

@Service
public class RedeemCode2017ServiceImpl implements RedeemCode2017Service {
	
	@Autowired
	private RedeemCode20171225Mapper redeemCode20171225Mapper;
	
	@Autowired
	private ActivityRecordMapper activityRecordMapper;
	
	/**
	 * 用户兑换操作
	 * @param uid 用户id
	 * @param redeemCode	兑换码
	 * @return	兑换结果
	 */
	@Override
    public PageInfo userReddemOper(String uid, String redeemCode) {
		PageInfo pageInfo = new PageInfo();
		
		//判断兑换码是否存在
		RedeemCode20171225Example example = new RedeemCode20171225Example();
		example.createCriteria().andRedeemCodeEqualTo(redeemCode);
		
		List<RedeemCode20171225> resList = redeemCode20171225Mapper.selectByExample(example);
		if(resList != null && resList.size() > 0) {
			RedeemCode20171225 redeemInfo = resList.get(0);
			
			if (redeemInfo.getStatus() > 0) {
                pageInfo.setCode(ResultInfo.FORMAT_PARAMS_ERROR.getCode());
				pageInfo.setMsg("兑换码已经被使用");
			} else {
				
				//更新兑换码表中的用户ID，和状态，兑换时间,通过数据ID进行更新
				RedeemCode20171225 updateRedeem = new RedeemCode20171225();
				updateRedeem.setId(redeemInfo.getId());
				updateRedeem.setuId(uid);
				updateRedeem.setStatus(1);
				updateRedeem.setUseTime(new Date());
				
				redeemCode20171225Mapper.updateByPrimaryKeySelective(updateRedeem);
				
                if (redeemInfo.getTypeId() == 4 || redeemInfo.getTypeId() == 5) {
                    int lingbaoCount = Integer.parseInt(redeemInfo.getTypeName());
                    //兑换领宝,增加领宝
					Map<String, Object> map = new HashMap<String, Object>();
                    map.put("uId", uid);
                    map.put("snum", lingbaoCount);
					redeemCode20171225Mapper.appendLingbao(map);
					
					//插入领宝流水
					ActivityRecord record = new ActivityRecord();
                    record.setuId(uid);
					record.setName("兑换");
                    record.setContent("兑换码兑换");
                    record.setAmount(lingbaoCount);
					record.setType(0);
					record.setUseDate(new Date());
					record.setStatus(1);
					
					activityRecordMapper.insert(record);
					
                    pageInfo.setCode(ResultInfo.SUCCESS.getCode());
                    pageInfo.setMsg("兑换成功！" + lingbaoCount + "领宝已发放至您的领钱儿账户！请下载APP查看");
				} else {
                    pageInfo.setCode(ResultInfo.SUCCESS.getCode());
					pageInfo.setMsg(redeemInfo.getTypeName() + "兑换成功！领钱儿客服将在3个工作日内与您联系");
				}
			}
		}else {
            pageInfo.setCode(ResultInfo.EMPTY_DATA.getCode());
			pageInfo.setMsg("兑换码不存在");
		}
		return pageInfo;
	}

}
