package com.tanzil.steelhub.view.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;

import com.tanzil.steelhub.R;
import com.tanzil.steelhub.utility.STLog;
import com.tanzil.steelhub.utility.Utils;
import com.tanzil.steelhub.customUi.MyButton;
import com.tanzil.steelhub.customUi.MyEditText;
import com.tanzil.steelhub.model.ModelManager;

import org.json.JSONObject;

import de.greenrobot.event.EventBus;

/**
 * Created by arun.sharma on 4/19/2016.
 */
public class ForgetPasswordScreen extends Activity implements View.OnClickListener {

    private String TAG = ForgetPasswordScreen.class.getSimpleName();
    private Activity activity = ForgetPasswordScreen.this;
    private MyEditText et_Email;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        // TODO Auto-generated method stub
        super.onCreate(savedInstanceState);
        setContentView(R.layout.forget_password_screen);

        MyButton sendBtn = (MyButton) findViewById(R.id.send_btn);
        ImageView img_back = (ImageView) findViewById(R.id.img_back);

        et_Email = (MyEditText) findViewById(R.id.et_email);

        sendBtn.setTransformationMethod(null);

        sendBtn.setOnClickListener(this);
        img_back.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.send_btn:
                if (et_Email.getText().toString().trim().length() == 0) {
                    et_Email.requestFocus();
                    Toast.makeText(getBaseContext(), getString(R.string.please_enter_email), Toast.LENGTH_SHORT).show();
                } else {
                    Utils.showLoading(activity, getString(R.string.please_wait));
                    JSONObject post_data = new JSONObject();
                    try {
                        post_data.put("email", et_Email.getText().toString().trim());
                        STLog.e(TAG, "LoginData" + post_data.toString());

                    } catch (Exception e1) {
                        e1.printStackTrace();
                    }
                    ModelManager.getInstance().getAuthManager().forgetPassword(ForgetPasswordScreen.this, post_data);
                }
                break;
            case R.id.img_back:
                finish();
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
        if (message.equalsIgnoreCase("ForgetPassword True")) {
            Utils.dismissLoading();
            STLog.e(TAG, "ForgetPassword True");
            startActivity(new Intent(activity, MainActivity.class));
            finish();
        } else if (message.contains("ForgetPassword False")) {
            // showMatchHistoryList();
            Utils.showMessage(activity, "Please check your credentials!");
            STLog.e(TAG, "ForgetPassword False");
            Utils.dismissLoading();
        } else if (message.equalsIgnoreCase("ForgetPassword Network Error")) {
            Utils.showMessage(activity, "Network Error! Please try again");
            STLog.e(TAG, "ForgetPassword Network Error");
            Utils.dismissLoading();
        }

    }

}
