package com.chillarcards.campuswallet.Diary.EDiary;

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

import java.util.List;

import com.chillarcards.campuswallet.networkmodels.Results.Code;

public class FromteacherFragmentAdapter extends RecyclerView.Adapter<FromteacherFragmentAdapter.ViewHolder> {

    Context context;
    List<Code> subcanteen;
    boolean Flagteacher;
    FirebaseAnalytics mFirebaseAnalytics;

    public FromteacherFragmentAdapter(Context context,List<Code> subcanteen,boolean Flagteacher,FirebaseAnalytics mFirebaseAnalytics) {
        this.subcanteen = subcanteen;
        this.Flagteacher = Flagteacher;
        this.context = context;
        this.mFirebaseAnalytics = mFirebaseAnalytics;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.row_message_from_teacher, parent, false);
        return new FromteacherFragmentAdapter.ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, final int position) {
        if (Flagteacher)
        {

            if (position==0)
            {
                holder.Layoutmsg.setBackgroundColor(context.getResources().getColor(R.color.home_green));
                holder.TeacherSubnameTV.setTextColor(context.getResources().getColor(R.color.white));
            }
            else {
                holder.Layoutmsg.setBackgroundColor(context.getResources().getColor(R.color.white));
                holder.TeacherSubnameTV.setTextColor(context.getResources().getColor(R.color.txtgrey));
            }
        }

        holder.TeacherSubnameTV.setText(subcanteen.get(position).getSubjectName());

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mFirebaseAnalytics.logEvent(context.getResources().getString(R.string.Message_From_Teacher_Subject_Item_Selected),new Bundle());
                boolean classteacherFlag;
                if (Flagteacher)
                {

                    if (position==0)
                    {
                        classteacherFlag=true;
                    }
                    else {
                        classteacherFlag=false;
                    }
                }
                else {
                    classteacherFlag=false;
                }
                Intent i = new Intent(context, TeachermessageDetailsActivity.class);
                Bundle b=new Bundle();
                //System.out.println("CODMOB: DivID"+ subcanteen.get(position).getSubjectID());
                b.putString("SubjectId", String.valueOf(subcanteen.get(position).getSubjectID()));
                b.putString("SubjectName",subcanteen.get(position).getSubjectName());
                b.putBoolean("Flagteacher",classteacherFlag);
                i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                i.putExtras(b);
                context.startActivity(i);




            }
        });

    }

    @Override
    public int getItemCount() {
        return subcanteen.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{
        TextView FromteacherMessageNoTV,TeacherSubnameTV,FromteacherMessageTV,FromteacherMessageDateTV;
        RelativeLayout Layoutmsg;
        public ViewHolder(View itemView) {
            super(itemView);
//            FromteacherMessageDateTV=itemView.findViewById(R.id.FromteacherMessageDateTV);
//            FromteacherMessageTV=itemView.findViewById(R.id.FromteacherMessageTV);
            TeacherSubnameTV=itemView.findViewById(R.id.TvSubject);
            Layoutmsg=itemView.findViewById(R.id.Layoutmsg);
//            FromteacherMessageNoTV=itemView.findViewById(R.id.FromteacherMessageNoTV);

        }
    }
}
