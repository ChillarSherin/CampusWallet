package com.chillarcards.campuswallet.payments.cardtransaction;

import android.content.Context;
import android.os.Bundle;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentStatePagerAdapter;

import com.chillarcards.campuswallet.R;
import com.google.firebase.analytics.FirebaseAnalytics;
import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.List;


import com.chillarcards.campuswallet.networkmodels.cardtransactionhistory.Transaction;
import com.chillarcards.campuswallet.networkmodels.cardtransactionhistory.TransactionCategory;

/*
Created By : Abhinand
Created Date : 09-10-2018
*/
public class CardHistoryAdapter extends FragmentStatePagerAdapter {

    List<TransactionCategory> catagotyList;
    List<Transaction> transactionList;
    Context cntxt;
    FirebaseAnalytics mFirebaseAnalytics;
    RefreshStatement refreshStatement;


    public CardHistoryAdapter(FragmentManager supportFragmentManager, List<TransactionCategory> catagotyList,
                              List<Transaction> transactionList, Context cntxt,FirebaseAnalytics mFirebaseAnalytics,RefreshStatement refreshStatement) {
        super(supportFragmentManager);
        this.catagotyList=catagotyList;
        this.transactionList=transactionList;
        this.cntxt=cntxt;
        this.mFirebaseAnalytics=mFirebaseAnalytics;
        this.refreshStatement=refreshStatement;
    }

    @Override
    public Fragment getItem(int position) {

        if(position ==0)
        {
            CardHistoryFragment paymentHistoryFragment = new CardHistoryFragment(refreshStatement);
            mFirebaseAnalytics.logEvent(cntxt.getResources().getString(R.string.Transaction_History_)+"All"
                    +cntxt.getResources().getString(R.string._Tab_Selected),new Bundle());
            String str = new Gson().toJson(transactionList);
            Bundle bundle=new Bundle();
            bundle.putString("transactionArrList",str);
            bundle.putInt("posSpec", position);
            paymentHistoryFragment.setArguments(bundle);
            return paymentHistoryFragment;
        }
        else
        {
            CardHistoryFragment paymentHistoryFragment=new CardHistoryFragment(refreshStatement);
            mFirebaseAnalytics.logEvent(cntxt.getResources().getString(R.string.Transaction_History_)+catagotyList.get(position).getName()
                    +cntxt.getResources().getString(R.string._Tab_Selected),new Bundle());
            String str = new Gson().toJson(SelectFragmentData(position));
            Bundle bundle=new Bundle();
            bundle.putString("transactionArrList",str);
            bundle.putInt("posSpec", position);
            paymentHistoryFragment.setArguments(bundle);
            return paymentHistoryFragment;
        }
    }
    public List<Transaction> SelectFragmentData(int pos)
    {
        List<Transaction> trans=new ArrayList<>();
        trans.clear();

        for (int i=0;i<transactionList.size();i++)
        {
            if (transactionList.get(i).getTransactionType().equalsIgnoreCase(catagotyList.get(pos).getName()))
            {
                trans.add(transactionList.get(i));
            }
        }
        return trans;
    }

    @Override
    public int getCount() {
        return catagotyList.size();
    }

    @Nullable
    @Override
    public CharSequence getPageTitle(int position) {
        return catagotyList.get(position).getName();
    }
}
