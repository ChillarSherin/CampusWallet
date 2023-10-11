package com.chillarcards.campuswallet.home;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.chillarcards.campuswallet.R;
import com.google.firebase.analytics.FirebaseAnalytics;

import java.util.ArrayList;

import com.chillarcards.campuswallet.Diary.Attendance.AttendanceActivity;
import com.chillarcards.campuswallet.Diary.Calendar.CalendarNew;
import com.chillarcards.campuswallet.Diary.EDiary.E_Diary_Activity;
import com.chillarcards.campuswallet.Diary.Gallary.GalleryActivity;
import com.chillarcards.campuswallet.Diary.LeaveRequest.LeaveRequestActivity;
import com.chillarcards.campuswallet.Diary.NoticeBoard.NoticeBoardActivity;
import com.chillarcards.campuswallet.Diary.Result.ResultActivity;
import com.chillarcards.campuswallet.Diary.TimeTable.TimeTableActivity;


public class DiaryAdapter  extends RecyclerView.Adapter<DiaryAdapter.MyViewHolder> {


    ArrayList<String> mData =new ArrayList<>();
    Activity activity;
    Context mContext;
    FirebaseAnalytics mFirebaseAnalytics;

    public DiaryAdapter(ArrayList<String> myDataset, Activity activity, Context mContext,FirebaseAnalytics mFirebaseAnalytics) {
        this.mData = myDataset;
        this.activity=activity;
        this.mContext=mContext;
        this.mFirebaseAnalytics=mFirebaseAnalytics;
    }


    @NonNull
    @Override
    public DiaryAdapter.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v= LayoutInflater.from(parent.getContext()).inflate(R.layout.item_home_list, parent, false);
        DiaryAdapter.MyViewHolder vh = new DiaryAdapter.MyViewHolder(v);
        return vh;
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        holder.txt_module.setText(mData.get(position));
        final int pos=position;

        switch (mData.get(position)){


            case "calendar":
                holder.txt_module.setText(mContext.getResources().getString(R.string.calender_header));
                holder.img_module.setImageResource(R.drawable.ic_calendar);
                break;
            case "e-diary":
                holder.txt_module.setText(mContext.getResources().getString(R.string.ediary_header));
                holder.img_module.setImageResource(R.drawable.ic_diary);
                break;
            case "timetable":
                holder.txt_module.setText(mContext.getResources().getString(R.string.timetable_header));
                holder.img_module.setImageResource(R.drawable.ic_timetable);
                break;
            case "leave-request":
                holder.txt_module.setText(mContext.getResources().getString(R.string.leaverequest_header));
                holder.img_module.setImageResource(R.drawable.ic_leave_request);
                break;
            case "notice-board":
                holder.txt_module.setText(mContext.getResources().getString(R.string.noticeboard_header));
                holder.img_module.setImageResource(R.drawable.ic_notice_board);
                break;
            case "result":
                holder.txt_module.setText(mContext.getResources().getString(R.string.results_header));
                holder.img_module.setImageResource(R.drawable.ic_result);
                break;
            case "attendance":
                holder.txt_module.setText(mContext.getResources().getString(R.string.attendance_header));
                holder.img_module.setImageResource(R.drawable.ic_attendance);
                break;
            case "Activity":
                holder.txt_module.setText(mContext.getResources().getString(R.string.gallery_header));
                holder.img_module.setImageResource(R.drawable.ic_highlights);
                break;
            default:
                break;

        }
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                switch (mData.get(pos)){

                    case "calendar":
                        mFirebaseAnalytics.logEvent(mContext.getResources().getString(R.string.Calendar_Menu_Selected),new Bundle());
                        Intent calendar=new Intent(activity, CalendarNew.class);
                        calendar.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                        activity.startActivity(calendar);

                        break;
                    case "e-diary":
                        mFirebaseAnalytics.logEvent(mContext.getResources().getString(R.string.E_Dairy_Menu_Selected),new Bundle());
                        Intent edairy=new Intent(activity, E_Diary_Activity.class);
                        activity.startActivity(edairy);

                        break;
                    case "timetable":
                        mFirebaseAnalytics.logEvent(mContext.getResources().getString(R.string.Time_Table_Menu_Selected),new Bundle());
                        Intent timetable=new Intent(activity, TimeTableActivity.class);
                        activity.startActivity(timetable);

                        break;
                    case "leave-request":
                        mFirebaseAnalytics.logEvent(mContext.getResources().getString(R.string.Leave_Request_Menu_Selected),new Bundle());
                        Intent leave=new Intent(activity, LeaveRequestActivity.class);
                        activity.startActivity(leave);

                        break;
                    case "notice-board":
                        mFirebaseAnalytics.logEvent(mContext.getResources().getString(R.string.Notice_Board_Menu_Selected),new Bundle());
                        Intent noticeboard=new Intent(activity, NoticeBoardActivity.class);
                        activity.startActivity(noticeboard);
                        break;
                    case "result":
                        mFirebaseAnalytics.logEvent(mContext.getResources().getString(R.string.Result_Menu_Selected),new Bundle());
                        Intent result=new Intent(activity, ResultActivity.class);
                        activity.startActivity(result);

                        break;
                    case "attendance":
                        mFirebaseAnalytics.logEvent(mContext.getResources().getString(R.string.Attendance_Menu_Selected),new Bundle());
                        Intent attendance=new Intent(activity, AttendanceActivity.class);
                        activity.startActivity(attendance);

                        break;
                    case "Activity":
                        mFirebaseAnalytics.logEvent(mContext.getResources().getString(R.string.Gallery_Menu_Selected),new Bundle());
                        Intent Activity=new Intent(activity, GalleryActivity.class);
                        activity.startActivity(Activity);

                        break;
                    default:
                        break;

                }
            }
        });

    }

    @Override
    public int getItemCount() {
        return mData.size();
    }


    public class MyViewHolder extends RecyclerView.ViewHolder {
        TextView txt_module;
        ImageView img_module;
        ConstraintLayout ItemhomeCL;
        public MyViewHolder(View itemView) {
            super(itemView);
            txt_module = itemView.findViewById(R.id.textView6);
            img_module = itemView.findViewById(R.id.imageView8);
            ItemhomeCL = itemView.findViewById(R.id.ItemhomeCL);
        }
    }

}
