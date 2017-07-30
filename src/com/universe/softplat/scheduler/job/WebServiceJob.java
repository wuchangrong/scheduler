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
 * webservice����
 * <p>time:2009-9-23</p>
 * @author wudongqing wudq@neusoft.com
 */
public class WebServiceJob extends AbstractJob {
	public static final String RPC_CONNECTION_AUTHORIZATION = "RPC_CONNECTION_AUTHORIZATION";
	/**
	 * ����WebService������������1��endpoint_w 2��operationName_w 3��rpcUserName_w 4��rpcPassword_w
	 * 5��inputParamValues �������;�ָ�
	 * 6��singleVerifyExp_w �ж����ʽ
	 */
	public boolean executeJob(JobDataMap jobdatamap) {
		//��ȡ����
		String endpoint  = jobdatamap.getString("endpoint_w").trim();
		String operationName = jobdatamap.getString("operationName_w").trim();
		String rpcUserName = jobdatamap.getString("rpcUserName_w").trim();
		String rpcPassword = jobdatamap.getString("rpcPassword_w").trim();
		//���ò���
		String inputParamValues = jobdatamap.getString("inputParamValues").trim();
		
		Service service = new Service();
		Call call;
		try {
			//����endpoint��operationName
			call = (Call) service.createCall();
			call.setTargetEndpointAddress(new java.net.URL(endpoint));
			//�����û���/����
			String auth = rpcUserName + ":" + rpcPassword;
			Hashtable hashTb = new Hashtable();
			hashTb.put(RPC_CONNECTION_AUTHORIZATION, Base64.bytesToBASE64NoSpace(auth.getBytes("utf-8")));
			call.setProperty(HTTPConstants.REQUEST_HEADERS, hashTb);		

			//��ͨWebService����
			QName qname = new QName(null, operationName);
			call.setOperationName(qname);
			Object[] paramArray = null;
			if("".equals(inputParamValues))
				paramArray = new Object[0];
			else
				paramArray = inputParamValues.split(";");
			//Զ�̵���
			Object res = call.invoke(paramArray);
			Object[] resArray = null;
			//���ؽ������
			if(res != null){
				String result = null;
				if(res.getClass().isArray()){
					int resultCount = Array.getLength(res);
					resArray = new Object[resultCount];
					
					if(resultCount > 0){
						result = "���1:"+Array.get(res,0)+" ";
						resArray[0] = Array.get(res,0);
					}
					for(int i = 1;i<resultCount;i++){
						result += "���"+(i+1)+":"+Array.get(res,i)+" ";
						resArray[i] = Array.get(res,i);
					}
				}else{
					result = ""+res;
					resArray = new Object[]{res};
				}
				super.addLogString("���1:" + result+ " ");
			}else{
				resArray = new Object[0];
			}
			return this.execVerifyExpression(resArray, jobdatamap.getString("singleVerifyExp_w"), this.getClass().getName());
		}catch(Exception e){
			//��װ�쳣
			RuntimeException ex = new RuntimeException(e);
			throw ex;
		}
	}
}
