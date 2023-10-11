package com.chillarcards.campuswallet.Diary.TimeTable;

import android.app.Activity;
import android.app.Dialog;
import android.os.Bundle;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.appcompat.widget.Toolbar;

import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.VolleyLog;
import com.android.volley.toolbox.StringRequest;
import com.chillarcards.campuswallet.R;
import com.chillarcards.campuswallet.databinding.ActivityPreOrderTabedBinding;
import com.chillarcards.campuswallet.databinding.ActivitySubjectMsgTeacherBinding;
import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.List;

import com.chillarcards.campuswallet.application.CampusWallet;
import com.chillarcards.campuswallet.application.Constants;
import com.chillarcards.campuswallet.application.CustomConnectionBuddyActivity;
import com.chillarcards.campuswallet.application.PrefManager;
import com.chillarcards.campuswallet.networkmodels.SubjectMessage.Code;
import com.chillarcards.campuswallet.networkmodels.SubjectMessage.Status;
import com.chillarcards.campuswallet.networkmodels.SubjectMessage.SubTeacherMsg;
import com.chillarcards.campuswallet.utils.CommonSSLConnection;

@SuppressWarnings("ALL")
public class SubjectMsgTeacher extends CustomConnectionBuddyActivity{

  
    private float offset;
    private boolean flipped;
    private RecyclerView mRecyclerView;
    Activity activity;
    ProgressBar progressBar;
    private String parentPh,s_Id;
    private TeacherMessageAdapter mAdapter;
    List<Integer> notice_id = new ArrayList<Integer>();
    List<String> notice_date = new ArrayList<String>();
    List<String> notice_content = new ArrayList<String>();
    List<String> notice_by = new ArrayList<String>();
    List<String> notice_on = new ArrayList<String>();
    List<String> notice_due_date = new ArrayList<String>();
    private final List<String> imageUrl = new ArrayList<>();
    private final List<String> imageExists = new ArrayList<>();

    TextView messTxtv;
    String Sub_id,Sub_Name;

//    Call<SubTeacherMsg> call=null;

    private ConstraintLayout coordinatorLayout;
    final String tag_json_object = "r_subject_messages";
    Button Reload;
    TextView Txt_Content;
    ImageView ErrorImage;
    Dialog NoInternet;
    int InternetFlag = 0;
    PrefManager prefManager;
    String phoneNum,studentId;
    private ActivitySubjectMsgTeacherBinding binding;
    Button ReloadBTN,GoBackBTN;
    TextView ErrorMessageTV,CodeErrorTV,NodataTV;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
      //  setContentView(R.layout.activity_subject_msg_teacher);
        binding = ActivitySubjectMsgTeacherBinding.inflate(getLayoutInflater());
        View view = binding.getRoot();
        setContentView(view);

        ReloadBTN = binding.getRoot().findViewById(R.id.ReloadBTN);
        ErrorMessageTV=binding.getRoot().findViewById(R.id.ErrorMessageTV);
        CodeErrorTV=binding.getRoot().findViewById(R.id.CodeErrorTV);
        GoBackBTN =binding.getRoot().findViewById(R.id.GoBackBTN);
        NodataTV =binding.getRoot().findViewById(R.id.NodataTV);

        //        Fabric.with(this, new Answers());
        prefManager = new PrefManager(SubjectMsgTeacher.this);
        binding.HeaderTV.setText(getResources().getString(R.string.timetable_header));
        binding.BackIV.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });

        Bundle b = getIntent().getExtras();
        if (b!=null) {
            Sub_id = b.getString("SubjectId");
            Sub_Name = b.getString("SubjectName");
        }

        //System.out.println("SubjectIdHere " + Sub_id);

        messTxtv=(TextView)findViewById(R.id.messID);
        messTxtv.setVisibility(View.GONE);
        activity = this;
//        findViewById(R.id.ErrorLayoutnew).setVisibility(View.GONE);
//
//        Reload= (Button) findViewById(R.id.reload);
//        Txt_Content= (TextView) findViewById(R.id.txt_content);
//        ErrorImage= (ImageView) findViewById(R.id.img_no_image);

        progressBar = (ProgressBar) findViewById(R.id.ProgressBar);
        mRecyclerView = (RecyclerView) findViewById(R.id.list);
//        mRecyclerView.setLayoutManager(new WrapContentLinearLayoutManager(activity, LinearLayoutManager.VERTICAL, false));
        /*Bundle b = getIntent().getExtras();
        Sub_id = b.getString("SubjectId");
        Sub_Name = b.getString("SubjectName");
        setSupportActionBar((Toolbar) findViewById(R.id.toolbar));
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowTitleEnabled(true);
        getSupportActionBar().setTitle(Sub_Name+" Teacher Messages");
*/
        coordinatorLayout = (ConstraintLayout) findViewById(R.id.detail_content);
//        PopupInternet();

    }

//    private void PopupInternet()
//    {
//        NoInternet = new Dialog(this);
//        NoInternet.requestWindowFeature(Window.FEATURE_NO_TITLE);
//        NoInternet.setContentView(R.layout.activity_no_internet);
//        NoInternet.getWindow().setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
//        NoInternet.setCancelable(false);
//        NoInternet.setCanceledOnTouchOutside(false);
//    }
@Override
protected void onDestroy() {
    super.onDestroy();
    CampusWallet.getInstance().cancelPendingRequests(tag_json_object);
}
public void phpSubjectmessages(String SubjectID)
{
    progressBar.setVisibility(View.VISIBLE);
    phoneNum = prefManager.getUserPhone();  //getting phone number from shared preference
    studentId = prefManager.getStudentId();

    String URL, parameters;
    parameters = "phoneNo=" + phoneNum + "&studentID=" + studentId+ "&subjectID=" + SubjectID;
    URL = Constants.BASE_URL  + "r_subject_messages.php?" + parameters.replaceAll(" ", "%20");
    //System.out.println("CHECK---> URL " + URL);
    CommonSSLConnection commonSSLConnection=new CommonSSLConnection();
    StringRequest jsonObjectRequestLogin = new StringRequest(Request.Method.GET, URL, new Response.Listener<String>() {
        @Override
        public void onResponse(String jsonObject) {
            progressBar.setVisibility(View.GONE);
            //System.out.println("CHECK---> Response " + jsonObject);
            //findViewById(R.id.progressLayout).setVisibility(View.GONE);
            Gson gson = new Gson();

            SubTeacherMsg model = gson.fromJson(jsonObject, SubTeacherMsg.class);
            Status status = model.getStatus();
            String code = status.getCode();

            if (code.equals("200")) {

                findViewById(R.id.NodataLL).setVisibility(View.GONE);
                findViewById(R.id.ErrorLL).setVisibility(View.GONE);
                notice_id.clear();
                notice_by.clear();
                notice_content.clear();
                notice_date.clear();
                notice_on.clear();
                notice_due_date.clear();
                imageExists.clear();
                imageUrl.clear();

                List<Code> relationship = model.getCode();
                if (relationship.size() > 0) {
                    //System.out.println("SHANIL 2");
                    for (int i = 0; i < relationship.size(); i++) {

                        Code relncode = relationship.get(i);


                        notice_id.add(Integer.valueOf(relncode.getMessageID()));

                        notice_content.add(relncode.getMessageContent());

                        notice_date.add(relncode.getMessageCreatedOn());

                        notice_due_date.add(relncode.getMessageDueDate());

                        notice_by.add(relncode.getMessageCreatedBy());

                        imageExists.add(relncode.getImageExists()+"");

                        imageUrl.add(relncode.getImageUrl());


                        progressBar.setVisibility(View.GONE);
                    }
                    if (notice_id.size() > 0) {
                        //System.out.println("SHANIL 3 "+notice_id);
                        // err.setVisibility(View.GONE);
                        mRecyclerView.setVisibility(View.VISIBLE);
                        messTxtv.setVisibility(View.VISIBLE);
                        mRecyclerView.setLayoutManager(new LinearLayoutManager(activity, LinearLayoutManager.VERTICAL, false));
                        mRecyclerView.setItemAnimator(new DefaultItemAnimator());
                        mRecyclerView.setItemAnimator(new DefaultItemAnimator());
                        mRecyclerView.setNestedScrollingEnabled(false);
                        mAdapter = new TeacherMessageAdapter(notice_id, notice_content, notice_date, notice_by
                                , notice_due_date,imageExists,imageUrl, activity, R.layout.layout_teachermsg, getApplicationContext());
                        mRecyclerView.setAdapter(mAdapter);

                    } else {
                        NodataTV.setText(getResources().getString(R.string.no_data_found));
                        GoBackBTN.setText(getResources().getString(R.string.go_back));
                        findViewById(R.id.NodataLL).setVisibility(View.VISIBLE);
                        GoBackBTN.setVisibility(View.GONE);

                    }
                }

            } else if(code.equals("400")){

                mRecyclerView.setVisibility(View.GONE);
                ReloadBTN.setText(getResources().getString(R.string.go_back));
                findViewById(R.id.ErrorLL).setVisibility(View.VISIBLE);
                ErrorMessageTV.setText(getResources().getString(R.string.error_message_errorlayout));
                CodeErrorTV.setText(getResources().getString(R.string.code_attendance)+code);
                ReloadBTN.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {


                        onBackPressed();

                    }
                });

            }else if (code.equals("204")){

                mRecyclerView.setVisibility(View.GONE);
                NodataTV.setText(getResources().getString(R.string.no_data_found));
                GoBackBTN.setText(getResources().getString(R.string.go_back));
//                        ErrorImage.setBackgroundResource(R.drawable.nodata);
                findViewById(R.id.NodataLL).setVisibility(View.VISIBLE);
                GoBackBTN.setVisibility(View.GONE);


            }else if (code.equals("401")){
                mRecyclerView.setVisibility(View.GONE);
                ReloadBTN.setText(getResources().getString(R.string.go_back));
                findViewById(R.id.ErrorLL).setVisibility(View.VISIBLE);
                ErrorMessageTV.setText(getResources().getString(R.string.error_message_errorlayout));
                CodeErrorTV.setText(getResources().getString(R.string.code_attendance)+code);
                ReloadBTN.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {


                        onBackPressed();

                    }
                });


            }else if (code.equals("500")){

                mRecyclerView.setVisibility(View.GONE);
                ReloadBTN.setText(getResources().getString(R.string.go_back));
                findViewById(R.id.ErrorLL).setVisibility(View.VISIBLE);
                ErrorMessageTV.setText(getResources().getString(R.string.error_message_errorlayout));
                CodeErrorTV.setText(getResources().getString(R.string.code_attendance)+code);
                ReloadBTN.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {


                        onBackPressed();

                    }
                });


            }
        }
    }, new Response.ErrorListener() {
        @Override
        public void onErrorResponse(VolleyError volleyError) {
            progressBar.setVisibility(View.GONE);
            VolleyLog.d("Object Error : ", volleyError.getMessage());
            ReloadBTN.setText(getResources().getString(R.string.go_back));
            findViewById(R.id.ErrorLL).setVisibility(View.VISIBLE);
            ErrorMessageTV.setText(getResources().getString(R.string.error_message_admin));
            CodeErrorTV.setText(getResources().getString(R.string.error_message_errorlayout));

            ReloadBTN.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {


                    onBackPressed();

                }
            });

            Toast.makeText(SubjectMsgTeacher.this, getResources().getString(R.string.network_error_try), Toast.LENGTH_SHORT).show();
        }
    });
    jsonObjectRequestLogin.setRetryPolicy(new DefaultRetryPolicy(50000, 3, DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
//    RequestQueue requestQueue = Volley.newRequestQueue(this, commonSSLConnection.getHulkstack(SubjectMsgTeacher.this));
        CampusWallet.getInstance().addToRequestQueue(jsonObjectRequestLogin, tag_json_object);
//    requestQueue.add(jsonObjectRequestLogin);
}



    @Override
    public void onBackPressed() {

        super.onBackPressed();

    }

    @Override
    protected void onResume()
    {

        super.onResume();



        phpSubjectmessages(Sub_id);

    }



}
