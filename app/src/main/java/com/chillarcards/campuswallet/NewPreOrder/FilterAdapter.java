package com.chillarcards.campuswallet.NewPreOrder;

import android.content.Context;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.TextView;

import com.chillarcards.campuswallet.R;

import java.util.ArrayList;
import java.util.List;

import com.chillarcards.campuswallet.networkmodels.OrderItemsDetails.Outlet;

/*
Created By : Abhinand
Created Date : 09-10-2018
*/
public class FilterAdapter extends  RecyclerView.Adapter<FilterAdapter.MyViewHolder> {
    Context mContext;
    List<Outlet> myDataset = new ArrayList<>();
//    List<Category> myCategory = new ArrayList<>();
    List<String> mOutlets = new ArrayList<>();
    FilterCallBack filterCallBack;



    public FilterAdapter(List<Outlet> myDataset/*,List<Category> myCategory*/, Context mContext,FilterCallBack filterCallBack) {

        this.myDataset = myDataset;
//        this.myCategory = myCategory;
        this.mContext = mContext;
        this.filterCallBack = filterCallBack;
    }

    @Override
    public FilterAdapter.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.row_filter, parent, false);
        FilterAdapter.MyViewHolder vh = new FilterAdapter.MyViewHolder(v);
        return vh;
    }

    @Override
    public void onBindViewHolder(@NonNull final FilterAdapter.MyViewHolder holder, final int position) {
        holder.TeacherSubnameTV.setText(myDataset.get(position).getOutletName());
        holder.FilterCB.setChecked(true);
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (holder.FilterCB.isChecked())
                {
                    holder.FilterCB.setChecked(false);
                }
                else {
                    holder.FilterCB.setChecked(true);
                }
            }
        });

    }

    @Override
    public int getItemCount() {
        //System.out.println("Category Size : "+myDataset.size());

        return myDataset.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        TextView TeacherSubnameTV;
        CheckBox FilterCB;
        public MyViewHolder(View itemView) {
            super(itemView);
            TeacherSubnameTV=itemView.findViewById(R.id.CartItemNameTV);
            FilterCB=itemView.findViewById(R.id.FilterCB);

        }
    }


}