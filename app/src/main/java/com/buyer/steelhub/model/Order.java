package com.buyer.steelhub.model;

import android.app.Activity;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
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
 * Created by arun.sharma on 9/14/2016.
 */
public class Order {
    String requirement_id, buyer_id, seller_id, final_amt, shipping_id, billing_id, RTGS, order_status, created_at, updated_at,
            user_id, grade_required, physical, chemical, test_certificate_required, length, type, required_by_date, budget, state,
            city, tax_type;

    private ArrayList<Response> responseArrayList;

    private String[] preffered_brands;
    private ArrayList<Quantity> quantityArrayList;

    public String getShipping_id() {
        return shipping_id;
    }

    public void setShipping_id(String shipping_id) {
        this.shipping_id = shipping_id;
    }

    public String getBilling_id() {
        return billing_id;
    }

    public void setBilling_id(String billing_id) {
        this.billing_id = billing_id;
    }

    public String getUser_id() {
        return user_id;
    }

    public void setUser_id(String user_id) {
        this.user_id = user_id;
    }

    public String getGrade_required() {
        return grade_required;
    }

    public void setGrade_required(String grade_required) {
        this.grade_required = grade_required;
    }

    public String getPhysical() {
        return physical;
    }

    public void setPhysical(String physical) {
        this.physical = physical;
    }

    public String getChemical() {
        return chemical;
    }

    public void setChemical(String chemical) {
        this.chemical = chemical;
    }

    public String getTest_certificate_required() {
        return test_certificate_required;
    }

    public void setTest_certificate_required(String test_certificate_required) {
        this.test_certificate_required = test_certificate_required;
    }

    public String getLength() {
        return length;
    }

    public void setLength(String length) {
        this.length = length;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getRequired_by_date() {
        return required_by_date;
    }

    public void setRequired_by_date(String required_by_date) {
        this.required_by_date = required_by_date;
    }

    public String getBudget() {
        return budget;
    }

    public void setBudget(String budget) {
        this.budget = budget;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getTax_type() {
        return tax_type;
    }

    public void setTax_type(String tax_type) {
        this.tax_type = tax_type;
    }

    public String getBuyer_id() {
        return buyer_id;
    }

    public void setBuyer_id(String buyer_id) {
        this.buyer_id = buyer_id;
    }

    public String getSeller_id() {
        return seller_id;
    }

    public void setSeller_id(String seller_id) {
        this.seller_id = seller_id;
    }

    public String getFinal_amt() {
        return final_amt;
    }

    public void setFinal_amt(String final_amt) {
        this.final_amt = final_amt;
    }

    public String getRTGS() {
        return RTGS;
    }

    public void setRTGS(String RTGS) {
        this.RTGS = RTGS;
    }

    public String getOrder_status() {
        return order_status;
    }

    public void setOrder_status(String order_status) {
        this.order_status = order_status;
    }

    public ArrayList<Response> getResponseArrayList() {
        return responseArrayList;
    }

    public void setResponseArrayList(ArrayList<Response> responseArrayList) {
        this.responseArrayList = responseArrayList;
    }

    public String getRequirement_id() {
        return requirement_id;
    }

    public void setRequirement_id(String requirement_id) {
        this.requirement_id = requirement_id;
    }


    public String getCreated_at() {
        return created_at;
    }

    public void setCreated_at(String created_at) {
        this.created_at = created_at;
    }

    public String getUpdated_at() {
        return updated_at;
    }

    public void setUpdated_at(String updated_at) {
        this.updated_at = updated_at;
    }

    public String[] getPreffered_brands() {
        return preffered_brands;
    }

    public void setPreffered_brands(String[] preffered_brands) {
        this.preffered_brands = preffered_brands;
    }

    public ArrayList<Quantity> getQuantityArrayList() {
        return quantityArrayList;
    }

    public void setQuantityArrayList(ArrayList<Quantity> quantityArrayList) {
        this.quantityArrayList = quantityArrayList;
    }


    public void updateConversation(final Activity activity, JSONObject jsonObject, final String token) {
        JsonObjectRequest jsonObjReq = new JsonObjectRequest(Request.Method.POST, ServiceApi.UPDATE_CONVERSATION, jsonObject,
                new com.android.volley.Response.Listener<JSONObject>() {
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
                }, new com.android.volley.Response.ErrorListener() {

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
