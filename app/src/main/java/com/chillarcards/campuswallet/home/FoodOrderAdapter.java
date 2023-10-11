package com.chillarcards.campuswallet.home;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.chillarcards.campuswallet.R;
import com.google.firebase.analytics.FirebaseAnalytics;

import java.util.ArrayList;
import java.util.List;


import com.chillarcards.campuswallet.NewPreOrder.MyOrders.MyOrdersActivity;
import com.chillarcards.campuswallet.NewPreOrder.PreOrderTabedActivity;


public class FoodOrderAdapter extends RecyclerView.Adapter<FoodOrderAdapter.MyViewHolder> {


    List<String> mData =new ArrayList<>();
    Activity activity;
    Context mContext;
    FirebaseAnalytics mFirebaseAnalytics;

    public FoodOrderAdapter(List<String> myDataset, Activity activity, Context mContext, FirebaseAnalytics mFirebaseAnalytics) {
        this.mData = myDataset;
        this.activity=activity;
        this.mContext=mContext;
        this.mFirebaseAnalytics=mFirebaseAnalytics;
    }


    @NonNull
    @Override
    public FoodOrderAdapter.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v= LayoutInflater.from(parent.getContext()).inflate(R.layout.item_home_list, parent, false);
        FoodOrderAdapter.MyViewHolder vh = new FoodOrderAdapter.MyViewHolder(v);
        return vh;
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        holder.txt_module.setText(mData.get(position));
        final int pos=position;

        switch (mData.get(position)){


            case "pre-order":
                holder.txt_module.setText(mContext.getResources().getString(R.string.pre_order_caps));
                holder.img_module.setImageResource(R.drawable.ic_preorder);
                break;
            case "order":
                holder.txt_module.setText(mContext.getResources().getString(R.string.normal_order_caps));
                holder.img_module.setImageResource(R.drawable.ic_preorder);
                break;
            case "my-order":
                holder.txt_module.setText(mContext.getResources().getString(R.string.my_order_caps));
                holder.img_module.setImageResource(R.drawable.ic_leave_request);
                break;

        }
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                switch (mData.get(pos)){

                    case "pre-order":
                        mFirebaseAnalytics.logEvent(mContext.getResources().getString(R.string.preorder_Menu_Selected),new Bundle());
                        Intent preprder=new Intent(activity, PreOrderTabedActivity.class);
                        preprder.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                        activity.startActivity(preprder);

                        break;
                    case "order":
                        mFirebaseAnalytics.logEvent(mContext.getResources().getString(R.string.order_Menu_Selected),new Bundle());
                        Intent order=new Intent(activity, PreOrderTabedActivity.class);
                        order.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                        activity.startActivity(order);

                        break;
                    case "my-order":
                        mFirebaseAnalytics.logEvent(mContext.getResources().getString(R.string.Myorder_Menu_Selected),new Bundle());
                        Intent timetable=new Intent(activity, MyOrdersActivity.class);
                        timetable.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                        activity.startActivity(timetable);

                        break;


                }
            }
        });

    }

    @Override
    public int getItemCount() {
        return mData.size();
    }


    public class MyViewHolder extends RecyclerView.ViewHolder {
        TextView txt_module;
        ImageView img_module;
        ConstraintLayout ItemhomeCL;
        public MyViewHolder(View itemView) {
            super(itemView);
            txt_module = itemView.findViewById(R.id.textView6);
            img_module = itemView.findViewById(R.id.imageView8);
            ItemhomeCL = itemView.findViewById(R.id.ItemhomeCL);
        }
    }

}
