package com.chillarcards.campuswallet.payments.cardtransaction;

import android.content.Context;

import androidx.recyclerview.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.chillarcards.campuswallet.R;

import java.util.List;

import com.chillarcards.campuswallet.networkmodels.PaymentHistoryDetails.Detail;

public class HistoryReceiptAdapter extends RecyclerView.Adapter<HistoryReceiptAdapter.ViewHolder> {

    List<Detail> transactionList;
    static Context cntxt;
    String TransactionID;

    public HistoryReceiptAdapter(List<Detail> transactionList, Context cntxt,String TransactionID) {
        this.transactionList = transactionList;
        this.cntxt = cntxt;
        this.TransactionID = TransactionID;
    }


    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.row_receipt_details_history, parent, false);
        return new ViewHolder(v);

    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, final int position) {


        holder.TransactionModeNameTV.setText(transactionList.get(position).getItemName());
        holder.PriceTV.setText(cntxt.getResources().getString(R.string.price_indian_rupee_symbol)+transactionList.get(position).getPrice());
        holder.TotalPriceTV.setText(cntxt.getResources().getString(R.string.total_prize_rupee_symbol)+transactionList.get(position).getTotal());
        holder.QuantityTV.setText(cntxt.getResources().getString(R.string.quantitysmall)+transactionList.get(position).getQuantity());
        holder.TransactionIDTV.setText(cntxt.getResources().getString(R.string.no_small)+TransactionID);
       // holder.TransactionDateTV.setText("No : "+transactionList.get(position).);


    }


    @Override
    public int getItemCount() {
        return transactionList.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder{
        TextView TransactionIDTV,TransactionDateTV,PayHistoryAmountTV,TransactionModeNameTV,PriceTV,QuantityTV,TotalPriceTV;
        ImageView transactionModeIV;

        public ViewHolder(View itemView) {
            super(itemView);
            TransactionModeNameTV=itemView.findViewById(R.id.ItemnameTV);
            TransactionIDTV=itemView.findViewById(R.id.TransactionIDTV);
            PriceTV=itemView.findViewById(R.id.PriceTV);
            QuantityTV=itemView.findViewById(R.id.QuantityTV);
            TransactionDateTV=itemView.findViewById(R.id.TransactionDateTV);
            TotalPriceTV=itemView.findViewById(R.id.TotalPriceTV);
            TransactionDateTV.setVisibility(View.GONE);

        }


    }


}

