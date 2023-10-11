
package com.chillarcards.campuswallet.networkmodels.calendarevent;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Code {

    @SerializedName("messageContent")
    @Expose
    private String messageContent;
    @SerializedName("messageCreatedBy")
    @Expose
    private String messageCreatedBy;
    @SerializedName("messageCreatorType")
    @Expose
    private String messageCreatorType;
    @SerializedName("messageCreatedOn")
    @Expose
    private String messageCreatedOn;
    @SerializedName("messageDueDate")
    @Expose
    private String messageDueDate;

    public String getMessageContent() {
        return messageContent;
    }

    public void setMessageContent(String messageContent) {
        this.messageContent = messageContent;
    }

    public String getMessageCreatedBy() {
        return messageCreatedBy;
    }

    public void setMessageCreatedBy(String messageCreatedBy) {
        this.messageCreatedBy = messageCreatedBy;
    }

    public String getMessageCreatorType() {
        return messageCreatorType;
    }

    public void setMessageCreatorType(String messageCreatorType) {
        this.messageCreatorType = messageCreatorType;
    }

    public String getMessageCreatedOn() {
        return messageCreatedOn;
    }

    public void setMessageCreatedOn(String messageCreatedOn) {
        this.messageCreatedOn = messageCreatedOn;
    }

    public String getMessageDueDate() {
        return messageDueDate;
    }

    public void setMessageDueDate(String messageDueDate) {
        this.messageDueDate = messageDueDate;
    }

}
