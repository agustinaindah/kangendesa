package com.kangendesa.app.features.order.order_result;

import com.kangendesa.app.base.BaseView;
import com.kangendesa.app.model.DetailOrder;
import com.kangendesa.app.model.ItemOrder;

import java.util.Map;

/**
 * Created by agustinaindah on 01 Februari 2019
 */
public interface OrderResultPresenter {

    void getDetailOrder(Map<String, String> queryRequest);

    interface View extends BaseView {
        void success(ItemOrder itemOrder);
    }


}
