package com.tanzil.steelhub.view.fragments;

/**
 * Created by arun.sharma on 29/07/15.
 */

import android.app.Activity;
import android.app.DatePickerDialog;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.DatePicker;

import com.tanzil.steelhub.R;
import com.tanzil.steelhub.customUi.MyButton;
import com.tanzil.steelhub.customUi.MyEditText;
import com.tanzil.steelhub.customUi.MyTextView;
import com.tanzil.steelhub.utility.Utils;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Locale;
import java.util.concurrent.TimeUnit;


public class BuyerPostFragment extends Fragment implements View.OnClickListener {

    private Activity activity;
    private MyEditText et_quantity, et_diameter, et_rate, et_approx_order;
    private MyTextView txt_grade_required, txt_physical, txt_chemical, txt_test_cert,
            txt_standard, txt_random, txt_straight, txt_bend, txt_preferred_brands,
            txt_required_date, txt_delivery_location, txt_city;
    private String physical = "", chemical = "", test_cert = "";
    private Calendar myCalendar = Calendar.getInstance();

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.new_requirement_screen, container, false);

        et_quantity = (MyEditText) rootView.findViewById(R.id.et_quantity);
        et_diameter = (MyEditText) rootView.findViewById(R.id.et_diameter);
//        et_rate = (MyEditText) rootView.findViewById(R.id.et_rate);
//        et_approx_order = (MyEditText) rootView.findViewById(R.id.et_approx_order);

        txt_grade_required = (MyTextView) rootView.findViewById(R.id.txt_grade_required);
        txt_physical = (MyTextView) rootView.findViewById(R.id.txt_physical);
        txt_chemical = (MyTextView) rootView.findViewById(R.id.txt_chemical);
        txt_test_cert = (MyTextView) rootView.findViewById(R.id.txt_test_cert);
        txt_standard = (MyTextView) rootView.findViewById(R.id.txt_standard);
        txt_random = (MyTextView) rootView.findViewById(R.id.txt_random);
        txt_straight = (MyTextView) rootView.findViewById(R.id.txt_straight);
        txt_bend = (MyTextView) rootView.findViewById(R.id.txt_bend);
        txt_preferred_brands = (MyTextView) rootView.findViewById(R.id.txt_preferred_brands);
        txt_required_date = (MyTextView) rootView.findViewById(R.id.txt_required_date);
//        txt_delivery_location = (MyTextView) rootView.findViewById(R.id.txt_delivery_location);
//        txt_city = (MyTextView) rootView.findViewById(R.id.txt_city);

        MyButton btn_submit = (MyButton) rootView.findViewById(R.id.btn_submit);
        btn_submit.setTransformationMethod(null);

        btn_submit.setOnClickListener(this);
        txt_grade_required.setOnClickListener(this);
        txt_physical.setOnClickListener(this);
        txt_chemical.setOnClickListener(this);
        txt_test_cert.setOnClickListener(this);
        txt_standard.setOnClickListener(this);
        txt_random.setOnClickListener(this);
        txt_straight.setOnClickListener(this);
        txt_bend.setOnClickListener(this);
        txt_preferred_brands.setOnClickListener(this);
        txt_required_date.setOnClickListener(this);
        txt_delivery_location.setOnClickListener(this);
        txt_city.setOnClickListener(this);
        // Inflate the layout for this fragment
        return rootView;
    }

    private Drawable getImage(int id) {
        Drawable check;
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.LOLLIPOP) {
            check = activity.getDrawable(id);
        } else {
            check = activity.getResources().getDrawable(id);
        }
        return check;
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.btn_submit:
                break;
            case R.id.txt_grade_required:
                break;
            case R.id.txt_physical:
                if (!physical.equalsIgnoreCase("true")) {
                    physical = "true";
                    txt_physical.setCompoundDrawablesWithIntrinsicBounds(null, null, getImage(R.drawable.check_on), null);
                } else {
                    physical = "false";
                    txt_physical.setCompoundDrawablesWithIntrinsicBounds(null, null, getImage(R.drawable.check), null);
                }
                break;
            case R.id.txt_chemical:
                if (!chemical.equalsIgnoreCase("true")) {
                    chemical = "true";
                    txt_chemical.setCompoundDrawablesWithIntrinsicBounds(null, null, getImage(R.drawable.check_on), null);
                } else {
                    chemical = "false";
                    txt_chemical.setCompoundDrawablesWithIntrinsicBounds(null, null, getImage(R.drawable.check), null);
                }
                break;
            case R.id.txt_test_cert:
                if (!test_cert.equalsIgnoreCase("true")) {
                    test_cert = "true";
                    txt_test_cert.setCompoundDrawablesWithIntrinsicBounds(null, null, getImage(R.drawable.check_on), null);
                } else {
                    test_cert = "false";
                    txt_test_cert.setCompoundDrawablesWithIntrinsicBounds(null, null, getImage(R.drawable.check), null);
                }
                break;
            case R.id.txt_standard:
                txt_standard.setTextColor(Utils.setColor(activity, R.color.transparent));
                txt_standard.setBackgroundColor(Utils.setColor(activity, R.color.white));
                txt_random.setTextColor(Utils.setColor(activity, R.color.white));
                txt_random.setBackgroundColor(Utils.setColor(activity, R.color.transparent));
                break;
            case R.id.txt_random:
                txt_standard.setTextColor(Utils.setColor(activity, R.color.white));
                txt_standard.setBackgroundColor(Utils.setColor(activity, R.color.transparent));
                txt_random.setTextColor(Utils.setColor(activity, R.color.transparent));
                txt_random.setBackgroundColor(Utils.setColor(activity, R.color.white));
                break;
            case R.id.txt_straight:
                txt_straight.setTextColor(Utils.setColor(activity, R.color.transparent));
                txt_straight.setBackgroundColor(Utils.setColor(activity, R.color.white));
                txt_bend.setTextColor(Utils.setColor(activity, R.color.white));
                txt_bend.setBackgroundColor(Utils.setColor(activity, R.color.transparent));
                break;
            case R.id.txt_bend:
                txt_straight.setTextColor(Utils.setColor(activity, R.color.white));
                txt_straight.setBackgroundColor(Utils.setColor(activity, R.color.transparent));
                txt_bend.setTextColor(Utils.setColor(activity, R.color.transparent));
                txt_bend.setBackgroundColor(Utils.setColor(activity, R.color.white));
                break;
            case R.id.txt_preferred_brands:
                break;
            case R.id.txt_required_date:
                DatePickerDialog starter = new DatePickerDialog(activity, transaction_date,
                        myCalendar.get(Calendar.YEAR),
                        myCalendar.get(Calendar.MONTH),
                        myCalendar.get(Calendar.DAY_OF_MONTH));
                starter.getDatePicker()
                        .setMinDate(System.currentTimeMillis() + TimeUnit.DAYS.toMillis(1));
                starter.show();
                break;
//            case R.id.txt_delivery_location:
//                break;
//            case R.id.txt_city:
//                break;
        }
    }

    DatePickerDialog.OnDateSetListener transaction_date = new DatePickerDialog.OnDateSetListener() {

        @Override
        public void onDateSet(DatePicker view, int year, int monthOfYear,
                              int dayOfMonth) {
            // TODO Auto-generated method stub
            myCalendar.set(Calendar.YEAR, year);
            myCalendar.set(Calendar.MONTH, monthOfYear);
            myCalendar.set(Calendar.DAY_OF_MONTH, dayOfMonth);
            String myFormat = "MM/dd/yyyy"; // In which you need put here
            SimpleDateFormat sdf = new SimpleDateFormat(myFormat, Locale.US);

            txt_required_date.setText(sdf.format(myCalendar.getTime()));
        }

    };
}
