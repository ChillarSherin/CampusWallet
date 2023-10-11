
package com.chillarcards.campuswallet.networkmodels.home;

import java.util.List;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class StudentData {

    @SerializedName("studentName")
    @Expose
    private String studentName;
    @SerializedName("schoolName")
    @Expose
    private String schoolName;
    @SerializedName("Standard")
    @Expose
    private String standard;
    @SerializedName("StandardID")
    @Expose
    private String standardID;
    @SerializedName("standardDivision")
    @Expose
    private String standardDivision;
    @SerializedName("standardDivisonID")
    @Expose
    private String standardDivisonID;
    @SerializedName("type")
    @Expose
    private String type;
    @SerializedName("onesignal_keys")
    @Expose
    private List<OnesignalKey> onesignalKeys = null;

    public String getStudentName() {
        return studentName;
    }

    public void setStudentName(String studentName) {
        this.studentName = studentName;
    }

    public String getSchoolName() {
        return schoolName;
    }

    public void setSchoolName(String schoolName) {
        this.schoolName = schoolName;
    }

    public String getStandard() {
        return standard;
    }

    public void setStandard(String standard) {
        this.standard = standard;
    }

    public String getStandardID() {
        return standardID;
    }

    public void setStandardID(String standardID) {
        this.standardID = standardID;
    }

    public String getStandardDivision() {
        return standardDivision;
    }

    public void setStandardDivision(String standardDivision) {
        this.standardDivision = standardDivision;
    }

    public String getStandardDivisonID() {
        return standardDivisonID;
    }

    public void setStandardDivisonID(String standardDivisonID) {
        this.standardDivisonID = standardDivisonID;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public List<OnesignalKey> getOnesignalKeys() {
        return onesignalKeys;
    }

    public void setOnesignalKeys(List<OnesignalKey> onesignalKeys) {
        this.onesignalKeys = onesignalKeys;
    }

}
