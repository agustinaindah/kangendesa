package com.kangendesa.app.features.confirm_payment;

import com.google.gson.JsonObject;
import com.kangendesa.app.base.BaseView;
import com.kangendesa.app.model.ItemBankName;
import com.kangendesa.app.model.ItemTransferMethod;

import java.util.List;

/**
 * Created by agustinaindah on 08 Februari 2019
 */
public interface ConfirmPaymentPresenter {

    void getComponent();

    void postConfirmPayment(JsonObject jsonRequest);

    interface View extends BaseView{

        void showComponent(List<ItemBankName> itemBankList, List<ItemTransferMethod> itemTransferMethodList);

        void successConfirmPayment(JsonObject jsonRes);
    }
}
