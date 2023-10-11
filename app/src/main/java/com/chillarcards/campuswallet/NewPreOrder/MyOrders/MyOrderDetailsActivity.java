package com.chillarcards.campuswallet.NewPreOrder.MyOrders;

import android.app.Dialog;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.graphics.Point;
import android.graphics.drawable.ColorDrawable;
import android.os.CountDownTimer;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import android.text.Html;
import android.util.Log;
import android.view.Display;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageView;
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
import com.chillarcards.campuswallet.databinding.ActivityMyOrderDetailsBinding;
import com.chillarcards.campuswallet.databinding.LayoutLeaveReqActivityBinding;
import com.google.gson.Gson;
import com.google.zxing.WriterException;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import androidmads.library.qrgenearator.QRGContents;
import androidmads.library.qrgenearator.QRGEncoder;

import com.chillarcards.campuswallet.application.CampusWallet;
import com.chillarcards.campuswallet.application.Constants;
import com.chillarcards.campuswallet.application.CustomConnectionBuddyActivity;
import com.chillarcards.campuswallet.application.PrefManager;
import com.chillarcards.campuswallet.networkmodels.MyOrder.Datum;
import com.chillarcards.campuswallet.networkmodels.RefreshOrderData.Data;
import com.chillarcards.campuswallet.networkmodels.RefreshOrderData.RefreshOrderData;
import com.chillarcards.campuswallet.networkmodels.RefreshOrderData.Status;
import com.chillarcards.campuswallet.utils.CommonSSLConnection;

public class MyOrderDetailsActivity extends CustomConnectionBuddyActivity {

    PrefManager prefManager;

    String Items;
    private MyOrderDetailsAdapter mAdapter;

    Bitmap bitmap;
    QRGEncoder qrgEncoder;
    private String TAG = "MyorderDetails";
    boolean isExpired;
    private CountDownTimer countDownTimer = null;
    private Dialog dialogWhstapp;
    private String tag_json_object = "refresh_QR";
    Datum orderDatas;
    private ActivityMyOrderDetailsBinding binding;
    Button ReloadBTN,GoBackBTN;
    TextView ErrorMessageTV,CodeErrorTV,NodataTV;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
       // setContentView(R.layout.activity_my_order_details);
        binding = ActivityMyOrderDetailsBinding.inflate(getLayoutInflater());
        View view = binding.getRoot();
        setContentView(view);

        ReloadBTN = binding.getRoot().findViewById(R.id.ReloadBTN);
        ErrorMessageTV=binding.getRoot().findViewById(R.id.ErrorMessageTV);
        CodeErrorTV=binding.getRoot().findViewById(R.id.CodeErrorTV);
        GoBackBTN =binding.getRoot().findViewById(R.id.GoBackBTN);
        NodataTV =binding.getRoot().findViewById(R.id.NodataTV);

        prefManager = new PrefManager(MyOrderDetailsActivity.this);
        setProgressBarGone();
        binding.HeaderTV.setText(getResources().getString(R.string.your_basket_header_details));
        binding.BackIV.setOnClickListener(v -> onBackPressed());

        Bundle bundle = new Bundle();
        bundle = getIntent().getExtras();
        if (bundle != null) {
//            Total=bundle.getString("Total");
            Items = bundle.getString("DetailsJSON");
            isExpired = bundle.getBoolean("isExpired");
//            RealOrderID=bundle.getString("OrderRealID");
//            OrderID=bundle.getString("OrderID");
        }
        Gson gson = new Gson();
        orderDatas = gson.fromJson(Items, Datum.class);
//        if (isExpired) {
//            binding.ExpiredQRIV.setVisibility(View.VISIBLE);
//        } else {
//            binding.ExpiredQRIV.setVisibility(View.GONE);
            if (orderDatas.getOrderPurchaseStatus().equalsIgnoreCase("0")) {
                if(isExpired)
                {
                    binding.ExpiredQRIV.setVisibility(View.VISIBLE);
                }
                 else {
                    binding.ExpiredQRIV.setVisibility(View.GONE);
                }
            } else {
                binding.ExpiredQRIV.setVisibility(View.VISIBLE);
                binding.ExpiredQRIV.setBackgroundResource(R.drawable.delivered_stamp);
            }
//        }
        generateQR(convertOrderJSON(orderDatas.getOrderPurchaseID()));

        binding.DetailsOrderIDTV.setText(Html.fromHtml(" <font color=#acacac>" + "Order ID " + "</font><br>" + orderDatas.getOrderPurchaseID()));
        binding.OrderTotalTV.setText("Total : " + getResources().getString(R.string.indian_rupee_symbol) + orderDatas.getTotalAmount());
        if (orderDatas.getItems().size() > 0) {
            binding.MyOrderDetailsRV.setLayoutManager(new LinearLayoutManager(MyOrderDetailsActivity.this, LinearLayoutManager.VERTICAL, false));
            binding.MyOrderDetailsRV.setItemAnimator(new DefaultItemAnimator());
            binding.MyOrderDetailsRV.setNestedScrollingEnabled(false);
            mAdapter = new MyOrderDetailsAdapter(orderDatas.getItems(), MyOrderDetailsActivity.this);
            mAdapter.notifyDataSetChanged();
            binding.MyOrderDetailsRV.setAdapter(mAdapter);
        } else {
            NodataTV.setText(getResources().getString(R.string.no_data_found));
            GoBackBTN.setText(getResources().getString(R.string.go_back));
//                        ErrorImage.setBackgroundResource(R.drawable.nodata);
            findViewById(R.id.NodataLL).setVisibility(View.VISIBLE);
            GoBackBTN.setVisibility(View.GONE);
        }


    }

    public void QRCountDownTimer() {
        countDownTimer = new CountDownTimer(150000, 1000) {

            @Override
            public void onTick(long l) {

            }

            @Override
            public void onFinish() {
                RefreshQRPHP(orderDatas.getRealOrderPurchaseID());
            }
        }.start();
    }

    @Override
    protected void onResume() {
        super.onResume();

    }

    public void generateQR(String orderData) {
        if (!isExpired) {
            QRCountDownTimer();
        }
        if (orderData.length() > 0) {
            WindowManager manager = (WindowManager) getSystemService(WINDOW_SERVICE);
            Display display = manager.getDefaultDisplay();
            Point point = new Point();
            display.getSize(point);
            int width = point.x;
            int height = point.y;
            int smallerDimension = width < height ? width : height;
            smallerDimension = smallerDimension * 3 / 4;

            qrgEncoder = new QRGEncoder(
                    orderData, null,
                    QRGContents.Type.TEXT,
                    smallerDimension);
            try {
                bitmap = qrgEncoder.encodeAsBitmap();
                binding.MyorderDetailsQRIV.setImageBitmap(bitmap);
            } catch (WriterException e) {
                Log.v(TAG, e.toString());
            }
        } else {
            //System.out.println("QR Genarator ERROR");
        }
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

    private void RefreshQRPHP(final String realOrderID) {

        setProgressBarVisible();
        String URL;
        URL = Constants.BASE_ORDER_URL + "user_single_orders";
        //System.out.println("CHECK---> URL " + URL);
        CommonSSLConnection commonSSLConnection = new CommonSSLConnection();
        StringRequest jsonObjectRequestLogin = new StringRequest(Request.Method.POST, URL, new Response.Listener<String>() {
            @Override
            public void onResponse(String jsonObject) {
                //System.out.println("CHECK---> Response " + jsonObject);
                setProgressBarGone();

                CampusWallet.getInstance().cancelPendingRequests(tag_json_object);

                Gson gson = new Gson();
                RefreshOrderData noticeboard = gson.fromJson(jsonObject, RefreshOrderData.class);
                Status status = noticeboard.getStatus();
                String code = status.getCode();
                if (code.equals("200")) {

                    List<Data> myDataset = noticeboard.getData();
                    if (myDataset.size() > 0) {

                        for (int i = 0; i < myDataset.size(); i++) {
                            if (orderDatas.getOrderPurchaseStatus().equalsIgnoreCase("0")) {
                                if(isExpired)
                                {
                                    binding.ExpiredQRIV.setVisibility(View.VISIBLE);
                                }
                                else {
                                    binding.ExpiredQRIV.setVisibility(View.GONE);
                                }
                            } else {
                                binding.ExpiredQRIV.setVisibility(View.VISIBLE);
                                binding.ExpiredQRIV.setBackgroundResource(R.drawable.delivered_stamp);
                            }
                            String orderID = myDataset.get(i).getOrderPurchaseID();
                            String realOrderID = myDataset.get(i).getRealOrderPurchaseID();
                            binding.DetailsOrderIDTV.setText(Html.fromHtml(" <font color=#acacac>" + "Order ID " + "</font><br>" + orderID));
                            generateQR(convertOrderJSON(orderID));

                            if (orderDatas.getItems().size() > 0) {
                                binding.MyOrderDetailsRV.setLayoutManager(new LinearLayoutManager(MyOrderDetailsActivity.this, LinearLayoutManager.VERTICAL, false));
                                binding.MyOrderDetailsRV.setItemAnimator(new DefaultItemAnimator());
                                binding.MyOrderDetailsRV.setNestedScrollingEnabled(false);
                                mAdapter = new MyOrderDetailsAdapter(orderDatas.getItems(), MyOrderDetailsActivity.this);
                                mAdapter.notifyDataSetChanged();
                                binding.MyOrderDetailsRV.setAdapter(mAdapter);
                            } else {
                                NodataTV.setText(getResources().getString(R.string.no_data_found));
                                GoBackBTN.setText(getResources().getString(R.string.go_back));
//                        ErrorImage.setBackgroundResource(R.drawable.nodata);
                                findViewById(R.id.NodataLL).setVisibility(View.VISIBLE);
                                GoBackBTN.setVisibility(View.GONE);
                            }
                        }
                    } else {
                        NodataTV.setText(getResources().getString(R.string.no_data_found));
                        GoBackBTN.setText(getResources().getString(R.string.go_back));
//                        ErrorImage.setBackgroundResource(R.drawable.nodata);
                        findViewById(R.id.NodataLL).setVisibility(View.VISIBLE);
                        GoBackBTN.setVisibility(View.GONE);
                    }
                } else if (code.equals("411")) {
                    showCommonDialogue(status.getMessage());
                } else {
                    setProgressBarGone();
                    String message = status.getMessage();
                    ReloadBTN.setText(getResources().getString(R.string.go_back));
                    findViewById(R.id.ErrorLL).setVisibility(View.VISIBLE);
                    ErrorMessageTV.setText(getResources().getString(R.string.error_message_errorlayout));
                    CodeErrorTV.setText(getResources().getString(R.string.code_attendance) + code);
                    ReloadBTN.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {


                            onBackPressed();

                        }
                    });
                    Toast.makeText(MyOrderDetailsActivity.this, message, Toast.LENGTH_SHORT).show();
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
                Toast.makeText(MyOrderDetailsActivity.this, getResources().getString(R.string.network_error_try), Toast.LENGTH_SHORT).show();
            }
        }) {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> params = new HashMap<>();
                params.put("targetUserID", prefManager.getStudentUserId());
                params.put("schoolID", prefManager.getSChoolID());
                params.put("realOrderPurchaseID", realOrderID);
                //System.out.println("CHECK---> " + prefManager.getStudentUserId() + " , " +
//                        prefManager.getSChoolID() + " ,\n " + realOrderID);

                return params;
            }
        };

        jsonObjectRequestLogin.setRetryPolicy(new DefaultRetryPolicy(50000, 3, DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
//        RequestQueue requestQueue = Volley.newRequestQueue(this, commonSSLConnection.getHulkstack(CartListActivity.this));
        CampusWallet.getInstance().addToRequestQueue(jsonObjectRequestLogin, tag_json_object);
//        requestQueue.add(jsonObjectRequestLogin);

    }

    public String convertOrderJSON(String orderID) {
        String jsonStr = "";
        JSONObject orderList = new JSONObject();
        try {
            orderList.put("OrderIDTemp", orderID);
        } catch (JSONException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        JSONObject orderObj = new JSONObject();
        try {
            orderObj.put("OrderDetails", orderList);
        } catch (JSONException e) {
            e.printStackTrace();

        }
        jsonStr = orderObj.toString();
        //System.out.println("ORDER JSON : " + jsonStr);
        return jsonStr;
    }

    private void showCommonDialogue(String message) {

        dialogWhstapp = new Dialog(MyOrderDetailsActivity.this, android.R.style.Theme_Dialog);
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
}
