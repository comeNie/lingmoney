package com.mrbt.lingmoney.admin.controller.info;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.mrbt.lingmoney.admin.controller.base.AdminUserController;
import com.mrbt.lingmoney.admin.controller.base.BaseController;
import com.mrbt.lingmoney.admin.service.info.InfoMediaService;
import com.mrbt.lingmoney.model.InfoMedia;
import com.mrbt.lingmoney.utils.MyUtils;
import com.mrbt.lingmoney.utils.PageInfo;
import com.mrbt.lingmoney.utils.ResultParame.ResultInfo;
import com.mrbt.lingmoney.utils.ResultParame.ResultNumber;

/**
 * 页面设置——》媒体资讯
 * 
 * @author lihq
 * @date 2017年5月16日 下午2:34:45
 * @description
 * @version 1.0
 * @since 2017-04-12
 */
@Controller
@RequestMapping("/info/media")
public class InfoMediaController extends BaseController {
	private Logger log = MyUtils.getLogger(AdminUserController.class);
	@Autowired
	private InfoMediaService infoMediaService;

	/**
	 * 分页查询
	 * @param vo	封装对象
	 * @param page	页数
	 * @param rows	条数
	 * @return	返回结果
	 */
	@RequestMapping("list")
	@ResponseBody
	public Object list(InfoMedia vo, Integer page, Integer rows) {
		log.info("/info/media/list");
		PageInfo pageInfo = new PageInfo(page, rows);
		try {
			if (vo != null) {
				pageInfo = infoMediaService.list(vo, pageInfo);
				pageInfo.setCode(ResultInfo.SUCCESS.getCode());
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
	 * 返回text内容
	 * @param id	数据ID
	 * @return	返回结果
	 */
	@RequestMapping("listContent")
	@ResponseBody
	public Object listContent(Integer id) {
		log.info("/info/media/listContent");
		PageInfo pageInfo = new PageInfo();
		try {
			pageInfo.setMsg(infoMediaService.listContent(id));
			pageInfo.setCode(ResultInfo.SUCCESS.getCode());
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
	 * 
	 * @param id
	 *            主键
	 * @return 分页实体类
	 */
	@RequestMapping("publish")
	@ResponseBody
	public Object publish(Integer id) {
		log.info("/info/media/publish");
		PageInfo pageInfo = new PageInfo();
		try {
			boolean falg = infoMediaService.changeStatus(id);
			if (falg) {
				pageInfo.setCode(ResultInfo.SUCCESS.getCode());
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
	 * 添加
	 * @param vo 封装对象
	 * @return	返回结果
	 */
	@RequestMapping("save")
	@ResponseBody
	public Object save(InfoMedia vo) {
		log.info("/info/media/save");
		PageInfo pageInfo = new PageInfo();
		try {
			infoMediaService.save(vo);
			pageInfo.setCode(ResultInfo.SUCCESS.getCode());
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
	 * @param vo	封装对象
	 * @return	返回结果
	 */
	@RequestMapping("update")
	@ResponseBody
	public Object update(InfoMedia vo) {
		log.info("/info/media/update");
		PageInfo pageInfo = new PageInfo();
		try {
			if (vo.getId() != null) {
				infoMediaService.update(vo);
				pageInfo.setCode(ResultInfo.SUCCESS.getCode());
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
	 * 刷新redis
	 * @return	修改结果
	 */
	@RequestMapping("reload")
	@ResponseBody
	public Object reload() {
		log.info("/info/media/reload");
		PageInfo pageInfo = new PageInfo();
		try {
			InfoMedia media = new InfoMedia();
			media.setStatus(1);
			infoMediaService.reload(infoMediaService.list(media, 0, ResultNumber.TEN.getNumber()));
			pageInfo.setCode(ResultInfo.SUCCESS.getCode());
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
	 * @param id	数据ID
	 * @return	修改结果
	 */
	@RequestMapping("delete")
	@ResponseBody
	public Object delete(Integer id) {
		log.info("/info/media/delete");
		PageInfo pageInfo = new PageInfo();
		try {
			infoMediaService.delete(id);
			pageInfo.setCode(ResultInfo.SUCCESS.getCode());
		} catch (Exception e) {
			log.error(e.getMessage());
			e.printStackTrace();
			pageInfo.setCode(ResultInfo.SERVER_ERROR.getCode());
			pageInfo.setMsg(ResultInfo.SERVER_ERROR.getMsg());
		}
		return pageInfo;
	}
}
