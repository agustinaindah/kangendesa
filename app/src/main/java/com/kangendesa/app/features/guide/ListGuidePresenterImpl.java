package com.kangendesa.app.features.guide;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.kangendesa.app.KangenDesaApp;
import com.kangendesa.app.model.BaseResponse;
import com.kangendesa.app.model.ItemGuide;
import com.kangendesa.app.model.ItemTour;
import com.kangendesa.app.utils.ApiService;
import com.kangendesa.app.utils.Helper;
import com.kangendesa.app.utils.ServiceInterface;

import java.util.ArrayList;
import java.util.Map;

import retrofit2.Call;
import retrofit2.Response;

/**
 * Created by agustinaindah on 22 Januari 2019
 */
public class ListGuidePresenterImpl implements ListGuidePresenter {

    private View mView;

    public ListGuidePresenterImpl(View mView) {
        this.mView = mView;
    }

    @Override
    public void getListGuide(final Map<String, String> queryRequest) {
        KangenDesaApp.getInstance().service(new ServiceInterface() {
            @Override
            public Call<BaseResponse> callBackResponse(ApiService apiService) {
                return apiService.getListGuide(queryRequest);
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
                    String totalData = jsonData.get("found_posts").getAsString();
                    JsonArray jsonRes = jsonData.get("users").getAsJsonArray();
                    ArrayList<ItemGuide> itemGuides = new ArrayList<ItemGuide>();
                    for (JsonElement element : jsonRes){
                        ItemGuide itemGuide =
                                Helper.getGsonInstance().fromJson(element, ItemGuide.class);
                        itemGuides.add(itemGuide);
                    }
                    mView.showListGuide(itemGuides, Integer.valueOf(totalData));

                }catch (Exception e){
                    e.printStackTrace();
                }
            }

            @Override
            public void responseFailed(Response<BaseResponse> response) {
                try {
                    JsonObject jsonRes = Helper.parseToJsonObject(response.errorBody().string());
                    mView.showMessage(jsonRes.get("error").getAsString());
                }catch (Exception e){
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
