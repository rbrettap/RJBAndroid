package com.rjb;

import com.rjb.service.store.UserProfileStore;
import com.rjb.utils.Dates;

import java.util.HashMap;
import java.util.Map;

public class Enums {

	/**
	 * added for GoogleAnalytics
	 * @author alpha
	 *
	 */
	public static enum GAnalytics{
		EVENT,PAGEVIEW,
	}
	
	public static enum GAnalyticsAction{
		PLATFORM,
		STORY_VIEW,
		CATEGORY,
		LOCAL_LOCATION,
		LOCAL_SOURCE,
		CUSTOMIZE_FCLICK,
		CUSTOMIZE_FSAVE,
		CUSTOMIZE_TCLICK,
		CUSTOMIZE_TSAVE,
		SHARE_STORY,
		SAVED_STORY,
		STORY_SOURCE_FRONT,
		STORY_SOURCE_CATEGORY,
		STORY_SOURCE_SWIPE,
		VIDEO,
		PHOTO,
	}

	public static enum AppIntent {
		ADVERTISEMENT_FINISHED_DOWNLOADING_INTENT,
		ADVERTISEMENT_ID,
		ARTICLE_ACTIVITY_CONTENT_IDS,
		ARTICLE_ACTIVITY_SAVED_CONTENT,
		ARTICLE_ACTIVITY_STARTING_CONTENT_INDEX,
		ARTICLE_LIST_ACTIVITY_BLOCK_ID,
		ARTICLE_LIST_PAGE_NUM,
		ARTICLE_MEDIA_CONTENT_ID,
		ARTICLE_MEDIA_FINISHED_DOWNLOADING_INTENT,
		BREAKING_NEWS_CONTENT_ID,
		BREAKING_NEWS_RECEIVED,
		CAMERA_PICTURE_FILE_PATH,
		CAMERA_PICTURE_TAKEN_INTENT,
		CASUAL_SELECTED_ARTICLE_INDEX,
		CASUAL_SELECTED_BLOCK_INDEX,
		CATEGORY_DESCRIPTION,
		CONTENT_FAILED_DOWNLOADING_INTENT,
		CONTENT_FINISHED_DOWNLOADING_INTENT,
		CONTENT_FINISHED_DOWNLOADING_MESSAGE,
		CONTENT_STARTED_DOWNLOADING_INTENT,
		CONTENT_STARTED_DOWNLOADING_LOADING_TEXT,
		CONTENT_DOWNLOADING_LOAD_ID,
		DESTROY_CACHE_ON_RESTART,
		EMBEDDED_WEB_BROWSER_URL,

		// Special case needed for 1.5 since 1.5 does not have this Intent built in although it will recognize the value.
		FLAG_ACTIVITY_NO_ANIMATION {
			public String toString() {return "65536";}
		},

		FONT_SIZE_CHANGED,
		FRONTPAGE_DOWNLOADING,
		FRONTPAGE_OPTIONS_NEW_BLOCK_IDS,
		FRONTPAGE_OPTIONS_NEW_ITEM_COUNT,
		FRONTPAGE_OPTIONS_SAVED,
		HORIZONTAL_BLOCK_TAB_SCROLL_POSITION,
		KILL_ALL_ACTIVITIES,
		LOADING_BAR_SHOWING,
		LOADING_BAR_TEXT,
		LOCATION_FINISHED_SEARCHING_INTENT,
		LOCATION_LATITUDE,
		LOCATION_LONGITUDE,
		MEDIA_FAILED_DOWNLOADING_INTENT,
		MEDIA_FINISHED_DOWNLOADING_INTENT,
		MEDIA_IS_DOWNLOADING_INTENT,
		MEDIA_URL,
		NOTIFICATION_ID,
		PHOTO_ACTIVITY_MEDIA_IDS,
		PHOTO_ACTIVITY_MEDIA_INDEX,
		PHOTO_ACTIVITY_SAVED_PHOTO,
		PHOTO_CAPTURE_REQUEST,
		PHOTO_CHOOSE_REQUEST,
		PUBLISHER_MEDIA_FINISHED_DOWNLOADING_INTENT,
		RATING_CHANGED,
		RATING_CHANGED_ARTICLE_GUID,
		REGISTRATION_INFORMATION_INTENT,
		RHYTHM_BANNER_FINISHED_DOWNLOADING,
		SEARCH_ACTIVITY_BLOCK_ID,
		SEARCH_ACTIVITY_BLOCK_IDS,
		SEARCH_CONTENT_IDS,
		SEARCH_FINISHED_INTENT,
		SEARCH_QUERY,
		SELECTED_TAB,
		SELECTED_CATEGORY,
		SEND_CAMERA_CAPTURE_FILE_NAME,
		SEND_EMAIL,
		SEND_HOME_ADDRESS,
		SEND_IMAGE_PATH,
		SEND_NAME,
		SEND_PHONE,
		SEND_YOUR_MESSAGE,
		SLIDESHOW_PLAYING,
		SLIDESHOW_TRANSITION,
		SPLASH_ACTIVITY_INTERSTITIAL_AD_STATE,
		SPLASH_ACTIVITY_RESUME_APPLICATION,
		SPLASH_ACTIVITY_SHOW_AD,
		SPLASH_ACTIVITY_STATE,
		THUMBNAIL_FINISHED_DOWNLOADING_INTENT,
		VIDEO_STREAM_URL,
		VIDEO_STREAM_TITLE,
		WEATHER_RADAR_IMAGE_URL,
		WEATHER_UNITS_CHANGED
	}

	public static enum AppHandler {
		FAILED_REGISTRATION_DIALOG,
		EDITION_DIALOG_CLOSED,
		LOCATION_DIALOG_CLOSED,
		EULA_AGREE,
		EULA_EXIT
	}

	public static enum Preferences {
		SESSION,


		/* User Profile */
		USER_PROFILE {
			public String toString() {return UserProfileStore.class.getName();};
		},
		USER_PROFILE_FONT_SCALE,
		USER_PROFILE_WEATHER_UNIT_CODE,
		USER_PROFILE_TWITTER_USERNAME,
		USER_PROFILE_TWITTER_TOKEN,
		USER_PROFILE_TWITTER_TOKEN_SECRET,
		USER_PROFILE_FACEBOOK_ACCESS_TOKEN,
		USER_PROFILE_FACEBOOK_EXPIRES_IN,
		USER_PROFILE_EULA_ACCEPTED,
		USER_PROFILE_APPLICATION_LAUNCHES,
		USER_PROFILE_WIDGET_REFRESH_INTERVAL,
		USER_PROFILE_SHOW_TABS_IN_ARTICLE_VIEW,
		USER_PROFILE_NOTIFICATIONS_ENABLED,
		UI_SAVED_ITEMS_STATE;
	}
	
	public static enum Datatype {
		NULL,
		INTEGER,
		REAL,
		TEXT,
		BLOB
	}
	
	// Do not change the order of this list unless it corresponds with R.array.widgetlist_refresh_interval_options.
	public static enum RefreshInterval {
		minutes_10 {
			public long getMillis() {return Dates.MINUTE * 10;}
		},
		minutes_15 {
			public long getMillis() {return Dates.MINUTE * 15;}
		},
		minutes_30 {
			public long getMillis() {return Dates.MINUTE * 30;}
		},
		hours_1 {
			public long getMillis() {return Dates.HOUR * 1;}
		},
		hours_12 {
			public long getMillis() {return Dates.HOUR * 12;}
		},
		days_1 {
			public long getMillis() {return Dates.DAY * 1;}
		};

		static final Map<Integer, RefreshInterval> lookupByOrdinal = new HashMap<Integer, RefreshInterval>();
		static final Map<String, RefreshInterval> lookupByString = new HashMap<String, RefreshInterval>();

		static {

			for (RefreshInterval refreshInterval : RefreshInterval.values()) {
				lookupByOrdinal.put(refreshInterval.ordinal(), refreshInterval);
				lookupByString.put(refreshInterval.toString(), refreshInterval);
			}
		}

		public static RefreshInterval lookupByOrdinal(int ordinal) {
			return lookupByOrdinal.get(ordinal);
		}

		public static RefreshInterval lookupByString(String name) {
			return lookupByString.get(name);
		}

		public abstract long getMillis();
	}
	
	// 
	public static enum DownloadResult {
		COMPLETED,
		ALREADY_IN_PROGRESS,
		NULL_OR_EMPTY_PARAMETERS,
		NO_NETWORK_CONNECTION,
		FAILED;

		static final Map<Integer, DownloadResult> lookupByOrdinal = new HashMap<Integer, DownloadResult>();
		static final Map<String, DownloadResult> lookupByString = new HashMap<String, DownloadResult>();

		static {

			for (DownloadResult downloadResult : DownloadResult.values()) {
				lookupByOrdinal.put(downloadResult.ordinal(), downloadResult);
				lookupByString.put(downloadResult.toString(), downloadResult);
			}
		}

		public static DownloadResult lookupByOrdinal(int ordinal) {
			return lookupByOrdinal.get(ordinal);
		}

		public static DownloadResult lookupByString(String name) {
			return lookupByString.get(name);
		}
	};
	
	public static enum SavedItemsState {
		Stories,
		Photos;
		
		static final Map<Integer, SavedItemsState> lookupByOrdinal = new HashMap<Integer, SavedItemsState>();
		static final Map<String, SavedItemsState> lookupByString = new HashMap<String, SavedItemsState>();

		static {

			for (SavedItemsState savedItemsState : SavedItemsState.values()) {
				lookupByOrdinal.put(savedItemsState.ordinal(), savedItemsState);
				lookupByString.put(savedItemsState.toString(), savedItemsState);
			}
		}

		public static SavedItemsState lookupByOrdinal(int ordinal) {
			return lookupByOrdinal.get(ordinal);
		}

		public static SavedItemsState lookupByString(String name) {
			return lookupByString.get(name);
		}
	}
}
