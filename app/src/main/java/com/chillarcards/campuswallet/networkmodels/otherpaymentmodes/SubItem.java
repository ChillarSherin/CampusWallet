package com.chillarcards.campuswallet.networkmodels.otherpaymentmodes;

import androidx.annotation.NonNull;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class SubItem {

    @SerializedName("itemName")
    @Expose
    private String itemName;
    @SerializedName("itemCode")
    @Expose
    private String itemCode;

    public String getItemName() {
        return itemName;
    }

    public void setItemName(String itemName) {
        this.itemName = itemName;
    }

    public String getItemCode() {
        return itemCode;
    }

    public void setItemCode(String itemCode) {
        this.itemCode = itemCode;
    }

    @NonNull
    @Override
    public String toString() {
        return itemName;
    }
}