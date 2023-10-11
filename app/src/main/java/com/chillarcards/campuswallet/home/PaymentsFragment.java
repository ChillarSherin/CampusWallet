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
import com.chillarcards.campuswallet.databinding.ActivityTeachermessageDetailsBinding;
import com.chillarcards.campuswallet.databinding.LayoutPaymentsFragmentBinding;
import com.google.firebase.analytics.FirebaseAnalytics;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.util.ArrayList;
import java.util.List;

import com.chillarcards.campuswallet.networkmodels.home.Payment;

public class PaymentsFragment extends Fragment {

    ArrayList<String> paymentModules= new ArrayList<>();
    String studentName,studentClass,schoolName;
    PaymentsAdapter mAdapter;
    FirebaseAnalytics mFirebaseAnalytics;

    private LayoutPaymentsFragmentBinding binding;
    Button ReloadBTN,GoBackBTN;
    TextView ErrorMessageTV,CodeErrorTV,NodataTV;
    
    public PaymentsFragment(){
        //Required empty public constructor
    }



    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        binding = LayoutPaymentsFragmentBinding.inflate(inflater, container, false);
        

        ReloadBTN = binding.getRoot().findViewById(R.id.ReloadBTN);
        ErrorMessageTV=binding.getRoot().findViewById(R.id.ErrorMessageTV);
        CodeErrorTV=binding.getRoot().findViewById(R.id.CodeErrorTV);
        GoBackBTN =binding.getRoot().findViewById(R.id.GoBackBTN);
        NodataTV =binding.getRoot().findViewById(R.id.NodataTV);
        
        LinearLayoutManager mLayoutManager = new GridLayoutManager(this.getActivity().getBaseContext(),3);
        binding.recyclerView3.setLayoutManager(mLayoutManager);
        mFirebaseAnalytics=FirebaseAnalytics.getInstance(getActivity());
        return binding.getRoot();
    }

    @Override
    public void onStart() {
        super.onStart();
        Bundle arguments = getArguments();
        String paymentModulesStr = arguments.getString("modulePaymentsNew");
        studentName = arguments.getString("studentName");
        studentClass = arguments.getString("studentClass");
        schoolName = arguments.getString("schoolName");

        binding.textView20.setText(studentName);
        binding.textView21.setText(getResources().getString(R.string.classtext)+studentClass);
        binding.textView22.setText(schoolName);

        ArrayList<Payment> modulePaymentsNew = new ArrayList<Payment>();
        Gson gson = new Gson();
        modulePaymentsNew = gson.fromJson(paymentModulesStr, new TypeToken<List<Payment>>(){}.getType());

//        //System.out.println("modulePaymentsNew : "+modulePaymentsNew.size());

        mAdapter = new PaymentsAdapter(modulePaymentsNew,this.getActivity(),getActivity(),mFirebaseAnalytics);
        binding.recyclerView3.setAdapter(mAdapter);



    }
}
