package com.kangendesa.app.features.auth.forgotpassword;

import com.google.gson.JsonObject;
import com.kangendesa.app.base.BaseView;

/**
 * Created by agustinaindah on 18 Januari 2019
 */
public interface ForgotPasswordPresenter {

    void forgotPassword(JsonObject jsonRequest);

    interface View extends BaseView {
        void successForgotPassword(JsonObject user);

    }
}
