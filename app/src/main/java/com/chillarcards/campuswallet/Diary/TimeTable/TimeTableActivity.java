package com.chillarcards.campuswallet.Diary.TimeTable;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.VolleyLog;
import com.android.volley.toolbox.StringRequest;
import com.chillarcards.campuswallet.R;
import com.chillarcards.campuswallet.databinding.ActivityTimeTableBinding;
import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;

import com.chillarcards.campuswallet.application.CampusWallet;
import com.chillarcards.campuswallet.application.Constants;
import com.chillarcards.campuswallet.application.CustomConnectionBuddyActivity;
import com.chillarcards.campuswallet.application.PrefManager;
import com.chillarcards.campuswallet.networkmodels.TimeTable.Code;
import com.chillarcards.campuswallet.networkmodels.TimeTable.Status;
import com.chillarcards.campuswallet.networkmodels.TimeTable.TimeTable;
import com.chillarcards.campuswallet.utils.CommonSSLConnection;

public class TimeTableActivity extends CustomConnectionBuddyActivity {


    List<String> list;
    private int todayposString;
    private int spinnerposition;
    private final ArrayList<HashMap<String, String>> timetablelist = new ArrayList<>();
    private String spinneritemno;
    private String parentPh,s_Id;
    private int datetoday;
    Activity activity;
    private final List<String> SubId = new ArrayList<>();
    private final List<String> SubName = new ArrayList<>();
    private final List<String> Teacher = new ArrayList<>();
    private final List<String> Day = new ArrayList<>();
    private final List<String> Order = new ArrayList<>();
    PrefManager prefManager;
    String phoneNum,studentId;
    private TimeTableAdapter mAdapter;

    final String tag_json_object = "r_time_table";

    private ActivityTimeTableBinding binding;
    Button ReloadBTN,GoBackBTN;
    TextView ErrorMessageTV,CodeErrorTV,NodataTV;
    
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_time_table);
       
        binding = ActivityTimeTableBinding.inflate(getLayoutInflater());
        View view = binding.getRoot();
        setContentView(view);

        ReloadBTN = binding.getRoot().findViewById(R.id.ReloadBTN);
        ErrorMessageTV=binding.getRoot().findViewById(R.id.ErrorMessageTV);
        CodeErrorTV=binding.getRoot().findViewById(R.id.CodeErrorTV);
        GoBackBTN =binding.getRoot().findViewById(R.id.GoBackBTN);
        NodataTV =binding.getRoot().findViewById(R.id.NodataTV);
        
        activity=this;

        binding.TimeTablePB.setVisibility(View.GONE);

        binding.HeaderTV.setText(getResources().getString(R.string.timetable_header));
        binding.BackIV.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });
        prefManager = new PrefManager(TimeTableActivity.this);

        list = new ArrayList<>();
        list.add(getResources().getString(R.string.monday));
        list.add(getResources().getString(R.string.tuesday));
        list.add(getResources().getString(R.string.wednesday));
        list.add(getResources().getString(R.string.thursday));
        list.add(getResources().getString(R.string.friday));
        list.add(getResources().getString(R.string.saturday));

        Calendar c = Calendar.getInstance();
        datetoday = (c.get(Calendar.DAY_OF_WEEK));
        //System.out.println("dateReal : "+datetoday);
        if(datetoday>7){
            datetoday=1;
        }

    }

    @Override
    protected void onResume() {
        super.onResume();
//        spinner Adapter

        binding.StudentnameTV.setText(prefManager.getStudentName());
        binding.StudentSTDTV.setText(prefManager.getStudentStandardDivision());


        ArrayAdapter<String> spinnerArrayAdapter = new ArrayAdapter<>(this, R.layout.item_spinner_white, list); //selected item will look like a spinner set from XML
        spinnerArrayAdapter.setDropDownViewResource(R.layout.item_spinner);
        binding.DayTimetableBTN.setAdapter(spinnerArrayAdapter);
        getSpinner();


    }
    public void getSpinner()
    {
        phoneNum = prefManager.getUserPhone();  //getting phone number from shared preference
        studentId = prefManager.getStudentId();
        todayposString=(datetoday - 2);
        //System.out.println("Todays'S dtae " + datetoday);

        binding.DayTimetableBTN.setSelection(todayposString);
        binding.DayTimetableBTN.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

                if(timetablelist.size()<0){
                    //System.out.println("NoDatTaTime " + "True");
                }
                //System.out.println("TimeTableListBeforeClear " + timetablelist);
                timetablelist.clear();
                //System.out.println("TimeTableListBeforeClear " + timetablelist);
                spinnerposition = binding.DayTimetableBTN.getSelectedItemPosition();
                spinneritemno = String.valueOf(spinnerposition + 1);


                //System.out.println("position is " + spinneritemno);
                String url = Constants.BASE_URL+"r_time_table.php?phoneNo="+phoneNum+"&studentID="+studentId+"&day="+spinneritemno;
                //System.out.println("Parameters : "+url);
                mFirebaseAnalytics.logEvent(getResources().getString(R.string.Time_Table_Day_Selected),new Bundle());
                phpTimeTable(url);

            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }

        });
    }
    public void phpTimeTable(String URL)
    {
        binding.TimeTablePB.setVisibility(View.VISIBLE);


        URL = URL.replaceAll(" ", "%20");
        //System.out.println("CHECK---> URL " + URL);
        CommonSSLConnection commonSSLConnection=new CommonSSLConnection();
        StringRequest jsonObjectRequestLogin = new StringRequest(Request.Method.GET, URL, new Response.Listener<String>() {
            @Override
            public void onResponse(String jsonObject) {
                binding.TimeTablePB.setVisibility(View.GONE);
                //System.out.println("CHECK---> Response " + jsonObject);
                //findViewById(R.id.progressLayout).setVisibility(View.GONE);
                Gson gson = new Gson();

                TimeTable model = gson.fromJson(jsonObject, TimeTable.class);
                Status status = model.getStatus();
                String code = status.getCode();

                if (code.equals("200")) {

                    Day.clear();
                    SubId.clear();
                    SubName.clear();
                    Teacher.clear();
                    Order.clear();
                    findViewById(R.id.ErrorLL).setVisibility(View.GONE);
                    findViewById(R.id.NodataLL).setVisibility(View.GONE);
                    List<Code> relationship = model.getCode();
                    if (relationship.size() > 0) {
                        for (int i = 0; i < relationship.size(); i++) {

                            Code relncode = relationship.get(i);
                            Day.add(relncode.getDay());
                            SubId.add(relncode.getSubjectId());
                            SubName.add(relncode.getSubjectName());
                            Teacher.add(relncode.getTeacher());
                            Order.add(relncode.getOrder());

                            binding.TimeTablePB.setVisibility(View.GONE);
                        }
                        try {

                            if (Day.size() > 0) {

                                //System.out.println("daysize : " + Day.size());
                                binding.TimetablemainRV.setVisibility(View.VISIBLE);
//                                TimetableListview.setLayoutManager(new LinearLayoutManager(getApplicationContext()));
                                binding.TimetablemainRV.setLayoutManager(new LinearLayoutManager(activity, LinearLayoutManager.VERTICAL, false));
                                binding.TimetablemainRV.setItemAnimator(new DefaultItemAnimator());
                                binding.TimetablemainRV.setNestedScrollingEnabled(false);
                                mAdapter = new TimeTableAdapter(SubId, SubName, Teacher, Day, Order, activity,
                                        R.layout.timetable_listitem, getApplicationContext(),mFirebaseAnalytics);

                                binding.TimetablemainRV.setAdapter(mAdapter);
                            } else {
                                //System.out.println("daysize2 : " + Day.size());
                                binding.TimetablemainRV.setVisibility(View.GONE);
                                NodataTV.setText(getResources().getString(R.string.no_data_found));
                                GoBackBTN.setText(getResources().getString(R.string.go_back));
//                        ErrorImage.setBackgroundResource(R.drawable.nodata);
                                findViewById(R.id.NodataLL).setVisibility(View.VISIBLE);
                                GoBackBTN.setVisibility(View.GONE);

                            }

                        } catch (Exception e) // no guruji
                        {
                            //System.out.println("ExceptiomHere " + e.toString());
                            e.printStackTrace();
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
                    } else {
                        binding.TimetablemainRV.setVisibility(View.GONE);
//                        ErrorLayout.setVisibility(View.VISIBLE);
                        String Msg = status.getMessage();
//                        Toast.makeText(TimeTable_Activity.this, "  " + Msg, Toast.LENGTH_SHORT).show();
                        NodataTV.setText(getResources().getString(R.string.no_data_found));
                        GoBackBTN.setText(getResources().getString(R.string.go_back));
//                        ErrorImage.setBackgroundResource(R.drawable.nodata);
                        findViewById(R.id.NodataLL).setVisibility(View.VISIBLE);
                        GoBackBTN.setVisibility(View.GONE);
                    }

                } else if(code.equals("400")){

                    binding.TimetablemainRV.setVisibility(View.GONE);
//                    ErrorLayout.setVisibility(View.VISIBLE);
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

                    binding.TimetablemainRV.setVisibility(View.GONE);
                    NodataTV.setText(getResources().getString(R.string.no_data_found));
                    GoBackBTN.setText(getResources().getString(R.string.go_back));
//                        ErrorImage.setBackgroundResource(R.drawable.nodata);
                    findViewById(R.id.NodataLL).setVisibility(View.VISIBLE);
                    GoBackBTN.setVisibility(View.GONE);

                }else if (code.equals("401")){
                    binding.TimetablemainRV.setVisibility(View.GONE);
//                    ErrorLayout.setVisibility(View.VISIBLE);
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

                    binding.TimetablemainRV.setVisibility(View.GONE);
//                    ErrorLayout.setVisibility(View.VISIBLE);
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
                binding.TimeTablePB.setVisibility(View.GONE);
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
                Toast.makeText(TimeTableActivity.this, getResources().getString(R.string.network_error_try), Toast.LENGTH_SHORT).show();
            }
        });
        jsonObjectRequestLogin.setRetryPolicy(new DefaultRetryPolicy(50000, 3, DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
//        RequestQueue requestQueue = Volley.newRequestQueue(this, commonSSLConnection.getHulkstack(TimeTableActivity.this));
        CampusWallet.getInstance().addToRequestQueue(jsonObjectRequestLogin, tag_json_object);
//        requestQueue.add(jsonObjectRequestLogin);
    }
    @Override
    protected void onDestroy() {
        super.onDestroy();
        CampusWallet.getInstance().cancelPendingRequests(tag_json_object);
    }
}
