package com.buyer.steelhub.view.fragments;

import android.app.Activity;
import android.app.Dialog;
import android.content.Intent;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.content.LocalBroadcastManager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.ListView;

import com.buyer.steelhub.R;
import com.buyer.steelhub.customUi.MyButton;
import com.buyer.steelhub.customUi.MyEditText;
import com.buyer.steelhub.customUi.MyTextView;
import com.buyer.steelhub.model.Address;
import com.buyer.steelhub.model.ModelManager;
import com.buyer.steelhub.model.States;
import com.buyer.steelhub.utility.STLog;
import com.buyer.steelhub.utility.Utils;
import com.buyer.steelhub.view.adapter.CommonDialogAdapter;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

import de.greenrobot.event.EventBus;

/**
 * Created by arun.sharma on 11/11/2016.
 */
public class AddNewAddressFragment extends Fragment implements View.OnClickListener {
    private String TAG = AddNewAddressFragment.class.getSimpleName();
    private Activity activity;
    private MyEditText et_firm_name, et_address_1, et_address_2, et_landmark, et_city, et_state, et_zip, et_mobile, et_landline;
    private String type = "", action = "", stateId = "", token = "";
    private ArrayList<States> statesArrayList;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        this.activity = super.getActivity();
        Intent intent = new Intent("Header");
        intent.putExtra("message", activity.getString(R.string.address));

        LocalBroadcastManager.getInstance(activity).sendBroadcast(intent);
        View rootView = inflater.inflate(R.layout.add_address_fragment, container, false);

        try {
            if (getArguments() != null) {
                Bundle bundle = getArguments();
                type = bundle.getString("type");
                action = bundle.getString("action");
            }

        } catch (Exception ex) {
            Log.e(TAG, ex.toString());
        }

        et_firm_name = (MyEditText) rootView.findViewById(R.id.et_firm_name);
        et_address_1 = (MyEditText) rootView.findViewById(R.id.et_address_1);
        et_address_2 = (MyEditText) rootView.findViewById(R.id.et_address_2);
        et_landmark = (MyEditText) rootView.findViewById(R.id.et_landmark);
        et_city = (MyEditText) rootView.findViewById(R.id.et_city);
        et_state = (MyEditText) rootView.findViewById(R.id.et_state);
        et_zip = (MyEditText) rootView.findViewById(R.id.et_zip);
        et_mobile = (MyEditText) rootView.findViewById(R.id.et_mobile);
        et_landline = (MyEditText) rootView.findViewById(R.id.et_landline);

        MyButton btn_submit = (MyButton) rootView.findViewById(R.id.btn_submit);
        btn_submit.setTransformationMethod(null);

        btn_submit.setOnClickListener(this);
        et_state.setOnClickListener(this);

        statesArrayList = ModelManager.getInstance().getCommonDataManager().getStates(activity, false);
        if (statesArrayList == null) {
            Utils.defaultLoader(activity);
            ModelManager.getInstance().getCommonDataManager().getStates(activity, true);
        } else if (!Utils.isEmptyString(action))
            if (action.equalsIgnoreCase("edit"))
                setData();
        return rootView;
    }

    private void setData() {
        ArrayList<Address> addressArrayList = ModelManager.getInstance().getAddressManager().getAddresses(activity, false);
        for (int i = 0; i < addressArrayList.size(); i++)
            if (addressArrayList.get(i).getAddressType().equalsIgnoreCase(type)) {
                et_firm_name.setText(addressArrayList.get(i).getFirm_name());
                et_address_1.setText(addressArrayList.get(i).getAddress1());
                et_address_2.setText(addressArrayList.get(i).getAddress2());
                et_landmark.setText(addressArrayList.get(i).getLandmark());
                et_landline.setText(addressArrayList.get(i).getLandline());
                et_city.setText(addressArrayList.get(i).getCity());
                et_state.setText(addressArrayList.get(i).getState());
                et_zip.setText(addressArrayList.get(i).getPincode());
                et_mobile.setText(addressArrayList.get(i).getMobile());
                break;
            }
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.btn_submit:
                if (et_firm_name.getText().toString().trim().length() == 0) {
                    et_firm_name.requestFocus();
                    Utils.showMessage(activity, "Please enter firm name.");
                } else if (et_address_1.getText().toString().trim().length() == 0 &&
                        et_address_2.getText().toString().trim().length() == 0) {
                    et_address_1.requestFocus();
                    Utils.showMessage(activity, "Please enter address.");
//                } else if (et_address_2.getText().toString().trim().length() == 0) {
//                    et_address_2.requestFocus();
//                    Utils.showMessage(activity, "Please enter address.");
                } else if (et_city.getText().toString().trim().length() == 0) {
                    et_city.requestFocus();
                    Utils.showMessage(activity, "Please enter firm name.");
                } else if (et_state.getText().toString().trim().length() == 0) {
                    et_state.requestFocus();
                    Utils.showMessage(activity, "Please enter select state.");
                } else if (et_zip.getText().toString().trim().length() == 0) {
                    et_zip.requestFocus();
                    Utils.showMessage(activity, "Please enter zip.");
                } else if (et_mobile.getText().toString().trim().length() == 0) {
                    et_mobile.requestFocus();
                    Utils.showMessage(activity, "Please enter mobile.");
                } else {
                    JSONObject jsonObject = new JSONObject();
                    try {
                        jsonObject.put("firm_name", et_firm_name.getText().toString());
                        jsonObject.put("city", et_city.getText().toString());
//                        jsonObject.put("state", et_state.getText().toString());
                        jsonObject.put("state", stateId);
                        jsonObject.put("address1", et_address_1.getText().toString());
                        jsonObject.put("address2", et_address_2.getText().toString());
                        jsonObject.put("pincode", et_zip.getText().toString());
                        jsonObject.put("mobile", et_mobile.getText().toString());
                        jsonObject.put("landline", et_landline.getText().toString());
                        jsonObject.put("addressType", type);
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }

                    Utils.defaultLoader(activity);
                    if (action.equalsIgnoreCase("edit"))
                        token = "UpdateAddress";
                    else
                        token = "AddNewAddress";

                    ModelManager.getInstance().getAddressManager().addAddress(activity, jsonObject, token);
                }
                break;

            case R.id.et_state:
                showDropDownDialog();
                break;
        }
    }

    private void showDropDownDialog() {
        final Dialog dropDownDialog = new Dialog(activity);
        dropDownDialog.requestWindowFeature(Window.FEATURE_NO_TITLE); // before
        dropDownDialog.setContentView(R.layout.dialog_dropdown_list);
        dropDownDialog.getWindow().setBackgroundDrawable(
                new ColorDrawable(android.graphics.Color.TRANSPARENT));
        MyTextView titleView = (MyTextView) dropDownDialog
                .findViewById(R.id.title_name);
        titleView.setText(activity.getString(R.string.please_select_an_option));
        final ListView listView = (ListView) dropDownDialog
                .findViewById(R.id.list_view);

        ArrayList<String> list = new ArrayList<>();
        if (statesArrayList != null)
            if (statesArrayList.size() > 0)
                for (int i = 0; i < statesArrayList.size(); i++)
                    list.add(statesArrayList.get(i).getName());
            else {
                Utils.showMessage(activity, activity.getString(R.string.no_record_found));
                return;
            }
        else {
            Utils.showMessage(activity, activity.getString(R.string.no_record_found));
            return;
        }
        CommonDialogAdapter commonDialogAdapter = new CommonDialogAdapter(
                activity, list);
        listView.setAdapter(commonDialogAdapter);
        commonDialogAdapter.notifyDataSetChanged();

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> parent, View view,
                                    int position, long id) {
                // TODO Auto-generated method stub
                et_state.setText(statesArrayList.get(position).getName());
                stateId = statesArrayList.get(position).getCode();
                dropDownDialog.dismiss();
            }
        });

        dropDownDialog.show();
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
        if (message.equalsIgnoreCase("GetStateList True")) {
            Utils.dismissLoading();
            statesArrayList = ModelManager.getInstance().getCommonDataManager().getStates(activity, false);
            STLog.e(TAG, "GetStateList True");
            if (!Utils.isEmptyString(action))
                if (action.equalsIgnoreCase("edit"))
                    setData();
        } else if (message.contains("GetStateList False")) {
            Utils.showMessage(activity, activity.getString(R.string.oops_something_went_wrong));
            STLog.e(TAG, "GetStateList False");
            Utils.dismissLoading();
        } else if (message.equalsIgnoreCase(token + " True")) {
            Utils.dismissLoading();
            statesArrayList = ModelManager.getInstance().getCommonDataManager().getStates(activity, false);
            STLog.e(TAG, token + " True");
            ((FragmentActivity) activity).getSupportFragmentManager()
                    .popBackStack();
        } else if (message.contains(token + " False")) {
            Utils.showMessage(activity, activity.getString(R.string.oops_something_went_wrong));
            STLog.e(TAG, token + " False");
            Utils.dismissLoading();
        }
    }
}
