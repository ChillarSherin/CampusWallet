
package com.chillarcards.campuswallet.networkmodels.home;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class OnesignalKey {

    @SerializedName("key")
    @Expose
    private String key;
    @SerializedName("value")
    @Expose
    private Integer value;

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public Integer getValue() {
        return value;
    }

    public void setValue(Integer value) {
        this.value = value;
    }

}
