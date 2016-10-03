package com.tanzil.steelhub.model;

import android.app.Activity;
import android.util.Log;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.tanzil.steelhub.utility.STLog;
import com.tanzil.steelhub.utility.ServiceApi;
import com.tanzil.steelhub.utility.Utils;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

import de.greenrobot.event.EventBus;

/**
 * Created by arun.sharma on 9/14/2016.
 */
public class CommonDataManager {

    private String TAG = CommonDataManager.class.getSimpleName();
    private ArrayList<Brands> brandsArrayList;
    private ArrayList<SteelSizes> steelSizesArrayList;
    private ArrayList<Grades> gradesArrayList;
    private ArrayList<States> statesArrayList;
    private ArrayList<TaxTypes> taxTypesArrayList;
    private ArrayList<CustomerType> customerTypeArrayList;

    public ArrayList<Brands> getBrands(Activity activity, boolean shouldRefresh) {
        if (shouldRefresh)
            getBrandList(activity);
        return brandsArrayList;
    }

    private void getBrandList(Activity activity) {
        JsonObjectRequest jsonObjReq = new JsonObjectRequest(Request.Method.GET, ServiceApi.FETCH_BRANDS, null,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        Log.e(TAG, "onSuccess  --> " + response.toString());

                        try {

                            boolean state = response.getBoolean("success");
                            if (state) {
                                JSONArray jsonArray = response.getJSONArray("data");
                                if (jsonArray == null)
                                    jsonArray = new JSONArray();
                                int count = jsonArray.length();
                                brandsArrayList = new ArrayList<>();
                                if (count > 0)
                                    for (int i = 0; i < count; i++) {
                                        Brands brands = new Brands();
                                        brands.setName(jsonArray.getJSONObject(i).getString("brand_name"));
                                        brands.setId(jsonArray.getJSONObject(i).getString("id"));

                                        brandsArrayList.add(brands);
                                    }
                                EventBus.getDefault().post("GetBrandList True");
                            }

                        } catch (JSONException e) {
                            e.printStackTrace();
                            EventBus.getDefault().post("GetBrandList False");
                        }
                    }

                }, new Response.ErrorListener() {

            @Override
            public void onErrorResponse(VolleyError error) {
                STLog.e("Error Response : ", "Error: " + error.getMessage());
                EventBus.getDefault().post("GetBrandList False");
            }
        });
        RequestQueue requestQueue = Utils.getVolleyRequestQueue(activity);
        requestQueue.add(jsonObjReq);
    }

    public ArrayList<SteelSizes> getSteelSize(Activity activity, boolean shouldRefresh) {
        if (shouldRefresh)
            getSizeList(activity);
        return steelSizesArrayList;
    }

    private void getSizeList(Activity activity) {
        JsonObjectRequest jsonObjReq = new JsonObjectRequest(Request.Method.GET, ServiceApi.FETCH_STEEL_SIZES, null,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        STLog.e("Success Response : ", "Response: " + response.toString());

                        try {

                            boolean state = response.getBoolean("success");
                            if (state) {
                                JSONArray jsonArray = response.getJSONArray("data");
                                if (jsonArray == null)
                                    jsonArray = new JSONArray();
                                int count = jsonArray.length();
                                steelSizesArrayList = new ArrayList<>();
                                if (count > 0)
                                    for (int i = 0; i < count; i++) {
                                        SteelSizes steelSizes = new SteelSizes();
                                        steelSizes.setSize(jsonArray.getJSONObject(i).getString("size"));
                                        steelSizes.setId(jsonArray.getJSONObject(i).getString("id"));

                                        steelSizesArrayList.add(steelSizes);
                                    }
                                EventBus.getDefault().post("GetSizeList True");
                            }

                        } catch (JSONException e) {
                            e.printStackTrace();
                            EventBus.getDefault().post("GetSizeList False");
                        }
                    }
                }, new Response.ErrorListener() {

            @Override
            public void onErrorResponse(VolleyError error) {
                STLog.e("Error Response : ", "Error: " + error.getMessage());
                EventBus.getDefault().post("GetSizeList False");
            }
        });
        RequestQueue requestQueue = Utils.getVolleyRequestQueue(activity);
        requestQueue.add(jsonObjReq);
    }

    public ArrayList<Grades> getGrades(Activity activity, boolean shouldRefresh) {
        if (shouldRefresh)
            getGradeList(activity);
        return gradesArrayList;
    }

    private void getGradeList(Activity activity) {
        JsonObjectRequest jsonObjReq = new JsonObjectRequest(Request.Method.GET, ServiceApi.FETCH_GRADES, null,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        STLog.e("Success Response : ", "Response: " + response.toString());

                        try {
                            boolean state = response.getBoolean("success");
                            if (state) {
                                JSONArray jsonArray = response.getJSONArray("data");
                                if (jsonArray == null)
                                    jsonArray = new JSONArray();
                                int count = jsonArray.length();
                                gradesArrayList = new ArrayList<>();
                                if (count > 0)
                                    for (int i = 0; i < count; i++) {
                                        Grades grades = new Grades();
                                        grades.setGrade(jsonArray.getJSONObject(i).getString("grade"));
                                        grades.setId(jsonArray.getJSONObject(i).getString("id"));

                                        gradesArrayList.add(grades);
                                    }
                                EventBus.getDefault().post("GetGradeList True");
                            }

                        } catch (JSONException e) {
                            e.printStackTrace();
                            EventBus.getDefault().post("GetGradeList False");
                        }
                    }
                }, new Response.ErrorListener() {

            @Override
            public void onErrorResponse(VolleyError error) {
                STLog.e("Error Response : ", "Error: " + error.getMessage());
                EventBus.getDefault().post("GetGradeList False");
            }
        });
        RequestQueue requestQueue = Utils.getVolleyRequestQueue(activity);
        requestQueue.add(jsonObjReq);
    }

    public ArrayList<States> getStates(Activity activity, boolean shouldRefresh) {
        if (shouldRefresh)
            getStateList(activity);
        return statesArrayList;
    }

    private void getStateList(Activity activity) {
        JsonObjectRequest jsonObjReq = new JsonObjectRequest(Request.Method.GET, ServiceApi.FETCH_STATES, null,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        Log.e(TAG, "onSuccess  --> " + response.toString());

                        try {

                            boolean state = response.getBoolean("success");
                            if (state) {
                                JSONArray jsonArray = response.getJSONArray("data");
                                if (jsonArray == null)
                                    jsonArray = new JSONArray();
                                int count = jsonArray.length();
                                statesArrayList = new ArrayList<>();
                                if (count > 0)
                                    for (int i = 0; i < count; i++) {
                                        States states = new States();
                                        states.setName(jsonArray.getJSONObject(i).getString("name"));
                                        states.setId(jsonArray.getJSONObject(i).getString("id"));
                                        states.setCode(jsonArray.getJSONObject(i).getString("code"));

                                        statesArrayList.add(states);
                                    }
                                EventBus.getDefault().post("GetStateList True");
                            }

                        } catch (JSONException e) {
                            e.printStackTrace();
                            EventBus.getDefault().post("GetStateList False");
                        }
                    }

                }, new Response.ErrorListener() {

            @Override
            public void onErrorResponse(VolleyError error) {
                STLog.e("Error Response : ", "Error: " + error.getMessage());
                EventBus.getDefault().post("GetStateList False");
            }
        });
        RequestQueue requestQueue = Utils.getVolleyRequestQueue(activity);
        requestQueue.add(jsonObjReq);
    }

    public ArrayList<TaxTypes> getTaxTypes(Activity activity, boolean shouldRefresh) {
        if (shouldRefresh)
            getTaxTypeList(activity);
        return taxTypesArrayList;
    }

    private void getTaxTypeList(Activity activity) {
        JsonObjectRequest jsonObjReq = new JsonObjectRequest(Request.Method.GET, ServiceApi.FETCH_TAX_TYPES, null,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        STLog.e("Success Response : ", "Response: " + response.toString());

                        try {

                            boolean state = response.getBoolean("success");
                            if (state) {
                                JSONArray jsonArray = response.getJSONArray("data");
                                if (jsonArray == null)
                                    jsonArray = new JSONArray();
                                int count = jsonArray.length();
                                taxTypesArrayList = new ArrayList<>();
                                if (count > 0)
                                    for (int i = 0; i < count; i++) {
                                        TaxTypes taxTypes = new TaxTypes();
                                        taxTypes.setType(jsonArray.getJSONObject(i).getString("type"));
                                        taxTypes.setId(jsonArray.getJSONObject(i).getString("id"));

                                        taxTypesArrayList.add(taxTypes);
                                    }
                                EventBus.getDefault().post("GetTaxTypeList True");
                            }

                        } catch (JSONException e) {
                            e.printStackTrace();
                            EventBus.getDefault().post("GetTaxTypeList False");
                        }
                    }
                }, new Response.ErrorListener() {

            @Override
            public void onErrorResponse(VolleyError error) {
                STLog.e("Error Response : ", "Error: " + error.getMessage());
                EventBus.getDefault().post("GetTaxTypeList False");
            }
        });
        RequestQueue requestQueue = Utils.getVolleyRequestQueue(activity);
        requestQueue.add(jsonObjReq);
    }

    public ArrayList<CustomerType> getCustomerType(Activity activity, boolean shouldRefresh) {
        if (shouldRefresh)
            getCustomerTypeList(activity);
        return customerTypeArrayList;
    }

    private void getCustomerTypeList(Activity activity) {
        JsonObjectRequest jsonObjReq = new JsonObjectRequest(Request.Method.GET, ServiceApi.FETCH_CUSTOMER_TYPE, null,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        STLog.e("Success Response : ", "Response: " + response.toString());

                        try {
                            boolean state = response.getBoolean("success");
                            if (state) {
                                JSONArray jsonArray = response.getJSONArray("data");
                                if (jsonArray == null)
                                    jsonArray = new JSONArray();
                                int count = jsonArray.length();
                                customerTypeArrayList = new ArrayList<>();
                                if (count > 0)
                                    for (int i = 0; i < count; i++) {
                                        CustomerType customerType = new CustomerType();
                                        customerType.setType(jsonArray.getJSONObject(i).getString("type"));
                                        customerType.setId(jsonArray.getJSONObject(i).getString("id"));

                                        customerTypeArrayList.add(customerType);
                                    }
                                EventBus.getDefault().post("GetCustomerTypeList True");
                            }

                        } catch (JSONException e) {
                            e.printStackTrace();
                            EventBus.getDefault().post("GetCustomerTypeList False");
                        }
                    }
                }, new Response.ErrorListener() {

            @Override
            public void onErrorResponse(VolleyError error) {
                STLog.e("Error Response : ", "Error: " + error.getMessage());
                EventBus.getDefault().post("GetCustomerTypeList False");
            }
        });
        RequestQueue requestQueue = Utils.getVolleyRequestQueue(activity);
        requestQueue.add(jsonObjReq);
    }
}
