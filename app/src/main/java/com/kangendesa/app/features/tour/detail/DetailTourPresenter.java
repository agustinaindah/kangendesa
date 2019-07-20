package com.kangendesa.app.features.tour.detail;

import com.kangendesa.app.base.BaseView;
import com.kangendesa.app.model.ItemGuide;
import com.kangendesa.app.model.ItemTour;

import java.util.Map;

/**
 * Created by agustinaindah on 21 Januari 2019
 */
public interface DetailTourPresenter {

    void getDetailTour(Map<String, String> queryRequest);

    void getDetailGuide(Map<String, String> queryRequest);

    interface View extends BaseView{

        void success(ItemTour itemTour);

        void successTour(String data);

        void successDetailGuide(ItemGuide itemGuide);
    }

}
