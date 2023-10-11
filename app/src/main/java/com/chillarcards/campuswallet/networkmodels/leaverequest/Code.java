
package com.chillarcards.campuswallet.networkmodels.leaverequest;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Code {

    @SerializedName("leaveRequestID")
    @Expose
    private String leaveRequestID;
    @SerializedName("reason")
    @Expose
    private String reason;
    @SerializedName("fromDate")
    @Expose
    private String fromDate;
    @SerializedName("toDate")
    @Expose
    private String toDate;
    @SerializedName("readStatus")
    @Expose
    private String readStatus;
    @SerializedName("createdDate")
    @Expose
    private String createdDate;

    public String getLeaveRequestID() {
        return leaveRequestID;
    }

    public void setLeaveRequestID(String leaveRequestID) {
        this.leaveRequestID = leaveRequestID;
    }

    public String getReason() {
        return reason;
    }

    public void setReason(String reason) {
        this.reason = reason;
    }

    public String getFromDate() {
        return fromDate;
    }

    public void setFromDate(String fromDate) {
        this.fromDate = fromDate;
    }

    public String getToDate() {
        return toDate;
    }

    public void setToDate(String toDate) {
        this.toDate = toDate;
    }

    public String getReadStatus() {
        return readStatus;
    }

    public void setReadStatus(String readStatus) {
        this.readStatus = readStatus;
    }

    public String getCreatedDate() {
        return createdDate;
    }

    public void setCreatedDate(String createdDate) {
        this.createdDate = createdDate;
    }

}
