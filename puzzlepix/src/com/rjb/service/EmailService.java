package com.rjb.service;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;

import com.rjb.*;


public class EmailService {
	private static final Logger logger = new Logger(EmailService.class);

 public static void send(Context context, String[] toAddresses, String subject, String body, Uri attachmentUri) {
 	logger.d("toAddresses: " + toAddresses);
 	logger.d("subject: " + subject);
  	logger.d("body: " + body);
  	logger.d("attachmentUri: " + attachmentUri);
   	
  Intent emailIntent = new Intent(Intent.ACTION_SEND);
  emailIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
  emailIntent.putExtra(Intent.EXTRA_EMAIL, toAddresses);
  emailIntent.putExtra(Intent.EXTRA_SUBJECT, subject);
  emailIntent.putExtra(Intent.EXTRA_TEXT, body);
  
  if (attachmentUri != null) {
  	emailIntent.setType("image/png");
   emailIntent.putExtra(Intent.EXTRA_STREAM, attachmentUri);
  }
  else {
  	emailIntent.setType("plain/text");
  }
 
  //context.startActivity(Intent.createChooser(emailIntent, context.getString(R.string.send_via_email) + "..."));
 }
}
