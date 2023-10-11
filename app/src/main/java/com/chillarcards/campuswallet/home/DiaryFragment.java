package com.chillarcards.campuswallet.home;

import android.os.Bundle;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.chillarcards.campuswallet.R;
import com.chillarcards.campuswallet.databinding.LayoutDiaryFragmentBinding;
import com.chillarcards.campuswallet.databinding.LayoutPaymentsFragmentBinding;
import com.google.firebase.analytics.FirebaseAnalytics;

import java.util.ArrayList;


public class DiaryFragment extends Fragment {
    ArrayList<String> diaryModules= new ArrayList<>();
    String studentName,studentClass,schoolName;
    DiaryAdapter mAdapter;
    FirebaseAnalytics mFirebaseAnalytics;
    LayoutDiaryFragmentBinding binding;
    public DiaryFragment(){
        //Required empty public constructor
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        binding = LayoutDiaryFragmentBinding.inflate(inflater, container, false);

        LinearLayoutManager mLayoutManager = new GridLayoutManager(this.getActivity().getBaseContext(),3);
        binding.recyclerView3.setLayoutManager(mLayoutManager);
        mFirebaseAnalytics=FirebaseAnalytics.getInstance(getActivity());
        return binding.getRoot();
    }


    @Override
    public void onStart() {
        super.onStart();
        Bundle arguments = getArguments();
        diaryModules = arguments.getStringArrayList("moduleDiaryNew");
        studentName = arguments.getString("studentName");
        studentClass = arguments.getString("studentClass");
        schoolName = arguments.getString("schoolName");

        binding.textView20.setText(studentName);
        binding.textView21.setText(getActivity().getResources().getString(R.string.classtext)+studentClass);
        binding.textView22.setText(schoolName);

//        //System.out.println("moduleDiaryNew : "+diaryModules.size());

        mAdapter = new DiaryAdapter(diaryModules,this.getActivity(),this.getActivity(),mFirebaseAnalytics);
        binding.recyclerView3.setAdapter(mAdapter);
    }

}