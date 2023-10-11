
package com.chillarcards.campuswallet.networkmodels.SubjectMessage;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Code {

    @SerializedName("messageID")
    @Expose
    private String messageID;
    @SerializedName("messageContent")
    @Expose
    private String messageContent;
    @SerializedName("messageCreatedBy")
    @Expose
    private String messageCreatedBy;
    @SerializedName("messageCreatorID")
    @Expose
    private String messageCreatorID;
    @SerializedName("messageDueDate")
    @Expose
    private String messageDueDate;
    @SerializedName("messageCreatedOn")
    @Expose
    private String messageCreatedOn;
    @SerializedName("imageExists")
    @Expose
    private Integer imageExists;
    @SerializedName("imageUrl")
    @Expose
    private String imageUrl;

    /**
     * No args constructor for use in serialization
     * 
     */
    public Code() {
    }

    /**
     * 
     * @param messageCreatedOn
     * @param messageDueDate
     * @param messageID
     * @param messageCreatedBy
     * @param imageUrl
     * @param messageContent
     * @param imageExists
     * @param messageCreatorID
     */
    public Code(String messageID, String messageContent, String messageCreatedBy, String messageCreatorID, String messageDueDate, String messageCreatedOn, Integer imageExists, String imageUrl) {
        super();
        this.messageID = messageID;
        this.messageContent = messageContent;
        this.messageCreatedBy = messageCreatedBy;
        this.messageCreatorID = messageCreatorID;
        this.messageDueDate = messageDueDate;
        this.messageCreatedOn = messageCreatedOn;
        this.imageExists = imageExists;
        this.imageUrl = imageUrl;
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

    public String getMessageCreatedBy() {
        return messageCreatedBy;
    }

    public void setMessageCreatedBy(String messageCreatedBy) {
        this.messageCreatedBy = messageCreatedBy;
    }

    public String getMessageCreatorID() {
        return messageCreatorID;
    }

    public void setMessageCreatorID(String messageCreatorID) {
        this.messageCreatorID = messageCreatorID;
    }

    public String getMessageDueDate() {
        return messageDueDate;
    }

    public void setMessageDueDate(String messageDueDate) {
        this.messageDueDate = messageDueDate;
    }

    public String getMessageCreatedOn() {
        return messageCreatedOn;
    }

    public void setMessageCreatedOn(String messageCreatedOn) {
        this.messageCreatedOn = messageCreatedOn;
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
