package com.chillarcards.campuswallet.NewPreOrder;

import android.content.Context;
import androidx.recyclerview.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.TextView;

import com.chillarcards.campuswallet.R;

import java.util.ArrayList;
import java.util.List;

import com.chillarcards.campuswallet.NewPreOrder.AllItems.FilterSingleItemCallback;
import com.chillarcards.campuswallet.networkmodels.OrderItemsDetails.Outlet;


public class FilterNewAdapter extends RecyclerView.Adapter<FilterNewAdapter.ViewHolder> {

    List<Outlet> CategoryList = new ArrayList<>();
    List<String> CategoryIDList;
    String singleOutlet;
    Context cnt;
    FilterSingleItemCallback mCallback;

    public FilterNewAdapter(List<Outlet> mCategoryList,List<String> CategoryIDList, Context cntxt, FilterSingleItemCallback listner) {
        this.CategoryList = mCategoryList;
        this.cnt = cntxt;
        this.mCallback = listner;
        this.CategoryIDList = CategoryIDList;

    }


    @Override
    public FilterNewAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.row_filter, parent, false);
        return new FilterNewAdapter.ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(final FilterNewAdapter.ViewHolder holder, final int position) {

        //System.out.println("Cart List : Filternew :"+CategoryList.get(position).getOutletName());
        holder.TeacherSubnameTV.setText(CategoryList.get(position).getOutletName());
        holder.FilterCB.setChecked(false);
        holder.FilterCB.setEnabled(true);
        holder.FilterCB.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                if (b)
                {
                    singleOutlet=CategoryList.get(position).getOutletName();
                    mCallback.onSingleItemCallback(singleOutlet,position,1);
                }
                else
                {
                    singleOutlet=CategoryList.get(position).getOutletName();
                    mCallback.onSingleItemCallback(singleOutlet,position,0);
                }
            }
        });
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (holder.FilterCB.isChecked())
                {
//                    if (CategoryIDList.size()>0) {
                        holder.FilterCB.setChecked(false);
//                        if (CategoryIDList.contains(CategoryList.get(position).getName())) {
////                        //System.out.println("Filter Changed : AA Removed : "+CategoryIDList.get(position));
//
//                            CategoryIDList.remove(CategoryList.get(position).getName());
//
//                        }
//                    }
                    singleOutlet=CategoryList.get(position).getOutletName();
                    mCallback.onSingleItemCallback(singleOutlet,position,0);
                }
                else {
                    holder.FilterCB.setChecked(true);

//                        if (!CategoryIDList.contains(CategoryList.get(position).getName()))
//                        {
//                            CategoryIDList.add(CategoryList.get(position).getName());
////                            //System.out.println("Filter Changed : AA Added : "+CategoryIDList.get(position));
//                        }
                    singleOutlet=CategoryList.get(position).getOutletName();
                    mCallback.onSingleItemCallback(singleOutlet,position,1);
                }

            }
        });

    }

    @Override
    public int getItemCount() {
        //System.out.println("Cart List : "+CategoryList.size());
        return CategoryList.size();
//        return 3;
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        TextView TeacherSubnameTV;
        CheckBox FilterCB;

        public ViewHolder(View itemView) {
            super(itemView);

            TeacherSubnameTV=(TextView)itemView.findViewById(R.id.CartItemNameTV);
            FilterCB=(CheckBox)itemView.findViewById(R.id.FilterCB);


        }
    }

}
