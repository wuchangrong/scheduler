package com.universe.softplat.scheduler.dao;

import java.sql.Connection;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class Db2DBAccess extends DefaultDBAccess{
	/**
	 * 通用查询(分页)
	 * List返回结果(0)--总行数 (1)--数据集
	 */
	public List execQuery(Connection conn,String sql,List params,int fromIndex,int toIndex) throws Exception{
		List result = new ArrayList();
		//1、查询总记录数
		String countSQL = "SELECT COUNT(*) AS COUNT FROM (" + sql + ") TEMP";
		result.add(((Map)this.execQuery(conn, countSQL, params).get(0)).get("COUNT"));
		//2、查询分页结果
		StringBuffer pageSQL = new StringBuffer("SELECT * FROM ( SELECT ROWNUMBER() OVER() AS ROW_NEXT,T.* FROM (");
		pageSQL.append(sql);
		pageSQL.append(") AS T) AS TEMP WHERE ROW_NEXT BETWEEN ");
		pageSQL.append(fromIndex);
		pageSQL.append(" AND ");
		pageSQL.append(toIndex);		
		result.add(this.execQuery(conn, pageSQL.toString(), params));
		return result;
	}
}
