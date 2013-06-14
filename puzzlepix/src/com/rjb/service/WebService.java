package com.rjb.service;

import android.content.Context;
import android.net.ConnectivityManager;
import android.os.Build;
import com.rjb.Enums.DownloadResult;
import com.rjb.service.store.UserProfileStore;
import com.rjb.utils.App;
import org.apache.http.Header;
import org.apache.http.HttpResponse;
import org.apache.http.HttpStatus;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.params.BasicHttpParams;
import org.apache.http.params.HttpConnectionParams;
import org.apache.http.params.HttpParams;
import org.apache.http.params.HttpProtocolParams;
import org.apache.http.protocol.HTTP;

import java.io.*;
import java.util.Collections;
import java.util.HashSet;
import java.util.Set;
import java.util.zip.GZIPInputStream;

public class WebService {
	private static final Logger logger = new Logger(WebService.class);

	// Synchronized because multiple threads will be using this set to determine if there are active downloads. The order doesn't matter.
	private static final Set<String> imageDownloadUrlsRequested = Collections.synchronizedSet(new HashSet<String>());


	public static String addParameterToUrl(String url, String parameter, String value) {
		StringBuilder stringBuilder = new StringBuilder(url);

		if (!url.contains("?")) {
			stringBuilder.append("?");
		}
		else {
			stringBuilder.append("&");
		}

		stringBuilder.append(parameter);
		stringBuilder.append("=");
		stringBuilder.append(value);

		return stringBuilder.toString();
	}


	public static HttpResponse sendGetRequest(Context context, String url, boolean requestGzipCompression) {

		if (!isNetworkConnected(context)) return null;

		HttpGet httpRequest = new HttpGet(url);

		if (requestGzipCompression) {
			httpRequest.addHeader("Accept-Encoding", "gzip");
		}

		// Set Timeouts
		HttpParams httpParameters = new BasicHttpParams();
		// Set the timeout in milliseconds until a connection is established.

		// set the user-agent...
		HttpProtocolParams.setUserAgent(httpParameters, WebService.getUserAgent());

		HttpConnectionParams.setConnectionTimeout(httpParameters, 5000);
		// Set the default socket timeout (SO_TIMEOUT)
		// in milliseconds which is the timeout for waiting for data.
		HttpConnectionParams.setSoTimeout(httpParameters, 5000);

		DefaultHttpClient httpClient = new DefaultHttpClient(httpParameters);

		HttpResponse httpResponse = null;

		try {
			httpResponse = httpClient.execute(httpRequest);
		}
		catch (Exception exception) {
            App.e(WebService.class, exception);
		}

		return httpResponse;
	}

	//
	public static HttpResponse sendXMLPostRequest(final Context context, final String url, final String xml, boolean requestGzipCompression) {

		if (!isNetworkConnected(context)) return null;

		HttpPost httpRequest = new HttpPost(url);
		logger.d("url: " + url);

		StringEntity stringEntity = null;

		try {
			stringEntity = new StringEntity(xml, HTTP.UTF_8);
		}
		catch (UnsupportedEncodingException exception) {
            App.e(WebService.class, exception);
			return null;
		}

		stringEntity.setContentType("text/xml");

		httpRequest.setHeader("Content-Type", "application/xml;charset=UTF-8");
		httpRequest.setEntity(stringEntity);

		if (requestGzipCompression) {
			httpRequest.addHeader("Accept-Encoding", "gzip");
		}

		// Set Timeouts
		HttpParams httpParameters = new BasicHttpParams();
		// Set the timeout in milliseconds until a connection is established.
		HttpConnectionParams.setConnectionTimeout(httpParameters, 5000);
		// Set the default socket timeout (SO_TIMEOUT)
		// in milliseconds which is the timeout for waiting for data.
		HttpConnectionParams.setSoTimeout(httpParameters, 5000);

		DefaultHttpClient httpClient = new DefaultHttpClient(httpParameters);

		HttpResponse httpResponse = null;

		try {
			httpResponse = (HttpResponse) httpClient.execute(httpRequest);
		}
		catch (Exception exception) {
            App.e(WebService.class, exception);
		}

		return httpResponse;
	}

	// This actually downloads and is not a wrapper or launch point.
	public static DownloadResult downloadImageToFile(final Context context, final String imageUrl, final String directory, final String outputFilePath) {

		if (imageUrl == null || imageUrl.length() == 0) {
			logger.d("imageUrl is null or empty.");
			return DownloadResult.NULL_OR_EMPTY_PARAMETERS;
		}

		if (!WebService.isNetworkConnected(context)) {
			return DownloadResult.NO_NETWORK_CONNECTION;
		}

        synchronized (WebService.class)
        {
            if (imageDownloadUrlsRequested.contains(imageUrl))
            {
                return DownloadResult.ALREADY_IN_PROGRESS;
            }

            imageDownloadUrlsRequested.add(imageUrl);
        }

        try
        {
            logger.d("Attempting to download: " + imageUrl);

            // Set Timeouts
            HttpParams httpParameters = new BasicHttpParams();

            // set the user-agent...
            HttpProtocolParams.setUserAgent(httpParameters, WebService.getUserAgent());

            final HttpClient httpClient = new DefaultHttpClient(httpParameters);
            final HttpGet httpGet = new HttpGet(imageUrl);

            HttpResponse httpResponse = null;

            try
            {
                httpResponse = httpClient.execute(httpGet);
            }
            catch (Exception exception)
            {
                App.e(WebService.class, exception);
                return DownloadResult.FAILED;
            }

            if (httpResponse == null)
            {
                logger.e("Failed to download image. Null response.");
                return DownloadResult.FAILED;
            }

            if (!WebService.isSuccessfulResponse(imageUrl, httpResponse))
            {
                logger.e("" + httpResponse.toString());
                return DownloadResult.FAILED;
            }

            if (httpResponse.getEntity().getContentLength() < 0)
            {
                return DownloadResult.FAILED;
            }

            File outputFile = new File(outputFilePath);
            File outputDirectory = new File(directory);
            File noMediaDirectory = new File(directory + ".nomedia");
            outputDirectory.mkdirs();
            noMediaDirectory.mkdirs();

            // Outer try block to catch exceptions.
            try
            {
                InputStream inputStream = null;
                FileOutputStream fileOutputStream = null;

                // Inner try block for streams that need closing.
                try
                {
                    inputStream = WebService.getInputStream(httpResponse);
                    fileOutputStream = new FileOutputStream(outputFile);
                    byte[] buffer = new byte[8192];
                    int bytesRead = 0;

                    while ((bytesRead = inputStream.read(buffer, 0, buffer.length)) > -1)
                    {
                        fileOutputStream.write(buffer, 0, bytesRead);
                    }

                    fileOutputStream.flush();
                }
                finally
                {

                    if (inputStream != null)
                    {
                        inputStream.close();
                    }

                    if (fileOutputStream != null)
                    {
                        fileOutputStream.close();
                    }
                }
            }
            catch (Throwable throwable)
            {
                App.e(WebService.class, throwable);
                return DownloadResult.FAILED;
            }
        }
        finally
        {
            imageDownloadUrlsRequested.remove(imageUrl);
        }

		logger.d("Finished downloading: " + imageUrl);

		return DownloadResult.COMPLETED;
	}

	public static boolean isSuccessfulResponse(final String url, final HttpResponse httpResponse) {
		final int responseCode = httpResponse.getStatusLine().getStatusCode();

		if (responseCode >= 200 && responseCode <= 299) {
			return true;
		}

		logger.d("         url: " + url);
		logger.d("responseCode: " + responseCode);
		logger.d("      reason: " + httpResponse.getStatusLine().getReasonPhrase());

		return false;
	}

	public static boolean isForbiddenResponse(HttpResponse httpResponse) {

		if (httpResponse == null) {
			return false;
		}

		int responseCode = httpResponse.getStatusLine().getStatusCode();

		if (responseCode == HttpStatus.SC_FORBIDDEN) {
			return true;
		}

		return false;
	}

	public static String getUserAgent() {
		return "Mozilla/5.0 (Linux; U; " + Build.DISPLAY + "; " + Build.DEVICE + "; " + Build.PRODUCT + ") AppleWebKit/533.1 (KHTML, like Gecko) Version/4.0 Mobile Safari/533.1";
	}

	public static boolean isNetworkConnected(final Context context) {
		final ConnectivityManager connectivityManager = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
		final boolean isNetworkConnected = (connectivityManager.getActiveNetworkInfo() == null) || connectivityManager.getActiveNetworkInfo().isConnected();

		if (!isNetworkConnected) {
			logger.d("Network not connected. Make sure WiFi is on. Try restarting emulator.");
		}

		return isNetworkConnected;
	}

	public static InputStream getInputStream(HttpResponse httpResponse) throws IllegalStateException, IOException {
		InputStream inputStream = httpResponse.getEntity().getContent();
		Header contentEncoding = httpResponse.getFirstHeader("Content-Encoding");

		if (contentEncoding != null && contentEncoding.getValue().equalsIgnoreCase("gzip")) {
			inputStream = new GZIPInputStream(inputStream);
		}

		return inputStream;
	}
}
