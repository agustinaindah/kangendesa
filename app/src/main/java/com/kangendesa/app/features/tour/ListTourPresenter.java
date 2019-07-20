package com.kangendesa.app.features.tour;

import com.kangendesa.app.base.BaseView;
import com.kangendesa.app.model.ItemTour;

import java.util.List;
import java.util.Map;

/**
 * Created by agustinaindah on 21 Januari 2019
 */
public interface ListTourPresenter {

    void getListTour(Map<String, String> queryRequest);

    interface View extends BaseView{
        void showListTour(List<ItemTour> itemTourList, int totalData);
    }
}
