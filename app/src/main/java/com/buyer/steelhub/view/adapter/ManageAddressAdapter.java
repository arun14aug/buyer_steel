package com.buyer.steelhub.view.adapter;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.buyer.steelhub.R;
import com.buyer.steelhub.customUi.MyTextView;
import com.buyer.steelhub.model.Address;

import java.util.ArrayList;

public class ManageAddressAdapter extends RecyclerView.Adapter<ManageAddressAdapter.ViewHolder> {
    private ArrayList<Address> list;

    public ManageAddressAdapter(ArrayList<Address> list) {
        this.list = list;
    }

    @Override
    public ManageAddressAdapter.ViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.row_address_list, viewGroup, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ViewHolder viewHolder, int position) {

        viewHolder.txt_area.setText(list.get(position).getCity() + ", " + list.get(position).getState());
        viewHolder.txt_name.setText(list.get(position).getFirm_name());
        viewHolder.txt_address1.setText(list.get(position).getAddress1());
        viewHolder.txt_address2.setText(list.get(position).getAddress2());
        viewHolder.txt_mobile.setText(list.get(position).getMobile());
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

//    void addItem(String country) {
//        countries.add(country);
//        notifyItemInserted(countries.size());
//    }

    void removeItem(int position) {
        list.remove(position);
        notifyItemRemoved(position);
        notifyItemRangeChanged(position, list.size());
    }

    class ViewHolder extends RecyclerView.ViewHolder {
        MyTextView txt_mobile, txt_name, txt_address1, txt_address2, txt_area;

        ViewHolder(View convertview) {
            super(convertview);

            txt_mobile = (MyTextView) convertview
                    .findViewById(R.id.txt_mobile);
            txt_name = (MyTextView) convertview
                    .findViewById(R.id.txt_name);
            txt_address1 = (MyTextView) convertview
                    .findViewById(R.id.txt_address1);
            txt_address2 = (MyTextView) convertview.findViewById(R.id.txt_address2);
            txt_area = (MyTextView) convertview.findViewById(R.id.txt_area);
        }
    }
}