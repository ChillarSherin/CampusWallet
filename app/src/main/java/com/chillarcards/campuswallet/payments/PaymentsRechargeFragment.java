package com.chillarcards.campuswallet.payments;


import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.text.InputFilter;
import android.text.Spanned;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ExpandableListView;
import android.widget.TextView;
import android.widget.Toast;

import com.chillarcards.campuswallet.R;
//import com.gocashfree.cashfreesdk.CFPaymentService;
//import com.gocashfree.cashfreesdk.ui.gpay.GooglePayStatusListener;
import com.chillarcards.campuswallet.databinding.LayoutLoginActivityBinding;
import com.chillarcards.campuswallet.databinding.LayoutRechargeFragmentRazorBinding;
import com.google.firebase.analytics.FirebaseAnalytics;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;


import com.chillarcards.campuswallet.application.Constants;
import com.chillarcards.campuswallet.networkmodels.otherpaymentmodes.Code;
import com.chillarcards.campuswallet.networkmodels.otherpaymentmodes.OtherPayment;
import com.chillarcards.campuswallet.networkmodels.otherpaymentmodes.Status;
import com.chillarcards.campuswallet.networkmodels.otherpaymentmodes.SubItem;
import com.chillarcards.campuswallet.networkmodels.paymentmodes.PaymentMode;
import com.chillarcards.campuswallet.utils.ClickListener;
import com.chillarcards.campuswallet.utils.Clipboard_Utils;
import com.chillarcards.campuswallet.utils.RecyclerTouchListener;

public class PaymentsRechargeFragment extends Fragment {

    PaymentModesAdapter mAdapter;
    String category = "";
    String fromLowbalance, rechargeAmount, paymentGateWayName;
    FirebaseAnalytics mFirebaseAnalytics;

    HashMap<String, List<SubItem>> expandableListDetail = new HashMap<String, List<SubItem>>();
    List<String> expandableListTitle;
    String jsonObject;
    int i = 0;
    private String blockCharacterSet = ".¿¡》《¤▪☆♧♢♡♤■□●○~#^|$%&*!&()€¥•_[]=£><@-+/`√π÷×¶∆¢°{}©®™✓:;?'\"\\";
    private InputFilter filter = new InputFilter() {

        @Override
        public CharSequence filter(CharSequence source, int start, int end, Spanned dest, int dstart, int dend) {

            if (source != null && blockCharacterSet.contains(("" + source))) {
                return "";
            }
            return null;
        }
    };

    public PaymentsRechargeFragment() {
    }
    private LayoutRechargeFragmentRazorBinding binding;
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        binding = LayoutRechargeFragmentRazorBinding.inflate(inflater, container, false);


        LinearLayoutManager mLayoutManager = new LinearLayoutManager(this.getActivity().getBaseContext());
        binding.recyclerView3.setLayoutManager(mLayoutManager);
        binding.editText2.setFilters(new InputFilter[]{filter/*, new InputFilter.LengthFilter(100)*/});
        binding.editText2.setOnClickListener(v -> Clipboard_Utils.copyToClipboard(getActivity(), ""));
        //Show keyboard and focuz on amount edittext
        binding.editText2.requestFocus();
//        InputMethodManager imm = (InputMethodManager) getActivity().getSystemService(Context.INPUT_METHOD_SERVICE);
//        imm.toggleSoftInput(InputMethodManager.SHOW_FORCED, InputMethodManager.HIDE_IMPLICIT_ONLY);

        init();
        mFirebaseAnalytics = FirebaseAnalytics.getInstance(getActivity());
        binding.btnRecharge.setOnClickListener(view -> btnClick());
        binding.textView5.setOnClickListener(view -> btnCharges());
        return binding.getRoot();
    }

    private void init() {

        Bundle arguments = getArguments();
        String paymentModesStr = arguments.getString("paymentModes");
        Log.d("abc", "init: "+paymentModesStr);
        jsonObject = arguments.getString("jsonObject");
        category = arguments.getString("category");
        fromLowbalance = arguments.getString("fromLowbalance");
        rechargeAmount = arguments.getString("rechargeAmount");
        paymentGateWayName = arguments.getString("paymentGateWayName");
        if (fromLowbalance.equalsIgnoreCase("1")) {
            binding.editText2.setText(rechargeAmount);
//            binding.editText2.setEnabled(false);
            Constants.from_Low_balance = true;
            Constants.from_old_preorder = 0;
        } else if (fromLowbalance.equalsIgnoreCase("5")) {
            Constants.from_Low_balance = true;
            Constants.from_old_preorder = 1;
        } else {
            binding.editText2.setEnabled(true);
            Constants.from_Low_balance = false;
        }


        if (paymentGateWayName.equalsIgnoreCase("cashfree")) {

            binding.recyclerView2.setVisibility(View.VISIBLE);
            Gson gson1 = new Gson();

            OtherPayment modes = gson1.fromJson(jsonObject, OtherPayment.class);
            Status status = modes.getStatus();

            Code code1 = modes.getCode();
            List<com.chillarcards.campuswallet.networkmodels.otherpaymentmodes.PaymentMode> paymentModes = new ArrayList<>();

            paymentModes = code1.getPaymentModes();

            for (int i = 0; i < paymentModes.size(); ++i) {

                String itemName = paymentModes.get(i).getName();
                List<SubItem> subItems = paymentModes.get(i).getSubItems();

                expandableListDetail.put(itemName, subItems);
            }

            expandableListTitle = new ArrayList<String>(expandableListDetail.keySet());
            CustomExpandableListAdapter expandableListAdapter = new CustomExpandableListAdapter(getActivity(), expandableListTitle, expandableListDetail);
            binding.recyclerView2.setAdapter(expandableListAdapter);


            binding.recyclerView2.setOnGroupClickListener(new ExpandableListView.OnGroupClickListener() {
                @Override
                public boolean onGroupClick(ExpandableListView parent, View v, int groupPosition, long id) {


                    if (expandableListDetail.get(expandableListTitle.get(groupPosition)).size() == 0) {

                        if (!binding.editText2.getText().toString().equalsIgnoreCase("")) {
                            float amount = Float.parseFloat(binding.editText2.getText().toString());
                            if (amount > 0) {

                                switch (expandableListTitle.get(groupPosition)) {

                                    case "CC":
                                    case "DC":
                                        // TODO: 24-06-2022 hidden to get rid of cashfree modules to check if cashfree causing intent redirection policy violation in playstore
//                                        mFirebaseAnalytics.logEvent(getActivity().getResources().getString(R.string.Card_Recharge_Credit_Debit_Card_Clicked), new Bundle());
//                                        Intent i0 = new Intent(getActivity(), PaymentsCashfreeActivity.class);
//                                        Bundle b0 = new Bundle();
//                                        b0.putString("paymentMode", expandableListTitle.get(groupPosition));
//                                        b0.putString("amount", binding.editText2.getText().toString());
//                                        b0.putString("trans_category", category);
//                                        b0.putString("paymentGateway", paymentGateWayName);
//                                        i0.putExtras(b0);
//                                        startActivity(i0);
                                        break;

//                            case "Netbanking":
//                                mFirebaseAnalytics.logEvent(getActivity().getResources().getString(R.string.Card_Recharge_Netbanking_Clicked), new Bundle());
//                                Intent i1 = new Intent(getActivity(), PaymentsCashfreeActivity.class);
//                                Bundle b1 = new Bundle();
//                                b1.putString("paymentMode", expandableListTitle.get(groupPosition));
//                                b1.putString("amount", binding.editText2.getText().toString());
//                                b1.putString("trans_category", category);
//                                b1.putString("paymentGateway", paymentGateWayName);
//                                i1.putExtras(b1);
//                                startActivity(i1);
//                                break;


//                            default:
//                                Intent i00 = new Intent(getActivity(), PaymentCreateActivity.class);
//                                Bundle b00 = new Bundle();
//                                b00.putString("paymentMode", expandableListTitle.get(groupPosition));
//                                b00.putString("amount", binding.editText2.getText().toString());
//                                b00.putString("trans_category", category);
//                                b00.putString("paymentGateway", paymentGateWayName);
//                                i00.putExtras(b00);
//                                startActivity(i00);
                                }


                            }


                        }

                    }
                    return false;
                }
            });

            binding.recyclerView2.setOnChildClickListener(new ExpandableListView.OnChildClickListener() {
                @Override
                public boolean onChildClick(ExpandableListView parent, View v,
                                            int groupPosition, int childPosition, long id) {
//                    Toast.makeText(
//                            getActivity(),
//                            expandableListTitle.get(groupPosition)
//                                    + " -> "
//                                    + expandableListDetail.get(
//                                    expandableListTitle.get(groupPosition)).get(
//                                    childPosition), Toast.LENGTH_SHORT
//                    ).show();
                    if (!binding.editText2.getText().toString().equalsIgnoreCase("")) {
                        float amount = Float.parseFloat(binding.editText2.getText().toString());
                        if (amount > 0 & amount <= 10000) {
                            switch (expandableListTitle.get(groupPosition)) {

                                case "UPI": {

                                    // TODO: 24-06-2022 hidden to get rid of cashfree modules to check if cashfree causing intent redirection policy violation in playstore
//                                    if (expandableListDetail.get(expandableListTitle.get(groupPosition)).get(childPosition).getItemName().equalsIgnoreCase("GooglePay")) {
//
//                                        CFPaymentService.getCFPaymentServiceInstance().isGPayReadyForPayment(getActivity(), new GooglePayStatusListener() {
//                                            @Override
//                                            public void isReady() {
//                                                Intent i00 = new Intent(getActivity(), PaymentCreateActivity.class);
//                                                Bundle b00 = new Bundle();
//                                                b00.putString("paymentMode", "UPI");
//                                                b00.putString("amount", binding.editText2.getText().toString());
//                                                b00.putString("trans_category", category);
//                                                b00.putString("paymentGateway", paymentGateWayName);
//                                                i00.putExtras(b00);
//                                                startActivity(i00);
//                                            }
//
//                                            @Override
//                                            public void isNotReady() {
//
//                                                Toast.makeText(getActivity(), "Sorry,Google Pay not found in your phone", Toast.LENGTH_SHORT).show();
//                                            }
//                                        });
//                                        break;
//                                    } else if (expandableListDetail.get(expandableListTitle.get(groupPosition)).get(childPosition).getItemName().equalsIgnoreCase("PhonePe")) {
//                                        if (CFPaymentService.getCFPaymentServiceInstance().doesPhonePeExist(getActivity(), "PROD")) {
//                                            Intent i00 = new Intent(getActivity(), PaymentCreateActivity.class);
//                                            Bundle b00 = new Bundle();
//                                            b00.putString("paymentMode", "UPI");
//                                            b00.putString("amount", binding.editText2.getText().toString());
//                                            b00.putString("trans_category", category);
//                                            b00.putString("paymentGateway", paymentGateWayName);
//                                            i00.putExtras(b00);
//                                            startActivity(i00);
//
//                                        } else {
//                                            Toast.makeText(getActivity(), "Sorry,PhonePe not found in your phone", Toast.LENGTH_SHORT).show();
//
//                                        }
//                                    } else
                                    if (expandableListDetail.get(expandableListTitle.get(groupPosition)).get(childPosition).getItemName().equalsIgnoreCase("AmazonPay")) {
                                        Intent i00 = new Intent(getActivity(), PaymentCreateActivity.class);
                                        Bundle b00 = new Bundle();
                                        b00.putString("paymentMode", "UPI");
                                        b00.putString("amount", binding.editText2.getText().toString());
                                        b00.putString("trans_category", category);
                                        b00.putString("paymentGateway", paymentGateWayName);
                                        i00.putExtras(b00);
                                        startActivity(i00);

                                    } else if (expandableListDetail.get(expandableListTitle.get(groupPosition)).get(childPosition).getItemName().equalsIgnoreCase("Others")) {
                                        // TODO: 24-06-2022 hidden to get rid of cashfree modules to check if cashfree causing intent redirection policy violation in playstore
//                                        Intent i = new Intent(getActivity(), PaymentsCashfreeActivity.class);
//                                        Bundle b = new Bundle();
//                                        b.putString("paymentMode", "UPI");
//                                        b.putString("amount", binding.editText2.getText().toString());
//                                        b.putString("trans_category", category);
//                                        b.putString("paymentGateway", paymentGateWayName);
//                                        i.putExtras(b);
//                                        startActivity(i);
                                    }
                                    break;
                                }

                                case "Wallet":
                                    Intent i00 = new Intent(getActivity(), PaymentCreateActivity.class);
                                    Bundle b00 = new Bundle();
                                    b00.putString("paymentMode", "Wallet");
                                    b00.putString("amount", binding.editText2.getText().toString());
                                    b00.putString("trans_category", category);
                                    b00.putString("paymentGateway", paymentGateWayName);
                                    b00.putString("extra", expandableListDetail.get(expandableListTitle.get(groupPosition)).get(childPosition).getItemCode());
                                    i00.putExtras(b00);
                                    startActivity(i00);
                                    break;
                                case "Netbanking":
                                    Intent i = new Intent(getActivity(), PaymentCreateActivity.class);
                                    Bundle b = new Bundle();
                                    b.putString("paymentMode", "Netbanking");
                                    b.putString("amount", binding.editText2.getText().toString());
                                    b.putString("trans_category", category);
                                    b.putString("paymentGateway", paymentGateWayName);
                                    b.putString("extra", expandableListDetail.get(expandableListTitle.get(groupPosition)).get(childPosition).getItemCode());
                                    i.putExtras(b);
                                    startActivity(i);
                                    break;

                            }


                        } else {
                            Toast.makeText(getActivity(), "Enter valid amount", Toast.LENGTH_SHORT).show();
                        }

                    }
                    return false;
                }
            });

        }
        else {
            Log.d("abc_pay_recharge", "razorpay: ");
            expandableListTitle = new ArrayList<>(expandableListDetail.keySet());
            CustomExpandableListAdapter expandableListAdapter = new CustomExpandableListAdapter(getActivity(), expandableListTitle, expandableListDetail);
            binding.recyclerView2.setAdapter(expandableListAdapter);

            binding.recyclerView3.setVisibility(View.VISIBLE);
            ArrayList<PaymentMode> modePaymentsNew;
            Gson gson = new Gson();
            modePaymentsNew = gson.fromJson(paymentModesStr, new TypeToken<List<PaymentMode>>() {
            }.getType());

            Log.d("abc_pay_recharge", "payment_modes: "+modePaymentsNew.size());

            if (modePaymentsNew.size() > 0) {

                mAdapter = new PaymentModesAdapter(modePaymentsNew, this.getActivity());
                binding.recyclerView3.setAdapter(mAdapter);

                final ArrayList<PaymentMode> finalModePaymentsNew = modePaymentsNew;
                binding.recyclerView3.addOnItemTouchListener(new RecyclerTouchListener(getActivity().getBaseContext(),
                        binding.recyclerView3, new ClickListener() {
                    @Override
                    public void onClick(View view, final int position) {
                        //Values are passing to activity & to fragment as well
//                    Toast.makeText(getActivity().getBaseContext(), "Single Click on position        :" + position,
//                            Toast.LENGTH_SHORT).show();

                        if (binding.editText2.getText().toString().length() > 0) {
                            float amount = Float.parseFloat(binding.editText2.getText().toString());
                            if (amount > 0) {

                                if (paymentGateWayName.equals("cashfree")) {

                                    switch (finalModePaymentsNew.get(position).getName()) {

                                        case "CC":
                                        case "DC":
                                            // TODO: 24-06-2022 hidden to get rid of cashfree modules to check if cashfree causing intent redirection policy violation in playstore
//                                            mFirebaseAnalytics.logEvent(getActivity().getResources().getString(R.string.Card_Recharge_Credit_Debit_Card_Clicked), new Bundle());
//                                            Intent i0 = new Intent(getActivity(), PaymentsCashfreeActivity.class);
//                                            Bundle b0 = new Bundle();
//                                            b0.putString("paymentMode", finalModePaymentsNew.get(position).getName());
//                                            b0.putString("amount", binding.editText2.getText().toString());
//                                            b0.putString("trans_category", category);
//                                            b0.putString("paymentGateway", paymentGateWayName);
//                                            i0.putExtras(b0);
//                                            startActivity(i0);
                                            break;

                                        case "Netbanking":
                                            // TODO: 24-06-2022 hidden to get rid of cashfree modules to check if cashfree causing intent redirection policy violation in playstore
//                                            mFirebaseAnalytics.logEvent(getActivity().getResources().getString(R.string.Card_Recharge_Netbanking_Clicked), new Bundle());
//                                            Intent i1 = new Intent(getActivity(), PaymentsCashfreeActivity.class);
//                                            Bundle b1 = new Bundle();
//                                            b1.putString("paymentMode", "netbanking");
//                                            b1.putString("amount", binding.editText2.getText().toString());
//                                            b1.putString("trans_category", category);
//                                            b1.putString("paymentGateway", paymentGateWayName);
//                                            i1.putExtras(b1);
//                                            startActivity(i1);
                                            break;
                                        case "UPI":
                                            // TODO: 24-06-2022 hidden to get rid of cashfree modules to check if cashfree causing intent redirection policy violation in playstore
//                                            mFirebaseAnalytics.logEvent(getActivity().getResources().getString(R.string.Card_Recharge_Upi_Clicked), new Bundle());
//                                            Intent i2 = new Intent(getActivity(), PaymentsCashfreeActivity.class);
//                                            Bundle b2 = new Bundle();
//                                            b2.putString("paymentMode", "upi");
//                                            b2.putString("amount", binding.editText2.getText().toString());
//                                            b2.putString("trans_category", category);
//                                            b2.putString("paymentGateway", paymentGateWayName);
//                                            i2.putExtras(b2);
//                                            startActivity(i2);
                                            break;


                                        default:
                                            Intent i00 = new Intent(getActivity(), PaymentCreateActivity.class);
                                            Bundle b00 = new Bundle();
                                            b00.putString("paymentMode", finalModePaymentsNew.get(position).getName());
                                            b00.putString("amount", binding.editText2.getText().toString());
                                            b00.putString("trans_category", category);
                                            b00.putString("paymentGateway", paymentGateWayName);
                                            i00.putExtras(b00);
                                            startActivity(i00);
                                    }


                                }
                                else {

                                    Log.d("abc_pay_recharge", "onClick: not cashfree" + finalModePaymentsNew.get(position).getName());
                                    switch (finalModePaymentsNew.get(position).getName()) {
                                        //----CC/DC
                                        case "CC":
                                        case "DC":
                                            mFirebaseAnalytics.logEvent(getActivity().getResources().getString(R.string.Card_Recharge_Credit_Debit_Card_Clicked), new Bundle());
                                            Intent i = new Intent(getActivity(), PaymentCreateActivity.class);
                                            Bundle b = new Bundle();
                                            b.putString("paymentMode", "card");
                                            b.putString("amount", binding.editText2.getText().toString());
                                            b.putString("trans_category", category);
                                            b.putString("paymentGateway", "razorpay");
                                            i.putExtras(b);
                                            startActivity(i);
                                            break;

                                        case "Xpay":
                                            mFirebaseAnalytics.logEvent(getActivity().getResources().getString(R.string.Card_Recharge_Xpay_Clicked), new Bundle());
                                            Intent i4 = new Intent(getActivity(), PaymentXPayWebview.class);
                                            Bundle b4 = new Bundle();
                                            b4.putString("trans_category", category);
                                            b4.putString("amount", String.valueOf(amount));
                                            i4.putExtras(b4);
                                            startActivity(i4);
                                            break;
                                        case "Netbanking":
                                            mFirebaseAnalytics.logEvent(getActivity().getResources().getString(R.string.Card_Recharge_Netbanking_Clicked), new Bundle());
                                            Intent i1 = new Intent(getActivity(), PaymentCreateActivity.class);
                                            Bundle b1 = new Bundle();
                                            b1.putString("paymentMode", "netbanking");
                                            b1.putString("amount", binding.editText2.getText().toString());
                                            b1.putString("trans_category", category);
                                            b1.putString("paymentGateway", "razorpay");
                                            i1.putExtras(b1);
                                            startActivity(i1);
                                            break;
                                        case "UPI":
                                            mFirebaseAnalytics.logEvent(getActivity().getResources().getString(R.string.Card_Recharge_Upi_Clicked), new Bundle());
                                            Intent i2 = new Intent(getActivity(), PaymentCreateActivity.class);
                                            Bundle b2 = new Bundle();
                                            b2.putString("paymentMode", "upi");
                                            b2.putString("amount", binding.editText2.getText().toString());
                                            b2.putString("trans_category", category);
                                            b2.putString("paymentGateway", "razorpay");
                                            i2.putExtras(b2);
                                            startActivity(i2);
                                            break;
                                    }
                                }
                            } else {
                                Toast.makeText(getActivity(), getActivity().getResources().getString(R.string.valid_amount), Toast.LENGTH_SHORT).show();
                            }

                        } else {
                            Toast.makeText(getActivity(), getActivity().getResources().getString(R.string.valid_amount), Toast.LENGTH_SHORT).show();
                        }
                    }

                    @Override
                    public void onLongClick(View view, int position) {
//                    Toast.makeText(getActivity().getBaseContext(), "Long press on position :" + position,
//                            Toast.LENGTH_LONG).show();
                    }
                }));

            } else {

                binding.recyclerView2.setVisibility(View.GONE);
                binding.textView15.setVisibility(View.GONE);
                binding.btnRecharge.setVisibility(View.VISIBLE);
            }
        }


//        binding.recyclerView2.setOnGroupExpandListener(new ExpandableListView.OnGroupExpandListener() {
//
//            @Override
//            public void onGroupExpand(int groupPosition) {
//                Toast.makeText(getActivity(),
//                        expandableListTitle.get(groupPosition) + " List Expanded.",
//                        Toast.LENGTH_SHORT).show();
//            }
//        });
//
//        binding.recyclerView2.setOnGroupCollapseListener(new ExpandableListView.OnGroupCollapseListener() {
//
//            @Override
//            public void onGroupCollapse(int groupPosition) {
//                Toast.makeText(getActivity(),
//                        expandableListTitle.get(groupPosition) + " List Collapsed.",
//                        Toast.LENGTH_SHORT).show();
//
//            }
//        });


        /*ArrayList<PaymentMode> modePaymentsNew = new ArrayList<PaymentMode>();
        Gson gson = new Gson();
        modePaymentsNew = gson.fromJson(paymentModesStr, new TypeToken<List<PaymentMode>>(){}.getType());


        if(modePaymentsNew.size()>0) {

            mAdapter = new PaymentModesAdapter(modePaymentsNew, this.getActivity());
            binding.recyclerView2.setAdapter(mAdapter);

            final ArrayList<PaymentMode> finalModePaymentsNew = modePaymentsNew;
            binding.recyclerView2.addOnItemTouchListener(new RecyclerTouchListener(getActivity().getBaseContext(),
                    binding.recyclerView2, new ClickListener() {
                @Override
                public void onClick(View view, final int position) {
                    //Values are passing to activity & to fragment as well
//                    Toast.makeText(getActivity().getBaseContext(), "Single Click on position        :" + position,
//                            Toast.LENGTH_SHORT).show();

                    if (binding.editText2.getText().toString().length() > 0) {
                        float amount = Float.parseFloat(binding.editText2.getText().toString());
                        if (amount > 0) {

                            if(paymentGateWayName.equals("cashfree")){

                                switch (finalModePaymentsNew.get(position).getName()) {

                                    case "CC":
                                    case "DC":

                                        mFirebaseAnalytics.logEvent(getActivity().getResources().getString(R.string.Card_Recharge_Credit_Debit_Card_Clicked), new Bundle());
                                        Intent i0 = new Intent(getActivity(), PaymentsCashfreeActivity.class);
                                        Bundle b0 = new Bundle();
                                        b0.putString("paymentMode", finalModePaymentsNew.get(position).getName());
                                        b0.putString("amount", binding.editText2.getText().toString());
                                        b0.putString("trans_category", category);
                                        b0.putString("paymentGateway", paymentGateWayName);
                                        i0.putExtras(b0);
                                        startActivity(i0);
                                        break;

                                    case "Netbanking":
                                        mFirebaseAnalytics.logEvent(getActivity().getResources().getString(R.string.Card_Recharge_Netbanking_Clicked), new Bundle());
                                        Intent i1 = new Intent(getActivity(), PaymentsCashfreeActivity.class);
                                        Bundle b1 = new Bundle();
                                        b1.putString("paymentMode", "netbanking");
                                        b1.putString("amount", binding.editText2.getText().toString());
                                        b1.putString("trans_category", category);
                                        b1.putString("paymentGateway", paymentGateWayName);
                                        i1.putExtras(b1);
                                        startActivity(i1);
                                        break;
                                    case "UPI":

                                        mFirebaseAnalytics.logEvent(getActivity().getResources().getString(R.string.Card_Recharge_Upi_Clicked), new Bundle());
                                        Intent i2 = new Intent(getActivity(), PaymentsCashfreeActivity.class);
                                        Bundle b2 = new Bundle();
                                        b2.putString("paymentMode", "upi");
                                        b2.putString("amount", binding.editText2.getText().toString());
                                        b2.putString("trans_category", category);
                                        b2.putString("paymentGateway", paymentGateWayName);
                                        i2.putExtras(b2);
                                        startActivity(i2);
                                        break;


                                        default:
                                            Intent i00 = new Intent(getActivity(), PaymentCreateActivity.class);
                                            Bundle b00 = new Bundle();
                                            b00.putString("paymentMode", finalModePaymentsNew.get(position).getName());
                                            b00.putString("amount", binding.editText2.getText().toString());
                                            b00.putString("trans_category", category);
                                            b00.putString("paymentGateway", paymentGateWayName);
                                            i00.putExtras(b00);
                                            startActivity(i00);
                                }






                            }else {

                                switch (finalModePaymentsNew.get(position).getName()) {

                                    case "CC":
                                    case "DC":

                                        mFirebaseAnalytics.logEvent(getActivity().getResources().getString(R.string.Card_Recharge_Credit_Debit_Card_Clicked), new Bundle());
                                        Intent i = new Intent(getActivity(), PaymentCreateActivity.class);
                                        Bundle b = new Bundle();
                                        b.putString("paymentMode", "card");
                                        b.putString("amount", binding.editText2.getText().toString());
                                        b.putString("trans_category", category);
                                        b.putString("paymentGateway", "razorpay");
                                        i.putExtras(b);
                                        startActivity(i);
                                        break;

                                    case "Xpay":
                                        mFirebaseAnalytics.logEvent(getActivity().getResources().getString(R.string.Card_Recharge_Xpay_Clicked), new Bundle());
                                        Intent i4 = new Intent(getActivity(), PaymentXPayWebview.class);
                                        Bundle b4 = new Bundle();
                                        b4.putString("trans_category", category);
                                        b4.putString("amount", String.valueOf(amount));
                                        i4.putExtras(b4);
                                        startActivity(i4);
                                        break;
                                    case "Netbanking":
                                        mFirebaseAnalytics.logEvent(getActivity().getResources().getString(R.string.Card_Recharge_Netbanking_Clicked), new Bundle());
                                        Intent i1 = new Intent(getActivity(), PaymentCreateActivity.class);
                                        Bundle b1 = new Bundle();
                                        b1.putString("paymentMode", "netbanking");
                                        b1.putString("amount", binding.editText2.getText().toString());
                                        b1.putString("trans_category", category);
                                        b1.putString("paymentGateway", "razorpay");
                                        i1.putExtras(b1);
                                        startActivity(i1);
                                        break;
                                    case "UPI":

                                        mFirebaseAnalytics.logEvent(getActivity().getResources().getString(R.string.Card_Recharge_Upi_Clicked), new Bundle());
                                        Intent i2 = new Intent(getActivity(), PaymentCreateActivity.class);
                                        Bundle b2 = new Bundle();
                                        b2.putString("paymentMode", "upi");
                                        b2.putString("amount", binding.editText2.getText().toString());
                                        b2.putString("trans_category", category);
                                        b2.putString("paymentGateway", "razorpay");
                                        i2.putExtras(b2);
                                        startActivity(i2);
                                        break;
                                }
                            }
                        } else {
                            Toast.makeText(getActivity(), getActivity().getResources().getString(R.string.valid_amount), Toast.LENGTH_SHORT).show();
                        }

                    }else{
                        Toast.makeText(getActivity(), getActivity().getResources().getString(R.string.valid_amount), Toast.LENGTH_SHORT).show();
                    }
                }

                @Override
                public void onLongClick(View view, int position) {
//                    Toast.makeText(getActivity().getBaseContext(), "Long press on position :" + position,
//                            Toast.LENGTH_LONG).show();
                }
            }));

        }else{

            binding.recyclerView2.setVisibility(View.GONE);
            binding.textView15.setVisibility(View.GONE);
            binding.btnRecharge.setVisibility(View.VISIBLE);
        }

         */
    }

    public void btnClick() {
        mFirebaseAnalytics.logEvent(getActivity().getResources().getString(R.string.Card_Recharge_Pay_Clicked), new Bundle());
        if (binding.editText2.getText().toString().length() > 0) {

            int amount = Integer.parseInt(binding.editText2.getText().toString());
            if (!binding.editText2.getText().toString().contains(".")) {
                if (amount > 0) {

                    if (amount <= 10000) {
                        //XPay
                        Intent i4 = new Intent(getActivity(), PaymentXPayWebview.class);
                        Bundle b4 = new Bundle();
                        b4.putString("trans_category", category);
                        b4.putString("amount", String.valueOf(amount));
                        i4.putExtras(b4);
                        startActivity(i4);
                    } else {
                        binding.editText2.setError(getActivity().getResources().getString(R.string.valid_amount_lessthan));
                    }

                } else {
                    binding.editText2.setError(getActivity().getResources().getString(R.string.valid_amount));

                    //Toast.makeText(getActivity(), "Enter a valid amount!", Toast.LENGTH_SHORT).show();
                }
            } else {
                binding.editText2.setError(getActivity().getResources().getString(R.string.valid_amount));
            }

        } else {
//            Toast.makeText(getActivity(), "Enter a valid amount!", Toast.LENGTH_SHORT).show();
            binding.editText2.setError(getActivity().getResources().getString(R.string.valid_amount));
        }


    }

    public void btnCharges() {
        mFirebaseAnalytics.logEvent(getActivity().getResources().getString(R.string.Card_Recharge_Transaction_Charges_Clicked), new Bundle());
        Intent i4 = new Intent(getActivity(), PaymentChargesWebview.class);
        Bundle b4 = new Bundle();
        b4.putString("trans_category", category);
        i4.putExtras(b4);
        startActivity(i4);

    }


    @Override
    public void onStart() {
        super.onStart();

        //System.out.println("PaymentsRechargeFragment OnSTART : "+i);
        i++;


    }
}
