package com.chillarcards.campuswallet.Diary.EDiary;

import android.os.Bundle;
import android.os.Handler;
import android.view.View;

import androidx.viewpager.widget.ViewPager;

import com.chillarcards.campuswallet.R;
import com.chillarcards.campuswallet.databinding.ActivityEDiaryBinding;
import com.google.android.material.tabs.TabLayout;

import com.chillarcards.campuswallet.Diary.LeaveRequest.CallBack;
import com.chillarcards.campuswallet.application.CustomConnectionBuddyActivity;

public class E_Diary_Activity extends CustomConnectionBuddyActivity implements CallBack {


    ViewPager EDiaryContainerVP;
    TabLayout EDiaryTL;

    EDiaryAdapter eDiaryAdapter;
    final int[] ICONS = new int[]{
            R.drawable.ic_msg_from_teacher,
            R.drawable.ic_msg_to_teacher};

    Integer messageID = -1;
    private ActivityEDiaryBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
       // setContentView(R.layout.activity_e__diary);
        binding = ActivityEDiaryBinding.inflate(getLayoutInflater());
        View view = binding.getRoot();
        setContentView(view);

        EDiaryContainerVP = binding.EDiaryContainerVP;
        EDiaryTL= binding.EDiaryTL;

        binding.EdiaryPB.setVisibility(View.GONE);

        binding.HeaderTV.setText(getResources().getString(R.string.ediary_header));
        binding.BackIV.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });


    }

    @Override
    protected void onResume() {
        super.onResume();
        eDiaryAdapter=new EDiaryAdapter(getSupportFragmentManager(),E_Diary_Activity.this,mFirebaseAnalytics);
        EDiaryContainerVP.setAdapter(eDiaryAdapter); // setting adapter to view pager

        EDiaryTL.setupWithViewPager(EDiaryContainerVP); //setting up tab with view pager
        EDiaryTL.setTabGravity(TabLayout.GRAVITY_FILL);
        EDiaryTL.getTabAt(0).setIcon(ICONS[0]);
        EDiaryTL.getTabAt(1).setIcon(ICONS[1]);

        Bundle bundle=getIntent().getExtras();
        if (bundle!=null)
        {
            String message=bundle.getString("MessageTo");
            messageID = bundle.getInt("messageID");

            if (message.equalsIgnoreCase("RequestFromParent"))
            {
                SelectTab();
            }
        }

    }
    public void SelectTab()
    {
        new Handler().postDelayed(
                new Runnable(){
                    @Override
                    public void run() {
                        EDiaryTL.getTabAt(1).select();
                    }
                }, 100);
    }

    @Override
    public void message(String message) {
        eDiaryAdapter=new EDiaryAdapter(getSupportFragmentManager(),E_Diary_Activity.this,mFirebaseAnalytics);
        EDiaryContainerVP.setAdapter(eDiaryAdapter); // setting adapter to view pager

        EDiaryTL.setupWithViewPager(EDiaryContainerVP); //setting up tab with view pager
        EDiaryTL.setTabGravity(TabLayout.GRAVITY_FILL);
        EDiaryTL.getTabAt(0).setIcon(ICONS[0]);
        EDiaryTL.getTabAt(1).setIcon(ICONS[1]);
        EDiaryContainerVP.setCurrentItem(1,true);


    }




}
