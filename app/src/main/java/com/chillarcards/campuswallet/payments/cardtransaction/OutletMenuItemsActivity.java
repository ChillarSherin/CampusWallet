package com.chillarcards.campuswallet.payments.cardtransaction;

import android.app.Activity;
import android.os.Bundle;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
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
import com.chillarcards.campuswallet.databinding.ActivityOutletMenuBinding;
import com.chillarcards.campuswallet.databinding.ActivityOutletMenuItemBinding;
import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.List;

import com.chillarcards.campuswallet.application.CampusWallet;
import com.chillarcards.campuswallet.application.Constants;
import com.chillarcards.campuswallet.application.CustomConnectionBuddyActivity;
import com.chillarcards.campuswallet.application.PrefManager;
import com.chillarcards.campuswallet.networkmodels.TransactionMenuItems.Code;
import com.chillarcards.campuswallet.networkmodels.TransactionMenuItems.Status;
import com.chillarcards.campuswallet.networkmodels.TransactionMenuItems.TransactionOutletMenuItems;
import com.chillarcards.campuswallet.utils.CommonSSLConnection;

public class OutletMenuItemsActivity extends CustomConnectionBuddyActivity {
    final String tag_json_object = "r_outlets";
    Activity activity;
    List<Code> code1= new ArrayList<>();

    PrefManager prefManager;
    String phoneNum,studentId,StudentNasme,OutletTypeID;
    private OutletMenuListAdapter mAdapter;
    private ActivityOutletMenuItemBinding binding;

    Button ReloadBTN,GoBackBTN;
    TextView ErrorMessageTV,CodeErrorTV,NodataTV;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
       //setContentView(R.layout.activity_outlet_menu_item);

        binding = ActivityOutletMenuItemBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        ReloadBTN = binding.getRoot().findViewById(R.id.ReloadBTN);
        ErrorMessageTV=binding.getRoot().findViewById(R.id.ErrorMessageTV);
        CodeErrorTV=binding.getRoot().findViewById(R.id.CodeErrorTV);
        GoBackBTN =binding.getRoot().findViewById(R.id.GoBackBTN);
        NodataTV =binding.getRoot().findViewById(R.id.NodataTV);

        binding.progressBar.setVisibility(View.GONE);
        prefManager=new PrefManager(OutletMenuItemsActivity.this);
        binding.HeaderTV.setText(getResources().getString(R.string.outlet_title));
        binding.BackIV.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });
        Bundle bundle=getIntent().getExtras();
        OutletTypeID=bundle.getString("OutletTypeID");
        activity=this;
    }

    @Override
    protected void onResume() {
        super.onResume();
        callOutletsItemsPHP();
        addTextListener();
        binding.searchbutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                addTextListener();
            }
        });

    }
    private void addTextListener() {

        binding.inputSearch.addTextChangedListener(new TextWatcher() {

            public void afterTextChanged(Editable s) {}

            public void beforeTextChanged(CharSequence s, int start, int count, int after) {}

            public void onTextChanged(CharSequence query, int start, int before, int count) {

                query = query.toString().toLowerCase();

                final List<Code> filteredList1 = new ArrayList<>();



                for (int i = 0; i < code1.size(); i++) {

                    final String text = code1.get(i).getItemName().toLowerCase();

                    if (text.contains(query)) {

                        //System.out.println("SHANIL 11111: ");

                        filteredList1.add(code1.get(i));


                    }
                }

                binding.OutletRV.setLayoutManager(new LinearLayoutManager(OutletMenuItemsActivity.this));
                mAdapter = new OutletMenuListAdapter(filteredList1,
                        OutletMenuItemsActivity.this, activity,mFirebaseAnalytics);
                binding.OutletRV.setAdapter(mAdapter);
                mAdapter.notifyDataSetChanged();  // data set changed
            }
        });
    }
    private void callOutletsItemsPHP() {

        binding.progressBar.setVisibility(View.VISIBLE);
        phoneNum = prefManager.getUserPhone();  //getting phone number from shared preference
        studentId = prefManager.getStudentId();
        StudentNasme = prefManager.getStudentName();


        String URL, parameters;
        parameters = "phoneNo=" + phoneNum + "&studentID=" + studentId+"&transactionTypeID="+OutletTypeID;
        URL = Constants.BASE_URL  + "r_outlet_item_details.php?" + parameters.replaceAll(" ", "%20");
        //System.out.println("CHECK---> URL " + URL);
        final CommonSSLConnection commonSSLConnection=new CommonSSLConnection();
        StringRequest jsonObjectRequestLogin = new StringRequest(Request.Method.GET, URL, new Response.Listener<String>() {
            @Override
            public void onResponse(String jsonObject) {
                binding.progressBar.setVisibility(View.GONE);
                //System.out.println("CHECK---> Response " + jsonObject);
                Gson gson = new Gson();

                TransactionOutletMenuItems model = gson.fromJson(jsonObject, TransactionOutletMenuItems.class);
                Status status = model.getStatus();
                String code = status.getCode();

                if (code.equals("200")) {

                    code1.clear();
                    code1= model.getCode();
                    if (code1.size()>0)
                    {
                        findViewById(R.id.NodataLL).setVisibility(View.GONE);
                        findViewById(R.id.ErrorLL).setVisibility(View.GONE);
                        binding.OutletRV.setVisibility(View.VISIBLE);
                        binding.OutletRV.setLayoutManager(new LinearLayoutManager(OutletMenuItemsActivity.this, LinearLayoutManager.VERTICAL, false));
                        binding.OutletRV.setItemAnimator(new DefaultItemAnimator());
                        binding.OutletRV.setNestedScrollingEnabled(false);
                        mAdapter = new OutletMenuListAdapter(code1,OutletMenuItemsActivity.this,OutletMenuItemsActivity.this,mFirebaseAnalytics);
                        binding.OutletRV.setAdapter(mAdapter);

                    }
                    else {
                        NodataTV.setText(getResources().getString(R.string.no_data_found));
                        GoBackBTN.setText(getResources().getString(R.string.go_back));
//                        ErrorImage.setBackgroundResource(R.drawable.nodata);
                        findViewById(R.id.NodataLL).setVisibility(View.VISIBLE);
                        GoBackBTN.setVisibility(View.GONE);
                    }

                }
                else  if (code.equals("204")){
                    NodataTV.setText(getResources().getString(R.string.no_data_found));
                    GoBackBTN.setText(getResources().getString(R.string.go_back));
//                        ErrorImage.setBackgroundResource(R.drawable.nodata);
                    findViewById(R.id.NodataLL).setVisibility(View.VISIBLE);
                    GoBackBTN.setVisibility(View.GONE);
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
                    Toast.makeText(OutletMenuItemsActivity.this, message, Toast.LENGTH_SHORT).show();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError volleyError) {
                binding.progressBar.setVisibility(View.GONE);
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
                Toast.makeText(OutletMenuItemsActivity.this, getResources().getString(R.string.network_error_try), Toast.LENGTH_SHORT).show();
            }
        });
        jsonObjectRequestLogin.setRetryPolicy(new DefaultRetryPolicy(50000, 3, DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
//        RequestQueue requestQueue = Volley.newRequestQueue(this, commonSSLConnection.getHulkstack(CardTransactionActivity.this));
        CampusWallet.getInstance().addToRequestQueue(jsonObjectRequestLogin, tag_json_object);
//        requestQueue.add(jsonObjectRequestLogin);

    }
}
