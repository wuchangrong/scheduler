package com.universe.softplat.scheduler.impl;

import java.util.Date;
import java.util.Properties;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.quartz.SchedulerFactory;
import org.quartz.impl.StdSchedulerFactory;

import com.universe.softplat.scheduler.Schedular;
import com.universe.softplat.scheduler.SchedularManager;
import com.universe.softplat.scheduler.Task;
import com.universe.softplat.scheduler.TaskManager;

public class SchedularManagerImpl implements SchedularManager{
	
    private Schedular schedular;
    private TaskManager taskManager;
    private boolean isInitial;
    private static Log log;

    static{
        log = LogFactory.getLog(com.universe.softplat.scheduler.impl.SchedularManagerImpl.class);
    }
    
    public SchedularManagerImpl(){
        schedular = null;
        taskManager = null;
        isInitial = false;
    }

    public void add(Task task) throws Exception{
        taskManager.add(task);
    }

    public void update(Task task)
        throws Exception
    {
        taskManager.update(task);
    }

    public void delete(String taskId)
        throws Exception
    {
        taskManager.delete(taskId);
    }

    public void runTask(String taskId)
        throws Exception
    {
        taskManager.run(taskId);
    }

    public boolean runOnceTask(String s)
		throws Exception{
    	return taskManager.runOnce(s);
    }
    
    public void pauseTask(String taskId)
        throws Exception
    {
        taskManager.pause(taskId);
    }

    public Task[] getAllTask()
        throws Exception
    {
        return taskManager.getAllTasks();
    }

    public Task[] getTaskByGroup(String group)
        throws Exception
    {
        return taskManager.getTasksByGroup(group);
    }

    public Task getTask(String taskId)
        throws Exception
    {
        return taskManager.getTask(taskId);
    }

    public void initial(Properties pro)
        throws Exception
    {
        if(pro != null)
            if(!isInitial)
                try
                {
                    SchedulerFactory sf = new StdSchedulerFactory(pro);
                    org.quartz.Scheduler sched = sf.getScheduler();
                                       
                    schedular = new SchedularImpl(sched);
                    taskManager = new TaskManagerImpl(sched);
                    isInitial = true;
                }
                catch(Exception e)
                {
                    throw new Exception("\u521D\u59CB\u5316\u8C03\u5EA6\u5668\u51FA\u9519\uFF1A", e);
                }
            else
                log.info("\u8C03\u5EA6\u5668\u5DF2\u88AB\u521D\u59CB\u5316\uFF0C\u8981\u91CD\u65B0\u521D\u59CB\u5316\u8C03\u5EA6\u5668\uFF0C\u8BF7\u5148\u8C03\u7528destroy\u65B9\u6CD5");
    }

    public void start()
        throws Exception
    {
        schedular.start();
    }

    public void stop()
        throws Exception
    {
        schedular.stop();
    }

    public void destroy()
        throws Exception
    {
        schedular.destroy();
        schedular = null;
        taskManager = null;
        isInitial = false;
    }

    public String getName()
        throws Exception
    {
        return schedular.getName();
    }

    public int getState()
        throws Exception
    {
        return schedular.getState();
    }

    public Date getStartTime()
        throws Exception
    {
        return schedular.getStartTime();
    }

    public boolean isInitialed()
    {
        return isInitial;
    }

    public String[] getAllGroup()
        throws Exception
    {
        return taskManager.getAllGroup();
    }
}
