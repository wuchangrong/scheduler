var SCHEDULARCONSTANTS ={
  alert_pageNoIsNotNull : "页号不能为空！",
  alert_pageNoIsNotLessZero : "页面不能小于0！",
  alert_noSetStartTime : "未设置开始时间！",
  alert_noSetEndTime : "未设置结束时间！",
  alert_noTriggerType : "未设置调度周期类型！",
  alert_noSelectDay : "未设置每周的日期！",
  alert_startTimeGreaterEndTime : "开始时间设置错误：开始时间大于结束时间！",
  alert_cronExpressionError : "cron表达式错误！",
  alert_noSetFirstTimePoint : "未设置时间点！",
  alert_TimePointGraterError: "后一个时间点的时、分域不能超过前一个时间点的相应域！",
  JSP_TaskManage_select_alert:"请选择任务！",
  JSP_TaskPage_stateImgAlt_run:"运行",
  JSP_TaskPage_stateImgAlt_stop:"停止",
  JSP_TaskPage_addCon_title:"参数设定：",
  JSP_TaskPage_addCon_alert:"读取参数失败！",
  JSP_TaskPage_conform_taskName:"未填写任务名称！",
  JSP_TaskPage_conform_runningTime:"未设置任务执行时间！",
  JSP_TaskPage_isOk_errorTime:"时间错误，任务不能执行！",
  JSP_TASKPage_noJobSelected:"未选择要调度的任务！",
  schedulerTask_delete_confirm:"是否删除所选的任务？",
  schedulerTask_delete_confirm_again:"再次确认是否删除所选的任务？",
  schedulerTask_hasSameName:"存在同名任务，请重新设置任务名称！"  
};

var cronExpressionDesc = "cron表达式分为7个域：秒、分、时、日、月、周、年，其中除年域外，其他都不能为空！";
cronExpressionDesc += "\n\n各域说明：";
cronExpressionDesc += "\n  秒：取值范围 0-59 特殊符号：, - * /";
cronExpressionDesc += "\n  分：取值范围 0-59 特殊符号：, - * /";
cronExpressionDesc += "\n  时：取值范围 0-23 特殊符号：, - * /";
cronExpressionDesc += "\n  日：取值范围 1-31 特殊符号：, - * ? / L W C";
cronExpressionDesc += "\n  月：取值范围 1-12或JAN-DEC   特殊符号：, - * /";
cronExpressionDesc += "\n  周：取值范围 1-7 或 SUN-SAT  特殊符号：, - * ? / L C #";
cronExpressionDesc += "\n  年：取值范围 空 或 1970-2099 特殊符号：, - * /";
cronExpressionDesc += "\n\n常用特殊符号说明：";
cronExpressionDesc += "\n  * 当前域所有合法值；";
cronExpressionDesc += "\n  ? 用于日和周域，表示不指定该域；";
cronExpressionDesc += "\n  , 用于指定一组值列表，如：1,3,5；";
cronExpressionDesc += "\n  / 用于表示递增关系，如：5/2；";
cronExpressionDesc += "\n  - 用于指定一个范围，如：5-10；";
cronExpressionDesc += "\n  L 表示当前域最后一个合法值；";
cronExpressionDesc += "\n  W 仅用于“日”域中，表示平日(周一至周五)。如15W，表示离15号最近的一个平日；";
cronExpressionDesc += "\n\n示例：";
cronExpressionDesc += "\n  0 0/5 23 * * ?   每天23:00至23:55中的每5分钟触发";
cronExpressionDesc += "\n  0 0-30/2 5 * * ? 每天5:00至5:30中的每2分钟触发";
cronExpressionDesc += "\n  0 0 3,10 * * ?   每天3点、10点触发";
cronExpressionDesc += "\n  0 15 10 L * ?    每月最后一天的10:15触发";

var verifyExpressionDesc = "判定表达式简要说明如下：";
verifyExpressionDesc += "\n  a) 数 据 类型：字符型用双引号,日期型用中括号";
verifyExpressionDesc += "\n  b) 常用运算符：+ - *  / == > >= < <= != &&(与) ||(或) !(非)";
verifyExpressionDesc += "\n  c) 函数用$开头,常用函数:$CONTAINS(String,String)(字符串包含)、$STARTSWITH(String,String)(字符串起始)、$ENDSWITH(String,String)(字符串结尾)";
verifyExpressionDesc += "\n  d) 此处判断表达式的结果必须为布尔类型,R1,R2...分别代表返回结果值";
verifyExpressionDesc += "\n  e) 示例：(R1 == \"0\") && $CONTAINS(R2,\"success\"),表示结果1为\"0\"且结果2包含\"success\",该任务才执行成功";
