package com.chillarcards.campuswallet.payments.cardtransaction;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
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
import com.chillarcards.campuswallet.databinding.ActivityHistoryReceiptBinding;
import com.google.gson.Gson;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.List;

import com.chillarcards.campuswallet.application.CampusWallet;
import com.chillarcards.campuswallet.application.Constants;
import com.chillarcards.campuswallet.application.CustomConnectionBuddyActivity;
import com.chillarcards.campuswallet.application.PrefManager;
import com.chillarcards.campuswallet.networkmodels.PaymentHistoryDetails.Code;
import com.chillarcards.campuswallet.networkmodels.PaymentHistoryDetails.Detail;
import com.chillarcards.campuswallet.networkmodels.PaymentHistoryDetails.OutletData;
import com.chillarcards.campuswallet.networkmodels.PaymentHistoryDetails.Status;
import com.chillarcards.campuswallet.utils.CommonSSLConnection;

public class HistoryReceiptActivity extends CustomConnectionBuddyActivity {

    private ActivityHistoryReceiptBinding binding;
    Button ReloadBTN,GoBackBTN;
    TextView ErrorMessageTV,CodeErrorTV,NodataTV;

    final String tag_json_object = "r_student_card_transaction_data";
    PrefManager prefManager;
    String phoneNum,studentId,Studentname,outletName,transID;
    Activity activity;
    private HistoryReceiptAdapter adapter;
    int posspec;
    int status=1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
       // setContentView(R.layout.activity_history_receipt);
        binding = ActivityHistoryReceiptBinding.inflate(getLayoutInflater());
        View view = binding.getRoot();
        setContentView(view);

        ReloadBTN = binding.getRoot().findViewById(R.id.ReloadBTN);
        ErrorMessageTV=binding.getRoot().findViewById(R.id.ErrorMessageTV);
        CodeErrorTV=binding.getRoot().findViewById(R.id.CodeErrorTV);
        GoBackBTN =binding.getRoot().findViewById(R.id.GoBackBTN);
        NodataTV =binding.getRoot().findViewById(R.id.NodataTV);
        //        Fabric.with(this, new Answers());
        activity=this;
        prefManager=new PrefManager(HistoryReceiptActivity.this);
        binding.HeaderTV.setText(getResources().getString(R.string.More_info_header));
        binding.progressBar.setVisibility(View.GONE);
        binding.InnerRefundSealLL.setVisibility(View.GONE);

        final Bundle bundle=getIntent().getExtras();
        if (bundle!= null) {
            outletName = bundle.getString("OutletName");
            transID = bundle.getString("transID");
            posspec = bundle.getInt("posspec");
            status = bundle.getInt("status");
            //System.out.println("Activity transID :  "+transID);
        }
        if (status==0)
        {
            binding.InnerRefundSealLL.setVisibility(View.VISIBLE);
        }
        binding.BackIV.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent i=new Intent(HistoryReceiptActivity.this,CardTransactionActivity.class);
                i.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                i.addFlags(Intent.FLAG_ACTIVITY_BROUGHT_TO_FRONT);
                Bundle bundle1=new Bundle();
                bundle1.putInt("posspec",posspec);
                i.putExtras(bundle1);
                startActivity(i);
            }
        });
    }

    @Override
    public void onBackPressed() {

        Intent i=new Intent(HistoryReceiptActivity.this,CardTransactionActivity.class);
        i.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        i.addFlags(Intent.FLAG_ACTIVITY_BROUGHT_TO_FRONT);
        Bundle bundle1=new Bundle();
        bundle1.putInt("posspec",posspec);
        i.putExtras(bundle1);
        startActivity(i);
    }

    @Override
    protected void onResume() {
        super.onResume();
        binding.OutletNameTV.setText(outletName);
        phpGethistoryDetails(transID);

    }
    @Override
    protected void onDestroy() {
        super.onDestroy();
        CampusWallet.getInstance().cancelPendingRequests(tag_json_object);
    }
    public void phpGethistoryDetails(String trans_ID)
    {
        binding.progressBar.setVisibility(View.VISIBLE);
        phoneNum = prefManager.getUserPhone();  //getting phone number from shared preference
        studentId = prefManager.getStudentId();
        Studentname = prefManager.getStudentName();

        try {
            trans_ID = URLEncoder.encode(trans_ID, "utf-8");
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }

        String URL, parameters;
        parameters = "phoneNo=" + phoneNum + "&studentID=" + studentId+"&transactionID="+trans_ID;
        URL = Constants.BASE_URL  + "r_student_card_transaction_data.php?" + parameters.replaceAll(" ", "%20");
        //System.out.println("CHECK---> URL " + URL);
        CommonSSLConnection commonSSLConnection=new CommonSSLConnection();
        final String finalTrans_ID = trans_ID;
        StringRequest jsonObjectRequestLogin = new StringRequest(Request.Method.GET, URL, new Response.Listener<String>() {
            @Override
            public void onResponse(String jsonObject) {
                binding.progressBar.setVisibility(View.GONE);
                //System.out.println("CHECK---> Response " + jsonObject);
                //findViewById(R.id.progressLayout).setVisibility(View.GONE);
                Gson gson = new Gson();

                OutletData model = gson.fromJson(jsonObject, OutletData.class);
                Status status = model.getStatus();
                String code = status.getCode();
                if (code.equals("200")){
                    findViewById(R.id.ErrorLL).setVisibility(View.GONE);
                    Code listcode=model.getCode();
                   if (!listcode.getBillAmount().equalsIgnoreCase("")){
                       binding.TotalAmountTV.setText(getResources().getString(R.string.indian_rupee_symbol)+" "+listcode.getBillAmount());
                   }
                   else
                   {
                       binding.TotalAmountTV.setText(getResources().getString(R.string.indian_rupee_symbol)+" 0");
                   }
                   List<Detail> DetailList=new ArrayList<>();
                   DetailList.clear();
                   DetailList=listcode.getDetails();


                    if (DetailList.size()>0) {
                        binding.TotalItemTV.setText(DetailList.size()+"");

                        findViewById(R.id.NodataLL).setVisibility(View.GONE);
                        binding.HistoryReceiptRV.setVisibility(View.VISIBLE);
                        binding.HistoryReceiptRV.setLayoutManager(new LinearLayoutManager(activity));
                        binding.HistoryReceiptRV.setItemAnimator(new DefaultItemAnimator());
                        binding.HistoryReceiptRV.setNestedScrollingEnabled(false);
                        adapter = new HistoryReceiptAdapter(DetailList,getApplicationContext(), finalTrans_ID);

                        binding.HistoryReceiptRV.setAdapter(adapter);



                    }else {
                        binding.TotalItemTV.setText("0");
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

                Toast.makeText(HistoryReceiptActivity.this, getResources().getString(R.string.network_error_try), Toast.LENGTH_SHORT).show();
            }
        });
        jsonObjectRequestLogin.setRetryPolicy(new DefaultRetryPolicy(50000, 3, DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
//        RequestQueue requestQueue = Volley.newRequestQueue(this, commonSSLConnection.getHulkstack(HistoryReceiptActivity.this));
        CampusWallet.getInstance().addToRequestQueue(jsonObjectRequestLogin, tag_json_object);
//        requestQueue.add(jsonObjectRequestLogin);
    }
}
