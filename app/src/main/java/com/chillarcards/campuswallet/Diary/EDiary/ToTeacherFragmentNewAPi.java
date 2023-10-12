package com.chillarcards.campuswallet.Diary.EDiary;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.VolleyLog;
import com.android.volley.toolbox.StringRequest;
import com.chillarcards.campuswallet.R;
import com.chillarcards.campuswallet.databinding.ActivityToTeachermessageDetailBinding;
import com.google.firebase.analytics.FirebaseAnalytics;
import com.google.gson.Gson;

import java.util.List;

import com.chillarcards.campuswallet.Diary.LeaveRequest.CallBack;
import com.chillarcards.campuswallet.application.CampusWallet;
import com.chillarcards.campuswallet.application.Constants;
import com.chillarcards.campuswallet.application.PrefManager;
import com.chillarcards.campuswallet.networkmodels.ParentMessagesNew.Code;
import com.chillarcards.campuswallet.networkmodels.ParentMessagesNew.ParentMessages;
import com.chillarcards.campuswallet.networkmodels.ParentMessagesNew.Status;
import com.chillarcards.campuswallet.utils.CommonSSLConnection;


public class ToTeacherFragmentNewAPi extends Fragment implements CallBack {


    PrefManager prefManager;
    Activity activity;
    private ToteacherMessageDetailAdapter mAdapter;

    final String tag_json_object = "toteacher";
    FirebaseAnalytics mFirebaseAnalytics;
    
    Button ReloadBTN,GoBackBTN;
    TextView ErrorMessageTV,CodeErrorTV,NodataTV;
    private ActivityToTeachermessageDetailBinding binding;

    public ToTeacherFragmentNewAPi() {
        // Required empty public constructor
    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
// Retain this fragment across configuration changes.
        setRetainInstance(true);

    }

    @Override
    public void onStart() {
        super.onStart();
        binding.progressBar.setVisibility(View.GONE);
        binding.HeaderTV.setText(getActivity().getResources().getString(R.string.message_toteacher_header));
        binding.BackIV.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getActivity().onBackPressed();
            }
        });
        binding.ToteacherSRL.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                phpGetAttendanceMain();
                binding.ToteacherSRL.setRefreshing(false);
            }
        });

    }
//
//    @Override
//    public void setUserVisibleHint(boolean isVisibleToUser) {
//        super.setUserVisibleHint(isVisibleToUser);
//        if (isVisibleToUser) {
//            getFragmentManager().beginTransaction().detach(this).attach(this).commit();
//        }
//
//    }

    @Override
    public void onResume() {
        super.onResume();
        //System.out.println("Abhinand : onResume");

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        binding = ActivityToTeachermessageDetailBinding.inflate(inflater, container, false);

        prefManager=new PrefManager(getActivity());
        activity=getActivity();

        binding.TeacherMessageDetailsRV.setLayoutManager(new LinearLayoutManager(activity, LinearLayoutManager.VERTICAL, false));
        binding.TeacherMessageDetailsRV.setItemAnimator(new DefaultItemAnimator());
        binding.TeacherMessageDetailsRV.setNestedScrollingEnabled(false);
        mFirebaseAnalytics=FirebaseAnalytics.getInstance(getActivity());
        binding.ToteacherSRL.setColorSchemeResources(R.color.colorAccent);
        phpGetAttendanceMain();
        
        binding.MessageToteacherAddFB.setOnClickListener(view -> submit());
        
        return binding.getRoot();
    }

    public void submit() {

        mFirebaseAnalytics.logEvent(getActivity().getResources().getString(R.string.Add_New_Message_To_Teacher_Button_Clicked),new Bundle());

        MessageToteacherDialog dialog=new MessageToteacherDialog(this);

        FragmentTransaction ft = getActivity().getSupportFragmentManager().beginTransaction();
        dialog.show(ft, MessageToteacherDialog.TAG);
    }
    @Override
    public void onAttach(Context context) {
        super.onAttach(context);

    }

    @Override
    public void onPause() {
        super.onPause();
        //System.out.println("Abhinand : onPause");
    }

    @Override
    public void onStop() {
        super.onStop();
        //System.out.println("Abhinand : onStop");
    }

    @Override
    public void onDetach() {
        super.onDetach();

    }
    public void phpGetAttendanceMain()
    {
        binding.progressBar.setVisibility(View.VISIBLE);
        String phoneNum = prefManager.getUserPhone();  //getting phone number from shared preference
        String studentId = prefManager.getStudentId();
        String studentname = prefManager.getUserName();


        String URL, parameters;

        parameters = "phoneNo=" + phoneNum + "&studentID=" + studentId;
        URL = Constants.BASE_URL  + "r_parent_messages.php?" + parameters.replaceAll(" ", "%20");
        final CommonSSLConnection commonSSLConnection=new CommonSSLConnection();

        //System.out.println("CHECK---> URL " + URL);
        StringRequest jsonObjectRequestLogin = new StringRequest(Request.Method.GET, URL, new Response.Listener<String>() {
            @Override
            public void onResponse(String jsonObject) {
                binding.progressBar.setVisibility(View.GONE);
                //System.out.println("CHECK---> Response " + jsonObject);
                //findViewById(R.id.progressLayout).setVisibility(View.GONE);
                Gson gson = new Gson();

                ParentMessages model = gson.fromJson(jsonObject, ParentMessages.class);
                Status status = model.getStatus();
                String code = status.getCode();
                if (code.equals("200"))
                {

                    List<Code> relationship=model.getCode();
                    if (relationship.size()>0) {

                        binding.TeacherMessageDetailsRV.setVisibility(View.VISIBLE);
                        binding.getRoot().findViewById(R.id.NodataLL).setVisibility(View.GONE);
                        binding.getRoot().findViewById(R.id.ErrorLL).setVisibility(View.GONE);

                        mAdapter = new ToteacherMessageDetailAdapter(activity, relationship,mFirebaseAnalytics);
                        binding.TeacherMessageDetailsRV.setAdapter(mAdapter);


                    }else {

                        NodataTV.setText(getActivity().getResources().getString(R.string.no_data_found));
                        GoBackBTN.setText(getActivity().getResources().getString(R.string.go_back));
//                        ErrorImage.setBackgroundResource(R.drawable.nodata);
                        binding.getRoot().findViewById(R.id.NodataLL).setVisibility(View.VISIBLE);
                        GoBackBTN.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {


                                getActivity().onBackPressed();

                            }
                        });

                    }
                }
                else if(code.equals("400")){
                    ReloadBTN.setText(getActivity().getResources().getString(R.string.go_back));
                    binding.getRoot().findViewById(R.id.ErrorLL).setVisibility(View.VISIBLE);
                    ErrorMessageTV.setText(getResources().getString(R.string.error_message_errorlayout));
                    CodeErrorTV.setText(getActivity().getResources().getString(R.string.code_attendance)+code);
                    ReloadBTN.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {


                            getActivity().onBackPressed();

                        }
                    });
                }else if (code.equals("204")){
                    NodataTV.setText(getActivity().getResources().getString(R.string.no_data_found));
                    GoBackBTN.setText(getActivity().getResources().getString(R.string.go_back));
//                        ErrorImage.setBackgroundResource(R.drawable.nodata);
                    binding.getRoot().findViewById(R.id.NodataLL).setVisibility(View.VISIBLE);
                    GoBackBTN.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {


                            getActivity().onBackPressed();

                        }
                    });

                }else if (code.equals("401")){

                    ReloadBTN.setText(getActivity().getResources().getString(R.string.go_back));
                    binding.getRoot().findViewById(R.id.ErrorLL).setVisibility(View.VISIBLE);
                    ErrorMessageTV.setText(getActivity().getResources().getString(R.string.error_message_errorlayout));
                    CodeErrorTV.setText(getActivity().getResources().getString(R.string.code_attendance)+code);
                    ReloadBTN.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {


                            getActivity().onBackPressed();

                        }
                    });

                }else if (code.equals("500")){

                    ReloadBTN.setText(getActivity().getResources().getString(R.string.go_back));
                    binding.getRoot().findViewById(R.id.ErrorLL).setVisibility(View.VISIBLE);
                    ErrorMessageTV.setText(getActivity().getResources().getString(R.string.error_message_errorlayout));
                    CodeErrorTV.setText(getActivity().getResources().getString(R.string.code_attendance)+code);
                    ReloadBTN.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {


                            getActivity().onBackPressed();

                        }
                    });

                }


            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError volleyError) {
                try {


                VolleyLog.d("Object Error : ", volleyError.getMessage());
                ReloadBTN.setText(getActivity().getResources().getString(R.string.go_back));
                    binding.getRoot().findViewById(R.id.ErrorLL).setVisibility(View.VISIBLE);
                ErrorMessageTV.setText(getActivity().getResources().getString(R.string.error_message_admin));
                CodeErrorTV.setText(getActivity().getResources().getString(R.string.error_message_errorlayout));

                ReloadBTN.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {


                        getActivity().onBackPressed();

                    }
                });
                }
                catch (Exception ignored)
                {

                }
               // Toast.makeText(getActivity(), getResources().getString(R.string.network_error_try), Toast.LENGTH_SHORT).show();
            }
        });
        jsonObjectRequestLogin.setRetryPolicy(new DefaultRetryPolicy(50000, 3, DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
//        RequestQueue requestQueue = Volley.newRequestQueue(getActivity(), commonSSLConnection.getHulkstack(getActivity()));
        CampusWallet.getInstance().addToRequestQueue(jsonObjectRequestLogin, tag_json_object);
//        requestQueue.add(jsonObjectRequestLogin);
    }

    @Override
    public void message(String message) {
        phpGetAttendanceMain();
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        CampusWallet.getInstance().getRequestQueue().cancelAll(tag_json_object);
    }
}
