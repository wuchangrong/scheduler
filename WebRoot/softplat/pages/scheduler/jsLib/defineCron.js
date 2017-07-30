function initialCronPage(cronExpress) {
	if(cronExpress != null&&cronExpress !="")
    {
    	 //更新页面的初始化
         updateInitial(cronExpress);
    }
    else {	
   		 //设置当前的开始时间 
         initialStartTime(new Date());
		 var dayDiv = document.getElementById("div_day");
		 var weekDiv = document.getElementById("div_week");
		 var monthDiv = document.getElementById("div_month");
		 var timeDiv = document.getElementById("div_time");
		 var defineDiv = document.getElementById("div_define");
         //显示每天的div
         dayDiv.style.display="block";
         weekDiv.style.display="none";
	     monthDiv.style.display="none";
	   	 timeDiv.style.display="block";		         
	   	 defineDiv.style.display="none";		 
     }
     //对于秒段，全部设置为0
     document.getElementById("startTimeSS").value = "0";
}
/* ---------------------------------------------------------
 * FUNCTION : initialStartTime()
 * ---------------------------------------------------------
 * DESCRIPTION : 设置开始时间的默认值
 * PARAMETERS :
 * retun :
 * ---------------------------------------------------------
 */
function initialStartTime(date) {
    var df=new SimpleDateFormat();
    df.applyPattern("yyyy-MM-dd hh:mm:ss");
    var startTime = df.format(date);
    document.getElementById("startTime").value = startTime;
    //
    document.getElementById("startTimeHH").value = "*";
    document.getElementById("startTimeMM").value = "0";
    document.getElementById("startTimeToHH").value = "";
    document.getElementById("startTimeToMM").value = "";
}

/* ---------------------------------------------------------
 * FUNCTION : setSelectOption()
 * ---------------------------------------------------------
 * DESCRIPTION : 设置开始时间的默认值
 * PARAMETERS :
 * selectId : 下拉域的Id
 * optionValue : 下拉项的值
 * retun :
 * ---------------------------------------------------------
 */
function setSelectOption(selectId,optionValue) {
	var obj = document.getElementById(selectId);
    if(obj != null) {
    	var options = obj.childNodes;
        for(i = 0 ; i < options.length; i++) {
        	if(options[i].value == optionValue) {
            	options[i].selected = true;
                break;
            }
       }
       if(selectId == "planType"){
           display();
       }
   }
}
/* ---------------------------------------------------------
 * FUNCTION : display()
 * ---------------------------------------------------------
 * DESCRIPTION : 显示选中的计划类型
 * PARAMETERS :
 * retun :
 * ---------------------------------------------------------
 */
function display() {     
	var type = document.getElementById("planType").value;
    var dayDiv = document.getElementById("div_day");
    var weekDiv = document.getElementById("div_week");
    var monthDiv = document.getElementById("div_month");
    var timeDiv = document.getElementById("div_time");
    var defineDiv = document.getElementById("div_define");
    if(type == ""||type == "onlyOne"){           
           //隐藏所有的Div        
           dayDiv.style.display="none";
           weekDiv.style.display="none";
	   	   monthDiv.style.display="none";
	   	   timeDiv.style.display="none";	 
	   	   defineDiv.style.display="none";	  	   
    }else if(type == "day"){
           //显示每天的div
           dayDiv.style.display="block";
           weekDiv.style.display="none";
	       monthDiv.style.display="none";
	   	   timeDiv.style.display="block";		         
	   	   defineDiv.style.display="none";	 	         
    }else if(type == "week"){
           //显示每周的div
		   dayDiv.style.display="none";
           weekDiv.style.display="block";
	       monthDiv.style.display="none";
	   	   timeDiv.style.display="block";		        
	   	   defineDiv.style.display="none";	 	        
    }else if(type == "month"){
           //显示每月的div     
	   	    dayDiv.style.display="none";
            weekDiv.style.display="none";
	   	    monthDiv.style.display="block";
	   	    timeDiv.style.display="block";	
            defineDiv.style.display="none";	 
    }else if(type == "define"){
             //显示自定义的div     
	   		dayDiv.style.display="none";
            weekDiv.style.display="none";
	   	    monthDiv.style.display="none";
	   	    timeDiv.style.display="none";		   	    
            defineDiv.style.display="block";	     	
    }
}
/**********************get time *************************************/
//time的格式为 yyyy-MM-dd hh:mm:ss
function getHour(time)
{
       if(time != null&&time !="")
        {
	        time = time.substring(time.indexOf(" ")+1,time.length);
	       
	        if(time.indexOf("0") == 0&&time.length > 1)
	        {
	           return time = time.substring(1,time.indexOf(":"));
	        }
	      
	        return time.substring(0,time.indexOf(":"));
        }   
}
function getMinute(time)
{
       if(time != null&&time !="")
        {
       		time = time.substring(time.indexOf(":")+1,time.lastIndexOf(":"));
       		if(time.indexOf("0") == 0&&time.length > 1)
	        {
	           return time = time.substring(1,time.length);
	        }
       		return time;
       	}
}
function getSecond(time)
{
       if(time != null&&time !="")
        {
       		time = time.substring(time.lastIndexOf(":")+1,time.length);
       		if(time.indexOf("0") == 0&&time.length > 1)
	        {
	           return time = time.substring(1,time.length);
	        }
       		return time;
       	}
     }
     
function getYear(time)
{
       if(time != null&&time !="")
        {
       		time = time.substring(0,time.indexOf("-"));
       		return time;
       	}
}
function getMonth(time)
     {
       if(time != null&&time !="")
        {
       		time = time.substring(time.indexOf("-")+1,time.lastIndexOf("-"));
       		if(time.indexOf("0") == 0&&time.length > 1)
       		{
       		   time = time.substring(1,time.length);
       		}
       		return time;
       	} 
}     
function getDay(time)
{
	     if(time != null&&time !="")
	     {
	       		time = time.substring(time.lastIndexOf("-")+1,time.indexOf(" "));
	       		return time;
	      } 
}
//更新时间点
function updateTimePoint(sSecond,sMinute,sHour){
	var startTimeMM = document.getElementById("startTimeMM");
	var startTimeToMM = document.getElementById("startTimeToMM");
	var startTimeHH = document.getElementById("startTimeHH");
	var startTimeToHH = document.getElementById("startTimeToHH");
	
	var timePointSplitNO = document.getElementById("timePointSplitNO");
	var timePointSplitHour = document.getElementById("timePointSplitHour");
	var otherHour = document.getElementById("otherHour");	
	var timePointSplitMinute = document.getElementById("timePointSplitMinute");	
	var otherMinute = document.getElementById("otherMinute");
	
	timePointSplitNO.checked = true;
	//second
	//document.getElementById("startTimeSS").value = sSecond;
	//minute	
	var index1 = sMinute.indexOf("-");
	var index2 = sMinute.indexOf("/");	
	if(index1 > 0){
	  if(index2>0){
	  	  //?-?/?
		  startTimeMM.value = sMinute.substring(0,index1);
		  startTimeToMM.value = sMinute.substring(index1+1,index2);
		  timePointSplitMinute.checked = true;
		  otherMinute.value = sMinute.substring(index2+1);  	  
	  }else{
	  	  //?-?
		  startTimeMM.value = sMinute.substring(0,index1);
		  startTimeToMM.value = sMinute.substring(index1+1);		  	  	 
	  }
	}else{
	  if(index2 > 0){
	  	  // ?/?
		  startTimeMM.value = sMinute.substring(0,index2);
		  startTimeToMM.value = "";
		  timePointSplitMinute.checked = true;
		  otherMinute.value = sMinute.substring(index2+1);
	  }else{
	  	  // ?
		  startTimeMM.value = sMinute;
		  startTimeToMM.value = "";
	  }
	}
	//hour
	var index1 = sHour.indexOf("-");
	var index2 = sHour.indexOf("/");	
	if(index1 > 0){
	  if(index2>0){
	  	  //?-?/?
		  startTimeHH.value = sHour.substring(0,index1);
		  startTimeToHH.value = sHour.substring(index1+1,index2);
		  timePointSplitHour.checked = true;
		  otherHour.value = sHour.substring(index2+1);  	  
	  }else{
	  	  //?-?
		  startTimeHH.value = sHour.substring(0,index1);
		  startTimeToHH.value = sHour.substring(index1+1); 	 
	  }
	}else{
	  if(index2 > 0){
	  	  // ?/?
		  startTimeHH.value = sHour.substring(0,index2);
		  startTimeToHH.value = "";
		  timePointSplitHour.checked = true;
		  otherHour.value = sHour.substring(index2+1);
	  }else{
	  	  // ?
		  startTimeHH.value = sHour;
		  startTimeToHH.value = "";
	  }
	}	
}

function updateInitial(cronExpress){
      var startTime = null;
      var endTime = null;
      var cron = null;
      
      //cronExpress = "startTime:10000;cron:* * * *　* *;endTime:"
      var strArray = cronExpress.split(";");
      startTime = strArray[0].substring(strArray[0].indexOf(":")+1,strArray[0].length);
       endTime = strArray[2].substring(strArray[2].indexOf(":")+1,strArray[2].length);
      cron = strArray[1].substring(strArray[1].indexOf(":")+1,strArray[1].length);
      
      var df=new SimpleDateFormat();
      df.applyPattern("yyyy-MM-dd hh:mm:ss");   
      
      if(startTime != null&& startTime != ""){
        //设置开始时间
        var startDate = new Date(Number(startTime));  
        var startTimeInput = document.getElementById("startTime");
         var startTimeValue = df.format(startDate);
        startTimeInput.value = startTimeValue;
        startDate = null;        
      }
      
      if(endTime != null&& endTime != ""){
        //设置结束时间
        var endDate = new Date(Number(endTime));
        var endTimeInput = document.getElementById("endTime");
        var endTimeValue = df.format(endDate);
        endTimeInput.value = endTimeValue;
        endDate = null;
      }       
      df = null;      
      
      //判断表达式类型
      var cronArray = cron.split(" ");
      if(cronArray.length == 6 || cronArray[6].trim() == "" || cronArray[6] == null){
      	cronArray[6] = "*";
      }
	  var typeAry = [],reg = /^[\d]+$/;
	  for(var i=0;i<cronArray.length;i++) {
			if(!reg.test(cronArray[i])) {
				typeAry.push(i);
			}
	  }
	  if(typeAry.length == 1 && cronArray[5]=="?") {
	  		//判断是否为仅执行一次
			setSelectOption("planType","onlyOne");
	  }
	  else 	if(cronArray[4] == '*'&& cronArray[6] == '*'&& cronArray[5] == '?'&&cronArray[3].match(/[\*]|[\*\/[\d]+]/)) {
			//判断是否为以天为周期
	        setSelectOption("planType","day");
	        if(cronArray[3] == "*")
	        {
	        	document.getElementById("radio_day").checked = true;
	        }
	        else
	        {
	        	//间隔多少天
	        	document.getElementById("radio_intervalDay").checked = true;
	        	var day = cronArray[3];
	        	day = day.substring(day.indexOf("/")+1,day.length);
	        	setSelectOption("interValDay",day);
	        }
	        updateTimePoint(cronArray[0],cronArray[1],cronArray[2]);
	  }
	  else 	if(cronArray[4] == '*'&& cronArray[6] == '*' && !cronArray[5].match(/[#|L|?]/) && unpack(cronArray[5])) {
	  	    //判断是否为以周为周期
	        setSelectOption("planType","week");
	        var weekDays = cronArray[5];
	        var weekDayArray = weekDays.split(",");
	        for(i = 0; i < weekDayArray.length; i++)
	        {
	          var weekDayCheckBox = document.getElementsByName("weekDay");
	          for(j = 0;j < weekDayCheckBox.length;j++)
	          {
	             if((!weekDayCheckBox[j].checked)&&weekDayArray[i] == weekDayCheckBox[j].value)
	             {
	                weekDayCheckBox[j].checked = true;
	             }
	          }
	        }
	        updateTimePoint(cronArray[0],cronArray[1],cronArray[2]);	        
	  }
	  else if(cronArray[6] == '*') {
			//判断是否以月为周期
	       setSelectOption("planType","month");
	        var monthes = cronArray[4];
	        var monthArray = monthes.split(",");
	        for(i = 0; i < monthArray.length; i++)
	        {
	          //设置选中的月份
	          var monthCheckBox = document.getElementsByName("month");
	           for(i = 0; i < monthArray.length; i++)
	           {
		          var monthCheckBox = document.getElementsByName("month");
		          for(j = 0;j < monthCheckBox.length;j++)
		          {
		             if((!(monthCheckBox[j].checked))&&monthArray[i] == monthCheckBox[j].value)
		             {
		                monthCheckBox[j].checked = true;
						break;
		             }
		          }
	            }
	        }
	       if(cronArray[5] == "?")
	       {
	          //设置每月多少号
	          document.getElementById("monthDay").checked = true;
	          document.getElementById("monthDayInput").value = cronArray[3];
	       }
	       else
	       { 
	            
	            //设置周数
	            document.getElementById("monthWeek").checked = true;
	            if(cronArray[5].indexOf("L")>0)
	            {
	            	var dayValue = cronArray[5].substring(0,cronArray[5].indexOf("L"));
	              	var weekValue = cronArray[5].substring(cronArray[5].indexOf("L"),cronArray[5].length);
	               	setSelectOption("weekType",weekValue);
		            setSelectOption("dayType",dayValue) ;
	            }
	            else
	            {
		            var weekValues = cronArray[5].split(">");
		            //set week order
		            setSelectOption("weekType",weekValues[1]);
		            //set day order
		            setSelectOption("dayType",weekValues[0]); 
	            }     
	       }
	       updateTimePoint(cronArray[0],cronArray[1],cronArray[2]);
	  }else{
	        //自定义
	  		setSelectOption("planType","define");
	  }
	  //所有情况都把cron表达式都写上
	  document.all("cronExpression").value = cron;
}
   
function finish(){
	 //起始、终止时间
     var startTime = document.getElementById("startTime").value;
     var endTime = document.getElementById("endTime").value;
     if(startTime == ""){         
       alert(SCHEDULARCONSTANTS.alert_noSetStartTime);
       return;         
     }
     var sYear = getYear(startTime);
     var sMonth = getMonth(startTime);
     var sDay = getDay(startTime);
     var sHour = getHour(startTime);
     var sMinute = getMinute(startTime);
     var sSecond = getSecond(startTime);
     
     var startDate = new Date(sYear,sMonth-1,sDay,sHour,sMinute,sSecond);   
     var endDate = null;
     if(endTime != ""){
          var endHour = getHour(endTime);  
          var endMinute = getMinute(endTime); 
          var endSecond = getSecond(endTime); 
          var endYear = getYear(endTime);
          var endMonth = getMonth(endTime);
          var endDay = getDay(endTime); 
          endDate = new Date(endYear,endMonth-1,endDay,endHour,endMinute,endSecond);
     }  
     if(endDate != null)
     {
         if(startDate.getTime() > endDate.getTime())
	      {
	        alert(SCHEDULARCONSTANTS.alert_startTimeGreaterEndTime); 
	        return;
	      }
     }
     //计划类型     
      var planType = document.getElementById("planType").value;
      //处理时间点,用于day，month，week
      if(planType == "day" || planType == "week" || planType == "month"){
	      var cronTime = "";
	      var startTimeHour = document.getElementById("startTimeHH").value;
	      var startTimeMinute = document.getElementById("startTimeMM").value;
	      var startTimeSecond = document.getElementById("startTimeSS").value;
	      var startTimeToHour = document.getElementById("startTimeToHH").value;
	      var startTimeToMinute = document.getElementById("startTimeToMM").value;
	      //0--no 1-time 2-hour
	      var intervalType = "0";
	      var intervalValue ="";
	      if(document.getElementById("timePointSplitHour").checked){
	        intervalType = "2";
	        intervalValue = document.getElementById("otherHour").value;
	      }else if(document.getElementById("timePointSplitMinute").checked){
	        intervalType = "1";
	        intervalValue = document.getElementById("otherMinute").value;
	      }
	      
	      if(startTimeHour == "" || startTimeHour == null || startTimeMinute == "" || startTimeMinute == null || startTimeSecond == "" || startTimeSecond == null){
	         alert(SCHEDULARCONSTANTS.alert_noSetFirstTimePoint);
	         return;
	      }
	      //cron-second
	      cronTime = ""+startTimeSecond;
	      //cron-minute
	      cronTime += " "+startTimeMinute;
	      if(startTimeToMinute != null && startTimeToMinute != ""){
	      	 var tempM1 = parseInt(startTimeMinute,10);
	      	 var tempM2 = parseInt(startTimeToMinute,10);
	         if(tempM1>tempM2){
	            alert(SCHEDULARCONSTANTS.alert_TimePointGraterError);
	            return;
	         }else if(tempM1<tempM2){
	         	cronTime += "-"+startTimeToMinute;
	         }
	      }
	      if(intervalType == "1"){
	      	 cronTime += "/"+intervalValue;
	      }
	      //cron-hour
	      cronTime += " "+startTimeHour;
	      if(startTimeToHour != null && startTimeToHour != ""){
	      	 var tempH1 = parseInt(startTimeHour,10);
	      	 var tempH2 = parseInt(startTimeToHour,10);
	      	 if(tempH1>tempH2){
		         alert(SCHEDULARCONSTANTS.alert_TimePointGraterError);
		         return;
	      	 }else if(tempH1<tempH2){
	         	 cronTime += "-"+startTimeToHour;      	 
	      	 }
	      }
	      if(intervalType == "2"){
	      	 cronTime += "/"+intervalValue;
	      }
      }
      //cron-main
      if(planType == "onlyOne"){
      	//该类时分秒用时间
        cron = ""+sSecond+" "+sMinute+" "+sHour+" "+sDay+" "+sMonth+" "+"?"+" "+sYear; 
      }       
      else if(planType == "day"){
         var dayArray = document.getElementsByName("dayRadio");
         var checkRadio = null;
         for( var i = 0; i < dayArray.length; i++)
         {
            if(dayArray[i].checked)
            {
               checkRadio = dayArray[i];
               break;
            }
         }      
         if(checkRadio.id == "radio_day")
         {       
            cron = cronTime +" * * ?";     
         }
         else
         { 
            var interValDay = document.getElementById("interValDay").value
            cron = cronTime +" */"+interValDay+" * ?"; 
         }        
      }   
      else if(planType == "week"){ 
         //获取用户选那些天weekDay
         var weekDayArr = document.getElementsByName("weekDay");
         var selectedDays = "";
         for(i = 0; i < weekDayArr.length; i++)
         {
            if(weekDayArr[i].checked)
            {
               selectedDays +=weekDayArr[i].value+",";
            }   
         }
         if(selectedDays == "")
         {
             alert(SCHEDULARCONSTANTS.alert_noSelectDay);
             return;
         }
         selectedDays = selectedDays.substring(0,selectedDays.lastIndexOf(","));    
         cron = cronTime +" ? *"+" "+selectedDays;
      }
      else if(planType == "month"){
         var dayArray = document.getElementsByName("monthGroup");
         var checkRadio = null;
         for( var i = 0; i < dayArray.length; i++)
         {
            if(dayArray[i].checked)
            {
               checkRadio = dayArray[i];
               break;
            }
         }
         var monthArray = document.getElementsByName("month");
         var selectMonth = "";
         for(i = 0; i < monthArray.length; i++ )
         {
            if(monthArray[i].checked)
            {
               selectMonth += monthArray[i].value+",";
            }
         }
         //wudongqing modify
         if(selectMonth == ""){
           	selectMonth = "*";
         }else{
         	selectMonth = selectMonth.substring(0,selectMonth.lastIndexOf(","));  
         }
         if(checkRadio.id == 'monthDay')
         {
             var day = document.getElementById("monthDayInput").value;
             cron = cronTime +" "+day+" "+selectMonth+" ?";           
         }
         else
         {
           var weekOrder = document.getElementById("weekType").value;
           var dayType = document.getElementById("dayType").value;
           if(weekOrder == 'L')
           {
              cron = cronTime +" ?"+" "+selectMonth+" "+dayType+weekOrder;
           }
           else
           {
           		cron = cronTime +" ?"+" "+selectMonth+" "+dayType+">"+weekOrder;
           }
         }    
      }else if(planType == "define"){
      	cron = document.all("cronExpression").value;
      	if(!isValidExpression(cron)){
      		alert(SCHEDULARCONSTANTS.alert_cronExpressionError);
      		return;
      	}
      }else{
        alert(SCHEDULARCONSTANTS.alert_noTriggerType);
        return;
      }
    window.dialogArguments.document.getElementById('cronPress').value = "startTime:"+startDate.getTime()+";cron:"+cron+";endTime:"+(endDate == null?"":endDate.getTime());
    window.close();  
 }
 
//处理cron表达式
function unpack(str) {
	var ret = [];
	var tempAry = str.split(",");
	for (var i = 0; i < tempAry.length; i++) {
		var innerAry = tempAry[i].split("-");
		if (innerAry.length == 1) {
			ret.push(tempAry[i]);
		} else if (innerAry.length == 2) {
			for (var j = innerAry[0]; j <= innerAry[1]; j++) {
				ret.push(j);
			}
		} else {
			throw UnsupportCronException;
		}
	}
	return ret.sort();
}

/**
 * SimpleDateFormat prototype 
 */
 function SimpleDateFormat(){
     
 }
 SimpleDateFormat.prototype.applyPattern=function(pattern){
     this.pattern=pattern;
 };
 SimpleDateFormat.prototype.constructor=SimpleDateFormat;
 SimpleDateFormat.zh_cn_month2=["01","02","03","04","05","06","07","08","09","10","11","12"];
 SimpleDateFormat.zh_cn_month3=["\u4e00\u6708","\u4e8c\u6708","\u4e09\u6708","\u56db\u6708","\u4e94\u6708","\u516d\u6708","\u4e03\u6708","\u516b\u6708","\u4e5d\u6708","\u5341\u6708","\u5341\u4e00\u6708","\u5341\u4e8c\u6708",];
 SimpleDateFormat.zh_cn_month4=["\u4e00\u6708","\u4e8c\u6708","\u4e09\u6708","\u56db\u6708","\u4e94\u6708","\u516d\u6708","\u4e03\u6708","\u516b\u6708","\u4e5d\u6708","\u5341\u6708","\u5341\u4e00\u6708","\u5341\u4e8c\u6708",];
 SimpleDateFormat.us_en_month4=["Janu","Febr","Marc","Apri","May","Juhn","July","Augu","Sept","Octo","Nove","Dece"];
 SimpleDateFormat.us_en_month3=["Jan","Feb","Mar","Apr","May","Juh","Jul","Aug","Sep","Oct","Nov","Dec"];
 SimpleDateFormat.us_en_month2=["01","02","03","04","05","06","07","08","09","10","11","12"];
 SimpleDateFormat.zh_cn_week=["\u661f\u671f\u65e5","\u661f\u671f\u4e00","\u661f\u671f\u4e8c","\u661f\u671f\u4e09","\u661f\u671f\u56db","\u661f\u671f\u4e94","\u661f\u671f\u516d"];
 SimpleDateFormat.zh_cn_am="\u4e0b\u5348";
 SimpleDateFormat.zh_cn_pm="\u4e0a\u5348"; 
 SimpleDateFormat.language=(navigator.userLanguage==undefined?navigator.language:navigator.userLanguage).replace("-","_").toLowerCase();
 SimpleDateFormat.prototype.format=function(date){
     var year4=date.getFullYear();
     var year2=year4.toString().substring(2);
     var pattern=this.pattern;
     pattern=pattern.replace(/yyyy/,year4);
     pattern=pattern.replace(/yy/,year2);
     var month=date.getMonth();
     pattern=pattern.replace(/MMMM/,eval("SimpleDateFormat."+SimpleDateFormat.language+"_month4[month]"));
     pattern=pattern.replace(/MMM/,eval("SimpleDateFormat."+SimpleDateFormat.language+"_month3[month]"));
     pattern=pattern.replace(/MM/,eval("SimpleDateFormat."+SimpleDateFormat.language+"_month2[month]"));
     var dayOfMonth=date.getDate();
     var dayOfMonth2=dayOfMonth;
     var dayOfMonthLength=dayOfMonth.toString().length;
     if(dayOfMonthLength==1){
         dayOfMonth2="0"+dayOfMonth;	
     }
     pattern=pattern.replace(/dd/,dayOfMonth2);
     pattern=pattern.replace(/d/,dayOfMonth);
     var hours=date.getHours();
     var hours2=hours;
     var hoursLength=hours.toString().length;
     if(hoursLength==1){
         hours2="0"+hours;	
     }
     pattern=pattern.replace(/HH/,hours2);
     pattern=pattern.replace(/H/,hours);
     var minutes=date.getMinutes();
     var minutes2=minutes;
     var minutesLength=minutes.toString().length;
     if(minutesLength==1){
         minutes2="0"+minutes;	
     }
     pattern=pattern.replace(/mm/,minutes2);
     pattern=pattern.replace(/m/,minutes);
     var seconds=date.getSeconds();
     var seconds2=seconds;
     var secondsLength=seconds.toString().length;
     if(secondsLength==1){
         seconds2="0"+seconds;	
     }
     pattern=pattern.replace(/ss/,seconds2);
     pattern=pattern.replace(/s/,seconds);
     var milliSeconds=date.getMilliseconds();
     pattern=pattern.replace(/S+/,milliSeconds);
     var day=date.getDay();
     pattern=pattern.replace(/E+/,eval("SimpleDateFormat."+SimpleDateFormat.language+"_week[day]"));
     if(hours>12){
         pattern=pattern.replace(/a+/,eval("SimpleDateFormat."+SimpleDateFormat.language+"_am"));	
     }else{
         pattern=pattern.replace(/a+/,eval("SimpleDateFormat."+SimpleDateFormat.language+"_pm"));  
     }
     var kHours=hours;
     if(kHours==0){
         kHours=24;	
     }
     var kHours2=kHours;
     var kHoursLength=kHours.toString().length;
     if(kHoursLength==1){
         kHours2="0"+kHours;	
     }
     pattern=pattern.replace(/kk/,kHours2);
     pattern=pattern.replace(/k/,kHours);
     var KHours=hours;
     /*
     if(hours>11){
         KHours=hours-12;	
     }
     */
     var KHours2=KHours;
     var KHoursLength=KHours.toString().length;
     if(KHoursLength==1){
         KHours2="0"+KHours;	
     }
     pattern=pattern.replace(/KK/,KHours2);
     pattern=pattern.replace(/K/,KHours);
     var hHours=KHours;
     if(hHours==0){
         hHours=12;	
     }
     var hHours2=hHours;
     var hHoursLength=hHours.toString().length;
     if(KHoursLength==1){
         hHours2="0"+hHours;	
     }
     pattern=pattern.replace(/hh/,hHours2);
     pattern=pattern.replace(/h/,hHours);
     return pattern;
 };




