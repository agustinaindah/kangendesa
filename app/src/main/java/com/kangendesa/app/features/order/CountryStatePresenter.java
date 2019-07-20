package com.kangendesa.app.features.order;

import com.kangendesa.app.base.BaseView;
import com.kangendesa.app.model.Countries;
import com.kangendesa.app.model.State;

import java.util.List;

/**
 * Created by agustinaindah on 30 Januari 2019
 */

public interface CountryStatePresenter {

    void getCountries();

    void getByCountry(String countryCode);

//  void getStates();

    interface View extends BaseView{

        void showCountries(List<Countries> countriesList);

        void showStates(List<State> stateList);
    }

}
