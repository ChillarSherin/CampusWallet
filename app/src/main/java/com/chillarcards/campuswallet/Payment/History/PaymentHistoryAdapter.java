package com.chillarcards.campuswallet.Payment.History;

import android.content.Context;
import android.os.Bundle;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentStatePagerAdapter;

import com.chillarcards.campuswallet.R;
import com.google.firebase.analytics.FirebaseAnalytics;
import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.List;

import com.chillarcards.campuswallet.networkmodels.FeePaymentReport.Payment;
import com.chillarcards.campuswallet.networkmodels.FeePaymentReport.PaymentDetail;
import com.chillarcards.campuswallet.payments.cardtransaction.RefreshStatement;

/*
Created By : Abhinand
Created Date : 09-10-2018
*/
public class PaymentHistoryAdapter extends FragmentStatePagerAdapter {

    List<Payment> catagotyList;
    List<PaymentDetail> transactionList;
    Context cntxt;
    FirebaseAnalytics mFirebaseAnalytics;
    RefreshStatement refreshStatement;


    public PaymentHistoryAdapter(FragmentManager supportFragmentManager, List<Payment> catagotyList,
                                 List<PaymentDetail> transactionList, Context cntxt, FirebaseAnalytics mFirebaseAnalytics,RefreshStatement refreshStatement) {
        super(supportFragmentManager);
        this.catagotyList=catagotyList;
        this.transactionList=transactionList;
        this.cntxt=cntxt;
        this.mFirebaseAnalytics=mFirebaseAnalytics;
        this.refreshStatement=refreshStatement;

    }

    @Override
    public Fragment getItem(int position) {
        int pos=position;

            PaymentHistoryFragment paymentHistoryFragment=new PaymentHistoryFragment(refreshStatement);
            mFirebaseAnalytics.logEvent(cntxt.getResources().getString(R.string.Fee_Payment_Report_)+catagotyList.get(position).getTransactionCategoryName()
                    +cntxt.getResources().getString(R.string._Tab_Selected),new Bundle());
            String str = new Gson().toJson(SelectFragmentData(pos));
            Bundle bundle=new Bundle();
            bundle.putString("transactionArrList",str);
            bundle.putInt("tabpos",pos);
            paymentHistoryFragment.setArguments(bundle);
            return paymentHistoryFragment;

    }
    public List<PaymentDetail> SelectFragmentData(int pos)
    {
        List<PaymentDetail> trans=new ArrayList<>();
        trans.clear();

        for (int i=0;i<transactionList.size();i++)
        {
            if (transactionList.get(i).getType().equalsIgnoreCase(catagotyList.get(pos).getTransactionCategory()))
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
        String headerName="";
        switch (catagotyList.get(position).getTransactionCategory())
        {
//            case "2":
//                headerName=cntxt.getResources().getString(R.string.card_recharge_caps);
//                break;
            case "fee_payment":
                headerName=cntxt.getResources().getString(R.string.fee_payments_caps);
                break;
            case "miscellaneous_payments":
                headerName=cntxt.getResources().getString(R.string.miscellanious_caps);
                break;
            case "trust_payments":
                headerName=cntxt.getResources().getString(R.string.trust_payments_caps);
                break;
            default:
                headerName=catagotyList.get(position).getTransactionCategory();
                break;

        }

//        return catagotyList.get(position).getTransactionCategoryKey();
        return headerName;
    }
}
