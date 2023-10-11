package com.chillarcards.campuswallet.NewPreOrder;

import android.app.Dialog;
import android.content.Intent;

import androidx.annotation.NonNull;
import androidx.fragment.app.FragmentTransaction;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import androidx.appcompat.app.AppCompatDelegate;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.ActionMode;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.view.animation.TranslateAnimation;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.ProgressBar;
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
import com.chillarcards.campuswallet.databinding.ActivityPaymentHistoryBinding;
import com.chillarcards.campuswallet.databinding.ActivityPreOrderTabedBinding;
import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

import com.chillarcards.campuswallet.NewPreOrder.AllItems.CartCallback;
import com.chillarcards.campuswallet.NewPreOrder.AllItems.Event;
import com.chillarcards.campuswallet.NewPreOrder.AllItems.EventItem;
import com.chillarcards.campuswallet.NewPreOrder.AllItems.EventsAdapter;
import com.chillarcards.campuswallet.NewPreOrder.AllItems.FilterOutletDialog;
import com.chillarcards.campuswallet.NewPreOrder.AllItems.HeaderItem;
import com.chillarcards.campuswallet.NewPreOrder.AllItems.ListItem;
import com.chillarcards.campuswallet.NewPreOrder.AllItems.SessionCallback;
import com.chillarcards.campuswallet.NewPreOrder.AllItems.SessionPopupAdapter;
import com.chillarcards.campuswallet.application.CampusWallet;
import com.chillarcards.campuswallet.application.Constants;
import com.chillarcards.campuswallet.application.CustomConnectionBuddyActivity;
import com.chillarcards.campuswallet.application.PrefManager;
import com.chillarcards.campuswallet.networkmodels.OrderItemsDetails.Category;
import com.chillarcards.campuswallet.networkmodels.OrderItemsDetails.Datum;
import com.chillarcards.campuswallet.networkmodels.OrderItemsDetails.Item;
import com.chillarcards.campuswallet.networkmodels.OrderItemsDetails.OrderItemsDetails;
import com.chillarcards.campuswallet.networkmodels.OrderItemsDetails.Outlet;
import com.chillarcards.campuswallet.networkmodels.OrderItemsDetails.Session;
import com.chillarcards.campuswallet.networkmodels.OrderItemsDetails.Status;
import com.chillarcards.campuswallet.utils.CommonSSLConnection;

public class PreOrderTabedActivity extends CustomConnectionBuddyActivity implements FilterCallBack,SessionCallback,CartCallback{

    PrefManager prefManager;

    final String tag_json_object = "r_order_items";


    private List<ListItem> items = new ArrayList<>();
    List<HeaderItem> headerItems = new ArrayList<>();
    List<Item> Originalitems;
    List<Session> sessionList;
    List<Category> categoryList;
    List<Outlet> outletList;

    List<DummyOrderItems> orderItems;
    List<DummyCategory> categories;

    private Dialog MyDialog;
    RecyclerView FilterRV;
    Button FilteCloseBTN, SetButton;
    List<String> customFilters;

    PopupWindow popupWindow;
    View popupContentView;
    LinearLayoutManager linearLayoutManager;
    private ActivityPreOrderTabedBinding binding;
    Button ReloadBTN,GoBackBTN;
    TextView ErrorMessageTV,CodeErrorTV,NodataTV;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
      //  setContentView(R.layout.activity_pre_order_tabed);

        binding = ActivityPreOrderTabedBinding.inflate(getLayoutInflater());
        View view = binding.getRoot();
        setContentView(view);

        ReloadBTN = binding.getRoot().findViewById(R.id.ReloadBTN);
        ErrorMessageTV=binding.getRoot().findViewById(R.id.ErrorMessageTV);
        CodeErrorTV=binding.getRoot().findViewById(R.id.CodeErrorTV);
        GoBackBTN =binding.getRoot().findViewById(R.id.GoBackBTN);
        NodataTV =binding.getRoot().findViewById(R.id.NodataTV);
        
        prefManager=new PrefManager(PreOrderTabedActivity.this);
        orderItems = new ArrayList<>();
        categories = new ArrayList<>();
        Originalitems=new ArrayList<>();
        sessionList =new ArrayList<>();
        categoryList=new ArrayList<>();
        outletList=new ArrayList<>();

        binding.HeaderTV.setText(getResources().getString(R.string.order_header));
        binding.BackIV.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });

        AppCompatDelegate.setCompatVectorFromResourcesEnabled(true);
        popupWindow = new PopupWindow(PreOrderTabedActivity.this);

        linearLayoutManager = new LinearLayoutManager(this);

        loadOrderItemsPHP();

        binding.MenuFAB.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                popupContentView = LayoutInflater.from(PreOrderTabedActivity.this).inflate(R.layout.popup_session, null);
                RecyclerView SessionRV=popupContentView.findViewById(R.id.SessionRV);
                SessionRV.setLayoutManager(new LinearLayoutManager(PreOrderTabedActivity.this, LinearLayoutManager.VERTICAL, false));
                SessionRV.setNestedScrollingEnabled(false);
                SessionPopupAdapter mAdapter = new SessionPopupAdapter(headerItems, PreOrderTabedActivity.this,
                        PreOrderTabedActivity.this,PreOrderTabedActivity.this);
                mAdapter.notifyDataSetChanged();
                SessionRV.setAdapter(mAdapter);


                popupWindow.setContentView(popupContentView);
                popupWindow.setWidth(ViewGroup.LayoutParams.WRAP_CONTENT);
                popupWindow.setHeight(ViewGroup.LayoutParams.WRAP_CONTENT);
                // Set popup window animation style.
//                popupWindow.setAnimationStyle(R.style.popup_window_animation_filter);

//                popupWindow.setBackgroundDrawable(new ColorDrawable(Color.WHITE));

                popupWindow.setFocusable(true);

                popupWindow.setOutsideTouchable(true);

                popupWindow.update();
                // Show popup window offset 1,1 to smsBtton.
//                popupWindow.showAsDropDown(binding.MenuFAB, 1, 1);
                popupWindow.showAtLocation(binding.MenuFAB, Gravity.BOTTOM, 0, -10);

//                for(int k = 0; k<headerItems.size(); ++k){
//
//                    if(headerItems.get(k).getSession().equalsIgnoreCase("LUNCH")){
//                        HeaderItem headerItem = headerItems.get(k);
//                        int pos =headerItem.getPosition();
//                        linearLayoutManager.scrollToPositionWithOffset(pos,0);
//                    }
//
//                }

            }
        });
        binding.FilterBTN.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
//                filterFunction();
                FilterOutletDialog dialog = new FilterOutletDialog(PreOrderTabedActivity.this,outletList,categoryList);
                FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
                dialog.show(ft, FilterOutletDialog.TAG);
            }
        });
        binding.SearchBTN.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (binding.constraintLayout.getVisibility()==View.VISIBLE)
                {
//                    slideUp(binding.constraintLayout);
                    binding.constraintLayout.setVisibility(View.GONE);
                }
                else {
                    binding.constraintLayout.setVisibility(View.VISIBLE);
//                    slideDown(binding.constraintLayout);

                }

            }
        });
        binding.SearchET.setLongClickable(false);
        binding.SearchET.setCustomSelectionActionModeCallback(new ActionMode.Callback() {
            @Override
            public boolean onCreateActionMode(ActionMode actionMode, Menu menu) {
                return false;
            }

            @Override
            public boolean onPrepareActionMode(ActionMode actionMode, Menu menu) {
                return false;
            }

            @Override
            public boolean onActionItemClicked(ActionMode actionMode, MenuItem menuItem) {
                return false;
            }

            @Override
            public void onDestroyActionMode(ActionMode actionMode) {

            }
        });
        binding.SearchET.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

                final Map<String, List<Event>> events_new = toMap(loadEvents(getSessionFilter(new ArrayList<String>(), getfilteredList(charSequence))));
                items.clear();
                int ik = 0;
                for (String sessions : events_new.keySet()) {
                    HeaderItem header = new HeaderItem(sessions,i);
                    items.add(header);

                    for (Event event : events_new.get(sessions)) {
                        ++ik;
                        EventItem item = new EventItem(event,ik);
                        items.add(item);

                    }
                    ++ik;

                }


                headerItems.clear();
                for(int j = 0;j<items.size();++j){

                    if(items.get(j).getType()==ListItem.TYPE_HEADER){
                        headerItems.add((HeaderItem) items.get(j));
                    }
                }


                binding.ItemsListRV.setLayoutManager(linearLayoutManager);
                EventsAdapter eventsAdapter=new EventsAdapter(items,PreOrderTabedActivity.this,
                        PreOrderTabedActivity.this,PreOrderTabedActivity.this);
                eventsAdapter.notifyDataSetChanged();
                binding.ItemsListRV.setAdapter(eventsAdapter);

            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });

    }
    public List<Item> getfilteredList(CharSequence sequence)
    {
        List<Item> items=new ArrayList<>();
        items.clear();

        for (Item orderItems: Originalitems) {

            if (orderItems.getItemName().toLowerCase().contains(sequence))
            {
                items.add(orderItems);
            }
        }
       return items;
    }

    // slide the view from below itself to the current position
    public void slideUp(View view){
        view.setVisibility(View.VISIBLE);
        TranslateAnimation animate = new TranslateAnimation(
                0,                 // fromXDelta
                0,                 // toXDelta
                view.getHeight(),  // fromYDelta
                0);                // toYDelta
        animate.setDuration(500);
        animate.setFillAfter(true);
        view.startAnimation(animate);
    }

    // slide the view from its current position to below itself
    public void slideDown(View view){
        TranslateAnimation animate = new TranslateAnimation(
                0,                 // fromXDelta
                0,                 // toXDelta
                0,                 // fromYDelta
                view.getHeight()); // toYDelta
        animate.setDuration(500);
        animate.setFillAfter(true);
        view.startAnimation(animate);
    }

    @Override
    protected void onResume() {
        super.onResume();
        if (Constants.CartItems.size()==0)
        {
            binding.CartLL.setVisibility(View.GONE);
        }
        else
        {
            float Total_Price = 0;
            int Total_Count=0;
            for (int i=0;i<Constants.CartItems.size();i++)
            {
                Total_Price=Total_Price+(Float.parseFloat(Constants.CartItems.get(i).getPrice()));
                Total_Count=Total_Count+i;
            }
            binding.CartQtyTV.setText(String.valueOf(Constants.CartItems.size()));
            binding.CartAmountTV.setText("Total : "+getResources().getString(R.string.indian_rupee_symbol)+Total_Price);
            binding.CartLL.setVisibility(View.VISIBLE);
        }
        binding.CartLL.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i=new Intent(PreOrderTabedActivity.this, CartListActivity.class);
                i.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(i);
            }
        });
    }
    @NonNull
    private List<Event> loadEvents(List<Item> values) {
        List<Event> events = new ArrayList<>();
//        for (int i = 1; i < 50; i++) {
//            events.add(new Event("event " + i, selectRandomSession()));
//        }
        for (Item orderItems: values)
        {
            events.add(new Event(orderItems,getSessionName(orderItems.getOrderSessionID())));
        }

        return events;
    }



    @NonNull
    private Map<String, List<Event>> toMap(@NonNull List<Event> events) {
        Map<String, List<Event>> map = new TreeMap<>();
        for (Event event : events) {
            List<Event> value = map.get(event.getSession());
            if (value == null) {
                value = new ArrayList<>();
                map.put(event.getSession(), value);
            }
            value.add(event);
        }
        return map;
    }

//    private List<DummyOrderItems> valuesInputs() {
//        orderItems.clear();
//        orderItems.add(new DummyOrderItems("1", "Idli and Sambar", "Idli and Sambar", "10", "1", "Outlet A", "10","10"));
//        orderItems.add(new DummyOrderItems("2", "Idli and Sambar", "Idli and Sambar", "10", "1", "Outlet B", "10","10"));
//        orderItems.add(new DummyOrderItems("3", "Dosai", "Dosai", "10", "1", "Outlet A", "10","10"));
//        orderItems.add(new DummyOrderItems("4", "Dosai", "Dosai", "10", "1", "Outlet C", "10","10"));
//        orderItems.add(new DummyOrderItems("5", "Onion Tomato Uttapam", "Onion Tomato Uttapam", "20", "1", "Outlet C", "10","20"));
//        orderItems.add(new DummyOrderItems("6", "Poori with Chana masala", "Poori with Chana masala", "30", "1", "Outlet B", "10","30"));
//        orderItems.add(new DummyOrderItems("7", "Veg Meals", "Veg Meals", "10", "2", "Outlet A", "10","10"));
//        orderItems.add(new DummyOrderItems("8", "Fish curry Meals", "Fish curry Meals", "80", "2", "Outlet A", "10","80"));
//        orderItems.add(new DummyOrderItems("9", "Veg Meals", "Veg Meals", "40", "2", "Outlet B", "10","40"));
//        orderItems.add(new DummyOrderItems("10", "Non-Veg meals", "Non-Veg meals", "100", "2", "Outlet B", "10","100"));
//        orderItems.add(new DummyOrderItems("11", "Non-Veg meals", "Non-Veg meals", "80", "2", "Outlet C", "10","80"));
//        orderItems.add(new DummyOrderItems("12", "Chicken Biriyani", "Chicken Biriyani", "80", "2", "Outlet A", "10","100"));
//        orderItems.add(new DummyOrderItems("13", "Veg Biriyani", "Veg Biriyani", "80", "2", "Outlet C", "10","80"));
//        orderItems.add(new DummyOrderItems("14", "Porotta", "Porotta", "10", "3", "Outlet A", "10","10"));
//        orderItems.add(new DummyOrderItems("15", "Gobi Manchurian", "Gobi Manchurian", "80", "3", "Outlet A", "10","80"));
//        orderItems.add(new DummyOrderItems("16", "Porotta", "Porotta", "10", "3", "Outlet B", "10","10"));
//        orderItems.add(new DummyOrderItems("17", "EGG curry", "Egg Curry", "40", "3", "Outlet B", "10","40"));
//        orderItems.add(new DummyOrderItems("18", "Porotta", "Porotta", "10", "3", "Outlet C", "10","10"));
//        orderItems.add(new DummyOrderItems("19", "Chicken Curry", "Chicken Curry", "60", "3", "Outlet C", "10","60"));
//        orderItems.add(new DummyOrderItems("20", "Water", "Water", "10", "4", "Outlet A", "10","10"));
//        orderItems.add(new DummyOrderItems("21", "Falooda", "Falooda", "10", "4", "Outlet B", "10","10"));
//        orderItems.add(new DummyOrderItems("22", "Vanilla Ice Cream", "Vanilla Ice Cream", "10", "4", "Outlet A", "10","10"));
//        orderItems.add(new DummyOrderItems("23", "PineApple Juice", "PineApple Juice", "10", "4", "Outlet B", "10","10"));
//        orderItems.add(new DummyOrderItems("24", "Coffee", "Coffee", "10", "4", "Outlet C", "10","10"));
//        orderItems.add(new DummyOrderItems("25", "Tea", "Tea", "10", "4", "Outlet C", "10","10"));
//        return orderItems;
//    }
//    private List<DummyCategory> getCatagories() {
//        categories.clear();
//        categories.add(new DummyCategory("1", "Outlet A"));
//        categories.add(new DummyCategory("2", "Outlet B"));
//        categories.add(new DummyCategory("3", "Outlet C"));
//        return categories;
//    }
//    private List<DummyCategory> getSessions() {
//        categories.clear();
//        categories.add(new DummyCategory("1", getString(R.string.str_menu_breakfast)));
//        categories.add(new DummyCategory("2", getString(R.string.str_menu_lunch)));
//        categories.add(new DummyCategory("3", getString(R.string.str_menu_dinner)));
//        categories.add(new DummyCategory("4", getString(R.string.str_menu_snacks)));
//        return categories;
//    }
    public String getSessionName(String ID)
    {
        String SessionName="";
        List<Session> dummyCategories=new ArrayList<>();
        dummyCategories.clear();
        dummyCategories=sessionList;
        for (Session category:dummyCategories)
        {
            if (category.getOrderSessionID().contains(ID))
            {
                SessionName=category.getSessionName();

            }
        }
        return  SessionName;
    }



    @Override
    public void OnOutletSelect(List<String> Outlets) {

        final Map<String, List<Event>> events_new = toMap(loadEvents(getSessionFilter(Outlets, Originalitems)));
        items.clear();
        int i = 0;
        for (String sessions : events_new.keySet()) {
            HeaderItem header = new HeaderItem(sessions,i);
            items.add(header);

            for (Event event : events_new.get(sessions)) {
                ++i;
                EventItem item = new EventItem(event,i);
                items.add(item);

            }
            ++i;

        }


        headerItems.clear();
        for(int j = 0;j<items.size();++j){

            if(items.get(j).getType()==ListItem.TYPE_HEADER){
                headerItems.add((HeaderItem) items.get(j));
            }
        }


        binding.ItemsListRV.setLayoutManager(linearLayoutManager);
        EventsAdapter eventsAdapter=new EventsAdapter(items,PreOrderTabedActivity.this,
                PreOrderTabedActivity.this,PreOrderTabedActivity.this);
        eventsAdapter.notifyDataSetChanged();
        binding.ItemsListRV.setAdapter(eventsAdapter);
    }
    public List<Item> getSessionFilter(List<String> filter, List<Item> dummyOrderItems) {
        List<Item> items = new ArrayList<>();
        items.clear();
        List<String> dummmyCategory = new ArrayList<>();
        dummmyCategory.clear();
        dummmyCategory = filter;
        for (Item orderItems : dummyOrderItems) {
            if (dummmyCategory.size() == 0) {

                    items.add(orderItems);
            } else {

                    if (dummmyCategory.contains(orderItems.getOutletName())) {
                        items.add(orderItems);
                    }

            }
        }
        return items;
    }

    @Override
    public void OnSessionSelected(HeaderItem headerItem) {
        int pos =headerItem.getPosition();
        linearLayoutManager.scrollToPositionWithOffset(pos,0);
    }

    @Override
    public void onAddtocartCallback() {
        if (Constants.CartItems.size()==0)
        {
            binding.CartLL.setVisibility(View.GONE);
        }
        else
        {
            float Total_Price = 0;
            int Total_Count=0;
            for (int i=0;i<Constants.CartItems.size();i++)
            {
                Total_Price=Total_Price+(Float.parseFloat(Constants.CartItems.get(i).getPrice()));
                Total_Count=Total_Count+i;
            }
            binding.CartQtyTV.setText(String.valueOf(Constants.CartItems.size()));
            binding.CartAmountTV.setText(/*"Total : "+*/getResources().getString(R.string.indian_rupee_symbol)+Total_Price);
            binding.CartLL.setVisibility(View.VISIBLE);
        }
    }

    private void loadOrderItemsPHP() {

        setProgressBarVisible();
        String URL;
        URL = Constants.BASE_ORDER_URL + "outlet_order_items";
//        System.out.println("CHECK---> URL " + URL);
        CommonSSLConnection commonSSLConnection = new CommonSSLConnection();
        StringRequest jsonObjectRequestLogin = new StringRequest(Request.Method.POST, URL, new Response.Listener<String>() {
            @Override
            public void onResponse(String jsonObject) {
//                System.out.println("CHECK---> Response " + jsonObject);
                setProgressBarGone();

                CampusWallet.getInstance().cancelPendingRequests(tag_json_object);

                Gson gson = new Gson();
                OrderItemsDetails noticeboard = gson.fromJson(jsonObject,OrderItemsDetails.class);
                Status status =noticeboard.getStatus();
                String code =status.getCode();
                if(code.equals("200")){

                    List<Datum> myDataset= noticeboard.getData();
                    if (myDataset.size()>0) {
                        for (int i=0;i<myDataset.size();i++)
                        {
                            sessionList.clear();
                            sessionList=myDataset.get(i).getSession();


                            categoryList.clear();
                            categoryList=myDataset.get(i).getCategory();


                            outletList.clear();
                            outletList=myDataset.get(i).getOutlet();

                            Originalitems.clear();

                            Originalitems=myDataset.get(i).getItems();
                            populateItems(Originalitems);



                        }


                    }
                    else {
                        NodataTV.setText(getResources().getString(R.string.shop_closed));
//                        NodataIV.setBackground(getDrawable(R.drawable.shop_close_img));
                        GoBackBTN.setText(getResources().getString(R.string.go_back));
//                        ErrorImage.setBackgroundResource(R.drawable.nodata);
                        findViewById(R.id.NodataLL).setVisibility(View.VISIBLE);
                        GoBackBTN.setVisibility(View.GONE);
                    }

                }

                else if (code.equalsIgnoreCase("204")){
                    setProgressBarGone();
                    String message = status.getMessage();
                    NodataTV.setText(getResources().getString(R.string.shop_closed));
//                    NodataIV.setBackground(getDrawable(R.drawable.shop_close_img));
                    GoBackBTN.setText(getResources().getString(R.string.go_back));
//                        ErrorImage.setBackgroundResource(R.drawable.nodata);
                    findViewById(R.id.NodataLL).setVisibility(View.VISIBLE);
                    findViewById(R.id.ErrorLL).setVisibility(View.GONE);
                    GoBackBTN.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {


                            onBackPressed();

                        }
                    });
//                    Toast.makeText(PreOrderTabedActivity.this, message, Toast.LENGTH_SHORT).show();
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
                    Toast.makeText(PreOrderTabedActivity.this, message, Toast.LENGTH_SHORT).show();
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
                Toast.makeText(PreOrderTabedActivity.this, getResources().getString(R.string.network_error_try), Toast.LENGTH_SHORT).show();
            }
        }){
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> params = new HashMap<>();
                params.put("targetUserID", prefManager.getStudentUserId());
                params.put("schoolID", prefManager.getSChoolID());
//                System.out.println("CHECK---> "+prefManager.getStudentUserId()+ " , "+prefManager.getSChoolID());

                return params;
            }
        };

        jsonObjectRequestLogin.setRetryPolicy(new DefaultRetryPolicy(50000, 3, DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
//        RequestQueue requestQueue = Volley.newRequestQueue(this, commonSSLConnection.getHulkstack(PreOrderTabedActivity.this));
        CampusWallet.getInstance().addToRequestQueue(jsonObjectRequestLogin, tag_json_object);
//        requestQueue.add(jsonObjectRequestLogin);

    }
    public void setProgressBarVisible(){
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE,
                WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE);
        binding.mProgressBar.setVisibility(View.VISIBLE);

    }

    public void setProgressBarGone(){

        getWindow().clearFlags(WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE);
        binding.mProgressBar.setVisibility(View.GONE);

    }
    public void populateItems(List<Item> itemsFood)
    {
        final Map<String, List<Event>> events_new = toMap(loadEvents(getSessionFilter(new ArrayList<String>(),itemsFood)));
        items.clear();
        int i = 0;
        for (String sessions : events_new.keySet()) {
            HeaderItem header = new HeaderItem(sessions,i);
            items.add(header);

            for (Event event : events_new.get(sessions)) {
                ++i;
                EventItem item = new EventItem(event,i);
                items.add(item);

            }
            ++i;

        }


        headerItems.clear();
        for(int j = 0;j<items.size();++j){

            if(items.get(j).getType()==ListItem.TYPE_HEADER){
                headerItems.add((HeaderItem) items.get(j));
            }
        }


        binding.ItemsListRV.setLayoutManager(linearLayoutManager);
        EventsAdapter eventsAdapter=new EventsAdapter(items,PreOrderTabedActivity.this,
                PreOrderTabedActivity.this,PreOrderTabedActivity.this);
        eventsAdapter.notifyDataSetChanged();
        binding.ItemsListRV.setAdapter(eventsAdapter);
    }
    
}
