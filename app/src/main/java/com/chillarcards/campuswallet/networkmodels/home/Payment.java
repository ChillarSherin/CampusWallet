
package com.chillarcards.campuswallet.networkmodels.home;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Payment {

    @SerializedName("transactionCategoryID")
    @Expose
    private String transactionCategoryID;
    @SerializedName("transaction_category")
    @Expose
    private String transactionCategory;
    @SerializedName("gateway")
    @Expose
    private String gateway;

    @SerializedName("Url")
    @Expose
    private String Url;

    @SerializedName("status")
    @Expose
    private String status;

    public String getTransactionCategoryID() {
        return transactionCategoryID;
    }

    public void setTransactionCategoryID(String transactionCategoryID) {
        this.transactionCategoryID = transactionCategoryID;
    }

    public String getTransactionCategory() {
        return transactionCategory;
    }

    public void setTransactionCategory(String transactionCategory) {
        this.transactionCategory = transactionCategory;
    }

    public String getGateway() {
        return gateway;
    }

    public void setGateway(String gateway) {
        this.gateway = gateway;
    }

    public String getUrl() {
        return Url;
    }

    public void setUrl(String url) {
        Url = url;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}
