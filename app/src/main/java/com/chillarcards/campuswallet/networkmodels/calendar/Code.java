
package com.chillarcards.campuswallet.networkmodels.calendar;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Code {

    @SerializedName("type")
    @Expose
    private String type;
    @SerializedName("messageDueDate")
    @Expose
    private String messageDueDate;

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getMessageDueDate() {
        return messageDueDate;
    }

    public void setMessageDueDate(String messageDueDate) {
        this.messageDueDate = messageDueDate;
    }

}
