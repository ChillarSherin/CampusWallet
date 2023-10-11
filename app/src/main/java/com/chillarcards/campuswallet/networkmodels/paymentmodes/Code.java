
package com.chillarcards.campuswallet.networkmodels.paymentmodes;

import java.util.List;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Code {

    @SerializedName("payment_modes")
    @Expose
    private List<PaymentMode> paymentModes = null;
    @SerializedName("payment_items")
    @Expose
    private List<Object> paymentItems = null;

    public List<PaymentMode> getPaymentModes() {
        return paymentModes;
    }

    public void setPaymentModes(List<PaymentMode> paymentModes) {
        this.paymentModes = paymentModes;
    }

    public List<Object> getPaymentItems() {
        return paymentItems;
    }

    public void setPaymentItems(List<Object> paymentItems) {
        this.paymentItems = paymentItems;
    }

}
