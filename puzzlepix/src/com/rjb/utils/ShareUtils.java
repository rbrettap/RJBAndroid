package com.rjb.utils;

import android.app.Activity;
import android.content.Intent;
import android.content.pm.ResolveInfo;

import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: rbrett
 * Date: 4/4/12
 * Time: 12:14 PM
 * To change this template use File | Settings | File Templates.
 */
public class ShareUtils
{
    private static boolean facebookInstalled;

    public static boolean isFacebookInstalled(Activity activity)
    {
        final Intent myIntent = new Intent(Intent.ACTION_SEND);
        List<ResolveInfo> resInfoList = activity.getPackageManager().queryIntentActivities(myIntent, 0);

        for (ResolveInfo resolveInfo : resInfoList)
        {
            String resolvePackageName = resolveInfo.activityInfo.packageName;
            if(resolvePackageName.equals("com.facebook.katana"))
                return true;
        }

        return false;
    }

    public static void callShare(Activity activity, String title, String body)
    {
        Intent intent=new Intent(android.content.Intent.ACTION_SEND);
        intent.setType("text/plain");
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_WHEN_TASK_RESET);

        if(title == null)
           title = "";

        intent.putExtra(Intent.EXTRA_SUBJECT, "[AP News] "+ title);
        intent.putExtra(Intent.EXTRA_TEXT, body);
        activity.startActivity(Intent.createChooser(intent, "How do you want to share?"));
    }
}
