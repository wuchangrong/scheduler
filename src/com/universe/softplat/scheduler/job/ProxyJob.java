package com.universe.softplat.scheduler.job;

import org.quartz.JobDataMap;
/**
 * 代理Job，用来代理自定义Job执行；用一字符串做通用参数
 */
public class ProxyJob extends AbstractJob {
	//通过该名称从jobdatamap取自定义参数
	public static final String JOB_PARAM = "JOB_PARAM";
	/**
	 * 代理执行方法：参数1、jobClassName 2、jobParam
	 */
	public boolean executeJob(JobDataMap jobdatamap) {
		String jobClassName = jobdatamap.getString("jobClassName").trim();
		AbstractJob delegateJob = null;	
		try {
			delegateJob = (AbstractJob)Class.forName(jobClassName).newInstance();
			boolean result = delegateJob.executeJob(jobdatamap);
			//传递日志和级别
			this.setLogString(delegateJob.getLogString());
			this.setLogLevel(delegateJob.getLogLevel());
			return result;
		} catch (Exception e) {
			RuntimeException ex;
			if(delegateJob == null)
				ex = new RuntimeException("无法创建自定义Job实例！",e);
			else
				ex = new RuntimeException(e);			
			throw ex;
		} 
	}
}
