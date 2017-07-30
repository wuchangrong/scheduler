package com.universe.softplat.scheduler.job;

import java.lang.reflect.Array;
import java.util.Hashtable;

import javax.xml.namespace.QName;

import org.apache.axis.client.Call;
import org.apache.axis.client.Service;
import org.apache.axis.transport.http.HTTPConstants;
import org.quartz.JobDataMap;

import com.universe.softplat.scheduler.util.Base64;
/**
 * webservice调用
 * <p>time:2009-9-23</p>
 * @author wudongqing wudq@neusoft.com
 */
public class WebServiceJob extends AbstractJob {
	public static final String RPC_CONNECTION_AUTHORIZATION = "RPC_CONNECTION_AUTHORIZATION";
	/**
	 * 调用WebService方法，参数：1、endpoint_w 2、operationName_w 3、rpcUserName_w 4、rpcPassword_w
	 * 5、inputParamValues 多参数用;分割
	 * 6、singleVerifyExp_w 判定表达式
	 */
	public boolean executeJob(JobDataMap jobdatamap) {
		//获取参数
		String endpoint  = jobdatamap.getString("endpoint_w").trim();
		String operationName = jobdatamap.getString("operationName_w").trim();
		String rpcUserName = jobdatamap.getString("rpcUserName_w").trim();
		String rpcPassword = jobdatamap.getString("rpcPassword_w").trim();
		//调用参数
		String inputParamValues = jobdatamap.getString("inputParamValues").trim();
		
		Service service = new Service();
		Call call;
		try {
			//设置endpoint、operationName
			call = (Call) service.createCall();
			call.setTargetEndpointAddress(new java.net.URL(endpoint));
			//设置用户名/密码
			String auth = rpcUserName + ":" + rpcPassword;
			Hashtable hashTb = new Hashtable();
			hashTb.put(RPC_CONNECTION_AUTHORIZATION, Base64.bytesToBASE64NoSpace(auth.getBytes("utf-8")));
			call.setProperty(HTTPConstants.REQUEST_HEADERS, hashTb);		

			//普通WebService调用
			QName qname = new QName(null, operationName);
			call.setOperationName(qname);
			Object[] paramArray = null;
			if("".equals(inputParamValues))
				paramArray = new Object[0];
			else
				paramArray = inputParamValues.split(";");
			//远程调用
			Object res = call.invoke(paramArray);
			Object[] resArray = null;
			//返回结果处理
			if(res != null){
				String result = null;
				if(res.getClass().isArray()){
					int resultCount = Array.getLength(res);
					resArray = new Object[resultCount];
					
					if(resultCount > 0){
						result = "结果1:"+Array.get(res,0)+" ";
						resArray[0] = Array.get(res,0);
					}
					for(int i = 1;i<resultCount;i++){
						result += "结果"+(i+1)+":"+Array.get(res,i)+" ";
						resArray[i] = Array.get(res,i);
					}
				}else{
					result = ""+res;
					resArray = new Object[]{res};
				}
				super.addLogString("结果1:" + result+ " ");
			}else{
				resArray = new Object[0];
			}
			return this.execVerifyExpression(resArray, jobdatamap.getString("singleVerifyExp_w"), this.getClass().getName());
		}catch(Exception e){
			//封装异常
			RuntimeException ex = new RuntimeException(e);
			throw ex;
		}
	}
}
