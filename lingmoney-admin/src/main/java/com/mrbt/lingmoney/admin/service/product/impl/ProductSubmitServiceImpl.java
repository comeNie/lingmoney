package com.mrbt.lingmoney.admin.service.product.impl;

import java.io.IOException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.apache.ibatis.session.RowBounds;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.mrbt.lingmoney.admin.service.product.ProductSubmitService;
import com.mrbt.lingmoney.mapper.HxBiddingMapper;
import com.mrbt.lingmoney.mapper.ProductMapper;
import com.mrbt.lingmoney.mapper.ProductSubmitMapper;
import com.mrbt.lingmoney.model.AdminUser;
import com.mrbt.lingmoney.model.HxBiddingExample;
import com.mrbt.lingmoney.model.ProductSubmit;
import com.mrbt.lingmoney.model.ProductSubmitExample;
import com.mrbt.lingmoney.model.ProductWithBLOBs;
import com.mrbt.lingmoney.utils.AppCons;
import com.mrbt.lingmoney.utils.GridPage;
import com.mrbt.lingmoney.utils.NetworkUtil;
import com.mrbt.lingmoney.utils.ResultParame;
import com.mrbt.lingmoney.utils.session.UserSession;

/**
 * 
 * 产品设置——》产品确认流程表
 *
 */
@Service
public class ProductSubmitServiceImpl implements ProductSubmitService {

	@Autowired
	private ProductSubmitMapper productSubmitMapper;

	@Autowired
	private ProductMapper productMapper;

	@Autowired
	private HxBiddingMapper hxBiddingMapper;

	@Override
	public void delete(int id) {
		productSubmitMapper.deleteByPrimaryKey(id);
	}

	@Override
	public GridPage<ProductSubmit> listGridType(int type, RowBounds page) {

		ProductSubmitExample example = new ProductSubmitExample();
		example.createCriteria().andStatusEqualTo(type);
		example.setLimitStart(page.getOffset());
		example.setLimitEnd(page.getLimit());

		GridPage<ProductSubmit> result = new GridPage<ProductSubmit>();
		result.setRows(productSubmitMapper.selectByExample(example));
		result.setTotal(productSubmitMapper.countByExample(example));
		return result;
	}

	@Override
	public GridPage<ProductSubmit> listGrid(ProductSubmit vo, RowBounds rowBounds) {
		ProductSubmitExample example = new ProductSubmitExample();
		example.setLimitStart(rowBounds.getOffset());
		example.setLimitEnd(rowBounds.getLimit());
		example.setOrderByClause("id desc");

		GridPage<ProductSubmit> result = new GridPage<ProductSubmit>();
		result.setRows(productSubmitMapper.selectByExample(example));
		result.setTotal(productSubmitMapper.countByExample(example));
		return result;
	}

	@Transactional
	@Override
	public int update(ProductSubmit vo, HttpServletRequest request, HttpSession session) {
		try {
			// 对应产品
			int pId = vo.getpId();
			ProductWithBLOBs pro = productMapper.selectByPrimaryKey(pId);
			// 产品所属pType 0领钱儿 1中粮 2华兴银行
			int pType = pro.getpType();
            // 如果为华兴银行产品，则判断该产品发标状态，如果不为0（发标成功）不可修改
			if (pType == ResultParame.ResultNumber.TWO.getNumber()) {
				HxBiddingExample example = new HxBiddingExample();
                example.createCriteria().andPIdEqualTo(pro.getId()).andInvestObjStateEqualTo("0");
				long count = hxBiddingMapper.countByExample(example);
                if (count < 1) {
					return ResultParame.ResultNumber.MINUS2.getNumber();
				}

            }

			// 获得本机IP
			vo.setIp(NetworkUtil.getIpAddress(request));

			AdminUser user = ((UserSession) session.getAttribute(AppCons.SESSION_USER)).getUser();
			vo.setuId(user.getId().toString());

			productSubmitMapper.updateByPrimaryKeySelective(vo);

			int status = vo.getStatus(); // 处理状态:0:待审批，1:审批退回,2:审批同意

			switch (status) {
			case 0:
				pro.setApproval(1); // 状态，0为初始状态，1:提交状态，2:审核通过状态
				break;
			case 1:
				pro.setApproval(0);
				break;
			case 2:
				pro.setApproval(ResultParame.ResultNumber.TWO.getNumber());
                // 如果是京东产品，更新产品状态为1（筹集中）
				if (pType == ResultParame.ResultNumber.ZERO.getNumber()) {
                    pro.setStatus(AppCons.PRODUCT_STATUS_READY);
                }
			default:
			}

			return productMapper.updateByPrimaryKeyWithBLOBs(pro);
		} catch (IOException e) {
			e.printStackTrace();
			return 0;
		}
	}
}
