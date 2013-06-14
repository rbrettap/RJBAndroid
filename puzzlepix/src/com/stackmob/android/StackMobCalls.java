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
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.sql.Date;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Hashtable;
import java.util.List;
import java.util.Map;

import android.app.Activity;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.Toast;

import com.stackmob.android.sdk.common.StackMobCommon;
import com.stackmob.sdk.api.StackMob;
import com.stackmob.sdk.callback.StackMobCallback;
import com.stackmob.sdk.exception.StackMobException;
import com.stackmob.sdk.model.StackMobModel;
import com.stackmob.sdk.push.StackMobPushToken;
import com.stackmob.sdk.api.StackMobQuery; 
import com.stackmob.sdk.api.StackMobFile; 

public class StackMobCalls extends Activity{

	private StackMob stackmob;
	
	public StackMobCalls(Context appContext)
	{
		StackMobCommon.API_KEY = "47e8d70f-6996-43fb-b0cc-82e32e8bf725";
		StackMobCommon.API_SECRET = "1652d3ba-1203-4eed-8e3e-9a72b3144480";
		StackMobCommon.USER_OBJECT_NAME = "user";
		StackMobCommon.API_VERSION = 0; 
		   
		StackMobCommon.API_URL_FORMAT = "api.mob1.stackmob.com";
		StackMobCommon.PUSH_API_URL_FORMAT = "push.mob1.stackmob.com";
		if (!StackMobCommon.initialized)
			StackMobCommon.init(appContext);
		stackmob = StackMobCommon.getStackMobInstance();
	}
	
	public void createUser(String login, String password, StackMobCallback standardToastCallback) {
		Map<String, String> params = new HashMap<String, String>();
		params.put("username", login);
		params.put("password", password);
		stackmob.post("user", params, standardToastCallback);
	}
	
	public void createFacebookUser(String login, String fbToken, StackMobCallback standardToastCallback) {
		stackmob.registerWithFacebookToken(fbToken, login, standardToastCallback);
	}

	public void resetPassword(String oldPassword, String newPassword, StackMobCallback standardToastCallback) {
		stackmob.resetPassword(oldPassword, newPassword, standardToastCallback);
	}
	
	public void deleteUser(String login, StackMobCallback standardToastCallback) {
		stackmob.delete("user", login, standardToastCallback);
	}
	
	public void deletePuzzle(String puzzleId, StackMobCallback standardToastCallback) {
		stackmob.delete("puzzles", puzzleId, standardToastCallback);
	}
	
	public String getLoggedInUser() {
		return stackmob.getLoggedInUser();
	}
	
	public boolean isUserLoggedIn() {
		return stackmob.isLoggedIn();
	}
	
	public void logoutUser(StackMobCallback standardToastCallback) {
		stackmob.logout(standardToastCallback);
	}
	
	public void userlogin(String login, String password, StackMobCallback standardToastCallback) {
		Map<String, String> params = new HashMap<String, String>();
		params.put("username", login);
		params.put("password", password);
		stackmob.login(params, standardToastCallback);
	}
	
	public void userFacebookLogin(String token, StackMobCallback standardToastCallback) {
		stackmob.facebookLogin(token, standardToastCallback);
	}
	
	public void createPuzzle(String message, boolean is_timed, int size, String user_created_by, List<String> users_sent_to, double solve_time, String puzzle_winner, StackMobFile imageFile, StackMobCallback standardToastCallback) {	
		HashMap<String, Object> fields = new HashMap<String, Object>();
		 
		// Fields ("content", "views") must be lower case, alpha numeric, and be 3-25 characters.
		fields.put("message", message);
		fields.put("is_timed", is_timed);
		fields.put("size", size);
		fields.put("user_created_by", user_created_by);
		fields.put("users_sent_to", users_sent_to);
		fields.put("solve_time", solve_time);
		fields.put("puzzle_winner", puzzle_winner);
        
        String datastring = imageFile.toString();
		fields.put("puzzle_image", datastring);
		fields.put("sm_owner", getLoggedInUser());
        
		stackmob.post("puzzles", fields, standardToastCallback);
	}
	
	//public void createPuzzleStats(double solve_time, String puzzle_winner, List<String> users_sent_to, StackMobCallback standardToastCallback) {	
	//	HashMap<String, Object> fields = new HashMap<String, Object>();
		 
		// Fields ("content", "views") must be lower case, alpha numeric, and be 3-25 characters.
	//	fields.put("solve_time", solve_time);
	//	fields.put("puzzle_winner", puzzle_winner);
	//	fields.put("users_sent_to", users_sent_to);
        
	//	stackmob.post("puzzle_stats", fields, standardToastCallback);
	//}
	
	//public void updatePuzzleStats(String puzzleStatsId, double solve_time, String puzzle_winner, List<String> users_sent_to, StackMobCallback standardToastCallback) {	
	//	HashMap<String, Object> fields = new HashMap<String, Object>();
		 
		// Fields ("content", "views") must be lower case, alpha numeric, and be 3-25 characters.
	//	fields.put("solve_time", solve_time);
	//	fields.put("puzzle_winner", puzzle_winner);
	//	fields.put("users_sent_to", users_sent_to);
        
	//	stackmob.put("puzzle_stats", puzzleStatsId, fields, standardToastCallback);
	//}
	
	public void updatePuzzle(String puzzleId, String message, boolean is_timed, int size, String user_created_by, List<String> users_sent_to, double solve_time, String puzzle_winner, StackMobFile imageFile, StackMobCallback standardToastCallback) {	
		HashMap<String, Object> fields = new HashMap<String, Object>();
		 
		// Fields ("content", "views") must be lower case, alpha numeric, and be 3-25 characters.
		fields.put("message", message);
		fields.put("is_timed", is_timed);
		fields.put("size", size);
		fields.put("user_created_by", user_created_by);
		fields.put("users_sent_to", users_sent_to);
		fields.put("solve_time", solve_time);
		fields.put("puzzle_winner", puzzle_winner);
        
        String datastring = imageFile.toString();
		fields.put("puzzle_image", datastring);
		 
		stackmob.put("puzzles", puzzleId, fields, standardToastCallback);
	}
	
	public void getPuzzleMessage(String puzzleId, StackMobCallback standardToastCallback) {
		String[] fields = new String[]{"message"};
		StackMobQuery q = new StackMobQuery("puzzles/" + puzzleId).select(Arrays.asList(fields));
		stackmob.get(q, standardToastCallback);
	}
	
	public void getPuzzleImage(String puzzleId, StackMobCallback standardToastCallback) {
		String[] fields = new String[]{"puzzle_image"};
		StackMobQuery q = new StackMobQuery("puzzles/" + puzzleId).select(Arrays.asList(fields));
		stackmob.get(q, standardToastCallback);
	}
	
	public void updatePuzzleImage(String puzzleId, StackMobFile imageFile, StackMobCallback standardToastCallback) {
		  Map<String, String> args = new HashMap<String, String>();
	      String datastring = imageFile.toString();
		  args.put("puzzle_image", datastring);
		  stackmob.put("puzzles", puzzleId, args, standardToastCallback);
	}
	
	public void getPuzzleObject(String puzzleId, StackMobCallback standardToastCallback) {
		StackMobQuery q = new StackMobQuery("puzzles/" + puzzleId);
		stackmob.get(q, standardToastCallback);
	}
	
	//public void getPuzzleObjectWithStats(String puzzleId, StackMobCallback standardToastCallback)
	//{
	//	StackMobQuery q = new StackMobQuery("puzzles/" + puzzleId).expandDepthIs(2);
 //
	//	stackmob.get(q, standardToastCallback);
	//}
	
	public void getUserObject(String login, StackMobCallback standardToastCallback) {
		StackMobQuery q = new StackMobQuery("users/" + login);
		stackmob.get(q, standardToastCallback);
	}
	
	public void getAllPuzzlesCreatedByUser(String login, StackMobCallback standardToastCallback) {
		StackMobQuery q = new StackMobQuery("puzzles").fieldIsEqualTo("user_created_by", login);
		stackmob.get(q, standardToastCallback);
	}

	public void getAllPuzzlesSolvedByUser(String login, StackMobCallback standardToastCallback) {
		StackMobQuery q = new StackMobQuery("puzzles").fieldIsEqualTo("puzzle_winner", login);
		stackmob.get(q, standardToastCallback);
	}
	
	public void getAllPuzzlesSentToUser(String login, StackMobCallback standardToastCallback) {
		StackMobQuery q = new StackMobQuery("puzzles").fieldIsIn("users_sent_to", Arrays.asList(login));
		stackmob.get(q, standardToastCallback);
	}
	
	public class Puzzle extends StackMobModel {
	     
        private String message;
		private boolean is_timed;
		private int puzzle_size;
		
		private String user_created_by;
		private List<String> users_sent_to;
		private double solve_time;
		private String puzzle_winner;
	     
	    public Puzzle(String message, boolean is_timed, int puzzle_size, String user_created_by, List<String> users_sent_to, double solve_time, String puzzle_winner) 
		{
	        super(Puzzle.class);
	        this.message = message;
	        this.is_timed = is_timed;
			this.puzzle_size = puzzle_size;
			
			this.user_created_by = user_created_by;
			this.users_sent_to = users_sent_to;
			this.solve_time = solve_time;
			this.puzzle_winner = puzzle_winner;
	        
	    }
	 
	    //Add whatever setters/getters/other functionality you want here
	}
	
	public void TestCreatePuzzleSchema()
	{
        String message = "This is a game message.";
		boolean is_timed = true;
		int puzzle_size = 3;
		
		String user_created_by = "craig2";
		List<String> users_sent_to = Arrays.asList("craig3", "craig4");
		double solve_time = 3.3;
		String puzzle_winner = "craig3";
		
		Puzzle puzz = new Puzzle(message, is_timed, puzzle_size, user_created_by, users_sent_to, solve_time, puzzle_winner);
		puzz.save();
	}
}
