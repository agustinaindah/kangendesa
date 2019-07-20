package com.kangendesa.app.features.bookingmanagement.traveler.paymenthistory;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.kangendesa.app.KangenDesaApp;
import com.kangendesa.app.model.BaseResponse;
import com.kangendesa.app.model.ItemListOrder;
import com.kangendesa.app.model.ItemPaymentHistory;
import com.kangendesa.app.utils.ApiService;
import com.kangendesa.app.utils.Helper;
import com.kangendesa.app.utils.ServiceInterface;

import java.util.ArrayList;
import java.util.Map;

import retrofit2.Call;
import retrofit2.Response;

/**
 * Created by agustinaindah on 18 Februari 2019
 */
public class PaymentHistoryPresenterImpl implements PaymentHistoryPresenter{

    private View mView;

    public PaymentHistoryPresenterImpl(View mView) {
        this.mView = mView;
    }

    @Override
    public void getPaymentHistory(final Map<String, String> queryRequest) {
        KangenDesaApp.getInstance().service(new ServiceInterface() {
            @Override
            public Call<BaseResponse> callBackResponse(ApiService apiService) {
                return apiService.getHistoryPembayaranList(queryRequest);
            }

            @Override
            public void showProgress(){
                mView.showProgress();
            }

            @Override
            public void hideProgress() {
                mView.hideProgress();
            }

            @Override
            public void responseSuccess(Response<BaseResponse> response) {
                try {
                    String data = Helper.getGsonInstance().toJson(response.body().getData());
                    JsonObject jsonData = Helper.parseToJsonObject(data);
                    String totalData = jsonData.get("found_posts").getAsString();
                    JsonArray jsonRes = jsonData.get("pembayaran").getAsJsonArray();
                    ArrayList<ItemPaymentHistory> itemPaymentHistories = new ArrayList<ItemPaymentHistory>();
                    for (JsonElement element : jsonRes){
                        ItemPaymentHistory itemPaymentHistory =
                                Helper.getGsonInstance().fromJson(element, ItemPaymentHistory.class);
                        itemPaymentHistories.add(itemPaymentHistory);
                    }
                    mView.showPaymentHistory(itemPaymentHistories, Integer.valueOf(totalData));
                }catch (Exception e){
                    e.printStackTrace();
                }
            }

            @Override
            public void responseFailed(Response<BaseResponse> response) {
                try {
                    JsonObject jsonRes = Helper.parseToJsonObject(response.errorBody().string());
                    mView.showMessage(jsonRes.get("response").getAsString());
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void failed(Throwable t) {
                mView.notConnect(t.getLocalizedMessage());
            }
        });
    }
}
