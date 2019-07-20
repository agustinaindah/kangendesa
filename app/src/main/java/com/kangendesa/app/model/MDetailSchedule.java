package com.kangendesa.app.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by agustinaindah on 21 Januari 2019
 */
public class MDetailSchedule {

    @SerializedName("first_time")
    @Expose
    private String firstTime;
    @SerializedName("last_time")
    @Expose
    private String lastTime;
    @SerializedName("schedule")
    @Expose
    private String schedule;

    public String getFirstTime() {
        return firstTime;
    }

    public void setFirstTime(String firstTime) {
        this.firstTime = firstTime;
    }

    public String getLastTime() {
        return lastTime;
    }

    public void setLastTime(String lastTime) {
        this.lastTime = lastTime;
    }

    public String getSchedule() {
        return schedule;
    }

    public void setSchedule(String schedule) {
        this.schedule = schedule;
    }
}
