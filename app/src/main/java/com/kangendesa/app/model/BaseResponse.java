package com.kangendesa.app.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by agustinaindah on 16 Januari 2019
 */
public class BaseResponse {

    @SerializedName("status")
    @Expose
    private Integer status;
    @SerializedName("response")
    @Expose
    private String response;
    @SerializedName("data")
    @Expose
    private Object data;

    /**
     * @return The status
     */
    public Integer getStatus() {
        return status;
    }

    /**
     * @param status The status
     */
    public void setStatus(Integer status) {
        this.status = status;
    }

    /**
     * @return The response
     */
    public String getResponse() {
        return response;
    }

    /**
     * @param response The message
     */
    public void setResponse(String response) {
        this.response = response;
    }

    /**
     * @return The data
     */
    public Object getData() {
        return data;
    }

    /**
     * @param data The data
     */
    public void setData(Object data) {
        this.data = data;
    }
}
