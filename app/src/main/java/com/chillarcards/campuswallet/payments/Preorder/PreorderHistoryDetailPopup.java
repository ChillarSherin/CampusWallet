package com.chillarcards.campuswallet.payments.Preorder;

import android.app.Activity;
import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
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
import com.chillarcards.campuswallet.databinding.PreeorderthreeLayoutBinding;
import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.List;

import com.chillarcards.campuswallet.application.CampusWallet;
import com.chillarcards.campuswallet.application.Constants;
import com.chillarcards.campuswallet.application.CustomConnectionBuddyActivity;
import com.chillarcards.campuswallet.application.PrefManager;
import com.chillarcards.campuswallet.networkmodels.PreOrderHistDetails.PreOrderHisDetail;
import com.chillarcards.campuswallet.networkmodels.PreOrderHistDetails.PreOrderHisDetailCode;
import com.chillarcards.campuswallet.networkmodels.PreOrderHistDetails.PreOrderHisDetailStatus;
import com.chillarcards.campuswallet.utils.CommonSSLConnection;


@SuppressWarnings("ALL")
public class PreorderHistoryDetailPopup extends CustomConnectionBuddyActivity {


    public String parentPh,s_Id,SelectedTransId,TypeName;
    Activity activity;
    TextView Txt_Content;

    LinearLayout ErrorLayout;

    Button Reload;
    ImageView ErrorImage;

//    Call<PreOrderHisDetail> call1;

    private List<String> preorderSalesItemID = new ArrayList<String>();
    private List<String> tansactionBillNo = new ArrayList<String>();
    private List<String> preorderItemTypeTimingItemID = new ArrayList<String>();
    private List<String> itemID = new ArrayList<String>();
    private List<String> itemCode = new ArrayList<String>();
    private List<String> itemName = new ArrayList<String>();
    private List<String> itemShortName = new ArrayList<String>();
    private List<String> preorderSalesItemQuantity = new ArrayList<String>();
    private List<String> preorderSalesItemAmount = new ArrayList<String>();
    private List<String> preorderItemSaleTransactionVendorID = new ArrayList<String>();
    private List<String> itemTypeID = new ArrayList<String>();
    private List<String> itemTypeName = new ArrayList<String>();



    ProgressBar ProgressBarnew;
    PrefManager prefManager;

 
    private PreOrderHistoryDetalsAdapter mAdapter;
    final String tag_json_object = "r_preorder_history_details";
    HistorydetailsPopupBinding binding;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.historydetails_popup);
        binding = HistorydetailsPopupBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

//        Fabric.with(this, new Answers());
        prefManager=new PrefManager(PreorderHistoryDetailPopup.this);

        binding.HeaderTV.setText(getResources().getString(R.string.preorder_details));
        binding.BackIV.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });
        binding.CloseIV.setVisibility(View.VISIBLE);
        binding.BackIV.setVisibility(View.VISIBLE);
        activity=this;
        ProgressBarnew = (ProgressBar) findViewById(R.id.ProgressBar2);
        ProgressBarnew.setVisibility(View.GONE);
        ErrorLayout=(LinearLayout) findViewById(R.id.LayoutErrorState);
        ErrorLayout.setVisibility(View.GONE);
        Reload= (Button) findViewById(R.id.reload);
        Txt_Content= (TextView) findViewById(R.id.txt_content);
        ErrorImage= (ImageView) findViewById(R.id.img_no_image);
        binding.CloseIV.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                finish();

            }
        });
        //System.out.println("Details : "+parentPh+"  "+s_Id+" "+SelectedTransId);
    }

    @Override
    protected void onResume() {
        super.onResume();
        parentPh = prefManager.getUserPhone();
        s_Id= prefManager.getStudentId();
        Bundle b = getIntent().getExtras();
        SelectedTransId = b.getString("BillNumber");
        ErrorLayout.setVisibility(View.GONE);
        binding.CloseIV.setVisibility(View.VISIBLE);
        binding.BackIV.setVisibility(View.VISIBLE);
        phpGetPreOrderDetails(SelectedTransId);

    }
    @Override
    protected void onDestroy() {
        super.onDestroy();
        CampusWallet.getInstance().cancelPendingRequests(tag_json_object);
    }
    public void phpGetPreOrderDetails(String transID)
    {
        ProgressBarnew.setVisibility(View.VISIBLE);

        String URL, parameters;
        parameters = "tansactionBillNo=" + transID;
        URL = Constants.BASE_URL  + "r_preorder_history_details.php?" + parameters.replaceAll(" ", "%20");
        //System.out.println("CHECK---> URL " + URL);
        CommonSSLConnection commonSSLConnection=new CommonSSLConnection();
        StringRequest jsonObjectRequestLogin = new StringRequest(Request.Method.GET, URL, new Response.Listener<String>() {
            @Override
            public void onResponse(String jsonObject) {
                ProgressBarnew.setVisibility(View.GONE);
                //System.out.println("CHECK---> Response " + jsonObject);
                //findViewById(R.id.progressLayout).setVisibility(View.GONE);
                Gson gson = new Gson();

                PreOrderHisDetail model = gson.fromJson(jsonObject, PreOrderHisDetail.class);
                PreOrderHisDetailStatus status = model.getStatus();
                String code = status.getCode();
                if (code.equals("200")){

                    //System.out.println("SHANIL 1: "+code.toString());
                    List<PreOrderHisDetailCode> transactionCode = model.getCode();
                    if (transactionCode.size() > 0) {

                        for (int i = 0; i < transactionCode.size(); i++) {

                            PreOrderHisDetailCode codeonline = transactionCode.get(i);
                            preorderSalesItemID.add(codeonline.getPreorderSalesItemID());
                            tansactionBillNo.add(codeonline.getTansactionBillNo());
                            preorderItemTypeTimingItemID.add(codeonline.getPreorderItemTypeTimingItemID());
                            itemID.add(codeonline.getItemID());
                            itemCode.add(codeonline.getItemCode());
                            itemName.add(codeonline.getItemName());
                            itemShortName.add(codeonline.getItemShortName());
                            preorderSalesItemQuantity.add(codeonline.getPreorderSalesItemQuantity());
                            preorderSalesItemAmount.add(codeonline.getPreorderSalesItemAmount());
                            itemTypeName.add(codeonline.getItemTypeName());
                            itemTypeID.add(codeonline.getItemTypeID());
                            preorderItemSaleTransactionVendorID.add(codeonline.getPreorderItemSaleTransactionVendorID());

                        }
                        binding.list.setVisibility(View.VISIBLE);
                        binding.list.setLayoutManager(new LinearLayoutManager(activity, LinearLayoutManager.VERTICAL, false));
                        binding.list.setItemAnimator(new DefaultItemAnimator());
                        binding.list.setNestedScrollingEnabled(false);
                        mAdapter = new PreOrderHistoryDetalsAdapter(preorderSalesItemID, tansactionBillNo, preorderItemTypeTimingItemID,
                                itemID, itemCode, itemName,itemShortName,preorderSalesItemQuantity,preorderSalesItemAmount,itemTypeName,
                                itemTypeID,preorderItemSaleTransactionVendorID,
                                activity, R.layout.preorderhistorydetails, getApplicationContext());
                        binding.list.setAdapter(mAdapter);
                        binding.list.setNestedScrollingEnabled(false);
                    }

                }
                else if(code.equals("400")){

                    ErrorImage.setBackgroundResource(R.drawable.no_data);
                    Txt_Content.setText(getResources().getString(R.string.something_went_wrong));
                    Reload.setText(getResources().getString(R.string.goback));
                    ErrorLayout.setVisibility(View.VISIBLE);
                    Reload.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {


                            onBackPressed();

                        }
                    });
                }else if (code.equals("204")){


                    ErrorImage.setBackgroundResource(R.drawable.no_data);
                    Txt_Content.setText(getResources().getString(R.string.no_data_found));
                    Reload.setText(getResources().getString(R.string.goback));
                    ErrorLayout.setVisibility(View.VISIBLE);
                    Reload.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {


                            onBackPressed();

                        }
                    });
                }else if (code.equals("401")){

                    ErrorImage.setBackgroundResource(R.drawable.no_data);
                    Txt_Content.setText(getResources().getString(R.string.something_went_wrong));
                    Reload.setText(getResources().getString(R.string.goback));
                    ErrorLayout.setVisibility(View.VISIBLE);
                    Reload.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {


                            onBackPressed();

                        }
                    });
                }else if (code.equals("500")){
                    ErrorImage.setBackgroundResource(R.drawable.no_data);
                    Txt_Content.setText(getResources().getString(R.string.something_went_wrong));
                    Reload.setText(getResources().getString(R.string.goback));
                    ErrorLayout.setVisibility(View.VISIBLE);
                    Reload.setOnClickListener(new View.OnClickListener() {
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
                ProgressBarnew.setVisibility(View.GONE);
                VolleyLog.d("Object Error : ", volleyError.getMessage());
                //ADD SNACKBAR LAYOUT
                ErrorImage.setBackgroundResource(R.drawable.no_data);
                Txt_Content.setText(getResources().getString(R.string.something_went_wrong));
                Reload.setText(getResources().getString(R.string.goback));
                ErrorLayout.setVisibility(View.VISIBLE);
                Reload.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {


                        onBackPressed();

                    }
                });
                Toast.makeText(PreorderHistoryDetailPopup.this, getResources().getString(R.string.network_error_try), Toast.LENGTH_SHORT).show();
            }
        });
        jsonObjectRequestLogin.setRetryPolicy(new DefaultRetryPolicy(50000, 3, DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
//        RequestQueue requestQueue = Volley.newRequestQueue(this, commonSSLConnection.getHulkstack(PreorderHistoryDetailPopup.this));
        CampusWallet.getInstance().addToRequestQueue(jsonObjectRequestLogin, tag_json_object);
//        requestQueue.add(jsonObjectRequestLogin);
    }


}
