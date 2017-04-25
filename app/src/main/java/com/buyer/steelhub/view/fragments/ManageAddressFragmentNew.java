package com.buyer.steelhub.view.fragments;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.RectF;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.content.LocalBroadcastManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.helper.ItemTouchHelper;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;

import com.buyer.steelhub.R;
import com.buyer.steelhub.customUi.MyButton;
import com.buyer.steelhub.customUi.MyTextView;
import com.buyer.steelhub.model.Address;
import com.buyer.steelhub.model.ModelManager;
import com.buyer.steelhub.utility.STLog;
import com.buyer.steelhub.utility.Utils;
import com.buyer.steelhub.view.adapter.ManageAddressAdapter;

import java.util.ArrayList;

import de.greenrobot.event.EventBus;

/**
 * Created by arun.sharma on 3/27/2017.
 */

public class ManageAddressFragmentNew extends Fragment implements View.OnClickListener {
    private String TAG = ManageAddressFragmentNew.class.getSimpleName();
    private Activity activity;
    private ArrayList<Address> addressArrayList;
    //    private ListView listView;
    private MyTextView txt_billing_address, txt_shipping_address;
    private String type = "";
    private RecyclerView recyclerView;
    private View view;
    private Paint p = new Paint();
    private int edit_position;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        this.activity = super.getActivity();
        Intent intent = new Intent("Header");
        intent.putExtra("message", activity.getString(R.string.address));

        LocalBroadcastManager.getInstance(activity).sendBroadcast(intent);
        View rootView = inflater.inflate(R.layout.fragment_manage_address, container, false);

//        listView = (ListView) rootView.findViewById(R.id.address_list);
        recyclerView = (RecyclerView) rootView.findViewById(R.id.address_list);
        recyclerView.setHasFixedSize(true);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(activity);
        recyclerView.setLayoutManager(layoutManager);

        txt_billing_address = (MyTextView) rootView.findViewById(R.id.txt_billing_address);
        txt_shipping_address = (MyTextView) rootView.findViewById(R.id.txt_shipping_address);
        MyButton btn_place_order = (MyButton) rootView.findViewById(R.id.btn_place_order);
        btn_place_order.setVisibility(View.GONE);

        ImageView img_add = (ImageView) activity.findViewById(R.id.img_add);
        img_add.setVisibility(View.VISIBLE);
        img_add.setImageResource(R.drawable.new_requirement);
        img_add.setOnClickListener(this);

        txt_billing_address.setOnClickListener(this);
        txt_shipping_address.setOnClickListener(this);

//        listView.setOnLongClickListener(new View.OnLongClickListener() {
//            @Override
//            public boolean onLongClick(View view) {
//                showDialog();
//                return false;
//            }
//        });

        type = "billing";

        getAddresses();
        return rootView;
    }

    private void getAddresses() {
        Utils.showLoading(activity, activity.getString(R.string.please_wait));
        ModelManager.getInstance().getAddressManager().getAddresses(activity, type, true);
    }

    private void setData() {
        ArrayList<Address> addresses = new ArrayList<>();
        if (addressArrayList.size() > 0)
            for (int i = 0; i < addressArrayList.size(); i++)
                if (addressArrayList.get(i).getAddressType().equalsIgnoreCase(type))
                    addresses.add(addressArrayList.get(i));

        if (addresses.size() > 0) {
            ManageAddressAdapter addressAdapter = new ManageAddressAdapter(addresses);
            recyclerView.setAdapter(addressAdapter);
            addressAdapter.notifyDataSetChanged();
        } else {
            recyclerView.setAdapter(null);
            Utils.showMessage(activity, activity.getString(R.string.no_record_found));
        }
    }
    private void initSwipe(){
        ItemTouchHelper.SimpleCallback simpleItemTouchCallback = new ItemTouchHelper.SimpleCallback(0, ItemTouchHelper.LEFT | ItemTouchHelper.RIGHT) {

            @Override
            public boolean onMove(RecyclerView recyclerView, RecyclerView.ViewHolder viewHolder, RecyclerView.ViewHolder target) {
                return false;
            }

            @Override
            public void onSwiped(RecyclerView.ViewHolder viewHolder, int direction) {
                int position = viewHolder.getAdapterPosition();

                if (direction == ItemTouchHelper.LEFT){
//                    adapter.removeItem(position);
                    showAlert();
                } else {
                    Fragment fragment = new AddNewAddressFragment();
                    Bundle bundle = new Bundle();
                    bundle.putString("type", type);
                    bundle.putString("action", "edit");
                    bundle.putString("id", addressArrayList.get(position).getId());
                    fragment.setArguments(bundle);
                    FragmentManager fragmentManager = ((FragmentActivity) activity).getSupportFragmentManager();
                    FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                    fragmentTransaction.replace(R.id.container_body, fragment, "AddNewAddressFragment");
                    fragmentTransaction.addToBackStack("AddNewAddressFragment");
                    fragmentTransaction.commit();
                }
            }

            @Override
            public void onChildDraw(Canvas c, RecyclerView recyclerView, RecyclerView.ViewHolder viewHolder, float dX, float dY, int actionState, boolean isCurrentlyActive) {

                Bitmap icon;
                if(actionState == ItemTouchHelper.ACTION_STATE_SWIPE){

                    View itemView = viewHolder.itemView;
                    float height = (float) itemView.getBottom() - (float) itemView.getTop();
                    float width = height / 3;

                    if(dX > 0){
                        p.setColor(Color.parseColor("#388E3C"));
                        RectF background = new RectF((float) itemView.getLeft(), (float) itemView.getTop(), dX,(float) itemView.getBottom());
                        c.drawRect(background,p);
                        icon = BitmapFactory.decodeResource(getResources(), R.drawable.ic_edit_white);
                        RectF icon_dest = new RectF((float) itemView.getLeft() + width ,(float) itemView.getTop() + width,(float) itemView.getLeft()+ 2*width,(float)itemView.getBottom() - width);
                        c.drawBitmap(icon,null,icon_dest,p);
                    } else {
                        p.setColor(Color.parseColor("#D32F2F"));
                        RectF background = new RectF((float) itemView.getRight() + dX, (float) itemView.getTop(),(float) itemView.getRight(), (float) itemView.getBottom());
                        c.drawRect(background,p);
                        icon = BitmapFactory.decodeResource(getResources(), R.drawable.ic_delete_white);
                        RectF icon_dest = new RectF((float) itemView.getRight() - 2*width ,(float) itemView.getTop() + width,(float) itemView.getRight() - width,(float)itemView.getBottom() - width);
                        c.drawBitmap(icon,null,icon_dest,p);
                    }
                }
                super.onChildDraw(c, recyclerView, viewHolder, dX, dY, actionState, isCurrentlyActive);
            }
        };
        ItemTouchHelper itemTouchHelper = new ItemTouchHelper(simpleItemTouchCallback);
        itemTouchHelper.attachToRecyclerView(recyclerView);
    }
    private void removeView(){
        if(view.getParent()!=null) {
            ((ViewGroup) view.getParent()).removeView(view);
        }
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.txt_billing_address:
                type = "billing";
                getAddresses();
                txt_billing_address.setTextColor(Utils.setColor(activity, R.color.white));
                txt_billing_address.setBackgroundColor(Utils.setColor(activity, R.color.transparent));
                txt_shipping_address.setTextColor(Utils.setColor(activity, R.color.sign_up_start_color));
                txt_shipping_address.setBackgroundColor(Utils.setColor(activity, R.color.white));
                break;
            case R.id.txt_shipping_address:
                type = "shipping";
                getAddresses();
                txt_billing_address.setTextColor(Utils.setColor(activity, R.color.sign_up_start_color));
                txt_billing_address.setBackgroundColor(Utils.setColor(activity, R.color.white));
                txt_shipping_address.setTextColor(Utils.setColor(activity, R.color.white));
                txt_shipping_address.setBackgroundColor(Utils.setColor(activity, R.color.transparent));
                break;
            case R.id.img_add:
                Fragment fragment = new AddNewAddressFragment();
                Bundle bundle = new Bundle();
                bundle.putString("type", type);
                bundle.putString("action", "add");
                fragment.setArguments(bundle);
                FragmentManager fragmentManager = ((FragmentActivity) activity).getSupportFragmentManager();
                FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                fragmentTransaction.replace(R.id.container_body, fragment, "AddNewAddressFragment");
                fragmentTransaction.addToBackStack("AddNewAddressFragment");
                fragmentTransaction.commit();
                break;
        }
    }

    private void showDialog() {
        AlertDialog.Builder builderSingle = new AlertDialog.Builder(activity);
        builderSingle.setTitle("Choose Action");

        final ArrayAdapter<String> arrayAdapter = new ArrayAdapter<>(
                activity,
                android.R.layout.simple_list_item_1);
        arrayAdapter.add("Edit");
        arrayAdapter.add("Delete");

        builderSingle.setAdapter(
                arrayAdapter,
                new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        String strName = arrayAdapter.getItem(which);
                        if (which == 0) {
                            Fragment fragment = new AddNewAddressFragment();
                            Bundle bundle = new Bundle();
                            bundle.putString("type", type);
                            bundle.putString("action", "edit");
                            fragment.setArguments(bundle);
                            FragmentManager fragmentManager = ((FragmentActivity) activity).getSupportFragmentManager();
                            FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                            fragmentTransaction.replace(R.id.container_body, fragment, "AddNewAddressFragment");
                            fragmentTransaction.addToBackStack("AddNewAddressFragment");
                            fragmentTransaction.commit();
                        } else
                            showAlert();
                    }
                });
        builderSingle.show();
    }

    private void showAlert() {
        activity.runOnUiThread(new Runnable() {
            @Override
            public void run() {
                AlertDialog.Builder builder = new AlertDialog.Builder(activity);
                builder.setMessage(activity.getString(R.string.action_alert))
                        .setCancelable(false)
                        .setPositiveButton(
                                activity.getString(R.string.ok_label),
                                new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialog,
                                                        int id) {

                                        dialog.cancel();
                                    }
                                })
                        .setNegativeButton(activity.getString(R.string.cancel),
                                new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialog,
                                                        int id) {
                                        dialog.cancel();
                                    }
                                });
                AlertDialog alert = builder.create();
                alert.show();
            }
        });
    }

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
        if (message.equalsIgnoreCase("AddressList True")) {
            Utils.dismissLoading();
            addressArrayList = ModelManager.getInstance().getAddressManager().getAddresses(activity, type, false);
            if (addressArrayList != null)
                setData();
            else
                Utils.showMessage(activity, activity.getString(R.string.no_record_found));
            STLog.e(TAG, "AddressList True");
        } else if (message.contains("AddressList False")) {
            // showMatchHistoryList();
            Utils.showMessage(activity, activity.getString(R.string.oops_something_went_wrong));
            STLog.e(TAG, "AddressList False");
            Utils.dismissLoading();
        } else if (message.contains("AddressList Network Error")) {
            // showMatchHistoryList();
            Utils.showMessage(activity, activity.getString(R.string.oops_something_went_wrong));
            STLog.e(TAG, "AddressList Network Error");
            Utils.dismissLoading();
        }

    }
}
