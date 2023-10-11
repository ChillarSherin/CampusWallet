
package com.chillarcards.campuswallet.networkmodels.TimeTable;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Code {

    @SerializedName("subjectId")
    @Expose
    private String subjectId;
    @SerializedName("subjectName")
    @Expose
    private String subjectName;
    @SerializedName("teacher")
    @Expose
    private String teacher;
    @SerializedName("day")
    @Expose
    private String day;
    @SerializedName("order")
    @Expose
    private String order;

    /**
     * No args constructor for use in serialization
     * 
     */
    public Code() {
    }

    /**
     * 
     * @param subjectName
     * @param order
     * @param subjectId
     * @param day
     * @param teacher
     */
    public Code(String subjectId, String subjectName, String teacher, String day, String order) {
        super();
        this.subjectId = subjectId;
        this.subjectName = subjectName;
        this.teacher = teacher;
        this.day = day;
        this.order = order;
    }

    public String getSubjectId() {
        return subjectId;
    }

    public void setSubjectId(String subjectId) {
        this.subjectId = subjectId;
    }

    public String getSubjectName() {
        return subjectName;
    }

    public void setSubjectName(String subjectName) {
        this.subjectName = subjectName;
    }

    public String getTeacher() {
        return teacher;
    }

    public void setTeacher(String teacher) {
        this.teacher = teacher;
    }

    public String getDay() {
        return day;
    }

    public void setDay(String day) {
        this.day = day;
    }

    public String getOrder() {
        return order;
    }

    public void setOrder(String order) {
        this.order = order;
    }

}
