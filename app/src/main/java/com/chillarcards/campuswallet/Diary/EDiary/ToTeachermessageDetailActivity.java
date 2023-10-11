//package com.chillarcards.campuswallet.Diary.EDiary;
//
//import android.app.Activity;
//import android.support.design.widget.FloatingActionButton;
//import android.support.v4.app.FragmentTransaction;
//import android.support.v7.app.AppCompatActivity;
//import android.os.Bundle;
//import android.support.v7.widget.DefaultItemAnimator;
//import android.support.v7.widget.LinearLayoutManager;
//import android.support.v7.widget.RecyclerView;
//import android.util.Log;
//import android.view.View;
//import android.widget.ImageView;
//import android.widget.ProgressBar;
//import android.widget.TextView;
//import android.widget.Toast;
//
//import com.android.volley.DefaultRetryPolicy;
//import com.android.volley.Request;
//import com.android.volley.Response;
//import com.android.volley.VolleyError;
//import com.android.volley.VolleyLog;
//import com.android.volley.toolbox.StringRequest;
//import com.chillarcards.campuswallet.R;
//import com.google.gson.Gson;
//
//import java.util.List;
//
//import butterknife.BindView;
//import butterknife.ButterKnife;
//import butterknife.OnClick;
//import com.chillarcards.campuswallet.Diary.LeaveRequest.CallBack;
//import com.chillarcards.campuswallet.application.CampusWallet;
//import com.chillarcards.campuswallet.application.Constants;
//import com.chillarcards.campuswallet.application.PrefManager;
//import com.chillarcards.campuswallet.networkmodels.ParentMessages.Code;
//import com.chillarcards.campuswallet.networkmodels.ParentMessages.MessageToTeacher;
//import com.chillarcards.campuswallet.networkmodels.ParentMessages.Status;
//
//public class ToTeachermessageDetailActivity extends AppCompatActivity implements CallBack {
//
//    @BindView(R.id.TeacherMessageDetailsRV)
//    RecyclerView TeacherMessageDetailsRV;
//    @BindView(R.id.HeaderTV)
//    TextView binding.HeaderTV;
//    @BindView(R.id.BackIV)
//    ImageView binding.BackIV;
//    @BindView(R.id.progressBar)
//    ProgressBar progressBar;
//    @BindView(R.id.MessageToteacherAddFB)
//    FloatingActionButton MessageToteacherAddFB;
//    PrefManager prefManager;
//    private String phoneNum,studentId,Studentname,ReasonId,reason;
//    Activity activity;
//    private ToteacherMessageDetailAdapter mAdapter;
//
//    @Override
//    protected void onCreate(Bundle savedInstanceState) {
//        super.onCreate(savedInstanceState);
//        setContentView(R.layout.activity_to_teachermessage_detail);
//        ButterKnife.bind(this);
//        prefManager=new PrefManager(this);
//        activity=this;
//        progressBar.setVisibility(View.GONE);
//        binding.HeaderTV.setText("Message to Teacher");
//        binding.BackIV.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                onBackPressed();
//            }
//        });
//        Bundle b = getIntent().getExtras();
//        if (b!=null) {
//            ReasonId = b.getString("ReasonId");
//            reason = b.getString("reason");
//        }
//    }
//
//    @Override
//    protected void onResume() {
//        super.onResume();
//        phpGetAttendanceMain();
//    }
//    @OnClick(R.id.MessageToteacherAddFB)
//    public void submit() {
//        MessageToteacherDialog dialog=new MessageToteacherDialog();
//
//        FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
//        dialog.show(ft, MessageToteacherDialog.TAG);
//    }
//    public void phpGetAttendanceMain()
//    {
//        progressBar.setVisibility(View.VISIBLE);
//        phoneNum = prefManager.getUserPhone();  //getting phone number from shared preference
//        studentId = prefManager.getStudentId();
//        Studentname = prefManager.getUserName();
//        final String tag_json_object = "toteacher";
//
//        String URL, parameters;
//
//            parameters = "phoneNo=" + phoneNum + "&studentID=" + studentId+"&message_type_Id="+ReasonId;
//            URL = Constants.BASE_URL  + "r_parent_messages_by_type.php?" + parameters.replaceAll(" ", "%20");
//
//
//        //System.out.println("CHECK---> URL " + URL);
//        StringRequest jsonObjectRequestLogin = new StringRequest(Request.Method.GET, URL, new Response.Listener<String>() {
//            @Override
//            public void onResponse(String jsonObject) {
//                progressBar.setVisibility(View.GONE);
//                //System.out.println("CHECK---> Response " + jsonObject);
//                //findViewById(R.id.progressLayout).setVisibility(View.GONE);
//                Gson gson = new Gson();
//
//                MessageToTeacher model = gson.fromJson(jsonObject, MessageToTeacher.class);
//                Status status = model.getStatus();
//                String code = status.getCode();
//                if (code.equals("200"))
//                {
//
//                    List<Code> relationship=model.getCode();
//                    if (relationship.size()>0) {
//
//                        TeacherMessageDetailsRV.setVisibility(View.VISIBLE);
//                        TeacherMessageDetailsRV.setLayoutManager(new LinearLayoutManager(activity, LinearLayoutManager.VERTICAL, false));
//                        TeacherMessageDetailsRV.setItemAnimator(new DefaultItemAnimator());
//                        TeacherMessageDetailsRV.setNestedScrollingEnabled(false);
//                        mAdapter = new ToteacherMessageDetailAdapter(activity, relationship);
//                        TeacherMessageDetailsRV.setAdapter(mAdapter);
//
//
//                    }else {
////                        ErrorImage.setBackgroundResource(R.drawable.nodata);
////                        Txt_Content.setText(getResources().getString(R.string.nodata));
////                        Reload.setText(getResources().getString(R.string.goback));
////                        findViewById(R.id.ErrorLayoutnew).setVisibility(View.VISIBLE);
////                        Reload.setOnClickListener(new View.OnClickListener() {
////                            @Override
////                            public void onClick(View v) {
////
////                                onBackPressed();
////
////                            }
////                        });
//
//                    }
//                }
//                else if(code.equals("400")){
//
//////                    ErrorImage.setBackgroundResource(R.drawable.server_issue);
////                    Txt_Content.setText("something went wrong - purely technical, give us a minute.");
////                    Reload.setText(getResources().getString(R.string.go_back));
//////                    findViewById(R.id.ErrorLayoutnew).setVisibility(View.VISIBLE);
////                    Reload.setOnClickListener(new View.OnClickListener() {
////                        @Override
////                        public void onClick(View v) {
////
////
////                            onBackPressed();
////
////                        }
////                    });
//                }else if (code.equals("204")){
//
////                    Txt_Content.setText("No Leave Details Found");
////                    Reload.setText(getResources().getString(R.string.go_back));
//////                    ErrorImage.setBackgroundResource(R.drawable.nodata);
//////                    findViewById(R.id.ErrorLayoutnew).setVisibility(View.VISIBLE);
////                    Reload.setOnClickListener(new View.OnClickListener() {
////                        @Override
////                        public void onClick(View v) {
////
////                            onBackPressed();
////
////                        }
////                    });
//
//                }else if (code.equals("401")){
//
//////                    ErrorImage.setBackgroundResource(R.drawable.server_issue);
////                    Txt_Content.setText("Authentication Failed");
////                    Reload.setText(getResources().getString(R.string.go_back));
//////                    findViewById(R.id.ErrorLayoutnew).setVisibility(View.VISIBLE);
////                    Reload.setOnClickListener(new View.OnClickListener() {
////                        @Override
////                        public void onClick(View v) {
////
////
////                            onBackPressed();
////
////                        }
////                    });
//
//                }else if (code.equals("500")){
//
//                    //ADD SNACKBAR LAYOUT
////                    ErrorImage.setBackgroundResource(R.drawable.server_issue);
////                    Txt_Content.setText("something went wrong - purely technical, give us a minute.");
////                    Reload.setText("TRY AGAIN");
//////                    findViewById(R.id.ErrorLayoutnew).setVisibility(View.VISIBLE);
////                    Reload.setOnClickListener(new View.OnClickListener() {
////                        @Override
////                        public void onClick(View v) {
////
////
////                            Intent i=new Intent(getApplicationContext(),Attendence_Activity.class);
////                            i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
////                            startActivity(i);
////                            finish();
////                            overridePendingTransition(R.anim.fade_in, R.anim.fade_out);
////
////                        }
////                    });
//
//                }
//
//
//            }
//        }, new Response.ErrorListener() {
//            @Override
//            public void onErrorResponse(VolleyError volleyError) {
//                progressBar.setVisibility(View.GONE);
//                VolleyLog.d("Object Error : ", volleyError.getMessage());
//
//                Toast.makeText(ToTeachermessageDetailActivity.this, "Network Error. Please Try Again!", Toast.LENGTH_SHORT).show();
//            }
//        });
//        jsonObjectRequestLogin.setRetryPolicy(new DefaultRetryPolicy(50000, 0, DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
//        CampusWallet.getInstance().addToRequestQueue(jsonObjectRequestLogin, tag_json_object);
//    }
//
//    @Override
//    public void message(String message) {
//        phpGetAttendanceMain();
//    }
//}
