
package com.chillarcards.campuswallet.networkmodels.home;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class ActiveNewsletter {

    @SerializedName("newsletterHeading")
    @Expose
    private String newsletterHeading;
    @SerializedName("newsletterImageURL")
    @Expose
    private String newsletterImageURL;
    @SerializedName("newsletterWebURL")
    @Expose
    private String newsletterWebURL;
    @SerializedName("chillarNewsletterID")
    @Expose
    private String chillarNewsletterID;

    public String getNewsletterHeading() {
        return newsletterHeading;
    }

    public void setNewsletterHeading(String newsletterHeading) {
        this.newsletterHeading = newsletterHeading;
    }

    public String getNewsletterImageURL() {
        return newsletterImageURL;
    }

    public void setNewsletterImageURL(String newsletterImageURL) {
        this.newsletterImageURL = newsletterImageURL;
    }

    public String getNewsletterWebURL() {
        return newsletterWebURL;
    }

    public void setNewsletterWebURL(String newsletterWebURL) {
        this.newsletterWebURL = newsletterWebURL;
    }

    public String getChillarNewsletterID() {
        return chillarNewsletterID;
    }

    public void setChillarNewsletterID(String chillarNewsletterID) {
        this.chillarNewsletterID = chillarNewsletterID;
    }

}
