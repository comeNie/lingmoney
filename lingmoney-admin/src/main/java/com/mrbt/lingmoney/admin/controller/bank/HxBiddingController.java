package com.mrbt.lingmoney.admin.controller.bank;

import java.util.HashMap;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.mrbt.lingmoney.admin.service.bank.HxBiddingService;
import com.mrbt.lingmoney.admin.service.bank.HxBorrowerInfoService;
import com.mrbt.lingmoney.admin.service.bank.SingleCancelBiddingService;
import com.mrbt.lingmoney.admin.service.bank.SingleIssueBidInfoNoticeService;
import com.mrbt.lingmoney.model.HxBidderCustomer;
import com.mrbt.lingmoney.model.HxBidding;
import com.mrbt.lingmoney.model.HxBiddingCustomer;
import com.mrbt.lingmoney.model.HxBorrowerInfo;
import com.mrbt.lingmoney.model.HxBorrowerInfoCustomer;
import com.mrbt.lingmoney.utils.PageInfo;
import com.mrbt.lingmoney.utils.ResultParame.ResultInfo;
import com.mrbt.lingmoney.utils.ResultParame.ResultNumber;

/**
 * 华兴银行标的后台相关controller
 * 
 * @author lihq
 * @date 2017年6月7日 下午1:36:48
 * @description
 * @version 1.0
 * @since 2017-04-12
 */
@Controller
@RequestMapping("/bank")
public class HxBiddingController {

	private static final Logger LOGGER = LogManager.getLogger(HxBiddingController.class);

	@Autowired
	private HxBiddingService hxBiddingService;
	@Autowired
	private HxBorrowerInfoService hxBorrowerInfoService;
	@Autowired
	private SingleIssueBidInfoNoticeService singleIssueBidInfoNoticeService;
	@Autowired
	private SingleCancelBiddingService singleCancelBiddingService;

	/**
	 * 发起标的
	 * 
	 * @param biddingId	标的ID
	 * @return 返回处理结果
	 */
	@RequestMapping("hxBidding/bidding")
	@ResponseBody
	public Object bidding(String biddingId) {
		LOGGER.info("/bank/hxBidding/bidding");
		PageInfo pageInfo = new PageInfo();
		try {
			if (StringUtils.isNotBlank(biddingId)) {
				pageInfo = singleIssueBidInfoNoticeService.requestSingleIssueBidInfoNotice("PC", biddingId);
			} else {
				pageInfo.setCode(ResultInfo.PARAM_MISS.getCode());
				pageInfo.setMsg(ResultInfo.PARAM_MISS.getMsg());
			}
		} catch (Exception e) {
			LOGGER.error(e.getMessage());
			e.printStackTrace();
			pageInfo.setCode(ResultInfo.SERVER_ERROR.getCode());
			pageInfo.setMsg(ResultInfo.SERVER_ERROR.getMsg());
		}
		return pageInfo;
	}

	/**
	 * 分页查询标的
	 * 
	 * @param vo	标的对象
	 * @param page	当前页
	 * @param rows	每页条数
	 * @return	返回数据列表
	 */
	@RequestMapping("hxBidding/list")
    public @ResponseBody Object list(HxBiddingCustomer vo, String proStatus, Integer page, Integer rows) {
		LOGGER.info("查询标的列表");
		PageInfo pageInfo = new PageInfo(page, rows);
		Map<String, Object> condition = new HashMap<String, Object>();
		try {
			if (vo != null) {
				// 标的id
				if (StringUtils.isNotBlank(vo.getId())) {
					condition.put("id", vo.getId());
				}
				// 产品id
				if (vo.getpId() != null) {
					condition.put("pId", vo.getpId());
				}
				// 标的状态
				if (StringUtils.isNotBlank(vo.getInvestObjState())) {
					condition.put("investObjState", vo.getInvestObjState());
				}
				// 借款编号
				if (StringUtils.isNotBlank(vo.getLoanNo())) {
					condition.put("loanNo", vo.getLoanNo());
				}
                if (StringUtils.isNotBlank(vo.getRepayDate())) {
                    condition.put("repayDate", vo.getRepayDate().replace("-", ""));
                }
                if (StringUtils.isNotBlank(proStatus)) {
                    condition.put("pStatus", proStatus);
                }
				pageInfo.setCondition(condition);
				pageInfo = hxBiddingService.getBiddingList(pageInfo);
			} else {
				pageInfo.setCode(ResultInfo.PARAM_MISS.getCode());
				pageInfo.setMsg(ResultInfo.PARAM_MISS.getMsg());
			}

		} catch (Exception e) {
			LOGGER.error(e.getMessage());
			e.printStackTrace();
			pageInfo.setCode(ResultInfo.SERVER_ERROR.getCode());
			pageInfo.setMsg(ResultInfo.SERVER_ERROR.getMsg());
		}
		return pageInfo;
	}

	/**
	 * 更新标的
	 * 
	 * @param vo	标的对象
	 * @return	返回处理结果
	 */
	@RequestMapping("hxBidding/update")
	@ResponseBody
	public Object update(HxBidding vo) {
		LOGGER.info("/bank/hxBidding/update");
		PageInfo pageInfo = new PageInfo();
		try {
			if (vo.getId() != null) {
				hxBiddingService.update(vo);
				pageInfo.setCode(ResultInfo.SUCCESS.getCode());
				pageInfo.setMsg(ResultInfo.SUCCESS.getMsg());
			} else {
				pageInfo.setCode(ResultInfo.PARAM_MISS.getCode());
				pageInfo.setMsg(ResultInfo.PARAM_MISS.getMsg());
			}
		} catch (Exception e) {
			LOGGER.error(e.getMessage());
			e.printStackTrace();
			pageInfo.setCode(ResultInfo.SERVER_ERROR.getCode());
			pageInfo.setMsg(ResultInfo.SERVER_ERROR.getMsg());
		}
		return pageInfo;
	}

	/**
	 * 查询标的借款人列表
	 * 
	 * @param vo 借款人对象
	 * @param page 当前页
	 * @param rows 每页显示的条数
	 * @return 分页实体类
	 */
	@RequestMapping("hxBorrowerInfo/list")
	@ResponseBody
	public Object list(HxBorrowerInfoCustomer vo, Integer page, Integer rows) {
		LOGGER.info("/bank/hxBorrowerInfo/list");
		PageInfo pageInfo = new PageInfo(page, rows);
		Map<String, Object> condition = new HashMap<String, Object>();
		try {
			if (vo != null) {
				// 标的id
				if (vo.getBiddingId() != null) {
					condition.put("biddingId", vo.getBiddingId());
				}
				// 状态
				if (vo.getStatus() != null) {
					condition.put("status", vo.getStatus());
				}
			}
			pageInfo.setCondition(condition);
			pageInfo = hxBorrowerInfoService.getList(pageInfo);
		} catch (Exception e) {
			LOGGER.error(e.getMessage());
			e.printStackTrace();
			pageInfo.setCode(ResultInfo.SERVER_ERROR.getCode());
			pageInfo.setMsg(ResultInfo.SERVER_ERROR.getMsg());
		}
		return pageInfo;
	}

	/**
	 * 添加或修改借款人
	 * 
	 * @param vo 借款人对象
	 * @return 分页实体类
	 */
	@RequestMapping("hxBorrowerInfo/saveAndUpdate")
	public @ResponseBody Object saveAndUpdate(HxBorrowerInfo vo) {
		LOGGER.info("添加或修改借款人");
		PageInfo pageInfo = new PageInfo();
		try {
			if (StringUtils.isBlank(vo.getId())) {
				int a = hxBorrowerInfoService.save(vo);
				if (a == ResultNumber.MINUS3.getNumber() || a == ResultNumber.MINUS2.getNumber()) {
					pageInfo.setCode(ResultInfo.DATA_EXISTED.getCode());
					pageInfo.setMsg("暂时只能添加一个借款人, 该借款人已添加");
					return pageInfo;
				}
			} else {
				int b = hxBorrowerInfoService.updateByPrimaryKeySelective(vo);
				if (b == ResultNumber.MINUS2.getNumber()) {
					pageInfo.setCode(ResultInfo.DATA_EXISTED.getCode());
					pageInfo.setMsg("该借款人已添加");
					return pageInfo;
				}
			}
			pageInfo.setCode(ResultInfo.SUCCESS.getCode());
			pageInfo.setMsg(ResultInfo.SUCCESS.getMsg());
		} catch (Exception e) {
			LOGGER.error(e.getMessage());
			e.printStackTrace();
			pageInfo.setCode(ResultInfo.SERVER_ERROR.getCode());
			pageInfo.setMsg(ResultInfo.SERVER_ERROR.getMsg());
		}
		return pageInfo;
	}
	
	/**
	 * 删除借款人
	 * @param id 借款人ID
	 * @return 分页实体类
	 */
	@RequestMapping("hxBorrowerInfo/delete")
	@ResponseBody
	public Object delete(String id) {
		LOGGER.info("/bank/hxBorrowerInfo/delete");
		PageInfo pageInfo = new PageInfo();
		try {
			if (StringUtils.isNotBlank(id)) {
				hxBorrowerInfoService.delete(id);
				pageInfo.setCode(ResultInfo.SUCCESS.getCode());
				pageInfo.setMsg(ResultInfo.SUCCESS.getMsg());
			} else {
				pageInfo.setCode(ResultInfo.PARAM_MISS.getCode());
				pageInfo.setMsg(ResultInfo.PARAM_MISS.getMsg());
			}
		} catch (Exception e) {
			LOGGER.error(e.getMessage());
			e.printStackTrace();
			pageInfo.setCode(ResultInfo.SERVER_ERROR.getCode());
			pageInfo.setMsg(ResultInfo.SERVER_ERROR.getMsg());
		}
		return pageInfo;
	}

	/**
	 * 查询投标人列表
	 * 
	 * @param vo 投标人对象
	 * @param page
	 *            当前页
	 * @param rows
	 *            每页显示的条数
	 * @return 分页实体类
	 */
	@RequestMapping("hxBidder/list")
	@ResponseBody
	public Object list(HxBidderCustomer vo, Integer page, Integer rows) {
		LOGGER.info("/bank/hxBidder/list");
		PageInfo pageInfo = new PageInfo(page, rows);
		Map<String, Object> condition = new HashMap<String, Object>();
		try {
			if (vo != null) {
				// 标的id
				if (StringUtils.isNotBlank(vo.getbId())) {
					condition.put("bId", vo.getbId());
				}
				// 用户id
				if (StringUtils.isNotBlank(vo.getuId())) {
					condition.put("uId", vo.getuId());
				}
				// 状态
				if (vo.getStatus() != null) {
					condition.put("status", vo.getStatus());
				}
			}
			pageInfo.setCondition(condition);
			pageInfo = hxBiddingService.getBidderList(pageInfo);
		} catch (Exception e) {
			LOGGER.error(e.getMessage());
			e.printStackTrace();
			pageInfo.setCode(ResultInfo.SERVER_ERROR.getCode());
			pageInfo.setMsg(ResultInfo.SERVER_ERROR.getMsg());
		}
		return pageInfo;
	}

	/**
	 * 单笔撤标
	 * 
	 * @param id
	 *            交易ID
	 * @param cancelReason
	 *            取消原因
	 * @return	返回处理结果
	 */
	@RequestMapping("hxBidding/cancelBidding")
	@ResponseBody
	public Object cancelBidding(Integer id, String cancelReason) {
		LOGGER.info("/bank/hxBidding/cancelBidding");
		PageInfo pageInfo = new PageInfo();
		try {
			if (id != null && id > 0) {
				pageInfo = singleCancelBiddingService.requestSingleCancelBidding(0, "PC", id, cancelReason);
			} else {
				pageInfo.setCode(ResultInfo.PARAM_MISS.getCode());
				pageInfo.setMsg(ResultInfo.PARAM_MISS.getMsg());
			}
		} catch (Exception e) {
			LOGGER.error(e.getMessage());
			e.printStackTrace();
			pageInfo.setCode(ResultInfo.SERVER_ERROR.getCode());
			pageInfo.setMsg(ResultInfo.SERVER_ERROR.getMsg());
		}
		return pageInfo;
	}

    /**
     * 手动成立标的
     * 
     * @author yiban
     * @date 2017年10月31日 上午10:02:41
     * @version 1.0
     * @param pCode  产品code
     * @return 返回处理结果
     *
     */
    @RequestMapping(value = "hxBidding/manualEstablish", method = RequestMethod.POST)
    public @ResponseBody Object manualEstablish(String pCode) {
        PageInfo pageInfo = new PageInfo();

        try {
            pageInfo = hxBiddingService.manualEstablish(pCode);
        } catch (Exception e) {
            pageInfo.setCode(ResultInfo.SERVER_ERROR.getCode());
            pageInfo.setMsg(ResultInfo.SERVER_ERROR.getMsg());
            e.printStackTrace();
        }

        return pageInfo;
    }
}
