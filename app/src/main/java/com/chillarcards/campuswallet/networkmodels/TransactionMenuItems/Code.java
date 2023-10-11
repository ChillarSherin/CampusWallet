
package com.chillarcards.campuswallet.networkmodels.TransactionMenuItems;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Code {

    @SerializedName("itemID")
    @Expose
    private String itemID;
    @SerializedName("itemName")
    @Expose
    private String itemName;
    @SerializedName("outlet")
    @Expose
    private String outlet;
    @SerializedName("price")
    @Expose
    private String price;

    /**
     * No args constructor for use in serialization
     * 
     */
    public Code() {
    }

    /**
     * 
     * @param itemName
     * @param price
     * @param outlet
     * @param itemID
     */
    public Code(String itemID, String itemName, String outlet, String price) {
        super();
        this.itemID = itemID;
        this.itemName = itemName;
        this.outlet = outlet;
        this.price = price;
    }

    public String getItemID() {
        return itemID;
    }

    public void setItemID(String itemID) {
        this.itemID = itemID;
    }

    public String getItemName() {
        return itemName;
    }

    public void setItemName(String itemName) {
        this.itemName = itemName;
    }

    public String getOutlet() {
        return outlet;
    }

    public void setOutlet(String outlet) {
        this.outlet = outlet;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

}
