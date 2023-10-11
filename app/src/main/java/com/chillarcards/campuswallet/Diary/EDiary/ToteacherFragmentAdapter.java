package com.chillarcards.campuswallet.Diary.EDiary;

import android.content.Context;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.chillarcards.campuswallet.R;

import java.util.List;

public class ToteacherFragmentAdapter extends RecyclerView.Adapter<ToteacherFragmentAdapter.ViewHolder> {

    Context context;
    List<ReasonModel> reasonList;

    public ToteacherFragmentAdapter(Context context,List<ReasonModel> reasonList) {
        this.context = context;
        this.reasonList = reasonList;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.row_message_from_teacher, parent, false);
        return new ToteacherFragmentAdapter.ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, final int position) {
        holder.TeacherSubnameTV.setText(reasonList.get(position).getReasonName());

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                Intent i=new Intent(context,ToTeachermessageDetailActivity.class);
//                Bundle bundle = new Bundle();
//                bundle.putString("ReasonId", reasonList.get(position).getReasonID());
//                bundle.putString("reason",reasonList.get(position).getReasonName());
//                i.putExtras(bundle);
//                context.startActivity(i);
//                context.overridePendingTransition(R.anim.fade_in, R.anim.fade_out);
            }
        });

    }

    @Override
    public int getItemCount() {
        return reasonList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{
        TextView ToteacherMessageStatusTV,TeacherSubnameTV,ToteacherMessageTV,ToteacherMessageDateTV;
        public ViewHolder(View itemView) {
            super(itemView);
//            ToteacherMessageDateTV=itemView.findViewById(R.id.ToteacherMessageDateTV);
//            ToteacherMessageTV=itemView.findViewById(R.id.ToteacherMessageTV);
            TeacherSubnameTV=itemView.findViewById(R.id.TvSubject);
//            ToteacherMessageStatusTV=itemView.findViewById(R.id.ToteacherMessageStatusTV);

        }
    }
}
