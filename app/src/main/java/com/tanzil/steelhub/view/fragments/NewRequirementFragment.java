package com.tanzil.steelhub.view.fragments;

/**
 * Created by arun.sharma on 29/07/15.
 */

import android.app.Activity;
import android.app.DatePickerDialog;
import android.app.Dialog;
import android.content.Context;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.DatePicker;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.Toast;

import com.tanzil.steelhub.R;
import com.tanzil.steelhub.customUi.MyButton;
import com.tanzil.steelhub.customUi.MyEditText;
import com.tanzil.steelhub.customUi.MyTextView;
import com.tanzil.steelhub.model.Brands;
import com.tanzil.steelhub.model.Grades;
import com.tanzil.steelhub.model.ModelManager;
import com.tanzil.steelhub.model.Specifications;
import com.tanzil.steelhub.model.States;
import com.tanzil.steelhub.model.SteelSizes;
import com.tanzil.steelhub.model.TaxTypes;
import com.tanzil.steelhub.utility.Preferences;
import com.tanzil.steelhub.utility.STLog;
import com.tanzil.steelhub.utility.Utils;
import com.tanzil.steelhub.view.adapter.CommonDialogAdapter;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Locale;

import de.greenrobot.event.EventBus;


public class NewRequirementFragment extends Fragment implements View.OnClickListener {

    private String TAG = NewRequirementFragment.class.getSimpleName();
    private Activity activity;
    private MyEditText et_quantity, et_preferred_brands, et_grade_required, et_required_by_date, et_city, et_state, et_budget_amount, et_tax_type;
    private MyTextView txt_random, txt_standard, txt_bend, txt_straight, txt_diameter;
    //    private MyButton btn_add_more;
    private LinearLayout addMoreLayout, default_quantity_layout;
    private ArrayList<Brands> brandsArrayList;
    private ArrayList<Grades> gradesArrayList;
    private ArrayList<SteelSizes> steelSizesArrayList;
//    private ArrayList<Specifications> specificationsArrayList = new ArrayList<>();
    private ArrayList<States> statesArrayList;
    private ArrayList<TaxTypes> taxTypesArrayList;
    private ImageView icon_remove, ic_physical, ic_chemical, ic_grade_required;
    private ArrayList<MyEditText> et_quantityArrayList = new ArrayList<>();
    private ArrayList<MyTextView> et_diameterArrayList = new ArrayList<>();
    private String brandId = "", steelId = "", gradeId = "", stateId = "", taxId = "", phy = "", che = "", gra = "";
    private Calendar myCalendar;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        this.activity = super.getActivity();
        View rootView = inflater.inflate(R.layout.new_requirement_screen, container, false);

        myCalendar = Calendar.getInstance();

        et_budget_amount = (MyEditText) rootView.findViewById(R.id.et_budget_amount);
        et_quantity = (MyEditText) rootView.findViewById(R.id.et_quantity);
        txt_diameter = (MyTextView) rootView.findViewById(R.id.txt_diameter);
        et_preferred_brands = (MyEditText) rootView.findViewById(R.id.et_preferred_brands);
        et_grade_required = (MyEditText) rootView.findViewById(R.id.et_grade_required);
        et_city = (MyEditText) rootView.findViewById(R.id.et_city);
        et_state = (MyEditText) rootView.findViewById(R.id.et_state);
        et_tax_type = (MyEditText) rootView.findViewById(R.id.et_tax_type);
        et_required_by_date = (MyEditText) rootView.findViewById(R.id.et_required_by_date);

        txt_random = (MyTextView) rootView.findViewById(R.id.txt_random);
        txt_standard = (MyTextView) rootView.findViewById(R.id.txt_standard);
        txt_bend = (MyTextView) rootView.findViewById(R.id.txt_bend);
        txt_straight = (MyTextView) rootView.findViewById(R.id.txt_straight);

        LinearLayout layout_grade_required = (LinearLayout) rootView.findViewById(R.id.layout_grade_required);
        LinearLayout layout_physical = (LinearLayout) rootView.findViewById(R.id.layout_physical);
        LinearLayout layout_chemical = (LinearLayout) rootView.findViewById(R.id.layout_chemical);

        default_quantity_layout = (LinearLayout) rootView.findViewById(R.id.default_quantity_layout);
        addMoreLayout = (LinearLayout) rootView.findViewById(R.id.layout_add_more);
        icon_remove = (ImageView) rootView.findViewById(R.id.icon_remove);
        ic_physical = (ImageView) rootView.findViewById(R.id.ic_physical);
        ic_chemical = (ImageView) rootView.findViewById(R.id.ic_chemical);
        ic_grade_required = (ImageView) rootView.findViewById(R.id.ic_grade_required);

        MyButton btn_add_more = (MyButton) rootView.findViewById(R.id.btn_add_more);
        btn_add_more.setTransformationMethod(null);
        MyButton btn_submit = (MyButton) rootView.findViewById(R.id.btn_submit);
        btn_submit.setTransformationMethod(null);

        /**     Checking the common data for dropdown usage     **/
        brandsArrayList = ModelManager.getInstance().getCommonDataManager().getBrands(activity, false);
        gradesArrayList = ModelManager.getInstance().getCommonDataManager().getGrades(activity, false);
        steelSizesArrayList = ModelManager.getInstance().getCommonDataManager().getSteelSize(activity, false);
        statesArrayList = ModelManager.getInstance().getCommonDataManager().getStates(activity, false);
        if (brandsArrayList == null) {
//            Utils.showLoading(activity, activity.getString(R.string.please_wait));
            ModelManager.getInstance().getCommonDataManager().getBrands(activity, true);
        }
        if (gradesArrayList == null) {
//            Utils.showLoading(activity, activity.getString(R.string.please_wait));
            ModelManager.getInstance().getCommonDataManager().getGrades(activity, true);
        }
        if (steelSizesArrayList == null) {
//            Utils.showLoading(activity, activity.getString(R.string.please_wait));
            ModelManager.getInstance().getCommonDataManager().getSteelSize(activity, true);
        }
        if (statesArrayList == null) {
//            Utils.showLoading(activity, activity.getString(R.string.please_wait));
            ModelManager.getInstance().getCommonDataManager().getStates(activity, true);
        }
        if (taxTypesArrayList == null) {
//            Utils.showLoading(activity, activity.getString(R.string.please_wait));
            ModelManager.getInstance().getCommonDataManager().getTaxTypes(activity, true);
        }

        /**     click events     **/
        btn_add_more.setOnClickListener(this);
        btn_submit.setOnClickListener(this);
        layout_grade_required.setOnClickListener(this);
        layout_physical.setOnClickListener(this);
        layout_chemical.setOnClickListener(this);
        txt_random.setOnClickListener(this);
        txt_standard.setOnClickListener(this);
        txt_bend.setOnClickListener(this);
        txt_straight.setOnClickListener(this);
        icon_remove.setOnClickListener(this);
        et_state.setOnClickListener(this);
        et_preferred_brands.setOnClickListener(this);
        txt_diameter.setOnClickListener(this);
        et_grade_required.setOnClickListener(this);
        et_tax_type.setOnClickListener(this);
        et_required_by_date.setOnClickListener(this);

        return rootView;
    }

    private void showDropDownDialog(final int type, final MyEditText et_myText) {
        final Dialog dropDownDialog = new Dialog(activity);
        dropDownDialog.requestWindowFeature(Window.FEATURE_NO_TITLE); // before
        dropDownDialog.setContentView(R.layout.dialog_dropdown_list);
        dropDownDialog.getWindow().setBackgroundDrawable(
                new ColorDrawable(android.graphics.Color.TRANSPARENT));
        MyTextView titleView = (MyTextView) dropDownDialog
                .findViewById(R.id.title_name);
        titleView.setText(activity.getString(R.string.please_select_an_option));
        final ListView listView = (ListView) dropDownDialog
                .findViewById(R.id.list_view);

        ArrayList<String> list = new ArrayList<>();
        if (type == 0) {
            if (brandsArrayList != null)
                if (brandsArrayList.size() > 0)
                    for (int i = 0; i < brandsArrayList.size(); i++)
                        list.add(brandsArrayList.get(i).getName());
                else {
                    Utils.showMessage(activity, activity.getString(R.string.no_record_found));
                    return;
                }
            else {
                Utils.showMessage(activity, activity.getString(R.string.no_record_found));
                return;
            }
        } else if (type == 1) {
            if (steelSizesArrayList != null)
                if (steelSizesArrayList.size() > 0)
                    for (int i = 0; i < steelSizesArrayList.size(); i++)
                        list.add(steelSizesArrayList.get(i).getSize());
                else {
                    Utils.showMessage(activity, activity.getString(R.string.no_record_found));
                    return;
                }
            else {
                Utils.showMessage(activity, activity.getString(R.string.no_record_found));
                return;
            }
        } else if (type == 2) {
            if (gradesArrayList != null)
                if (gradesArrayList.size() > 0)
                    for (int i = 0; i < gradesArrayList.size(); i++)
                        list.add(gradesArrayList.get(i).getGrade());
                else {
                    Utils.showMessage(activity, activity.getString(R.string.no_record_found));
                    return;
                }
            else {
                Utils.showMessage(activity, activity.getString(R.string.no_record_found));
                return;
            }
        } else if (type == 3) {
            if (statesArrayList != null)
                if (statesArrayList.size() > 0)
                    for (int i = 0; i < statesArrayList.size(); i++)
                        list.add(statesArrayList.get(i).getName());
                else {
                    Utils.showMessage(activity, activity.getString(R.string.no_record_found));
                    return;
                }
            else {
                Utils.showMessage(activity, activity.getString(R.string.no_record_found));
                return;
            }
        } else if (type == 4) {
            if (taxTypesArrayList != null)
                if (taxTypesArrayList.size() > 0)
                    for (int i = 0; i < taxTypesArrayList.size(); i++)
                        list.add(taxTypesArrayList.get(i).getType());
                else {
                    Utils.showMessage(activity, activity.getString(R.string.no_record_found));
                    return;
                }
            else {
                Utils.showMessage(activity, activity.getString(R.string.no_record_found));
                return;
            }
        }
        CommonDialogAdapter commonDialogAdapter = new CommonDialogAdapter(
                activity, list);
        listView.setAdapter(commonDialogAdapter);
        commonDialogAdapter.notifyDataSetChanged();

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> parent, View view,
                                    int position, long id) {
                // TODO Auto-generated method stub
                if (type == 0) {
                    et_myText.setText(brandsArrayList.get(position).getName());
                    brandId = brandsArrayList.get(position).getId();
                } else if (type == 1) {
                    et_myText.setText(steelSizesArrayList.get(position).getSize());
                    steelId = steelSizesArrayList.get(position).getId();
                } else if (type == 2) {
                    et_myText.setText(gradesArrayList.get(position).getGrade());
                    gradeId = gradesArrayList.get(position).getId();
                } else if (type == 3) {
                    et_myText.setText(statesArrayList.get(position).getName());
                    stateId = statesArrayList.get(position).getCode();
                } else if (type == 4) {
                    et_myText.setText(taxTypesArrayList.get(position).getType());
                    taxId = taxTypesArrayList.get(position).getId();
                }
                dropDownDialog.dismiss();
            }
        });

        dropDownDialog.show();
    }

    private void showDropDownForSteel(final MyTextView et_myText) {
        final Dialog dropDownDialog = new Dialog(activity);
        dropDownDialog.requestWindowFeature(Window.FEATURE_NO_TITLE); // before
        dropDownDialog.setContentView(R.layout.dialog_dropdown_list);
        dropDownDialog.getWindow().setBackgroundDrawable(
                new ColorDrawable(android.graphics.Color.TRANSPARENT));
        MyTextView titleView = (MyTextView) dropDownDialog
                .findViewById(R.id.title_name);
        titleView.setText(activity.getString(R.string.please_select_an_option));
        final ListView listView = (ListView) dropDownDialog
                .findViewById(R.id.list_view);

        ArrayList<String> list = new ArrayList<>();
        if (steelSizesArrayList != null)
            if (steelSizesArrayList.size() > 0)
                for (int i = 0; i < steelSizesArrayList.size(); i++)
                    list.add(steelSizesArrayList.get(i).getSize());
            else {
                Utils.showMessage(activity, activity.getString(R.string.no_record_found));
                return;
            }
        else {
            Utils.showMessage(activity, activity.getString(R.string.no_record_found));
            return;
        }
        CommonDialogAdapter commonDialogAdapter = new CommonDialogAdapter(
                activity, list);
        listView.setAdapter(commonDialogAdapter);
        commonDialogAdapter.notifyDataSetChanged();

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> parent, View view,
                                    int position, long id) {
                // TODO Auto-generated method stub
                et_myText.setText(steelSizesArrayList.get(position).getSize());
                steelId = steelSizesArrayList.get(position).getId();
                dropDownDialog.dismiss();
            }
        });

        dropDownDialog.show();
    }

    private void addMoreQuantity() {

        LayoutInflater layoutInflater =
                (LayoutInflater) activity.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        final View addView = layoutInflater.inflate(R.layout.row_add_more, null);
        MyEditText quantity = (MyEditText) addView.findViewById(R.id.quantity);
        MyTextView diameter = (MyTextView) addView.findViewById(R.id.diameter);
        ImageView remove = (ImageView) addView.findViewById(R.id.remove);
        remove.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (addMoreLayout.getChildCount() == 1)
                    if (default_quantity_layout.getVisibility() == View.GONE) {
                        default_quantity_layout.setVisibility(View.VISIBLE);
                        icon_remove.setVisibility(View.GONE);
                    }
                ((LinearLayout) addView.getParent()).removeView(addView);
                et_diameterArrayList.remove(((LinearLayout) addView.getParent()).getChildAt(1));
                et_quantityArrayList.remove(((LinearLayout) addView.getParent()).getChildAt(0));
            }
        });

        et_quantityArrayList.add(quantity);
        et_diameterArrayList.add(diameter);
        addMoreLayout.addView(addView);

        if (addMoreLayout != null)
            if (addMoreLayout.getChildCount() > 0)
                icon_remove.setVisibility(View.VISIBLE);
            else
                icon_remove.setVisibility(View.GONE);
        else
            icon_remove.setVisibility(View.GONE);
    }

    private boolean isValidate() {
        boolean result = true;
        if (et_preferred_brands.getText().length() == 0) {
            et_preferred_brands.requestFocus();
            Toast.makeText(activity, getString(R.string.please_enter_preferred_brands),
                    Toast.LENGTH_SHORT).show();
            result = false;
        } else if (et_grade_required.getText().length() == 0) {
            et_grade_required.requestFocus();
            Toast.makeText(activity, getString(R.string.please_enter_grade_required),
                    Toast.LENGTH_SHORT).show();
            result = false;
        } else if (et_city.getText().length() == 0) {
            et_city.requestFocus();
            Toast.makeText(activity, getString(R.string.please_enter_city),
                    Toast.LENGTH_SHORT).show();
            result = false;
        } else if (et_state.getText().length() == 0) {
            et_state.requestFocus();
            Toast.makeText(activity, getString(R.string.please_enter_state),
                    Toast.LENGTH_SHORT).show();
            result = false;
        } else if (et_budget_amount.getText().length() == 0) {
            et_budget_amount.requestFocus();
            Toast.makeText(activity, getString(R.string.please_enter_budget_amount),
                    Toast.LENGTH_SHORT).show();
            result = false;
        }
        return result;
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.layout_grade_required:
                if (gra.equalsIgnoreCase("1")) {
                    ic_grade_required.setImageResource(R.drawable.toggle_off);
                    gra = "0";
                } else {
                    ic_grade_required.setImageResource(R.drawable.toggle_on);
                    gra = "1";
                }
                break;
            case R.id.btn_add_more:
                addMoreQuantity();
                break;
            case R.id.layout_physical:
                if (phy.equalsIgnoreCase("1")) {
                    ic_physical.setImageResource(R.drawable.toggle_off);
                    phy = "0";
                } else {
                    ic_physical.setImageResource(R.drawable.toggle_on);
                    phy = "1";
                }
                break;
            case R.id.layout_chemical:
                if (che.equalsIgnoreCase("1")) {
                    ic_chemical.setImageResource(R.drawable.toggle_off);
                    che = "0";
                } else {
                    ic_chemical.setImageResource(R.drawable.toggle_on);
                    che = "1";
                }
                break;
            case R.id.txt_random:
                txt_random.setTextColor(Utils.setColor(activity, R.color.white));
                txt_random.setBackgroundColor(Utils.setColor(activity, R.color.transparent));
                txt_standard.setTextColor(Utils.setColor(activity, R.color.dark_grey));
                txt_standard.setBackgroundColor(Utils.setColor(activity, R.color.white));
                break;
            case R.id.txt_standard:
                txt_standard.setTextColor(Utils.setColor(activity, R.color.white));
                txt_standard.setBackgroundColor(Utils.setColor(activity, R.color.transparent));
                txt_random.setTextColor(Utils.setColor(activity, R.color.dark_grey));
                txt_random.setBackgroundColor(Utils.setColor(activity, R.color.white));
                break;
            case R.id.txt_bend:
                txt_bend.setTextColor(Utils.setColor(activity, R.color.white));
                txt_bend.setBackgroundColor(Utils.setColor(activity, R.color.transparent));
                txt_straight.setTextColor(Utils.setColor(activity, R.color.dark_grey));
                txt_straight.setBackgroundColor(Utils.setColor(activity, R.color.white));
                break;
            case R.id.txt_straight:
                txt_straight.setTextColor(Utils.setColor(activity, R.color.white));
                txt_straight.setBackgroundColor(Utils.setColor(activity, R.color.transparent));
                txt_bend.setTextColor(Utils.setColor(activity, R.color.dark_grey));
                txt_bend.setBackgroundColor(Utils.setColor(activity, R.color.white));
                break;
            case R.id.btn_submit:
                String[] qt, dt;
                if (et_quantityArrayList != null)
                    if (et_quantityArrayList.size() > 0) {
                        qt = new String[et_quantityArrayList.size()];
                        for (int i = 0; i < et_quantityArrayList.size(); i++) {
                            qt[i] = et_quantityArrayList.get(i).getText().toString();
                            STLog.e("Quantity Values : ", "" + et_quantityArrayList.get(i).getText().toString());
                        }
                    } else
                        qt = new String[0];
                else
                    qt = new String[0];

                if (et_diameterArrayList != null)
                    if (et_diameterArrayList.size() > 0) {
                        dt = new String[et_diameterArrayList.size()];
                        for (int i = 0; i < et_diameterArrayList.size(); i++) {
                            dt[i] = et_diameterArrayList.get(i).getText().toString();
                            STLog.e("Diameter Values : ", "" + et_diameterArrayList.get(i).getText().toString());
                        }
                    } else
                        dt = new String[0];
                else
                    dt = new String[0];

                ArrayList<Specifications> specificationsArrayList = new ArrayList<>();
                if (default_quantity_layout.getVisibility() == View.VISIBLE) {
                    Specifications specifications = new Specifications();
                    specifications.setSize(txt_diameter.getText().toString());
                    specifications.setQuantity(et_quantity.getText().toString());
                    specificationsArrayList.add(specifications);
                }
                if (qt.length > 0 && dt.length > 0) {
                    for (int i = 0; i < qt.length; i++) {
                        Specifications specifications = new Specifications();
                        if (!Utils.isEmptyString(dt[i]))
                            specifications.setSize(dt[i]);
                        else {
                            Utils.showMessage(activity, activity.getString(R.string.please_enter_diameter));
                            return;
                        }
                        if (!Utils.isEmptyString(qt[i]))
                            specifications.setQuantity(qt[i]);
                        else {
                            Utils.showMessage(activity, activity.getString(R.string.please_enter_quantity));
                            return;
                        }
                        specificationsArrayList.add(specifications);
                    }
                } else if (qt.length > 0 && dt.length == 0) {
                    Utils.showMessage(activity, activity.getString(R.string.please_enter_diameter));
                    return;
                } else if (qt.length == 0 && dt.length > 0) {
                    Utils.showMessage(activity, activity.getString(R.string.please_enter_quantity));
                    return;
                }
                if (isValidate()) {
                    JSONObject jsonObject = new JSONObject();
                    try {
                        jsonObject.put("user_id", Preferences.readString(activity, Preferences.USER_ID, ""));
                        jsonObject.put("physical", phy);
                        jsonObject.put("chemical", che);
                        jsonObject.put("length", "");
                        jsonObject.put("type", "");
                        jsonObject.put("tax_type", taxId);
                        jsonObject.put("required_by_date", et_required_by_date.getText().toString());
                        jsonObject.put("budget", et_budget_amount.getText().toString());
                        jsonObject.put("city", et_city.getText().toString());
                        jsonObject.put("state", stateId);
                        jsonObject.put("grade_required", gradeId);
                        jsonObject.put("preffered_brands", brandId);

                        JSONArray jsonArray = new JSONArray();
                        for (int i = 0; i < specificationsArrayList.size(); i++) {
                            JSONObject jsonObject1 = new JSONObject();
                            jsonObject1.put("size", specificationsArrayList.get(i).getSize());
                            jsonObject1.put("quantity", specificationsArrayList.get(i).getQuantity());
                            jsonArray.put(i, jsonObject1);
                        }

                        jsonObject.put("specification", jsonArray);
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }

                    Utils.showLoading(activity, activity.getString(R.string.please_wait));
                    ModelManager.getInstance().getRequirementManager().addBuyerPost(activity, jsonObject);
                }
                break;
            case R.id.icon_remove:
                default_quantity_layout.setVisibility(View.GONE);
                et_quantity.setText("");
                txt_diameter.setText("");
                break;

            case R.id.et_grade_required:
                showDropDownDialog(2, et_grade_required);
                break;
            case R.id.et_preferred_brands:
                showDropDownDialog(0, et_preferred_brands);
                break;
            case R.id.txt_diameter:
                showDropDownForSteel(txt_diameter);
                break;
            case R.id.et_state:
                showDropDownDialog(3, et_state);
                break;
            case R.id.et_tax_type:
                showDropDownDialog(4, et_tax_type);
                break;
            case R.id.et_required_by_date:
                new DatePickerDialog(activity, date, myCalendar
                        .get(Calendar.YEAR), myCalendar.get(Calendar.MONTH),
                        myCalendar.get(Calendar.DAY_OF_MONTH)).show();
                break;
        }
    }

    // date picker diaSPLog for date Text
    DatePickerDialog.OnDateSetListener date = new DatePickerDialog.OnDateSetListener() {

        @Override
        public void onDateSet(DatePicker view, int year, int monthOfYear,
                              int dayOfMonth) {
            // TODO Auto-generated method stub
            myCalendar.set(Calendar.YEAR, year);
            myCalendar.set(Calendar.MONTH, monthOfYear);
            myCalendar.set(Calendar.DAY_OF_MONTH, dayOfMonth);
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd", Locale.US);

            et_required_by_date.setText(sdf.format(myCalendar.getTime()));
        }

    };

    @Override
    public void onStart() {
        super.onStart();
        EventBus.getDefault().register(this);
    }

    @Override
    public void onStop() {
        super.onStop();
        EventBus.getDefault().unregister(this);

    }

    public void onEventMainThread(String message) {
        if (message.equalsIgnoreCase("GetBrandList True")) {
            Utils.dismissLoading();
            brandsArrayList = ModelManager.getInstance().getCommonDataManager().getBrands(activity, false);
            STLog.e(TAG, "GetBrandList True");
        } else if (message.contains("GetBrandList False")) {
            // showMatchHistoryList();
            Utils.showMessage(activity, activity.getString(R.string.oops_something_went_wrong));
            STLog.e(TAG, "GetBrandList False");
            Utils.dismissLoading();
        } else if (message.equalsIgnoreCase("GetSizeList True")) {
            Utils.dismissLoading();
            steelSizesArrayList = ModelManager.getInstance().getCommonDataManager().getSteelSize(activity, false);
            STLog.e(TAG, "GetSizeList True");
        } else if (message.contains("GetSizeList False")) {
            // showMatchHistoryList();
            Utils.showMessage(activity, activity.getString(R.string.oops_something_went_wrong));
            STLog.e(TAG, "GetSizeList False");
            Utils.dismissLoading();
        } else if (message.equalsIgnoreCase("GetTaxTypeList True")) {
            Utils.dismissLoading();
            taxTypesArrayList = ModelManager.getInstance().getCommonDataManager().getTaxTypes(activity, false);
            STLog.e(TAG, "GetTaxTypeList True");
        } else if (message.contains("GetTaxTypeList False")) {
            // showMatchHistoryList();
            Utils.showMessage(activity, activity.getString(R.string.oops_something_went_wrong));
            STLog.e(TAG, "GetTaxTypeList False");
            Utils.dismissLoading();
        } else if (message.equalsIgnoreCase("GetGradeList True")) {
            Utils.dismissLoading();
            gradesArrayList = ModelManager.getInstance().getCommonDataManager().getGrades(activity, false);
            STLog.e(TAG, "GetGradeList True");
        } else if (message.contains("GetGradeList False")) {
            // showMatchHistoryList();
            Utils.showMessage(activity, activity.getString(R.string.oops_something_went_wrong));
            STLog.e(TAG, "GetGradeList False");
            Utils.dismissLoading();
        } else if (message.equalsIgnoreCase("GetStateList True")) {
            Utils.dismissLoading();
            statesArrayList = ModelManager.getInstance().getCommonDataManager().getStates(activity, false);
            STLog.e(TAG, "GetStateList True");
        } else if (message.contains("GetStateList False")) {
            // showMatchHistoryList();
            Utils.showMessage(activity, activity.getString(R.string.oops_something_went_wrong));
            STLog.e(TAG, "GetStateList False");
            Utils.dismissLoading();
        } else if (message.equalsIgnoreCase("NewRequirementPosted True")) {
            Utils.dismissLoading();
            ((FragmentActivity) activity).getSupportFragmentManager()
                    .popBackStack();
            STLog.e(TAG, "NewRequirementPosted True");
        } else if (message.contains("NewRequirementPosted False")) {
            // showMatchHistoryList();
            Utils.showMessage(activity, activity.getString(R.string.oops_something_went_wrong));
            STLog.e(TAG, "NewRequirementPosted False");
            Utils.dismissLoading();
        }

    }
}
