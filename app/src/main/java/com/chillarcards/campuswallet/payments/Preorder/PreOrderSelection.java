package com.chillarcards.campuswallet.payments.Preorder;

import android.app.Dialog;
import android.content.Intent;
import android.graphics.PorterDuff;
import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import androidx.appcompat.widget.Toolbar;

import android.view.MotionEvent;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.chillarcards.campuswallet.R;
import com.chillarcards.campuswallet.databinding.PreorderoneBinding;
import com.chillarcards.campuswallet.databinding.PreorderselectionBinding;
import com.squareup.picasso.Picasso;


import com.chillarcards.campuswallet.application.CustomConnectionBuddyActivity;

@SuppressWarnings("ALL")
public class PreOrderSelection extends CustomConnectionBuddyActivity {
    private float offset;
    private boolean flipped;
    private ImageView FrmTeacher;
    private ImageView ToTeacher;
    private RelativeLayout LaytFromTeacher;
    private RelativeLayout LaytToTeacher;
    CardView preorderItem,PreorderHistoryItem;
    TextView fromteacherTxtv,toteacherTxtv;
    Dialog NoInternet;
    int InternetFlag = 0;

    PreorderselectionBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.preorderselection);
//        Fabric.with(this, new Answers());
        binding = PreorderselectionBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        binding.HeaderTV.setText(getResources().getString(R.string.pre_order_caps));
        binding.BackIV.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });

        FrmTeacher = (ImageView)findViewById(R.id.imgfromteacher);
        ToTeacher= (ImageView) findViewById(R.id.imgtoteacher);
        LaytFromTeacher=(RelativeLayout)findViewById(R.id.LaytMsgFromTeacher);
        LaytToTeacher=(RelativeLayout)findViewById(R.id.LaytMsgToTeacher);
        fromteacherTxtv=(TextView)findViewById(R.id.fromteachertxtv);
        toteacherTxtv=(TextView)findViewById(R.id.toteachertxtv);
        preorderItem=(CardView)findViewById(R.id.preorderidcard);
        PreorderHistoryItem=(CardView)findViewById(R.id.preorderhistorycardid);




        //set the ontouch listener
        FrmTeacher.setOnTouchListener(new View.OnTouchListener() {

            @Override
            public boolean onTouch(View v, MotionEvent event) {

                switch (event.getAction()) {
                    case MotionEvent.ACTION_DOWN: {
                        ImageView view = (ImageView) v;
                        //overlay is black with transparency of 0x77 (119)
                        view.getDrawable().setColorFilter(0x77000000, PorterDuff.Mode.SRC_ATOP);
                        view.invalidate();
                        break;
                    }
                    case MotionEvent.ACTION_UP:
                    case MotionEvent.ACTION_CANCEL: {
                        ImageView view = (ImageView) v;
                        //clear the overlay
                        view.getDrawable().clearColorFilter();
                        view.invalidate();
                        break;
                    }
                }

                return false;
            }
        });



        preorderItem.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mFirebaseAnalytics.logEvent(getResources().getString(R.string.Pre_Order_Make_Order_Button_clicked),new Bundle());
                Intent intent=new Intent(getApplicationContext(),PreOrderTimmings.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity(intent);
                overridePendingTransition(R.anim.fade_in, R.anim.fade_out);
            }
        });
        PreorderHistoryItem.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mFirebaseAnalytics.logEvent(getResources().getString(R.string.Pre_Order_History_Button_clicked),new Bundle());
                Intent intent=new Intent(getApplicationContext(),PreOrderHistory_Activity.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity(intent);
                overridePendingTransition(R.anim.fade_in, R.anim.fade_out);
            }
        });



        //set the ontouch listener
        ToTeacher.setOnTouchListener(new View.OnTouchListener() {

            @Override
            public boolean onTouch(View v, MotionEvent event) {

                switch (event.getAction()) {
                    case MotionEvent.ACTION_DOWN: {
                        ImageView view = (ImageView) v;
                        //overlay is black with transparency of 0x77 (119)
                        view.getDrawable().setColorFilter(0x77000000, PorterDuff.Mode.SRC_ATOP);
                        view.invalidate();
                        break;
                    }
                    case MotionEvent.ACTION_UP:
                    case MotionEvent.ACTION_CANCEL: {
                        ImageView view = (ImageView) v;
                        //clear the overlay
                        view.getDrawable().clearColorFilter();
                        view.invalidate();
                        break;
                    }
                }

                return false;
            }
        });
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
    }

    @Override
    protected void onResume() {
        super.onResume();
        Picasso.get().load(R.drawable.preordericonnewone).placeholder(R.drawable.place_holder)
                .error(R.drawable.place_holder_error).into(FrmTeacher);


        Picasso.get().load(R.drawable.preorderhistory).placeholder(R.drawable.place_holder)
                .error(R.drawable.place_holder_error).into(ToTeacher);
    }

    @Override
    protected void onStart() {
        super.onStart();

    }

    @Override
    protected void onStop() {
        super.onStop();


    }


}