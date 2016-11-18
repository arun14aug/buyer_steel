package com.buyer.steelhub.view.fragments;

/**
 * Created by arun.sharma on 29/07/15.
 */

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.content.LocalBroadcastManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.buyer.steelhub.R;


public class HistoryFragment extends Fragment {

    public HistoryFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        Activity activity = super.getActivity();
        Intent intent = new Intent("Header");
        intent.putExtra("message", activity.getString(R.string.title_history));

        LocalBroadcastManager.getInstance(activity).sendBroadcast(intent);

        View rootView = inflater.inflate(R.layout.fragment_home, container, false);


        // Inflate the layout for this fragment
        return rootView;
    }

}
