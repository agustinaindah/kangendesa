package com.kangendesa.app.features.guide.guide_detail;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.kangendesa.app.KangenDesaApp;
import com.kangendesa.app.model.BaseResponse;
import com.kangendesa.app.model.ItemGuide;
import com.kangendesa.app.model.ItemTourByUser;
import com.kangendesa.app.utils.ApiService;
import com.kangendesa.app.utils.Helper;
import com.kangendesa.app.utils.ServiceInterface;

import java.util.ArrayList;
import java.util.Map;

import retrofit2.Call;
import retrofit2.Response;

/**
 * Created by agustinaindah on 23 Januari 2019
 */
public class DetailGuidePresenterImpl implements DetailGuidePresenter {

    private View mView;

    public DetailGuidePresenterImpl(View mView) {
        this.mView = mView;
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
                    mView.success(itemGuide);
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
    public void getTourByUser(final Map<String, String> queryRequest) {
        KangenDesaApp.getInstance().service(new ServiceInterface() {
            @Override
            public Call<BaseResponse> callBackResponse(ApiService apiService) {
                return apiService.getTourByUser(queryRequest);
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
                    ArrayList<ItemTourByUser> itemTourByUsers = new ArrayList<ItemTourByUser>();
                    for (JsonElement element : jsonRes){
                        ItemTourByUser itemTourByUser =
                                Helper.getGsonInstance().fromJson(element, ItemTourByUser.class);
                        itemTourByUsers.add(itemTourByUser);
                    }
                    mView.showTourByUser(itemTourByUsers, Integer.valueOf(totalData));

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
