package com.rjb.utils;

import android.util.Log;

import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

/**
 * Created by IntelliJ IDEA.
 * User: kgalligan
 * Date: 3/26/12
 * Time: 1:00 PM
 * To change this template use File | Settings | File Templates.
 */
public class SimpleThreadPoolProcessor
{
    private BlockingQueue<Runnable> opsToRun = new LinkedBlockingQueue<Runnable>();
    private ThreadPoolExecutor executor;

    public SimpleThreadPoolProcessor(int threadCount)
    {
        executor = new ThreadPoolExecutor(threadCount, threadCount * 2, 30, TimeUnit.SECONDS, opsToRun){
            @Override
            protected void beforeExecute(Thread t, Runnable r)
            {
                App.i(SimpleThreadPoolProcessor.this.getClass(), "Adding to queue count: "+ opsToRun.size());
                super.beforeExecute(t, r);
            }

            @Override
            protected void afterExecute(Runnable r, Throwable t)
            {
                App.i(SimpleThreadPoolProcessor.this.getClass(), "Removing from queue count: "+ opsToRun.size());
                super.afterExecute(r, t);
            }
        };
    }

    public void addRunnable(Runnable runnable)
    {
        executor.execute(runnable);
    }

    public void clear()
    {
        opsToRun.clear();
    }
}
