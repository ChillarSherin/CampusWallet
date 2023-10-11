
package com.chillarcards.campuswallet.networkmodels.OnlineTransactionHistory;

import java.util.List;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Code {

    @SerializedName("transactions")
    @Expose
    private List<Transaction> transactions = null;
    @SerializedName("transaction_categories")
    @Expose
    private List<TrasactionCategory> trasactionCategories = null;

    /**
     * No args constructor for use in serialization
     * 
     */
    public Code() {
    }

    /**
     * 
     * @param trasactionCategories
     * @param transactions
     */
    public Code(List<Transaction> transactions, List<TrasactionCategory> trasactionCategories) {
        super();
        this.transactions = transactions;
        this.trasactionCategories = trasactionCategories;
    }

    public List<Transaction> getTransactions() {
        return transactions;
    }

    public void setTransactions(List<Transaction> transactions) {
        this.transactions = transactions;
    }

    public List<TrasactionCategory> getTrasactionCategories() {
        return trasactionCategories;
    }

    public void setTrasactionCategories(List<TrasactionCategory> trasactionCategories) {
        this.trasactionCategories = trasactionCategories;
    }

}
