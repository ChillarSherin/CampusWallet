
package com.chillarcards.campuswallet.networkmodels.ExamResults;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Result {

    @SerializedName("skillName")
    @Expose
    private String skillName;
    @SerializedName("skillMark")
    @Expose
    private String skillMark;
    @SerializedName("skillGrade")
    @Expose
    private String skillGrade;

    /**
     * No args constructor for use in serialization
     * 
     */
    public Result() {
    }

    /**
     * 
     * @param skillName
     * @param skillGrade
     * @param skillMark
     */
    public Result(String skillName, String skillMark, String skillGrade) {
        super();
        this.skillName = skillName;
        this.skillMark = skillMark;
        this.skillGrade = skillGrade;
    }

    public String getSkillName() {
        return skillName;
    }

    public void setSkillName(String skillName) {
        this.skillName = skillName;
    }

    public String getSkillMark() {
        return skillMark;
    }

    public void setSkillMark(String skillMark) {
        this.skillMark = skillMark;
    }

    public String getSkillGrade() {
        return skillGrade;
    }

    public void setSkillGrade(String skillGrade) {
        this.skillGrade = skillGrade;
    }

}
