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
public class OrderManager {
    private ArrayList<Order> ordersArrayList;
    private String token = "";

    public ArrayList<Order> getOrders(Activity activity, boolean shouldRefresh, int orderType) {
        if (shouldRefresh)
            getOrderList(activity, orderType);
        return ordersArrayList;
    }

    private void getOrderList(final Activity activity, int orderType) {
        JSONObject jsonObject = new JSONObject();
        if (orderType == 0)
            token = "Pending";
        else if (orderType == 1)
            token = "Confirmed";
        else if (orderType == 3)
            token = "InProgress";
        else if (orderType == 4)
            token = "History";

        try {
            jsonObject.put("type", "buyer");
            jsonObject.put("status", orderType);
        } catch (JSONException e) {
            e.printStackTrace();
        }

        STLog.e("Post Data : ", "" + jsonObject.toString());
        JsonObjectRequest jsonObjReq = new JsonObjectRequest(Request.Method.POST, ServiceApi.GET_ORDERS, jsonObject,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        STLog.e("Success Response : ", "Response: " + response.toString());
                        try {
                            if (response.getBoolean("success")) {
                                JSONArray jsonArray = response.getJSONArray("data");
                                int count = jsonArray.length();
                                if (count > 0) {
                                    ordersArrayList = new ArrayList<>();
                                    for (int i = 0; i < jsonArray.length(); i++) {

//                                        "requirement_id": 2,
//                                                "buyer_id": 23,
//                                                "seller_id": 22,
//                                                "final_amt": "900000",
//                                                "shipping_id": 0,
//                                                "billing_id": 0,
//                                                "RTGS": 0,
//                                                "order_status": 0,
//                                                "created_at": {
//                                            "date": "2017-03-19 11:24:20.000000",
//                                                    "timezone_type": 3,
//                                                    "timezone": "UTC"
//                                        },
//                                        "updated_at": {
//                                            "date": "2016-12-11 15:53:02.000000",
//                                                    "timezone_type": 3,
//                                                    "timezone": "UTC"
//                                        },
//                                        "postdata": {
//                                            "user_id": 2,
//                                                    "quantity": [{
//                                                "size": "8 mm",
//                                                        "quantity": "21"
//                                            }],
//                                            "grade_required": 7,
//                                                    "physical": 1,
//                                                    "chemical": 1,
//                                                    "test_certificate_required": 1,
//                                                    "length": 0,
//                                                    "type": 0,
//                                                    "preffered_brands": ["Kamdhenu"],
//                                            "required_by_date": "26\/09\/2016",
//                                                    "budget": 21000,
//                                                    "state": "Andaman And Nicobar Islands",
//                                                    "city": "chandigarh",
//                                                    "tax_type": 1
//                                        }
//                                    }]
                                        Order order = new Order();
                                        order.setRequirement_id(jsonArray.getJSONObject(i).getString("requirement_id"));
                                        order.setBuyer_id(jsonArray.getJSONObject(i).getString("buyer_id"));
                                        order.setSeller_id(jsonArray.getJSONObject(i).getString("seller_id"));
                                        order.setFinal_amt(jsonArray.getJSONObject(i).getString("final_amt"));
                                        order.setShipping_id(jsonArray.getJSONObject(i).getString("shipping_id"));
                                        order.setBilling_id(jsonArray.getJSONObject(i).getString("billing_id"));
                                        order.setRTGS(jsonArray.getJSONObject(i).getString("RTGS"));
                                        order.setOrder_status(jsonArray.getJSONObject(i).getString("order_status"));
                                        order.setCreated_at(jsonArray.getJSONObject(i).getJSONObject("created_at").getString("date"));
                                        order.setUpdated_at(jsonArray.getJSONObject(i).getJSONObject("updated_at").getString("date"));
                                        if (jsonArray.getJSONObject(i).has("postdata")) {
                                            JSONObject jsonObject1 = jsonArray.getJSONObject(i).getJSONObject("postdata");
                                            order.setUser_id(jsonObject1.getString("user_id"));
                                            order.setGrade_required(jsonObject1.getString("grade_required"));
                                            order.setPhysical(jsonObject1.getString("physical"));
                                            order.setChemical(jsonObject1.getString("chemical"));
                                            order.setTest_certificate_required(jsonObject1.getString("test_certificate_required"));
                                            order.setLength(jsonObject1.getString("length"));
                                            order.setType(jsonObject1.getString("type"));
                                            order.setRequired_by_date(jsonObject1.getString("required_by_date"));
                                            order.setState(jsonObject1.getString("state"));
                                            order.setCity(jsonObject1.getString("city"));
                                            order.setTax_type(jsonObject1.getString("tax_type"));
                                            if (jsonObject1.has("quantity")) {
                                                JSONArray jsonArray1 = jsonObject1.getJSONArray("quantity");
                                                if (jsonArray1.length() > 0) {
                                                    ArrayList<Quantity> quantities = new ArrayList<>();
                                                    for (int j = 0; j < jsonArray1.length(); j++) {
                                                        Quantity quantity = new Quantity();
                                                        quantity.setSize(jsonArray1.getJSONObject(j).getString("size"));
                                                        quantity.setQuantity(jsonArray1.getJSONObject(j).getString("quantity"));
                                                        quantities.add(quantity);
                                                    }
                                                    order.setQuantityArrayList(quantities);
                                                }
                                            }

                                            if (jsonObject1.has("preffered_brands")) {
                                                JSONArray jsonArray1 = jsonObject1.getJSONArray("preffered_brands");
                                                if (jsonArray1.length() > 0) {
                                                    String[] brand = new String[jsonArray1.length()];
                                                    for (int j = 0; j < jsonArray1.length(); j++) {
                                                        brand[j] = jsonArray1.getString(j);
                                                    }
                                                    order.setPreffered_brands(brand);
                                                }
                                            }
                                        }
                                        ordersArrayList.add(order);
                                    }
                                }
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
