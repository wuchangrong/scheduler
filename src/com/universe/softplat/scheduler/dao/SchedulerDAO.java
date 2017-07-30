package com.universe.softplat.scheduler.dao;

import java.sql.Connection;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.quartz.utils.DBConnectionManager;


import com.universe.softplat.scheduler.server.ConfigSchedular;
import com.universe.softplat.scheduler.util.UUID;

/**
 * ����ϵͳDAO
 */
public class SchedulerDAO {
	//private static final String INSERT_LOG_SQL = "INSERT INTO QRTZ_SCHEDULER_LOG(PKID,TASK_NAME,CALL_METHOD,START_TIME,LOG_LEVEL) VALUES(?,?,'��������',?,?)";
	//private static final String UPDATE_LOG_SQL = "UPDATE QRTZ_SCHEDULER_LOG SET END_TIME = ?,RESULT_FLAG = ?,LOG_LEVEL = ?,INFO= ? WHERE PKID = ?";
	//private static final String SELECT_LOG_SQL1 = "SELECT TASK_NAME,CALL_METHOD,START_TIME,END_TIME,RESULT_FLAG,LOG_LEVEL ,INFO FROM QRTZ_SCHEDULER_LOG";
	//private static final String SELECT_LOG_SQL2 = " ORDER BY START_TIME DESC";
	//private static final String QUERY_LAST_LOG_SQL = "WITH T0 AS(SELECT TASK_NAME,MAX(START_TIME) START_TIME FROM QRTZ_SCHEDULER_LOG GROUP BY TASK_NAME) SELECT M.TASK_NAME,M.START_TIME,M.RESULT_FLAG FROM QRTZ_SCHEDULER_LOG M JOIN T0 ON M.TASK_NAME = T0.TASK_NAME AND M.START_TIME = T0.START_TIME";
	//private static final String QUERY_TASKNAME_IN_LOG = "SELECT DISTINCT TASK_NAME FROM QRTZ_SCHEDULER_LOG";
    
    private static final String INSERT_LOG_SQL = "INSERT INTO QRTZ_SCHEDULER_LOG(PKID,TASK_NAME,CALL_METHOD,START_TIME,LOG_LEVEL) VALUES(?,?,'��������',?,?)";
    private static final String UPDATE_LOG_SQL = "UPDATE QRTZ_SCHEDULER_LOG SET END_TIME = ?,RESULT_FLAG = ?,LOG_LEVEL = ?,INFO= ? WHERE PKID = ?";
    private static final String SELECT_LOG_SQL1 = "SELECT TASK_NAME,CALL_METHOD,TO_CHAR(START_TIME,'yyyy-MM-dd HH24:mi:ss') START_TIME,TO_CHAR(END_TIME,'yyyy-MM-dd HH24:mi:ss') END_TIME ,RESULT_FLAG,LOG_LEVEL,INFO FROM QRTZ_SCHEDULER_LOG";
    private static final String SELECT_LOG_SQL2 = " ORDER BY START_TIME DESC";
    private static final String QUERY_LAST_LOG_SQL = "WITH T0 AS(SELECT TASK_NAME,MAX(START_TIME) START_TIME FROM QRTZ_SCHEDULER_LOG GROUP BY TASK_NAME) SELECT M.TASK_NAME,TO_CHAR(M.START_TIME,'yyyy-MM-dd HH24:mi:ss') START_TIME,M.RESULT_FLAG FROM QRTZ_SCHEDULER_LOG M JOIN T0 ON M.TASK_NAME = T0.TASK_NAME AND M.START_TIME = T0.START_TIME";
    private static final String QUERY_TASKNAME_IN_LOG = "SELECT DISTINCT TASK_NAME FROM QRTZ_SCHEDULER_LOG";    
	
	private SchedulerDAO(){
	}
	/**
	 * ��ȡQuartz֧�ſ�����Դ����
	 */
	private static Connection getQuartzConnection() throws Exception{
		String dsName = ConfigSchedular.getQuartzDSName();
		//System.out.println("dsName===="+dsName);
		return DBConnectionManager.getInstance().getConnection(dsName);
	}
	/**
	 * ��������:��¼������־--��������ǰ������־
	 */
	public static String inertLog(String taskName,Timestamp startTime,String level){
	    
	    //Date sdate = new Date();
	    //sdate = startTime; 
		String pkid = UUID.randomUUID().toString();
		//��װ����
		List params = new ArrayList();
		params.add(pkid);
		params.add(taskName);
		params.add(startTime);
		params.add(level);
		
		
		
		Connection conn = null;
		try{
			conn = getQuartzConnection();
			String insertSql = INSERT_LOG_SQL;
			//if(DBAccess.MYSQL.equals(ConfigSchedular.getQuartzDBType()))
			//    insertSql = "INSERT INTO QRTZ_SCHEDULER_LOG(PKID,TASK_NAME,CALL_METHOD,START_TIME,LOG_LEVEL) VALUES(?,?,'��������',?,?)";
			System.out.println("insertSql===="+insertSql);
			DBAccessFactory.getQuartzDBAccess().execUpdate(conn, insertSql, params,false);
			System.out.println("insert end............");
			return pkid;
		}catch(Exception e){
			System.out.println("��־��¼�쳣��");
			e.printStackTrace();
			return null;
		}finally{
			//�ر�����
			if(conn != null){
				try{
					conn.close();
				}catch(Exception e){}
			}
		}
	}
	/**
	 * ��������:��¼������־--���������������־
	 */
	public static void updateLog(String pkid,Timestamp endTime,String resultFlag,String level,String info){
		//��װ����
		if(info != null && info.length() > 2500){
			info = info.substring(0,2500);
		}		
        //Date sdate = new Date();
        //sdate = endTime; 
        
		List params = new ArrayList();
		params.add(endTime);
		params.add(resultFlag);
		params.add(level);
		params.add(info);
		params.add(pkid);
	
		//System.out.println("endTime===="+endTime);
		Connection conn = null;
		try{
			conn = getQuartzConnection();
			
            String updateSql = UPDATE_LOG_SQL;
            //if(DBAccess.MYSQL.equals(ConfigSchedular.getQuartzDBType()))
            //    updateSql = "UPDATE QRTZ_SCHEDULER_LOG SET END_TIME = ?,RESULT_FLAG = ?,LOG_LEVEL = ?,INFO= ? WHERE PKID = ?";
            System.out.println("updateSql===="+updateSql);
			DBAccessFactory.getQuartzDBAccess().execUpdate(conn, updateSql, params,false);
			System.out.println("update end............");
		}catch(Exception e){
			System.out.println("��־��¼�쳣��");
			e.printStackTrace();
		}finally{
			//�ر�����
			if(conn != null){
				try{
					conn.close();
				}catch(Exception e){}
			}
		}		
	}
	/**
	 * ��ѯ��־
	 */
	public static List queryLog(int pageNO,int pageSize,String taskNameCon,String resultFlagCon,String levelCon,String infoCon,Date fromDate,Date toDate){
		//��װSQL���
		List params = new ArrayList();
		StringBuffer sf = new StringBuffer(SELECT_LOG_SQL1).append(" WHERE 1=1");
		
		if(DBAccess.MYSQL.equals(ConfigSchedular.getQuartzDBType()))
		    sf = new StringBuffer("SELECT TASK_NAME,CALL_METHOD, START_TIME, END_TIME ,RESULT_FLAG,LOG_LEVEL,INFO FROM QRTZ_SCHEDULER_LOG WHERE 1=1");
				
		if(taskNameCon != null && !"".equals(taskNameCon)){
			sf.append(" AND TASK_NAME LIKE ?");
			params.add("%"+taskNameCon+"%");
		}
		//System.out.println("resultFlagCon====="+resultFlagCon);
		if(resultFlagCon != null && !"".equals(resultFlagCon)){
			if("0".equals(resultFlagCon))
				sf.append(" AND RESULT_FLAG IS NULL");
			else{
				sf.append(" AND RESULT_FLAG = ?");
				params.add(resultFlagCon);
			}
		}
		
		if(levelCon != null && !"".equals(levelCon)){
			sf.append(" AND LOG_LEVEL = ?");
			params.add(levelCon);
		}
		if(infoCon != null && !"".equals(infoCon)){
			sf.append(" AND INFO LIKE ?");
			params.add("%"+infoCon+"%");
		}
		if(fromDate != null){
			sf.append(" AND START_TIME >= ?");
			params.add(fromDate);
		}
		if(toDate != null){
			sf.append(" AND START_TIME < ?");
			params.add(toDate);
		}
		sf.append(SELECT_LOG_SQL2);
		//�����з�Χ
		int fromIndex = (pageNO-1)*pageSize +1;
		int toIndex = pageNO * pageSize;
		//
		Connection conn = null;
		List result = null;
		try{
			conn = getQuartzConnection();
			System.out.println(sf);
			//System.out.println(params);
			result = DBAccessFactory.getQuartzDBAccess().execQuery(conn, sf.toString(), params, fromIndex, toIndex);
		}catch(Exception e){
			System.out.println("��ѯ��־�쳣��");
			e.printStackTrace();
		}finally{
			//�ر�����
			if(conn != null){
				try{
					conn.close();
				}catch(Exception e){}
			}
		}
		return result;
	}
	/**
	 * ��������:��ѯ��������ִ��״̬
	 */
	public static List queryLastState(){
		Connection conn = null;
		List result = null;
		try{
			conn = getQuartzConnection();
			String querySql = QUERY_LAST_LOG_SQL;
	        if(DBAccess.MYSQL.equals(ConfigSchedular.getQuartzDBType()))
	            querySql = "SELECT M.TASK_NAME, M.START_TIME,M.RESULT_FLAG FROM QRTZ_SCHEDULER_LOG M JOIN  (SELECT TASK_NAME,MAX(START_TIME) START_TIME FROM QRTZ_SCHEDULER_LOG GROUP BY TASK_NAME) T0 ON M.TASK_NAME = T0.TASK_NAME AND M.START_TIME = T0.START_TIME";
			
			result = DBAccessFactory.getQuartzDBAccess().execQuery(conn,querySql, null);
		}catch(Exception e){
			System.out.println("��ѯ��־�쳣��");
			e.printStackTrace();
		}finally{
			//�ر�����
			if(conn != null){
				try{
					conn.close();
				}catch(Exception e){}
			}
		}
		return result;
	}
	/**
	 * ��������:��ѯ��־�����е�������
	 */	
	public static List queryTaskNamesInLog(){
		Connection conn = null;
		List result = null;
		try{
			conn = getQuartzConnection();
			result = DBAccessFactory.getQuartzDBAccess().execQuery(conn,QUERY_TASKNAME_IN_LOG, null);
		}catch(Exception e){
			System.out.println("��ѯ��־�쳣��");
			e.printStackTrace();
		}finally{
			//�ر�����
			if(conn != null){
				try{
					conn.close();
				}catch(Exception e){}
			}
		}
		return result;		
	}
}
