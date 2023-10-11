package com.chillarcards.campuswallet.payments.Preorder;

import static android.view.View.GONE;

import android.app.Activity;
import android.app.Dialog;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.IdRes;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.VolleyLog;
import com.android.volley.toolbox.StringRequest;
import com.chillarcards.campuswallet.R;
import com.chillarcards.campuswallet.databinding.PreordertimingsBinding;
import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.List;

import com.chillarcards.campuswallet.application.CampusWallet;
import com.chillarcards.campuswallet.application.Constants;
import com.chillarcards.campuswallet.application.CustomConnectionBuddyActivity;
import com.chillarcards.campuswallet.application.PrefManager;
import com.chillarcards.campuswallet.networkmodels.PreOrderTimingsModule.PreOderTimingCode;
import com.chillarcards.campuswallet.networkmodels.PreOrderTimingsModule.PreOderTimingDetails;
import com.chillarcards.campuswallet.networkmodels.PreOrderTimingsModule.PreOderTimingStatus;
import com.chillarcards.campuswallet.utils.CommonSSLConnection;
import info.hoang8f.android.segmented.SegmentedGroup;

@SuppressWarnings("ALL")
public class PreOrderTimmings extends CustomConnectionBuddyActivity {
    final String tag_json_object = "r_preorder_timings";
    private RecyclerView mRecyclerView;
    private PreOrderTimimgAdapter mAdapter;
    Button Reload;
    TextView Txt_Content;
    ImageView ErrorImage;
    private List<Integer> schoolID = new ArrayList<>();
    private List<Integer> preorderTimingID = new ArrayList<>();
    private final List<String> preorderTimingKey = new ArrayList<>();
    private final List<String> preorderTimingName = new ArrayList<>();
    private final List<String> preorderTimingStatus = new ArrayList<>();
//    Call<PreOderTimingDetails> call2=null;
    Activity activity;
    RadioButton today;
    RadioButton tomorrow;
    SegmentedGroup segmented2;
    String dayString;
    String DaySelection;
    Dialog NoInternet;
    int InternetFlag = 0;

    PrefManager prefManager;
    String phoneNum,studentId,Studentname;
    private PreordertimingsBinding binding;
    Button ReloadBTN,GoBackBTN;
    TextView ErrorMessageTV,CodeErrorTV,NodataTV;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.preordertimings);
        binding = PreordertimingsBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        activity=this;
        prefManager=new PrefManager(PreOrderTimmings.this);
        hideSoftKeyboard();
        findViewById(R.id.incProgressBar).setVisibility(View.VISIBLE);
        findViewById(R.id.ErrorLL).setVisibility(GONE);
        findViewById(R.id.NodataLL).setVisibility(GONE);

        binding.HeaderTV.setText(getResources().getString(R.string.preorder_title));
        binding.BackIV.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });

        mRecyclerView=(RecyclerView)findViewById(R.id.listviewid);

         segmented2 = (SegmentedGroup) findViewById(R.id.segmented2);
        segmented2.setTintColor(Color.WHITE);
//        segmented2.setTintColor(Color.parseColor("#FFD0FF3C"), Color.parseColor("#FF7B07B2"));



        today=(RadioButton)findViewById(R.id.button21);
        tomorrow=(RadioButton)findViewById(R.id.button22);

        today=(RadioButton)findViewById(R.id.button21);
        tomorrow=(RadioButton)findViewById(R.id.button22);
        today.setTextColor(Color.parseColor("#2a8ad8"));
        tomorrow.setTextColor(Color.parseColor("#FFFFFF"));
        today.setChecked(true);
        DaySelection=today.getText().toString();
        Constants.daySelected=DaySelection;
        segmented2.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, @IdRes int checkedId) {


                if (today.isChecked()) {
                    mFirebaseAnalytics.logEvent(getResources().getString(R.string.Pre_Order_Today_Button_clicked),new Bundle());
//                    Toast.makeText(activity, "  " + today.getText().toString(), Toast.LENGTH_SHORT).show();
                    DaySelection=today.getText().toString();
                    tomorrow.setTextColor(Color.parseColor("#FFFFFF"));
                    today.setTextColor(Color.parseColor("#2a8ad8"));
                    MenuList("1");

                }else {
                    mFirebaseAnalytics.logEvent(getResources().getString(R.string.Pre_Order_Tomorrow_Button_clicked),new Bundle());
//                    Toast.makeText(activity, "  " + tomorrow.getText().toString(), Toast.LENGTH_SHORT).show();
                    DaySelection=tomorrow.getText().toString();
                    today.setTextColor(Color.parseColor("#FFFFFF"));
                    tomorrow.setTextColor(Color.parseColor("#2a8ad8"));
                    MenuList("2");

                }
                //System.out.println("CHILLAR DAY : "+DaySelection);
                Constants.daySelected=DaySelection;
            }
        });
    }

    @Override
    public void onBackPressed() {
        Intent i=new Intent(getApplicationContext(),PreOrderSelection.class);
        i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK|Intent.FLAG_ACTIVITY_CLEAR_TOP);
        startActivity(i);
        finish();
        overridePendingTransition(R.anim.fade_in, R.anim.fade_out);
        finish();
       // super.onBackPressed();
    }

    public void hideSoftKeyboard() {
        if(getCurrentFocus()!=null) {
            InputMethodManager inputMethodManager = (InputMethodManager) getSystemService(INPUT_METHOD_SERVICE);
            inputMethodManager.hideSoftInputFromWindow(getCurrentFocus().getWindowToken(), 0);
        }
    }
    @Override
    protected void onStart() {
        super.onStart();

    }

    @Override
    protected void onStop() {
        super.onStop();

    }


    @Override
    protected void onResume() {
        super.onResume();
        phoneNum = prefManager.getUserPhone();  //getting phone number from shared preference
        studentId = prefManager.getStudentId();
        Studentname = prefManager.getStudentName();

        if (today.isChecked()) {
            MenuList("1");
        }else {
            MenuList("2");
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        CampusWallet.getInstance().cancelPendingRequests(tag_json_object);
    }

    public void MenuList(final String day)
    {
        // progressBar.setVisibility(View.VISIBLE);


        String URL, parameters;
        parameters = "phoneNo=" + phoneNum + "&studentID=" + studentId+"&daySelected="+day;
        URL = Constants.BASE_URL  + "r_preorder_timings.php?" + parameters.replaceAll(" ", "%20");
        //System.out.println("CHECK---> URL " + URL);
        CommonSSLConnection commonSSLConnection=new CommonSSLConnection();
        StringRequest jsonObjectRequestLogin = new StringRequest(Request.Method.GET, URL, new Response.Listener<String>() {
            @Override
            public void onResponse(String jsonObject) {
                findViewById(R.id.incProgressBar).setVisibility(View.GONE);
                //System.out.println("CHECK---> Response " + jsonObject);
                //findViewById(R.id.progressLayout).setVisibility(View.GONE);
                Gson gson = new Gson();

                PreOderTimingDetails model = gson.fromJson(jsonObject, PreOderTimingDetails.class);
                PreOderTimingStatus status = model.getStatus();
                String code = status.getCode();
                if (code.equals("200"))
                {

                    schoolID.clear();
                    preorderTimingKey.clear();
                    preorderTimingStatus.clear();
                    preorderTimingName.clear();
                    preorderTimingID.clear();

                    findViewById(R.id.ErrorLL).setVisibility(View.GONE);
                    Log.i("shanil ", String.valueOf(model.getStatus()));
                    List<PreOderTimingCode> relationship=model.getCode();
                    if (relationship.size()>0) {
                        for (int i = 0; i < relationship.size(); i++) {

                            PreOderTimingCode relncode=relationship.get(i);
                            schoolID.add(Integer.valueOf(relncode.getSchoolID()));
                            preorderTimingKey.add(relncode.getPreorderTimingKey());
                            preorderTimingStatus.add(relncode.getPreorderTimingStatus());
                            preorderTimingID.add(Integer.valueOf(relncode.getPreorderTimingID()));
                            preorderTimingName.add(relncode.getPreorderTimingName());

                        }
                        try {
                            //System.out.println("ArraySizeHere " + DaySelection);
                            if (schoolID.size() > 0) {

                                mRecyclerView.setVisibility(View.VISIBLE);
                                mRecyclerView.setLayoutManager(new GridLayoutManager(PreOrderTimmings.this, 2));
                                mRecyclerView.setItemAnimator(new DefaultItemAnimator());
                                mRecyclerView.setNestedScrollingEnabled(false);
                                mAdapter = new PreOrderTimimgAdapter(preorderTimingID, schoolID, preorderTimingKey, preorderTimingStatus,
                                        preorderTimingName,day, activity, R.layout.timings_layout, getApplicationContext(),mFirebaseAnalytics);
                                mAdapter.notifyDataSetChanged();
                                mRecyclerView.setAdapter(mAdapter);
                                findViewById(R.id.NodataLL).setVisibility(GONE);

                            } else {

                                mRecyclerView.setVisibility(GONE);
                                NodataTV.setText(getResources().getString(R.string.no_data_found));
                                GoBackBTN.setText(getResources().getString(R.string.go_back));
//                        ErrorImage.setBackgroundResource(R.drawable.nodata);
                                findViewById(R.id.NodataLL).setVisibility(View.VISIBLE);
                                GoBackBTN.setVisibility(View.GONE);
                            }
                        } catch (Exception e) // no guruji
                        {
                            //System.out.println("ExceptiomHere "+e.toString());
                            e.printStackTrace();

                        }
                    }else {
                        mRecyclerView.setVisibility(GONE);
                        NodataTV.setText(getResources().getString(R.string.no_data_found));
                        GoBackBTN.setText(getResources().getString(R.string.go_back));
//                        ErrorImage.setBackgroundResource(R.drawable.nodata);
                        findViewById(R.id.NodataLL).setVisibility(View.VISIBLE);
                        GoBackBTN.setVisibility(View.GONE);
                    }
                }
                else if(code.equals("400")){

//                    ErrorImage.setBackgroundResource(R.drawable.server_issue);
                    // Txt_Content.setText("something went wrong - purely technical, give us a minute.");
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

                    //ADD SNACKBAR LAYOUT
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
                //progressBar.setVisibility(View.GONE);
                findViewById(R.id.incProgressBar).setVisibility(View.VISIBLE);
                VolleyLog.d("Object Error : ", volleyError.getMessage());
                //ADD SNACKBAR LAYOUT
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

                Toast.makeText(PreOrderTimmings.this, getResources().getString(R.string.network_error_try), Toast.LENGTH_SHORT).show();
            }
        });
        jsonObjectRequestLogin.setRetryPolicy(new DefaultRetryPolicy(50000, 3, DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
//        RequestQueue requestQueue = Volley.newRequestQueue(this, commonSSLConnection.getHulkstack(PreOrderTimmings.this));
        CampusWallet.getInstance().addToRequestQueue(jsonObjectRequestLogin, tag_json_object);
//        requestQueue.add(jsonObjectRequestLogin);
    }
}
