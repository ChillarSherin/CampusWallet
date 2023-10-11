package com.chillarcards.campuswallet.NewPreOrder;

import android.app.Dialog;

import androidx.annotation.NonNull;

import com.chillarcards.campuswallet.databinding.ActivityPaymentHistoryBinding;
import com.chillarcards.campuswallet.databinding.ActivityPreorderDesignBinding;
import com.chillarcards.campuswallet.databinding.LayoutPaymentsActivityBinding;
import com.google.android.material.bottomnavigation.BottomNavigationView;

import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.chillarcards.campuswallet.R;

import java.util.ArrayList;
import java.util.List;


import com.chillarcards.campuswallet.application.PrefManager;
import com.chillarcards.campuswallet.utils.BottomNavigationViewHelper;

public class PreorderDesignActivity extends AppCompatActivity implements FilterCallBack {
    PrefManager prefManager;

  
    BottomNavigationView bottomNavigationView;


    RecyclerView FilterRV;
    Button FilteCloseBTN, SetButton;
    List<String> customFilters;

    private static final int MENU_ITEM_ID_BREAKFAST = 1;
    private static final int MENU_ITEM_ID_LUNCH = 2;
    private static final int MENU_ITEM_ID_DINNER = 3;
    private static final int MENU_ITEM_ID_SNACKS = 4;

    List<DummyOrderItems> orderItems;
    List<DummyCategory> categories;
    private PreorderDesignAdapter mAdapter;
    private Dialog MyDialog;
    private ActivityPreorderDesignBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
       // setContentView(R.layout.activity_preorder_design);
        binding = ActivityPreorderDesignBinding.inflate(getLayoutInflater());
        View view = binding.getRoot();
        setContentView(view);
        
        prefManager = new PrefManager(this);
        orderItems = new ArrayList<>();
        categories = new ArrayList<>();

        binding.HeaderTV.setText(getResources().getString(R.string.pre_order_caps));
        binding.BackIV.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });
        customFilters = new ArrayList<>();
        customFilters.clear();
        bottomNavigationView.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);
    }

    @Override
    protected void onResume() {
        super.onResume();

        Menu menu = bottomNavigationView.getMenu();
        menu.add(Menu.NONE, MENU_ITEM_ID_BREAKFAST, Menu.NONE, getString(R.string.str_menu_breakfast)).setIcon(R.drawable.ic_breakfast).setShowAsAction(MenuItem.SHOW_AS_ACTION_ALWAYS | MenuItem.SHOW_AS_ACTION_WITH_TEXT);
        menu.add(Menu.NONE, MENU_ITEM_ID_LUNCH, Menu.NONE, getString(R.string.str_menu_lunch)).setIcon(R.drawable.ic_lunch).setShowAsAction(MenuItem.SHOW_AS_ACTION_ALWAYS | MenuItem.SHOW_AS_ACTION_WITH_TEXT);
        menu.add(Menu.NONE, MENU_ITEM_ID_DINNER, Menu.NONE, getString(R.string.str_menu_dinner)).setIcon(R.drawable.ic_dinner).setShowAsAction(MenuItem.SHOW_AS_ACTION_ALWAYS | MenuItem.SHOW_AS_ACTION_WITH_TEXT);
        menu.add(Menu.NONE, MENU_ITEM_ID_SNACKS, Menu.NONE, getString(R.string.str_menu_snacks)).setIcon(R.drawable.ic_breakfast).setShowAsAction(MenuItem.SHOW_AS_ACTION_ALWAYS | MenuItem.SHOW_AS_ACTION_WITH_TEXT);

        BottomNavigationViewHelper.removeShiftMode(bottomNavigationView);

        binding.ItemRL.setLayoutManager(new LinearLayoutManager(PreorderDesignActivity.this, LinearLayoutManager.VERTICAL, false));
        binding.ItemRL.setItemAnimator(new DefaultItemAnimator());
        binding.ItemRL.setNestedScrollingEnabled(false);
        mAdapter = new PreorderDesignAdapter(getSessionFilter("1", customFilters, valuesInputs()), PreorderDesignActivity.this);
        mAdapter.notifyDataSetChanged();
        binding.ItemRL.setAdapter(mAdapter);

        binding.FilterBTN.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                FilterListDialogue filterListDialogue=
//                        new FilterListDialogue(PreorderDesignActivity.this,PreorderDesignActivity.this,
//                                getCatagories(),PreorderDesignActivity.this);
////                filterListDialogue.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
//                filterListDialogue.show();
//                DummyFilterListDialogue dialog = new DummyFilterListDialogue(PreorderDesignActivity.this,
//                                getCatagories(),PreorderDesignActivity.this);
//                FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
//                dialog.show(ft, DummyFilterListDialogue.TAG);
//                filterFunction();

            }
        });

    }

    private BottomNavigationView.OnNavigationItemSelectedListener mOnNavigationItemSelectedListener
            = new BottomNavigationView.OnNavigationItemSelectedListener() {

        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {
            Bundle Firebase_bundle = new Bundle();
            Firebase_bundle.putString("", "");
            switch (item.getItemId()) {
                case MENU_ITEM_ID_BREAKFAST:
                    binding.ItemRL.setLayoutManager(new LinearLayoutManager(PreorderDesignActivity.this, LinearLayoutManager.VERTICAL, false));
                    binding.ItemRL.setItemAnimator(new DefaultItemAnimator());
                    binding.ItemRL.setNestedScrollingEnabled(false);
                    mAdapter = new PreorderDesignAdapter(getSessionFilter("1", customFilters, valuesInputs()), PreorderDesignActivity.this);
                    mAdapter.notifyDataSetChanged();
                    binding.ItemRL.setAdapter(mAdapter);
                    return true;
                case MENU_ITEM_ID_LUNCH:
                    binding.ItemRL.setLayoutManager(new LinearLayoutManager(PreorderDesignActivity.this, LinearLayoutManager.VERTICAL, false));
                    binding.ItemRL.setItemAnimator(new DefaultItemAnimator());
                    binding.ItemRL.setNestedScrollingEnabled(false);
                    mAdapter = new PreorderDesignAdapter(getSessionFilter("2", customFilters, valuesInputs()), PreorderDesignActivity.this);
                    mAdapter.notifyDataSetChanged();
                    binding.ItemRL.setAdapter(mAdapter);
                    return true;
                case MENU_ITEM_ID_DINNER:
                    binding.ItemRL.setLayoutManager(new LinearLayoutManager(PreorderDesignActivity.this, LinearLayoutManager.VERTICAL, false));
                    binding.ItemRL.setItemAnimator(new DefaultItemAnimator());
                    binding.ItemRL.setNestedScrollingEnabled(false);
                    mAdapter = new PreorderDesignAdapter(getSessionFilter("3", customFilters, valuesInputs()), PreorderDesignActivity.this);
                    mAdapter.notifyDataSetChanged();
                    binding.ItemRL.setAdapter(mAdapter);
                    return true;
                case MENU_ITEM_ID_SNACKS:
                    binding.ItemRL.setLayoutManager(new LinearLayoutManager(PreorderDesignActivity.this, LinearLayoutManager.VERTICAL, false));
                    binding.ItemRL.setItemAnimator(new DefaultItemAnimator());
                    binding.ItemRL.setNestedScrollingEnabled(false);
                    mAdapter = new PreorderDesignAdapter(getSessionFilter("4", customFilters, valuesInputs()), PreorderDesignActivity.this);
                    mAdapter.notifyDataSetChanged();
                    binding.ItemRL.setAdapter(mAdapter);
                    return true;

                default:
                    break;
            }
            return false;
        }
    };

    private List<DummyOrderItems> valuesInputs() {
//        orderItems.clear();
//        orderItems.add(new DummyOrderItems("1", "Idili", "Idili", "10", "1", "Outlet A", "10","10"));
//        orderItems.add(new DummyOrderItems("2", "Idili", "Idili", "10", "1", "Outlet B", "10","10"));
//        orderItems.add(new DummyOrderItems("3", "Dosha", "Dosha", "10", "1", "Outlet A", "10","10"));
//        orderItems.add(new DummyOrderItems("4", "Dosha", "Dosha", "10", "1", "Outlet C", "10","10"));
//        orderItems.add(new DummyOrderItems("5", "Meals", "Meals", "10", "2", "Outlet A", "10","10"));
//        orderItems.add(new DummyOrderItems("6", "Meals", "Fish curry Meals", "80", "2", "Outlet A", "10","80"));
//        orderItems.add(new DummyOrderItems("7", "Meals", "veg Meals", "40", "2", "Outlet B", "10","40"));
//        orderItems.add(new DummyOrderItems("8", "meals", "non-veg meals", "100", "2", "Outlet B", "10","100"));
//        orderItems.add(new DummyOrderItems("9", "meals", "non-veg meals", "80", "2", "Outlet C", "10","80"));
//        orderItems.add(new DummyOrderItems("10", "Porotta", "Porotta", "10", "3", "Outlet A", "10","10"));
//        orderItems.add(new DummyOrderItems("11", "Beef", "Beef", "80", "3", "Outlet A", "10","80"));
//        orderItems.add(new DummyOrderItems("12", "Porotta", "Porotta", "10", "3", "Outlet B", "10","10"));
//        orderItems.add(new DummyOrderItems("13", "egg curry", "Egg Curry", "40", "3", "Outlet B", "10","40"));
//        orderItems.add(new DummyOrderItems("14", "Porotta", "Porotta", "10", "3", "Outlet C", "10","10"));
//        orderItems.add(new DummyOrderItems("15", "Chicken Curry", "Chicken Curry", "60", "3", "Outlet C", "10","60"));
//        orderItems.add(new DummyOrderItems("16", "Water", "Water", "10", "4", "Outlet A", "10","10"));
//        orderItems.add(new DummyOrderItems("17", "Bonda", "Bonda", "10", "4", "Outlet B", "10","10"));
       return orderItems;
    }

    public List<DummyOrderItems> getSessionFilter(String session, List<String> filter, List<DummyOrderItems> dummyOrderItems) {
        List<DummyOrderItems> items = new ArrayList<>();
        items.clear();
        List<String> dummmyCategory = new ArrayList<>();
        dummmyCategory.clear();
        dummmyCategory = filter;
        for (DummyOrderItems orderItems : dummyOrderItems) {
            if (dummmyCategory.size() == 0) {
                if (orderItems.getCategotyId().equalsIgnoreCase(session)) {
                    items.add(orderItems);
                }
            } else {
                if (orderItems.getCategotyId().equalsIgnoreCase(session)) {

                    if (dummmyCategory.contains(orderItems.getStoreName())) {
                        items.add(orderItems);
                    }
//
//
//                    for (String mFilters: dummmyCategory)
//                    {
//                        if (orderItems.getStoreName().equalsIgnoreCase(mFilters)) {
//                            items.add(orderItems);
//                        }
//                    }

                }
            }
        }
        return items;
    }

    private List<DummyCategory> getCatagories() {
        categories.clear();
        categories.add(new DummyCategory("1", "Outlet A"));
        categories.add(new DummyCategory("2", "Outlet B"));
        categories.add(new DummyCategory("3", "Outlet C"));
        return categories;
    }

    public List<String> getCategoryname() {
        List<String> CategorieName = new ArrayList<>();
        CategorieName.clear();

        for (DummyCategory dummyCategory : getCatagories()) {
            CategorieName.add(dummyCategory.getName());
        }


        return CategorieName;
    }


    @Override
    public void OnOutletSelect(List<String> Outlets) {
        //System.out.println("Filter Changed : " + Outlets);
       // customFilters.clear();
        customFilters = Outlets;
        binding.ItemRL.setLayoutManager(new LinearLayoutManager(PreorderDesignActivity.this, LinearLayoutManager.VERTICAL, false));
        binding.ItemRL.setItemAnimator(new DefaultItemAnimator());
        binding.ItemRL.setNestedScrollingEnabled(false);
        mAdapter = new PreorderDesignAdapter(getSessionFilter("1", customFilters, valuesInputs()), PreorderDesignActivity.this);
        mAdapter.notifyDataSetChanged();
        binding.ItemRL.setAdapter(mAdapter);

    }

//    public void filterFunction() {
//        MyDialog = new Dialog(this);
//        MyDialog.setContentView(R.layout.filter_outlets);
//        MyDialog.setCanceledOnTouchOutside(false);
//        FilteCloseBTN = MyDialog.findViewById(R.id.FilteCloseBTN);
//        SetButton = MyDialog.findViewById(R.id.SetButton);
//        FilterRV = MyDialog.findViewById(R.id.FilterRV);
//
//        FilteCloseBTN.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                MyDialog.dismiss();
//            }
//        });
//        SetButton.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                MyDialog.dismiss();
//            }
//        });
//
//        LinearLayoutManager layoutManager = new LinearLayoutManager(MyDialog.getContext());
//        layoutManager.setOrientation(LinearLayoutManager.VERTICAL);
//        FilterRV.setLayoutManager(layoutManager);
//
//
//        FilterNewAdapter FilterAdapter = new FilterNewAdapter(getCatagories(), getCategoryname(), this, PreorderDesignActivity.this);
//        FilterRV.setAdapter(FilterAdapter);
//        MyDialog.getWindow().setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
//        try {
//            MyDialog.show();
//        } catch (Exception e) {
//
//        }
//    }
}
