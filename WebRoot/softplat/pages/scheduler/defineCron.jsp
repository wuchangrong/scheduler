<%@ page language="java" pageEncoding="GBK"%>
<%@ taglib uri="/WEB-INF/taglib/struts-bean.tld" prefix="bean"%>
<html>
<%
   String cronExpress = request.getParameter("cronExpress");
   if(cronExpress == null)
   {
      cronExpress = "";
   }
 %>
<head>
  <title><bean:message bundle="softplat_schedular_resource" key="defineCron.title"/></title>
  <link href="<%=request.getContextPath() %>/softplat/pages/scheduler/datepicker/default/datepicker.css" rel="stylesheet" type="text/css">
  <LINK href="<%=request.getContextPath() %>/softplat/pages/scheduler/css/Style.css" rel="stylesheet"></LINK> 
  <script language="javascript" type="text/javascript" src="<%=request.getContextPath()%>/softplat/pages/scheduler/datepicker/WdatePicker.js"></script>
  <script language="javascript" type="text/javascript" src="<%=request.getContextPath()%>/softplat/pages/scheduler/jsLib/defineCron.js"></script>
  <script language="javascript" type="text/javascript" src="<%=request.getContextPath()%>/softplat/pages/scheduler/jsLib/cronValidate.js"></script>
  <script language="javascript" type="text/javascript" src="<%=request.getContextPath()%>/softplat/pages/scheduler/jsLib/SCHEDULARCONSTANTS.js"></script>
</head>
<body onload="initialCronPage('<%=cronExpress %>')">
<table width="100%">
    <tr>      
      <td width="50%" class="main_label_td">
      <bean:message bundle="softplat_schedular_resource" key="defineCron.startTime"/>
      <input type="text" name="startTime" id="startTime"  class="Wdate" onClick="new WdatePicker(this,'%Y-%M-%D %h:%m:%s',true)" contentEditable="false"/>
     </td>
     <td width="50%" class="main_label_td">
      <bean:message bundle="softplat_schedular_resource" key="defineCron.endTime"/>
      <input type="text" name="endTime" id="endTime"  class="Wdate" onClick="new WdatePicker(this,'%Y-%M-%D %h:%m:%s',true)" contentEditable="false"/>
     </td>
    </tr>
</table>
<table width="100%">
<tr>
  <td width="50%" class="main_label_td"><bean:message bundle="softplat_schedular_resource" key="defineCron.runType"/>
  		<select id="planType" onchange="display()">
			<option value="day" selected><bean:message bundle="softplat_schedular_resource" key="defineCron.runTypeDay"/></option>
			<option value="week"><bean:message bundle="softplat_schedular_resource" key="defineCron.runTypeWeek"/></option>
        	<option value="month"><bean:message bundle="softplat_schedular_resource" key="defineCron.runTypeMonth"/></option>
			<option value="onlyOne"><bean:message bundle="softplat_schedular_resource" key="defineCron.runTypeOnlyOne"/></option>        	
        	<option value="define"><bean:message bundle="softplat_schedular_resource" key="defineCron.runTypeDefine"/></option>
        </select>
  </td>
</tr>
</table>
<div id="div_day" style="display:none;">
      <table>
      <tr>
       <td class="main_label_td">
          <input  type="radio" checked = "true" id="radio_day" name="dayRadio" value="">
       </td>
       <td class="main_label_td">
        <bean:message bundle="softplat_schedular_resource" key="defineCron.everyDay"/>
       </td>
       <td/>
       <td/>
     </tr>
      <tr>
       <td class="main_label_td">
	  <input  type="radio" id="radio_intervalDay" name="dayRadio" value="" >
       </td>
       <td class="main_label_td"><bean:message bundle="softplat_schedular_resource" key="defineCron.intervalDay"/></td>
       <td class="main_label_td">
         <select id="interValDay">
            <option value="1" selected>1</option>
            <option value="2">2</option>
            <option value="3">3</option>
            <option value="4">4</option>
            <option value="5">5</option>
            <option value="6">6</option>
            <option value="7">7</option>
            <option value="8">8</option>
            <option value="9">9</option>
            <option value="10">10</option>
            <option value="11">11</option>
            <option value="12">12</option>
            <option value="13">13</option>
            <option value="14">14</option>
            <option value="15">15</option>
            <option value="16">16</option>
            <option value="17">17</option>
            <option value="18">18</option>
            <option value="19">19</option>
            <option value="20">20</option>
            <option value="21">21</option>
            <option value="22">22</option>
            <option value="23">23</option>
            <option value="24">24</option>
            <option value="25">25</option>
            <option value="26">26</option>
            <option value="27">27</option>
            <option value="28">28</option>
            <option value="29">29</option>
            <option value="30">30</option>
            <option value="31">31</option>
        </select>
         </td>
        <td class="main_label_td">
          <bean:message bundle="softplat_schedular_resource" key="defineCron.runTypeDay"/>
       </td>
     </tr>
   </table>
</div>
<div id="div_week" style="display:none;">
   <table>
      <tr>       
       <td>
          <table>
             <tr>
               <td class="main_label_td"><input type="checkbox" id="day1" name="weekDay" value="2"><bean:message bundle="softplat_schedular_resource" key="defineCron.monday"/></td>
               <td class="main_label_td"><input type="checkbox" id="day2" name="weekDay" value="3"><bean:message bundle="softplat_schedular_resource" key="defineCron.tuesday"/></td>
               <td class="main_label_td"><input type="checkbox" id="day3" name="weekDay" value="4"><bean:message bundle="softplat_schedular_resource" key="defineCron.wednesday"/></td>
               <td class="main_label_td"><input type="checkbox" id="day4" name="weekDay" value="5"><bean:message bundle="softplat_schedular_resource" key="defineCron.thursday"/></td>
               <td class="main_label_td"><input type="checkbox" id="day5" name="weekDay" value="6"><bean:message bundle="softplat_schedular_resource" key="defineCron.friday"/></td>
               <td class="main_label_td"><input type="checkbox" id="day6" name="weekDay" value="7"><bean:message bundle="softplat_schedular_resource" key="defineCron.saturday"/></td>
               <td class="main_label_td"><input type="checkbox" id="day7" name="weekDay" value="1"><bean:message bundle="softplat_schedular_resource" key="defineCron.sunday"/></td>
             </tr>
          </table>
       </td>
     </tr>
   </table>
</div>
<div id="div_month" style="display:none;">
<table>
      <tr>
       <td class="main_label_td">
       	 <input type="radio" name="monthGroup" id="monthDay" checked="true"> 
       </td>
       <td class="main_label_td">
         <bean:message bundle="softplat_schedular_resource" key="defineCron.everyMonth"/>
       </td>
       <td class="main_label_td">
           <select id="monthDayInput" >
            <option value="1" selected>1<bean:message bundle="softplat_schedular_resource" key="defineCron.dayUnit"/></option>
            <option value="2">2<bean:message bundle="softplat_schedular_resource" key="defineCron.dayUnit"/></option>
            <option value="3">3<bean:message bundle="softplat_schedular_resource" key="defineCron.dayUnit"/></option>
            <option value="4">4<bean:message bundle="softplat_schedular_resource" key="defineCron.dayUnit"/></option>
            <option value="5">5<bean:message bundle="softplat_schedular_resource" key="defineCron.dayUnit"/></option>
            <option value="6">6<bean:message bundle="softplat_schedular_resource" key="defineCron.dayUnit"/></option>
            <option value="7">7<bean:message bundle="softplat_schedular_resource" key="defineCron.dayUnit"/></option>
            <option value="8">8<bean:message bundle="softplat_schedular_resource" key="defineCron.dayUnit"/></option>
            <option value="9">9<bean:message bundle="softplat_schedular_resource" key="defineCron.dayUnit"/></option>
            <option value="10">10<bean:message bundle="softplat_schedular_resource" key="defineCron.dayUnit"/></option>
            <option value="11">11<bean:message bundle="softplat_schedular_resource" key="defineCron.dayUnit"/></option>
            <option value="12">12<bean:message bundle="softplat_schedular_resource" key="defineCron.dayUnit"/></option>
            <option value="13">13<bean:message bundle="softplat_schedular_resource" key="defineCron.dayUnit"/></option>
            <option value="14">14<bean:message bundle="softplat_schedular_resource" key="defineCron.dayUnit"/></option>
            <option value="15">15<bean:message bundle="softplat_schedular_resource" key="defineCron.dayUnit"/></option>
            <option value="16">16<bean:message bundle="softplat_schedular_resource" key="defineCron.dayUnit"/></option>
            <option value="17">17<bean:message bundle="softplat_schedular_resource" key="defineCron.dayUnit"/></option>
            <option value="18">18<bean:message bundle="softplat_schedular_resource" key="defineCron.dayUnit"/></option>
            <option value="19">19<bean:message bundle="softplat_schedular_resource" key="defineCron.dayUnit"/></option>
            <option value="20">20<bean:message bundle="softplat_schedular_resource" key="defineCron.dayUnit"/></option>
            <option value="21">21<bean:message bundle="softplat_schedular_resource" key="defineCron.dayUnit"/></option>
            <option value="22">22<bean:message bundle="softplat_schedular_resource" key="defineCron.dayUnit"/></option>
            <option value="23">23<bean:message bundle="softplat_schedular_resource" key="defineCron.dayUnit"/></option>
            <option value="24">24<bean:message bundle="softplat_schedular_resource" key="defineCron.dayUnit"/></option>
            <option value="25">25<bean:message bundle="softplat_schedular_resource" key="defineCron.dayUnit"/></option>
            <option value="26">26<bean:message bundle="softplat_schedular_resource" key="defineCron.dayUnit"/></option>
            <option value="27">27<bean:message bundle="softplat_schedular_resource" key="defineCron.dayUnit"/></option>
            <option value="28">28<bean:message bundle="softplat_schedular_resource" key="defineCron.dayUnit"/></option>
            <option value="29">29<bean:message bundle="softplat_schedular_resource" key="defineCron.dayUnit"/></option>
            <option value="30">30<bean:message bundle="softplat_schedular_resource" key="defineCron.dayUnit"/></option>
            <option value="31">31<bean:message bundle="softplat_schedular_resource" key="defineCron.dayUnit"/></option>
			<option value="L"><bean:message bundle="softplat_schedular_resource" key="defineCron.lastDay"/></option>            
        </select>    
       </td>   
     </tr>
</table>
<table>
  <tr>
     <td class="main_label_td">
		 <input type="radio" name="monthGroup" id="monthWeek">
     </td>
     <td class="main_label_td">
        <select id="weekType" >  
			<option value="1"><bean:message bundle="softplat_schedular_resource" key="defineCron.firstWeek"/></option>
			<option value="2"><bean:message bundle="softplat_schedular_resource" key="defineCron.secondWeek"/></option>
	        <option value="3"><bean:message bundle="softplat_schedular_resource" key="defineCron.thirdWeek"/></option>
			<option value="4"><bean:message bundle="softplat_schedular_resource" key="defineCron.fourthWeek"/></option>
			<option value="L"><bean:message bundle="softplat_schedular_resource" key="defineCron.lastWeek"/></option>
        </select>   
        <bean:message bundle="softplat_schedular_resource" key="defineCron.runTypeWeek"/>  
		<select id="dayType" >  
			<option value="2"><bean:message bundle="softplat_schedular_resource" key="defineCron.monday"/></option>
			<option value="3"><bean:message bundle="softplat_schedular_resource" key="defineCron.tuesday"/></option>
		    <option value="4"><bean:message bundle="softplat_schedular_resource" key="defineCron.wednesday"/></option>
			<option value="5"><bean:message bundle="softplat_schedular_resource" key="defineCron.thursday"/></option>
			<option value="6"><bean:message bundle="softplat_schedular_resource" key="defineCron.friday"/></option>
			<option value="7"><bean:message bundle="softplat_schedular_resource" key="defineCron.saturday"/></option>
			<option value="1"><bean:message bundle="softplat_schedular_resource" key="defineCron.sunday"/></option>
		</select>
     </td>
  </tr>
</table>
<table>
  <tr>
     <td class="main_label_td">
       <bean:message bundle="softplat_schedular_resource" key="defineCron.selectMonth"/>
     </td>
  </tr>
  <tr>   
     <td>
        <table>
           <tr>
             <td class="main_label_td"><input type="checkbox" id="month1" name="month" value="1"><bean:message bundle="softplat_schedular_resource" key="defineCron.January"/></td>
             <td class="main_label_td"><input type="checkbox" id="month2" name="month" value="2"><bean:message bundle="softplat_schedular_resource" key="defineCron.February"/></td>
             <td class="main_label_td"><input type="checkbox" id="month3"name="month" value="3" ><bean:message bundle="softplat_schedular_resource" key="defineCron.March"/></td>
             <td class="main_label_td"><input type="checkbox" id="month4" name="month" value="4"><bean:message bundle="softplat_schedular_resource" key="defineCron.April"/></td>	   
             <td class="main_label_td"><input type="checkbox" id="month5" name="month" value="5"><bean:message bundle="softplat_schedular_resource" key="defineCron.may"/></td>
             <td class="main_label_td"><input type="checkbox" id="month6" name="month" value="6"><bean:message bundle="softplat_schedular_resource" key="defineCron.June"/></td>
      	   </tr>
      	   <tr>             
             <td class="main_label_td"><input type="checkbox" id="month7" name="month" value="7"><bean:message bundle="softplat_schedular_resource" key="defineCron.July"/></td>
             <td class="main_label_td"><input type="checkbox" id="month8" name="month" value="8"><bean:message bundle="softplat_schedular_resource" key="defineCron.August"/></td>
             <td class="main_label_td"><input type="checkbox" id="month9" name="month" value="9"><bean:message bundle="softplat_schedular_resource" key="defineCron.September"/></td>
             <td class="main_label_td"><input type="checkbox" id="month10" name="month" value="10"><bean:message bundle="softplat_schedular_resource" key="defineCron.October"/></td>
             <td class="main_label_td"><input type="checkbox" id="month11" name="month" value="11"><bean:message bundle="softplat_schedular_resource" key="defineCron.November"/></td>
             <td class="main_label_td"><input type="checkbox" id="month12" name="month" value="12"><bean:message bundle="softplat_schedular_resource" key="defineCron.December"/></td>	
           </tr>
        </table>        
     </td>
  </tr>
</table>
</div>
<!-- 时间点 -->
<div id="div_time" style="display:none">
	<table>
		<tr>
		  <td class="main_label_td"><bean:message bundle="softplat_schedular_resource" key="defineCron.timePoint"/></td>
          <td ><DIV class="WdateDiv" style="border:0;width:80px;">
				<DIV id=dpTime style="DISPLAY: block; FLOAT: left">
				<TABLE cellSpacing=0 cellPadding=0 border=0>
				<TBODY>
				<TR>
					<TD rowSpan=2><INPUT class=tB maxLength=2 name="startTimeHH"><INPUT class=tm readOnly value=:><INPUT class=tE maxLength=2 name="startTimeMM"><INPUT class=tm readOnly value=:><INPUT class=tE style="background:gray;" readOnly value="0" maxLength=2 name="startTimeSS"></TD>
					<TD><BUTTON id=dpTimeUp name="up1"></BUTTON></TD>
				</TR>
				<TR>
					<TD><BUTTON id=dpTimeDown name="down1"></BUTTON></TD>
				</TR>
				</TBODY>
				</TABLE>
				</DIV>
				</DIV></td>
		     <td class="main_label_td">&nbsp;<bean:message bundle="softplat_schedular_resource" key="defineCron.to"/>&nbsp;</td>
		     <td><DIV class="WdateDiv" style="border:0;width:80px;">
				<DIV id=dpTime style="DISPLAY: block; FLOAT: left">
				<TABLE cellSpacing=0 cellPadding=0 border=0>
				<TBODY>
				<TR>
					<TD rowSpan=2><INPUT class=tB maxLength=2 name="startTimeToHH"><INPUT class=tm readOnly value=:><INPUT class=tE maxLength=2 name="startTimeToMM"><INPUT class=tm readOnly value=:><INPUT class=tE style="background:gray;" readOnly maxLength=2 value="0" name="startTimeToSS"></TD>
					<TD><BUTTON id=dpTimeUp name="up2"></BUTTON></TD>
				</TR>
				<TR>
					<TD><BUTTON id=dpTimeDown name="down2"></BUTTON></TD>
				</TR>
				</TBODY>
				</TABLE>
				</DIV>
				</DIV></td>
			  <td class="main_label_td" style="color:blue;"><bean:message bundle="softplat_schedular_resource" key="defineCron.timePointDesc"/></td>
          </tr>
	</table>
	<table>
		<tr>
     	  	<td  class="main_label_td">
     	  		<input type="radio" name="timePointSplit" id="timePointSplitNO" checked = "true"> 
				<bean:message bundle="softplat_schedular_resource" key="defineCron.noInterval"/>
				<input type="radio" name="timePointSplit" id="timePointSplitHour"> 
				<bean:message bundle="softplat_schedular_resource" key="defineCron.interval"/>
				<select id="otherHour" name="otherHour" id = "otherHour">
					<option value="1">1<bean:message bundle="softplat_schedular_resource" key="defineCron.hourUnit"/></option>
					<option value="2">2<bean:message bundle="softplat_schedular_resource" key="defineCron.hourUnit"/></option>
					<option value="3">3<bean:message bundle="softplat_schedular_resource" key="defineCron.hourUnit"/></option>
					<option value="4">4<bean:message bundle="softplat_schedular_resource" key="defineCron.hourUnit"/></option>
					<option value="5">5<bean:message bundle="softplat_schedular_resource" key="defineCron.hourUnit"/></option>
					<option value="6">6<bean:message bundle="softplat_schedular_resource" key="defineCron.hourUnit"/></option>
					<option value="7">7<bean:message bundle="softplat_schedular_resource" key="defineCron.hourUnit"/></option>
					<option value="8">8<bean:message bundle="softplat_schedular_resource" key="defineCron.hourUnit"/></option>
					<option value="9">9<bean:message bundle="softplat_schedular_resource" key="defineCron.hourUnit"/></option>
					<option value="10">10<bean:message bundle="softplat_schedular_resource" key="defineCron.hourUnit"/></option>
					<option value="11">11<bean:message bundle="softplat_schedular_resource" key="defineCron.hourUnit"/></option>
					<option value="12">12<bean:message bundle="softplat_schedular_resource" key="defineCron.hourUnit"/></option>
					<option value="13">13<bean:message bundle="softplat_schedular_resource" key="defineCron.hourUnit"/></option>
					<option value="14">14<bean:message bundle="softplat_schedular_resource" key="defineCron.hourUnit"/></option>
					<option value="15">15<bean:message bundle="softplat_schedular_resource" key="defineCron.hourUnit"/></option>
					<option value="16">16<bean:message bundle="softplat_schedular_resource" key="defineCron.hourUnit"/></option>
					<option value="17">17<bean:message bundle="softplat_schedular_resource" key="defineCron.hourUnit"/></option>
					<option value="18">18<bean:message bundle="softplat_schedular_resource" key="defineCron.hourUnit"/></option>
					<option value="19">19<bean:message bundle="softplat_schedular_resource" key="defineCron.hourUnit"/></option>
					<option value="20">20<bean:message bundle="softplat_schedular_resource" key="defineCron.hourUnit"/></option>
					<option value="21">21<bean:message bundle="softplat_schedular_resource" key="defineCron.hourUnit"/></option>
					<option value="22">22<bean:message bundle="softplat_schedular_resource" key="defineCron.hourUnit"/></option>
					<option value="23">23<bean:message bundle="softplat_schedular_resource" key="defineCron.hourUnit"/></option>
				</select>
			  <input type="radio" name="timePointSplit" id="timePointSplitMinute"> 
			  <bean:message bundle="softplat_schedular_resource" key="defineCron.interval"/>
	           <select id="otherMinute" name="otherMinute" id="otherMinute">  
					<option value="1">1<bean:message bundle="softplat_schedular_resource" key="defineCron.minuteUnit"/></option>
					<option value="2">2<bean:message bundle="softplat_schedular_resource" key="defineCron.minuteUnit"/></option>
					<option value="3">3<bean:message bundle="softplat_schedular_resource" key="defineCron.minuteUnit"/></option>
					<option value="4">4<bean:message bundle="softplat_schedular_resource" key="defineCron.minuteUnit"/></option>
					<option value="5">5<bean:message bundle="softplat_schedular_resource" key="defineCron.minuteUnit"/></option>
					<option value="6">6<bean:message bundle="softplat_schedular_resource" key="defineCron.minuteUnit"/></option>
					<option value="7">7<bean:message bundle="softplat_schedular_resource" key="defineCron.minuteUnit"/></option>
					<option value="8">8<bean:message bundle="softplat_schedular_resource" key="defineCron.minuteUnit"/></option>
					<option value="9">9<bean:message bundle="softplat_schedular_resource" key="defineCron.minuteUnit"/></option>
					<option value="10">10<bean:message bundle="softplat_schedular_resource" key="defineCron.minuteUnit"/></option>
					<option value="11">11<bean:message bundle="softplat_schedular_resource" key="defineCron.minuteUnit"/></option>
					<option value="12">12<bean:message bundle="softplat_schedular_resource" key="defineCron.minuteUnit"/></option>
					<option value="13">13<bean:message bundle="softplat_schedular_resource" key="defineCron.minuteUnit"/></option>
					<option value="14">14<bean:message bundle="softplat_schedular_resource" key="defineCron.minuteUnit"/></option>
					<option value="15">15<bean:message bundle="softplat_schedular_resource" key="defineCron.minuteUnit"/></option>
					<option value="16">16<bean:message bundle="softplat_schedular_resource" key="defineCron.minuteUnit"/></option>
					<option value="17">17<bean:message bundle="softplat_schedular_resource" key="defineCron.minuteUnit"/></option>
					<option value="18">18<bean:message bundle="softplat_schedular_resource" key="defineCron.minuteUnit"/></option>
					<option value="19">19<bean:message bundle="softplat_schedular_resource" key="defineCron.minuteUnit"/></option>
					<option value="20">20<bean:message bundle="softplat_schedular_resource" key="defineCron.minuteUnit"/></option>
					<option value="21">21<bean:message bundle="softplat_schedular_resource" key="defineCron.minuteUnit"/></option>
					<option value="22">22<bean:message bundle="softplat_schedular_resource" key="defineCron.minuteUnit"/></option>
					<option value="23">23<bean:message bundle="softplat_schedular_resource" key="defineCron.minuteUnit"/></option>
					<option value="24">24<bean:message bundle="softplat_schedular_resource" key="defineCron.minuteUnit"/></option>
					<option value="25">25<bean:message bundle="softplat_schedular_resource" key="defineCron.minuteUnit"/></option>
					<option value="26">26<bean:message bundle="softplat_schedular_resource" key="defineCron.minuteUnit"/></option>
					<option value="27">27<bean:message bundle="softplat_schedular_resource" key="defineCron.minuteUnit"/></option>
					<option value="28">28<bean:message bundle="softplat_schedular_resource" key="defineCron.minuteUnit"/></option>
					<option value="29">29<bean:message bundle="softplat_schedular_resource" key="defineCron.minuteUnit"/></option>
					<option value="30">30<bean:message bundle="softplat_schedular_resource" key="defineCron.minuteUnit"/></option>
					<option value="31">31<bean:message bundle="softplat_schedular_resource" key="defineCron.minuteUnit"/></option>
					<option value="32">32<bean:message bundle="softplat_schedular_resource" key="defineCron.minuteUnit"/></option>
					<option value="33">33<bean:message bundle="softplat_schedular_resource" key="defineCron.minuteUnit"/></option>
					<option value="34">34<bean:message bundle="softplat_schedular_resource" key="defineCron.minuteUnit"/></option>
					<option value="35">35<bean:message bundle="softplat_schedular_resource" key="defineCron.minuteUnit"/></option>
					<option value="36">36<bean:message bundle="softplat_schedular_resource" key="defineCron.minuteUnit"/></option>
					<option value="37">37<bean:message bundle="softplat_schedular_resource" key="defineCron.minuteUnit"/></option>
					<option value="38">38<bean:message bundle="softplat_schedular_resource" key="defineCron.minuteUnit"/></option>
					<option value="39">39<bean:message bundle="softplat_schedular_resource" key="defineCron.minuteUnit"/></option>
					<option value="40">40<bean:message bundle="softplat_schedular_resource" key="defineCron.minuteUnit"/></option>
					<option value="41">41<bean:message bundle="softplat_schedular_resource" key="defineCron.minuteUnit"/></option>
					<option value="42">42<bean:message bundle="softplat_schedular_resource" key="defineCron.minuteUnit"/></option>
					<option value="43">43<bean:message bundle="softplat_schedular_resource" key="defineCron.minuteUnit"/></option>
					<option value="44">44<bean:message bundle="softplat_schedular_resource" key="defineCron.minuteUnit"/></option>
					<option value="45">45<bean:message bundle="softplat_schedular_resource" key="defineCron.minuteUnit"/></option>
					<option value="46">46<bean:message bundle="softplat_schedular_resource" key="defineCron.minuteUnit"/></option>
					<option value="47">47<bean:message bundle="softplat_schedular_resource" key="defineCron.minuteUnit"/></option>
					<option value="48">48<bean:message bundle="softplat_schedular_resource" key="defineCron.minuteUnit"/></option>
					<option value="49">49<bean:message bundle="softplat_schedular_resource" key="defineCron.minuteUnit"/></option>
					<option value="50">50<bean:message bundle="softplat_schedular_resource" key="defineCron.minuteUnit"/></option>
					<option value="51">51<bean:message bundle="softplat_schedular_resource" key="defineCron.minuteUnit"/></option>
					<option value="52">52<bean:message bundle="softplat_schedular_resource" key="defineCron.minuteUnit"/></option>
					<option value="53">53<bean:message bundle="softplat_schedular_resource" key="defineCron.minuteUnit"/></option>
					<option value="54">54<bean:message bundle="softplat_schedular_resource" key="defineCron.minuteUnit"/></option>
					<option value="55">55<bean:message bundle="softplat_schedular_resource" key="defineCron.minuteUnit"/></option>
					<option value="56">56<bean:message bundle="softplat_schedular_resource" key="defineCron.minuteUnit"/></option>
					<option value="57">57<bean:message bundle="softplat_schedular_resource" key="defineCron.minuteUnit"/></option>
					<option value="58">58<bean:message bundle="softplat_schedular_resource" key="defineCron.minuteUnit"/></option>
					<option value="59">59<bean:message bundle="softplat_schedular_resource" key="defineCron.minuteUnit"/></option>
				</select>
			</td>
     	</tr>
	</table>
</div>
<!-- 自定义cron表达式 -->
<div id="div_define" style="display:none;">
	<table>
	<tr><td class="main_label_td">
		<bean:message bundle="softplat_schedular_resource" key="defineCron.cronExpression"/>
		<input type="text" size="30" id="cronExpression" name="cronExpression">
	</td>
	<td class="main_label_td"><div style="cursor:pointer;color:blue;" onclick="alert(cronExpressionDesc);"><bean:message bundle="softplat_schedular_resource" key="defineCron.cronExpDesc"/></div></td>
	</tr>
	</table>
</div>
<br>
<table width="100%">
<tr>
<td align="right">
<input align="right" type="button"  id="finishButton" name="finishButton" value="<bean:message bundle="softplat_schedular_resource" key="Common.confirm"/>" onclick="finish()" class="button_normal">
<input align="right" type="button"  id="finishButton" name="calcelButton" value="<bean:message bundle="softplat_schedular_resource" key="Common.cancel"/>" onclick="window.close()" class="button_normal">
</td>
</tr>
<tr>
<td align="left" class="main_label_td" style="color:blue;"><bean:message bundle="softplat_schedular_resource" key="defineCron.desc"/></td>
</tr>
</table>
</body>
</html>
<script>
var tempFocus1 = null;
var tempFocus2 = null;
function timeOnFocus1(){
	this.select();
	tempFocus1=this;
};
function timeOnFocus2(){
	this.select();
	tempFocus2=this;
};
function upClick1(){
	if(tempFocus1==null){
		tempFocus1=document.all("startTimeHH");
	}
	if((tempFocus1==document.all("startTimeHH")&&parseInt(tempFocus1.value)<23)||(tempFocus1!=document.all("startTimeHH")&&parseInt(tempFocus1.value)<59)){
		tempFocus1.value=parseInt(tempFocus1.value)+1;
	}
	tempFocus1.focus();
};
function downClick1(){
	if(tempFocus1==null){
		tempFocus1=document.all("startTimeHH");
	}
	if(parseInt(tempFocus1.value)>0){
		tempFocus1.value=parseInt(tempFocus1.value)-1;
	}
	tempFocus1.focus();
};
function upClick2(){
	if(tempFocus2==null){
		tempFocus2=document.all("startTimeToHH");
	}
	if((tempFocus2==document.all("startTimeToHH")&&parseInt(tempFocus2.value)<23)||(tempFocus2!=document.all("startTimeToHH")&&parseInt(tempFocus2.value)<59)){
		tempFocus2.value=parseInt(tempFocus2.value)+1;
	}
	tempFocus2.focus();
};
function downClick2(){
	if(tempFocus2==null){
		tempFocus2=document.all("startTimeToHH");
	}
	if(parseInt(tempFocus2.value)>0){
		tempFocus2.value=parseInt(tempFocus2.value)-1;
	}
	tempFocus2.focus();
};
document.all("startTimeHH").onfocus = timeOnFocus1;
document.all("startTimeMM").onfocus = timeOnFocus1;
document.all("startTimeToHH").onfocus = timeOnFocus2;
document.all("startTimeToMM").onfocus = timeOnFocus2;
document.all("up1").onclick = upClick1;
document.all("down1").onclick = downClick1;
document.all("up2").onclick = upClick2;
document.all("down2").onclick = downClick2;
</script>