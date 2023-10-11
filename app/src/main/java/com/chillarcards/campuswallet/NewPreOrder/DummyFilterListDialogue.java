package com.chillarcards.campuswallet.NewPreOrder;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.os.Bundle;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.chillarcards.campuswallet.R;

import java.util.List;

import com.chillarcards.campuswallet.networkmodels.OrderItemsDetails.Outlet;


public class DummyFilterListDialogue extends DialogFragment {

    RecyclerView FilterRV;
    Button FilteCloseBTN;
    public static String TAG = "filteroutlettDialog";

    Activity preorderDesignActivity;
    List<Outlet> categories;
    FilterCallBack filterCallBack;
    private FilterAdapter mAdapter;


    @SuppressLint("ValidFragment")
    public DummyFilterListDialogue(Activity preorderDesignActivity, List<Outlet> categories, FilterCallBack filterCallBack) {

        this.preorderDesignActivity = preorderDesignActivity;
        this.categories = categories;
        this.filterCallBack = filterCallBack;
    }

    public DummyFilterListDialogue() {
    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        super.onCreateView(inflater, container, savedInstanceState);
        View view = inflater.inflate(R.layout.filter_outlets, container, false);
        FilterRV = view.findViewById(R.id.FilterRV);
        FilteCloseBTN = view.findViewById(R.id.FilteCloseBTN);

        FilterRV.setVisibility(View.VISIBLE);
        FilteCloseBTN.setOnClickListener(v -> dismiss());

        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        FilterRV.setLayoutManager(new LinearLayoutManager(getActivity()));
        FilterRV.setItemAnimator(new DefaultItemAnimator());
        FilterRV.setNestedScrollingEnabled(false);
        mAdapter = new FilterAdapter(categories, getActivity(),filterCallBack);
        FilterRV.setAdapter(mAdapter);
    }

    @Override
    public void onStart() {
        super.onStart();


    }
}
