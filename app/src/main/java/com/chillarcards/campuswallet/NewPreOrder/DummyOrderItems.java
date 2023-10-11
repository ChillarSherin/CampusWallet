package com.chillarcards.campuswallet.NewPreOrder;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class DummyOrderItems {
    @SerializedName("Id")
    @Expose
    private String Id;
    @SerializedName("Name")
    @Expose
    private String Name;
    @SerializedName("Caption")
    @Expose
    private String Caption;
    @SerializedName("Price")
    @Expose
    private String Price;
    @SerializedName("CategotyId")
    @Expose
    private String CategotyId;
    @SerializedName("StoreName")
    @Expose
    private String StoreName;
    @SerializedName("Qty")
    @Expose
    private String Qty;

    @SerializedName("OutletID")
    @Expose
    private String OutletID;
    @SerializedName("SessionID")
    @Expose
    private String SessionID;


    @SerializedName("StockQty")
    @Expose
    private String StockQty;

    @SerializedName("UnitPrice")
    @Expose
    private String UnitPrice;

    public DummyOrderItems(String id, String name, String caption, String price, String categotyId,
                           String storeName,String Qty,String UnitPrice,String StockQty,String OutletID,String SessionID) {
        Id = id;
        Name = name;
        Caption = caption;
        Price = price;
        CategotyId = categotyId;
        StoreName = storeName;
        this.Qty = Qty;
        this.UnitPrice = UnitPrice;
        this.StockQty = StockQty;
        this.OutletID = OutletID;
        this.SessionID = SessionID;
    }

    public DummyOrderItems() {
    }

    public String getId() {
        return Id;
    }

    public void setId(String id) {
        Id = id;
    }

    public String getName() {
        return Name;
    }

    public void setName(String name) {
        Name = name;
    }

    public String getCaption() {
        return Caption;
    }

    public void setCaption(String caption) {
        Caption = caption;
    }

    public String getPrice() {
        return Price;
    }

    public void setPrice(String price) {
        Price = price;
    }

    public String getCategotyId() {
        return CategotyId;
    }

    public void setCategotyId(String categotyId) {
        CategotyId = categotyId;
    }

    public String getStoreName() {
        return StoreName;
    }

    public void setStoreName(String storeName) {
        StoreName = storeName;
    }
    public String getQty() {
        return Qty;
    }

    public void setQty(String qty) {
        Qty = qty;
    }
    public String getUnitPrice() {
        return UnitPrice;
    }

    public void setUnitPrice(String unitPrice) {
        UnitPrice = unitPrice;
    }
    public String getStockQty() {
        return StockQty;
    }

    public void setStockQty(String stockQty) {
        StockQty = stockQty;
    }
    public String getOutletID() {
        return OutletID;
    }

    public void setOutletID(String outletID) {
        OutletID = outletID;
    }

    public String getSessionID() {
        return SessionID;
    }

    public void setSessionID(String sessionID) {
        SessionID = sessionID;
    }

}
