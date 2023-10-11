package com.chillarcards.campuswallet.payments.cardtransaction;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
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
import com.chillarcards.campuswallet.networkmodels.cardtransactionhistory.Cardhistory;
import com.chillarcards.campuswallet.networkmodels.cardtransactionhistory.Code;
import com.chillarcards.campuswallet.networkmodels.cardtransactionhistory.Status;
import com.chillarcards.campuswallet.networkmodels.cardtransactionhistory.Transaction;
import com.chillarcards.campuswallet.networkmodels.cardtransactionhistory.TransactionCategory;
import com.chillarcards.campuswallet.utils.CommonSSLConnection;


//import com.chillarcards.campuswallet.R;

public class CardTransactionActivity extends CustomConnectionBuddyActivity implements RefreshStatement{


    CardHistoryAdapter historyAdapter;
    PrefManager prefManager;
    String phoneNum,studentId,StudentNasme;
    List<TransactionCategory> catagotyList;
    List<Transaction> transactionList;

    final String tag_json_object = "r_card_transaction_history";

    ViewPager HistoryVP;
    TabLayout HistoryTL;
    Button ReloadBTN,GoBackBTN;
    TextView ErrorMessageTV,CodeErrorTV,NodataTV;
    String PosSpecific="";
    int posspec=0;
    boolean IsSpecific;
    DatabaseHandler databaseHandler;
    ActivityPaymentHistoryBinding binding;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_payment_history);
        binding = ActivityPaymentHistoryBinding.inflate(getLayoutInflater());
        View view = binding.getRoot();
        setContentView(view);

        ReloadBTN = binding.getRoot().findViewById(R.id.ReloadBTN);
        GoBackBTN =binding.getRoot().findViewById(R.id.GoBackBTN);
        NodataTV =binding.getRoot().findViewById(R.id.NodataTV);

        databaseHandler=new DatabaseHandler(CardTransactionActivity.this);
        HistoryVP = binding.PaymentHistoryContainerVP;
        HistoryTL = binding.PaymentHistoryTL;

        prefManager = new PrefManager(CardTransactionActivity.this);
        catagotyList=new ArrayList<>();
        transactionList=new ArrayList<>();
        binding.HistoryPB.setVisibility(View.GONE);

        binding.HeaderTV.setText(getResources().getString(R.string.card_transaction));
        binding.BackIV.setOnClickListener(v -> onBackPressed());
        binding.preorderMenuIV.setOnClickListener(v -> menuClickFn());
    }

public void menuClickFn()
{
    Intent intent=new Intent(CardTransactionActivity.this, OutletMenuActivity.class);
    intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
    intent.addFlags(Intent.FLAG_ACTIVITY_BROUGHT_TO_FRONT);
    startActivity(intent);
}
    @Override
    protected void onResume() {
        super.onResume();
        Bundle bundle=getIntent().getExtras();
        if (bundle!=null) {
            posspec=bundle.getInt("posspec");
        }
        if (!prefManager.getPreorderAvailable().equalsIgnoreCase("0"))
        {
            binding.preorderMenuLL.setVisibility(View.VISIBLE);
        }
        else
        {
            binding.preorderMenuLL.setVisibility(View.GONE);
        }
        studentId=prefManager.getStudentId();

        CardTransactionHistoryPHP(1,"");

    }
    public void setPosSpecific(int pos)
    {

    }
    @Override
    protected void onDestroy() {
        super.onDestroy();
        CampusWallet.getInstance().cancelPendingRequests(tag_json_object);
    }
    private void CardTransactionHistoryPHP(int filter,String transactionID) {

        binding.HistoryPB.setVisibility(View.VISIBLE);
        phoneNum = prefManager.getUserPhone();  //getting phone number from shared preference
        studentId = prefManager.getStudentId();
        StudentNasme = prefManager.getStudentName();


        String URL, parameters;
        if (filter==1) {
            parameters = "phoneNo=" + phoneNum + "&studentID=" + studentId + "&page=1";
        }
        else
        {
            parameters = "phoneNo=" + phoneNum + "&studentID=" + studentId + "&page=1&last_transactionID="+transactionID;
        }
        URL = Constants.BASE_URL  + "r_card_transaction_history.php?" + parameters.replaceAll(" ", "%20");
        System.out.println("CHECK---> URL " + URL);
        final CommonSSLConnection commonSSLConnection=new CommonSSLConnection();
        StringRequest jsonObjectRequestLogin = new StringRequest(Request.Method.GET, URL, new Response.Listener<String>() {
            @Override
            public void onResponse(String jsonObject) {
                binding.HistoryPB.setVisibility(View.GONE);
                //System.out.println("CHECK---> Response " + jsonObject);
                Gson gson = new Gson();

                Cardhistory model = gson.fromJson(jsonObject, Cardhistory.class);
                Status status = model.getStatus();
                String code = status.getCode();

                if (code.equals("200")) {

                    Code code1 = model.getCode();
                    catagotyList.clear();
                    String walletbalance=code1.getCurrent_balance();
                    binding.balancewalletTV.setText(getResources().getString(R.string.wallet_balance_home)+walletbalance);
                    binding.StudentnameTV.setText(StudentNasme);

                    TransactionCategory all=new TransactionCategory("0","All");
                    catagotyList.add(all);
                    catagotyList.addAll(code1.getTransactionCategories());
                    transactionList.clear();
                    transactionList=code1.getTransactions();
//                    Collections.reverse(transactionList);
//                    for (Transaction transaction: transactionList) {
//                        //System.out.println("ABHINAND :: STATEMENT :: "+transaction.getTransactionID());
//                        databaseHandler.addWalletTransactions(transaction,studentId);
//                    }
//                    transactionList.clear();
//                    transactionList=databaseHandler.getAllWalletTransactions(studentId);

                    historyAdapter=new CardHistoryAdapter(getSupportFragmentManager(),catagotyList,transactionList,
                            CardTransactionActivity.this,mFirebaseAnalytics,CardTransactionActivity.this);
                    HistoryVP.setAdapter(historyAdapter); // setting adapter to view pager

                    HistoryTL.setupWithViewPager(HistoryVP); //setting up tab with view pager
                    HistoryTL.setTabMode(TabLayout.MODE_SCROLLABLE);

                    HistoryTL.getTabAt(posspec).select();

                }
                else  if (code.equals("204"))
                {
                    Code code1 = model.getCode();
                    catagotyList.clear();
                    String walletbalance=code1.getCurrent_balance();
                    binding.balancewalletTV.setText(getResources().getString(R.string.wallet_balance_home)+walletbalance);
                    binding.StudentnameTV.setText(StudentNasme);

                    TransactionCategory all=new TransactionCategory("0","All");
                    catagotyList.add(all);
                    catagotyList.addAll(code1.getTransactionCategories());

                    transactionList=new ArrayList<>();
                    transactionList.clear();
//                    transactionList=databaseHandler.getAllWalletTransactions(studentId);

                    historyAdapter=new CardHistoryAdapter(getSupportFragmentManager(),catagotyList,transactionList,
                            CardTransactionActivity.this,mFirebaseAnalytics,CardTransactionActivity.this);
                    HistoryVP.setAdapter(historyAdapter); // setting adapter to view pager

                    HistoryTL.setupWithViewPager(HistoryVP); //setting up tab with view pager
                    HistoryTL.setTabMode(TabLayout.MODE_SCROLLABLE);

                    HistoryTL.getTabAt(posspec).select();
                }
                else {

                    String message = status.getMessage();
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
                    Toast.makeText(CardTransactionActivity.this, message, Toast.LENGTH_SHORT).show();
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
                Toast.makeText(CardTransactionActivity.this, getResources().getString(R.string.network_error_try), Toast.LENGTH_SHORT).show();
            }
        });
        jsonObjectRequestLogin.setRetryPolicy(new DefaultRetryPolicy(50000, 3, DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
//        RequestQueue requestQueue = Volley.newRequestQueue(this, commonSSLConnection.getHulkstack(CardTransactionActivity.this));
        CampusWallet.getInstance().addToRequestQueue(jsonObjectRequestLogin, tag_json_object);
//        requestQueue.add(jsonObjectRequestLogin);

    }

    @Override
    public void onStatementRefresh() {
        studentId=prefManager.getStudentId();
//        transactionList.clear();
//        transactionList= databaseHandler.getAllWalletTransactions(studentId);
//        if (transactionList.size()>0) {
//
//            Transaction transaction=new Transaction();
//            transaction=databaseHandler.getLastWalletTransactionsID(studentId);
//
//            CardTransactionHistoryPHP(0,transaction.getTransactionID());
//        }
//        else
//        {
            CardTransactionHistoryPHP(1,"");
//        }
    }
}
