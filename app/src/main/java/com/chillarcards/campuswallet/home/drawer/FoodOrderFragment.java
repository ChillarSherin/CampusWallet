package com.chillarcards.campuswallet.home.drawer;

import android.os.Bundle;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.chillarcards.campuswallet.R;
import com.chillarcards.campuswallet.databinding.LayoutCreateLeaverequestDlgBinding;
import com.chillarcards.campuswallet.databinding.LayoutDiaryFragmentBinding;
import com.google.firebase.analytics.FirebaseAnalytics;

import java.util.ArrayList;
import java.util.List;

import com.chillarcards.campuswallet.home.FoodOrderAdapter;

public class FoodOrderFragment extends Fragment {
    ArrayList<String> diaryModules= new ArrayList<>();
    String studentName,studentClass,schoolName;
    FoodOrderAdapter mAdapter;
    FirebaseAnalytics mFirebaseAnalytics;


    public FoodOrderFragment(){
        //Required empty public constructor
    }
    private LayoutDiaryFragmentBinding binding;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
       // View view = inflater.inflate(R.layout.layout_diary_fragment, container, false);
        binding = LayoutDiaryFragmentBinding.inflate(inflater, container, false);

        LinearLayoutManager mLayoutManager = new GridLayoutManager(this.getActivity().getBaseContext(),3);
        binding.recyclerView3.setLayoutManager(mLayoutManager);
        mFirebaseAnalytics=FirebaseAnalytics.getInstance(getActivity());
        return binding.getRoot();
    }
    public List<String> dummyPreorderArray()
    {
        List<String> foorOrderList=new ArrayList<>();
        foorOrderList.clear();
//        foorOrderList.add("pre-order");
        foorOrderList.add("order");
        foorOrderList.add("my-order");
        return foorOrderList;
    }

    @Override
    public void onStart() {
        super.onStart();
        Bundle arguments = getArguments();
        diaryModules = arguments.getStringArrayList("modulefood");
        studentName = arguments.getString("studentName");
        studentClass = arguments.getString("studentClass");
        schoolName = arguments.getString("schoolName");

        binding.textView20.setText(studentName);
        binding.textView21.setText(getActivity().getResources().getString(R.string.classtext)+studentClass);
        binding.textView22.setText(schoolName);


//        //System.out.println("moduleDiaryNew : "+diaryModules.size());

        mAdapter = new FoodOrderAdapter(dummyPreorderArray(),this.getActivity(),this.getActivity(),mFirebaseAnalytics);
        binding.recyclerView3.setAdapter(mAdapter);
    }

}