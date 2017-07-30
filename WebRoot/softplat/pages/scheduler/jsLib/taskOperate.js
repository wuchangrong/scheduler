//新增/修改/删除
function work(method) {
	if (method=="delete"){
		if (!window.confirm(SCHEDULARCONSTANTS.schedulerTask_delete_confirm)) {
       		return;
     	}
		if (!window.confirm(SCHEDULARCONSTANTS.schedulerTask_delete_confirm_again)) {
       		return;
     	}     	
	}
	//
	showLoading(true);
	//	
	tasksForm.action = "Scheduler-SchedulerOperator.do?method=" + method;
  	tasksForm.submit();
}

//task operate:start/pause/runOnce-异步执行
function operateTask(method,requestPath,currentPage){
	//
	showLoading(true);
	//	
	setTimeout(function(){
		operateTask1(method,requestPath,currentPage);
	},100);
}

function operateTask1(method,requestPath,currentPage){
	var taskIds = getTasks();
    if(taskIds == "") {
    	alert("\u8fd8\u6ca1\u9009\u62e9\u4efb\u52a1");
        return;        
    }
    var post = "taskIds="+taskIds;
	post = encodeURI(post);
	post = encodeURI(post);
    
  	req = new ActiveXObject("Microsoft.XMLHTTP");
    req.open("POST", "Scheduler-SchedulerOperator.do?method="+method, false);
    req.setrequestheader("content-length",post.length); 
    req.setrequestheader("content-type","application/x-www-form-urlencoded");
    req.send(post);
	
	var v = req.responseText;
	//
	showLoading(false);
	//
	if (v=="1"){
		if(method == "startTask"){
       		alert("\u542f\u52a8\u4efb\u52a1\u6210\u529f");
       		reFreshPage(currentPage); 
		}else if(method == "pauseTask"){
		    alert("\u6682\u505c\u4efb\u52a1\u6210\u529f");
       		reFreshPage(currentPage); 
		}else if(method == "runOnceTask"){			
		    alert("\u6267\u884C\u6210\u529F");
		}
	}else{
		if(v == null || trim(v) == "")
			alert('\u6267\u884C\u9519\u8BEF');
		else
			alert('\u6267\u884C\u9519\u8BEF:'+v);
	}
}
//取得已选中的任务
function getTasks() {
	var o = document.getElementsByTagName("input");	
    var ids = "";   	
    for(i = 0; i < o.length; i++) {
    	var obj = o[i];
      	if(obj.type == "checkbox"&&obj.checked) { 
      		ids = obj.id+","+ids;
      	}	   
    }
    return ids;    	           
} 
//刷新页面
function reFreshPage(page) {
	document.forms[0].action = "displaTasks.do?method=displayTasks";
	document.forms[0].target = "";
	document.forms[0].pageNo.value =page;
	document.forms[0].submit();
}
//查看日志
function viewLog(){
	var taskIds = document.getElementsByName("taskId");
	if(taskIds == null || taskIds.length == 0)
		return;
	var targetTaskId = null;
	for(var i = 0;i<taskIds.length;i++){
	  if(taskIds[i].checked){
	    targetTaskId = taskIds[i];
	    break;
	  }
	}
	if(targetTaskId == null)
		return;
	//
	showLoading(true);
	//	
	var taskName = targetTaskId.parentElement.parentElement.cells[1].innerText;
	tasksForm.action = "displaTasks.do?method=displayLogs&pageSize=50";
	document.getElementById("taskNameCon").value = taskName;	
  	tasksForm.submit();
  	//切换menu
  	this.parent.frames[0].tab_change("td_logs");
}
//保存任务
function conform() {
	if (taskForm.taskName.value == "") {
		alert(SCHEDULARCONSTANTS.JSP_TaskPage_conform_taskName);
		return;
	} else if (taskForm.cron.value == "") {
		alert(SCHEDULARCONSTANTS.JSP_TaskPage_conform_runningTime);
		return;
	} else if ((taskForm.scheduleType.value == "" )){
		alert(SCHEDULARCONSTANTS.JSP_TASKPage_noJobSelected);
		return;
	}
	//任务名检查
	if(taskForm.operType.value=="new" && !checkName() || taskForm.operType.value=="edit" && ("taskGroup#"+trim(taskForm.taskName.value) != taskForm.taskId.value) && !checkName()){
		alert(SCHEDULARCONSTANTS.schedulerTask_hasSameName);
		return;
	}

	var obj = taskForm.autoOrManual;
  	taskForm.runType.value = obj.options[obj.selectedIndex].value;
  	
  	obj = taskForm.taskPriority;
  	taskForm.priority.value = obj.options[obj.selectedIndex].value;
  	
  	obj = taskForm.scheduleType;
  	taskForm.schType.value = obj.options[obj.selectedIndex].value;
  	isOk();	
}
//检查任务是否重名
function checkName() {
	var post = "name="+trim(taskForm.taskName.value)+"&taskId="+taskForm.taskId.value;
	post = encodeURI(post);
	post = encodeURI(post);
  	req = new ActiveXObject("Microsoft.XMLHTTP");   
    req.open("POST", "Scheduler-SchedulerOperator.do?method=checkName", false);
    req.setrequestheader("content-length",post.length); 
    req.setrequestheader("content-type","application/x-www-form-urlencoded");
    req.send(post);
	var v = req.responseText;
	if (v=="1"){
		return true;
	}else{
		return false;
	}
}
//返回任务列表页面
function cloze() {
	taskForm.action = "displaTasks.do?method=displayTasks";
  	taskForm.submit();
}

function trim(s){
    return s.replace(/(^[\s|　]*)|([\s|　]*$)/g, "");
}

function showLoading(isShow){
	var loading = document.getElementById("unieap-loading");
	if(!loading){
		loading = document.createElement("DIV");
		loading.setAttribute("id","unieap-loading");
		loading.style.cssText = "position:absolute;top:0px;left:0px;width:100%;height:100%;z-Index:10001;background-color:#fff;filter: alpha(opacity=50);overflow:hidden;display:none";
		var p = document.createElement("P");
		p.style.cssText="position:absolute;left:50%;top:50%";
		var img = document.createElement("DIV");
		img.style.cssText = "+position:relative;text-align:center;top:-50%;left:-50%;width:110px;height:40px; padding:2px;background-color: #c3daf9;border:1px solid #6593cf;z-Index:10002;";
		var text = document.createElement("DIV");
		text.style.cssText = "height:100%;text-align:center;padding-top:9px;background: #eee;border:1px solid #a3bad9;color:#333; font:normal 12px tahoma, arial, helvetica, sans-serif;cursor:wait;";
		text.innerHTML = "<b>正在执行......</b>";
		img.appendChild(text);
		p.appendChild(img);
		loading.appendChild(p);
		document.body.appendChild(loading);
	}
	if(isShow==false){
		loading.style.display = "none";
	}
	else{
		loading.style.display = "block";
	}
};