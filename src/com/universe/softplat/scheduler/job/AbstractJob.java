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
 * 通用存储过程Job
 * <p>time:2009-9-21</p>
 * @author wudongqing wudq@neusoft.com
 */
public abstract class AbstractJob implements ExtendJob{
	public static final String LOG_LEVEL_URGENT = "U";
	public static final String LOG_LEVEL_NORMAL = "N";
	
	private String logString = "";
	private String logLevel = null;
    /**
     * Quartz Job调用方法接口，加入日志功能
     */
	public final void execute(JobExecutionContext jobexecutioncontext) throws JobExecutionException{
		executeOnce(jobexecutioncontext.getJobDetail());
	}
	/**
	 * ExtendJob 接口，用来执行整个Job，加入日志功能
	 */
	public final boolean executeOnce(JobDetail jobDetail){
	    System.out.println(System.currentTimeMillis());
	    Timestamp startTime = new Timestamp(System.currentTimeMillis());
		
		System.out.println("startTime==========="+startTime);
		//
        JobDataMap jobdatamap = jobDetail.getJobDataMap();
        //任务运行前插入日志
        String taskName = jobDetail.getName();
        String logPkid = SchedulerDAO.inertLog(taskName, startTime, LOG_LEVEL_NORMAL);
        //执行任务主体
        boolean result = true;
        try{
        	result = executeJob(jobdatamap);
        	this.logLevel = result?LOG_LEVEL_NORMAL:LOG_LEVEL_URGENT;
        }catch(Throwable t1){
        	this.logLevel = LOG_LEVEL_URGENT;
        	//封装异常处理
        	Throwable t2 = t1.getCause();
        	if(t2 == null)
        		t2 = t1;
        	//
        	t2.printStackTrace();
        	result = false;
        	//输出异常信息
        	try{
	        	StringWriter sWriter = new StringWriter();
	        	PrintWriter  pWriter = new PrintWriter(sWriter);
	        	t2.printStackTrace(pWriter);
	        	pWriter.flush();
	        	pWriter.close();
	        	sWriter.close();
	        	this.logString += t1.getMessage();
	        	this.logString += " 异常信息：" + sWriter.toString().replaceAll("\n", " ");
        	}catch(Exception e1){}
        }
        //
        Timestamp endTime = new Timestamp(System.currentTimeMillis());
        //记录日志
        SchedulerDAO.updateLog(logPkid, endTime, result?"Y":"N", this.logLevel, this.logString);
        return result;
	}
	/**
	 * 执行判定表达式
	 * resultArray 返回结果数组，传入判定表达式
	 * singleVerifyExp：单个任务的验证表达式
	 * groupName，组名(类名),用来取得组的验证表达式
	 * 组表达式暂不实现
	 */
	public boolean execVerifyExpression(Object[] resultArray,String singleVerifyExp,String groupName){
		String expression = null;
		//取得组判定表达式
		String groupExpression = "";
		//执行
		//1、确定表达式
		if(singleVerifyExp != null && !singleVerifyExp.trim().equals(""))
			expression = singleVerifyExp.trim();
		else if(groupExpression != null && !groupExpression.trim().equals(""))
			expression = groupExpression.trim();
		//2、执行表达式
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
					this.addLogString(" 表达式 expression 定义错误，执行结果不是逻辑(布尔)类型");
					return false;
				}
			}catch(Exception e){
				e.printStackTrace();
				this.addLogString(" 表达式 expression 执行出错");
				return false;				
			}
		}else
			return true;
	}
	/**
	 * get/set方法
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
