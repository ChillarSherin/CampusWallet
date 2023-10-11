package com.chillarcards.campuswallet.Payment.History;

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

import com.chillarcards.campuswallet.networkmodels.OnlineTransactionHistory.Transaction;
import com.chillarcards.campuswallet.payments.PaymentReceiptActivity;

public class HistoryFragmentAdapter extends RecyclerView.Adapter<HistoryFragmentAdapter.ViewHolder> {

    List<Transaction> transactionList;
    static Context cntxt;
    int tabpos;
    FirebaseAnalytics mFirebaseAnalytics;

    public HistoryFragmentAdapter(List<Transaction> transactionList, Context cntxt,int tabpos,FirebaseAnalytics mFirebaseAnalytics) {
        this.transactionList = transactionList;
        this.cntxt = cntxt;
        this.tabpos=tabpos;
        this.mFirebaseAnalytics=mFirebaseAnalytics;
    }


    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.row_pay_recharge_transaction_history_dummy, parent, false);
        return new ViewHolder(v);

    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, final int position) {

        if (transactionList.get(position).getStatus().equalsIgnoreCase("Success"))
        {
            holder.PayTransactionStatusTV.setTextColor(ContextCompat.getColor(cntxt, R.color.green));
        }
        else {
            holder.PayTransactionStatusTV.setTextColor(ContextCompat.getColor(cntxt, R.color.red));
        }
        holder.PayTransactionStatusTV.setText(transactionList.get(position).getStatus());
        holder.TransactionModeNameTV.setText(transactionList.get(position).getTransactionCategoryKey());
        holder.PayHistoryAmountTV.setText(cntxt.getResources().getString(R.string.indian_rupee_symbol)+transactionList.get(position).getAmount());
        holder.PayHistoryTransactionIDTV.setText(transactionList.get(position).getTransactionID());
        holder.PaymenttransactionDateTV.setText(transactionList.get(position).getOnlineTransactionCreatedOn());
        switch (transactionList.get(position).getTransactionCategoryKey())
        {
            case "card_recharge":
            {
                holder.transactionModeIV.setBackground(cntxt.getDrawable(R.drawable.ic_card_recharge_grey));
                holder.TransactionModeNameTV.setText(cntxt.getResources().getString(R.string.card_recharge_caps));
                break;
            }
            case "fee_payment":
            {
                holder.transactionModeIV.setBackground(cntxt.getDrawable(R.drawable.ic_fee_payment_grey));
                holder.TransactionModeNameTV.setText(cntxt.getResources().getString(R.string.fee_payments_caps));
                break;
            }
            case "miscellaneous_payments":
            {
                holder.transactionModeIV.setBackground(cntxt.getDrawable(R.drawable.ic_miscellaneous_grey));
                holder.TransactionModeNameTV.setText(cntxt.getResources().getString(R.string.miscellanious_caps));
                break;
            }
            case "trust_payments":
            {
                holder.transactionModeIV.setBackground(cntxt.getDrawable(R.drawable.ic_trust_payment_grey));
                holder.TransactionModeNameTV.setText(cntxt.getResources().getString(R.string.trust_payments_caps));
                break;
            }
            default:
            {
                holder.transactionModeIV.setBackground(cntxt.getDrawable(R.drawable.ic_card_recharge_grey));
                holder.TransactionModeNameTV.setText(transactionList.get(position).getTransactionCategoryKey());
                break;
            }
        }

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Bundle firebaseBundle=new Bundle();
                firebaseBundle.putString("Status",transactionList.get(position).getStatus());
                mFirebaseAnalytics.logEvent(cntxt.getResources().getString(R.string.Payment_History_Item_Clicked),firebaseBundle);
              //  if (transactionList.get(position).getStatus().equalsIgnoreCase("Success"))
             //   {
                    Intent i = new Intent(cntxt.getApplicationContext(),PaymentReceiptActivity.class);
                    Bundle b = new Bundle();
                    b.putString("status",transactionList.get(position).getStatus());
                    b.putString("onlineTransactionID",transactionList.get(position).getTransactionID());
                    b.putString("transaction_category",transactionList.get(position).getTransactionCategoryKey());
                    b.putString("amount",transactionList.get(position).getAmount());
                    b.putString("from","0");
                    b.putInt("tabpos",tabpos);
                    i.putExtras(b);
                    cntxt.startActivity(i);
              //  }
            }
        });


    }


    @Override
    public int getItemCount() {
        return transactionList.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        TextView PayHistoryTransactionIDTV,PaymenttransactionDateTV,PayHistoryAmountTV,TransactionModeNameTV,PayTransactionStatusTV;
        ImageView transactionModeIV;

        public ViewHolder(View itemView) {
            super(itemView);
            PayTransactionStatusTV=itemView.findViewById(R.id.PayTransactionStatusTV);
            TransactionModeNameTV=itemView.findViewById(R.id.TransactionModeNameTV);
            PayHistoryAmountTV=itemView.findViewById(R.id.PayHistoryAmountTV);
            PayHistoryTransactionIDTV=itemView.findViewById(R.id.PayHistoryTransactionIDTV);
            PaymenttransactionDateTV=itemView.findViewById(R.id.PaymenttransactionDateTV);
            transactionModeIV=itemView.findViewById(R.id.transactionModeIV);

            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View view) {
//            Intent i = new Intent(cntxt,PaymentReceiptActivity.class);
//            i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
//            cntxt.startActivity(i);
        }
    }


}

