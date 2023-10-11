
package com.chillarcards.campuswallet.networkmodels.Attendance;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class LeaveDate {

    @SerializedName("leave_date")
    @Expose
    private String leaveDate;
    @SerializedName("leave_reason")
    @Expose
    private String leaveReason;
    @SerializedName("leave_status")
    @Expose
    private String leave_status;

    /**
     * No args constructor for use in serialization
     * 
     */
    public LeaveDate() {
    }

    /**
     * 
     * @param leaveReason
     * @param leaveDate
     */
    public LeaveDate(String leaveDate, String leaveReason) {
        super();
        this.leaveDate = leaveDate;
        this.leaveReason = leaveReason;
    }

    public String getLeaveDate() {
        return leaveDate;
    }

    public void setLeaveDate(String leaveDate) {
        this.leaveDate = leaveDate;
    }

    public String getLeaveReason() {
        return leaveReason;
    }

    public void setLeaveReason(String leaveReason) {
        this.leaveReason = leaveReason;
    }

    public String getLeave_status() {
        return leave_status;
    }

    public void setLeave_status(String leave_status) {
        this.leave_status = leave_status;
    }
}
