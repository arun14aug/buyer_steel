package com.tanzil.steelhub.view.fragments;

/**
 * Created by arun.sharma on 29/07/15.
 */

import android.app.Activity;
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
import android.widget.ListView;

import com.tanzil.steelhub.R;
import com.tanzil.steelhub.customUi.MyButton;
import com.tanzil.steelhub.model.ModelManager;
import com.tanzil.steelhub.model.Requirements;
import com.tanzil.steelhub.utility.STLog;
import com.tanzil.steelhub.utility.Utils;
import com.tanzil.steelhub.view.adapter.RequirementAdapter;

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

        listView = (ListView) rootView.findViewById(R.id.list_requirements);

        MyButton btn_new_requirement = (MyButton) rootView.findViewById(R.id.btn_new_requirement);
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


        requirementsArrayList = ModelManager.getInstance().getRequirementManager().getRequirements(activity, false);
        if (requirementsArrayList == null) {
            Utils.showLoading(activity, activity.getString(R.string.please_wait));
            ModelManager.getInstance().getRequirementManager().getRequirements(activity, true);
        } else {
            setData();
            ModelManager.getInstance().getRequirementManager().getRequirements(activity, true);
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
        return rootView;
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
        } else if (message.contains("GetRequirements Network Error")) {
            // showMatchHistoryList();
            Utils.showMessage(activity, activity.getString(R.string.oops_something_went_wrong));
            STLog.e(TAG, "GetRequirements Network Error");
            Utils.dismissLoading();
        }

    }
}
