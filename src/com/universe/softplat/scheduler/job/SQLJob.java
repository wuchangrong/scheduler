package com.universe.softplat.scheduler.job;

import java.sql.Connection;
import java.util.StringTokenizer;

import org.quartz.JobDataMap;

import com.universe.softplat.scheduler.dao.DBAccess;
import com.universe.softplat.scheduler.dao.DBAccessFactory;

/**
 * ִ��һ��SQL Job
 * <p>time:2009-9-21</p>
 * @author wudongqing wudq@neusoft.com
 */
public class SQLJob extends AbstractJob {
	/**
	 * ִ��һ��SQL���
	 * ������1�����ݿ����ӣ�a)dataSourceName_s/b)driverName_s,url_s,userName_s,password_s(ͬ�洢���̲�������)
	 * �����������洢���̲�����sqlText(;�ָ�)
	 * 		3�������쳣��ʾִ�гɹ����޷��ؽ�����������ж���
	 */
	public boolean executeJob(JobDataMap jobdatamap) {
		String dataSourceName = jobdatamap.getString("dataSourceName_s").trim();
		Connection conn = null;
		String tempSQL = null;
		DBAccess dbAccess = DBAccessFactory.getDBAccess(DBAccess.DB2);
		try{
			//1�����ݿ�����
			if(!"".equals(dataSourceName)){
				//ʹ�õ�ǰ�����ṩ������Դ
				conn = dbAccess.getConnection(dataSourceName);
			}else{
				//ֱ���������ݿ�
				String driverName = jobdatamap.getString("driverName_s").trim();
				String url = jobdatamap.getString("url_s").trim();
				String userName = jobdatamap.getString("userName_s");
				String password = jobdatamap.getString("password_s");
				conn = dbAccess.getConnection(driverName, url, userName, password);
			}
			//2��sqlText;������;�ָ�,ִ��
			conn.setAutoCommit(false);
			String sqlText = jobdatamap.getString("sqlText").trim();
			StringTokenizer st = new StringTokenizer(sqlText,";��");
			while(st.hasMoreTokens()){
				tempSQL = st.nextToken();
				//У�鵥��SQL
				tempSQL = tempSQL.replaceAll("\n|\t|\r", "").trim();
				if("".equals(tempSQL))
					continue;
				//ִ�е���SQL
				dbAccess.execUpdate(conn, tempSQL, null, true);
			}
			conn.commit();
			//���ؽ��
			return true;
		}catch(Exception e){
			//��װ�쳣
			RuntimeException ex = null;
			if(conn == null){
				ex = new RuntimeException("�޷���ȡ���ݿ����ӣ�",e);
			}else{
				ex = new RuntimeException("ִ�����:"+tempSQL+"�����쳣��",e);
				//�ع�
				try{
					conn.rollback();
				}catch(Exception e1){}
			}
			throw ex;
		}finally{
			//�ر�����
			if(conn != null){
				try{
					conn.close();
				}catch(Exception e){}
			}			
		}
	}

}