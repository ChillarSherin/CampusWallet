package com.chillarcards.campuswallet.Diary.EDiary;

import android.content.Context;
import android.os.Bundle;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentStatePagerAdapter;

import com.chillarcards.campuswallet.R;
import com.google.firebase.analytics.FirebaseAnalytics;

/*
Created By : Abhinand
Created Date : 09-10-2018
*/
public class EDiaryAdapter extends FragmentStatePagerAdapter {

    Context cntxt;
    FirebaseAnalytics mFirebaseAnalytics;


    public EDiaryAdapter(FragmentManager supportFragmentManager, Context cntxt,FirebaseAnalytics mFirebaseAnalytics) {
        super(supportFragmentManager);
        this.cntxt=cntxt;
        this.mFirebaseAnalytics=mFirebaseAnalytics;

    }

    @Override
    public Fragment getItem(int position) {
        int pos=position;
        if(pos==0)
        {
            mFirebaseAnalytics.logEvent(cntxt.getResources().getString(R.string.Message_From_Teacher_Tab_Selected),new Bundle());
            FromTeacherFragment fromTeacherFragment;
            fromTeacherFragment = new FromTeacherFragment();
            return fromTeacherFragment;


        }
        else
        {
            mFirebaseAnalytics.logEvent(cntxt.getResources().getString(R.string.Message_To_Teacher_Tab_Selected),new Bundle());
            ToTeacherFragmentNewAPi toTeacherFragment=new ToTeacherFragmentNewAPi();

            return toTeacherFragment;
        }
    }


    @Override
    public int getCount() {
        return 2;
    }

    @Nullable
    @Override
    public CharSequence getPageTitle(int position) {
        String header="";
        switch (position)
        {

            case 0:
            {
                header= cntxt.getResources().getString(R.string.message_fromteacher_header);
                break;
            }
            case 1:
            {
                header= cntxt.getResources().getString(R.string.message_toteacher_header);
                break;
            }
        }


        return header;
    }
}
