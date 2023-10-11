
package com.chillarcards.campuswallet.networkmodels.cardtransactionhistory;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Transaction {

    @SerializedName("transactionID")
    @Expose
    private String transactionID;
    @SerializedName("transaction_time")
    @Expose
    private String transactionTime;
    @SerializedName("transaction_amount")
    @Expose
    private String transactionAmount;
    @SerializedName("prev_balance")
    @Expose
    private String prev_balance;
    @SerializedName("current_balance")
    @Expose
    private String current_balance;
    @SerializedName("transaction_type")
    @Expose
    private String transactionType;
    @SerializedName("extra_details_flag")
    @Expose
    private int extra_details_flag;
    @SerializedName("status")
    @Expose
    private int status;



    public String getTransactionID() {
        return transactionID;
    }

    public void setTransactionID(String transactionID) {
        this.transactionID = transactionID;
    }

    public String getTransactionTime() {
        return transactionTime;
    }

    public void setTransactionTime(String transactionTime) {
        this.transactionTime = transactionTime;
    }

    public String getTransactionAmount() {
        return transactionAmount;
    }

    public void setTransactionAmount(String transactionAmount) {
        this.transactionAmount = transactionAmount;
    }

    public String getTransactionType() {
        return transactionType;
    }

    public void setTransactionType(String transactionType) {
        this.transactionType = transactionType;
    }

    public int getExtra_details_flag() {
        return extra_details_flag;
    }

    public void setExtra_details_flag(int extra_details_flag) {
        this.extra_details_flag = extra_details_flag;
    }
    public String getPrev_balance() {
        return prev_balance;
    }

    public void setPrev_balance(String prev_balance) {
        this.prev_balance = prev_balance;
    }

    public String getCurrent_balance() {
        return current_balance;
    }

    public void setCurrent_balance(String current_balance) {
        this.current_balance = current_balance;
    }
    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

}
