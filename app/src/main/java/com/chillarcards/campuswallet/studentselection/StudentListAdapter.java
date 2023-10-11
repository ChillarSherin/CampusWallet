package com.chillarcards.campuswallet.studentselection;

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
import java.util.List;

import com.chillarcards.campuswallet.NotificationCenter.Contact;
import com.chillarcards.campuswallet.NotificationCenter.DatabaseHandler;
import com.chillarcards.campuswallet.application.PrefManager;
import com.chillarcards.campuswallet.home.HomeActivity;
import com.chillarcards.campuswallet.networkmodels.studentlist.Code;

public class StudentListAdapter extends RecyclerView.Adapter<StudentListAdapter.MyViewHolder> {

    private List<Code> mData = new ArrayList<>();
    private Activity activity;
    Context mContext;
    FirebaseAnalytics mFirebaseAnalytics;

    public StudentListAdapter(List<Code> myDataset, Activity activity, Context mContext, FirebaseAnalytics mFirebaseAnalytics) {
        this.mData = myDataset;
        this.activity=activity;
        this.mContext=mContext;
        this.mFirebaseAnalytics=mFirebaseAnalytics;
    }


    @NonNull
    @Override
    public StudentListAdapter.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v= LayoutInflater.from(parent.getContext()).inflate(R.layout.item_student_list_activity, parent, false);
        MyViewHolder vh = new MyViewHolder(v);
        return vh;
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, final int position) {

        holder.txt_name.setText(mData.get(position).getStudentFullName());
        holder.txt_school.setText(mData.get(position).getSchool());
        holder.txt_stdntId.setText(mData.get(position).getStudentCode());
        holder.txt_std.setText(mData.get(position).getStandard()+" : "+ mData.get(position).getStandardDivision());

        DatabaseHandler db = new DatabaseHandler(activity);
        List<Contact> contacts = db.getAllContactsCount(mData.get(position).getStudentID(),mData.get(position).getStandard());
        //System.out.println("SHANIL KICHU : " + contacts.size());


        if (contacts.size() <= 0) {

            holder.countTV.setVisibility(View.GONE);
//            holder.notificationlay.setVisibility(View.GONE);

        } else {
            //System.out.println("CHILLARTEST 3  : "+contacts.size());
            holder.countTV.setVisibility(View.VISIBLE);
            holder.countTV.setText("" + contacts.size() + "");

        }


        holder.cl_main.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                PrefManager prefManager = new PrefManager(activity);
                prefManager.setStudentId(mData.get(position).getStudentID());
                prefManager.setStudentName(mData.get(position).getStudentFullName());
                prefManager.setStudentStandard(mData.get(position).getStandard());
                prefManager.setStudentDivision(mData.get(position).getStandardDivision());
                prefManager.setStudentStandardDivisionID(mData.get(position).getStandardDivisonID());
                prefManager.setSChoolID(mData.get(position).getSchoolID());
                prefManager.setSChoolName(mData.get(position).getSchool());
                prefManager.setStudentStandardDivision(mData.get(position).getStandard()+" "+ mData.get(position).getStandardDivision());
                prefManager.setIsStudentSelected(true);
                prefManager.setStudentCode(mData.get(position).getStudentCode());
                prefManager.setStudentUserId(mData.get(position).getStudentUserID());
                prefManager.setType(mData.get(position).getType());

                //setting Server Urls
                prefManager.setInnerApiBaseUrl(mData.get(position).getServerURLConstant().getBaseUrl());  //base url
                prefManager.setBaseImageUrl(mData.get(position).getServerURLConstant().getBaseImageUrl());  //Order url
                prefManager.setBaseOrderUrl(mData.get(position).getServerURLConstant().getBaseOrderUrl());  //image url
                prefManager.setBaseUrlNl(mData.get(position).getServerURLConstant().getBaseUrlNl());  //News letter url
                prefManager.setBaseUrlXpay(mData.get(position).getServerURLConstant().getBaseUrlXpay());  //xpay url

                Bundle Firebase_bundle = new Bundle();
                Firebase_bundle.putString("StudentName",mData.get(position).getStudentFullName());
                mFirebaseAnalytics.logEvent(mContext.getResources().getString(R.string.Student_Selection_View_Clicked),Firebase_bundle);
                mFirebaseAnalytics.setUserProperty(mContext.getResources().getString(R.string.UP_SchoolName),mData.get(position).getSchool());
                mFirebaseAnalytics.setUserProperty(mContext.getResources().getString(R.string.UP_Standard),mData.get(position).getStandard());
                mFirebaseAnalytics.setUserProperty(mContext.getResources().getString(R.string.UP_StudentName),mData.get(position).getStudentFullName());
                mFirebaseAnalytics.setUserProperty(mContext.getResources().getString(R.string.UP_Division),mData.get(position).getStandardDivision());
                mFirebaseAnalytics.setUserProperty(mContext.getResources().getString(R.string.UP_StudentUserID),mData.get(position).getStudentUserID());

                Intent i=new Intent(activity,HomeActivity.class);
                i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                activity.startActivity(i);
                activity.finish();
            }
        });

    }

    @Override
    public int getItemCount() {
        return mData.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {

        TextView txt_name;
        TextView txt_school;
        TextView txt_stdntId;
        TextView txt_std;
        TextView txt_stdid_title;
        TextView txt_std_title;
        ImageView img_notif;
        ConstraintLayout cl_main;
        TextView countTV;

        public MyViewHolder(View itemView) {
            super(itemView);
            txt_name = itemView.findViewById(R.id.textView7);
            txt_school = itemView.findViewById(R.id.textView8);
            txt_stdntId = itemView.findViewById(R.id.textView10);
            txt_std = itemView.findViewById(R.id.textView12);
            txt_stdid_title = itemView.findViewById(R.id.textView9);
            txt_std_title = itemView.findViewById(R.id.textView11);
            img_notif = itemView.findViewById(R.id.imageView7);
            cl_main = itemView.findViewById(R.id.mainLayout);
            countTV = itemView.findViewById(R.id.countTV);

        }
    }
}
