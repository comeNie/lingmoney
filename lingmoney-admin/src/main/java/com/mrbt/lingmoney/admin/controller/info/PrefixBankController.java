package com.mrbt.lingmoney.admin.controller.info;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletResponse;

import net.sf.json.JSONObject;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.mrbt.lingmoney.admin.service.info.PrefixBankService;
import com.mrbt.lingmoney.model.PrefixBank;
import com.mrbt.lingmoney.utils.MyUtils;
import com.mrbt.lingmoney.utils.PageInfo;

/**
 * @author yiban sun
 * @date 2016年8月25日 下午9:08:32
 * @version 1.0
 * @description 银行卡前六位管理
 * @since
 **/
@Controller
@RequestMapping("/info/prefixBank")
public class PrefixBankController {
	private Logger log = MyUtils.getLogger(PrefixBankController.class);
	@Autowired
	private PrefixBankService prefixBankService;

	/**
	 * 添加
	 * @param response	response
	 * @param record	封装对象
	 * @return	返回结果
	 */
	@RequestMapping(value = "/save", method = RequestMethod.GET)
	public @ResponseBody Object save(HttpServletResponse response, @ModelAttribute("record") PrefixBank record) {
		log.info("/user/prefixBank/save");

		JSONObject json = new JSONObject();

		int i = prefixBankService.insert(record);
		if (i > 0) {
			json.put("status", "1");
			json.put("msg", "添加成功");
		} else {
			json.put("status", "0");
			json.put("msg", "添加失败");
		}
		return json;
	}

	/**
	 * 删除
	 * @param id	数据ID
	 * @return	处理结果
	 */
	@RequestMapping(value = "/delete", method = RequestMethod.GET)
	public @ResponseBody Object delete(Integer id) {
		JSONObject json = new JSONObject();
		int i = prefixBankService.deleteByPrimaryKey(id);
		if (i > 0) {
			json.put("status", "1");
			json.put("msg", "删除成功");
		} else {
			json.put("status", "0");
			json.put("msg", "删除失败");
		}
		return json;
	}

	/**
	 * 更新
	 * @param response	res
	 * @param record	封装对象
	 * @return	返回结果
	 */
	@RequestMapping(value = "/update", method = RequestMethod.GET)
	public @ResponseBody Object update(HttpServletResponse response, @ModelAttribute("record") PrefixBank record) {
		JSONObject json = new JSONObject();
		int i = prefixBankService.updateByPrimaryKey(record);
		if (i > 0) {
			json.put("status", "1");
			json.put("msg", "修改成功");
		} else {
			json.put("status", "0");
			json.put("msg", "修改失败");
		}
		return json;
	}

	/**
	 * 查询
	 * @param record	封装对象
	 * @param page	页数
	 * @param rows	条数
	 * @return	结果列表
	 */
	@RequestMapping(value = "/query", method = RequestMethod.GET)
	public @ResponseBody Object query(@ModelAttribute("record") PrefixBank record, Integer page, Integer rows) {
		Map<String, Object> map = new HashMap<String, Object>();
		PageInfo pi = new PageInfo(page, rows);
		map.put("number", pi.getSize());
		map.put("start", pi.getFrom());
		map.put("name", record.getName());
		map.put("prefixNumber", record.getPrefixNumber());
		map.put("bankShort", record.getBankShort());
		map.put("status", record.getStatus());
		List<PrefixBank> pb = prefixBankService.selectByCondition(map);
		int total = prefixBankService.selectByConditionCount(map);

		Map<String, Object> maps = new HashMap<String, Object>();
		maps.put("total", total);
		maps.put("rows", pb);

		return JSONObject.fromObject(maps);
	}

}
