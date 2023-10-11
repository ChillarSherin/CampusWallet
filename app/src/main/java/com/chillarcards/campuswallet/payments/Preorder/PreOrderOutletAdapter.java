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
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.chillarcards.campuswallet.R;
import com.google.firebase.analytics.FirebaseAnalytics;

import java.util.ArrayList;
import java.util.List;

public class PreOrderOutletAdapter extends RecyclerView.Adapter<PreOrderOutletAdapter.ViewHolder> {

    private List<String> typename = new ArrayList<>();
    private List<String> preorderItemTypeTimingID = new ArrayList<>();
    private  List<String> preorderItemTypeTimingDay = new ArrayList<>();
    private  List<String> preorderItemTypeTimingTime = new ArrayList<>();
    private final int rowLayout;
    private final Activity activity;
    String PINN,TimingId;
    int pos;
    String parameters;
    Context mContext;
    FirebaseAnalytics mFirebaseAnalytics;

    public PreOrderOutletAdapter(List<String> id, List<String> typename, List<String> preorderItemTypeTimingID, String TimingId,
                                 List<String> preorderItemTypeTimingDay, List<String> preorderItemTypeTimingTime,
                                 Activity activity, Context context,FirebaseAnalytics mFirebaseAnalytics) {
        List<String> id1 = id;
        this.typename = typename;
        this.TimingId = TimingId;
        this.preorderItemTypeTimingID = preorderItemTypeTimingID;
        this.preorderItemTypeTimingDay = preorderItemTypeTimingDay;
        this.preorderItemTypeTimingTime = preorderItemTypeTimingTime;
        this.mFirebaseAnalytics=mFirebaseAnalytics;
        this.activity = activity;
        this.rowLayout = R.layout.preorderone_item_layout;
        this. mContext = context;

        ProgressDialog progressDialog = new ProgressDialog(activity);

    }

    @Override
    public PreOrderOutletAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(rowLayout, parent, false);
        return new PreOrderOutletAdapter.ViewHolder(v);


    }

    @Override
    public void onBindViewHolder(final PreOrderOutletAdapter.ViewHolder holder, final int position) {


//        holder.ItemTime.setText(""+typename.get(position));

        //System.out.println("CHILLARTEST  : "+typename.get(holder.getAdapterPosition()));
        if (typename.get(holder.getAdapterPosition()).equals("CANTEEN")){
            holder.ItemName.setText(""+typename.get(holder.getAdapterPosition()));
//            holder.carditem.setBackgroundColor(Color.CYAN);
            if (preorderItemTypeTimingDay.get(position).equals("0"))
            {

                holder.Timing.setText(mContext.getResources().getString(R.string.until)+preorderItemTypeTimingTime.get(position));

            }
        }else if (typename.get(holder.getAdapterPosition()).equals("SNACKS BAR")){
            holder.ItemName.setText(""+typename.get(holder.getAdapterPosition()));
//            holder.carditem.setBackgroundColor(Color.MAGENTA);
            if (preorderItemTypeTimingDay.get(position).equals("0"))
            {
                holder.Timing.setText(mContext.getResources().getString(R.string.until)+preorderItemTypeTimingTime.get(position));

            }
        }else if (typename.get(holder.getAdapterPosition()).equals("STORE")){
            holder.ItemName.setText(""+typename.get(holder.getAdapterPosition()));
//            holder.carditem.setBackgroundColor(Color.BLUE);
            if (preorderItemTypeTimingDay.get(position).equals("0"))
            {
                holder.Timing.setText(mContext.getResources().getString(R.string.until)+preorderItemTypeTimingTime.get(position));

            }
        }else {
            holder.ItemName.setText(""+typename.get(holder.getAdapterPosition()));
//            holder.carditem.setBackgroundColor(Color.RED);
            if (preorderItemTypeTimingDay.get(position).equals("0"))
            {
                holder.Timing.setText(mContext.getResources().getString(R.string.until)+preorderItemTypeTimingTime.get(position));

            }
        }

        holder.carditem.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mFirebaseAnalytics.logEvent(mContext.getResources().getString(R.string.Pre_Order_Outlet_clicked),new Bundle());
                Intent in=new Intent(activity,PreOrderItemsList_Activity.class);
                in.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK|Intent.FLAG_ACTIVITY_CLEAR_TOP);
                Bundle b=new Bundle();
                b.putString("TimingId",TimingId);
                b.putString("preorderItemTypeTimingID",preorderItemTypeTimingID.get(holder.getAdapterPosition()));
                in.putExtras(b);
                activity.startActivity(in);
                activity.finish();

            }
        });

    }

    @Override
    public int getItemCount() {
        return typename.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {

       RelativeLayout carditem;
        TextView ItemName,Timing;


        public ViewHolder(View itemView) {
            super(itemView);

            carditem = itemView.findViewById(R.id.cadviewid);
            ItemName = itemView.findViewById(R.id.itemnameid);
            Timing = itemView.findViewById(R.id.timigid);

        }
    }

}
