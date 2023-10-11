
package com.chillarcards.campuswallet.networkmodels.studentlist;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class ServerURLConstant {

    @SerializedName("base_url")
    @Expose
    private String baseUrl;
    @SerializedName("base_order_url")
    @Expose
    private String baseOrderUrl;
    @SerializedName("base_image_url")
    @Expose
    private String baseImageUrl;
    @SerializedName("base_url_nl")
    @Expose
    private String baseUrlNl;
    @SerializedName("base_url_xpay")
    @Expose
    private String baseUrlXpay;

    public String getBaseUrl() {
        return baseUrl;
    }

    public void setBaseUrl(String baseUrl) {
        this.baseUrl = baseUrl;
    }

    public String getBaseOrderUrl() {
        return baseOrderUrl;
    }

    public void setBaseOrderUrl(String baseOrderUrl) {
        this.baseOrderUrl = baseOrderUrl;
    }

    public String getBaseImageUrl() {
        return baseImageUrl;
    }

    public void setBaseImageUrl(String baseImageUrl) {
        this.baseImageUrl = baseImageUrl;
    }

    public String getBaseUrlNl() {
        return baseUrlNl;
    }

    public void setBaseUrlNl(String baseUrlNl) {
        this.baseUrlNl = baseUrlNl;
    }

    public String getBaseUrlXpay() {
        return baseUrlXpay;
    }

    public void setBaseUrlXpay(String baseUrlXpay) {
        this.baseUrlXpay = baseUrlXpay;
    }

}
