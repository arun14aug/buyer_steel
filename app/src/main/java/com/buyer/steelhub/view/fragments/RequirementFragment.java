package com.buyer.steelhub.view.fragments;

/**
 * Created by arun.sharma on 29/07/15.
 */

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.content.LocalBroadcastManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.buyer.steelhub.R;
import com.buyer.steelhub.customUi.MyButton;
import com.buyer.steelhub.model.ModelManager;
import com.buyer.steelhub.model.Requirements;
import com.buyer.steelhub.model.States;
import com.buyer.steelhub.utility.Preferences;
import com.buyer.steelhub.utility.STLog;
import com.buyer.steelhub.utility.Utils;
import com.buyer.steelhub.view.adapter.RequirementAdapter;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

import de.greenrobot.event.EventBus;


public class RequirementFragment extends Fragment {

    private String TAG = RequirementFragment.class.getSimpleName();
    private Activity activity;
    private ArrayList<Requirements> requirementsArrayList;
    private ListView listView;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        this.activity = super.getActivity();
        Intent intent = new Intent("Header");
        intent.putExtra("message", activity.getString(R.string.requirements));

        LocalBroadcastManager.getInstance(activity).sendBroadcast(intent);
        View rootView = inflater.inflate(R.layout.requirements_listview, container, false);

        listView = rootView.findViewById(R.id.list_requirements);

        MyButton btn_new_requirement = rootView.findViewById(R.id.btn_new_requirement);
        btn_new_requirement.setTransformationMethod(null);
        btn_new_requirement.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Fragment fragment = new NewRequirementFragment();
                Bundle bundle = new Bundle();
                bundle.putString("type", "add");
                FragmentManager fragmentManager = ((FragmentActivity) activity).getSupportFragmentManager();
                FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                fragmentTransaction.replace(R.id.container_body, fragment, "NewRequirementFragment");
                fragmentTransaction.addToBackStack("NewRequirementFragment");
                fragmentTransaction.commit();
            }
        });

        ArrayList<States> statesArrayList = ModelManager.getInstance().getCommonDataManager().getStates(activity, false);
        if (statesArrayList == null)
            ModelManager.getInstance().getCommonDataManager().getStates(activity, true);
        else {
            getData();
        }
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Fragment fragment = new RequirementDetailFragment();
                Bundle bundle = new Bundle();
                bundle.putString("id", requirementsArrayList.get(i).getRequirement_id());
                fragment.setArguments(bundle);
                FragmentManager fragmentManager = ((FragmentActivity) activity).getSupportFragmentManager();
                FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                fragmentTransaction.replace(R.id.container_body, fragment, "RequirementDetailFragment");
                fragmentTransaction.addToBackStack("RequirementDetailFragment");
                fragmentTransaction.commit();
            }
        });

        listView.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> adapterView, View view, int i, long l) {
                showDialog(requirementsArrayList.get(i).getRequirement_id(), requirementsArrayList.get(i).getUser_id());
                return true;
            }
        });
        STLog.e("User Token :", Preferences.readString(activity, Preferences.USER_TOKEN, ""));
        return rootView;
    }

    private void getData() {
        requirementsArrayList = ModelManager.getInstance().getRequirementManager().getRequirements(activity, false);
        if (requirementsArrayList == null) {
            Utils.showLoading(activity, activity.getString(R.string.please_wait));
            ModelManager.getInstance().getRequirementManager().getRequirements(activity, true);
        } else {
            setData();
            ModelManager.getInstance().getRequirementManager().getRequirements(activity, true);
        }
    }

    private void showDialog(final String id, final String buyer_id) {
        AlertDialog.Builder builderSingle = new AlertDialog.Builder(activity);
//        builderSingle.setIcon(R.drawable.ic_launcher);
        builderSingle.setTitle("Choose Action");

        final ArrayAdapter<String> arrayAdapter = new ArrayAdapter<>(
                activity,
                android.R.layout.simple_list_item_1);
        arrayAdapter.add("Delete");

        builderSingle.setAdapter(
                arrayAdapter,
                new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        JSONObject jsonObject = new JSONObject();
                        try {

                            jsonObject.put("requirement_id", id);
                            jsonObject.put("seller_id", "");
                            jsonObject.put("buyer_id", Preferences.readString(activity, Preferences.USER_ID, ""));
                            jsonObject.put("Is_seller_deleted", "0");
                            jsonObject.put("Is_buyer_deleted", "1");
                            jsonObject.put("type", "buyer");
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                        STLog.e("JSON : ", jsonObject.toString());
                        Utils.showLoading(activity, activity.getString(R.string.please_wait));
                        ModelManager.getInstance().getRequirementManager().deletePost(activity, jsonObject);
                    }
                });
        builderSingle.show();
    }

    private void setData() {
        if (requirementsArrayList.size() > 0) {
            RequirementAdapter requirementAdapter = new RequirementAdapter(activity, requirementsArrayList);
            listView.setAdapter(requirementAdapter);
            requirementAdapter.notifyDataSetChanged();
        } else {
            listView.setAdapter(null);
            Utils.showMessage(activity, activity.getString(R.string.no_record_found));
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
        if (message.equalsIgnoreCase("GetRequirements True")) {
            Utils.dismissLoading();
            requirementsArrayList = ModelManager.getInstance().getRequirementManager().getRequirements(activity, false);
            if (requirementsArrayList != null)
                setData();
            else
                Utils.showMessage(activity, activity.getString(R.string.no_record_found));
            STLog.e(TAG, "GetRequirements True");
        } else if (message.contains("GetRequirements False")) {
            // showMatchHistoryList();
            Utils.showMessage(activity, activity.getString(R.string.oops_something_went_wrong));
            STLog.e(TAG, "GetRequirements False");
            Utils.dismissLoading();
        } else if (message.equalsIgnoreCase("DeletePost True")) {
            Utils.dismissLoading();
            Utils.showLoading(activity, activity.getString(R.string.please_wait));
            ModelManager.getInstance().getRequirementManager().getRequirements(activity, true);
            STLog.e(TAG, "DeletePost True");
        } else if (message.contains("DeletePost False")) {
            // showMatchHistoryList();
            Utils.showMessage(activity, activity.getString(R.string.oops_something_went_wrong));
            STLog.e(TAG, "DeletePost False");
            Utils.dismissLoading();
        } else if (message.contains("GetStateList")) {
            Utils.dismissLoading();
            getData();
            STLog.e(TAG, "GetStateList True");
        }

    }
}
