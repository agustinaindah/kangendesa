package com.kangendesa.app.features.category;

import com.kangendesa.app.base.BaseView;
import com.kangendesa.app.model.ItemCategory;

import java.util.List;

/**
 * Created by agustinaindah on 01 Februari 2019
 */
public interface CategoryTourPresenter {

    void getAllCategory();

    interface View extends BaseView{

        void showAllCategory(List<ItemCategory> itemCategoryList);
    }

}
