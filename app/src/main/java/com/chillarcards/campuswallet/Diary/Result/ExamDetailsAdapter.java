package com.chillarcards.campuswallet.Diary.Result;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import androidx.recyclerview.widget.RecyclerView;
import android.text.SpannableStringBuilder;
import android.text.Spanned;
import android.text.style.RelativeSizeSpan;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.chillarcards.campuswallet.R;
import com.google.firebase.analytics.FirebaseAnalytics;

import java.util.ArrayList;
import java.util.List;

public class ExamDetailsAdapter extends RecyclerView.Adapter<ExamDetailsAdapter.ViewHolder> {
    private List<String> Examname = new ArrayList<>();
    private List<String> Examid = new ArrayList<>();
    private List<String> examTotalMark = new ArrayList<>();
    private List<String> class_avg = new ArrayList<>();
    private List<String> student_score = new ArrayList<>();
    List<String> ExamDate=new ArrayList<>();

    private final int rowLayout;
    private final Context mContext;
    private final Activity ACtivity;
//    private final ProgressDialog progressDialog;
    public String parentPh,s_Id,TranactionTypeId,SelectedTransId;
    String PINN;
    int pos;
    String parameters;
    Dialog Dlg;
    FirebaseAnalytics mFirebaseAnalytics;
    public ExamDetailsAdapter(List<String> Examid, List<String> ExamDate, List<String> Examname, List<String> examTotalMark,
                              List<String> class_avg, List<String> student_score,
                              Activity activity, Context context,FirebaseAnalytics mFirebaseAnalytics) {
        this.Examid = Examid;
        this.ExamDate = ExamDate;
        this.examTotalMark = examTotalMark;
        this.Examname = Examname;
        this.class_avg = class_avg;
        this.student_score = student_score;

        this.mFirebaseAnalytics=mFirebaseAnalytics;

        this.ACtivity = activity;
        this.rowLayout = R.layout.chartresult_layout;
        this.mContext = context;
//        progressDialog = new ProgressDialog(activity);

    }

    @Override
    public ExamDetailsAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(rowLayout, parent, false);
        return new ExamDetailsAdapter.ViewHolder(v);


    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, final int position) {

        //System.out.println("DETAILS : "+Examname.get(position)+" "+class_avg.get(position)+"/"+examTotalMark.get(position));

        String nameStr=" "+Examname.get(holder.getAdapterPosition())+System.getProperty("line.separator")+" "+ ExamDate.get(holder.getAdapterPosition());

        // Initialize a new SpannableStringBuilder instance
        SpannableStringBuilder ssBuilder = new SpannableStringBuilder(nameStr);

        // Initialize a new RelativeSizeSpan to display large size text
        RelativeSizeSpan largeSizeText = new RelativeSizeSpan(5.0f);

        // Initialize a new RelativeSizeSpan to display medium size text
        RelativeSizeSpan mediumSizeText = new RelativeSizeSpan(1.1f);

        // Initialize a new RelativeSizeSpan to display small size text
        RelativeSizeSpan smallSizeText = new RelativeSizeSpan(.7f);

        // Apply the large size text to span
        ssBuilder.setSpan(
                mediumSizeText, // Span to add
                nameStr.indexOf(Examname.get(holder.getAdapterPosition())), // Start of the span (inclusive)
                nameStr.indexOf(Examname.get(holder.getAdapterPosition())) + String.valueOf(Examname.get(holder.getAdapterPosition())).length(), // End of the span (exclusive)
                Spanned.SPAN_EXCLUSIVE_EXCLUSIVE // Do not extend the span when text add later
        );
        // Apply the medium size text to span
        ssBuilder.setSpan(
                smallSizeText,
                nameStr.indexOf(ExamDate.get(holder.getAdapterPosition())),
                nameStr.indexOf(ExamDate.get(holder.getAdapterPosition())) + String.valueOf(ExamDate.get(holder.getAdapterPosition())).length(),
                Spanned.SPAN_EXCLUSIVE_EXCLUSIVE
        );
        holder.Name.setText(ssBuilder);
        holder.Marks.setText(" "+student_score.get(holder.getAdapterPosition())+"/"+examTotalMark.get(holder.getAdapterPosition()));
        holder.relative.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mFirebaseAnalytics.logEvent(mContext.getResources().getString(R.string.Result_Subject_Exam_Item_Clicked),new Bundle());
                Intent i=new Intent(mContext,ResultDetails.class);
                i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                Bundle b=new Bundle();
                b.putString("ExamID",Examid.get(holder.getAdapterPosition()));
                b.putString("ClassAvg",class_avg.get(holder.getAdapterPosition()));

                i.putExtras(b);

                mContext.startActivity(i);
                ACtivity.overridePendingTransition(R.anim.fade_in, R.anim.fade_out);
            }
        });


    }


    @Override
    public int getItemCount() {
        return Examid.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {

        TextView Name,Marks;
        RelativeLayout relative;

        public ViewHolder(View itemView) {
            super(itemView);


            Name = itemView.findViewById(R.id.subjectname);
            Marks = itemView.findViewById(R.id.mark);
            relative = itemView.findViewById(R.id.Layoutmsg);



        }
    }

}

