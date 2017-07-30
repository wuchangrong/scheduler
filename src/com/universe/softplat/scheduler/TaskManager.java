package com.universe.softplat.scheduler;

public interface TaskManager{

    public abstract Task[] getAllTasks()
        throws Exception;

    public abstract Task getTask(String s)
        throws Exception;

    public abstract Task[] getTasksByGroup(String s)
        throws Exception;

    public abstract String[] getAllGroup()
        throws Exception;

    public abstract void add(Task task)
        throws Exception;

    public abstract void delete(String s)
        throws Exception;

    public abstract void update(Task task)
        throws Exception;

    public abstract void run(String s)
        throws Exception;

    public abstract void pause(String s)
        throws Exception;
    
    public abstract boolean runOnce(String s) 
    	throws Exception;
}
