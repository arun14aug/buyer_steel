package com.buyer.steelhub.view.adapter;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;

import com.buyer.steelhub.R;
import com.buyer.steelhub.customUi.MyTextView;
import com.buyer.steelhub.model.Order;
import com.buyer.steelhub.utility.Utils;
import com.buyer.steelhub.view.fragments.RTGSFragment;

import java.util.ArrayList;

public class OrderAdapter extends BaseAdapter {
    private ArrayList<Order> list;
    private Activity activity;

    public OrderAdapter(final Activity context,
                        ArrayList<Order> list) {
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
            v = li.inflate(R.layout.row_orders_list, null);

            viewHolder = new CompleteListViewHolder(v);
            v.setTag(viewHolder);
        } else {
            viewHolder = (CompleteListViewHolder) v.getTag();
        }

        try {

            viewHolder.txt_city.setText(list.get(position).getCity());
            viewHolder.txt_state.setText(list.get(position).getState());
            viewHolder.txt_budget.setText(list.get(position).getBudget());

            viewHolder.txt_date.setText(list.get(position).getRequired_by_date());
            String flag = list.get(position).getOrder_status();
            if (flag == null)
                flag = "";
            if (flag.equalsIgnoreCase("1"))
                viewHolder.color_view.setBackgroundColor(Utils.setColor(activity, R.color.red_color));
            else
                viewHolder.color_view.setBackgroundColor(Utils.setColor(activity, R.color.k_blue_color));

            viewHolder.img_action.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if (list.get(position).getOrder_status().equalsIgnoreCase("0")) {
                        Fragment fragment = new RTGSFragment();
                        Bundle bundle = new Bundle();
                        bundle.putString("requirement_id", list.get(position).getRequirement_id());
                        bundle.putString("seller_id", list.get(position).getSeller_id());
                        bundle.putString("buyer_id", list.get(position).getBuyer_id());
                        FragmentManager fragmentManager = ((FragmentActivity) activity).getSupportFragmentManager();
                        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                        fragmentTransaction.replace(R.id.container_body, fragment, "RTGSFragment");
                        fragmentTransaction.addToBackStack("RTGSFragment");
                        fragmentTransaction.commit();
                    }
                }
            });
        } catch (Exception ex) {
            ex.printStackTrace();
        }

        return v;
    }

    private class CompleteListViewHolder {
        private MyTextView txt_city, txt_state, txt_budget, txt_date;
        private ImageView img_action;
        private View color_view;

        private CompleteListViewHolder(View convertview) {
            txt_city = (MyTextView) convertview
                    .findViewById(R.id.txt_city);
            txt_state = (MyTextView) convertview
                    .findViewById(R.id.txt_state);
            txt_budget = (MyTextView) convertview
                    .findViewById(R.id.txt_budget);
            txt_date = (MyTextView) convertview.findViewById(R.id.txt_date);
            img_action = (ImageView) convertview
                    .findViewById(R.id.img_action);
            color_view = convertview.findViewById(R.id.color_view);
        }
    }
}
