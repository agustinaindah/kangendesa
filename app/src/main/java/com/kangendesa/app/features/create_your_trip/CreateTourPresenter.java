package com.kangendesa.app.features.create_your_trip;

import com.google.gson.JsonObject;
import com.kangendesa.app.base.BaseView;

/**
 * Created by agustinaindah on 14 Februari 2019
 */
public interface CreateTourPresenter {

    void postCreateTour(JsonObject jsonRequest);

    interface View extends BaseView {

        void successCreateTour(JsonObject jsonRes);
    }
}
