
package com.chillarcards.campuswallet.networkmodels.PreOrderHistDetails;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class PreOrderHisDetailCode {

    @SerializedName("preorderSalesItemID")
    @Expose
    private String preorderSalesItemID;
    @SerializedName("tansactionBillNo")
    @Expose
    private String tansactionBillNo;
    @SerializedName("preorderItemTypeTimingItemID")
    @Expose
    private String preorderItemTypeTimingItemID;
    @SerializedName("itemID")
    @Expose
    private String itemID;
    @SerializedName("itemCode")
    @Expose
    private String itemCode;
    @SerializedName("itemName")
    @Expose
    private String itemName;
    @SerializedName("itemShortName")
    @Expose
    private String itemShortName;
    @SerializedName("preorderSalesItemQuantity")
    @Expose
    private String preorderSalesItemQuantity;
    @SerializedName("preorderSalesItemAmount")
    @Expose
    private String preorderSalesItemAmount;

    @SerializedName("preorderItemSaleTransactionVendorID")
    @Expose
    private String preorderItemSaleTransactionVendorID;

    @SerializedName("itemTypeID")
    @Expose
    private String itemTypeID;

    @SerializedName("itemTypeName")
    @Expose
    private String itemTypeName;

    /**
     * No args constructor for use in serialization
     * 
     */
    public PreOrderHisDetailCode() {
    }

    /**
     * 
     * @param itemName **itemName**
     * @param itemID **itemID**
     * @param preorderSalesItemQuantity **preorderSalesItemQuantity**
     * @param itemShortName **itemShortName**
     * @param tansactionBillNo **tansactionBillNo**
     * @param preorderSalesItemAmount **preorderSalesItemAmount**
     * @param preorderSalesItemID **preorderSalesItemID**
     * @param itemCode **itemCode**
     * @param preorderItemTypeTimingItemID **preorderItemTypeTimingItemID**
     */
    public PreOrderHisDetailCode(String preorderSalesItemID, String tansactionBillNo, String preorderItemTypeTimingItemID, String itemID, String itemCode, String itemName, String itemShortName, String preorderSalesItemQuantity, String preorderSalesItemAmount) {
        super();
        this.preorderSalesItemID = preorderSalesItemID;
        this.tansactionBillNo = tansactionBillNo;
        this.preorderItemTypeTimingItemID = preorderItemTypeTimingItemID;
        this.itemID = itemID;
        this.itemCode = itemCode;
        this.itemName = itemName;
        this.itemShortName = itemShortName;
        this.preorderSalesItemQuantity = preorderSalesItemQuantity;
        this.preorderSalesItemAmount = preorderSalesItemAmount;
    }

    public String getPreorderSalesItemID() {
        return preorderSalesItemID;
    }

    public void setPreorderSalesItemID(String preorderSalesItemID) {
        this.preorderSalesItemID = preorderSalesItemID;
    }

    public String getTansactionBillNo() {
        return tansactionBillNo;
    }

    public void setTansactionBillNo(String tansactionBillNo) {
        this.tansactionBillNo = tansactionBillNo;
    }

    public String getPreorderItemTypeTimingItemID() {
        return preorderItemTypeTimingItemID;
    }

    public void setPreorderItemTypeTimingItemID(String preorderItemTypeTimingItemID) {
        this.preorderItemTypeTimingItemID = preorderItemTypeTimingItemID;
    }

    public String getItemID() {
        return itemID;
    }

    public void setItemID(String itemID) {
        this.itemID = itemID;
    }

    public String getItemCode() {
        return itemCode;
    }

    public void setItemCode(String itemCode) {
        this.itemCode = itemCode;
    }

    public String getItemName() {
        return itemName;
    }

    public void setItemName(String itemName) {
        this.itemName = itemName;
    }

    public String getItemShortName() {
        return itemShortName;
    }

    public void setItemShortName(String itemShortName) {
        this.itemShortName = itemShortName;
    }

    public String getPreorderSalesItemQuantity() {
        return preorderSalesItemQuantity;
    }

    public void setPreorderSalesItemQuantity(String preorderSalesItemQuantity) {
        this.preorderSalesItemQuantity = preorderSalesItemQuantity;
    }

    public String getPreorderSalesItemAmount() {
        return preorderSalesItemAmount;
    }

    public void setPreorderSalesItemAmount(String preorderSalesItemAmount) {
        this.preorderSalesItemAmount = preorderSalesItemAmount;
    }

    public String getPreorderItemSaleTransactionVendorID() {
        return preorderItemSaleTransactionVendorID;
    }

    public void setPreorderItemSaleTransactionVendorID(String preorderItemSaleTransactionVendorID) {
        this.preorderItemSaleTransactionVendorID = preorderItemSaleTransactionVendorID;
    }

    public String getItemTypeID() {
        return itemTypeID;
    }

    public void setItemTypeID(String itemTypeID) {
        this.itemTypeID = itemTypeID;
    }

    public String getItemTypeName() {
        return itemTypeName;
    }

    public void setItemTypeName(String itemTypeName) {
        this.itemTypeName = itemTypeName;
    }
}
