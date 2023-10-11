package com.chillarcards.campuswallet.payments.Transactions;

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

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import com.chillarcards.campuswallet.networkmodels.OnlineTransactionHistory.Transaction;
import com.chillarcards.campuswallet.payments.PaymentReceiptActivity;

public class OnlineTransactionStatusAdapter extends RecyclerView.Adapter<OnlineTransactionStatusAdapter.ViewHolder> {

    List<Transaction> transactionList;
    static Context cntxt;
    FirebaseAnalytics mFirebaseAnalytics;

    public OnlineTransactionStatusAdapter(List<Transaction> transactionList, Context cntxt,FirebaseAnalytics mFirebaseAnalytics) {
        this.transactionList = transactionList;
        this.cntxt = cntxt;
        this.mFirebaseAnalytics=mFirebaseAnalytics;
    }


    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.row_pay_recharge_transaction_history, parent, false);
        return new ViewHolder(v);

    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, final int position) {

        if (transactionList.get(position).getStatus().equalsIgnoreCase("Success")) {
            holder.PayTransactionStatusTV.setTextColor(ContextCompat.getColor(cntxt, R.color.green));
        } else {
            holder.PayTransactionStatusTV.setTextColor(ContextCompat.getColor(cntxt, R.color.red));
        }
        holder.PayTransactionStatusTV.setText(transactionList.get(position).getStatus());
//        holder.TransactionModeNameTV.setText(transactionList.get(position).getTransactionCategoryKey());
        holder.PayHistoryAmountTV.setText(cntxt.getResources().getString(R.string.indian_rupee_symbol) + transactionList.get(position).getAmount());
//        holder.PayHistoryTransactionIDTV.setText(transactionList.get(position).getTransactionID());

        SimpleDateFormat formatter=new SimpleDateFormat("dd-MM-yyyy hh:mm aaa");
        SimpleDateFormat formatter1=new SimpleDateFormat("dd-MM-yyyy HH:mm:ss");
        String dateTime = "";
        try {
            Date date=formatter1.parse(transactionList.get(position).getOnlineTransactionCreatedOn());
            dateTime = formatter.format(date);
        } catch (ParseException e) {
            e.printStackTrace();
            //System.out.println("DATE ERROR : "+e);
        }


        holder.PaymenttransactionDateTV.setText(dateTime.toString());


        holder.TransactionModeNameTV.setText(changeColorFirsttext(cntxt.getResources().getString(R.string.paid_for)+System.getProperty("line.separator")
                +transactionList.get(position).getItem()));
        switch (transactionList.get(position).getTransactionCategoryKey()) {
            case "card_recharge":
            {
                holder.transactionModeIV.setBackground(cntxt.getDrawable(R.drawable.ic_card_recharge_grey));

                break;
            }
            case "fee_payment": {
                holder.transactionModeIV.setBackground(cntxt.getDrawable(R.drawable.ic_fee_payment_grey));

                break;
            }
            case "miscellaneous_payments": {
                holder.transactionModeIV.setBackground(cntxt.getDrawable(R.drawable.ic_miscellaneous_grey));

                break;
            }
            case "trust_payments": {
                holder.transactionModeIV.setBackground(cntxt.getDrawable(R.drawable.ic_trust_payment_grey));

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
               // if (transactionList.get(position).getStatus().equalsIgnoreCase("Success")) {
                    Intent i = new Intent(cntxt.getApplicationContext(), PaymentReceiptActivity.class);
                    Bundle b = new Bundle();
                    b.putString("status", transactionList.get(position).getStatus());
                    b.putString("onlineTransactionID", transactionList.get(position).getTransactionID());
                    b.putString("transaction_category", transactionList.get(position).getTransactionCategoryKey());
                    b.putString("amount", transactionList.get(position).getAmount());
                    b.putString("from", "1");
                    b.putInt("tabpos", 0);
                    i.putExtras(b);
                    cntxt.startActivity(i);
               // }
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
        TextView PayHistoryTransactionIDTV,PaymenttransactionDateTV,PayHistoryAmountTV,TransactionModeNameTV,PayTransactionStatusTV;
        ImageView transactionModeIV;

        public ViewHolder(View itemView) {
            super(itemView);
            PayTransactionStatusTV=itemView.findViewById(R.id.PayTransactionStatusTV);
            TransactionModeNameTV=itemView.findViewById(R.id.TransactionModeNameTV);
            PayHistoryAmountTV=itemView.findViewById(R.id.PayHistoryAmountTV);
//            PayHistoryTransactionIDTV=itemView.findViewById(R.id.PayHistoryTransactionIDTV);
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

