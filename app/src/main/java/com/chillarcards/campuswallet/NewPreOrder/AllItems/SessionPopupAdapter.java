package com.chillarcards.campuswallet.NewPreOrder.AllItems;

import android.app.Activity;
import android.content.Context;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.chillarcards.campuswallet.R;

import java.util.List;

public class SessionPopupAdapter extends RecyclerView.Adapter<SessionPopupAdapter.MyViewholder> {

    List<HeaderItem> headerItems;
    Context context;
    Activity activity;
    SessionCallback sessionCallback;

    public SessionPopupAdapter() {
    }

    public SessionPopupAdapter(List<HeaderItem> headerItems, Context context, Activity activity,SessionCallback sessionCallback) {
        this.headerItems = headerItems;
        this.context = context;
        this.activity = activity;
        this.sessionCallback = sessionCallback;
    }

    @NonNull
    @Override
    public MyViewholder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View v= LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.session_item_row, viewGroup, false);
        SessionPopupAdapter.MyViewholder vh = new SessionPopupAdapter.MyViewholder(v);
        return vh;
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewholder myViewholder, final int i) {
        myViewholder.SessionNameTV.setText(headerItems.get(i).getSession());
        myViewholder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                sessionCallback.OnSessionSelected(headerItems.get(i));
            }
        });
    }

    @Override
    public int getItemCount() {
        return headerItems.size();
    }

    public class MyViewholder extends RecyclerView.ViewHolder{
        TextView SessionNameTV;
        public MyViewholder(@NonNull View itemView) {
            super(itemView);
            SessionNameTV=itemView.findViewById(R.id.SessionNameTV);
        }
    }
}
