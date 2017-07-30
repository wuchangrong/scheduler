package com.universe.softplat.scheduler;

import com.universe.softplat.scheduler.impl.SchedularManagerImpl;

public class SchedularManagerFactory{

    public SchedularManagerFactory(){
    }

    public static SchedularManager getSchedularManager(){
        if(schedularManager == null)
            schedularManager = new SchedularManagerImpl();
        return schedularManager;
    }

    private static SchedularManager schedularManager = null;

}
