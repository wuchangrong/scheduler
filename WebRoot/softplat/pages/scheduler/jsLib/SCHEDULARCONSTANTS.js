var SCHEDULARCONSTANTS ={
  alert_pageNoIsNotNull : "ҳ�Ų���Ϊ�գ�",
  alert_pageNoIsNotLessZero : "ҳ�治��С��0��",
  alert_noSetStartTime : "δ���ÿ�ʼʱ�䣡",
  alert_noSetEndTime : "δ���ý���ʱ�䣡",
  alert_noTriggerType : "δ���õ����������ͣ�",
  alert_noSelectDay : "δ����ÿ�ܵ����ڣ�",
  alert_startTimeGreaterEndTime : "��ʼʱ�����ô��󣺿�ʼʱ����ڽ���ʱ�䣡",
  alert_cronExpressionError : "cron���ʽ����",
  alert_noSetFirstTimePoint : "δ����ʱ��㣡",
  alert_TimePointGraterError: "��һ��ʱ����ʱ�������ܳ���ǰһ��ʱ������Ӧ��",
  JSP_TaskManage_select_alert:"��ѡ������",
  JSP_TaskPage_stateImgAlt_run:"����",
  JSP_TaskPage_stateImgAlt_stop:"ֹͣ",
  JSP_TaskPage_addCon_title:"�����趨��",
  JSP_TaskPage_addCon_alert:"��ȡ����ʧ�ܣ�",
  JSP_TaskPage_conform_taskName:"δ��д�������ƣ�",
  JSP_TaskPage_conform_runningTime:"δ��������ִ��ʱ�䣡",
  JSP_TaskPage_isOk_errorTime:"ʱ�����������ִ�У�",
  JSP_TASKPage_noJobSelected:"δѡ��Ҫ���ȵ�����",
  schedulerTask_delete_confirm:"�Ƿ�ɾ����ѡ������",
  schedulerTask_delete_confirm_again:"�ٴ�ȷ���Ƿ�ɾ����ѡ������",
  schedulerTask_hasSameName:"����ͬ�����������������������ƣ�"  
};

var cronExpressionDesc = "cron���ʽ��Ϊ7�����롢�֡�ʱ���ա��¡��ܡ��꣬���г������⣬����������Ϊ�գ�";
cronExpressionDesc += "\n\n����˵����";
cronExpressionDesc += "\n  �룺ȡֵ��Χ 0-59 ������ţ�, - * /";
cronExpressionDesc += "\n  �֣�ȡֵ��Χ 0-59 ������ţ�, - * /";
cronExpressionDesc += "\n  ʱ��ȡֵ��Χ 0-23 ������ţ�, - * /";
cronExpressionDesc += "\n  �գ�ȡֵ��Χ 1-31 ������ţ�, - * ? / L W C";
cronExpressionDesc += "\n  �£�ȡֵ��Χ 1-12��JAN-DEC   ������ţ�, - * /";
cronExpressionDesc += "\n  �ܣ�ȡֵ��Χ 1-7 �� SUN-SAT  ������ţ�, - * ? / L C #";
cronExpressionDesc += "\n  �꣺ȡֵ��Χ �� �� 1970-2099 ������ţ�, - * /";
cronExpressionDesc += "\n\n�����������˵����";
cronExpressionDesc += "\n  * ��ǰ�����кϷ�ֵ��";
cronExpressionDesc += "\n  ? �����պ����򣬱�ʾ��ָ������";
cronExpressionDesc += "\n  , ����ָ��һ��ֵ�б��磺1,3,5��";
cronExpressionDesc += "\n  / ���ڱ�ʾ������ϵ���磺5/2��";
cronExpressionDesc += "\n  - ����ָ��һ����Χ���磺5-10��";
cronExpressionDesc += "\n  L ��ʾ��ǰ�����һ���Ϸ�ֵ��";
cronExpressionDesc += "\n  W �����ڡ��ա����У���ʾƽ��(��һ������)����15W����ʾ��15�������һ��ƽ�գ�";
cronExpressionDesc += "\n\nʾ����";
cronExpressionDesc += "\n  0 0/5 23 * * ?   ÿ��23:00��23:55�е�ÿ5���Ӵ���";
cronExpressionDesc += "\n  0 0-30/2 5 * * ? ÿ��5:00��5:30�е�ÿ2���Ӵ���";
cronExpressionDesc += "\n  0 0 3,10 * * ?   ÿ��3�㡢10�㴥��";
cronExpressionDesc += "\n  0 15 10 L * ?    ÿ�����һ���10:15����";

var verifyExpressionDesc = "�ж����ʽ��Ҫ˵�����£�";
verifyExpressionDesc += "\n  a) �� �� ���ͣ��ַ�����˫����,��������������";
verifyExpressionDesc += "\n  b) �����������+ - *  / == > >= < <= != &&(��) ||(��) !(��)";
verifyExpressionDesc += "\n  c) ������$��ͷ,���ú���:$CONTAINS(String,String)(�ַ�������)��$STARTSWITH(String,String)(�ַ�����ʼ)��$ENDSWITH(String,String)(�ַ�����β)";
verifyExpressionDesc += "\n  d) �˴��жϱ��ʽ�Ľ������Ϊ��������,R1,R2...�ֱ�����ؽ��ֵ";
verifyExpressionDesc += "\n  e) ʾ����(R1 == \"0\") && $CONTAINS(R2,\"success\"),��ʾ���1Ϊ\"0\"�ҽ��2����\"success\",�������ִ�гɹ�";
