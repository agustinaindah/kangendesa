package com.kangendesa.app.features.create_your_trip;

import com.kangendesa.app.base.BaseView;
import com.kangendesa.app.model.ItemDay;
import com.kangendesa.app.model.ItemExtraCon;
import com.kangendesa.app.model.ItemTransportation;

import java.util.List;

/**
 * Created by agustinaindah on 15 Februari 2019
 */
public interface GetComponentTourPresenter {

    void getComponentTour();

    interface View extends BaseView{

        void showComponentTour(List<ItemTransportation> itemTransportationList,
                               List<ItemExtraCon> itemExtraConList,
                               List<ItemDay> itemDayList);

    }
}
