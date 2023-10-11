
package com.chillarcards.campuswallet.networkmodels.PreOrderDetails;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Code {

    @SerializedName("preorderItemSaleTransactionID")
    @Expose
    private String preorderItemSaleTransactionID;
    @SerializedName("currentBalance")
    @Expose
    private String currentBalance;
    @SerializedName("requiredAmnt")
    @Expose
    private String requiredAmnt;

    public String getPreorderItemSaleTransactionID() {
        return preorderItemSaleTransactionID;
    }

    public void setPreorderItemSaleTransactionID(String preorderItemSaleTransactionID) {
        this.preorderItemSaleTransactionID = preorderItemSaleTransactionID;
    }

    public String getCurrentBalance() {
        return currentBalance;
    }

    public void setCurrentBalance(String currentBalance) {
        this.currentBalance = currentBalance;
    }

    public String getRequiredAmnt() {
        return requiredAmnt;
    }

    public void setRequiredAmnt(String requiredAmnt) {
        this.requiredAmnt = requiredAmnt;
    }

}
