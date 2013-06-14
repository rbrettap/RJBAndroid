package com.rjb.utils;

/**
 * I occasionally see this lock up in the UI, but most of the time its just going to memory.  Having its own thread pool will help.
 *
 * User: rbrett
 * Date: 3/26/12
 * Time: 1:03 PM
 * To change this template use File | Settings | File Templates.
 */
public class TabLoadingThreadPool
{
    private static SimpleThreadPoolProcessor pool = new SimpleThreadPoolProcessor(2);

    public static void run(Runnable runnable)
    {
        pool.addRunnable(runnable);
    }

}
