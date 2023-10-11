package com.chillarcards.campuswallet.payments;


import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ExpandableListView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.chillarcards.campuswallet.R;
//import com.gocashfree.cashfreesdk.CFPaymentService;
//import com.gocashfree.cashfreesdk.ui.gpay.GooglePayStatusListener;
import com.chillarcards.campuswallet.databinding.ActivityOutletMenuBinding;
import com.chillarcards.campuswallet.databinding.ActivityOutletMenuItemBinding;
import com.chillarcards.campuswallet.databinding.LayoutOtherPaymentsFragmentBinding;
import com.google.firebase.analytics.FirebaseAnalytics;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import com.chillarcards.campuswallet.networkmodels.otherpaymentmodes.Code;
import com.chillarcards.campuswallet.networkmodels.otherpaymentmodes.OtherPayment;
import com.chillarcards.campuswallet.networkmodels.otherpaymentmodes.PaymentItem;
import com.chillarcards.campuswallet.networkmodels.otherpaymentmodes.Status;
import com.chillarcards.campuswallet.networkmodels.otherpaymentmodes.SubItem;
import com.chillarcards.campuswallet.networkmodels.paymentmodes.PaymentMode;
import com.chillarcards.campuswallet.utils.ClickListener;
import com.chillarcards.campuswallet.utils.RecyclerTouchListener;

public class PaymentsOtherFragment extends Fragment implements CompletedListener {


    PaymentModesAdapter mAdapter;
    String amount = "";
    String itemId = "";
    String category = "";
    String paymentGateWayName = "";
    FirebaseAnalytics mFirebaseAnalytics;

    HashMap<String, List<SubItem>> expandableListDetail = new HashMap<String, List<SubItem>>();
    List<String> expandableListTitle;
    String jsonObject;

    public PaymentsOtherFragment() {
    }
    private LayoutOtherPaymentsFragmentBinding binding;

    Button ReloadBTN,GoBackBTN;
    TextView ErrorMessageTV,CodeErrorTV,NodataTV;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        binding = LayoutOtherPaymentsFragmentBinding.inflate(inflater, container, false);

        ReloadBTN = binding.getRoot().findViewById(R.id.ReloadBTN);
        ErrorMessageTV=binding.getRoot().findViewById(R.id.ErrorMessageTV);
        CodeErrorTV=binding.getRoot().findViewById(R.id.CodeErrorTV);
        GoBackBTN =binding.getRoot().findViewById(R.id.GoBackBTN);
        NodataTV =binding.getRoot().findViewById(R.id.NodataTV);
        
        LinearLayoutManager mLayoutManager = new LinearLayoutManager(this.getActivity().getBaseContext());
        binding.recyclerView3.setLayoutManager(mLayoutManager);


        callothers();
        mFirebaseAnalytics = FirebaseAnalytics.getInstance(getActivity());
        
        binding.btnRecharge.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                payment();
            }
        });
        binding.textView5.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                btnCharges();
            }
        });
        
        return binding.getRoot();
    }


    public void payment() {

        mFirebaseAnalytics.logEvent(getActivity().getResources().getString(R.string.Card_Recharge_Credit_Debit_Card_Clicked), new Bundle());
        if (!amount.equals("") || !category.equals("")) {
            setFirebasePayButtonClickIdentifier(category);
            Intent i4 = new Intent(getActivity(), PaymentXPayWebview.class);
            i4.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            i4.addFlags(Intent.FLAG_ACTIVITY_BROUGHT_TO_FRONT);
            Bundle b4 = new Bundle();
            b4.putString("trans_category", category);
            b4.putString("amount", String.valueOf(amount));
            b4.putString("paymentItemId", itemId);
            i4.putExtras(b4);
            startActivity(i4);
        } else {
            Toast.makeText(getActivity(), getActivity().getResources().getString(R.string.select_item_to_pay), Toast.LENGTH_SHORT).show();
        }
    }

    public void btnCharges() {

        Intent i4 = new Intent(getActivity(), PaymentChargesWebview.class);
        Bundle b4 = new Bundle();
        b4.putString("trans_category", category);
        i4.putExtras(b4);
        startActivity(i4);

    }

    private void callothers() {
        {

            Bundle arguments = getArguments();
            String paymentModesStr = arguments.getString("paymentModes");
            String paymentItemsStr = arguments.getString("paymentItems");
            jsonObject = arguments.getString("jsonObject");
            //System.out.println("Items Payments : paymentItemsStr : "+paymentItemsStr); //[{"amount":"100","id":"54","item":"Term 1"}]
            category = arguments.getString("category");
            paymentGateWayName = arguments.getString("paymentGateWayName");
            //Work around
            if (paymentItemsStr.equalsIgnoreCase("[]")) {
                paymentItemsStr = "[{\"amount\":\"0\",\"id\":\"0\",\"item\":\"" + getActivity().getResources().getString(R.string.select_op_hd) + "\"}]";
            } else {
                paymentItemsStr = "[{\"amount\":\"0\",\"id\":\"0\",\"item\":\"" + getActivity().getResources().getString(R.string.select_op_hd) + "\"}," + paymentItemsStr.substring(1);
            }

            ArrayList<PaymentMode> modePaymentsNew = new ArrayList<PaymentMode>();
            ArrayList<PaymentItem> itemPaymentsNew = new ArrayList<PaymentItem>();
            Gson gson = new Gson();
            modePaymentsNew = gson.fromJson(paymentModesStr, new TypeToken<List<PaymentMode>>() {
            }.getType());
            itemPaymentsNew.clear();
//        itemPaymentsNew.add(0,new PaymentItem("0","0",getActivity().getResources().getString(R.string.select_term)));
            itemPaymentsNew = gson.fromJson(paymentItemsStr, new TypeToken<List<PaymentItem>>() {
            }.getType());


            if (itemPaymentsNew.size() > 1) {
                binding.spinner.setVisibility(View.VISIBLE);
//            for (int a=0; a<itemPaymentsNew.size(); a++) {
//                //System.out.println("Items Payments :" + itemPaymentsNew.get(a));
//            }

                ArrayAdapter<PaymentItem> adapter =
                        new ArrayAdapter<PaymentItem>(getActivity(), R.layout.item_spinner_white, itemPaymentsNew);
                adapter.setDropDownViewResource(R.layout.item_spinner);

                binding.spinner.setAdapter(adapter);

                final ArrayList<PaymentItem> finalItemPaymentsNew = itemPaymentsNew;
                binding.spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {

                    @Override
                    public void onItemSelected(AdapterView<?> parent,
                                               View view, int pos, long id) {
                        String cardStatusString = parent.getItemAtPosition(pos).toString();
                        itemId = finalItemPaymentsNew.get(pos).getId();
                        amount = finalItemPaymentsNew.get(pos).getAmount();

                        binding.editText2.setText(getActivity().getResources().getString(R.string.indian_rupee_symbol) + amount);
                    }

                    @Override
                    public void onNothingSelected(AdapterView<?> arg0) {
                        // TODO Auto-generated method stub

                    }
                });

            } else {
                binding.spinner.setVisibility(View.GONE);
                binding.editText2.setText(getActivity().getResources().getString(R.string.no_pending_payments));
                binding.editText2.setTextSize(14);
//            Toast.makeText(getActivity(),"No pending payments",Toast.LENGTH_SHORT);
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

                        if (!amount.equals("")) {
                            float amount1 = Float.parseFloat(amount);
                            if (amount1 > 0) {

                                if (expandableListDetail.get(expandableListTitle.get(groupPosition)).size() == 0) {


                                    switch (expandableListTitle.get(groupPosition)) {

                                        case "CC":
                                        case "DC":
                                            // TODO: 24-06-2022 hidden to get rid of cashfree modules to check if cashfree causing intent redirection policy violation in playstore
//                                            mFirebaseAnalytics.logEvent(getActivity().getResources().getString(R.string.Card_Recharge_Credit_Debit_Card_Clicked), new Bundle());
//                                            Intent i0 = new Intent(getActivity(), PaymentsCashfreeActivity.class);
//                                            Bundle b0 = new Bundle();
//                                            b0.putString("paymentMode", expandableListTitle.get(groupPosition));
//                                            b0.putString("amount", amount);
//                                            b0.putString("trans_category", category);
//                                            b0.putString("paymentGateway", paymentGateWayName);
//                                            b0.putString("paymentItemId", itemId);
//                                            i0.putExtras(b0);
//                                            startActivity(i0);
                                            break;

//                            case "Netbanking":
//                                mFirebaseAnalytics.logEvent(getActivity().getResources().getString(R.string.Card_Recharge_Netbanking_Clicked), new Bundle());
//                                Intent i1 = new Intent(getActivity(), PaymentsCashfreeActivity.class);
//                                Bundle b1 = new Bundle();
//                                b1.putString("paymentMode", expandableListTitle.get(groupPosition));
//                                b1.putString("amount", et_amount.getText().toString());
//                                b1.putString("trans_category", category);
//                                b1.putString("paymentGateway", paymentGateWayName);
//                                i1.putExtras(b1);
//                                startActivity(i1);
//                                break;

//
//                                default:
//                                    Intent i00 = new Intent(getActivity(), PaymentCreateActivity.class);
//                                    Bundle b00 = new Bundle();
//                                    b00.putString("paymentMode", expandableListTitle.get(groupPosition));
//                                    b00.putString("amount",amount);
//                                    b00.putString("trans_category", category);
//                                    b00.putString("paymentGateway", paymentGateWayName);
//                                    b00.putString("paymentItemId", itemId);
//                                    i00.putExtras(b00);
//                                    startActivity(i00);
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

                        if (!amount.equals("")) {
                            float amount1 = Float.parseFloat(amount);
                            if (amount1 > 0) {


                                switch (expandableListTitle.get(groupPosition)) {

                                    case "UPI": {
                                        // TODO: 24-06-2022 hidden to get rid of cashfree modules to check if cashfree causing intent redirection policy violation in playstore
//                                        if (expandableListDetail.get(expandableListTitle.get(groupPosition)).get(childPosition).getItemName().equalsIgnoreCase("GooglePay")) {
//
//                                            CFPaymentService.getCFPaymentServiceInstance().isGPayReadyForPayment(getActivity(), new GooglePayStatusListener() {
//                                                @Override
//                                                public void isReady() {
//                                                    Intent i00 = new Intent(getActivity(), PaymentCreateActivity.class);
//                                                    Bundle b00 = new Bundle();
//                                                    b00.putString("paymentMode", "UPI");
//                                                    b00.putString("amount", amount);
//                                                    b00.putString("trans_category", category);
//                                                    b00.putString("paymentGateway", paymentGateWayName);
//                                                    b00.putString("paymentItemId", itemId);
//                                                    i00.putExtras(b00);
//                                                    startActivity(i00);
//                                                }
//
//                                                @Override
//                                                public void isNotReady() {
//
//                                                    Toast.makeText(getActivity(), "Sorry,Google Pay not found in your phone", Toast.LENGTH_SHORT).show();
//                                                }
//                                            });
//
//                                        } else if (expandableListDetail.get(expandableListTitle.get(groupPosition)).get(childPosition).getItemName().equalsIgnoreCase("PhonePe")) {
//                                            if (CFPaymentService.getCFPaymentServiceInstance().doesPhonePeExist(getActivity(), "PROD")) {
//                                                Intent i00 = new Intent(getActivity(), PaymentCreateActivity.class);
//                                                Bundle b00 = new Bundle();
//                                                b00.putString("paymentMode", "UPI");
//                                                b00.putString("amount", amount);
//                                                b00.putString("trans_category", category);
//                                                b00.putString("paymentGateway", paymentGateWayName);
//                                                b00.putString("paymentItemId", itemId);
//                                                i00.putExtras(b00);
//                                                startActivity(i00);
//
//                                            } else {
//                                                Toast.makeText(getActivity(), "Sorry,PhonePe not found in your phone", Toast.LENGTH_SHORT).show();
//
//                                            }
//
//
//                                        } else
                                        if (expandableListDetail.get(expandableListTitle.get(groupPosition)).get(childPosition).getItemName().equalsIgnoreCase("AmazonPay")) {
                                            Intent i00 = new Intent(getActivity(), PaymentCreateActivity.class);
                                            Bundle b00 = new Bundle();
                                            b00.putString("paymentMode", "UPI");
                                            b00.putString("amount", amount);
                                            b00.putString("trans_category", category);
                                            b00.putString("paymentGateway", paymentGateWayName);
                                            b00.putString("paymentItemId", itemId);
                                            i00.putExtras(b00);
                                            startActivity(i00);

                                        }
                                        // TODO: 24-06-2022 hidden to get rid of cashfree modules to check if cashfree causing intent redirection policy violation in playstore
//                                        else if (expandableListDetail.get(expandableListTitle.get(groupPosition)).get(childPosition).getItemName().equalsIgnoreCase("Others")) {
//                                            Intent i = new Intent(getActivity(), PaymentsCashfreeActivity.class);
//                                            Bundle b = new Bundle();
//                                            b.putString("paymentMode", "UPI");
//                                            b.putString("amount", amount);
//                                            b.putString("trans_category", category);
//                                            b.putString("paymentGateway", paymentGateWayName);
//                                            b.putString("paymentItemId", itemId);
//                                            i.putExtras(b);
//                                            startActivity(i);
//
//                                        }
                                        break;
                                    }

                                    case "Wallet":
                                        Intent i00 = new Intent(getActivity(), PaymentCreateActivity.class);
                                        Bundle b00 = new Bundle();
                                        b00.putString("paymentMode", "Wallet");
                                        b00.putString("amount", amount);
                                        b00.putString("trans_category", category);
                                        b00.putString("paymentGateway", paymentGateWayName);
                                        b00.putString("paymentItemId", itemId);
                                        b00.putString("extra", expandableListDetail.get(expandableListTitle.get(groupPosition)).get(childPosition).getItemCode());
                                        i00.putExtras(b00);
                                        startActivity(i00);
                                        break;
                                    case "Netbanking":
                                        Intent i = new Intent(getActivity(), PaymentCreateActivity.class);
                                        Bundle b = new Bundle();
                                        b.putString("paymentMode", "Netbanking");
                                        b.putString("amount", amount);
                                        b.putString("trans_category", category);
                                        b.putString("paymentGateway", paymentGateWayName);
                                        b.putString("paymentItemId", itemId);
                                        b.putString("extra", expandableListDetail.get(expandableListTitle.get(groupPosition)).get(childPosition).getItemCode());
                                        i.putExtras(b);
                                        startActivity(i);

                                        break;

                                }


                            }
                        }
                        return false;
                    }

                });

            } else {

                if (modePaymentsNew.size() > 0) {
                    binding.recyclerView3.setVisibility(View.VISIBLE);
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

                            if (!amount.equals("")) {
                                float amount1 = Float.parseFloat(amount);
                                if (amount1 > 0) {
                                    setFirebaseClickIdentifier(category, finalModePaymentsNew.get(position).getName());


                                    if (paymentGateWayName.equals("cashfree")) {

                                        Intent i = new Intent(getActivity(), PaymentCreateActivity.class);
                                        Bundle b = new Bundle();
                                        b.putString("paymentMode", finalModePaymentsNew.get(position).getName());
                                        b.putString("amount", String.valueOf(amount1));
                                        b.putString("trans_category", category);
                                        b.putString("paymentGateway", paymentGateWayName);
                                        b.putString("paymentItemId", itemId);
                                        i.putExtras(b);
                                        startActivity(i);


                                    } else {
                                        switch (finalModePaymentsNew.get(position).getName()) {

                                            case "CC/DC":
                                                Intent i = new Intent(getActivity(), PaymentCreateActivity.class);
                                                Bundle b = new Bundle();
                                                b.putString("paymentMode", "card");
                                                b.putString("amount", String.valueOf(amount1));
                                                b.putString("trans_category", category);
                                                b.putString("paymentGateway", "razorpay");
                                                b.putString("paymentItemId", itemId);
                                                i.putExtras(b);
                                                startActivity(i);
                                                break;

                                            case "Xpay":
                                                Intent i4 = new Intent(getActivity(), PaymentXPayWebview.class);
                                                i4.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                                                i4.addFlags(Intent.FLAG_ACTIVITY_BROUGHT_TO_FRONT);
                                                Bundle b4 = new Bundle();
                                                b4.putString("trans_category", category);
                                                b4.putString("amount", String.valueOf(amount1));
                                                b4.putString("paymentItemId", itemId);
                                                i4.putExtras(b4);
                                                startActivity(i4);
                                                break;
                                            case "Netbanking":
                                                Intent i1 = new Intent(getActivity(), PaymentCreateActivity.class);
                                                Bundle b1 = new Bundle();
                                                b1.putString("paymentMode", "netbanking");
                                                b1.putString("amount", String.valueOf(amount1));
                                                b1.putString("trans_category", category);
                                                b1.putString("paymentGateway", "razorpay");
                                                b1.putString("paymentItemId", itemId);
                                                i1.putExtras(b1);
                                                startActivity(i1);
                                                break;

                                            case "UPI":
                                                Intent i2 = new Intent(getActivity(), PaymentCreateActivity.class);
                                                Bundle b2 = new Bundle();
                                                b2.putString("paymentMode", "upi");
                                                b2.putString("amount", String.valueOf(amount1));
                                                b2.putString("trans_category", category);
                                                b2.putString("paymentItemId", itemId);
                                                b2.putString("paymentGateway", "razorpay");
                                                i2.putExtras(b2);
                                                startActivity(i2);
                                                break;

                                        }
                                    }
                                } else {
                                    Toast.makeText(getActivity(), getActivity().getResources().getString(R.string.valid_payment), Toast.LENGTH_SHORT).show();
                                }

                            } else {
                                Toast.makeText(getActivity(), getActivity().getResources().getString(R.string.valid_payment), Toast.LENGTH_SHORT).show();
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


         /*   if(modePaymentsNew.size()>0) {

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

                        if (!amount.equals("")) {
                            float amount1 = Float.parseFloat(amount);
                            if (amount1 > 0) {
                                setFirebaseClickIdentifier(category, finalModePaymentsNew.get(position).getName());


                                if (paymentGateWayName.equals("cashfree")) {

                                    Intent i = new Intent(getActivity(), PaymentCreateActivity.class);
                                    Bundle b = new Bundle();
                                    b.putString("paymentMode", finalModePaymentsNew.get(position).getName());
                                    b.putString("amount", String.valueOf(amount1));
                                    b.putString("trans_category", category);
                                    b.putString("paymentGateway", paymentGateWayName);
                                    b.putString("paymentItemId", itemId);
                                    i.putExtras(b);
                                    startActivity(i);


                                } else {
                                    switch (finalModePaymentsNew.get(position).getName()) {

                                        case "CC/DC":
                                            Intent i = new Intent(getActivity(), PaymentCreateActivity.class);
                                            Bundle b = new Bundle();
                                            b.putString("paymentMode", "card");
                                            b.putString("amount", String.valueOf(amount1));
                                            b.putString("trans_category", category);
                                            b.putString("paymentGateway", "razorpay");
                                            b.putString("paymentItemId", itemId);
                                            i.putExtras(b);
                                            startActivity(i);
                                            break;

                                        case "Xpay":
                                            Intent i4 = new Intent(getActivity(), PaymentXPayWebview.class);
                                            i4.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                                            i4.addFlags(Intent.FLAG_ACTIVITY_BROUGHT_TO_FRONT);
                                            Bundle b4 = new Bundle();
                                            b4.putString("trans_category", category);
                                            b4.putString("amount", String.valueOf(amount1));
                                            b4.putString("paymentItemId", itemId);
                                            i4.putExtras(b4);
                                            startActivity(i4);
                                            break;
                                        case "Netbanking":
                                            Intent i1 = new Intent(getActivity(), PaymentCreateActivity.class);
                                            Bundle b1 = new Bundle();
                                            b1.putString("paymentMode", "netbanking");
                                            b1.putString("amount", String.valueOf(amount1));
                                            b1.putString("trans_category", category);
                                            b1.putString("paymentGateway", "razorpay");
                                            b1.putString("paymentItemId", itemId);
                                            i1.putExtras(b1);
                                            startActivity(i1);
                                            break;

                                        case "UPI":
                                            Intent i2 = new Intent(getActivity(), PaymentCreateActivity.class);
                                            Bundle b2 = new Bundle();
                                            b2.putString("paymentMode", "upi");
                                            b2.putString("amount", String.valueOf(amount1));
                                            b2.putString("trans_category", category);
                                            b2.putString("paymentItemId", itemId);
                                            b2.putString("paymentGateway", "razorpay");
                                            i2.putExtras(b2);
                                            startActivity(i2);
                                            break;

                                    }
                                }
                            } else{
                                Toast.makeText(getActivity(), getActivity().getResources().getString(R.string.valid_payment), Toast.LENGTH_SHORT).show();
                            }

                        } else {
                            Toast.makeText(getActivity(), getActivity().getResources().getString(R.string.valid_payment), Toast.LENGTH_SHORT).show();
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
            }*/
        }
    }

    public void setFirebasePayButtonClickIdentifier(String category) {
        switch (category) {
            case "2":
                mFirebaseAnalytics.logEvent(getActivity().getResources().getString(R.string.Card_Recharge_Pay_Clicked), new Bundle());
                break;
            case "3":
                mFirebaseAnalytics.logEvent(getActivity().getResources().getString(R.string.Fee_Payment_Pay_Clicked), new Bundle());
                break;
            case "4":
                mFirebaseAnalytics.logEvent(getActivity().getResources().getString(R.string.Miscellaneous_Payment_Pay_Clicked), new Bundle());
                break;
            case "5":
                mFirebaseAnalytics.logEvent(getActivity().getResources().getString(R.string.Trust_Payment_Pay_Clicked), new Bundle());
                break;
        }
    }

    public void setFirebaseClickIdentifier(String catName, String modName) {
//        String headerName="";
        switch (catName) {
            case "2":
                switch (modName) {
                    case "CC/DC":
                        mFirebaseAnalytics.logEvent(getActivity().getResources().getString(R.string.Card_Recharge_Credit_Debit_Card_Clicked), new Bundle());
                        break;

                    case "Xpay":
                        mFirebaseAnalytics.logEvent(getActivity().getResources().getString(R.string.Card_Recharge_Xpay_Clicked), new Bundle());
                        break;
                    case "Netbanking":
                        mFirebaseAnalytics.logEvent(getActivity().getResources().getString(R.string.Card_Recharge_Netbanking_Clicked), new Bundle());
                        break;

                    case "UPI":
                        mFirebaseAnalytics.logEvent(getActivity().getResources().getString(R.string.Card_Recharge_Upi_Clicked), new Bundle());
                        break;
                }
                break;
            case "3":
                switch (modName) {
                    case "CC/DC":
                        mFirebaseAnalytics.logEvent(getActivity().getResources().getString(R.string.Fee_Payment_Credit_Debit_Card_Clicked), new Bundle());
                        break;

                    case "Xpay":
                        mFirebaseAnalytics.logEvent(getActivity().getResources().getString(R.string.Fee_Payment_Xpay_Clicked), new Bundle());
                        break;
                    case "Netbanking":
                        mFirebaseAnalytics.logEvent(getActivity().getResources().getString(R.string.Fee_Payment_Netbanking_Clicked), new Bundle());
                        break;

                    case "UPI":
                        mFirebaseAnalytics.logEvent(getActivity().getResources().getString(R.string.Fee_Payment_Upi_Clicked), new Bundle());
                        break;
                }
                break;
            case "4":
                switch (modName) {
                    case "CC/DC":
                        mFirebaseAnalytics.logEvent(getActivity().getResources().getString(R.string.Miscellaneous_Payment_Credit_Debit_Card_Clicked), new Bundle());
                        break;

                    case "Xpay":
                        mFirebaseAnalytics.logEvent(getActivity().getResources().getString(R.string.Miscellaneous_Payment_Xpay_Clicked), new Bundle());
                        break;
                    case "Netbanking":
                        mFirebaseAnalytics.logEvent(getActivity().getResources().getString(R.string.Miscellaneous_Payment_Netbanking_Clicked), new Bundle());
                        break;

                    case "UPI":
                        mFirebaseAnalytics.logEvent(getActivity().getResources().getString(R.string.Miscellaneous_Payment_Upi_Clicked), new Bundle());
                        break;
                }
                break;
            case "5":
                switch (modName) {
                    case "CC/DC":
                        mFirebaseAnalytics.logEvent(getActivity().getResources().getString(R.string.Trust_Payment_Credit_Debit_Card_Clicked), new Bundle());
                        break;

                    case "Xpay":
                        mFirebaseAnalytics.logEvent(getActivity().getResources().getString(R.string.Trust_Payment_Xpay_Clicked), new Bundle());
                        break;
                    case "Netbanking":
                        mFirebaseAnalytics.logEvent(getActivity().getResources().getString(R.string.Trust_Payment_Netbanking_Clicked), new Bundle());
                        break;

                    case "UPI":
                        mFirebaseAnalytics.logEvent(getActivity().getResources().getString(R.string.Trust_Payment_Upi_Clicked), new Bundle());
                        break;
                }
                break;


        }
//        return headerName;
    }

    @Override
    public void onStart() {
        super.onStart();
    }

    @Override
    public void onCompleted(int position, String paymentID) {

    }
}
