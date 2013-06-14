package com.rjb.utils;

/**
 * Added a content specific thread pool to make sure we download content pretty fast after a screen opens.  Currently it can take a while before it starts.
 * User: kgalligan
 * Date: 3/28/12
 * Time: 10:01 AM
 * To change this template use File | Settings | File Templates.
 */
public class ContentThreadPool
{
    private static SimpleThreadPoolProcessor pool = new SimpleThreadPoolProcessor(5);

    public static void run(Runnable runnable)
    {
        pool.addRunnable(runnable);
    }

}
