package com.kangendesa.app.features.bookingmanagement.traveler.mybooking;

import com.kangendesa.app.base.BaseView;
import com.kangendesa.app.model.DetailOrder;
import com.kangendesa.app.model.ItemListOrder;

import java.util.List;
import java.util.Map;

/**
 * Created by agustinaindah on 04 Februari 2019
 */
public interface MyBookingPresenter {

    void getListOrder(Map<String, String> queryRequest);

    interface View extends BaseView {
        void showListOrder(List<ItemListOrder> detailOrderList, int totalData);
    }
}
