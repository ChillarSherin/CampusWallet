
package com.chillarcards.campuswallet.networkmodels.ExamDetails;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class ExamlistStatus {

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
    public ExamlistStatus() {
    }

    /**
     * 
     * @param message **message**
     * @param code **code**
     */
    public ExamlistStatus(String code, String message) {
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
