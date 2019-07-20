package com.kangendesa.app.features.search;

import com.kangendesa.app.base.BaseView;
import com.kangendesa.app.model.ItemTour;

import java.util.List;
import java.util.Map;

/**
 * Created by agustinaindah on 21 Januari 2019
 */
public interface SearchPresenter {

    void getSearch(Map<String, String> queryRequest);

    interface View extends BaseView{
        void success (List<ItemTour> itemTours, int page);

        void noData();
    }

}
