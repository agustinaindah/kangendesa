package com.kangendesa.app.features.account_setting.verifyemail;

import com.google.gson.JsonObject;
import com.kangendesa.app.base.BaseView;

/**
 * Created by agustinaindah on 18 Februari 2019
 */
public interface VerifyEmailPresenter {

    void postVerifyEmail(JsonObject jsonRequest);

    interface View extends BaseView {
        void successVerifyEmail(JsonObject jsonRequest);

    }
}
