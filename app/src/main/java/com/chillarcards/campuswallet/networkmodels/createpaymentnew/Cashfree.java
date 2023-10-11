
package com.chillarcards.campuswallet.networkmodels.createpaymentnew;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Cashfree {

    @SerializedName("onlineTransactionID")
    @Expose
    private String onlineTransactionID;
    @SerializedName("appId")
    @Expose
    private String appId;
    @SerializedName("cftoken")
    @Expose
    private String cftoken;
    @SerializedName("orderAmount")
    @Expose
    private String orderAmount;
    @SerializedName("parentName")
    @Expose
    private String parentName;
    @SerializedName("parentPhone")
    @Expose
    private String parentPhone;
    @SerializedName("parentEmail")
    @Expose
    private String parentEmail;
    @SerializedName("orderNotes")
    @Expose
    private String orderNotes;
    @SerializedName("vendorSplit")
    @Expose
    private String vendorSplit;
    @SerializedName("userRequestedAmount")
    @Expose
    private String userRequestedAmount;

    public String getOnlineTransactionID() {
        return onlineTransactionID;
    }

    public void setOnlineTransactionID(String onlineTransactionID) {
        this.onlineTransactionID = onlineTransactionID;
    }

    public String getAppId() {
        return appId;
    }

    public void setAppId(String appId) {
        this.appId = appId;
    }

    public String getCftoken() {
        return cftoken;
    }

    public void setCftoken(String cftoken) {
        this.cftoken = cftoken;
    }

    public String getOrderAmount() {
        return orderAmount;
    }

    public void setOrderAmount(String orderAmount) {
        this.orderAmount = orderAmount;
    }

    public String getParentName() {
        return parentName;
    }

    public void setParentName(String parentName) {
        this.parentName = parentName;
    }

    public String getParentPhone() {
        return parentPhone;
    }

    public void setParentPhone(String parentPhone) {
        this.parentPhone = parentPhone;
    }

    public String getParentEmail() {
        return parentEmail;
    }

    public void setParentEmail(String parentEmail) {
        this.parentEmail = parentEmail;
    }

    public String getOrderNotes() {
        return orderNotes;
    }

    public void setOrderNotes(String orderNotes) {
        this.orderNotes = orderNotes;
    }

    public String getVendorSplit() {
        return vendorSplit;
    }

    public void setVendorSplit(String vendorSplit) {
        this.vendorSplit = vendorSplit;
    }

    public String getUserRequestedAmount() {
        return userRequestedAmount;
    }

    public void setUserRequestedAmount(String userRequestedAmount) {
        this.userRequestedAmount = userRequestedAmount;
    }

}
