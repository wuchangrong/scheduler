<%@ page contentType="text/html;" pageEncoding="GBK"%>
<%@ page import = "com.universe.softplat.scheduler.util.ProjectConfigHelper" %>
<%@ taglib uri="/WEB-INF/taglib/struts-bean.tld" prefix="bean" %>
<%
request.setCharacterEncoding(ProjectConfigHelper.PROJECT_CODINGKIND);
%>
<%
 response.setContentType("text/html;charset="+ProjectConfigHelper.PROJECT_CODINGKIND);
%>
<html>

<head>
<meta http-equiv="Content-Language" content="zh-cn">
<meta name="GENERATOR" content="Microsoft FrontPage 6.0">
<meta name="ProgId" content="FrontPage.Editor.Document">
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>top</title>
<base target="contents">
<link href="<%=request.getContextPath()%>/softplat/pages/sample/style/UI.css" rel="stylesheet" type="text/css">

<script language="javascript"> 
  function tab_click(oEvent,flag){
  	var	event =oEvent || window.event;  	
  	var target = event.srcElement || event.target;
  	
  	if(target == null) return false;
  	if(target.tagName != "A") return false;
  	var tabs= document.all("tr_tab_parent").childNodes;
  	for(var i=0; i<tabs.length; i++){
  		var tab = tabs[i];
  		if(flag == tab.id){
	  		tab.className = "tab1";
  		}else{
  			tab.className = "tab2";
  		}
  	}
  }
  
  function tab_change(flag){
  	var tabs= document.all("tr_tab_parent").childNodes;
  	for(var i=0; i<tabs.length; i++){
  		var tab = tabs[i];
  		if(flag == tab.id){
	  		tab.className = "tab1";
  		}else{
  			tab.className = "tab2";
  		}
  	}
  }
  
  function logout(){
  	bootMenu.action = "Scheduler-CheckLogin.do";
  	bootMenu.submit();
  }
</script>
</head>
<body class="body">
<form name="bootMenu" method="post">
<table width="100%" height="67" border="0" cellpadding="0" cellspacing="0" class="head_bg1">
  <tr>
    <td>
    	<table width="100%" height="67" border="0" cellpadding="0" cellspacing="0" class="table_base">
     		<tr>
        		<td width="30%">&nbsp;</td>
        		<td valign="bottom">
        		<table border="0" cellpadding="0" cellspacing="0" class="table_base">
          			<tr id="tr_tab_parent">
            			<td id="td_scheduler" align="center" valign="middle" class="tab1"><a href="<%=request.getContextPath()%>/displaTasks.do?method=displayTasks" target="pagearea" class="link_tab_14_bl" onclick="tab_click(event,'td_scheduler')"><bean:message bundle="softplat_schedular_resource" key="sample.menu.schedulerTitle"/></a></td>
            			<td id="td_logs" align="center" valign="middle" class="tab2"><a href="<%=request.getContextPath()%>/displaTasks.do?method=displayLogs&resultFlagCon=N" target="pagearea" class="link_tab_14_bl" onclick="tab_click(event,'td_logs')"><bean:message bundle="softplat_schedular_resource" key="DisplayLogs.title"/></a></td>
          			</tr>
        		</table>
        		</td>
        		<td class="head_bg3">
      			<a href="<%=request.getContextPath()%>/Scheduler-CheckLogin.do?method=logout" target="_self"><img src="<%=request.getContextPath()%>/softplat/pages/sample/style/images/icon/exit.gif" alt="<bean:message bundle="softplat_schedular_resource" key="sample.menu.logout"/>" onclick="" class="icon" border="0" /></a></td>
      		</tr>
    	</table>
    </td>
  </tr>
</table>

</form>
</body>
</html>