package com.mrbt.lingmoney.admin.controller.bank;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.StringUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.mrbt.lingmoney.admin.service.bank.HxBorrowerService;
import com.mrbt.lingmoney.model.HxAccount;
import com.mrbt.lingmoney.model.HxBorrower;
import com.mrbt.lingmoney.model.HxBorrowerCustomer;
import com.mrbt.lingmoney.utils.PageInfo;
import com.mrbt.lingmoney.utils.ResultParame.ResultInfo;
import com.mrbt.lingmoney.utils.ResultParame.ResultNumber;


/**
 * 华兴银行借款人列表
 * 
 * @author lihq
 * @date 2017年6月8日 上午8:53:55
 * @description
 * @version 1.0
 * @since 2017-04-12
 */
@Controller
@RequestMapping("/bank/hxBorrower")
public class HxBorrowerController {

	private static final Logger LOGGER = LogManager.getLogger(HxBorrowerController.class);

	@Autowired
	private HxBorrowerService hxBorrowerService;

	/**
	 * 分页查询
	 * @param vo	借款人列表
	 * @param page	当前页
	 * @param rows	每页条数
	 * @return 返回数据列表
	 */
	@RequestMapping("list")
	public @ResponseBody Object list(HxBorrowerCustomer vo, Integer page, Integer rows) {
		LOGGER.info("/bank/hxBorrower/list");
		PageInfo pageInfo = new PageInfo(page, rows);
		Map<String, Object> condition = new HashMap<String, Object>();
		try {
			if (vo != null) {
				if (StringUtils.isNotBlank(vo.getBwAcname())) {
					condition.put("acName", vo.getBwAcname());
				}
				if (StringUtils.isNotBlank(vo.getMobile())) {
					condition.put("mobile", vo.getMobile());
				}
				if (vo.getStatus() != null) {
					condition.put("status", vo.getStatus());
				}
			}
			pageInfo.setCondition(condition);
			pageInfo = hxBorrowerService.getList(pageInfo);
		} catch (Exception e) {
			LOGGER.error(e.getMessage());
			e.printStackTrace();
			pageInfo.setCode(ResultInfo.SERVER_ERROR.getCode());
			pageInfo.setMsg(ResultInfo.SERVER_ERROR.getMsg());
		}
		return pageInfo;
	}

	/**
	 * 更改状态
	 * 
	 * @param id
	 *            主键
	 * @return 分页实体类
	 */
	@RequestMapping("publish")
	@ResponseBody
	public Object publish(String id) {
		PageInfo pageInfo = new PageInfo();
		try {
			pageInfo = hxBorrowerService.changeStatus(id);
		} catch (Exception e) {
			LOGGER.error(e.getMessage());
			e.printStackTrace();
			pageInfo.setCode(ResultInfo.SERVER_ERROR.getCode());
			pageInfo.setMsg(ResultInfo.SERVER_ERROR.getMsg());
		}
		return pageInfo;
	}

	/**
	 * 添加或修改
	 * 
	 * @param vo 借款人对象
	 * @return	返回处理结果
	 */
	@RequestMapping("saveAndUpdate")
	public @ResponseBody Object saveAndUpdate(HxBorrower vo) {
		PageInfo pageInfo = new PageInfo();
		try {

			if (StringUtils.isBlank(vo.getId())) { // 添加
				int a = hxBorrowerService.save(vo);
				if (a == ResultNumber.MINUS2.getNumber()) {
					pageInfo.setCode(ResultInfo.DATA_EXISTED.getCode());
					pageInfo.setMsg(ResultInfo.DATA_EXISTED.getMsg());
					return pageInfo;
				}
			} else { // 修改
				int b = hxBorrowerService.update(vo);
				if (b == ResultNumber.MINUS2.getNumber()) {
					pageInfo.setCode(ResultInfo.DATA_EXISTED.getCode());
					pageInfo.setMsg(ResultInfo.DATA_EXISTED.getMsg());
				} else if (b == ResultNumber.MINUS3.getNumber()) {
					pageInfo.setCode(ResultInfo.MODIFY_REJECT.getCode());
					pageInfo.setMsg("该借款人已发标，不可修改");
				}
				return pageInfo;
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
	 * 根据主键删除
	 * 
	 * @param id
	 *            主键
	 * @return 分页实体类
	 */
	@RequestMapping("delete")
	@ResponseBody
	public Object delete(String id) {
		PageInfo pageInfo = new PageInfo();
		try {
			pageInfo = hxBorrowerService.delete(id);
		} catch (Exception e) {
			LOGGER.error(e.getMessage());
			e.printStackTrace();
			pageInfo.setCode(ResultInfo.SERVER_ERROR.getCode());
			pageInfo.setMsg(ResultInfo.SERVER_ERROR.getMsg());
		}
		return pageInfo;
	}

	/**
	 * 分页查询华兴E账户
	 * 
	 * @param vo
	 *            实体类
	 * @param page
	 *            当前页
	 * @param rows
	 *            每页显示的条数
	 * @return 分页实体类
	 */
	@RequestMapping("hxList")
	@ResponseBody
	public Object hxList(HxAccount vo, Integer page, Integer rows) {
		PageInfo pageInfo = new PageInfo(page, rows);
		try {
			pageInfo = hxBorrowerService.getList(vo, pageInfo);
		} catch (Exception e) {
			e.printStackTrace();
			pageInfo.setCode(ResultInfo.SERVER_ERROR.getCode());
			pageInfo.setMsg(ResultInfo.SERVER_ERROR.getMsg());
		}
		return pageInfo;
	}

	/**
	 * 根据id查询华兴银行E账户姓名
	 * 
	 * @param accId 账号数据ID
	 * @return 返回查询的E账号姓名
	 */
	@RequestMapping("hxAccountName")
	public @ResponseBody Object hxAccountName(String accId) {
		PageInfo pageInfo = new PageInfo();
		try {
			pageInfo = hxBorrowerService.getHxAccountName(accId);
		} catch (Exception e) {
			e.printStackTrace();
			pageInfo.setCode(ResultInfo.SERVER_ERROR.getCode());
			pageInfo.setMsg(ResultInfo.SERVER_ERROR.getMsg());
		}
		return pageInfo;
	}
	
	/**
	 * 判断该借款人是否已借款，是则不可删除
	 * 
	 * @param bwId 借款人ID
	 * @return	返回结果
	 */
	@RequestMapping("checkIsBw")
	public @ResponseBody Object checkIsBw(String bwId) {
		PageInfo pageInfo = new PageInfo();
		try {
			pageInfo = hxBorrowerService.checkIsBw(bwId);
		} catch (Exception e) {
			e.printStackTrace();
			pageInfo.setCode(ResultInfo.SERVER_ERROR.getCode());
			pageInfo.setMsg(ResultInfo.SERVER_ERROR.getMsg());
		}
		return pageInfo;
	}
	
	
	/**
	 * 查看借款人交易流水，调用华兴账户管理接口，跳转到华兴页面
	 * @param request	request
	 * @param response	response
	 * @param acNo	E账户
	 * @return
	 */
	@RequestMapping(value = "/accountManager")
	public @ResponseBody Object accountManager(HttpServletRequest request, HttpServletResponse response, String acNo) {
		LOGGER.info("华兴账户管理接口");
		PageInfo pageInfo = new PageInfo();
		try {
			pageInfo = hxBorrowerService.accountManager("PC", acNo);
		} catch (Exception e) {
			LOGGER.info("个人客户进行重置交易密码异常：" + e.toString()); // 抛出堆栈信息
			e.printStackTrace();
			pageInfo.setResultInfo(ResultInfo.SERVER_ERROR);
		}
		return pageInfo;
	}
	
}
