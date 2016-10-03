package com.tanzil.steelhub.view.fragments;

/**
 * Created by arun.sharma on 29/07/15.
 */

import android.app.Activity;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.tanzil.steelhub.R;
import com.tanzil.steelhub.customUi.MyTextView;


public class DescriptionFragment extends Fragment {

    private Activity activity;
    private MyTextView txt_quantity, txt_physical, txt_chemical, txt_test_cert,
            txt_length, txt_type, txt_rate, txt_approx_order_value, txt_preferred_brands,
            txt_required_date, txt_grade_required;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.description_layout, container, false);

        txt_grade_required = (MyTextView) rootView.findViewById(R.id.txt_grade_required);
        txt_physical = (MyTextView) rootView.findViewById(R.id.txt_physical);
        txt_chemical = (MyTextView) rootView.findViewById(R.id.txt_chemical);
        txt_test_cert = (MyTextView) rootView.findViewById(R.id.txt_test_cert);
        txt_quantity = (MyTextView) rootView.findViewById(R.id.txt_quantity);
        txt_length = (MyTextView) rootView.findViewById(R.id.txt_length);
        txt_type = (MyTextView) rootView.findViewById(R.id.txt_type);
        txt_rate = (MyTextView) rootView.findViewById(R.id.txt_rate);
        txt_preferred_brands = (MyTextView) rootView.findViewById(R.id.txt_preferred_brands);
        txt_required_date = (MyTextView) rootView.findViewById(R.id.txt_required_date);
        txt_approx_order_value = (MyTextView) rootView.findViewById(R.id.txt_approx_order_value);

        // Inflate the layout for this fragment
        return rootView;
    }

}
