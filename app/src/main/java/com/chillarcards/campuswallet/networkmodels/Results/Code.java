
package com.chillarcards.campuswallet.networkmodels.Results;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Code {

    @SerializedName("subjectID")
    @Expose
    private String subjectID;
    @SerializedName("subjectName")
    @Expose
    private String subjectName;

    /**
     * No args constructor for use in serialization
     * 
     */
    public Code() {
    }

    /**
     * 
     * @param subjectName
     * @param subjectID
     */
    public Code(String subjectID, String subjectName) {
        super();
        this.subjectID = subjectID;
        this.subjectName = subjectName;
    }

    public String getSubjectID() {
        return subjectID;
    }

    public void setSubjectID(String subjectID) {
        this.subjectID = subjectID;
    }

    public String getSubjectName() {
        return subjectName;
    }

    public void setSubjectName(String subjectName) {
        this.subjectName = subjectName;
    }

}
