
package com.chillarcards.campuswallet.networkmodels.ParentMessages;

import java.util.List;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class MessageToTeacher {

    @SerializedName("code")
    @Expose
    private List<Code> code = null;
    @SerializedName("status")
    @Expose
    private Status status;

    /**
     * No args constructor for use in serialization
     * 
     */
    public MessageToTeacher() {
    }

    /**
     * 
     * @param status
     * @param code
     */
    public MessageToTeacher(List<Code> code, Status status) {
        super();
        this.code = code;
        this.status = status;
    }

    public List<Code> getCode() {
        return code;
    }

    public void setCode(List<Code> code) {
        this.code = code;
    }

    public Status getStatus() {
        return status;
    }

    public void setStatus(Status status) {
        this.status = status;
    }

}
