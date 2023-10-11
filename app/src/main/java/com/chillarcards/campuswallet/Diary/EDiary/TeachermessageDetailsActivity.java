package com.chillarcards.campuswallet.Diary.EDiary;

import android.Manifest;
import android.app.Activity;
import android.content.pm.PackageManager;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;
import android.os.Bundle;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
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
import com.chillarcards.campuswallet.databinding.ActivityTeachermessageDetailsBinding;
import com.chillarcards.campuswallet.databinding.ActivityTimeTableBinding;
import com.google.gson.Gson;

import java.util.List;

import com.chillarcards.campuswallet.application.CampusWallet;
import com.chillarcards.campuswallet.application.Constants;
import com.chillarcards.campuswallet.application.CustomConnectionBuddyActivity;
import com.chillarcards.campuswallet.application.PrefManager;
import com.chillarcards.campuswallet.networkmodels.SubjectMessage.Code;
import com.chillarcards.campuswallet.networkmodels.SubjectMessage.Status;
import com.chillarcards.campuswallet.networkmodels.SubjectMessage.SubTeacherMsg;
import com.chillarcards.campuswallet.utils.CommonSSLConnection;

public class TeachermessageDetailsActivity extends CustomConnectionBuddyActivity {
    private static final int MY_PERMISSIONS_REQUEST_READ_STORAGE = 123;
  
    private TeacherMessageDetailAdapter mAdapter;

    PrefManager prefManager;
    private String phoneNum,studentId,Studentname;
    Activity activity;
    String SubId,SubName;
    boolean flagTeacher;

    final String tag_json_object = "fromteacher";
    Integer messageID = -1;

    private ActivityTeachermessageDetailsBinding binding;
    Button ReloadBTN,GoBackBTN;
    TextView ErrorMessageTV,CodeErrorTV,NodataTV;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_teachermessage_details);
       
        binding = ActivityTeachermessageDetailsBinding.inflate(getLayoutInflater());
        View view = binding.getRoot();
        setContentView(view);

        ReloadBTN = binding.getRoot().findViewById(R.id.ReloadBTN);
        ErrorMessageTV=binding.getRoot().findViewById(R.id.ErrorMessageTV);
        CodeErrorTV=binding.getRoot().findViewById(R.id.CodeErrorTV);
        GoBackBTN =binding.getRoot().findViewById(R.id.GoBackBTN);
        NodataTV =binding.getRoot().findViewById(R.id.NodataTV);
        
        activity=this;
        binding.progressBar.setVisibility(View.GONE);
        Bundle b = getIntent().getExtras();
        if (b!=null) {
            SubId = b.getString("SubjectId");
            SubName = b.getString("SubjectName");
            flagTeacher = b.getBoolean("Flagteacher");
            messageID = b.getInt("messageID");

        }

        prefManager=new PrefManager(this);
        if (flagTeacher)
        {
            if (SubName.trim().length()>0 ||SubName.trim().equalsIgnoreCase(""))
            {
                binding.HeaderTV.setText(getResources().getString(R.string.class_teacher_messag));
            }
            else {
                binding.HeaderTV.setText(SubName + getResources().getString(R.string.sub_teacher_head));
            }
        }
        else
        {
            binding.HeaderTV.setText(SubName+getResources().getString(R.string.class_teacher_head));
        }

        binding.BackIV.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });
        binding.TeacherMessageDetailsSRL.setColorSchemeResources(R.color.colorAccent);

    }

    @Override
    protected void onResume() {
        super.onResume();

        CheckPermission();
        binding.TeacherMessageDetailsSRL.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                CheckPermission();
                binding.TeacherMessageDetailsSRL.setRefreshing(false);
            }
        });
    }
    public void CheckPermission() {
        // Here, thisActivity is the current activity
        if (ContextCompat.checkSelfPermission(getApplicationContext(),
                Manifest.permission.WRITE_EXTERNAL_STORAGE)
                != PackageManager.PERMISSION_GRANTED && ContextCompat.checkSelfPermission(getApplicationContext(),
                Manifest.permission.READ_EXTERNAL_STORAGE)
                != PackageManager.PERMISSION_GRANTED) {
            //System.out.println("PERMISSION NOT GRANTED ");
            // Permission is not granted
            // Should we show an explanation?
            if (
                    ActivityCompat.shouldShowRequestPermissionRationale(TeachermessageDetailsActivity.this,
                            Manifest.permission.WRITE_EXTERNAL_STORAGE) &&
                            ActivityCompat.shouldShowRequestPermissionRationale(TeachermessageDetailsActivity.this,
                                    Manifest.permission.READ_EXTERNAL_STORAGE)) {
                // Show an explanation to the user *asynchronously* -- don't block
                // this thread waiting for the user's response! After the user
                // sees the explanation, try again to request the permission.
                //System.out.println("PERMISSION RETRY ");
                ActivityCompat.requestPermissions(TeachermessageDetailsActivity.this,
                        new String[]{ Manifest.permission.WRITE_EXTERNAL_STORAGE, Manifest.permission.READ_EXTERNAL_STORAGE},
                        MY_PERMISSIONS_REQUEST_READ_STORAGE);
            } else {
                // No explanation needed; request the permission
                ActivityCompat.requestPermissions(TeachermessageDetailsActivity.this,
                        new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE, Manifest.permission.READ_EXTERNAL_STORAGE},
                        MY_PERMISSIONS_REQUEST_READ_STORAGE);

                // MY_PERMISSIONS_REQUEST_READ_CONTACTS is an
                // app-defined int constant. The callback method gets the
                // result of the request.
                //System.out.println("PERMISSION REQUESTED ");
            }
        } else {
            // Permission has already been granted
            phpGetAttendanceMain(flagTeacher,SubId);
        }
    }
    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        switch (requestCode) {
            case MY_PERMISSIONS_REQUEST_READ_STORAGE: {
                // If request is cancelled, the result arrays are empty.
                //System.out.println("PERMISSION ARRAY SIZE : " + grantResults.length);
                if (grantResults.length > 0
                        && grantResults[0] == PackageManager.PERMISSION_GRANTED
                        && grantResults[1] == PackageManager.PERMISSION_GRANTED) {
                    // permission was granted, yay! Do the
                    // contacts-related task you need to do.
                    // dispatchTakePictureIntent();
                    //System.out.println("PERMISSION GRANTED ");

                    try {
                        phpGetAttendanceMain(flagTeacher,SubId);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                    //galleryAddPic();
                    // setPic();
                } else {
                    // permission denied, boo! Disable the
                    // functionality that depends on this permission.
                    //System.out.println("PERMISSION DENIED ");
                }
                return;
            }

            // other 'case' lines to check for other
            // permissions this app might request.
        }
    }
    public void phpGetAttendanceMain(boolean FlagTeacher,String subjectID)
    {
        binding.progressBar.setVisibility(View.VISIBLE);
        phoneNum = prefManager.getUserPhone();  //getting phone number from shared preference
        studentId = prefManager.getStudentId();
        Studentname = prefManager.getUserName();


        String URL, parameters;
        if (FlagTeacher){
            parameters = "phoneNo=" + phoneNum + "&studentID=" + studentId;
            URL = Constants.BASE_URL  + "r_class_teacher_messages.php?" + parameters.replaceAll(" ", "%20");
        }
        else {
            parameters = "phoneNo=" + phoneNum + "&studentID=" + studentId+"&subjectID=" + subjectID;
            URL = Constants.BASE_URL  + "r_subject_messages.php?" + parameters.replaceAll(" ", "%20");
        }
        CommonSSLConnection commonSSLConnection=new CommonSSLConnection();
        System.out.println("CHECK---> URL " + URL);
        StringRequest jsonObjectRequestLogin = new StringRequest(Request.Method.GET, URL, new Response.Listener<String>() {
            @Override
            public void onResponse(String jsonObject) {
                binding.progressBar.setVisibility(View.GONE);
                //System.out.println("CHECK---> Response " + jsonObject);
                //findViewById(R.id.progressLayout).setVisibility(View.GONE);
                Gson gson = new Gson();

                SubTeacherMsg model = gson.fromJson(jsonObject, SubTeacherMsg.class);
                Status status = model.getStatus();
                String code = status.getCode();
                if (code.equals("200"))
                {
                    List<Code> relationship = model.getCode();
                    if (relationship.size() > 0) {


                        if(messageID!=-1) {

                            int index = getIndexByname(String.valueOf(messageID), relationship);
                            System.out.println("Index: " + index);

                            if (index == -1) {

                                findViewById(R.id.NodataLL).setVisibility(View.GONE);
                                findViewById(R.id.ErrorLL).setVisibility(View.GONE);
                                binding.TeacherMessageDetailsRV.setLayoutManager(new LinearLayoutManager(activity,LinearLayoutManager.VERTICAL, false));
                                binding.TeacherMessageDetailsRV.setItemAnimator(new DefaultItemAnimator());
                                binding.TeacherMessageDetailsRV.setNestedScrollingEnabled(false);
                                mAdapter = new TeacherMessageDetailAdapter(activity, index, relationship,mFirebaseAnalytics);
                                binding.TeacherMessageDetailsRV.setAdapter(mAdapter);

                            } else {

                                findViewById(R.id.NodataLL).setVisibility(View.GONE);
                                findViewById(R.id.ErrorLL).setVisibility(View.GONE);
                                binding.TeacherMessageDetailsRV.setLayoutManager(new LinearLayoutManager(activity));
                                binding.TeacherMessageDetailsRV.smoothScrollToPosition(index);
                                binding.TeacherMessageDetailsRV.setItemAnimator(new DefaultItemAnimator());
                                binding.TeacherMessageDetailsRV.setNestedScrollingEnabled(false);
                                mAdapter = new TeacherMessageDetailAdapter(activity,index,relationship,mFirebaseAnalytics);
                                binding.TeacherMessageDetailsRV.setAdapter(mAdapter);
                            }
                        }else{

                            findViewById(R.id.NodataLL).setVisibility(View.GONE);
                            findViewById(R.id.ErrorLL).setVisibility(View.GONE);
                            binding.TeacherMessageDetailsRV.setLayoutManager(new LinearLayoutManager(activity, LinearLayoutManager.VERTICAL, false));
                            binding.TeacherMessageDetailsRV.setItemAnimator(new DefaultItemAnimator());
                            binding.TeacherMessageDetailsRV.setNestedScrollingEnabled(false);
                            mAdapter = new TeacherMessageDetailAdapter(activity, -1, relationship,mFirebaseAnalytics);
                            binding.TeacherMessageDetailsRV.setAdapter(mAdapter);

                        }




                    } else {
                        NodataTV.setText(getResources().getString(R.string.no_data_found));
                        GoBackBTN.setText(getResources().getString(R.string.go_back));
//                        ErrorImage.setBackgroundResource(R.drawable.nodata);
                        findViewById(R.id.NodataLL).setVisibility(View.VISIBLE);
                        GoBackBTN.setVisibility(View.GONE);


                    }

                }
                else if(code.equals("400")){

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

                    NodataTV.setText(getResources().getString(R.string.no_data_found));
                    GoBackBTN.setText(getResources().getString(R.string.go_back));
//                        ErrorImage.setBackgroundResource(R.drawable.nodata);
                    findViewById(R.id.NodataLL).setVisibility(View.VISIBLE);
                    GoBackBTN.setVisibility(View.GONE);
                }else if (code.equals("401")){

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
                binding.progressBar.setVisibility(View.GONE);
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

                Toast.makeText(TeachermessageDetailsActivity.this, getResources().getString(R.string.network_error_try), Toast.LENGTH_SHORT).show();
            }
        });
        jsonObjectRequestLogin.setRetryPolicy(new DefaultRetryPolicy(50000, 3, DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
//        RequestQueue requestQueue = Volley.newRequestQueue(this, commonSSLConnection.getHulkstack(TeachermessageDetailsActivity.this));
        CampusWallet.getInstance().addToRequestQueue(jsonObjectRequestLogin, tag_json_object);
//        requestQueue.add(jsonObjectRequestLogin);
    }
    @Override
    protected void onDestroy() {
        super.onDestroy();
        CampusWallet.getInstance().cancelPendingRequests(tag_json_object);
    }

    public int getIndexByname(String pName,List<Code> dataSet)
    {
        for(Code _item : dataSet)
        {
            if(_item.getMessageID().equals(pName))
                return dataSet.indexOf(_item);
        }
        return -1;
    }
}
