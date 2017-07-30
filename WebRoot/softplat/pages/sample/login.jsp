<%@ page contentType="text/html;" pageEncoding="GBK"%>
<%@ page import = "com.universe.softplat.scheduler.util.ProjectConfigHelper" %>
<%
 request.setCharacterEncoding(ProjectConfigHelper.PROJECT_CODINGKIND);
 response.setContentType("text/html;charset="+ProjectConfigHelper.PROJECT_CODINGKIND);
 String result = (String)request.getAttribute("result");
%>
<%@ taglib uri="/WEB-INF/taglib/struts-bean.tld" prefix="bean"%>
<html>
<head>
<title><bean:message bundle="softplat_schedular_resource" key="sample.login.title"/></title>

<LINK href="<%=request.getContextPath()%>/softplat/pages/sample/style/style.css" rel="stylesheet"></LINK>
<style type="text/css">
<!--
body {
	background-color: #d7d7d7;
}
-->
</style>
<script language="javascript">
<!--
if (top.location != self.location)top.location=self.location;

function setClass(eleName,clsName) {
	document.all(eleName).className = clsName;
}

function onsubmit(){
	var j1 = document.getElementById("j_username");
	var j2 = document.getElementById("j_password");
	if(trim(j1.value) == ""){
		alert("用户名不能为空");
		j1.focus();
		return;
	}
	if(trim(j2.value) == ""){
		alert("密码不能为空");
		j2.focus();
		return;
	}
	document.all("logonform").submit();
	document.all("loginButton").disabled=true;
}

function trim(s){
  return s.replace( /^\s*/, "").replace( /\s*$/, "");
}

function enterToTab(oEvent){
	var event = window.event || oEvent;
	var target = event.srcElement || event.target;
	
  	if(event.keyCode == 13){
		var j1 = document.getElementById("j_username");
		var j2 = document.getElementById("j_password");
		if(j1 == target){
			if(trim(j1.value) == ""){
				alert("用户名不能为空");
				return;
			}
			else{
				j2.focus();
				return;
			}				
		}else if(j2 == target){
			if(trim(j1.value) == ""){
				alert("用户名不能为空");
				j1.focus();
				return;
			}
			if(trim(j2.value) == ""){
				alert("密码不能为空");
				return;
			}
			onsubmit();	
		}	
    }
}

function pageInit(){
  var result = "<%=result%>";
  if(result == "loginFail")
  	alert("用户名/密码错误");
  logonform.j_username.focus();
}
//-->
</script>
</head>

<body onload="pageInit();">
<form name="logonform" method="post" action="<%=request.getContextPath()%>/Scheduler-CheckLogin.do?method=login">
<table width="100%" height="100%" border="0" cellpadding="0" cellspacing="0">
  <tr>
    <td width="100%"  align="center" valign="top" bordercolor="#1A50B8"><table width="200" height="101" border="0" cellpadding="0" cellspacing="0">
      <tr>
        <td>&nbsp;</td>
      </tr>
    </table>
      <table width="609" border="0" cellpadding="0" cellspacing="0">
      <tr>
        <td width="23">&nbsp;</td>
        <td align="left" valign="top"><table width="554" height="324" border="0" cellpadding="0" cellspacing="0" class="login_bg">
          <tr>
            <td align="center" valign="bottom">
              <table width="100%" border="0" cellpadding="0" cellspacing="0">
              <tr>
                <td height="160" align="right" valign="bottom">
                  <table width="407"  border="0" cellpadding="0" cellspacing="0">
                  <tr>
                	<td width="50" height="28" align="left" class="Text_12_bk_B"><bean:message bundle="softplat_schedular_resource" key="sample.login.username"/></td>
                	<td height="28" align="left"><input tabIndex="1" id="j_username" name="j_username" type="text" value="" class="input_login" size="20" onkeydown="enterToTab(event)"/></td>
                	<td width="60" rowspan="2"><a name="loginButton" tabIndex="4" href="javascript:onsubmit()" target="_self"><img src="<%=request.getContextPath()%>/softplat/pages/sample/style/images/button/button_login.png" width="52" height="52" border="0" /></a></td>
                	<td width="142" rowspan="2" align="right">&nbsp;</td>
              	  </tr>
              	  <tr>
                	<td width="50" height="28" align="left" class="Text_12_bk_B"><bean:message bundle="softplat_schedular_resource" key="sample.login.password"/></td>
                	<td height="28" align="left"><input tabIndex="2" id="j_password" name="j_password" type="password" value="" class="input_login" size="20" onkeydown="enterToTab(event)"/></td>
              	  </tr>
              	  <tr>
                	<td height="55" colspan="4" valign="top">&nbsp;</td>
              	  </tr>
                  </table>
                </td>
              </tr>
              <tr>
                <td height="50" align="center" class="Text_12_wt"><span class="Text_wt"><bean:message bundle="softplat_schedular_resource" key="sample.login.copyright"/> Copyright&copy;2015 Neusoft All rights reserved</span></td>
              </tr>
            </table></td>
          </tr>
        </table></td>
      </tr>
    </table>
    </td>
  </tr>
</table>
</form>
</body>
</html>