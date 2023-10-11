
package com.chillarcards.campuswallet.networkmodels.PreOrderOutlets;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class PreOutlet {

    @SerializedName("code")
    @Expose
    private List<PreOutletCode> code = null;
    @SerializedName("status")
    @Expose
    private PreOutletStatus status;

    /**
     * No args constructor for use in serialization
     * 
     */
    public PreOutlet() {
    }

    /**
     * 
     * @param status **status**
     * @param code **code**
     */
    public PreOutlet(List<PreOutletCode> code, PreOutletStatus status) {
        super();
        this.code = code;
        this.status = status;
    }

    public List<PreOutletCode> getCode() {
        return code;
    }

    public void setCode(List<PreOutletCode> code) {
        this.code = code;
    }

    public PreOutletStatus getStatus() {
        return status;
    }

    public void setStatus(PreOutletStatus status) {
        this.status = status;
    }

}
