package com.chillarcards.campuswallet.payments.Preorder;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import androidx.recyclerview.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.chillarcards.campuswallet.R;
import com.google.firebase.analytics.FirebaseAnalytics;

import java.util.ArrayList;

import com.chillarcards.campuswallet.application.Constants;


@SuppressWarnings("ALL")
public class CartListAdapter extends RecyclerView.Adapter<CartListAdapter.ViewHolder> {

    private ArrayList<String> price = new ArrayList<String>();
    private ArrayList<String> name = new ArrayList<String>();
    private ArrayList<String> quant = new ArrayList<String>();
    private ArrayList<String> id = new ArrayList<String>();
    private ArrayList<String> total = new ArrayList<String>();
    private final int rowLayout;
    private final Context mContext;
    private final Activity activity;
    //    private final ProgressDialog progressDialog;
    String PINN;
    int pos;
    String parameters;
    private AdapterView.OnItemClickListener mItemClickListener;
    FirebaseAnalytics mFirebaseAnalytics;



    public CartListAdapter(ArrayList<String> id, ArrayList<String> name,
                           ArrayList<String> quanti, ArrayList<String> pricc,
                           ArrayList<String> tot, Activity activity,
                           int rowLayout, Context context,FirebaseAnalytics mFirebaseAnalytics) {
        this.quant = quanti;
        this.id = id;
        this.name = name;
        this.price =pricc;
        this.total=tot;
        this.activity = activity;
        this.rowLayout = R.layout.cartlistitem_layout;
        this.mContext = context;
        this.mFirebaseAnalytics = mFirebaseAnalytics;

//        progressDialog = new ProgressDialog(activity);

    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(rowLayout, parent, false);
        return new ViewHolder(v);


    }



    @Override
    public void onBindViewHolder(ViewHolder holder, final int position) {

        //System.out.println("ijaz   name   "+name.get(position));
        //System.out.println("ijaz   price   "+ price.get(position));
        //System.out.println("ijaz   quant   "+quant.get(position));
        //System.out.println("ijaz   total   "+total.get(position));


        holder.itemname.setText(name.get(position));
//        holder.itemname.setText(name.get(position));
        holder.pric.setText(price.get(position));
        holder.qty.setText(quant.get(position));
        holder.total.setText(total.get(position));



    }



    @Override
    public int getItemCount() {
        return id.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        final TextView itemname;
        final TextView qty;
        final TextView pric;
        final TextView total;
        LinearLayout linearbackground;
        TextView OkButton,NoButton;
        Dialog PopupConfrm;



        public ImageView imgViewRemoveIcon;

        public ViewHolder(View itemView) {
            super(itemView);



            itemname = (TextView) itemView.findViewById(R.id.itemname);
            qty = (TextView) itemView.findViewById(R.id.quantity);
            pric = (TextView) itemView.findViewById(R.id.price);
            total = (TextView) itemView.findViewById(R.id.total);
            linearbackground=(LinearLayout)itemView.findViewById(R.id.linearlay);


            imgViewRemoveIcon= (ImageView) itemView.findViewById(R.id.imgremove);


            linearbackground.setOnClickListener(this);



        }

        @Override
        public void onClick(View v) {
            if(v.equals(linearbackground)){
                mFirebaseAnalytics.logEvent(mContext.getResources().getString(R.string.Pre_Order_Cart_Remove_Item_Button_clicked),new Bundle());
                final Dialog dlg = new Dialog(v.getContext());
                dlg.requestWindowFeature(Window.FEATURE_NO_TITLE);
                //dlg.setTitle("Are you sure to cancel the item?");
                dlg.setContentView(R.layout.alert_layout);
                dlg.getWindow().setLayout(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
                dlg.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
                dlg.setCancelable(true);
                dlg.setCanceledOnTouchOutside(true);
                dlg.show();

                OkButton= (TextView) dlg.findViewById(R.id.btn_ok);
                NoButton= (TextView) dlg.findViewById(R.id.btn_no);

//removing the item from the list
                OkButton.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {


                        Constants.sales_items.remove(getAdapterPosition());
                        notifyItemRemoved(getAdapterPosition());

                        notifyItemRangeChanged(getAdapterPosition(),Constants.sales_items.size());

                        Intent intent=new Intent(mContext,PreOrderCartList_Activity.class);
                        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                        mContext.startActivity(intent);
                        activity.finish();
                    }
                });

                NoButton.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        dlg.dismiss();
                    }
                });

//                removeAt(getAdapterPosition());

            }
        }
    }


    public void setOnItemClickListener(final AdapterView.OnItemClickListener mItemClickListener) {
        this.mItemClickListener = mItemClickListener;
    }




}
