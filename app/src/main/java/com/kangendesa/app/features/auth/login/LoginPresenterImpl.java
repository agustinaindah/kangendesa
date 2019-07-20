package com.kangendesa.app.features.auth.login;

import android.util.Log;

import com.google.gson.JsonObject;
import com.kangendesa.app.KangenDesaApp;
import com.kangendesa.app.model.BaseResponse;
import com.kangendesa.app.utils.ApiService;
import com.kangendesa.app.utils.Consts;
import com.kangendesa.app.utils.Helper;
import com.kangendesa.app.utils.ServiceInterface;
import com.kangendesa.app.utils.SharedPref;

import retrofit2.Call;
import retrofit2.Response;

/**
 * Created by agustinaindah on 18 Januari 2019
 */
public class LoginPresenterImpl implements LoginPresenter {

    private View mView;

    public LoginPresenterImpl(View mView) {
        this.mView = mView;
    }

    @Override
    public void login(final JsonObject jsonRequest) {
        KangenDesaApp.getInstance().service(new ServiceInterface() {
            @Override
            public Call<BaseResponse> callBackResponse(ApiService apiService) {
                return apiService.postLogin(jsonRequest);
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
                try {
                    String data = Helper.getGsonInstance().toJson(response.body());
                    JsonObject jsonData = Helper.parseToJsonObject(data);
                    JsonObject jsonDataUser = jsonData.getAsJsonObject("data");

                    saveDataToPref(jsonDataUser);

                    mView.successLogin(jsonData);

                }catch (Exception e){
                    e.printStackTrace();
                    String message = Helper.getGsonInstance().toJson(response.body().getResponse());
                    mView.showMessage(message);
                }
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
                mView.showMessage(t.getLocalizedMessage());
            }
        });
    }

    private void saveDataToPref(JsonObject jsonData) {
        String token = jsonData.get("user_oauth_token").getAsString();
        JsonObject user = jsonData.get("user").getAsJsonObject();
        String iduser = user.get("ID").getAsString();
        String userName = user.get("user_login").getAsString();
        String email = user.get("user_email").getAsString();
        String firstName = user.get("m_first_name").getAsString();
        String lastName = user.get("m_last_name").getAsString();
        String displayName = user.get("display_name").getAsString();
        String image = user.get("m_profile_image_url").getAsString();
        String address = user.get("m_kd_alamat").getAsString();


        SharedPref.saveString(Consts.TOKEN, token);
        SharedPref.saveString(Consts.ID, iduser);
        SharedPref.saveString(Consts.USERNAME, userName);
        SharedPref.saveString(Consts.EMAIL, email);
        SharedPref.saveString(Consts.FIRSTNAME, firstName);
        SharedPref.saveString(Consts.LASTNAME, lastName);
        SharedPref.saveString(Consts.DISPLAYNAME, displayName);
        SharedPref.saveString(Consts.AVATAR, image);
        SharedPref.saveString(Consts.ADDRESS, address);
    }
}
