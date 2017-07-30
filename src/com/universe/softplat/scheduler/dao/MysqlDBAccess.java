/**
 * <p>Copyright 2016 �����̲���ҵ��ҵ����Ȩ���С�</p>
 */
package com.universe.softplat.scheduler.dao;

import java.sql.Connection;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 *
 *
 * <p>application name:xxxxxx</p>
 * <p>application describing:</p>
 * <p>Copyright��Copyright 2016 xxxxxx��Ȩ���С�</p>
 * <p>company:neusoft</p>
 * <p>time:2016-7-4</p>
 * @author wucr
 * @version $Revision
 */
public class MysqlDBAccess extends DefaultDBAccess
{
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
        StringBuffer pageSQL = new StringBuffer("");
        pageSQL.append(sql);
        pageSQL.append(" LIMIT ");
        pageSQL.append(fromIndex);
        pageSQL.append(" , ");
        pageSQL.append(toIndex);    
        
        System.out.println("pageSQL===="+pageSQL);
        result.add(this.execQuery(conn, pageSQL.toString(), params));
        return result;
    }   
}
