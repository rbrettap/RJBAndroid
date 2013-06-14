package com.rjb;

import android.app.Activity;
import android.app.Application;
import android.content.Context;
import android.content.ContextWrapper;
import android.content.res.Configuration;

import java.lang.reflect.Method;
import java.util.Locale;

/**
 * Turn on StrictMode, and do the pending transition check (although we'll probably
 * remove that soon).
 *
 * User: kgalligan
 * Date: 3/21/12
 * Time: 8:36 PM
 * To change this template use File | Settings | File Templates.
 */
public class ApApplication extends Application
{
    private static Method activity_overridePendingTransition;

    @Override
    public void onCreate()
    {
           /* StrictMode.setThreadPolicy(new StrictMode.ThreadPolicy.Builder()

                    .detectDiskReads()
                    .detectDiskWrites()
                    .detectNetwork()   // or .detectAll() for all detectable problems
                    .penaltyLog()
                    .build());
            StrictMode.setVmPolicy(new StrictMode.VmPolicy.Builder()
                    .detectLeakedSqlLiteObjects()
                    .penaltyLog()
                    .penaltyDeath()
                    .build());*/

        super.onCreate();

    }

   /* public static List<IssueParam> reportIssuePrep(Context c) {
//        IssueReporter.saveDatabase(c, DatabaseHelper.DATABASE_NAME, false);

        List<IssueParam> params = new ArrayList<IssueParam>();

        try {
            Integer myUserId = Props.getMyUserId(c);
            params.add(new IssueParam("userId", myUserId.toString()));
            DatabaseHelper instance = DatabaseHelper.getInstance(c);
            User myUser = instance.getUserDao().queryForId(myUserId);
            params.add(new IssueParam("email", myUser.getEmail()));
            instance.getCompanyDao().refresh(myUser.getCompany());
            params.add(new IssueParam("company", myUser.getCompany().getName()));
            params.add(new IssueParam("firstName", myUser.getFirstName()));
            params.add(new IssueParam("lastName", myUser.getLastName()));
            params.add(new IssueParam("companyRole", myUser.getCompanyRole().toString()));
        } catch (Exception e)
        {
            App.e(FieldLensApplication.class, e);
        }
        return params;
    }*/




    static {
        initializeCompatibility();
    }

    private static void initializeCompatibility() {

        try {
            activity_overridePendingTransition = Activity.class.getMethod("overridePendingTransition", new Class[] { int.class, int.class });
        }
        catch (NoSuchMethodException exception) {

            // It's not necessary to log this statement; This would be thrown if the OS is older than 2.0
            //   Log.e("HomeActivity#initializeCompatibility", "No overridePendingTransition method in this SDK.", exception);
        }
    }

    public void overridePendingTransition_new(Activity activity, int startAnimation, int endAnimation) {

        if (activity_overridePendingTransition != null) {

            try {
                activity_overridePendingTransition.invoke(activity, startAnimation, endAnimation);
            }
            catch (Exception exception) {

                // It's not necessary to log this statement; This would be thrown if the OS is older than 2.0
                //    Log.e("HomeActivity#overridePendingTransition", "Error overriding pending transition animation. " + exception.getMessage(), exception);
            }
        }
    }
}
