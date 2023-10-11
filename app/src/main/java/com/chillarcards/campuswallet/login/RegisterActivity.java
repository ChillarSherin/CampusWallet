package com.chillarcards.campuswallet.login;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.Toast;

import androidx.annotation.Nullable;

import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.chillarcards.campuswallet.R;
import com.chillarcards.campuswallet.databinding.LayoutRegisterActivityBinding;
import com.google.gson.Gson;

import com.chillarcards.campuswallet.application.CampusWallet;
import com.chillarcards.campuswallet.application.Constants;
import com.chillarcards.campuswallet.application.CustomConnectionBuddyActivity;
import com.chillarcards.campuswallet.application.PrefManager;
import com.chillarcards.campuswallet.networkmodels.updateuser.Status;
import com.chillarcards.campuswallet.networkmodels.updateuser.UpdateUser;
import com.chillarcards.campuswallet.studentselection.StudentListActivity;
import com.chillarcards.campuswallet.utils.CommonSSLConnection;


/**
 * Created by Codmob on 17-07-18.
 */

public class RegisterActivity extends CustomConnectionBuddyActivity {


    final String tag_json_object = "json_obj_u_user_details";
    private LayoutRegisterActivityBinding binding;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
      //  setContentView(R.layout.layout_register_activity);
        binding = LayoutRegisterActivityBinding.inflate(getLayoutInflater());
        View view = binding.getRoot();
        setContentView(view);
        
        Bundle b = getIntent().getExtras();
        String userPhone = b.getString("userPhone");
        String userName = b.getString("userName");
        String userEmail = b.getString("userEmail");

        binding.userName.setText(userName);
        binding.userEmail.setText(userEmail);
        binding.userPhone.setText(userPhone);
        binding.userPhone.setEnabled(false);

        binding.button3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                setBtnContinue(binding.button3);
            }
        });


    }


    public void setBtnContinue(Button btnContinue) {
       // this.binding.button3 = btnContinue;
        btnContinue.setText(getResources().getString(R.string.u_p_d_a_t_i_n_g));

        String phoneNum = binding.userPhone.getText().toString();
        String userEmail = binding.userEmail.getText().toString();
        String userName = binding.userName.getText().toString();

        setProgressBarVisible();

        Bundle Firebase_bundle = new Bundle();
        Firebase_bundle.putString("MobileNumber",phoneNum);
        Firebase_bundle.putString("Email",userEmail);
        Firebase_bundle.putString("UserName",userName);
        mFirebaseAnalytics.logEvent(getResources().getString(R.string.Register_Button_Clicked),Firebase_bundle);
        mFirebaseAnalytics.setUserProperty(getResources().getString(R.string.UP_UserName),userName);
        mFirebaseAnalytics.setUserProperty(getResources().getString(R.string.UP_UserEmail),userEmail);
        mFirebaseAnalytics.setUserProperty(getResources().getString(R.string.UP_UserPhone),phoneNum);

        UpdateUser(phoneNum,userEmail,userName);

    }

    public void setProgressBarVisible(){
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE,
                WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE);
        binding.progressBar3.setVisibility(View.VISIBLE);

    }

    public void setProgressBarGone(){

        getWindow().clearFlags(WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE);
        binding.progressBar3.setVisibility(View.GONE);

    }

    private void UpdateUser(final String phoneNum, final String userEmail, final String userName) {


        String URL, parameters;
        CommonSSLConnection commonSSLConnection=new CommonSSLConnection();
        parameters = "phoneNo=" + phoneNum +"&userEmail="+userEmail+"&userName="+userName;
        URL = Constants.BASE_URL + "u_user_details.php?" + parameters.replaceAll(" ", "%20");
        //System.out.println("CHECK---> URL " + URL);
        StringRequest jsonObjectRequestLogin = new StringRequest(Request.Method.GET, URL, new Response.Listener<String>() {
            @Override
            public void onResponse(String jsonObject) {
                setProgressBarGone();
                CampusWallet.getInstance().cancelPendingRequests(tag_json_object);
                Gson gson = new Gson();
                UpdateUser updateUser = gson.fromJson(jsonObject, UpdateUser.class);

                Status status = updateUser.getStatus();
                String code = status.getCode();
                if (code.equals("200")) {

                    binding.button3.setText(getResources().getString(R.string.S_u_c_c_e_s_s));

                    PrefManager prefManager = new PrefManager(RegisterActivity.this);
                    prefManager.setIsNumberVerified(true);
                    prefManager.setVersion32(true);
                    prefManager.setFirstTimeLaunch(false);
                    prefManager.setUserEmail(userEmail);
                    prefManager.setUserName(userName);
                    prefManager.setUserPhone(phoneNum);

                    //UNIQUE_ID is used to uniquely identify a user.
                    String UNIQUE_ID=phoneNum;
//                    MoEHelper.getInstance(getApplicationContext()).setUniqueId(UNIQUE_ID);


                    Intent intent=new Intent(RegisterActivity.this,StudentListActivity.class);
                    startActivity(intent);
                    finish();


                }else{
                    setProgressBarGone();
                    binding.button3.setText(getResources().getString(R.string.c_o_n_t_i_n_u_e));
                    String message = status.getMessage();
                    Toast.makeText(RegisterActivity.this, message, Toast.LENGTH_SHORT).show();
                }


            }
        }, new Response.ErrorListener()

        {
            @Override
            public void onErrorResponse (VolleyError volleyError){
                setProgressBarGone();
                binding.button3.setText(getResources().getString(R.string.c_o_n_t_i_n_u_e));
                CampusWallet.getInstance().cancelPendingRequests(tag_json_object);

            }


        });
        jsonObjectRequestLogin.setRetryPolicy(new DefaultRetryPolicy(50000, 3, DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
//        RequestQueue requestQueue = Volley.newRequestQueue(this, commonSSLConnection.getHulkstack(RegisterActivity.this));
        CampusWallet.getInstance().addToRequestQueue(jsonObjectRequestLogin, tag_json_object);
//        requestQueue.add(jsonObjectRequestLogin);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        CampusWallet.getInstance().cancelPendingRequests(tag_json_object);
    }
}


