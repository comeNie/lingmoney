package com.mrbt.lingmoney.admin.service.bank.impl;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import com.mrbt.lingmoney.admin.service.bank.HxBiddingService;
import com.mrbt.lingmoney.commons.exception.PageInfoException;
import com.mrbt.lingmoney.mapper.CustomQueryMapper;
import com.mrbt.lingmoney.mapper.HxBidderCustomerMapper;
import com.mrbt.lingmoney.mapper.HxBiddingCustomerMapper;
import com.mrbt.lingmoney.mapper.HxBiddingMapper;
import com.mrbt.lingmoney.mapper.ProductMapper;
import com.mrbt.lingmoney.model.HxBidderCustomer;
import com.mrbt.lingmoney.model.HxBidding;
import com.mrbt.lingmoney.model.HxBiddingCustomer;
import com.mrbt.lingmoney.model.Product;
import com.mrbt.lingmoney.model.ProductExample;
import com.mrbt.lingmoney.model.ProductWithBLOBs;
import com.mrbt.lingmoney.utils.AppCons;
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
public class HxBiddingServiceImpl implements HxBiddingService {
	@Autowired
	private HxBiddingMapper hxBiddingMapper;
	@Autowired
	private HxBidderCustomerMapper hxBidderCustomerMapper;
	@Autowired
	private HxBiddingCustomerMapper hxBiddingCustomerMapper;
	@Autowired
	private ProductMapper productMapper;
    @Autowired
    private CustomQueryMapper customQueryMapper;

	@Transactional
	@Override
	public void save(HxBidding vo) {
		hxBiddingMapper.insert(vo);
	}

	@Transactional
	@Override
	public void update(HxBidding vo) {
		hxBiddingMapper.updateByPrimaryKeySelective(vo);
	}

	@Override
	public PageInfo getBiddingList(PageInfo pageInfo) {
		int resSize = hxBiddingCustomerMapper.findCountByCondition(pageInfo);
		List<HxBiddingCustomer> list = hxBiddingCustomerMapper.findByCondition(pageInfo);
		pageInfo.setRows(list);
		pageInfo.setTotal(resSize);
		pageInfo.setCode(ResultInfo.SUCCESS.getCode());
		return pageInfo;
	}

	@Override
	public PageInfo getBidderList(PageInfo pageInfo) {
		int resSize = hxBidderCustomerMapper.findCountByCondition(pageInfo);
		List<HxBidderCustomer> list = hxBidderCustomerMapper.findByCondition(pageInfo);
		pageInfo.setRows(list);
		pageInfo.setTotal(resSize);
		return pageInfo;
	}

	@Override
	public PageInfo manualEstablish(String pCode) throws PageInfoException {
		PageInfo pageInfo = new PageInfo();

		if (StringUtils.isEmpty(pCode)) {
			pageInfo.setCode(ResultInfo.PARAM_MISS.getCode());
			pageInfo.setMsg(ResultInfo.PARAM_MISS.getMsg());
			return pageInfo;
		}

		String percentOfBidding = PropertiesUtil.getPropertiesByKey("AVAILABLE_BIDDING_PERCENT");

		ProductExample example = new ProductExample();
		example.createCriteria().andCodeEqualTo(pCode);
		List<Product> productList = productMapper.selectByExample(example);
		if (productList != null && productList.size() > 0) {
			Product product = productList.get(0);
			if (!product.getStatus().equals(AppCons.PRODUCT_STATUS_READY)) {
				pageInfo.setCode(ResultInfo.DO_FAIL.getCode());
				pageInfo.setMsg("该项目不在筹集期，无法成立");
				return pageInfo;
			}

			// 判断产品募集额度是否达标 已筹集金额/募集金额 保留4位小数
            BigDecimal reachMoney = customQueryMapper.queryValidTradingMoney(product.getId());
            BigDecimal reachedPercent = reachMoney.divide(product.getPriorMoney(), ResultNumber.FOUR.getNumber(), RoundingMode.HALF_DOWN);
			BigDecimal requiredPercent = new BigDecimal(percentOfBidding);

			// 操作记录
			StringBuffer resultRecord = new StringBuffer(product.getId() + "号产品募集比例：" + reachedPercent);

			ProductWithBLOBs productRecord = new ProductWithBLOBs();
			productRecord.setId(product.getId());
			if (reachedPercent.compareTo(requiredPercent) != ResultNumber.MINUS_ONE.getNumber()) {
				// 达标，产品成立，等待申请放款
				pageInfo.setCode(ResultInfo.SUCCESS.getCode());
				resultRecord.append("，金额达标，已成立，等待申请放款。");
				productRecord.setStatus(AppCons.PRODUCT_STATUS_COLLECT_SUCCESS);
				productMapper.updateByPrimaryKeySelective(productRecord);

			} else {
				// 不达标，产品失败
				resultRecord.append("，金额不达标 目前无法成立。");
			}

			pageInfo.setMsg(resultRecord.toString());
		}

		return pageInfo;
	}
}
