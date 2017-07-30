<%@ page import = "com.universe.softplat.scheduler.util.ProjectConfigHelper"%>
<%@ page import = "com.universe.softplat.scheduler.Task"%>
<%@ page import="java.util.*" %>
<%@ page contentType="text/html;" pageEncoding="UTF-8"%>
<%@ taglib uri="/WEB-INF/taglib/struts-bean.tld" prefix="bean" %>
<%
 request.setCharacterEncoding(ProjectConfigHelper.PROJECT_CODINGKIND);
 response.setContentType("text/html;charset="+ProjectConfigHelper.PROJECT_CODINGKIND);
%>
<html>
<head>
<%
	String path = request.getContextPath();
	//操作类型(new/edit)
	String operKind =(String)request.getAttribute("operType");
	String operType = operKind;
	//调度任务封装对象接口
	Task myTask = (Task)request.getAttribute("task");

	Map extendJobDetails = null;
	String taskId = "";
	String taskName = "";
	String schType = "";
	String cron = "";
	int state = 0;
	String runType = "1";
	String priority = "5";
	String description = "";
	
	if ((operKind.equals("edit")) || (operKind.equals("view"))){
	    extendJobDetails = myTask.getExtendJobDetails();
		taskId = myTask.getId();
		taskName = myTask.getName();
		description = myTask.getDescription() == null ? "":myTask.getDescription();		
		schType = myTask.getClassName();
		cron = myTask.getCron();
		if (cron == null){
			cron = "";
		}
		runType = myTask.getType()+"";
		if (runType == null){
			runType = "1";
		}
		priority = myTask.getPriority()+"";
		if (priority == null){
			priority = "5";
		}
		state = (int)myTask.getState();

		if (operKind.equals("edit")){
			operKind = "save";
		}
	}else if (operKind.equals("new")){
		operKind = "save";
	}
	Map jobConfig = (Map)request.getAttribute("jobs");
	List jobList = (List)request.getAttribute("jobList");
%>
<title><bean:message bundle="softplat_schedular_resource" key="TaskPage.title"/></title>
<META HTTP-EQUIV="MSThemeCompatible" CONTENT="No">
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<LINK href="<%=path%>/softplat/pages/scheduler/css/Style.css" rel="stylesheet"></LINK>  
<script type="text/javascript" src="<%=path %>/softplat/pages/scheduler/jsLib/SCHEDULARCONSTANTS.js"></script>
<script type="text/javascript" src="<%=path %>/softplat/pages/scheduler/jsLib/taskOperate.js"></script>
<script type="text/javascript">
function init() {   
	var tempTag = document.getElementById('runtime');
	tempTag.contentEditable = false;

	if ('<%=runType%>' == "0") {
		var obj = taskForm.autoOrManual;
  		obj.selectedIndex = 0;
  	}
  	
  	var obj = taskForm.taskPriority;
  	if('<%=priority%>' == "6"){  		
  		obj.selectedIndex = 0;
  	}else if('<%=priority%>' == "4"){
  		obj.selectedIndex = 2;
  	}
  	
  	var obj = taskForm.scheduleType;
  	obj.value="<%=schType%>"; 
  	
  	schTypeChange();
}
function cronDialog() {
	var location = "<%=request.getContextPath()%>/defineCron.do?cronExpress="+taskForm.cronPress.value;
	var edocfeatures = "status:no;resizable:no;center:yes;scroll=no;help:no;dialogWidth:600px;dialogHeight:350px";
	var cron = showModalDialog(location,window,edocfeatures);
	taskForm.cron.value = taskForm.cronPress.value;
}
function setChecked(tagName){
	var tag = document.getElementById(tagName);
	tag.checked=true;
}
function show(divName){ 
    var divTag = document.getElementsByTagName('div');
    for(i=0;i<divTag.length;i++){
	 divTag[i].style.display='none';
    } 
    if(divName == '')
      return;
    divShowTag = document.getElementById(divName);
    if(divShowTag == null)
      return;
 	divShowTag.style.display='';
}
function check() {
  	var post = "cron=" + taskForm.cron.value; 
  	req = new ActiveXObject("Microsoft.XMLHTTP");   
    req.open("POST", "Scheduler-SchedulerOperator.do?method=<%=operKind %>", false);
    req.setrequestheader("content-length",post.length); 
    req.setrequestheader("content-type","application/x-www-form-urlencoded");
    req.send(post);
    isOk();
}
function isOk() {
	taskForm.action = "Scheduler-SchedulerOperator.do?method=<%=operKind %>";
  	taskForm.submit();
}
function schTypeChange(){
	show(taskForm.scheduleType.value);
}
</script>
</head>
<body onload="init()">
<form name="taskForm" method="post">
<table border="0" cellpadding="0" cellspacing="0" width="100%" height="70%">
  <tr>
    <td align="center" valign="middle">
        <table width="70%" height="100%" border="0" cellpadding="0" cellspacing="5" class="main_label_outline">
          <tr>
            <td valign="top">
              <table  cellspacing="0" class="main_label_table">
                <tr>
                  <td class="main_label_td" valign="middle" nowrap><bean:message bundle="softplat_schedular_resource" key="TaskPage.taskForm.taskName"/></td>
                  <td class="main_label_td" valign="middle" nowrap colspan="3"><input type="text" size="50" id="taskName" name="taskName" value="<%=taskName %>">
                  <span style="color:red">*</span></td>
                </tr>
                <tr>
                  <td class="main_label_td" valign="middle" nowrap><bean:message bundle="softplat_schedular_resource" key="TaskPage.taskForm.taskDesc"/></td>
                  <td class="main_label_td" valign="middle" nowrap colspan="3"><input type="text" size="50" id="description" name="description" value="<%=description %>"></td>
                </tr>                            
              	<tr>
                  <td class="main_label_td" valign="middle" nowrap><bean:message bundle="softplat_schedular_resource" key="TaskPage.taskForm.jobName"/></td>
                  <td class="main_label_td" valign="middle" nowrap colspan="3">
                    <select name="scheduleType" onchange="schTypeChange()">
                      <%
                      if(jobConfig!=null){
                    	  for(int i = 0;i<jobList.size();i++){
                              String jobClass =(String)jobList.get(i);
                              Map jobMap = (Map)jobConfig.get(jobClass);
                              String name = (String)jobMap.get("name");                                                  
                        		out.print("<option value=\""+jobClass+"\">"+name+"</option>");                    		  
                    	  }
                      }
                      %>
                     </select>                    
                  </td>
                </tr>        
                <tr>
                  <td class="main_label_td" valign="middle" nowrap><bean:message bundle="softplat_schedular_resource" key="TaskPage.taskForm.execTime"/></td> 
                  <td class="main_label_td" valign="middle" nowrap colspan="3"><input type="text" size="50" id="runtime" name="cron"  value="<%=cron %>">
                  <img src="<%=request.getContextPath()%>/softplat/pages/scheduler/image/time.gif" alt="<bean:message bundle="softplat_schedular_resource" key="TaskPage.taskForm.setExecTime"/>" width="18" height="18" border="0" align="absmiddle" onclick="cronDialog()" class="icon">
                  <span style="color:red">*</span>&nbsp;<span style="cursor:pointer;color:blue;" onclick="alert(cronExpressionDesc);"><bean:message bundle="softplat_schedular_resource" key="defineCron.cronExpDesc"/></span></td>
                </tr>
                </table>
                
                <div id="runType" style="display:none">
                <table>                  
                <tr>
                  <td class="main_label_td" valign="middle" nowrap><bean:message bundle="softplat_schedular_resource" key="TaskPage.taskForm.runType"/></td>
                  <td class="main_label_td" valign="middle" nowrap>
                  <select name="autoOrManual">
                  	<option value="0"><bean:message bundle="softplat_schedular_resource" key="TaskPage.taskForm.manual"/></option>
                  	<option value="1" selected><bean:message bundle="softplat_schedular_resource" key="TaskPage.taskForm.auto"/></option>
                  </select>
                  </td>
                  <td class="main_label_td" align="right" nowrap><bean:message bundle="softplat_schedular_resource" key="TaskPage.taskForm.priority"/></td>
                  <td class="main_label_td" align="left" nowrap><select name="taskPriority">
                  	<option value="6"><bean:message bundle="softplat_schedular_resource" key="TaskPage.taskForm.high"/></option>
                  	<option value="5" selected><bean:message bundle="softplat_schedular_resource" key="TaskPage.taskForm.middle"/></option>
                  	<option value="4"><bean:message bundle="softplat_schedular_resource" key="TaskPage.taskForm.low"/></option>
                  </select>
                  </td>
                </tr>
                </table>  
                </div>         
             <%
                          if(jobConfig!=null){
                                 	  	for(int tempIndex = 0;tempIndex<jobList.size();tempIndex++){
                                	 String jobClass =(String)jobList.get(tempIndex);
                                   Map jobMap = (Map)jobConfig.get(jobClass);
                                   
                                   List paramNameList = (List)jobMap.get(com.universe.softplat.scheduler.server.JobConfigParser.PARAM_NAME_LIST);                
                                   Properties p = null;     
                                   String type = null;
                                   String label = null; 
                                   String value = null;  
                                   String defaultValue = null;
                                   String paramDesc = null;
                                   out.println("<div id=\""+jobClass+"\">");
                                   out.println("<table cellspacing=\"0\" class=\"main_label_table\">");
                                   for(int pIndex = 0;pIndex<paramNameList.size();pIndex++){
                                     	String key = (String)paramNameList.get(pIndex);
                                      p=(Properties)jobMap.get(key);
                                      label = p.getProperty("label")+"：";
                                      type = p.getProperty("type");
                                      value = p.getProperty("value");
                                      defaultValue = p.getProperty("default");
                                      //add by wudq
                                      paramDesc = p.getProperty("desc");
                                      //
                                      if(key.startsWith("singleVerifyExp")){
                                    		out.println("<tr><td class=\"main_label_td\" valign=\"middle\"><span style=\"cursor:pointer;color:blue;\" onclick=\"alert(verifyExpressionDesc);\">"+label+"</span></td>");                        	
                                      }else if(paramDesc != null && !"".equals(paramDesc.trim()))
                                    		out.println("<tr><td class=\"main_label_td\" valign=\"middle\"><span style=\"cursor:pointer;color:blue;\" title=\""+paramDesc+"\" onclick=\"alert(this.title);\">"+label+"</span></td>");
                                      else
                                    		out.println("<tr><td class=\"main_label_td\" valign=\"middle\">"+label+"</td>");
                                    	out.print("<td class=\"main_label_td\" valign=\"middle\" nowrap>");
                                    	if(type!=null && type.equalsIgnoreCase("checkbox")||type.equalsIgnoreCase("radio")){
                                    	                      		
                                    		String[] values = value.split(",");
                                    		for(int i=0;i<values.length;i++ ){                    
                                    			out.println("<input type=\""+type+"\" id=\""+key+values[i]+"\" name=\""+key+"\" value=\""+values[i]+"\"/>"+values[i]);
                                    		}
                                    		  if(type.equalsIgnoreCase("checkbox")){
                                    		  	out.print("<script language=\"javascript\">");
                                    	  		 String[] checkBoxValues = null;
                                    	  		 String[] defaultValues = null;
                                    	  		 
                                    	  		 if(extendJobDetails!=null){
                                    	  			 checkBoxValues = (String[])extendJobDetails.get(key);
                                    	  		 	 if(checkBoxValues!=null){
                                    	     		 	for(int i=0;i<checkBoxValues.length;i++){    
                                    	        	    	out.println(" setChecked('"+key+checkBoxValues[i]+"');");                      
                                    	      		    }                     	     
                                    	    		 }else if(defaultValue!=null && !operType.equalsIgnoreCase("edit")){
                                    	  		 	 	defaultValues = defaultValue.split(",");
                                    	  		 	 	for(int i=0;i<defaultValues.length;i++){    
                                    	        	 		out.println(" setChecked('"+key+defaultValues[i]+"');");                      
                                    	      			}   
                                    	  			 }
                                    	    	}else if(checkBoxValues==null&&defaultValue!=null){
                                    	  		 	 defaultValues = defaultValue.split(",");
                                    	  		 	 for(int i=0;i<defaultValues.length;i++){    
                                    	        	 	out.println(" setChecked('"+key+defaultValues[i]+"');");                      
                                    	      		}   
                                    	  		 } 
                                    	      out.print("</script>");
                                    	   }else {
                                    	       out.print("<script language=\"javascript\">"); 
                                    	   		String radioValue;
                                    	   		if(defaultValue!=null){
                                    	         	out.println(" setChecked('"+key+defaultValue+"');"); 
                                    	         }     
                                    	   	 if(extendJobDetails!=null){
                                    	  		 radioValue = (String)extendJobDetails.get(key);                     	  		
                                    	  		 if(radioValue!=null){
                                    	      		out.println(" setChecked('"+key+radioValue+"');");                     	      
                                    	         }else if(defaultValue!=null){
                                    	         	out.println(" setChecked('"+key+defaultValue+"');"); 
                                    	         }                      	          
                                    	    }
                                    	    out.print("</script>");
                                    	   }                     		
                                    	}else{
                                    	  String textValue="";
                                    	  if(defaultValue!=null)
                                    	      	textValue=defaultValue;
                                    	  if(extendJobDetails!=null){
                      	                      	   textValue = (String)extendJobDetails.get(key);
                      	                      	   if(textValue==null){
                      	                      	      if(defaultValue!=null)
                      	                      	      	textValue=defaultValue;
                      	                      	      else
                      	                      	     	 textValue="";
                      	                      	   } 
                                    	   }
                                    	   textValue = textValue.replaceAll("\"","&quot;");
                                    	   if("textArea".equalsIgnoreCase(type))
                                    		   out.println("<textarea name=\""+(String)key+"\" cols=\"45\" rows=\"5\">"+textValue+"</textarea>");
                                    	   else
                                    		   out.println("<input type=\""+type+"\"  name=\""+(String)key+"\" size=\"45\" value=\""+textValue+"\"/>");     
                                    	}
                                    	out.println("</td></tr>");
                                    }                     
                                    out.println("</table></div>");
                                   }                   
                           }
                      %>
               
            </td>
          </tr>
          <tr>
            <td align="right">
              <table class="button_table" cellspacing="0">
                <tr align="right">
                  <td class="button_td" ><input type="button" name="ok" value="<bean:message bundle="softplat_schedular_resource" key="Common.confirm"/>" class="button_normal" onclick="conform()"></td>
                  <td class="button_td" ><input type="button" name="return" value="<bean:message bundle="softplat_schedular_resource" key="Common.cancel"/>" class="button_normal" onclick="cloze()"></td>
                </tr>
              </table>
            </td>
          </tr>
        </table>
      <table cellspacing="0" class="main_button">
      </table>
    </td>
  </tr>
</table>
  	<input type="hidden" name="taskId" value="<%=taskId %>"> 	
   	<input type="hidden" name="priority" value="<%=priority %>">
   	<input type="hidden" name="schType" value="<%=schType %>">   	
   	<input type="hidden" name="runType" value="<%=runType %>">
   	<input type="hidden" name="state" value="<%=state %>">
   	<input type="hidden" name="operType" value="<%=operType %>">
   	<input type="hidden" id="cronPress" name="cronPress" value="<%=cron %>">
</form>
</body>
</html>
