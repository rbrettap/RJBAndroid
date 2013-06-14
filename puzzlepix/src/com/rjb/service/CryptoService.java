package com.rjb.service;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class CryptoService {

 public static String toMD5(final byte[] bytes) throws NoSuchAlgorithmException {
  MessageDigest md5Digest = MessageDigest.getInstance("MD5");
  md5Digest.reset();
  md5Digest.update(bytes);
  return toHexString(md5Digest.digest());
 }

 public static String toSha1Hash(final byte[] bytes) throws NoSuchAlgorithmException {
  MessageDigest sha1Digest = MessageDigest.getInstance("SHA-1");
  sha1Digest.reset();
  sha1Digest.update(bytes);
  return toHexString(sha1Digest.digest());
 }

 /**
  * Converts a byte[] into a string of Hex values.
  * 
  * @param bytes
  * @return
  */
 private static String toHexString(final byte[] bytes) {
  StringBuilder stringBuilder = new StringBuilder();

  for (int i = 0; i < bytes.length; i++) {
   String hexString = Integer.toHexString(0xFF & bytes[i]);
   
   if (hexString.length() == 1) {
    stringBuilder.append("0");
   }
   
   stringBuilder.append(hexString);
  }

  return stringBuilder.toString();
 }
}
