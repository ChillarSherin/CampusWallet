package com.chillarcards.campuswallet.payments;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;

import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.chillarcards.campuswallet.R;
import com.chillarcards.campuswallet.databinding.ActivityOutletMenuItemBinding;
import com.chillarcards.campuswallet.databinding.LayoutCreatePaymentActivityBinding;
import com.google.gson.Gson;

import java.util.List;

import com.chillarcards.campuswallet.NewPreOrder.CartListActivity;
import com.chillarcards.campuswallet.application.CampusWallet;
import com.chillarcards.campuswallet.application.Constants;
import com.chillarcards.campuswallet.application.CustomConnectionBuddyActivity;
import com.chillarcards.campuswallet.application.PrefManager;
import com.chillarcards.campuswallet.networkmodels.createpaymentnew.Code;
import com.chillarcards.campuswallet.networkmodels.createpaymentnew.Createpaymentnew;
import com.chillarcards.campuswallet.networkmodels.createpaymentnew.Razorpay;
import com.chillarcards.campuswallet.networkmodels.createpaymentnew.Status;
import com.chillarcards.campuswallet.payments.Preorder.PreOrderCartList_Activity;
import com.chillarcards.campuswallet.utils.CommonSSLConnection;

//import static com.gocashfree.cashfreesdk.CFPaymentService.PARAM_APP_ID;
//import static com.gocashfree.cashfreesdk.CFPaymentService.PARAM_BANK_CODE;
//import static com.gocashfree.cashfreesdk.CFPaymentService.PARAM_CUSTOMER_EMAIL;
//import static com.gocashfree.cashfreesdk.CFPaymentService.PARAM_CUSTOMER_NAME;
//import static com.gocashfree.cashfreesdk.CFPaymentService.PARAM_CUSTOMER_PHONE;
//import static com.gocashfree.cashfreesdk.CFPaymentService.PARAM_ORDER_AMOUNT;
//import static com.gocashfree.cashfreesdk.CFPaymentService.PARAM_ORDER_ID;
//import static com.gocashfree.cashfreesdk.CFPaymentService.PARAM_ORDER_NOTE;
//import static com.gocashfree.cashfreesdk.CFPaymentService.PARAM_PAYMENT_OPTION;
//import static com.gocashfree.cashfreesdk.CFPaymentService.PARAM_VENDOR_SPLIT;

public class PaymentCreateActivity extends CustomConnectionBuddyActivity {

    PrefManager prefManager;
    Activity activity;
    final String tag_json_object = "c_online_transaction";

    String parentPhone, studentId, trans_category, orderId, orderAmount, extra;
    private LayoutCreatePaymentActivityBinding binding;

    Button ReloadBTN;
    TextView ErrorMessageTV,CodeErrorTV;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_create_payment_activity);

        binding = LayoutCreatePaymentActivityBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        ReloadBTN = binding.getRoot().findViewById(R.id.ReloadBTN);
        ErrorMessageTV=binding.getRoot().findViewById(R.id.ErrorMessageTV);
        CodeErrorTV=binding.getRoot().findViewById(R.id.CodeErrorTV);


        activity = this;
        prefManager = new PrefManager(PaymentCreateActivity.this);


        parentPhone = prefManager.getUserPhone();
        studentId = prefManager.getStudentId();

        Bundle b = getIntent().getExtras();
        String paymentMode = b.getString("paymentMode");
        String amount = b.getString("amount");
        trans_category = b.getString("trans_category");
        String paymentGateway = b.getString("paymentGateway");
        if (paymentMode.equalsIgnoreCase("Wallet") || paymentMode.equalsIgnoreCase("Netbanking")) {
            extra = b.getString("extra");
            System.out.println("CashFree: Code:: " + extra);
        }

        if (!trans_category.equals("2")) {
            String paymentItemId = b.getString("paymentItemId");
            createTransaction(parentPhone, studentId, amount, trans_category, paymentGateway, paymentMode, paymentItemId);
        } else {
            createTransaction(parentPhone, studentId, amount, trans_category, paymentGateway, paymentMode, "");
        }


    }

    private void createTransaction(String phoneNum, String s, final String amount, final String trans_category, final String paymentGateway, final String paymentMode, String paymentItemId) {


        final String URL;
        String parameters;
        orderAmount = "";
        orderId = "";

        parameters = "phoneNo=" + phoneNum + "&studentID=" + s + "&amount=" + amount + "&transaction_category=" + trans_category + "&paymentGateway=" + paymentGateway + "&payment_modes=" + paymentMode;

        if (!trans_category.equals("2") && !paymentItemId.equals("")) {
            parameters = parameters + "&itemID=" + paymentItemId;
        }
        URL = Constants.BASE_URL + "c_online_transaction.php?" + parameters.replaceAll(" ", "%20");
        System.out.println("CHECK---> URL " + URL);
        CommonSSLConnection commonSSLConnection = new CommonSSLConnection();
        final StringRequest jsonObjectRequestLogin = new StringRequest(Request.Method.GET, URL, new Response.Listener<String>() {
            @Override
            public void onResponse(String jsonObject) {

                Log.i("CreatePayment", jsonObject);

                CampusWallet.getInstance().cancelPendingRequests(tag_json_object);
                Gson gson = new Gson();

                Createpaymentnew createpayment = gson.fromJson(jsonObject, Createpaymentnew.class);
                Status status = createpayment.getStatus();
                String code = status.getCode();

                if (code.equals("200")) {
                    findViewById(R.id.ErrorLL).setVisibility(View.GONE);
                    Code code1 = createpayment.getCode();


//                    if(paymentGateway.equals("cashfree")){
//
//
//                        List<Cashfree> cashfree = code1.getCashfree();
//                        if(cashfree.size()>0){
//
//                            Cashfree cashfree1 = cashfree.get(0);
//
//
//                            String appId= cashfree1.getAppId();
//                            String token=cashfree1.getCftoken();
//                            orderAmount=cashfree1.getOrderAmount();
//                            String orderNotes=cashfree1.getOrderNotes();
//                            orderId=cashfree1.getOnlineTransactionID();
//                            String parentName=cashfree1.getParentName();
//                            String parentPhone=cashfree1.getParentPhone();
//                            String parentEmail=cashfree1.getParentEmail();
//                            String vendorSplit = cashfree1.getVendorSplit();
//
//
//                            String stage = "PROD";
//
//
//                            Map<String, String> params = new HashMap<>();
//
//                            params.put(PARAM_APP_ID, appId);
//                            params.put(PARAM_ORDER_ID, orderId);
//                            params.put(PARAM_ORDER_AMOUNT, orderAmount);
//                            params.put(PARAM_ORDER_NOTE, orderNotes);
//                            params.put(PARAM_CUSTOMER_NAME, parentName);
//                            params.put(PARAM_CUSTOMER_PHONE, parentPhone);
//                            params.put(PARAM_CUSTOMER_EMAIL, parentEmail);
//                            params.put(PARAM_VENDOR_SPLIT,vendorSplit);
//
//
//                            for (Map.Entry entry : params.entrySet()) {
//                                Log.d("CFSKDSample", entry.getKey() + " " + entry.getValue());
//                            }
//
//
//
//                            CFPaymentService cfPaymentService = CFPaymentService.getCFPaymentServiceInstance();
//                            cfPaymentService.setOrientation(0);
//                            switch (paymentMode) {
//
//                                /***
//                                 * This method handles the payment gateway invocation (web flow).
//                                 *
//                                 * @param context Android context of the calling activity
//                                 * @param params HashMap containing all the parameters required for creating a payment order
//                                 * @param token Provide the token for the transaction
//                                 * @param stage Identifies if test or production service needs to be invoked. Possible values:
//                                 *              PROD for production, TEST for testing.
//                                 * @param color1 Background color of the toolbar
//                                 * @param color2 text color and icon color of toolbar
//                                 * @param hideOrderId If true hides order Id from the toolbar
//                                 */
//                                case "CC/DC":
//                                    {
//                                    cfPaymentService.doPayment(PaymentCreateActivity.this, params, token, stage, "#784BD2", "#FFFFFF", false);
////                 cfPaymentService.doPayment(MainActivity.this, params, token, stage);
//                                    break;
//                                }
//                                /***
//                                 * Same for all payment modes below.
//                                 *
//                                 * @param context Android context of the calling activity
//                                 * @param params HashMap containing all the parameters required for creating a payment order
//                                 * @param token Provide the token for the transaction
//                                 * @param stage Identifies if test or production service needs to be invoked. Possible values:
//                                 *              PROD for production, TEST for testing.
//                                 */
//                                case "UPI": {
////                                cfPaymentService.selectUpiClient("com.google.android.apps.nbu.paisa.user");
//                                    cfPaymentService.upiPayment(PaymentCreateActivity.this, params, token, stage);
//                                    break;
//                                }
//                                case "AmazonPay": {
//                                    cfPaymentService.doAmazonPayment(PaymentCreateActivity.this, params, token, stage);
//                                    break;
//                                }
//                                case "GooglePay": {
//                                    cfPaymentService.gPayPayment(PaymentCreateActivity.this, params, token, stage);
//                                    break;
//                                }
//                                case "PhonePe": {
//                                    cfPaymentService.phonePePayment(PaymentCreateActivity.this, params, token, stage);
//                                    break;
//                                }
//                                case "Netbanking":
//                                    params.put(PARAM_PAYMENT_OPTION, "nb");
//                                    params.put(PARAM_BANK_CODE, extra);// Put correct bank code here
//                                    cfPaymentService.doPayment(PaymentCreateActivity.this, params, token, stage, "#784BD2", "#FFFFFF", false);
//                                    break;
//                                case "Wallet":
//                                    params.put(PARAM_PAYMENT_OPTION, "wallet");
//                                    params.put(PARAM_BANK_CODE, extra);// Put correct wallet code here
//                                    cfPaymentService.doPayment(PaymentCreateActivity.this, params, token, stage, "#784BD2", "#FFFFFF", false);
//                                    break;
//
//                            }
//
//
//                        }
//
//                    }
//                    else {


                    List<Razorpay> razorpay = code1.getRazorpay();
                    if (razorpay.size() > 0) {

                        Razorpay razorpay1 = razorpay.get(0);


                        String onlineTransactionID = razorpay1.getOnlineTransactionID();
                        String razorpayKey = razorpay1.getRazorpayKey();
                        String razorpaySecretKey = razorpay1.getRazorpaySecretKey();
                        String parentName = razorpay1.getParentName();
                        String parentEmail = razorpay1.getParentEmail();


                        if (trans_category.equals("2")) {

                            Intent i = new Intent(getApplicationContext(), PaymentRazorpay.class);
                            Bundle b = new Bundle();
                            b.putString("onlineTransactionID", onlineTransactionID);
                            b.putString("razorpayKey", razorpayKey);
                            b.putString("razorpaySecretKey", razorpaySecretKey);
                            b.putString("parentName", parentName);
                            b.putString("parentEmail", parentEmail);
                            b.putString("paymentFor", "card_recharge");
                            b.putString("paymentMode", paymentMode);
                            b.putString("trans_category", trans_category);
                            b.putString("razor_order_id", razorpay1.getRazorpayOrderID());
                            b.putString("amount", amount);
                            i.putExtras(b);
                            startActivity(i);
                            activity.finish();

                        }
                        else if (trans_category.equals("3")) {

                            Intent i = new Intent(getApplicationContext(), PaymentRazorpay.class);
                            Bundle b = new Bundle();
                            b.putString("onlineTransactionID", onlineTransactionID);
                            b.putString("razorpayKey", razorpayKey);
                            b.putString("razorpaySecretKey", razorpaySecretKey);
                            b.putString("parentName", parentName);
                            b.putString("parentEmail", parentEmail);
                            b.putString("paymentFor", "fee_payment");
                            b.putString("paymentMode", paymentMode);
                            b.putString("trans_category", trans_category);
                            b.putString("amount", amount);
                            b.putString("razor_order_id", razorpay1.getRazorpayOrderID());
                            i.putExtras(b);
                            startActivity(i);
                            activity.finish();

                        }
                        else if (trans_category.equals("4")) {

                            Intent i = new Intent(getApplicationContext(), PaymentRazorpay.class);
                            Bundle b = new Bundle();
                            b.putString("onlineTransactionID", onlineTransactionID);
                            b.putString("razorpayKey", razorpayKey);
                            b.putString("razorpaySecretKey", razorpaySecretKey);
                            b.putString("parentName", parentName);
                            b.putString("parentEmail", parentEmail);
                            b.putString("paymentFor", "miscellaneous_payments");
                            b.putString("paymentMode", paymentMode);
                            b.putString("trans_category", trans_category);
                            b.putString("razor_order_id", razorpay1.getRazorpayOrderID());
                            b.putString("amount", amount);
                            i.putExtras(b);
                            startActivity(i);
                            activity.finish();

                        }
                        else if (trans_category.equals("5")) {

                            Intent i = new Intent(getApplicationContext(), PaymentRazorpay.class);
                            Bundle b = new Bundle();
                            b.putString("onlineTransactionID", onlineTransactionID);
                            b.putString("razorpayKey", razorpayKey);
                            b.putString("razorpaySecretKey", razorpaySecretKey);
                            b.putString("parentName", parentName);
                            b.putString("parentEmail", parentEmail);
                            b.putString("paymentFor", "trust_payments");
                            b.putString("paymentMode", paymentMode);
                            b.putString("trans_category", trans_category);
                            b.putString("razor_order_id", razorpay1.getRazorpayOrderID());
                            b.putString("amount", amount);
                            i.putExtras(b);
                            startActivity(i);
                            activity.finish();
                        }

                    }
//
//                    }


                } else if (code.equals("500")) {
                    binding.progressBar.setVisibility(View.GONE);
                    ReloadBTN.setText(getResources().getString(R.string.go_back));
                    findViewById(R.id.ErrorLL).setVisibility(View.VISIBLE);
                    ErrorMessageTV.setText(status.getMessage());
                    CodeErrorTV.setText(getResources().getString(R.string.code_attendance) + code);
                    ReloadBTN.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            onBackPressed();
                        }
                    });

                } else if (code.equals("201")) {
                    binding.progressBar.setVisibility(View.GONE);
                    ReloadBTN.setText(getResources().getString(R.string.go_back));
                    findViewById(R.id.ErrorLL).setVisibility(View.VISIBLE);
                    ErrorMessageTV.setText(status.getMessage());
                    CodeErrorTV.setText(getResources().getString(R.string.code_attendance) + code);
                    ReloadBTN.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {


                            onBackPressed();

                        }
                    });

                } else {
                    binding.progressBar.setVisibility(View.GONE);
                    ReloadBTN.setText(getResources().getString(R.string.go_back));
                    findViewById(R.id.ErrorLL).setVisibility(View.VISIBLE);
                    ErrorMessageTV.setText(getResources().getString(R.string.error_message_errorlayout));
                    CodeErrorTV.setText(getResources().getString(R.string.code_attendance) + code);
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
                volleyError.printStackTrace();
                binding.progressBar.setVisibility(View.GONE);
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
            }
        });
        jsonObjectRequestLogin.setRetryPolicy(new DefaultRetryPolicy(50000, 0, DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
//        RequestQueue requestQueue = Volley.newRequestQueue(this, commonSSLConnection.getHulkstack(PaymentCreateActivity.this));
        CampusWallet.getInstance().addToRequestQueue(jsonObjectRequestLogin, tag_json_object);
//        requestQueue.add(jsonObjectRequestLogin);

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
//        //System.out.println("OnDestroy ");
        CampusWallet.getInstance().cancelPendingRequests(tag_json_object);
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        //Same request code for all payment APIs.
//        Log.d(TAG, "ReqCode : " + CFPaymentService.REQ_CODE);
        Log.d("CashFree", "API Response : ");
        //Prints all extras. Replace with app logic.
        if (data != null) {
            Bundle bundle = data.getExtras();

            if (bundle.getString("txStatus").equals("SUCCESS")) {
                UpdateStatus(bundle.getString("referenceId"), "S", bundle.getString("orderId"), bundle.getString("orderAmount"));
            } else {

                UpdateStatus("", "F", orderId, orderAmount);
            }
//            if (bundle != null)
//                for (String key : bundle.keySet()) {
//                    if (bundle.getString(key) != null) {
//                        Log.d("CashFree", key + " : " + bundle.getString(key));
//                        Toast.makeText(activity, key + " : " + bundle.getString(key), Toast.LENGTH_SHORT).show();
//
//
//
//                    }
//                }
        }
    }

    private void UpdateStatus(String testIdentifier, final String status, String onlineTransactionID, String orderAmount) {

        String URL, parameters = "";
        if (testIdentifier.equals("")) {
            parameters = "phoneNo=" + parentPhone + "&studentID=" + studentId + "&tran_id=" + onlineTransactionID + "&transaction_category=" + trans_category + "&tran_status=" + status;
        } else {
            parameters = "phoneNo=" + parentPhone + "&studentID=" + studentId + "&tran_id=" + onlineTransactionID + "&transaction_category=" + trans_category + "&tran_status=" + status + "&identifier=" + testIdentifier;
        }

        URL = Constants.BASE_URL + "u_online_transaction_status.php?" + parameters.replaceAll(" ", "%20");
//        System.out.println("CHECK---> URL " + URL);
        CommonSSLConnection commonSSLConnection = new CommonSSLConnection();
        StringRequest jsonObjectRequestLogin = new StringRequest(Request.Method.GET, URL, new Response.Listener<String>() {
            @Override
            public void onResponse(String jsonObject) {

                CampusWallet.getInstance().cancelPendingRequests(tag_json_object);
                //System.out.println("onResponse : "+jsonObject);

                if (Constants.from_Low_balance) {
                    if (status.equalsIgnoreCase("S")) {
                        if (Constants.from_old_preorder == 0) {
//                            mFirebaseAnalytics.logEvent(getResources().getString(R.string.Pre_Order_Cart_Button_clicked),new Bundle());
                            Intent i = new Intent(getApplicationContext(), CartListActivity.class);
                            startActivity(i);
                            activity.finish();
                        } else {
                            mFirebaseAnalytics.logEvent(getResources().getString(R.string.Pre_Order_Cart_Button_clicked), new Bundle());
                            Intent i = new Intent(getApplicationContext(), PreOrderCartList_Activity.class);
                            startActivity(i);
                            activity.finish();
                        }
                        Toast.makeText(PaymentCreateActivity.this, getResources().getString(R.string.success), Toast.LENGTH_SHORT).show();

                    } else {
//                        Toast.makeText(PaymentRazorpay.this,getResources().getString(R.string.failed),Toast.LENGTH_SHORT).show();
                        Intent i = new Intent(getApplicationContext(), PaymentReceiptActivity.class);
                        Bundle b = new Bundle();
                        b.putString("status", status);
                        b.putString("onlineTransactionID", onlineTransactionID);
                        b.putString("transaction_category", trans_category);
                        b.putString("amount", orderAmount);
                        b.putString("from", "1");
                        i.putExtras(b);
                        startActivity(i);
                        activity.finish();
                    }
                } else {
                    Intent i = new Intent(getApplicationContext(), PaymentReceiptActivity.class);
                    Bundle b = new Bundle();
                    b.putString("status", status);
                    b.putString("onlineTransactionID", onlineTransactionID);
                    b.putString("transaction_category", trans_category);
                    b.putString("amount", orderAmount);
                    b.putString("from", "1");
                    i.putExtras(b);
                    startActivity(i);
                    activity.finish();
                }


            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError volleyError) {
                volleyError.printStackTrace();
                Toast.makeText(PaymentCreateActivity.this, getResources().getString(R.string.failed), Toast.LENGTH_SHORT).show();
            }
        });
        jsonObjectRequestLogin.setRetryPolicy(new DefaultRetryPolicy(50000, 0, DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
//        RequestQueue requestQueue = Volley.newRequestQueue(this, commonSSLConnection.getHulkstack(PaymentRazorpay.this));

        CampusWallet.getInstance().addToRequestQueue(jsonObjectRequestLogin, tag_json_object);
//        requestQueue.add(jsonObjectRequestLogin);

    }
}
