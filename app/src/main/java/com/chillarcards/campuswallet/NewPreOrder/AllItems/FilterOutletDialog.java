package com.chillarcards.campuswallet.NewPreOrder.AllItems;

import android.annotation.SuppressLint;
import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.appcompat.widget.Toolbar;

import android.text.InputFilter;
import android.text.Spanned;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.chillarcards.campuswallet.R;
import com.google.firebase.analytics.FirebaseAnalytics;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import com.chillarcards.campuswallet.NewPreOrder.FilterCallBack;
import com.chillarcards.campuswallet.NewPreOrder.FilterNewAdapter;
import com.chillarcards.campuswallet.networkmodels.OrderItemsDetails.Category;
import com.chillarcards.campuswallet.networkmodels.OrderItemsDetails.Outlet;

public class FilterOutletDialog extends DialogFragment implements FilterSingleItemCallback{

    public static String TAG = "CreateLeaveRequestDialog";
    public static Calendar FmCalendar;
    public static Calendar TmCalendar;
    private int mYear, mMonth, mDay;
    private String dateFrom = "",dateTo = "";
    FilterCallBack filterCallBack;
    List<Outlet> outletList;
    List<Category> categoryList;
    FirebaseAnalytics mFirebaseAnalytics;
    List<String> listOutlet=new ArrayList<>();
    private String blockCharacterSet = "¿¡》《¤▪☆♧♢♡♤■□●○~#^|$%&*!&()€¥•_[]=£><@-+/`√π÷×¶∆¢°{}©®™✓:;?'\"\\";
    private InputFilter filter = new InputFilter() {

        @Override
        public CharSequence filter(CharSequence source, int start, int end, Spanned dest, int dstart, int dend) {

            if (source != null && blockCharacterSet.contains(("" + source))) {
                return "";
            }
            return null;
        }
    };
    final String tag_json_object = "c_leaveRequest";
    public FilterOutletDialog() {
    }
    @SuppressLint("ValidFragment")
    public FilterOutletDialog(FilterCallBack filterCallBack,List<Outlet> outletList,List<Category> categoryList) {
        this.filterCallBack=filterCallBack;
        this.outletList=outletList;
        this.categoryList=categoryList;
    }


    Button FilteCloseBTN;
    Button SetButton;
    RecyclerView FilterRV;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
//        setStyle(DialogFragment.STYLE_NORMAL, R.style.FullScreenDialogStyle);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        super.onCreateView(inflater, container, savedInstanceState);
        View view = inflater.inflate(R.layout.filter_outlets, container, false);
        FilterRV = view.findViewById(R.id.FilterRV);
        SetButton = view.findViewById(R.id.SetButton);
        FilteCloseBTN = view.findViewById(R.id.FilteCloseBTN);
        listOutlet.clear();
        mFirebaseAnalytics=FirebaseAnalytics.getInstance(getActivity());

        FilteCloseBTN.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dismiss();
            }
        });
        SetButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                filterCallBack.OnOutletSelect(listOutlet);
                dismiss();
            }
        });
        LinearLayoutManager layoutManager = new LinearLayoutManager(getActivity());
        layoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        FilterRV.setLayoutManager(layoutManager);


        FilterNewAdapter FilterAdapter = new FilterNewAdapter(outletList, getCategoryname(), getActivity(), this);
        FilterRV.setAdapter(FilterAdapter);
        return view;
    }

    @Override
    public void onStart() {
        super.onStart();
        Dialog dialog = getDialog();

        if (dialog != null) {
            int width = ViewGroup.LayoutParams.MATCH_PARENT;
            int height = ViewGroup.LayoutParams.MATCH_PARENT;
            dialog.getWindow().setLayout(width, height);
        }

    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);

    }

    public void cancel()
    {
        dismiss();
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();

    }
    public List<String> getCategoryname() {
        List<String> CategorieName = new ArrayList<>();
        CategorieName.clear();

        for (Outlet dummyCategory : outletList) {
            CategorieName.add(dummyCategory.getOutletName());
        }


        return CategorieName;
    }
//    private List<DummyCategory> getCatagories() {
//        List<DummyCategory> categories=new ArrayList<>();
//        categories.clear();
//        categories.add(new DummyCategory("1", "Outlet A"));
//        categories.add(new DummyCategory("2", "Outlet B"));
//        categories.add(new DummyCategory("3", "Outlet C"));
//        return categories;
//    }



    @Override
    public void onSingleItemCallback(String singleOutlet,int pos,int flag) {
        if (flag==0)
        {
            if (listOutlet.contains(singleOutlet)) {
                listOutlet.remove(singleOutlet);
            }
        }
        else {
            if (!listOutlet.contains(singleOutlet)) {
                listOutlet.add(singleOutlet);
            }
        }

    }
}
