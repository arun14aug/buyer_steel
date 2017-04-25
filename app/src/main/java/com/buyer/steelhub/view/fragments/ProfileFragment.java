package com.buyer.steelhub.view.fragments;

/*
 * Created by arun.sharma on 29/07/15.
 */

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.content.LocalBroadcastManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.buyer.steelhub.R;
import com.buyer.steelhub.customUi.MyButton;
import com.buyer.steelhub.customUi.MyEditText;
import com.buyer.steelhub.model.AuthManager;
import com.buyer.steelhub.model.ModelManager;
import com.buyer.steelhub.model.States;
import com.buyer.steelhub.model.User;
import com.buyer.steelhub.utility.GPSTracker;
import com.buyer.steelhub.utility.STLog;
import com.buyer.steelhub.utility.Utils;

import java.util.ArrayList;

import de.greenrobot.event.EventBus;


public class ProfileFragment extends Fragment {

    private String TAG = ProfileFragment.class.getSimpleName();
    private Activity activity;
    private MyEditText et_Email, et_Name, et_Company, et_Contact, et_Address, et_State, et_City,
            et_Zip, et_Tin, et_Pan;
    private AuthManager authManager;
    private double lat = 0.000, lng = 0.000;
    private GPSTracker gps;
    private ArrayList<States> statesArrayList;
    private String stateId = "";
    private ArrayList<User> userArrayList;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        this.activity = super.getActivity();
        Intent intent = new Intent("Header");
        intent.putExtra("message", activity.getString(R.string.title_profile));

        LocalBroadcastManager.getInstance(activity).sendBroadcast(intent);

        View rootView = inflater.inflate(R.layout.fragment_profile, container, false);

        et_Email = (MyEditText) rootView.findViewById(R.id.et_email);
        et_Name = (MyEditText) rootView.findViewById(R.id.et_username);
        et_Company = (MyEditText) rootView.findViewById(R.id.et_company_name);
        et_Contact = (MyEditText) rootView.findViewById(R.id.et_contact);
        et_Address = (MyEditText) rootView.findViewById(R.id.et_address);

        et_State = (MyEditText) rootView.findViewById(R.id.et_state);
        et_City = (MyEditText) rootView.findViewById(R.id.et_city);
        et_Zip = (MyEditText) rootView.findViewById(R.id.et_zip);
        et_Tin = (MyEditText) rootView.findViewById(R.id.et_tin);
        et_Pan = (MyEditText) rootView.findViewById(R.id.et_pan);

        MyButton submit = (MyButton) rootView.findViewById(R.id.btn_submit);
        submit.setVisibility(View.GONE);

        et_Email.setFocusable(false);
        et_Name.setFocusable(false);
        et_Company.setFocusable(false);
        et_Contact.setFocusable(false);
        et_Address.setFocusable(false);
        et_State.setFocusable(false);
        et_City.setFocusable(false);
        et_Zip.setFocusable(false);
        et_Tin.setFocusable(false);
        et_Pan.setFocusable(false);

        Utils.showLoading(activity, activity.getString(R.string.please_wait));
        ModelManager.getInstance().getAuthManager().getProfile(activity, true);
        // Inflate the layout for this fragment
        return rootView;
    }

    private void setData() {
        if (userArrayList.size() > 0) {
            et_Email.setText(userArrayList.get(0).getEmail());
            et_Name.setText(userArrayList.get(0).getName());
            et_Pan.setText(userArrayList.get(0).getPan());
            et_Tin.setText(userArrayList.get(0).getTin());
            et_Zip.setText(userArrayList.get(0).getZip());
            et_Address.setText(userArrayList.get(0).getAddress());
            et_City.setText(userArrayList.get(0).getCity());
            et_Company.setText(userArrayList.get(0).getCompany_name());
            et_Contact.setText(userArrayList.get(0).getContact());
            et_State.setText(userArrayList.get(0).getState());
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
        if (message.equalsIgnoreCase("Profile True")) {
            Utils.dismissLoading();
            userArrayList = ModelManager.getInstance().getAuthManager().getProfile(activity, false);
            if (userArrayList != null)
                setData();
            else {
                Utils.showMessage(activity, activity.getString(R.string.no_record_found));
            }
            STLog.e(TAG, "Profile True");
        } else if (message.contains("Profile False")) {
            // showMatchHistoryList();
            String[] m = message.split("@#@");
            if (m.length > 1)
                Utils.showMessage(activity, m[1]);
            else
                Utils.showMessage(activity, activity.getString(R.string.oops_something_went_wrong));
            STLog.e(TAG, "Profile False");
            Utils.dismissLoading();
        } else if (message.contains("Profile Network Error")) {
            // showMatchHistoryList();
            Utils.showMessage(activity, activity.getString(R.string.oops_something_went_wrong));
            STLog.e(TAG, "Profile Network Error");
            Utils.dismissLoading();
        }

    }
}
