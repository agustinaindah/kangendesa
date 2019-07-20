package com.kangendesa.app.model;

/**
 * Created by agustinaindah on 22 Februari 2019
 */
public class ItemFaq {

    private String title;
    private String desc;

    public ItemFaq(String title, String desc) {
        this.title = title;
        this.desc = desc;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }
}
