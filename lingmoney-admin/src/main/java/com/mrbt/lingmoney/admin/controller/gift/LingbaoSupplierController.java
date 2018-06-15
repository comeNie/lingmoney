package com.mrbt.lingmoney.admin.controller.gift;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.mrbt.lingmoney.admin.service.gift.LingbaoSupplierService;
import com.mrbt.lingmoney.model.LingbaoSupplier;
import com.mrbt.lingmoney.model.LingbaoSupplierExample;
import com.mrbt.lingmoney.utils.MyUtils;
import com.mrbt.lingmoney.utils.PageInfo;
import com.mrbt.lingmoney.utils.ResultParame.ResultInfo;

/**
 * 我的领地供应商
 * 
 * @author lhq
 *
 */
@Controller
@RequestMapping("/gift/lingbaoSupplier")
public class LingbaoSupplierController {

	private Logger log = MyUtils.getLogger(LingbaoSupplierController.class);

	@Autowired
	private LingbaoSupplierService lingbaoSupplierService;

	/**
	 * 更改我的领地供应商状态
	 * 
	 * @param id
	 *            主键
	 * @return 分页实体类
	 */
	@RequestMapping("publish")
	@ResponseBody
	public Object publish(Integer id) {
		PageInfo pageInfo = new PageInfo();
		try {
			boolean flag = lingbaoSupplierService.changeStatus(id);
			if (flag) {
				pageInfo.setCode(ResultInfo.SUCCESS.getCode());
				pageInfo.setMsg(ResultInfo.SUCCESS.getMsg());
			} else {
				pageInfo.setCode(ResultInfo.PARAMETER_ERROR.getCode());
				pageInfo.setMsg(ResultInfo.PARAMETER_ERROR.getMsg());
			}
		} catch (Exception e) {
			e.printStackTrace();
			log.error("我的领地供应商，更改我的领地供应商状态，失败原因是：" + e);
			pageInfo.setCode(ResultInfo.SERVER_ERROR.getCode());
			pageInfo.setMsg(ResultInfo.SERVER_ERROR.getMsg());
		}
		return pageInfo;
	}

	/**
	 * 添加或修改我的领地供应商
	 * 
	 * @param vo
	 *            我的领地供应商
	 * @return 分页实体类
	 */
	@RequestMapping("saveAndUpdate")
	@ResponseBody
	public Object saveAndUpdate(LingbaoSupplier vo) {
		PageInfo pageInfo = new PageInfo();
		try {
			if (vo.getId() == null || vo.getId() <= 0) {
				vo.setStatus(0);
				lingbaoSupplierService.save(vo);
			} else {
				lingbaoSupplierService.update(vo);
			}
			pageInfo.setCode(ResultInfo.SUCCESS.getCode());
			pageInfo.setMsg(ResultInfo.SUCCESS.getMsg());
		} catch (Exception e) {
			e.printStackTrace();
			log.error("我的领地供应商，添加或修改我的领地供应商，失败原因是：" + e);
			pageInfo.setCode(ResultInfo.SERVER_ERROR.getCode());
			pageInfo.setMsg(ResultInfo.SERVER_ERROR.getMsg());
		}
		return pageInfo;
	}

	/**
	 * 查询我的领地供应商列表
	 * 
	 * @param vo
	 *            我的领地供应商
	 * @param page
	 *            当前页
	 * @param rows
	 *            每页显示的条数
	 * @return 分页实体类
	 */
	@RequestMapping("list")
	@ResponseBody
	public Object list(LingbaoSupplier vo, Integer page, Integer rows) {
		PageInfo pageInfo = new PageInfo(page, rows);
		try {
			LingbaoSupplierExample example = new LingbaoSupplierExample();
			LingbaoSupplierExample.Criteria cri = example.createCriteria();
			if (StringUtils.isNotBlank(vo.getName())) {
				cri.andNameLike("%" + vo.getName() + "%");
			}
			if (vo.getStatus() != null) {
				cri.andStatusEqualTo(vo.getStatus());
			}
			if (vo.getId() != null) {
				cri.andIdEqualTo(vo.getId());
			}
			if (page != null && rows != null) {
				example.setLimitStart(pageInfo.getFrom());
				example.setLimitEnd(pageInfo.getSize());
			}
			pageInfo = lingbaoSupplierService.getList(example);
		} catch (Exception e) {
			e.printStackTrace();
			log.error("我的领地供应商，查询我的领地供应商列表，失败原因是：" + e);
			pageInfo.setCode(ResultInfo.SERVER_ERROR.getCode());
			pageInfo.setMsg(ResultInfo.SERVER_ERROR.getMsg());
		}
		return pageInfo;
	}

	/**
	 * 删除我的领地供应商
	 * 
	 * @param id
	 *            主键
	 * @return 分页实体类
	 */
	@RequestMapping("delete")
	@ResponseBody
	public Object delete(Integer id) {
		PageInfo pageInfo = new PageInfo();
		try {
			if (id != null && id > 0) {
				lingbaoSupplierService.delete(id);
				pageInfo.setCode(ResultInfo.SUCCESS.getCode());
				pageInfo.setMsg(ResultInfo.SUCCESS.getMsg());
			} else {
				pageInfo.setCode(ResultInfo.PARAMETER_ERROR.getCode());
				pageInfo.setMsg(ResultInfo.PARAMETER_ERROR.getMsg());
			}
		} catch (Exception e) {
			e.printStackTrace();
			log.error("我的领地供应商，删除我的领地供应商，失败原因是：" + e);
			pageInfo.setCode(ResultInfo.SERVER_ERROR.getCode());
			pageInfo.setMsg(ResultInfo.SERVER_ERROR.getMsg());
		}
		return pageInfo;
	}

}
