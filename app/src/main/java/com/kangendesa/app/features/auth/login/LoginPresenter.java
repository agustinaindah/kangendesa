package com.kangendesa.app.features.auth.login;

import com.google.gson.JsonObject;
import com.kangendesa.app.base.BaseView;

/**
 * Created by agustinaindah on 18 Januari 2019
 */
public interface LoginPresenter {

    void login(JsonObject jsonRequest);

    interface View extends BaseView {
        void successLogin(JsonObject user);

    }
}
