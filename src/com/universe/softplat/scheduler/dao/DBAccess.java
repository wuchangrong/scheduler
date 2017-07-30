package com.universe.softplat.scheduler.dao;

import java.sql.Connection;
import java.util.List;

/**
 * 通用数据库访问接口
 */
public interface DBAccess {
	public static final String ORACLE = "ORACLE";
	public static final String DB2 = "DB2";
	public static final String MYSQL = "MYSQL";
	
	public Connection getConnection(String dataSourceName) throws Exception;
	public Connection getConnection(String driverName,String url,String userName,String password) throws Exception;
	public List execProcedure(Connection conn,String procName,List inputValues,int outputCount,boolean isInTransaction) throws Exception;
	public List execProcedure(Connection conn,String callSQL,int outputCount,boolean isInTransaction) throws Exception;	
	public int execUpdate(Connection conn,String sql,List params,boolean isInTransaction) throws Exception;
	public List execQuery(Connection conn,String sql,List params) throws Exception;
	public List execQuery(Connection conn,String sql,List params,int fromIndex,int toIndex) throws Exception;
}
