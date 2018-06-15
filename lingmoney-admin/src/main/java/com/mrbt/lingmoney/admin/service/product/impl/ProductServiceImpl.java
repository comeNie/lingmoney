package com.mrbt.lingmoney.admin.service.product.impl;

import java.io.IOException;
import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.apache.commons.lang.StringUtils;
import org.apache.ibatis.session.RowBounds;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.mrbt.lingmoney.admin.service.bank.BankAccountBalanceService;
import com.mrbt.lingmoney.admin.service.product.ProductService;
import com.mrbt.lingmoney.admin.service.trading.TradingBuyCallbackService;
import com.mrbt.lingmoney.admin.vo.trading.CallbackValidCode;
import com.mrbt.lingmoney.mapper.CustomQueryMapper;
import com.mrbt.lingmoney.mapper.EnterpriseNameMoneyMapper;
import com.mrbt.lingmoney.mapper.HxBiddingMapper;
import com.mrbt.lingmoney.mapper.ProductCategoryMapper;
import com.mrbt.lingmoney.mapper.ProductCustomerMapper;
import com.mrbt.lingmoney.mapper.ProductMapper;
import com.mrbt.lingmoney.mapper.ProductSubmitMapper;
import com.mrbt.lingmoney.model.AdminUser;
import com.mrbt.lingmoney.model.EnterpriseNameMoney;
import com.mrbt.lingmoney.model.EnterpriseNameMoneyExample;
import com.mrbt.lingmoney.model.HxBidding;
import com.mrbt.lingmoney.model.HxBiddingExample;
import com.mrbt.lingmoney.model.Product;
import com.mrbt.lingmoney.model.ProductCategory;
import com.mrbt.lingmoney.model.ProductExample;
import com.mrbt.lingmoney.model.ProductSubmit;
import com.mrbt.lingmoney.model.ProductSubmitExample;
import com.mrbt.lingmoney.model.ProductWithBLOBs;
import com.mrbt.lingmoney.utils.AppCons;
import com.mrbt.lingmoney.utils.DateUtils;
import com.mrbt.lingmoney.utils.GridPage;
import com.mrbt.lingmoney.utils.NetworkUtil;
import com.mrbt.lingmoney.utils.PageInfo;
import com.mrbt.lingmoney.utils.ProductUtils;
import com.mrbt.lingmoney.utils.ResultParame;
import com.mrbt.lingmoney.utils.ResultParame.ResultInfo;
import com.mrbt.lingmoney.utils.StringFromatUtils;
import com.mrbt.lingmoney.utils.session.UserSession;

import net.sf.json.JSONObject;

/**
 * 
 * 产品设置——》产品表
 *
 */
@Service
public class ProductServiceImpl implements ProductService {

	private static final Logger LOGGER = LogManager.getLogger(ProductServiceImpl.class);

	@Autowired
	private ProductMapper productMapper;
	@Autowired
	private ProductCategoryMapper productCategoryMapper;
	@Autowired
	private ProductSubmitMapper productSubmitMapper;
	@Autowired
	private ProductCustomerMapper productCustomerMapper;
	@Autowired
	private CustomQueryMapper customQueryMapper;
	@Autowired
	private BankAccountBalanceService bankAccountBalanceService;
	@Autowired
	private HxBiddingMapper hxBiddingMapper;
    @Autowired
    private TradingBuyCallbackService tradingBuyCallbackService;
	@Autowired
	private EnterpriseNameMoneyMapper enterpriseNameMoneyMapper;

	/**
	 * 生成辅助类根据实体bean
	 * 
	 * @param vo
	 *            实体bean
	 * @return ProductExample辅助类
	 */
	private ProductExample createProductExample(Product vo) {
		ProductExample example = new ProductExample();
		ProductExample.Criteria cri = example.createCriteria();
		if (StringUtils.isNotBlank(vo.getName())) {
			cri.andNameLike("%" + vo.getName() + "%");
		}
		if (StringUtils.isNotBlank(vo.getBatch())) {
			cri.andBatchLike("%" + vo.getBatch() + "%");
		}
		if (vo.getApproval() != null) {
			cri.andApprovalEqualTo(vo.getApproval());
		}
		if (vo.getId() != null) {
			cri.andIdEqualTo(vo.getId());
		}
		if (vo.getStatus() != null) {
			cri.andStatusEqualTo(vo.getStatus());
		}
		if (vo.getActivity() != null) {
			cri.andActivityEqualTo(vo.getActivity());
		}
		if (vo.getpType() != null) {
			cri.andPTypeEqualTo(vo.getpType());
		}
		if (StringUtils.isNotBlank(vo.getCityCode())) {
			cri.andCityCodeEqualTo(vo.getCityCode());
		}
		cri.andStatusNotEqualTo(ResultParame.ResultNumber.MINUS_ONE.getNumber());
		return example;
	}

	@Override
	public List<Product> getNnSetUpProduct() {
		return productCustomerMapper.getNnSetUpProduct();
	}

	/**
	 * 生成datagrid表格需要的结果
	 * 
	 * @param vo
	 *            Product实体bean
	 * @param page
	 *            翻页信息
	 * @return GridPage<Product>
	 */
	@Override
	public GridPage<Product> listGrid(Product vo, RowBounds page) {
		ProductExample example = createProductExample(vo);
		example.setLimitStart(page.getOffset());
		example.setLimitEnd(page.getLimit());
		example.setOrderByClause("id desc");

		GridPage<Product> result = new GridPage<Product>();
		result.setRows(productMapper.selectByExample(example));

		result.setTotal(productMapper.countByExample(example));

		return result;
	}

	/**
	 * 查找，根据Product
	 * 
	 * @param type
	 *            固定类和浮动类标志
	 * @return Product的集合
	 */
	@Override
	public List<Product> listType(int type, Product record) {
		ProductExample example = new ProductExample();
		ProductExample.Criteria cri = example.createCriteria();
		if (record.getApproval() != null && record.getApproval() >= 0) {
			cri.andApprovalEqualTo(record.getApproval());
		}
		if (StringUtils.isNotBlank(record.getName())) {
			cri.andNameLike("%" + record.getName() + "%");
		}
		cri.andStatusNotEqualTo(ResultParame.ResultNumber.MINUS_ONE.getNumber());
		if (type == AppCons.FIX_FLAG) {
			return productCustomerMapper.selectByFixType(example);
		}
		return productCustomerMapper.selectByFloatType(example);
	}

	@Override
	public void save(ProductWithBLOBs vo, PageInfo pageInfo) {
		vo.setApproval(AppCons.PRODUCT_APPROVAL_INIT);
		vo.setStatus(AppCons.PRODUCT_STATUS_INIT);
		int result = productMapper.insertSelective(vo);
		if (result >= 0) {
			ProductCategory pc = productCategoryMapper.selectByPrimaryKey(vo.getPcId());
			ProductWithBLOBs record = new ProductWithBLOBs();

			record.setId(vo.getId());
			record.setCode(
					pc.getCode() + StringFromatUtils.leftFill(ResultParame.ResultNumber.FOUR.getNumber(), vo.getId()));
			result = productMapper.updateByPrimaryKeySelective(record);
			if (result >= 0) {
				pageInfo.setCode(ResultParame.ResultInfo.SUCCESS.getCode());
				pageInfo.setMsg("新增产品成功");
			} else {
				pageInfo.setCode(ResultParame.ResultInfo.UPDATE_FAIL.getCode());
				pageInfo.setMsg("修改产品失败");
			}
			
		} else {
			pageInfo.setCode(ResultParame.ResultInfo.UPDATE_FAIL.getCode());
			pageInfo.setMsg("新增产品失败");
		}
	}

	/**
	 * 更新
	 */
	@Override
	public void update(ProductWithBLOBs vo, PageInfo pageInfo) {

		if (vo.getId() == null || vo.getId() <= 0) {
			pageInfo.setCode(ResultParame.ResultInfo.UPDATE_FAIL.getCode());
			pageInfo.setMsg("参数错误");
			return;
		}

		ProductWithBLOBs tmp = productMapper.selectByPrimaryKey(vo.getId());

		if (tmp == null) {
			pageInfo.setCode(ResultParame.ResultInfo.UPDATE_FAIL.getCode());
			pageInfo.setMsg("更新产品错误");
			return;
		}

		if (tmp != null && tmp.getApproval() == AppCons.PRODUCT_APPROVAL_OK) {
			pageInfo.setCode(ResultParame.ResultInfo.UPDATE_FAIL.getCode());
            pageInfo.setMsg("已经审批过的产品不能修改");
			return;
		}

        HxBiddingExample hxBiddingExmp = new HxBiddingExample();
        hxBiddingExmp.createCriteria().andPIdEqualTo(vo.getId()).andInvestObjStateEqualTo("0");
        int num = (int) hxBiddingMapper.countByExample(hxBiddingExmp);
        if (num > 0) {
            pageInfo.setCode(ResultParame.ResultInfo.UPDATE_FAIL.getCode());
            pageInfo.setMsg("发标成功产品不可修改");
            return;
        }

		// 设置审核状态
		vo.setApproval(tmp.getApproval());
		// 如果规则为金额或者是时间金额
		if (vo.getRule() != null && (vo.getRule() == AppCons.RULE_MONEY || vo.getRule() == AppCons.RULE_MONEY_DATE)) {
			vo.setReachMoney(new BigDecimal(0));
		}

		ProductCategory pc = productCategoryMapper.selectByPrimaryKey(vo.getPcId());
		vo.setCode(pc.getCode() + StringFromatUtils.leftFill(ResultParame.ResultNumber.FOUR.getNumber(), vo.getId()));
		vo.setDescription(vo.getDescription());

		int result = productMapper.updateByPrimaryKeySelective(vo);
		if (result >= 0) {
			pageInfo.setCode(ResultParame.ResultInfo.SUCCESS.getCode());
			pageInfo.setMsg("新增产品成功");

			// 如果修改了产品名，需要修改产品推荐表中产品名
			if (vo.getName() != null && !vo.getName().equals(tmp.getName())) {
				ProductSubmitExample productSubmitExmp = new ProductSubmitExample();
				productSubmitExmp.createCriteria().andPIdEqualTo(vo.getId());

				ProductSubmit productSubmitRecord = new ProductSubmit();
				productSubmitRecord.setpName(vo.getName());
				productSubmitMapper.updateByExampleSelective(productSubmitRecord, productSubmitExmp);
			}

            // 如果产品已提交，修改对应的标的信息
            if (tmp.getpType() == 2 && tmp.getApproval() == 1) {
                updateProductBiddingInfo(vo);
            }

		} else {
			pageInfo.setCode(ResultParame.ResultInfo.UPDATE_FAIL.getCode());
			pageInfo.setMsg("新增产品失败");
		}
	}

    private void updateProductBiddingInfo(ProductWithBLOBs vo) {
        vo = productMapper.selectByPrimaryKey(vo.getId());
        // 如果是华兴的产品需要更新标的信息
        if (vo.getpType() == ResultParame.ResultNumber.TWO.getNumber()) {
            // 查询库中是否存在标的，如果有则更新，否则添加
            HxBiddingExample example = new HxBiddingExample();
            example.createCriteria().andPIdEqualTo(vo.getId());
            long count = hxBiddingMapper.countByExample(example);

            HxBidding hxBidding = new HxBidding();
            // 产品id
            hxBidding.setpId(vo.getId());
            // 应用标识
            hxBidding.setAppId("PC");
            // 借款编号,暂时为产品code
            hxBidding.setLoanNo(vo.getCode());
            // 标的编号
            hxBidding.setInvestId(hxBidding.getLoanNo());
            // 还款日期
            hxBidding.setRepayDate(new SimpleDateFormat("yyyyMMdd").format(DateUtils.addDay(vo.getEdDt(),
                    vo.getfTime())));
            // 标的状态，-1 初始， 0 正常，1 撤销 ，2 流标，3 银行主动流标 ， 4 标的成立
            hxBidding.setInvestObjState("-1");
            // 借款人总数 默认0
            hxBidding.setBwTotalNum(0);
            // 是否为债权转让标的 默认否
            hxBidding.setZrFlag("0");
            if (count > 0) {
                hxBiddingMapper.updateByExampleSelective(hxBidding, example);
            } else {
                hxBidding.setId(UUID.randomUUID().toString().replace("-", ""));
                hxBiddingMapper.insert(hxBidding);
            }
        }
    }

	@Override
	public void copy(ProductWithBLOBs vo, PageInfo pageInfo) {
		if (vo.getId() == null || vo.getId() <= 0) {
			pageInfo.setCode(ResultParame.ResultInfo.UPDATE_FAIL.getCode());
			pageInfo.setMsg("参数错误");
			return;
		}

		ProductWithBLOBs tmp = productMapper.selectByPrimaryKey(vo.getId());
		if (tmp == null) {
			pageInfo.setCode(ResultParame.ResultInfo.UPDATE_FAIL.getCode());
			pageInfo.setMsg("复制产品错误");
			return;
		}
		vo.setId(null);
		vo.setApproval(AppCons.PRODUCT_APPROVAL_INIT);
		vo.setStatus(AppCons.PRODUCT_STATUS_INIT);
		vo.setDescription(tmp.getDescription());
		vo.setIntroduction(tmp.getIntroduction());
		vo.setProjectInfo(tmp.getProjectInfo());
		vo.setProjectInfoMobile(tmp.getProjectInfoMobile());
		vo.setLonnerInfo(tmp.getLonnerInfo());
		vo.setNormalProblem(tmp.getNormalProblem());
		int result = productMapper.insertSelective(vo);
		if (result >= 0) {
			ProductCategory pc = productCategoryMapper.selectByPrimaryKey(vo.getPcId());
			ProductWithBLOBs record = new ProductWithBLOBs();

			record.setId(vo.getId());
			record.setCode(
					pc.getCode() + StringFromatUtils.leftFill(ResultParame.ResultNumber.FOUR.getNumber(), vo.getId()));
			result = productMapper.updateByPrimaryKeySelective(record);
			if (result >= 0) {
				pageInfo.setCode(ResultParame.ResultInfo.SUCCESS.getCode());
				pageInfo.setMsg("复制产品成功");
			} else {
				pageInfo.setCode(ResultParame.ResultInfo.UPDATE_FAIL.getCode());
				pageInfo.setMsg("修改产品code失败");
			}

		} else {
			pageInfo.setCode(ResultParame.ResultInfo.UPDATE_FAIL.getCode());
			pageInfo.setMsg("复制产品失败");
		}
	}

	@Override
	public List<String> getBlobContent(int id) {
		ProductWithBLOBs record = productMapper.selectByPrimaryKey(id);

		if (record != null) {
			List<String> list = new ArrayList<String>();
			String projectInfo = record.getProjectInfo() == null ? "" : record.getProjectInfo();
			String projectInfoMobile = record.getProjectInfoMobile() == null ? "" : record.getProjectInfoMobile();
			String introduction = record.getIntroduction() == null ? "" : record.getIntroduction();
			String description = record.getDescription() == null ? "" : record.getDescription();
			list.add(projectInfo);
			list.add(projectInfoMobile);
			list.add(introduction);
			list.add(description);
			return list;
		}
		return null;
	}

	@Override
	public int deleteProduct(int id) {
		ProductWithBLOBs record = productMapper.selectByPrimaryKey(id);

		if (record == null) {
			return 0;
		}

		if (record.getApproval() == AppCons.PRODUCT_APPROVAL_OK) {
			return ResultParame.ResultNumber.MINUS_ONE.getNumber();
		}

        // 已发标的不可删除
        HxBiddingExample hxBiddingExmp = new HxBiddingExample();
        hxBiddingExmp.createCriteria().andPIdEqualTo(id).andInvestObjStateEqualTo("0");
        int num = (int) hxBiddingMapper.countByExample(hxBiddingExmp);
        if (num > 0) {
            return ResultParame.ResultNumber.MINUS3.getNumber();
        }

		record.setId(id);
		record.setStatus(ResultParame.ResultNumber.MINUS_ONE.getNumber());
        int result = productMapper.updateByPrimaryKeySelective(record);

        if (result > 0) {
            // 删除标的信息
            hxBiddingExmp = new HxBiddingExample();
            hxBiddingExmp.createCriteria().andPIdEqualTo(id);

            hxBiddingMapper.deleteByExample(hxBiddingExmp);
        }

        return result;
	}

	@Override
	public int updateApproval(int id, HttpSession session, HttpServletRequest request) throws IOException {
		ProductWithBLOBs record = productMapper.selectByPrimaryKey(id);

		if (record == null) {
			return 0;
		}

		if (record.getApproval() != AppCons.PRODUCT_APPROVAL_INIT) {
			return ResultParame.ResultNumber.MINUS_ONE.getNumber();
		}

		record.setApproval(AppCons.PRODUCT_APPROVAL_SUTMIT);
		productMapper.updateByPrimaryKeyWithBLOBs(record);

        // 如果是华兴产品，不存在标的信息，初始化一条记录
        if (record.getpType() == 2) {
            HxBiddingExample hxBiddingExmp = new HxBiddingExample();
            hxBiddingExmp.createCriteria().andPIdEqualTo(id);
            int num = (int) hxBiddingMapper.countByExample(hxBiddingExmp);
            if (num < 1) {
                updateProductBiddingInfo(record);
            }
        }

		ProductSubmitExample example = new ProductSubmitExample();
		example.createCriteria().andPIdEqualTo(id);
		long count = productSubmitMapper.countByExample(example);

		ProductSubmit ps = new ProductSubmit();
		ps.setInfo("该产品需要审批");
		ps.setIp(NetworkUtil.getIpAddress(request));
		ps.setOpTime(new Date());
		ps.setpId(id);
		ps.setpName(record.getName());
		ps.setStatus(0);

		UserSession usersession = (UserSession) session.getAttribute(AppCons.SESSION_USER);
		AdminUser user = usersession.getUser();
		ps.setuId(user.getId().toString());

		if (count > 0) {
			productSubmitMapper.updateByExampleSelective(ps, example);

		} else {
			ps.setId(null);
			productSubmitMapper.insertSelective(ps);
		}

		return 1;
	}

	@Override
	public String listDescription(int id) {
		ProductWithBLOBs vo = productMapper.selectByPrimaryKey(id);
		if (vo != null) {
			return vo.getDescription();
		}
		return "";
	}

	@Override
	public int raiseOpertion(int id) {
		ProductWithBLOBs product = productMapper.selectByPrimaryKey(id);
        // 只限成立京东产品
        if (product == null || product.getpType() != 0) {
            return -1;
        }

		// 判断是钱包模式 还是产品模式
		if (product.getpModel() == 1) {
			product.setStatus(1);
			productMapper.updateByPrimaryKeyWithBLOBs(product);
			return 1;
		} else {
            // 原手点成立逻辑
            Integer days = ProductUtils.getFinancialDays(product);
            CallbackValidCode code = tradingBuyCallbackService.manualEstablish(id, days, false);
            if (code.getCode() == CallbackValidCode.callback_success.getCode()) {
                return 1;
            } else {
                return -1;
            }
		}
	}

	@Override
	public List<Product> selectByActivity(Product vo) {
		ProductExample example = new ProductExample();
		ProductExample.Criteria cri = example.createCriteria();

		if (vo.getStatus() != null) {
			cri.andStatusEqualTo(vo.getStatus());
		}
		if (vo.getRule() != null) {
			cri.andRuleEqualTo(vo.getRule());
		}

		return productMapper.selectByExample(example);
	}

	@Override
	public Product findByPk(int id) {
		return productCustomerMapper.selectProductByPrimaryKey(id);
	}

	@Override
	public int getLingBaoNum(double buyMoney, int unitTime, int number) {
		int day = 0;
		if (unitTime == AppCons.UNIT_TIME_DAY) {
			day = number;
		} else if (unitTime == AppCons.UNIT_TIME_MONTH) {
			day = number * ResultParame.ResultNumber.THIRTY.getNumber();
		}
		int count = 0;
		if (day > 0) {
			if (buyMoney >= ResultParame.ResultNumber.ONE_HUNDRED.getNumber()) { // 理财额÷365天×理财天数÷50×100
				count = (int) (buyMoney / ResultParame.ResultNumber.THREE_SIXTY_FIVE.getNumber() * day
						/ ResultParame.ResultNumber.FIFTY.getNumber());
			}
		}
		return count;
	}

	@Override
	public PageInfo getList(Product vo, PageInfo pageInfo) {
		ProductExample example = createProductExample(vo);
		example.setLimitStart(pageInfo.getFrom());
		example.setLimitEnd(pageInfo.getSize());
        example.setOrderByClause("id desc");

        int resSize = productMapper.countByExample(example);
		List<Product> list = productMapper.selectByExample(example);
		pageInfo.setCode(ResultParame.ResultInfo.SUCCESS.getCode());
		pageInfo.setRows(list);
		pageInfo.setTotal(resSize);
		return pageInfo;
	}

	@Override
	public void saveDetail(Integer pId, ProductWithBLOBs vo, Integer type, PageInfo pageInfo) {
		ProductWithBLOBs bloBs = productMapper.selectByPrimaryKey(pId);
		if (bloBs != null) {
			if (type == 0) { // pc项目信息
				bloBs.setProjectInfo(vo.getProjectInfo());
			} else if (type == 1) { // app项目信息
				bloBs.setProjectInfoMobile(vo.getProjectInfoMobile());
			} else if (type == ResultParame.ResultNumber.TWO.getNumber()) { // 抵押物信息
				bloBs.setIntroduction(vo.getIntroduction());
			}
			productMapper.updateByPrimaryKeySelective(bloBs);
		}
	}

	@Override
	public void saveDesc(Integer id, String title1, String content1, String title2, String content2, String title3,
			String content3, String title4, String content4, String title5, String content5, String title6,
			String content6, String title7, String content7, String title8, String content8, String description,
			Integer insertType) {
		ProductWithBLOBs bloBs = productMapper.selectByPrimaryKey(id);
		if (bloBs != null) {
			// 如果是统一录入，description不为空，则直接保存；否则是分块录入，description为空，分块字段转成json保存。
			if (insertType != null && insertType == 0) {
				Map<String, Object> map = new HashMap<String, Object>();
				map.put("title1", title1);
				map.put("content1", content1);
				map.put("title2", title2);
				map.put("content2", content2);
				map.put("title3", title3);
				map.put("content3", content3);
				map.put("title4", title4);
				map.put("content4", content4);
				map.put("title5", title5);
				map.put("content5", content5);
				map.put("title6", title6);
				map.put("content6", content6);
				map.put("title7", title7);
				map.put("content7", content7);
				map.put("title8", title8);
				map.put("content8", content8);

				JSONObject json = JSONObject.fromObject(map);
				bloBs.setDescription(json.toString());
				productMapper.updateByPrimaryKeySelective(bloBs);
			} else {
				bloBs.setDescription(description);
				productMapper.updateByPrimaryKeySelective(bloBs);
			}
		}
	}

	@Override
	public PageInfo listAutoRepaymentProduct(Integer pageNo, Integer pageSize) {
		if (pageNo == null) {
			pageNo = AppCons.DEFAULT_PAGE_START;
		}

		if (pageSize == null) {
			pageSize = AppCons.DEFAULT_PAGE_SIZE;
		}

		PageInfo pi = new PageInfo(pageNo, pageSize);

		Map<String, Object> param = new HashMap<String, Object>();
		param.put("start", pi.getFrom());
		param.put("number", pi.getSize());

		List<Map<String, Object>> list = customQueryMapper.listAutoRepaymentProduct(param);
		int total = customQueryMapper.countAutoRepaymentProduct();

		pi.setRows(list);
		pi.setTotal(total);
		pi.setNowpage(pageNo);

		return pi;
	}

	@Transactional
	@Override
	public PageInfo productAutoRepaymentConfirm(String productIds, Integer type) {
		PageInfo pi = new PageInfo();

		if (StringUtils.isNotBlank(productIds) && type != null) {
			String[] productId = productIds.split(",");

			// 确认自动还款时 需要验证账户余额是否足够
			if (type == 1) {
				PageInfo validInfo = checkIfBalanceEnough(productId);
				if (!(boolean) validInfo.getObj()) {
					validInfo.setCode(ResultParame.ResultNumber.THREE_ZERO_TWO.getNumber());
					return validInfo;
				}
			}

			for (int i = 0; i < productId.length; i++) {
				int pid = Integer.parseInt(productId[i]);
				Product product = productMapper.selectByPrimaryKey(pid);

				if (product == null) {
					pi.setCode(ResultParame.ResultInfo.EMPTY_DATA.getCode());
					pi.setMsg("未查询到指定产品");
					return pi;
				}

				if (product.getpType() != ResultParame.ResultNumber.TWO.getNumber()) {
					pi.setCode(ResultParame.ResultInfo.UPDATE_FAIL.getCode());
					pi.setMsg("产品类型有误，非华兴产品");
					return pi;
				}

				if (product.getStatus() != AppCons.PRODUCT_STATUS_REPAYMENT
						&& product.getStatus() != AppCons.PRODUCT_STATUS_CONFIRM_AUTO_REPAYMENT) {
					pi.setCode(ResultParame.ResultInfo.PRODUCT_STATUS_FAIL.getCode());
					pi.setMsg(ResultParame.ResultInfo.PRODUCT_STATUS_FAIL.getMsg());
					return pi;
				}

				// 根据类型判断更改状态为3还是11
				int status = type == 0 ? AppCons.PRODUCT_STATUS_REPAYMENT
						: AppCons.PRODUCT_STATUS_CONFIRM_AUTO_REPAYMENT;

				if (status == product.getStatus()) {
					continue;
				}

				ProductWithBLOBs productRecord = new ProductWithBLOBs();
				productRecord.setId(pid);
				productRecord.setStatus(status);

				int result = productMapper.updateByPrimaryKeySelective(productRecord);

				if (result < 0) {
					pi.setCode(ResultParame.ResultInfo.SERVER_ERROR.getCode());
					pi.setMsg("操作失败，请刷新后重试");
					return pi;
				}

			}

			pi.setCode(ResultParame.ResultInfo.SUCCESS.getCode());
			pi.setMsg("操作成功");

		} else {
			pi.setCode(ResultParame.ResultInfo.PARAM_MISS.getCode());
			pi.setMsg("参数错误");
		}

		return pi;
	}

	/**
	 * 验证自动还款金额是否足够
	 * 
	 * @param productId
	 *            productId
	 * @return 数据返回
	 */
	private PageInfo checkIfBalanceEnough(String[] productId) {
		PageInfo pi = new PageInfo();

		boolean result = true;
		try {
			// 对账记录MAP
			Map<String, String> balanceMap = new HashMap<String, String>();

			// 对账结果
			StringBuffer resultString = new StringBuffer();

			for (int i = 0; i < productId.length; i++) {
				int pid = Integer.parseInt(productId[i]);
				// 查询标的借款人账号信息
				Map<String, Object> resultMap = customQueryMapper.queryBorrowerInfoByPid(pid);
				if (resultMap != null && resultMap.containsKey("acNo")) {
					// 验证借款人账号余额
					String acNo = (String) resultMap.get("acNo");
					if (StringUtils.isNotBlank(acNo)) {

						// 正常还款总金额
						BigDecimal allMoney = customQueryMapper.sumAllRepaymentMoney(pid);

						// 查询加息总额
						BigDecimal extraMoney = customQueryMapper.sumRedPacketMoneyByPid(pid);

						// 还款金额 = 还款总额 + 加息券金额
						BigDecimal amount = allMoney.add(extraMoney);

						if (balanceMap.containsKey(acNo)) {
							balanceMap.put(acNo, amount.add(new BigDecimal(balanceMap.get(acNo))).toString());
						} else {
							balanceMap.put(acNo, amount.toString());
						}

					}

				} else {
					result = false;
					resultString.append("未查询到产品" + productId[i] + "的借款人账号信息;</br>");
				}
			}

			// 查询账户余额是否足够
			for (Map.Entry<String, String> entry : balanceMap.entrySet()) {
				String acNo = entry.getKey();
				String amount = entry.getValue();
				String code = bankAccountBalanceService.checkAccountBalance("PC", amount.toString(), acNo);

				if (!"000000".equals(code)) {
					result = false;
				}

				switch (code) {
				case "000000":
					resultString.append("账号" + acNo + "余额充足;</br>");
					break;
				case "540026":
					resultString.append("账号" + acNo + "余额为零;</br>");
					break;
				case "540009":
					resultString.append("账号" + acNo + "余额不足;</br>");
					break;
				case "540008":
					resultString.append("账号" + acNo + "账户没有关联;</br>");
					break;
				case "OGW001":
					resultString.append("账号" + acNo + "账户与户名不匹配;</br>");
					break;
				default:
					resultString.append("账号" + acNo + "校验结果未知;</br>");
					break;

				}

			}

			pi.setCode(ResultParame.ResultInfo.SUCCESS.getCode());
			pi.setMsg(resultString.toString());
			pi.setObj(result);
		} catch (Exception e) {
			e.printStackTrace();
			pi.setCode(ResultParame.ResultInfo.SERVER_ERROR.getCode());
			pi.setMsg("校验失败");
			pi.setObj(false);
		}

		return pi;
	}

	@Override
	public void selectProductList(PageInfo pageInfo) {
		// 查询条件
		Map<String, Object> condition = new HashMap<String, Object>();
		condition.put("status", 1);
		pageInfo.setCondition(condition);
		// 总条数
		Integer total = productCustomerMapper.selectProductCount(pageInfo);
		if (total > 0) {
			pageInfo.setCode(ResultParame.ResultInfo.SUCCESS.getCode());
			pageInfo.setMsg("查询成功");
			// 结果集
			pageInfo.setRows(productCustomerMapper.selectProductList(pageInfo));
			pageInfo.setTotal(total);
		} else {
			pageInfo.setCode(ResultParame.ResultInfo.RETURN_DATA_EMPTY_DATA.getCode());
			pageInfo.setMsg("暂无数据");
		}
	}

    @Override
    public PageInfo sortProduct(Integer id, Byte sort) {
        PageInfo pi = new PageInfo();

		if (null == id || null == sort || sort < ResultParame.ResultNumber.ZERO.getNumber()
				|| sort > ResultParame.ResultNumber.NINETY_NINE.getNumber()) {
			pi.setCode(ResultParame.ResultInfo.MODIFY_REJECT.getCode());
            pi.setMsg("参数有误");
            return pi;
        }

        Product product = productMapper.selectByPrimaryKey(id);
        if (product == null) {
			pi.setCode(ResultParame.ResultInfo.EMPTY_DATA.getCode());
            pi.setMsg("未查询到有效数据");
            return pi;
        }

        ProductWithBLOBs record = new ProductWithBLOBs();
        record.setId(id);
        record.setSort(sort);
        int result = productMapper.updateByPrimaryKeySelective(record);

        if (result > 0) {
			pi.setCode(ResultParame.ResultInfo.SUCCESS.getCode());
            pi.setMsg("操作成功");

        } else {
			pi.setCode(ResultParame.ResultInfo.MODIFY_REJECT.getCode());
            pi.setMsg("操作失败，请刷新后重试");
        }

        return pi;
    }

	@Override
	public PageInfo festivalBuyDetailList() {
		PageInfo pageInfo = new PageInfo();
		EnterpriseNameMoneyExample example = new EnterpriseNameMoneyExample();
		List<EnterpriseNameMoney> enterpriseNameMoneyList = enterpriseNameMoneyMapper.selectByExample(example);
		pageInfo.setRows(enterpriseNameMoneyList);
		return pageInfo;
	}

    @Override
    public PageInfo updateProductDetailInfo(Integer id, String productDetailLonnerInfo, String productDetailNormalQues) {
        PageInfo pageInfo = new PageInfo();

        ProductWithBLOBs product = productMapper.selectByPrimaryKey(id);
        if (product != null) {
            ProductWithBLOBs record = new ProductWithBLOBs();
            record.setId(id);
            record.setLonnerInfo(productDetailLonnerInfo);
            record.setNormalProblem(productDetailNormalQues);
            productMapper.updateByPrimaryKeySelective(record);
            pageInfo.setResultInfo(ResultInfo.SUCCESS);

        } else {
            pageInfo.setResultInfo(ResultInfo.NO_DATA);
        }

        return pageInfo;
    }

    @Override
    public PageInfo getProductDetailInfo(Integer pId) {
        PageInfo pageInfo = new PageInfo();

        if (pId != null) {
            ProductWithBLOBs pwb = productMapper.selectByPrimaryKey(pId);
            if (pwb != null) {
                Map<String, String> resultMap = new HashMap<String, String>();
                resultMap.put("lonnerInfo", pwb.getLonnerInfo());
                resultMap.put("normalProblem", pwb.getNormalProblem());
                pageInfo.setObj(resultMap);
                pageInfo.setResultInfo(ResultInfo.SUCCESS);

            } else {
                pageInfo.setResultInfo(ResultInfo.NO_DATA);
            }

        } else {
            pageInfo.setResultInfo(ResultInfo.PARAM_MISS);
        }

        return pageInfo;
    }

}
