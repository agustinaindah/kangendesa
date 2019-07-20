package com.kangendesa.app.utils;

import com.kangendesa.app.model.BaseResponse;

import retrofit2.Call;
import retrofit2.Response;

/**
 * Created by agustinaindah on 16 Januari 2019
 */
public interface ServiceInterface {

    Call<BaseResponse> callBackResponse(ApiService apiService);

    void showProgress();

    void hideProgress();

    void responseSuccess(Response<BaseResponse> response);

    void responseFailed(Response<BaseResponse> response);

    void failed(Throwable t);
}
