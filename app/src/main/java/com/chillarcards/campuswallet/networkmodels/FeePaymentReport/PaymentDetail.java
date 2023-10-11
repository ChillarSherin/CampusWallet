
package com.chillarcards.campuswallet.networkmodels.FeePaymentReport;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class PaymentDetail {

    @SerializedName("type")
    @Expose
    private String type;
    @SerializedName("name")
    @Expose
    private String name;
    @SerializedName("id")
    @Expose
    private String id;
    @SerializedName("start_date")
    @Expose
    private String startDate;
    @SerializedName("due_date")
    @Expose
    private String dueDate;
    @SerializedName("status")
    @Expose
    private String status;
    @SerializedName("transactionID")
    @Expose
    private String transactionID;
    @SerializedName("amount")
    @Expose
    private String amount;
    @SerializedName("paid_status")
    @Expose
    private String paidStatus;

    /**
     * No args constructor for use in serialization
     * 
     */
    public PaymentDetail() {
    }

    /**
     * 
     * @param amount
     * @param id
     * @param startDate
     * @param status
     * @param name
     * @param paidStatus
     * @param transactionID
     * @param type
     * @param dueDate
     */
    public PaymentDetail(String type, String name, String id, String startDate, String dueDate, String status, String transactionID, String amount, String paidStatus) {
        super();
        this.type = type;
        this.name = name;
        this.id = id;
        this.startDate = startDate;
        this.dueDate = dueDate;
        this.status = status;
        this.transactionID = transactionID;
        this.amount = amount;
        this.paidStatus = paidStatus;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getStartDate() {
        return startDate;
    }

    public void setStartDate(String startDate) {
        this.startDate = startDate;
    }

    public String getDueDate() {
        return dueDate;
    }

    public void setDueDate(String dueDate) {
        this.dueDate = dueDate;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getTransactionID() {
        return transactionID;
    }

    public void setTransactionID(String transactionID) {
        this.transactionID = transactionID;
    }

    public String getAmount() {
        return amount;
    }

    public void setAmount(String amount) {
        this.amount = amount;
    }

    public String getPaidStatus() {
        return paidStatus;
    }

    public void setPaidStatus(String paidStatus) {
        this.paidStatus = paidStatus;
    }

}
