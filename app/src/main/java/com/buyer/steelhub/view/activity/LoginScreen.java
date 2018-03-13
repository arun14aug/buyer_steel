package com.buyer.steelhub.view.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import com.buyer.steelhub.R;
import com.buyer.steelhub.customUi.MyButton;
import com.buyer.steelhub.customUi.MyEditText;
import com.buyer.steelhub.customUi.MyTextView;
import com.buyer.steelhub.model.AuthManager;
import com.buyer.steelhub.model.ModelManager;
import com.buyer.steelhub.utility.Preferences;
import com.buyer.steelhub.utility.STLog;
import com.buyer.steelhub.utility.Utils;

import org.json.JSONException;
import org.json.JSONObject;

import de.greenrobot.event.EventBus;


/**
 * Created by arun.sharma on 4/19/2016.
 */
public class LoginScreen extends Activity implements View.OnClickListener {

    private String TAG = LoginScreen.class.getSimpleName();
    private Activity activity = LoginScreen.this;
    private MyEditText et_Email, et_Password;
    private AuthManager authManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        // TODO Auto-generated method stub
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login_screen);

        authManager = ModelManager.getInstance().getAuthManager();
        String deviceId = Preferences.readString(getApplicationContext(), Preferences.DEVICE_ID, "");
        authManager.setDeviceToken(deviceId);

        if (!Utils.isEmptyString(Preferences.readString(getApplicationContext(), Preferences.USER_TOKEN, "")))
            ModelManager.getInstance().getAuthManager().setUserToken(Preferences.readString(getApplicationContext(), Preferences.USER_TOKEN, ""));

        if (Utils.isConnectingToInternet(LoginScreen.this)) {
            /* Starts new activity */
            if (Preferences.readBoolean(LoginScreen.this, Preferences.LOGIN, false)) {
                startActivity(new Intent(LoginScreen.this, MainActivity.class));
                finish();
            }
        } else {
            Utils.showMessage(LoginScreen.this, "Your Internet connection is unavailable");
        }
        MyButton loginBtn = findViewById(R.id.btn_login);
        MyButton signUpBtn = findViewById(R.id.btn_sign_up);
        MyTextView txt_forget_password = findViewById(R.id.forget_password);

        loginBtn.setTransformationMethod(null);
        signUpBtn.setTransformationMethod(null);

        et_Email = findViewById(R.id.et_username);
        et_Password = findViewById(R.id.et_password);


        loginBtn.setOnClickListener(this);
        txt_forget_password.setOnClickListener(this);
        signUpBtn.setOnClickListener(this);
    }


    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_login:
                if (et_Email.getText().toString().trim().length() == 0) {
                    et_Email.requestFocus();
                    Toast.makeText(getBaseContext(), getString(R.string.please_enter_username), Toast.LENGTH_SHORT).show();
                } else if (et_Password.getText().toString().trim().length() == 0) {
                    et_Password.requestFocus();
                    Toast.makeText(getBaseContext(), getString(R.string.please_enter_password), Toast.LENGTH_SHORT).show();
                } else {
                    Utils.showLoading(activity, getString(R.string.please_wait));
                    JSONObject jsonObject = new JSONObject();
                    try {
                        jsonObject.put("email", et_Email.getText().toString().trim());
                        jsonObject.put("password", et_Password.getText().toString().trim());
                        jsonObject.put("device_type", "android");
                        jsonObject.put("role", "buyer");
                        jsonObject.put("device_token", Preferences.readString(activity, Preferences.DEVICE_ID, ""));
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                    authManager.logIn(LoginScreen.this, jsonObject);
                }
                break;

            case R.id.btn_sign_up:
                startActivity(new Intent(activity, SignUpScreen.class));
                break;
            case R.id.forget_password:
                startActivity(new Intent(activity, ForgetPasswordScreen.class));
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
        if (message.equalsIgnoreCase("Login True")) {
            Utils.dismissLoading();
            Preferences.writeString(activity, Preferences.EMAIL, et_Email.getText().toString());
            Preferences.writeString(activity, Preferences.PASSWORD, et_Password.getText().toString());
            STLog.e(TAG, "Login True");
            startActivity(new Intent(activity, MainActivity.class));
            finish();
        } else if (message.contains("Login False")) {
            // showMatchHistoryList();
            String[] msg = message.split("@#@");
            if (msg.length > 1)
                Utils.showMessage(activity, msg[1]);
            else
                Utils.showMessage(activity, "Please check your credentials!");
            STLog.e(TAG, "Login False");
            Utils.dismissLoading();
        } else if (message.equalsIgnoreCase("Login Network Error")) {
            Utils.showMessage(activity, "Network Error! Please try again");
            STLog.e(TAG, "Login Network Error");
            Utils.dismissLoading();
        }

    }

}
