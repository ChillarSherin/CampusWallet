
package com.chillarcards.campuswallet.networkmodels.ExamDetails;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class ExamlistDetails {

    @SerializedName("code")
    @Expose
    private List<ExamlistCode> code = null;
    @SerializedName("status")
    @Expose
    private ExamlistStatus status;

    /**
     * No args constructor for use in serialization
     * 
     */
    public ExamlistDetails() {
    }

    /**
     * 
     * @param status **status**
     * @param code **code**
     */
    public ExamlistDetails(List<ExamlistCode> code, ExamlistStatus status) {
        super();
        this.code = code;
        this.status = status;
    }

    public List<ExamlistCode> getCode() {
        return code;
    }

    public void setCode(List<ExamlistCode> code) {
        this.code = code;
    }

    public ExamlistStatus getStatus() {
        return status;
    }

    public void setStatus(ExamlistStatus status) {
        this.status = status;
    }

}
