
package com.chillarcards.campuswallet.networkmodels.ParentMessages;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Code {

    @SerializedName("messageID")
    @Expose
    private String messageID;
    @SerializedName("messageContent")
    @Expose
    private String messageContent;
    @SerializedName("readStatus")
    @Expose
    private String readStatus;
    @SerializedName("messageCreatedOn")
    @Expose
    private String messageCreatedOn;

    /**
     * No args constructor for use in serialization
     * 
     */
    public Code() {
    }

    /**
     * 
     * @param messageCreatedOn
     * @param readStatus
     * @param messageID
     * @param messageContent
     */
    public Code(String messageID, String messageContent, String readStatus, String messageCreatedOn) {
        super();
        this.messageID = messageID;
        this.messageContent = messageContent;
        this.readStatus = readStatus;
        this.messageCreatedOn = messageCreatedOn;
    }

    public String getMessageID() {
        return messageID;
    }

    public void setMessageID(String messageID) {
        this.messageID = messageID;
    }

    public String getMessageContent() {
        return messageContent;
    }

    public void setMessageContent(String messageContent) {
        this.messageContent = messageContent;
    }

    public String getReadStatus() {
        return readStatus;
    }

    public void setReadStatus(String readStatus) {
        this.readStatus = readStatus;
    }

    public String getMessageCreatedOn() {
        return messageCreatedOn;
    }

    public void setMessageCreatedOn(String messageCreatedOn) {
        this.messageCreatedOn = messageCreatedOn;
    }

}
