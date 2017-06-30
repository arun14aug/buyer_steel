package com.buyer.steelhub.view.fragments;

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
import android.widget.ImageView;
import android.widget.ListView;

import com.buyer.steelhub.R;
import com.buyer.steelhub.customUi.MyButton;
import com.buyer.steelhub.customUi.MyTextView;
import com.buyer.steelhub.model.Address;
import com.buyer.steelhub.model.ModelManager;
import com.buyer.steelhub.utility.Preferences;
import com.buyer.steelhub.utility.STLog;
import com.buyer.steelhub.utility.Utils;
import com.buyer.steelhub.view.adapter.AddressAdapter;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

import de.greenrobot.event.EventBus;

/**
 * Created by arun.sharma on 3/27/2017.
 */

public class ManageAddressFragment extends Fragment implements View.OnClickListener {
    private String TAG = ManageAddressFragment.class.getSimpleName();
    private Activity activity;
    private ArrayList<Address> addressArrayList;
    private ListView listView;
    private MyTextView txt_billing_address, txt_shipping_address;
    private String type = "";
    private ImageView img_add;
    private AddressAdapter addressAdapter;

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
        btn_place_order.setVisibility(View.GONE);

        img_add = (ImageView) activity.findViewById(R.id.img_add);
        img_add.setVisibility(View.VISIBLE);
        img_add.setImageResource(R.drawable.new_requirement);
        img_add.setOnClickListener(this);

        txt_billing_address.setOnClickListener(this);
        txt_shipping_address.setOnClickListener(this);

        listView.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
                showDialog(addressArrayList.get(position).getId());
                return false;
            }
        });

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                if (type.equalsIgnoreCase("billing"))
                    Preferences.writeString(activity, Preferences.BILLING_ID, addressArrayList.get(position).getId());
                else
                    Preferences.writeString(activity, Preferences.SHIPPING_ID, addressArrayList.get(position).getId());
                addressAdapter.notifyDataSetChanged();
            }
        });

        type = "billing";

        getAddresses();
        return rootView;
    }

    private void getAddresses() {
        Utils.showLoading(activity, activity.getString(R.string.please_wait));
        ModelManager.getInstance().getAddressManager().getAddresses(activity, type, true);
    }

    private void setData() {
        ArrayList<Address> addresses = new ArrayList<>();
        if (addressArrayList.size() > 0)
            for (int i = 0; i < addressArrayList.size(); i++)
                if (addressArrayList.get(i).getAddressType().equalsIgnoreCase(type))
                    addresses.add(addressArrayList.get(i));

        if (addresses.size() > 0) {
            addressAdapter = new AddressAdapter(activity, addresses, type);
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
                getAddresses();
                txt_billing_address.setTextColor(Utils.setColor(activity, R.color.white));
                txt_billing_address.setBackgroundColor(Utils.setColor(activity, R.color.transparent));
                txt_shipping_address.setTextColor(Utils.setColor(activity, R.color.dark_grey));
                txt_shipping_address.setBackgroundColor(Utils.setColor(activity, R.color.white));
                break;
            case R.id.txt_shipping_address:
                type = "shipping";
                getAddresses();
                txt_billing_address.setTextColor(Utils.setColor(activity, R.color.dark_grey));
                txt_billing_address.setBackgroundColor(Utils.setColor(activity, R.color.white));
                txt_shipping_address.setTextColor(Utils.setColor(activity, R.color.white));
                txt_shipping_address.setBackgroundColor(Utils.setColor(activity, R.color.transparent));
                break;
            case R.id.img_add:
                Fragment fragment = new AddNewAddressFragment();
                Bundle bundle = new Bundle();
                bundle.putString("type", type);
                bundle.putString("action", "add");
                fragment.setArguments(bundle);
                FragmentManager fragmentManager = ((FragmentActivity) activity).getSupportFragmentManager();
                FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                fragmentTransaction.replace(R.id.container_body, fragment, "AddNewAddressFragment");
                fragmentTransaction.addToBackStack("AddNewAddressFragment");
                fragmentTransaction.commit();
                break;
        }
    }

    private void showDialog(final String id) {
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
                            bundle.putString("id", id);
                            fragment.setArguments(bundle);
                            FragmentManager fragmentManager = ((FragmentActivity) activity).getSupportFragmentManager();
                            FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                            fragmentTransaction.replace(R.id.container_body, fragment, "AddNewAddressFragment");
                            fragmentTransaction.addToBackStack("AddNewAddressFragment");
                            fragmentTransaction.commit();
                        } else
                            showAlert(id);
                    }
                });
        builderSingle.show();
    }

    private void showAlert(final String ID) {
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
                                        JSONObject jsonObject = new JSONObject();
                                        try {
                                            jsonObject.put("id", ID);
                                            jsonObject.put("addressType", type);
                                        } catch (JSONException e) {
                                            e.printStackTrace();
                                        }
                                        Utils.showLoading(activity, activity.getString(R.string.please_wait));
                                        ModelManager.getInstance().getAddressManager().deleteAddress(activity, jsonObject);
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
        img_add.setImageResource(R.drawable.settings);
    }

    public void onEventMainThread(String message) {
        if (message.equalsIgnoreCase("AddressList True")) {
            Utils.dismissLoading();
            addressArrayList = ModelManager.getInstance().getAddressManager().getAddresses(activity, type, false);
            if (addressArrayList != null)
                setData();
            else
                Utils.showMessage(activity, activity.getString(R.string.no_record_found));
            STLog.e(TAG, "AddressList True");
        } else if (message.contains("AddressList False")) {
            // showMatchHistoryList();
            String[] m = message.split("@#@");
            if (m.length > 1)
                Utils.showMessage(activity, m[1]);
            else
                Utils.showMessage(activity, activity.getString(R.string.oops_something_went_wrong));
            STLog.e(TAG, "AddressList False");
            Utils.dismissLoading();
        } else if (message.contains("AddressList Network Error")) {
            // showMatchHistoryList();
            Utils.showMessage(activity, activity.getString(R.string.oops_something_went_wrong));
            STLog.e(TAG, "AddressList Network Error");
            Utils.dismissLoading();
        } else if (message.equalsIgnoreCase("AddressDelete True")) {
            Utils.dismissLoading();
            getAddresses();
            STLog.e(TAG, "AddressDelete True");
        } else if (message.contains("AddressDelete False")) {
            // showMatchHistoryList();
            Utils.showMessage(activity, activity.getString(R.string.oops_something_went_wrong));
            STLog.e(TAG, "AddressDelete False");
            Utils.dismissLoading();
        } else if (message.contains("AddressDelete Network Error")) {
            // showMatchHistoryList();
            Utils.showMessage(activity, activity.getString(R.string.oops_something_went_wrong));
            STLog.e(TAG, "AddressDelete Network Error");
            Utils.dismissLoading();
        }

    }
}
