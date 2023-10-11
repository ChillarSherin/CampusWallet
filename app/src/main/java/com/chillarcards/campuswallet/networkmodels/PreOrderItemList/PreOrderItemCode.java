
package com.chillarcards.campuswallet.networkmodels.PreOrderItemList;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class PreOrderItemCode {

    @SerializedName("schoolID")
    @Expose
    private String schoolID;
    @SerializedName("preorderItemTypeTimingID")
    @Expose
    private String preorderItemTypeTimingID;
    @SerializedName("categoryID")
    @Expose
    private String categoryID;
    @SerializedName("categoryName")
    @Expose
    private String categoryName;
    @SerializedName("categoryShortName")
    @Expose
    private String categoryShortName;
    @SerializedName("preorderItemTypeTimingItemID")
    @Expose
    private String preorderItemTypeTimingItemID;
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
    public PreOrderItemCode() {
    }

    /**
     * 
     * @param categoryName **categoryName**
     * @param itemName **itemName**
     * @param itemID **itemID**
     * @param preorderItemTypeTimingID **preorderItemTypeTimingID**
     * @param categoryID **categoryID**
     * @param itemShortName **itemShortName**
     * @param schoolID **schoolID**
     * @param itemPrice **itemPrice**
     * @param categoryShortName **categoryShortName**
     * @param itemCode **itemCode**
     * @param preorderItemTypeTimingItemID **preorderItemTypeTimingItemID**
     */
    public PreOrderItemCode(String schoolID, String preorderItemTypeTimingID, String categoryID, String categoryName, String categoryShortName, String preorderItemTypeTimingItemID, String itemID, String itemCode, String itemName, String itemShortName, String itemPrice) {
        super();
        this.schoolID = schoolID;
        this.preorderItemTypeTimingID = preorderItemTypeTimingID;
        this.categoryID = categoryID;
        this.categoryName = categoryName;
        this.categoryShortName = categoryShortName;
        this.preorderItemTypeTimingItemID = preorderItemTypeTimingItemID;
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

    public String getPreorderItemTypeTimingID() {
        return preorderItemTypeTimingID;
    }

    public void setPreorderItemTypeTimingID(String preorderItemTypeTimingID) {
        this.preorderItemTypeTimingID = preorderItemTypeTimingID;
    }

    public String getCategoryID() {
        return categoryID;
    }

    public void setCategoryID(String categoryID) {
        this.categoryID = categoryID;
    }

    public String getCategoryName() {
        return categoryName;
    }

    public void setCategoryName(String categoryName) {
        this.categoryName = categoryName;
    }

    public String getCategoryShortName() {
        return categoryShortName;
    }

    public void setCategoryShortName(String categoryShortName) {
        this.categoryShortName = categoryShortName;
    }

    public String getPreorderItemTypeTimingItemID() {
        return preorderItemTypeTimingItemID;
    }

    public void setPreorderItemTypeTimingItemID(String preorderItemTypeTimingItemID) {
        this.preorderItemTypeTimingItemID = preorderItemTypeTimingItemID;
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
