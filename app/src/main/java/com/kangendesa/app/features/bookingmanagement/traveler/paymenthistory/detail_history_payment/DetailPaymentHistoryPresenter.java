package com.kangendesa.app.features.bookingmanagement.traveler.paymenthistory.detail_history_payment;

import com.kangendesa.app.base.BaseView;
import com.kangendesa.app.model.ItemDetailPayment;
import com.kangendesa.app.model.ItemTour;

import java.util.List;
import java.util.Map;

/**
 * Created by agustinaindah on 20 Februari 2019
 */
public interface DetailPaymentHistoryPresenter {

    void getSinglePembayaran(Map<String, String> queryRequest);

    interface View extends BaseView {
        void success(ItemDetailPayment itemDetailPayment);
    }
}
