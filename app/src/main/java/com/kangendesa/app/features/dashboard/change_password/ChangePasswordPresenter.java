package com.kangendesa.app.features.dashboard.change_password;

import com.google.gson.JsonObject;
import com.kangendesa.app.base.BaseView;

/**
 * Created by agustinaindah on 12 Februari 2019
 */
public interface ChangePasswordPresenter {

    void postChangePassword(JsonObject jsonRequest);

    interface View extends BaseView {
        void successChangePassword(JsonObject jsonRequest);

    }

}
