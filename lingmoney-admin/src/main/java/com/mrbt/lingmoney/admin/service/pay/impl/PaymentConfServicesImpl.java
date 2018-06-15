package com.mrbt.lingmoney.admin.service.pay.impl;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.mrbt.lingmoney.admin.service.pay.PaymentConfServices;
import com.mrbt.lingmoney.mapper.PaymentPartitionMapper;
import com.mrbt.lingmoney.model.PaymentPartition;
import com.mrbt.lingmoney.model.PaymentPartitionExample;
import com.mrbt.lingmoney.utils.ResultParame;

/**
 * 
 * 企业管理——》兑付确认
 *
 */
@Service
public class PaymentConfServicesImpl implements PaymentConfServices {
	
	@Autowired
	private PaymentPartitionMapper paymentPartitionMapper;

	/**
	 * 查询需要确认兑付的数据
	 */
	@Override
	public List<PaymentPartition> paymentList() {
		PaymentPartitionExample example = new PaymentPartitionExample();
		PaymentPartitionExample.Criteria cri = example.createCriteria();
		cri.andSubmitEqualTo(0).andStatusNotEqualTo(ResultParame.ResultNumber.MINUS_ONE.getNumber());
		return paymentPartitionMapper.selectByExample(example);
	}

	/**
	 * 确认兑付
	 */
	@Override
	public Integer paymentSubmission(String idStr) {
		List<Integer> list = new ArrayList<Integer>();
		String[] ids = idStr.split(",");
		for (int i = 0; i < ids.length; i++) {
			list.add(Integer.parseInt(ids[i]));
		}
		PaymentPartitionExample example = new PaymentPartitionExample();
		PaymentPartitionExample.Criteria cri = example.createCriteria();
		cri.andIdIn(list).andStatusNotEqualTo(ResultParame.ResultNumber.MINUS_ONE.getNumber());
		PaymentPartition record = new PaymentPartition();
		record.setSubmit(1);
		return paymentPartitionMapper.updateByExampleSelective(record, example);
	}
	
}
