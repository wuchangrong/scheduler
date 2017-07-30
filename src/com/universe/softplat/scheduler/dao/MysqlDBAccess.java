/**
 * <p>Copyright 2016 东软烟草行业事业部版权所有。</p>
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
 * <p>Copyright：Copyright 2016 xxxxxx版权所有。</p>
 * <p>company:neusoft</p>
 * <p>time:2016-7-4</p>
 * @author wucr
 * @version $Revision
 */
public class MysqlDBAccess extends DefaultDBAccess
{
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
