package com.kangendesa.app.features.bookingmanagement.local_guide.tripbooked;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.kangendesa.app.KangenDesaApp;
import com.kangendesa.app.model.BaseResponse;
import com.kangendesa.app.model.ItemListOrder;
import com.kangendesa.app.model.ItemTripBooked;
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
public class TripBookedPresenterImpl implements TripBookedPresenter{

    private View mView;

    public TripBookedPresenterImpl(View mView) {
        this.mView = mView;
    }

    @Override
    public void getTripBooked(final Map<String, String> queryRequest) {
        KangenDesaApp.getInstance().service(new ServiceInterface() {
            @Override
            public Call<BaseResponse> callBackResponse(ApiService apiService) {
                return apiService.getBookedList(queryRequest);
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
                    JsonArray jsonRes = jsonData.get("trips").getAsJsonArray();
                    ArrayList<ItemTripBooked> itemTripBookeds = new ArrayList<ItemTripBooked>();
                    for (JsonElement element : jsonRes){
                        ItemTripBooked itemTripBooked =
                                Helper.getGsonInstance().fromJson(element, ItemTripBooked.class);
                        itemTripBookeds.add(itemTripBooked);
                    }
                    mView.showTripBooked(itemTripBookeds, Integer.valueOf(totalData));
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
