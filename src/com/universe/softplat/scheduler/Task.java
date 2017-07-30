package com.universe.softplat.scheduler;

import java.util.Date;
import java.util.Map;

public interface Task extends Comparable{
	//参见Constant类
    public abstract int getState();
    public abstract void setState(int i);
    //其他上下文数据
    public abstract Map getDetail();
    public abstract void setDetail(Map map);

    public abstract String getName();
    public abstract void setName(String s);

    public abstract String getDescription();
    public abstract void setDescription(String s);

    public abstract String getCron();
    public abstract void setCron(String s);

    public abstract String getGroup();
    public abstract void setGroup(String s);

    public abstract String getId();
    public abstract void setId(String s);

    public abstract boolean isStart();

    public abstract Date getNextStartTime();
    public abstract void setNextStartTime(Date date);
    //优先级 4-低 5-中 6-高
    public abstract int getPriority();
    public abstract void setPriority(int i);    
    //运行方式RunType：0-手动 1-自动
    public abstract void setType(int i);    
    public abstract int getType();

    public abstract void setClassName(String s);
    public abstract String getClassName();
    
    public abstract String getCreater();
    //扩展属性(通过XML配置)
    public abstract Map getExtendJobDetails();
    //
    public abstract Date getExecTime();
    public abstract void setExecTime(Date time);
    public abstract String getExecResult();
    public abstract void setExecResult(String result);
}
