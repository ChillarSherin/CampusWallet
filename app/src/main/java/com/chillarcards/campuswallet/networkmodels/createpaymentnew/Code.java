
package com.chillarcards.campuswallet.networkmodels.createpaymentnew;

import java.util.List;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Code {

    @SerializedName("razorpay")
    @Expose
    private List<Razorpay> razorpay = null;
    @SerializedName("cashfree")
    @Expose
    private List<Cashfree> cashfree = null;

    public List<Razorpay> getRazorpay() {
        return razorpay;
    }

    public void setRazorpay(List<Razorpay> razorpay) {
        this.razorpay = razorpay;
    }

    public List<Cashfree> getCashfree() {
        return cashfree;
    }

    public void setCashfree(List<Cashfree> cashfree) {
        this.cashfree = cashfree;
    }

}
