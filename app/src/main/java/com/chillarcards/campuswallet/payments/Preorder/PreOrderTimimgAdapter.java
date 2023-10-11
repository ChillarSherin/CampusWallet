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
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.chillarcards.campuswallet.R;
import com.google.firebase.analytics.FirebaseAnalytics;

import java.util.ArrayList;
import java.util.List;

import com.chillarcards.campuswallet.application.Constants;

public class PreOrderTimimgAdapter extends RecyclerView.Adapter<PreOrderTimimgAdapter.ViewHolder> {

    private List<Integer> preorderTimingID = new ArrayList<>();
    private List<String> preorderTimingName = new ArrayList<>();
    private String DayString;

    private int rowLayout;
    private final Context mContext;
    private Activity activity;
    ProgressDialog progressDialog;
    String PINN;
    int pos;
    String parameters;
    FirebaseAnalytics mFirebaseAnalytics;


    public PreOrderTimimgAdapter(List<Integer> preorderTimingID, List<Integer> schoolID,
                                 List<String> preorderTimingKey, List<String> preorderTimingStatus,
                                 List<String> preorderTimingName, String Day, Activity activity,
                                 int rowLayout, Context context,FirebaseAnalytics mFirebaseAnalytics) {
        this.preorderTimingID = preorderTimingID;
        List<Integer> schoolID1 = schoolID;
        List<String> preorderTimingKey1 = preorderTimingKey;
        List<String> preorderTimingStatus1 = preorderTimingStatus;
        this.preorderTimingName = preorderTimingName;
        this.DayString=Day;
        this.mFirebaseAnalytics=mFirebaseAnalytics;
        this.activity = activity;
        this.rowLayout = rowLayout;
        this.mContext = context;

        progressDialog = new ProgressDialog(activity);
//        //System.out.println("SHANIL TEST5 : "+DayString);

    }



    @Override
    public PreOrderTimimgAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(rowLayout, parent, false);
        return new PreOrderTimimgAdapter.ViewHolder(v);


    }

    @Override
    public void onBindViewHolder(final PreOrderTimimgAdapter.ViewHolder holder, final int position) {


        holder.Timingname.setText(" "+ preorderTimingName.get(holder.getAdapterPosition()));

        if (preorderTimingName.get(holder.getAdapterPosition()).equals("Breakfast")){

            holder.ImageIcon.setImageResource(R.drawable.ic_breakfast);

        }else if(preorderTimingName.get(holder.getAdapterPosition()).equals("Lunch")){

            holder.ImageIcon.setImageResource(R.drawable.ic_lunch);

        }else if(preorderTimingName.get(holder.getAdapterPosition()).equals("Tea")){

            holder.ImageIcon.setImageResource(R.drawable.ic_tea);

        }else if(preorderTimingName.get(holder.getAdapterPosition()) .equals("Dinner")){

            holder.ImageIcon.setImageResource(R.drawable.ic_dinner);

        }
        holder.Mainlayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mFirebaseAnalytics.logEvent(mContext.getResources().getString(R.string.Pre_Order_Timing_Button_clicked),new Bundle());
                Intent i = new Intent(mContext, PreOrderOutlets_Activity.class);
                i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK|Intent.FLAG_ACTIVITY_CLEAR_TOP);
                Bundle b=new Bundle();
                b.putString("timingid", String.valueOf(preorderTimingID.get(holder.getAdapterPosition())));
                i.putExtras(b);
                mContext.startActivity(i);
                activity.finish();
                //System.out.println("SHANIL TEST : "+preorderTimingID.get(holder.getAdapterPosition()));
                Constants.daySelected=DayString;
            }
        });
    }

    @Override
    public int getItemCount() {
        return preorderTimingID.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{

        int flag=0;
        TextView Timingname;
        LinearLayout Mainlayout;
        ImageView ImageIcon;
        Button OkButton,NoButton;
        public ViewHolder(View itemView) {
            super(itemView);

            Timingname = itemView.findViewById(R.id.itemnameid);
            ImageIcon = itemView.findViewById(R.id.imageidicon);
            Mainlayout = itemView.findViewById(R.id.Mainlayout);

        }

    }
}
