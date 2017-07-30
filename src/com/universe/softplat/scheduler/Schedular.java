package com.universe.softplat.scheduler;

import java.util.Date;

public interface Schedular{

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
}
