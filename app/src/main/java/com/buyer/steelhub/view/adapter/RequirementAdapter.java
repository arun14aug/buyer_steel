package com.buyer.steelhub.view.adapter;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;

import com.buyer.steelhub.R;
import com.buyer.steelhub.customUi.MyTextView;
import com.buyer.steelhub.model.ModelManager;
import com.buyer.steelhub.model.Requirements;
import com.buyer.steelhub.model.States;
import com.buyer.steelhub.utility.Utils;

import java.util.ArrayList;

public class RequirementAdapter extends BaseAdapter {
    private ArrayList<Requirements> list;
    private Activity activity;

    public RequirementAdapter(final Activity context,
                              ArrayList<Requirements> list) {
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

            viewHolder.txt_city.setText(Utils.firstLetterCap(list.get(position).getCity()));
//            ArrayList<States> statesArrayList = ModelManager.getInstance().getCommonDataManager().getStates(activity, false);
//            String state = "";
//            if (statesArrayList != null) {
//                for (States states :
//                        statesArrayList) {
//                    if (states.getName().equalsIgnoreCase(list.get(position).getState())) {
//                        state = states.getCode();
//                        break;
//                    }
//                }
//            }
//            if (!TextUtils.isEmpty(state))
//                state = list.get(position).getState();
//            viewHolder.txt_state.setText(Utils.firstLetterCap(state));
            viewHolder.txt_state.setText(Utils.firstLetterCap(list.get(position).getState()));
            viewHolder.txt_budget.setText(list.get(position).getBudget() + "/- Rs");

            viewHolder.txt_date.setText(list.get(position).getRequired_by_date());
            String flag = list.get(position).getFlag();
            if (flag == null)
                flag = "";
            if (flag.equalsIgnoreCase("1"))
                viewHolder.color_view.setBackgroundColor(Utils.setColor(activity, R.color.red_color));
            else
                viewHolder.color_view.setBackgroundColor(Utils.setColor(activity, R.color.k_blue_color));

//            viewHolder.img_action.setOnClickListener(new View.OnClickListener() {
//                @Override
//                public void onClick(View view) {
//                    Fragment fragment = new NewRequirementFragment();
//                    Bundle bundle = new Bundle();
//                    bundle.putString("type", "edit");
//                    bundle.putString("id", list.get(position).getRequirement_id());
//                    FragmentManager fragmentManager = ((FragmentActivity) activity).getSupportFragmentManager();
//                    FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
//                    fragmentTransaction.replace(R.id.container_body, fragment, "NewRequirementFragment");
//                    fragmentTransaction.addToBackStack("NewRequirementFragment");
//                    fragmentTransaction.commit();
//
//                }
//            });
        } catch (Exception ex) {
            ex.printStackTrace();
        }

        return v;
    }

    class CompleteListViewHolder {
        public MyTextView txt_city, txt_state, txt_budget, txt_date;
        public ImageView img_action;
        public View color_view;

        public CompleteListViewHolder(View convertview) {
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
