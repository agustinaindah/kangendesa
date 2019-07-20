package com.kangendesa.app.features.auth.register;

import com.google.gson.JsonObject;
import com.kangendesa.app.base.BaseView;

/**
 * Created by agustinaindah on 18 Januari 2019
 */
public interface RegisterPresenter {

    void register(JsonObject jsonRequest);

    interface View extends BaseView {
        void successRegister(JsonObject user);

    }
}
