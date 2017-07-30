package com.universe.softplat.scheduler.impl;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.Map;

import org.quartz.CronTrigger;
import org.quartz.JobDetail;
import org.quartz.Scheduler;
import org.quartz.SchedulerException;

import com.universe.softplat.scheduler.Task;
import com.universe.softplat.scheduler.TaskManager;
import com.universe.softplat.scheduler.job.ExtendJob;
import com.universe.softplat.scheduler.util.CronUtil;

public class TaskManagerImpl implements TaskManager{
	
	private Scheduler scheduler;
    
	public TaskManagerImpl(Scheduler scheduler){
        this.scheduler = scheduler;
    }

    public Task[] getAllTasks() throws Exception{
        ArrayList taskList = new ArrayList();
        Task tasks[];
        String schedulerGroup[] = scheduler.getJobGroupNames();
        if(schedulerGroup != null && schedulerGroup.length > 0){
            for(int i = 0; i < schedulerGroup.length; i++)
            {
                String groupName = schedulerGroup[i];
                Task groupTasks[] = getTasksByGroup(groupName);
                if(groupTasks.length > 0){
                    for(int j = 0; j < groupTasks.length; j++)
                        taskList.add(groupTasks[j]);
                }
            }
        }
        tasks = new Task[taskList.size()];
        for(int i = 0; i < taskList.size(); i++)
            tasks[i] = (Task)taskList.get(i);
        return tasks;
    }

    public Task getTask(String id) throws Exception{
        Task task = null;
        try{
            if(id == null || id.equals(""))
                throw new Exception("\u8F93\u5165\u7684TaskID\u4E3A\u7A7A");
            int style = id.indexOf("#");
            if(style > 0){
                String group = id.substring(0, style);
                String taskName = id.substring(style + 1);
                JobDetail jobDetail = scheduler.getJobDetail(taskName, group);
                task = transformJobDetail(jobDetail);
            }
        }
        catch(SchedulerException e)
        {
            throw new Exception("\u83B7\u53D6id\u4E3A" + id + "\u7684Task\u51FA\u9519", e);
        }
        return task;
    }

    public Task[] getTasksByGroup(String group)
        throws Exception
    {
        ArrayList taskList = new ArrayList();
        Task tasks[];
        String schedulerGroup[] = scheduler.getJobGroupNames();
        if(schedulerGroup != null && schedulerGroup.length > 0)
        {
            for(int i = 0; i < schedulerGroup.length; i++)
            {
                String tempGoupName = schedulerGroup[i];
                if(!tempGoupName.equalsIgnoreCase(group))
                    continue;
                String jobNames[] = scheduler.getJobNames(group);
                if(jobNames != null && jobNames.length > 0)
                {
                    for(int j = 0; j < jobNames.length; j++)
                    {
                        String jobName = jobNames[j];
                        JobDetail jobDetail = scheduler.getJobDetail(jobName, group);
                        Task task = transformJobDetail(jobDetail);
                        if(task != null)
                            taskList.add(task);
                    }

                }
                break;
            }

        }
        tasks = new Task[taskList.size()];
        for(int i = 0; i < taskList.size(); i++)
            tasks[i] = (Task)taskList.get(i);

        return tasks;
    }

    private Task transformJobDetail(JobDetail jobDetail)
        throws SchedulerException
    {
        Task task = null;
        if(jobDetail != null)
        {
            Map detail = jobDetail.getJobDataMap().getWrappedMap();
            task = new TaskImpl(jobDetail.getGroup() + "#" + jobDetail.getName(), jobDetail.getName(), jobDetail.getGroup(), detail);
            task.setDescription(jobDetail.getDescription());
            task.setClassName(jobDetail.getJobClass().getName());
            org.quartz.Trigger trigger[] = scheduler.getTriggersOfJob(task.getName(), task.getGroup());
            if(trigger.length > 0)
            {
                org.quartz.Trigger t = trigger[0];
                if(t != null && (t instanceof CronTrigger))
                {
                    CronTrigger ct = (CronTrigger)t;
                    String cron = CronUtil.getCronFormate(ct);
                    task.setCron(cron);
                    int taskState = scheduler.getTriggerState(ct.getName(), ct.getGroup());
                    task.setState(taskState);
                    java.util.Date nextStartTime = ct.getNextFireTime();
                    task.setNextStartTime(nextStartTime);
                    int priority = ct.getPriority();
                    task.setPriority(priority);
                }
            }
            String taskType = (String)jobDetail.getJobDataMap().get("taskType");
            if(taskType != null && !taskType.equals(""))
                task.setType((new Integer(taskType)).intValue());
            Map detailMap = jobDetail.getJobDataMap().getWrappedMap();
            detailMap.remove("taskType");
            task.setDetail(detailMap);
        }
        return task;
    }

    private JobDetail transformTask(Task task)
        throws Exception
    {
        JobDetail job = null;
        if(task != null)
        {
            Map detail = task.getDetail();
            if(detail == null)
                throw new Exception("Task \u7684Detail \u4E3ANull");
            String className = task.getClassName();
            if(className == null || className.equals(""))
                throw new Exception("\u6CA1\u6709\u6307\u5B9A\u4EFB\u52A1\u5BF9\u5E94\u7684\u4E1A\u52A1\u903B\u8F91\u5904\u7406\u7C7B");
            Class jobClass = Class.forName(className);
            if(jobClass == null)
                throw new Exception(className + " \u7C7B\u4E0D\u5B58\u5728");
            if(jobClass == null)
                throw new Exception("\u6CA1\u6709\u8BBE\u7F6ETask\u5BF9\u5E94\u7684jobClass");
            job = new JobDetail(task.getName(), task.getGroup(), jobClass);
            if(task.getDescription() != null)
                job.setDescription(task.getDescription());
            int taskType = task.getType();
            job.getJobDataMap().put("taskType", (new Integer(taskType)).toString());
            if(!job.isDurable())
                job.setDurability(true);
            job.getJobDataMap().putAll(task.getDetail());
        }
        return job;
    }

    public void add(Task task)
        throws Exception
    {
        if(task != null)
        {
            JobDetail job = transformTask(task);
            try
            {
                job.validate();
                String cronExpress = task.getCron();
                if(cronExpress != null && !cronExpress.equals(""))
                {
                    java.util.Date startTime = CronUtil.getStartTime(cronExpress);
                    //起始时间处理，其实时间不能小于当前服务器时间+2秒
                    long startTimeM = startTime.getTime();
                    long currentTimeM = System.currentTimeMillis()+2000;
                    if(startTimeM < currentTimeM)
                    	startTime = new java.util.Date(currentTimeM);
                    //
                    java.util.Date endTime = CronUtil.getEndTime(cronExpress);
                    String cron = CronUtil.getCronExpress(cronExpress);
                    CronTrigger cronTrigger = new CronTrigger(task.getName(), task.getGroup(), task.getName(), task.getGroup(), startTime, endTime, cron);
                    if(task.getNextStartTime() != null)
                        cronTrigger.setNextFireTime(task.getNextStartTime());
                    cronTrigger.setPriority(task.getPriority());
                    cronTrigger.setMisfireInstruction(2);
                    scheduler.scheduleJob(job, cronTrigger);
                } else
                {
                    scheduler.addJob(job, false);
                }
            }
            catch(SchedulerException scheE)
            {
                throw new Exception("\u6DFB\u52A0\u4EFB\u52A1\u51FA\u9519 " + scheE.getMessage(), scheE);
            }
            catch(ParseException parseE)
            {
                throw new Exception("\u6DFB\u52A0\u4EFB\u52A1\u51FA\u9519 " + parseE.getMessage(), parseE);
            }
        }
    }

    public void delete(String taskId)
        throws Exception
    {
        if(taskId != null && !taskId.equals(""))
        {
            Task task = getTask(taskId);
            try
            {
                if(task != null)
                    scheduler.deleteJob(task.getName(), task.getGroup());
            }
            catch(Exception e)
            {
                throw new Exception("\u5220\u9664Task\u51FA\u9519 ", e);
            }
        }
    }

    public void update(Task task)
        throws Exception
    {
        if(task != null)
            try
            {
                String taskId = task.getId();
                Task oldTask = getTask(taskId);
                JobDetail jobDetail = transformTask(task);
                
                if(taskId.equals(task.getGroup()+"#"+task.getName()) && oldTask.getCron() != null && !oldTask.getCron().equals("") && task.getCron().equals(oldTask.getCron())){
                	//名字不变，且触发规则相同
                	org.quartz.Trigger oldTrigger = scheduler.getTrigger(oldTask.getName(), oldTask.getGroup());
                	if(oldTrigger != null){
                		delete(oldTask.getId());
                		scheduler.scheduleJob(jobDetail, oldTrigger);
                	}else{
                        delete(oldTask.getId());
                        add(task);                   		
                	}                	
                }else{
                	//名字变化，删除原任务，并添加新任务
                    delete(oldTask.getId());
                    add(task);                	
                }
            }
            catch(Exception e){
                throw new Exception("\u66F4\u65B0Task\u51FA\u9519", e);
            }
    }

    public void run(String taskId)
        throws Exception
    {
        Task task = getTask(taskId);
        if(task == null)
            throw new Exception("ID \u4E3A" + taskId + " \u7684Task\u4E0D\u5B58\u5728");
        try
        {
            if(task.getType() == 1)
            {
                if(!task.isStart())
                {
                    JobDetail jobDetail = transformTask(task);
                    scheduler.resumeJob(jobDetail.getName(), jobDetail.getGroup());
                }
            } else
            {
                scheduler.triggerJob(task.getName(), task.getGroup());
            }
        }
        catch(SchedulerException scheE)
        {
            throw new Exception("\u8FD0\u884CID\u4E3A " + taskId + "\u7684Task\u51FA\u9519", scheE);
        }
    }

    public void pause(String taskId)
        throws Exception
    {
        Task task = getTask(taskId);
        if(task.getType() == 1 && task.isStart())
            try{
                scheduler.pauseJob(task.getName(), task.getGroup());
            }
            catch(SchedulerException scheE)
            {
                throw new Exception("\u6682\u505CID\u4E3A " + taskId + "\u7684Task\u51FA\u9519", scheE);
            }
    }
    /**
     * 执行一次任务
     */
    public boolean runOnce(String taskId) throws Exception{
        Task task = getTask(taskId);
        JobDetail jobDetail = scheduler.getJobDetail(task.getName(), task.getGroup());
        ExtendJob extendJob = (ExtendJob) Class.forName(task.getClassName()).newInstance();
        return extendJob.executeOnce(jobDetail);
    }

    public String[] getAllGroup()
        throws Exception{
        return scheduler.getJobGroupNames();
    }
}



//public void update(Task task)
//throws Exception
//{
//if(task != null)
//    try
//    {
//        String taskId = task.getId();
//        Task oldTask = getTask(taskId);
//        JobDetail jobDetail = transformTask(task);
//        //if(oldTask.getCron() != null && oldTask.getCron().equals("") && task.getCron().equals(oldTask.getCron())){
//        //触发器不变，则重新利用原来出发器
//        if(oldTask.getCron() != null && !oldTask.getCron().equals("") && task.getCron().equals(oldTask.getCron())){
//        	org.quartz.Trigger oldTrigger = scheduler.getTrigger(oldTask.getName(), oldTask.getGroup());
//        	//删除老的调度
//        	delete(oldTask.getId());
//        	//名字相同，直接部署
//            if(oldTrigger != null){
//                scheduler.scheduleJob(jobDetail, oldTrigger);
//            }
//            else{                    	
//                add(task);
//            }
//        }else{
//            delete(oldTask.getId());
//            add(task);
//        }
//    }
//    catch(Exception e){
//        throw new Exception("\u66F4\u65B0Task\u51FA\u9519", e);
//    }
//}


