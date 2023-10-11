
package com.chillarcards.campuswallet.networkmodels.Attendance;

import java.util.List;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Code {

    @SerializedName("no_of_working_days")
    @Expose
    private String noOfWorkingDays;
    @SerializedName("no_of_leave_days")
    @Expose
    private String noOfLeaveDays;
    @SerializedName("leave_dates")
    @Expose
    private List<LeaveDate> leaveDates = null;

    /**
     * No args constructor for use in serialization
     * 
     */
    public Code() {
    }

    /**
     * 
     * @param leaveDates
     * @param noOfWorkingDays
     * @param noOfLeaveDays
     */
    public Code(String noOfWorkingDays, String noOfLeaveDays, List<LeaveDate> leaveDates) {
        super();
        this.noOfWorkingDays = noOfWorkingDays;
        this.noOfLeaveDays = noOfLeaveDays;
        this.leaveDates = leaveDates;
    }

    public String getNoOfWorkingDays() {
        return noOfWorkingDays;
    }

    public void setNoOfWorkingDays(String noOfWorkingDays) {
        this.noOfWorkingDays = noOfWorkingDays;
    }

    public String getNoOfLeaveDays() {
        return noOfLeaveDays;
    }

    public void setNoOfLeaveDays(String noOfLeaveDays) {
        this.noOfLeaveDays = noOfLeaveDays;
    }

    public List<LeaveDate> getLeaveDates() {
        return leaveDates;
    }

    public void setLeaveDates(List<LeaveDate> leaveDates) {
        this.leaveDates = leaveDates;
    }

}
