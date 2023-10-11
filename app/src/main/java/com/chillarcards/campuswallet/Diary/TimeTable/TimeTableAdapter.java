package com.chillarcards.campuswallet.Diary.TimeTable;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import androidx.recyclerview.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.chillarcards.campuswallet.R;

import com.google.firebase.analytics.FirebaseAnalytics;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

public class TimeTableAdapter extends RecyclerView.Adapter<TimeTableAdapter.ViewHolder> {


    private List<String> ID = new ArrayList<>();
    private List<String> Name = new ArrayList<>();
    private List<String> Teacher = new ArrayList<>();
    private List<String> Day = new ArrayList<>();
    private List<String> Order = new ArrayList<>();

    private int rowLayout;
    private Context mContext;
    private Activity activity;
    ProgressDialog progressDialog;
    String PINN;
    int pos;
    String parameters;
    FirebaseAnalytics mFirebaseAnalytics;

    public TimeTableAdapter(List<String> ID,
                            List<String> Name, List<String> Teacher, List<String> Day, List<String> Order,
                            Activity activity, int rowLayout, Context context, FirebaseAnalytics mFirebaseAnalytics) {

        this.ID = ID;
        this.Name = Name;
        this.Teacher = Teacher;
        this.Day = Day;
        this.Order=Order;
        this.activity = activity;
        this.rowLayout = rowLayout;
        this.mContext = context;
        this.mFirebaseAnalytics = mFirebaseAnalytics;

        progressDialog = new ProgressDialog(activity);
        //System.out.println("FromDate "+ID);
        //System.out.println("ToDate "+Name);
        //System.out.println("CreatedDate "+Teacher);
        //System.out.println("Reason "+Day);


    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(rowLayout, parent, false);
        return new ViewHolder(v);


    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, final int position) {
        final String mydate = java.text.DateFormat.getDateTimeInstance().format(Calendar.getInstance().getTime());
//        Fabric.with(mContext, new Answers());
        try {
            holder.Id.setText(ID.get(holder.getAdapterPosition()));
        } catch (Exception ignored) {


        }
        try {
            holder.NAME.setText(Name.get(holder.getAdapterPosition()));
        } catch (Exception ignored) {


        }

        try {
            holder.TEACHER.setText(Teacher.get(holder.getAdapterPosition()));
        } catch (Exception ignored) {


        }
        try {
            holder.DAY.setText(Day.get(holder.getAdapterPosition()));
            //System.out.println("Day : "+Day);
        } catch (Exception ignored) {


        }

        try {
            holder.ORDER.setText(ordinal(Order.get(holder.getAdapterPosition()))+mContext.getResources().getString(R.string.timetable_period));
            //System.out.println("order : "+Order);
        } catch (Exception ignored) {


        }


        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mFirebaseAnalytics.logEvent(mContext.getResources().getString(R.string.Time_Table_Subject_Clicked),new Bundle());

                Intent i = new Intent(mContext, SubjectMsgTeacher.class);
                i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);

                Bundle bundle = new Bundle();
                bundle.putString("SubjectId", (ID.get(holder.getAdapterPosition())));
                bundle.putString("SubjectName", (Name.get(holder.getAdapterPosition())));
                i.putExtras(bundle);
                mContext.startActivity(i);
                activity.overridePendingTransition(R.anim.fade_in, R.anim.fade_out);
            }
        });


    }
    public static String ordinal(String per) {
        int i=Integer.parseInt(per);
        String[] sufixes = new String[] { "th", "st", "nd", "rd", "th", "th", "th", "th", "th", "th" };
        switch (i % 100) {
            case 11:
            case 12:
            case 13:
                return i + "th";
            default:
                return i + sufixes[i % 10];

        }
    }
    @Override
    public int getItemCount() {
        return ID.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {

        TextView Id,NAME,TEACHER,DAY,ORDER;
        public ViewHolder(View itemView) {
            super(itemView);


//            Id = (TextView) itemView.findViewById(R.id.subid);
            NAME = itemView.findViewById(R.id.subname);
//            TEACHER = (TextView) itemView.findViewById(R.id.teacher);
//            DAY = (TextView) itemView.findViewById(R.id.dayid);
            ORDER = itemView.findViewById(R.id.orderid);

        }
    }

}
