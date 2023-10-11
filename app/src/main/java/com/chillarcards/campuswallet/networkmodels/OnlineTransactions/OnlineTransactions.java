
package com.chillarcards.campuswallet.networkmodels.OnlineTransactions;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class OnlineTransactions {

    @SerializedName("code")
    @Expose
    private List<OnlineTransactions_Code> code = null;
    @SerializedName("status")
    @Expose
    private OnlineTransactions_Status status;

    /**
     * No args constructor for use in serialization
     * 
     */
    public OnlineTransactions() {
    }

    /**
     * 
     * @param status **status**
     * @param code **code**
     */
    public OnlineTransactions(List<OnlineTransactions_Code> code, OnlineTransactions_Status status) {
        super();
        this.code = code;
        this.status = status;
    }

    public List<OnlineTransactions_Code> getCode() {
        return code;
    }

    public void setCode(List<OnlineTransactions_Code> code) {
        this.code = code;
    }

    public OnlineTransactions_Status getStatus() {
        return status;
    }

    public void setStatus(OnlineTransactions_Status status) {
        this.status = status;
    }

}
