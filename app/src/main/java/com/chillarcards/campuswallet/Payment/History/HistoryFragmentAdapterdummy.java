package com.chillarcards.campuswallet.Payment.History;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.RecyclerView;
import android.text.SpannableString;
import android.text.style.ForegroundColorSpan;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.chillarcards.campuswallet.R;
import com.google.firebase.analytics.FirebaseAnalytics;

import java.util.List;

import com.chillarcards.campuswallet.networkmodels.FeePaymentReport.PaymentDetail;

public class HistoryFragmentAdapterdummy extends RecyclerView.Adapter<HistoryFragmentAdapterdummy.ViewHolder> {

    List<PaymentDetail> transactionList;
    static Context cntxt;
    int tabpos;
    FirebaseAnalytics mFirebaseAnalytics;

    public HistoryFragmentAdapterdummy(List<PaymentDetail> transactionList, Context cntxt, int tabpos, FirebaseAnalytics mFirebaseAnalytics) {
        this.transactionList = transactionList;
        this.cntxt = cntxt;
        this.tabpos=tabpos;
        this.mFirebaseAnalytics=mFirebaseAnalytics;
    }


    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.row_pay_fee_pay_transaction_history, parent, false);
        return new ViewHolder(v);

    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, final int position) {
        holder.PayTransactionStatusTV.setVisibility(View.GONE);
        holder.TransactionModeNameTV.setText(transactionList.get(position).getName());
        holder.PayHistoryAmountTV.setText(cntxt.getResources().getString(R.string.indian_rupee_symbol) + transactionList.get(position).getAmount());
        if (transactionList.get(position).getStatus().equalsIgnoreCase("1"))
        {
            holder.PayedOnTV.setTextColor(cntxt.getResources().getColor(R.color.green));
        }
        else {
            holder.PayedOnTV.setTextColor(cntxt.getResources().getColor(R.color.litered));
        }
        holder.PayedOnTV.setText(transactionList.get(position).getPaidStatus());
        switch (transactionList.get(position).getType()) {
//            case "card_recharge":
//            {
//                holder.transactionModeIV.setBackground(cntxt.getDrawable(R.drawable.ic_card_recharge_grey));
//                holder.TransactionModeNameTV.setText(changeColorFirsttext(cntxt.getResources().getString(R.string.paid_for)+System.getProperty("line.separator")
//                        +cntxt.getResources().getString(R.string.card_recharge_caps)));
//                break;
//            }
            case "fee_payment": {
                holder.transactionModeIV.setBackground(cntxt.getDrawable(R.drawable.ic_fee_payment_grey));
//                holder.TransactionModeNameTV.setText(changeColorFirsttext(cntxt.getResources().getString(R.string.paid_for) + System.getProperty("line.separator")
//                        + transactionList.get(position).getItem()));
                break;
            }
            case "miscellaneous_payments": {
                holder.transactionModeIV.setBackground(cntxt.getDrawable(R.drawable.ic_miscellaneous_grey));
//                holder.TransactionModeNameTV.setText(changeColorFirsttext(cntxt.getResources().getString(R.string.paid_for) + System.getProperty("line.separator")
//                        + transactionList.get(position).getItem()));
                break;
            }
            case "trust_payments": {
                holder.transactionModeIV.setBackground(cntxt.getDrawable(R.drawable.ic_trust_payment_grey));
//                holder.TransactionModeNameTV.setText(changeColorFirsttext(cntxt.getResources().getString(R.string.paid_for) + System.getProperty("line.separator")
//                        + transactionList.get(position).getItem()));
                break;
            }
//            default: {
//                holder.transactionModeIV.setBackground(cntxt.getDrawable(R.drawable.ic_card_recharge_grey));
//                holder.TransactionModeNameTV.setText(changeColorFirsttext(cntxt.getResources().getString(R.string.paid_for) + System.getProperty("line.separator")
//                        + transactionList.get(position).getTransactionCategoryKey()));
//                break;
//            }
        }
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Bundle firebaseBundle = new Bundle();
                firebaseBundle.putString("Status", transactionList.get(position).getStatus());
                mFirebaseAnalytics.logEvent(cntxt.getResources().getString(R.string.Payment_History_Item_Clicked), firebaseBundle);
                if (transactionList.get(position).getStatus().equalsIgnoreCase("1")) {
                    Intent i = new Intent(cntxt.getApplicationContext(), FeeInvoiceDownloadActivity.class);
                    Bundle b = new Bundle();
                    b.putString("TransID", transactionList.get(position).getId());
                    b.putString("key", transactionList.get(position).getType());
                    b.putInt("tabpos", tabpos);
                    i.putExtras(b);
                    cntxt.startActivity(i);
                }
            }
        });


    }



    public SpannableString changeColorFirsttext(String fulltext)
    {
        SpannableString ss1=  new SpannableString(fulltext);
        ss1.setSpan(new ForegroundColorSpan(cntxt.getResources().getColor(R.color.gray)), 0, 8, 0);// set color
        return ss1;
    }


    @Override
    public int getItemCount() {
        return transactionList.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        TextView PayHistoryTransactionIDTV,PaymenttransactionDateTV,PayHistoryAmountTV,TransactionModeNameTV,PayTransactionStatusTV,PayedOnTV;
        ImageView transactionModeIV;

        public ViewHolder(View itemView) {
            super(itemView);
            PayTransactionStatusTV=itemView.findViewById(R.id.PayTransactionStatusTV);
            TransactionModeNameTV=itemView.findViewById(R.id.TransactionModeNameTV);
            PayHistoryAmountTV=itemView.findViewById(R.id.PayHistoryAmountTV);
//            PayHistoryTransactionIDTV=itemView.findViewById(R.id.PayHistoryTransactionIDTV);
            PaymenttransactionDateTV=itemView.findViewById(R.id.PaymenttransactionDateTV);
            transactionModeIV=itemView.findViewById(R.id.transactionModeIV);
            PayedOnTV=itemView.findViewById(R.id.PayedOnTV);

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

