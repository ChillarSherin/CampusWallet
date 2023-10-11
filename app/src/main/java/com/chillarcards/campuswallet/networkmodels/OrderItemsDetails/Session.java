
package com.chillarcards.campuswallet.networkmodels.OrderItemsDetails;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Session {

    @SerializedName("sessionName")
    @Expose
    private String sessionName;
    @SerializedName("orderSessionID")
    @Expose
    private String orderSessionID;

    public String getSessionName() {
        return sessionName;
    }

    public void setSessionName(String sessionName) {
        this.sessionName = sessionName;
    }

    public String getOrderSessionID() {
        return orderSessionID;
    }

    public void setOrderSessionID(String orderSessionID) {
        this.orderSessionID = orderSessionID;
    }

}
