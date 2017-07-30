package com.universe.softplat.scheduler;

import java.io.InputStream;

public interface TaskParser{

    public abstract Task getTask(InputStream inputstream)
        throws Exception;
}
