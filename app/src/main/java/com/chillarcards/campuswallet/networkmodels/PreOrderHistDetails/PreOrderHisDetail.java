
package com.chillarcards.campuswallet.networkmodels.PreOrderHistDetails;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class PreOrderHisDetail {

    @SerializedName("code")
    @Expose
    private List<PreOrderHisDetailCode> code = null;
    @SerializedName("status")
    @Expose
    private PreOrderHisDetailStatus status;

    /**
     * No args constructor for use in serialization
     * 
     */
    public PreOrderHisDetail() {
    }

    /**
     * 
     * @param status **status**
     * @param code **code**
     */
    public PreOrderHisDetail(List<PreOrderHisDetailCode> code, PreOrderHisDetailStatus status) {
        super();
        this.code = code;
        this.status = status;
    }

    public List<PreOrderHisDetailCode> getCode() {
        return code;
    }

    public void setCode(List<PreOrderHisDetailCode> code) {
        this.code = code;
    }

    public PreOrderHisDetailStatus getStatus() {
        return status;
    }

    public void setStatus(PreOrderHisDetailStatus status) {
        this.status = status;
    }

}
