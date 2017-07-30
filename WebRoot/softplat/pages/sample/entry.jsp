<%@ page contentType="text/html;" pageEncoding="GBK"%>
<%@ page import = "com.universe.softplat.scheduler.util.ProjectConfigHelper" %>
<%@ taglib uri="/WEB-INF/taglib/struts-bean.tld" prefix="bean"%>
<%
request.setCharacterEncoding(ProjectConfigHelper.PROJECT_CODINGKIND);
response.setContentType("text/html;charset="+ProjectConfigHelper.PROJECT_CODINGKIND);
%>
<html>
<head>
<title><bean:message bundle="softplat_schedular_resource" key="sample.entry.title"/></title>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
</head>
<frameset rows="67,*" frameborder="NO" border="0" framespacing="0">
  <frame name="rootmenu" scrolling="NO" noresize src="Scheduler-RootMenu.do"/>  
  <frameset name="Child" cols="*" frameborder="NO" border="0" framespacing="0" rows="*">
    <frame name="pagearea" scrolling="NO" noresize src="displaTasks.do?method=displayTasks"/>
  </frameset>
</frameset>
<noframes>
<body bgcolor="#FFFFFF" text="#000000">
</body>
</noframes>
</html>
