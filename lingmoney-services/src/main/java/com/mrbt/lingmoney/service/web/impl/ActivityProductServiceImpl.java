package com.mrbt.lingmoney.service.web.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.apache.commons.beanutils.BeanUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.commons.lang.math.NumberUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.mrbt.lingmoney.mapper.ActivityProductMapper;
import com.mrbt.lingmoney.mapper.ProductMapper;
import com.mrbt.lingmoney.model.ActivityProduct;
import com.mrbt.lingmoney.model.ActivityProductExample;
import com.mrbt.lingmoney.model.ActivityProductWithBLOBs;
import com.mrbt.lingmoney.model.Product;
import com.mrbt.lingmoney.model.ProductExample;
import com.mrbt.lingmoney.model.webView.ActivityProductView;
import com.mrbt.lingmoney.service.web.ActivityProductService;

/**
 * @author syb
 * @date 2017年5月16日 下午2:43:46
 * @version 1.0
 * @description
 **/
@Service
public class ActivityProductServiceImpl implements ActivityProductService {
	@Autowired
	private ActivityProductMapper activityProductMapper;
	@Autowired
	private ProductMapper productMapper;

    private final int acitivityProductEndStatus = 2; // 活动产品结束状态

	@Override
	public ActivityProductView findActivityByActivityKey(String activityKey, int timeLimit) {
		ActivityProductView activityProductview = null;
		ActivityProductExample example = new ActivityProductExample();
		ActivityProductExample.Criteria criteria = example.createCriteria();
		criteria.andStatusEqualTo(1);
		criteria.andPriorityEqualTo(1);
		criteria.andActUrlEqualTo(activityKey);
		if (timeLimit == 1) {
			Date date = new Date();
			criteria.andStartDateLessThanOrEqualTo(date);
			criteria.andEndDateGreaterThanOrEqualTo(date);
		}
		example.setOrderByClause("create_time desc");
		List<ActivityProductWithBLOBs> activityProductList = activityProductMapper.selectByExampleWithBLOBs(example);
		if (activityProductList.size() > 0) {
			ActivityProduct activityProduct = activityProductList.get(0);
			activityProductview = new ActivityProductView();
			try {
				BeanUtils.copyProperties(activityProductview, activityProduct);
			} catch (Exception e) {
				e.printStackTrace();
				throw new RuntimeException();
			}
			this.checkToExecute(activityProductview);
			String pid = activityProductview.getpId();
			if (pid != null && !pid.equals("")) {
				String[] pids = pid.split(",");
				List<Integer> pidList = new ArrayList<Integer>();
				for (int i = 0; i < pids.length; i++) {
					if (StringUtils.isNotBlank(pids[i]) && NumberUtils.isNumber(pids[i])) {
						pidList.add(NumberUtils.toInt(pids[i]));
					}
				}
				ProductExample pExample = new ProductExample();
				pExample.createCriteria().andIdIn(pidList);
				List<Product> productList = productMapper.selectByExample(pExample);
				activityProductview.setProductList(productList);
                Boolean isAllOver = true; //是否全部活动产品已结束 false=未结束 true=全部结束
				for (Product product : productList) {
					if (product.getStatus() == 1) {
						isAllOver = false;
					}
				}
                if (isAllOver) { // 如果产品都已成立，则设置活动为结束activityProduct.setBuyState(2);
                    activityProductview.setBuyState(acitivityProductEndStatus);
				}
			}
		}
		return activityProductview;
	}

    /**
     * 查看活動是否在執行週期內
     * 
     * @param activityProduct 活动产品
     *
     */
	public void checkToExecute(ActivityProductView activityProduct) {
		// 当前时间
		Date dtNow = new Date();

		Date startDate = activityProduct.getStartDate();
		Date endDate = activityProduct.getEndDate();

		if (dtNow.getTime() > endDate.getTime()) {
			// 活动已结束
            activityProduct.setBuyState(acitivityProductEndStatus);
		} else if (dtNow.getTime() < startDate.getTime()) {
			// 还未开始
			activityProduct.setBuyState(1);
			activityProduct.setNextBuyDate(startDate);
		} else {
			activityProduct.setBuyState(0);
		}

	}
}
