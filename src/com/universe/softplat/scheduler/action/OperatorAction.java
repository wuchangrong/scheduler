package com.universe.softplat.scheduler.action;

import java.io.IOException;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.net.URLDecoder;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Properties;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.actions.DispatchAction;

import com.universe.softplat.sample.AccountHolder;
import com.universe.softplat.scheduler.SchedularManager;
import com.universe.softplat.scheduler.SchedularManagerFactory;
import com.universe.softplat.scheduler.impl.TaskImpl;
import com.universe.softplat.scheduler.server.JobConfigParser;
import com.universe.softplat.scheduler.util.ProjectConfigHelper;
import com.universe.softplat.scheduler.util.UUID;

public class OperatorAction extends DispatchAction{
	
	//调度管理器
    private static SchedularManager manage = SchedularManagerFactory.getSchedularManager();
    //扩展任务列表
    private static List jobList = null;    
    //扩展任务配置
    private static Map jobConfig = null;

    public OperatorAction(){
    }
    /**
     * 方法描述:装载Job任务配置文件，并放入httpservletrequest
     */
    public void init(HttpServletRequest httpservletrequest) throws IOException{
        if(jobConfig == null){
        	List result = (new JobConfigParser()).loadJobConfig();
        	jobList = (List)result.get(0);
        	jobConfig = (Map)result.get(1);            
        }
        httpservletrequest.setAttribute("jobs", jobConfig);
        httpservletrequest.setAttribute("jobList", jobList);
    }
    /**
     * 方法描述:新增任务
     */
    public ActionForward news(ActionMapping actionmapping, ActionForm actionform, HttpServletRequest httpservletrequest, HttpServletResponse httpservletresponse)
        throws Exception
    {
        init(httpservletrequest);
        httpservletrequest.setCharacterEncoding(ProjectConfigHelper.PROJECT_CODINGKIND);
        httpservletrequest.setAttribute("operType", "new");
        return actionmapping.findForward("TaskPage");
    }
    /**
     * 方法描述:删除指定任务，可以删除多个
     */
    public ActionForward delete(ActionMapping actionmapping, ActionForm actionform, HttpServletRequest httpservletrequest, HttpServletResponse httpservletresponse)
        throws Exception
    {
        httpservletrequest.setCharacterEncoding(ProjectConfigHelper.PROJECT_CODINGKIND);
        String taskIds[] = (String[])httpservletrequest.getParameterValues("taskId");
        for(int i = 0; i < taskIds.length; i++)
            manage.delete(taskIds[i]);
        return actionmapping.findForward("displaTasks");
    }
    /**
     * 方法描述:修改任务
     */
    public ActionForward edit(ActionMapping actionmapping, ActionForm actionform, HttpServletRequest httpservletrequest, HttpServletResponse httpservletresponse)
        throws Exception{
        init(httpservletrequest);
        httpservletrequest.setCharacterEncoding(ProjectConfigHelper.PROJECT_CODINGKIND);
        String taskId = httpservletrequest.getParameterValues("taskId")[0];
        com.universe.softplat.scheduler.Task task = manage.getTask(taskId);
        httpservletrequest.setAttribute("task", task);
        httpservletrequest.setAttribute("params", task.getDetail().get("params"));
        httpservletrequest.setAttribute("operType", "edit");
        return actionmapping.findForward("TaskPage");
    }
    /**
     * 方法描述:保存任务
     */    
    public ActionForward save(ActionMapping actionmapping, ActionForm actionform, HttpServletRequest httpservletrequest, HttpServletResponse httpservletresponse) throws Exception{   	
        Map detail = new HashMap();    	
    	httpservletrequest.setCharacterEncoding(ProjectConfigHelper.PROJECT_CODINGKIND);
    	
        String taskId = httpservletrequest.getParameter("taskId"); 
        
        String operType = httpservletrequest.getParameter("operType");
        if(operType.equals("new"))
            taskId = UUID.randomUUID().toString();
        
        String taskName = httpservletrequest.getParameter("taskName");        
        
        String taskGroup = httpservletrequest.getParameter("taskGroup");
        if(taskGroup == null || taskGroup.equalsIgnoreCase(""))
        	taskGroup = "taskGroup";
        
        String jobClass = httpservletrequest.getParameter("schType");
        if(jobClass == null || "".equals(jobClass))
            throw new RuntimeException("Job无对应的Class类！");
        
        String cron = httpservletrequest.getParameter("cron");
        //加入判断
        //
        String description = httpservletrequest.getParameter("description");
        
        String stateString = httpservletrequest.getParameter("state");
        if(stateString == null || stateString.equalsIgnoreCase(""))
        	stateString = "0";
        int state = Integer.parseInt(stateString);
        
        String creater = AccountHolder.getAccessorAccount();
        
        String runType = httpservletrequest.getParameter("runType");
        if(runType == null || runType.equalsIgnoreCase(""))
        	runType = "0";        
        
        String priority = httpservletrequest.getParameter("priority");
        if(priority == null || priority.equalsIgnoreCase(""))
        	priority = "5";        
        
        detail.put("creater", creater);
        detail.put("taskName", taskName);
        detail.put("schType", jobClass);
        Map map = (Map)jobConfig.get(jobClass);
        if(map != null){
            detail.putAll(getExtendJobDetails(httpservletrequest, map));
            detail.put("extendJobDetails", getExtendJobDetails(httpservletrequest, map));
        }
        
        String params = httpservletrequest.getParameter("params");
        detail.put("params", params);
        
        TaskImpl taskimpl = new TaskImpl(taskId, taskName, taskGroup, detail);
        taskimpl.setCron(cron);
        taskimpl.setClassName(jobClass);
        taskimpl.setDescription(description);
        taskimpl.setState(state);
        taskimpl.setPriority(Integer.valueOf(priority).intValue());
        taskimpl.setType(Integer.valueOf(runType).intValue());
        
        if(operType.equals("new"))
            manage.add(taskimpl);
        else
            manage.update(taskimpl);
        return actionmapping.findForward("displaTasks");
    }
    /**
     * 方法描述:检查是否有重名，AJAX调用
     */    
    public ActionForward checkName(ActionMapping actionmapping, ActionForm actionform, HttpServletRequest httpservletrequest, HttpServletResponse httpservletresponse)
        throws Exception{
        httpservletrequest.setCharacterEncoding(ProjectConfigHelper.PROJECT_CODINGKIND);
        String s = URLDecoder.decode(httpservletrequest.getParameter("name"), "UTF-8");
        String s1 = httpservletrequest.getParameter("group");
        String s2 = httpservletrequest.getParameter("taskId");
        if(s2 == null)
            s2 = "";
        if(s1 == null || s1.equalsIgnoreCase(""))
            s1 = "taskGroup";
        else
            s1 = URLDecoder.decode(s1, "UTF-8");
        com.universe.softplat.scheduler.Task atask[] = manage.getAllTask();
        for(int i = 0; i < atask.length; i++)
            if(atask[i].getName().equalsIgnoreCase(s) && atask[i].getGroup().equalsIgnoreCase(s1) && !atask[i].getId().equalsIgnoreCase(s2))
            {
                httpservletresponse.getOutputStream().print("-1");
                return null;
            }
        httpservletresponse.getOutputStream().print("1");
        return null;
    }
    /**
     * 方法描述:启动任务，AJAX调用
     */    
    public ActionForward startTask(ActionMapping actionmapping, ActionForm actionform, HttpServletRequest httpservletrequest, HttpServletResponse httpservletresponse) throws Exception{
    	String resultMsg = null;
    	//
    	try{
	    	httpservletrequest.setCharacterEncoding(ProjectConfigHelper.PROJECT_CODINGKIND);  	
	    	String taskIds = httpservletrequest.getParameter("taskIds");
	        if(taskIds != null)
	        	taskIds = URLDecoder.decode(taskIds, "UTF-8");
	        
	        if(taskIds != null && !taskIds.equals("")){
	            String taskIdArray[] = taskIds.split(",");
	            for(int i = 0; i < taskIdArray.length; i++){
	                String taskId = taskIdArray[i];
	                manage.runTask(taskId);
	            }
	            resultMsg = "1";
	        }else{
	        	resultMsg = "TaskID \u4E3A\u7A7A";
	        }
    	}catch(Exception e){
    		resultMsg = e.getMessage();
    	}
        //输出结果
    	httpservletresponse.getOutputStream().print(resultMsg);
        return null;
    }
    /**
     * 方法描述:启动任务，AJAX调用
     */    
    public ActionForward pauseTask(ActionMapping actionmapping, ActionForm actionform, HttpServletRequest httpservletrequest, HttpServletResponse httpservletresponse) throws Exception{
    	String resultMsg = null;
    	//
    	try{
	    	httpservletrequest.setCharacterEncoding(ProjectConfigHelper.PROJECT_CODINGKIND);  	
	    	String taskIds = httpservletrequest.getParameter("taskIds");
	        if(taskIds != null)
	        	taskIds = URLDecoder.decode(taskIds, "UTF-8");
	        
	        if(taskIds != null && !taskIds.equals("")){
	            String taskIdArray[] = taskIds.split(",");
	            for(int i = 0; i < taskIdArray.length; i++){
	                String taskId = taskIdArray[i];
	                manage.pauseTask(taskId);
	            }
	            resultMsg = "1";
	        }else{
	        	resultMsg = "TaskID \u4E3A\u7A7A";
	        }
    	}catch(Exception e){
    		StringWriter sWriter = new StringWriter();
    		PrintWriter pWriter = new PrintWriter(sWriter);
    		e.printStackTrace(pWriter);
    		resultMsg = sWriter.toString();
    		sWriter.close();
    	}
        //输出结果
    	httpservletresponse.getOutputStream().print(resultMsg);
        return null;
    }
    /**
     * 方法描述:启动任务，AJAX调用
     */    
    public ActionForward runOnceTask(ActionMapping actionmapping, ActionForm actionform, HttpServletRequest httpservletrequest, HttpServletResponse httpservletresponse) throws Exception{
    	String resultMsg = null;
    	//
    	try{
	    	httpservletrequest.setCharacterEncoding(ProjectConfigHelper.PROJECT_CODINGKIND);  	
	    	String taskIds = httpservletrequest.getParameter("taskIds");
	        if(taskIds != null)
	        	taskIds = URLDecoder.decode(taskIds, "UTF-8");
	        
	        if(taskIds != null && !taskIds.equals("")){
	            String taskIdArray[] = taskIds.split(",");
	            if(manage.runOnceTask(taskIdArray[0])){
	            	resultMsg = "1";
	            }else{
	            	resultMsg = "";
	            }
	        }else{
	        	resultMsg = "TaskID \u4E3A\u7A7A";
	        }
    	}catch(Exception e){
    		StringWriter sWriter = new StringWriter();
    		PrintWriter pWriter = new PrintWriter(sWriter);
    		e.printStackTrace(pWriter);
    		resultMsg = sWriter.toString();
    		sWriter.close();
    	}
        //输出结果
    	httpservletresponse.getOutputStream().print(resultMsg);
        return null;
    }
    /**
     * 方法描述:返回自定义属性
     */
    private Map getExtendJobDetails(HttpServletRequest httpservletrequest, Map map){
    	Map extendJobDetails = new HashMap();
    	
        Iterator iterator = map.keySet().iterator();
        while(iterator.hasNext()){
            Object obj = iterator.next();
            if(!((String)obj).equalsIgnoreCase("name") && !((String)obj).equalsIgnoreCase(JobConfigParser.PARAM_NAME_LIST)){
                Properties properties = (Properties)map.get(obj);
                String s = properties.getProperty("type");
                if(s.equalsIgnoreCase("checkbox")){
                    String as[] = httpservletrequest.getParameterValues((String)obj);
                    extendJobDetails.put(obj, as);
                } else{
                    String s1 = httpservletrequest.getParameter((String)obj);
                    extendJobDetails.put(obj, s1);
                }
            }
        }
        return extendJobDetails;
    }
}

//private Task Task2ReportTask(com.universe.softplat.scheduler.Task task){
//Task task1 = new Task();
//task1.setTaskId(task.getId());
//task1.setTaskName(task.getName());
//task1.setGroup(task.getGroup());
//task1.setTaskClass(task.getClassName());
//task1.setCron(task.getCron());
//task1.setDescript(task.getDescription());
//task1.setState(task.getState());
//task1.setRunType(String.valueOf(task.getType()));
//task1.setPriority(String.valueOf(task.getPriority()));
//
//Map map = task.getDetail();//扩展数据(自定义运行参数)
//task1.setCreater((String)map.get("creater"));
//task1.setSchType((String)map.get("schType"));
//task1.setExtendJobDetails((Map)map.get("extendJobDetails"));
//return task1;
//}

//private static final String EXTEND_JOB_DETAILS = "extendJobDetails";
//private static final String TASK_LIST_PAGE = "displaTasks";
//private static final String TASK_PAGE = "TaskPage";
//private static final String TASK = "task";
//private static final String TASKGROUP = "taskGroup";
//private static final String SCH_TYPE = "schType";
//private static final String RUN_TYPE = "runType";
//private static final String PRIORITY = "priority";
//private static final String CRON = "cron";
//private static final String DESCRIPT = "descript";
//private static final String STATE = "state";
//private static final String NEW = "new";
//private static final String EDIT = "edit";
//private static final String VIEW = "view";
//private static final String OPERTYPE = "operType";
//private static final String MAIL = "mail";
