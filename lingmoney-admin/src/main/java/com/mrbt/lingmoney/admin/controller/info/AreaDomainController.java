package com.mrbt.lingmoney.admin.controller.info;

import java.util.HashMap;
import java.util.Map;

import net.sf.json.JSONObject;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.mrbt.lingmoney.admin.service.info.AreaDomainService;
import com.mrbt.lingmoney.model.AreaDomain;
import com.mrbt.lingmoney.utils.MyUtils;
import com.mrbt.lingmoney.utils.PageInfo;
import com.mrbt.lingmoney.utils.ResultParame.ResultInfo;

/**
 * @author yiban sun
 * @date 2016年11月9日 下午2:22:52
 * @version 1.0
 * @description 页面设置--》地区页面配置 管理不同地区访问地址已经城市代码
 * @since
 **/
@Controller
@RequestMapping("/areaDomain")
public class AreaDomainController {
	
	private Logger log = MyUtils.getLogger(AreaDomainController.class);
	
	@Autowired
	private AreaDomainService areaDomainService;
	
	
	/**
	 * 查询城市信息
	 * 所属地区用的json
	 * @return map  {"000","全国"}
	 */
	@RequestMapping("queryCityInfo")
	@ResponseBody
	public Object queryCityInfo() {
		log.info("/areaDomain/queryCityInfo");
		PageInfo pageInfo = new PageInfo();
		try {
			pageInfo = areaDomainService.queryCityInfo(pageInfo);
			pageInfo.setCode(ResultInfo.SUCCESS.getCode());
			pageInfo.setMsg(ResultInfo.SUCCESS.getMsg());
		} catch (Exception e) {
			log.error(e.getMessage());
			e.printStackTrace();
			pageInfo.setCode(ResultInfo.SERVER_ERROR.getCode());
			pageInfo.setMsg(ResultInfo.SERVER_ERROR.getMsg());
		}
		return pageInfo;

	}

	/**
	 * 查询所有分页
	 * 
	 * @Description
	 * @param rows	rows
	 * @param page	page
	 * @param cityName 城市名（模糊查询）
	 * @param provinceName 省份名（模糊查询）
	 * @return	return
	 */
	@RequestMapping("queryAll")
	@ResponseBody
	public Object queryAll(Integer rows, Integer page, String cityName, String provinceName) {
		log.info("/areaDomain/queryAll");
		PageInfo pageInfo = new PageInfo(page, rows);
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("start", pageInfo.getFrom());
		map.put("number", pageInfo.getSize());
		map.put("cityName", cityName);
		map.put("provinceName", provinceName);
		try {
			pageInfo = areaDomainService.listGrid(map);
			pageInfo.setCode(ResultInfo.SUCCESS.getCode());
			pageInfo.setMsg(ResultInfo.SUCCESS.getMsg());
		} catch (Exception e) {
			log.error(e.getMessage());
			e.printStackTrace();
			pageInfo.setCode(ResultInfo.SERVER_ERROR.getCode());
			pageInfo.setMsg(ResultInfo.SERVER_ERROR.getMsg());
		}
		return pageInfo;

	}

	/**
	 * 有ID更新，没ID添加
	 * 
	 * @Description
	 * @param ad	ad
	 * @return	return
	 */
	@RequestMapping("save")
	@ResponseBody
	public Object save(AreaDomain ad) {
		log.info("/areaDomain/save");
		JSONObject json = new JSONObject();
		boolean b;
		if (null == ad.getId()) {
			b = areaDomainService.insert(ad);
		} else {
			b = areaDomainService.update(ad);
		}

		if (b) {
			json.put("code", "200");
			json.put("msg", "操作成功");
		} else {
			json.put("code", "0000");
			json.put("msg", "操作失败");
		}
		return json;
	}

	/**
	 * 查询城市代码：城市名
	 * 
	 * @Description 用于下拉列表
	 * @return	return
	 */
	@RequestMapping("queryCodeName")
	@ResponseBody
	public Object queryCodeName() {
		log.info("/areaDomain/queryCodeName");
		return areaDomainService.queryCodeName();
	}

}
