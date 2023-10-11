package com.chillarcards.campuswallet.studentselection;

import android.app.Activity;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.os.Bundle;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
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
import com.chillarcards.campuswallet.databinding.LayoutRechargeHistoryBinding;
import com.chillarcards.campuswallet.databinding.LayoutStudentListActivityBinding;
import com.google.firebase.messaging.FirebaseMessaging;
import com.google.gson.Gson;
//import com.onesignal.OneSignal;

import java.util.List;

import com.chillarcards.campuswallet.application.CampusWallet;
import com.chillarcards.campuswallet.application.Constants;
import com.chillarcards.campuswallet.application.CustomConnectionBuddyActivity;
import com.chillarcards.campuswallet.application.PrefManager;
import com.chillarcards.campuswallet.networkmodels.studentlist.Code;
import com.chillarcards.campuswallet.networkmodels.studentlist.OnesignalKey;
import com.chillarcards.campuswallet.networkmodels.studentlist.Status;
import com.chillarcards.campuswallet.networkmodels.studentlist.StudentList;
import com.chillarcards.campuswallet.utils.CommonSSLConnection;

public class StudentListActivity extends CustomConnectionBuddyActivity {

    private LayoutStudentListActivityBinding binding;
    Button ReloadBTN,GoBackBTN;
    TextView ErrorMessageTV,CodeErrorTV,NodataTV;
    final String tag_json_object = "r_student_list";
    PrefManager prefManager;
    Activity activity;
    private String AppVersion;
    String phoneNum;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
       // setContentView(R.layout.layout_student_list_activity);
        binding = LayoutStudentListActivityBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        
        activity=this;
        prefManager = new PrefManager(StudentListActivity.this);
        phoneNum = prefManager.getUserPhone();
        prefManager.setCheckNewSwitch("Done");


    }

    @Override
    protected void onResume() {
        super.onResume();
        PackageManager manager = getApplicationContext().getPackageManager();
        PackageInfo info = null;
        try {
            info = manager.getPackageInfo(
                    getPackageName(), 0);
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }
        AppVersion = info.versionName;
        Constants.BASE_URL=Constants.BASE_URL_ORIGINAL;
        setProgressBarVisible();

        LoadStudentList(phoneNum);
    }

    private void LoadStudentList(final String phoneNum) {


        String URL, parameters;
        parameters = "phoneNo=" + phoneNum;
        URL = Constants.BASE_URL + "r_student_list.php?" + parameters.replaceAll(" ", "%20");
        //System.out.println("CHECK---> URL " + URL);
        CommonSSLConnection commonSSLConnection=new CommonSSLConnection();
        StringRequest jsonObjectRequestLogin = new StringRequest(Request.Method.GET, URL, new Response.Listener<String>() {
            @Override
            public void onResponse(String jsonObject) {
                //System.out.println("CHECK---> Response " + jsonObject);
                setProgressBarGone();

                CampusWallet.getInstance().cancelPendingRequests(tag_json_object);

                Gson gson = new Gson();

                StudentList studentList = gson.fromJson(jsonObject,StudentList.class);

                Status status = studentList.getStatus();
                String code= status.getCode();

                if(code.equals("200")){

                    List<Code> myDataset= studentList.getCode();
                    if (myDataset.size()!=0)
                    {
                        for (int i=0;i<myDataset.size();i++)
                        {
                            List<OnesignalKey> onesignalKeys= myDataset.get(i).getOnesignalKeys();
                            for (int j=0;j<onesignalKeys.size();j++){
                                Log.i("Abhinand : ","onesignalkey");
                                OnesignalKey keyvalue=new OnesignalKey();
                                keyvalue =onesignalKeys.get(j);
                                if (keyvalue!=null) {
                                    Log.i("Abhinand : ","keyvalue");

                                    String KEY = keyvalue.getKey();
//                                    String VALUE = String.valueOf(keyvalue.getValue());
                                    FirebaseMessaging.getInstance().subscribeToTopic(KEY);
                                    //onesignalKeys.add(KEY + " : " + VALUE);
                                    //System.out.println("CHILLAR TEST NEW : "+KEY+"   "+VALUE);

//                                    OneSignal.sendTag(KEY, VALUE);
                                }
                            }
//                            OneSignal.sendTag("App version", AppVersion);
//                            OneSignal.sendTag("user_mobile", prefManager.getUserPhone());

                           FirebaseMessaging.getInstance().subscribeToTopic("App_version_"+AppVersion);
                           FirebaseMessaging.getInstance().subscribeToTopic("user_mobile_"+prefManager.getUserPhone());
                        }
                        binding.recyclerView.setHasFixedSize(true);
                        binding.recyclerView.setLayoutManager(new LinearLayoutManager(StudentListActivity.this, LinearLayoutManager.VERTICAL, false));
                        binding.recyclerView.setNestedScrollingEnabled(false);
                        StudentListAdapter mAdapter = new StudentListAdapter(myDataset,activity,StudentListActivity.this,mFirebaseAnalytics);
                        mAdapter.notifyDataSetChanged();
                        binding.recyclerView.setAdapter(mAdapter);
                    }
                    else
                    {
                        NodataTV.setText(getResources().getString(R.string.no_data_found));
                        GoBackBTN.setText(getResources().getString(R.string.go_back));
//                        ErrorImage.setBackgroundResource(R.drawable.nodata);
                        findViewById(R.id.NodataLL).setVisibility(View.VISIBLE);
                        GoBackBTN.setVisibility(View.GONE);
                    }


                }else{
                    setProgressBarGone();
                    String message = status.getMessage();
                    Toast.makeText(StudentListActivity.this, message, Toast.LENGTH_SHORT).show();
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
                setProgressBarGone();
                CampusWallet.getInstance().cancelPendingRequests(tag_json_object);
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
                VolleyLog.d("Object Error : ", volleyError.getMessage());
                Toast.makeText(StudentListActivity.this, getResources().getString(R.string.network_error_try), Toast.LENGTH_SHORT).show();
            }
        });

        jsonObjectRequestLogin.setRetryPolicy(new DefaultRetryPolicy(50000, 3, DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
//        RequestQueue requestQueue = Volley.newRequestQueue(this, commonSSLConnection.getHulkstack(StudentListActivity.this));
        CampusWallet.getInstance().addToRequestQueue(jsonObjectRequestLogin, tag_json_object);
//        requestQueue.add(jsonObjectRequestLogin);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        CampusWallet.getInstance().cancelPendingRequests(tag_json_object);
    }

    public void setProgressBarVisible(){
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE,
                WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE);
        binding.progressBar4.setVisibility(View.VISIBLE);

    }

    public void setProgressBarGone(){

        getWindow().clearFlags(WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE);
        binding.progressBar4.setVisibility(View.GONE);

    }

}
