package com.universe.softplat.scheduler.action;

import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.actions.DispatchAction;

import com.universe.softplat.scheduler.SchedularManager;
import com.universe.softplat.scheduler.SchedularManagerFactory;
import com.universe.softplat.scheduler.dao.SchedulerDAO;
import com.universe.softplat.scheduler.util.EAPDataFormat;

public class GetTasksAction extends DispatchAction{

    public GetTasksAction()
    {
    }
    /**
     * 方法描述:调度任务列表页面
     */
    public ActionForward displayTasks(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception{
        SchedularManager schedularManager = SchedularManagerFactory.getSchedularManager();
        String currentPage = request.getParameter("pageNo");
        com.universe.softplat.scheduler.Task[] tasks = schedularManager.getAllTask();
        Arrays.sort(tasks);
        //查询任务最新执行情况
        if(tasks != null){
	        List list = SchedulerDAO.queryLastState();
	        if(list != null){
		        HashMap map = new HashMap();
	        	for(int i = 0;i<list.size();i++){
	        		Map rowItem = (Map)list.get(i);
	        		Object taskName = rowItem.get("TASK_NAME");
	        		map.put(taskName, rowItem);
	        	}
	        	for(int i = 0;i<tasks.length;i++){
	        		Map rowItem = (Map)map.get(tasks[i].getName());
	        		if(rowItem == null)
	        			continue;
	        		//Date execTime = (Date)rowItem.get("START_TIME");
	        		Date execTime = EAPDataFormat.toTimestamp(String.valueOf(rowItem.get("START_TIME")));
	        		String result = (String)rowItem.get("RESULT_FLAG");
	        		if(result == null)
	        			result = "";
	        		tasks[i].setExecResult(result);
	        		tasks[i].setExecTime(execTime);
	        	}
	        }
        }
        //        
        request.setAttribute("tasks", tasks);
        request.setAttribute("currentPage", currentPage);
        return mapping.findForward("displayTasks");
    }
    /**
     * 方法描述:调度任务日志页面
     */
    public ActionForward displayLogs(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception{
    	//pageNo
    	int pageNo = 1;
    	try{
    		pageNo = Integer.parseInt(request.getParameter("pageNo"));
    	}catch(Exception e){}
    	//pageSize
    	int pageSize = 50;
    	try{
    		pageSize = Integer.parseInt(request.getParameter("pageSize"));
    	}catch(Exception e){
    	}    	
    	//查询参数
    	String taskNameCon = request.getParameter("taskNameCon");
    	if(taskNameCon != null) 
    		taskNameCon = taskNameCon.trim();
    	
    	String resultFlagCon = request.getParameter("resultFlagCon");
    	if(resultFlagCon != null)
    		resultFlagCon = resultFlagCon.trim();
    	
    	String levelCon = request.getParameter("levelCon");
    	if(levelCon != null)
    		levelCon = levelCon.trim();
    	
    	String infoCon = request.getParameter("infoCon");
    	if(infoCon != null)
    		infoCon = infoCon.trim();
    	//日期参数处理
    	SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
    	String fromDateS = request.getParameter("fromDate");
    	if(fromDateS != null)
    		fromDateS = fromDateS.trim();
    	
    	String toDateS = request.getParameter("toDate");
    	if(toDateS != null)
    		toDateS = toDateS.trim();
    	
    	Date fromDate = null;
    	Date toDate = null;
    	//fromDate
    	try{
	    	if(fromDateS !=null && !"".equals(fromDateS))
	    		fromDate = dateFormat.parse(fromDateS);
	    	if(fromDate != null)
	    		fromDate = new java.sql.Date(fromDate.getTime());
    	}catch(Exception e){
    	}
    	//toDate
    	try{
	    	if(toDateS != null && !"".equals(toDateS))
	    		toDate = dateFormat.parse(toDateS);
	    	if(toDate != null)
	    		toDate = new java.sql.Date(toDate.getTime()+24*60*60*1000);
    	}catch(Exception e){
    	}    	
    	List result = SchedulerDAO.queryLog(pageNo,pageSize,taskNameCon, resultFlagCon, levelCon, infoCon,fromDate,toDate);
    	if(result != null){
    		request.setAttribute("totalSize",result.get(0));
    		request.setAttribute("data",result.get(1));
    	}
    	//取出所有任务的名称
    	//1、当前运行任务名
    	Set taskNameSet = new HashSet();
    	SchedularManager schedularManager = SchedularManagerFactory.getSchedularManager();
        com.universe.softplat.scheduler.Task[] tasks = schedularManager.getAllTask();
        if(tasks != null){
        	for(int i = 0;i<tasks.length;i++)
        		taskNameSet.add(tasks[i].getName().trim());
        }
        //2、日志中任务名
        List tempList = SchedulerDAO.queryTaskNamesInLog();
        if(tempList != null){
        	for(int i = 0;i<tempList.size();i++)
        		taskNameSet.add(((Map)tempList.get(i)).get("TASK_NAME").toString().trim());
        }
        Object[] taskNames = taskNameSet.toArray();
        java.util.Arrays.sort(taskNames);
        
        request.setAttribute("taskNames", taskNames);
		request.setAttribute("currentPage",new Integer(pageNo));
	    return mapping.findForward("displayLogs");
    }    
}
