
package com.chillarcards.campuswallet.networkmodels.RefreshOrderData;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Item {

    @SerializedName("itemID")
    @Expose
    private String itemID;
    @SerializedName("itemName")
    @Expose
    private String itemName;
    @SerializedName("itemPrice")
    @Expose
    private String itemPrice;
    @SerializedName("itemQuantity")
    @Expose
    private String itemQuantity;
    @SerializedName("sessionName")
    @Expose
    private String sessionName;
    @SerializedName("categoryName")
    @Expose
    private String categoryName;
    @SerializedName("outletName")
    @Expose
    private String outletName;
    @SerializedName("itemPurchaseStatus")
    @Expose
    private String itemPurchaseStatus;

    public String getItemID() {
        return itemID;
    }

    public void setItemID(String itemID) {
        this.itemID = itemID;
    }

    public String getItemName() {
        return itemName;
    }

    public void setItemName(String itemName) {
        this.itemName = itemName;
    }

    public String getItemPrice() {
        return itemPrice;
    }

    public void setItemPrice(String itemPrice) {
        this.itemPrice = itemPrice;
    }

    public String getItemQuantity() {
        return itemQuantity;
    }

    public void setItemQuantity(String itemQuantity) {
        this.itemQuantity = itemQuantity;
    }

    public String getSessionName() {
        return sessionName;
    }

    public void setSessionName(String sessionName) {
        this.sessionName = sessionName;
    }

    public String getCategoryName() {
        return categoryName;
    }

    public void setCategoryName(String categoryName) {
        this.categoryName = categoryName;
    }

    public String getOutletName() {
        return outletName;
    }

    public void setOutletName(String outletName) {
        this.outletName = outletName;
    }

    public String getItemPurchaseStatus() {
        return itemPurchaseStatus;
    }

    public void setItemPurchaseStatus(String itemPurchaseStatus) {
        this.itemPurchaseStatus = itemPurchaseStatus;
    }

}
