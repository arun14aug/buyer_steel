package com.buyer.steelhub.view.adapter;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

import com.buyer.steelhub.R;
import com.buyer.steelhub.customUi.MyTextView;
import com.buyer.steelhub.model.BargainUnit;

import java.util.ArrayList;

public class AmountDetailAdapter extends BaseAdapter {
    private ArrayList<BargainUnit> list;
    private Activity activity;

    public AmountDetailAdapter(final Activity context,
                               ArrayList<BargainUnit> list) {
        this.list = list;
        this.activity = context;
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
            v = li.inflate(R.layout.row_amount_details, null);

            viewHolder = new CompleteListViewHolder(v);
            v.setTag(viewHolder);
        } else {
            viewHolder = (CompleteListViewHolder) v.getTag();
        }

        try {

            viewHolder.txt_size.setText(list.get(position).getSize());
            viewHolder.txt_quantity.setText(list.get(position).getQuantity());
            viewHolder.txt_bargain.setText(list.get(position).getNew_unit_price());

            viewHolder.txt_amount.setText(list.get(position).getUnit_price());
        } catch (Exception ex) {
            ex.printStackTrace();
        }

        return v;
    }

    private class CompleteListViewHolder {
        private MyTextView txt_size, txt_bargain, txt_quantity, txt_amount;

        private CompleteListViewHolder(View convertview) {
            txt_size = (MyTextView) convertview
                    .findViewById(R.id.txt_size);
            txt_bargain = (MyTextView) convertview
                    .findViewById(R.id.txt_bargain);
            txt_quantity = (MyTextView) convertview
                    .findViewById(R.id.txt_quantity);
            txt_amount = (MyTextView) convertview.findViewById(R.id.txt_amount);
        }
    }
}
