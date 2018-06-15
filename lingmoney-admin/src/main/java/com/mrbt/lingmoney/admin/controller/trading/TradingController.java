package com.mrbt.lingmoney.admin.controller.trading;

import java.util.HashMap;
import java.util.Map;

import net.sf.json.JSONObject;

import org.apache.ibatis.session.RowBounds;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.mrbt.lingmoney.admin.service.trading.TradingService;
import com.mrbt.lingmoney.utils.GridPage;
import com.mrbt.lingmoney.utils.ProductUtils;
import com.mrbt.lingmoney.utils.RowBoundsUtils;

/**
 * 
 * @author Administrator
 *
 */
@Controller
@RequestMapping("/trade/trading")
public class TradingController {

	@Autowired
	private TradingService tradingService;

	/**
	 * 根据条件查询稳赢宝交易记录
	 * @param rows	条数
	 * @param page	页数
	 * @param username	用户名称
	 * @param tel	电话
	 * @param productName 产品名称
	 * @param buyDt	购买时间
	 * @return	返回记录
	 */
	@RequestMapping("wenYingList")
	@ResponseBody
	public Object wenYingList(String rows, String page, String username, String tel, String productName, String buyDt) {
		String wenyingcode = ProductUtils.getContent("wenyingbao_code");
		String chedaicode = ProductUtils.getContent("chedaibao_code");
		RowBounds rw = RowBoundsUtils.getRowBounds(page, rows);
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("pWCode", wenyingcode);
		map.put("pCCode", chedaicode);
		map.put("username", username);
		map.put("tel", tel);
		map.put("productName", productName);
		map.put("buyDt", buyDt);
		map.put("start", rw.getOffset());
		map.put("number", rw.getLimit());
		GridPage<Map<String, Object>> gridPage = tradingService.listGridWenYingBao(map);
		return gridPage;
	}

	/**
	 * 稳赢宝提前兑付
	 * @param tId	交易ID
	 * @param uId	用户ID
	 * @param minSellDt	最小兑付时间
	 * @return	return
	 */
	@RequestMapping("duifuWenYingBao")
	@ResponseBody
	public Object duifuWenYingBao(Integer tId, String uId, String minSellDt) {
		JSONObject json = new JSONObject();
		boolean b = tradingService.cashAhead(tId, minSellDt);
		if (b) {
			// 提前兑付加息金额修改
			b = tradingService.cashAheadRecountRedPacket(uId, tId);
			if (b) {
				json.put("code", "200");
				json.put("msg", "提前兑付成功");
			} else {
				json.put("code", "0000");
				json.put("msg", "提前兑付失败");
			}
		} else {
			json.put("code", "0000");
			json.put("msg", "提前兑付失败");
			json.put("data", tId + "_" + uId);
		}
		return json;
	}
}
