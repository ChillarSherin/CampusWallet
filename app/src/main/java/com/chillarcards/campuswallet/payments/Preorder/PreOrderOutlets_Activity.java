package com.chillarcards.campuswallet.payments.Preorder;

import android.app.Activity;
import android.app.Dialog;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.appcompat.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.inputmethod.InputMethodManager;
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
import com.chillarcards.campuswallet.databinding.HistorydetailsPopupBinding;
import com.chillarcards.campuswallet.databinding.LayoutPreorderHistoryBinding;
import com.chillarcards.campuswallet.databinding.PreorderoneBinding;
import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.List;

import com.chillarcards.campuswallet.application.CampusWallet;
import com.chillarcards.campuswallet.application.Constants;
import com.chillarcards.campuswallet.application.CustomConnectionBuddyActivity;
import com.chillarcards.campuswallet.application.PrefManager;
import com.chillarcards.campuswallet.networkmodels.PreOrderOutlets.PreOutlet;
import com.chillarcards.campuswallet.networkmodels.PreOrderOutlets.PreOutletCode;
import com.chillarcards.campuswallet.networkmodels.PreOrderOutlets.PreOutletStatus;
import com.chillarcards.campuswallet.utils.CommonSSLConnection;

@SuppressWarnings("ALL")
public class PreOrderOutlets_Activity extends CustomConnectionBuddyActivity {


    private final List<String> typeid = new ArrayList<>();
    private final List<String> typename = new ArrayList<>();
    private final List<String> preorderItemTypeTimingID = new ArrayList<>();
    private final List<String> preorderItemTypeTimingDay = new ArrayList<>();
    private final List<String> preorderItemTypeTimingTime = new ArrayList<>();

    private RecyclerView mRecyclerView;
    private Activity activity;
    private ProgressBar progressBar;
    private PreOrderOutletAdapter mAdapter;

    private String parentPh,s_Id,UserListCount;
    Button Reload;
    TextView Txt_Content;
    ImageView ErrorImage;
    ImageView CartButton;
    String DayIDString="1",TimingId,DayID,SchoolId;
//    Dialog PopupConfrm;
    Dialog OtpDialoglogout;
    TextView Texttitle,CountTxt;
    Button OkButton,NoButton;
//    ArrayList<Sales_Item> userList   = new ArrayList<Sales_Item>();
    Dialog NoInternet;
    int InternetFlag = 0;
    final String tag_json_object = "r_preorder_outlets";

    Button ReloadBTN,GoBackBTN;
    TextView ErrorMessageTV,CodeErrorTV,NodataTV;
    PrefManager prefManager;
    private PreorderoneBinding binding;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //setContentView(R.layout.preorderone);
        binding = PreorderoneBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        activity = this;
        hideSoftKeyboard();

        prefManager=new PrefManager(PreOrderOutlets_Activity.this);
        binding.HeaderTV.setText(getResources().getString(R.string.outlet_title));
        binding.BackIV.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });
        findViewById(R.id.ErrorLL).setVisibility(View.GONE);
        findViewById(R.id.NodataLL).setVisibility(View.GONE);

        CountTxt= (TextView) findViewById(R.id.cartcountIV);
        CartButton = (ImageView) findViewById(R.id.cartIV);

        CartButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mFirebaseAnalytics.logEvent(getResources().getString(R.string.Pre_Order_Cart_Button_clicked),new Bundle());
                Intent in=new Intent(getApplicationContext(),PreOrderCartList_Activity.class);
                in.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity(in);


            }
        });
//        drawerArrow();

        progressBar = (ProgressBar) findViewById(R.id.ProgressBar);
        mRecyclerView = (RecyclerView) findViewById(R.id.list);
        progressBar.setVisibility(View.GONE);

    }



    public void hideSoftKeyboard() {
        if(getCurrentFocus()!=null) {
            InputMethodManager inputMethodManager = (InputMethodManager) getSystemService(INPUT_METHOD_SERVICE);
            inputMethodManager.hideSoftInputFromWindow(getCurrentFocus().getWindowToken(), 0);
        }
    }
    @Override
    protected void onResume() {
        super.onResume();
        parentPh = prefManager.getUserPhone();
        s_Id= prefManager.getStudentId();
        SchoolId= prefManager.getSChoolID();
        DayID= Constants.daySelected;
        Bundle bundle = getIntent().getExtras();
        TimingId = bundle.getString("timingid");
        if(DayID.equals("Today")){

            DayIDString="1";

        }else {
            DayIDString="2";
        }
        PreOrderOutletsList(DayIDString);
        UserListCount= String.valueOf(Constants.sales_items.size());
        if (Constants.sales_items.size()!=0) {

            CountTxt.setText("" + UserListCount);
            CountTxt.setVisibility(View.VISIBLE);
        }
        else
        {
            CountTxt.setVisibility(View.GONE);
        }



    }

    public void PreOrderOutletsList(String day)
    {
         progressBar.setVisibility(View.VISIBLE);


        String URL, parameters;
        parameters = "schoolID=" + SchoolId + "&preorderTimingID=" +TimingId +"&daySelected="+day;
        URL = Constants.BASE_URL  + "r_preorder_outlets.php?" + parameters.replaceAll(" ", "%20");
        //System.out.println("CHECK---> URL " + URL);
        CommonSSLConnection commonSSLConnection=new CommonSSLConnection();
        StringRequest jsonObjectRequestLogin = new StringRequest(Request.Method.GET, URL, new Response.Listener<String>() {
            @Override
            public void onResponse(String jsonObject) {
                  progressBar.setVisibility(View.GONE);
                //System.out.println("CHECK---> Response " + jsonObject);
               // progressBar.setVisibility(View.VISIBLE);
                Gson gson = new Gson();

                PreOutlet model = gson.fromJson(jsonObject, PreOutlet.class);
                PreOutletStatus status = model.getStatus();
                String code = status.getCode();
                if (code.equals("200"))
                {

                    typeid.clear();
                    typename.clear();
                    preorderItemTypeTimingID.clear();
                    preorderItemTypeTimingDay.clear();
                    preorderItemTypeTimingTime.clear();

                    Log.i("shanil ", String.valueOf(model.getStatus()));
                    List<PreOutletCode> relationship=model.getCode();
                    if (relationship.size()>0) {
                        for (int i = 0; i < relationship.size(); i++) {

                            PreOutletCode relncode=relationship.get(i);
                            typeid.add(relncode.getItemTypeID());
                            typename.add(relncode.getItemTypeName());
                            preorderItemTypeTimingID.add(relncode.getPreorderItemTypeTimingID());
                            preorderItemTypeTimingDay.add(relncode.getPreorderItemTypeTimingDay());
                            preorderItemTypeTimingTime.add(relncode.getPreorderItemTypeTimingTime());

                        }
                        try {

                            if (typeid.size() > 0) {

                                mRecyclerView.setVisibility(View.VISIBLE);
                                mRecyclerView.setLayoutManager(new LinearLayoutManager(activity, LinearLayoutManager.VERTICAL, false));
                                mRecyclerView.setItemAnimator(new DefaultItemAnimator());
                                mRecyclerView.setNestedScrollingEnabled(false);
                                mAdapter = new PreOrderOutletAdapter(typeid, typename,preorderItemTypeTimingID,
                                        TimingId,preorderItemTypeTimingDay,preorderItemTypeTimingTime,
                                        activity, getApplicationContext(),mFirebaseAnalytics);
                                mRecyclerView.setAdapter(mAdapter);

                            } else {

                                progressBar.setVisibility(View.GONE);
                                mRecyclerView.setVisibility(View.GONE);

                                NodataTV.setText(getResources().getString(R.string.no_data_found));
                                GoBackBTN.setText(getResources().getString(R.string.go_back));
//                        ErrorImage.setBackgroundResource(R.drawable.nodata);
                                findViewById(R.id.NodataLL).setVisibility(View.VISIBLE);
                                GoBackBTN.setVisibility(View.GONE);
                            }
                        } catch (Exception e) // no guruji
                        {

                            e.printStackTrace();

                        }

                    }else {

                        String Msg=status.getMessage();
                        mRecyclerView.setVisibility(View.GONE);
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

                Toast.makeText(PreOrderOutlets_Activity.this, getResources().getString(R.string.network_error_try), Toast.LENGTH_SHORT).show();
            }
        });
        jsonObjectRequestLogin.setRetryPolicy(new DefaultRetryPolicy(50000, 3, DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
//        RequestQueue requestQueue = Volley.newRequestQueue(this, commonSSLConnection.getHulkstack(PreOrderOutlets_Activity.this));
        CampusWallet.getInstance().addToRequestQueue(jsonObjectRequestLogin, tag_json_object);
//        requestQueue.add(jsonObjectRequestLogin);
    }
    @Override
    protected void onStart() {
        super.onStart();

    }
    @Override
    protected void onStop() {
        super.onStop();

    }
    @Override
    protected void onDestroy() {
        super.onDestroy();
        CampusWallet.getInstance().cancelPendingRequests(tag_json_object);
    }
    @Override
    public void onBackPressed() {
//        super.onBackPressed();
        //System.out.println("Cart Size1 : "+UserListCount.length());
        if (Constants.sales_items.size() > 0) {

            //System.out.println("Cart Size : "+ Constants.sales_items.size());


            OtpDialoglogout = new Dialog(this);
            OtpDialoglogout.requestWindowFeature(Window.FEATURE_NO_TITLE);
            OtpDialoglogout.setContentView(R.layout.layout_confrmpopup);
            OtpDialoglogout.setCanceledOnTouchOutside(true);
            OtpDialoglogout.getWindow().setLayout(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
            OtpDialoglogout.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
            TextView confirm = (TextView) OtpDialoglogout.findViewById(R.id.confirm);
            TextView deny=(TextView) OtpDialoglogout.findViewById(R.id.deny);
            OtpDialoglogout.show();


            confirm.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                      OtpDialoglogout.cancel();

                    Constants.sales_items.clear();
                    Intent i=new Intent(getApplicationContext(),PreOrderTimmings.class);
                    i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK|Intent.FLAG_ACTIVITY_CLEAR_TOP);
                    startActivity(i);
                    finish();
                    overridePendingTransition(R.anim.fade_in, R.anim.fade_out);
                    finish();
                   // onBackPressed();


                }
            });


            deny.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    OtpDialoglogout.cancel();


                }
            });


        }else{
//            new FieldOffTask().execute();
            Intent in=new Intent(activity,PreOrderTimmings.class);
            in.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            startActivity(in);
            return;
        }
    }

}
