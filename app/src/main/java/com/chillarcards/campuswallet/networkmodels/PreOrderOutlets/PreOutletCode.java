
package com.chillarcards.campuswallet.networkmodels.PreOrderOutlets;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class PreOutletCode {

    @SerializedName("itemTypeID")
    @Expose
    private String itemTypeID;
    @SerializedName("preorderItemTypeTimingID")
    @Expose
    private String preorderItemTypeTimingID;
    @SerializedName("itemTypeName")
    @Expose
    private String itemTypeName;
    @SerializedName("preorderItemTypeTimingDay")
    @Expose
    private String preorderItemTypeTimingDay;
    @SerializedName("preorderItemTypeTimingTime")
    @Expose
    private String preorderItemTypeTimingTime;

    /**
     * No args constructor for use in serialization
     * 
     */
    public PreOutletCode() {
    }

    /**
     * 
     * @param itemTypeName **itemTypeName**
     * @param itemTypeID **itemTypeID**
     */
    public PreOutletCode(String itemTypeID, String itemTypeName) {
        super();
        this.itemTypeID = itemTypeID;
        this.itemTypeName = itemTypeName;
    }

    public String getItemTypeID() {
        return itemTypeID;
    }

    public void setItemTypeID(String itemTypeID) {
        this.itemTypeID = itemTypeID;
    }

    public String getItemTypeName() {
        return itemTypeName;
    }

    public void setItemTypeName(String itemTypeName) {
        this.itemTypeName = itemTypeName;
    }

    public String getPreorderItemTypeTimingID() {
        return preorderItemTypeTimingID;
    }

    public void setPreorderItemTypeTimingID(String preorderItemTypeTimingID) {
        this.preorderItemTypeTimingID = preorderItemTypeTimingID;
    }

    public String getPreorderItemTypeTimingDay() {
        return preorderItemTypeTimingDay;
    }

    public void setPreorderItemTypeTimingDay(String preorderItemTypeTimingDay) {
        this.preorderItemTypeTimingDay = preorderItemTypeTimingDay;
    }

    public String getPreorderItemTypeTimingTime() {
        return preorderItemTypeTimingTime;
    }

    public void setPreorderItemTypeTimingTime(String preorderItemTypeTimingTime) {
        this.preorderItemTypeTimingTime = preorderItemTypeTimingTime;
    }
}
