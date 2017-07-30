package com.universe.softplat.scheduler.util;

import java.util.Date;
import org.quartz.CronTrigger;

public class CronUtil
{

    public CronUtil()
    {
    }

    public static Date getStartTime(String str)
    {
        Date startTime = null;
        if(str != null && !str.equals(""))
        {
            String strArr[] = str.split(";");
            String temp = strArr[0];
            String longTime = temp.substring(temp.indexOf(":") + 1);
            if(longTime != null && !longTime.equals(""))
                startTime = new Date((new Long(longTime)).longValue());
        }
        return startTime;
    }

    public static Date getEndTime(String str)
    {
        Date endTime = null;
        if(str != null && !str.equals(""))
        {
            String strArr[] = str.split(";");
            String temp = strArr[2];
            String longTime = temp.substring(temp.indexOf(":") + 1);
            if(longTime != null && !longTime.equals(""))
                endTime = new Date((new Long(longTime)).longValue());
        }
        return endTime;
    }

    public static String getCronExpress(String str)
    {
        String cronExpress = null;
        if(str != null && !str.equals(""))
        {
            String strArr[] = str.split(";");
            String temp = strArr[1];
            cronExpress = temp.substring(temp.indexOf(":") + 1);
        }
        return cronExpress;
    }

    public static String getCronFormate(CronTrigger ct)
    {
        String cronFormate = "";
        if(ct != null)
        {
            Date startTime = ct.getStartTime();
            if(startTime != null)
                cronFormate = cronFormate + "startTime:" + startTime.getTime() + ";";
            else
                cronFormate = cronFormate + "startTime:;";
            String cron = ct.getCronExpression();
            if(cron != null)
                cronFormate = cronFormate + "cron:" + cron + ";";
            else
                cronFormate = cronFormate + "cron:;";
            Date endTime = ct.getEndTime();
            if(endTime != null)
                cronFormate = cronFormate + "endTime:" + endTime.getTime();
            else
                cronFormate = cronFormate + "endTime:";
        }
        return cronFormate;
    }
    /**
     * ∑Ω∑®√Ë ˆ:
     */
    public static String dealWithCron(String str){
    	return "";
    }
}
