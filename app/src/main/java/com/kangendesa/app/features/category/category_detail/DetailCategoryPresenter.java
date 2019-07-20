package com.kangendesa.app.features.category.category_detail;

import com.kangendesa.app.base.BaseView;
import com.kangendesa.app.model.ItemTour;

import java.util.List;
import java.util.Map;

/**
 * Created by agustinaindah on 04 Februari 2019
 */
public interface DetailCategoryPresenter {

    void getListCategory(Map<String, String> queryRequest);

    interface View extends BaseView {
        void showListCategory(List<ItemTour> itemTourList, int totalData);
    }

}
