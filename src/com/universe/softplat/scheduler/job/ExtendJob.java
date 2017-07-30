package com.universe.softplat.scheduler.job;

import org.quartz.Job;
import org.quartz.JobDataMap;
import org.quartz.JobDetail;

public interface ExtendJob extends Job {
	/**
	 * Job单次执行接口
	 */
	boolean executeOnce(JobDetail jobDetail);
	/**
	 * 具体任务执行方法
	 */
	boolean executeJob(JobDataMap jobdatamap);
}