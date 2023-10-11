package com.chillarcards.campuswallet.payments.cardtransaction;

import android.annotation.SuppressLint;
import android.content.Context;
import android.os.Bundle;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.chillarcards.campuswallet.R;
import com.chillarcards.campuswallet.databinding.FragmentPaymentHistoryBinding;
import com.chillarcards.campuswallet.databinding.LayoutAdsWebviewBinding;
import com.google.firebase.analytics.FirebaseAnalytics;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.util.List;

import com.chillarcards.campuswallet.networkmodels.cardtransactionhistory.Transaction;


public class CardHistoryFragment extends Fragment {
    private static final String ARG_TRANS_LIST = "transactionArrList";


    private String jsonTransactionArray;
    List<Transaction> transactionList;
    Context cntxt;
    CardHistoryFragmentAdapterdummy mAdapter;

    View view;
    int posSpec;
    FirebaseAnalytics mFirebaseAnalytics;
    RefreshStatement refreshStatement;
    Button ReloadBTN,GoBackBTN;
    TextView NodataTV;

    FragmentPaymentHistoryBinding binding;
    public CardHistoryFragment() {
        // Required empty public constructor
    }
    @SuppressLint("ValidFragment")
    public CardHistoryFragment(RefreshStatement refreshStatement) {
        // Required empty public constructor
        this.refreshStatement=refreshStatement;
    }

    @Override
    public void onStart() {
        super.onStart();
        if (getArguments() != null) {
            jsonTransactionArray = getArguments().getString(ARG_TRANS_LIST);
            posSpec=getArguments().getInt("posSpec");
            Gson gson=new Gson();
            TypeToken<List<Transaction>> token = new TypeToken<List<Transaction>>() {};
            transactionList= gson.fromJson(jsonTransactionArray,token.getType());

            if (transactionList.size()!=0) {
                view.findViewById(R.id.NodataLL).setVisibility(View.GONE);
                binding.TransactionListRV.setVisibility(View.VISIBLE);
                mAdapter = new CardHistoryFragmentAdapterdummy(transactionList, getActivity(),posSpec,mFirebaseAnalytics);
                binding.TransactionListRV.setAdapter(mAdapter);
            }
            else
            {
                binding.TransactionListRV.setVisibility(View.GONE);
                //System.out.println("Abhinand Empty : ");
                NodataTV.setText(getActivity().getResources().getString(R.string.no_data_found));
                GoBackBTN.setText(getActivity().getResources().getString(R.string.go_back));
//                        ErrorImage.setBackgroundResource(R.drawable.nodata);
                view.findViewById(R.id.NodataLL).setVisibility(View.VISIBLE);
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
//       view = inflater.inflate(R.layout.fragment_payment_history, container, false);
        binding = FragmentPaymentHistoryBinding.inflate(getLayoutInflater());

        ReloadBTN = binding.getRoot().findViewById(R.id.ReloadBTN);
        GoBackBTN =binding.getRoot().findViewById(R.id.GoBackBTN);
        NodataTV =binding.getRoot().findViewById(R.id.NodataTV);

        binding.TransactionListRV.setLayoutManager(new LinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL, false));
        binding.TransactionListRV.setItemAnimator(new DefaultItemAnimator());
        binding.TransactionListRV.setNestedScrollingEnabled(false);
        binding.WalletTransactionSRL.setColorSchemeResources(R.color.colorAccent);

        mFirebaseAnalytics=FirebaseAnalytics.getInstance(getActivity());
        return view;
    }


}
