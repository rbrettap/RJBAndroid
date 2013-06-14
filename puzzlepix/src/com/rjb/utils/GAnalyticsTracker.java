/**
 * 
 */
package com.rjb.utils;

import android.content.Context;
import android.util.Log;
import com.rjb.Enums;
import com.rjb.Enums.GAnalytics;
import com.rjb.Enums.GAnalyticsAction;
import com.rjb.service.Logger;
import com.google.android.apps.analytics.GoogleAnalyticsTracker;

/**
 * @author alpha
 *
 */
public class GAnalyticsTracker {

    protected GoogleAnalyticsTracker _tracker;
	protected Context context;
	private static final Logger logger = new Logger(GAnalyticsTracker.class);
	
	private final String DEVICE_PLATFORM = "Platform";
	private final String STORY_VIEW_HIT = "Story View Hit";
	private final String CATEGORY_HIT ="Category Hit";
	private final String LOCAL_LOCATION_HIT = "Local Location Hit";
	private final String LOCAL_SOURCE_HIT = "Local Source Hit";
	private final String CUSTOMIZE_FCLICK_HIT = "Customize FrontPage Click Hit";
	private final String CUSTOMIZE_FSAVE_HIT = "Customize FrontPage Save Hit";
	private final String CUSTOMIZE_TCLICK_HIT = "Customize Tabs Click Hit";
	private final String CUSTOMIZE_TSAVE_HIT = "Customize Tabs Save Hit ";
	private final String SHARE_STORY_HIT = "Share Story Hit";
	private final String SAVED_STORY_HIT = "Saved Story Hit";
	private final String STORY_SOURCE_FRONT_HIT ="Story Accessed From Front Page";
	private final String STORY_SOURCE_CATEGORY_HIT = "Story Accessed From Category Option";
	private final String STORY_SOURCE_SWIPE_HIT = "Story Accessed From Swipe Gesture";
	private final String VIDEO_HIT = "Video Hit";
	private final String PHOTO_HIT ="Photo Hit";


    public GAnalyticsTracker(final String analyticsKey, final Context context)
    {
        GAThreadPool.run(new Runnable()
        {
            @Override
            public void run()
            {
                try
                {
                    Log.i(GAnalyticsTracker.class.getSimpleName(), "initTracker");
                    initTracker(analyticsKey, context);
                }
                catch (Exception e)
                {
                    Log.e(GAnalyticsTracker.class.getSimpleName(), null, e);
                }
            }
        });
    }

    private void initTracker(String analyticsKey, Context context)
    {
        GoogleAnalyticsTracker tr = GoogleAnalyticsTracker.getInstance();
        try
        {
            tr.start(analyticsKey, context);
            setTracker(tr);
        }
        catch (Exception e)
        {
            //This happened once. Tracker needs a bigger review: https://touchtrack.co/r/1tgqu3k
            Log.e(GAnalyticsTracker.class.getSimpleName(), null, e);
        }
    }

    private synchronized GoogleAnalyticsTracker getTracker()
    {
        return _tracker;
    }

    private synchronized void setTracker(GoogleAnalyticsTracker _tracker)
    {
        this._tracker = _tracker;
    }

    public synchronized void dispatchTracking(final GAnalyticsAction action, final GAnalytics type, final String value)
    {
        GAThreadPool.run(new Runnable()
        {
            @Override
            public void run()
            {
                try
                {
                    Log.i(GAnalyticsTracker.class.getSimpleName(), "dispatchTrackingInternal("+ action.toString() +", "+ type.toString() +", "+ value +")");
                    dispatchTrackingInternal(action, type, value);
                }
                catch (Exception e)
                {
                    Log.e(GAnalyticsTracker.class.getSimpleName(), null, e);
                }
            }
        });
    }

    private void dispatchTrackingInternal(GAnalyticsAction action, GAnalytics type, String value)
    {
        try{
            switch(type){
                case EVENT: dispatchEventTracker(action, value);
                    break;
                case PAGEVIEW: dispatchPageViewTracker(action, value);
                    break;
            }
        }
        catch(Throwable t){
            Log.e(getClass().getSimpleName(), null, t);
        }
    }

    private void dispatchPageViewTracker(GAnalyticsAction action, String value){
		switch(action){
			case STORY_VIEW: logger.i("tracking storyView = " +value);
                GoogleAnalyticsTracker tr = getTracker();
                if(tr == null)
                    return;

                tr.setCustomVar(1,STORY_VIEW_HIT,value,1);
							 tr.trackPageView(value);
							 tr.dispatch();
					break;
			default: logger.i("Invalid GAnalyticsAction " +value +" no action taken");
			
		}
	}
	
	private void dispatchEventTracker(GAnalyticsAction action, String value){
        GoogleAnalyticsTracker tracker = getTracker();
        if(tracker == null)
            return;

		switch(action){
			case PLATFORM: logger.i("tracking platform  = " +value);
						if(value != null){
							tracker.trackEvent(DEVICE_PLATFORM,value,"platformEvent",101);
							tracker.dispatch();
						}
					break;
			case CATEGORY: logger.i("tracking category = " +value);
						if(value != null){
                            tracker.trackEvent(CATEGORY_HIT,value,"categoryEvent",102);
							tracker.dispatch();
						}
					break;
			
			case LOCAL_LOCATION: logger.i("Local Location = " +value);
					if(value != null){
                        tracker.trackEvent(LOCAL_LOCATION_HIT,value,"localLocationEvent",103);
						tracker.dispatch();
					}
					 
				break;
			case LOCAL_SOURCE: logger.i("Local Source = " +value);
					if(value != null){
                        tracker.trackEvent(LOCAL_SOURCE_HIT,value,"localSourceEvent",104);
						tracker.dispatch();
					}
					 
				break;
			
			case CUSTOMIZE_FCLICK: logger.i("Customize Front Page Click  = " +value);
					if(value != null){
						tracker.trackEvent(CUSTOMIZE_FCLICK_HIT,value,"customizeFrontPageClickEvent",105);
						tracker.dispatch();
					}
				 break;
				
			case CUSTOMIZE_FSAVE: logger.i("Customize Front Page Save = " +value);
					if(value != null){
						tracker.trackEvent(CUSTOMIZE_FSAVE_HIT,value,"customizeFrontPageSaveEvent",106);
						tracker.dispatch();
					}
				 break;	
			
			case CUSTOMIZE_TCLICK: logger.i("Customize Custom Tabs Click  = " +value);
					if(value != null){
						tracker.trackEvent(CUSTOMIZE_TCLICK_HIT,value,"customizeCustomTabsClickEvent",107);
						tracker.dispatch();
					}
				 break;
		
			case CUSTOMIZE_TSAVE: logger.i("Customize Custom Tabs Save = " +value);
					if(value != null){
						tracker.trackEvent(CUSTOMIZE_TSAVE_HIT,value,"customizeCustomTabsSaveEvent",108);
						tracker.dispatch();
					}
				 break;	
	
			case SAVED_STORY: logger.i("Saved Story = " +value);
					if(value != null){
						tracker.trackEvent(SAVED_STORY_HIT,value,"saveStoryEvent",109);
						tracker.dispatch();
					}
				break;	
			
			case SHARE_STORY: logger.i("Share Story = " +value);
					if(value != null){
						tracker.trackEvent(SHARE_STORY_HIT,value,"shareStoryEvent",110);
						tracker.dispatch();
					}
				break;	
			case STORY_SOURCE_FRONT: logger.i("Article Accessed From FrontPage = " +value);
					if(value != null){
						tracker.trackEvent(STORY_SOURCE_FRONT_HIT,value,"storySourceFrontPageEvent",111);
						tracker.dispatch();
					}
				break;	
			
			case STORY_SOURCE_CATEGORY: logger.i("Article Accessed From Category = " +value);
					if(value != null){
						tracker.trackEvent(STORY_SOURCE_CATEGORY_HIT,value,"storySourceCategoryEvent",112);
						tracker.dispatch();
					}
				break;	
			
			case STORY_SOURCE_SWIPE: logger.i("Article Accessed From Swipe Gesture = " +value);
					if(value != null){
						tracker.trackEvent(STORY_SOURCE_SWIPE_HIT,value,"storySourceSwipeGestureEvent",113);
						tracker.dispatch();
					}
				break;	
		 
			case PHOTO: logger.i("tracking photo = " +value);
						if(value != null){
							tracker.trackEvent(PHOTO_HIT,value,"photoEvent",114);
							tracker.dispatch();
						}
					break;
			case VIDEO: logger.i("tracking video = " +value);
				if(value != null){
					tracker.trackEvent(VIDEO_HIT,value,"videoEvent",115);
					tracker.dispatch();
				}
			break;
					
			default: logger.i("Invalid GAnalyticsAction dispatch Ignored");
		}
	}

	public synchronized void stop()
    {
        GAThreadPool.run(new Runnable()
        {
            @Override
            public void run()
            {
                try
                {
                    Log.i(GAnalyticsTracker.class.getSimpleName(), "stop");
                    GoogleAnalyticsTracker tracker = getTracker();
                    if(tracker == null)
                        return;

                    tracker.stop();
                }
                catch (Exception e)
                {
                    Log.e(GAnalyticsTracker.class.getSimpleName(), null, e);
                }
            }
        });
	}

    private static class GAThreadPool
    {
        private static SimpleThreadPoolProcessor pool = new SimpleThreadPoolProcessor(3);

        public static void run(Runnable runnable)
        {
            pool.addRunnable(runnable);
        }
    }
}
