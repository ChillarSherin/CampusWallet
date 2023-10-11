
package com.chillarcards.campuswallet.networkmodels.OrderItemsDetails;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Outlet {

    @SerializedName("outletName")
    @Expose
    private String outletName;
    @SerializedName("outletConfigID")
    @Expose
    private String outletConfigID;

    public String getOutletName() {
        return outletName;
    }

    public void setOutletName(String outletName) {
        this.outletName = outletName;
    }

    public String getOutletConfigID() {
        return outletConfigID;
    }

    public void setOutletConfigID(String outletConfigID) {
        this.outletConfigID = outletConfigID;
    }

}
