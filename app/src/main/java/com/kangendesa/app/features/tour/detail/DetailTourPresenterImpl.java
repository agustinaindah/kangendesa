package com.kangendesa.app.features.tour.detail;

import android.util.Log;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.kangendesa.app.KangenDesaApp;
import com.kangendesa.app.model.BaseResponse;
import com.kangendesa.app.model.ItemGuide;
import com.kangendesa.app.model.ItemTour;
import com.kangendesa.app.model.MConditionsDay;
import com.kangendesa.app.utils.ApiService;
import com.kangendesa.app.utils.Helper;
import com.kangendesa.app.utils.ServiceInterface;

import java.util.ArrayList;
import java.util.Map;

import retrofit2.Call;
import retrofit2.Response;

/**
 * Created by agustinaindah on 21 Januari 2019
 */
public class DetailTourPresenterImpl implements DetailTourPresenter {

    private View mView;

    public DetailTourPresenterImpl(View mView) {
        this.mView = mView;
    }

    @Override
    public void getDetailTour(final Map<String, String> queryRequest) {
        KangenDesaApp.getInstance().service(new ServiceInterface() {
            @Override
            public Call<BaseResponse> callBackResponse(ApiService apiService) {
                return apiService.getDetailTour(queryRequest);
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
                    JsonObject jsonRes = jsonData.get(0).getAsJsonObject();
                    ItemTour itemTour = Helper.getGsonInstance().fromJson(jsonRes, ItemTour.class);

                    JsonArray jsonDay = jsonRes.get("m_conditions_day").getAsJsonArray();
                    ArrayList<MConditionsDay> mConditionsDays = new ArrayList<MConditionsDay>();
                    for (JsonElement element : jsonDay){
                        MConditionsDay mConditionsDay =
                                Helper.getGsonInstance().fromJson(element, MConditionsDay.class);
                        mConditionsDays.add(mConditionsDay);
                    }
                    itemTour.setMConditionsDay(mConditionsDays);

                    mView.success(itemTour);
                    mView.successTour(data);
                }catch (Exception e){
                    e.printStackTrace();
                }
            }

            @Override
            public void responseFailed(Response<BaseResponse> response) {
                try {
                    JsonObject jsonRes = Helper.parseToJsonObject(response.errorBody().string());
                    mView.showMessage(jsonRes.get("response").getAsString());
                }
                catch (Exception e) {
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
    public void getDetailGuide(final Map<String, String> queryRequest) {
        KangenDesaApp.getInstance().service(new ServiceInterface() {
            @Override
            public Call<BaseResponse> callBackResponse(ApiService apiService) {
                return apiService.getDetailGuide(queryRequest);
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
                    JsonObject item = jsonData.get("user").getAsJsonObject();
                    ItemGuide itemGuide = Helper.getGsonInstance().fromJson(item, ItemGuide.class);
                    mView.successDetailGuide(itemGuide);
                }catch (Exception e){
                    e.printStackTrace();
                }
            }

            @Override
            public void responseFailed(Response<BaseResponse> response) {
                try {
                    JsonObject jsonRes = Helper.parseToJsonObject(response.errorBody().string());
                    mView.showMessage(jsonRes.get("response").getAsString());
                }
                catch (Exception e) {
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
