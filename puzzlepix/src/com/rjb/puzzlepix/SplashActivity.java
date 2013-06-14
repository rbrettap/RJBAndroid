package com.rjb.puzzlepix;

import android.app.Activity;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.location.LocationManager;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.TextView;

import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

import com.rjb.Enums.AppHandler;
import com.rjb.Enums.AppIntent;
import com.rjb.service.BackgroundContextService;
import com.rjb.service.Logger;
import com.rjb.service.store.UserProfileStore;
import com.rjb.utils.Dates;
import com.rjb.utils.Numbers;
import com.rjb.utils.StaticMainThreadReference;
import com.rjb.widgets.EulaDialog;

/*
import com.ap.Enums;
import com.ap.Enums.AppHandler;
import com.ap.Enums.AppIntent;
import com.ap.Enums.GAnalytics;
import com.ap.Enums.GAnalyticsAction;
import com.ap.model.Location;
import com.ap.model.Registration;
import com.ap.service.ApAlertDialogs;
import com.ap.service.ApService;
import com.ap.service.BackgroundContextService;
import com.ap.service.Logger;
import com.ap.service.advertisement.AdvertisementService;
import com.ap.service.block.BlockUtils;
import com.ap.service.block.content.purge.ContentPurge;
import com.ap.service.block.content.update.ContentUpdate;
import com.ap.service.block.media.MediaService;
import com.ap.service.edition.EditionUtils;
import com.ap.service.registration.RegistrationUtils;
import com.ap.service.registration.RegistrationUtils.RegistrationStatus;
import com.ap.service.store.UserProfileStore;
import com.ap.service.store.WeatherStore;
import com.ap.service.store.helpers.BreakingContentHelper;
import com.ap.service.store.helpers.LocationHistoryHelper;
import com.ap.service.store.helpers.MergedContentHelper;
import com.ap.service.weather.WeatherUpdate;
import com.ap.utils.*;
*/
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;


public class SplashActivity extends Activity
{
    private static final Logger logger = new Logger(SplashActivity.class);
    private final BroadcastReceiver registrationInformationBroadcastReceiver = new RegistrationInformationBroadcastReceiver();
    private final BroadcastReceiver resumeApplicationBroadcastReceiver = new ResumeApplicationBroadcastReceiver();
    private boolean interstitialAdHasShown;
    private boolean skipBrandingDelay;
    private boolean clearCache;
    private EulaDialog eulaDialog;

    @Override
    public void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.splash_activity);

        StaticMainThreadReference.initGlobalVariables(this);

        dispatchTracker();

        if (savedInstanceState != null)
        {
            setSkipBrandingDelay(savedInstanceState.getBoolean(AppIntent.SPLASH_ACTIVITY_STATE.toString(), false));
        }

        setClearCache(getIntent().getBooleanExtra(AppIntent.DESTROY_CACHE_ON_RESTART.toString(), false));

        registerReceiver(registrationInformationBroadcastReceiver, new IntentFilter(AppIntent.REGISTRATION_INFORMATION_INTENT.toString()));
        registerReceiver(resumeApplicationBroadcastReceiver, new IntentFilter(AppIntent.SPLASH_ACTIVITY_RESUME_APPLICATION.toString()));

        final UserProfileStore userProfileStore = new UserProfileStore(SplashActivity.this);

        final Handler handler = new Handler()
        {
            @Override
            public void handleMessage(Message message)
            {
                int what = message.what;

                if (what == AppHandler.FAILED_REGISTRATION_DIALOG.ordinal())
                {
/*                    ApAlertDialogs.inform(SplashActivity.this, getString(R.string.registration_failed), getString(R.string.please_try_again_later), getString(R.string.ok), new Runnable()
                    {

                        @Override
                        public void run()
                        {
                            Log.e("SplashActivity", "Registration failed. The application cannot continue.");
                            finish();
                            return;
                        }
                    }); */
                }
                else if (what == AppHandler.EULA_AGREE.ordinal())
                {
                    eulaDialog.dismiss();
                    userProfileStore.setEulaAccepted(true);
                    initializeApplication(this);
                }
                else if (what == AppHandler.EULA_EXIT.ordinal())
                {
                    eulaDialog.dismiss();
                    userProfileStore.setEulaAccepted(false);
                    finish();
                }
            }
        };

        if (userProfileStore.isEulaAccepted())
        {
            initializeApplication(handler);
        }
        else
        {

            if (eulaDialog != null) eulaDialog.dismiss();

            eulaDialog = new EulaDialog(SplashActivity.this, handler);
            eulaDialog.show();
        }
    }

    private void dispatchTracker()
    {
        //GAnalyticsTracker tracker = new GAnalyticsTracker(getString(R.string.google_analytics_key), SplashActivity.this);
        //tracker.dispatchTracking(GAnalyticsAction.PLATFORM, GAnalytics.EVENT, Build.MANUFACTURER);
        //tracker.stop();
    }

    @Override
    protected void onResume()
    {
        super.onResume();

        BackgroundContextService.resetStartTimeInBackground(SplashActivity.this);
    }


    private void initializeApplication(final Handler handler)
    {
        final Thread splashThread = new SplashThread(handler, isClearCache());
        splashThread.start();
    }

    @Override
    protected void onSaveInstanceState(Bundle outState)
    {
        outState.putBoolean(AppIntent.SPLASH_ACTIVITY_STATE.toString(), isSkipBrandingDelay());
        super.onSaveInstanceState(outState);
    }

    @Override
    protected void onDestroy()
    {
        unregisterReceiver(registrationInformationBroadcastReceiver);
        unregisterReceiver(resumeApplicationBroadcastReceiver);
        super.onDestroy();

    }

    private void continueToApplication()
    {

    	/* if adding editions...
        if (EditionUtils.getEditions() == null)
        {
            finish();
        }
        */
        

    	/*

        final Timer timer = new Timer(true);
        timer.schedule(new TimerTask()
        {

            @Override
            public void run()
            {

                // Run Content Update
                //ContentUpdate.checkForUpdate(SplashActivity.this);
            	// here should download new puzzles

                // Run Content Purge
                //ContentPurge.purge(SplashActivity.this);
            	// here should delete old puzzles???
            }
        }, Dates.MINUTE * 10);
        */

        final UserProfileStore userProfileStore = new UserProfileStore(SplashActivity.this);

        /* should check the following mechanism to see whether or not we should upgrade to a new version 
        if (Numbers.isSet(getResources().getInteger(R.integer.capi_upg), userProfileStore.getRegistration().getApiStatus()))
        {

            // Show upgrade notification on first app launch since set and every 5th app launch
            int appLaunches = userProfileStore.getApplicationLaunches();
            userProfileStore.setApplicationLaunches(appLaunches + 1);

            if (appLaunches % 5 == 0)
            {
                ApAlertDialogs.inform(this, getString(R.string.update_available), getString(R.string.update_available_message), getString(R.string.ok), new Runnable()
                {

                    public void run()
                    {
                        finishSplash();
                    }
                });
            }
            else
            {
                finishSplash();
            }
        }
        else
        {
            finishSplash();
        }*/
        
        finishSplash();
    }

    private void finishSplash()
    {
        logger.d("finishSplash()");
        final Intent intent = new Intent(SplashActivity.this, SlidePuzzleActivity.class); // should change to be the home screen activity
        intent.putExtra(AppIntent.FRONTPAGE_DOWNLOADING.toString(), true);
        startActivity(intent);
        finish();
    }

    /* should only show this if we need to get the user's gps locations???
     *  why would we do this?
     *  
     *  
     */
    /*
    private void showLocationDialog(final Handler handler)
    {
        final LocationHistoryHelper locationHistoryHelper = LocationHistoryHelper.getInstance(this);
        final List<Location> locationHistory = locationHistoryHelper.loadFullHistory();

        if (locationHistory.isEmpty())
        {
            SplashActivity.this.runOnUiThread(new Runnable()
            {

                @Override
                public void run()
                {
                    updateInformationTextView(getString(R.string.select_location));

                    if (locationDialog != null) locationDialog.dismiss();

                    //
                    final LocationManager manager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);

                    if (manager.isProviderEnabled(LocationManager.GPS_PROVIDER) || manager.isProviderEnabled(LocationManager.NETWORK_PROVIDER))
                    {
                        locationDialog = new LocationDialog(SplashActivity.this, handler, false, true);
                    }
                    else
                    {
                        locationDialog = new LocationDialog(SplashActivity.this, handler, false, false);
                    }

                    locationDialog.show();
                }
            });
        }
        else
        {

            if (ApService.isAp(SplashActivity.this) && !isInterstitialAdHasShown())
            {
            }
            else
            {
                continueToApplication();
            }
        }
    }
    */

  
      private final class RegistrationInformationBroadcastReceiver extends BroadcastReceiver
    {

        @Override
        public void onReceive(Context context, Intent intent)
        {
            //String update = intent.getStringExtra(RegistrationStatus.INFORMATION_UPDATE.toString());
            //updateInformationTextView(update);
        }
    }

    private final class ResumeApplicationBroadcastReceiver extends BroadcastReceiver
    {

        @Override
        public void onReceive(Context context, Intent intent)
        {
            continueToApplication();
        }
    }

  
    private final class SplashThread extends Thread
    {
        private final Handler handler;
        private final boolean clearCache;

        private final String SHOW_EDITION = "show_edition";
        private final String SHOW_LOCATION = "show_location";
        private final String EXIT = "exit";

        private boolean active = true;
        private int splashTime = 3000;

        public SplashThread(Handler handler, boolean clearCache)
        {
            this.handler = handler;
            this.clearCache = clearCache;
        }

        @Override
        public void run()
        {
            String action = EXIT;
            long startTime = System.currentTimeMillis();

            if (clearCache)
            {
                //final MergedContentHelper.ContentHelper contentHelper = MergedContentHelper.getInstance(SplashActivity.this).getContentHelper();
                //contentHelper.destroy();

                //MediaService.destroy(SplashActivity.this);

            }

           // final RegistrationStatus registrationStatus = RegistrationUtils.register(SplashActivity.this);

            final UserProfileStore userProfileStore = new UserProfileStore(SplashActivity.this);

            //if (userProfileStore.getRegistration() != null && userProfileStore.getRegistration().getEdition() != null && userProfileStore.getRegistration().getEdition().getName() != null)
            //{
            //    logger.d("edition: " + userProfileStore.getRegistration().getEdition().getName());
            //}

            //if (registrationStatus == RegistrationStatus.SUCCESSFUL)
            //{
              //  BlockUtils.downloadHierarchy(SplashActivity.this, true);
               // action = SHOW_LOCATION;
            //}
            //else if (registrationStatus == RegistrationStatus.UNSUCCESSFUL)
            //{
              //  action = EXIT;
               // handler.sendEmptyMessage(AppHandler.FAILED_REGISTRATION_DIALOG.ordinal());
            //}

            long finishTime = System.currentTimeMillis();

        	// this continue to application would be in the handler....
       	    continueToApplication();
       	    
            if (EXIT.equals(action)) return;

            // Delay to show the splash screen for branding purposes
            long splashDuration = splashTime - (finishTime - startTime);

            try
            {

                if (!isSkipBrandingDelay())
                {
                    int waited = 0;

                    while (active && (waited < splashDuration))
                    {
                        sleep(100);

                        if (active)
                        {
                            waited += 100;
                        }
                    }

                    setSkipBrandingDelay(true);
                }
                
            }
            catch (InterruptedException e)
            {
                e.printStackTrace();
            }
            finally
            {

                if (SHOW_LOCATION.equals(action))
                {
                    //showLocationDialog(handler);
 
                }
            }

        }
   	 
    }

    private void setSkipBrandingDelay(boolean skipBrandingDelay)
    {
        this.skipBrandingDelay = skipBrandingDelay;
    }

    private boolean isSkipBrandingDelay()
    {
        return skipBrandingDelay;
    }

    private void setClearCache(boolean clearCache)
    {
        this.clearCache = clearCache;
    }

    private boolean isClearCache()
    {
        return clearCache;
    }

}
