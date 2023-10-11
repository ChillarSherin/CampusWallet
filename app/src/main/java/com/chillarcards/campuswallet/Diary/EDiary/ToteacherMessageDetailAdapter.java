package com.chillarcards.campuswallet.Diary.EDiary;

import android.content.Context;
import androidx.annotation.NonNull;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.chillarcards.campuswallet.R;
import com.google.firebase.analytics.FirebaseAnalytics;

import java.util.List;

import com.chillarcards.campuswallet.networkmodels.ParentMessagesNew.Code;

public class ToteacherMessageDetailAdapter extends RecyclerView.Adapter<ToteacherMessageDetailAdapter.ViewHolder> {

    Context context;
    List<Code> reasonList;
    FirebaseAnalytics mFirebaseAnalytics;

    public ToteacherMessageDetailAdapter(Context context, List<Code> reasonList,FirebaseAnalytics mFirebaseAnalytics) {
        this.context = context;
        this.reasonList = reasonList;
        this.mFirebaseAnalytics = mFirebaseAnalytics;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.row_to_teacher_dummy, parent, false);
        return new ToteacherMessageDetailAdapter.ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, final int position) {
        holder.TeacherSubnameTV.setText(reasonList.get(position).getSubject());
        holder.ToteacherMessageDateTV.setText(reasonList.get(position).getMessageCreatedOn());
        holder.ToteacherMessageTV.setText(reasonList.get(position).getMessageContent());
        if (reasonList.get(position).getReadStatus().equalsIgnoreCase("0"))
        {
            holder.ToteacherMessageStatusTV.setText(context.getResources().getString(R.string.sent));
            holder.ToteacherMessageStatusTV.setTextColor(ContextCompat.getColor(context, R.color.red));
            holder.ToteacherMessageStatusTV.setTextColor(context.getResources().getColor(R.color.red));

        }
        else {
            holder.ToteacherMessageStatusTV.setText(context.getResources().getString(R.string.read));
            holder.ToteacherMessageStatusTV.setTextColor(ContextCompat.getColor(context, R.color.green));
            holder.ToteacherMessageStatusTV.setTextColor(context.getResources().getColor(R.color.green));
        }




    }

    @Override
    public int getItemCount() {
        return reasonList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{
        TextView ToteacherMessageStatusTV,TeacherSubnameTV,ToteacherMessageTV,ToteacherMessageDateTV;
        public ViewHolder(View itemView) {
            super(itemView);
            ToteacherMessageDateTV=itemView.findViewById(R.id.ItemPriceTV);
            ToteacherMessageTV=itemView.findViewById(R.id.CartOutletNameTV);
            TeacherSubnameTV=itemView.findViewById(R.id.CartItemNameTV);
            ToteacherMessageStatusTV=itemView.findViewById(R.id.QtyEBTN);

        }
    }
}
