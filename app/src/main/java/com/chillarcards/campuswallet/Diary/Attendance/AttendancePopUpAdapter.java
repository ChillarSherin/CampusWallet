package com.chillarcards.campuswallet.Diary.Attendance;


import android.content.Context;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.chillarcards.campuswallet.R;

import java.util.List;

import com.chillarcards.campuswallet.networkmodels.AttendancePopup.Code;

public class AttendancePopUpAdapter extends  RecyclerView.Adapter<AttendancePopUpAdapter.ViewHolder>
{
    Context context;
    List<Code> leavdate;


    public AttendancePopUpAdapter(Context context,List<Code> leavdate) {
        this.context = context;
        this.leavdate = leavdate;

    }

    @NonNull
    @Override
    public AttendancePopUpAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_attendance_popup, parent, false);
        return new AttendancePopUpAdapter.ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull AttendancePopUpAdapter.ViewHolder holder, int position) {
        final int pos=position;

        holder.textAttend.setText(""+leavdate.get(pos).getPeriodName());



    }

    @Override
    public int getItemCount() {
        return leavdate.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView textAttend;
        public ViewHolder(View itemView) {
            super(itemView);
            textAttend=itemView.findViewById(R.id.textAttend);
        }
    }
}
