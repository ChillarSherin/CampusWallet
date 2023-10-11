package com.chillarcards.campuswallet.home;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.chillarcards.campuswallet.R;
import com.google.firebase.analytics.FirebaseAnalytics;

import java.util.ArrayList;


import com.chillarcards.campuswallet.application.PrefManager;
import com.chillarcards.campuswallet.networkmodels.home.Payment;
import com.chillarcards.campuswallet.payments.PaymentsActivity;
import com.chillarcards.campuswallet.payments.Preorder.PreOrderSelection;
import com.chillarcards.campuswallet.payments.Transactions.TransactionSelectionActivity;


public class PaymentsAdapter extends RecyclerView.Adapter<PaymentsAdapter.MyViewHolder> {


    ArrayList<Payment> mData = new ArrayList<>();
    Activity activity;
    Context mContext;
    FirebaseAnalytics mFirebaseAnalytics;
    PrefManager prefManager;

    public PaymentsAdapter(ArrayList<Payment> myDataset, Activity activity, Context mContext, FirebaseAnalytics mFirebaseAnalytics) {
        this.mData = myDataset;
        this.activity = activity;
        this.mContext = mContext;
        this.mFirebaseAnalytics = mFirebaseAnalytics;
        prefManager = new PrefManager(mContext);
    }


    @NonNull
    @Override
    public PaymentsAdapter.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_home_list, parent, false);
        PaymentsAdapter.MyViewHolder vh = new PaymentsAdapter.MyViewHolder(v);
        return vh;
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {

        prefManager.setPreorderAvailable("0");
        switch (mData.get(position).getTransactionCategory()) {
            case "card_recharge":
                holder.txt_module.setText(mContext.getResources().getString(R.string.card_recharge_caps));
                holder.img_module.setImageResource(R.drawable.ic_card_recharge);
                break;
//            case "card-transaction":
//                holder.txt_module.setText("TRANSACTIONS");
//                holder.img_module.setImageResource(R.drawable.ic_transaction);
//                break;
            case "card-transfer":
                holder.txt_module.setText(mContext.getResources().getString(R.string.transactions_caps));
                holder.img_module.setImageResource(R.drawable.ic_transaction);
                break;

            case "fee_payment":
                holder.txt_module.setText(mContext.getResources().getString(R.string.fee_payments_caps));
                holder.img_module.setImageResource(R.drawable.ic_fee_payment);
                break;
            case "miscellaneous_payments":
                holder.txt_module.setText(mContext.getResources().getString(R.string.miscellanious_caps));
                holder.img_module.setImageResource(R.drawable.ic_miscellanious_payment);
                break;
            case "trust_payments":
                holder.txt_module.setText(mContext.getResources().getString(R.string.trust_payments_caps));
                holder.img_module.setImageResource(R.drawable.ic_trust_payment);
                break;
            case "preorder":
                holder.txt_module.setText(mContext.getResources().getString(R.string.pre_order_caps));
                holder.img_module.setImageResource(R.drawable.ic_preorder);
                prefManager.setPreorderAvailable("1");
                break;
            default:
                break;

        }
    }

    @Override
    public int getItemCount() {
        return mData.size();
    }


    public class MyViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        TextView txt_module;
        ImageView img_module;

        public MyViewHolder(View itemView) {
            super(itemView);
            txt_module = itemView.findViewById(R.id.textView6);
            img_module = itemView.findViewById(R.id.imageView8);

            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View view) {
            Log.d("RecyclerView", "onClick：" + mData.get(getAdapterPosition()).getTransactionCategory());

            switch (mData.get(getAdapterPosition()).getTransactionCategory()) {
                case "card_recharge":
                    mFirebaseAnalytics.logEvent(mContext.getResources().getString(R.string.Card_Recharge_Menu_Selected), new Bundle());
                    Intent i = new Intent(activity, PaymentsActivity.class);
                    i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                    Bundle b = new Bundle();
                    b.putString("categoryId", mData.get(getAdapterPosition()).getTransactionCategoryID());
                    b.putString("categoryName", mData.get(getAdapterPosition()).getTransactionCategory());
                    b.putString("fromLowbalance", "0");
                    b.putString("rechargeAmount", "0");
                    i.putExtras(b);
                    activity.startActivity(i);


                    break;
//                case "card-transaction":
                case "card-transfer":
                    mFirebaseAnalytics.logEvent(mContext.getResources().getString(R.string.Transactions_Menu_Selected), new Bundle());
                    Intent i6 = new Intent(activity, TransactionSelectionActivity.class);
                    i6.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                    activity.startActivity(i6);

                    break;
                case "fee_payment":
                    mFirebaseAnalytics.logEvent(mContext.getResources().getString(R.string.Fee_Payment_Menu_Selected), new Bundle());
                    Intent i2 = new Intent(activity, PaymentsActivity.class);
                    i2.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                    Bundle b2 = new Bundle();
                    b2.putString("categoryId", mData.get(getAdapterPosition()).getTransactionCategoryID());
                    b2.putString("categoryName", mData.get(getAdapterPosition()).getTransactionCategory());
                    b2.putString("fromLowbalance", "0");
                    b2.putString("rechargeAmount", "0");
                    i2.putExtras(b2);
                    activity.startActivity(i2);

                    break;
                case "miscellaneous_payments":
                    mFirebaseAnalytics.logEvent(mContext.getResources().getString(R.string.Miscellaneous_Payment_Menu_Selected), new Bundle());
                    Intent i3 = new Intent(activity, PaymentsActivity.class);
                    i3.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                    Bundle b3 = new Bundle();
                    b3.putString("categoryId", mData.get(getAdapterPosition()).getTransactionCategoryID());
                    b3.putString("categoryName", mData.get(getAdapterPosition()).getTransactionCategory());
                    b3.putString("fromLowbalance", "0");
                    b3.putString("rechargeAmount", "0");
                    i3.putExtras(b3);
                    activity.startActivity(i3);

                    break;
                case "trust_payments":
                    mFirebaseAnalytics.logEvent(mContext.getResources().getString(R.string.Trust_Payment_Menu_Selected), new Bundle());
                    Intent i4 = new Intent(activity, PaymentsActivity.class);
                    i4.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                    Bundle b4 = new Bundle();
                    b4.putString("categoryId", mData.get(getAdapterPosition()).getTransactionCategoryID());
                    b4.putString("categoryName", mData.get(getAdapterPosition()).getTransactionCategory());
                    b4.putString("fromLowbalance", "0");
                    b4.putString("rechargeAmount", "0");
                    i4.putExtras(b4);
                    activity.startActivity(i4);

                    break;
                case "preorder":
//                    mFirebaseAnalytics.logEvent(mContext.getResources().getString(R.string.Pre_Order_Menu_Selected),new Bundle());
//                    Intent i5 =new Intent(activity,PreorderDesignActivity.class);
////                    Intent i5 =new Intent(activity,PreOrderSelection.class);
//                    i5.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
//                    Bundle b5= new Bundle();
//                    b5.putString("categoryId",mData.get(getAdapterPosition()).getTransactionCategoryID());
//                    b5.putString("categoryName",mData.get(getAdapterPosition()).getTransactionCategory());
//                    i5.putExtras(b5);
//                    activity.startActivity(i5);
                    //hidden prev flow for now - 14th JUN 2022
                    mFirebaseAnalytics.logEvent(mContext.getResources().getString(R.string.Pre_Order_Search_Menu_Selected), new Bundle());
                    Intent i5 = new Intent(activity, PreOrderSelection.class);
                    i5.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                    Bundle b5 = new Bundle();
                    b5.putString("categoryId", mData.get(getAdapterPosition()).getTransactionCategoryID());
                    b5.putString("categoryName", mData.get(getAdapterPosition()).getTransactionCategory());
                    i5.putExtras(b5);
                    activity.startActivity(i5);
                    break;
                default:

                    break;

            }
        }
    }

}
