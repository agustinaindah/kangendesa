package com.kangendesa.app.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by agustinaindah on 13 Februari 2019
 */
public class MBasicTrans {
    @SerializedName("key")
    @Expose
    private String key;
    @SerializedName("value")
    @Expose
    private String value;

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }
}
