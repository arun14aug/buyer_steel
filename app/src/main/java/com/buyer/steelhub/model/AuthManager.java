package com.buyer.steelhub.model;

import android.app.Activity;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.buyer.steelhub.utility.Preferences;
import com.buyer.steelhub.utility.STLog;
import com.buyer.steelhub.utility.ServiceApi;
import com.buyer.steelhub.utility.Utils;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import de.greenrobot.event.EventBus;

/**
 * Created by arun on 28/12/15.
 */
public class AuthManager {

    private String TAG = AuthManager.class.getSimpleName();
    private String deviceToken;
    private String userToken;
    private ArrayList<User> userArrayList;

    public String getUserToken() {
        return userToken;
    }

    public void setUserToken(String userToken) {
        this.userToken = userToken;
    }

    public String getDeviceToken() {
        return deviceToken;
    }

    public void setDeviceToken(String deviceToken) {
        this.deviceToken = deviceToken;
    }

    public void logIn(final Activity activity, JSONObject post_data) {
        STLog.e("JSON : ", post_data.toString());
        JsonObjectRequest jsonObjReq = new JsonObjectRequest(Request.Method.POST, ServiceApi.LOGIN, post_data,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        STLog.e("Success Response : ", "Response: " + response.toString());

                        try {
                            boolean state = false;
                            if (response.has("success"))
                                state = response.getBoolean("success");

                            if (state) {
                                Preferences.writeBoolean(activity, Preferences.LOGIN, true);
                                Preferences.writeString(activity, Preferences.USER_ID, response.getString("user_id"));
                                Preferences.writeString(activity, Preferences.USER_TOKEN, response.getString("token"));
                                ModelManager.getInstance().getAuthManager().setUserToken(response.getString("token"));
                                EventBus.getDefault().postSticky("Login True");
                            } else if (response.has("token")) {
                                Preferences.writeBoolean(activity, Preferences.LOGIN, true);
                                if (response.has("user_id"))
                                    Preferences.writeString(activity, Preferences.USER_ID, response.getString("user_id"));
                                Preferences.writeString(activity, Preferences.USER_TOKEN, response.getString("token"));
                                ModelManager.getInstance().getAuthManager().setUserToken(response.getString("token"));
                                EventBus.getDefault().postSticky("Login True");
                            } else
                                EventBus.getDefault().postSticky("Login False@#@" + response.getString("msg"));

                        } catch (JSONException e) {
                            e.printStackTrace();
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

    public ArrayList<User> getProfile(Activity activity, boolean shouldRefresh) {
        if (shouldRefresh)
            getProfileDetail(activity);
        return userArrayList;
    }

    private void getProfileDetail(final Activity activity) {
        final String token = "Profile";
        JsonObjectRequest jsonObjReq = new JsonObjectRequest(Request.Method.GET, ServiceApi.GET_PROFILE, new JSONObject(),
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        STLog.e("Success Response : ", "Response: " + response.toString());
                        try {
                            if (response.getBoolean("success")) {
                                JSONObject jsonObject = response.getJSONObject("data");
                                userArrayList = new ArrayList<>();

//                                "id": 23,
//                                        "name": "buyer",
//                                        "email": "buyer@gmail.com",
//                                        "customer_type": "",
//                                        "company_name": "ambuja",
//                                        "contact": 9087789909,
//                                        "address": "8phase",
//                                        "state": "punjab",
//                                        "city": "mohali",
//                                        "latitude": "8789787878",
//                                        "longitude": "66777787878",
//                                        "role": "buyer",
//                                        "zip": 189768,
//                                        "tin": "5455",
//                                        "pan": "455654467",
//                                        "brand": null,
//                                        "exp_quantity": "10000",
                                User user = new User();
                                user.setId(jsonObject.getString("id"));
                                user.setName(jsonObject.getString("name"));
                                user.setEmail(jsonObject.getString("email"));
                                user.setCustomer_type(jsonObject.getString("customer_type"));
                                user.setCompany_name(jsonObject.getString("company_name"));
                                user.setContact(jsonObject.getString("contact"));
                                user.setAddress(jsonObject.getString("address"));
                                user.setState(jsonObject.getString("state"));
                                user.setCity(jsonObject.getString("city"));
                                user.setLatitude(jsonObject.getString("latitude"));
                                user.setLongitude(jsonObject.getString("longitude"));
                                user.setRole(jsonObject.getString("role"));
                                user.setCreated_at(jsonObject.getJSONObject("created_at").getString("date"));
                                user.setUpdated_at(jsonObject.getJSONObject("updated_at").getString("date"));
                                user.setZip(jsonObject.getString("zip"));
                                user.setTin(jsonObject.getString("tin"));
                                user.setPan(jsonObject.getString("pan"));
                                user.setBrand(jsonObject.getString("brand"));
                                user.setExp_quantity(jsonObject.getString("exp_quantity"));

//                                if (jsonObject.has("preffered_brands")) {
//                                    JSONArray jsonArray1 = jsonObject.getJSONArray("preffered_brands");
//                                    if (jsonArray1.length() > 0) {
//                                        String[] brand = new String[jsonArray1.length()];
//                                        for (int j = 0; j < jsonArray1.length(); j++) {
//                                            brand[j] = jsonArray1.getString(j);
//                                        }
//                                        user.setPreffered_brands(brand);
//                                    }
//                                }
                                userArrayList.add(user);
                                EventBus.getDefault().post(token + " True");
                            } else
                                EventBus.getDefault().post(token + " False@#@" + response.getString("message"));
                        } catch (JSONException e) {
                            e.printStackTrace();
                            EventBus.getDefault().post(token + " False");
                        }
                    }
                }, new Response.ErrorListener() {

            @Override
            public void onErrorResponse(VolleyError error) {
                STLog.e("Error Response : ", "Error: " + error.getMessage());
                EventBus.getDefault().post(token + " False");
            }
        }) {
            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                Map<String, String> params = new HashMap<>();
                params.put("Authorization", "Bearer" + Preferences.readString(activity, Preferences.USER_TOKEN, ""));
                return params;
            }
        };
        RequestQueue requestQueue = Utils.getVolleyRequestQueue(activity);
        requestQueue.add(jsonObjReq);
    }
}
