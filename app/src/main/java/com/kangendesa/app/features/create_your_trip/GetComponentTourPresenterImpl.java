package com.kangendesa.app.features.create_your_trip;

import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.kangendesa.app.KangenDesaApp;
import com.kangendesa.app.model.BaseResponse;
import com.kangendesa.app.model.ItemDay;
import com.kangendesa.app.model.ItemExtraCon;
import com.kangendesa.app.model.ItemTransportation;
import com.kangendesa.app.utils.ApiService;
import com.kangendesa.app.utils.Helper;
import com.kangendesa.app.utils.ServiceInterface;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Response;

/**
 * Created by agustinaindah on 15 Februari 2019
 */
public class GetComponentTourPresenterImpl implements GetComponentTourPresenter {

    private View mView;

    public GetComponentTourPresenterImpl(View mView) {
        this.mView = mView;
    }

    @Override
    public void getComponentTour() {
        KangenDesaApp.getInstance().service(new ServiceInterface() {
            @Override
            public Call<BaseResponse> callBackResponse(ApiService apiService) {
                return apiService.getComponentTour();
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
                    JsonArray jsonTransport = jsonData.get("basic_mtp_img").getAsJsonArray();
                    ArrayList<ItemTransportation> itemTransportations = new ArrayList<ItemTransportation>();
                    for (JsonElement element : jsonTransport){
                        ItemTransportation itemTransportation = (new Gson()).fromJson(element, ItemTransportation.class);
                        itemTransportations.add(itemTransportation);
                    }

                    JsonArray jsonExtraCon = jsonData.get("exstra_con").getAsJsonArray();
                    ArrayList<ItemExtraCon> itemExtraCons = new ArrayList<ItemExtraCon>();
                    for (JsonElement element : jsonExtraCon){
                        ItemExtraCon itemExtraCon = (new Gson()).fromJson(element, ItemExtraCon.class);
                        itemExtraCons.add(itemExtraCon);
                    }

                    JsonArray jsonDay = jsonData.get("op_day").getAsJsonArray();
                    ArrayList<ItemDay> itemDays = new ArrayList<ItemDay>();
                    for (JsonElement element : jsonDay){
                        ItemDay itemDay = (new Gson()).fromJson(element, ItemDay.class);
                        itemDays.add(itemDay);
                    }
                    mView.showComponentTour(itemTransportations, itemExtraCons, itemDays);
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
