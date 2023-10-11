package com.chillarcards.campuswallet.payments.Preorder;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import androidx.recyclerview.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.chillarcards.campuswallet.R;

import java.util.ArrayList;
import java.util.List;

@SuppressWarnings("ALL")
public class PreOrderHistoryDetalsAdapter extends RecyclerView.Adapter<PreOrderHistoryDetalsAdapter.ViewHolder> {

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

    private int rowLayout;
    private Context mContext;
    private Activity activity;
    ProgressDialog progressDialog;
    String PINN;
    int pos;
    String parameters;

    public PreOrderHistoryDetalsAdapter
  (List<String> preorderSalesItemID, List<String> tansactionBillNo, List<String> preorderItemTypeTimingItemID,List<String> itemID,
   List<String> itemCode,List<String> itemName,List<String> itemShortName,List<String> preorderSalesItemQuantity,
   List<String> preorderSalesItemAmount,
   List<String> itemTypeName,
   List<String> itemTypeID,
   List<String> preorderItemSaleTransactionVendorID, Activity activity, int rowLayout,
    Context context) {
        this.preorderSalesItemID = preorderSalesItemID;
        this.tansactionBillNo = tansactionBillNo;
        this.preorderItemTypeTimingItemID = preorderItemTypeTimingItemID;
        this.itemID = itemID;
        this.itemCode = itemCode;
        this.itemName = itemName;
        this.itemShortName = itemShortName;
        this.preorderSalesItemQuantity = preorderSalesItemQuantity;
        this.preorderSalesItemAmount = preorderSalesItemAmount;
        this.itemTypeName = itemTypeName;
        this.itemTypeID = itemTypeID;
        this.preorderItemSaleTransactionVendorID = preorderItemSaleTransactionVendorID;


        this.activity = activity;
        this.rowLayout = rowLayout;
        this.mContext = context;

        progressDialog = new ProgressDialog(activity);

    }

    @Override
    public PreOrderHistoryDetalsAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(rowLayout, parent, false);
        return new PreOrderHistoryDetalsAdapter.ViewHolder(v);


    }

    @Override
    public void onBindViewHolder(PreOrderHistoryDetalsAdapter.ViewHolder holder, final int position) {

        if (itemName.get(position).length()>9){
            holder.Name.setTextSize(12);
        }else {

        }
        holder.Name.setText(""+itemName.get(position));
        holder.Outletname.setText(""+itemTypeName.get(position));
        holder.QTY.setText(""+preorderSalesItemQuantity.get(position));
        holder.Amount.setText(""+preorderSalesItemAmount.get(position));

    }

    @Override
    public int getItemCount() {
        return tansactionBillNo.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {

        TextView QTY,Name,Amount,Date,Outletname;
        ImageView StatusImg;
        RelativeLayout Card;


        public ViewHolder(View itemView) {
            super(itemView);


            QTY =(TextView)itemView.findViewById(R.id.qtyid);
            Outletname =(TextView)itemView.findViewById(R.id.outletnameid);
            Name=(TextView)itemView.findViewById(R.id.itemnameid);
            Amount=(TextView)itemView.findViewById(R.id.totalid);
            Card=(RelativeLayout) itemView.findViewById(R.id.cardid1);


        }
    }

}
