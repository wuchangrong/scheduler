package com.universe.softplat.scheduler.job;

import org.quartz.Job;
import org.quartz.JobDataMap;
import org.quartz.JobDetail;

public interface ExtendJob extends Job {
	/**
	 * Job����ִ�нӿ�
	 */
	boolean executeOnce(JobDetail jobDetail);
	/**
	 * ��������ִ�з���
	 */
	boolean executeJob(JobDataMap jobdatamap);
}