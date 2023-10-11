
package com.chillarcards.campuswallet.networkmodels.PreOrderHistory;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class PreOrderHistoryCode {

    @SerializedName("preorderItemSaleTransactionID")
    @Expose
    private String preorderItemSaleTransactionID;
    @SerializedName("tansactionBillNo")
    @Expose
    private String tansactionBillNo;
    @SerializedName("preorderTimingID")
    @Expose
    private String preorderTimingID;
    @SerializedName("preorderTimingName")
    @Expose
    private String preorderTimingName;
    @SerializedName("preorderItemSaleTransactionTotalAmount")
    @Expose
    private String preorderItemSaleTransactionTotalAmount;
    @SerializedName("preorderItemSaleTransactionOrderTime")
    @Expose
    private String preorderItemSaleTransactionOrderTime;

    /**
     * No args constructor for use in serialization
     * 
     */
    public PreOrderHistoryCode() {
    }

    /**
     * 
     * @param preorderItemSaleTransactionID **preorderItemSaleTransactionID**
     * @param preorderItemSaleTransactionOrderTime **preorderItemSaleTransactionOrderTime**
     * @param preorderItemSaleTransactionTotalAmount **preorderItemSaleTransactionTotalAmount**
     * @param preorderTimingName **preorderTimingName**
     * @param preorderTimingID **preorderTimingID**
     * @param tansactionBillNo **tansactionBillNo**
     */
    public PreOrderHistoryCode(String preorderItemSaleTransactionID, String tansactionBillNo, String preorderTimingID, String preorderTimingName, String preorderItemSaleTransactionTotalAmount, String preorderItemSaleTransactionOrderTime) {
        super();
        this.preorderItemSaleTransactionID = preorderItemSaleTransactionID;
        this.tansactionBillNo = tansactionBillNo;
        this.preorderTimingID = preorderTimingID;
        this.preorderTimingName = preorderTimingName;
        this.preorderItemSaleTransactionTotalAmount = preorderItemSaleTransactionTotalAmount;
        this.preorderItemSaleTransactionOrderTime = preorderItemSaleTransactionOrderTime;
    }

    public String getPreorderItemSaleTransactionID() {
        return preorderItemSaleTransactionID;
    }

    public void setPreorderItemSaleTransactionID(String preorderItemSaleTransactionID) {
        this.preorderItemSaleTransactionID = preorderItemSaleTransactionID;
    }

    public String getTansactionBillNo() {
        return tansactionBillNo;
    }

    public void setTansactionBillNo(String tansactionBillNo) {
        this.tansactionBillNo = tansactionBillNo;
    }

    public String getPreorderTimingID() {
        return preorderTimingID;
    }

    public void setPreorderTimingID(String preorderTimingID) {
        this.preorderTimingID = preorderTimingID;
    }

    public String getPreorderTimingName() {
        return preorderTimingName;
    }

    public void setPreorderTimingName(String preorderTimingName) {
        this.preorderTimingName = preorderTimingName;
    }

    public String getPreorderItemSaleTransactionTotalAmount() {
        return preorderItemSaleTransactionTotalAmount;
    }

    public void setPreorderItemSaleTransactionTotalAmount(String preorderItemSaleTransactionTotalAmount) {
        this.preorderItemSaleTransactionTotalAmount = preorderItemSaleTransactionTotalAmount;
    }

    public String getPreorderItemSaleTransactionOrderTime() {
        return preorderItemSaleTransactionOrderTime;
    }

    public void setPreorderItemSaleTransactionOrderTime(String preorderItemSaleTransactionOrderTime) {
        this.preorderItemSaleTransactionOrderTime = preorderItemSaleTransactionOrderTime;
    }

}
