package com.chillarcards.campuswallet.Payment.History;

import android.annotation.SuppressLint;
import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.chillarcards.campuswallet.R;
import com.chillarcards.campuswallet.databinding.FragmentPaymentHistoryBinding;
import com.google.firebase.analytics.FirebaseAnalytics;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.util.List;

import com.chillarcards.campuswallet.networkmodels.FeePaymentReport.PaymentDetail;
import com.chillarcards.campuswallet.payments.cardtransaction.RefreshStatement;

public class PaymentHistoryFragment extends Fragment {
    private static final String ARG_TRANS_LIST = "transactionArrList";


    private String jsonTransactionArray;
    List<PaymentDetail> transactionList;
    Context cntxt;
    HistoryFragmentAdapterdummy mAdapter;
    RefreshStatement refreshStatement;

    View view;
    int tabpos;
    FirebaseAnalytics mFirebaseAnalytics;
    private FragmentPaymentHistoryBinding binding;
    Button ReloadBTN,GoBackBTN;
    TextView NodataTV;
    public PaymentHistoryFragment() {
        // Required empty public constructor
    }

    @SuppressLint("ValidFragment")
    public PaymentHistoryFragment(RefreshStatement refreshStatement) {
        this.refreshStatement = refreshStatement;
    }


    @Override
    public void onStart() {
        super.onStart();
        if (getArguments() != null) {
            jsonTransactionArray = getArguments().getString(ARG_TRANS_LIST);
            tabpos=getArguments().getInt("tabpos");
            Gson gson=new Gson();
            TypeToken<List<PaymentDetail>> token = new TypeToken<List<PaymentDetail>>() {};
            transactionList= gson.fromJson(jsonTransactionArray,token.getType());
//            Iterator itr = transactionList.iterator();
//            while (itr.hasNext())
//            {
//                PaymentDetail x = (PaymentDetail)itr.next();
//                if (x.getTransactionCategoryID() .equalsIgnoreCase("2"))
//                    itr.remove();
//            }

            if (transactionList.size()!=0) {
                binding.getRoot().findViewById(R.id.NodataLL).setVisibility(View.GONE);
                mAdapter = new HistoryFragmentAdapterdummy(transactionList, getActivity(),tabpos,mFirebaseAnalytics);
                binding.TransactionListRV.setAdapter(mAdapter);
            }
            else
            {
                NodataTV.setText(getActivity().getResources().getString(R.string.no_data_found));
                GoBackBTN.setText(getActivity().getResources().getString(R.string.go_back));
//                        ErrorImage.setBackgroundResource(R.drawable.nodata);
                binding.getRoot().findViewById(R.id.NodataLL).setVisibility(View.VISIBLE);
                GoBackBTN.setVisibility(View.GONE);
            }
            binding.WalletTransactionSRL.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
                @Override
                public void onRefresh() {
                    refreshStatement.onStatementRefresh();
                    binding.WalletTransactionSRL.setRefreshing(false);
                }
            });
        }
    }


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        binding = FragmentPaymentHistoryBinding.inflate(inflater, container, false);
        
        ReloadBTN = binding.getRoot().findViewById(R.id.ReloadBTN);
        GoBackBTN =binding.getRoot().findViewById(R.id.GoBackBTN);
        NodataTV =binding.getRoot().findViewById(R.id.NodataTV);
        
        binding.TransactionListRV.setLayoutManager(new LinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL, false));
        binding.TransactionListRV.setItemAnimator(new DefaultItemAnimator());
        binding.TransactionListRV.setNestedScrollingEnabled(false);
        mFirebaseAnalytics=FirebaseAnalytics.getInstance(getActivity());
        binding.WalletTransactionSRL.setColorSchemeResources(R.color.colorAccent);

        return view;
    }


}
