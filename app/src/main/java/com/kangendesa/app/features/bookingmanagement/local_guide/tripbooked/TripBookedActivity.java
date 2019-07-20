package com.kangendesa.app.features.bookingmanagement.local_guide.tripbooked;

import android.graphics.PorterDuff;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.kangendesa.app.R;
import com.kangendesa.app.base.BaseActivity;
import com.kangendesa.app.features.bookingmanagement.traveler.mybooking.MyBookingAdapter;
import com.kangendesa.app.model.ItemListOrder;
import com.kangendesa.app.model.ItemTripBooked;
import com.kangendesa.app.utils.Consts;
import com.kangendesa.app.utils.EndlessScrollListener;
import com.kangendesa.app.utils.Helper;
import com.kangendesa.app.utils.SharedPref;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * Created by agustinaindah on 18 Februari 2019
 */
public class TripBookedActivity extends BaseActivity implements TripBookedPresenter.View{

    @BindView(R.id.rvListOrder)
    RecyclerView rvListOrder;
    @BindView(R.id.progressBar)
    ProgressBar progressBar;
    @BindView(R.id.txtNoData)
    TextView txtNoData;
    @BindView(R.id.layError)
    LinearLayout layError;
    @BindView(R.id.btnError)
    Button btnError;

    private TripBookedPresenter mPresenter;
    private int lastCount = 0;
    private int totalData = 0;
    private LinearLayoutManager linearLayoutManager;
    private TripBookedAdapter mAdapter;

    @Override
    protected void onActivityCreated(Bundle savedInstanceState) {
        final Drawable upArrow = getResources().getDrawable(R.drawable.ic_arrow_left);
        upArrow.setColorFilter(getResources().getColor(R.color.colorPrimaryDark), PorterDuff.Mode.SRC_ATOP);

        getSupportActionBar().setHomeAsUpIndicator(upArrow);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle("Trip Booked");

        mPresenter = new TripBookedPresenterImpl(this);
        mPresenter.getTripBooked(getQueryRequest(Consts.FIRST_PAGE));

        linearLayoutManager = new LinearLayoutManager(this);
        rvListOrder.addOnScrollListener(new EndlessScrollListener(linearLayoutManager) {
            @Override
            public void onLoadMore(int nextPage) {
                if (lastCount < totalData){
                    mPresenter.getTripBooked(getQueryRequest(nextPage));
                }
            }
        });
    }

    @Override
    protected int setView() {
        return R.layout.activity_list_booking_management;
    }

    private Map<String, String> getQueryRequest(int page){
        HashMap<String, String> requestMap = new HashMap<>();
        requestMap.put("paged",String.valueOf(page));
        requestMap.put("limit", "3");
        return requestMap;
    }

    @Override
    public void showTripBooked(List<ItemTripBooked> tripBookedList, int totalData) {
        lastCount = lastCount + tripBookedList.size();
        this.totalData = totalData;

        if (mAdapter == null){
            txtNoData.setVisibility((tripBookedList.size() == 0) ? View.VISIBLE : View.GONE);
            mAdapter = new TripBookedAdapter(tripBookedList, this);

            rvListOrder.setHasFixedSize(true);
            rvListOrder.setLayoutManager(linearLayoutManager);
            rvListOrder.setAdapter(mAdapter);
            rvListOrder.setNestedScrollingEnabled(false);

            Animation animation = AnimationUtils.loadAnimation(this, R.anim.up_from_bottom);
            rvListOrder.startAnimation(animation);
        } else {
            for (ItemTripBooked itemTripBooked : tripBookedList){
                mAdapter.add(itemTripBooked);
                mAdapter.notifyDataSetChanged();
            }
        }
    }

    @Override
    public void showProgress() {
        progressBar.setVisibility(View.VISIBLE);
    }

    @Override
    public void hideProgress() {
        if (progressBar != null){
            progressBar.setVisibility(View.GONE);
        }
    }

    @Override
    public void showMessage(String msg) {
//        Helper.createAlert(this, "Info", msg);
        String pesan = msg;
        if (pesan.equals("Data is Empty")){
            txtNoData.setVisibility(View.VISIBLE);
        }
    }

    @Override
    public void notConnect(String msg) {
        layError.setVisibility(View.VISIBLE);
        mPresenter.getTripBooked(getQueryRequest(Consts.FIRST_PAGE));
    }

    @OnClick(R.id.btnError)
    public void reload(){
        layError.setVisibility(View.GONE);
        mPresenter.getTripBooked(getQueryRequest(Consts.FIRST_PAGE));
        rvListOrder.setVisibility(View.VISIBLE);
    }
}
