package com.rjb.utils;

/**
 * Created by IntelliJ IDEA.
 * User: kgalligan
 * Date: 3/26/12
 * Time: 1:03 PM
 * To change this template use File | Settings | File Templates.
 */
public class GlobalThreadPool
{
    private static SimpleThreadPoolProcessor pool = new SimpleThreadPoolProcessor(5);

    public static void run(Runnable runnable)
    {
        pool.addRunnable(runnable);
    }

}
