package com.universe.softplat.scheduler.impl;

import java.util.Date;

import org.quartz.Scheduler;

import com.universe.softplat.scheduler.Schedular;
import com.universe.softplat.scheduler.Task;
import com.universe.softplat.scheduler.TaskManager;

public class SchedularImpl implements Schedular{
    
	private Scheduler scheduler;
    private boolean isLoad;
    private TaskManager job;
	
    public SchedularImpl(Scheduler scheduler)
    {
        this.scheduler = null;
        isLoad = false;
        job = null;
        this.scheduler = scheduler;
        job = new TaskManagerImpl(scheduler);
    }

    public void load() throws Exception{
        if(!isLoad)
            try{
                Task tasks[] = job.getAllTasks();
                if(tasks != null && tasks.length > 0){
                    for(int i = 0; i < tasks.length; i++){
                        Task task = tasks[i];
                        if(!task.isStart())
                            scheduler.pauseJob(task.getName(), task.getGroup());
                    }
                    isLoad = true;
                }
            }catch(Exception e){
                e.printStackTrace();
                throw new Exception("\u88C5\u8F7D\u8C03\u5EA6\u5668\u51FA\u9519\uFF1A", e);
            }
    }

    public void start()
        throws Exception
    {
        try
        {
            if(!scheduler.isStarted() || scheduler.isInStandbyMode())
                scheduler.start();
        }
        catch(Exception e)
        {
            throw new Exception("\u542F\u52A8\u8C03\u5EA6\u5668\u51FA\u9519\uFF1A", e);
        }
    }

    public void stop()
        throws Exception
    {
        try
        {
            scheduler.standby();
        }
        catch(Exception e)
        {
            throw new Exception("\u6682\u505C\u8C03\u5EA6\u5668\u51FA\u9519\uFF1A", e);
        }
    }

    public void destroy()
        throws Exception
    {
        try
        {
            scheduler.shutdown();
        }
        catch(Exception e)
        {
            throw new Exception("\u9500\u6BC1\u8C03\u5EA6\u5668\u51FA\u9519\uFF1A", e);
        }
    }

    public String getName() throws Exception {
        return scheduler.getSchedulerName();
    }

    public int getState() throws Exception{
        int state = -1;
        if(scheduler.isInStandbyMode())
            state = 0;
        else if(scheduler.isShutdown())
            state = 2;
        else if(scheduler.isStarted())
            state = 1;
        return state;
    }

    public Date getStartTime() throws Exception{
        return scheduler.getMetaData().runningSince();
    }
}
