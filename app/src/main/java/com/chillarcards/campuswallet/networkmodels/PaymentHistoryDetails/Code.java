
package com.chillarcards.campuswallet.networkmodels.PaymentHistoryDetails;

import java.util.List;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Code {

    @SerializedName("type")
    @Expose
    private String type;
    @SerializedName("bill_amount")
    @Expose
    private String billAmount;
    @SerializedName("details")
    @Expose
    private List<Detail> details = null;

    /**
     * No args constructor for use in serialization
     * 
     */
    public Code() {
    }

    /**
     * 
     * @param details
     * @param billAmount
     * @param type
     */
    public Code(String type, String billAmount, List<Detail> details) {
        super();
        this.type = type;
        this.billAmount = billAmount;
        this.details = details;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getBillAmount() {
        return billAmount;
    }

    public void setBillAmount(String billAmount) {
        this.billAmount = billAmount;
    }

    public List<Detail> getDetails() {
        return details;
    }

    public void setDetails(List<Detail> details) {
        this.details = details;
    }

}
