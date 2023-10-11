package com.chillarcards.campuswallet.NewPreOrder.MyOrders;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.chillarcards.campuswallet.R;
import com.google.gson.Gson;

import java.util.List;

import com.chillarcards.campuswallet.networkmodels.MyOrder.Datum;

public class MyOrderAdapter extends RecyclerView.Adapter<MyOrderAdapter.MyviewHolder> {
    List<Datum> myDataset;
    Context context;
    boolean isExpired;

    public MyOrderAdapter(List<Datum> myDataset, Context context,boolean isExpired) {
        this.myDataset = myDataset;
        this.context = context;
        this.isExpired = isExpired;
    }

    @NonNull
    @Override
    public MyviewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View v = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.row_my_order, viewGroup, false);
        return new MyOrderAdapter.MyviewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull MyviewHolder myviewHolder, final int i) {
        if (myDataset.get(i).getOrderPurchaseStatus().equalsIgnoreCase("0"))
        {
            myviewHolder.OrderStatusTV.setText("Order Placed");
        }
        else{
//            myviewHolder.OrderStatusTV.setText("Order Delivered");
            myviewHolder.OrderStatusTV.setText("Order Completed");
        }


        myviewHolder.OrderTotalTV.setText("Total : "+context.getResources().getString(R.string.indian_rupee_symbol)+myDataset.get(i).getTotalAmount());
        myviewHolder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Gson gson = new Gson();
                String ItemsJSON = gson.toJson(myDataset.get(i));

                Intent intent=new Intent(context,MyOrderDetailsActivity.class);
                Bundle bundle=new Bundle();
                bundle.putString("DetailsJSON",ItemsJSON);
                bundle.putBoolean("isExpired",isExpired);
//                bundle.putString("OrderID",myDataset.get(i).getOrderPurchaseID());
//                bundle.putString("OrderRealID",myDataset.get(i).getRealOrderPurchaseID());
//                bundle.putString("Total",myDataset.get(i).getTotalAmount());
                intent.putExtras(bundle);
                context.startActivity(intent);
            }
        });


    }

    @Override
    public int getItemCount() {
        return myDataset.size();
    }

    public class MyviewHolder extends RecyclerView.ViewHolder{
        TextView OrderStatusTV,OrderTotalTV;
        ImageView OrderImageIV,OrderStatusIV;
        public MyviewHolder(@NonNull View itemView) {
            super(itemView);
            OrderTotalTV=itemView.findViewById(R.id.OrderTotalTV);
            OrderStatusTV=itemView.findViewById(R.id.OrderStatusTV);
            OrderImageIV=itemView.findViewById(R.id.OrderImageIV);
            OrderStatusIV=itemView.findViewById(R.id.OrderStatusIV);
        }
    }
}
