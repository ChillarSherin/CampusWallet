package com.chillarcards.campuswallet.Diary.Attendance;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.chillarcards.campuswallet.R;
import com.google.firebase.analytics.FirebaseAnalytics;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import com.chillarcards.campuswallet.networkmodels.Attendance.LeaveDate;

/*
Created By : Abhinand
Created Date : 09-10-2018
*/
public class AttendanceAdapter extends  RecyclerView.Adapter<AttendanceAdapter.ViewHolder>
        {
            Context context;
            List<LeaveDate> leavdate;
            private String type;
            FirebaseAnalytics mFirebaseAnalytics;

            public AttendanceAdapter(Context context, List<LeaveDate> leavdate, String type, FirebaseAnalytics mFirebaseAnalytics) {
                this.context = context;
                this.leavdate = leavdate;
                this.type = type;
                this.mFirebaseAnalytics = mFirebaseAnalytics;
            }

            @NonNull
            @Override
            public AttendanceAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
                View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.grid_single_date, parent, false);
                return new AttendanceAdapter.ViewHolder(v);
            }

            @Override
            public void onBindViewHolder(@NonNull AttendanceAdapter.ViewHolder holder, int position) {
                final int pos=position;

                DateFormat inputFormat = new SimpleDateFormat("dd-MM-yyyy");
                DateFormat outputFormat = new SimpleDateFormat("dd MMM yyyy");
                DateFormat outputFormat2 = new SimpleDateFormat("yyyy-MM-dd");
                String inputDateStr=leavdate.get(position).getLeaveDate();
                final String leaveStatus=leavdate.get(position).getLeave_status();

                if(leaveStatus.equals("2")){
                    holder.leaveStatus.setVisibility(View.VISIBLE);
                    holder.leaveStatus.setText("Half Day");
                }

                Date date = null;
                try {
                    date = inputFormat.parse(inputDateStr);
                    //System.out.println("SHANIL DATES : "+date);
                    //System.out.println("SHANIL DATE2 : "+outputFormat2);
                } catch (ParseException e) {
                    e.printStackTrace();
                }
                String outputDateStr = outputFormat.format(date);


                String[] items1 = outputDateStr.split(" ");
                String d1=items1[0];
                String m1=items1[1];
                String y1=items1[2];
                int d = Integer.parseInt(d1);
                String m = (m1);
                int y = Integer.parseInt(y1);


                holder.monthdayDetailTV.setText(""+y);
                holder.monthdayTV.setText(""+d+" "+m);
                holder.itemView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        mFirebaseAnalytics.logEvent(context.getResources().getString(R.string.Attendance_Leave_Details_Item_Clicked),new Bundle());
                    }
                });

//                for college


                holder.linLay1.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if (type.equalsIgnoreCase("college")){
                            Intent in=new Intent(context, AttendancePopupActivity.class);
                            in.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                            Bundle b=new Bundle();
                            b.putString("date",leavdate.get(pos).getLeaveDate());
                            in.putExtras(b);
                            context.startActivity(in);
                        }
                    }
                });


            }

            @Override
            public int getItemCount() {
                return leavdate.size();
            }

            public class ViewHolder extends RecyclerView.ViewHolder {
                TextView monthdayTV,monthdayDetailTV,leaveStatus;
                RelativeLayout linLay1;
                public ViewHolder(View itemView) {
                    super(itemView);
                    monthdayTV=itemView.findViewById(R.id.monthdayTV);
                    monthdayDetailTV=itemView.findViewById(R.id.monthdayDetailTV);
                    leaveStatus=itemView.findViewById(R.id.leaveStatus);
                    linLay1=itemView.findViewById(R.id.linLay1);

                }
            }
        }