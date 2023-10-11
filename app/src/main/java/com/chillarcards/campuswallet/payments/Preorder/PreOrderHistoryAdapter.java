package com.chillarcards.campuswallet.payments.Preorder;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import androidx.recyclerview.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.chillarcards.campuswallet.R;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;


@SuppressWarnings("ALL")
public class PreOrderHistoryAdapter extends RecyclerView.Adapter<PreOrderHistoryAdapter.ViewHolder> {

    private List<String> preorderItemSaleTransactionID = new ArrayList<String>();
    private List<String> tansactionBillNo = new ArrayList<String>();
    private List<String> preorderTimingID = new ArrayList<String>();
    private List<String> preorderTimingName = new ArrayList<String>();
    private List<String> preorderItemSaleTransactionTotalAmount = new ArrayList<String>();
    private List<String> preorderItemSaleTransactionOrderTime = new ArrayList<String>();

    private int rowLayout;
    private Context mContext;
    private Activity activity;
    ProgressDialog progressDialog;
    String PINN;
    int pos;
    String parameters;

    public PreOrderHistoryAdapter(List<String> preorderItemSaleTransactionID, List<String> tansactionBillNo, List<String> preorderTimingID, List<String> preorderTimingName,
                                  List<String> preorderItemSaleTransactionTotalAmount, List<String> preorderItemSaleTransactionOrderTime, Activity activity, int rowLayout,
                                  Context context) {
        this.preorderItemSaleTransactionID = preorderItemSaleTransactionID;
        this.tansactionBillNo = tansactionBillNo;
        this.preorderTimingID = preorderTimingID;
        this.preorderTimingName = preorderTimingName;
        this.preorderItemSaleTransactionTotalAmount = preorderItemSaleTransactionTotalAmount;
        this.preorderItemSaleTransactionOrderTime = preorderItemSaleTransactionOrderTime;


        this.activity = activity;
        this.rowLayout = rowLayout;
        this.mContext = context;

        progressDialog = new ProgressDialog(activity);

    }

    @Override
    public PreOrderHistoryAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(rowLayout, parent, false);
        return new PreOrderHistoryAdapter.ViewHolder(v);


    }

    @Override
    public void onBindViewHolder(final PreOrderHistoryAdapter.ViewHolder holder, final int position) {


        String s = preorderItemSaleTransactionOrderTime.get(holder.getAdapterPosition());
        SimpleDateFormat inputFormat=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        Date d= null;
        try {
            d = inputFormat.parse(s);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        SimpleDateFormat outputFormat=
                new SimpleDateFormat(" MMM dd, yyyy hh:mm a");
        //System.out.println("CHILLAR TIME  : "+outputFormat.format(d));


        if (preorderTimingName.get(holder.getAdapterPosition()).length()>9){
            holder.Name.setTextSize(12);
        }else {

        }
        holder.BillNo.setText(""+tansactionBillNo.get(holder.getAdapterPosition()));
        holder.Name.setText(""+preorderTimingName.get(holder.getAdapterPosition()));
        holder.Amount.setText(""+preorderItemSaleTransactionTotalAmount.get(holder.getAdapterPosition()));
        holder.Date.setText(""+outputFormat.format(d));

        holder.Card.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent i=new Intent(mContext,PreorderHistoryDetailPopup.class);
                Bundle b=new Bundle();
                b.putString("BillNumber",tansactionBillNo.get(holder.getAdapterPosition()));
                i.putExtras(b);
                i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                mContext.startActivity(i);
                activity.overridePendingTransition(R.anim.fade_in, R.anim.fade_out);
            }
        });

    }

    @Override
    public int getItemCount() {
        return preorderItemSaleTransactionID.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {

        TextView BillNo,Name,Amount,Date;
        ImageView StatusImg;
        RelativeLayout Card;


        public ViewHolder(View itemView) {
            super(itemView);


            BillNo=(TextView)itemView.findViewById(R.id.billnumberid);
            Name=(TextView)itemView.findViewById(R.id.itemnameid);
            Amount=(TextView)itemView.findViewById(R.id.totalid);
            Date=(TextView)itemView.findViewById(R.id.dateid);
            Card=(RelativeLayout) itemView.findViewById(R.id.cardid1);



        }
    }

}
