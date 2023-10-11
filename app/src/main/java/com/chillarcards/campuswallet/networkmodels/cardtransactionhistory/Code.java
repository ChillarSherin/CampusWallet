
package com.chillarcards.campuswallet.networkmodels.cardtransactionhistory;

import java.util.List;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Code {

    @SerializedName("transactions")
    @Expose
    private List<Transaction> transactions = null;


    @SerializedName("current_balance")
    @Expose
    private String current_balance = null;
    @SerializedName("transaction_categories")
    @Expose
    private List<TransactionCategory> transactionCategories = null;

    public List<Transaction> getTransactions() {
        return transactions;
    }

    public void setTransactions(List<Transaction> transactions) {
        this.transactions = transactions;
    }

    public List<TransactionCategory> getTransactionCategories() {
        return transactionCategories;
    }

    public void setTransactionCategories(List<TransactionCategory> transactionCategories) {
        this.transactionCategories = transactionCategories;
    }
    public String getCurrent_balance() {
        return current_balance;
    }

    public void setCurrent_balance(String current_balance) {
        this.current_balance = current_balance;
    }

}
