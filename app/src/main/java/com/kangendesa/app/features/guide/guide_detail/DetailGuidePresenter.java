package com.kangendesa.app.features.guide.guide_detail;

import com.kangendesa.app.base.BaseView;
import com.kangendesa.app.model.ItemGuide;
import com.kangendesa.app.model.ItemTour;
import com.kangendesa.app.model.ItemTourByUser;

import java.util.List;
import java.util.Map;

/**
 * Created by agustinaindah on 23 Januari 2019
 */
public interface DetailGuidePresenter {

    void getDetailGuide(Map<String, String> queryRequest);

    void getTourByUser(Map<String, String> queryRequest);

    interface View extends BaseView {
        void success(ItemGuide itemGuide);

        void showTourByUser(List<ItemTourByUser> itemTourByUserList, int totalData);
    }
}
