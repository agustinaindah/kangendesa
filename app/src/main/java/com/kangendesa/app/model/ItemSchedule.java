package com.kangendesa.app.model;

/**
 * Created by agustinaindah on 22 Februari 2019
 */
public class ItemSchedule {

    private String firstTime;
    private String lastTime;
    private String schedule;

    public ItemSchedule(String firstTime, String lastTime, String schedule) {
        this.firstTime = firstTime;
        this.lastTime = lastTime;
        this.schedule = schedule;
    }

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
