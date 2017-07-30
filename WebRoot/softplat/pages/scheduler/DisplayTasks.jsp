<%@ page language="java"  pageEncoding="GBK"%>
<%@ page import = "java.util.*" %>
<%@ page import = "com.universe.softplat.scheduler.util.*" %>
<%@ page import = "com.universe.softplat.sample.LoginAction" %>
<%
request.setCharacterEncoding(ProjectConfigHelper.PROJECT_CODINGKIND);
response.setContentType("text/html;charset="+ProjectConfigHelper.PROJECT_CODINGKIND);
boolean isAdmin = LoginAction.isAdmin(request);
%>
<%@ taglib uri="/WEB-INF/taglib/struts-bean.tld" prefix="bean"%>
<%@ taglib uri="/WEB-INF/taglib/struts-logic.tld" prefix="logic"%>
<%@ page import="java.text.SimpleDateFormat"%>
<%@ page import="com.universe.softplat.scheduler.Constant"%>
<%@ page import="com.universe.softplat.scheduler.Task"%>
<%
String path = request.getContextPath();
Task[] tasksAll = (Task[])request.getAttribute("tasks");
//过滤条件
//名称条件
String taskNameCon = request.getParameter("taskNameCon");
taskNameCon = taskNameCon == null?"":taskNameCon.trim();
boolean isNameCon = !"".equals(taskNameCon);
//状态条件
String taskStateConS = request.getParameter("taskStateCon");
int taskStateCon = -2;
if(taskStateConS != null && !"".equals(taskStateConS.trim())){
	try{
		taskStateCon = Integer.parseInt(taskStateConS);
	}catch(Exception e){}
}
boolean isStateCon = taskStateCon <= Constant.TASK_STATE_BLOCKED && taskStateCon >= Constant.TASK_STATE_NONE;
//最近运行结果条件
String resultFlagCon = request.getParameter("resultFlagCon");
boolean isResultCon = resultFlagCon != null && !"".equals(resultFlagCon.trim());
//过滤操作
Task[] tasks = null;
if(isNameCon || isStateCon || isResultCon){
	//过滤操作
	ArrayList resultList = new ArrayList();
	for(int i = 0;i<tasksAll.length;i++){
		boolean temp = true;
		//1
		if(temp && isNameCon){
			temp = tasksAll[i].getName().indexOf(taskNameCon)>=0;
		}
		//2
		if(temp && isStateCon){
			temp = tasksAll[i].getState() == taskStateCon;
		}
		//3
		if(temp && isResultCon){
			temp = "0".equals(resultFlagCon) && (tasksAll[i].getExecResult() == null || "".equals(tasksAll[i].getExecResult())) || resultFlagCon.equals(tasksAll[i].getExecResult());
		}
		//结果
		if(temp){
			resultList.add(tasksAll[i]);
		}			
	}
	tasks = new Task[resultList.size()];
	for(int i = 0;i<tasks.length;i++)
		tasks[i] = (Task)resultList.get(i);
}else
	tasks = tasksAll;
//
String currentPage = (String)request.getAttribute("currentPage");
if(currentPage == null||currentPage.equals("")){
  currentPage = "1";
}
//页面大小
int pageSize = 100;

int totalSize = tasks.length;
int start = pageSize*(Integer.parseInt(currentPage)-1);
int totalPage = 0;
if(tasks.length%pageSize != 0){
   totalPage = tasks.length/pageSize+1;
}else{
   totalPage = tasks.length/pageSize;
   if(totalPage == 0)
   {
      totalPage = 1;
   }
}
SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
  <head>
    <title><bean:message bundle="softplat_schedular_resource" key="DisplyTasks.title"/></title>
    <LINK href="<%=path%>/softplat/pages/scheduler/css/Style.css" rel="stylesheet"></LINK>   
    <script type="text/javascript" src="<%=path%>/softplat/pages/scheduler/jsLib/page.js"></script>    
  	<script type="text/javascript" src="<%=path%>/softplat/pages/scheduler/jsLib/sortTable.js"></script>       
    <script type="text/javascript" src="<%=path%>/softplat/pages/scheduler/jsLib/SCHEDULARCONSTANTS.js"></script>
    <script type="text/javascript" src="<%=path%>/softplat/pages/scheduler/jsLib/taskOperate.js"></script>
  	<script type="text/javascript">
  	var isAdmin = <%=isAdmin?"true":"false"%>;
  	
  	var taskState0 = 0;
  	var taskState1 = 0;
    function setButton(obj,taskType,taskState){
        if (obj.checked) {
			if (taskType == 1){
				switch(taskState){
				case '0':
					taskState0++;
					break;
				case '1':
					taskState1++;
					break;
				}
			}
  		} else {
    		if (taskType == 1){
				switch(taskState){
				case '0':
					taskState0--;
					break;
				case '1':
					taskState1--;
					break;
				}
			}
  		}
        if (isAdmin && taskState0 > 0){
	    	document.getElementById("pauseBut").disabled = false;
	    }else{
	    	taskState0 = 0;
	    	document.getElementById("pauseBut").disabled = true;
	    }
	    	    
	    if (isAdmin && taskState1 > 0){
	    	document.getElementById("startBut").disabled = false;
	    }else{
	    	taskState1 = 0;
	    	document.getElementById("startBut").disabled = true;
	    }
	            
        var o = document.getElementsByTagName("input");	
    	var checkedCount=0; 	
    	for(i = 0; i < o.length; i++) {
    		var obj = o[i];
      		if(obj.type == "checkbox"&&obj.checked) { 
      			checkedCount++;
      		}	   
    	}
    	
    	if (checkedCount == 1){
    		document.getElementById("editBut").disabled = !isAdmin || false;
    		document.getElementById("delBut").disabled = !isAdmin || false;
    		document.getElementById("viewLogBut").disabled = false;
    		document.getElementById("runOnceBut").disabled = false;    	
    	}else if(checkedCount > 1){
    		document.getElementById("editBut").disabled = true;
    		document.getElementById("delBut").disabled = !isAdmin || false;
    		document.getElementById("viewLogBut").disabled = true;
    		document.getElementById("runOnceBut").disabled = true;  
    	}else{
    		document.getElementById("editBut").disabled = true;
    		document.getElementById("delBut").disabled = true;
    		document.getElementById("viewLogBut").disabled = true; 
    		document.getElementById("runOnceBut").disabled = true; 		
    	}   
     }
     
	function resizeTable(){
		var windivObj=document.all("windiv");
		var winTotalObj=document.all("windiv1").item(1);
		var height = document.body.clientHeight;
		var winTotalObjHeight=winTotalObj.offsetHeight;
		var navigateObjHeight = 0;
		var navigateObj = document.all("navigateTbl");
		if(navigateObj) 
			navigateObjHeight = navigateObj.offsetHeight;
		windivObj.style.height = height - document.all("maintbl").offsetTop-document.all("maintbl").offsetHeight-10-winTotalObjHeight-navigateObjHeight;
	}
	window.onload = function(){
		resizeTable();
		initial('<%=currentPage %>','<%=totalPage%>');
	};
	window.onresize = resizeTable;     
    </script>
  </head>
  <body>
    <br>
    <form id="tasks" name="tasksForm" method="post">
     <input type="hidden" name="totalPage" value="<%=totalPage %>">
     <input type="hidden" name="taskIdValue" id="taskIdValue" value="">
<!-- table header -->
<table id='maintbl' name='maintbl' border="0" cellpadding="0" cellspacing="0" width="100%">
<tr>    		
	<td width="100%" colspan=2>
	<table cellspacing="0" border = 0>
			<tr>
			<td><input type="button" class="button_normal" id="delBut" value='<bean:message bundle="softplat_schedular_resource" key="DisplyTasks.taskDelete"/>' onclick="work('delete',1)" disabled="true"></td>
			<!-- task condition -->
        	<td class="main_label_td" align="right"><bean:message bundle="softplat_schedular_resource" key="DisplayLogs.taskName"/></td>	
			<td class="main_label_td" align="left"><input type="text" size="23" name="taskNameCon" value="<%=taskNameCon%>"></td>  	
        	<td class="main_label_td" align="right">&nbsp;&nbsp;&nbsp;<bean:message bundle="softplat_schedular_resource" key="DisplyTasks.taskState"/></td>
        	<td class="main_label_td" align="left"><select name="taskStateCon">    	
                  	<option value="" <%=taskStateCon > Constant.TASK_STATE_BLOCKED || taskStateCon < Constant.TASK_STATE_NONE?"selected":""%>><bean:message bundle="softplat_schedular_resource" key="DisplayLogs.all"/></option>                  
                  	<option value="<%=Constant.TASK_STATE_BLOCKED%>"  <%=taskStateCon == Constant.TASK_STATE_BLOCKED? "selected":"" %>><bean:message bundle="softplat_schedular_resource" key="DisplyTasks.task.blockState"/></option>
                  	<option value="<%=Constant.TASK_STATE_COMPLETE%>" <%=taskStateCon == Constant.TASK_STATE_COMPLETE?"selected":"" %>><bean:message bundle="softplat_schedular_resource" key="DisplyTasks.task.completeState"/></option>
                  	<option value="<%=Constant.TASK_STATE_ERROR%>" 	  <%=taskStateCon == Constant.TASK_STATE_ERROR?   "selected":"" %>><bean:message bundle="softplat_schedular_resource" key="DisplyTasks.task.errorState"/></option>
                  	<option value="<%=Constant.TASK_STATE_NORMAL%>"   <%=taskStateCon == Constant.TASK_STATE_NORMAL?  "selected":"" %>><bean:message bundle="softplat_schedular_resource" key="DisplyTasks.task.normalState"/></option>
                  	<option value="<%=Constant.TASK_STATE_PAUSED%>"   <%=taskStateCon == Constant.TASK_STATE_PAUSED?  "selected":"" %>><bean:message bundle="softplat_schedular_resource" key="DisplyTasks.task.pauseState"/></option>
                  	<option value="<%=Constant.TASK_STATE_NONE%>"	  <%=taskStateCon == Constant.TASK_STATE_NONE?    "selected":"" %>><bean:message bundle="softplat_schedular_resource" key="DisplyTasks.task.noneState"/></option>
                  </select>          	
        	</td>
        	<td class="main_label_td" align="right">&nbsp;&nbsp;&nbsp;<bean:message bundle="softplat_schedular_resource" key="DisplyTasks.lastRunResult"/></td>
        	<td class="main_label_td" align="left"><select name="resultFlagCon">
                  	<option value=""  <%=!"Y".equals(resultFlagCon) && !"N".equals(resultFlagCon) && !"0".equals(resultFlagCon)?"selected":""%>><bean:message bundle="softplat_schedular_resource" key="DisplayLogs.all"/></option>                    
                  	<option value="0" <%="0".equals(resultFlagCon)?"selected":"" %>><bean:message bundle="softplat_schedular_resource" key="DisplyTasks.task.noneState"/></option>   
                  	<option value="Y" <%="Y".equals(resultFlagCon)?"selected":"" %>><bean:message bundle="softplat_schedular_resource" key="DisplayLogs.resultSuccess"/></option>
                  	<option value="N" <%="N".equals(resultFlagCon)?"selected":"" %>><bean:message bundle="softplat_schedular_resource" key="DisplayLogs.resultFail"/></option>              	
                  </select>&nbsp;&nbsp;
        	</td>
        	<td><input type="button" name="query" value="<bean:message bundle="softplat_schedular_resource" key="Common.query"/>" class="button_normal" onclick="jumpPage('first','<%=currentPage%>','tasks','displaTasks.do?method=displayTasks')">&nbsp;&nbsp;</td>			
   			<td><input type="button" class="button_normal" id="startBut" value='<bean:message bundle="softplat_schedular_resource" key="DisplyTasks.run"/>'  onclick="operateTask('startTask','<%=path%>/Scheduler-SchedulerOperator.do','<%=currentPage%>')" disabled="true"></td>
   			<td><input type="button" class="button_normal" id="pauseBut" value='<bean:message bundle="softplat_schedular_resource" key="DisplyTasks.stop"/>' onclick="operateTask('pauseTask','<%=path%>/Scheduler-SchedulerOperator.do','<%=currentPage%>')" disabled="true"></td>
   			<td><input type="button" class="button_large" id="runOnceBut" value='<bean:message bundle="softplat_schedular_resource" key="DisplyTasks.runOnce"/>' onclick="operateTask('runOnceTask','<%=path%>/Scheduler-SchedulerOperator.do','<%=currentPage%>')" disabled="true"></td>
   			<td><input type="button" class="button_normal" id="newBut" value='<bean:message bundle="softplat_schedular_resource" key="DisplyTasks.taskNew"/>' onclick="work('news')" <%=isAdmin?"":"disabled=\"true\""%>></td>
  			<td><input type="button" class="button_normal" id="editBut" value='<bean:message bundle="softplat_schedular_resource" key="DisplyTasks.taskEdit"/>' onclick="work('edit')" disabled="true"></td>         				
   			<td><input type="button" class="button_large" id="viewLogBut" value='<bean:message bundle="softplat_schedular_resource" key="DisplyTasks.viewLog"/>' onclick="viewLog()" disabled="true"></td>         				
 			</tr>      
	</table>
   </td> 
</tr>
<tr><td width=100%>
<DIV align="center" id='windiv1' name='windiv1' style=" BORDER-RIGHT: gray 0px solid; PADDING-RIGHT: 0px; PADDING-LEFT: 0px; PADDING-BOTTOM: 0px; MARGIN: 0px; OVERFLOW: hidden; BORDER-LEFT: gray 0px solid; PADDING-TOP: 0px; BORDER-BOTTOM: gray 0px solid">
     <table name="displayTasks" border="1" cellpadding="2" cellspacing="0" bordercolordark="#FFFFFF" bordercolorlight="#848284" bgcolor="#F4F4F4" width="100%" class="NEUDwListTableback">
        <tr>
          <th class="NEUDwListTH" width="4%">
           <font class="NEUDwListDodyFont">
            <bean:message bundle="softplat_schedular_resource" key="DisplyTasks.select"/>
            </font>         
          </th>
          <th class="NEUDwListTH" width="24%">
           <font class="NEUDwListDodyFont">
            <bean:message bundle="softplat_schedular_resource" key="DisplyTasks.taskName"/>
            </font>
          </th>     
          <th  class="NEUDwListTH" width="6%">
          <font class="NEUDwListDodyFont">
           <bean:message bundle="softplat_schedular_resource" key="DisplyTasks.taskState"/>
             </font>
          </th>
          <th  class="NEUDwListTH" width="15%">
          <font class="NEUDwListDodyFont">
            <bean:message bundle="softplat_schedular_resource" key="DisplyTasks.startTime"/>
          </font>
          </th>
          <th  class="NEUDwListTH" width="15%">
          <font class="NEUDwListDodyFont">
            <bean:message bundle="softplat_schedular_resource" key="DisplyTasks.endTime"/>
          </font>
          </th>
          <th  class="NEUDwListTH" width="15%">
          <font class="NEUDwListDodyFont"><bean:message bundle="softplat_schedular_resource" key="DisplyTasks.task.lastExecTime"/></font>
          </th>
          <th  class="NEUDwListTH" width="6%">
          <font class="NEUDwListDodyFont"><bean:message bundle="softplat_schedular_resource" key="DisplayLogs.resultFlag"/></font>
          </th>          
          <th  class="NEUDwListTH" width="15%">
          <font class="NEUDwListDodyFont">
            <bean:message bundle="softplat_schedular_resource" key="DisplyTasks.taskNextRunTime"/>
          </font>
          </th>                              
        </tr>
        </table>
</div></td>
<th class="NEUDwListTH"><img src="<%=path%>/softplat/pages/scheduler/image/corner.gif"></th></tr>
</table>
<!-- table content -->
<table border="0" cellpadding="0" cellspacing="0" width="100%">
<tr><td width="100%">
<div align="center" id='windiv' name='windiv' style="OVERFLOW-y: scroll;OVERFLOW-x: hidden " >       
     	<table border="1" cellpadding="2" cellspacing="0" bordercolordark="#FFFFFF" bordercolorlight="#848284" bgcolor="#F4F4F4" width="100%" class="NEUDwListTableback"> 
        <%
          if(tasks != null&&tasks.length > 0){
              int count = 0;
	          for(int i = start; i < tasks.length; i++){
	             Task task = tasks[i];
	             String description = task.getDescription();
	             
	             String cronExp = task.getCron();
	             if(description == null || "".equals(description.trim()))
	            	 description = "Cron:"+CronUtil.getCronExpress(cronExp);
	             else
	            	 description += "\nCron:"+CronUtil.getCronExpress(cronExp);	             
	             Date startTime = CronUtil.getStartTime(cronExp);
	             Date endTime = CronUtil.getEndTime(cronExp);
	             String triggerExp = CronUtil.getCronExpress(cronExp);
	             
	             if(i%2 == 1){
         %>
         <tr class="NEUDwListRowBgColor2">            
         <%}else{%>
        <tr class="NEUDwListRowBgColor1">
        <%}%>
          <td align="middle" valign="middle" width="4%">
           <input  type="checkbox"   id="<%=task.getId()%>" value="<%=task.getId()%>" name="taskId" onclick="setButton(this,'<%=task.getType()%>','<%= task.getState()%>')">
          </td>
          <td class="NEUHTCInput"  width="24%">
          <div style="cursor:pointer;color:blue;" title="<%=description%>" onclick="alert(this.title);"><%=task.getName()%></div>
          </td>               
          <td class="NEUHTCInput" align="center" width="6%" <%=task.getState()!=Constant.TASK_STATE_NORMAL?"style=\"color:blue\"":""%>>
            <% switch(task.getState()){
                 case Constant.TASK_STATE_BLOCKED : %><b><bean:message bundle="softplat_schedular_resource" key="DisplyTasks.task.blockState"/></b>
                 <%;break;
                 case Constant.TASK_STATE_COMPLETE : %><b><bean:message bundle="softplat_schedular_resource" key="DisplyTasks.task.completeState"/></b>
                 <%;break;
                 case Constant.TASK_STATE_ERROR : %><b><bean:message bundle="softplat_schedular_resource" key="DisplyTasks.task.errorState"/></b>
                 <%;break;
                 case Constant.TASK_STATE_NONE : %><b><bean:message bundle="softplat_schedular_resource" key="DisplyTasks.task.noneState"/></b>
                 <%;break;
                 case Constant.TASK_STATE_NORMAL : %><bean:message bundle="softplat_schedular_resource" key="DisplyTasks.task.normalState"/>
                 <%;break;
                 case Constant.TASK_STATE_PAUSED :%><b><bean:message bundle="softplat_schedular_resource" key="DisplyTasks.task.pauseState"/></b>
                 <% 
               }
             %>
          </td>
          <td class="NEUHTCInput" width="15%">
          	 <%
          	 if(startTime == null){
             %>&nbsp;
             <%}else{%>
             <%=df.format(startTime)%>
             <%}%>
          </td>     
          <td class="NEUHTCInput" width="15%">
          	 <%
          	 if(endTime == null){
             %>&nbsp;
             <%}else{%>
             <%=df.format(endTime)%>
             <%}%>
          </td> 
          <td class="NEUHTCInput" width="15%">
          <%
          if(task.getExecTime() != null)
          	out.print(df.format(task.getExecTime()));
          else
        	out.print("&nbsp;&nbsp;");
          %>
          </td>      
          <td class="NEUHTCInput" align="center" width="6%" <%="N".equalsIgnoreCase(task.getExecResult())?"style=\"color:red;\"":""%>>
            <%if("Y".equalsIgnoreCase(task.getExecResult())){%>
            <bean:message bundle="softplat_schedular_resource" key="DisplayLogs.resultSuccess"/>
            <%}else if("N".equalsIgnoreCase(task.getExecResult())){%>
            <b><bean:message bundle="softplat_schedular_resource" key="DisplayLogs.resultFail"/></b>            
            <%}else
            	  out.print("&nbsp;");
            %>
          </td>                   
          <td class="NEUHTCInput" width="15%">
          	 <%
                if(task.getNextStartTime() == null){
             %>&nbsp;
             <%}else{%>
             <%=df.format(task.getNextStartTime())%>
             <%}%>
          </td>
        </tr>
        <% 		 count++;
	         	 if(count == pageSize) break;
           	 }      	
           }
         %>     
    </table>
</div></td></tr>   
<!-- navigate  -->
<tr><td width="100%">
   <DIV align="center" id='windiv1' name='windiv1' style=" OVERFLOW-y: scroll;OVERFLOW-x: hidden ; BORDER-RIGHT: gray 0px solid; PADDING-RIGHT: 0px; PADDING-LEFT: 0px; PADDING-BOTTOM: 0px; MARGIN: 0px; OVERFLOW-y: scroll;BORDER-LEFT: gray 0px solid; PADDING-TOP: 0px; BORDER-BOTTOM: gray 0px solid">
    <table cellspacing="0" width="100%" >
    	<tr>
    		<td >
    			<table>
         			<tr>
               			<td class="icon2_td"><input type="button" title="<bean:message bundle="softplat_schedular_resource" key="DisplyTasks.taskFirstPage"/>"  id="firstButton"class="NEUDwButton_FirstPage" onclick="jumpPage('first','<%=currentPage%>','tasks','displaTasks.do?method=displayTasks')"/></td>
               			<td class="icon2_td"><input type="button" title="<bean:message bundle="softplat_schedular_resource" key="DisplyTasks.taskPrePage"/>" id="upButton" class="NEUDwButton_PreviousPage" onclick="jumpPage('up','<%=currentPage%>','tasks','displaTasks.do?method=displayTasks')"/></td>
			   			<td style="height: 22px; width: 27px;"><font class="NEUDwListDodyFont"><%=currentPage%>/<%=totalPage %></font></td>
			   			<td class="icon2_td"><input type="button" title="<bean:message bundle="softplat_schedular_resource" key="DisplyTasks.taskNextPage"/>"  id="downButton" class="NEUDwButton_NextPage" onclick="jumpPage('down','<%=currentPage%>','tasks','displaTasks.do?method=displayTasks')"/></td>
			   			<td class="icon2_td"><input type="button" title="<bean:message bundle="softplat_schedular_resource" key="DisplyTasks.taskLastPage"/>" id="lastButton" class="NEUDwButton_LastPage" onclick="jumpPage('last','<%=currentPage%>','tasks','displaTasks.do?method=displayTasks')"/></td>					 
			   			<td style="height: 22px; width: 27px;"><input type="text"  id="pageNo" name="pageNo" value="<%=currentPage%>" style="width:25;ime-mode:disabled"  onkeydown="" onkeypress="" minlength="1" maxlength="9"/> </td>
			   			<td class="icon2_td"><input type="button" title="<bean:message bundle="softplat_schedular_resource" key="DisplyTasks.taskGoPage"/>" id="" class="NEUDwButton_GoPage"  onclick="jumpPage('jump','<%=currentPage%>','tasks','displaTasks.do?method=displayTasks')" /></td>	
			   			<td style="height: 22px;"><font class="NEUDwListDodyFont">&#160;<bean:message bundle="softplat_schedular_resource" key="DisplyTasks.taskCount"/><%=totalSize%><bean:message bundle="softplat_schedular_resource" key="DisplyTasks.taskPageUnit"/>&#160;</font></td>		    
         			</tr>      
    			</table>
    		</td>
    	</tr>
    </table> 
    </DIV>
    </td></tr></table>
      </form>  
  </body>
</html>
