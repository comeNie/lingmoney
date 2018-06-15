package com.mrbt.lingmoney.admin.service.product.impl;

import java.util.List;

import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.mrbt.lingmoney.admin.service.product.AutoSubmitProductService;
import com.mrbt.lingmoney.mapper.ProductCustomerMapper;
import com.mrbt.lingmoney.mapper.ProductMapper;
import com.mrbt.lingmoney.mapper.ProductSubmitMapper;
import com.mrbt.lingmoney.model.ProductExample;
import com.mrbt.lingmoney.model.ProductSubmit;
import com.mrbt.lingmoney.model.ProductSubmitExample;
import com.mrbt.lingmoney.model.ProductWithBLOBs;
import com.mrbt.lingmoney.utils.AppCons;
import com.mrbt.lingmoney.utils.ResultParame;

/**
 * 自动提交产品
 */
@Service
public class AutoSubmitProductServiceImpl implements AutoSubmitProductService {
	private static final Logger LOG = LogManager.getLogger(AutoSubmitProductServiceImpl.class);

	@Autowired
	private ProductCustomerMapper productCustomerMapper;
	@Autowired
	private ProductMapper productMapper;
	@Autowired
	private ProductSubmitMapper productSubmitMapper;

	/**
	 * 自动提交产品
	 */
	@Override
	public void autoSubmitProduct() {
		List<String> batchList = null;
		LOG.info("查询approval=1(已提交审核)status=1(募集中)根据batch(批次号)分组的List<String>，所有需要审核通过的批次");
		if ((batchList = productCustomerMapper.queryNeedToApprovalBatch()) != null && batchList.size() > 0) {
			for (String batch : batchList) {
				LOG.info("遍历批次号，根据批次号" + batch
						+ "查询approval=2(审核通过)status=1(募集中)的产品个数，如果大于0则跳过不管；否则修改第一条，将产品表approval设为2(审核通过)，将对应产品提交表状态设为审核通过");
				int count = productCustomerMapper.queryAlreadyApprovalProCount(batch);
				if (count > 0) {
					LOG.info("根据批次号" + batch + "查询已发布approval=2(审核通过)status=1(募集中)的产品个数为" + count + "直接跳过");
					continue;
				}
				Integer pId = productCustomerMapper.queryNeedToApprovalFirstPid(batch);

                ProductWithBLOBs pBlob = productMapper.selectByPrimaryKey(pId);
				LOG.info("根据批次号查询approval=1(已提交审核)status=1(募集中)的第一个产品id为" + pId);
				// 将产品表approval设为2(审核通过)，将对应产品提交表状态设为审核通过2
				ProductWithBLOBs product = new ProductWithBLOBs();
				product.setApproval(ResultParame.ResultNumber.TWO.getNumber());
                if (pBlob != null && pBlob.getpType() == 0) {// 如果是京东产品，修改产品状态为1（筹集中）
                    product.setStatus(AppCons.PRODUCT_STATUS_READY);
                }
				ProductExample productExample = new ProductExample();
				productExample.createCriteria().andIdEqualTo(pId);
				int result = productMapper.updateByExampleSelective(product, productExample);
				if (result > 0) {
					LOG.info("将产品表approval设为2(审核通过)，成功");
				} else {
					LOG.info("将产品表approval设为2(审核通过)，失败");
				}
				ProductSubmit productSubmit = new ProductSubmit();
				productSubmit.setStatus(ResultParame.ResultNumber.TWO.getNumber());
				ProductSubmitExample productSubmitExample = new ProductSubmitExample();
				productSubmitExample.createCriteria().andPIdEqualTo(pId);
				result = productSubmitMapper.updateByExampleSelective(productSubmit, productSubmitExample);
				if (result > 0) {
					LOG.info("将对应产品提交表状态设为审核通过2，成功");
				} else {
					LOG.info("将对应产品提交表状态设为审核通过2，失败");
				}
			}
		}
	}
}
