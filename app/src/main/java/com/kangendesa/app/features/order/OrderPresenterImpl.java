package com.kangendesa.app.features.order;

import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.kangendesa.app.KangenDesaApp;
import com.kangendesa.app.model.BaseResponse;
import com.kangendesa.app.model.Countries;
import com.kangendesa.app.model.PaymentMethod;
import com.kangendesa.app.model.State;
import com.kangendesa.app.utils.ApiService;
import com.kangendesa.app.utils.Consts;
import com.kangendesa.app.utils.Helper;
import com.kangendesa.app.utils.ServiceInterface;
import com.kangendesa.app.utils.SharedPref;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Response;

/**
 * Created by agustinaindah on 28 Januari 2019
 */
public class OrderPresenterImpl implements OrderPresenter {

    private View mView;

    public OrderPresenterImpl(View mView) {
        this.mView = mView;
    }

    @Override
    public void getPaymentMethod() {
        KangenDesaApp.getInstance().service(new ServiceInterface() {
            @Override
            public Call<BaseResponse> callBackResponse(ApiService apiService) {
                return apiService.getPaymentMethod();
            }

            @Override
            public void showProgress() {
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
                    JsonArray jsonData = Helper.parseToJsonArray(data);
                    ArrayList<PaymentMethod> paymentMethodArrayList = new ArrayList<PaymentMethod>();
                    for (JsonElement element : jsonData){
                        PaymentMethod paymentMethod = (new Gson()).fromJson(element, PaymentMethod.class);
                        paymentMethodArrayList.add(paymentMethod);
                    }
                    mView.showPaymentMethod(paymentMethodArrayList);
                }catch (Exception e){
                    e.printStackTrace();
                }
            }

            @Override
            public void responseFailed(Response<BaseResponse> response) {
                try {
                    JsonObject jsonRes = Helper.parseToJsonObject(response.errorBody().string());
                    mView.showMessage(jsonRes.get("msg").getAsString());
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

    @Override
    public void postOrder(final JsonObject jsonRequest) {
        KangenDesaApp.getInstance().service(new ServiceInterface() {
            @Override
            public Call<BaseResponse> callBackResponse(ApiService apiService) {
                return apiService.postOrder(jsonRequest);
            }

            @Override
            public void showProgress() {
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

                    saveDataToPref(jsonData);
                    mView.successOrder(jsonData);
                } catch (Exception e){
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

    private void saveDataToPref(JsonObject jsonData) {
        String orderId = jsonData.get("order_id").getAsString();

        SharedPref.saveString(Consts.ORDERID, orderId);
    }
}
