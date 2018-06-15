package com.mrbt.lingmoney.admin.controller.trading;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.mrbt.lingmoney.admin.service.trading.RedeemFailFlowService;
import com.mrbt.lingmoney.model.RedeemFailFlow;
import com.mrbt.lingmoney.utils.GridPage;
import com.mrbt.lingmoney.utils.PageInfo;
import com.mrbt.lingmoney.utils.ResultParame.ResultInfo;
import com.mrbt.lingmoney.utils.RowBoundsUtils;

/**
 * 企业管理--》赎回失败流水
 * 
 * @author yiban 2017年11月16日 下午2:51:23
 *
 */

@Controller
@RequestMapping("/trading/redeemFailFlow")
public class RedeemFailFlowController {
    @Autowired
    private RedeemFailFlowService redeemFailFlowService;

    /**
     * 查询
     * @param vo	封装
     * @param page	页数
     * @param rows	条数
     * @param status	状态
     * @return	return
     */
    @RequestMapping("list")
    @ResponseBody
    public Object list(RedeemFailFlow vo, String rows, String page, Integer status) {
        GridPage<RedeemFailFlow> gridPage = redeemFailFlowService.listGrid(vo, RowBoundsUtils.getRowBounds(page, rows));
        return gridPage;
    }

    /**
     * 赎回
     * @param id	数据ID
     * @return	RETURN
     */
    @RequestMapping("redeem")
    @ResponseBody
    public Object redeem(Integer id) {
        PageInfo pageInfo = new PageInfo();

        try {
        	pageInfo = redeemFailFlowService.redeem(id);
        } catch (Exception e) {
        	pageInfo.setCode(ResultInfo.SERVER_ERROR.getCode());
			pageInfo.setMsg(ResultInfo.SERVER_ERROR.getMsg());
            e.printStackTrace();
        }

        return pageInfo;
    }

}
