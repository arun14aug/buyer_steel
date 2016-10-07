package com.tanzil.steelhub.view.adapter;

/**
 * Created by arun.sharma on 8/25/2016.
 */

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

import com.tanzil.steelhub.R;
import com.tanzil.steelhub.customUi.MyTextView;

import java.util.ArrayList;


public class CommonDialogAdapter extends BaseAdapter {
    private ArrayList<String> list;
    Activity context;

    public CommonDialogAdapter(Activity context, ArrayList<String> list) {
        this.list = list;
        this.context = context;
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
        return arg0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        // TODO Auto-generated method stub
        View v = convertView;
        if (v == null) {
            LayoutInflater vi = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            v = vi.inflate(R.layout.row_dropdown_list, null);
        }
        MyTextView textName = (MyTextView) v.findViewById(R.id.txt_name);

        textName.setText(list.get(position));

        return v;
    }

}