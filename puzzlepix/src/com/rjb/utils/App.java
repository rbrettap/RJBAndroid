package com.rjb.utils;

import android.util.Log;
//import co.touchlab.ir.MemLog;

/**
 * Created with IntelliJ IDEA.
 * User: rbrett
 * Date: 4/6/12
 * Time: 11:39 AM
 * To change this template use File | Settings | File Templates.
 */
public class App
{
    public static void i(Class c, String s)
    {
        String simpleName = c.getSimpleName();
        Log.i(simpleName, s);
//        MemLog.i(simpleName, s);
    }

    public static void i(Class c, String s, Throwable t)
    {
        String simpleName = c.getSimpleName();
        Log.i(simpleName, s, t);
//        MemLog.i(simpleName, s, t);
    }

    public static void w(Class c, String s)
    {
        String simpleName = c.getSimpleName();
        Log.w(simpleName, s);
//        MemLog.w(simpleName, s);
    }

    public static void e(Class c, String s)
    {
        String simpleName = c.getSimpleName();
        Log.e(simpleName, s);
//        MemLog.e(simpleName, s);
    }

    public static void e(Class c, String s, Throwable t)
    {
        String simpleName = c.getSimpleName();
        Log.e(simpleName, s, t);
//        MemLog.e(simpleName, s, t);
    }

    public static void e(Class c, Throwable t)
    {
        String simpleName = c.getSimpleName();
        Log.e(simpleName, null, t);
//        MemLog.e(simpleName, t);
    }

    public static void d(Class c, String s)
    {
        String simpleName = c.getSimpleName();
        Log.d(simpleName, s);
//        MemLog.d(simpleName, s);
    }

    public static void d(Class c, String s, Throwable t)
    {
        String simpleName = c.getSimpleName();
        Log.d(simpleName, s, t);
//        MemLog.d(simpleName, s, t);
    }
}

