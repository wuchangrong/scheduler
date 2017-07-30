<%@ page language="java"  pageEncoding="GBK"%>
<%@ page import = "java.util.*" %>
<%@ page import = "com.universe.softplat.scheduler.util.*" %>
<%
request.setCharacterEncoding(ProjectConfigHelper.PROJECT_CODINGKIND);
response.setContentType("text/html;charset="+ProjectConfigHelper.PROJECT_CODINGKIND);
%>
<%@ taglib uri="/WEB-INF/taglib/struts-bean.tld" prefix="bean"%>
<%@ taglib uri="/WEB-INF/taglib/struts-logic.tld" prefix="logic"%>
<%@ page import="java.text.SimpleDateFormat"%>
<%
Object temp;
String path = request.getContextPath();
//当前页
int currentPage = 1;
temp = request.getAttribute("currentPage");
if(temp != null)
	currentPage = ((Number)temp).intValue();
//页面大小
int pageSize = 50;
//总记录数
temp = request.getAttribute("totalSize");
int totalSize = 0;
if(temp != null)
	totalSize = ((Number)temp).intValue();
//总页数
int totalPage = 0;
if(totalSize%pageSize != 0){
   totalPage = totalSize/pageSize+1;
}else{
   totalPage = totalSize/pageSize;
   if(totalPage == 0){
      totalPage = 1;
   }
}
//数据
List data = (List)request.getAttribute("data");
Object[] taskNames = (Object[])request.getAttribute("taskNames");
//日期格式化
SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

String taskNameCon = request.getParameter("taskNameCon");
int taskNameSelectedIndex = -1;
if(taskNames != null && taskNameCon != null){
	taskNameSelectedIndex = java.util.Arrays.binarySearch(taskNames,taskNameCon.trim());
}
String resultFlagCon = request.getParameter("resultFlagCon");
String levelCon = request.getParameter("levelCon");
String infoCon = request.getParameter("infoCon");

String fromDate = request.getParameter("fromDate");
String toDate = request.getParameter("toDate");

%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
  <head>
    <title><bean:message bundle="softplat_schedular_resource" key="DisplayLogs.title"/></title>
    <LINK href="<%=path%>/softplat/pages/scheduler/css/Style.css" rel="stylesheet"></LINK>  
  	<link href="<%=path%>/softplat/pages/scheduler/datepicker/default/datepicker.css" rel="stylesheet" type="text/css">
    <script type="text/javascript" src="<%=path%>/softplat/pages/scheduler/jsLib/page.js"></script>
  	<script type="text/javascript" src="<%=path%>/softplat/pages/scheduler/datepicker/WdatePicker.js"></script>
  	<script type="text/javascript" src="<%=path%>/softplat/pages/scheduler/jsLib/sortTable.js"></script>
  </head>
  <body>
    <br>
    <form id="tasks" name="tasksForm" method="post">
     <input type="hidden" name="totalPage" value="<%=totalPage %>">
     <input type="hidden" name="pageSize"  value="<%=pageSize %>">
     <table cellspacing="0" cellpadding="0" class="main_label_table2" width="100%">
     	<tr>
        	<td class="main_label_td" align="right"><bean:message bundle="softplat_schedular_resource" key="DisplayLogs.taskName"/></td>	
			<td class="main_label_td" align="left">
			<select name="taskNameCon" style="width:150px">
                  	<option value="" <%=taskNameSelectedIndex<0?"selected":"" %>><bean:message bundle="softplat_schedular_resource" key="DisplayLogs.all"/></option>
                  	<% 
                  	if(taskNames != null){
                  		for(int i = 0;i<taskNames.length;i++){
                  	%>
                    <option value="<%=taskNames[i]%>" <%=taskNameSelectedIndex==i?"selected":"" %>><%=taskNames[i]%></option>
                  	<%
                  		}
                  	}
                  	%>                  			
			</select>
			</td>  	
        	<td class="main_label_td" align="right"><bean:message bundle="softplat_schedular_resource" key="DisplayLogs.startTime"/>&nbsp;<bean:message bundle="softplat_schedular_resource" key="DisplayLogs.from"/></td>
        	<td class="main_label_td" align="left"><input type="text" name="fromDate" value="<%=fromDate==null?"":fromDate.trim()%>" class="Wdate" style="width=90px;" onClick="new WdatePicker(this,'%Y-%M-%D',false)" contentEditable="false"/></td>
        	<td class="main_label_td" align="left"><bean:message bundle="softplat_schedular_resource" key="DisplayLogs.to"/></td>
        	<td class="main_label_td" align="left"><input type="text" name="toDate" value="<%=toDate==null?"":toDate.trim()%>" class="Wdate" style="width=90px;" onClick="new WdatePicker(this,'%Y-%M-%D',false)" contentEditable="false"/></td>
        	<td class="main_label_td" align="right"><bean:message bundle="softplat_schedular_resource" key="DisplayLogs.resultFlag"/></td>
        	<td class="main_label_td" align="left"><select name="resultFlagCon">
                  	<option value=""  <%=!"Y".equals(resultFlagCon) && !"N".equals(resultFlagCon)&& !"0".equals(resultFlagCon)?"selected":"" %>><bean:message bundle="softplat_schedular_resource" key="DisplayLogs.all"/></option>  
                  	<option value="0" <%="0".equals(resultFlagCon)?"selected":"" %>><bean:message bundle="softplat_schedular_resource" key="DisplyTasks.task.noneState"/></option>                  	                  
                  	<option value="Y" <%="Y".equals(resultFlagCon)?"selected":"" %>><bean:message bundle="softplat_schedular_resource" key="DisplayLogs.resultSuccess"/></option>
                  	<option value="N" <%="N".equals(resultFlagCon)?"selected":"" %>><bean:message bundle="softplat_schedular_resource" key="DisplayLogs.resultFail"/></option>
                  </select>          	
        	</td>
        	<td class="main_label_td" align="right"><bean:message bundle="softplat_schedular_resource" key="DisplayLogs.level"/></td>
        	<td class="main_label_td" align="left"><select name="levelCon">
                  	<option value=""  <%=!"U".equals(levelCon) && !"N".equals(levelCon)?"selected":"" %>><bean:message bundle="softplat_schedular_resource" key="DisplayLogs.all"/></option>                    
                  	<option value="U" <%="U".equals(levelCon)?"selected":"" %>><bean:message bundle="softplat_schedular_resource" key="DisplayLogs.level.urgent"/></option>
                  	<option value="N" <%="N".equals(levelCon)?"selected":"" %>><bean:message bundle="softplat_schedular_resource" key="DisplayLogs.level.normal"/></option>
                  </select>          	
        	</td>
        	<td class="main_label_td" align="right"><bean:message bundle="softplat_schedular_resource" key="DisplayLogs.info"/></td>
        	<td class="main_label_td" align="left"><input type="text" size="15" name="infoCon" value="<%=infoCon==null?"":infoCon.trim()%>"></td>        	
        	<td class="button_td" align="right"><input type="button" name="query" value="<bean:message bundle="softplat_schedular_resource" key="Common.query"/>" class="button_normal" onclick="jumpPage('first','<%=currentPage%>','tasks','displaTasks.do?method=displayLogs')"></td>        	
     	</tr>
     </table>
<!-- table header -->
<table id='maintbl' name='maintbl' border="0" cellpadding="0" cellspacing="0" width="100%">
<tr><td width=100%>
<DIV align="center" id='windiv1' name='windiv1' style=" BORDER-RIGHT: gray 0px solid; PADDING-RIGHT: 0px; PADDING-LEFT: 0px; PADDING-BOTTOM: 0px; MARGIN: 0px; OVERFLOW: hidden; BORDER-LEFT: gray 0px solid; PADDING-TOP: 0px; BORDER-BOTTOM: gray 0px solid">
     <table border="1" cellpadding="2" cellspacing="0" bordercolordark="#FFFFFF" bordercolorlight="#848284" bgcolor="#F4F4F4" width="100%" class="NEUDwListTableback">
        <tr>
          <th class="NEUDwListTH" width="20%">
           <font class="NEUDwListDodyFont">
            <bean:message bundle="softplat_schedular_resource" key="DisplayLogs.taskName"/>
            </font>         
          </th>
          <th class="NEUDwListTH" width="6%">
           <font class="NEUDwListDodyFont">
            <bean:message bundle="softplat_schedular_resource" key="DisplayLogs.callMethod"/>
            </font>         
          </th>          
          <th class="NEUDwListTH" width="15%">
           <font class="NEUDwListDodyFont">
            <bean:message bundle="softplat_schedular_resource" key="DisplayLogs.startTime"/>
            </font>
          </th>     
          <th  class="NEUDwListTH" width="15%">
          <font class="NEUDwListDodyFont">
           <bean:message bundle="softplat_schedular_resource" key="DisplayLogs.endTime"/>
             </font>
          </th>
          <th  class="NEUDwListTH" width="6%">
          <font class="NEUDwListDodyFont">
            <bean:message bundle="softplat_schedular_resource" key="DisplayLogs.resultFlag"/>
          </font>
          </th>
          <th  class="NEUDwListTH" width="6%">
          <font class="NEUDwListDodyFont">
            <bean:message bundle="softplat_schedular_resource" key="DisplayLogs.level"/>
          </font>
          </th>
          <th  class="NEUDwListTH" width="32%">
          <font class="NEUDwListDodyFont">
            <bean:message bundle="softplat_schedular_resource" key="DisplayLogs.info"/>
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
          if(data != null && data.size() > 0){
	          for(int i = 0; i < data.size(); i++){
	             HashMap rowItem = (HashMap)data.get(i);
	             String taskName = (String)rowItem.get("TASK_NAME");
	             String callMethod = (String)rowItem.get("CALL_METHOD");
	             //Date startTime = (Date)rowItem.get("START_TIME");
	             //Date endTime = (Date)rowItem.get("END_TIME");
	             Date startTime = EAPDataFormat.toTimestamp(String.valueOf(rowItem.get("START_TIME")));
	             Date endTime = EAPDataFormat.toTimestamp(String.valueOf(rowItem.get("END_TIME")));	             
	             String resultFlag = (String)rowItem.get("RESULT_FLAG");
	             String level = (String)rowItem.get("LOG_LEVEL");
	             String info = (String)rowItem.get("INFO");	             
	             if(i%2 == 1){
         %>
         <tr class="NEUDwListRowBgColor2">            
         <%}else{%>
         <tr class="NEUDwListRowBgColor1">
         <%}%>
          <td class="NEUHTCInput" align="left" width="20%">
          <%=taskName %>
          </td>
          <td class="NEUHTCInput" align="left" width="6%">
          <%="".equals(callMethod)?"&nbsp;":callMethod %>
          </td>          
          <td class="NEUHTCInput" align="center" width="15%">
        	<%=startTime==null?"&nbsp;":dateFormat.format(startTime)%>
          </td>
          <td class="NEUHTCInput" align="center" width="15%">
        	<%=endTime==null?"&nbsp;":dateFormat.format(endTime)%>
          </td>
          <td class="NEUHTCInput" align="center" width="6%">
            <%if("Y".equalsIgnoreCase(resultFlag)){%>
            <bean:message bundle="softplat_schedular_resource" key="DisplayLogs.resultSuccess"/>
            <%}else if("N".equalsIgnoreCase(resultFlag)){%>
            <b><bean:message bundle="softplat_schedular_resource" key="DisplayLogs.resultFail"/></b>         
            <%}else
            	  out.print("&nbsp;");
            %>            
          </td>
          <td class="NEUHTCInput" align="center" width="6%">
            <%if("U".equalsIgnoreCase(level)){%>
            <bean:message bundle="softplat_schedular_resource" key="DisplayLogs.level.urgent"/>
            <%}else if("N".equalsIgnoreCase(level)){%>
            <bean:message bundle="softplat_schedular_resource" key="DisplayLogs.level.normal"/>
            <%}else
            	  out.print("&nbsp;");
            %>            
          </td>   
          <td class="NEUHTCInput" width="32%">
            <%
              if(info == null || "".equals(info.trim())){
            	  out.print("&nbsp;");
              }else if(info.length() < 35){
            	  out.print(info.trim());
              }else{%>
              	  <div style="cursor:pointer;color:blue;" info="<%=info.trim()%>" onclick="viewInfoDetail(this);"><%=info.substring(0,35)+"..."%></div>
              <%}
            %>
          </td> 
        </tr>
        <%	 }      	
           }
         %>     
    </table>
</div></td></tr>   
<!-- navigate  -->
<tr><td width="100%">
   <DIV align="center" id='windiv1' name='windiv1' style=" OVERFLOW-y: scroll;OVERFLOW-x: hidden ; BORDER-RIGHT: gray 0px solid; PADDING-RIGHT: 0px; PADDING-LEFT: 0px; PADDING-BOTTOM: 0px; MARGIN: 0px; OVERFLOW-y: scroll;BORDER-LEFT: gray 0px solid; PADDING-TOP: 0px; BORDER-BOTTOM: gray 0px solid">
    <table cellspacing="0" width="100%">
    	<tr>
    		<td >
    			<table>
         			<tr>
               			<td class="icon2_td"><input type="button" title="<bean:message bundle="softplat_schedular_resource" key="DisplyTasks.taskFirstPage"/>"  id="firstButton"class="NEUDwButton_FirstPage" onclick="jumpPage('first','<%=currentPage%>','tasks','displaTasks.do?method=displayLogs')"/></td>
               			<td class="icon2_td"><input type="button" title="<bean:message bundle="softplat_schedular_resource" key="DisplyTasks.taskPrePage"/>" id="upButton" class="NEUDwButton_PreviousPage" onclick="jumpPage('up','<%=currentPage%>','tasks','displaTasks.do?method=displayLogs')"/></td>
			   			<td style="height: 22px; width: 27px;"><font class="NEUDwListDodyFont"><%=currentPage%>/<%=totalPage %></font></td>
			   			<td class="icon2_td"><input type="button" title="<bean:message bundle="softplat_schedular_resource" key="DisplyTasks.taskNextPage"/>"  id="downButton" class="NEUDwButton_NextPage" onclick="jumpPage('down','<%=currentPage%>','tasks','displaTasks.do?method=displayLogs')"/></td>
			   			<td class="icon2_td"><input type="button" title="<bean:message bundle="softplat_schedular_resource" key="DisplyTasks.taskLastPage"/>" id="lastButton" class="NEUDwButton_LastPage" onclick="jumpPage('last','<%=currentPage%>','tasks','displaTasks.do?method=displayLogs')"/></td>					 
			   			<td style="height: 22px; width: 27px;"><input type="text"  id="pageNo" name="pageNo" value="<%=currentPage%>" style="width:25;ime-mode:disabled"  onkeydown="" onkeypress="" minlength="1" maxlength="9"/> </td>
			   			<td class="icon2_td"><input type="button" title="<bean:message bundle="softplat_schedular_resource" key="DisplyTasks.taskGoPage"/>" id="" class="NEUDwButton_GoPage"  onclick="jumpPage('jump','<%=currentPage%>','tasks','displaTasks.do?method=displayLogs')" /></td>	
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
<script>
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

function viewInfoDetail(obj){
  alert(obj.info);
}
</script>