package com.rjb.service.store;

import android.content.Context;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import com.rjb.Enums.Preferences;
import com.rjb.Enums.RefreshInterval;
import com.rjb.Enums.SavedItemsState;
import com.rjb.service.Logger;

import java.util.Arrays;

public class UserProfileStore {
	private static final Logger logger = new Logger(UserProfileStore.class);
	private final Context context;

    private static SharedPreferences cachedPrefs;

	public UserProfileStore(Context context) {
		this.context = context;
	}

	private Editor getEditor() {
		final SharedPreferences preferences = getPreferences();
		return preferences.edit();
	}

    /**
     * KPG: This was being called on each row in the ContentAdapter.  Really bad. This solution is not ideal (io should be done in a background thread), but should
     * function well.
     * @return
     */
	private synchronized SharedPreferences getPreferences() {
        if(cachedPrefs == null)
            cachedPrefs = context.getSharedPreferences(Preferences.USER_PROFILE.toString(), Context.MODE_PRIVATE);
		return cachedPrefs;
	}

	public final void setEulaAccepted(final boolean eulaAccepted) {
		final Editor editor = getEditor();
		editor.putBoolean(Preferences.USER_PROFILE_EULA_ACCEPTED.toString(), eulaAccepted);
		editor.commit();
	}

	public final boolean isEulaAccepted() {
		final SharedPreferences preferences = getPreferences();
		return preferences.getBoolean(Preferences.USER_PROFILE_EULA_ACCEPTED.toString(), false);
	}

	public final void setApplicationLaunches(final int applicationLaunches) {
		final Editor editor = getEditor();
		editor.putInt(Preferences.USER_PROFILE_APPLICATION_LAUNCHES.toString(), applicationLaunches);
		editor.commit();
	}

	public final int getApplicationLaunches() {
		final SharedPreferences preferences = getPreferences();
		return preferences.getInt(Preferences.USER_PROFILE_APPLICATION_LAUNCHES.toString(), 0);
	}


	public final void setFontScale(int size) {
		final Editor editor = getEditor();
		editor.putInt(Preferences.USER_PROFILE_FONT_SCALE.toString(), size);
		editor.commit();
	}

	public final int getFontScale() {
		final SharedPreferences preferences = getPreferences();
		return preferences.getInt(Preferences.USER_PROFILE_FONT_SCALE.toString(), 2);
	}


	public final void setTwitterUsername(final String twitterUsername) {
		final Editor editor = getEditor();
		editor.putString(Preferences.USER_PROFILE_TWITTER_USERNAME.toString(), twitterUsername);
		editor.commit();
	}

	public final String getTwitterUsername() {
		final SharedPreferences preferences = getPreferences();
		return preferences.getString(Preferences.USER_PROFILE_TWITTER_USERNAME.toString(), null);
	}

	public final void setTwitterToken(final String twitterToken) {
		final Editor editor = getEditor();
		editor.putString(Preferences.USER_PROFILE_TWITTER_TOKEN.toString(), twitterToken);
		editor.commit();
	}

	public final String getTwitterToken() {
		final SharedPreferences preferences = getPreferences();
		return preferences.getString(Preferences.USER_PROFILE_TWITTER_TOKEN.toString(), null);
	}

	public final void setTwitterTokenSecret(final String twitterTokenSecret) {
		final Editor editor = getEditor();
		editor.putString(Preferences.USER_PROFILE_TWITTER_TOKEN_SECRET.toString(), twitterTokenSecret);
		editor.commit();
	}

	public final String getTwitterTokenSecret() {
		final SharedPreferences preferences = getPreferences();
		return preferences.getString(Preferences.USER_PROFILE_TWITTER_TOKEN_SECRET.toString(), null);
	}

	public final void setFacebookAccessToken(final String accessToken) {
		final Editor editor = getEditor();
		editor.putString(Preferences.USER_PROFILE_FACEBOOK_ACCESS_TOKEN.toString(), accessToken);
		editor.commit();
	}

	public final String getFacebookAccessToken() {
		final SharedPreferences preferences = getPreferences();
		return preferences.getString(Preferences.USER_PROFILE_FACEBOOK_ACCESS_TOKEN.toString(), null);
	}

	public final void setFacebookExpiresIn(final String expiresIn) {
		final Editor editor = getEditor();
		editor.putString(Preferences.USER_PROFILE_FACEBOOK_EXPIRES_IN.toString(), expiresIn);
		editor.commit();
	}

	public final String getFacebookExpiresIn() {
		final SharedPreferences preferences = getPreferences();
		return preferences.getString(Preferences.USER_PROFILE_FACEBOOK_EXPIRES_IN.toString(), null);
	}

	public final void setWeatherUnitPreference(final int unitCode) {
		final Editor editor = getEditor();
		editor.putInt(Preferences.USER_PROFILE_WEATHER_UNIT_CODE.toString(), unitCode);
		editor.commit();
	}

	public final int getWeatherUnitPreference() {
		final SharedPreferences preferences = getPreferences();
		return preferences.getInt(Preferences.USER_PROFILE_WEATHER_UNIT_CODE.toString(), 0);
	}


	public final RefreshInterval getWidgetRefreshInterval() {
		final SharedPreferences preferences = getPreferences();
		return RefreshInterval.lookupByOrdinal((int) preferences.getLong(Preferences.USER_PROFILE_WIDGET_REFRESH_INTERVAL.toString(), 0));
	}

	public final void setWidgetRefreshIntervalInMillis(final RefreshInterval refreshInterval) {
		final Editor editor = getEditor();
		editor.putLong(Preferences.USER_PROFILE_WIDGET_REFRESH_INTERVAL.toString(), (long) refreshInterval.ordinal());
		editor.commit();
	}

	public final void setSavedItemsState(final SavedItemsState savedItemsState) {
		final Editor editor = getEditor();
		editor.putInt(Preferences.UI_SAVED_ITEMS_STATE.toString(), savedItemsState.ordinal());
		editor.commit();
	}

	public final SavedItemsState getSavedItemsState() {
		final SharedPreferences preferences = getPreferences();
		return SavedItemsState.lookupByOrdinal(preferences.getInt(Preferences.UI_SAVED_ITEMS_STATE.toString(), 0));
	}
	
	public final void setShowTabsInArticleView(final boolean showTabsInArticleView) {
		final Editor editor = getEditor();
		editor.putBoolean(Preferences.USER_PROFILE_SHOW_TABS_IN_ARTICLE_VIEW.toString(), showTabsInArticleView);
		editor.commit();
	}

	public final boolean isShowTabsInArticleView() {
		final SharedPreferences preferences = getPreferences();
		return preferences.getBoolean(Preferences.USER_PROFILE_SHOW_TABS_IN_ARTICLE_VIEW.toString(), false);
	}
	
	public final void setNotificationsEnabled(final boolean notificationsEnabled) {
		final Editor editor = getEditor();
		editor.putBoolean(Preferences.USER_PROFILE_NOTIFICATIONS_ENABLED.toString(), notificationsEnabled);
		editor.commit();
	}

	public final boolean isNotificationsEnabled() {
		final SharedPreferences preferences = getPreferences();
		return preferences.getBoolean(Preferences.USER_PROFILE_NOTIFICATIONS_ENABLED.toString(), true);
	}
}
