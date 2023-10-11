
package com.chillarcards.campuswallet.networkmodels.Adds;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Adds {

    @SerializedName("status")
    @Expose
    private Adds_Status status;

    /**
     * No args constructor for use in serialization
     * 
     */
    public Adds() {
    }

    /**
     * 
     * @param status **status**
     */
    public Adds(Adds_Status status) {
        super();
        this.status = status;
    }

    public Adds_Status getStatus() {
        return status;
    }

    public void setStatus(Adds_Status status) {
        this.status = status;
    }

}
