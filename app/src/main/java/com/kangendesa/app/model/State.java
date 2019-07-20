package com.kangendesa.app.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by agustinaindah on 29 Januari 2019
 */
public class State {

    @SerializedName("state_code")
    @Expose
    private String stateCode;
    @SerializedName("state_name")
    @Expose
    private String stateName;

    public String getStateCode() {
        return stateCode;
    }

    public void setStateCode(String stateCode) {
        this.stateCode = stateCode;
    }

    public String getStateName() {
        return stateName;
    }

    public void setStateName(String stateName) {
        this.stateName = stateName;
    }

    @Override
    public String toString() {
        return stateName;
    }

}
