package com.chillarcards.campuswallet.payments.Preorder;

import android.app.Activity;
import android.app.Dialog;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import android.text.Html;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.VolleyLog;
import com.android.volley.toolbox.StringRequest;
import com.chillarcards.campuswallet.R;
import com.chillarcards.campuswallet.databinding.CartlistBinding;
import com.chillarcards.campuswallet.databinding.LayoutPaymentsActivityBinding;
import com.google.gson.Gson;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

import com.chillarcards.campuswallet.application.CampusWallet;
import com.chillarcards.campuswallet.application.Constants;
import com.chillarcards.campuswallet.application.CustomConnectionBuddyActivity;
import com.chillarcards.campuswallet.application.PrefManager;
import com.chillarcards.campuswallet.networkmodels.PreOrderDetails.Code;
import com.chillarcards.campuswallet.networkmodels.PreOrderDetails.PreOrdermodule;
import com.chillarcards.campuswallet.networkmodels.PreOrderDetails.Status;
import com.chillarcards.campuswallet.payments.PaymentsActivity;

@SuppressWarnings("ALL")
public class PreOrderCartList_Activity extends CustomConnectionBuddyActivity{

    RecyclerView mRecyclerView;
    Activity activity;
    CartListAdapter mAdapter;
    Float total=(float) 0;
    TextView totamount;
    Button paybtn;
    Button OkButton,NoButton;
//    ProgressBar progressBar;

    JSONArray items;
    JSONObject itemarray;

    Dialog PopupConfrm;
    private float offset;
    private boolean flipped;

    String id;
    String pric;
    String qty;;
    String amount;;
    String name;

    private final ArrayList<String> item = new ArrayList<String>();
    private final ArrayList<String> itemid = new ArrayList<String>();
    private final ArrayList<String> price = new ArrayList<String>();
    private final ArrayList<String> quantity = new ArrayList<String>();
    private final ArrayList<String> totamt = new ArrayList<String>();
    private String parentPh,s_Id,schoolid;
//    ArrayList<Sales_Item> userList   = new ArrayList<Sales_Item>();
    private String UserListCount;
    ProgressBar progbar1;
    Button Reload;
    TextView Txt_Content;
    ImageView ErrorImage;
    String Dayselected;
    Dialog NoInternet;
    int InternetFlag = 0;
    PrefManager prefManager;


    private Dialog dialogWhstapp;
    private CartlistBinding binding;
    Button ReloadBTN,GoBackBTN;
    TextView ErrorMessageTV,CodeErrorTV,NodataTV;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.cartlist);
        activity= this;
        progbar1= (ProgressBar) findViewById(R.id.progbar);
        progbar1.setVisibility(View.INVISIBLE);

        binding = CartlistBinding.inflate(getLayoutInflater());
        View view = binding.getRoot();
        setContentView(view);

        ReloadBTN = binding.getRoot().findViewById(R.id.ReloadBTN);
        ErrorMessageTV=binding.getRoot().findViewById(R.id.ErrorMessageTV);
        CodeErrorTV=binding.getRoot().findViewById(R.id.CodeErrorTV);
        GoBackBTN =binding.getRoot().findViewById(R.id.GoBackBTN);
        NodataTV =binding.getRoot().findViewById(R.id.NodataTV);


        prefManager=new PrefManager(PreOrderCartList_Activity.this);
        binding.HeaderTV.setText(getResources().getString(R.string.outlet_title));
        binding.BackIV.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });
        findViewById(R.id.ErrorLL).setVisibility(View.GONE);
        findViewById(R.id.NodataLL).setVisibility(View.GONE);

        parentPh = prefManager.getUserPhone();
        s_Id= prefManager.getStudentId();
        schoolid= prefManager.getSChoolID();
        mRecyclerView= (RecyclerView) findViewById(R.id.listCheckout);
        totamount= (TextView) findViewById(R.id.totalamt);
        paybtn= (Button) findViewById(R.id.paybtn);


// Load user List from preferences

        paybtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {



//                Toast.makeText(getApplicationContext(), "name "+item+" price "+price+" qty "+quantity+" amount "+totamt+" Total "+total, Toast.LENGTH_SHORT).show();

                mFirebaseAnalytics.logEvent(getResources().getString(R.string.Pre_Order_Cart_Order_Button_clicked),new Bundle());
                Constants.Items=item;
                Constants.preorderItemTypeTimingItemIDnew =itemid;
                Constants.Quantity=quantity;
                Constants.Amount=totamt;
                Constants.PriceList=price;
                Constants.TotalAmount= String.valueOf(total);
                Dayselected= Constants.daySelected;

                //System.out.println("SHANIL2 : "+ Constants.sales_items.toString());

                if(Constants.sales_items.size()>0) {
                    progbar1.setVisibility(View.VISIBLE);
                    findViewById(R.id.NodataLL).setVisibility(View.GONE);
                    items = new JSONArray();
                    for (int i = 0; i< Constants.sales_items.size(); i++){

                     itemarray = new JSONObject();
                    try {
                        itemarray.put("preorderItemTypeTimingItemID", Constants.preorderItemTypeTimingItemIDnew.get(i));
                        itemarray.put("itemQuantity", Constants.Quantity.get(i));

                        //System.out.println("SHANIL itemarray : "+itemarray);
                        //System.out.println("SHANIL items : "+items);

                    } catch (JSONException e) {
                        // TODO Auto-generated catch block
                        e.printStackTrace();
                    }
                        items.put(itemarray);

                    }
                    //System.out.println("SHANIL itemarray1 : "+itemarray);
                    //System.out.println("SHANIL items1 : "+items);

                    PayCart(items,Dayselected);

                    progbar1.setVisibility(View.VISIBLE);
                }else
                {
                    NodataTV.setText(getResources().getString(R.string.no_data_found));
                    GoBackBTN.setText(getResources().getString(R.string.go_back));
//                        ErrorImage.setBackgroundResource(R.drawable.nodata);
                    findViewById(R.id.NodataLL).setVisibility(View.VISIBLE);
                    GoBackBTN.setVisibility(View.VISIBLE);
                    GoBackBTN.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {

                            onBackPressed();

                        }
                    });
//                    Toast.makeText(PreOrderCartList_Activity.this, "You have no items in ur cart for Pay...", Toast.LENGTH_SHORT).show();
                }


            }
        });



        mRecyclerView.setVisibility(View.VISIBLE);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(getApplicationContext()));
        mRecyclerView.setItemAnimator(new DefaultItemAnimator());


        //System.out.println("ijaz   itemid   "+itemid);
        //System.out.println("ijaz   item   "+item);
        //System.out.println("ijaz   name   "+quantity);
        //System.out.println("ijaz   name   "+price);
        //System.out.println("ijaz   name   "+totamt);
//        //System.out.println("ijaz   name   "+itemid);


        mAdapter = new CartListAdapter(itemid,item,quantity,price,totamt, activity, R.layout.cartlistitem_layout ,getApplicationContext(),mFirebaseAnalytics);
        mRecyclerView.setAdapter(mAdapter);
//        db.deleteAllItemssale();

    }





    public void PayCart(JSONArray items,String DaySelected)
    {
        // progressBar.setVisibility(View.VISIBLE);
        String preorder=items.toString();

        final String tag_json_object = "c_preorder_sales_item_lists";
        String URL, parameters;
        parameters = "phoneNo=" + parentPh + "&studentID=" +s_Id +"&daySelected=" + DaySelected + "&items=" +preorder ;
        URL = Constants.BASE_URL  + "c_preorder_sales_item_lists.php?" + parameters.replaceAll(" ", "%20");
        //System.out.println("CHECK---> URL " + URL);
        StringRequest jsonObjectRequestLogin = new StringRequest(Request.Method.GET, URL, new Response.Listener<String>() {
            @Override
            public void onResponse(String jsonObject) {
                //  progressBar.setVisibility(View.GONE);
                //System.out.println("CHECK---> Response " + jsonObject);
                //findViewById(R.id.progressLayout).setVisibility(View.GONE);
                Gson gson = new Gson();

                PreOrdermodule model = gson.fromJson(jsonObject, PreOrdermodule.class);
                Status status = model.getStatus();
                String code = status.getCode();
                if (code.equals("200"))
                {
                    findViewById(R.id.ErrorLL).setVisibility(View.GONE);
                    Code otpstatus2=model.getCode();

                    String ID= String.valueOf(otpstatus2.getPreorderItemSaleTransactionID());
                    //System.out.println("CHILLAR TEST  : "+ID);
//                    Toast.makeText(activity, "Success", Toast.LENGTH_SHORT).show();
                    Constants.sales_items.clear();
                    Intent in=new Intent(activity,OrderSuccessPage.class);
                    in.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                    Bundle b=new Bundle();
                    b.putString("OrderID",ID);
                    in.putExtras(b);
                    startActivity(in);
                    finish();
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
                }
                else if (code.equals("410")){

                    findViewById(R.id.ErrorLL).setVisibility(View.GONE);
                    Code otpstatus2=model.getCode();
                    showPayment(otpstatus2.getRequiredAmnt(),otpstatus2.getCurrentBalance());

                }
                else if (code.equals("500")){

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
                //progressBar.setVisibility(View.GONE);
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

                Toast.makeText(PreOrderCartList_Activity.this, "Network Error. Please Try Again!", Toast.LENGTH_SHORT).show();
            }
        });
        jsonObjectRequestLogin.setRetryPolicy(new DefaultRetryPolicy(50000, 0, DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
        CampusWallet.getInstance().addToRequestQueue(jsonObjectRequestLogin, tag_json_object);
    }
    @Override
    protected void onStart() {
        super.onStart();

    }
    @Override
    protected void onStop() {
        super.onStop();
    }
    private void showPayment(final String reqAmount,String CurrentBalance) {

        dialogWhstapp = new Dialog(PreOrderCartList_Activity.this, android.R.style.Theme_Dialog);
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
                Intent i = new Intent(PreOrderCartList_Activity.this, PaymentsActivity.class);
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
    @Override
    protected void onResume() {
        super.onResume();



//        SharedPreferences prefs2 = getSharedPreferences("CampusWallet", activity.MODE_PRIVATE);
        //            UserListCount= prefs2.getString("UserListCount", "");
        if (Constants.sales_items.size()>0){
            findViewById(R.id.NodataLL).setVisibility(View.GONE);
            item.clear();
            itemid.clear();
            price.clear();
            quantity.clear();
            totamt.clear();
//                userList = (ArrayList) ObjectSerializer.deserialize(prefs2.getString("UserList",""));
            //System.out.println("CHILLARTEST : "+ Constants.sales_items);
            for (int i = 0; i< Constants.sales_items.size(); i++){
//                //System.out.println("CHILLER: salesitems: "+Constants.sales_items.get(i).getitem_id());

//                String test= "Item ID : "+Constants.sales_items.get(i).getitem_id()+" Price : "+Constants.sales_items.get(i).getamount()+" Quantity : "+Constants.sales_items.get(i).getitem_quantity();

                id= String.valueOf(Constants.sales_items.get(i).getPreorderItemTypeTimingItemIDs());
                pric= String.valueOf(Constants.sales_items.get(i).getamount());
                qty= String.valueOf(Constants.sales_items.get(i).getitem_quantity());
                name= Constants.sales_items.get(i).getsales_name();

                amount= String.valueOf(Float.parseFloat(Constants.sales_items.get(i).getamount())*Integer.parseInt(Constants.sales_items.get(i).getitem_quantity()));


                //System.out.println("CHILL itname"+name);
                item.add(name);
                itemid.add(id);
                price.add(pric);
                quantity.add(qty);
                totamt.add(amount);

                total=total+Float.parseFloat(amount);

//            //System.out.println("CHILLAR Checkoutlist: "+test);
            }

            totamount.setText(total+"");

        }
       else {
            NodataTV.setText(getResources().getString(R.string.no_data_found));
            GoBackBTN.setText(getResources().getString(R.string.go_back));
//                        ErrorImage.setBackgroundResource(R.drawable.nodata);
            findViewById(R.id.NodataLL).setVisibility(View.VISIBLE);
            GoBackBTN.setVisibility(View.VISIBLE);
            GoBackBTN.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    onBackPressed();

                }
            });
        }
        //System.out.println("SHANIL List : "+ Constants.sales_items);
        //System.out.println("SHANIL List2 : "+ Constants.sales_items.toString());

    }


}
