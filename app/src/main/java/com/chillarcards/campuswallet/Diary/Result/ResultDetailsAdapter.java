package com.chillarcards.campuswallet.Diary.Result;

import android.app.Activity;
import android.content.Context;
import androidx.recyclerview.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.chillarcards.campuswallet.R;

import java.util.List;
import com.chillarcards.campuswallet.networkmodels.ExamResults.Result;

public class ResultDetailsAdapter extends RecyclerView.Adapter<ResultDetailsAdapter.ViewHolder> {

    List<Result> leavdate;
    private final int rowLayout;
    private final Context mContext;
    Activity activity1;
    public ResultDetailsAdapter(List<Result> leavdate, Activity activity, Context context) {
        this.leavdate = leavdate;
        this.activity1 = activity;
        this.rowLayout = R.layout.items_result_details;
        this.mContext = context;


    }

    @Override
    public ResultDetailsAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(rowLayout, parent, false);
        return new ResultDetailsAdapter.ViewHolder(v);


    }



    @Override
    public void onBindViewHolder(ResultDetailsAdapter.ViewHolder holder, final int position) {

//        Fabric.with(mContext, new Answers());




        try {


            holder.S_Name.setText(leavdate.get(position).getSkillName());

            Log.i("Chillar 1 ",holder.S_Name.getText().toString());

            if(leavdate.get(position).getSkillName().equals("")&&leavdate.get(position).getSkillMark().equals("")){

                holder.S_Mark_layout.setVisibility(View.GONE);
                holder.S_Grade_layout.setVisibility(View.GONE);


            }else {

                if(leavdate.get(position).getSkillGrade().equals("")){

                    holder.S_Grade_layout.setVisibility(View.GONE);

                    holder.S_Mark.setText(mContext.getResources().getString(R.string.marks)+leavdate.get(position).getSkillMark());

                }else if(leavdate.get(position).getSkillMark().equals("")){

                    holder.S_Mark_layout.setVisibility(View.GONE);

                    holder.S_Grade.setText(mContext.getResources().getString(R.string.grade)+leavdate.get(position).getSkillGrade());

                }


            }


        } catch (Exception ignored) {


            ignored.printStackTrace();

        }

    }


    @Override
    public int getItemCount() {
        return leavdate.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {

        TextView S_Name,S_Grade,S_Mark;
        RelativeLayout S_Grade_layout,S_Mark_layout,S_name_layout;
        public ViewHolder(View itemView) {
            super(itemView);


            S_Name = itemView.findViewById(R.id.s_name);
            S_Grade = itemView.findViewById(R.id.s_grade);
            S_Mark = itemView.findViewById(R.id.s_mark);
            S_Grade_layout = itemView.findViewById(R.id.layout2);
            S_Mark_layout = itemView.findViewById(R.id.layout3);




        }
    }

}
