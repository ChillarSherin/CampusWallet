
package com.chillarcards.campuswallet.networkmodels.ExamDetails;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class ExamlistCode {

    @SerializedName("examID")
    @Expose
    private String examID;
    @SerializedName("examDate")
    @Expose
    private String examDate;
    @SerializedName("examName")
    @Expose
    private String examName;
    @SerializedName("examTotalMark")
    @Expose
    private String examTotalMark;
    @SerializedName("class_avg ")
    @Expose
    private String classAvg;
    @SerializedName("student_score")
    @Expose
    private Float studentScore;

    /**
     * No args constructor for use in serialization
     * 
     */
    public ExamlistCode() {
    }

    /**
     * 
     * @param examTotalMark **examTotalMark**
     * @param examDate **examDate**
     * @param classAvg **classAvg**
     * @param examID **examID**
     * @param examName **examName**
     * @param studentScore **studentScore**
     */
    public ExamlistCode(String examID, String examDate, String examName, String examTotalMark, String classAvg, Float studentScore) {
        super();
        this.examID = examID;
        this.examDate = examDate;
        this.examName = examName;
        this.examTotalMark = examTotalMark;
        this.classAvg = classAvg;
        this.studentScore = studentScore;
    }

    public String getExamID() {
        return examID;
    }

    public void setExamID(String examID) {
        this.examID = examID;
    }

    public String getExamDate() {
        return examDate;
    }

    public void setExamDate(String examDate) {
        this.examDate = examDate;
    }

    public String getExamName() {
        return examName;
    }

    public void setExamName(String examName) {
        this.examName = examName;
    }

    public String getExamTotalMark() {
        return examTotalMark;
    }

    public void setExamTotalMark(String examTotalMark) {
        this.examTotalMark = examTotalMark;
    }

    public String getClassAvg() {
        return classAvg;
    }

    public void setClassAvg(String classAvg) {
        this.classAvg = classAvg;
    }

    public Float getStudentScore() {
        return studentScore;
    }

    public void setStudentScore(Float studentScore) {
        this.studentScore = studentScore;
    }

}
