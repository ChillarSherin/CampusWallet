package com.chillarcards.campuswallet.payments.Preorder;

import android.app.Activity;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.os.Bundle;
import androidx.recyclerview.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.chillarcards.campuswallet.R;
import com.google.firebase.analytics.FirebaseAnalytics;

import java.util.ArrayList;
import java.util.List;

import com.chillarcards.campuswallet.application.Constants;

@SuppressWarnings("ALL")
public class PreOrderCatListAdapter extends RecyclerView.Adapter<PreOrderCatListAdapter.ViewHolder> {

    private  List<String> schoolID = new ArrayList<String>();
    private  List<String> categoryID = new ArrayList<String>();
    private  List<String> itemID = new ArrayList<String>();
    private  List<String> itemCode = new ArrayList<String>();
    private  List<String> itemName = new ArrayList<String>();
    private  List<String> itemShortName = new ArrayList<String>();
    private  List<String> itemPrice = new ArrayList<String>();
    private  List<String> preorderItemTypeTimingID = new ArrayList<>();
    private  List<String> categoryName = new ArrayList<>();
    private  List<String> categoryShortName = new ArrayList<>();
    private  List<String> preorderItemTypeTimingItemID = new ArrayList<>();

    PreOrderItemsList_Activity preorderitems;

    private  int rowLayout;
    private static Context mContext;
    private Activity activity;
    private ProgressDialog progressDialog;
    String PINN;
    int pos;
    String parameters;
    FirebaseAnalytics mFirebaseAnalytics;

    public PreOrderCatListAdapter(List<String> schoolID, List<String> categoryID,
                                  List<String> itemID, List<String> itemCode, List<String> itemShortName, List<String> itemName,
                                  List<String> itemPrice, List<String> categoryName, List<String> categoryShortName, List<String> preorderItemTypeTimingItemID, List<String> preorderItemTypeTimingID,
                                  PreOrderItemsList_Activity preorderitems, Activity activity, Context context, FirebaseAnalytics mFirebaseAnalytics) {
        this.schoolID = schoolID;
        this.categoryID = categoryID;
        this.itemID = itemID;
        this.itemCode = itemCode;
        this.itemPrice = itemPrice;
        this.itemName = itemName;
        this.itemShortName = itemShortName;
        this.categoryName = categoryName;
        this.categoryShortName = categoryShortName;
        this.preorderItemTypeTimingItemID = preorderItemTypeTimingItemID;
        this.preorderItemTypeTimingID = preorderItemTypeTimingID;
        this.preorderitems = preorderitems;
        this.mFirebaseAnalytics=mFirebaseAnalytics;
        this.activity = activity;
        this.rowLayout = R.layout.preordercatlist_item_layout;
        this.mContext = context;

        progressDialog = new ProgressDialog(activity);

    }




    @Override
    public PreOrderCatListAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(rowLayout, parent, false);
        return new PreOrderCatListAdapter.ViewHolder(v);


    }

    @Override
    public void onBindViewHolder(final PreOrderCatListAdapter.ViewHolder holder, final int position) {


        holder.ItemName.setText(""+itemName.get(position));
        holder.Price.setText(""+itemPrice.get(position));
        //System.out.println("SHANIL TEST 1 : "+itemName.get(position));

    }

    @Override
    public int getItemCount() {
        return schoolID.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        RelativeLayout carditem;
        TextView ItemName,Price;


        public ViewHolder(View itemView) {
            super(itemView);
            carditem=(RelativeLayout)itemView.findViewById(R.id.cadviewid);
            ItemName=(TextView)itemView.findViewById(R.id.itemnameid);
            Price=(TextView)itemView.findViewById(R.id.priceid);

            carditem.setOnClickListener(this);


        }

        @Override
        public void onClick(View v) {
            if (v.equals(carditem)){
                mFirebaseAnalytics.logEvent(mContext.getResources().getString(R.string.Pre_Order_Outlet_Item_clicked),new Bundle());
                final Dialog dlg = new Dialog(v.getContext());
                dlg.requestWindowFeature(Window.FEATURE_NO_TITLE);
                dlg.setContentView(R.layout.addcount);
                dlg.getWindow().setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
                dlg.setCancelable(true);
                dlg.setCanceledOnTouchOutside(true);
                dlg.show();

                final EditText editText = (EditText) dlg.findViewById(R.id.countedt);
                final Button button = (Button) dlg.findViewById(R.id.addcart);
                final ImageView closebutton = (ImageView) dlg.findViewById(R.id.drawer_indicator);
                final TextView itemname = (TextView) dlg.findViewById(R.id.itemname);
                final TextView itemprice = (TextView) dlg.findViewById(R.id.itempriceid);

                itemprice.setText(""+itemPrice.get(getAdapterPosition())+" "+mContext.getResources().getString(R.string.Rs));
                itemname.setText(""+itemName.get(getAdapterPosition()));

                closebutton.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        mFirebaseAnalytics.logEvent(mContext.getResources().getString(R.string.Pre_Order_Add_Count_Close_Button_clicked),new Bundle());
                        dlg.dismiss();


                    }
                });

                button.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        mFirebaseAnalytics.logEvent(mContext.getResources().getString(R.string.Pre_Order_Add_To_Cart_Button_clicked),new Bundle());
                        int qnty = 0;
                        if (String.valueOf(editText.getText()).equals("") || String.valueOf(editText.getText()).equals("0")) {
//                        Constants.Qty = 1;

                            Toast.makeText(mContext, mContext.getResources().getString(R.string.valid_quantity), Toast.LENGTH_SHORT).show();

                        } else {
                            Constants.Qty = Integer.parseInt(String.valueOf(editText.getText()));

                            String id =preorderItemTypeTimingItemID.get(getAdapterPosition());
                            String nameItem = String.valueOf(itemName.get(getAdapterPosition()));

                            //System.out.println("CHILLER:ITID:" + id);


                            //System.out.println("ijaz   qty   "+ Constants.Qty);
                            //System.out.println("ijaz   id   "+id);

                            // iff add same item in many times it will increase the quantity only
                            int flag=0;
                            for (int i = 0; i < Constants.sales_items.size(); i++) {

                                if (Constants.sales_items.get(i).getPreorderItemTypeTimingItemIDs().equals(id)) {

                                    try {
                                        qnty = Integer.parseInt(Constants.sales_items.get(i).getitem_quantity()) + Integer.parseInt(String.valueOf(editText.getText()));
                                        //System.out.println("ELDHOS:QNTY :" + qnty);
//                            Constants.Qty=qnty;
                                        //System.out.println("ELDHOS:QNTY FINAL:" + Constants.Qty);

                                    } catch (NumberFormatException e) {
                                        qnty = Integer.parseInt(Constants.sales_items.get(i).getitem_quantity()) + 1;
                                        //System.out.println("ELDHOS:QNTY :" + qnty);
//                            Constants.Qty=qnty;
                                        //System.out.println("ELDHOS:QNTY FINAL:" + Constants.Qty);
                                    }
                                    flag = 1;
                                    Constants.sales_items.get(i).setitem_quantity(String.valueOf(qnty));
                                    break;

                                }

                            }

                            if (flag == 0) {


                                //System.out.println("ELDHOS:QNTY FINAL:" + Constants.Qty);


                                String pric = itemPrice.get(getAdapterPosition());

                                //System.out.println("ijaz   name   nameItem "+nameItem);


                                Constants.sales_items.add(new Sales_Item(nameItem, id, String.valueOf(Constants.Qty), pric));

                                //System.out.println("Constants.sales_items:"+ Constants.sales_items.size());

                            }
//                            if (Constants.sales_items.size()>0) {
//                                preorderitems.CountTxt.setVisibility(View.GONE);
//                            }else {
                                preorderitems.CountTxt.setVisibility(View.VISIBLE);
//                            }

                            preorderitems.CountTxt.setText(""+ Constants.sales_items.size());


                            dlg.dismiss();

//                            Intent in=new Intent(activity,PreOrderItemsList_Activity.class);
//                            in.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
//                            Bundle b=new Bundle();
//                            b.putString("preorderItemTypeTimingID",preorderItemTypeTimingID.get(pos));
//                            in.putExtras(b);
//                            activity.startActivity(in);
//                            activity.finish();

                        }
                    }
                });

            }
        }
    }

}
