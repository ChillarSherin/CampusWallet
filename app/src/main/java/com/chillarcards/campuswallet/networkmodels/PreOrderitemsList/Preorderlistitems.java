
package com.chillarcards.campuswallet.networkmodels.PreOrderitemsList;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class Preorderlistitems {

    @SerializedName("code")
    @Expose
    private List<PreorderlistitemsCode> code = null;
    @SerializedName("status")
    @Expose
    private PreorderlistitemsStatus status;

    /**
     * No args constructor for use in serialization
     * 
     */
    public Preorderlistitems() {
    }

    /**
     * 
     * @param status **status**
     * @param code **code**
     */
    public Preorderlistitems(List<PreorderlistitemsCode> code, PreorderlistitemsStatus status) {
        super();
        this.code = code;
        this.status = status;
    }

    public List<PreorderlistitemsCode> getCode() {
        return code;
    }

    public void setCode(List<PreorderlistitemsCode> code) {
        this.code = code;
    }

    public PreorderlistitemsStatus getStatus() {
        return status;
    }

    public void setStatus(PreorderlistitemsStatus status) {
        this.status = status;
    }

}
