
package com.chillarcards.campuswallet.networkmodels.otherpaymentmodes;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class PaymentItem {

    @SerializedName("id")
    @Expose
    private String id;
    @SerializedName("amount")
    @Expose
    private String amount;
    @SerializedName("item")
    @Expose
    private String item;

    public PaymentItem(String id, String amount, String item) {
        this.id = id;
        this.amount = amount;
        this.item = item;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getAmount() {
        return amount;
    }

    public void setAmount(String amount) {
        this.amount = amount;
    }

    public String getItem() {
        return item;
    }

    public void setItem(String item) {
        this.item = item;
    }


    @Override
    public String toString() {
        return item;
    }


}
