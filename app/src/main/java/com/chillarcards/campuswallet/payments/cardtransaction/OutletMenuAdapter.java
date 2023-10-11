package com.chillarcards.campuswallet.payments.cardtransaction;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.chillarcards.campuswallet.R;
import com.google.firebase.analytics.FirebaseAnalytics;

import java.util.ArrayList;
import java.util.List;

import com.chillarcards.campuswallet.networkmodels.OutletMenu.Code;

public class OutletMenuAdapter extends RecyclerView.Adapter<OutletMenuAdapter.ViewHolder>{

    List<Code> OutletList=new ArrayList<>();
    Activity activity;
    Context mContext;
    FirebaseAnalytics mFirebaseAnalytics;

    public OutletMenuAdapter(List<Code> outletList, Activity activity, Context mContext,FirebaseAnalytics mFirebaseAnalytics) {
        OutletList = outletList;
        this.activity = activity;
        this.mContext = mContext;
        this.mFirebaseAnalytics = mFirebaseAnalytics;
    }


    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.preorderone_item_layout, parent, false);
        return new OutletMenuAdapter.ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        final int pos=position;
        holder.ItemName.setText(OutletList.get(pos).getOutlet().toString());
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i=new Intent(activity,OutletMenuItemsActivity.class);
                Bundle bundle=new Bundle();
                bundle.putString("OutletTypeID",OutletList.get(pos).getId());
                i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                i.addFlags(Intent.FLAG_ACTIVITY_BROUGHT_TO_FRONT);
                i.putExtras(bundle);
                activity.startActivity(i);
            }
        });

    }

    @Override
    public int getItemCount() {
        return OutletList.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {

        RelativeLayout carditem;
        TextView ItemName;


        public ViewHolder(View itemView) {
            super(itemView);

            ItemName = itemView.findViewById(R.id.itemnameid);
            carditem = itemView.findViewById(R.id.cadviewid);

        }
    }
}
