package com.kangendesa.app.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * Created by agustinaindah on 01 Februari 2019
 */
public class Taxes {

    @SerializedName("total")
    @Expose
    private List<Object> total = null;
    @SerializedName("subtotal")
    @Expose
    private List<Object> subtotal = null;

    public List<Object> getTotal() {
        return total;
    }

    public void setTotal(List<Object> total) {
        this.total = total;
    }

    public List<Object> getSubtotal() {
        return subtotal;
    }

    public void setSubtotal(List<Object> subtotal) {
        this.subtotal = subtotal;
    }
}
