package com.universe.softplat.scheduler.job;

import java.sql.Connection;
import java.util.StringTokenizer;

import org.quartz.JobDataMap;

import com.universe.softplat.scheduler.dao.DBAccess;
import com.universe.softplat.scheduler.dao.DBAccessFactory;

/**
 * 执行一组SQL Job
 * <p>time:2009-9-21</p>
 * @author wudongqing wudq@neusoft.com
 */
public class SQLJob extends AbstractJob {
	/**
	 * 执行一组SQL语句
	 * 参数：1、数据库连接：a)dataSourceName_s/b)driverName_s,url_s,userName_s,password_s(同存储过程参数区分)
	 * 　　　２、存储过程参数：sqlText(;分割)
	 * 		3、不抛异常表示执行成功（无返回结果，无需结果判定）
	 */
	public boolean executeJob(JobDataMap jobdatamap) {
		String dataSourceName = jobdatamap.getString("dataSourceName_s").trim();
		Connection conn = null;
		String tempSQL = null;
		DBAccess dbAccess = DBAccessFactory.getDBAccess(DBAccess.DB2);
		try{
			//1、数据库连接
			if(!"".equals(dataSourceName)){
				//使用当前容器提供的数据源
				conn = dbAccess.getConnection(dataSourceName);
			}else{
				//直接连接数据库
				String driverName = jobdatamap.getString("driverName_s").trim();
				String url = jobdatamap.getString("url_s").trim();
				String userName = jobdatamap.getString("userName_s");
				String password = jobdatamap.getString("password_s");
				conn = dbAccess.getConnection(driverName, url, userName, password);
			}
			//2、sqlText;多条用;分割,执行
			conn.setAutoCommit(false);
			String sqlText = jobdatamap.getString("sqlText").trim();
			StringTokenizer st = new StringTokenizer(sqlText,";；");
			while(st.hasMoreTokens()){
				tempSQL = st.nextToken();
				//校验单个SQL
				tempSQL = tempSQL.replaceAll("\n|\t|\r", "").trim();
				if("".equals(tempSQL))
					continue;
				//执行单个SQL
				dbAccess.execUpdate(conn, tempSQL, null, true);
			}
			conn.commit();
			//返回结果
			return true;
		}catch(Exception e){
			//封装异常
			RuntimeException ex = null;
			if(conn == null){
				ex = new RuntimeException("无法获取数据库连接！",e);
			}else{
				ex = new RuntimeException("执行语句:"+tempSQL+"出现异常！",e);
				//回滚
				try{
					conn.rollback();
				}catch(Exception e1){}
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