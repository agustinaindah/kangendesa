package com.kangendesa.app;

import android.app.Application;
import android.content.Context;
import android.content.SharedPreferences;
import android.support.multidex.MultiDex;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.kangendesa.app.model.BaseResponse;
import com.kangendesa.app.utils.ApiService;
import com.kangendesa.app.utils.Consts;
import com.kangendesa.app.utils.Helper;
import com.kangendesa.app.utils.ServiceInterface;
import com.kangendesa.app.utils.SharedPref;

import java.io.IOException;

import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by agustinaindah on 16 Januari 2019
 */

public class KangenDesaApp extends Application{

    private static Context context;
    private static KangenDesaApp ourInstance;
    private Call<BaseResponse> mRequest = null;

    public static synchronized Context getContext() {
        return context;
    }

    private Gson gson = new GsonBuilder()
            .setLenient()
            .create();

    public static KangenDesaApp getInstance() {
        return ourInstance;
    }

    public static void getBanner() {
        KangenDesaApp.getInstance().service(new ServiceInterface() {
            @Override
            public Call<BaseResponse> callBackResponse(ApiService apiService) {
                return apiService.getSlider();
            }

            @Override
            public void showProgress() {
            }

            @Override
            public void hideProgress() {
            }

            @Override
            public void responseSuccess(Response<BaseResponse> response) {
                try {
                    String data = Helper.getGsonInstance().toJson(response.body().getData());
                    JsonArray jsonRes = Helper.parseToJsonObject(data).get("slides").getAsJsonArray();
                    KangenDesaApp
                            .getInstance()
                            .Prefs()
                            .edit()
                            .putString(Consts.BANNER, jsonRes.toString())
                            .commit();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void responseFailed(Response<BaseResponse> response) {
            }

            @Override
            public void failed(Throwable t) {
                t.printStackTrace();
            }
        });
    }

    @Override
    protected void attachBaseContext(Context base) {
        super.attachBaseContext(base);
        MultiDex.install(this);
    }

    @Override
    public void onCreate() {
        super.onCreate();
        context = getApplicationContext();
        ourInstance = this;
    }

    @Override
    public void onTerminate() {
        super.onTerminate();
        ourInstance = null;
    }

    /**
     * get SharedPreferences
     *
     * @return SharedPreferences
     */
    public SharedPreferences Prefs() {
        return getSharedPreferences(getPackageName(), MODE_PRIVATE);
    }

    /**
     * @return String base_url
     */
    public String getBaseUrl() {
        return isLogin() ? getBaseUrlMember() : getString(R.string.base_url);
    }

    /**
     * @return String base_url /web
     */
    public String getBaseUrlWeb() {
        return getString(R.string.base_url);
    }

    /**
     * @return String base_url /member
     */
    public String getBaseUrlMember() {
        return getString(R.string.base_url);
    }

    /**
     * @return String X-API-KEY value
     */
    public String getApiKey() {
        return getString(R.string.X_API_KEY);
    }

    /**
     * @return String email from preferences
     */
    public String getEmail() {
        return SharedPref.getString(Consts.EMAIL);
    }

    /**
     * @return String token from preferences
     */
    public String getToken() {
        return SharedPref.getString(Consts.TOKEN);
    }

    /**
     * @return boolean login
     */
    public boolean isLogin() {
        return (getToken() != null);
    }

    /**
     * logout | remove token
     */
    public void logout() {
        SharedPreferences.Editor edit = SharedPref.getEditor();
        edit.clear().commit();
        edit.putBoolean(Consts.FIRST_RUN, false).commit();
    }

    public Retrofit retrofit() {
        return new Retrofit.Builder()
                .baseUrl(KangenDesaApp.getInstance().getBaseUrl())
                .addConverterFactory(GsonConverterFactory.create())
                .client(new OkHttpClient.Builder()
                        .addInterceptor(new HttpLoggingInterceptor()
                                .setLevel(HttpLoggingInterceptor.Level.BODY))
                        .addInterceptor(new Interceptor() {
                            @Override
                            public okhttp3.Response intercept(Chain chain) throws IOException {

                                Request ori = chain.request();

                                Request.Builder reqBuilder = ori.newBuilder()
                                        .addHeader("API-KEY", KangenDesaApp.getInstance().getApiKey())
                                        .addHeader("Content-Type","application/x-www-form-urlencoded");

                                String token = KangenDesaApp.getInstance().getToken();

                                if (token != null) {
                                    reqBuilder.addHeader("USER-OAUTH-TOKEN", token);
                                }

                                Request req = reqBuilder.build();

                                return chain.proceed(req);
                            }
                        }).build()
                ).build();
    }

    public void service(final ServiceInterface callApiService) {
        callApiService.showProgress();
        ApiService apiService = retrofit().create(ApiService.class);
        mRequest = callApiService.callBackResponse(apiService);
        mRequest.enqueue(new Callback<BaseResponse>() {
            @Override
            public void onResponse(Call<BaseResponse> call, Response<BaseResponse> response) {
                if (!response.isSuccessful()) {
                    callApiService.responseFailed(response);
                    callApiService.hideProgress();
                    return;
                }
                if (response.body().equals(null)) {
                    return;
                } else {
                    callApiService.responseSuccess(response);
                    callApiService.hideProgress();
                }
            }

            @Override
            public void onFailure(Call<BaseResponse> call, Throwable t) {
                callApiService.failed(t);
                callApiService.hideProgress();
            }
        });

    }

    public Call<BaseResponse> getRequest() {
        return mRequest;
    }

}
