
package com.chillarcards.campuswallet.networkmodels.ExamResults;

import java.util.List;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Code {

    @SerializedName("examID")
    @Expose
    private String examID;
    @SerializedName("examName")
    @Expose
    private String examName;
    @SerializedName("subjectname")
    @Expose
    private String subjectname;
    @SerializedName("teacherName")
    @Expose
    private String teacherName;
    @SerializedName("examDate")
    @Expose
    private String examDate;
    @SerializedName("result")
    @Expose
    private List<Result> result = null;
    @SerializedName("TotalMark")
    @Expose
    private String totalMark;

    /**
     * No args constructor for use in serialization
     * 
     */
    public Code() {
    }

    /**
     * 
     * @param result
     * @param totalMark
     * @param examDate
     * @param examID
     * @param subjectname
     * @param examName
     * @param teacherName
     */
    public Code(String examID, String examName, String subjectname, String teacherName, String examDate, List<Result> result, String totalMark) {
        super();
        this.examID = examID;
        this.examName = examName;
        this.subjectname = subjectname;
        this.teacherName = teacherName;
        this.examDate = examDate;
        this.result = result;
        this.totalMark = totalMark;
    }

    public String getExamID() {
        return examID;
    }

    public void setExamID(String examID) {
        this.examID = examID;
    }

    public String getExamName() {
        return examName;
    }

    public void setExamName(String examName) {
        this.examName = examName;
    }

    public String getSubjectname() {
        return subjectname;
    }

    public void setSubjectname(String subjectname) {
        this.subjectname = subjectname;
    }

    public String getTeacherName() {
        return teacherName;
    }

    public void setTeacherName(String teacherName) {
        this.teacherName = teacherName;
    }

    public String getExamDate() {
        return examDate;
    }

    public void setExamDate(String examDate) {
        this.examDate = examDate;
    }

    public List<Result> getResult() {
        return result;
    }

    public void setResult(List<Result> result) {
        this.result = result;
    }

    public String getTotalMark() {
        return totalMark;
    }

    public void setTotalMark(String totalMark) {
        this.totalMark = totalMark;
    }

}
