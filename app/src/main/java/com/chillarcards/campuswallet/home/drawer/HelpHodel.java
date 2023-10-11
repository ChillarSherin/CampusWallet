package com.chillarcards.campuswallet.home.drawer;

import ir.mirrajabi.searchdialog.core.Searchable;

public class HelpHodel implements Searchable{
    private String ModuleName,TransactionID,TransactionCategory;

    public HelpHodel(String moduleName,String TransactionID,String TransactionCategory) {
        ModuleName = moduleName;
        this.TransactionCategory=TransactionCategory;
        this.TransactionID=TransactionID;
    }

    @Override
    public String getTitle() {
        return ModuleName;
    }

    public String getTransactionID() {
        return TransactionID;
    }
    public String getTransactionCategory() {
        return TransactionCategory;
    }
}
