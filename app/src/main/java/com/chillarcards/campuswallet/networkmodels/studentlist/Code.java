
package com.chillarcards.campuswallet.networkmodels.studentlist;

import java.util.List;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Code {

    @SerializedName("studentID")
    @Expose
    private String studentID;
    @SerializedName("studentUserID")
    @Expose
    private String studentUserID;
    @SerializedName("studentCode")
    @Expose
    private String studentCode;
    @SerializedName("studentFullName")
    @Expose
    private String studentFullName;
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
    @SerializedName("school")
    @Expose
    private String school;
    @SerializedName("schoolID")
    @Expose
    private String schoolID;
    @SerializedName("type")
    @Expose
    private String type;
    @SerializedName("onesignal_keys")
    @Expose
    private List<OnesignalKey> onesignalKeys = null;
    @SerializedName("serverURLConstant")
    @Expose
    private ServerURLConstant serverURLConstant;

    public String getStudentID() {
        return studentID;
    }

    public void setStudentID(String studentID) {
        this.studentID = studentID;
    }

    public String getStudentUserID() {
        return studentUserID;
    }

    public void setStudentUserID(String studentUserID) {
        this.studentUserID = studentUserID;
    }

    public String getStudentCode() {
        return studentCode;
    }

    public void setStudentCode(String studentCode) {
        this.studentCode = studentCode;
    }

    public String getStudentFullName() {
        return studentFullName;
    }

    public void setStudentFullName(String studentFullName) {
        this.studentFullName = studentFullName;
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

    public String getSchool() {
        return school;
    }

    public void setSchool(String school) {
        this.school = school;
    }

    public String getSchoolID() {
        return schoolID;
    }

    public void setSchoolID(String schoolID) {
        this.schoolID = schoolID;
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

    public ServerURLConstant getServerURLConstant() {
        return serverURLConstant;
    }

    public void setServerURLConstant(ServerURLConstant serverURLConstant) {
        this.serverURLConstant = serverURLConstant;
    }

}
