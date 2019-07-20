package com.kangendesa.app.features.guide;

import com.kangendesa.app.base.BaseView;
import com.kangendesa.app.model.ItemGuide;

import java.util.List;
import java.util.Map;

/**
 * Created by agustinaindah on 22 Januari 2019
 */
public interface ListGuidePresenter {
    void getListGuide(Map<String, String> queryRequest);

    interface View extends BaseView {
        void showListGuide(List<ItemGuide> itemGuideList, int totalData);
    }
}
