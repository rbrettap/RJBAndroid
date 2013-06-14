package com.rjb.service;

import com.rjb.utils.App;
import com.rjb.utils.CharacterEncodings;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;

public class IOUtility {
	private static final Logger logger = new Logger(IOUtility.class);
	private static final int BUFFER_SIZE = 256;

	public static void safeClose(InputStream is) {

		try {
			is.close();
		}
		catch (Throwable t) {
		}
	}

	public static String toString(InputStream inputStream) {

		if (inputStream == null) return null;

		StringBuilder stringBuilder = new StringBuilder();
		String line;

		// Outer try block for exceptions.
		try {
			InputStreamReader inputStreamReader = null;
			BufferedReader bufferedReader = null;

			// Inner try block for streams that need closing.
			try {
				inputStreamReader = new InputStreamReader(inputStream, CharacterEncodings.UTF8);
				bufferedReader = new BufferedReader(inputStreamReader);

				while ((line = bufferedReader.readLine()) != null) {
					stringBuilder.append(line);
				}
			}
			finally {

				if (bufferedReader != null) bufferedReader.close();
                inputStream.close();
			}
		}
        //TODO: remove this
		catch (Throwable throwable)
        {
            App.e(IOUtility.class, "Unable to convert the InputStream to a String.", throwable);
		}

		return stringBuilder.toString();
	}
}
