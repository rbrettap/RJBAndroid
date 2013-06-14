package com.rjb.service;

import android.content.Context;
import com.rjb.service.store.UserProfileStore;

public class Fonts {

	public static final float getArticleTitleFontSize(Context context) {
		return getArticleTextFontSize(context) + 2;
	}

	public static final int getArticleTextFontSize(Context context) {
		UserProfileStore userProfileStore = new UserProfileStore(context);
		int fontScale = userProfileStore.getFontScale();
		int fontSize = 16;

		switch (fontScale) {
		
			case 0:
				fontSize = 8;
				break;
			case 1:
				fontSize = 12;
				break;
			case 2:
				fontSize = 14;
				break;
			case 3:
				fontSize = 16;
				break;
			case 4:
				fontSize = 22;
				break;
		}

		return fontSize;
	}

	public static final float getArticleAgeFontSize(Context context) {
		return getArticleListAgeFontSize(context);
	}

	public static final float getArticleBylineFontSize(Context context) {
		return getArticleAgeFontSize(context) - 2;
	}

	public static final float getCategoryBarFontSize(Context context) {
        UserProfileStore userProfileStore = new UserProfileStore(context);
        int fontScale = userProfileStore.getFontScale();
        int fontSize = 18;

        switch (fontScale) {

            case 0:
                fontSize = 14;
                break;
            case 1:
                fontSize = 16;
                break;
            case 2:
                fontSize = 18;
                break;
            case 3:
                fontSize = 20;
                break;
            case 4:
                fontSize = 22;
                break;
        }

        return fontSize;
	}

	public static final float getArticleListTitleFontSize(Context context) {
		return getArticleTitleFontSize(context);
	}

	public static final float getArticleListSummaryFontSize(Context context) {
		return getArticleTextFontSize(context);
	}

	public static final float getArticleListAgeFontSize(Context context) {
		return getArticleListSummaryFontSize(context) - 2;
	}

	public static final float getPhotoTitleFontSize(Context context) {
		return getArticleTitleFontSize(context);
	}

	public static final float getPhotoCaptionFontSize(Context context) {
		return getArticleTextFontSize(context) - 2;
	}

	public static final float getPhotoCountFontSize(Context context) {
		return getPhotoTitleFontSize(context);
	}
}
