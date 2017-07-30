package com.universe.softplat.scheduler;

import java.util.Date;
import java.util.Map;

public interface Task extends Comparable{
	//�μ�Constant��
    public abstract int getState();
    public abstract void setState(int i);
    //��������������
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
    //���ȼ� 4-�� 5-�� 6-��
    public abstract int getPriority();
    public abstract void setPriority(int i);    
    //���з�ʽRunType��0-�ֶ� 1-�Զ�
    public abstract void setType(int i);    
    public abstract int getType();

    public abstract void setClassName(String s);
    public abstract String getClassName();
    
    public abstract String getCreater();
    //��չ����(ͨ��XML����)
    public abstract Map getExtendJobDetails();
    //
    public abstract Date getExecTime();
    public abstract void setExecTime(Date time);
    public abstract String getExecResult();
    public abstract void setExecResult(String result);
}
