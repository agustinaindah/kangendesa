package com.kangendesa.app.features.tripmanagement;

import com.kangendesa.app.base.BaseView;
import com.kangendesa.app.model.ItemTourByUser;

import java.util.List;
import java.util.Map;

/**
 * Created by agustinaindah on 19 Februari 2019
 */
public interface TripManagementPresenter {

    void getTripManagement(Map<String, String> queryRequest);

    interface View extends BaseView {

        void showTripManagement(List<ItemTourByUser> itemTourByUserList, int totalData);
    }
}
