package com.kangendesa.app.features.tour.detail;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.AppBarLayout;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.v4.app.DialogFragment;
import android.support.v7.widget.Toolbar;
import android.text.Html;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.RatingBar;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.daimajia.slider.library.Animations.DescriptionAnimation;
import com.daimajia.slider.library.SliderLayout;
import com.daimajia.slider.library.SliderTypes.BaseSliderView;
import com.daimajia.slider.library.SliderTypes.DefaultSliderView;
import com.kangendesa.app.KangenDesaApp;
import com.kangendesa.app.R;
import com.kangendesa.app.base.BaseActivity;
import com.kangendesa.app.features.auth.login.LoginActivity;
import com.kangendesa.app.features.order.OrderActivity;
import com.kangendesa.app.features.order.dialog_calendar_booking.CalendarBookingDialogFragment;
import com.kangendesa.app.model.ItemGuide;
import com.kangendesa.app.model.ItemTour;
import com.kangendesa.app.model.MDetailFaq;
import com.kangendesa.app.model.MDetailSchedule;
import com.kangendesa.app.utils.CallbackInterface;
import com.kangendesa.app.utils.Consts;
import com.kangendesa.app.utils.Helper;
import com.kangendesa.app.utils.SharedPref;
import com.mikhaellopez.circularimageview.CircularImageView;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.BindView;
import butterknife.OnClick;

public class DetailTourActivity extends BaseActivity implements DetailTourPresenter.View,
        CalendarBookingDialogFragment.CalendarBookingDialog{

    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.collapsingToolbar)
    CollapsingToolbarLayout collapsingToolbar;
    @BindView(R.id.appBarLayout)
    AppBarLayout appBarLayout;
    @BindView(R.id.layProgress)
    RelativeLayout layProgress;
    @BindView(R.id.layError)
    LinearLayout layError;
    @BindView(R.id.btnError)
    Button btnError;
    @BindView(R.id.btnOrderNow)
    Button btnOrderNow;
    @BindView(R.id.btnCallToAction)
    ImageButton btnCallToAction;
    @BindView(R.id.sliderBannerDetail)
    SliderLayout sliderBannerDetail;

    /*content*/
    @BindView(R.id.txtItemProductTitle)
    TextView txtItemProductTitle;
    @BindView(R.id.txtItemProductPrice)
    TextView txtItemProductPrice;
    @BindView(R.id.ratingBar)
    RatingBar ratingBar;
    @BindView(R.id.txtItemProductLocation)
    TextView txtItemProductLocation;

    /*qty*/
    @BindView(R.id.btnDecrease)
    Button btnDecrease;
    @BindView(R.id.txtQty)
    TextView txtQty;
    @BindView(R.id.btnIncrease)
    Button btnIncrease;

    /*author*/
    @BindView(R.id.imgAuthor)
    CircularImageView imgAuthor;
    @BindView(R.id.txtGuideName)
    TextView txtGuideName;
    @BindView(R.id.txtRegisteredMember)
    TextView txtRegisteredMember;

    @BindView(R.id.txtDeskripsi)
    TextView txtDeskripsi;

    @BindView(R.id.txtScheduleTrip)
    TextView txtScheduleTrip;
    @BindView(R.id.txtFaq)
    TextView txtFaq;

    @BindView(R.id.txtBookingDate)
    TextView txtBookingDate;

    private DetailTourPresenter mPresenter;

    private String id;
    private String guideId;
    private String postTitle;

    private ItemTour mItemTour;
    private ItemGuide mItemGuide;

    private Integer total = 1;
    private Integer totalPembayaran;
    private String price;
    private String condition;
    private String date;

    // TODO: 07/03/2019 merapikan tanggal

    @Override
    protected void onActivityCreated(Bundle savedInstanceState) {

        id = getIntent().getStringExtra(Consts.ARG_ID);
        guideId = getIntent().getStringExtra("guideID");
        postTitle = getIntent().getStringExtra("postTitle");
        mPresenter = new DetailTourPresenterImpl(this);
        mPresenter.getDetailTour(getQueryRequest());
        mPresenter.getDetailGuide(getRequest());

        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle("");

        collapsingToolbar.setTitle("");

        appBarLayout.addOnOffsetChangedListener(new AppBarLayout.OnOffsetChangedListener() {
            boolean isShow = false;
            int scrollRange = -1;

            @Override
            public void onOffsetChanged(AppBarLayout appBarLayout, int verticalOffset) {
                if (scrollRange == -1){
                    scrollRange = appBarLayout.getTotalScrollRange();
                }
                if (scrollRange + verticalOffset == 0){
                    collapsingToolbar.setTitle(Html.fromHtml(postTitle));
                    collapsingToolbar.setCollapsedTitleTextColor(getResources().getColor(R.color.colorPrimaryDark));
                    isShow = true;
                } else if (isShow){
                    collapsingToolbar.setTitle("");
                    collapsingToolbar.setCollapsedTitleTextColor(getResources().getColor(R.color.colorPrimaryDark));
                    isShow = false;
                }
            }
        });

        /*Html.fromHtml(mItemTour.getPostTitle())*/
        totalQty();
    }

    private void totalQty() {
        btnDecrease.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (total > 1){
                    total = total - 1;
                }
                txtQty.setText(String.valueOf(total));
            }
        });

        btnIncrease.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (total < 10){
                    total = total + 1;
                }
                txtQty.setText(String.valueOf(total));
            }
        });
    }

    private Map<String, String> getQueryRequest() {
        HashMap<String, String> requestMap = new HashMap<>();
        requestMap.put("id", id);
        return requestMap;
    }

    private Map<String, String> getRequest() {
        HashMap<String, String> requestMap = new HashMap<>();
        requestMap.put("id", guideId);
        return requestMap;
    }

    @Override
    protected int setView() {
        return R.layout.activity_detail_tour;
    }

    private void setupSlider(ItemTour itemTour) {
        for (int i = 0; i < itemTour.getMOverviewPhotosImg().size(); i++) {
            DefaultSliderView sliderView = new DefaultSliderView(this);
            sliderView.image(itemTour.getMOverviewPhotosImg().get(i))
                    .setScaleType(BaseSliderView.ScaleType.Fit);
            sliderBannerDetail.addSlider(sliderView);
        }
        sliderBannerDetail.setPresetTransformer(SliderLayout.Transformer.Default);
        sliderBannerDetail.setPresetIndicator(SliderLayout.PresetIndicators.Center_Bottom);
        sliderBannerDetail.setCustomAnimation(new DescriptionAnimation());
        sliderBannerDetail.stopAutoCycle();
    }

    @Override
    public void success(ItemTour itemTour) {
        mItemTour = itemTour;
        showData(itemTour);
        setupSlider(itemTour);
    }

    @Override
    public void successTour(String data) {

    }

    @Override
    public void successDetailGuide(ItemGuide itemGuide) {
        mItemGuide = itemGuide;
        showData(itemGuide);
    }

    private void showData(ItemTour itemTour) {
        txtItemProductTitle.setText(Html.fromHtml(itemTour.getPostTitle()));

        if (itemTour.getMPrice().equals("")){
            txtItemProductPrice.setText("Rp " + "0");
        } else {
            txtItemProductPrice.setText(Helper.numberCurrency(Integer.valueOf(itemTour.getMPrice())));
            price = mItemTour.getMPrice();
        }

        txtItemProductLocation.setText(itemTour.getMBasicDestinasi());
        ratingBar.setRating(5);
        txtDeskripsi.setText(Html.fromHtml(itemTour.getPostContent()));
        travelItinerary(itemTour.getMDetailSchedule());
        travelFaq(itemTour.getMDetailFaq());
    }

    private void showData(ItemGuide itemGuide) {
        if (!itemGuide.getMProfileImageUrl().isEmpty()){
            Helper.displayImage(this, itemGuide.getMProfileImageUrl(), imgAuthor, true);
        }
        txtGuideName.setText("Author : " + itemGuide.getDisplayName());
        txtRegisteredMember.setText("Member Since : " + Helper.parseToDateString(itemGuide.getUserRegistered(), Consts.TYPE_DATE));
    }

    private void travelItinerary(List<MDetailSchedule> mDetailSchedule) {
        String text = "";
        for (int i = 0; i < mDetailSchedule.size(); ++i) {
            text = text +  mDetailSchedule.get(i).getFirstTime() + " - " + mDetailSchedule.get(i).getLastTime() + " : "+ mDetailSchedule.get(i).getSchedule() + "\n";
        }

        txtScheduleTrip.setText(text);
    }

    private void travelFaq(List<MDetailFaq> mDetailFaq) {
        String faq = "";
        for (int i = 0; i < mDetailFaq.size(); ++i){
            faq = faq + " - " + mDetailFaq.get(i).getTitle() + "\n" + mDetailFaq.get(i).getDesc() + "\n";
        }
        txtFaq.setText(faq);
    }

    @Override
    public void showProgress() {
        layProgress.setVisibility(View.VISIBLE);
    }

    @Override
    public void hideProgress() {
        layProgress.setVisibility(View.GONE);
    }

    @Override
    public void showMessage(String msg) {
        Helper.createAlert(this, Consts.STR_INFO, msg, true, new CallbackInterface() {
            @Override
            public void callback() {
                mPresenter.getDetailTour(getQueryRequest());
            }
        });
    }

    @Override
    public void notConnect(String msg) {
        layError.setVisibility(View.VISIBLE);
        mPresenter.getDetailTour(getQueryRequest());
    }

    @OnClick(R.id.btnOrderNow)
    public void orderNow() {
        if (KangenDesaApp.getInstance().isLogin()){
            Intent intent = new Intent(this, OrderActivity.class);
            intent.putExtra(Consts.ARG_ID, mItemTour.getID());
            intent.putExtra("Image", mItemTour.getMOverviewPhotosFeaturedImg().get(0));
            intent.putExtra("Title", mItemTour.getPostTitle());

            totalPembayaran = total * Integer.valueOf(price);
            Log.d("Total Pembayaran", String.valueOf(totalPembayaran));

            intent.putExtra("Price", totalPembayaran);
            intent.putExtra("Jml_wisatawan", total);

            intent.putExtra("date", date);
            startActivity(intent);
        } else {
            Helper.createAlert(this, Consts.STR_INFO, "Silahkan Login terlebih dahulu", false, new CallbackInterface() {
                @Override
                public void callback() {
                    gotoActivity(LoginActivity.class, true);
                }
            });
        }
    }

    @OnClick(R.id.btnError)
    public void reload(){
        layError.setVisibility(View.GONE);
        mPresenter.getDetailTour(getQueryRequest());
    }

    @OnClick(R.id.txtBookingDate)
    public void bookingDate(){
        DialogFragment dialogFragment = CalendarBookingDialogFragment.newInstance(this, mItemTour);
        dialogFragment.show(getSupportFragmentManager(), Consts.DIALOG);
    }

    @Override
    public void onCalendarBookingDialog(String tanggal) {
        txtBookingDate.setText(tanggal);
        date = tanggal;
    }
}
