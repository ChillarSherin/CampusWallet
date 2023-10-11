package com.chillarcards.campuswallet.payments.Transactions;

import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;
import androidx.appcompat.app.AppCompatActivity;
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
import com.chillarcards.campuswallet.databinding.ActivityOnlineTransactionStatusBinding;
import com.chillarcards.campuswallet.databinding.LayoutRechargeHistoryBinding;
import com.chillarcards.campuswallet.databinding.PreordertimingsBinding;
import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.List;

import com.chillarcards.campuswallet.application.CampusWallet;
import com.chillarcards.campuswallet.application.Constants;
import com.chillarcards.campuswallet.application.CustomConnectionBuddyActivity;
import com.chillarcards.campuswallet.application.PrefManager;
import com.chillarcards.campuswallet.networkmodels.OnlineTransactionHistory.Code;
import com.chillarcards.campuswallet.networkmodels.OnlineTransactionHistory.Status;
import com.chillarcards.campuswallet.networkmodels.OnlineTransactionHistory.Transaction;
import com.chillarcards.campuswallet.networkmodels.OnlineTransactionHistory.TransactionHistory;
import com.chillarcards.campuswallet.utils.CommonSSLConnection;

public class OnlineTransactionStatusActivity extends CustomConnectionBuddyActivity {

    final String tag_json_object = "r_online_transaction_history";

    String phoneNum,studentId;

    PrefManager prefManager;
    List<Transaction> transactionList;
    private OnlineTransactionStatusAdapter mAdapter;
    private ActivityOnlineTransactionStatusBinding binding;
    Button ReloadBTN,GoBackBTN;
    TextView ErrorMessageTV,CodeErrorTV,NodataTV;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
       // setContentView(R.layout.activity_online_transaction_status);
        binding = ActivityOnlineTransactionStatusBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        
        prefManager = new PrefManager(OnlineTransactionStatusActivity.this);
        binding.HistoryPB.setVisibility(View.GONE);
        transactionList=new ArrayList<>();

        binding.HeaderTV.setText(getResources().getString(R.string.online_trans_status_header));
        binding.BackIV.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });
        binding.OnlineTransactionStatusSRL.setColorSchemeResources(R.color.colorAccent);
    }

    @Override
    protected void onResume() {
        super.onResume();
        binding.StudentnameTV.setText(prefManager.getStudentName());
        binding.balancewalletTV.setText(getResources().getString(R.string.wallet_balance_home)+ prefManager.getWalletBalance());

        OnlineTransactionHistoryPHP();
        binding.OnlineTransactionStatusSRL.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                OnlineTransactionHistoryPHP();
                binding.OnlineTransactionStatusSRL.setRefreshing(false);
            }
        });

    }
    private void OnlineTransactionHistoryPHP() {

        binding.HistoryPB.setVisibility(View.VISIBLE);
        phoneNum = prefManager.getUserPhone();  //getting phone number from shared preference
        studentId = prefManager.getStudentId();


        String URL, parameters;
        parameters = "phoneNo=" + phoneNum + "&studentID=" + studentId;
        URL = Constants.BASE_URL  + "r_online_transaction_history.php?" + parameters.replaceAll(" ", "%20");
        //System.out.println("CHECK---> URL " + URL);
        final CommonSSLConnection commonSSLConnection=new CommonSSLConnection();
        StringRequest jsonObjectRequestLogin = new StringRequest(Request.Method.GET, URL, new Response.Listener<String>() {
            @Override
            public void onResponse(String jsonObject) {
                binding.HistoryPB.setVisibility(View.GONE);
                //System.out.println("CHECK---> Response " + jsonObject);
                //findViewById(R.id.progressLayout).setVisibility(View.GONE);
                Gson gson = new Gson();

                TransactionHistory model = gson.fromJson(jsonObject, TransactionHistory.class);
                Status status = model.getStatus();
                String code = status.getCode();

                if (code.equals("200")) {

                    Code code1 = model.getCode();

                    if (code1.getTransactions().size()>0) {

                        transactionList.clear();
                        transactionList = code1.getTransactions();
                        binding.OnlineTransactionStatusRV.setLayoutManager(new LinearLayoutManager(OnlineTransactionStatusActivity.this, LinearLayoutManager.VERTICAL, false));
                        binding.OnlineTransactionStatusRV.setItemAnimator(new DefaultItemAnimator());
                        binding.OnlineTransactionStatusRV.setNestedScrollingEnabled(false);
                        mAdapter = new OnlineTransactionStatusAdapter(transactionList, OnlineTransactionStatusActivity.this,mFirebaseAnalytics);
                        binding.OnlineTransactionStatusRV.setAdapter(mAdapter);

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
                else if(code.equals("204")){


                        NodataTV.setText(getResources().getString(R.string.no_data_found));
                        GoBackBTN.setText(getResources().getString(R.string.go_back));
//                        ErrorImage.setBackgroundResource(R.drawable.nodata);
                        findViewById(R.id.NodataLL).setVisibility(View.VISIBLE);
                        GoBackBTN.setVisibility(View.GONE);

                }

                else {

                    String message = status.getMessage();
                    Toast.makeText(OnlineTransactionStatusActivity.this, message, Toast.LENGTH_SHORT).show();
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

                Toast.makeText(OnlineTransactionStatusActivity.this, getResources().getString(R.string.network_error_try), Toast.LENGTH_SHORT).show();
            }
        });
        jsonObjectRequestLogin.setRetryPolicy(new DefaultRetryPolicy(50000, 3, DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
//        RequestQueue requestQueue = Volley.newRequestQueue(this, commonSSLConnection.getHulkstack(PaymentHistoryActivity.this));
        CampusWallet.getInstance().addToRequestQueue(jsonObjectRequestLogin, tag_json_object);
//        requestQueue.add(jsonObjectRequestLogin);

    }
}
