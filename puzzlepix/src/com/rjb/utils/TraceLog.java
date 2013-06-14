package com.rjb.utils;

import android.util.Log;

/**
 * Created by IntelliJ IDEA.
 * User: rbrett
 * Date: 3/23/12
 * Time: 12:04 PM
 * To change this template use File | Settings | File Templates.
 */
public class TraceLog
{
    public static void i(Class c, String s)
    {
        App.i(TraceLog.class, System.currentTimeMillis() + ": " + s);
    }
}
