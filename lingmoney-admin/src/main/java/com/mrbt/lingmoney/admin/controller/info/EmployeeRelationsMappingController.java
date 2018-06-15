package com.mrbt.lingmoney.admin.controller.info;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.mrbt.lingmoney.admin.service.info.EmployeeRelationsMappingService;
import com.mrbt.lingmoney.model.EmployeeRelationsMapping;
import com.mrbt.lingmoney.utils.MyUtils;
import com.mrbt.lingmoney.utils.PageInfo;
import com.mrbt.lingmoney.utils.ResultParame.ResultInfo;

/**
 * 页面设置——》员工推荐码映射
 * 
 * @author lihq
 * @date 2017年5月18日 下午3:07:21
 * @description
 * @version 1.0
 * @since 2017-04-12
 */
@Controller
@RequestMapping("/user/employeeRelationsMapping")
public class EmployeeRelationsMappingController {
	private Logger log = MyUtils.getLogger(EmployeeRelationsMappingController.class);
	@Autowired
	private EmployeeRelationsMappingService employeeRelationsMappingService;

	/**
	 * 删除
	 * @param id	id
	 * @return	x
	 */
	@RequestMapping("delete")
	@ResponseBody
	public Object delete(Integer id) {
		log.info("/user/employeeRelationsMapping/delete");
		PageInfo pageInfo = new PageInfo();
		try {
			employeeRelationsMappingService.delete(id);
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
	 * 查询
	 * @param vo	数据对象
	 * @param employeeid	员工id
	 * @param employeename	员工姓名
	 * @param rows	条数
	 * @param page	页数
	 * @return	返回列表
	 */
	@RequestMapping("list")
	@ResponseBody
	public Object list(EmployeeRelationsMapping vo, String employeeid, String employeename, Integer rows,
			Integer page) {
		log.info("/user/employeeRelationsMapping/list");
		PageInfo pageInfo = new PageInfo(page, rows);
		try {
			if (StringUtils.isNotBlank(employeeid)) {
				vo.setEmployeeid(employeeid);
			}
			if (StringUtils.isNotBlank(employeename)) {
				vo.setEmployeeName(employeename);
			}
			employeeRelationsMappingService.listGrid(vo, pageInfo);
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
	 * 添加
	 * @param vo	vo
	 * @return	处理结果
	 */
	@RequestMapping("save")
	@ResponseBody
	public Object save(EmployeeRelationsMapping vo) {
		log.info("/user/employeeRelationsMapping/save");
		PageInfo pageInfo = new PageInfo();
		try {
			employeeRelationsMappingService.save(vo);
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
	 * @param vo	vo
	 * @return	处理结果
	 */
	@RequestMapping("update")
	@ResponseBody
	public Object update(EmployeeRelationsMapping vo) {
		log.info("/user/employeeRelationsMapping/update");
		PageInfo pageInfo = new PageInfo();
		try {
			employeeRelationsMappingService.update(vo);
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
