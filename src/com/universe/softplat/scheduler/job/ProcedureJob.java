package com.universe.softplat.scheduler.job;

import java.sql.Connection;
import java.util.List;

import org.quartz.JobDataMap;

import com.universe.softplat.scheduler.dao.DBAccess;
import com.universe.softplat.scheduler.dao.DBAccessFactory;

/**
 * 通用存储过程Job
 * <p>time:2009-9-21</p>
 * @author wudongqing wudq@neusoft.com
 */
public class ProcedureJob extends AbstractJob {
	/**
	 * 执行存储过程任务
	 * 参数：1、数据库连接：a)dataSourceName/b)driverName,url,userName,password
	 * 　　　２、存储过程参数：procName
	 * 		3、任务的验证表达式：singleVerifyExp_p
	 */
	public boolean executeJob(JobDataMap jobdatamap) {
		String dataSourceName = jobdatamap.getString("dataSourceName").trim();
		Connection conn = null;
		String procName = null;
		DBAccess dbAccess = DBAccessFactory.getDBAccess(DBAccess.DB2);
		try{
			//1、数据库连接
			if(!"".equals(dataSourceName)){
				//使用当前容器提供的数据源
				conn = dbAccess.getConnection(dataSourceName);
			}else{
				//直接连接数据库
				String driverName = jobdatamap.getString("driverName").trim();
				String url = jobdatamap.getString("url").trim();
				String userName = jobdatamap.getString("userName");
				String password = jobdatamap.getString("password");
				conn = dbAccess.getConnection(driverName, url, userName, password);
			}
			//2、存储过程参数
			procName = jobdatamap.getString("procName").trim();
			int outparamCount = procName.split("\\?").length - 1;
			//处理输出参数
			String[] outputNames = new String[outparamCount];
			for(int i = 0;i<outputNames.length;i++){
				outputNames[i] = "结果"+(i+1);
			}
			//执行存储过程
			List outputValues = dbAccess.execProcedure(conn, procName,outparamCount,false);
			Object[] outputArray = new Object[outputNames.length];
			//输出参数（结果）写入日志
			StringBuffer outputSf = new StringBuffer();
			for(int i = 0;i<outputNames.length;i++){
				Object tempValue = outputValues.get(i);
				outputSf.append(outputNames[i]).append(":").append(tempValue).append(" ");
				outputArray[i] = tempValue;
			}
			this.addLogString(outputSf.toString());
			//执行判定表达式
			return this.execVerifyExpression(outputArray, jobdatamap.getString("singleVerifyExp_p"), this.getClass().getName());
		}catch(Exception e){
			//封装异常
			RuntimeException ex = null;
			if(conn == null){
				ex = new RuntimeException("无法获取数据库连接！",e);
			}else{
				ex = new RuntimeException("执行存储过程:"+procName+"出现异常！",e);
			}
			throw ex;
		}finally{
			//关闭连接
			if(conn != null){
				try{
					conn.close();
				}catch(Exception e){}
			}			
		}
	}

}
