package com.rjb.utils;

import android.app.Activity;
import android.os.Handler;
import android.view.Display;

/**
 * Created by IntelliJ IDEA.
 * User: kgalligan
 * Date: 3/14/12
 * Time: 11:07 PM
 * To change this template use File | Settings | File Templates.
 */
public class StaticMainThreadReference
{
    private static Handler mainThreadHander;
    private static Display display;

    public static Handler getMainThreadHander()
    {
        return mainThreadHander;
    }

    private static void setMainThreadHander(Handler mainThreadHander)
    {
        StaticMainThreadReference.mainThreadHander = mainThreadHander;
    }

    //TODO: This should be removed
    public static Display getDisplay()
    {
        return display;
    }

    private static void setDisplay(Display display)
    {
        StaticMainThreadReference.display = display;
    }

    public static void initGlobalVariables(Activity activity)
    {
        //Assume we're in the UI thread.  If not, this will not work properly.
        setMainThreadHander(new Handler());
        setDisplay(activity.getWindowManager().getDefaultDisplay());
    }
}
