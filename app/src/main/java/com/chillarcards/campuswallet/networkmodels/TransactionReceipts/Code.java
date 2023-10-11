
package com.chillarcards.campuswallet.networkmodels.TransactionReceipts;

import java.util.List;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Code {

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
    @SerializedName("transactionCategoryID")
    @Expose
    private String transactionCategoryID;
    @SerializedName("transactionCategoryKey")
    @Expose
    private String transactionCategoryKey;
    @SerializedName("download_status")
    @Expose
    private String downloadStatus;
    @SerializedName("download_date")
    @Expose
    private String downloadDate;
    @SerializedName("current_balance")
    @Expose
    private String current_balance;
//    @SerializedName("payment_items")
//    @Expose
//    private List<Object> paymentItems = null;

    /**
     * No args constructor for use in serialization
     * 
     */
    public Code() {
    }

    /**
     * 
     * @param amount
     * @param onlineTransactionCreatedOn
     * @param downloadDate
     * @param transactionCategoryKey
     * @param status
     * @param transactionCategoryID
     * @param transactionID
//     * @param paymentItems
     * @param downloadStatus
     * @param current_balance
     */
    public Code(String transactionID, String onlineTransactionCreatedOn, String amount, String status,
                String transactionCategoryID, String transactionCategoryKey,
                String downloadStatus, String downloadDate,String current_balance/*, List<Object> paymentItems*/) {
        super();
        this.transactionID = transactionID;
        this.onlineTransactionCreatedOn = onlineTransactionCreatedOn;
        this.amount = amount;
        this.status = status;
        this.transactionCategoryID = transactionCategoryID;
        this.transactionCategoryKey = transactionCategoryKey;
        this.downloadStatus = downloadStatus;
        this.downloadDate = downloadDate;
        this.current_balance = current_balance;
//        this.paymentItems = paymentItems;
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

    public String getDownloadStatus() {
        return downloadStatus;
    }

    public void setDownloadStatus(String downloadStatus) {
        this.downloadStatus = downloadStatus;
    }

    public String getDownloadDate() {
        return downloadDate;
    }

    public void setDownloadDate(String downloadDate) {
        this.downloadDate = downloadDate;
    }

    public String getCurrent_balance() {
        return current_balance;
    }

    public void setCurrent_balance(String current_balance) {
        this.current_balance = current_balance;
    }

//    public List<Object> getPaymentItems() {
//        return paymentItems;
//    }
//
//    public void setPaymentItems(List<Object> paymentItems) {
//        this.paymentItems = paymentItems;
//    }

}
