
package com.chillarcards.campuswallet.networkmodels.PreOrderTimingsModule;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class PreOderTimingCode {

    @SerializedName("schoolID")
    @Expose
    private String schoolID;
    @SerializedName("preorderTimingID")
    @Expose
    private String preorderTimingID;
    @SerializedName("preorderTimingKey")
    @Expose
    private String preorderTimingKey;
    @SerializedName("preorderTimingName")
    @Expose
    private String preorderTimingName;
    @SerializedName("preorderTimingStatus")
    @Expose
    private String preorderTimingStatus;

    /**
     * No args constructor for use in serialization
     * 
     */
    public PreOderTimingCode() {
    }

    /**
     * 
     * @param preorderTimingStatus **preorderTimingStatus**
     * @param preorderTimingKey **preorderTimingKey**
     * @param preorderTimingName **preorderTimingName**
     * @param preorderTimingID **preorderTimingID**
     * @param schoolID **schoolID**
     */
    public PreOderTimingCode(String schoolID, String preorderTimingID, String preorderTimingKey, String preorderTimingName, String preorderTimingStatus) {
        super();
        this.schoolID = schoolID;
        this.preorderTimingID = preorderTimingID;
        this.preorderTimingKey = preorderTimingKey;
        this.preorderTimingName = preorderTimingName;
        this.preorderTimingStatus = preorderTimingStatus;
    }

    public String getSchoolID() {
        return schoolID;
    }

    public void setSchoolID(String schoolID) {
        this.schoolID = schoolID;
    }

    public String getPreorderTimingID() {
        return preorderTimingID;
    }

    public void setPreorderTimingID(String preorderTimingID) {
        this.preorderTimingID = preorderTimingID;
    }

    public String getPreorderTimingKey() {
        return preorderTimingKey;
    }

    public void setPreorderTimingKey(String preorderTimingKey) {
        this.preorderTimingKey = preorderTimingKey;
    }

    public String getPreorderTimingName() {
        return preorderTimingName;
    }

    public void setPreorderTimingName(String preorderTimingName) {
        this.preorderTimingName = preorderTimingName;
    }

    public String getPreorderTimingStatus() {
        return preorderTimingStatus;
    }

    public void setPreorderTimingStatus(String preorderTimingStatus) {
        this.preorderTimingStatus = preorderTimingStatus;
    }

}
