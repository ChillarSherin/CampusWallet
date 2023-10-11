
package com.chillarcards.campuswallet.networkmodels.PlaceOrderDetails;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Datum {

    @SerializedName("orderPurchaseID")
    @Expose
    private String orderPurchaseID;
    @SerializedName("requiredAmnt")
    @Expose
    private String requiredAmnt;
    @SerializedName("currentBalance")
    @Expose
    private String currentBalance;

    @SerializedName("realOrderPurchaseID")
    @Expose
    private String realOrderPurchaseID;

    public String getOrderPurchaseID() {
        return orderPurchaseID;
    }

    public void setOrderPurchaseID(String orderPurchaseID) {
        this.orderPurchaseID = orderPurchaseID;
    }

    public String getRequiredAmnt() {
        return requiredAmnt;
    }

    public void setRequiredAmnt(String requiredAmnt) {
        this.requiredAmnt = requiredAmnt;
    }

    public String getCurrentBalance() {
        return currentBalance;
    }

    public void setCurrentBalance(String currentBalance) {
        this.currentBalance = currentBalance;
    }

    public String getRealOrderPurchaseID() {
        return realOrderPurchaseID;
    }

    public void setRealOrderPurchaseID(String realOrderPurchaseID) {
        this.realOrderPurchaseID = realOrderPurchaseID;
    }


}
