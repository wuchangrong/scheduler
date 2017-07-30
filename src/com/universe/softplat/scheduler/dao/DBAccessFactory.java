package com.universe.softplat.scheduler.dao;

import java.util.HashMap;
import java.util.Map;

import com.universe.softplat.scheduler.server.ConfigSchedular;

/**
 * 数据库访问Factory类
 */
public class DBAccessFactory {

	private static Map map = new HashMap();
	
	private DBAccessFactory(){
	}
	/**
	 * 方法描述:返回指定数据库的持久化类
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
				throw new RuntimeException("当前版本不支持数据库："+dbType);
			}
			map.put(dbType,result);
		}
		return result;
	}
	/**
	 * 方法描述:返回Quartz支撑库的持久化类
	 */
	public static DBAccess getQuartzDBAccess(){
		return getDBAccess(ConfigSchedular.getQuartzDBType());
	}
}
