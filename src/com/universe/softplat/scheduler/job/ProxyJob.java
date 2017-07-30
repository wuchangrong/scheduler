package com.universe.softplat.scheduler.job;

import org.quartz.JobDataMap;
/**
 * ����Job�����������Զ���Jobִ�У���һ�ַ�����ͨ�ò���
 */
public class ProxyJob extends AbstractJob {
	//ͨ�������ƴ�jobdatamapȡ�Զ������
	public static final String JOB_PARAM = "JOB_PARAM";
	/**
	 * ����ִ�з���������1��jobClassName 2��jobParam
	 */
	public boolean executeJob(JobDataMap jobdatamap) {
		String jobClassName = jobdatamap.getString("jobClassName").trim();
		AbstractJob delegateJob = null;	
		try {
			delegateJob = (AbstractJob)Class.forName(jobClassName).newInstance();
			boolean result = delegateJob.executeJob(jobdatamap);
			//������־�ͼ���
			this.setLogString(delegateJob.getLogString());
			this.setLogLevel(delegateJob.getLogLevel());
			return result;
		} catch (Exception e) {
			RuntimeException ex;
			if(delegateJob == null)
				ex = new RuntimeException("�޷������Զ���Jobʵ����",e);
			else
				ex = new RuntimeException(e);			
			throw ex;
		} 
	}
}
