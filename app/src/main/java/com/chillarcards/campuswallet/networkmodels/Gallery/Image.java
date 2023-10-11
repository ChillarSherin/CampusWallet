
package com.chillarcards.campuswallet.networkmodels.Gallery;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Image {

    @SerializedName("imageID")
    @Expose
    private String imageID;
    @SerializedName("imageURL")
    @Expose
    private String imageURL;

    /**
     * No args constructor for use in serialization
     * 
     */
    public Image() {
    }

    /**
     * 
     * @param imageID
     * @param imageURL
     */
    public Image(String imageID, String imageURL) {
        super();
        this.imageID = imageID;
        this.imageURL = imageURL;
    }

    public String getImageID() {
        return imageID;
    }

    public void setImageID(String imageID) {
        this.imageID = imageID;
    }

    public String getImageURL() {
        return imageURL;
    }

    public void setImageURL(String imageURL) {
        this.imageURL = imageURL;
    }

}
