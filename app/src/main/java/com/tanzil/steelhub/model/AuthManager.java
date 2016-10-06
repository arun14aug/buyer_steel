package com.tanzil.steelhub.model;

import android.app.Activity;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.tanzil.steelhub.utility.Preferences;
import com.tanzil.steelhub.utility.STLog;
import com.tanzil.steelhub.utility.ServiceApi;
import com.tanzil.steelhub.utility.Utils;

import org.json.JSONException;
import org.json.JSONObject;

import de.greenrobot.event.EventBus;

/**
 * Created by arun on 28/12/15.
 */
public class AuthManager {

    private String TAG = AuthManager.class.getSimpleName();
    private String deviceToken;
    private String userToken;

    public String getDeviceToken() {
        return deviceToken;
    }

    public void setDeviceToken(String deviceToken) {
        this.deviceToken = deviceToken;
    }

    public void logIn(final Activity activity, JSONObject post_data) {
        JsonObjectRequest jsonObjReq = new JsonObjectRequest(Request.Method.POST, ServiceApi.LOGIN, post_data,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        STLog.e("Success Response : ", "Response: " + response.toString());

                        try {
                            boolean state = response.getBoolean("success");
                            if (state) {
                                Preferences.writeBoolean(activity, Preferences.LOGIN, true);
                                Preferences.writeString(activity, Preferences.USER_ID, response.getString("user_id"));
                                Preferences.writeString(activity, Preferences.USER_TOKEN, response.getString("token"));

                                EventBus.getDefault().postSticky("Login True");
                            } else {

                                EventBus.getDefault().postSticky("Login False@#@" + response.getString("message"));
                            }
                        } catch (JSONException e) {
                            EventBus.getDefault().postSticky("Login False");
                        }
                    }
                }, new Response.ErrorListener() {

            @Override
            public void onErrorResponse(VolleyError error) {
                STLog.e("Error Response : ", "Error: " + error.getMessage());
                EventBus.getDefault().postSticky("Login False");
            }
        });
        RequestQueue requestQueue = Utils.getVolleyRequestQueue(activity);
        requestQueue.add(jsonObjReq);
    }

    public void registerUser(final Activity activity, JSONObject jsonObject) {
        JsonObjectRequest jsonObjReq = new JsonObjectRequest(Request.Method.POST, ServiceApi.REGISTER, jsonObject,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        STLog.e("Success Response : ", "Response: " + response.toString());

                        try {
                            boolean state = response.getBoolean("success");
                            if (state) {
                                Preferences.writeBoolean(activity, Preferences.REGISTRATION, true);

                                EventBus.getDefault().postSticky("Register True");
                            } else {
                                EventBus.getDefault().postSticky("Register False@#@" + response.getString("msg"));
                            }
                        } catch (JSONException e) {
                            EventBus.getDefault().postSticky("Register False");
                        }


                    }
                }, new Response.ErrorListener() {

            @Override
            public void onErrorResponse(VolleyError error) {
                STLog.e("Error Response : ", "Error: " + error.getMessage());
                EventBus.getDefault().postSticky("Register False");
            }
        });
        RequestQueue requestQueue = Utils.getVolleyRequestQueue(activity);
        requestQueue.add(jsonObjReq);
    }

    public void verifyUser(final Activity activity, JSONObject jsonObject) {
        JsonObjectRequest jsonObjReq = new JsonObjectRequest(Request.Method.POST, ServiceApi.VERIFY_USER, jsonObject,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        STLog.e("Success Response : ", "Response: " + response.toString());

                        try {
                            boolean state = response.getBoolean("success");
                            if (state) {
                                Preferences.writeBoolean(activity, Preferences.LOGIN, true);
                                Preferences.writeBoolean(activity, Preferences.REGISTRATION, false);

                                EventBus.getDefault().postSticky("Verify True");
                            } else {
                                EventBus.getDefault().postSticky("Verify False@#@" + response.getString("msg"));
                            }
                        } catch (JSONException e) {
                            EventBus.getDefault().postSticky("Verify False");
                        }
                    }
                }, new Response.ErrorListener() {

            @Override
            public void onErrorResponse(VolleyError error) {
                STLog.e("Error Response : ", "Error: " + error.getMessage());
                EventBus.getDefault().postSticky("Verify False");
            }
        });
        RequestQueue requestQueue = Utils.getVolleyRequestQueue(activity);
        requestQueue.add(jsonObjReq);
    }

    public void forgetPassword(final Activity activity, JSONObject jsonObject) {
        JsonObjectRequest jsonObjReq = new JsonObjectRequest(Request.Method.POST, ServiceApi.FORGOT_PASSWORD, jsonObject,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        STLog.e("Success Response : ", "Response: " + response.toString());

                        try {
                            boolean state = response.getBoolean("success");
                            if (state) {
                                Preferences.writeBoolean(activity, Preferences.FORGET_PASS, true);

                                EventBus.getDefault().postSticky("ForgetPassword True@#@" + response.getString("message"));
                            } else {
                                EventBus.getDefault().postSticky("ForgetPassword False@#@" + response.getString("msg"));
                            }
                        } catch (JSONException e) {
                            EventBus.getDefault().postSticky("ForgetPassword False");
                        }
                    }
        }, new Response.ErrorListener() {

            @Override
            public void onErrorResponse(VolleyError error) {
                STLog.e("Error Response : ", "Error: " + error.getMessage());
                EventBus.getDefault().postSticky("ForgetPassword False");
            }
        });
        RequestQueue requestQueue = Utils.getVolleyRequestQueue(activity);
        requestQueue.add(jsonObjReq);
    }

    public void changePassword(final Activity activity, JSONObject jsonObject) {
        JsonObjectRequest jsonObjReq = new JsonObjectRequest(Request.Method.POST, ServiceApi.CHANGE_PASSWORD, jsonObject,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        STLog.e("Success Response : ", "Response: " + response.toString());

                        try {
                            boolean state = response.getBoolean("success");
                            if (state) {
                                Preferences.writeBoolean(activity, Preferences.LOGIN, true);

                                EventBus.getDefault().postSticky("ChangePassword True");
                            } else {
                                EventBus.getDefault().postSticky("ChangePassword False@#@" + response.getString("msg"));
                            }
                        } catch (JSONException e) {
                            EventBus.getDefault().postSticky("ChangePassword False");
                        }
                    }
                }, new Response.ErrorListener() {

            @Override
            public void onErrorResponse(VolleyError error) {
                STLog.e("Error Response : ", "Error: " + error.getMessage());
                EventBus.getDefault().postSticky("ChangePassword False");
            }
        });
        RequestQueue requestQueue = Utils.getVolleyRequestQueue(activity);
        requestQueue.add(jsonObjReq);
    }

    public void logout(final Activity activity, JSONObject jsonObject) {
        JsonObjectRequest jsonObjReq = new JsonObjectRequest(Request.Method.POST, ServiceApi.LOGOUT, jsonObject,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        STLog.e("Success Response : ", "Response: " + response.toString());

                        try {
                            boolean state = response.getBoolean("success");
                            if (state) {
                                Preferences.writeBoolean(activity, Preferences.LOGIN, true);

                                EventBus.getDefault().postSticky("Logout True");
                            } else {
                                EventBus.getDefault().postSticky("Logout False@#@" + response.getString("msg"));
                            }
                        } catch (JSONException e) {
                            EventBus.getDefault().postSticky("Logout False");
                        }
                    }
                }, new Response.ErrorListener() {

            @Override
            public void onErrorResponse(VolleyError error) {
                STLog.e("Error Response : ", "Error: " + error.getMessage());
                EventBus.getDefault().postSticky("Logout False");
            }
        });
        RequestQueue requestQueue = Utils.getVolleyRequestQueue(activity);
        requestQueue.add(jsonObjReq);
    }
}
