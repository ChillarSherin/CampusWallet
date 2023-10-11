package com.chillarcards.campuswallet.payments;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;
import android.util.Log;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.chillarcards.campuswallet.R;
import com.razorpay.Checkout;
import com.razorpay.PaymentResultListener;

import org.json.JSONObject;


import com.chillarcards.campuswallet.NewPreOrder.CartListActivity;
import com.chillarcards.campuswallet.application.CampusWallet;
import com.chillarcards.campuswallet.application.Constants;
import com.chillarcards.campuswallet.application.CustomConnectionBuddyActivity;
import com.chillarcards.campuswallet.application.PrefManager;
import com.chillarcards.campuswallet.payments.Preorder.PreOrderCartList_Activity;
import com.chillarcards.campuswallet.utils.CommonSSLConnection;


@SuppressWarnings("ALL")
public class PaymentRazorpay extends CustomConnectionBuddyActivity implements PaymentResultListener {

    PrefManager prefManager;
    final String tag_json_object = "u_online_transaction_status";
     ProgressBar progressBar;
    String parentPhone,studentId,versionCode,studentCode,onlineTransactionID,amount,
            trans_category,parentEmail,parentName,razorpayKey,paymentMode,paymentFor,razorPayOrderID;
    Activity activity;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_create_payment_activity);
        progressBar = findViewById(R.id.progressBar);
        prefManager = new PrefManager(this);
        activity=this;
        parentPhone = prefManager.getUserPhone();
        studentId = prefManager.getStudentId();
        versionCode = prefManager.getVersionCode();
        studentCode = prefManager.getStudentCode();


        Bundle b = getIntent().getExtras();
        onlineTransactionID = b.getString("onlineTransactionID");
        amount = b.getString("amount");
        trans_category = b.getString("trans_category");
        parentEmail=b.getString("parentEmail", "");
        parentName=b.getString("parentName", "");
        razorpayKey=b.getString("razorpayKey", "");
        paymentMode=b.getString("paymentMode", "");
        paymentFor=b.getString("paymentFor", "");
        razorPayOrderID=b.getString("razor_order_id", "");

        Checkout.preload(getApplicationContext());

        /**
         * start payment
        * */

        startPayment(parentPhone,studentId,onlineTransactionID,amount,trans_category,parentEmail,
                parentName,razorpayKey,paymentMode,paymentFor,versionCode,studentCode);



    }





    public void startPayment(String parentPhone, String studentId, String onlineTransactionID, String amount, String trans_category, String parentEmail, String parentName, String razorpayKey, String paymentMode, String paymentFor, String versionCode, String studentCode){


        Checkout razorpayCheckout = new Checkout();
        razorpayCheckout.setPublicKey(razorpayKey);

        /**
         * Image for checkout form can passed as reference to a drawable
         */
        razorpayCheckout.setImage(R.drawable.ic_logo);

        /**
         * Reference to current activity
         */
        Activity activity = this;

        try{
            JSONObject options = new JSONObject();

            /**
             * Image for checkout form can also be set from a URL
             * For this, pass URL inside JSONObject as following:
             *
             * options.put("image", "<link to image>");
             */

            Float RazrAmt=Float.parseFloat(amount)*100;
            options.put("amount",RazrAmt);
            options.put("name", trans_category);
            options.put("description", "Reference No. #"+onlineTransactionID);
            options.put("order_id", razorPayOrderID);
            options.put("currency", "INR");

            JSONObject preFill = new JSONObject();
            preFill.put("email", parentEmail);
            preFill.put("contact", parentPhone);
            preFill.put("method", paymentMode);

            JSONObject notes = new JSONObject();
            notes.put("order_id", onlineTransactionID);
            notes.put("device","android");
            notes.put("VersionCode",versionCode);
            notes.put("StudentId",studentId);
            notes.put("payment_for",paymentFor);
            notes.put("StudentCode",studentCode);
            options.put("prefill", preFill);
            options.put("notes", notes);

            razorpayCheckout.open(activity, options);

        } catch(Exception e){
            e.printStackTrace();
        }
    }

    /**
     * The name of the function has to be
     *   onPaymentSuccess
     * Wrap your code in try catch, as shown, to ensure that this method runs correctly
     */
    @Override
    public void onPaymentSuccess(String razorpayPaymentID){

//        //System.out.println("UpdateStatus : onPaymentSuccess ");

        try {
          UpdateStatus(razorpayPaymentID,"S");
        }
        catch (Exception e){
            Log.e("com.merchant", e.getMessage(), e);
        }
    }

    /**
     * The name of the function has to be
     *   onPaymentError
     * Wrap your code in try catch, as shown, to ensure that this method runs correctly
     */
    @Override
    public void onPaymentError(int code, String response){

        //System.out.println("UpdateStatus : onPaymentError response: "+ response+" Code : "+code);

        String sta="F";
        if(code==Checkout.TLS_ERROR){
            //System.out.println("Checkout: TLS_ERROR");
            sta="A";
        }else if(code == Checkout.NETWORK_ERROR){
            //System.out.println("Checkout: NETWORK_ERROR");
            sta="A";
        }else if(code == Checkout.PAYMENT_CANCELED){
            //System.out.println("Checkout: PAYMENT_CANCELED");
            sta="A";
        }else if(code == Checkout.INVALID_OPTIONS){
            //System.out.println("Checkout: INVALID_OPTIONS");
            sta="A";
        }else {
            //System.out.println("Checkout: ");
            sta="F";

        }

        try {

            UpdateStatus("",sta);

        }
        catch (Exception e){
            Log.e("com.merchant", e.getMessage(), e);
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        //System.out.println("PaymentRazorpay:  ON RESUME ");

    }


    private void UpdateStatus(String testIdentifier, final String status){

        String URL, parameters = "";
        if(testIdentifier.equals("")){
            parameters = "phoneNo=" + parentPhone+"&studentID="+studentId+"&tran_id="+onlineTransactionID+"&transaction_category="+trans_category+"&tran_status="+status;
        }else{
            parameters = "phoneNo=" + parentPhone+"&studentID="+studentId+"&tran_id="+onlineTransactionID+"&transaction_category="+trans_category+"&tran_status="+status+"&identifier="+testIdentifier;
        }

        URL = Constants.BASE_URL + "u_online_transaction_status.php?" + parameters.replaceAll(" ", "%20");
        System.out.println("CHECK---> URLs " + URL);
        CommonSSLConnection commonSSLConnection=new CommonSSLConnection();
        String finalParameters = parameters;
        StringRequest jsonObjectRequestLogin = new StringRequest(Request.Method.GET, URL, new Response.Listener<String>() {
            @Override
            public void onResponse(String jsonObject) {

                CampusWallet.getInstance().cancelPendingRequests(tag_json_object);
                System.out.println("onResponse par : "+ finalParameters);
                System.out.println("onResponse php : "+jsonObject);

                if (Constants.from_Low_balance)
                {
                    if (status.equalsIgnoreCase("S")) {
                        if (Constants.from_old_preorder==0)
                        {
//                            mFirebaseAnalytics.logEvent(getResources().getString(R.string.Pre_Order_Cart_Button_clicked),new Bundle());
                            Intent i = new Intent(getApplicationContext(), CartListActivity.class);
                            startActivity(i);
                            activity.finish();
                        }
                        else {
                            mFirebaseAnalytics.logEvent(getResources().getString(R.string.Pre_Order_Cart_Button_clicked),new Bundle());
                            Intent i = new Intent(getApplicationContext(), PreOrderCartList_Activity.class);
                            startActivity(i);
                            activity.finish();
                        }
                        Toast.makeText(PaymentRazorpay.this,getResources().getString(R.string.success),Toast.LENGTH_SHORT).show();

                    }
                    else
                    {
//                        Toast.makeText(PaymentRazorpay.this,getResources().getString(R.string.failed),Toast.LENGTH_SHORT).show();
                        Intent i = new Intent(getApplicationContext(), PaymentReceiptActivity.class);
                        Bundle b = new Bundle();
                        b.putString("status", status);
                        b.putString("onlineTransactionID", onlineTransactionID);
                        b.putString("transaction_category", trans_category);
                        b.putString("amount", amount);
                        b.putString("from", "1");
                        i.putExtras(b);
                        startActivity(i);
                        activity.finish();
                    }
                }
                else {
                    Intent i = new Intent(getApplicationContext(), PaymentReceiptActivity.class);
                    Bundle b = new Bundle();
                    b.putString("status", status);
                    b.putString("onlineTransactionID", onlineTransactionID);
                    b.putString("transaction_category", trans_category);
                    b.putString("amount", amount);
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
                Toast.makeText(PaymentRazorpay.this,getResources().getString(R.string.failed),Toast.LENGTH_SHORT).show();
            }
        });
        jsonObjectRequestLogin.setRetryPolicy(new DefaultRetryPolicy(50000, 0, DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
//        RequestQueue requestQueue = Volley.newRequestQueue(this, commonSSLConnection.getHulkstack(PaymentRazorpay.this));

        CampusWallet.getInstance().addToRequestQueue(jsonObjectRequestLogin, tag_json_object);
//        requestQueue.add(jsonObjectRequestLogin);

    }
    @Override
    protected void onDestroy() {
        super.onDestroy();
        CampusWallet.getInstance().cancelPendingRequests(tag_json_object);
    }
    @Override
    public void onBackPressed() {
        super.onBackPressed();
    }


}
