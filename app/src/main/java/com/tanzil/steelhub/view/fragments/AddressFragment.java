package com.tanzil.steelhub.view.fragments;

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
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.tanzil.steelhub.R;
import com.tanzil.steelhub.customUi.MyButton;
import com.tanzil.steelhub.customUi.MyTextView;
import com.tanzil.steelhub.model.Address;
import com.tanzil.steelhub.model.ModelManager;
import com.tanzil.steelhub.utility.STLog;
import com.tanzil.steelhub.utility.Utils;
import com.tanzil.steelhub.view.adapter.AddressAdapter;

import java.util.ArrayList;

import de.greenrobot.event.EventBus;

/**
 * Created by arun.sharma on 11/11/2016.
 */
public class AddressFragment extends Fragment implements View.OnClickListener {
    private String TAG = AddressFragment.class.getSimpleName();
    private Activity activity;
    private ArrayList<Address> addressArrayList;
    private ListView listView;
    private MyTextView txt_billing_address, txt_shipping_address;
    private String type = "";

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        this.activity = super.getActivity();
        Intent intent = new Intent("Header");
        intent.putExtra("message", activity.getString(R.string.address));

        LocalBroadcastManager.getInstance(activity).sendBroadcast(intent);
        View rootView = inflater.inflate(R.layout.fragment_address, container, false);

        listView = (ListView) rootView.findViewById(R.id.address_list);
        txt_billing_address = (MyTextView) rootView.findViewById(R.id.txt_billing_address);
        txt_shipping_address = (MyTextView) rootView.findViewById(R.id.txt_shipping_address);
        MyButton btn_place_order = (MyButton) rootView.findViewById(R.id.btn_place_order);
        btn_place_order.setTransformationMethod(null);

        txt_billing_address.setOnClickListener(this);
        txt_shipping_address.setOnClickListener(this);
        btn_place_order.setOnClickListener(this);

        listView.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View view) {
                showDialog();
                return false;
            }
        });

        Utils.showLoading(activity, activity.getString(R.string.please_wait));
        ModelManager.getInstance().getAddressManager().getAddresses(activity, true);
        return rootView;
    }

    private void setData() {
        ArrayList<Address> addresses = new ArrayList<>();
        if (addressArrayList.size() > 0)
            for (int i = 0; i < addressArrayList.size(); i++)
                if (addressArrayList.get(i).getAddressType().equalsIgnoreCase(type))
                    addresses.add(addressArrayList.get(i));

        if (addresses.size() > 0) {
            AddressAdapter addressAdapter = new AddressAdapter(activity, addresses);
            listView.setAdapter(addressAdapter);
            addressAdapter.notifyDataSetChanged();
        } else {
            listView.setAdapter(null);
            Utils.showMessage(activity, activity.getString(R.string.no_record_found));
        }
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.txt_billing_address:
                type = "billing";
                txt_billing_address.setTextColor(Utils.setColor(activity, R.color.white));
                txt_billing_address.setBackgroundColor(Utils.setColor(activity, R.color.transparent));
                txt_shipping_address.setTextColor(Utils.setColor(activity, R.color.dark_grey));
                txt_shipping_address.setBackgroundColor(Utils.setColor(activity, R.color.white));
                break;
            case R.id.txt_shipping_address:
                type = "shipping";
                txt_billing_address.setTextColor(Utils.setColor(activity, R.color.dark_grey));
                txt_billing_address.setBackgroundColor(Utils.setColor(activity, R.color.white));
                txt_shipping_address.setTextColor(Utils.setColor(activity, R.color.white));
                txt_shipping_address.setBackgroundColor(Utils.setColor(activity, R.color.transparent));
                break;
            case R.id.btn_place_order:
                break;
        }
    }

    private void showDialog() {
        AlertDialog.Builder builderSingle = new AlertDialog.Builder(activity);
        builderSingle.setTitle("Choose Action");

        final ArrayAdapter<String> arrayAdapter = new ArrayAdapter<>(
                activity,
                android.R.layout.simple_list_item_1);
        arrayAdapter.add("Edit");
        arrayAdapter.add("Delete");

        builderSingle.setAdapter(
                arrayAdapter,
                new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        String strName = arrayAdapter.getItem(which);
                        if (which == 0) {
                            Fragment fragment = new AddNewAddressFragment();
                            Bundle bundle = new Bundle();
                            bundle.putString("type", type);
                            bundle.putString("action", "edit");
                            fragment.setArguments(bundle);
                            FragmentManager fragmentManager = ((FragmentActivity) activity).getSupportFragmentManager();
                            FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                            fragmentTransaction.replace(R.id.container_body, fragment, "AddNewAddressFragment");
                            fragmentTransaction.addToBackStack("AddNewAddressFragment");
                            fragmentTransaction.commit();
                        } else
                            showAlert();
                    }
                });
        builderSingle.show();
    }

    private void showAlert() {
        activity.runOnUiThread(new Runnable() {
            @Override
            public void run() {
                AlertDialog.Builder builder = new AlertDialog.Builder(activity);
                builder.setMessage(activity.getString(R.string.action_alert))
                        .setCancelable(false)
                        .setPositiveButton(
                                activity.getString(R.string.ok_label),
                                new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialog,
                                                        int id) {

                                        dialog.cancel();
                                    }
                                })
                        .setNegativeButton(activity.getString(R.string.cancel),
                                new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialog,
                                                        int id) {
                                        dialog.cancel();
                                    }
                                });
                AlertDialog alert = builder.create();
                alert.show();
            }
        });
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
            addressArrayList = ModelManager.getInstance().getAddressManager().getAddresses(activity, false);
            if (addressArrayList != null)
                setData();
            else
                Utils.showMessage(activity, activity.getString(R.string.no_record_found));
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
