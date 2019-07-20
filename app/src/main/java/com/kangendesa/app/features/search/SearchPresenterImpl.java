package com.kangendesa.app.features.search;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.kangendesa.app.KangenDesaApp;
import com.kangendesa.app.model.BaseResponse;
import com.kangendesa.app.model.ItemTour;
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
public class SearchPresenterImpl implements SearchPresenter {

    private View mView;

    public SearchPresenterImpl(View mView) {
        this.mView = mView;
    }

    @Override
    public void getSearch(final Map<String, String> queryRequest) {
        KangenDesaApp.getInstance().service(new ServiceInterface() {
            @Override
            public Call<BaseResponse> callBackResponse(ApiService apiService) {
                return apiService.getSearch(queryRequest);
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
                    JsonArray jsonRes = jsonData.get("posts").getAsJsonArray();
                    ArrayList<ItemTour> itemTours = new ArrayList<ItemTour>();
                    for (JsonElement element : jsonRes){
                        ItemTour itemTour =
                                Helper.getGsonInstance().fromJson(element, ItemTour.class);
                        itemTours.add(itemTour);
                    }
                    mView.success(itemTours, Integer.valueOf(totalData));
                }catch (Exception e){
                    e.printStackTrace();
                }
            }

            @Override
            public void responseFailed(Response<BaseResponse> response) {
                try {
                    JsonObject jsonRes = Helper.parseToJsonObject(response.errorBody().string());
                    mView.showMessage(jsonRes.get("response").getAsString());
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
