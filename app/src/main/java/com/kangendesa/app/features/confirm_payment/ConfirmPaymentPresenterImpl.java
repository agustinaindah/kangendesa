package com.kangendesa.app.features.confirm_payment;

import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.kangendesa.app.KangenDesaApp;
import com.kangendesa.app.model.BaseResponse;
import com.kangendesa.app.model.ItemBankName;
import com.kangendesa.app.model.ItemTransferMethod;
import com.kangendesa.app.utils.ApiService;
import com.kangendesa.app.utils.Helper;
import com.kangendesa.app.utils.ServiceInterface;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Response;

/**
 * Created by agustinaindah on 08 Februari 2019
 */
public class ConfirmPaymentPresenterImpl implements ConfirmPaymentPresenter {

    private View mView;

    public ConfirmPaymentPresenterImpl(View mView) {
        this.mView = mView;
    }

    @Override
    public void getComponent() {
        KangenDesaApp.getInstance().service(new ServiceInterface() {
            @Override
            public Call<BaseResponse> callBackResponse(ApiService apiService) {
                return apiService.getComponent();
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
                    JsonArray jsonBank = jsonData.get("bank_name").getAsJsonArray();
                    ArrayList<ItemBankName> itemBanks = new ArrayList<ItemBankName>();
                    for (JsonElement element : jsonBank){
                        ItemBankName itemBank = (new Gson()).fromJson(element, ItemBankName.class);
                        itemBanks.add(itemBank);
                    }
                    JsonArray jsonTransfer = jsonData.get("transfer_method").getAsJsonArray();
                    ArrayList<ItemTransferMethod> itemTransfers = new ArrayList<ItemTransferMethod>();
                    for (JsonElement element : jsonTransfer){
                        ItemTransferMethod itemTransfer = (new Gson()).fromJson(element, ItemTransferMethod.class);
                        itemTransfers.add(itemTransfer);
                    }
                    mView.showComponent(itemBanks,itemTransfers);
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

    @Override
    public void postConfirmPayment(final JsonObject jsonRequest) {
        KangenDesaApp.getInstance().service(new ServiceInterface() {
            @Override
            public Call<BaseResponse> callBackResponse(ApiService apiService) {
                return apiService.postConfirmPayment(jsonRequest);
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

                    mView.successConfirmPayment(jsonData);
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
}
