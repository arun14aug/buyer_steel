package com.buyer.steelhub.view.fragments;

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
import com.buyer.steelhub.model.ModelManager;
import com.buyer.steelhub.model.Requirements;
import com.buyer.steelhub.utility.STLog;
import com.buyer.steelhub.utility.Utils;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

import de.greenrobot.event.EventBus;

/**
 * Created by arun.sharma on 4/11/2017.
 */

public class RTGSFragment extends Fragment {

    private String TAG = RTGSFragment.class.getSimpleName();
    private Activity activity;
    private String requirement_id = "", seller_id = "", buyer_id = "";

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        this.activity = super.getActivity();
        Intent intent = new Intent("Header");
        intent.putExtra("message", activity.getString(R.string.rtgs));
        try {
            if (getArguments() != null) {
                Bundle bundle = getArguments();
                requirement_id = bundle.getString("requirement_id");
                seller_id = bundle.getString("seller_id");
                buyer_id = bundle.getString("buyer_id");
            }
        } catch (Exception ex) {
            STLog.e(TAG, ex.toString());
        }

        LocalBroadcastManager.getInstance(activity).sendBroadcast(intent);
        View rootView = inflater.inflate(R.layout.fragment_rtgs_screen, container, false);

        final MyEditText et_rtgs_number = (MyEditText) rootView.findViewById(R.id.et_rtgs_number);

        MyButton btn_submit = (MyButton) rootView.findViewById(R.id.btn_submit);
        btn_submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (et_rtgs_number.getText().toString().length() > 0) {
                    ArrayList<Requirements> requirementsArrayList = ModelManager.getInstance().getRequirementManager().getRequirements(activity, false);
                    if (requirementsArrayList != null)
                        for (int i = 0; i < requirementsArrayList.size(); i++)
                            if (requirement_id.equalsIgnoreCase(requirementsArrayList.get(i).getRequirement_id())) {
                                JSONObject jsonObject = new JSONObject();
                                try {
                                    jsonObject.put("requirement_id", requirement_id);
                                    jsonObject.put("seller_id", seller_id);
                                    jsonObject.put("buyer_id", buyer_id);
                                    jsonObject.put("RTGS", et_rtgs_number.getText().toString().trim());
                                } catch (JSONException e) {
                                    e.printStackTrace();
                                }
                                Utils.showLoading(activity, activity.getString(R.string.please_wait));
                                requirementsArrayList.get(i).saveRTGS(activity, jsonObject);
                                break;
                            }
                } else {
                    et_rtgs_number.requestFocus();
                    Utils.showMessage(activity, "Please enter valid RTGS number.");
                }
            }
        });
        return rootView;
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
        if (message.equalsIgnoreCase("SaveRTGS True")) {
            Utils.dismissLoading();

            STLog.e(TAG, "SaveRTGS True");
        } else if (message.contains("SaveRTGS False")) {
            // showMatchHistoryList();
            String[] m = message.split("@#@");
            if (m.length > 1)
                Utils.showMessage(activity, m[1]);
            else
                Utils.showMessage(activity, activity.getString(R.string.oops_something_went_wrong));
            STLog.e(TAG, "SaveRTGS False");
            Utils.dismissLoading();
        } else if (message.contains("SaveRTGS Network Error")) {
            // showMatchHistoryList();
            Utils.showMessage(activity, activity.getString(R.string.oops_something_went_wrong));
            STLog.e(TAG, "SaveRTGS Network Error");
            Utils.dismissLoading();
        }

    }
}
