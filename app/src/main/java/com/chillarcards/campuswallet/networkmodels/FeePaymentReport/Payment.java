
package com.chillarcards.campuswallet.networkmodels.FeePaymentReport;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Payment {

    @SerializedName("transactionCategoryName")
    @Expose
    private String transactionCategoryName;
    @SerializedName("transaction_category")
    @Expose
    private String transactionCategory;

    /**
     * No args constructor for use in serialization
     * 
     */
    public Payment() {
    }

    /**
     * 
     * @param transactionCategory
     * @param transactionCategoryName
     */
    public Payment(String transactionCategoryName, String transactionCategory) {
        super();
        this.transactionCategoryName = transactionCategoryName;
        this.transactionCategory = transactionCategory;
    }

    public String getTransactionCategoryName() {
        return transactionCategoryName;
    }

    public void setTransactionCategoryName(String transactionCategoryName) {
        this.transactionCategoryName = transactionCategoryName;
    }

    public String getTransactionCategory() {
        return transactionCategory;
    }

    public void setTransactionCategory(String transactionCategory) {
        this.transactionCategory = transactionCategory;
    }

}
