package com.rjb.service;

import android.util.Log;
import com.rjb.utils.App;

public class Logger {

	// To reveal all DEBUG lines:
	// ./adb shell setprop log.tag.apmobile DEBUG
	// ./adb logcat
	private static final String root = "apmobile";
	private Class<?> clazz;

	public Logger(final Class<?> clazz) {
		this.clazz = clazz;
	}

	public void v(final String msg)
    {
        Log.v(clazz.getName(), msg);
	}
	
	public void d(final String msg)
    {
        App.d(clazz, msg);
	}
	
	public void i(final String msg)
    {
    	App.i(clazz, msg);
	}
	
	public void w(final String msg)
    {
		App.w(clazz, msg);
	}
	
	public void e(final String msg)
    {
		App.e(clazz, msg);
	}

}
