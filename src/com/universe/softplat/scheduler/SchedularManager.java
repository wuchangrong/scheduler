package com.universe.softplat.scheduler;

import java.util.Date;
import java.util.Properties;

public interface SchedularManager{

    public abstract void add(Task task)
        throws Exception;

    public abstract void update(Task task)
        throws Exception;

    public abstract void delete(String s)
        throws Exception;

    public abstract void runTask(String s)
        throws Exception;

    public abstract boolean runOnceTask(String s)
    	throws Exception;    
    
    public abstract void pauseTask(String s)
        throws Exception;

    public abstract Task[] getAllTask()
        throws Exception;

    public abstract Task[] getTaskByGroup(String s)
        throws Exception;

    public abstract Task getTask(String s)
        throws Exception;

    public abstract String[] getAllGroup()
        throws Exception;

    public abstract void initial(Properties properties)
        throws Exception;

    public abstract void start()
        throws Exception;

    public abstract void stop()
        throws Exception;

    public abstract void destroy()
        throws Exception;

    public abstract String getName()
        throws Exception;

    public abstract int getState()
        throws Exception;

    public abstract Date getStartTime()
        throws Exception;

    public abstract boolean isInitialed();
}
