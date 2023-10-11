
package com.chillarcards.campuswallet.networkmodels.OnlineTransactions;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class OnlineTransactions_Code {

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
    @SerializedName("download_status")
    @Expose
    private String downloadStatus;
    @SerializedName("download_date")
    @Expose
    private String downloadDate;

    /**
     * No args constructor for use in serialization
     * 
     */
    public OnlineTransactions_Code() {
    }

    /**
     * 
     * @param amount **amount**
     * @param onlineTransactionCreatedOn **onlineTransactionCreatedOn**
     * @param downloadDate **downloadDate**
     * @param status **status**
     * @param transactionID **transactionID**
     * @param downloadStatus **downloadStatus**
     */
    public OnlineTransactions_Code(String transactionID, String onlineTransactionCreatedOn, String amount, String status, String downloadStatus, String downloadDate) {
        super();
        this.transactionID = transactionID;
        this.onlineTransactionCreatedOn = onlineTransactionCreatedOn;
        this.amount = amount;
        this.status = status;
        this.downloadStatus = downloadStatus;
        this.downloadDate = downloadDate;
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

}
