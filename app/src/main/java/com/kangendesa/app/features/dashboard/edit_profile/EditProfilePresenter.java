package com.kangendesa.app.features.dashboard.edit_profile;

import com.google.gson.JsonObject;
import com.kangendesa.app.base.BaseView;

/**
 * Created by agustinaindah on 11 Februari 2019
 */
public interface EditProfilePresenter {

    void getEditProfile();

//    void postPhotoProfile(JsonObject jsonRequest);

    void postEditProfile(JsonObject jsonRequest);

    interface View extends BaseView {

//        void successEditPhoto(JsonObject jsonRequest);

        void displayEditProfile(JsonObject jsonData);

        void successEditProfile(JsonObject jsonRequest);
    }
}
