package com.chillarcards.campuswallet.NewPreOrder;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.TextView;

import com.cepheuen.elegantnumberbutton.view.ElegantNumberButton;
import com.chillarcards.campuswallet.R;

import java.util.List;

import com.chillarcards.campuswallet.NewPreOrder.AllItems.CartCallback;
import com.chillarcards.campuswallet.application.Constants;

public class CartListAdapter extends RecyclerView.Adapter<CartListAdapter.MyViewHolder> {

    List<DummyOrderItems> orderItems;
    Context context;
    Activity activity;
    CartCallback cartCallback;
    private Dialog OtpDialoglogout;
    private int lastPosition = -1;
    public CartListAdapter() {
    }

    public CartListAdapter(List<DummyOrderItems> orderItems, Context context, Activity activity, CartCallback cartCallback) {
        this.orderItems = orderItems;
        this.context = context;
        this.activity = activity;
        this.cartCallback = cartCallback;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View v = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.row_cart_items, viewGroup, false);
        return new CartListAdapter.MyViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull final MyViewHolder myViewHolder, final int i) {

        myViewHolder.CartItemNameTV.setText(orderItems.get(i).getCaption());
        myViewHolder.CartOutletNameTV.setText(orderItems.get(i).getStoreName());
        myViewHolder.ItemPriceTV.setText(orderItems.get(i).getPrice());
        myViewHolder.QtyEBTN.setNumber(orderItems.get(i).getQty());
        myViewHolder.QtyEBTN.setRange(0,Integer.parseInt(orderItems.get(i).getStockQty()));

        myViewHolder.QtyEBTN.setOnValueChangeListener(new ElegantNumberButton.OnValueChangeListener() {
            @Override
            public void onValueChange(ElegantNumberButton view, int oldValue, int newValue) {

                float total = (Float.parseFloat(orderItems.get(i).getUnitPrice()) * newValue);

                myViewHolder.ItemPriceTV.setText(context.getResources().getString(R.string.indian_rupee_symbol) + String.valueOf(total));
                if (newValue==0) {
                    DeleteCartPopup(i,myViewHolder);
                }
                else {

                    orderItems.set(i, new DummyOrderItems(orderItems.get(i).getId(), orderItems.get(i).getName(),
                            orderItems.get(i).getCaption(), String.valueOf(total), orderItems.get(i).getCategotyId(),
                            orderItems.get(i).getStoreName(), myViewHolder.QtyEBTN.getNumber(), orderItems.get(i).getUnitPrice(),
                            orderItems.get(i).getStockQty(),orderItems.get(i).getOutletID(),orderItems.get(i).getSessionID()));
                    Constants.CartItems.set(i, new DummyOrderItems(orderItems.get(i).getId(), orderItems.get(i).getName(),
                            orderItems.get(i).getCaption(), String.valueOf(total), orderItems.get(i).getCategotyId(),
                            orderItems.get(i).getStoreName(), myViewHolder.QtyEBTN.getNumber(), orderItems.get(i).getUnitPrice(),
                            orderItems.get(i).getStockQty(),orderItems.get(i).getOutletID(),orderItems.get(i).getSessionID()));
                    cartCallback.onAddtocartCallback();
                }

            }
        });
        myViewHolder.itemView.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View view) {
                DeleteCartPopup(i,myViewHolder);
                return true;
            }
        });

//        setAnimation(myViewHolder.itemView, i);
    }

    @Override
    public int getItemCount() {
        return orderItems.size();
    }

    private void setAnimation(View viewToAnimate, int position)
    {
        // If the bound view wasn't previously displayed on screen, it's animated
        if (position > lastPosition)
        {
            Animation animation = AnimationUtils.loadAnimation(context, android.R.anim.slide_in_left);
            viewToAnimate.startAnimation(animation);
            lastPosition = position;
        }
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        TextView CartItemNameTV, CartOutletNameTV, ItemPriceTV;
        ElegantNumberButton QtyEBTN;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            CartItemNameTV = itemView.findViewById(R.id.CartItemNameTV);
            CartOutletNameTV = itemView.findViewById(R.id.CartOutletNameTV);
            ItemPriceTV = itemView.findViewById(R.id.ItemPriceTV);
            QtyEBTN = itemView.findViewById(R.id.QtyEBTN);
        }
    }

    public void DeleteCartPopup(final int pos, final MyViewHolder myViewHolder) {
        OtpDialoglogout = new Dialog(context);
        OtpDialoglogout.requestWindowFeature(Window.FEATURE_NO_TITLE);
        OtpDialoglogout.setContentView(R.layout.confirm_delete_cart_popup);
        OtpDialoglogout.setCanceledOnTouchOutside(false);
        OtpDialoglogout.getWindow().setLayout(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        OtpDialoglogout.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        TextView confirm = (TextView) OtpDialoglogout.findViewById(R.id.confirmDeleteCart);
        TextView deny = (TextView) OtpDialoglogout.findViewById(R.id.cancelDeleteCart);

        try {
            OtpDialoglogout.show();
        } catch (Exception e) {
            //System.out.println("Logout PopUp Error : " + e.toString());
        }


        confirm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Constants.CartItems.remove(pos);
                notifyDataSetChanged();
                cartCallback.onAddtocartCallback();
                OtpDialoglogout.dismiss();
            }
        });
        deny.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                myViewHolder.QtyEBTN.setNumber(orderItems.get(pos).getQty());
                float total = (Float.parseFloat(orderItems.get(pos).getUnitPrice()) * 1);

                myViewHolder.ItemPriceTV.setText(context.getResources().getString(R.string.indian_rupee_symbol) + String.valueOf(total));
                OtpDialoglogout.dismiss();
            }
        });

    }
}
