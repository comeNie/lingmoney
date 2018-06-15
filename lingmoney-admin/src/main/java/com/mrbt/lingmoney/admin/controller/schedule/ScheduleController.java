package com.mrbt.lingmoney.admin.controller.schedule;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.lang.math.NumberUtils;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.mrbt.lingmoney.admin.service.schedule.ScheduleService;
import com.mrbt.lingmoney.model.schedule.ScheduleJob;
import com.mrbt.lingmoney.model.schedule.ScheduleJobExample;
import com.mrbt.lingmoney.utils.PageInfo;
import com.mrbt.lingmoney.utils.ResultParame.ResultInfo;

/**
 *@author syb
 *@date 2017年5月17日 上午10:29:11
 *@version 1.0
 *@description 定时任务管理
 **/
@Controller
@RequestMapping("/schedule")
public class ScheduleController {
	private static final Logger LOG = LogManager.getLogger(ScheduleController.class);
	@Autowired
	private ScheduleService scheduleService;
	
	/**
	 * 任务修改
	 * @param scheduleJob	 scheduleJob对象
	 * @return	返回结果
	 */
	@RequestMapping(value = "/editSchedule")
	public @ResponseBody Object editSchedule(ScheduleJob scheduleJob) {
		PageInfo pageInfo = new PageInfo();
		try {
			pageInfo = scheduleService.editSchedule(scheduleJob);
		} catch (Exception e) {
			pageInfo.setCode(ResultInfo.SERVER_ERROR.getCode());
			pageInfo.setMsg(ResultInfo.SERVER_ERROR.getMsg());
			e.printStackTrace();
		}

		return pageInfo;
	}

	/**
	 * 删除任务
	 * 
	 * @param jobId jobId
	 * @return	return
	 */
	@RequestMapping(value = "/removeSchedule")
	public @ResponseBody Object removeSchedule(Integer jobId) {
		PageInfo pageInfo = new PageInfo();
		try {
			pageInfo = scheduleService.removeSchedule(jobId);
		} catch (Exception e) {
			pageInfo.setCode(ResultInfo.SERVER_ERROR.getCode());
			pageInfo.setMsg(ResultInfo.SERVER_ERROR.getMsg());
			e.printStackTrace();
		}
		return pageInfo;
	}

	/**
	 * 请求任务添加页
	 * @param request	request
	 * @return	return
	 */
	@RequestMapping(value = "/scheduleAdd", method = RequestMethod.GET)
	public String taskAdd(HttpServletRequest request) {
		return "schedule/scheduleAdd";
	}

	/**
	 * 请求任务列表
	 * @param request	request
	 * @param page	页数
	 * @param rows	条数
	 * @param jobId	任务ID
	 * @param jobStatus	任务状态
	 * @param jobName	任务名称
	 * @return	return
	 */
	@RequestMapping("/scheduleList")
	public @ResponseBody Object taskList(HttpServletRequest request, Integer page, Integer rows, Integer jobId,
			String jobStatus, String jobName) {
		PageInfo retObj = null;
		if (page != null && rows != null) {
			retObj = new PageInfo(page, rows);
			ScheduleJobExample example = new ScheduleJobExample();
			example.setLimitStart(retObj.getFrom());
			example.setLimitEnd(retObj.getSize());
			ScheduleJobExample.Criteria cri = example.createCriteria();
			if (jobId != null) {
				cri.andJobIdEqualTo(jobId);
			}
			if (StringUtils.isNotBlank(jobStatus)) {
				cri.andJobStatusEqualTo(NumberUtils.toInt(jobStatus));
			}
			if (StringUtils.isNotBlank(jobName)) {
				cri.andJobNameEqualTo(jobName);
			}
			retObj = scheduleService.getAllTask(example);
			retObj.setCode(ResultInfo.SUCCESS.getCode());
			retObj.setMsg(ResultInfo.SUCCESS.getMsg());
		} else {
			retObj = new PageInfo();
			retObj.setCode(ResultInfo.PARAMETER_ERROR.getCode());
			retObj.setMsg(ResultInfo.PARAMETER_ERROR.getMsg());
		}

		return retObj;
	}

	/**
	 * 添加任务
	 * 
	 * @param request	request
	 * @param scheduleJob	scheduleJob
	 * @return	return
	 */
	@RequestMapping(value = "/scheduleAdd", method = RequestMethod.POST)
	@ResponseBody
	public PageInfo addTask(HttpServletRequest request, ScheduleJob scheduleJob) {
		PageInfo pageInfo = new PageInfo();
		try {
			pageInfo = scheduleService.addTask(scheduleJob);
		} catch (Exception e) {
			e.printStackTrace();
			pageInfo.setCode(ResultInfo.SERVER_ERROR.getCode());
			pageInfo.setMsg(ResultInfo.SERVER_ERROR.getMsg());
		}
		return pageInfo;
	}

	/**
	 * 修改任务状态
	 * @param request	request
	 * @param jobId	任务ID
	 * @param status	状态
	 * @return	return	
	 */
	@RequestMapping(value = "/changeJobStatus", method = RequestMethod.POST)
	@ResponseBody
	public PageInfo changeJobStatus(HttpServletRequest request, Integer jobId, Integer status) {
		PageInfo pageInfo = new PageInfo();
		try {
			pageInfo = scheduleService.changeStatus(jobId, status);
		} catch (Exception e) {
			LOG.error(e.getMessage(), e);
			pageInfo.setCode(ResultInfo.SERVER_ERROR.getCode());
			pageInfo.setMsg(ResultInfo.SERVER_ERROR.getMsg());
		}
		return pageInfo;
	}

	/**
	 * 更改任务执行时间
	 * @param request	request
	 * @param jobId	任务ID
	 * @param cron	定时配置
	 * @return	return
	 */
	@ResponseBody
	@RequestMapping(value = "/updateCron", method = RequestMethod.POST)
	public PageInfo updateCron(HttpServletRequest request, Integer jobId, String cron) {
		PageInfo pageInfo = new PageInfo();
		try {
			pageInfo = scheduleService.updateCron(jobId, cron);
		} catch (Exception e) {
			pageInfo.setCode(ResultInfo.SERVER_ERROR.getCode());
			pageInfo.setMsg(ResultInfo.SERVER_ERROR.getMsg());
		}
		return pageInfo;
	}

	/**
	 * 立即执行任务
	 * @param jobId	任务
	 * @param response	response
	 * @return return
	 */
	@RequestMapping("/executeJobNow")
	public @ResponseBody Object executeJobNow(Integer jobId, HttpServletResponse response) {
		PageInfo pageInfo = new PageInfo();
		try {
			pageInfo = scheduleService.executeJobNow(jobId);
		} catch (Exception e) {
			e.printStackTrace();
			pageInfo.setCode(ResultInfo.SERVER_ERROR.getCode());
			pageInfo.setMsg(ResultInfo.SERVER_ERROR.getMsg());
			LOG.error("立即执行定时任务失败，系统错误。" + e.getMessage());
		}
		return pageInfo;
	}
	
	/**
	 * 暂停任务
	 * @param response	response
	 * @param jobId	任务ID
	 * @return return
	 */
	@RequestMapping("/pauseJob")
	public @ResponseBody Object pauseJob(HttpServletResponse response, Integer jobId) {
		PageInfo pageInfo = new PageInfo();
		try {
			pageInfo = scheduleService.pauseJob(jobId);
			response.getWriter().write("success");
		} catch (Exception e) {
			e.printStackTrace();
			pageInfo.setCode(ResultInfo.SERVER_ERROR.getCode());
			pageInfo.setMsg(ResultInfo.SERVER_ERROR.getMsg());
			LOG.error("暂停计划任务失败,系统错误");
		}
		return pageInfo;
	}
	
	/**
	 * 
	 * @param response	response
	 * @param jobId	任务ID
	 * @return return
	 */
	@RequestMapping("/resumeJob")
	public @ResponseBody Object resumeJob(HttpServletResponse response, Integer jobId) {
		PageInfo pageInfo = new PageInfo();
		try {
			pageInfo = scheduleService.resumeJob(jobId);
		} catch (Exception e) {
			e.printStackTrace();
			pageInfo.setCode(ResultInfo.SERVER_ERROR.getCode());
			pageInfo.setMsg(ResultInfo.SERVER_ERROR.getMsg());
			LOG.error("系统错误" + e.getMessage());
		}
		return pageInfo;
	}
	
}
