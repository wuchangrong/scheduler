package com.universe.softplat.scheduler.dao;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.sql.DataSource;
/**
 * DBAccess默认实现
 */
public abstract class DefaultDBAccess implements DBAccess{
	
	private static Map dataSourceMap = new HashMap();
	/**
	 * 方法描述:从当前容器内返回指定数据源的连接
	 * @param dataSourceName
	 */
	public synchronized Connection getConnection(String dataSourceName) throws Exception{
		DataSource targetDataSource = (DataSource)dataSourceMap.get(dataSourceName);
		if(targetDataSource == null){
			Context context = new InitialContext();
			targetDataSource = (DataSource)context.lookup(dataSourceName);
			dataSourceMap.put(dataSourceName, targetDataSource);
		}
		return targetDataSource.getConnection();
	}
	/**
	 * 方法描述:通过参数，实时建立并返回一个数据库连接
	 * @param driverName
	 * @param url
	 * @param userName
	 * @param password
	 */
	public Connection getConnection(String driverName,String url,String userName,String password) throws Exception{
		Class.forName(driverName);
		return DriverManager.getConnection(url, userName, password);
	}
	/**
	 * 方法描述:执行存储过程,返回执行结果
	 * 注：因Connection为外部传入，故本方法内部未关闭连接
	 * @param conn
	 * @param procName
	 * @param inputValues
	 * @param outputCount
	 */
	public List execProcedure(Connection conn,String procName,List inputValues,int outputCount,boolean isInTransaction) throws Exception{
		List result = new ArrayList();
		int inputCount = inputValues == null?0:inputValues.size();
		int paramCount = inputCount + outputCount;		
		String sql = "{call " + procName + "(";
		if(paramCount > 0)
			sql += "?";
		for(int i = 1;i<paramCount;i++)
			sql += ",?";
		sql += ")}";
		
		CallableStatement cstmt = null;
		try{
			if(!isInTransaction){
				conn.setAutoCommit(false);
			}
			cstmt = conn.prepareCall(sql);
			for(int i = 0;i<inputCount;i++){
				cstmt.setObject(i+1,inputValues.get(i));
			}
			for(int i = 0;i<outputCount;i++){
				cstmt.registerOutParameter(inputValues.size()+1+i,java.sql.Types.VARCHAR);
			}
			//执行
			cstmt.executeUpdate();
			//取出返回结果
			for (int i = 0; i < outputCount; i++) {
				result.add(cstmt.getObject(inputValues.size()+1+i));
			}
			if(!isInTransaction){
				conn.commit();
				conn.setAutoCommit(true);
			}
		}catch(Exception e){
			if(conn != null && !isInTransaction){
				try{
					conn.rollback();
					conn.setAutoCommit(true);
				}catch(Exception e1){}
			}
			//向外抛出异常
			throw e;
		}finally{
			//关闭statement
			if(cstmt != null){
				try{
					cstmt.close();
				}catch(Exception e){}
			}
		}
		return result;
	}
	/**
	 * 方法描述:执行存储过程,返回执行结果
	 * 注：因Connection为外部传入，故本方法内部未关闭连接
	 * @param conn
	 * @param procName
	 * @param inputValues
	 * @param outputCount
	 */	
	public List execProcedure(Connection conn,String callSQL,int outputCount,boolean isInTransaction) throws Exception{
		List result = new ArrayList();
		CallableStatement cstmt = null;
		try{
			if(!isInTransaction){
				conn.setAutoCommit(false);
			}
			cstmt = conn.prepareCall(callSQL);
			for(int i = 0;i<outputCount;i++){
				cstmt.registerOutParameter(1+i,java.sql.Types.VARCHAR);
			}
			//执行
			cstmt.executeUpdate();
			//取出返回结果
			for (int i = 0; i < outputCount; i++) {
				result.add(cstmt.getObject(1+i));
			}
			if(!isInTransaction){
				conn.commit();
				conn.setAutoCommit(true);
			}
		}catch(Exception e){
			if(conn != null && !isInTransaction){
				try{
					conn.rollback();
					conn.setAutoCommit(true);
				}catch(Exception e1){}
			}
			//向外抛出异常
			throw e;
		}finally{
			//关闭statement
			if(cstmt != null){
				try{
					cstmt.close();
				}catch(Exception e){}
			}
		}
		return result;		
	}	
	/**
	 * 执行一SQL语句更新，出异常返回-1；正常返回JDBC执行结果
	 */
	public int execUpdate(Connection conn,String sql,List params,boolean isInTransaction) throws Exception{
		int result = -1;
		PreparedStatement pst = null;
		try{
			if(!isInTransaction){
				conn.setAutoCommit(true);
			}
			pst = conn.prepareStatement(sql);
			//设置参数
			if(params != null){
				for(int i = 0;i<params.size();i++)
					pst.setObject(i+1, params.get(i));
			}			
			result = pst.executeUpdate();
		}catch(Exception e){
			//向外抛出异常
			throw e;			
		}finally{
			//关闭statement
			if(pst != null){
				try{
					pst.close();
				}catch(Exception e){}
			}
		}	
		return result;
	}
	/**
	 * 通用查询，不分页,返回为查询的结果集,每条记录映射成HashMap
	 */
	public List execQuery(Connection conn,String sql,List params) throws Exception{
		List result = null;
		PreparedStatement pst = null;
		ResultSet rs = null;
		try{
			pst = conn.prepareStatement(sql);
			//设置参数
			if(params != null){
				for(int i = 0;i<params.size();i++)
					pst.setObject(i+1, params.get(i));
			}
			rs = pst.executeQuery();
			//获取列名元数据
			List columnNames = new ArrayList();
			ResultSetMetaData rsm = rs.getMetaData();
			for(int i = 0;i<rsm.getColumnCount();i++)
				columnNames.add(rsm.getColumnName(i+1));
			//获取数据
			result = new ArrayList();
			for(;rs.next();){
				HashMap rowData = new HashMap();
				result.add(rowData);
				for(int i = 0;i<columnNames.size();i++){
					String aColumn = (String)columnNames.get(i);
					rowData.put(aColumn, rs.getObject(aColumn));
				}
			}			
		}catch(Exception e){
			//向外抛出异常
			throw e;			
		}finally{
			//关闭ResultSet
			if(rs != null){
				try{
					rs.close();
				}catch(Exception e){}
			}
			//关闭statement
			if(pst != null){
				try{
					pst.close();
				}catch(Exception e){}
			}
		}	
		return result;
	}
}
