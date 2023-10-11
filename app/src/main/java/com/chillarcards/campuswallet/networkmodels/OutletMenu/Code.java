
package com.chillarcards.campuswallet.networkmodels.OutletMenu;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Code {

    @SerializedName("id")
    @Expose
    private String id;
    @SerializedName("outlet")
    @Expose
    private String outlet;

    /**
     * No args constructor for use in serialization
     * 
     */
    public Code() {
    }

    /**
     * 
     * @param id
     * @param outlet
     */
    public Code(String id, String outlet) {
        super();
        this.id = id;
        this.outlet = outlet;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getOutlet() {
        return outlet;
    }

    public void setOutlet(String outlet) {
        this.outlet = outlet;
    }

}
