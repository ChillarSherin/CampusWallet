
package com.chillarcards.campuswallet.networkmodels.Results;

import java.util.List;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class ResultMain {

    @SerializedName("standardDivisionID")
    @Expose
    private String standardDivisionID;
    @SerializedName("collegeClassID")
    @Expose
    private String collegeClassID;
    @SerializedName("teacherID")
    @Expose
    private String teacherID;
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
    public ResultMain() {
    }

    /**
     * 
     * @param teacherID
     * @param standardDivisionID
     * @param status
     * @param collegeClassID
     * @param code
     */
    public ResultMain(String standardDivisionID, String collegeClassID, String teacherID, List<Code> code, Status status) {
        super();
        this.standardDivisionID = standardDivisionID;
        this.collegeClassID = collegeClassID;
        this.teacherID = teacherID;
        this.code = code;
        this.status = status;
    }

    public String getStandardDivisionID() {
        return standardDivisionID;
    }

    public void setStandardDivisionID(String standardDivisionID) {
        this.standardDivisionID = standardDivisionID;
    }

    public String getCollegeClassID() {
        return collegeClassID;
    }

    public void setCollegeClassID(String collegeClassID) {
        this.collegeClassID = collegeClassID;
    }

    public String getTeacherID() {
        return teacherID;
    }

    public void setTeacherID(String teacherID) {
        this.teacherID = teacherID;
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
