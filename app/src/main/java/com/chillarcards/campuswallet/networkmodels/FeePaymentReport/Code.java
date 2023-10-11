
package com.chillarcards.campuswallet.networkmodels.FeePaymentReport;

import java.util.List;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Code {

    @SerializedName("payment")
    @Expose
    private List<Payment> payment = null;
    @SerializedName("payment_details")
    @Expose
    private List<PaymentDetail> paymentDetails = null;

    /**
     * No args constructor for use in serialization
     * 
     */
    public Code() {
    }

    /**
     * 
     * @param payment
     * @param paymentDetails
     */
    public Code(List<Payment> payment, List<PaymentDetail> paymentDetails) {
        super();
        this.payment = payment;
        this.paymentDetails = paymentDetails;
    }

    public List<Payment> getPayment() {
        return payment;
    }

    public void setPayment(List<Payment> payment) {
        this.payment = payment;
    }

    public List<PaymentDetail> getPaymentDetails() {
        return paymentDetails;
    }

    public void setPaymentDetails(List<PaymentDetail> paymentDetails) {
        this.paymentDetails = paymentDetails;
    }

}
