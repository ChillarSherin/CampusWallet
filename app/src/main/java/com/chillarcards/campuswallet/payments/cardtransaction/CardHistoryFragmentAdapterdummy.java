package com.chillarcards.campuswallet.payments.cardtransaction;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import androidx.recyclerview.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.chillarcards.campuswallet.R;
import com.google.firebase.analytics.FirebaseAnalytics;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import com.chillarcards.campuswallet.networkmodels.cardtransactionhistory.Transaction;

public class CardHistoryFragmentAdapterdummy extends RecyclerView.Adapter<CardHistoryFragmentAdapterdummy.ViewHolder> {

    List<Transaction> transactionList;
    static Context cntxt;
    int posspec;
    FirebaseAnalytics mFirebaseAnalytics;

    public CardHistoryFragmentAdapterdummy(List<Transaction> transactionList, Context cntxt, int posspec, FirebaseAnalytics mFirebaseAnalytics) {
        this.transactionList = transactionList;
        this.cntxt = cntxt;
        this.posspec = posspec;
        this.mFirebaseAnalytics = mFirebaseAnalytics;
    }


    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.row_payment_transaction_history_dummy, parent, false);
        return new ViewHolder(v);

    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, final int position) {

        holder.RefundSealLL.setVisibility(View.GONE);
        holder.TransactionModeNameTV.setText(transactionList.get(position).getTransactionType());
        holder.PayHistoryAmountTV.setText(cntxt.getResources().getString(R.string.indian_rupee_symbol)+transactionList.get(position).getTransactionAmount());
        holder.PayHistoryPreviousBalanceTV.setText(cntxt.getResources().getString(R.string.indian_rupee_symbol)+transactionList.get(position).getPrev_balance());
        holder.PayHistoryCurrentBalanceTV.setText(cntxt.getResources().getString(R.string.indian_rupee_symbol)+transactionList.get(position).getCurrent_balance());

        SimpleDateFormat formatter=new SimpleDateFormat("dd-MM-yyyy hh:mm aaa");
        SimpleDateFormat formatter1=new SimpleDateFormat("dd-MM-yyyy HH:mm:ss");
        String dateTime = "";
        try {
            Date date=formatter1.parse(transactionList.get(position).getTransactionTime());
            dateTime = formatter.format(date);
        } catch (ParseException e) {
            e.printStackTrace();
            //System.out.println("DATE ERROR : "+e);
        }

        holder.PaymenttransactionDateTV.setText(dateTime.toString());
        if (transactionList.get(position).getStatus()==0
                || transactionList.get(position).getTransactionType().equalsIgnoreCase("REFUND"))
        {
            holder.RefundSealLL.setVisibility(View.VISIBLE);
            holder.RefundSealLL.setBackground(cntxt.getResources().getDrawable(R.drawable.ic_refund_one));
        }
        else {
            holder.RefundSealLL.setVisibility(View.GONE);
        }
        if (transactionList.get(position).getExtra_details_flag()!=0)
        {
            holder.eyeIV.setVisibility(View.VISIBLE);
        }
        else {
            holder.eyeIV.setVisibility(View.GONE);
        }
        switch (transactionList.get(position).getTransactionType())
        {
            case "RECHARGE":
            {
                holder.transactionModeIV.setBackground(cntxt.getDrawable(R.drawable.ic_card_recharge_grey));
                break;
            }
            case "REFUND":
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
                    if (transactionList.get(position).getTransactionType().equalsIgnoreCase("REFUND")
                            || transactionList.get(position).getStatus()==0)
                    {
                        bundle.putInt("status",0);
                    }
                    else {
                        bundle.putInt("status",1);
                    }

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
        TextView PayHistoryCurrentBalanceTV,PaymenttransactionDateTV,PayHistoryAmountTV
                ,TransactionModeNameTV,PayHistoryPreviousBalanceTV,eyeIV;
        ImageView transactionModeIV;
        LinearLayout RefundSealLL;

        public ViewHolder(View itemView) {
            super(itemView);
            TransactionModeNameTV=itemView.findViewById(R.id.TransactionModeNameTV);
            PayHistoryAmountTV=itemView.findViewById(R.id.PayHistoryAmountTV);
            PaymenttransactionDateTV=itemView.findViewById(R.id.PaymenttransactionDateTV);
            transactionModeIV=itemView.findViewById(R.id.transactionModeIV);
            PayHistoryPreviousBalanceTV=itemView.findViewById(R.id.PayHistoryPreviousBalanceTV);
            PayHistoryCurrentBalanceTV=itemView.findViewById(R.id.PayHistoryCurrentBalanceTV);
            RefundSealLL=itemView.findViewById(R.id.RefundSealLL);
            eyeIV=itemView.findViewById(R.id.eyeIV);

        }


    }


}

