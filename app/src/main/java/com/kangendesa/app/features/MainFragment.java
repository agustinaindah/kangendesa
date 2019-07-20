package com.kangendesa.app.features;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.daimajia.slider.library.Animations.DescriptionAnimation;
import com.daimajia.slider.library.SliderLayout;
import com.daimajia.slider.library.SliderTypes.BaseSliderView;
import com.daimajia.slider.library.SliderTypes.DefaultSliderView;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.kangendesa.app.KangenDesaApp;
import com.kangendesa.app.R;
import com.kangendesa.app.base.BaseFragment;
import com.kangendesa.app.features.banner.BannerAdapter;
import com.kangendesa.app.features.banner.BannerFragment;
import com.kangendesa.app.features.banner.SlidderBannerFragment;
import com.kangendesa.app.features.category.CategoryTourFragment;
import com.kangendesa.app.features.guide.ListGuideFragment;
import com.kangendesa.app.features.tour.ListTourFragment;
import com.kangendesa.app.model.TourDummy;
import com.kangendesa.app.utils.Consts;
import com.kangendesa.app.utils.Helper;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;

/**
 * Created by agustinaindah on 16 Januari 2019
 */
public class MainFragment extends BaseFragment {

    public static MainFragment newInstance() {
        Bundle args = new Bundle();
        MainFragment fragment = new MainFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    protected int setView() {
        return R.layout.fragment_home;
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        FragmentTransaction ft = getFragmentManager().beginTransaction();

        ft.add(R.id.fragmentContainer, SlidderBannerFragment.newInstance());
        ft.add(R.id.fragmentContainer2, ListGuideFragment.newInstance());
        ft.add(R.id.fragmentContainer3, CategoryTourFragment.newInstance());
        ft.add(R.id.fragmentContainer4, BannerFragment.newInstance());
        ft.add(R.id.fragmentContainer5, ListTourFragment.newInstance());
        ft.commit();
    }
}
