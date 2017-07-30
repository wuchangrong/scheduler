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
 * DBAccessĬ��ʵ��
 */
public abstract class DefaultDBAccess implements DBAccess{
	
	private static Map dataSourceMap = new HashMap();
	/**
	 * ��������:�ӵ�ǰ�����ڷ���ָ������Դ������
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
	 * ��������:ͨ��������ʵʱ����������һ�����ݿ�����
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
	 * ��������:ִ�д洢����,����ִ�н��
	 * ע����ConnectionΪ�ⲿ���룬�ʱ������ڲ�δ�ر�����
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
			//ִ��
			cstmt.executeUpdate();
			//ȡ�����ؽ��
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
			//�����׳��쳣
			throw e;
		}finally{
			//�ر�statement
			if(cstmt != null){
				try{
					cstmt.close();
				}catch(Exception e){}
			}
		}
		return result;
	}
	/**
	 * ��������:ִ�д洢����,����ִ�н��
	 * ע����ConnectionΪ�ⲿ���룬�ʱ������ڲ�δ�ر�����
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
			//ִ��
			cstmt.executeUpdate();
			//ȡ�����ؽ��
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
			//�����׳��쳣
			throw e;
		}finally{
			//�ر�statement
			if(cstmt != null){
				try{
					cstmt.close();
				}catch(Exception e){}
			}
		}
		return result;		
	}	
	/**
	 * ִ��һSQL�����£����쳣����-1����������JDBCִ�н��
	 */
	public int execUpdate(Connection conn,String sql,List params,boolean isInTransaction) throws Exception{
		int result = -1;
		PreparedStatement pst = null;
		try{
			if(!isInTransaction){
				conn.setAutoCommit(true);
			}
			pst = conn.prepareStatement(sql);
			//���ò���
			if(params != null){
				for(int i = 0;i<params.size();i++)
					pst.setObject(i+1, params.get(i));
			}			
			result = pst.executeUpdate();
		}catch(Exception e){
			//�����׳��쳣
			throw e;			
		}finally{
			//�ر�statement
			if(pst != null){
				try{
					pst.close();
				}catch(Exception e){}
			}
		}	
		return result;
	}
	/**
	 * ͨ�ò�ѯ������ҳ,����Ϊ��ѯ�Ľ����,ÿ����¼ӳ���HashMap
	 */
	public List execQuery(Connection conn,String sql,List params) throws Exception{
		List result = null;
		PreparedStatement pst = null;
		ResultSet rs = null;
		try{
			pst = conn.prepareStatement(sql);
			//���ò���
			if(params != null){
				for(int i = 0;i<params.size();i++)
					pst.setObject(i+1, params.get(i));
			}
			rs = pst.executeQuery();
			//��ȡ����Ԫ����
			List columnNames = new ArrayList();
			ResultSetMetaData rsm = rs.getMetaData();
			for(int i = 0;i<rsm.getColumnCount();i++)
				columnNames.add(rsm.getColumnName(i+1));
			//��ȡ����
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
			//�����׳��쳣
			throw e;			
		}finally{
			//�ر�ResultSet
			if(rs != null){
				try{
					rs.close();
				}catch(Exception e){}
			}
			//�ر�statement
			if(pst != null){
				try{
					pst.close();
				}catch(Exception e){}
			}
		}	
		return result;
	}
}
