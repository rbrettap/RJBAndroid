package com.rjb.service;

import com.rjb.utils.CharacterEncodings;
import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;
import org.xmlpull.v1.XmlPullParserFactory;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;

public class Parsers {

 public static XmlPullParser getParser(InputStream inputStream) throws XmlPullParserException, UnsupportedEncodingException {

  XmlPullParser xmlPullParser = XmlPullParserFactory.newInstance().newPullParser();
  xmlPullParser.setInput(new InputStreamReader(inputStream, CharacterEncodings.UTF8));
  xmlPullParser.setFeature(XmlPullParser.FEATURE_PROCESS_NAMESPACES, true);

  return xmlPullParser;
 }

 public static void skipSubTree(XmlPullParser xmlPullParser) throws XmlPullParserException, IOException {

  String nameTag = xmlPullParser.getName();

  while (true) {

   xmlPullParser.next();
   if (xmlPullParser.getEventType() == XmlPullParser.END_TAG) {

    if (xmlPullParser.getName().equals(nameTag)) {

     break;
    }
   }
  }
 }

 public static final boolean shouldAddCharacter(char character) {

  if (character == '\n') {

   return false;
  }
  else if (character == '\r') {

   return false;
  }
  else if (character == '\t') {

   return false;
  }

  return true;
 }
}
