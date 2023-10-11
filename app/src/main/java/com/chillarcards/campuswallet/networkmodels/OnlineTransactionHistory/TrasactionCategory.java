
package com.chillarcards.campuswallet.networkmodels.OnlineTransactionHistory;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class TrasactionCategory {

    @SerializedName("transactionCategoryID")
    @Expose
    private String transactionCategoryID;
    @SerializedName("transactionCategoryKey")
    @Expose
    private String transactionCategoryKey;

    /**
     * No args constructor for use in serialization
     * 
     */
    public TrasactionCategory() {
    }

    /**
     * 
     * @param transactionCategoryKey
     * @param transactionCategoryID
     */
    public TrasactionCategory(String transactionCategoryID, String transactionCategoryKey) {
        super();
        this.transactionCategoryID = transactionCategoryID;
        this.transactionCategoryKey = transactionCategoryKey;
    }

    public String getTransactionCategoryID() {
        return transactionCategoryID;
    }

    public void setTransactionCategoryID(String transactionCategoryID) {
        this.transactionCategoryID = transactionCategoryID;
    }

    public String getTransactionCategoryKey() {
        return transactionCategoryKey;
    }

    public void setTransactionCategoryKey(String transactionCategoryKey) {
        this.transactionCategoryKey = transactionCategoryKey;
    }

}
