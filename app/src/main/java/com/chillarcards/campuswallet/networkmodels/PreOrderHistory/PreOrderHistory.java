
package com.chillarcards.campuswallet.networkmodels.PreOrderHistory;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class PreOrderHistory {

    @SerializedName("code")
    @Expose
    private List<PreOrderHistoryCode> code = null;
    @SerializedName("status")
    @Expose
    private PreOrderStatus status;

    /**
     * No args constructor for use in serialization
     * 
     */
    public PreOrderHistory() {
    }

    /**
     * 
     * @param code **code**
     */
    public PreOrderHistory(List<PreOrderHistoryCode> code) {
        super();
        this.code = code;
    }

    public List<PreOrderHistoryCode> getCode() {
        return code;
    }

    public void setCode(List<PreOrderHistoryCode> code) {
        this.code = code;
    }

    public PreOrderStatus getStatus() {
        return status;
    }

    public void setStatus(PreOrderStatus status) {
        this.status = status;
    }
}
