package com.kangendesa.app.features.banner;

import android.content.SharedPreferences;
import android.os.Bundle;
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
import com.kangendesa.app.utils.Consts;
import com.kangendesa.app.utils.Helper;

import butterknife.BindView;

/**
 * Created by agustinaindah on 06 Februari 2019
 */
public class SlidderBannerFragment extends BaseFragment {

    @BindView(R.id.sliderBanner)
    SliderLayout sliderBanner;

    public static SlidderBannerFragment newInstance() {
        Bundle args = new Bundle();
        SlidderBannerFragment fragment = new SlidderBannerFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    protected int setView() {
        return R.layout.fragment_slider_banner;
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        setupBanner();
    }

    private void setupBanner() {
        SharedPreferences sPref = KangenDesaApp.getInstance().Prefs();
        String banner = sPref.getString(Consts.BANNER, "[]");
        JsonArray jsonBanner = Helper.parseToJsonArray(banner);
        for (JsonElement element : jsonBanner) {
            JsonObject itemBanner = element.getAsJsonObject();
            JsonArray arrSlider = itemBanner.get("m_sslider_data").getAsJsonArray();
            Helper.log("Slider: Panjang: "+ String.valueOf(arrSlider.size()));
            for(int i =0; i < arrSlider.size(); i++){
                Helper.log("Slider: ke - "+ String.valueOf(i)+ "::"+arrSlider.get(i).getAsString());
                DefaultSliderView sliderView = new DefaultSliderView(getActivity());
                sliderView.image(arrSlider.get(i).getAsString())
                        .setScaleType(BaseSliderView.ScaleType.CenterCrop);
                sliderBanner.addSlider(sliderView);

            }
        }
        sliderBanner.setPresetTransformer(SliderLayout.Transformer.Default);
        sliderBanner.setPresetIndicator(SliderLayout.PresetIndicators.Center_Bottom);
        sliderBanner.setCustomAnimation(new DescriptionAnimation());
        sliderBanner.setDuration(5000);
    }

    @Override
    public void onStop() {
        super.onStop();
        sliderBanner.stopAutoCycle();
    }
}
