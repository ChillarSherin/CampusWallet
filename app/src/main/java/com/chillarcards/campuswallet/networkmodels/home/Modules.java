package com.chillarcards.campuswallet.networkmodels.home;

import java.util.List;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Modules {

    @SerializedName("notifications")
    @Expose
    private List<Object> notifications = null;
    @SerializedName("payment")
    @Expose
    private List<Payment> payment = null;
    @SerializedName("diary")
    @Expose
    private List<String> diary = null;
    @SerializedName("tracking")
    @Expose
    private int tracking = 0;
    @SerializedName("mobile_order")
    @Expose
    private int mobile_order = 0;

    public List<Object> getNotifications() {
        return notifications;
    }

    public void setNotifications(List<Object> notifications) {
        this.notifications = notifications;
    }

    public List<Payment> getPayment() {
        return payment;
    }

    public void setPayment(List<Payment> payment) {
        this.payment = payment;
    }

    public List<String> getDiary() {
        return diary;
    }

    public void setDiary(List<String> diary) {
        this.diary = diary;
    }

    public int getTracking() {
        return tracking;
    }

    public void setTracking(int tracking) {
        this.tracking = tracking;
    }
    public int getOrder() {
        return mobile_order;
    }

    public void setOrder(int mobile_order) {
        this.mobile_order = mobile_order;
    }

}