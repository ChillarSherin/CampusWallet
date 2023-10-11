package com.chillarcards.campuswallet.Diary.LeaveRequest;

import androidx.fragment.app.FragmentTransaction;
import android.os.Bundle;
import androidx.annotation.Nullable;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
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
import com.android.volley.VolleyLog;
import com.android.volley.toolbox.StringRequest;
import com.chillarcards.campuswallet.R;
import com.chillarcards.campuswallet.databinding.ActivityHistoryReceiptBinding;
import com.chillarcards.campuswallet.databinding.LayoutLeaveReqActivityBinding;
import com.google.gson.Gson;

import java.util.List;

import com.chillarcards.campuswallet.application.CampusWallet;
import com.chillarcards.campuswallet.application.Constants;
import com.chillarcards.campuswallet.application.CustomConnectionBuddyActivity;
import com.chillarcards.campuswallet.application.PrefManager;
import com.chillarcards.campuswallet.networkmodels.leaverequest.Code;
import com.chillarcards.campuswallet.networkmodels.leaverequest.Leaverequest;
import com.chillarcards.campuswallet.networkmodels.leaverequest.Status;
import com.chillarcards.campuswallet.utils.CommonSSLConnection;


public class LeaveRequestActivity extends CustomConnectionBuddyActivity implements CallBack{

    PrefManager prefManager;
    final String tag_json_object = "r_leave_request";

    Integer messageID = -1;
    private LayoutLeaveReqActivityBinding binding;
    Button ReloadBTN,GoBackBTN;
    TextView ErrorMessageTV,CodeErrorTV,NodataTV;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
      //  setContentView(R.layout.layout_leave_req_activity);
       
        binding = LayoutLeaveReqActivityBinding.inflate(getLayoutInflater());
        View view = binding.getRoot();
        setContentView(view);

        ReloadBTN = binding.getRoot().findViewById(R.id.ReloadBTN);
        ErrorMessageTV=binding.getRoot().findViewById(R.id.ErrorMessageTV);
        CodeErrorTV=binding.getRoot().findViewById(R.id.CodeErrorTV);
        GoBackBTN =binding.getRoot().findViewById(R.id.GoBackBTN);
        NodataTV =binding.getRoot().findViewById(R.id.NodataTV);
        
        try {
            Bundle b = getIntent().getExtras();
            messageID = b.getInt("messageID");
        }catch (Exception e){

        }

        prefManager = new PrefManager(this);

        binding.HeaderTV.setText(getResources().getString(R.string.leaverequest_header));
        binding.BackIV.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });

        binding.floatingActionButton.setOnClickListener(v -> {
            mFirebaseAnalytics.logEvent(getResources().getString(R.string.New_Leave_Request_Add_Button_Clicked),new Bundle());
            CreateLeaveRequestDialog dialog = new CreateLeaveRequestDialog(LeaveRequestActivity.this);
            FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
            dialog.show(ft, CreateLeaveRequestDialog.TAG);
        });

        binding.LeaveRequesrSRL.setColorSchemeResources(R.color.colorAccent);

    }

    @Override
    protected void onResume() {
        super.onResume();

        loadLeaveRequest();
        binding.LeaveRequesrSRL.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                loadLeaveRequest();
                binding.LeaveRequesrSRL.setRefreshing(false);
            }
        });

    }


    @Override
    protected void onDestroy() {
        super.onDestroy();
        CampusWallet.getInstance().cancelPendingRequests(tag_json_object);
    }

    public int getIndexByname(String pName,List<Code> dataSet)
    {
        for(Code _item : dataSet)
        {
            if(_item.getLeaveRequestID().equals(pName))
                return dataSet.indexOf(_item);
        }
        return -1;
    }


    private void loadLeaveRequest() {


        String userPhone = prefManager.getUserPhone();
        String studentId = prefManager.getStudentId();
        setProgressBarVisible();
        String URL, parameters;
        parameters = "phoneNo=" +userPhone+"&studentID="+studentId;
        URL = Constants.BASE_URL + "r_leave_request.php?" + parameters.replaceAll(" ", "%20");
        //System.out.println("CHECK---> URL " + URL);
        CommonSSLConnection commonSSLConnection=new CommonSSLConnection();
        StringRequest jsonObjectRequestLogin = new StringRequest(Request.Method.GET, URL, new Response.Listener<String>() {
            @Override
            public void onResponse(String jsonObject) {
                //System.out.println("CHECK---> Response " + jsonObject);
                setProgressBarGone();

                CampusWallet.getInstance().cancelPendingRequests(tag_json_object);

                Gson gson = new Gson();
                Leaverequest leaverequest =gson.fromJson(jsonObject,Leaverequest.class);
                Status status = leaverequest.getStatus();
                String code = status.getCode();


                if(code.equals("200")){

                    List<Code> myDataset= leaverequest.getCode();
                    if (myDataset.size()>0) {

                        if(messageID!=-1) {

                            int index = getIndexByname(String.valueOf(messageID), myDataset);
                            System.out.println("Index: " + index);

                            if (index == -1) {

                                binding.recyclerView4.setHasFixedSize(true);
                                binding.recyclerView4.setLayoutManager(new LinearLayoutManager(LeaveRequestActivity.this, LinearLayoutManager.VERTICAL, false));
                                binding.recyclerView4.setNestedScrollingEnabled(false);
                                LeaveRequestAdapter mAdapter = new LeaveRequestAdapter(myDataset, index, LeaveRequestActivity.this,
                                        LeaveRequestActivity.this,mFirebaseAnalytics);
                                mAdapter.notifyDataSetChanged();
                                binding.recyclerView4.setAdapter(mAdapter);

                            } else {

                                binding.recyclerView4.setHasFixedSize(true);
                                binding.recyclerView4.setLayoutManager(new LinearLayoutManager(LeaveRequestActivity.this));
                                binding.recyclerView4.smoothScrollToPosition(index);
                                binding.recyclerView4.setNestedScrollingEnabled(false);
                                LeaveRequestAdapter mAdapter = new LeaveRequestAdapter(myDataset,index, LeaveRequestActivity.this,
                                        LeaveRequestActivity.this,mFirebaseAnalytics);
                                mAdapter.notifyDataSetChanged();
                                binding.recyclerView4.setAdapter(mAdapter);

                            }
                        }else {

                            binding.recyclerView4.setHasFixedSize(true);
                            binding.recyclerView4.setLayoutManager(new LinearLayoutManager(LeaveRequestActivity.this, LinearLayoutManager.VERTICAL, false));
                            binding.recyclerView4.setNestedScrollingEnabled(false);
                            LeaveRequestAdapter mAdapter = new LeaveRequestAdapter(myDataset,-1, LeaveRequestActivity.this,
                                    LeaveRequestActivity.this,mFirebaseAnalytics);
                            mAdapter.notifyDataSetChanged();
                            binding.recyclerView4.setAdapter(mAdapter);

                        }


                    }
                    else {
                        NodataTV.setText(getResources().getString(R.string.no_data_found));
                        GoBackBTN.setText(getResources().getString(R.string.go_back));
//                        ErrorImage.setBackgroundResource(R.drawable.nodata);
                        findViewById(R.id.NodataLL).setVisibility(View.VISIBLE);
                        GoBackBTN.setVisibility(View.GONE);
                    }

                }
                else if (code.equals("204"))
                {
                    NodataTV.setText(getResources().getString(R.string.no_data_found));
                    GoBackBTN.setText(getResources().getString(R.string.go_back));
//                        ErrorImage.setBackgroundResource(R.drawable.nodata);
                    findViewById(R.id.NodataLL).setVisibility(View.VISIBLE);
                    GoBackBTN.setVisibility(View.GONE);
                }

                else{
                    setProgressBarGone();
                    String message = status.getMessage();
                    Toast.makeText(LeaveRequestActivity.this, message, Toast.LENGTH_SHORT).show();
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
                setProgressBarGone();
                CampusWallet.getInstance().cancelPendingRequests(tag_json_object);
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
                Toast.makeText(LeaveRequestActivity.this, getResources().getString(R.string.network_error_try), Toast.LENGTH_SHORT).show();
            }
        });

        jsonObjectRequestLogin.setRetryPolicy(new DefaultRetryPolicy(50000, 3, DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
//        RequestQueue requestQueue = Volley.newRequestQueue(this, commonSSLConnection.getHulkstack(LeaveRequestActivity.this));
        CampusWallet.getInstance().addToRequestQueue(jsonObjectRequestLogin, tag_json_object);
//        requestQueue.add(jsonObjectRequestLogin);

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


    @Override
    public void message(String message) {
        //System.out.println(message);

        if(message.equals("200")){

            loadLeaveRequest();

        }
    }
}
