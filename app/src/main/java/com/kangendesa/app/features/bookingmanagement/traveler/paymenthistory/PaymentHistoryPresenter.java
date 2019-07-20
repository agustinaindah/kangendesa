package com.kangendesa.app.features.bookingmanagement.traveler.paymenthistory;

import com.kangendesa.app.base.BaseView;
import com.kangendesa.app.model.ItemListOrder;
import com.kangendesa.app.model.ItemPaymentHistory;

import java.util.List;
import java.util.Map;

/**
 * Created by agustinaindah on 18 Februari 2019
 */
public interface PaymentHistoryPresenter {

    void getPaymentHistory(Map<String, String> queryRequest);

    interface View extends BaseView {
        void showPaymentHistory(List<ItemPaymentHistory> paymentHistoryList, int totalData);
    }
}
