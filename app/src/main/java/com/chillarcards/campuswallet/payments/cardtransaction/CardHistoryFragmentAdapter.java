package com.chillarcards.campuswallet.payments.cardtransaction;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.chillarcards.campuswallet.R;
import com.google.firebase.analytics.FirebaseAnalytics;

import java.util.List;


import com.chillarcards.campuswallet.networkmodels.cardtransactionhistory.Transaction;

public class CardHistoryFragmentAdapter extends RecyclerView.Adapter<CardHistoryFragmentAdapter.ViewHolder> {

    List<Transaction> transactionList;
    static Context cntxt;
    int posspec;
    FirebaseAnalytics mFirebaseAnalytics;

    public CardHistoryFragmentAdapter(List<Transaction> transactionList, Context cntxt,int posspec,FirebaseAnalytics mFirebaseAnalytics) {
        this.transactionList = transactionList;
        this.cntxt = cntxt;
        this.posspec = posspec;
        this.mFirebaseAnalytics = mFirebaseAnalytics;
    }


    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.row_payment_transaction_history, parent, false);
        return new ViewHolder(v);

    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, final int position) {


        holder.TransactionModeNameTV.setText(transactionList.get(position).getTransactionType());
        holder.PayHistoryAmountTV.setText(cntxt.getResources().getString(R.string.indian_rupee_symbol)+transactionList.get(position).getTransactionAmount());
        holder.PaymenttransactionDateTV.setText(transactionList.get(position).getTransactionTime());
        switch (transactionList.get(position).getTransactionType())
        {
            case "RECHARGE":
            {
                holder.transactionModeIV.setBackground(cntxt.getDrawable(R.drawable.ic_card_recharge_grey));
                break;
            }
            case "fee_payment":
            {
                holder.transactionModeIV.setBackground(cntxt.getDrawable(R.drawable.ic_fee_payment_grey));
                break;
            }
            case "miscellaneous_payments":
            {
                holder.transactionModeIV.setBackground(cntxt.getDrawable(R.drawable.ic_miscellaneous_grey));
                break;
            }
            case "trust_payments":
            {
                holder.transactionModeIV.setBackground(cntxt.getDrawable(R.drawable.ic_trust_payment_grey));
                break;
            }
            default:
            {
                holder.transactionModeIV.setBackground(cntxt.getDrawable(R.drawable.ic_miscellaneous_grey));
                break;
            }
        }
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Bundle firebaseBundle=new Bundle();
                firebaseBundle.putString("Outlet_Name",transactionList.get(position).getTransactionType());
                mFirebaseAnalytics.logEvent(cntxt.getResources().getString(R.string.Transaction_History_Item_Clicked),firebaseBundle);
                if (transactionList.get(position).getExtra_details_flag()!=0)
                {
                    Intent i = new Intent(cntxt,HistoryReceiptActivity.class);
                    //System.out.println("transID : "+ transactionList.get(position).getTransactionID());
                    i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                    Bundle bundle=new Bundle();
                    bundle.putString("OutletName",transactionList.get(position).getTransactionType());
                    bundle.putString("transID",transactionList.get(position).getTransactionID());
                    bundle.putInt("posspec",posspec);
                    i.putExtras(bundle);
                    cntxt.startActivity(i);
                }


            }
        });



    }


    @Override
    public int getItemCount() {
        return transactionList.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder{
        TextView PayHistoryTransactionIDTV,PaymenttransactionDateTV,PayHistoryAmountTV,TransactionModeNameTV,PayTransactionStatusTV;
        ImageView transactionModeIV;

        public ViewHolder(View itemView) {
            super(itemView);
            TransactionModeNameTV=itemView.findViewById(R.id.TransactionModeNameTV);
            PayHistoryAmountTV=itemView.findViewById(R.id.PayHistoryAmountTV);
            PaymenttransactionDateTV=itemView.findViewById(R.id.PaymenttransactionDateTV);
            transactionModeIV=itemView.findViewById(R.id.transactionModeIV);

        }


    }


}

