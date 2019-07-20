package com.kangendesa.app.features.dashboard;

import com.google.gson.JsonObject;
import com.kangendesa.app.base.BaseView;
import com.kangendesa.app.model.ItemTourByUser;

import java.util.List;
import java.util.Map;

/**
 * Created by agustinaindah on 18 Februari 2019
 */
public interface TourByUserPresenter {

    void getProfile();

    void getTourByUser(Map<String, String> queryRequest);

    interface View extends BaseView {

        void displayProfile(JsonObject jsonData);

        void showTourByUser(List<ItemTourByUser> itemTourByUserList, int totalData);
    }
}
