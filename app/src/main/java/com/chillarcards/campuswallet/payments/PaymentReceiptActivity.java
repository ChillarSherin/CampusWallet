package com.chillarcards.campuswallet.payments;

import android.Manifest;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;

import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.VolleyLog;
import com.android.volley.toolbox.StringRequest;
import com.chillarcards.campuswallet.R;
import com.chillarcards.campuswallet.databinding.LayoutTransactionReceiptActivityBinding;
import com.google.gson.Gson;

import com.chillarcards.campuswallet.NewPreOrder.CartListActivity;
import com.chillarcards.campuswallet.Payment.History.PaymentHistoryActivity;
import com.chillarcards.campuswallet.application.CampusWallet;
import com.chillarcards.campuswallet.application.Constants;
import com.chillarcards.campuswallet.application.CustomConnectionBuddyActivity;
import com.chillarcards.campuswallet.application.PrefManager;
import com.chillarcards.campuswallet.networkmodels.TransactionReceipts.Code;
import com.chillarcards.campuswallet.networkmodels.TransactionReceipts.Status;
import com.chillarcards.campuswallet.networkmodels.TransactionReceipts.TransactionReceipt;
import com.chillarcards.campuswallet.payments.Preorder.PreOrderCartList_Activity;
import com.chillarcards.campuswallet.payments.Transactions.OnlineTransactionStatusActivity;
import com.chillarcards.campuswallet.utils.CommonSSLConnection;
//import okhttp3.internal.framed.ErrorCode;

public class PaymentReceiptActivity extends CustomConnectionBuddyActivity {

    final String tag_json_object = "r_student_online_transaction_data";

    PrefManager prefManager;
    String phoneNum,studentId,Studentname,onlineTransactionID,TransactionID;
    private static final int PERMISSIONS_REQUEST_PHONE_CALL = 100;
    private static String[] PERMISSIONS_PHONECALL = {Manifest.permission.CALL_PHONE};
    String From;
    int tabpos;
   
    private LayoutTransactionReceiptActivityBinding binding;
    Button ReloadBTN,GoBackBTN;
    TextView ErrorMessageTV,CodeErrorTV,NodataTV;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
      //  setContentView(R.layout.layout_transaction_receipt_activity);
        binding = LayoutTransactionReceiptActivityBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
      
        ReloadBTN = binding.getRoot().findViewById(R.id.ReloadBTN);
        ErrorMessageTV=binding.getRoot().findViewById(R.id.ErrorMessageTV);
        CodeErrorTV=binding.getRoot().findViewById(R.id.CodeErrorTV);
        GoBackBTN =binding.getRoot().findViewById(R.id.GoBackBTN);
        NodataTV =binding.getRoot().findViewById(R.id.NodataTV);
        
        binding.textView.setText(getResources().getString(R.string.payment_receipt_header));

        prefManager=new PrefManager(PaymentReceiptActivity.this);


        Bundle b = getIntent().getExtras();
        String status = b.getString("status");
        //System.out.println("Checkout: "+ status);
        onlineTransactionID = b.getString("onlineTransactionID");
        String transaction_category = b.getString("transaction_category");
        String amount = b.getString("amount");
        From = b.getString("from");
        tabpos=b.getInt("tabpos");

        binding.imageView5.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (From.equals("1"))
                {
                    onBackPressed();
                }
                else if (Constants.from_Low_balance)
                {
                    if (Constants.from_old_preorder==0)
                    {
                        Intent i = new Intent(PaymentReceiptActivity.this, CartListActivity.class);
                        i.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                        i.addFlags(Intent.FLAG_ACTIVITY_BROUGHT_TO_FRONT);
                        Bundle bundle1 = new Bundle();
                        i.putExtras(bundle1);
                        startActivity(i);
                    }
                    else {
                        mFirebaseAnalytics.logEvent(getResources().getString(R.string.Pre_Order_Cart_Button_clicked),new Bundle());
                        Intent i = new Intent(PaymentReceiptActivity.this, PreOrderCartList_Activity.class);
                        i.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                        i.addFlags(Intent.FLAG_ACTIVITY_BROUGHT_TO_FRONT);
                        Bundle bundle1 = new Bundle();
                        i.putExtras(bundle1);
                        startActivity(i);
                    }

                }
                else {
                    Intent i = new Intent(PaymentReceiptActivity.this, PaymentHistoryActivity.class);
                    i.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                    i.addFlags(Intent.FLAG_ACTIVITY_BROUGHT_TO_FRONT);
                    Bundle bundle1 = new Bundle();
                    bundle1.putInt("tabpos", tabpos);
                    i.putExtras(bundle1);
                    startActivity(i);
                }
            }
        });

//        if(status.equalsIgnoreCase("F")){
//
//            imageView.setImageResource(R.mipmap.ic_failed);
//            binding.textView18.setTextColor(getResources().getColor(R.color.red));
//            binding.textView18.setText(getResources().getString(R.string.failed));
//            binding.textView24.setText(getResources().getString(R.string.failed_transaction));
//            binding.DownloadLL.setVisibility(View.GONE);
//
//
//        }else if(status.equalsIgnoreCase("P")){
//            imageView.setImageResource(R.mipmap.ic_pending);
//            binding.textView18.setTextColor(getResources().getColor(R.color.red));
//            binding.textView18.setText(getResources().getString(R.string.pending_payment));
//            binding.textView24.setText(getResources().getString(R.string.pending_transaction));
//            binding.DownloadLL.setVisibility(View.GONE);
//        }
//        else if(status.equalsIgnoreCase("A")){
//            imageView.setImageResource(R.mipmap.ic_failed);
//            binding.textView18.setTextColor(getResources().getColor(R.color.red));
//            binding.textView18.setText(getResources().getString(R.string.failed));
//            binding.textView24.setText(getResources().getString(R.string.failed_transaction));
//            binding.DownloadLL.setVisibility(View.GONE);
//        }else{
//            imageView.setImageResource(R.mipmap.ic_success);
//            binding.textView18.setTextColor(getResources().getColor(R.color.green_txt));
//            binding.textView18.setText(getResources().getString(R.string.Success));
//            binding.textView24.setText(getResources().getString(R.string.success_transaction));
//            binding.DownloadLL.setVisibility(View.VISIBLE);
//        }
        binding.textView26.setText(getResources().getString(R.string.indian_rupee_symbol)+amount);
        binding.textView25.setText(onlineTransactionID);
        binding.textView27.setText(setHeaderText(transaction_category));
        binding.linearLayout11.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mFirebaseAnalytics.logEvent(getResources().getString(R.string.Receipt_Payment_History_Button_Clicked),new Bundle());
                Intent i=new Intent(PaymentReceiptActivity.this, OnlineTransactionStatusActivity.class);
                i.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP|Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity(i);
            }
        });
        binding.DownloadLL.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mFirebaseAnalytics.logEvent(getResources().getString(R.string.Receipt_Download_Button_Clicked),new Bundle());
                Intent i=new Intent(PaymentReceiptActivity.this, InvoiceDownloadActivity.class);
                i.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP|Intent.FLAG_ACTIVITY_NEW_TASK);
                Bundle bundle=new Bundle();
                bundle.putString("TransID",onlineTransactionID);
                i.putExtras(bundle);
                //System.out.println("AA Payment Receipt :: TransID : "+onlineTransactionID +" TransID 1 : "+TransactionID);
                startActivity(i);
            }
        });
        binding.textView29.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                callPermission();
            }
        });

    }
    public String setHeaderText(String catName)
    {
        String headerName="";
        switch (catName)
        {
            case "card_recharge":
                headerName=getResources().getString(R.string.card_recharge_caps);
                break;
            case "fee_payment":
                headerName=getResources().getString(R.string.fee_payments_caps);
                break;
            case "miscellaneous_payments":
                headerName=getResources().getString(R.string.miscellanious_caps);
                break;
            case "trust_payments":
                headerName=getResources().getString(R.string.trust_payments_caps);
                break;
            default :
                headerName=catName;
                break;

        }
        return headerName;
    }
//    @Override
//    public void onRequestPermissionsResult(int requestCode, String[] permissions,
//                                           int[] grantResults) {
//        if (requestCode == PERMISSIONS_REQUEST_PHONE_CALL) {
//            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
//                callPermission();
//            } else {
//                callPermission();
//            }
//        }
//    }
    private void callPermission() {
        // Check the SDK version and whether the permission is already granted or not.
//        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M && checkSelfPermission(Manifest.permission.CALL_PHONE) != PackageManager.PERMISSION_GRANTED) {
//            requestPermissions(new String[]{Manifest.permission.CALL_PHONE}, PERMISSIONS_REQUEST_PHONE_CALL);
//        } else {
            //Open call function
            String number = getResources().getString(R.string.customercare_number);
            Intent intent = new Intent(Intent.ACTION_DIAL);
            intent.setData(Uri.parse("tel:" + number));
            startActivity(intent);
            overridePendingTransition(R.anim.fade_in, R.anim.fade_out);
//        }
    }
    @Override
    protected void onDestroy() {
        super.onDestroy();
        CampusWallet.getInstance().cancelPendingRequests(tag_json_object);
    }
    @Override
    public void onBackPressed() {
        if (From.equals("1"))
        {
            super.onBackPressed();
        }
        else {
            Intent i = new Intent(PaymentReceiptActivity.this, PaymentHistoryActivity.class);
            i.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            i.addFlags(Intent.FLAG_ACTIVITY_BROUGHT_TO_FRONT);
            Bundle bundle1 = new Bundle();
            bundle1.putInt("tabpos", tabpos);
            i.putExtras(bundle1);
            startActivity(i);
        }

    }

    @Override
    protected void onResume() {
        super.onResume();
        phpGetTransactionReceipt();
    }

    public void phpGetTransactionReceipt()
    {
        // progressBar.setVisibility(View.VISIBLE);
        phoneNum = prefManager.getUserPhone();  //getting phone number from shared preference
        studentId = prefManager.getStudentId();
        Studentname = prefManager.getStudentName();

        String URL, parameters;
        parameters = "phoneNo=" + phoneNum + "&studentID=" + studentId+"&onlineTransactionID="+onlineTransactionID;
        URL = Constants.BASE_URL  + "r_student_online_transaction_data.php?" + parameters.replaceAll(" ", "%20");
        //System.out.println("CHECK---> URL " + URL);
        final CommonSSLConnection commonSSLConnection=new CommonSSLConnection();
        StringRequest jsonObjectRequestLogin = new StringRequest(Request.Method.GET, URL, new Response.Listener<String>() {
            @Override
            public void onResponse(String jsonObject) {
                //  progressBar.setVisibility(View.GONE);
                //System.out.println("CHECK---> Response " + jsonObject);
                //findViewById(R.id.progressLayout).setVisibility(View.GONE);
                Gson gson = new Gson();

                TransactionReceipt model = gson.fromJson(jsonObject, TransactionReceipt.class);
                Status status = model.getStatus();
                String code = status.getCode();
                if (code.equals("200")){

                    Code code1=model.getCode();
                    TransactionID=code1.getTransactionID();
                    String TransactionCategoryID=code1.getTransactionCategoryID();
                    String OnlineTransactionCreatedOn=code1.getOnlineTransactionCreatedOn();
                    String TransactionCategoryKey=code1.getTransactionCategoryKey();
                    String Amount=code1.getAmount();
                    String DownloadDate=code1.getDownloadDate();
                    String DownloadStatus=code1.getDownloadStatus();
                    String Status=code1.getStatus();
                    prefManager.setWalletBalance(code1.getCurrent_balance());
                    if (Status.equalsIgnoreCase("Success"))
                    {
                        binding.imageView.setImageResource(R.mipmap.ic_success);
                        binding.textView18.setTextColor(getResources().getColor(R.color.green_txt));
                        binding.DownloadLL.setVisibility(View.VISIBLE);
                        binding.textView24.setText(getResources().getString(R.string.success_transaction));

                    }else if(Status.equalsIgnoreCase("Pending")){
                        binding.imageView.setImageResource(R.mipmap.ic_pending);
                        binding.textView18.setTextColor(getResources().getColor(R.color.red));
                        binding.textView24.setText(getResources().getString(R.string.pending_transaction));
                        binding.DownloadLL.setVisibility(View.GONE);
                    }
                    else if(Status.equalsIgnoreCase("Aborted")){
                        binding.imageView.setImageResource(R.mipmap.ic_failed);
                        binding.textView18.setTextColor(getResources().getColor(R.color.red));
                        binding.textView24.setText(getResources().getString(R.string.failed_transaction));
                        binding.DownloadLL.setVisibility(View.GONE);
                    }
                    else
                    {
                        binding.imageView.setImageResource(R.mipmap.ic_failed);
                        binding.textView18.setTextColor(getResources().getColor(R.color.red));
                        binding.DownloadLL.setVisibility(View.GONE);
                        binding.textView24.setText(getResources().getString(R.string.failed_transaction));

                    }
                    binding.textView18.setText(Status);
                    binding.textView26.setText(getResources().getString(R.string.indian_rupee_symbol)+Amount);
                    binding.textView25.setText(TransactionID);
                    binding.textView27.setText(setHeaderText(TransactionCategoryKey));
                    binding.textView28.setText(OnlineTransactionCreatedOn);

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

                    ReloadBTN.setText(getResources().getString(R.string.go_back));
                    findViewById(R.id.ErrorLL).setVisibility(View.VISIBLE);
                    ErrorMessageTV.setText(getResources().getString(R.string.no_data_found));
                    CodeErrorTV.setText(getResources().getString(R.string.code_attendance)+code);
                    CodeErrorTV.setVisibility(View.GONE);
                    ReloadBTN.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {


                            onBackPressed();

                        }
                    });

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

                Toast.makeText(PaymentReceiptActivity.this, getResources().getString(R.string.network_error_try), Toast.LENGTH_SHORT).show();
            }
        });
        jsonObjectRequestLogin.setRetryPolicy(new DefaultRetryPolicy(50000, 3, DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
//        RequestQueue requestQueue = Volley.newRequestQueue(this, commonSSLConnection.getHulkstack(PaymentReceiptActivity.this));
        CampusWallet.getInstance().addToRequestQueue(jsonObjectRequestLogin, tag_json_object);
//        requestQueue.add(jsonObjectRequestLogin);
    }
}
