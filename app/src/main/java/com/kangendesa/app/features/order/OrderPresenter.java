package com.kangendesa.app.features.order;

import com.google.gson.JsonObject;
import com.kangendesa.app.base.BaseView;
import com.kangendesa.app.model.Countries;
import com.kangendesa.app.model.PaymentMethod;
import com.kangendesa.app.model.State;

import java.util.List;
import java.util.Map;

/**
 * Created by agustinaindah on 28 Januari 2019
 */
public interface OrderPresenter  {

    void getPaymentMethod();

    void postOrder(JsonObject jsonRequest);

//    void getCountryDetail(String countryCode);

//    void getStatesDetail(Map<String, String> queryRequest);

    interface View extends BaseView{

        void showPaymentMethod(List<PaymentMethod> paymentMethodList);

        void successOrder(JsonObject jsonRes);

//        void showByCountry();

    }
}
