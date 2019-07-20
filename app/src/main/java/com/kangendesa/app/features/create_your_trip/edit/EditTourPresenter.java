package com.kangendesa.app.features.create_your_trip.edit;

import com.google.gson.JsonObject;
import com.kangendesa.app.base.BaseView;

/**
 * Created by agustinaindah on 25 Februari 2019
 */
public interface EditTourPresenter {

    void postEditTour(JsonObject jsonRequest);

    interface View extends BaseView{

        void successEditTour(JsonObject jsonRequest);
    }
}
