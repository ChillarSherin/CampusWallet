package com.chillarcards.campuswallet.Diary.NoticeBoard;

import android.Manifest;
import android.content.pm.PackageManager;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.LinearSmoothScroller;
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
import com.chillarcards.campuswallet.databinding.ActivityMyOrdersBinding;
import com.chillarcards.campuswallet.databinding.ActivityNoticeBoardBinding;
import com.google.gson.Gson;

import java.util.List;

import com.chillarcards.campuswallet.application.CampusWallet;
import com.chillarcards.campuswallet.application.Constants;
import com.chillarcards.campuswallet.application.CustomConnectionBuddyActivity;
import com.chillarcards.campuswallet.application.PrefManager;
import com.chillarcards.campuswallet.networkmodels.noticeboard.Code;
import com.chillarcards.campuswallet.networkmodels.noticeboard.Noticeboard;
import com.chillarcards.campuswallet.networkmodels.noticeboard.Status;
import com.chillarcards.campuswallet.utils.CommonSSLConnection;

public class NoticeBoardActivity extends CustomConnectionBuddyActivity {

    private static final int MY_PERMISSIONS_REQUEST_READ_STORAGE = 123;

    private NoticeBoardAdapter mAdapter;
    PrefManager prefManager;
    final String tag_json_object = "r_noticeboard";
    Integer messageID = -1;
    RecyclerView.SmoothScroller smoothScroller;
    private ActivityNoticeBoardBinding binding;
    Button ReloadBTN,GoBackBTN;
    TextView ErrorMessageTV,CodeErrorTV,NodataTV;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
      //  setContentView(R.layout.activity_notice_board);
       
        binding = ActivityNoticeBoardBinding.inflate(getLayoutInflater());
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
        binding.BackIV.setOnClickListener(v -> onBackPressed());


        prefManager = new PrefManager(this);
        binding.HeaderTV.setText(getResources().getString(R.string.noticeboard_header));
        binding.NoticeBoardSRL.setColorSchemeResources(R.color.colorAccent);

        smoothScroller = new
                LinearSmoothScroller(this) {
                    @Override protected int getVerticalSnapPreference() {
                        return LinearSmoothScroller.SNAP_TO_START;
                    }
                };

    }


    @Override
    protected void onResume() {
        super.onResume();

        CheckPermission();
        binding.NoticeBoardSRL.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                CheckPermission();
                binding.NoticeBoardSRL.setRefreshing(false);
            }
        });

    }
    public void CheckPermission() {
        // Here, thisActivity is the current activity
        if (ContextCompat.checkSelfPermission(getApplicationContext(),
                Manifest.permission.WRITE_EXTERNAL_STORAGE)
                != PackageManager.PERMISSION_GRANTED && ContextCompat.checkSelfPermission(getApplicationContext(),
                Manifest.permission.READ_EXTERNAL_STORAGE)
                != PackageManager.PERMISSION_GRANTED) {
            //System.out.println("PERMISSION NOT GRANTED ");
            // Permission is not granted
            // Should we show an explanation?
            if (
                    ActivityCompat.shouldShowRequestPermissionRationale(NoticeBoardActivity.this,
                            Manifest.permission.WRITE_EXTERNAL_STORAGE) &&
                            ActivityCompat.shouldShowRequestPermissionRationale(NoticeBoardActivity.this,
                                    Manifest.permission.READ_EXTERNAL_STORAGE)) {
                // Show an explanation to the user *asynchronously* -- don't block
                // this thread waiting for the user's response! After the user
                // sees the explanation, try again to request the permission.
                //System.out.println("PERMISSION RETRY ");
                ActivityCompat.requestPermissions(NoticeBoardActivity.this,
                        new String[]{ Manifest.permission.WRITE_EXTERNAL_STORAGE, Manifest.permission.READ_EXTERNAL_STORAGE},
                        MY_PERMISSIONS_REQUEST_READ_STORAGE);
            } else {
                // No explanation needed; request the permission
                ActivityCompat.requestPermissions(NoticeBoardActivity.this,
                        new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE, Manifest.permission.READ_EXTERNAL_STORAGE},
                        MY_PERMISSIONS_REQUEST_READ_STORAGE);

                // MY_PERMISSIONS_REQUEST_READ_CONTACTS is an
                // app-defined int constant. The callback method gets the
                // result of the request.
                //System.out.println("PERMISSION REQUESTED ");
            }
        } else {
            // Permission has already been granted
            loadNoticeBoard();
        }
    }
    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        switch (requestCode) {
            case MY_PERMISSIONS_REQUEST_READ_STORAGE: {
                // If request is cancelled, the result arrays are empty.
                //System.out.println("PERMISSION ARRAY SIZE : " + grantResults.length);
                if (grantResults.length > 0
                        && grantResults[0] == PackageManager.PERMISSION_GRANTED
                        && grantResults[1] == PackageManager.PERMISSION_GRANTED) {
                    // permission was granted, yay! Do the
                    // contacts-related task you need to do.
                    // dispatchTakePictureIntent();
                    //System.out.println("PERMISSION GRANTED ");

                    try {
                        loadNoticeBoard();
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                    //galleryAddPic();
                    // setPic();
                } else {
                    // permission denied, boo! Disable the
                    // functionality that depends on this permission.
                    //System.out.println("PERMISSION DENIED ");
                }
                return;
            }

            // other 'case' lines to check for other
            // permissions this app might request.
        }
    }
    private void loadNoticeBoard() {


        String userPhone = prefManager.getUserPhone();
        String studentId = prefManager.getStudentId();
        setProgressBarVisible();
        String URL, parameters;
        parameters = "phoneNo=" +userPhone+"&studentID="+studentId;
        URL = Constants.BASE_URL + "r_notice_board.php?" + parameters.replaceAll(" ", "%20");
        //System.out.println("CHECK---> URL " + URL);
        CommonSSLConnection commonSSLConnection = new CommonSSLConnection();
        StringRequest jsonObjectRequestLogin = new StringRequest(Request.Method.GET, URL, new Response.Listener<String>() {
            @Override
            public void onResponse(String jsonObject) {
                //System.out.println("CHECK---> Response " + jsonObject);
                setProgressBarGone();

                CampusWallet.getInstance().cancelPendingRequests(tag_json_object);

                Gson gson = new Gson();
                Noticeboard noticeboard = gson.fromJson(jsonObject,Noticeboard.class);
                Status status =noticeboard.getStatus();
                String code =status.getCode();
                if(code.equals("200")){

                    List<Code> myDataset= noticeboard.getCode();
                    if (myDataset.size()>0) {

                        if(messageID!=-1){

                            int index = getIndexByname(String.valueOf(messageID),myDataset);
                            System.out.println("Index: "+index);

                            if(index ==-1){
                                binding.NoticeBoardRV.setLayoutManager(new LinearLayoutManager(NoticeBoardActivity.this, LinearLayoutManager.VERTICAL, false));
                                binding.NoticeBoardRV.setItemAnimator(new DefaultItemAnimator());
                                binding.NoticeBoardRV.setNestedScrollingEnabled(false);
                                mAdapter = new NoticeBoardAdapter(myDataset,index, NoticeBoardActivity.this,mFirebaseAnalytics);
                                mAdapter.notifyDataSetChanged();
                                binding.NoticeBoardRV.setAdapter(mAdapter);
                            }else{
                                binding.NoticeBoardRV.setLayoutManager(new LinearLayoutManager(NoticeBoardActivity.this));
                                binding.NoticeBoardRV.smoothScrollToPosition(index);
                                binding.NoticeBoardRV.setItemAnimator(new DefaultItemAnimator());
                                binding.NoticeBoardRV.setNestedScrollingEnabled(false);
                                mAdapter = new NoticeBoardAdapter(myDataset,index, NoticeBoardActivity.this,mFirebaseAnalytics);
                                mAdapter.notifyDataSetChanged();
                                binding.NoticeBoardRV.setAdapter(mAdapter);
                            }


                        }else{

                            binding.NoticeBoardRV.setLayoutManager(new LinearLayoutManager(NoticeBoardActivity.this, LinearLayoutManager.VERTICAL, false));
                            binding.NoticeBoardRV.setItemAnimator(new DefaultItemAnimator());
                            binding.NoticeBoardRV.setNestedScrollingEnabled(false);
                            mAdapter = new NoticeBoardAdapter(myDataset,-1, NoticeBoardActivity.this,mFirebaseAnalytics);
                            mAdapter.notifyDataSetChanged();
                            binding.NoticeBoardRV.setAdapter(mAdapter);
                        }

                    }
                    else {
                        NodataTV.setText(getResources().getString(R.string.no_new_message));
                        GoBackBTN.setText(getResources().getString(R.string.go_back));
//                        ErrorImage.setBackgroundResource(R.drawable.nodata);
                        findViewById(R.id.NodataLL).setVisibility(View.VISIBLE);
                        findViewById(R.id.ErrorLL).setVisibility(View.GONE);
                        GoBackBTN.setVisibility(View.GONE);
                    }

                }
                else if (code.equals("204"))
                {
                    setProgressBarGone();
                    NodataTV.setText(getResources().getString(R.string.no_new_message));
                    GoBackBTN.setText(getResources().getString(R.string.go_back));
//                        ErrorImage.setBackgroundResource(R.drawable.nodata);
                    findViewById(R.id.NodataLL).setVisibility(View.VISIBLE);
                    findViewById(R.id.ErrorLL).setVisibility(View.GONE);
                    GoBackBTN.setVisibility(View.GONE);
                }

                else{
                    setProgressBarGone();
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
                    Toast.makeText(NoticeBoardActivity.this, message, Toast.LENGTH_SHORT).show();
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
                Toast.makeText(NoticeBoardActivity.this, getResources().getString(R.string.network_error_try), Toast.LENGTH_SHORT).show();
            }
        });

        jsonObjectRequestLogin.setRetryPolicy(new DefaultRetryPolicy(50000, 3, DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
//        RequestQueue requestQueue = Volley.newRequestQueue(this, commonSSLConnection.getHulkstack(NoticeBoardActivity.this));
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


    public int getIndexByname(String pName,List<Code> dataSet)
    {
        for(Code _item : dataSet)
        {
            if(_item.getNoticeBoardID().equals(pName))
                return dataSet.indexOf(_item);
        }
        return -1;
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        CampusWallet.getInstance().cancelPendingRequests(tag_json_object);
    }
}
