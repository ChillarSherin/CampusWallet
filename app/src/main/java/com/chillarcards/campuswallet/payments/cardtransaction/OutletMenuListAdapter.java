package com.chillarcards.campuswallet.payments.cardtransaction;

import android.app.Activity;
import android.content.Context;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.chillarcards.campuswallet.R;
import com.google.firebase.analytics.FirebaseAnalytics;

import java.util.ArrayList;
import java.util.List;

import com.chillarcards.campuswallet.networkmodels.TransactionMenuItems.Code;


public class OutletMenuListAdapter extends RecyclerView.Adapter<OutletMenuListAdapter.ViewHolder>{

    List<Code> OutletList=new ArrayList<>();
    Activity activity;
    Context mContext;
    FirebaseAnalytics mFirebaseAnalytics;

    public OutletMenuListAdapter(List<Code> outletList, Activity activity, Context mContext, FirebaseAnalytics mFirebaseAnalytics) {
        OutletList = outletList;
        this.activity = activity;
        this.mContext = mContext;
        this.mFirebaseAnalytics = mFirebaseAnalytics;
    }


    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.preordercatlist_item_layout, parent, false);
        return new OutletMenuListAdapter.ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.ItemName.setText(OutletList.get(position).getItemName().toString());
        holder.priceid.setText(OutletList.get(position).getPrice().toString());


    }

    @Override
    public int getItemCount() {
        return OutletList.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {

//        RelativeLayout carditem;
        TextView ItemName,priceid;


        public ViewHolder(View itemView) {
            super(itemView);

            ItemName = itemView.findViewById(R.id.itemnameid);
            priceid = itemView.findViewById(R.id.priceid);
//            carditem = itemView.findViewById(R.id.cadviewid);

        }
    }
}
