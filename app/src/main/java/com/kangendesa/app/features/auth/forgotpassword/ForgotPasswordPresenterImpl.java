package com.kangendesa.app.features.auth.forgotpassword;

import com.google.gson.JsonObject;
import com.kangendesa.app.KangenDesaApp;
import com.kangendesa.app.model.BaseResponse;
import com.kangendesa.app.utils.ApiService;
import com.kangendesa.app.utils.Helper;
import com.kangendesa.app.utils.ServiceInterface;

import retrofit2.Call;
import retrofit2.Response;

/**
 * Created by agustinaindah on 18 Januari 2019
 */
public class ForgotPasswordPresenterImpl implements ForgotPasswordPresenter {

    private View mView;

    public ForgotPasswordPresenterImpl(View mView) {
        this.mView = mView;
    }

    @Override
    public void forgotPassword(final JsonObject jsonRequest) {
        KangenDesaApp.getInstance().service(new ServiceInterface() {
            @Override
            public Call<BaseResponse> callBackResponse(ApiService apiService) {
                return apiService.postResetpassword(jsonRequest);
            }

            @Override
            public void showProgress() {
                mView.showProgress();
            }

            @Override
            public void hideProgress() {
                mView.hideProgress();
            }

            @Override
            public void responseSuccess(Response<BaseResponse> response) {
                String data = Helper.getGsonInstance().toJson(response.body());
                JsonObject jsonData = Helper.parseToJsonObject(data);

                mView.successForgotPassword(jsonData);
//                mView.showMessage(jsonData.get("response").getAsString());
            }

            @Override
            public void responseFailed(Response<BaseResponse> response) {
                try {
                    JsonObject jsonRes = Helper.parseToJsonObject(response.errorBody().string());
                    mView.showMessage(jsonRes.get("response").getAsString());
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void failed(Throwable t) {
                mView.notConnect(t.getLocalizedMessage());
            }
        });
    }
}
