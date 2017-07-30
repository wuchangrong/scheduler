package com.universe.softplat.scheduler.job;

import java.sql.Connection;
import java.util.List;

import org.quartz.JobDataMap;

import com.universe.softplat.scheduler.dao.DBAccess;
import com.universe.softplat.scheduler.dao.DBAccessFactory;

/**
 * ͨ�ô洢����Job
 * <p>time:2009-9-21</p>
 * @author wudongqing wudq@neusoft.com
 */
public class ProcedureJob extends AbstractJob {
	/**
	 * ִ�д洢��������
	 * ������1�����ݿ����ӣ�a)dataSourceName/b)driverName,url,userName,password
	 * �����������洢���̲�����procName
	 * 		3���������֤���ʽ��singleVerifyExp_p
	 */
	public boolean executeJob(JobDataMap jobdatamap) {
		String dataSourceName = jobdatamap.getString("dataSourceName").trim();
		Connection conn = null;
		String procName = null;
		DBAccess dbAccess = DBAccessFactory.getDBAccess(DBAccess.DB2);
		try{
			//1�����ݿ�����
			if(!"".equals(dataSourceName)){
				//ʹ�õ�ǰ�����ṩ������Դ
				conn = dbAccess.getConnection(dataSourceName);
			}else{
				//ֱ���������ݿ�
				String driverName = jobdatamap.getString("driverName").trim();
				String url = jobdatamap.getString("url").trim();
				String userName = jobdatamap.getString("userName");
				String password = jobdatamap.getString("password");
				conn = dbAccess.getConnection(driverName, url, userName, password);
			}
			//2���洢���̲���
			procName = jobdatamap.getString("procName").trim();
			int outparamCount = procName.split("\\?").length - 1;
			//�����������
			String[] outputNames = new String[outparamCount];
			for(int i = 0;i<outputNames.length;i++){
				outputNames[i] = "���"+(i+1);
			}
			//ִ�д洢����
			List outputValues = dbAccess.execProcedure(conn, procName,outparamCount,false);
			Object[] outputArray = new Object[outputNames.length];
			//��������������д����־
			StringBuffer outputSf = new StringBuffer();
			for(int i = 0;i<outputNames.length;i++){
				Object tempValue = outputValues.get(i);
				outputSf.append(outputNames[i]).append(":").append(tempValue).append(" ");
				outputArray[i] = tempValue;
			}
			this.addLogString(outputSf.toString());
			//ִ���ж����ʽ
			return this.execVerifyExpression(outputArray, jobdatamap.getString("singleVerifyExp_p"), this.getClass().getName());
		}catch(Exception e){
			//��װ�쳣
			RuntimeException ex = null;
			if(conn == null){
				ex = new RuntimeException("�޷���ȡ���ݿ����ӣ�",e);
			}else{
				ex = new RuntimeException("ִ�д洢����:"+procName+"�����쳣��",e);
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
