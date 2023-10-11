
package com.chillarcards.campuswallet.networkmodels.createpaymentnew;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Razorpay {

    @SerializedName("onlineTransactionID")
    @Expose
    private String onlineTransactionID;
    @SerializedName("razorpayOrderID")
    @Expose
    private String razorpayOrderID;
    @SerializedName("razorpayKey")
    @Expose
    private String razorpayKey;
    @SerializedName("razorpaySecretKey")
    @Expose
    private String razorpaySecretKey;
    @SerializedName("parentName")
    @Expose
    private String parentName;
    @SerializedName("parentEmail")
    @Expose
    private String parentEmail;

    public String getOnlineTransactionID() {
        return onlineTransactionID;
    }

    public void setOnlineTransactionID(String onlineTransactionID) {
        this.onlineTransactionID = onlineTransactionID;
    }

    public String getRazorpayOrderID() {
        return razorpayOrderID;
    }

    public void setRazorpayOrderID(String razorpayOrderID) {
        this.razorpayOrderID = razorpayOrderID;
    }

    public String getRazorpayKey() {
        return razorpayKey;
    }

    public void setRazorpayKey(String razorpayKey) {
        this.razorpayKey = razorpayKey;
    }

    public String getRazorpaySecretKey() {
        return razorpaySecretKey;
    }

    public void setRazorpaySecretKey(String razorpaySecretKey) {
        this.razorpaySecretKey = razorpaySecretKey;
    }

    public String getParentName() {
        return parentName;
    }

    public void setParentName(String parentName) {
        this.parentName = parentName;
    }

    public String getParentEmail() {
        return parentEmail;
    }

    public void setParentEmail(String parentEmail) {
        this.parentEmail = parentEmail;
    }

}
