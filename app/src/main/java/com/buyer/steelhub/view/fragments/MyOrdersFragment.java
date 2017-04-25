package com.buyer.steelhub.view.fragments;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.content.LocalBroadcastManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import com.buyer.steelhub.R;
import com.buyer.steelhub.customUi.MyTextView;
import com.buyer.steelhub.model.ModelManager;
import com.buyer.steelhub.model.Order;
import com.buyer.steelhub.utility.STLog;
import com.buyer.steelhub.utility.Utils;
import com.buyer.steelhub.view.adapter.OrderAdapter;

import java.util.ArrayList;

import de.greenrobot.event.EventBus;

/**
 * Created by arun.sharma on 3/27/2017.
 */

public class MyOrdersFragment extends Fragment implements View.OnClickListener {

    private String TAG = MyOrdersFragment.class.getSimpleName();
    private Activity activity;
    private MyTextView txt_pending, txt_confirmed, txt_in_progress, txt_history;
    private ListView orderList;
    private int orderType = 0;
    private ArrayList<Order> orderArrayList;
    private String token = "";

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        this.activity = super.getActivity();
        Intent intent = new Intent("Header");
        intent.putExtra("message", activity.getString(R.string.title_my_orders));

        LocalBroadcastManager.getInstance(activity).sendBroadcast(intent);

        View rootView = inflater.inflate(R.layout.fragment_my_orders, container, false);

        txt_confirmed = (MyTextView) rootView.findViewById(R.id.txt_confirmed);
        txt_pending = (MyTextView) rootView.findViewById(R.id.txt_pending);
        txt_in_progress = (MyTextView) rootView.findViewById(R.id.txt_in_progress);
        txt_history = (MyTextView) rootView.findViewById(R.id.txt_history);

        orderList = (ListView) rootView.findViewById(R.id.list_orders);

        txt_history.setOnClickListener(this);
        txt_confirmed.setOnClickListener(this);
        txt_pending.setOnClickListener(this);
        txt_in_progress.setOnClickListener(this);

        orderType = 0;
        token = "Pending";
        setOrderType();
        getData();
        // Inflate the layout for this fragment
        return rootView;
    }

    private void getData() {
        Utils.showLoading(activity, activity.getString(R.string.please_wait));
        ModelManager.getInstance().getOrderManager().getOrders(activity, true, orderType);
    }

    private void setData() {
        if (orderArrayList.size() > 0) {
            OrderAdapter orderAdapter = new OrderAdapter(activity, orderArrayList);
            orderList.setAdapter(orderAdapter);
            orderAdapter.notifyDataSetChanged();
        } else {
            orderList.setAdapter(null);
            Utils.showMessage(activity, activity.getString(R.string.no_record_found));
        }
    }


    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.txt_pending:
                token = "Pending";
                orderType = 0;
                setOrderType();
                getData();
                break;
            case R.id.txt_confirmed:
                token = "Confirmed";
                orderType = 1;
                setOrderType();
                getData();
                break;
            case R.id.txt_in_progress:
                token = "InProgress";
                orderType = 3;
                setOrderType();
                getData();
                break;
            case R.id.txt_history:
                token = "History";
                orderType = 4;
                setOrderType();
                getData();
                break;
        }

    }

    private void setOrderType(/*int type*/) {
        if (orderType == 0) {
            txt_pending.setTextColor(Utils.setColor(activity, R.color.white));
            txt_pending.setBackgroundColor(Utils.setColor(activity, R.color.transparent));

            txt_confirmed.setTextColor(Utils.setColor(activity, R.color.colorAccent));
            txt_confirmed.setBackgroundColor(Utils.setColor(activity, R.color.white));
            txt_in_progress.setTextColor(Utils.setColor(activity, R.color.colorAccent));
            txt_in_progress.setBackgroundColor(Utils.setColor(activity, R.color.white));
            txt_history.setTextColor(Utils.setColor(activity, R.color.colorAccent));
            txt_history.setBackgroundColor(Utils.setColor(activity, R.color.white));
        } else if (orderType == 1) {
            txt_confirmed.setTextColor(Utils.setColor(activity, R.color.white));
            txt_confirmed.setBackgroundColor(Utils.setColor(activity, R.color.transparent));

            txt_pending.setTextColor(Utils.setColor(activity, R.color.colorAccent));
            txt_pending.setBackgroundColor(Utils.setColor(activity, R.color.white));
            txt_in_progress.setTextColor(Utils.setColor(activity, R.color.colorAccent));
            txt_in_progress.setBackgroundColor(Utils.setColor(activity, R.color.white));
            txt_history.setTextColor(Utils.setColor(activity, R.color.colorAccent));
            txt_history.setBackgroundColor(Utils.setColor(activity, R.color.white));
        } else if (orderType == 3) {
            txt_in_progress.setTextColor(Utils.setColor(activity, R.color.white));
            txt_in_progress.setBackgroundColor(Utils.setColor(activity, R.color.transparent));

            txt_confirmed.setTextColor(Utils.setColor(activity, R.color.colorAccent));
            txt_confirmed.setBackgroundColor(Utils.setColor(activity, R.color.white));
            txt_pending.setTextColor(Utils.setColor(activity, R.color.colorAccent));
            txt_pending.setBackgroundColor(Utils.setColor(activity, R.color.white));
            txt_history.setTextColor(Utils.setColor(activity, R.color.colorAccent));
            txt_history.setBackgroundColor(Utils.setColor(activity, R.color.white));
        } else if (orderType == 4) {
            txt_history.setTextColor(Utils.setColor(activity, R.color.white));
            txt_history.setBackgroundColor(Utils.setColor(activity, R.color.transparent));

            txt_confirmed.setTextColor(Utils.setColor(activity, R.color.colorAccent));
            txt_confirmed.setBackgroundColor(Utils.setColor(activity, R.color.white));
            txt_in_progress.setTextColor(Utils.setColor(activity, R.color.colorAccent));
            txt_in_progress.setBackgroundColor(Utils.setColor(activity, R.color.white));
            txt_pending.setTextColor(Utils.setColor(activity, R.color.colorAccent));
            txt_pending.setBackgroundColor(Utils.setColor(activity, R.color.white));
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
        if (message.equalsIgnoreCase(token + " True")) {
            Utils.dismissLoading();
            orderArrayList = ModelManager.getInstance().getOrderManager().getOrders(activity, false, orderType);
            if (orderArrayList != null)
                setData();
            else {
                Utils.showMessage(activity, activity.getString(R.string.no_record_found));
                orderList.setAdapter(null);
            }
            STLog.e(TAG, token + " True");
        } else if (message.contains(token + " False")) {
            // showMatchHistoryList();
            String[] m = message.split("@#@");
            if (m.length > 1)
                Utils.showMessage(activity, m[1]);
            else
                Utils.showMessage(activity, activity.getString(R.string.oops_something_went_wrong));
            STLog.e(TAG, token + " False");
            Utils.dismissLoading();
            orderList.setAdapter(null);
        } else if (message.contains(token + " Network Error")) {
            // showMatchHistoryList();
            Utils.showMessage(activity, activity.getString(R.string.oops_something_went_wrong));
            STLog.e(TAG, token + " Network Error");
            orderList.setAdapter(null);
            Utils.dismissLoading();
        }

    }
}
