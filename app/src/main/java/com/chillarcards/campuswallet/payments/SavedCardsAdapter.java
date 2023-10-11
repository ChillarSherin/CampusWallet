package com.chillarcards.campuswallet.payments;

import android.app.Activity;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.chillarcards.campuswallet.R;

import java.util.ArrayList;
import java.util.List;

import com.chillarcards.campuswallet.networkmodels.ListSavedCards.CardsDetail;

public class SavedCardsAdapter  extends RecyclerView.Adapter<SavedCardsAdapter.MyViewHolder> {


    List<CardsDetail> mData =new ArrayList<>();
    Activity activity;
    SavedCardListener mSavedCardListener;

    public SavedCardsAdapter(List<CardsDetail> myDataset, Activity activity ,SavedCardListener savedCardListener) {
        this.mData = myDataset;
        this.activity=activity;
        this.mSavedCardListener =savedCardListener;
    }


    @NonNull
    @Override
    public SavedCardsAdapter.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v= LayoutInflater.from(parent.getContext()).inflate(R.layout.item_saved_cards, parent, false);
        SavedCardsAdapter.MyViewHolder vh = new SavedCardsAdapter.MyViewHolder(v);
        return vh;
    }

    @Override
    public void onBindViewHolder(@NonNull SavedCardsAdapter.MyViewHolder holder, int position) {


        holder.txt_mode.setText(mData.get(position).getMaskedCard());
    }

    @Override
    public int getItemCount() {
        return mData.size();
    }


    public class MyViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        TextView txt_mode;

        ImageView img_icon_mode;


        public MyViewHolder(View itemView) {
            super(itemView);
            img_icon_mode = itemView.findViewById(R.id.imageView9);
            txt_mode = itemView.findViewById(R.id.textView16);

            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View view) {
            Log.d("RecyclerView", "onClick：" + mData.get(getAdapterPosition()).getMaskedCard());

            mSavedCardListener.onSavedCardSelected(mData.get(getAdapterPosition()));


        }
    }

}
