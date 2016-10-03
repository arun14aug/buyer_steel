package com.tanzil.steelhub.view.activity;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.Window;
import android.widget.Toast;

import com.tanzil.steelhub.R;
import com.tanzil.steelhub.model.ModelManager;
import com.tanzil.steelhub.utility.Preferences;
import com.tanzil.steelhub.utility.STLog;
import com.tanzil.steelhub.utility.Utils;
import com.tanzil.steelhub.view.fragments.ChangePasswordFragment;
import com.tanzil.steelhub.view.fragments.ContactUsFragment;
import com.tanzil.steelhub.view.fragments.HistoryFragment;
import com.tanzil.steelhub.view.fragments.NewRequirementFragment;
import com.tanzil.steelhub.view.fragments.RequirementFragment;

import org.json.JSONException;
import org.json.JSONObject;

import de.greenrobot.event.EventBus;


public class MainActivity extends AppCompatActivity implements FragmentDrawer.FragmentDrawerListener {

    private String TAG = MainActivity.class.getSimpleName();

    private FragmentManager fragmentManager;
    private boolean backer = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        fragmentManager = getSupportFragmentManager();
        Toolbar mToolbar = (Toolbar) findViewById(R.id.toolbar);

        setSupportActionBar(mToolbar);
        getSupportActionBar().setDisplayShowHomeEnabled(true);

        FragmentDrawer drawerFragment = (FragmentDrawer)
                getSupportFragmentManager().findFragmentById(R.id.fragment_navigation_drawer);
        drawerFragment.setUp(R.id.fragment_navigation_drawer, (DrawerLayout) findViewById(R.id.drawer_layout), mToolbar);
        drawerFragment.setDrawerListener(this);

        // display the first navigation drawer view on app launch
        displayView(0);
    }


    @Override
    public void onDrawerItemSelected(View view, int position) {
        displayView(position);
    }

    private void displayView(int position) {
        Fragment fragment = null;
        String title = getString(R.string.app_name);
        switch (position) {
            case 0:
                fragment = new RequirementFragment();
                title = getString(R.string.requirements);
                break;
            case 1:
                fragment = new NewRequirementFragment();
                title = getString(R.string.title_new_requirement);
                break;
            case 2:
                fragment = new HistoryFragment();
                title = getString(R.string.title_history);
                break;
//            case 3:
//                fragment = new ProfileFragment();
//                title = getString(R.string.title_profile);
//                break;
            case 3:
                fragment = new ChangePasswordFragment();
                title = getString(R.string.title_change_pass);
                break;
            case 4:
                fragment = new ContactUsFragment();
                title = getString(R.string.title_contact_us);
                break;
//            case 5:
//                fragment = new SettingsFragment();
//                title = getString(R.string.title_settings);
//                break;
            case 5:
                showAlert(MainActivity.this, "Are you sure, you want to log out?");
                break;
            default:
                break;
        }

        if (fragment != null) {
            FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
            fragmentTransaction.replace(R.id.container_body, fragment, title);
            fragmentTransaction.addToBackStack(title);
            fragmentTransaction.commit();

            // set the toolbar title
            getSupportActionBar().setTitle(title);
        }
    }

    public void showAlert(Activity activity, String msg) {
        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(activity);
        alertDialogBuilder
                .setTitle("Logout!")
                .setMessage(msg)
                .setCancelable(false)
                .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        Utils.showLoading(MainActivity.this, getString(R.string.please_wait));
                        JSONObject jsonObject = new JSONObject();
                        try {
                            jsonObject.put("device_type", "android");
                            jsonObject.put("device_token", ModelManager.getInstance().getAuthManager().getDeviceToken());
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                        ModelManager.getInstance().getAuthManager().logout(MainActivity.this, jsonObject);
                        dialog.cancel();
                    }
                })
                .setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        dialog.cancel();
                    }
                });

        AlertDialog alertDialog = alertDialogBuilder.create();
        alertDialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        // show it
        alertDialog.show();
    }

    @Override
    public void onBackPressed() {
        Fragment f = fragmentManager.findFragmentById(R.id.container_body);
        try {
            if (f instanceof RequirementFragment) {
                if (backer)
                    finish();
                else {
                    backer = true;
                    Toast.makeText(MainActivity.this, "Press again to exit the app.", Toast.LENGTH_SHORT).show();
                }
            } else {
                super.onBackPressed();
                backer = false;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        setTitle();
    }

    private void setTitle() {
        String title = "";
        Fragment f = fragmentManager.findFragmentById(R.id.container_body);
        try {
            if (f instanceof RequirementFragment) {
                title = getString(R.string.requirements);
            } else if (f instanceof NewRequirementFragment) {
                title = getString(R.string.title_new_requirement);
            } else if (f instanceof HistoryFragment) {
                title = getString(R.string.title_history);
            } else if (f instanceof ChangePasswordFragment) {
                title = getString(R.string.title_change_pass);
            } else if (f instanceof ContactUsFragment) {
                title = getString(R.string.title_contact_us);
            }
        } catch (Exception e) {
            e.printStackTrace();
            title = getString(R.string.title_home);
        }
        // set the toolbar title
        getSupportActionBar().setTitle(title);
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
        if (message.equalsIgnoreCase("Logout True")) {
            Utils.dismissLoading();
            STLog.e(TAG, "Logout True");
            Preferences.clearAllPreference(MainActivity.this);
            startActivity(new Intent(MainActivity.this, LoginScreen.class));
            finish();
        } else if (message.contains("Logout False")) {
            // showMatchHistoryList();
            Utils.showMessage(MainActivity.this, "Please check your credentials!");
            STLog.e(TAG, "Logout False");
            Utils.dismissLoading();
        } else if (message.equalsIgnoreCase("Logout Network Error")) {
            Utils.showMessage(MainActivity.this, "Network Error! Please try again");
            STLog.e(TAG, "Logout Network Error");
            Utils.dismissLoading();
        }

    }


}