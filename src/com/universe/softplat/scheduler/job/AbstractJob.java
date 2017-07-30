package com.universe.softplat.scheduler.job;

import java.io.PrintWriter;
import java.io.StringWriter;
import java.sql.Date;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

import org.quartz.JobDataMap;
import org.quartz.JobDetail;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.wltea.expression.ExpressionEvaluator;
import org.wltea.expression.datameta.Variable;

import com.universe.softplat.scheduler.dao.SchedulerDAO;
/**
 * ͨ�ô洢����Job
 * <p>time:2009-9-21</p>
 * @author wudongqing wudq@neusoft.com
 */
public abstract class AbstractJob implements ExtendJob{
	public static final String LOG_LEVEL_URGENT = "U";
	public static final String LOG_LEVEL_NORMAL = "N";
	
	private String logString = "";
	private String logLevel = null;
    /**
     * Quartz Job���÷����ӿڣ�������־����
     */
	public final void execute(JobExecutionContext jobexecutioncontext) throws JobExecutionException{
		executeOnce(jobexecutioncontext.getJobDetail());
	}
	/**
	 * ExtendJob �ӿڣ�����ִ������Job��������־����
	 */
	public final boolean executeOnce(JobDetail jobDetail){
	    System.out.println(System.currentTimeMillis());
	    Timestamp startTime = new Timestamp(System.currentTimeMillis());
		
		System.out.println("startTime==========="+startTime);
		//
        JobDataMap jobdatamap = jobDetail.getJobDataMap();
        //��������ǰ������־
        String taskName = jobDetail.getName();
        String logPkid = SchedulerDAO.inertLog(taskName, startTime, LOG_LEVEL_NORMAL);
        //ִ����������
        boolean result = true;
        try{
        	result = executeJob(jobdatamap);
        	this.logLevel = result?LOG_LEVEL_NORMAL:LOG_LEVEL_URGENT;
        }catch(Throwable t1){
        	this.logLevel = LOG_LEVEL_URGENT;
        	//��װ�쳣����
        	Throwable t2 = t1.getCause();
        	if(t2 == null)
        		t2 = t1;
        	//
        	t2.printStackTrace();
        	result = false;
        	//����쳣��Ϣ
        	try{
	        	StringWriter sWriter = new StringWriter();
	        	PrintWriter  pWriter = new PrintWriter(sWriter);
	        	t2.printStackTrace(pWriter);
	        	pWriter.flush();
	        	pWriter.close();
	        	sWriter.close();
	        	this.logString += t1.getMessage();
	        	this.logString += " �쳣��Ϣ��" + sWriter.toString().replaceAll("\n", " ");
        	}catch(Exception e1){}
        }
        //
        Timestamp endTime = new Timestamp(System.currentTimeMillis());
        //��¼��־
        SchedulerDAO.updateLog(logPkid, endTime, result?"Y":"N", this.logLevel, this.logString);
        return result;
	}
	/**
	 * ִ���ж����ʽ
	 * resultArray ���ؽ�����飬�����ж����ʽ
	 * singleVerifyExp�������������֤���ʽ
	 * groupName������(����),����ȡ�������֤���ʽ
	 * ����ʽ�ݲ�ʵ��
	 */
	public boolean execVerifyExpression(Object[] resultArray,String singleVerifyExp,String groupName){
		String expression = null;
		//ȡ�����ж����ʽ
		String groupExpression = "";
		//ִ��
		//1��ȷ�����ʽ
		if(singleVerifyExp != null && !singleVerifyExp.trim().equals(""))
			expression = singleVerifyExp.trim();
		else if(groupExpression != null && !groupExpression.trim().equals(""))
			expression = groupExpression.trim();
		//2��ִ�б��ʽ
		if(expression != null){
			List variables = new ArrayList();
			if(resultArray != null){
				for(int i = 0;i<resultArray.length;i++){
					variables.add(Variable.createVariable("R"+(i+1),resultArray[i]));
					variables.add(Variable.createVariable("r"+(i+1),resultArray[i]));
				}
			}
			try{
				Object expResult = ExpressionEvaluator.evaluate(expression,variables);
				if(expResult instanceof Boolean){
					return ((Boolean)expResult).booleanValue();
				}else{
					this.addLogString(" ���ʽ expression �������ִ�н�������߼�(����)����");
					return false;
				}
			}catch(Exception e){
				e.printStackTrace();
				this.addLogString(" ���ʽ expression ִ�г���");
				return false;				
			}
		}else
			return true;
	}
	/**
	 * get/set����
	 */
	public String getLogString() {
		return logString;
	}
	public void setLogString(String logString) {
		this.logString = logString;
	}
	public void addLogString(String addString){
		if("".equals(this.logString))
			this.logString = addString;
		else
			this.logString = this.logString +" "+addString;
	}
	public String getLogLevel() {
		return logLevel;
	}
	public void setLogLevel(String logLevel) {
		this.logLevel = logLevel;
	}
}
