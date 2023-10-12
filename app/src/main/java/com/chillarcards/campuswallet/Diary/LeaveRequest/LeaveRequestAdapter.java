package com.chillarcards.campuswallet.Diary.LeaveRequest;

import android.app.Activity;
import android.content.Context;

import androidx.annotation.NonNull;
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

import com.chillarcards.campuswallet.networkmodels.leaverequest.Code;


public class LeaveRequestAdapter extends RecyclerView.Adapter<LeaveRequestAdapter.MyViewHolder> {

    private List<Code> mData = new ArrayList<>();
    private Activity activity;
    Context mContext;
    FirebaseAnalytics mFirebaseAnalytics;
    int index =-1;

    public LeaveRequestAdapter(List<Code> myDataset, int index, Activity activity, Context mContext, FirebaseAnalytics mFirebaseAnalytics) {
        this.mData = myDataset;
        this.activity=activity;
        this.mContext=mContext;
        this.index=index;
        this.mFirebaseAnalytics=mFirebaseAnalytics;
    }


    @NonNull
    @Override
    public LeaveRequestAdapter.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v= LayoutInflater.from(parent.getContext()).inflate(R.layout.item_leave_request, parent, false);
       // LeaveRequestAdapter.MyViewHolder vh = new LeaveRequestAdapter.MyViewHolder(v);
        return new MyViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull LeaveRequestAdapter.MyViewHolder holder, final int position) {

        holder.firstlayout.setBackgroundResource(R.drawable.bg_leave_item);
        if(index!=-1){
            if(index == position){
                System.out.println("TEST ITEM COLOR");
                holder.firstlayout.setBackgroundResource(R.drawable.bg_leave_item_new);
            }
        }

        holder.txtFrom.setText(mData.get(position).getFromDate());
        holder.txtTo.setText(mData.get(position).getToDate());
        holder.txtMsg.setText(mData.get(position).getReason());
        holder.txtApplied.setText(mData.get(position).getCreatedDate());

        if(mData.get(position).getReadStatus().equals("1")){

            holder.topLayout.setBackgroundResource(R.drawable.bg_leave_item_top_two);
            holder.txtStatus.setText(mContext.getResources().getString(R.string.leave_granted_leave));

        }else{
            holder.topLayout.setBackgroundResource(R.drawable.bg_leave_item_top_one);
            holder.txtStatus.setText(mContext.getResources().getString(R.string.waiting_for_approval));
        }

    }

    @Override
    public int getItemCount() {
        return mData.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
         TextView txtFrom;
        TextView txtTo;
         TextView txtMsg;
         TextView txtApplied;
        TextView txtStatus;
         RelativeLayout topLayout;
        RelativeLayout firstlayout;
        RelativeLayout mainContainer;


        public MyViewHolder(View itemView) {
            super(itemView);
            txtFrom = itemView.findViewById(R.id.fromid);
            txtTo = itemView.findViewById(R.id.toid);
            txtMsg = itemView.findViewById(R.id.dataid);
            txtApplied = itemView.findViewById(R.id.applied);
            txtStatus = itemView.findViewById(R.id.statusid);
            topLayout = itemView.findViewById(R.id.toplayout);
            firstlayout = itemView.findViewById(R.id.firstlayout);
            mainContainer = itemView.findViewById(R.id.mainContainer);

        }
    }
}
