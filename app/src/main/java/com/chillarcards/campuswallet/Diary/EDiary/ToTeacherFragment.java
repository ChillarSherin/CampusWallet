package com.chillarcards.campuswallet.Diary.EDiary;

import android.app.AlertDialog;
import android.content.Context;
import android.os.Bundle;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;

import com.chillarcards.campuswallet.R;

import java.util.ArrayList;
import java.util.List;


public class ToTeacherFragment extends Fragment {

    RecyclerView ToteacherRV;
    FloatingActionButton MessageToteacherAddFB;
    private ToteacherFragmentAdapter mAdapter;
    List<ReasonModel> reasonList;

    public ToTeacherFragment() {
        // Required empty public constructor
    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {

        }
        reasonList=new ArrayList<>();
        reasonList.clear();
        reasonList.add(new ReasonModel("1","Sickness"));
        reasonList.add(new ReasonModel("2","Excuses"));
        reasonList.add(new ReasonModel("3","Others"));
//        MessageToteacherAddFB.setVisibility(View.GONE);
    }

    @Override
    public void onStart() {
        super.onStart();
//        MessageToteacherAddFB.setVisibility(View.GONE);
        mAdapter = new ToteacherFragmentAdapter(getActivity(),reasonList);
        ToteacherRV.setAdapter(mAdapter);
//        MessageToteacherAddFB.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                PopupTLSVersion();
//
//            }
//        });
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view= inflater.inflate(R.layout.fragment_to_teacher, container, false);

        ToteacherRV = view.findViewById(R.id.ToteacherRV);
        MessageToteacherAddFB = view.findViewById(R.id.MessageToteacherAddFB);

        ToteacherRV.setLayoutManager(new LinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL, false));
        ToteacherRV.setItemAnimator(new DefaultItemAnimator());
        ToteacherRV.setNestedScrollingEnabled(false);
        return view;
    }


    @Override
    public void onAttach(Context context) {
        super.onAttach(context);

    }

    @Override
    public void onDetach() {
        super.onDetach();

    }
    public void PopupTLSVersion()
    {
        AlertDialog.Builder dialogBuilder = new AlertDialog.Builder(getActivity());
        LayoutInflater inflater = getActivity().getLayoutInflater();
        View dialogView = inflater.inflate(R.layout.popup_send_teacher_message, null);
        dialogBuilder.setView(dialogView);

        Button SendBTN=dialogView.findViewById(R.id.TeacherMessageSendBTN);
        Button callBTN=dialogView.findViewById(R.id.TeacherMessageCancelBTN);
        EditText MessageToteacherET=dialogView.findViewById(R.id.MessageToteacherET);
        Spinner SubjectSP=dialogView.findViewById(R.id.SubjectSP);

        final AlertDialog alertDialog = dialogBuilder.create();
        alertDialog.show();
        SendBTN.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                alertDialog.dismiss();
            }
        });
        callBTN.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                alertDialog.dismiss();
            }
        });
    }

}
