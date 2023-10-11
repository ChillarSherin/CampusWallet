package com.chillarcards.campuswallet.payments.Preorder;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
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
import com.chillarcards.campuswallet.databinding.LayoutRechargeHistoryBinding;
import com.chillarcards.campuswallet.databinding.PreordertimingsBinding;
import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.List;

import com.chillarcards.campuswallet.application.CampusWallet;
import com.chillarcards.campuswallet.application.Constants;
import com.chillarcards.campuswallet.application.CustomConnectionBuddyActivity;
import com.chillarcards.campuswallet.application.PrefManager;
import com.chillarcards.campuswallet.networkmodels.OnlineTransactions.OnlineTransactions;
import com.chillarcards.campuswallet.networkmodels.OnlineTransactions.OnlineTransactions_Code;
import com.chillarcards.campuswallet.networkmodels.OnlineTransactions.OnlineTransactions_Status;


@SuppressWarnings("ALL")
public class RechargeHistory extends CustomConnectionBuddyActivity {


    private float offset;
    private boolean flipped;

    String parentPh,s_Id;
    ProgressBar progressBar;

    RecyclerView mRecyclerView;

    private List<String> transId = new ArrayList<String>();
    private List<String> createdOn = new ArrayList<String>();
    private List<String> amount = new ArrayList<String>();
    List<String> Status = new ArrayList<String>();
    List<String> downloadStatus= new ArrayList<String>();
    List<String> downloaddate= new ArrayList<String>();

    private RechargeHistoryAdapter mAdapter;
    private Activity activity;
    Button Reload;
    TextView Txt_Content;
    ImageView ErrorImage;

    private LayoutRechargeHistoryBinding binding;
    Button ReloadBTN,GoBackBTN;
    TextView ErrorMessageTV,CodeErrorTV,NodataTV;

    PrefManager prefManager;
    String phoneNum,studentId,Studentname;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
       // setContentView(R.layout.layout_recharge_history);
        binding = LayoutRechargeHistoryBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        prefManager=new PrefManager(RechargeHistory.this);
        binding.HeaderTV.setText(getResources().getString(R.string.rechrge_history));
        binding.BackIV.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });

//        Fabric.with(this, new Answers());
        activity=this;
        progressBar= (ProgressBar) findViewById(R.id.ProgressBar);

        findViewById(R.id.ErrorLL).setVisibility(View.GONE);
        findViewById(R.id.NodataLL).setVisibility(View.GONE);
        mRecyclerView = (RecyclerView) findViewById(R.id.list);
//        mRecyclerView.setLayoutManager(new WrapContentLinearLayoutManager(activity, LinearLayoutManager.VERTICAL, false));

        Reload.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent i = new Intent(getApplicationContext(), RechargeHistory.class);
                i.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity(i);
                finish();
                overridePendingTransition(R.anim.fade_in, R.anim.fade_out);
            }
        });


        phpGetonlineTransactions();

    }


    public void phpGetonlineTransactions()
    {
        // progressBar.setVisibility(View.VISIBLE);
        phoneNum = prefManager.getUserPhone();  //getting phone number from shared preference
        studentId = prefManager.getStudentId();
        Studentname = prefManager.getStudentName();
        final String tag_json_object = "r_onlineTransactions";
        String URL, parameters;
        parameters = "phoneNo=" + phoneNum + "&studentID=" + studentId+"transaction_category=card_recharge";
        URL = Constants.BASE_URL  + "r_onlineTransactions.php?" + parameters.replaceAll(" ", "%20");
        //System.out.println("CHECK---> URL " + URL);
        StringRequest jsonObjectRequestLogin = new StringRequest(Request.Method.GET, URL, new Response.Listener<String>() {
            @Override
            public void onResponse(String jsonObject) {
                //  progressBar.setVisibility(View.GONE);
                //System.out.println("CHECK---> Response " + jsonObject);
                //findViewById(R.id.progressLayout).setVisibility(View.GONE);
                Gson gson = new Gson();

                OnlineTransactions model = gson.fromJson(jsonObject, OnlineTransactions.class);
                OnlineTransactions_Status status = model.getStatus();
                String code = status.getCode();
                if (code.equals("200")){

                    transId.clear();
                    createdOn.clear();
                    amount.clear();
                    Status.clear();
                    downloadStatus.clear();
                    downloaddate.clear();

                    List<OnlineTransactions_Code> transactionCode =model.getCode();
                    if (transactionCode.size() > 0) {

                        for (int i = 0; i < transactionCode.size(); i++) {

                            OnlineTransactions_Code codeonline = transactionCode.get(i);
                            transId.add(codeonline.getTransactionID());
                            createdOn.add(codeonline.getOnlineTransactionCreatedOn());
                            amount.add(codeonline.getAmount());
                            downloadStatus.add(codeonline.getDownloadStatus());
                            downloaddate.add(codeonline.getDownloadDate());
                            Status.add(codeonline.getStatus());

                        }
                        mRecyclerView.setVisibility(View.VISIBLE);
                        mRecyclerView.setLayoutManager(new LinearLayoutManager(activity, LinearLayoutManager.VERTICAL, false));                        mRecyclerView.setItemAnimator(new DefaultItemAnimator());
                        mRecyclerView.setNestedScrollingEnabled(false);
                        mAdapter = new RechargeHistoryAdapter(transId, createdOn, amount, Status, downloadStatus, downloaddate,
                                activity, R.layout.item_rech_history, getApplicationContext());
                        mRecyclerView.setAdapter(mAdapter);
                        mRecyclerView.setNestedScrollingEnabled(false);
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

                Toast.makeText(RechargeHistory.this, "Network Error. Please Try Again!", Toast.LENGTH_SHORT).show();
            }
        });
        jsonObjectRequestLogin.setRetryPolicy(new DefaultRetryPolicy(50000, 0, DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
        CampusWallet.getInstance().addToRequestQueue(jsonObjectRequestLogin, tag_json_object);
    }


}
