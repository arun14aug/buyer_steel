package com.buyer.steelhub.view.fragments;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.content.LocalBroadcastManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;

import com.buyer.steelhub.R;
import com.buyer.steelhub.customUi.MyButton;
import com.buyer.steelhub.model.ModelManager;
import com.buyer.steelhub.utility.Preferences;
import com.buyer.steelhub.utility.STLog;
import com.buyer.steelhub.utility.Utils;

import org.json.JSONObject;

import de.greenrobot.event.EventBus;

/**
 * Created by arun.sharma on 12/1/16.
 */
public class ChangePasswordFragment extends Fragment implements View.OnClickListener {

    private Activity activity;
    private EditText newPassword, confirmPassword, oldPassword;
    private String TAG = ChangePasswordFragment.class.getSimpleName();

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        this.activity = super.getActivity();
        Intent intent = new Intent("Header");
        intent.putExtra("message", activity.getString(R.string.title_change_pass));

        LocalBroadcastManager.getInstance(activity).sendBroadcast(intent);

        View rootView = inflater.inflate(R.layout.change_password_screen, container, false);

        oldPassword = (EditText) rootView.findViewById(R.id.et_old_password);
        newPassword = (EditText) rootView.findViewById(R.id.et_new_password);
        confirmPassword = (EditText) rootView.findViewById(R.id.et_confirm_password);

        MyButton submit = (MyButton) rootView.findViewById(R.id.btn_submit);

        submit.setOnClickListener(this);
        return rootView;
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_submit:
                if (oldPassword.getText().toString().trim().length() == 0) {
                    oldPassword.requestFocus();
                    Utils.showMessage(activity, "Please enter old password.");
                } else if (newPassword.getText().toString().trim().length() == 0) {
                    newPassword.requestFocus();
                    Utils.showMessage(activity, "Please enter new password.");
                } else if (confirmPassword.getText().toString().trim().length() == 0) {
                    confirmPassword.requestFocus();
                    Utils.showMessage(activity, "Please enter confirm password.");
                } else if (!newPassword.getText().toString().trim().equalsIgnoreCase(confirmPassword.getText().toString().trim())) {
                    confirmPassword.requestFocus();
                    Utils.showMessage(activity, "Password does not match.");
                } else {
                    if (Utils.isConnectingToInternet(activity)) {
                        JSONObject jsonObject = new JSONObject();
                        try {
                            jsonObject.put("old_password", oldPassword.getText().toString().trim());
                            jsonObject.put("user_id", Preferences.readString(activity, Preferences.USER_ID, ""));
                            jsonObject.put("new_password", newPassword.getText().toString().trim());
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                        Utils.showLoading(activity, getString(R.string.please_wait));
                        ModelManager.getInstance().getAuthManager().changePassword(activity, jsonObject);
                    } else {
                        Utils.showMessage(activity, "Your Internet connection is unavailable");
                    }
                }
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
        if (message.equalsIgnoreCase("ChangePassword True")) {
            Utils.dismissLoading();
            STLog.e(TAG, "ChangePassword True");
        } else if (message.equalsIgnoreCase("ChangePassword False")) {
            // showMatchHistoryList();
            Utils.showMessage(activity, "Please check your credentials!");
            STLog.e(TAG, "ChangePassword False");
            Utils.dismissLoading();
        } else if (message.equalsIgnoreCase("ChangePassword Network Error")) {
            Utils.showMessage(activity, "Network Error! Please try again");
            STLog.e(TAG, "ChangePassword Network Error");
            Utils.dismissLoading();
        }

    }

}
