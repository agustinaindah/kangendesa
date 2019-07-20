package com.kangendesa.app.features.category;

import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.kangendesa.app.KangenDesaApp;
import com.kangendesa.app.model.BaseResponse;
import com.kangendesa.app.model.ItemCategory;
import com.kangendesa.app.utils.ApiService;
import com.kangendesa.app.utils.Helper;
import com.kangendesa.app.utils.ServiceInterface;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Response;

/**
 * Created by agustinaindah on 01 Februari 2019
 */
public class CategoryTourPresenterImpl implements CategoryTourPresenter {

    private View mView;

    public CategoryTourPresenterImpl(View mView) {
        this.mView = mView;
    }

    @Override
    public void getAllCategory() {
        KangenDesaApp.getInstance().service(new ServiceInterface() {
            @Override
            public Call<BaseResponse> callBackResponse(ApiService apiService) {
                return apiService.getAllCategory();
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
                    ArrayList<ItemCategory> itemCategoryArrayList = new ArrayList<ItemCategory>();
                    for (JsonElement element : jsonData){
                        ItemCategory itemCategory = (new Gson()).fromJson(element, ItemCategory.class);
                        itemCategoryArrayList.add(itemCategory);
                    }
                    mView.showAllCategory(itemCategoryArrayList);
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
