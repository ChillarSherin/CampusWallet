
package com.chillarcards.campuswallet.networkmodels.noticeboard;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Code {

    @SerializedName("noticeBoardID")
    @Expose
    private String noticeBoardID;
    @SerializedName("noticeCreatorID")
    @Expose
    private String noticeCreatorID;
    @SerializedName("noticeCreatedBy")
    @Expose
    private String noticeCreatedBy;
    @SerializedName("notice")
    @Expose
    private String notice;
    @SerializedName("created_date_time")
    @Expose
    private String createdDateTime;
    @SerializedName("noticeCreatedOn")
    @Expose
    private String noticeCreatedOn;
    @SerializedName("created_time")
    @Expose
    private String createdTime;
    @SerializedName("noticeDueDate")
    @Expose
    private String noticeDueDate;
    @SerializedName("imageExists")
    @Expose
    private Integer imageExists;
    @SerializedName("imageUrl")
    @Expose
    private String imageUrl;

    public String getNoticeBoardID() {
        return noticeBoardID;
    }

    public void setNoticeBoardID(String noticeBoardID) {
        this.noticeBoardID = noticeBoardID;
    }

    public String getNoticeCreatorID() {
        return noticeCreatorID;
    }

    public void setNoticeCreatorID(String noticeCreatorID) {
        this.noticeCreatorID = noticeCreatorID;
    }

    public String getNoticeCreatedBy() {
        return noticeCreatedBy;
    }

    public void setNoticeCreatedBy(String noticeCreatedBy) {
        this.noticeCreatedBy = noticeCreatedBy;
    }

    public String getNotice() {
        return notice;
    }

    public void setNotice(String notice) {
        this.notice = notice;
    }

    public String getCreatedDateTime() {
        return createdDateTime;
    }

    public void setCreatedDateTime(String createdDateTime) {
        this.createdDateTime = createdDateTime;
    }

    public String getNoticeCreatedOn() {
        return noticeCreatedOn;
    }

    public void setNoticeCreatedOn(String noticeCreatedOn) {
        this.noticeCreatedOn = noticeCreatedOn;
    }

    public String getCreatedTime() {
        return createdTime;
    }

    public void setCreatedTime(String createdTime) {
        this.createdTime = createdTime;
    }

    public String getNoticeDueDate() {
        return noticeDueDate;
    }

    public void setNoticeDueDate(String noticeDueDate) {
        this.noticeDueDate = noticeDueDate;
    }

    public Integer getImageExists() {
        return imageExists;
    }

    public void setImageExists(Integer imageExists) {
        this.imageExists = imageExists;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

}
