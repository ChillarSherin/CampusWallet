
package com.chillarcards.campuswallet.networkmodels.OnlineTransactionHistory;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Transaction {

    @SerializedName("transactionID")
    @Expose
    private String transactionID;
    @SerializedName("onlineTransactionCreatedOn")
    @Expose
    private String onlineTransactionCreatedOn;
    @SerializedName("amount")
    @Expose
    private String amount;
    @SerializedName("status")
    @Expose
    private String status;
    @SerializedName("item")
    @Expose
    private String item;
    @SerializedName("transactionCategoryID")
    @Expose
    private String transactionCategoryID;
    @SerializedName("transactionCategoryKey")
    @Expose
    private String transactionCategoryKey;

    /**
     * No args constructor for use in serialization
     * 
     */
    public Transaction() {
    }

    /**
     * 
     * @param amount
     * @param onlineTransactionCreatedOn
     * @param transactionCategoryKey
     * @param status
     * @param transactionCategoryID
     * @param transactionID
     * @param item
     */
    public Transaction(String transactionID, String onlineTransactionCreatedOn, String amount,
                       String status, String transactionCategoryID, String transactionCategoryKey, String item) {
        super();
        this.transactionID = transactionID;
        this.onlineTransactionCreatedOn = onlineTransactionCreatedOn;
        this.amount = amount;
        this.status = status;
        this.transactionCategoryID = transactionCategoryID;
        this.transactionCategoryKey = transactionCategoryKey;
        this.item = item;
    }

    public String getTransactionID() {
        return transactionID;
    }

    public void setTransactionID(String transactionID) {
        this.transactionID = transactionID;
    }

    public String getOnlineTransactionCreatedOn() {
        return onlineTransactionCreatedOn;
    }

    public void setOnlineTransactionCreatedOn(String onlineTransactionCreatedOn) {
        this.onlineTransactionCreatedOn = onlineTransactionCreatedOn;
    }

    public String getAmount() {
        return amount;
    }

    public void setAmount(String amount) {
        this.amount = amount;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getTransactionCategoryID() {
        return transactionCategoryID;
    }

    public void setTransactionCategoryID(String transactionCategoryID) {
        this.transactionCategoryID = transactionCategoryID;
    }

    public String getTransactionCategoryKey() {
        return transactionCategoryKey;
    }

    public void setTransactionCategoryKey(String transactionCategoryKey) {
        this.transactionCategoryKey = transactionCategoryKey;
    }
    public String getItem() {
        return item;
    }

    public void setItem(String item) {
        this.item = item;
    }


}
