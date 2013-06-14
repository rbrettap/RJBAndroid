package com.rjb.utils;

/**
 * Created by IntelliJ IDEA.
 * User: rbrett
 * Date: 3/28/12
 * Time: 10:01 AM
 * To change this template use File | Settings | File Templates.
 */
public class ReceiverThreadPool
{
    private static SimpleThreadPoolProcessor pool = new SimpleThreadPoolProcessor(2);

    public static void run(Runnable runnable)
    {
        pool.addRunnable(runnable);
    }

}
