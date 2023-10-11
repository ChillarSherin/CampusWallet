package com.chillarcards.campuswallet.Diary.Result;

import android.app.Activity;
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

import java.util.List;

import com.chillarcards.campuswallet.networkmodels.Results.Code;


public class ResultAllSubjectsAdapter extends RecyclerView.Adapter<ResultAllSubjectsAdapter.ViewHolder> {


    List<Code> subcanteen;
    private final int rowLayout;
    private final Context mContext;
    private final Activity activity;
    FirebaseAnalytics mFirebaseAnalytics;
    String PINN;
    int pos;
    String parameters;
    //    MoEHelper mHelper=null;
    public ResultAllSubjectsAdapter(List<Code> subcanteen, Activity activity, Context context,FirebaseAnalytics mFirebaseAnalytics) {
        this.subcanteen = subcanteen;
        this.activity = activity;
        this.rowLayout = R.layout.resultitems_layout;
        this.mContext = context;
        this.mFirebaseAnalytics = mFirebaseAnalytics;

    }

    @Override
    public ResultAllSubjectsAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(rowLayout, parent, false);
        return new ResultAllSubjectsAdapter.ViewHolder(v);


    }

    @Override
    public void onBindViewHolder(final ResultAllSubjectsAdapter.ViewHolder holder, final int position) {
        try {
            holder.TvSubject.setText(subcanteen.get(position).getSubjectName());
        } catch (Exception ignored) {


        }

        holder.Card.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mFirebaseAnalytics.logEvent(mContext.getResources().getString(R.string.Result_Subject_Clicked),new Bundle());
                Intent i=new Intent(mContext,ComboLineColumnChartActivity.class);
                i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                Bundle bundle = new Bundle();
                bundle.putString("SubId", String.valueOf(subcanteen.get(holder.getAdapterPosition()).getSubjectID()));
                i.putExtras(bundle);
                mContext.startActivity(i);
                activity.overridePendingTransition(R.anim.fade_in, R.anim.fade_out);

                //System.out.println("SHANILKICHU : "+holder.getAdapterPosition());

            }
        });

    }


    @Override
    public int getItemCount() {
        return subcanteen.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {

        final TextView TvSubject;
        final RelativeLayout Card;

        public ViewHolder(View itemView) {
            super(itemView);

            TvSubject = itemView.findViewById(R.id.subjectname);
            Card= itemView.findViewById(R.id.Layoutmsg);


        }
    }

}