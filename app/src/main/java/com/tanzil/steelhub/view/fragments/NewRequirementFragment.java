package com.tanzil.steelhub.view.fragments;

/**
 * Created by arun.sharma on 29/07/15.
 */

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.tanzil.steelhub.R;
import com.tanzil.steelhub.customUi.MyButton;
import com.tanzil.steelhub.customUi.MyEditText;
import com.tanzil.steelhub.customUi.MyTextView;
import com.tanzil.steelhub.model.Brands;
import com.tanzil.steelhub.model.Grades;
import com.tanzil.steelhub.model.ModelManager;
import com.tanzil.steelhub.model.Quantity;
import com.tanzil.steelhub.model.Specifications;
import com.tanzil.steelhub.model.States;
import com.tanzil.steelhub.model.SteelSizes;
import com.tanzil.steelhub.utility.STLog;
import com.tanzil.steelhub.utility.Utils;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

import de.greenrobot.event.EventBus;


public class NewRequirementFragment extends Fragment implements View.OnClickListener {

    private String TAG = NewRequirementFragment.class.getSimpleName();
    private Activity activity;
    private MyEditText et_quantity, et_diameter, et_preferred_brands, et_grade_required, et_city, et_state, et_budget_amount;
    private MyTextView txt_random, txt_standard, txt_bend, txt_straight;
    //    private MyButton btn_add_more;
    private LinearLayout addMoreLayout, default_quantity_layout;
    private ArrayList<Brands> brandsArrayList;
    private ArrayList<Grades> gradesArrayList;
    private ArrayList<Quantity> quantityArrayList;
    private ArrayList<SteelSizes> steelSizesArrayList;
    private ArrayList<Specifications> specificationsArrayList;
    private ArrayList<States> statesArrayList;
    private ImageView icon_remove;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        this.activity = super.getActivity();
        View rootView = inflater.inflate(R.layout.new_requirement_screen, container, false);

        et_budget_amount = (MyEditText) rootView.findViewById(R.id.et_budget_amount);
        et_quantity = (MyEditText) rootView.findViewById(R.id.et_quantity);
        et_diameter = (MyEditText) rootView.findViewById(R.id.et_diameter);
        et_preferred_brands = (MyEditText) rootView.findViewById(R.id.et_preferred_brands);
        et_grade_required = (MyEditText) rootView.findViewById(R.id.et_grade_required);
        et_city = (MyEditText) rootView.findViewById(R.id.et_city);
        et_state = (MyEditText) rootView.findViewById(R.id.et_state);

        txt_random = (MyTextView) rootView.findViewById(R.id.txt_random);
        txt_standard = (MyTextView) rootView.findViewById(R.id.txt_standard);
        txt_bend = (MyTextView) rootView.findViewById(R.id.txt_bend);
        txt_straight = (MyTextView) rootView.findViewById(R.id.txt_straight);

        LinearLayout layout_grade_required = (LinearLayout) rootView.findViewById(R.id.layout_grade_required);
        LinearLayout layout_physical = (LinearLayout) rootView.findViewById(R.id.layout_physical);
        LinearLayout layout_chemical = (LinearLayout) rootView.findViewById(R.id.layout_chemical);

        default_quantity_layout = (LinearLayout) rootView.findViewById(R.id.default_quantity_layout);
        addMoreLayout = (LinearLayout) rootView.findViewById(R.id.layout_add_more);
        icon_remove = (ImageView) rootView.findViewById(R.id.icon_remove);

        MyButton btn_add_more = (MyButton) rootView.findViewById(R.id.btn_add_more);
        btn_add_more.setTransformationMethod(null);
        MyButton btn_submit = (MyButton) rootView.findViewById(R.id.btn_submit);
        btn_submit.setTransformationMethod(null);

        /**     Checking the common data for dropdown usage     **/
        brandsArrayList = ModelManager.getInstance().getCommonDataManager().getBrands(activity, false);
        gradesArrayList = ModelManager.getInstance().getCommonDataManager().getGrades(activity, false);
        steelSizesArrayList = ModelManager.getInstance().getCommonDataManager().getSteelSize(activity, false);
        statesArrayList = ModelManager.getInstance().getCommonDataManager().getStates(activity, false);
        if (brandsArrayList == null) {
//            Utils.showLoading(activity, activity.getString(R.string.please_wait));
            ModelManager.getInstance().getCommonDataManager().getBrands(activity, true);
        }
        if (gradesArrayList == null) {
//            Utils.showLoading(activity, activity.getString(R.string.please_wait));
            ModelManager.getInstance().getCommonDataManager().getGrades(activity, true);
        }
        if (steelSizesArrayList == null) {
//            Utils.showLoading(activity, activity.getString(R.string.please_wait));
            ModelManager.getInstance().getCommonDataManager().getSteelSize(activity, true);
        }
        if (statesArrayList == null) {
//            Utils.showLoading(activity, activity.getString(R.string.please_wait));
            ModelManager.getInstance().getCommonDataManager().getStates(activity, true);
        }

        /**     click events     **/
        btn_add_more.setOnClickListener(this);
        btn_submit.setOnClickListener(this);
        layout_grade_required.setOnClickListener(this);
        layout_physical.setOnClickListener(this);
        layout_chemical.setOnClickListener(this);
        txt_random.setOnClickListener(this);
        txt_standard.setOnClickListener(this);
        txt_bend.setOnClickListener(this);
        txt_straight.setOnClickListener(this);
        icon_remove.setOnClickListener(this);
        // Inflate the layout for this fragment
        return rootView;
    }

    private void addMoreQuantity() {

        LayoutInflater layoutInflater =
                (LayoutInflater) activity.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        final View addView = layoutInflater.inflate(R.layout.row_add_more, null);
        MyEditText quantity = (MyEditText) addView.findViewById(R.id.quantity);
        MyEditText diameter = (MyEditText) addView.findViewById(R.id.diameter);
        ImageView remove = (ImageView) addView.findViewById(R.id.remove);
        remove.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (addMoreLayout.getChildCount() == 1)
                    if (default_quantity_layout.getVisibility() == View.GONE) {
                        default_quantity_layout.setVisibility(View.VISIBLE);
                        icon_remove.setVisibility(View.GONE);
                    }
                ((LinearLayout) addView.getParent()).removeView(addView);
            }
        });
        addMoreLayout.addView(addView);

        if (addMoreLayout != null)
            if (addMoreLayout.getChildCount() > 0)
                icon_remove.setVisibility(View.VISIBLE);
            else
                icon_remove.setVisibility(View.GONE);
        else
            icon_remove.setVisibility(View.GONE);
    }

    private boolean isValidate() {
        boolean result = true;
        if (et_preferred_brands.getText().length() == 0) {
            et_preferred_brands.requestFocus();
            Toast.makeText(activity, getString(R.string.please_enter_preferred_brands),
                    Toast.LENGTH_SHORT).show();
            result = false;
        } else if (et_grade_required.getText().length() == 0) {
            et_grade_required.requestFocus();
            Toast.makeText(activity, getString(R.string.please_enter_grade_required),
                    Toast.LENGTH_SHORT).show();
            result = false;
        } else if (et_city.getText().length() == 0) {
            et_city.requestFocus();
            Toast.makeText(activity, getString(R.string.please_enter_city),
                    Toast.LENGTH_SHORT).show();
            result = false;
        } else if (et_state.getText().length() == 0) {
            et_state.requestFocus();
            Toast.makeText(activity, getString(R.string.please_enter_state),
                    Toast.LENGTH_SHORT).show();
            result = false;
        } else if (et_budget_amount.getText().length() == 0) {
            et_budget_amount.requestFocus();
            Toast.makeText(activity, getString(R.string.please_enter_budget_amount),
                    Toast.LENGTH_SHORT).show();
            result = false;
        }
        return result;
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.layout_grade_required:
                break;
            case R.id.btn_add_more:
                addMoreQuantity();
                break;
            case R.id.layout_physical:
                break;
            case R.id.layout_chemical:
                break;
            case R.id.txt_random:
                break;
            case R.id.txt_standard:
                break;
            case R.id.txt_bend:
                break;
            case R.id.txt_straight:
                break;
            case R.id.btn_submit:

                JSONObject jsonObject = new JSONObject();
                try {
                    jsonObject.put("user_id", "");
                    jsonObject.put("physical", "");
                    jsonObject.put("chemical", "");
                    jsonObject.put("length", "");
                    jsonObject.put("type", "");
                    jsonObject.put("tax_type", "");
                    jsonObject.put("required_by_date", "");
                    jsonObject.put("budget", "");
                    jsonObject.put("city", "");
                    jsonObject.put("state", "");
                    jsonObject.put("grade_required", "");
                    jsonObject.put("preffered_brands", new String[3]);

                    JSONArray jsonArray = new JSONArray();
                    for (int i = 0; i < specificationsArrayList.size(); i++) {
                        JSONObject jsonObject1 = new JSONObject();
                        jsonObject1.put("size", specificationsArrayList.get(i).getSize());
                        jsonObject1.put("quantity", specificationsArrayList.get(i).getQuantity());
                        jsonArray.put(i, jsonObject1);
                    }

                    jsonObject.put("specification", jsonArray);
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                break;
            case R.id.icon_remove:
                default_quantity_layout.setVisibility(View.GONE);
                et_quantity.setText("");
                et_diameter.setText("");
                break;
        }
    }

    @Override
    public void onStart() {
        super.onStart();
        EventBus.getDefault().register(this);
    }

    @Override
    public void onStop() {
        super.onStop();
        EventBus.getDefault().unregister(this);

    }

    public void onEventMainThread(String message) {
        if (message.equalsIgnoreCase("GetBrandList True")) {
            Utils.dismissLoading();
            brandsArrayList = ModelManager.getInstance().getCommonDataManager().getBrands(activity, false);
            STLog.e(TAG, "GetBrandList True");
        } else if (message.contains("GetBrandList False")) {
            // showMatchHistoryList();
            Utils.showMessage(activity, activity.getString(R.string.oops_something_went_wrong));
            STLog.e(TAG, "GetBrandList False");
            Utils.dismissLoading();
        } else if (message.equalsIgnoreCase("GetSizeList True")) {
            Utils.dismissLoading();
            steelSizesArrayList = ModelManager.getInstance().getCommonDataManager().getSteelSize(activity, false);
            STLog.e(TAG, "GetSizeList True");
        } else if (message.contains("GetSizeList False")) {
            // showMatchHistoryList();
            Utils.showMessage(activity, activity.getString(R.string.oops_something_went_wrong));
            STLog.e(TAG, "GetSizeList False");
            Utils.dismissLoading();
        } else if (message.equalsIgnoreCase("GetGradeList True")) {
            Utils.dismissLoading();
            gradesArrayList = ModelManager.getInstance().getCommonDataManager().getGrades(activity, false);
            STLog.e(TAG, "GetGradeList True");
        } else if (message.contains("GetGradeList False")) {
            // showMatchHistoryList();
            Utils.showMessage(activity, activity.getString(R.string.oops_something_went_wrong));
            STLog.e(TAG, "GetGradeList False");
            Utils.dismissLoading();
        } else if (message.equalsIgnoreCase("GetStateList True")) {
            Utils.dismissLoading();
            statesArrayList = ModelManager.getInstance().getCommonDataManager().getStates(activity, false);
            STLog.e(TAG, "GetStateList True");
        } else if (message.contains("GetStateList False")) {
            // showMatchHistoryList();
            Utils.showMessage(activity, activity.getString(R.string.oops_something_went_wrong));
            STLog.e(TAG, "GetStateList False");
            Utils.dismissLoading();
        } else if (message.equalsIgnoreCase("NewRequirementPosted True")) {
            Utils.dismissLoading();
            ((FragmentActivity) activity).getSupportFragmentManager()
                    .popBackStack();
            STLog.e(TAG, "NewRequirementPosted True");
        } else if (message.contains("NewRequirementPosted False")) {
            // showMatchHistoryList();
            Utils.showMessage(activity, activity.getString(R.string.oops_something_went_wrong));
            STLog.e(TAG, "NewRequirementPosted False");
            Utils.dismissLoading();
        }

    }
}
