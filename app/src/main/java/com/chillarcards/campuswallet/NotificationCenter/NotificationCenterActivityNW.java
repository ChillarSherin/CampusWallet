package com.chillarcards.campuswallet.NotificationCenter;

import android.app.Activity;
import android.app.ProgressDialog;
import android.os.Bundle;
import androidx.annotation.Nullable;

import com.chillarcards.campuswallet.databinding.ActivityEDiaryBinding;
import com.chillarcards.campuswallet.databinding.ActivityNoticeBoardBinding;
import com.chillarcards.campuswallet.databinding.NotificationListBinding;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.appcompat.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.chillarcards.campuswallet.R;
import com.google.firebase.auth.FirebaseAuth;

import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import com.chillarcards.campuswallet.application.CustomConnectionBuddyActivity;
import com.chillarcards.campuswallet.application.PrefManager;

/**
 * Created by user on 12/2/2017.
 */

public class NotificationCenterActivityNW extends CustomConnectionBuddyActivity {RecyclerView mRecyclerView;
    TextView noGroupsText;
    //    TextView Chillargroup;
//    TextView Codmobgroup;
//    TextView Chillarcodgroup;
    ArrayList<String> al = new ArrayList<>();
    int totalUsers = 0;
    ProgressDialog pd;
    private FloatingActionButton fab;

    private FirebaseAuth mAuth;
    private FirebaseAuth.AuthStateListener mAuthListener;
    static int SIGN_IN_REQUEST_CODE=100;
    String userName,id,s_Id,parentPh,PhNoWithCC;
    private Toolbar toolbar;
    private ProgressBar progressBar;
    private final List<String> GroupsName = new ArrayList<>();
    private final List<String> GroupsID = new ArrayList<>();
    List<String> Body=new ArrayList<>();
    List<String> notificationID=new ArrayList<>();
    List<String> Title=new ArrayList<>();
    List<String> pushTypeLists=new ArrayList<>();
    List<String> schoolIDList=new ArrayList<>();
    List<String> studentIDList=new ArrayList<>();
    List<String> Clicked=new ArrayList<>();
    String additionalData;
    JSONObject jsonobjct;
//    List<String> =new ArrayList<>();
//    String Clicked;
    private Notifications_list_adapterNW mAdapter;
    Activity activity;
    Button Reload;
    TextView Txt_Content;
    ImageView ErrorImage;
    String log,SchoolID,studentID,studentStandard;
    String DataString;

  
    PrefManager prefManager;


    Button GoBackBTN;
    TextView NodataTV;

    NotificationListBinding binding;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
       // setContentView(R.layout.notification_list);
        binding = NotificationListBinding.inflate(getLayoutInflater());
        View view = binding.getRoot();
        setContentView(view);

        GoBackBTN =binding.getRoot().findViewById(R.id.GoBackBTN);
        NodataTV =binding.getRoot().findViewById(R.id.NodataTV);

        prefManager=new PrefManager(NotificationCenterActivityNW.this);
        binding.HeaderTV.setText(getResources().getString(R.string.notificationcenter));
        binding.BackIV.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });


        activity=this;
        mRecyclerView = (RecyclerView)findViewById(R.id.groupsList);

        progressBar=(ProgressBar)findViewById(R.id.ProgressBar);
//        pd = new ProgressDialog(Groups.this);
//        pd.setMessage("Loading...");
//        pd.show();
        Reload= (Button) findViewById(R.id.reload);

        Txt_Content= (TextView) findViewById(R.id.txt_content);
        ErrorImage= (ImageView) findViewById(R.id.img_no_image);
        findViewById(R.id.ErrorLL).setVisibility(View.GONE);
        findViewById(R.id.NodataLL).setVisibility(View.GONE);
        mAuth = FirebaseAuth.getInstance();
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);



    }


    @Override
    protected void onResume() {
        super.onResume();

        DatabaseHandler db = new DatabaseHandler(this);

        progressBar.setVisibility(View.VISIBLE);

        s_Id=prefManager.getStudentId();
        parentPh=prefManager.getUserPhone();
        SchoolID=prefManager.getSChoolID();
        studentID=prefManager.getStudentId();
        studentStandard=prefManager.getStudentStandard();

        Body.clear();
        notificationID.clear();
        Title.clear();
        pushTypeLists.clear();
        Clicked.clear();
        List<Contact> contacts = db.getAllContacts(studentID,SchoolID,studentStandard);

        if (contacts.size()>0){

            findViewById(R.id.ErrorLL).setVisibility(View.GONE);
            progressBar.setVisibility(View.GONE);
            mRecyclerView.setLayoutManager(new LinearLayoutManager(NotificationCenterActivityNW.this));
            mAdapter = new Notifications_list_adapterNW(contacts, activity, R.layout.item_notification_center ,getApplicationContext(),mFirebaseAnalytics);
            mRecyclerView.setAdapter(mAdapter);
            mAdapter.notifyDataSetChanged();
        }
        else {
            NodataTV.setText(getResources().getString(R.string.no_new_message));
//                        ErrorImage.setBackgroundResource(R.drawable.nodata);
            findViewById(R.id.NodataLL).setVisibility(View.VISIBLE);
            GoBackBTN.setText(getResources().getString(R.string.go_back));
            findViewById(R.id.ErrorLL).setVisibility(View.GONE);
            GoBackBTN.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    onBackPressed();
                }
            });
        }
        //System.out.println("NOTI : 5  ");
        //System.out.println("SHANIL DATA3  : "+Body);
    }
}
