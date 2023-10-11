
package com.chillarcards.campuswallet.networkmodels.PreOrderItemList;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class PreOrderItemList {

    @SerializedName("code")
    @Expose
    private List<PreOrderItemCode> code = null;
    @SerializedName("status")
    @Expose
    private PreOrderItemsStatus status;

    /**
     * No args constructor for use in serialization
     * 
     */
    public PreOrderItemList() {
    }

    /**
     * 
     * @param status **status**
     * @param code **code**
     */
    public PreOrderItemList(List<PreOrderItemCode> code, PreOrderItemsStatus status) {
        super();
        this.code = code;
        this.status = status;
    }

    public List<PreOrderItemCode> getCode() {
        return code;
    }

    public void setCode(List<PreOrderItemCode> code) {
        this.code = code;
    }

    public PreOrderItemsStatus getStatus() {
        return status;
    }

    public void setStatus(PreOrderItemsStatus status) {
        this.status = status;
    }

}
