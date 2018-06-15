package com.mrbt.lingmoney.admin.controller.bank;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.mrbt.lingmoney.admin.service.bank.HxEnterpriseBindCardService;
import com.mrbt.lingmoney.utils.PageInfo;
import com.mrbt.lingmoney.utils.ResultParame.ResultInfo;

/**
 * 企业绑卡
 * 
 * @author syb
 * @date 2017年9月29日 下午2:06:32
 * @version 1.0
 **/
@Controller
@RequestMapping("/bank/enterpriseBindCard")
public class HxEnterpriseBindCardController {

	@Autowired
	private HxEnterpriseBindCardService hxEnterpriseBindCardService;

    /**
     * 企业绑定卡信息查询
     * 
     * @author yiban
     * @date 2017年12月6日 下午5:19:21
     * @version 1.0
     * @param acNo 企业账号
     * @param page	当前页数
     * @param rows	每页条数
     * @return	返回查询结果
     *
     */
	@RequestMapping(value = "/listEnterpriseBindCard", method = RequestMethod.POST)
	public @ResponseBody Object listEnterpriseBindCard(String acNo, Integer page, Integer rows) {
		PageInfo pageInfo = new PageInfo();
		try {
			pageInfo = hxEnterpriseBindCardService.listEnterpriseBindCard(acNo, page, rows);
		} catch (Exception e) {
			pageInfo.setCode(ResultInfo.SERVER_ERROR.getCode());
			pageInfo.setMsg(ResultInfo.SERVER_ERROR.getMsg());
			e.printStackTrace();
		}

		return pageInfo;
	}

    /**
     * 企业绑定卡解绑
     * 
     * @author yiban
     * @date 2017年12月6日 下午5:20:04
     * @version 1.0
     * @param bankNo  银行账号（存管账户）
     * @return	返回
     *
     */
	@RequestMapping(value = "/unBindCard", method = RequestMethod.POST)
	public @ResponseBody Object unBindCard(String bankNo) {
		String logGroup = "\nhxEnterpriseUnBindCard_" + System.currentTimeMillis() + "_";
		return hxEnterpriseBindCardService.requestUnBindCard("PC", 0, bankNo, logGroup);
	}

    /**
     * 查询企业充值来账记录
     * 
     * @author yiban
     * @date 2017年12月6日 下午5:21:19
     * @version 1.0
     * @param transdate 交易日期
     * @param bankNo 银行账号
     * @param transType 交易类型 0收入 1支出
     * @param page	当前页数
     * @param rows	每页条数
     * @return	返回查询列表
     *
     */
	@RequestMapping(value = "/listEnterpriseRechargeRecord", method = RequestMethod.POST)
	public @ResponseBody Object listEnterpriseRechargeRecord(String transdate, String bankNo, String transType,
			Integer page, Integer rows) {
		PageInfo pageInfo = new PageInfo();
		try {
			pageInfo = hxEnterpriseBindCardService.listEnterpriseRechargeRecord(transdate, bankNo, transType, page, rows);
		} catch (Exception e) {
			pageInfo.setCode(ResultInfo.SERVER_ERROR.getCode());
			pageInfo.setMsg(ResultInfo.SERVER_ERROR.getMsg());
			e.printStackTrace();
		}
		return pageInfo;
	}

}
