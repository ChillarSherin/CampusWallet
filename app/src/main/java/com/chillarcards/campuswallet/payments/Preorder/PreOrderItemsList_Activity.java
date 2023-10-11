package com.chillarcards.campuswallet.payments.Preorder;

import android.app.Activity;
import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.appcompat.widget.Toolbar;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
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
import com.chillarcards.campuswallet.databinding.LayoutPaymentsActivityBinding;
import com.chillarcards.campuswallet.databinding.PreeorderthreeLayoutBinding;
import com.google.gson.Gson;
import java.util.ArrayList;
import java.util.List;

import com.chillarcards.campuswallet.application.CampusWallet;
import com.chillarcards.campuswallet.application.Constants;
import com.chillarcards.campuswallet.application.CustomConnectionBuddyActivity;
import com.chillarcards.campuswallet.application.PrefManager;
import com.chillarcards.campuswallet.networkmodels.PreOrderItemList.PreOrderItemCode;
import com.chillarcards.campuswallet.networkmodels.PreOrderItemList.PreOrderItemList;
import com.chillarcards.campuswallet.networkmodels.PreOrderItemList.PreOrderItemsStatus;
import com.chillarcards.campuswallet.utils.CommonSSLConnection;


@SuppressWarnings("ALL")
public class PreOrderItemsList_Activity extends CustomConnectionBuddyActivity {

    final String tag_json_object = "r_preorder_outlet_items";
    private final List<String> schoolID = new ArrayList<>();
    private final List<String> preorderItemTypeTimingID = new ArrayList<>();
    private final List<String> categoryID = new ArrayList<>();
    private final List<String> categoryName = new ArrayList<>();
    private final List<String> categoryShortName = new ArrayList<>();
    private final List<String> preorderItemTypeTimingItemID = new ArrayList<>();
    private final List<String> itemID = new ArrayList<>();
    private final List<String> itemCode = new ArrayList<>();
    private List<String> itemName = new ArrayList<>();
    private final List<String> itemShortName = new ArrayList<>();
    private final List<String> itemPrice = new ArrayList<>();

    private RecyclerView mRecyclerView;
    private Activity activity;
    private ProgressBar progressBar;
    private PreOrderCatListAdapter mAdapter;

    private String parentPh,s_Id;
    Button Reload,Search;
    TextView Txt_Content;
    public TextView CountTxt;
    ImageView ErrorImage;
    ImageView CartButton;
    String preorderItemTypeTimingIDString,schoolid,TimingId;
    private String UserListCount;

    EditText inputSearch;
    Dialog NoInternet;
    int InternetFlag = 0;

    PrefManager prefManager;
    Button ReloadBTN,GoBackBTN;
    TextView ErrorMessageTV,CodeErrorTV,NodataTV;
    PreeorderthreeLayoutBinding binding;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
       // setContentView(R.layout.preeorderthree_layout);
        binding = PreeorderthreeLayoutBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        prefManager=new PrefManager(PreOrderItemsList_Activity.this);

        ReloadBTN = binding.getRoot().findViewById(R.id.ReloadBTN);
        ErrorMessageTV=binding.getRoot().findViewById(R.id.ErrorMessageTV);
        CodeErrorTV=binding.getRoot().findViewById(R.id.CodeErrorTV);
        GoBackBTN =binding.getRoot().findViewById(R.id.GoBackBTN);
        NodataTV =binding.getRoot().findViewById(R.id.NodataTV);

        binding.HeaderTV.setText(getResources().getString(R.string.itemslist_title));
        binding.BackIV.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });
        findViewById(R.id.ErrorLL).setVisibility(View.GONE);
        findViewById(R.id.NodataLL).setVisibility(View.GONE);

        Search= (Button) findViewById(R.id.searchbutton);
        inputSearch = (EditText) findViewById(R.id.inputSearch);
        CountTxt= (TextView) findViewById(R.id.countid);
        CartButton = (ImageView) findViewById(R.id.cartid);

        CartButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                mFirebaseAnalytics.logEvent(getResources().getString(R.string.Pre_Order_Cart_Button_clicked),new Bundle());
//                finish();
                if (Constants.sales_items.size()!=0) {

                    CountTxt.setText("" + UserListCount);
                    CountTxt.setVisibility(View.VISIBLE);
                    Intent in=new Intent(getApplicationContext(),PreOrderCartList_Activity.class);
                    in.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                    startActivity(in);
                }
                else
                {
                    CountTxt.setVisibility(View.GONE);
                }


            }
        });
//        SharedPreferences prefs = getSharedPreferences("CampusWallet", MODE_PRIVATE);
//        parentPh = prefs.getString("PhoneNo", "");
//        s_Id= prefs.getString("studentID", "");
//        schoolid= prefs.getString("SchoolID", "");

//        drawerArrow();
        activity = this;
        progressBar = (ProgressBar) findViewById(R.id.ProgressBar);
        mRecyclerView = (RecyclerView) findViewById(R.id.list);
        progressBar.setVisibility(View.GONE);
        addTextListener();

        Search.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                addTextListener();

            }
        });

    }


    private void addTextListener() {

        inputSearch.addTextChangedListener(new TextWatcher() {

            public void afterTextChanged(Editable s) {}

            public void beforeTextChanged(CharSequence s, int start, int count, int after) {}

            public void onTextChanged(CharSequence query, int start, int before, int count) {

                query = query.toString().toLowerCase();

                final List<String> filteredList1 = new ArrayList<>();
                final List<String> filteredList2 = new ArrayList<>();
                final List<String> filteredList3 = new ArrayList<>();
                final List<String> filteredList4 = new ArrayList<>();
                final List<String> filteredList5 = new ArrayList<>();
                final List<String> filteredList6 = new ArrayList<>();
                final List<String> filteredList7 = new ArrayList<>();
                final List<String> filteredList8 = new ArrayList<>();
                final List<String> filteredList9 = new ArrayList<>();
                final List<String> filteredList10 = new ArrayList<>();
                final List<String> filteredList11 = new ArrayList<>();



                for (int i = 0; i < schoolID.size(); i++) {

                    final String text = schoolID.get(i).toLowerCase();
                    final String text2 = categoryID.get(i).toLowerCase();
                    final String text3 = itemID.get(i).toLowerCase();
                    final String text4 = itemCode.get(i).toString();
                    final String text5 = itemShortName.get(i).toString();
                    final String text6 = itemName.get(i).toString().toLowerCase();
                    final String text7 = itemPrice.get(i).toString();
                    final String text8 = categoryName.get(i).toString();
                    final String text9 = categoryShortName.get(i).toString();
                    final String text10 = preorderItemTypeTimingItemID.get(i).toString();
                    final String text11 = preorderItemTypeTimingID.get(i).toString();
                    if (text.contains(query)||text2.contains(query)||text3.contains(query)
                            ||text4.contains(query)||text5.contains(query)||text6.contains(query)
                            ||text7.contains(query)) {

                        //System.out.println("SHANIL 11111: ");

                        filteredList1.add(schoolID.get(i));
                        filteredList2.add(categoryID.get(i));
                        filteredList3.add(itemID.get(i));
                        filteredList4.add(itemCode.get(i));
                        filteredList5.add(itemShortName.get(i));
                        filteredList6.add(itemName.get(i));
                        filteredList7.add(itemPrice.get(i));
                        filteredList8.add(categoryName.get(i));
                        filteredList9.add(categoryShortName.get(i));
                        filteredList10.add(preorderItemTypeTimingItemID.get(i));
                        filteredList11.add(preorderItemTypeTimingID.get(i));

                    }
                }

                mRecyclerView.setLayoutManager(new LinearLayoutManager(PreOrderItemsList_Activity.this));
                mAdapter = new PreOrderCatListAdapter(filteredList1,filteredList2,filteredList3,filteredList4,
                        filteredList5,filteredList6,filteredList7,filteredList8,filteredList9,filteredList10,filteredList11,
                        PreOrderItemsList_Activity.this, activity,getApplicationContext(),mFirebaseAnalytics);
                mRecyclerView.setAdapter(mAdapter);
                mAdapter.notifyDataSetChanged();  // data set changed
            }
        });
    }
    private void filter(String text) {
        //new array list that will hold the filtered data
        ArrayList<String> filterdNames = new ArrayList<>();

        //looping through existing elements
        for (String s : itemName) {
            //if the existing elements contains the search input
            if (s.toLowerCase().contains(text.toLowerCase())) {
                //adding the element to filtered list
                filterdNames.add(s);
            }
        }

        //calling a method of the adapter class and passing the filtered list
        filterList(filterdNames);
    }
    public void filterList(ArrayList<String> filterdNames) {
        this.itemName = filterdNames;
        mAdapter.notifyDataSetChanged();
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
    protected void onResume() {
        super.onResume();
        parentPh = prefManager.getUserPhone();
        s_Id= prefManager.getStudentId();
        Bundle b=getIntent().getExtras();
        preorderItemTypeTimingIDString =b.getString("preorderItemTypeTimingID");
        TimingId =b.getString("TimingId");
        PreOrderOutletsList();
        UserListCount= String.valueOf(Constants.sales_items.size());
        //System.out.println("SHANIL2 : "+UserListCount);
        if (Constants.sales_items.size()!=0) {

            CountTxt.setText("" + UserListCount);
            CountTxt.setVisibility(View.VISIBLE);
        }
        else
        {
            CountTxt.setVisibility(View.GONE);
        }


    }


    public void PreOrderOutletsList()
    {
         progressBar.setVisibility(View.VISIBLE);


        String URL, parameters;
        parameters = "schoolID=" + schoolid + "&preorderItemTypeTimingID=" +preorderItemTypeTimingIDString;
        URL = Constants.BASE_URL  + "r_preorder_outlet_items.php?" + parameters.replaceAll(" ", "%20");
        //System.out.println("CHECK---> URL " + URL);
        CommonSSLConnection commonSSLConnection=new CommonSSLConnection();
        StringRequest jsonObjectRequestLogin = new StringRequest(Request.Method.GET, URL, new Response.Listener<String>() {
            @Override
            public void onResponse(String jsonObject) {
                  progressBar.setVisibility(View.GONE);
                //System.out.println("CHECK---> Response " + jsonObject);
                //findViewById(R.id.progressLayout).setVisibility(View.GONE);
                Gson gson = new Gson();

                PreOrderItemList model = gson.fromJson(jsonObject, PreOrderItemList.class);
                PreOrderItemsStatus status = model.getStatus();
                String code = status.getCode();
                if (code.equals("200"))
                {

                    schoolID.clear();
                    categoryID.clear();
                    itemID.clear();
                    itemCode.clear();
                    itemShortName.clear();
                    itemName.clear();
                    itemPrice.clear();
                    categoryName.clear();
                    categoryShortName.clear();
                    preorderItemTypeTimingItemID.clear();
                    preorderItemTypeTimingID.clear();


                    Log.i("shanil ", String.valueOf(model.getStatus()));
                    List<PreOrderItemCode> relationship=model.getCode();
                    if (relationship.size()>0) {
                        for (int i = 0; i < relationship.size(); i++) {
                            findViewById(R.id.NodataLL).setVisibility(View.GONE);
                            findViewById(R.id.ErrorLL).setVisibility(View.GONE);

                            PreOrderItemCode relncode=relationship.get(i);
                            schoolID.add(relncode.getSchoolID());
                            categoryID.add(relncode.getCategoryID());
                            itemID.add(relncode.getItemID());
                            itemCode.add(relncode.getItemCode());
                            itemShortName.add(relncode.getItemShortName());
                            itemName.add(relncode.getItemName());
                            itemPrice.add(relncode.getItemPrice());
                            categoryName.add(relncode.getCategoryName());
                            categoryShortName.add(relncode.getItemShortName());
                            preorderItemTypeTimingItemID.add(relncode.getPreorderItemTypeTimingItemID());
                            preorderItemTypeTimingID.add(relncode.getPreorderItemTypeTimingID());


                        }
                        try {

                            if (schoolID.size() > 0) {

                                mRecyclerView.setVisibility(View.VISIBLE);
                                mRecyclerView.setLayoutManager(new LinearLayoutManager(activity, LinearLayoutManager.VERTICAL, false));
                                mRecyclerView.setItemAnimator(new DefaultItemAnimator());
                                mRecyclerView.setNestedScrollingEnabled(false);
                                mAdapter = new PreOrderCatListAdapter(schoolID,categoryID,itemID,itemCode,itemShortName,
                                        itemName,itemPrice,categoryName,categoryShortName,preorderItemTypeTimingItemID,
                                        preorderItemTypeTimingID,PreOrderItemsList_Activity.this, activity, getApplicationContext(),mFirebaseAnalytics);
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

                Toast.makeText(PreOrderItemsList_Activity.this, getResources().getString(R.string.network_error_try), Toast.LENGTH_SHORT).show();
            }
        });
        jsonObjectRequestLogin.setRetryPolicy(new DefaultRetryPolicy(50000, 3, DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
//        RequestQueue requestQueue = Volley.newRequestQueue(this, commonSSLConnection.getHulkstack(PreOrderItemsList_Activity.this));
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

        Intent in=new Intent(activity,PreOrderOutlets_Activity.class);
        in.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK|Intent.FLAG_ACTIVITY_CLEAR_TOP);
        Bundle b=new Bundle();
        b.putString("timingid", TimingId);
//                b.putString("dayid", DayString);
        in.putExtras(b);
        startActivity(in);
        finish();;
    }


}
