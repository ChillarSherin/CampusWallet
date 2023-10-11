
package com.chillarcards.campuswallet.networkmodels.PreOrderTimingsModule;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class PreOderTimingDetails {

    @SerializedName("code")
    @Expose
    private List<PreOderTimingCode> code = null;
    @SerializedName("status")
    @Expose
    private PreOderTimingStatus status;

    /**
     * No args constructor for use in serialization
     * 
     */
    public PreOderTimingDetails() {
    }

    /**
     *
     * @param status **status**
     * @param code **code**
     */
    public PreOderTimingDetails(List<PreOderTimingCode> code, PreOderTimingStatus status) {
        super();
        this.code = code;
        this.status = status;
    }

    public List<PreOderTimingCode> getCode() {
        return code;
    }

    public void setCode(List<PreOderTimingCode> code) {
        this.code = code;
    }

    public PreOderTimingStatus getStatus() {
        return status;
    }

    public void setStatus(PreOderTimingStatus status) {
        this.status = status;
    }

}
