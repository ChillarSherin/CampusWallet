package com.chillarcards.campuswallet.NewPreOrder;

import android.content.Context;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.chillarcards.campuswallet.R;

import java.util.ArrayList;
import java.util.List;

/*
Created By : Abhinand
Created Date : 09-10-2018
*/
public class PreorderDesignAdapter extends  RecyclerView.Adapter<PreorderDesignAdapter.ViewHolder> {
    Context context;
    List<DummyOrderItems> myDataset = new ArrayList<>();



    public PreorderDesignAdapter(List<DummyOrderItems> myDataset, PreorderDesignActivity preorderDesignActivity) {

        this.myDataset = myDataset;
        this.context = preorderDesignActivity;
    }

    @NonNull
    @Override
    public PreorderDesignAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.row_new_preorder_dummy, parent, false);
        return new PreorderDesignAdapter.ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull PreorderDesignAdapter.ViewHolder holder, final int position) {
        holder.TeacherSubnameTV.setText(myDataset.get(position).getCaption());
        holder.ToteacherMessageTV.setText("Outlet : "+myDataset.get(position).getStoreName());
        holder.ToteacherMessageStatusTV.setText("Price : "+myDataset.get(position).getPrice());
        holder.ToteacherMessageDateTV.setText("Quantity : "+myDataset.get(position).getQty());

    }

    @Override
    public int getItemCount() {
        return myDataset.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView TeacherSubnameTV,ToteacherMessageStatusTV,ToteacherMessageTV,ToteacherMessageDateTV;
        public ViewHolder(View itemView) {
            super(itemView);
            TeacherSubnameTV=itemView.findViewById(R.id.CartItemNameTV);
            ToteacherMessageStatusTV=itemView.findViewById(R.id.QtyEBTN);
            ToteacherMessageTV=itemView.findViewById(R.id.CartOutletNameTV);
            ToteacherMessageDateTV=itemView.findViewById(R.id.ItemPriceTV);

        }
    }


}