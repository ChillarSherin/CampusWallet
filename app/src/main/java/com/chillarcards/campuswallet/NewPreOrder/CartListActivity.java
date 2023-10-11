package com.chillarcards.campuswallet.NewPreOrder;

import android.app.Dialog;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.ItemTouchHelper;
import android.text.Html;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;


import com.android.volley.AuthFailureError;
import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.VolleyLog;
import com.android.volley.toolbox.StringRequest;
import com.chillarcards.campuswallet.R;
import com.chillarcards.campuswallet.databinding.ActivityAttendancePopupBinding;
import com.chillarcards.campuswallet.databinding.ActivityCartListBinding;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonArray;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.chillarcards.campuswallet.NewPreOrder.AllItems.CartCallback;
import com.chillarcards.campuswallet.NewPreOrder.Swipe.SwipeController;
import com.chillarcards.campuswallet.application.CampusWallet;
import com.chillarcards.campuswallet.application.Constants;
import com.chillarcards.campuswallet.application.CustomConnectionBuddyActivity;
import com.chillarcards.campuswallet.application.PrefManager;
import com.chillarcards.campuswallet.networkmodels.PlaceOrder.UploadCart;
import com.chillarcards.campuswallet.networkmodels.PlaceOrderDetails.Datum;
import com.chillarcards.campuswallet.networkmodels.PlaceOrderDetails.PlaceOrderData;
import com.chillarcards.campuswallet.networkmodels.PlaceOrderDetails.Status;
import com.chillarcards.campuswallet.payments.PaymentsActivity;
import com.chillarcards.campuswallet.utils.CommonSSLConnection;

public class CartListActivity extends CustomConnectionBuddyActivity implements CartCallback {

    PrefManager prefManager;

    private CartListAdapter mAdapter;
    private SwipeController swipeController;
    private Dialog dialogWhstapp;
    private String tag_json_object = "Upload_Cart_data";
    Button ReloadBTN,GoBackBTN;
    TextView ErrorMessageTV,CodeErrorTV,NodataTV;
    ActivityCartListBinding binding;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //setContentView(R.layout.activity_cart_list);
        
        binding = ActivityCartListBinding.inflate(getLayoutInflater());
        View view = binding.getRoot();
        setContentView(view);

        ReloadBTN = binding.getRoot().findViewById(R.id.ReloadBTN);
        ErrorMessageTV=binding.getRoot().findViewById(R.id.ErrorMessageTV);
        CodeErrorTV=binding.getRoot().findViewById(R.id.CodeErrorTV);
        GoBackBTN =binding.getRoot().findViewById(R.id.GoBackBTN);
        NodataTV =binding.getRoot().findViewById(R.id.NodataTV);

        prefManager = new PrefManager(CartListActivity.this);
//        generateRandomNo(); //random Number TOKEN
        binding.HeaderTV.setText(getResources().getString(R.string.your_basket_header));
        setProgressBarGone();
        binding.BackIV.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });
        binding.PlaceOrderLL.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String mTotal = binding.CartTotalAmountTV.getText().toString().trim();
                String[] split = mTotal.split(getResources().getString(R.string.indian_rupee_symbol));
                String myval = split[1];
                if (!myval.equalsIgnoreCase("0")) {

                    if (!prefManager.getIsTokenClear()) {
                        prefManager.setTokenCart(generateRandomNo());
                        prefManager.setIsTokenClear(true);
                    }
//                    showPayment(myval);  //low balance
                    List<UploadCart> uploadCarts = new ArrayList<>();
                    uploadCarts.clear();
                    if (Constants.CartItems.size()>0) {
                        for (DummyOrderItems mOrderItems : Constants.CartItems) {
                            uploadCarts.add(new UploadCart(mOrderItems.getId(), mOrderItems.getOutletID(), mOrderItems.getSessionID(), mOrderItems.getCategotyId(),
                                    mOrderItems.getUnitPrice(), mOrderItems.getQty()));

                            //System.out.println("JSON UPLOAD ITEMs : " + mOrderItems.getId()+" , "+ mOrderItems.getOutletID()+" , "+mOrderItems.getSessionID()+" , "+mOrderItems.getCategotyId());
                        }

                        Gson gson = new GsonBuilder().create();
                        JsonArray myCustomArray = gson.toJsonTree(uploadCarts).getAsJsonArray();
                        //System.out.println("JSON UPLOAD Array : " + myCustomArray.toString());
                        Gson gson1 = new Gson();
                        String UploadJSON = gson1.toJson(uploadCarts);
                        //System.out.println("JSON UPLOAD : " + UploadJSON);
                        PlaceOrderPHP(UploadJSON, prefManager.getTokenCart());
                    }
                    else
                    {
                        Toast.makeText(CartListActivity.this, "Please Select Atleast One Item", Toast.LENGTH_SHORT).show();
                    }

                } else {
                    Toast.makeText(CartListActivity.this, "Please Select Atleast One Item", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    public String generateRandomNo() {
        RandomString tickets = new RandomString(14);
//        //System.out.println("Random No :"+tickets.nextString());
        return tickets.nextString();
    }

    @Override
    protected void onResume() {
        super.onResume();


        ChangeTotalText();
        binding.CartItemsRV.setLayoutManager(new LinearLayoutManager(CartListActivity.this, LinearLayoutManager.VERTICAL, false));
        binding.CartItemsRV.setItemAnimator(new DefaultItemAnimator());
        binding.CartItemsRV.setNestedScrollingEnabled(false);
        mAdapter = new CartListAdapter(Constants.CartItems, CartListActivity.this, CartListActivity.this, CartListActivity.this);
        mAdapter.notifyDataSetChanged();
        binding.CartItemsRV.setAdapter(mAdapter);

//        swipeController = new SwipeController(new SwipeControllerActions() {
//            @Override
//            public void onRightClicked(int position) {
//                mAdapter.orderItems.remove(position);
//                mAdapter.notifyItemRemoved(position);
//                mAdapter.notifyItemRangeChanged(position, mAdapter.getItemCount());
//            }
//
//            @Override
//            public void onLeftClicked(int position) {
//                super.onLeftClicked(position);
//
//            }
//        });
//
//        ItemTouchHelper itemTouchhelper = new ItemTouchHelper(swipeController);
//        itemTouchhelper.attachToRecyclerView(binding.CartItemsRV);
//
//        binding.CartItemsRV.addItemDecoration(new RecyclerView.ItemDecoration() {
//            @Override
//            public void onDraw(Canvas c, RecyclerView parent, RecyclerView.State state) {
//                swipeController.onDraw(c);
//            }
//        });

    }

    @Override
    public void onAddtocartCallback() {
        ChangeTotalText();
    }

    public void ChangeTotalText() {
        float Total_Amount = 0;
        for (int i = 0; i < Constants.CartItems.size(); i++) {
            Total_Amount = Total_Amount + (Float.parseFloat(Constants.CartItems.get(i).getPrice()));
        }
        binding.CartTotalAmountTV.setText(getResources().getString(R.string.indian_rupee_symbol) + String.valueOf(Total_Amount));
    }

    private void showPayment(final String reqAmount,String CurrentBalance) {

        dialogWhstapp = new Dialog(CartListActivity.this, android.R.style.Theme_Dialog);
        dialogWhstapp.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialogWhstapp.setContentView(R.layout.payment_balance_popup_layout);
        dialogWhstapp.setCanceledOnTouchOutside(true);
        dialogWhstapp.getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);
        dialogWhstapp.getWindow().setLayout(WindowManager.LayoutParams.MATCH_PARENT, WindowManager.LayoutParams.WRAP_CONTENT);
        dialogWhstapp.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        RelativeLayout close = dialogWhstapp.findViewById(R.id.exit);
        Button CancelButton = dialogWhstapp.findViewById(R.id.CancelBtn);
        Button OKButton = dialogWhstapp.findViewById(R.id.OkBtn);
        TextView popupcontent = dialogWhstapp.findViewById(R.id.popupcontent);
        String text="<font color=#FFFFFF> "+getString(R.string.you_require)+"</font><font color=#FE2E64> "+getString(R.string.indian_rupee_symbol)+reqAmount+
                " </font><font color=#FFFFFF>"+getString(R.string.to_complete)+"\n "+
                getString(R.string.your_current)+"</font><font color=#FE2E64> "+getString(R.string.indian_rupee_symbol)+CurrentBalance+
                "\n </font><font color=#FFFFFF>"+getString(R.string.please_recharge)+"</font>";

        popupcontent.setText(Html.fromHtml(text));

        try {
            dialogWhstapp.show();
        } catch (Exception e) {
            e.printStackTrace();
        }
        close.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                dialogWhstapp.dismiss();

            }
        });

        CancelButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                dialogWhstapp.dismiss();
            }
        });
        OKButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mFirebaseAnalytics.logEvent(getResources().getString(R.string.Low_Balance_popup_Selected), new Bundle());
                Intent i = new Intent(CartListActivity.this, PaymentsActivity.class);
                i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                Bundle b = new Bundle();
                b.putString("categoryId", "2");
                b.putString("categoryName", "card_recharge");
                b.putString("fromLowbalance", "1");
                b.putString("rechargeAmount", reqAmount);
                i.putExtras(b);
                startActivity(i);
                dialogWhstapp.dismiss();
            }
        });

    }
    private void showCommonDialogue(String message) {

        dialogWhstapp = new Dialog(CartListActivity.this, android.R.style.Theme_Dialog);
        dialogWhstapp.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialogWhstapp.setContentView(R.layout.common_popup_layout);
        dialogWhstapp.setCanceledOnTouchOutside(true);
        dialogWhstapp.getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);
        dialogWhstapp.getWindow().setLayout(WindowManager.LayoutParams.MATCH_PARENT, WindowManager.LayoutParams.WRAP_CONTENT);
        dialogWhstapp.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        RelativeLayout close = dialogWhstapp.findViewById(R.id.exit);
        Button OKButton = dialogWhstapp.findViewById(R.id.OkBtn);
        TextView popupcontent = dialogWhstapp.findViewById(R.id.popupcontent);
        TextView logouttext = dialogWhstapp.findViewById(R.id.logouttext);
//        logouttext.setVisibility(View.GONE);
        popupcontent.setText(message);

        try {
            dialogWhstapp.show();
        } catch (Exception e) {
            e.printStackTrace();
        }
        close.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                dialogWhstapp.dismiss();

            }
        });


        OKButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                dialogWhstapp.dismiss();
            }
        });

    }
    public void setProgressBarVisible() {
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE,
                WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE);
        binding.mProgressBar.setVisibility(View.VISIBLE);

    }

    public void setProgressBarGone() {

        getWindow().clearFlags(WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE);
        binding.mProgressBar.setVisibility(View.GONE);

    }

    private void PlaceOrderPHP(final String jsonData, final String token) {

        setProgressBarVisible();
        String URL;
        URL = Constants.BASE_ORDER_URL + "outlet_place_order";
        //System.out.println("CHECK---> URL " + URL);
        CommonSSLConnection commonSSLConnection = new CommonSSLConnection();
        StringRequest jsonObjectRequestLogin = new StringRequest(Request.Method.POST, URL, new Response.Listener<String>() {
            @Override
            public void onResponse(String jsonObject) {
                //System.out.println("CHECK---> Response " + jsonObject);
                setProgressBarGone();

                CampusWallet.getInstance().cancelPendingRequests(tag_json_object);

                Gson gson = new Gson();
                PlaceOrderData noticeboard = gson.fromJson(jsonObject, PlaceOrderData.class);
                Status status = noticeboard.getStatus();
                String code = status.getCode();
                if (code.equals("200")) {

                    List<Datum> myDataset = noticeboard.getData();
                    if (myDataset.size() > 0) {
                        for (int i = 0; i < myDataset.size(); i++) {
                            String orderID = myDataset.get(i).getOrderPurchaseID();
                            String realOrderID = myDataset.get(i).getRealOrderPurchaseID();

                            prefManager.setIsTokenClear(false);
                            Constants.CartItems.clear();
//                            Intent intent = new Intent(CartListActivity.this, CartStatusActivity.class);
//                            Bundle bundle=new Bundle();
//                            bundle.putString("OrderID",orderID);
//                            bundle.putString("RealOrderID",realOrderID);
//                            intent.putExtras(bundle);
//                            startActivity(intent);

                            Gson mgson = new Gson();
                            String ItemsJSON = mgson.toJson(myDataset.get(i));

                            Intent intent=new Intent(CartListActivity.this, CartOrderDetailsActivity.class);
                            Bundle bundle=new Bundle();
                            bundle.putString("OrderRealID",realOrderID);
                            bundle.putBoolean("isExpired",false);
                            intent.putExtras(bundle);
                            startActivity(intent);

                        }


                    } else {
//                        NodataTV.setText(getResources().getString(R.string.no_data_found));
//                        GoBackBTN.setText(getResources().getString(R.string.go_back));
////                        ErrorImage.setBackgroundResource(R.drawable.nodata);
//                        findViewById(R.id.NodataLL).setVisibility(View.VISIBLE);
//                        GoBackBTN.setVisibility(View.GONE);
                        String message = getResources().getString(R.string.network_error_try);
                        showCommonDialogue(message);
                    }

                }
                else if (code.equals("410"))
                {
                    List<Datum> myDataset = noticeboard.getData();
                    if (myDataset.size() > 0) {
                        for (int i = 0; i < myDataset.size(); i++) {
                            String orderID = myDataset.get(i).getOrderPurchaseID();
                            String CurrentBalance = myDataset.get(i).getCurrentBalance();
                            String RequiredAmount = myDataset.get(i).getRequiredAmnt();

                            showPayment(RequiredAmount,CurrentBalance);
                        }


                    } else {
//                        NodataTV.setText(getResources().getString(R.string.no_data_found));
//                        GoBackBTN.setText(getResources().getString(R.string.go_back));
////                        ErrorImage.setBackgroundResource(R.drawable.nodata);
//                        findViewById(R.id.NodataLL).setVisibility(View.VISIBLE);
//                        GoBackBTN.setVisibility(View.GONE);
                        String message = getResources().getString(R.string.network_error_try);
                        showCommonDialogue(message);
                    }


                }
                else if (code.equals("411"))
                {
                    showCommonDialogue(status.getMessage());
                }

                else {
                    setProgressBarGone();
//                    String message = status.getMessage();
//                    ReloadBTN.setText(getResources().getString(R.string.go_back));
//                    findViewById(R.id.ErrorLL).setVisibility(View.VISIBLE);
//                    ErrorMessageTV.setText(getResources().getString(R.string.error_message_errorlayout));
//                    CodeErrorTV.setText(getResources().getString(R.string.code_attendance) + code);
//                    ReloadBTN.setOnClickListener(new View.OnClickListener() {
//                        @Override
//                        public void onClick(View v) {
//
//
//                            onBackPressed();
//
//                        }
//                    });
                    String message = status.getMessage();
                    showCommonDialogue(message);
//                    Toast.makeText(CartListActivity.this, message, Toast.LENGTH_SHORT).show();
                }


            }


        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError volleyError) {
                setProgressBarGone();
                CampusWallet.getInstance().cancelPendingRequests(tag_json_object);
                VolleyLog.d("Object Error : ", volleyError.getMessage());
//                ReloadBTN.setText(getResources().getString(R.string.go_back));
//                findViewById(R.id.ErrorLL).setVisibility(View.VISIBLE);
//                ErrorMessageTV.setText(getResources().getString(R.string.error_message_admin));
//                CodeErrorTV.setText(getResources().getString(R.string.error_message_errorlayout));
//                ReloadBTN.setOnClickListener(new View.OnClickListener() {
//                    @Override
//                    public void onClick(View v) {
//
//
//                        onBackPressed();
//
//                    }
//                });
                String message = getResources().getString(R.string.network_error_try);
                showCommonDialogue(message);
                Toast.makeText(CartListActivity.this, getResources().getString(R.string.network_error_try), Toast.LENGTH_SHORT).show();
            }
        }) {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> params = new HashMap<>();
                params.put("targetUserID", prefManager.getStudentUserId());
                params.put("schoolID", prefManager.getSChoolID());
                params.put("itemListJSON", jsonData);
                params.put("transactionToken", token);
                //System.out.println("CHECK---> " + prefManager.getStudentUserId() + " , " +
//                        prefManager.getSChoolID()+ " ,\n " +jsonData+ " , " +token);

                return params;
            }
        };

        jsonObjectRequestLogin.setRetryPolicy(new DefaultRetryPolicy(50000, 3, DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
//        RequestQueue requestQueue = Volley.newRequestQueue(this, commonSSLConnection.getHulkstack(CartListActivity.this));
        CampusWallet.getInstance().addToRequestQueue(jsonObjectRequestLogin, tag_json_object);
//        requestQueue.add(jsonObjectRequestLogin);

    }
}
