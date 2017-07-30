package com.universe.softplat.scheduler.dao;

import java.util.HashMap;
import java.util.Map;

import com.universe.softplat.scheduler.server.ConfigSchedular;

/**
 * ���ݿ����Factory��
 */
public class DBAccessFactory {

	private static Map map = new HashMap();
	
	private DBAccessFactory(){
	}
	/**
	 * ��������:����ָ�����ݿ�ĳ־û���
	 */
	public static synchronized DBAccess getDBAccess(String dbType){
		dbType = dbType.toUpperCase();
		DBAccess result = (DBAccess)map.get(dbType);
		if(result == null){
			if(DBAccess.DB2.equals(dbType)){
				result = new Db2DBAccess();
			}else if(DBAccess.ORACLE.equals(dbType)){
				result = new OracleDBAccess();
            }else if(DBAccess.MYSQL.equals(dbType)){
                result = new MysqlDBAccess();				
			}else{
				throw new RuntimeException("��ǰ�汾��֧�����ݿ⣺"+dbType);
			}
			map.put(dbType,result);
		}
		return result;
	}
	/**
	 * ��������:����Quartz֧�ſ�ĳ־û���
	 */
	public static DBAccess getQuartzDBAccess(){
		return getDBAccess(ConfigSchedular.getQuartzDBType());
	}
}
