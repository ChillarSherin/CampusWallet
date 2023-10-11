package com.chillarcards.campuswallet.login;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
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
import com.chillarcards.campuswallet.databinding.LayoutLoginActivityBinding;
import com.google.gson.Gson;

import com.chillarcards.campuswallet.application.CampusWallet;
import com.chillarcards.campuswallet.application.Constants;
import com.chillarcards.campuswallet.application.CustomConnectionBuddyActivity;
import com.chillarcards.campuswallet.networkmodels.checkuser.CheckUser;
import com.chillarcards.campuswallet.networkmodels.checkuser.Code;
import com.chillarcards.campuswallet.networkmodels.checkuser.Status;
import com.chillarcards.campuswallet.utils.CommonSSLConnection;
import it.sephiroth.android.library.tooltip.Tooltip;


public class LoginActivity extends CustomConnectionBuddyActivity {

    final String tag_json_object = "json_obj_req_r_login";
    private static final int PERMISSIONS_REQUEST_PHONE_CALL = 100;
    private LayoutLoginActivityBinding binding;
    Button ReloadBTN,GoBackBTN;
    TextView ErrorMessageTV,CodeErrorTV,NodataTV;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
     //   setContentView(R.layout.layout_login_activity);
        binding = LayoutLoginActivityBinding.inflate(getLayoutInflater());
        View view = binding.getRoot();
        setContentView(view);


        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            binding.phoneNumber.setTooltipText(getResources().getString(R.string.please_enter_reg_mobile));
        }
        else {
            Tooltip.make(LoginActivity.this,
                    new Tooltip.Builder(101)
                            .anchor(binding.phoneNumber, Tooltip.Gravity.TOP)
                            .closePolicy(new Tooltip.ClosePolicy()
                                    .insidePolicy(true, false)
                                    .outsidePolicy(true, true), 10000)
                            .activateDelay(1000)
                            .showDelay(900)
                            .text(getResources().getString(R.string.please_enter_reg_mobile))
                            .maxWidth(500)
                            .withArrow(true)
                            .withOverlay(true)

                            .floatingAnimation(Tooltip.AnimationBuilder.SLOW)
                            .build()
            ).show();
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
//        OneSignal.getTags(new OneSignal.GetTagsHandler() {
//            @Override
//            public void tagsAvailable(JSONObject tags) {
//                try {
//                    //System.out.println("One Signal Tags : " + tags.toString());
//                    Iterator<String> iter = tags.keys();
//                    while (iter.hasNext()) {
//                        String key = iter.next();
//                        //System.out.println("One Signal Tags key : " + key);
//                        OneSignal.deleteTag(key);
//
//                    }
//                }
//                catch(Exception e)
//                {
//                    //System.out.println("One Signal Tags : ERROR : " + e);
//                }
//
//
//            }
//        });
//        OneSignal.sendTag("log_out", "Login_OnResume");
        binding.ContactUsTV.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Bundle Firebase_bundle = new Bundle();
                Firebase_bundle.putString("CustomerCare","Contacted");
                mFirebaseAnalytics.logEvent(getResources().getString(R.string.Login_Customer_Care_Clicked),Firebase_bundle);

                callPermission();
            }
        });

        binding.button3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                login(binding.button3);
            }
        });

    }

    public void login(Button button) {
        binding.button3.setText(getResources().getString(R.string.checking));
        setProgressBarVisible();
        Bundle Firebase_bundle = new Bundle();
        Firebase_bundle.putString("MobileNumber",binding.phoneNumber.getText().toString());
        mFirebaseAnalytics.logEvent(getResources().getString(R.string.Login_Button_Clicked),Firebase_bundle);

        LoginCall(binding.phoneNumber.getText().toString());
    }

    public void setProgressBarVisible(){
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE,
                WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE);
        binding.progressBar.setVisibility(View.VISIBLE);

    }

    public void setProgressBarGone(){

        getWindow().clearFlags(WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE);
        binding.progressBar.setVisibility(View.GONE);

    }
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
    private void LoginCall(final String phoneNum) {

        try {

        String URL, parameters;
        parameters = "phoneNo=" + phoneNum ;
        URL = Constants.BASE_URL + "r_check_user.php?" + parameters.replaceAll(" ", "%20");
        System.out.println("CHECK---> URL " + URL);
            final CommonSSLConnection commonSSLConnection=new CommonSSLConnection();
        StringRequest jsonObjectRequestLogin = new StringRequest(Request.Method.GET, URL, new Response.Listener<String>() {
            @Override
            public void onResponse(String jsonObject) {
                System.out.println("CHECK---> Response " + jsonObject);
                setProgressBarGone();

                CampusWallet.getInstance().cancelPendingRequests(tag_json_object);

                Gson gson = new Gson();

                CheckUser model = gson.fromJson(jsonObject, CheckUser.class);

                Status status = model.getStatus();
                String code = status.getCode();

                if (code.equals("200")) {
                    binding.button3.setText(getResources().getString(R.string.S_u_c_c_e_s_s));
                    Code code1 = model.getCode();

                    String userName = code1.getParentName();
                    String userEmail = code1.getParentEmail();
                    String bypass=code1.getBypass();
//                    OneSignal.sendTag("user_mobile", phoneNum);
//                    OneSignal.sendTag("log_out", "Login_Button_Click");
                    if (bypass.equalsIgnoreCase("0"))
                    {

                        Intent i = new Intent(getApplicationContext(), PhoneAuthActivity.class);
                        Bundle b = new Bundle();
                        b.putString("userPhoneNumber", phoneNum);
                        b.putString("parentName", userName);
                        b.putString("parentEmail",userEmail );
                        i.putExtras(b);
                        startActivity(i);
                        killActivity();
                    }
                    else if (bypass.equalsIgnoreCase("1"))
                    {

                        Intent i = new Intent(getApplicationContext(), RegisterActivity.class);
                        Bundle b = new Bundle();
                        b.putString("userPhone", phoneNum);
                        b.putString("userEmail", userEmail);
                        b.putString("userName", userName);
                        i.putExtras(b);
                        startActivity(i);
                        killActivity();
                    }




                } else {
                    setProgressBarGone();
                    binding.button3.setText(getResources().getString(R.string.l_o_g_i_n));
                    binding.button3.setEnabled(true);
                    String message = status.getMessage();
                    Toast.makeText(LoginActivity.this, message, Toast.LENGTH_SHORT).show();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError volleyError) {
                setProgressBarGone();
                binding.button3.setText(getResources().getString(R.string.l_o_g_i_n));
                binding.button3.setEnabled(true);
                CampusWallet.getInstance().cancelPendingRequests(tag_json_object);
                VolleyLog.d("Object Error : ", volleyError.getMessage());
                Toast.makeText(LoginActivity.this, getResources().getString(R.string.network_error_try), Toast.LENGTH_SHORT).show();
            }
        });
        jsonObjectRequestLogin.setRetryPolicy(new DefaultRetryPolicy(50000, 3, DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
//            RequestQueue requestQueue = Volley.newRequestQueue(this, commonSSLConnection.getHulkstack(LoginActivity.this));
        CampusWallet.getInstance().addToRequestQueue(jsonObjectRequestLogin, tag_json_object);
//            requestQueue.add(jsonObjectRequestLogin);

        }catch (Exception e)
        {
            setProgressBarGone();
            binding.button3.setText(getResources().getString(R.string.l_o_g_i_n));
            binding.button3.setEnabled(true);
            CampusWallet.getInstance().cancelPendingRequests(tag_json_object);
            Toast.makeText(LoginActivity.this, getResources().getString(R.string.network_error_try), Toast.LENGTH_SHORT).show();
        }

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        CampusWallet.getInstance().cancelPendingRequests(tag_json_object);
    }

    private void killActivity() {
        ((Activity) LoginActivity.this).finish();
    }

}
