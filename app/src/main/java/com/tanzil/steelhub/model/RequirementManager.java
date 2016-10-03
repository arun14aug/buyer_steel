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

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

import de.greenrobot.event.EventBus;

/**
 * Created by arun.sharma on 9/30/2016.
 */
public class RequirementManager {
    private String TAG = RequirementManager.class.getSimpleName();
    private ArrayList<Requirements> requirementsArrayList;

    public ArrayList<Requirements> getRequirements(Activity activity, boolean shouldRefresh) {
        if (shouldRefresh)
            getRequirementList(activity);
        return requirementsArrayList;
    }

    public void getRequirementList(final Activity activity) {
        JSONObject jsonObject = new JSONObject();
        try {
            jsonObject.put("user_id", Preferences.readString(activity, Preferences.USER_ID, ""));
        } catch (JSONException e) {
            e.printStackTrace();
        }
        STLog.e("Post Data : ", "" + jsonObject.toString());
        JsonObjectRequest jsonObjReq = new JsonObjectRequest(Request.Method.POST, ServiceApi.POSTED_REQUIREMENTS, jsonObject,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        STLog.e("Success Response : ", "Response: " + response.toString());
                        try {
                            if (response.getBoolean("success")) {
                                JSONArray jsonArray = response.getJSONArray("data");
                                int count = jsonArray.length();
                                if (count > 0) {
                                    requirementsArrayList = new ArrayList<>();
                                    for (int i = 0; i < jsonArray.length(); i++) {
                                        Requirements requirements = new Requirements();
                                        requirements.setRequirement_id(jsonArray.getJSONObject(i).getString("requirement_id"));
                                        requirements.setUser_id(jsonArray.getJSONObject(i).getString("user_id"));
                                        requirements.setGrade_required(jsonArray.getJSONObject(i).getString("grade_required"));
                                        requirements.setPhysical(jsonArray.getJSONObject(i).getString("physical"));
                                        requirements.setChemical(jsonArray.getJSONObject(i).getString("chemical"));
                                        requirements.setTest_certificate_required(jsonArray.getJSONObject(i).getString("test_certificate_required"));
                                        requirements.setLength(jsonArray.getJSONObject(i).getString("length"));
                                        requirements.setType(jsonArray.getJSONObject(i).getString("type"));
                                        requirements.setRequired_by_date(jsonArray.getJSONObject(i).getString("required_by_date"));
                                        requirements.setBudget(jsonArray.getJSONObject(i).getString("budget"));
                                        requirements.setState(jsonArray.getJSONObject(i).getString("state"));
                                        requirements.setCity(jsonArray.getJSONObject(i).getString("city"));
//                                        requirements.setRequirement_id(jsonArray.getJSONObject(i).getString("created_at"));
//                                        requirements.setRequirement_id(jsonArray.getJSONObject(i).getString("updated_at"));

                                        if (jsonArray.getJSONObject(i).has("quantity")) {
                                            JSONArray jsonArray1 = jsonArray.getJSONObject(i).getJSONArray("quantity");
                                            if (jsonArray1.length() > 0) {
                                                ArrayList<Quantity> quantities = new ArrayList<>();
                                                for (int j = 0; j < jsonArray1.length(); j++) {
                                                    Quantity quantity = new Quantity();
                                                    quantity.setSize(jsonArray1.getJSONObject(j).getString("size"));
                                                    quantity.setQuantity(jsonArray1.getJSONObject(j).getString("quantity"));
                                                    quantities.add(quantity);
                                                }
                                                requirements.setQuantityArrayList(quantities);
                                            }
                                        }
                                        if (jsonArray.getJSONObject(i).has("preffered_brands")) {
                                            JSONArray jsonArray1 = jsonArray.getJSONObject(i).getJSONArray("preffered_brands");
                                            if (jsonArray1.length() > 0) {
                                                String[] brand = new String[jsonArray1.length()];
                                                for (int j = 0; j < jsonArray1.length(); j++) {
                                                    brand[j] = jsonArray1.getString(j);
                                                }
                                                requirements.setPreffered_brands(brand);
                                            }
                                        }
                                        requirementsArrayList.add(requirements);
                                    }
                                }
                                EventBus.getDefault().post("GetRequirements True");
                            } else
                                EventBus.getDefault().post("GetRequirements False");
                        } catch (JSONException e) {
                            e.printStackTrace();
                            EventBus.getDefault().post("GetRequirements False");
                        }
                    }
                }, new Response.ErrorListener() {

            @Override
            public void onErrorResponse(VolleyError error) {
                STLog.e("Error Response : ", "Error: " + error.getMessage());
                EventBus.getDefault().post("GetRequirements False");
            }
        });
        RequestQueue requestQueue = Utils.getVolleyRequestQueue(activity);
        requestQueue.add(jsonObjReq);
    }

    public void addBuyerPost(final Activity activity, JSONObject jsonObject) {
        JsonObjectRequest jsonObjReq = new JsonObjectRequest(Request.Method.POST, ServiceApi.BUYER_POST, jsonObject,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        STLog.e("Success Response : ", "Response: " + response.toString());

                        try {
                            boolean state = response.getBoolean("success");
                            if (state) {
                                Preferences.writeBoolean(activity, Preferences.LOGIN, true);

                                EventBus.getDefault().postSticky("NewRequirementPosted True");
                            } else {
                                EventBus.getDefault().postSticky("NewRequirementPosted False@#@" + response.getString("msg"));
                            }
                        } catch (JSONException e) {
                            EventBus.getDefault().postSticky("NewRequirementPosted False");
                        }
                    }
                }, new Response.ErrorListener() {

            @Override
            public void onErrorResponse(VolleyError error) {
                STLog.e("Error Response : ", "Error: " + error.getMessage());
                EventBus.getDefault().postSticky("NewRequirementPosted False");
            }
        });
        RequestQueue requestQueue = Utils.getVolleyRequestQueue(activity);
        requestQueue.add(jsonObjReq);
    }

}
