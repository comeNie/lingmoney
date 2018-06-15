package com.mrbt.lingmoney.service.web.impl;

import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.mrbt.lingmoney.mapper.ActivityMapper;
import com.mrbt.lingmoney.mapper.ActivityRecordMapper;
import com.mrbt.lingmoney.mapper.ExchangeCodeMapper;
import com.mrbt.lingmoney.mapper.UsersAccountMapper;
import com.mrbt.lingmoney.model.Activity;
import com.mrbt.lingmoney.model.ActivityRecord;
import com.mrbt.lingmoney.model.ExchangeCode;
import com.mrbt.lingmoney.model.ExchangeCodeExample;
import com.mrbt.lingmoney.model.UsersAccount;
import com.mrbt.lingmoney.service.web.ExchangeCodeService;

/**
 * @author syb
 * @date 2017年5月16日 下午3:48:04
 * @version 1.0
 * @description
 **/
@Service
public class ExchangeCodeServiceImpl implements ExchangeCodeService {
	@Autowired
	private ExchangeCodeMapper exchangeCodeMapper;
	@Autowired
	private ActivityMapper activityMapper;
	@Autowired
	private ActivityRecordMapper activityRecordMapper;
	@Autowired
	private UsersAccountMapper usersAccountMapper;

	@Override
	public List<ExchangeCode> findByCode(String code, int status) {
		ExchangeCodeExample example = new ExchangeCodeExample();
		example.createCriteria().andCodeEqualTo(code).andStatusEqualTo(status);
		return exchangeCodeMapper.selectByExample(example);
	}

	@Override
	public void exchangeGift(String code, String uid) {
		List<ExchangeCode> list = findByCode(code, 0);
		if (null != list && list.size() > 0) {
			ExchangeCode exchangeCode = list.get(0);
			exchangeCode.setStatus(1);
			exchangeCodeMapper.updateByPrimaryKeySelective(exchangeCode);

            int aId = exchangeCode.getaId(); // 活动id
			Activity activity = activityMapper.selectByPrimaryKey(aId);
            int amount = exchangeCode.getAmount(); // 活动金额(50领宝等)
			if (activity != null) {
				ActivityRecord activityRecord = new ActivityRecord();
				activityRecord.setAmount(amount);
				activityRecord.setContent(activity.getContent());
				activityRecord.setName(activity.getName());
				activityRecord.setStatus(0);
				activityRecord.setType(activity.getType());
				activityRecord.setuId(uid);
				activityRecord.setValidity(activity.getValidity());
				activityRecord.setUseDate(new Date());
				activityRecordMapper.insertSelective(activityRecord);

				UsersAccount usersAccount = usersAccountMapper.selectByUid(uid);
				usersAccount.setCountLingbao(usersAccount.getCountLingbao() + amount);
				usersAccountMapper.updateByPrimaryKeySelective(usersAccount);
			}
		}
	}

}
