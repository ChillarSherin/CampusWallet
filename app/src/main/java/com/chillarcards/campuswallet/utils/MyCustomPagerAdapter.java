package com.chillarcards.campuswallet.utils;

import android.content.Context;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.viewpager.widget.PagerAdapter;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.chillarcards.campuswallet.R;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

import com.chillarcards.campuswallet.application.Constants;
import com.chillarcards.campuswallet.application.PrefManager;
import com.chillarcards.campuswallet.networkmodels.home.ActiveNewsletter;


public class MyCustomPagerAdapter extends PagerAdapter{
    Context context;
    private List<ActiveNewsletter> images =new ArrayList<>();
    LayoutInflater layoutInflater;
    String currentbalance_Flag,CurrentBalance,AwaitingBalance;
    boolean MachineTypeBoth;
    PrefManager prefManager;



    public MyCustomPagerAdapter(Context context, List<ActiveNewsletter> images,String currentbalance_Flag,String CurrentBalance,String AwaitingBalance,boolean MachineTypeBoth) {
        this.context = context;
        this.images = images;
        this.AwaitingBalance=AwaitingBalance;
        this.CurrentBalance=CurrentBalance;
        this.currentbalance_Flag=currentbalance_Flag;
        this.MachineTypeBoth=MachineTypeBoth;
        layoutInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        prefManager=new PrefManager(context);
    }

    @Override
    public int getCount() {
        return images.size();
    }

    @Override
    public boolean isViewFromObject(View view, Object object) {
        return view == (object);
    }

    @Override
    public Object instantiateItem(ViewGroup container, final int position) {
        View itemView = layoutInflater.inflate(R.layout.item_viewpager, container, false);

        ImageView imageView =  itemView.findViewById(R.id.imageView);
        TextView CurrentBalanceTV =  itemView.findViewById(R.id.CurrentBalanceTV);
        TextView AwaitingBalanceTV =  itemView.findViewById(R.id.AwaitingBalanceTV);
        // TextView CurrentBalanceTV1 =  itemView.findViewById(R.id.CurrentBalanceTV1);
        // TextView AwaitingBalanceTV1 =  itemView.findViewById(R.id.AwaitingBalanceTV1);

//        if (currentbalance_Flag.equalsIgnoreCase("1"))
//        {
//            CurrentBalanceTV.setVisibility(View.VISIBLE);
//            AwaitingBalanceTV.setVisibility(View.VISIBLE);
//            CurrentBalanceTV.setText(context.getResources().getString(R.string.wallet_balance_home)+prefManager.getWalletBalance());
//            AwaitingBalanceTV.setText(context.getResources().getString(R.string.pending_download_home)+prefManager.getAwatingBalance());
////            CurrentBalanceTV1.setVisibility(View.VISIBLE);
////            AwaitingBalanceTV1.setVisibility(View.VISIBLE);
////            CurrentBalanceTV1.setText(context.getResources().getString(R.string.wallet_balance_home)+CurrentBalance);
////            AwaitingBalanceTV1.setText(context.getResources().getString(R.string.pending_download_home)+AwaitingBalance);
//            if (MachineTypeBoth)
//            {
//                CurrentBalanceTV.setVisibility(View.VISIBLE);
//                AwaitingBalanceTV.setVisibility(View.VISIBLE);
////                CurrentBalanceTV1.setVisibility(View.VISIBLE);
////                AwaitingBalanceTV1.setVisibility(View.VISIBLE);
//            }
//            else
//            {
//                CurrentBalanceTV.setVisibility(View.VISIBLE);
//                AwaitingBalanceTV.setVisibility(View.GONE);
////                CurrentBalanceTV1.setVisibility(View.VISIBLE);
////                AwaitingBalanceTV1.setVisibility(View.GONE);
//                ConstraintLayout.LayoutParams layoutParams=(ConstraintLayout.LayoutParams)CurrentBalanceTV.getLayoutParams();
//                layoutParams.rightMargin=16;
//                layoutParams.leftMargin=16;
//                layoutParams.topMargin=8;
//                layoutParams.bottomMargin=16;
//                CurrentBalanceTV.setLayoutParams(layoutParams);
////                ConstraintLayout.LayoutParams layoutParams1=(ConstraintLayout.LayoutParams)CurrentBalanceTV1.getLayoutParams();
////                layoutParams1.rightMargin=9;
////                layoutParams1.leftMargin=21;
////                layoutParams1.topMargin=8;
////                layoutParams1.bottomMargin=16;
////                CurrentBalanceTV1.setLayoutParams(layoutParams1);
//
//            }
//        }
//        else {
//            CurrentBalanceTV.setVisibility(View.GONE);
//            AwaitingBalanceTV.setVisibility(View.GONE);
////            CurrentBalanceTV1.setVisibility(View.GONE);
////            AwaitingBalanceTV1.setVisibility(View.GONE);
//        }


//        Glide.with(imageView.getContext()).load(Constants.BASE_URL_NL+images.get(position).getNewsletterImageURL()).into(imageView);
        //System.out.println("News Letter : "+Constants.BASE_URL_NL+images.get(position).getNewsletterImageURL());
        Picasso.get().load(Constants.BASE_URL_NL+images.get(position).getNewsletterImageURL())
                .placeholder(R.drawable.place_holder)
                .error(R.drawable.place_holder_error)
                .into(imageView);

        container.addView(itemView);

//        //listening to image click
//        imageView.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                Toast.makeText(context, "you clicked image " + (position + 1), Toast.LENGTH_LONG).show();
//            }
//        });

        return itemView;
    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        container.removeView((ConstraintLayout) object);
    }
}