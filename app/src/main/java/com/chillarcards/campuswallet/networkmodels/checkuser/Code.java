
package com.chillarcards.campuswallet.networkmodels.checkuser;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Code {

    @SerializedName("parentEmail")
    @Expose
    private String parentEmail;
    @SerializedName("parentName")
    @Expose
    private String parentName;
    @SerializedName("bypass")
    @Expose
    private String bypass;

    public String getParentEmail() {
        return parentEmail;
    }

    public void setParentEmail(String parentEmail) {
        this.parentEmail = parentEmail;
    }

    public String getParentName() {
        return parentName;
    }

    public void setParentName(String parentName) {
        this.parentName = parentName;
    }

    public String getBypass() {
        return bypass;
    }

    public void setBypass(String bypass) {
        this.bypass = bypass;
    }

}
