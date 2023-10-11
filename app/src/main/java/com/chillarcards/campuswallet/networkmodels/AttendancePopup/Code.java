
package com.chillarcards.campuswallet.networkmodels.AttendancePopup;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Code {

    @SerializedName("periodNo")
    @Expose
    private String periodNo;
    @SerializedName("periodName")
    @Expose
    private String periodName;

    public String getPeriodNo() {
        return periodNo;
    }

    public void setPeriodNo(String periodNo) {
        this.periodNo = periodNo;
    }

    public String getPeriodName() {
        return periodName;
    }

    public void setPeriodName(String periodName) {
        this.periodName = periodName;
    }

}
