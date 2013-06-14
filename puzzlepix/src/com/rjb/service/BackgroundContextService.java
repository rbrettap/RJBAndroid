package com.rjb.service;

import android.app.Activity;
import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
//import com.ap.receiver.ContentPurgeReceiver;
//import com.ap.receiver.ContentUpdateReceiver;
//import com.ap.receiver.WeatherUpdateReceiver;
//import com.ap.receiver.WidgetProvider;
//import com.ap.service.store.UserProfileStore;
import com.rjb.utils.App;
import com.rjb.utils.Dates;

public class BackgroundContextService
{
	private static final Logger logger = new Logger(BackgroundContextService.class);
	private static long startTimeInBackground = -1;
	private static long lastRefreshTime = -1;
	private static int articlesViewedCount = 0;

	public static void startInBackground()
    {
		App.i(BackgroundContextService.class, "Going to the background.");
		startTimeInBackground = System.currentTimeMillis();
	}

	public static long getStartTimeInBackground() {
		return startTimeInBackground;
	}

	public static void resetStartTimeInBackground(Context context) {
		long timeInBackground = System.currentTimeMillis() - startTimeInBackground;
        App.i(BackgroundContextService.class, "Coming to the foreground. startTimeInBackground = " + startTimeInBackground + "; timeInBackground = " + timeInBackground + "; " + context.getClass().getSimpleName());
		startTimeInBackground = -1;
	}

	public static void setLastRefreshTime(long millis) {
		BackgroundContextService.lastRefreshTime = millis;
	}

	public static void setLastRefreshTime() {
		BackgroundContextService.lastRefreshTime = System.currentTimeMillis();
	}

	public static long getLastRefreshTime() {
		return lastRefreshTime;
	}
/*
	private static PendingIntent getContentUpdatePendingIntent(Context context) {
		Intent intent = new Intent(context, ContentUpdateReceiver.class);
		return PendingIntent.getBroadcast(context, 0, intent, 0);
	}

	private static PendingIntent getContentPurgePendingIntent(Context context) {
		Intent intent = new Intent(context, ContentPurgeReceiver.class);
		return PendingIntent.getBroadcast(context, 0, intent, 0);
	}

	private static PendingIntent getWeatherUpdatePendingIntent(Context context) {
		Intent intent = new Intent(context, WeatherUpdateReceiver.class);
		return PendingIntent.getBroadcast(context, 0, intent, 0);
	}
*/
	public static void scheduleAlarmForPendingIntentNoWake(Context context, PendingIntent pendingIntent, long timeFromNow) {
        scheduleAlarmForPendingIntent(context, pendingIntent, timeFromNow, AlarmManager.RTC);
    }

	public static void scheduleAlarmForPendingIntentWake(Context context, PendingIntent pendingIntent, long timeFromNow) {
        scheduleAlarmForPendingIntent(context, pendingIntent, timeFromNow, AlarmManager.RTC_WAKEUP);
    }

	private static void scheduleAlarmForPendingIntent(Context context, PendingIntent pendingIntent, long timeFromNow, int alarmType) {
		final AlarmManager alarmManager = (AlarmManager) context.getSystemService(Activity.ALARM_SERVICE);
		alarmManager.set(alarmType, System.currentTimeMillis() + timeFromNow, pendingIntent);
	}


	public static void scheduleContentPurge(Context context) {
		//scheduleAlarmForPendingIntentWake(context, getContentPurgePendingIntent(context), Dates.DAY);
        App.i(BackgroundContextService.class, "Content Purge Alarm is set.");
	}


	public static void cancelAlarmForPendingIntent(Context context, PendingIntent pendingIntent) {
		AlarmManager alarmManager = (AlarmManager) context.getSystemService(Activity.ALARM_SERVICE);
		alarmManager.cancel(pendingIntent);
	}

	public static void cancelWidgetUpdate(Context context) {
        //final PendingIntent pendingIntent = WidgetProvider.createUpdateIntent(context);
		//cancelAlarmForPendingIntent(context, pendingIntent);
	}

    public static void cancelWeatherUpdate(Context context) {
       // cancelAlarmForPendingIntent(context, getWeatherUpdatePendingIntent(context));
	}

    public static void cancelContentUpdate(Context context) {
		//cancelAlarmForPendingIntent(context, getContentUpdatePendingIntent(context));
	}

    public static void cancelContentPurge(Context context) {
		//cancelAlarmForPendingIntent(context, getContentPurgePendingIntent(context));
	}

	public static void incrementArticlesViewedCount() {
		articlesViewedCount++;
	}

	public static int getArticlesViewedCount() {
		return articlesViewedCount;
	}
}
