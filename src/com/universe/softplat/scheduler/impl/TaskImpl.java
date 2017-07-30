package com.universe.softplat.scheduler.impl;

import java.util.Date;
import java.util.Map;

import com.universe.softplat.scheduler.Task;

public class TaskImpl implements Task{
	
    private Map detail;
    private String name;
    private String description;
    private String id;
    private String cron;
    private String group;
    private Date nextStartTime;
    private String className;
    private int priority;
    private int state;
    private int type;
    //
    private Date execTime;
    private String execResult;
    
    public TaskImpl(String id, String name, String group, Map detail){
        this.name = null;
        description = null;
        this.id = null;
        cron = null;
        this.group = null;
        nextStartTime = null;
        className = null;
        priority = 5;
        state = -1;
        type = 1;
        this.id = id;
        this.name = name;
        this.group = group;
        this.detail = detail;
    }

    public String getCron()
    {
        return cron;
    }

    public String getId()
    {
        return id;
    }

    public void setId(String id)
    {
        this.id = id;
    }

    public void setCron(String cron)
    {
        this.cron = cron;
    }

    public String getGroup()
    {
        return group;
    }

    public void setGroup(String group)
    {
        this.group = group;
    }

    public Map getDetail()
    {
        return detail;
    }

    public boolean isStart()
    {
        return state != 1 && state != 3 && state != -1;
    }

    public void setDetail(Map detail)
    {
        this.detail = detail;
    }

    public void setName(String name)
    {
        this.name = name;
    }

    public void setDescription(String description)
    {
        this.description = description;
    }

    public String getDescription()
    {
        return description;
    }

    public String getName()
    {
        return name;
    }

    public Date getNextStartTime()
    {
        return nextStartTime;
    }

    public int getPriority()
    {
        return priority;
    }

    public void setPriority(int priority)
    {
        this.priority = priority;
    }

    public int getState()
    {
        return state;
    }

    public void setState(int state)
    {
        this.state = state;
    }

    public void setType(int type)
    {
        this.type = type;
    }

    public int getType()
    {
        return type;
    }

    public void setNextStartTime(Date nextStartTime)
    {
        this.nextStartTime = nextStartTime;
    }

    public void setClassName(String className)
    {
        this.className = className;
    }

    public String getClassName()
    {
        return className;
    }
    
    public String getCreater(){
    	return (String)getDetail().get("creater");
    }
    
    public Map getExtendJobDetails(){
    	return (Map)getDetail().get("extendJobDetails");
    }

	public Date getExecTime() {
		return execTime;
	}

	public void setExecTime(Date execTime) {
		this.execTime = execTime;
	}

	public String getExecResult() {
		return execResult;
	}

	public void setExecResult(String execResult) {
		this.execResult = execResult;
	}
	//
    public boolean equals(Object obj){
        if(obj == null)
        	return false;
        if(obj instanceof TaskImpl){
        	TaskImpl target = (TaskImpl)obj;
        	String taskId1 = this.getGroup()+"#"+this.getName();
        	String taskId2 = target.getGroup()+"#"+target.getName();
        	return taskId1.equals(taskId2);
        }else
        	return false;
    }
    //
    public int compareTo(Object obj){
        if(obj == null)
        	return 1;
        if(obj instanceof TaskImpl){
        	TaskImpl target = (TaskImpl)obj;
        	String taskId1 = this.getGroup()+"#"+this.getName();
        	String taskId2 = target.getGroup()+"#"+target.getName();
        	return taskId1.compareTo(taskId2);
        }else
        	throw new RuntimeException("数据类型不同，无法比较!");
    }
}
