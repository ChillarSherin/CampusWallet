package com.chillarcards.campuswallet.Payment.History;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.viewpager.widget.ViewPager;

import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.VolleyLog;
import com.android.volley.toolbox.StringRequest;
import com.chillarcards.campuswallet.R;
import com.chillarcards.campuswallet.databinding.ActivityPaymentHistoryBinding;
import com.google.android.material.tabs.TabLayout;
import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.List;

import com.chillarcards.campuswallet.NotificationCenter.DatabaseHandler;
import com.chillarcards.campuswallet.application.CampusWallet;
import com.chillarcards.campuswallet.application.Constants;
import com.chillarcards.campuswallet.application.CustomConnectionBuddyActivity;
import com.chillarcards.campuswallet.application.PrefManager;
import com.chillarcards.campuswallet.networkmodels.FeePaymentReport.Code;
import com.chillarcards.campuswallet.networkmodels.FeePaymentReport.FeePaymentReport;
import com.chillarcards.campuswallet.networkmodels.FeePaymentReport.Payment;
import com.chillarcards.campuswallet.networkmodels.FeePaymentReport.PaymentDetail;
import com.chillarcards.campuswallet.networkmodels.FeePaymentReport.Status;
import com.chillarcards.campuswallet.payments.cardtransaction.RefreshStatement;
import com.chillarcards.campuswallet.utils.CommonSSLConnection;

/*
Created By : Abhinand
Created Date : 09-10-2018
*/

public class PaymentHistoryActivity extends CustomConnectionBuddyActivity implements RefreshStatement {

    ViewPager HistoryVP;
    TabLayout HistoryTL;
    PaymentHistoryAdapter historyAdapter;
    PrefManager prefManager;
    String phoneNum,studentId;
    List<Payment> catagotyList;
    List<PaymentDetail> transactionList;

    final String tag_json_object = "r_online_transaction_history";

    int tabpos=0;
    DatabaseHandler db;
    private ActivityPaymentHistoryBinding binding;
    Button ReloadBTN,GoBackBTN;
    TextView ErrorMessageTV,CodeErrorTV,NodataTV;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //setContentView(R.layout.activity_payment_history);

        binding = ActivityPaymentHistoryBinding.inflate(getLayoutInflater());
        View view = binding.getRoot();
        setContentView(view);

        ReloadBTN = binding.getRoot().findViewById(R.id.ReloadBTN);
        ErrorMessageTV=binding.getRoot().findViewById(R.id.ErrorMessageTV);
        CodeErrorTV=binding.getRoot().findViewById(R.id.CodeErrorTV);
        GoBackBTN =binding.getRoot().findViewById(R.id.GoBackBTN);
        NodataTV =binding.getRoot().findViewById(R.id.NodataTV);


        getHandles(); //getting handles or initializing widgets

        prefManager = new PrefManager(PaymentHistoryActivity.this);
        catagotyList=new ArrayList<>();
        transactionList=new ArrayList<>();
        binding.HistoryPB.setVisibility(View.GONE);

        binding.HeaderTV.setText(getResources().getString(R.string.history_header));
        binding.BackIV.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });
        binding.preorderMenuLL.setVisibility(View.GONE);
        db = new DatabaseHandler(PaymentHistoryActivity.this);

    }
    public void getHandles()
    {
        HistoryVP=findViewById(R.id.PaymentHistoryContainerVP);
        HistoryTL=findViewById(R.id.PaymentHistoryTL);
    }

    @Override
    protected void onResume() {
        super.onResume();
        binding.preorderMenuLL.setVisibility(View.GONE);
        binding.StudentnameTV.setText(prefManager.getStudentName());
        binding.balancewalletTV.setText(getResources().getString(R.string.wallet_balance_home)+ prefManager.getWalletBalance());

        Bundle bundle=getIntent().getExtras();
        if (bundle!=null) {
            tabpos=bundle.getInt("tabpos");
        }

        OnlineTransactionHistoryPHP();
    }
    @Override
    protected void onDestroy() {
        super.onDestroy();
        CampusWallet.getInstance().cancelPendingRequests(tag_json_object);
    }
    private void OnlineTransactionHistoryPHP() {

        binding.HistoryPB.setVisibility(View.VISIBLE);
        phoneNum = prefManager.getUserPhone();  //getting phone number from shared preference
        studentId = prefManager.getStudentId();


        String URL, parameters;
        parameters = "phoneNo=" + phoneNum + "&studentID=" + studentId;
        URL = Constants.BASE_URL  + "r_fee_payment_report.php?" + parameters.replaceAll(" ", "%20");
        System.out.println("CHECK---> URL " + URL);
        final CommonSSLConnection commonSSLConnection=new CommonSSLConnection();
        StringRequest jsonObjectRequestLogin = new StringRequest(Request.Method.GET, URL, new Response.Listener<String>() {
            @Override
            public void onResponse(String jsonObject) {
                binding.HistoryPB.setVisibility(View.GONE);
                //System.out.println("CHECK---> Response " + jsonObject);
                //findViewById(R.id.progressLayout).setVisibility(View.GONE);
                Gson gson = new Gson();

                FeePaymentReport model = gson.fromJson(jsonObject, FeePaymentReport.class);
                Status status = model.getStatus();
                String code = status.getCode();

                if (code.equals("200")) {

                    Code code1 = model.getCode();
                    catagotyList.clear();
                    if (code1.getPayment().size()>0) {

                        // catagotyList=code1.getTrasactionCategories();
//                        TrasactionCategory all = new TrasactionCategory("0", "All");
//                        catagotyList.add(all);
                        catagotyList.addAll(code1.getPayment());
//                        for (TrasactionCategory category: catagotyList)
//                        {
//                            if (db.UpdateTransactionCategories(category)==0) {
//                                db.addTransactionCategory(category);
//                            }
//                        }

//                        catagotyList.remove(0);
//                    for (TrasactionCategory other : code1.getTrasactionCategories())
//                    {
//                        catagotyList.add(other);
//                    }

                        transactionList.clear();
                        transactionList = code1.getPaymentDetails();
//                        for (Transaction transaction: transactionList)
//                        {
//                            db.addTransactions(transaction);
//                        }
//                        transactionList.clear();
//                        transactionList=db.getAllTransactions();
//                        if (transactionList.size()>0) {
                            historyAdapter = new PaymentHistoryAdapter(getSupportFragmentManager(), catagotyList, transactionList,
                                    PaymentHistoryActivity.this, mFirebaseAnalytics,PaymentHistoryActivity.this);
                            HistoryVP.setAdapter(historyAdapter); // setting adapter to view pager

                            HistoryTL.setupWithViewPager(HistoryVP); //setting up tab with view pager
                            HistoryTL.setTabMode(TabLayout.MODE_SCROLLABLE);
                            HistoryTL.getTabAt(tabpos).select();
//                        }
//                        else
//                        {
//                            NodataTV.setText(getResources().getString(R.string.no_data_found));
//                            GoBackBTN.setText(getResources().getString(R.string.go_back));
////                        ErrorImage.setBackgroundResource(R.drawable.nodata);
//                            findViewById(R.id.NodataLL).setVisibility(View.VISIBLE);
//                            GoBackBTN.setVisibility(View.GONE);
//                        }
                    }
                    else
                    {
//                        catagotyList.addAll(code1.getTrasactionCategories());
//                        for (TrasactionCategory category: catagotyList)
//                        {
//                            if (db.UpdateTransactionCategories(category)==0) {
//                                db.addTransactionCategory(category);
//                            }
//                        }
//
//                        catagotyList.remove(0);
//                        transactionList.clear();
//                        transactionList = code1.getTransactions();
//                        for (Transaction transaction: transactionList)
//                        {
//                            db.addTransactions(transaction);
//                        }
//                        transactionList.clear();
//                        transactionList=db.getAllTransactions();
//                        if (transactionList.size()>0) {
//                            historyAdapter = new PaymentHistoryAdapter(getSupportFragmentManager(), catagotyList, transactionList, PaymentHistoryActivity.this, mFirebaseAnalytics);
//                            HistoryVP.setAdapter(historyAdapter); // setting adapter to view pager
//
//                            HistoryTL.setupWithViewPager(HistoryVP); //setting up tab with view pager
//                            HistoryTL.setTabMode(TabLayout.MODE_SCROLLABLE);
//                            HistoryTL.getTabAt(tabpos).select();
//                        }
//                        else {
                            NodataTV.setText(getResources().getString(R.string.no_data_found));
                            GoBackBTN.setText(getResources().getString(R.string.go_back));
//                        ErrorImage.setBackgroundResource(R.drawable.nodata);
                            findViewById(R.id.NodataLL).setVisibility(View.VISIBLE);
                            GoBackBTN.setVisibility(View.GONE);
//                        }
                    }

                }
                else if(code.equals("204")){

                    Code code1 = model.getCode();
                    catagotyList.clear();
                    if (code1.getPayment().size()>0) {

                        // catagotyList=code1.getTrasactionCategories();
//                        TrasactionCategory all = new TrasactionCategory("0", "All");
//                        catagotyList.add(all);
//                        catagotyList.addAll(code1.getTrasactionCategories());
                        catagotyList.addAll(code1.getPayment());
                        catagotyList.remove(0);
//                    for (TrasactionCategory other : code1.getTrasactionCategories())
//                    {
//                        catagotyList.add(other);
//                    }
                        transactionList = new ArrayList<>();
                        transactionList.clear();

                        historyAdapter = new PaymentHistoryAdapter(getSupportFragmentManager(), catagotyList, transactionList,
                                PaymentHistoryActivity.this,mFirebaseAnalytics,PaymentHistoryActivity.this);
                        HistoryVP.setAdapter(historyAdapter); // setting adapter to view pager

                        HistoryTL.setupWithViewPager(HistoryVP); //setting up tab with view pager
                        HistoryTL.setTabMode(TabLayout.MODE_SCROLLABLE);
                    }
                    else
                    {
                        NodataTV.setText(getResources().getString(R.string.no_data_found));
                        GoBackBTN.setText(getResources().getString(R.string.go_back));
//                        ErrorImage.setBackgroundResource(R.drawable.nodata);
                        findViewById(R.id.NodataLL).setVisibility(View.VISIBLE);
                        GoBackBTN.setVisibility(View.GONE);
                    }

                }

                else {

                    String message = status.getMessage();
                    Toast.makeText(PaymentHistoryActivity.this, message, Toast.LENGTH_SHORT).show();
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
                binding.HistoryPB.setVisibility(View.GONE);
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

                Toast.makeText(PaymentHistoryActivity.this, getResources().getString(R.string.network_error_try), Toast.LENGTH_SHORT).show();
            }
        });
        jsonObjectRequestLogin.setRetryPolicy(new DefaultRetryPolicy(50000, 3, DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
//        RequestQueue requestQueue = Volley.newRequestQueue(this, commonSSLConnection.getHulkstack(PaymentHistoryActivity.this));
        CampusWallet.getInstance().addToRequestQueue(jsonObjectRequestLogin, tag_json_object);
//        requestQueue.add(jsonObjectRequestLogin);

    }

    @Override
    public void onStatementRefresh() {
        OnlineTransactionHistoryPHP();
    }
}
