package com.mrbt.lingmoney.admin.service.schedule;

import com.mrbt.lingmoney.model.schedule.ScheduleJob;
import com.mrbt.lingmoney.model.schedule.ScheduleJobExample;
import com.mrbt.lingmoney.utils.PageInfo;

/**
 *@author syb
 *@date 2017年5月17日 上午10:31:53
 *@version 1.0
 *@description 定时任务
 **/
public interface ScheduleService {

	/**
	 * 根据JOBID获取任务
	 * 
	 * @param jobId
	 *            jobId
	 * @return 返回信息
	 */
	ScheduleJob getTaskById(Integer jobId);

	/**
	 * 获取全部任务
	 * 
	 * @param example
	 *            ScheduleJobExample
	 * @return 返回信息
	 */
	PageInfo getAllTask(ScheduleJobExample example);

	/**
	 * 添加任务
	 * 
	 * @param scheduleJob
	 *            ScheduleJob
	 * @return 返回信息
	 */
	PageInfo addTask(ScheduleJob scheduleJob);

	/**
	 * 修改任务状态
	 * 
	 * @param jobId
	 *            jobId
	 * @param status
	 *            status
	 * @return 返回信息
	 */
	PageInfo changeStatus(Integer jobId, Integer status);

	/**
	 * 修改执行时间
	 * 
	 * @param jobId
	 *            jobId
	 * @param cron
	 *            cron
	 * @return 返回信息
	 */
	PageInfo updateCron(Integer jobId, String cron);

	/**
	 * 立即执行
	 * 
	 * @param jobId
	 *            jobId
	 * @return 返回信息
	 */
	PageInfo executeJobNow(Integer jobId);

	/**
	 * 暂停任务
	 * 
	 * @param jobId
	 *            jobId
	 * @return 返回信息
	 */
	PageInfo pauseJob(Integer jobId);

	/**
	 * 恢复任务
	 * 
	 * @param jobId
	 *            jobId
	 * @return 返回信息
	 */
	PageInfo resumeJob(Integer jobId);

	/**
	 * 保存操作日志
	 * 
	 * @param jobId
	 *            jobId
	 * @param description
	 *            description
	 * @param errorLog
	 *            errorLog
	 */
	void saveScheduleLog(Integer jobId, String description, String errorLog);

	/**
	 * 编辑任务
	 * 
	 * @param scheduleJob
	 *            ScheduleJob
	 * @return 返回信息
	 */
	PageInfo editSchedule(ScheduleJob scheduleJob);

	/***
	 * 删除任务
	 * 
	 * @param jobId
	 *            jobId
	 * @return 返回信息
	 */
	PageInfo removeSchedule(Integer jobId);
}
