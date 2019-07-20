package com.kangendesa.app.base;

/**
 * Created by agustinaindah on 16 Januari 2019
 */
public interface BaseView {

    void showProgress();

    void hideProgress();

    void showMessage(String msg);

    void notConnect(String msg);
}
