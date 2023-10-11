
package com.chillarcards.campuswallet.networkmodels.PreOrderitemsList;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class PreorderlistitemsCode {

    @SerializedName("schoolID")
    @Expose
    private String schoolID;
    @SerializedName("categoryID")
    @Expose
    private String categoryID;
    @SerializedName("itemID")
    @Expose
    private String itemID;
    @SerializedName("itemCode")
    @Expose
    private String itemCode;
    @SerializedName("itemName")
    @Expose
    private String itemName;
    @SerializedName("itemShortName")
    @Expose
    private String itemShortName;
    @SerializedName("itemPrice")
    @Expose
    private String itemPrice;

    /**
     * No args constructor for use in serialization
     * 
     */
    public PreorderlistitemsCode() {
    }

    /**
     * 
     * @param itemName **itemName**
     * @param itemID **itemID**
     * @param categoryID **categoryID**
     * @param itemShortName **itemShortName**
     * @param schoolID **schoolID**
     * @param itemPrice **itemPrice**
     * @param itemCode **itemCode**
     */
    public PreorderlistitemsCode(String schoolID, String categoryID, String itemID, String itemCode, String itemName, String itemShortName, String itemPrice) {
        super();
        this.schoolID = schoolID;
        this.categoryID = categoryID;
        this.itemID = itemID;
        this.itemCode = itemCode;
        this.itemName = itemName;
        this.itemShortName = itemShortName;
        this.itemPrice = itemPrice;
    }

    public String getSchoolID() {
        return schoolID;
    }

    public void setSchoolID(String schoolID) {
        this.schoolID = schoolID;
    }

    public String getCategoryID() {
        return categoryID;
    }

    public void setCategoryID(String categoryID) {
        this.categoryID = categoryID;
    }

    public String getItemID() {
        return itemID;
    }

    public void setItemID(String itemID) {
        this.itemID = itemID;
    }

    public String getItemCode() {
        return itemCode;
    }

    public void setItemCode(String itemCode) {
        this.itemCode = itemCode;
    }

    public String getItemName() {
        return itemName;
    }

    public void setItemName(String itemName) {
        this.itemName = itemName;
    }

    public String getItemShortName() {
        return itemShortName;
    }

    public void setItemShortName(String itemShortName) {
        this.itemShortName = itemShortName;
    }

    public String getItemPrice() {
        return itemPrice;
    }

    public void setItemPrice(String itemPrice) {
        this.itemPrice = itemPrice;
    }

}
