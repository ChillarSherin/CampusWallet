package com.chillarcards.campuswallet.payments.Preorder;

import android.app.Activity;
import android.app.Dialog;
import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;
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
import com.chillarcards.campuswallet.databinding.CartlistBinding;
import com.chillarcards.campuswallet.databinding.HistorydetailsPopupBinding;
import com.chillarcards.campuswallet.databinding.LayoutPreorderHistoryBinding;
import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.List;

import com.chillarcards.campuswallet.application.CampusWallet;
import com.chillarcards.campuswallet.application.Constants;
import com.chillarcards.campuswallet.application.CustomConnectionBuddyActivity;
import com.chillarcards.campuswallet.application.PrefManager;
import com.chillarcards.campuswallet.networkmodels.PreOrderHistory.PreOrderHistory;
import com.chillarcards.campuswallet.networkmodels.PreOrderHistory.PreOrderHistoryCode;
import com.chillarcards.campuswallet.networkmodels.PreOrderHistory.PreOrderStatus;
import com.chillarcards.campuswallet.utils.CommonSSLConnection;

@SuppressWarnings("ALL")
public class PreOrderHistory_Activity extends CustomConnectionBuddyActivity {
    String parentPh,s_Id;
    ProgressBar progressBar;
    RecyclerView mRecyclerView;
    private List<String> preorderItemSaleTransactionID = new ArrayList<String>();
    private List<String> tansactionBillNo = new ArrayList<String>();
    private List<String> preorderTimingID = new ArrayList<String>();
    private List<String> preorderTimingName = new ArrayList<String>();
    private List<String> preorderItemSaleTransactionTotalAmount = new ArrayList<String>();
    private List<String> preorderItemSaleTransactionOrderTime = new ArrayList<String>();
    private PreOrderHistoryAdapter mAdapter;
    private Activity activity;
    Button Reload;
    TextView Txt_Content;
    ImageView ErrorImage;
    Dialog NoInternet;
    int InternetFlag = 0;
    PrefManager prefManager;
    String phoneNum,studentId,Studentname;
    private LayoutPreorderHistoryBinding binding;
    Button ReloadBTN,GoBackBTN;
    TextView ErrorMessageTV,CodeErrorTV,NodataTV;
    final String tag_json_object = "r_preorder_history";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
       // setContentView(R.layout.layout_preorder_history);
        binding = LayoutPreorderHistoryBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        prefManager=new PrefManager(PreOrderHistory_Activity.this);
//        Fabric.with(this, new Answers());
        activity=this;

        binding.HeaderTV.setText(getResources().getString(R.string.preorder_history));
        binding.BackIV.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });

        progressBar= (ProgressBar) findViewById(R.id.ProgressBar);

        progressBar.setVisibility(View.GONE);
        findViewById(R.id.ErrorLL).setVisibility(View.GONE);
        findViewById(R.id.NodataLL).setVisibility(View.GONE);

        mRecyclerView = (RecyclerView) findViewById(R.id.list);
//        mRecyclerView.setLayoutManager(new WrapContentLinearLayoutManager(activity, LinearLayoutManager.VERTICAL, false));

//        Reload.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//
//                Intent i = new Intent(getApplicationContext(), RechargeHistory.class);
//                i.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
//                i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
//                startActivity(i);
//                finish();
//                overridePendingTransition(R.anim.fade_in, R.anim.fade_out);
//            }
//        });

        parentPh = prefManager.getUserPhone();
        s_Id= prefManager.getStudentId();

        phpGetPreOrder();

    }


    public void phpGetPreOrder()
    {
         progressBar.setVisibility(View.VISIBLE);
        phoneNum = prefManager.getUserPhone();  //getting phone number from shared preference
        studentId = prefManager.getStudentId();
        Studentname = prefManager.getStudentName();

        String URL, parameters;
        parameters = "phoneNo=" + phoneNum + "&studentID=" + studentId;
        URL = Constants.BASE_URL  + "r_preorder_history.php?" + parameters.replaceAll(" ", "%20");
        //System.out.println("CHECK---> URL " + URL);
        CommonSSLConnection  commonSSLConnection=new CommonSSLConnection();
        StringRequest jsonObjectRequestLogin = new StringRequest(Request.Method.GET, URL, new Response.Listener<String>() {
            @Override
            public void onResponse(String jsonObject) {
                  progressBar.setVisibility(View.GONE);
                //System.out.println("CHECK---> Response " + jsonObject);
                //findViewById(R.id.progressLayout).setVisibility(View.GONE);
                Gson gson = new Gson();

                PreOrderHistory model = gson.fromJson(jsonObject, PreOrderHistory.class);
                PreOrderStatus status = model.getStatus();
                String code = status.getCode();
                if (code.equals("200")){


                    preorderItemSaleTransactionID.clear();
                    tansactionBillNo.clear();
                    preorderTimingID.clear();
                    preorderTimingName.clear();
                    preorderItemSaleTransactionTotalAmount.clear();
                    preorderItemSaleTransactionOrderTime.clear();

                    List<PreOrderHistoryCode> transactionCode = model.getCode();
                    if (transactionCode.size() > 0) {

                        for (int i = 0; i < transactionCode.size(); i++) {

                            PreOrderHistoryCode codeonline = transactionCode.get(i);
                            preorderItemSaleTransactionID.add(codeonline.getPreorderItemSaleTransactionID());
                            tansactionBillNo.add(codeonline.getTansactionBillNo());
                            preorderTimingID.add(codeonline.getPreorderTimingID());
                            preorderTimingName.add(codeonline.getPreorderTimingName());
                            preorderItemSaleTransactionTotalAmount.add(codeonline.getPreorderItemSaleTransactionTotalAmount());
                            preorderItemSaleTransactionOrderTime.add(codeonline.getPreorderItemSaleTransactionOrderTime());

                            //System.out.println("SHANIL Test1 : "+preorderItemSaleTransactionOrderTime.get(i));

                        }
                        mRecyclerView.setVisibility(View.VISIBLE);
                        mRecyclerView.setLayoutManager(new LinearLayoutManager(activity, LinearLayoutManager.VERTICAL, false));                        mRecyclerView.setItemAnimator(new DefaultItemAnimator());
                        mRecyclerView.setNestedScrollingEnabled(false);
                        mAdapter = new PreOrderHistoryAdapter(preorderItemSaleTransactionID, tansactionBillNo, preorderTimingID,
                                preorderTimingName, preorderItemSaleTransactionTotalAmount, preorderItemSaleTransactionOrderTime,
                                activity, R.layout.preorderhistory, getApplicationContext());
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
                progressBar.setVisibility(View.GONE);
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

                Toast.makeText(PreOrderHistory_Activity.this, getResources().getString(R.string.network_error_try), Toast.LENGTH_SHORT).show();
            }
        });
        jsonObjectRequestLogin.setRetryPolicy(new DefaultRetryPolicy(50000, 3, DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
//        RequestQueue requestQueue = Volley.newRequestQueue(this, commonSSLConnection.getHulkstack(PreOrderHistory_Activity.this));
        CampusWallet.getInstance().addToRequestQueue(jsonObjectRequestLogin, tag_json_object);
//        requestQueue.add(jsonObjectRequestLogin);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        CampusWallet.getInstance().cancelPendingRequests(tag_json_object);
    }
}
