package com.mrbt.lingmoney.admin.service.schedule.impl;

import java.lang.reflect.Method;
import java.util.Date;
import java.util.List;

import javax.annotation.PostConstruct;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import org.quartz.CronScheduleBuilder;
import org.quartz.CronTrigger;
import org.quartz.Job;
import org.quartz.JobBuilder;
import org.quartz.JobDetail;
import org.quartz.JobKey;
import org.quartz.Scheduler;
import org.quartz.SchedulerException;
import org.quartz.TriggerBuilder;
import org.quartz.TriggerKey;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.quartz.SchedulerFactoryBean;
import org.springframework.stereotype.Service;

import com.mrbt.lingmoney.admin.service.schedule.ScheduleService;
import com.mrbt.lingmoney.admin.utils.QuartzJobFactory;
import com.mrbt.lingmoney.admin.utils.QuartzJobFactoryDisallowConcurrentExecution;
import com.mrbt.lingmoney.admin.utils.SpringUtils;
import com.mrbt.lingmoney.mapper.ScheduleJobMapper;
import com.mrbt.lingmoney.mapper.ScheduleLogMapper;
import com.mrbt.lingmoney.model.schedule.ScheduleJob;
import com.mrbt.lingmoney.model.schedule.ScheduleJobExample;
import com.mrbt.lingmoney.model.schedule.ScheduleLog;
import com.mrbt.lingmoney.utils.PageInfo;
import com.mrbt.lingmoney.utils.ResultParame;

/**
 * @author syb
 * @date 2017年5月17日 上午10:32:10
 * @version 1.0
 * @description
 **/
@Service("scheduleService")
public class ScheduleServiceImpl implements ScheduleService {
	private static final Logger LOG = LogManager.getLogger(ScheduleServiceImpl.class);
	@Autowired
	private SchedulerFactoryBean schedulerFactoryBean;
	@Autowired
	private ScheduleJobMapper scheduleJobMapper;
	@Autowired
	private ScheduleLogMapper scheduleLogMapper;

	/**
	 * 启动所有状态正常的计划任务（依赖于服务）
	 * 
	 * @throws Exception
	 */
	@PostConstruct
	public void init() {
		ScheduleJobExample example = new ScheduleJobExample();
		// 获取任务数据
		List<ScheduleJob> jobList = scheduleJobMapper.selectByExample(example);
		for (ScheduleJob job : jobList) {
			try {
				addJob(job);
				saveScheduleLog(job.getJobId(), "初始化任务成功:" + job.getJobName(), null);
			} catch (Exception e) {
				saveScheduleLog(job.getJobId(), "初始化任务失败", e.getMessage());
				e.printStackTrace();
				LOG.error(e.getMessage(), e);
				// catch异常后，继续启动下一个任务
				continue;
			}

		}
	}

	@Override
	public ScheduleJob getTaskById(Integer jobId) {
		return scheduleJobMapper.selectByPrimaryKey(jobId);
	}

	@Override
	public PageInfo getAllTask(ScheduleJobExample example) {
		PageInfo pf = new PageInfo();
		List<ScheduleJob> li = scheduleJobMapper.selectByExample(example);
		int count = scheduleJobMapper.countByExample(example);
		pf.setRows(li);
		pf.setTotal(count);
		return pf;
	}

	@Override
	public PageInfo addTask(ScheduleJob scheduleJob) {
		PageInfo pi = new PageInfo();
		try {
			CronScheduleBuilder.cronSchedule(scheduleJob.getCronExpression());
		} catch (Exception e) {
			pi.setCode(ResultParame.ResultInfo.SERVER_ERROR.getCode());
			pi.setMsg("cron表达式有误，不能被解析！");
			return pi;
		}
		Object obj = null;
		try {
			// 如果 spring id和类路径都存在，优先使用spring bean的设置
			if (StringUtils.isNotBlank(scheduleJob.getSpringId())) {
				obj = SpringUtils.getBean(scheduleJob.getSpringId());
			} else {
				Class<?> clazz = Class.forName(scheduleJob.getBeanClass());
				obj = clazz.newInstance();
			}
		} catch (Exception e) {
			LOG.error(e.getMessage(), e);
			e.printStackTrace();
			pi.setCode(ResultParame.ResultInfo.SERVER_ERROR.getCode());
			pi.setMsg("spring bean id或类路径错误！");
			return pi;
		}
		if (obj == null) {
			pi.setCode(ResultParame.ResultInfo.MODIFY_REJECT.getCode());
			pi.setMsg("未找到目标类！");
			return pi;
		} else {
			Class<?> clazz = obj.getClass();
			Method method = null;
			try {
				method = clazz.getMethod(scheduleJob.getMethodName());
			} catch (Exception e) {
				LOG.error(e.getMessage(), e);
				e.printStackTrace();
				pi.setCode(ResultParame.ResultInfo.SERVER_ERROR.getCode());
				pi.setMsg("加载方法错误！");
				return pi;
			}
			if (method == null) {
				pi.setCode(ResultParame.ResultInfo.SIGN_SUCCESS.getCode());
				pi.setMsg("未找到目标方法！");
				return pi;
			}
		}
		// 判断 组.任务名 是否在数据库中有重复
		ScheduleJobExample ex = new ScheduleJobExample();
		ex.createCriteria().andJobGroupEqualTo(scheduleJob.getJobGroup()).andJobNameEqualTo(scheduleJob.getJobName());
		try {
			if (scheduleJobMapper.countByExample(ex) > 0) {
				pi.setCode(ResultParame.ResultInfo.SERVER_ERROR.getCode());
				pi.setMsg("数据重复");
			} else {
				scheduleJob.setCreateTime(new Date());
				scheduleJobMapper.insertSelective(scheduleJob);
				addJob(scheduleJob);
				pi.setCode(ResultParame.ResultInfo.SUCCESS.getCode());
				pi.setMsg("操作成功");
			}
		} catch (Exception e) {
			e.printStackTrace();
			LOG.error("添加任务失败" + e.getMessage());
		}
		return pi;
	}

	@Override
	public PageInfo changeStatus(Integer jobId, Integer status) {
		PageInfo pi = new PageInfo();
		ScheduleJob job = getTaskById(jobId);
		if (job == null) {
			pi.setCode(ResultParame.ResultInfo.SERVER_ERROR.getCode());
			pi.setMsg("未查询到该任务信息");
			return pi;
		}
		switch (status) {
		case 0: // 停止
			job.setJobStatus(ScheduleJob.STATUS_INIT);
			removeSchedule(job);
			break;
		case 1: // 启动
			job.setJobStatus(ScheduleJob.STATUS_RUNNING);
			addJob(job);
			break;
		case 2: // 暂停
			job.setJobStatus(ScheduleJob.STATUS_PAUSE);
			pauseJob(job.getJobId());
			break;
		case 3: // 删除
			removeSchedule(job);
			job.setJobStatus(ScheduleJob.STATUS_REMOVE);
			break;
		default:
			break;
		}
		job.setUpdateTime(new Date());
		scheduleJobMapper.updateByPrimaryKeySelective(job);
		pi.setCode(ResultParame.ResultInfo.SUCCESS.getCode());
		pi.setMsg("执行成功");
		return pi;
	}

	@Override
	public PageInfo updateCron(Integer jobId, String cron) {
		PageInfo pi = new PageInfo();
		try {
			CronScheduleBuilder.cronSchedule(cron);
		} catch (Exception e) {
			LOG.error("cron表达式有误，不能被解析！cron:" + cron);
			pi.setMsg("cron表达式有误，不能被解析！");
			pi.setCode(ResultParame.ResultInfo.SERVER_ERROR.getCode());
			return pi;
		}
		ScheduleJob job = getTaskById(jobId);
		if (job == null) {
			pi.setCode(ResultParame.ResultInfo.MODIFY_REJECT.getCode());
			pi.setMsg("未查询到该计划任务");
			return pi;
		}
		String oldCron = job.getCronExpression();
		job.setCronExpression(cron);
		if (ScheduleJob.STATUS_RUNNING == job.getJobStatus()) {
			saveScheduleLog(job.getJobId(), "更新执行时间，原执行时间" + oldCron + ";现执行时间:" + job.getCronExpression(), null);
			updateJobCron(job);
		}
		job.setUpdateTime(new Date());
		scheduleJobMapper.updateByPrimaryKeySelective(job);
		pi.setCode(ResultParame.ResultInfo.SUCCESS.getCode());
		pi.setMsg("执行成功");
		return pi;
	}

	/**
	 * 添加任务
	 * 
	 * @param job
	 *            ScheduleJob
	 * @throws SchedulerException
	 *             异常
	 */
	public void addJob(ScheduleJob job) {
		try {
			if (job == null || ScheduleJob.STATUS_RUNNING != job.getJobStatus()) {
				return;
			}
			Scheduler scheduler = schedulerFactoryBean.getScheduler();
			// 任务名+任务组 为任务的标识
			TriggerKey triggerKey = TriggerKey.triggerKey(job.getJobName(), job.getJobGroup());
			CronTrigger trigger = (CronTrigger) scheduler.getTrigger(triggerKey);
			// 若不存在，创建一个
			if (null == trigger) {
				// 如果等于1 则直接执行 反之 等待上次流转中未执行完的方法执行完毕才执行下一次操作(即等于0是为同步 1为异步)
				Class<? extends Job> clazz = ScheduleJob.CONCURRENT_IS == job.getIsConcurrent() ? QuartzJobFactory.class
						: QuartzJobFactoryDisallowConcurrentExecution.class;
				JobDetail jobDetail = JobBuilder.newJob(clazz).withIdentity(job.getJobName(), job.getJobGroup())
						.build();
				jobDetail.getJobDataMap().put("scheduleJob", job);
				CronScheduleBuilder scheduleBuilder = CronScheduleBuilder.cronSchedule(job.getCronExpression());
				// 构建触发器
				trigger = TriggerBuilder.newTrigger().withIdentity(job.getJobName(), job.getJobGroup())
						.withSchedule(scheduleBuilder).build();
				scheduler.scheduleJob(jobDetail, trigger);
				saveScheduleLog(job.getJobId(), "启动任务成功", null);
			} else {
				// 触发器已存在，那么更新相应的定时设置
				CronScheduleBuilder scheduleBuilder = CronScheduleBuilder.cronSchedule(job.getCronExpression());
				// 按新的cronExpression表达式重新构建trigger
				trigger = trigger.getTriggerBuilder().withIdentity(triggerKey).withSchedule(scheduleBuilder).build();
				// 按新的trigger重新设置job执行
				scheduler.rescheduleJob(triggerKey, trigger);
				saveScheduleLog(job.getJobId(), "更新任务执行时间成功", null);
			}
		} catch (SchedulerException e) {
			saveScheduleLog(job.getJobId(), "添加定时任务失败", e.getMessage());
			e.printStackTrace();
		}

	}

	/**
	 * 删除定时任务
	 * 
	 * @param job
	 *            job
	 * @throws SchedulerException
	 *             异常
	 */
	private void removeSchedule(ScheduleJob job) {
		try {
			Scheduler scheduler = schedulerFactoryBean.getScheduler();
			JobKey jobKey = JobKey.jobKey(job.getJobName(), job.getJobGroup());
			if (jobKey != null) {
				scheduler.deleteJob(jobKey);
				saveScheduleLog(job.getJobId(), "删除任务成功", null);
			}
		} catch (Exception e) {
			saveScheduleLog(job.getJobId(), "删除任务失败", e.getMessage());
			e.printStackTrace();
		}
	}

	/**
	 * 记录定时器执行日志
	 * 
	 * @param jobId
	 *            定时任务ID
	 * @param description
	 *            任务描述
	 * @param errorLog
	 *            错误日志
	 */
	@Override
	public void saveScheduleLog(Integer jobId, String description, String errorLog) {
		ScheduleLog scheduleLog = new ScheduleLog();
		if (jobId != null) {
			scheduleLog.setJobId(jobId);
		}
		if (StringUtils.isNotBlank(errorLog)) {
			scheduleLog.setErrorLog(errorLog);
		}
		scheduleLog.setExecuteTime(new Date());
		scheduleLog.setDescription(description);
		scheduleLogMapper.insertSelective(scheduleLog);
	}

	/**
	 * 更新任务时间表达式
	 * 
	 * @param scheduleJob
	 *            scheduleJob
	 * @throws SchedulerException
	 *             异常
	 */
	public void updateJobCron(ScheduleJob scheduleJob) {
		try {
			Scheduler scheduler = schedulerFactoryBean.getScheduler();
			TriggerKey triggerKey = TriggerKey.triggerKey(scheduleJob.getJobName(), scheduleJob.getJobGroup());
			CronTrigger trigger = (CronTrigger) scheduler.getTrigger(triggerKey);
			CronScheduleBuilder scheduleBuilder = CronScheduleBuilder.cronSchedule(scheduleJob.getCronExpression());
			trigger = trigger.getTriggerBuilder().withIdentity(triggerKey).withSchedule(scheduleBuilder).build();
			scheduler.rescheduleJob(triggerKey, trigger);
			saveScheduleLog(scheduleJob.getJobId(), "更新执行时间成功，现执行时间：" + scheduleJob.getCronExpression(), null);
		} catch (SchedulerException e) {
			saveScheduleLog(scheduleJob.getJobId(), "更新执行时间失败", e.getMessage());
			e.printStackTrace();
		}
	}

	@Override
	public PageInfo executeJobNow(Integer jobId) {
		PageInfo pi = new PageInfo();
		try {
			ScheduleJob scheduleJob = scheduleJobMapper.selectByPrimaryKey(jobId);
			Scheduler scheduler = schedulerFactoryBean.getScheduler();
			JobKey jobKey = JobKey.jobKey(scheduleJob.getJobName(), scheduleJob.getJobGroup());
			if (scheduler.checkExists(jobKey)) {
				scheduler.triggerJob(jobKey);
				// 添加操作日志
				saveScheduleLog(jobId, "立即执行任务成功", null);
				pi.setCode(ResultParame.ResultInfo.SUCCESS.getCode());
				pi.setMsg("执行成功");
			} else {
				pi.setCode(ResultParame.ResultInfo.MODIFY_REJECT.getCode());
				pi.setMsg("该任务未被初始化");
			}
		} catch (SchedulerException e) {
			e.printStackTrace();
			// 添加操作日志
			saveScheduleLog(jobId, "立即执行任务失败", e.getMessage());
			pi.setCode(ResultParame.ResultInfo.SERVER_ERROR.getCode());
			pi.setMsg("执行失败");
			LOG.error("立即执行计划任务失败，系统错误" + jobId);
		}
		return pi;
	}

	@Override
	public PageInfo pauseJob(Integer jobId) {
		PageInfo pi = new PageInfo();
		ScheduleJob scheduleJob = scheduleJobMapper.selectByPrimaryKey(jobId);
		if (scheduleJob == null) {
			pi.setCode(ResultParame.ResultInfo.MODIFY_REJECT.getCode());
			pi.setMsg("未查询到该计划任务");
			LOG.info("未查询到该计划任务。" + jobId);
			return pi;
		}
		scheduleJob.setJobStatus(ScheduleJob.STATUS_PAUSE);
		scheduleJobMapper.updateByPrimaryKeySelective(scheduleJob);
		Scheduler scheduler = schedulerFactoryBean.getScheduler();
		JobKey jobKey = JobKey.jobKey(scheduleJob.getJobName(), scheduleJob.getJobGroup());
		try {
			scheduler.pauseJob(jobKey);
			// 添加操作日志
			saveScheduleLog(jobId, "暂停任务成功", null);
			pi.setCode(ResultParame.ResultInfo.SUCCESS.getCode());
			pi.setMsg("操作成功");
		} catch (SchedulerException e) {
			e.printStackTrace();
			// 添加操作日志
			saveScheduleLog(jobId, "暂停任务失败", e.getMessage());
			pi.setCode(ResultParame.ResultInfo.SERVER_ERROR.getCode());
			pi.setMsg("操作失败，系统错误");
			LOG.error("暂停计划任务失败，系统错误。" + e.getMessage());
		}
		return pi;
	}

	@Override
	public PageInfo resumeJob(Integer jobId) {
		PageInfo pi = new PageInfo();
		ScheduleJob scheduleJob = scheduleJobMapper.selectByPrimaryKey(jobId);
		if (scheduleJob == null) {
			pi.setCode(ResultParame.ResultInfo.MODIFY_REJECT.getCode());
			pi.setMsg("未查询到该计划任务");
			return pi;
		}
		Scheduler scheduler = schedulerFactoryBean.getScheduler();
		JobKey jobKey = JobKey.jobKey(scheduleJob.getJobName(), scheduleJob.getJobGroup());
		try {
			scheduler.resumeJob(jobKey);
			scheduleJob.setJobStatus(ScheduleJob.STATUS_RUNNING);
			scheduleJobMapper.updateByPrimaryKeySelective(scheduleJob);
			saveScheduleLog(jobId, "恢复任务成功", null);
			pi.setCode(ResultParame.ResultInfo.SUCCESS.getCode());
			pi.setMsg("操作成功");
		} catch (SchedulerException e) {
			saveScheduleLog(jobId, "恢复任务失败", e.getMessage());
			e.printStackTrace();
			pi.setCode(ResultParame.ResultInfo.SERVER_ERROR.getCode());
			pi.setMsg("系统错误");
			LOG.error("恢复计划任务失败，系统错误" + e.getMessage());
		}
		return pi;
	}

	@Override
	public PageInfo editSchedule(ScheduleJob scheduleJob) {
		PageInfo pi = new PageInfo();

		// 如果原计划运行中，删除原计划任务
		ScheduleJob orgiJob = scheduleJobMapper.selectByPrimaryKey(scheduleJob.getJobId());
		if (orgiJob != null) {
			if (orgiJob.getJobStatus() == ResultParame.ResultNumber.ONE.getNumber()
					|| orgiJob.getJobStatus() == ResultParame.ResultNumber.TWO.getNumber()) {
				removeSchedule(orgiJob);
			}
		}

		int result = scheduleJobMapper.updateByPrimaryKeySelective(scheduleJob);
		if (result > 0) {
			ScheduleJob nowJob = scheduleJobMapper.selectByPrimaryKey(scheduleJob.getJobId());
			if (nowJob.getJobStatus() == 1) {
				addJob(nowJob);
			}
			pi.setCode(ResultParame.ResultInfo.SUCCESS.getCode());
			pi.setMsg("操作成功");
		} else {
			pi.setCode(ResultParame.ResultInfo.SERVER_ERROR.getCode());
			pi.setMsg("操作失败");
		}

		return pi;
	}

	@Override
	public PageInfo removeSchedule(Integer jobId) {
		PageInfo pi = new PageInfo();

		if (jobId != null) {
			ScheduleJob orgiJob = scheduleJobMapper.selectByPrimaryKey(jobId);

			if (orgiJob != null) {
				if (orgiJob.getJobStatus() == ResultParame.ResultNumber.ONE.getNumber()
						|| orgiJob.getJobStatus() == ResultParame.ResultNumber.TWO.getNumber()) {
					removeSchedule(orgiJob);
				}
				int result = scheduleJobMapper.deleteByPrimaryKey(jobId);

				if (result > 0) {
					pi.setCode(ResultParame.ResultInfo.SUCCESS.getCode());
					pi.setMsg("操作成功");
				} else {
					pi.setCode(ResultParame.ResultInfo.SERVER_ERROR.getCode());
					pi.setMsg("操作失败");
				}

			} else {
				pi.setCode(ResultParame.ResultNumber.ONE_ZERO_ZERO_THREE.getNumber());
				pi.setMsg("无效数据");
			}

		} else {
			pi.setCode(ResultParame.ResultNumber.ONE_ZERO_ZERO_ONE.getNumber());
			pi.setMsg("参数有误");
		}

		return pi;
	}

}
