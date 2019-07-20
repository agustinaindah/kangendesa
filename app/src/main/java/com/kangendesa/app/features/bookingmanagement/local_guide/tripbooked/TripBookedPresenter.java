package com.kangendesa.app.features.bookingmanagement.local_guide.tripbooked;

import com.kangendesa.app.base.BaseView;
import com.kangendesa.app.model.ItemTripBooked;

import java.util.List;
import java.util.Map;

/**
 * Created by agustinaindah on 18 Februari 2019
 */
public interface TripBookedPresenter {

    void getTripBooked(Map<String, String> queryRequest);

    interface View extends BaseView {
        void showTripBooked(List<ItemTripBooked> tripBookedList, int totalData);
    }

}
