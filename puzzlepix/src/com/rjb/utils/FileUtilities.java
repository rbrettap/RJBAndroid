package com.rjb.utils;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.Drawable;
import android.os.Environment;
import android.util.Log;
import android.view.Display;
import com.rjb.service.Logger;

import java.io.*;

public class FileUtilities {
	private static final Logger logger = new Logger(FileUtilities.class);
	public static final String PORTRAIT_IDENTIFIER = "-portrait";
	public static final String LANDSCAPE_IDENTIFIER = "-landscape";


	// XXX This is used for testing purposes and it forces the application to write to internal memory
	// public static final String ROOT_DIRECTORY = BackgroundContextService.getContext().getFileUtilitiesDir().getPath();

	//public static String getSdPath(Context context) {
	//	return Environment.getExternalStorageDirectory().getPath() + "/" + context.getString(R.string.application_name_nospaces).replaceAll("/", "-");
//	}

	/*
	public static String getRootDirectory(Context context) {
		
		return isSdCardPresent() ? getSdPath(context) : context.getFilesDir().getPath();
	}
	*/

	private static boolean isSdCardPresent() {
		return Environment.getExternalStorageState().equals(Environment.MEDIA_MOUNTED);
	}

	public static boolean deleteFile(String fileName) {
		File file = new File(fileName);
		
		if (file.exists()) {
			return file.delete();
		}

		return false;
	}

	public static void deleteDatabaseDirectory(Context context) {
		File databaseDirectory = context.getDatabasePath("").getAbsoluteFile();
		deleteContentsOfDirectory(databaseDirectory);
		logger.i("Database Directory: " + databaseDirectory.getAbsolutePath());
	}

	public static boolean deleteDatabase(Context context, String name) {
		logger.i(context.getDatabasePath(name).getPath());
		return context.getDatabasePath(name).delete();
	}

	public static boolean deleteContentsOfDirectory(File directory) {

		if (directory.isDirectory()) {
			String[] children = directory.list();
			
			for (int i = 0; i < children.length; i++) {

				//if (!children[i].contains(MediaService.SAVED_TAG)) {
				//	new File(directory, children[i]).delete();
				//}
			}

			return true;
		}

		return false;
	}

	public static String getOrientationIdentifier() {
		String orientation = PORTRAIT_IDENTIFIER;
		Display display = StaticMainThreadReference.getDisplay();
		int screenWidth = display.getWidth();
		int screenHeight = display.getHeight();
		
		if (screenWidth > screenHeight) {
			orientation = LANDSCAPE_IDENTIFIER;
		}

		return orientation;
	}

	public static void writeToFile(byte[] buffer, String directory, String outputFilePath) throws IOException {
		File outputFile = new File(outputFilePath);
		File outputDirectory = new File(directory);
		outputDirectory.mkdirs();
		outputFile.createNewFile();

		FileOutputStream fileOutputStream = new FileOutputStream(outputFile);
		fileOutputStream.write(buffer);
		fileOutputStream.flush();
		fileOutputStream.close();
	}

	public static Drawable getDrawableFromResource(Context context, int drawable) {

		try {
			return context.getResources().getDrawable(drawable);
		}
		catch (Exception exception) {
            App.e(FileUtilities.class, exception);
		}

		return null;
	}

	public static Bitmap getBitmapFromStream(InputStream inputStream) {

		try {
			return BitmapFactory.decodeStream(inputStream);
		}
		catch (Exception exception) {
            App.e(FileUtilities.class, exception);
		}

		return null;
	}

	public static Bitmap getBitmapFromResource(Context context, int drawable) {

		try {
			return BitmapFactory.decodeResource(context.getResources(), drawable);
		}
		catch (Exception exception) {
            App.e(FileUtilities.class, exception);
		}

		return null;
	}

	public static Bitmap getBitmapFromFile(File imageFile) {

		try {
			return BitmapFactory.decodeFile(imageFile.getPath());
		}
		catch (Exception exception) {
            App.e(FileUtilities.class, exception);
		}

		return null;
	}

	public static void copyFile(File sourceFile, File destinationFile) {

		try {
			FileInputStream fileInputStream = null;
			FileOutputStream fileOutputStream = null;

			// Inner try block is for streams and readers that needs closing.
			try {
				fileInputStream = new FileInputStream(sourceFile);
				fileOutputStream = new FileOutputStream(destinationFile);
				byte[] buffer = new byte[1024];
				int length = 0;

				while ((length = fileInputStream.read(buffer)) != -1) {
					fileOutputStream.write(buffer, 0, length);
				}
			}
			finally {

				if (fileInputStream != null) fileInputStream.close();

				if (fileOutputStream != null) fileOutputStream.close();
			}
		}
		catch (Throwable throwable) {
            App.e(FileUtilities.class, throwable);
		}
	}
}
