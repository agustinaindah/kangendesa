package com.kangendesa.app.utils;

import com.google.gson.JsonObject;
import com.kangendesa.app.model.BaseResponse;

import java.util.Map;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Query;
import retrofit2.http.QueryMap;

/**
 * Created by agustinaindah on 16 Januari 2019
 */
public interface ApiService {

    /*========================slider===========================*/

    @GET("res-api/v1/widget/slider")
    Call<BaseResponse> getSlider();

    /*=======================auth=============================*/

    @POST("res-api/v1/register")
    Call<BaseResponse> postRegister(@Body JsonObject jsonRequest);

    @POST("res-api/v1/login")
    Call<BaseResponse> postLogin(@Body JsonObject jsonRequest);

    @POST("res-api/v1/reset-password")
    Call<BaseResponse> postResetpassword(@Body JsonObject jsonRequest);

    @POST("res-api/v1/user/reset-password")
    Call<BaseResponse> postChangePassword(@Body JsonObject jsonRequest);

    /*=======================list guide======================*/

    @GET("res-api/v1/users/guide")
    Call<BaseResponse> getListGuide(@QueryMap Map<String, String> queryRequest);

    @GET("res-api/v1/public/users/detail")
    Call<BaseResponse> getDetailGuide(@QueryMap Map<String, String> queryRequest);

    /*=======================list tour======================*/

    @GET("res-api/v1/wisata/list")
    Call<BaseResponse> getListTour(@QueryMap Map<String, String> queryRequest);

    @GET("res-api/v1/wisata/detail")
    Call<BaseResponse> getDetailTour(@QueryMap Map<String, String> queryRequest);

    @GET("res-api/v1/wisata/search")
    Call<BaseResponse> getSearch(@QueryMap Map<String, String> queryRequest);

    @GET("res-api/v1/user/wisata/list")
    Call<BaseResponse> getTourByUser(@QueryMap Map<String, String> queryRequest);

    /*====================Order Login=========================*/

    @POST("res-api/v1/wc/order/create")
    Call<BaseResponse> postOrder(@Body JsonObject jsonRequest);

    @GET("res-api/v1/public/wc/billing/countries/all")
    Call<BaseResponse> getCountries();

    @GET("res-api/v1/public/wc/billing/states/all")
    Call<BaseResponse> getStates();

    @GET("res-api/v1/public/wc/billing/states/by-country")
    Call<BaseResponse> getByCountry(@Query("country_code") String countryCode);

    @GET("res-api/v1/public/wc/billing/country/detail")
    Call<BaseResponse> getCountryDetail(@Query("country_code") String countryCode);

    @GET("res-api/v1/public/wc/billing/states/detail")
    Call<BaseResponse> getStatesDetail(@QueryMap Map<String, String> queryRequest);

    @GET("res-api/v1/public/wc/payment-method")
    Call<BaseResponse> getPaymentMethod();

    @GET("res-api/v1/wc/order/detail")
    Call<BaseResponse> getOrderDetail(@QueryMap Map<String, String> queryRequest);

    /*=====================Category=============================*/

    @GET("res-api/v1/term/wisata")
    Call<BaseResponse> getAllCategory();

    @GET("res-api/v1/wisata/category")
    Call<BaseResponse> getCategory(@QueryMap Map<String, String> queryRequest);

    /*===================Confirm Payment=======================*/

    @GET("res-api/v1/public/konfirmasi-pembayaran/component")
    Call<BaseResponse> getComponent();

    @POST("res-api/v1/public/konfirmasi-pembayaran/add")
    Call<BaseResponse> postConfirmPayment(@Body JsonObject jsonRequest);

    /*==================Edit Profile===========================*/

    @POST("res-api/v1/user/upload-photo-profile")
    Call<BaseResponse> postPhotoProfile(@Body JsonObject jsonRequest);

    @GET("res-api/v1/user/edit/get")
    Call<BaseResponse> getEditProfile();

    @POST("res-api/v1/user/edit/act")
    Call<BaseResponse> postEditProfile(@Body JsonObject jsonRequest);

    /*=====================Create Tour==============================*/

    @GET("res-api/v1/user/trip/component")
    Call<BaseResponse> getComponentTour();

    @POST("res-api/v1/user/trip/create")
    Call<BaseResponse> postCreateTour(@Body JsonObject jsonRequest);

    @POST("res-api/v1/user/trip/edit")
    Call<BaseResponse> postEditTour(@Body JsonObject jsonRequest);

    /*==================Booking Management===========================*/

    @GET("res-api/v1/user/order/list")
    Call<BaseResponse> getOrderList(@QueryMap Map<String, String> queryRequest);

    @GET("res-api/v1/user/trip/booked-list")
    Call<BaseResponse> getBookedList(@QueryMap Map<String, String> queryRequest);

    @GET("res-api/v1/user/history-pendapatan/list")
    Call<BaseResponse> getHistoryPendapatanList(@QueryMap Map<String, String> queryRequest);

    @GET("res-api/v1/user/history-pembayaran/list")
    Call<BaseResponse> getHistoryPembayaranList(@QueryMap Map<String, String> queryRequest);

    @GET("res-api/v1/user/history-pembayaran/single")
    Call<BaseResponse> getSinglePembayaran(@QueryMap Map<String, String> queryRequest);

    /*====================Verify Email===============================*/

    @POST("res-api/v1/register/verify")
    Call<BaseResponse> postVerifyEmail(@Body JsonObject jsonRequest);

    /*=============================================================*/
}
