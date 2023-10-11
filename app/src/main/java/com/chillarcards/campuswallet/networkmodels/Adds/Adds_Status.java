
package com.chillarcards.campuswallet.networkmodels.Adds;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Adds_Status {

    @SerializedName("code")
    @Expose
    private String code;
    @SerializedName("message")
    @Expose
    private String message;

    /**
     * No args constructor for use in serialization
     * 
     */
    public Adds_Status() {
    }

    /**
     * 
     * @param message **message**
     * @param code **code**
     */
    public Adds_Status(String code, String message) {
        super();
        this.code = code;
        this.message = message;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

}
