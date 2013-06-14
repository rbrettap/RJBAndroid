package com.rjb.service.store;

import android.database.Cursor;
import android.database.sqlite.SQLiteStatement;
import android.util.Log;
import com.rjb.utils.App;

class AbstractStore {
 protected static final String[] COUNT_COLUMN = new String[] {"count(1)"};

 protected static boolean cursorToFirst(final Cursor cursor) {
  boolean cursorToFirst = false;
  
  try {
   cursorToFirst = cursor.moveToFirst();
  }
  catch (Exception exception) {
      App.e(AbstractStore.class, exception);
  }

  return cursorToFirst;
 }

 protected synchronized static long executeInsert(final SQLiteStatement insert) {
  long rowId = insert.executeInsert();
  
  if (rowId == -1) {
   Log.e("AbstractStore#executeInsert", "Unable to insert row.");
  }

  return rowId;
 }

 protected synchronized static void executeUpdate(final SQLiteStatement update) {
  update.execute();
 }

 protected synchronized static void executeDelete(final SQLiteStatement delete) {
  delete.execute();
 }
}
