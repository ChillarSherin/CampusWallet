//package com.chillarcards.campuswallet.Payment.History;
//
//import android.content.Context;
//import android.os.Bundle;
//import android.support.annotation.Nullable;
//import android.support.v4.app.Fragment;
//import android.support.v4.app.FragmentManager;
//import android.support.v4.app.FragmentStatePagerAdapter;
//
//import com.chillarcards.campuswallet.R;
//import com.google.firebase.analytics.FirebaseAnalytics;
//import com.google.gson.Gson;
//
//import java.util.ArrayList;
//import java.util.List;
//
//import com.chillarcards.campuswallet.networkmodels.OnlineTransactionHistory.Transaction;
//import com.chillarcards.campuswallet.networkmodels.OnlineTransactionHistory.TrasactionCategory;
//import com.chillarcards.campuswallet.payments.cardtransaction.RefreshStatement;
//
///*
//Created By : Abhinand
//Created Date : 09-10-2018
//*/
//public class PaymentHistoryAdapteroOld extends FragmentStatePagerAdapter {
//
//    List<TrasactionCategory> catagotyList;
//    List<Transaction> transactionList;
//    Context cntxt;
//    FirebaseAnalytics mFirebaseAnalytics;
//    RefreshStatement refreshStatement;
//
//
//    public PaymentHistoryAdapteroOld(FragmentManager supportFragmentManager, List<TrasactionCategory> catagotyList,
//                                     List<Transaction> transactionList, Context cntxt, FirebaseAnalytics mFirebaseAnalytics, RefreshStatement refreshStatement) {
//        super(supportFragmentManager);
//        this.catagotyList=catagotyList;
//        this.transactionList=transactionList;
//        this.cntxt=cntxt;
//        this.mFirebaseAnalytics=mFirebaseAnalytics;
//        this.refreshStatement=refreshStatement;
//
//    }
//
//    @Override
//    public Fragment getItem(int position) {
//        int pos=position;
//        if(pos==0)
//        {
//            PaymentHistoryFragment paymentHistoryFragment;
//            paymentHistoryFragment = new PaymentHistoryFragment(refreshStatement);
//
//            mFirebaseAnalytics.logEvent(cntxt.getResources().getString(R.string.Payment_History_)+"All"
//                    +cntxt.getResources().getString(R.string._Tab_Selected),new Bundle());
//            String str = new Gson().toJson(transactionList);
//            Bundle bundle=new Bundle();
//            bundle.putString("transactionArrList",str);
//            bundle.putInt("tabpos",pos);
//            paymentHistoryFragment.setArguments(bundle);
//            return paymentHistoryFragment;
//        }
//        else
//        {
//            PaymentHistoryFragment paymentHistoryFragment=new PaymentHistoryFragment(refreshStatement);
//            mFirebaseAnalytics.logEvent(cntxt.getResources().getString(R.string.Payment_History_)+catagotyList.get(position).getTransactionCategoryKey()
//                    +cntxt.getResources().getString(R.string._Tab_Selected),new Bundle());
//            String str = new Gson().toJson(SelectFragmentData(pos));
//            Bundle bundle=new Bundle();
//            bundle.putString("transactionArrList",str);
//            bundle.putInt("tabpos",pos);
//            paymentHistoryFragment.setArguments(bundle);
//            return paymentHistoryFragment;
//        }
//    }
//    public List<Transaction> SelectFragmentData(int pos)
//    {
//        List<Transaction> trans=new ArrayList<>();
//        trans.clear();
//
//        for (int i=0;i<transactionList.size();i++)
//        {
//            if (transactionList.get(i).getTransactionCategoryID().equalsIgnoreCase(catagotyList.get(pos).getTransactionCategoryID()))
//            {
//                trans.add(transactionList.get(i));
//            }
//        }
//        return trans;
//    }
//
//    @Override
//    public int getCount() {
//        return catagotyList.size();
//    }
//
//    @Nullable
//    @Override
//    public CharSequence getPageTitle(int position) {
//        String headerName="";
//        switch (catagotyList.get(position).getTransactionCategoryID())
//        {
////            case "2":
////                headerName=cntxt.getResources().getString(R.string.card_recharge_caps);
////                break;
//            case "3":
//                headerName=cntxt.getResources().getString(R.string.fee_payments_caps);
//                break;
//            case "4":
//                headerName=cntxt.getResources().getString(R.string.miscellanious_caps);
//                break;
//            case "5":
//                headerName=cntxt.getResources().getString(R.string.trust_payments_caps);
//                break;
//            case "0":
//                headerName=cntxt.getResources().getString(R.string.all_payments_caps);
//                break;
//
//        }
//
////        return catagotyList.get(position).getTransactionCategoryKey();
//        return headerName;
//    }
//}
