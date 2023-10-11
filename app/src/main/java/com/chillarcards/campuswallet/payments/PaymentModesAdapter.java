package com.chillarcards.campuswallet.payments;

import android.app.Activity;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.chillarcards.campuswallet.R;

import java.util.ArrayList;

import com.chillarcards.campuswallet.networkmodels.paymentmodes.PaymentMode;

public class PaymentModesAdapter  extends RecyclerView.Adapter<PaymentModesAdapter.MyViewHolder> {


    ArrayList<PaymentMode> mData =new ArrayList<>();
    Activity activity;

    public PaymentModesAdapter(ArrayList<PaymentMode> myDataset, Activity activity) {
        this.mData = myDataset;
        this.activity=activity;
    }


    @NonNull
    @Override
    public PaymentModesAdapter.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v= LayoutInflater.from(parent.getContext()).inflate(R.layout.item_payment_modes, parent, false);
        PaymentModesAdapter.MyViewHolder vh = new PaymentModesAdapter.MyViewHolder(v);
        return vh;
    }

    @Override
    public void onBindViewHolder(@NonNull PaymentModesAdapter.MyViewHolder holder, int position) {

        switch(mData.get(position).getName()) {
            case "Xpay":
                holder.txt_mode.setText(R.string.xpay);
                holder.txt_extra.setText(mData.get(position).getMessage());
                holder.img_icon_mode.setImageResource(R.mipmap.ic_xpay_payment_mode);
                break;
            case "CC/DC":
                holder.txt_mode.setText(R.string.credit_debit);
                holder.txt_extra.setText(mData.get(position).getMessage());
                holder.img_icon_mode.setImageResource(R.mipmap.ic_card_payment_mode);
                break;
            case "UPI":
                holder.txt_mode.setText(R.string.upi);
                holder.txt_extra.setText(mData.get(position).getMessage());
                holder.img_icon_mode.setImageResource(R.mipmap.ic_upi_payment_mode);
                break;
            case "Netbanking":
                holder.txt_mode.setText(R.string.net_banking);
                holder.txt_extra.setText(mData.get(position).getMessage());
                holder.img_icon_mode.setImageResource(R.mipmap.ic_netbanking_payment_mode);
                break;

            case "GooglePay":
//                CFPaymentService.getCFPaymentServiceInstance().isGPayReadyForPayment(activity, new GooglePayStatusListener() {
//                    @Override
//                    public void isReady() {
//                        holder.txt_mode.setText("Google Pay");
//                        holder.txt_extra.setText(mData.get(position).getMessage());
//                        holder.img_icon_mode.setImageResource(R.drawable.ic_google_pay);
//                    }
//
//                    @Override
//                    public void isNotReady() {
//
//                    }
//                });

              break;

            case "PhonePe":

//                if(CFPaymentService.getCFPaymentServiceInstance().doesPhonePeExist(activity, "PROD")){
//                    holder.txt_mode.setText(mData.get(position).getName());
//                    holder.txt_extra.setText(mData.get(position).getMessage());
//                    holder.img_icon_mode.setImageResource(R.mipmap.ic_netbanking_payment_mode);
//                }
                break;

            default:
                holder.txt_mode.setText(mData.get(position).getName());
                holder.txt_extra.setText(mData.get(position).getMessage());
                holder.img_icon_mode.setImageResource(R.mipmap.ic_card_payment_mode);
                break;

        }
    }

    @Override
    public int getItemCount() {
        return mData.size();
    }


    public class MyViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        TextView txt_mode;
        TextView txt_extra;
        ImageView img_icon_mode;
        public MyViewHolder(View itemView) {
            super(itemView);
            img_icon_mode = itemView.findViewById(R.id.imageView9);
            txt_extra = itemView.findViewById(R.id.textView17);
            txt_mode = itemView.findViewById(R.id.textView16);
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View view) {
            Log.d("RecyclerView", "onClick：" + mData.get(getAdapterPosition()).getName());

        }
    }

}
