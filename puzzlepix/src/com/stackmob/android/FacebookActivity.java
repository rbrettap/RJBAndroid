/**
 * Copyright 2011 StackMob
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.stackmob.android;

import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;

import org.json.JSONException;
import org.json.JSONObject;

import com.facebook.android.DialogError;
import com.facebook.android.Facebook;
import com.facebook.android.Facebook.DialogListener;
import com.facebook.android.FacebookError;
import com.facebook.android.Util;
import com.stackmob.android.sdk.callback.StackMobFacebookCallback;
import com.stackmob.sdk.api.StackMob;
import com.stackmob.sdk.exception.StackMobException;

import static com.stackmob.android.sdk.common.StackMobCommon.*;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import com.rjb.puzzlepix.R;

import com.stackmob.sdk.exception.StackMobException;


public class FacebookActivity extends Activity {
	public static String FACEBOOK_APP_ID = "285212258242810";
	
	private StackMobCalls smc;
	
	    Facebook facebook = new Facebook(FACEBOOK_APP_ID);
		private static final String TAG = FacebookActivity.class.getCanonicalName();
		private void setFacebookToken(String accessToken, long accessTokenExpiry, Throwable e) {
			
				if(e != null) {
					Log.i(TAG, "request had exception " + e.getMessage());
				}
				else {
					Log.i(TAG, "retrieved Facebook token " + accessToken);
				}
		}
	    
	    @Override
	    public void onCreate(Bundle savedInstanceState) {
	        super.onCreate(savedInstanceState);
	        setContentView(R.layout.main);
	        
			smc = new StackMobCalls(this.getApplicationContext());

	        facebook.authorize(this, new String[] {"email", "publish_checkins" },new DialogListener() {
	            @Override
	            public void onComplete(Bundle values) {
	    			 setFacebookToken(facebook.getAccessToken(), facebook.getAccessExpires(), null);
	    			 
	    			 String response = null;
	    			 
	    			 try
	    			 {
	    				 response =facebook.request("me");
	    			 }
	    			 catch (IOException e)
	    			 {
		    			 Log.i(TAG, "Error getting facebook email response: " + e.getMessage());	 
	    			 }
	    			 
	    			 JSONObject obj = null;
	    			 String useremail = null;
	    			 try
	    			 {
	    				 obj = Util.parseJson(response);
	    				 useremail=obj.getString("email");
	    			 }
	    			 catch (FacebookError e)
	    			 {
		    			 Log.i(TAG, "Facebook error getting facebook email response: " + e.getMessage());	
	    			 }
	    			 catch (JSONException e)
	    			 {
		    			 Log.i(TAG, "JSON getting facebook email response: " + e.getMessage());	
	    			 }
	    			 
	    			 Log.i(TAG, "Got facebook email " + useremail);	
					 Intent intent=new Intent();
					 intent.putExtra("token", facebook.getAccessToken());
					 intent.putExtra("login", useremail);
					 setResult(RESULT_OK, intent);
					 finish();

			}

			@Override
			public void onFacebookError(FacebookError error) {
				setFacebookToken(null, 0, error);
				Intent intent=new Intent();
				setResult(RESULT_CANCELED, intent);
				finish();
			}
			
			@Override
			public void onError(DialogError e) {
				setFacebookToken(null, 0, e);
				Intent intent=new Intent();
				setResult(RESULT_CANCELED, intent);
				finish();
			}
			
			@Override
			public void onCancel() {
				setFacebookToken(null, 0, new Exception("Cancelled"));
				Intent intent=new Intent();
				setResult(RESULT_CANCELED, intent);
				finish();
			}
	        });
	    }

	    @Override
	    public void onActivityResult(int requestCode, int resultCode, Intent data) {
	        super.onActivityResult(requestCode, resultCode, data);

	        facebook.authorizeCallback(requestCode, resultCode, data);
	    }
	}