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
import com.buyer.steelhub.utility.STLog;
import com.buyer.steelhub.utility.Utils;

import de.greenrobot.event.EventBus;

/**
 * Created by arun.sharma on 4/11/2017.
 */

public class RTGSFragment extends Fragment {

    private String TAG = RTGSFragment.class.getSimpleName();
    private Activity activity;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        this.activity = super.getActivity();
        Intent intent = new Intent("Header");
        intent.putExtra("message", activity.getString(R.string.rtgs));

        LocalBroadcastManager.getInstance(activity).sendBroadcast(intent);
        View rootView = inflater.inflate(R.layout.fragment_rtgs_screen, container, false);

        final MyEditText et_rtgs_number = (MyEditText) rootView.findViewById(R.id.et_rtgs_number);

        MyButton btn_submit = (MyButton) rootView.findViewById(R.id.btn_submit);
        btn_submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (et_rtgs_number.getText().toString().length() > 0) {

                } else {
                    et_rtgs_number.requestFocus();
                    Utils.showMessage(activity, "Please enter firm name.");
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
        if (message.equalsIgnoreCase("AddressList True")) {
            Utils.dismissLoading();

            STLog.e(TAG, "AddressList True");
        } else if (message.contains("AddressList False")) {
            // showMatchHistoryList();
            Utils.showMessage(activity, activity.getString(R.string.oops_something_went_wrong));
            STLog.e(TAG, "AddressList False");
            Utils.dismissLoading();
        } else if (message.contains("AddressList Network Error")) {
            // showMatchHistoryList();
            Utils.showMessage(activity, activity.getString(R.string.oops_something_went_wrong));
            STLog.e(TAG, "AddressList Network Error");
            Utils.dismissLoading();
        }

    }
}
