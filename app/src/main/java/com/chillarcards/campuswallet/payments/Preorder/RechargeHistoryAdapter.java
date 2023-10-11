package com.chillarcards.campuswallet.payments.Preorder;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import androidx.recyclerview.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.chillarcards.campuswallet.R;

import java.util.ArrayList;
import java.util.List;

public class RechargeHistoryAdapter extends RecyclerView.Adapter<RechargeHistoryAdapter.ViewHolder> {

    private List<String> transId = new ArrayList<>();
    private List<String> createdOn = new ArrayList<>();
    private List<String> amount = new ArrayList<>();
    List<String> status = new ArrayList<>();
    List<String> downloadStatus= new ArrayList<>();
    List<String> downloadDate= new ArrayList<>();
    private int rowLayout;
    ProgressDialog progressDialog;
    String PINN;
    int pos;
    String parameters;
    Context cntxt;

    public RechargeHistoryAdapter(List<String> transId, List<String> createdOn, List<String> amount, List<String> status, List<String> downloadStatus, List<String> downloaddate, Activity activity, int rowLayout, Context context) {
        this.transId = transId;
        this.createdOn = createdOn;
        this.amount = amount;
        this.status = status;
        this.downloadStatus = downloadStatus;
        this.downloadDate = downloaddate;

        Activity activity1 = activity;
        cntxt=activity;
        this.rowLayout = rowLayout;
        Context mContext = context;

        progressDialog = new ProgressDialog(activity);

    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(rowLayout, parent, false);
        return new ViewHolder(v);


    }

    @Override
    public void onBindViewHolder(ViewHolder holder, final int position) {


        holder.STATUS.setTextColor(Color.BLACK);

        try {
            holder.ID.setText(transId.get(position));
        } catch (Exception ignored) {


        }
        try {
            holder.CREATED.setText(createdOn.get(position));
        } catch (Exception ignored) {
        }
        try {
            holder.AMOUNT.setText(amount.get(position));
        } catch (Exception ignored) {
        }
        try {
            holder.STATUS.setText(status.get(position));
        } catch (Exception ignored) {
        }
        try {
            holder.DOWNLOADSTATUS.setText(downloadStatus.get(position));
        } catch (Exception ignored) {
        }
        try {
            holder.DownloadDATE.setText(downloadDate.get(position));
        } catch (Exception ignored) {
        }

        if (status.get(position).equals("Success")){
            holder.STATUS.setTextColor(Color.GREEN);
            holder.viewReceiptBTN.setVisibility(View.VISIBLE);
            Animation anim = new AlphaAnimation(0.0f, 1.0f);
            anim.setDuration(500); //You can manage the blinking time with this parameter
            anim.setStartOffset(20);
            anim.setRepeatMode(Animation.REVERSE);
            anim.setRepeatCount(Animation.INFINITE);
            holder.viewReceiptBTN.startAnimation(anim);

        }
        else if(status.get(position).equals("Failed")||status.get(position).equals("Failed At App")){
            holder.STATUS.setTextColor(Color.RED);
            holder.viewReceiptBTN.setVisibility(View.GONE);
        }
        else if(status.get(position).equals("Pending")){
            holder.STATUS.setTextColor(Color.BLUE);
            holder.viewReceiptBTN.setVisibility(View.GONE);
        }
        holder.viewReceiptBTN.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i=new Intent(cntxt, HistoryReceipt.class);
                Bundle bud=new Bundle();
                bud.putString("TransID",transId.get(position));
                i.putExtras(bud);
                cntxt.startActivity(i);
            }
        });

    }

    @Override
    public int getItemCount() {
        return transId.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {

        TextView  ID,CREATED,AMOUNT,STATUS,DOWNLOADSTATUS,DownloadDATE;
        ImageView StatusImg;
        Button viewReceiptBTN;


        public ViewHolder(View itemView) {
            super(itemView);


            ID = itemView.findViewById(R.id.transID);
            CREATED = itemView.findViewById(R.id.transCount);
            AMOUNT = itemView.findViewById(R.id.amountid);
            STATUS = itemView.findViewById(R.id.statusid);
            DOWNLOADSTATUS = itemView.findViewById(R.id.downloadstatusid);
            DownloadDATE = itemView.findViewById(R.id.downloadatenew);
            viewReceiptBTN=itemView.findViewById(R.id.viewReceiptBTN);


        }
    }

}
