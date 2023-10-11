
package com.chillarcards.campuswallet.networkmodels.SendResonToTeacher;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Code {

    @SerializedName("ID")
    @Expose
    private Integer iD;

    /**
     * No args constructor for use in serialization
     * 
     */
    public Code() {
    }

    /**
     * 
     * @param iD
     */
    public Code(Integer iD) {
        super();
        this.iD = iD;
    }

    public Integer getID() {
        return iD;
    }

    public void setID(Integer iD) {
        this.iD = iD;
    }

}
