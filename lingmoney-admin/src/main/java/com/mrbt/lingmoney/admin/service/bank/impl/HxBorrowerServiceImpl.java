package com.mrbt.lingmoney.admin.service.bank.impl;

import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.mrbt.lingmoney.admin.service.bank.HxBorrowerService;
import com.mrbt.lingmoney.bank.eaccount.HxModifyTradingPassword;
import com.mrbt.lingmoney.mapper.HxAccountMapper;
import com.mrbt.lingmoney.mapper.HxBorrowerCustomerMapper;
import com.mrbt.lingmoney.mapper.HxBorrowerInfoMapper;
import com.mrbt.lingmoney.mapper.HxBorrowerMapper;
import com.mrbt.lingmoney.model.HxAccount;
import com.mrbt.lingmoney.model.HxAccountExample;
import com.mrbt.lingmoney.model.HxBorrower;
import com.mrbt.lingmoney.model.HxBorrowerCustomer;
import com.mrbt.lingmoney.model.HxBorrowerExample;
import com.mrbt.lingmoney.model.HxBorrowerInfoExample;
import com.mrbt.lingmoney.utils.PageInfo;
import com.mrbt.lingmoney.utils.PropertiesUtil;
import com.mrbt.lingmoney.utils.ResultParame.ResultInfo;
import com.mrbt.lingmoney.utils.ResultParame.ResultNumber;

/**
 * 
 * @author Administrator
 *
 */
@Service
public class HxBorrowerServiceImpl implements HxBorrowerService {
	@Autowired
	private HxBorrowerMapper hxBorrowerMapper;
	@Autowired
	private HxBorrowerInfoMapper hxBorrowerInfoMapper;
	@Autowired
	private HxBorrowerCustomerMapper hxBorrowerCustomerMapper;
	@Autowired
	private HxAccountMapper hxAccountMapper;
	
	@Autowired
	private HxModifyTradingPassword hxModifyTradingPassword;
	
	private static final String BANKURL = PropertiesUtil.getPropertiesByKey("BANK_POST_URL");

	@Transactional
	@Override
	public int save(HxBorrower vo) {
		// 每个E账户最多添加一次，从库中能查到该accid则失败
		HxBorrowerExample hxBorrowerExample = new HxBorrowerExample();
		hxBorrowerExample.createCriteria().andAccIdEqualTo(vo.getAccId()).andStatusIn(Arrays.asList(0, 1));
		long count = hxBorrowerMapper.countByExample(hxBorrowerExample);
		if (count > 0) {
			return ResultNumber.MINUS2.getNumber();
		}
		vo.setId(UUID.randomUUID().toString().replace("-", ""));
		vo.setStatus(0);
		return hxBorrowerMapper.insert(vo);
	}

	@Transactional
	@Override
	public int update(HxBorrower vo) {
		// 每个E账户最多添加一次，从库中能查到该accid并且不等于当前编辑的accid则失败，过滤当前编辑的记录
		HxBorrowerExample hxBorrowerExample = new HxBorrowerExample();
		hxBorrowerExample.createCriteria().andAccIdEqualTo(vo.getAccId()).andStatusIn(Arrays.asList(0, 1))
				.andAccIdNotEqualTo(hxBorrowerMapper.selectByPrimaryKey(vo.getId()).getAccId());
		long count = hxBorrowerMapper.countByExample(hxBorrowerExample);
		if (count > 0) {
			return ResultNumber.MINUS2.getNumber();
		}

		// 如果该借款人已经有标的信息，不可修改
		HxBorrowerInfoExample hxBorrowerInfoExmp = new HxBorrowerInfoExample();
		hxBorrowerInfoExmp.createCriteria().andBwIdEqualTo(vo.getId());
		count = hxBorrowerInfoMapper.countByExample(hxBorrowerInfoExmp);
		if (count > 0) {
			return ResultNumber.MINUS3.getNumber();
		}
		return hxBorrowerMapper.updateByPrimaryKeySelective(vo);
	}

	@Override
	public PageInfo getList(PageInfo pageInfo) {
		int resSize = hxBorrowerCustomerMapper.findCountByCondition(pageInfo);
		List<HxBorrowerCustomer> list = hxBorrowerCustomerMapper.findByCondition(pageInfo);
		pageInfo.setRows(list);
		pageInfo.setTotal(resSize);
		return pageInfo;
	}

	@Override
	public PageInfo changeStatus(String id) {
		PageInfo pageInfo = new PageInfo();
		try {
			if (StringUtils.isNotBlank(id)) {
				HxBorrower record = hxBorrowerMapper.selectByPrimaryKey(id);
				if (record != null) {
					if (record.getStatus() == null || record.getStatus() == 0) { // 不可用，设置为可用。
						record.setStatus(1);
					} else { // 可用，设置为不可用。
						record.setStatus(0);
					}
					hxBorrowerMapper.updateByPrimaryKeySelective(record);
					pageInfo.setCode(ResultInfo.SUCCESS.getCode());
					return pageInfo;
				}
			}
			pageInfo.setCode(ResultInfo.EMPTY_DATA.getCode());
			pageInfo.setMsg(ResultInfo.EMPTY_DATA.getMsg());
		} catch (Exception e) {
			e.printStackTrace();
			pageInfo.setCode(ResultInfo.SERVER_ERROR.getCode());
			pageInfo.setMsg(ResultInfo.SERVER_ERROR.getMsg());
		}
		return pageInfo;
	}

	@Transactional
	@Override
	public PageInfo delete(String id) {
		PageInfo pageInfo = new PageInfo();
		HxBorrower hxBorrower = hxBorrowerMapper.selectByPrimaryKey(id);
		if (hxBorrower == null) {
			pageInfo.setCode(ResultInfo.EMPTY_DATA.getCode());
			pageInfo.setMsg(ResultInfo.EMPTY_DATA.getMsg());
			return pageInfo;
		}
		HxBorrowerInfoExample example = new HxBorrowerInfoExample();
		example.createCriteria().andBwIdEqualTo(id);
		long count = hxBorrowerInfoMapper.countByExample(example);
		if (count > 0) {
			pageInfo.setCode(ResultInfo.DO_FAIL.getCode());
			pageInfo.setMsg("该借款人已借款，不可删除");
			return pageInfo;
		}
		hxBorrower.setStatus(ResultNumber.MINUS_ONE.getNumber());
		int result = hxBorrowerMapper.updateByPrimaryKeySelective(hxBorrower);
		if (result > 0) {
			pageInfo.setCode(ResultInfo.SUCCESS.getCode());
			pageInfo.setMsg(ResultInfo.SUCCESS.getMsg());
			return pageInfo;
		} else {
			pageInfo.setCode(ResultInfo.DO_FAIL.getCode());
			pageInfo.setMsg("删除失败");
			return pageInfo;
		}
	}

	@Override
	public PageInfo getList(HxAccount vo, PageInfo pageInfo) {
		HxAccountExample example = new HxAccountExample();
		HxAccountExample.Criteria cri = example.createCriteria();
		if (StringUtils.isNotBlank(vo.getAcName())) {
			cri.andAcNameLike("%" + vo.getAcName() + "%");
		}
		if (StringUtils.isNotBlank(vo.getMobile())) {
			cri.andMobileLike("%" + vo.getMobile() + "%");
		}
		if (vo.getStatus() != null) {
			cri.andStatusEqualTo(vo.getStatus());
		}
		example.setLimitStart(pageInfo.getFrom());
		example.setLimitEnd(pageInfo.getSize());

		int resSize = (int) hxAccountMapper.countByExample(example);
		List<HxAccount> list = hxAccountMapper.selectByExample(example);
		pageInfo.setCode(ResultInfo.SUCCESS.getCode());
		pageInfo.setRows(list);
		pageInfo.setTotal(resSize);
		return pageInfo;
	}

	@Override
	public PageInfo getHxAccountName(String accId) {
		PageInfo pageInfo = new PageInfo();
		if (StringUtils.isNotBlank(accId)) {
			HxAccount hxAccount = hxAccountMapper.selectByPrimaryKey(accId);
			if (hxAccount != null) {
				String acName = hxAccount.getAcName();
				if (StringUtils.isNotBlank(acName)) {
					pageInfo.setCode(ResultInfo.SUCCESS.getCode());
					pageInfo.setObj(acName);
					return pageInfo;
				}
			}
		}
		pageInfo.setCode(ResultInfo.EMPTY_DATA.getCode());
		pageInfo.setMsg(ResultInfo.EMPTY_DATA.getMsg());
		return pageInfo;
	}

	@Override
	public PageInfo checkIsBw(String bwId) {
		PageInfo pageInfo = new PageInfo();
		if (StringUtils.isNotBlank(bwId)) {
			HxBorrowerInfoExample example = new HxBorrowerInfoExample();
			example.createCriteria().andBwIdEqualTo(bwId);
			long count = hxBorrowerInfoMapper.countByExample(example);
			if (count > 0) {
				pageInfo.setCode(ResultInfo.SUCCESS.getCode());
				pageInfo.setObj(count);
				return pageInfo;
			}
		}
		pageInfo.setCode(ResultInfo.EMPTY_DATA.getCode());
		pageInfo.setMsg(ResultInfo.EMPTY_DATA.getMsg());
		return pageInfo;
	}

	@Override
	public PageInfo accountManager(String clientType, String acNo) {
		PageInfo pageInfo = new PageInfo();
		Map<String, String> resultMap = hxModifyTradingPassword.requestHxModifyTradingPassword(0, clientType, acNo, "", "");
		resultMap.put("bankUrl", BANKURL);
		pageInfo.setResultInfo(ResultInfo.SUCCESS);
		pageInfo.setObj(resultMap);
		return pageInfo;
	}
}
