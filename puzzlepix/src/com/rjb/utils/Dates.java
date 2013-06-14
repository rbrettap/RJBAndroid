package com.rjb.utils;

import android.util.Log;

import java.text.SimpleDateFormat;
import java.util.*;

public class Dates {

 public static final long SECOND = 1000;
 public static final long MINUTE = 60 * SECOND;
 public static final long HOUR = 60 * MINUTE;
 public static final long DAY = 24 * HOUR;
 public static final long WEEK = DAY * 7;

 public static final int FLAG_SECOND = 1;
 public static final int FLAG_MINUTE = 2;
 public static final int FLAG_HOUR = 4;
 public static final int FLAG_DAY = 8;
 public static final int FLAG_WEEK = 16;

 private static final String TEXT_SECOND = "sec";
 private static final String TEXT_MINUTE = "min";
 private static final String TEXT_HOUR = "hour";
 private static final String TEXT_DAY = "day";
 private static final String TEXT_WEEK = "week";

 private static final int INDEX_WEEK = 0;
 private static final int INDEX_DAY = 1;
 private static final int INDEX_HOUR = 2;
 private static final int INDEX_MINUTE = 3;
 private static final int INDEX_SECOND = 4;

 private static GregorianCalendar gregorianCalendar = new GregorianCalendar();

 private static String GMT = "GMT";

 private static Hashtable<String, Integer> monthLookupTable;
 private static String JAN = "Jan";
 private static String FEB = "Feb";
 private static String MAR = "Mar";
 private static String APR = "Apr";
 private static String MAY = "May";
 private static String JUN = "Jun";
 private static String JUL = "Jul";
 private static String AUG = "Aug";
 private static String SEP = "Sep";
 private static String OCT = "Oct";
 private static String NOV = "Nov";
 private static String DEC = "Dec";

 private static final SimpleDateFormat publishedDateFormat = new SimpleDateFormat("EEE, dd MMM yyyy HH:mm:ss z");
 private static final SimpleDateFormat createdDateFormat = new SimpleDateFormat("yyyy-mm-dd'T'HH:mm:ss'Z'");
 private static final SimpleDateFormat PAGE_VIEW_DATE_FORMAT = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ssZ");

 private static final SimpleDateFormat observationTimeFormat = new SimpleDateFormat("hh:mm a");
 private static final SimpleDateFormat observationDateFormat = new SimpleDateFormat("M'/'dd'/'yyyy");

 public static long parsePublishedDate(String dateValue) {

  return parseDate(publishedDateFormat, dateValue);
 }

 public static long parseCreatedDate(String dateValue) {

  return parseDate(createdDateFormat, dateValue);
 }

 public static long parseObservationTime(String dateValue) {

  return parseDate(observationTimeFormat, dateValue);
 }

 public static long parseObservationDate(String dateValue) {

  return parseDate(observationDateFormat, dateValue);
 }

 private static long parseDate(SimpleDateFormat simpleDateFormat, String dateValue) {

  try {

   Date date = simpleDateFormat.parse(dateValue);
   return date.getTime();
  }
  catch (Exception exception) {

      App.e(Dates.class, exception);
  }

  return 0;
 }

 /**
  * Returns a human-readable String describing the amount of time
  * represented by the given number of milliseconds.
  * 
  * @param milliseconds Number of milliseconds
  * @param mask Mask 
  * @return human-readable String describing the amount of time
  */
 public static String getTimeDuration(long milliseconds, int mask) {

  // display week, day, hour, minute and second
  long[] unitOrder = { WEEK, DAY, HOUR, MINUTE, SECOND };
  return getTimeDuration(milliseconds, mask, unitOrder);
 }

 /**
  * Returns a human-readable String describing the amount of 
  * time represented by the given number of milliseconds.
  * 
  * @param milliseconds number of milliseconds
  * @param mask
  * @param unitOrder order units should be printed eg. xhrs xmins xsecs would be { HOUR, MINUTE, SECOND }
  * @return human-readable String describing the amount of time represented by the given parameter (e.g., "2hours 3mins 10secs".
  */
 public static String getTimeDuration(long milliseconds, int mask, long[] unitOrder) {

  StringBuilder stringBuilder = new StringBuilder();

  boolean lessThan = true;
  lessThan = getLessThanText(stringBuilder, milliseconds, SECOND, mask, lessThan);
  lessThan = getLessThanText(stringBuilder, milliseconds, MINUTE, mask, lessThan);
  lessThan = getLessThanText(stringBuilder, milliseconds, HOUR, mask, lessThan);
  lessThan = getLessThanText(stringBuilder, milliseconds, DAY, mask, lessThan);
  lessThan = getLessThanText(stringBuilder, milliseconds, WEEK, mask, lessThan);

  if (stringBuilder.length() > 0) {

   stringBuilder.insert(0, "less than 1 ");
  }
  else {

   List<String> timeDurationStrings = new ArrayList<String>();

   milliseconds = getTimeDuration(timeDurationStrings, milliseconds, WEEK, mask);
   milliseconds = getTimeDuration(timeDurationStrings, milliseconds, DAY, mask);
   milliseconds = getTimeDuration(timeDurationStrings, milliseconds, HOUR, mask);
   milliseconds = getTimeDuration(timeDurationStrings, milliseconds, MINUTE, mask);
   milliseconds = getTimeDuration(timeDurationStrings, milliseconds, SECOND, mask);

   for (int i = 0; i < unitOrder.length; i++) {

    int index = getIndexForUnit(unitOrder[i]);

    if (index != -1) {

     String timeDuration = (String) timeDurationStrings.get(index);

     if (timeDuration != null) {

      if (stringBuilder.length() > 0) {

       stringBuilder.append(" ");
      }

      stringBuilder.append(timeDuration);
     }
    }
   }
  }

  return stringBuilder.toString();
 }

 private static boolean getLessThanText(StringBuilder stringBuilder, long milliseconds, long unit, int mask, boolean lessThan) {

  int flag = getFlagForUnit(unit);

  if (lessThan && Numbers.isSet(flag, mask)) {

   if (milliseconds < unit) {

    if (stringBuilder.length() == 0 && Numbers.isSet(flag, mask)) {

     stringBuilder.append(getTextForUnit(unit));
    }
   }
   else {

    lessThan = false;
   }
  }

  return lessThan;
 }

 private static long getTimeDuration(List<String> timeDurationStrings, long milliseconds, long unit, int mask) {

  int flag = getFlagForUnit(unit);
  if (milliseconds >= unit && Numbers.isSet(flag, mask)) {

   StringBuilder stringBuilder = new StringBuilder();

   int amount = (int) Math.floor((double) (milliseconds / unit));
   milliseconds -= (unit * amount);

   stringBuilder.append(amount);
   stringBuilder.append(" ");
   stringBuilder.append(getTextForUnit(unit));
   stringBuilder.append((amount > 1) ? "s" : "");

   timeDurationStrings.add(stringBuilder.toString());
  }
  else {

   timeDurationStrings.add(null);
  }

  return milliseconds;
 }

 private static final int getFlagForUnit(long unit) {

  if (unit == SECOND) {

   return FLAG_SECOND;
  }
  else if (unit == MINUTE) {

   return FLAG_MINUTE;
  }
  else if (unit == HOUR) {

   return FLAG_HOUR;
  }
  else if (unit == DAY) {

   return FLAG_DAY;
  }
  else if (unit == WEEK) {

   return FLAG_WEEK;
  }
  else {

   return 0;
  }
 }

 private static final String getTextForUnit(long unit) {

  if (unit == SECOND) {

   return TEXT_SECOND;
  }
  else if (unit == MINUTE) {

   return TEXT_MINUTE;
  }
  else if (unit == HOUR) {

   return TEXT_HOUR;
  }
  else if (unit == DAY) {

   return TEXT_DAY;
  }
  else if (unit == WEEK) {

   return TEXT_WEEK;
  }
  else {

   return "";
  }
 }

 private static final int getIndexForUnit(long unit) {

  if (unit == SECOND) {

   return INDEX_SECOND;
  }
  else if (unit == MINUTE) {

   return INDEX_MINUTE;
  }
  else if (unit == HOUR) {

   return INDEX_HOUR;
  }
  else if (unit == DAY) {

   return INDEX_DAY;
  }
  else if (unit == WEEK) {

   return INDEX_WEEK;
  }
  else {

   return -1;
  }
 }

 private static final String format(SimpleDateFormat simpleDateFormat, long date) {

  try {

   return simpleDateFormat.format(new Date(date));
  }
  catch (Exception e) {

   throw new RuntimeException(e.getMessage());
  }
 }

 public static final String toPageViewDate(long pageViewDate) {

  return format(PAGE_VIEW_DATE_FORMAT, pageViewDate);
 }

 public static long fastParsePublishedDate(String date) {

  // EEE, dd MMM yyyy HH:mm:ss z
  // dd: 5-6
  // MMM: 8-10
  // yyyy: 12-15
  // HH: 17-18
  // mm: 20-21
  // ss: 23-24

  int year = Numbers.fastParseInt(date, 12, 16);
  int month = getMonthLookupTable().get(date.substring(8, 11));
  int day = Numbers.fastParseInt(date, 5, 7);
  int hour = Numbers.fastParseInt(date, 17, 19);
  int minute = Numbers.fastParseInt(date, 20, 22);
  int second = Numbers.fastParseInt(date, 23, 25);

  gregorianCalendar.set(year, month, day, hour, minute, second);
  gregorianCalendar.setTimeZone(TimeZone.getTimeZone(GMT));
  return gregorianCalendar.getTimeInMillis();
 }

 public static Hashtable<String, Integer> getMonthLookupTable() {

  if (monthLookupTable == null) {

   monthLookupTable = new Hashtable<String, Integer>();
   monthLookupTable.put(JAN, 0);
   monthLookupTable.put(FEB, 1);
   monthLookupTable.put(MAR, 2);
   monthLookupTable.put(APR, 3);
   monthLookupTable.put(MAY, 4);
   monthLookupTable.put(JUN, 5);
   monthLookupTable.put(JUL, 6);
   monthLookupTable.put(AUG, 7);
   monthLookupTable.put(SEP, 8);
   monthLookupTable.put(OCT, 9);
   monthLookupTable.put(NOV, 10);
   monthLookupTable.put(DEC, 11);
  }
  return monthLookupTable;
 }
}
