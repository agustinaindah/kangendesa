package com.kangendesa.app.features.order;

import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.kangendesa.app.KangenDesaApp;
import com.kangendesa.app.model.BaseResponse;
import com.kangendesa.app.model.Countries;
import com.kangendesa.app.model.State;
import com.kangendesa.app.utils.ApiService;
import com.kangendesa.app.utils.Helper;
import com.kangendesa.app.utils.ServiceInterface;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Response;

/**
 * Created by agustinaindah on 30 Januari 2019
 */
public class CountryStatePresenterImpl implements CountryStatePresenter {

    private View mView;

    public CountryStatePresenterImpl(View mView) {
        this.mView = mView;
    }

    @Override
    public void getCountries() {
        KangenDesaApp.getInstance().service(new ServiceInterface() {
            @Override
            public Call<BaseResponse> callBackResponse(ApiService apiService) {
                return apiService.getCountries();
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
                    ArrayList<Countries> countriesArrayList = new ArrayList<Countries>();
                    for (JsonElement element : jsonData){
                        Countries countries = (new Gson()).fromJson(element, Countries.class);
                        countriesArrayList.add(countries);
                    }
                    mView.showCountries(countriesArrayList);
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
    public void getByCountry(final String countryCode) {
        KangenDesaApp.getInstance().service(new ServiceInterface() {
            @Override
            public Call<BaseResponse> callBackResponse(ApiService apiService) {
                return apiService.getByCountry(countryCode);
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
                    JsonArray jsonRes = jsonData.get("states").getAsJsonArray();
                    ArrayList<State> stateArrayList = new ArrayList<State>();
                    for (JsonElement element : jsonRes){
                        State state = (new Gson()).fromJson(element, State.class);
                        stateArrayList.add(state);
                    }
                    mView.showStates(stateArrayList);
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
}
