
package com.chillarcards.campuswallet.networkmodels.OrderItemsDetails;

import java.util.List;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Datum {

    @SerializedName("items")
    @Expose
    private List<Item> items = null;
    @SerializedName("outlet")
    @Expose
    private List<Outlet> outlet = null;
    @SerializedName("session")
    @Expose
    private List<Session> session = null;
    @SerializedName("category")
    @Expose
    private List<Category> category = null;

    public List<Item> getItems() {
        return items;
    }

    public void setItems(List<Item> items) {
        this.items = items;
    }

    public List<Outlet> getOutlet() {
        return outlet;
    }

    public void setOutlet(List<Outlet> outlet) {
        this.outlet = outlet;
    }

    public List<Session> getSession() {
        return session;
    }

    public void setSession(List<Session> session) {
        this.session = session;
    }

    public List<Category> getCategory() {
        return category;
    }

    public void setCategory(List<Category> category) {
        this.category = category;
    }

}
