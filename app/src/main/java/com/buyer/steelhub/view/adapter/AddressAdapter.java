package com.buyer.steelhub.view.adapter;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.LinearLayout;

import com.buyer.steelhub.R;
import com.buyer.steelhub.customUi.MyTextView;
import com.buyer.steelhub.model.Address;
import com.buyer.steelhub.utility.Preferences;
import com.buyer.steelhub.utility.Utils;

import java.util.ArrayList;

public class AddressAdapter extends BaseAdapter {
    private ArrayList<Address> list;
    private Activity activity;
    private String type;

    public AddressAdapter(final Activity context,
                          ArrayList<Address> list, String type) {
        this.list = list;
        this.activity = context;
        this.type = type;
    }

    @Override
    public int getCount() {
        // TODO Auto-generated method stub
        return list.size();
    }

    @Override
    public Object getItem(int arg0) {
        // TODO Auto-generated method stub
        return list.get(arg0);
    }

    @Override
    public long getItemId(int arg0) {
        // TODO Auto-generated method stub
        return 0;
    }

    @SuppressLint({"InflateParams", "NewApi"})
    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        // TODO Auto-generated method stub
        View v = convertView;
        final CompleteListViewHolder viewHolder;
        if (convertView == null) {
            LayoutInflater li = (LayoutInflater) activity
                    .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            v = li.inflate(R.layout.row_address_list, null);

            viewHolder = new CompleteListViewHolder(v);
            v.setTag(viewHolder);
        } else {
            viewHolder = (CompleteListViewHolder) v.getTag();
        }

        try {

            viewHolder.txt_area.setText(list.get(position).getCity() + ", " + list.get(position).getState());
            viewHolder.txt_name.setText(list.get(position).getFirm_name());
            viewHolder.txt_address1.setText(list.get(position).getAddress1());
            viewHolder.txt_address2.setText(list.get(position).getAddress2());
            viewHolder.txt_mobile.setText(list.get(position).getMobile());

            String id;
            if (type.equalsIgnoreCase("billing")) {
                id = Preferences.readString(activity, Preferences.BILLING_ID, "");
            } else {
                id = Preferences.readString(activity, Preferences.SHIPPING_ID, "");
            }

            if (list.get(position).getId().equalsIgnoreCase(id))
                viewHolder.row_layout.setBackgroundColor(Utils.setColor(activity, R.color.bg_color));
            else
                viewHolder.row_layout.setBackgroundColor(Utils.setColor(activity, R.color.colorWhite));

        } catch (Exception ex) {
            ex.printStackTrace();
        }

        return v;
    }

    private class CompleteListViewHolder {
        MyTextView txt_mobile, txt_name, txt_address1, txt_address2, txt_area;
        LinearLayout row_layout;

        CompleteListViewHolder(View convertview) {
            txt_mobile = (MyTextView) convertview
                    .findViewById(R.id.txt_mobile);
            txt_name = (MyTextView) convertview
                    .findViewById(R.id.txt_name);
            txt_address1 = (MyTextView) convertview
                    .findViewById(R.id.txt_address1);
            txt_address2 = (MyTextView) convertview.findViewById(R.id.txt_address2);
            txt_area = (MyTextView) convertview.findViewById(R.id.txt_area);

            row_layout = (LinearLayout) convertview.findViewById(R.id.row_layout);
        }
    }
}
