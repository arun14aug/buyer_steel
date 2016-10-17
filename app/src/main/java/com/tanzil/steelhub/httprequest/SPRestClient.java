package com.tanzil.steelhub.httprequest;

import android.os.Looper;

import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.AsyncHttpResponseHandler;
import com.loopj.android.http.SyncHttpClient;
import com.tanzil.steelhub.model.ModelManager;
import com.tanzil.steelhub.utility.STLog;

import java.io.UnsupportedEncodingException;
import java.net.SocketTimeoutException;
import java.net.URLEncoder;

import cz.msebera.android.httpclient.entity.StringEntity;
import cz.msebera.android.httpclient.message.BasicHeader;
import cz.msebera.android.httpclient.protocol.HTTP;

/**
 * Created by arun on 05/3/15.
 */
public class SPRestClient {

    private static final String TAG = SPRestClient.class.getSimpleName();
    private static StringEntity se = null;
    private static final int CONNECTION_TIMEOUT = 50000;
    public static AsyncHttpClient syncHttpClient = new SyncHttpClient();
    public static AsyncHttpClient asyncHttpClient = new AsyncHttpClient();


    public static void get(String url, String post_data, AsyncHttpResponseHandler responseHandler) {
        STLog.e(TAG, "POST-URL---> " + url + " <-> POST-DATA---> " + post_data);

        try {
            url= URLEncoder.encode(url, "UTF-8");
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }


        getClient().get(url, responseHandler);
       // getClient().get(url, responseHandler);
    }

    public static void post(String url, String post_data, AsyncHttpResponseHandler responseHandler) {
        STLog.e(TAG, "POST-URL---> " + url + " <-> POST-DATA---> " + post_data);
        try {
            url= URLEncoder.encode(url, "UTF-8");
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        try {
            String token = ModelManager.getInstance().getAuthManager().getUserToken();
            se = null;
            se = new StringEntity(post_data);
            se.setContentEncoding(new BasicHeader(HTTP.CONTENT_TYPE, "application/json"));
            se.setContentEncoding(new BasicHeader("Authorization", ""));
            getClient().post(null, url, se, "application/json", responseHandler);
        } catch (Exception e1) {
            e1.printStackTrace();
        }

    }

    private static AsyncHttpClient getClient() {
        // Return the synchronous HTTP client when the thread is not prepared
        if (Looper.myLooper() == null) {
            AsyncHttpClient.allowRetryExceptionClass(SocketTimeoutException.class);
            syncHttpClient.setMaxRetriesAndTimeout(1, CONNECTION_TIMEOUT);
            return syncHttpClient;
        } else {
            AsyncHttpClient.allowRetryExceptionClass(SocketTimeoutException.class);
            asyncHttpClient.setMaxRetriesAndTimeout(1, CONNECTION_TIMEOUT);
            return asyncHttpClient;
        }
    }


    public static void put(String url, String post_data, AsyncHttpResponseHandler responseHandler) {

        try {
            STLog.e(TAG, "POST-URL---> " + url + " <-> POST-DATA---> " + post_data.toString());
            se = null;
            se = new StringEntity(post_data);
            getClient().put(null, url, se, "application/json", responseHandler);
        } catch (Exception e1) {
            e1.printStackTrace();
        }

    }

    public static void delete(String url, String post_data, AsyncHttpResponseHandler responseHandler) {
        getClient().delete(url, responseHandler);
    }
}