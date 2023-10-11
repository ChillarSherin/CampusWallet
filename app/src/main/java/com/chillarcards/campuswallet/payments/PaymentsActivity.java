package com.chillarcards.campuswallet.payments;

import android.app.Activity;
import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.appcompat.app.AppCompatActivity;

import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.chillarcards.campuswallet.R;
//import com.gocashfree.cashfreesdk.CFPaymentService;
//import com.gocashfree.cashfreesdk.ui.gpay.GooglePayStatusListener;
import com.chillarcards.campuswallet.databinding.ActivityPaymentHistoryBinding;
import com.chillarcards.campuswallet.databinding.LayoutPaymentsActivityBinding;
import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import com.chillarcards.campuswallet.application.CampusWallet;
import com.chillarcards.campuswallet.application.Constants;
import com.chillarcards.campuswallet.application.CustomConnectionBuddyActivity;
import com.chillarcards.campuswallet.application.PrefManager;
import com.chillarcards.campuswallet.networkmodels.otherpaymentmodes.Code;
import com.chillarcards.campuswallet.networkmodels.otherpaymentmodes.OtherPayment;
import com.chillarcards.campuswallet.networkmodels.otherpaymentmodes.PaymentItem;
import com.chillarcards.campuswallet.networkmodels.otherpaymentmodes.PaymentMode;
import com.chillarcards.campuswallet.networkmodels.otherpaymentmodes.Status;
import com.chillarcards.campuswallet.networkmodels.otherpaymentmodes.SubItem;
import com.chillarcards.campuswallet.utils.CommonSSLConnection;


public class PaymentsActivity extends CustomConnectionBuddyActivity {

    PrefManager prefManager;
    Activity activity;

    Fragment fragment1 = new PaymentsRechargeFragment();
    Fragment fragment2 = new PaymentsOtherFragment();
    Fragment fragment3 = null;
    Fragment fragment4 = null;
    final FragmentManager fm = getSupportFragmentManager();
    Fragment active = null;
    final String tag_json_object = "r_online_transaction_details";

    String fromLowbalance, rechargeAmount;

    String phoneNum, studentId, category;
    private LayoutPaymentsActivityBinding binding;
    Button ReloadBTN,GoBackBTN;
    TextView ErrorMessageTV,CodeErrorTV,NodataTV;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
      //  setContentView(R.layout.layout_payments_activity);

        binding = LayoutPaymentsActivityBinding.inflate(getLayoutInflater());
        View view = binding.getRoot();
        setContentView(view);

        ReloadBTN = binding.getRoot().findViewById(R.id.ReloadBTN);
        ErrorMessageTV=binding.getRoot().findViewById(R.id.ErrorMessageTV);
        CodeErrorTV=binding.getRoot().findViewById(R.id.CodeErrorTV);
        GoBackBTN =binding.getRoot().findViewById(R.id.GoBackBTN);
        NodataTV =binding.getRoot().findViewById(R.id.NodataTV);
        activity = this;
        prefManager = new PrefManager(PaymentsActivity.this);
        phoneNum = prefManager.getUserPhone();
        studentId = prefManager.getStudentId();

        Bundle b = getIntent().getExtras();
        category = b.getString("categoryId");
        String categoryName = b.getString("categoryName");
        fromLowbalance = b.getString("fromLowbalance");
        rechargeAmount = b.getString("rechargeAmount");

        binding.HeaderTV.setText(setHeaderText(category));   //set header name

        binding.BackIV.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });
        setProgressBarVisible();
        LoadAllPaymentModes(phoneNum, studentId, category);

    }

    public String setHeaderText(String catName) {
        String headerName = "";
        switch (catName) {
            case "2":
                headerName = getResources().getString(R.string.card_recharge_caps);
                break;
            case "3":
                headerName = getResources().getString(R.string.fee_payments_caps);
                break;
            case "4":
                headerName = getResources().getString(R.string.miscellanious_caps);
                break;
            case "5":
                headerName = getResources().getString(R.string.trust_payments_caps);
                break;
            default:
                headerName = getResources().getString(R.string.payement_title);
                break;

        }
        return headerName;
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        CampusWallet.getInstance().cancelPendingRequests(tag_json_object);
    }

    private void LoadAllPaymentModes(String phoneNum, String s, final String category) {


        String URL, parameters;
        parameters = "phoneNo=" + phoneNum + "&studentID=" + s + "&transactionCategoryID=" + category;
        URL = Constants.BASE_URL + "r_online_transaction_details.php?" + parameters.replaceAll(" ", "%20");
        System.out.println("CHECK---> URL " + URL);
        final CommonSSLConnection commonSSLConnection = new CommonSSLConnection();
        StringRequest jsonObjectRequestLogin = new StringRequest(Request.Method.GET, URL, new Response.Listener<String>() {
            @Override
            public void onResponse(String jsonObject) {
                Log.d("abc_payments", "onResponse: " + jsonObject);
                CampusWallet.getInstance().cancelPendingRequests(tag_json_object);
                setProgressBarGone();

                Gson gson = new Gson();

                OtherPayment modes = gson.fromJson(jsonObject, OtherPayment.class);
                Status status = modes.getStatus();
                String code = status.getCode();

                if (code.equals("200")) {

                    Code code1 = modes.getCode();
                    List<PaymentMode> paymentModes = new ArrayList<>();
                    List<PaymentMode> paymentModes1 = new ArrayList<>();


                    paymentModes = code1.getPaymentModes();


                    // TODO: 24-06-2022 hidden to get rid of cashfree modules to check if cashfree causing intent redirection policy violation in playstore
                    for(int i = 0;i<paymentModes.size();++i){

//                        if(paymentModes.get(i).getName().equalsIgnoreCase("PhonePe")){
//                            if(CFPaymentService.getCFPaymentServiceInstance().doesPhonePeExist(activity, "PROD")){
//                                paymentModes1.add(paymentModes.get(i));
//
//                            }
//                        }else if(paymentModes.get(i).getName().equalsIgnoreCase("GooglePay")){
//                            List<PaymentMode> finalPaymentModes = paymentModes;
//                            int finalI = i;
//                            CFPaymentService.getCFPaymentServiceInstance().isGPayReadyForPayment(activity, new GooglePayStatusListener() {
//                                @Override
//                                public void isReady() {
//                                    paymentModes1.add(finalPaymentModes.get(finalI));
//
//                                }
//
//                                @Override
//                                public void isNotReady() {
//
//                                }
//                            });
//                        }
//                        else {
                            paymentModes1.add(paymentModes.get(i));

//                        }

                    }


                    List<PaymentItem> paymentItems = new ArrayList<>();
                    paymentItems = code1.getPaymentItems();

                    String paymentGateWayName = code1.getPaymentGateWayName();

//                    Toast.makeText(activity, "Payment Gateway: "+paymentGateWayName, Toast.LENGTH_SHORT).show();

                    if (category.equals("2")) {   //Card Recharge -inflate card recharge fragment

                        ArrayList<PaymentMode> modesPaymentsNew = new ArrayList<PaymentMode>();
                        modesPaymentsNew.addAll(paymentModes1);

                        Gson gson1 = new Gson();
                        String paymentModesStr = gson1.toJson(modesPaymentsNew);

                        Bundle bundle1 = new Bundle();
                        bundle1.putString("paymentModes", paymentModesStr);
                        bundle1.putString("jsonObject", jsonObject);
                        bundle1.putString("category", category);
                        bundle1.putString("fromLowbalance", fromLowbalance);
                        bundle1.putString("rechargeAmount", rechargeAmount);
                        bundle1.putString("paymentGateWayName", paymentGateWayName);
                        fragment1.setArguments(bundle1);
                        active = fragment1;
                        fm.beginTransaction().add(R.id.frame_layout, fragment1, "1").commitAllowingStateLoss();
//                        fm.beginTransaction().hide(active).show(fragment1).commit();
                        if (active != null) {
                            fm.beginTransaction().hide(active).show(fragment1).commitAllowingStateLoss();
                        } else {
                            fm.beginTransaction().show(fragment1).commitAllowingStateLoss();
                        }


                    } else {

                        ArrayList<PaymentMode> modesPaymentsNew = new ArrayList<PaymentMode>();
                        modesPaymentsNew.addAll(paymentModes1);
                        ArrayList<PaymentItem> itemPaymentsNew = new ArrayList<PaymentItem>();
                        itemPaymentsNew.addAll(paymentItems);


                        Gson gson1 = new Gson();
                        String paymentModesStr = gson1.toJson(modesPaymentsNew);
                        String paymentItemsStr = gson1.toJson(itemPaymentsNew);

                        //System.out.println("paymentItemsStr :: "+paymentItemsStr);

                        Bundle bundle1 = new Bundle();
                        bundle1.putString("paymentModes", paymentModesStr);
                        bundle1.putString("jsonObject", jsonObject);
                        bundle1.putString("paymentItems", paymentItemsStr);
                        bundle1.putString("category", category);
                        bundle1.putString("paymentGateWayName", paymentGateWayName);
                        fragment2.setArguments(bundle1);
                        active = fragment2;

                        fm.beginTransaction().add(R.id.frame_layout, fragment2, "2").commitAllowingStateLoss();
                        if (active != null) {
                            fm.beginTransaction().hide(active).show(fragment2).commitAllowingStateLoss();
                        } else {
                            fm.beginTransaction().show(fragment2).commitAllowingStateLoss();
                        }

                    }


                } else {
                    setProgressBarGone();
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
                    String message = status.getMessage();
                    Toast.makeText(PaymentsActivity.this, message, Toast.LENGTH_SHORT).show();
                }

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError volleyError) {
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

                volleyError.printStackTrace();
                setProgressBarGone();

            }
        });
        jsonObjectRequestLogin.setRetryPolicy(new DefaultRetryPolicy(50000, 3, DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
//        RequestQueue requestQueue = Volley.newRequestQueue(this, commonSSLConnection.getHulkstack(PaymentsActivity.this));
        CampusWallet.getInstance().addToRequestQueue(jsonObjectRequestLogin, tag_json_object);
//        requestQueue.add(jsonObjectRequestLogin);

    }


    public void setProgressBarVisible() {
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE,
                WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE);
        binding.progressBar5.setVisibility(View.VISIBLE);

    }

    public void setProgressBarGone() {

        getWindow().clearFlags(WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE);
        binding.progressBar5.setVisibility(View.GONE);

    }

    @Override
    protected void onResume() {
        super.onResume();
    }
}
