package com.buyer.steelhub.view.activity;

import android.Manifest;
import android.annotation.TargetApi;
import android.app.Activity;
import android.app.Dialog;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.drawable.ColorDrawable;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.Toast;

import com.buyer.steelhub.R;
import com.buyer.steelhub.customUi.MyButton;
import com.buyer.steelhub.customUi.MyEditText;
import com.buyer.steelhub.customUi.MyTextView;
import com.buyer.steelhub.model.AuthManager;
import com.buyer.steelhub.model.ModelManager;
import com.buyer.steelhub.model.States;
import com.buyer.steelhub.utility.GPSTracker;
import com.buyer.steelhub.utility.Preferences;
import com.buyer.steelhub.utility.STLog;
import com.buyer.steelhub.utility.Utils;
import com.buyer.steelhub.view.adapter.CommonDialogAdapter;
import com.google.android.gms.common.ConnectionResult;

import org.json.JSONObject;

import java.util.ArrayList;

import de.greenrobot.event.EventBus;

/**
 * Created by arun.sharma on 4/19/2016.
 */
public class SignUpScreen extends Activity implements View.OnClickListener {

    private String TAG = SignUpScreen.class.getSimpleName();
    private Activity activity = SignUpScreen.this;
    private MyEditText et_Email, et_Password, et_ConfirmPassword, et_Name,
            et_Company, et_Contact, et_Address, et_State, et_City, et_Zip, et_Tin, et_Pan, et_Required;
    private AuthManager authManager;
    private double lat = 0.000, lng = 0.000;
    private GPSTracker gps;
    private ArrayList<States> statesArrayList;
    private String stateId = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        // TODO Auto-generated method stub
        super.onCreate(savedInstanceState);
        setContentView(R.layout.registration_screen);

        gps = new GPSTracker(activity);
        if (!canAccessLocation()) {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                requestPermissions(Utils.INITIAL_PERMS, Utils.INITIAL_REQUEST);
            }
        }

        authManager = ModelManager.getInstance().getAuthManager();
        String deviceId = Preferences.readString(getApplicationContext(), Preferences.DEVICE_ID, "");
        authManager.setDeviceToken(deviceId);

        MyButton submitBtn = findViewById(R.id.btn_submit);
        ImageView img_back = findViewById(R.id.back);


        if (lat == 0.000 || lng == 0.000)
            getLatLong();

        if (statesArrayList == null) {
//            Utils.showLoading(activity, activity.getString(R.string.please_wait));
            ModelManager.getInstance().getCommonDataManager().getStates(activity, true);
        }

        et_Email = findViewById(R.id.et_email);
        et_Password = findViewById(R.id.et_password);
        et_Name = findViewById(R.id.et_username);
        et_ConfirmPassword = findViewById(R.id.et_confirm_password);
        et_Company = findViewById(R.id.et_company_name);
        et_Contact = findViewById(R.id.et_contact);
        et_Address = findViewById(R.id.et_address);

        et_State = findViewById(R.id.et_state);
        et_City = findViewById(R.id.et_city);
        et_Zip = findViewById(R.id.et_zip);
        et_Tin = findViewById(R.id.et_tin);
        et_Pan = findViewById(R.id.et_pan);
        et_Required = findViewById(R.id.et_requirement_dropdown);

        submitBtn.setTransformationMethod(null);

        submitBtn.setOnClickListener(this);
        img_back.setOnClickListener(this);
        et_State.setOnClickListener(this);
    }

    private void getLatLong() {
        if (gps.canGetLocation()) {
            lat = gps.getLatitude();
            lng = gps.getLongitude();
        } else {
            gps.showSettingsAlert();
        }

    }

    public boolean canAccessLocation() {
        return (hasPermission(Manifest.permission.ACCESS_FINE_LOCATION));
    }

    @TargetApi(Build.VERSION_CODES.M)
    public boolean hasPermission(String perm) {
        return Build.VERSION.SDK_INT < Build.VERSION_CODES.M || (PackageManager.PERMISSION_GRANTED == activity.checkSelfPermission(perm));
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        switch (requestCode) {

            case Utils.LOCATION_REQUEST:
                if (canAccessLocation()) {
                    getLatLong();
                } else {
                    gps.showSettingsAlert();
                }
                break;
        }
    }

    private void showDropDownDialog() {
        final Dialog dropDownDialog = new Dialog(activity);
        dropDownDialog.requestWindowFeature(Window.FEATURE_NO_TITLE); // before
        dropDownDialog.setContentView(R.layout.dialog_dropdown_list);
        dropDownDialog.getWindow().setBackgroundDrawable(
                new ColorDrawable(android.graphics.Color.TRANSPARENT));
        MyTextView titleView = dropDownDialog
                .findViewById(R.id.title_name);
        titleView.setText(activity.getString(R.string.please_select_an_option));
        final ListView listView = dropDownDialog
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
                et_State.setText(statesArrayList.get(position).getName());
                stateId = statesArrayList.get(position).getCode();
                dropDownDialog.dismiss();
            }
        });

        dropDownDialog.show();
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_submit:
                if (lat == 0.000 || lng == 0.000) {
                    getLatLong();
                }
                if (et_Name.getText().toString().trim().length() == 0) {
                    et_Name.requestFocus();
                    Toast.makeText(getBaseContext(), getString(R.string.please_enter_username), Toast.LENGTH_SHORT).show();
                } else if (et_Password.getText().toString().trim().length() == 0) {
                    et_Password.requestFocus();
                    Toast.makeText(getBaseContext(), getString(R.string.please_enter_password), Toast.LENGTH_SHORT).show();
                } else if (et_ConfirmPassword.getText().toString().trim().length() == 0) {
                    et_ConfirmPassword.requestFocus();
                    Toast.makeText(getBaseContext(), getString(R.string.please_enter_confirm_password), Toast.LENGTH_SHORT).show();
                } else if (!et_Password.getText().toString().equalsIgnoreCase(et_ConfirmPassword.getText().toString())) {
                    et_ConfirmPassword.requestFocus();
                    Toast.makeText(getBaseContext(), getString(R.string.please_enter_correct_password), Toast.LENGTH_SHORT).show();
                } else if (et_Company.getText().toString().trim().length() == 0) {
                    et_Company.requestFocus();
                    Toast.makeText(getBaseContext(), getString(R.string.please_enter_company), Toast.LENGTH_SHORT).show();
                } else if (et_Email.getText().toString().trim().length() == 0) {
                    et_Email.requestFocus();
                    Toast.makeText(getBaseContext(), getString(R.string.please_enter_email), Toast.LENGTH_SHORT).show();
                } else if (et_Contact.getText().toString().trim().length() == 0) {
                    et_Contact.requestFocus();
                    Toast.makeText(getBaseContext(), getString(R.string.please_enter_contact), Toast.LENGTH_SHORT).show();
                } else if (et_Address.getText().toString().trim().length() == 0) {
                    et_Address.requestFocus();
                    Toast.makeText(getBaseContext(), getString(R.string.please_enter_address), Toast.LENGTH_SHORT).show();
                } else if (et_State.getText().toString().trim().length() == 0) {
                    et_State.requestFocus();
                    Toast.makeText(getBaseContext(), getString(R.string.please_enter_state), Toast.LENGTH_SHORT).show();
                } else if (et_Zip.getText().toString().trim().length() == 0) {
                    et_Zip.requestFocus();
                    Toast.makeText(getBaseContext(), getString(R.string.please_enter_zip), Toast.LENGTH_SHORT).show();
                } else if (et_Tin.getText().toString().trim().length() == 0) {
                    et_Tin.requestFocus();
                    Toast.makeText(getBaseContext(), getString(R.string.please_enter_tin), Toast.LENGTH_SHORT).show();
                } else if (et_Pan.getText().toString().trim().length() == 0) {
                    et_Pan.requestFocus();
                    Toast.makeText(getBaseContext(), getString(R.string.please_enter_pan), Toast.LENGTH_SHORT).show();
                } else if (et_Required.getText().toString().trim().length() == 0) {
                    et_Required.requestFocus();
                    Toast.makeText(getBaseContext(), getString(R.string.please_enter_expected_req), Toast.LENGTH_SHORT).show();
                } else {

                    Utils.showLoading(activity, getString(R.string.please_wait));
                    JSONObject post_data = new JSONObject();
                    try {
                        post_data.put("user_id", Preferences.readString(activity, Preferences.USER_ID, ""));
                        post_data.put("email", et_Email.getText().toString().trim());
                        post_data.put("password", et_Password.getText().toString().trim());
                        post_data.put("name", et_Name.getText().toString().trim());
                        post_data.put("contact", et_Contact.getText().toString().trim());
                        post_data.put("address", et_Address.getText().toString().trim());
                        post_data.put("state", /*et_State.getText().toString().trim()*/ stateId);
                        post_data.put("city", et_City.getText().toString().trim());
                        post_data.put("zip", et_Zip.getText().toString().trim());
                        post_data.put("tin", et_Tin.getText().toString().trim());
//                        post_data.put("brand", bra);
                        post_data.put("company_name", et_Company.getText().toString().trim());
                        post_data.put("role", "buyer");
                        post_data.put("pan", et_Pan.getText().toString().trim());
                        post_data.put("latitude", lat);
                        post_data.put("longitude", lng);
                        post_data.put("exp_quantity", et_Required.getText().toString().trim());

                        STLog.e(TAG, "Data" + post_data.toString());
                    } catch (Exception e1) {
                        e1.printStackTrace();
                    }
                    authManager.registerUser(SignUpScreen.this, post_data);
                }
                break;
            case R.id.back:
                finish();
                break;
            case R.id.et_state:
                showDropDownDialog();
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
        if (message.equalsIgnoreCase("GetStateList True")) {
            Utils.dismissLoading();
            statesArrayList = ModelManager.getInstance().getCommonDataManager().getStates(activity, false);
            STLog.e(TAG, "GetStateList True");
        } else if (message.contains("GetStateList False")) {
            // showMatchHistoryList();
            Utils.showMessage(activity, activity.getString(R.string.oops_something_went_wrong));
            STLog.e(TAG, "GetStateList False");
            Utils.dismissLoading();
        } else if (message.equalsIgnoreCase("Register True")) {
            Utils.dismissLoading();
            Preferences.writeString(activity, Preferences.EMAIL, et_Email.getText().toString());
            Preferences.writeString(activity, Preferences.PASSWORD, et_Password.getText().toString());
            Preferences.writeString(activity, Preferences.USER_NAME, et_Name.getText().toString());
            STLog.e(TAG, "Register True");
            startActivity(new Intent(activity, MainActivity.class));
            finish();
        } else if (message.contains("Register False")) {
            // showMatchHistoryList();
            Utils.showMessage(activity, "Please check your credentials!");
            STLog.e(TAG, "Register False");
            Utils.dismissLoading();
        } else if (message.equalsIgnoreCase("Register Network Error")) {
            Utils.showMessage(activity, "Network Error! Please try again");
            STLog.e(TAG, "Register Network Error");
            Utils.dismissLoading();
        }

    }

}
