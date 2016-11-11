package com.tanzil.steelhub.model;

import android.app.Activity;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.tanzil.steelhub.utility.Preferences;
import com.tanzil.steelhub.utility.STLog;
import com.tanzil.steelhub.utility.ServiceApi;
import com.tanzil.steelhub.utility.Utils;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import de.greenrobot.event.EventBus;

/**
 * Created by arun.sharma on 9/30/2016.
 */
public class AddressManager {
    //    private String TAG = RequirementManager.class.getSimpleName();
    private ArrayList<Address> addressArrayList;

    public ArrayList<Address> getAddresses(Activity activity, boolean shouldRefresh) {
        if (shouldRefresh)
            getAddressList(activity);
        return addressArrayList;
    }

    public void getAddressList(final Activity activity) {
        JSONObject jsonObject = new JSONObject();
//        try {
////            jsonObject.put("user_id", Preferences.readString(activity, Preferences.USER_ID, ""));
//            jsonObject.put("user_id", "23");
//        } catch (JSONException e) {
//            e.printStackTrace();
//        }
        STLog.e("Post Data : ", "" + jsonObject.toString());
        JsonObjectRequest jsonObjReq = new JsonObjectRequest(Request.Method.POST, ServiceApi.FETCH_ADDRESS, jsonObject,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        STLog.e("Success Response : ", "Response: " + response.toString());
                        try {
                            if (response.getBoolean("success")) {
                                JSONArray jsonArray = response.getJSONArray("data");
                                int count = jsonArray.length();
                                if (count > 0) {
                                    addressArrayList = new ArrayList<>();
                                    for (int i = 0; i < jsonArray.length(); i++) {

                                        Address address = new Address();
                                        address.setAddressType(jsonArray.getJSONObject(i).getString("addressType"));
                                        address.setAddress1(jsonArray.getJSONObject(i).getString("address1"));
                                        address.setAddress2(jsonArray.getJSONObject(i).getString("address2"));
                                        address.setFirm_name(jsonArray.getJSONObject(i).getString("firm_name"));
                                        address.setPincode(jsonArray.getJSONObject(i).getString("pincode"));
                                        address.setMobile(jsonArray.getJSONObject(i).getString("mobile"));
                                        address.setLandline(jsonArray.getJSONObject(i).getString("landline"));
                                        address.setCurrent(jsonArray.getJSONObject(i).getString("current"));
                                        address.setState(jsonArray.getJSONObject(i).getString("state"));
                                        address.setCity(jsonArray.getJSONObject(i).getString("city"));

                                        addressArrayList.add(address);
                                    }
                                }
                                EventBus.getDefault().post("AddressList True");
                            } else
                                EventBus.getDefault().post("AddressList False");
                        } catch (JSONException e) {
                            e.printStackTrace();
                            EventBus.getDefault().post("AddressList False");
                        }
                    }
                }, new Response.ErrorListener() {

            @Override
            public void onErrorResponse(VolleyError error) {
                STLog.e("Error Response : ", "Error: " + error.getMessage());
                EventBus.getDefault().post("AddressList False");
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

    public void addAddress(final Activity activity, JSONObject jsonObject, final String token) {
        String url;
        if (token.equalsIgnoreCase("AddNewAddress"))
            url = ServiceApi.ADD_ADDRESS;
        else
            url = ServiceApi.UPDATE_ADDRESS;
        JsonObjectRequest jsonObjReq = new JsonObjectRequest(Request.Method.POST, url, jsonObject,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        STLog.e("Success Response : ", "Response: " + response.toString());

                        try {
                            boolean state = response.getBoolean("success");
                            if (state) {

                                EventBus.getDefault().postSticky(token + " True");
                            } else {
                                EventBus.getDefault().postSticky(token + " False@#@" + response.getString("msg"));
                            }
                        } catch (JSONException e) {
                            EventBus.getDefault().postSticky(token + " False");
                        }
                    }
                }, new Response.ErrorListener() {

            @Override
            public void onErrorResponse(VolleyError error) {
                STLog.e("Error Response : ", "Error: " + error.getMessage());
                EventBus.getDefault().postSticky(token + " False");
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
