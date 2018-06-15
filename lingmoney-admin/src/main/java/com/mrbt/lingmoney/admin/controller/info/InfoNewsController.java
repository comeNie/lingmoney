package com.mrbt.lingmoney.admin.controller.info;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.mrbt.lingmoney.admin.service.info.InfoNewsService;
import com.mrbt.lingmoney.model.InfoNews;
import com.mrbt.lingmoney.utils.MyUtils;
import com.mrbt.lingmoney.utils.PageInfo;
import com.mrbt.lingmoney.utils.ResultParame.ResultInfo;

/**
 * 页面设置——》公司新闻
 * 
 * @author lihq
 * @date 2017年5月19日 下午2:35:30
 * @description
 * @version 1.0
 * @since 2017-04-12
 */
@Controller
@RequestMapping("/info/news")
public class InfoNewsController {
	private Logger log = MyUtils.getLogger(InfoNewsController.class);
	@Autowired
	private InfoNewsService infoNewsService;

	/**
	 * 查询
	 * @param vo	封装对象
	 * @param page	页数
	 * @param rows	条数
	 * @return	返回列表
	 */
	@RequestMapping("list")
	@ResponseBody
	public Object list(InfoNews vo, Integer page, Integer rows) {
		log.info("/info/news/list");
		PageInfo pageInfo = new PageInfo(page, rows);
		try {
			infoNewsService.listGrid(vo, pageInfo);
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
	 * 返回text类型的内容
	 * @param id	数据ID
	 * @return	返回结果
	 */
	@RequestMapping("listContent")
	@ResponseBody
	public Object listContent(Integer id) {
		log.info("/info/news/listContent");
		PageInfo pageInfo = new PageInfo();
		try {
			pageInfo.setMsg(infoNewsService.listContent(id));
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
	 * 删除
	 * @param id	数据id
	 * @return	返回处理结果
	 */
	@RequestMapping("delete")
	@ResponseBody
	public Object delete(Integer id) {
		log.info("/info/news/delete");
		PageInfo pageInfo = new PageInfo();
		try {
			infoNewsService.delete(id);
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
	 * 修改状态
	 * @param id	数据ID
	 * @return	返回处理结果
	 */
	@RequestMapping("publish")
	@ResponseBody
	public Object publish(Integer id) {
		log.info("/info/news/publish");
		PageInfo pageInfo = new PageInfo();
		try {
			boolean falg = infoNewsService.changeStatus(id);
			if (falg) {
				pageInfo.setCode(ResultInfo.SUCCESS.getCode());
				pageInfo.setMsg(ResultInfo.SUCCESS.getMsg());
			} else {
				pageInfo.setCode(ResultInfo.EMPTY_DATA.getCode());
				pageInfo.setMsg(ResultInfo.EMPTY_DATA.getMsg());
			}
		} catch (Exception e) {
			log.error(e.getMessage());
			e.printStackTrace();
			pageInfo.setCode(ResultInfo.SERVER_ERROR.getCode());
			pageInfo.setMsg(ResultInfo.SERVER_ERROR.getMsg());
		}
		return pageInfo;
	}

	/**
	 * 新增
	 * @param vo 封装对象
	 * @return 返回处理结果
	 */
	@RequestMapping("save")
	@ResponseBody
	public Object save(InfoNews vo) {
		log.info("/info/news/save");
		PageInfo pageInfo = new PageInfo();
		try {
			infoNewsService.save(vo);
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
	 * 更新
	 * 
	 * @param vo	封装对象
	 * @return 返回处理结果
	 */
	@RequestMapping("update")
	@ResponseBody
	public Object update(InfoNews vo) {
		log.info("/info/news/update");
		PageInfo pageInfo = new PageInfo();
		try {
			infoNewsService.update(vo);
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
}
