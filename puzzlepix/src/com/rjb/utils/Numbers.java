package com.rjb.utils;

public class Numbers {

 private static String TRUE = "true";

 /**
  * 11110001 (value)
  * & 00010000 (flag)
  * = 00010000 if flag is set for value
  * @param flag
  * @param value
  * @return
  */
 public static final boolean isSet(int flag, int value) {

  return (value & flag) == flag;
 }

 public static final int unset(int flag, int value) {

  return value & ~flag;
 }

 public static int set(int flag, int value) {

  return value | flag;
 }

 public static final boolean safeParseBoolean(String value) {

  return TRUE.equalsIgnoreCase(value);
 }

 /**
  * KPG: This might be slow in blackberry, but its not in regular java, and stacked against the I/O issues, this is nothing.
  * @param s The integer to parse
  * @return The parsed int
  */
 public static int fastParseInt(String s) {

  return Integer.parseInt(s);
 }

    /**
     * KPG: This is kind of ridiculous.  I took it out, but some code was using it, so put it back.  Should replace.
     * @param s
     * @param startIndex
     * @param endIndex
     * @return
     */
 public static int fastParseInt(String s, int startIndex, int endIndex) {

  int num = 0;
  int sign = -1;
  final char ch = s.charAt(startIndex);
  if (ch == '-') {

   sign = 1;
  }
  else {

   num = '0' - ch;
  }

  int i = startIndex + 1;
  while (i < endIndex) {

   num = num * 10 + '0' - s.charAt(i++);
  }

  return sign * num;
 }
}
