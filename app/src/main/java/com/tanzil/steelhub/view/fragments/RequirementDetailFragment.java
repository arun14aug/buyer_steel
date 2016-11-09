package com.tanzil.steelhub.view.fragments;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.content.LocalBroadcastManager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.tanzil.steelhub.R;
import com.tanzil.steelhub.customUi.MyButton;
import com.tanzil.steelhub.customUi.MyEditText;
import com.tanzil.steelhub.customUi.MyTextView;
import com.tanzil.steelhub.model.ModelManager;
import com.tanzil.steelhub.model.Quantity;
import com.tanzil.steelhub.model.Requirements;
import com.tanzil.steelhub.utility.Utils;

import java.util.ArrayList;

/**
 * Created by arun.sharma on 11/7/2016.
 */
public class RequirementDetailFragment extends Fragment implements View.OnClickListener {
    private String TAG = RequirementDetailFragment.class.getSimpleName();
    private Activity activity;
    private MyEditText /*et_quantity,*/ et_preferred_brands, et_grade_required, et_required_by_date, et_city, et_state,
            et_budget_amount, et_tax_type, et_amount, et_bargain_amount;
    private MyTextView txt_random, txt_standard, txt_bend, txt_straight/*, txt_diameter*/;
    private MyButton btn_submit, btn_show_more;
    private LinearLayout addMoreLayout, layout_seller_list, layout_show_more, layout_amount, layout_bargain_amount;
    private ImageView ic_physical, ic_chemical, /*ic_grade_required,*/ ic_test_certificate;
    private String brandId = "", steelId = "", gradeId = "", stateId = "",
            taxId = "", phy = "", che = "", gra = "", lngth = "", typ = "", test_cert = "", id = "";

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        this.activity = super.getActivity();
        Intent intent = new Intent("Header");
        intent.putExtra("message", activity.getString(R.string.new_requirements));

        LocalBroadcastManager.getInstance(activity).sendBroadcast(intent);
        View rootView = inflater.inflate(R.layout.requirement_detail_screen, container, false);

        try {
            if (getArguments() != null) {
                Bundle bundle = getArguments();
                id = bundle.getString("id");
            }

        } catch (Exception ex) {
            Log.e(TAG, ex.toString());
        }


        et_budget_amount = (MyEditText) rootView.findViewById(R.id.et_budget_amount);
//        et_quantity = (MyEditText) rootView.findViewById(R.id.et_quantity);
//        txt_diameter = (MyTextView) rootView.findViewById(R.id.txt_diameter);
        et_preferred_brands = (MyEditText) rootView.findViewById(R.id.et_preferred_brands);
        et_grade_required = (MyEditText) rootView.findViewById(R.id.et_grade_required);
        et_city = (MyEditText) rootView.findViewById(R.id.et_city);
        et_state = (MyEditText) rootView.findViewById(R.id.et_state);
        et_tax_type = (MyEditText) rootView.findViewById(R.id.et_tax_type);
        et_required_by_date = (MyEditText) rootView.findViewById(R.id.et_required_by_date);
        et_amount = (MyEditText) rootView.findViewById(R.id.et_amount);
        et_bargain_amount = (MyEditText) rootView.findViewById(R.id.et_bargain_amount);

        txt_random = (MyTextView) rootView.findViewById(R.id.txt_random);
        txt_standard = (MyTextView) rootView.findViewById(R.id.txt_standard);
        txt_bend = (MyTextView) rootView.findViewById(R.id.txt_bend);
        txt_straight = (MyTextView) rootView.findViewById(R.id.txt_straight);

        layout_show_more = (LinearLayout) rootView.findViewById(R.id.layout_show_more);
        layout_seller_list = (LinearLayout) rootView.findViewById(R.id.layout_seller_list);
        layout_amount = (LinearLayout) rootView.findViewById(R.id.layout_amount);
        layout_bargain_amount = (LinearLayout) rootView.findViewById(R.id.layout_bargain_amount);
        addMoreLayout = (LinearLayout) rootView.findViewById(R.id.layout_add_more);

        ic_physical = (ImageView) rootView.findViewById(R.id.ic_physical);
        ic_chemical = (ImageView) rootView.findViewById(R.id.ic_chemical);
//        ic_grade_required = (ImageView) rootView.findViewById(R.id.ic_grade_required);
        ic_test_certificate = (ImageView) rootView.findViewById(R.id.ic_test_certificate);

        btn_show_more = (MyButton) rootView.findViewById(R.id.btn_show_more);
        btn_show_more.setTransformationMethod(null);
        btn_submit = (MyButton) rootView.findViewById(R.id.btn_submit);
        btn_submit.setTransformationMethod(null);

        btn_submit.setOnClickListener(this);
        btn_show_more.setOnClickListener(this);

        setData();
        return rootView;
    }

    private void setData() {
        ArrayList<Requirements> requirementsArrayList = ModelManager.getInstance().getRequirementManager().getRequirements(activity, false);
        for (int i = 0; i < requirementsArrayList.size(); i++) {
            if (id.equalsIgnoreCase(requirementsArrayList.get(i).getRequirement_id())) {
//                if (requirementsArrayList.get(i).getGrade_required().equalsIgnoreCase("0"))
//                    ic_grade_required.setImageResource(R.drawable.toggle_off);
//                else
//                    ic_grade_required.setImageResource(R.drawable.toggle_on);

                if (requirementsArrayList.get(i).getChemical().equalsIgnoreCase("0"))
                    ic_chemical.setImageResource(R.drawable.toggle_off);
                else
                    ic_chemical.setImageResource(R.drawable.toggle_on);

                if (requirementsArrayList.get(i).getPhysical().equalsIgnoreCase("0"))
                    ic_physical.setImageResource(R.drawable.toggle_off);
                else
                    ic_physical.setImageResource(R.drawable.toggle_on);

                if (requirementsArrayList.get(i).getTest_certificate_required().equalsIgnoreCase("0"))
                    ic_test_certificate.setImageResource(R.drawable.toggle_off);
                else
                    ic_test_certificate.setImageResource(R.drawable.toggle_on);

                et_budget_amount.setText(requirementsArrayList.get(i).getBudget());
                et_grade_required.setText(requirementsArrayList.get(i).getGrade_required());
                et_required_by_date.setText(requirementsArrayList.get(i).getRequired_by_date());
                et_city.setText(requirementsArrayList.get(i).getCity());
                et_state.setText(requirementsArrayList.get(i).getState());
                et_tax_type.setText(requirementsArrayList.get(i).getType());

                if (!Utils.isEmptyString(requirementsArrayList.get(i).getInitial_amt())) {
                    layout_amount.setVisibility(View.VISIBLE);
                    et_amount.setText(requirementsArrayList.get(i).getInitial_amt());
                    et_amount.setFocusable(false);
                    layout_bargain_amount.setVisibility(View.VISIBLE);
                    if (!Utils.isEmptyString(requirementsArrayList.get(i).getBargain_amt())) {
                        et_bargain_amount.setFocusable(false);
                        et_bargain_amount.setText(requirementsArrayList.get(i).getBargain_amt());
                        btn_submit.setVisibility(View.GONE);
                    } else {
                        et_bargain_amount.setFocusable(true);
                        btn_submit.setVisibility(View.VISIBLE);
                    }
                } else {
                    et_amount.setFocusable(true);
                    layout_bargain_amount.setVisibility(View.GONE);
                    et_bargain_amount.setFocusable(false);
                    btn_submit.setVisibility(View.VISIBLE);
                }

                String[] preferredBrands = requirementsArrayList.get(i).getPreffered_brands();
                String val = "";
                if (preferredBrands != null)
                    if (preferredBrands.length > 0) {
                        for (String preferredBrand : preferredBrands)
                            val = val + preferredBrand + ", ";
                    }
                if (val.length() > 0)
                    val = val.substring(0, val.length() - 1);
                et_preferred_brands.setText(val);

                ArrayList<Quantity> quantityArrayList = requirementsArrayList.get(i).getQuantityArrayList();
                if (quantityArrayList != null)
                    if (quantityArrayList.size() > 0)
                        for (int j = 0; j < quantityArrayList.size(); j++) {
                            LayoutInflater layoutInflater =
                                    (LayoutInflater) activity.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
                            final View addView = layoutInflater.inflate(R.layout.row_add_more, null);
                            MyEditText quantity = (MyEditText) addView.findViewById(R.id.quantity);
                            MyTextView diameter = (MyTextView) addView.findViewById(R.id.diameter);
                            MyEditText amount = (MyEditText) addView.findViewById(R.id.amount);
                            ImageView remove = (ImageView) addView.findViewById(R.id.remove);
                            remove.setVisibility(View.GONE);

                            quantity.setFocusable(false);
                            amount.setFocusable(false);

                            quantity.setText(quantityArrayList.get(j).getQuantity());
                            diameter.setText(quantityArrayList.get(j).getSize());

                            addMoreLayout.addView(addView);
                        }
                break;
            }
        }
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.btn_show_more:
                if (layout_show_more.getVisibility() == View.VISIBLE) {
                    layout_show_more.setVisibility(View.GONE);
                    btn_show_more.setText(activity.getString(R.string.show_more));
                } else {
                    layout_show_more.setVisibility(View.VISIBLE);
                    btn_show_more.setText(activity.getString(R.string.show_less));
                }
                break;
        }
    }
}
