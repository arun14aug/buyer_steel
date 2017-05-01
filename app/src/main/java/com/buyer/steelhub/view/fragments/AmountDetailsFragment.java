package com.buyer.steelhub.view.fragments;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.content.LocalBroadcastManager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import com.buyer.steelhub.R;
import com.buyer.steelhub.model.BargainUnit;
import com.buyer.steelhub.model.ModelManager;
import com.buyer.steelhub.model.Requirements;
import com.buyer.steelhub.model.Response;
import com.buyer.steelhub.view.adapter.AmountDetailAdapter;

import java.util.ArrayList;

/**
 * Created by arun.sharma on 5/1/2017.
 */

public class AmountDetailsFragment extends Fragment {

    private String TAG = AmountDetailsFragment.class.getSimpleName();
    private ArrayList<BargainUnit> bargainUnitArrayList;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        Activity activity = super.getActivity();
        Intent intent = new Intent("Header");
        intent.putExtra("message", activity.getString(R.string.requirement_details));

        LocalBroadcastManager.getInstance(activity).sendBroadcast(intent);
        View rootView = inflater.inflate(R.layout.fragment_amount_details, container, false);

        String requirement_id = "", seller_id = "";
        try {
            if (getArguments() != null) {
                Bundle bundle = getArguments();
                requirement_id = bundle.getString("requirement_id");
                seller_id = bundle.getString("seller_id");
            }

        } catch (Exception ex) {
            Log.e(TAG, ex.toString());
            requirement_id = "";
            seller_id = "";
        }

        ArrayList<Requirements> requirementsArrayList = ModelManager.getInstance().getRequirementManager().getRequirements(activity, false);
        for (int i = 0; i < requirementsArrayList.size(); i++) {
            if (requirement_id.equalsIgnoreCase(requirementsArrayList.get(i).getRequirement_id())) {
                ArrayList<Response> responses = requirementsArrayList.get(i).getResponseArrayList();
                if (responses != null)
                    if (responses.size() > 0) {
                        for (int j = 0; j < responses.size(); j++) {
                            if (seller_id.equalsIgnoreCase(responses.get(j).getSeller_id())) {
                                bargainUnitArrayList = responses.get(j).getBargainUnitArrayList();
                                break;
                            }
                        }
                    }
                break;
            }
        }

        ListView listView = (ListView) rootView.findViewById(R.id.amount_list);
        AmountDetailAdapter amountDetailAdapter = new AmountDetailAdapter(activity, bargainUnitArrayList);
        listView.setAdapter(amountDetailAdapter);
        amountDetailAdapter.notifyDataSetChanged();
        return rootView;
    }
}
