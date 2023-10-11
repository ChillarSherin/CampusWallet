package com.chillarcards.campuswallet.Diary.EDiary;

public class ReasonModel {
    public String getReasonID() {
        return reasonID;
    }

    public void setReasonID(String reasonID) {
        this.reasonID = reasonID;
    }

    public String getReasonName() {
        return reasonName;
    }

    public void setReasonName(String reasonName) {
        this.reasonName = reasonName;
    }

    private String reasonID,reasonName;

    public ReasonModel(String reasonID, String reasonName) {
        this.reasonID = reasonID;
        this.reasonName = reasonName;
    }

    public ReasonModel() {
    }
}
