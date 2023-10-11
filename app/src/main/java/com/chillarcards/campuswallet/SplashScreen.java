package com.chillarcards.campuswallet;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.chillarcards.campuswallet.R;

import com.chillarcards.campuswallet.application.PrefManager;
import com.chillarcards.campuswallet.home.HomeActivity;
import com.chillarcards.campuswallet.login.LoginActivity;
import com.chillarcards.campuswallet.studentselection.StudentListActivity;

/**
 * Created by Codmob on 12-06-18.
 */

public class SplashScreen extends AppCompatActivity {

    private static final int TIME = 1 * 2000;// 2 seconds
    Boolean isInternetPresent = false;
    String version;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
//        Fabric.with(this, new Crashlytics());
        setContentView(R.layout.layout_splash_activity);

        final PrefManager prefManager = new PrefManager(SplashScreen.this);


        new Handler().postDelayed(new Runnable() {

            @Override
            public void run() {


                if(prefManager.isVersion3_2()) {

                    if (prefManager.isNumberVerified()) {


                        if (prefManager.getIsStudentSelected()) {


                            /*Launch Home*/
                            Intent i = new Intent(getApplicationContext(), HomeActivity.class);
                            startActivity(i);
                            finish();


                        } else {
                            Intent i = new Intent(getApplicationContext(), StudentListActivity.class);
                            startActivity(i);
                            finish();
                        }


                    } else {

                        Intent i = new Intent(getApplicationContext(), LoginActivity.class);
                        startActivity(i);
                        finish();
                    }
                }else{
                    Intent i = new Intent(getApplicationContext(), LoginActivity.class);
                    startActivity(i);
                    finish();
                }


            }
        }, TIME);
    }
}
