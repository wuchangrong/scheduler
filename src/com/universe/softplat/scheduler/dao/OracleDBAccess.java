package com.universe.softplat.scheduler.dao;

import java.sql.Connection;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class OracleDBAccess extends DefaultDBAccess{
	/**
	 * ͨ�ò�ѯ��pageSize<=0,�򲻷�ҳ
	 */
//	public List execQuery(Connection conn,String sql,List params,int pageSize,int pageNO) throws Exception{
//		throw new RuntimeException("not implements");
//	}	
	
    /**
     * ͨ�ò�ѯ(��ҳ)
     * List���ؽ��(0)--������ (1)--���ݼ�
     */
    public List execQuery(Connection conn,String sql,List params,int fromIndex,int toIndex) throws Exception{
        List result = new ArrayList();
        //1����ѯ�ܼ�¼��
        String countSQL = "SELECT COUNT(*) AS COUNT FROM (" + sql + ") TEMP";
        result.add(((Map)this.execQuery(conn, countSQL, params).get(0)).get("COUNT"));
        //2����ѯ��ҳ���
        StringBuffer pageSQL = new StringBuffer("SELECT * FROM ( SELECT ROWNUM AS ROW_NEXT,T.* FROM (");
        pageSQL.append(sql);
        pageSQL.append(")  T)  TEMP WHERE ROW_NEXT BETWEEN ");
        pageSQL.append(fromIndex);
        pageSQL.append(" AND ");
        pageSQL.append(toIndex);    
        
        System.out.println("pageSQL===="+pageSQL);
        result.add(this.execQuery(conn, pageSQL.toString(), params));
        return result;
    }	
}
