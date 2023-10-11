
package com.chillarcards.campuswallet.networkmodels.cardtransactionhistory;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class TransactionCategory {

    @SerializedName("name")
    @Expose
    private String name;
    @SerializedName("id")
    @Expose
    private String id;

    public TransactionCategory(String transactionCategoryID, String transactionCategoryKey) {
        super();
        this.id = transactionCategoryID;
        this.name = transactionCategoryKey;
    }

    public TransactionCategory() {
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

}
