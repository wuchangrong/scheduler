package com.universe.softplat.scheduler.job;

import java.util.Hashtable;

import javax.xml.namespace.QName;

import org.apache.axis.client.Call;
import org.apache.axis.client.Service;
import org.apache.axis.transport.http.HTTPConstants;
import org.quartz.JobDataMap;

import com.universe.softplat.scheduler.util.Base64;
/**
 * DXP任务
 * <p>time:2009-9-23</p>
 * @author wudongqing wudq@neusoft.com
 */
public class DXPJob extends AbstractJob {
	
	private static final String DXP_TIMEOUT = "120000";   //DXP TIMEOUT最长两分钟
	/**
	 * 调用WebService方法，参数：1、endpoint 2、operationName 3、rpcUserName 4、rpcPassword
	 * 7、DXPTaskName 8、DXPRunType-"ASYNC" 异步，"SYNC" 同步
	 * 8、singleVerifyExp_d 判定表达式
	 */
	public boolean executeJob(JobDataMap jobdatamap) {
		//获取参数
		String endpoint  = jobdatamap.getString("endpoint").trim();
		String operationName = jobdatamap.getString("operationName").trim();
		String rpcUserName = jobdatamap.getString("rpcUserName").trim();
		String rpcPassword = jobdatamap.getString("rpcPassword").trim();
		//DXP Parameter
		String dxpTaskName = jobdatamap.getString("DXPTaskName").trim();
		String dxpRunType = jobdatamap.getString("DXPRunType").trim();
		if(dxpRunType.indexOf("ASYNC")>=0){
			dxpRunType = "ASYNC";
		}else{
			dxpRunType = "SYNC";			
		}
		
		Service service = new Service();
		Call call;
		try {
			//设置endpoint、operationName
			call = (Call) service.createCall();
			call.setTargetEndpointAddress(new java.net.URL(endpoint));
			//设置用户名/密码
			String auth = rpcUserName + ":" + rpcPassword;
			Hashtable hashTb = new Hashtable();
			hashTb.put(WebServiceJob.RPC_CONNECTION_AUTHORIZATION, Base64.bytesToBASE64NoSpace(auth.getBytes("utf-8")));
			call.setProperty(HTTPConstants.REQUEST_HEADERS, hashTb);		
			
			//DXP Task invoke(webservice interface)
			QName qname = new QName(null, operationName);
			call.setOperationName(qname);
			Object[] paramArray = new Object[]{dxpTaskName,dxpRunType,DXP_TIMEOUT};
			String[] res = (String[])call.invoke(paramArray);
			//DXP:res[0]:返回代码 res[1] 返回信息 res[0] == "0",表示成功
			if(res == null){
				super.addLogString("调用DXP无返回结果！");
				return false;
			}else{
				super.addLogString("DXP结果 CODE:"+ res[0] +" INFO："+res[1]);
				Object[] resArray = new Object[]{res[0],res[1]};
				return this.execVerifyExpression(resArray, jobdatamap.getString("singleVerifyExp_d"), this.getClass().getName());
			}
		}catch(Exception e){
			//封装异常
			RuntimeException ex = new RuntimeException(e);
			throw ex;
		}
	}
}
