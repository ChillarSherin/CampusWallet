
package com.chillarcards.campuswallet.networkmodels.Gallery;

import java.util.List;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Code {

    @SerializedName("teacherTimelineID")
    @Expose
    private String teacherTimelineID;
    @SerializedName("teacherTimelineDate")
    @Expose
    private String teacherTimelineDate;
    @SerializedName("teacherTimelineData")
    @Expose
    private String teacherTimelineData;
    @SerializedName("images")
    @Expose
    private List<Image> images = null;

    /**
     * No args constructor for use in serialization
     * 
     */
    public Code() {
    }

    /**
     * 
     * @param teacherTimelineID
     * @param teacherTimelineData
     * @param images
     * @param teacherTimelineDate
     */
    public Code(String teacherTimelineID, String teacherTimelineDate, String teacherTimelineData, List<Image> images) {
        super();
        this.teacherTimelineID = teacherTimelineID;
        this.teacherTimelineDate = teacherTimelineDate;
        this.teacherTimelineData = teacherTimelineData;
        this.images = images;
    }

    public String getTeacherTimelineID() {
        return teacherTimelineID;
    }

    public void setTeacherTimelineID(String teacherTimelineID) {
        this.teacherTimelineID = teacherTimelineID;
    }

    public String getTeacherTimelineDate() {
        return teacherTimelineDate;
    }

    public void setTeacherTimelineDate(String teacherTimelineDate) {
        this.teacherTimelineDate = teacherTimelineDate;
    }

    public String getTeacherTimelineData() {
        return teacherTimelineData;
    }

    public void setTeacherTimelineData(String teacherTimelineData) {
        this.teacherTimelineData = teacherTimelineData;
    }

    public List<Image> getImages() {
        return images;
    }

    public void setImages(List<Image> images) {
        this.images = images;
    }

}
