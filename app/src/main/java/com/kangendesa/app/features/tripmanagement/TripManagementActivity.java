package com.kangendesa.app.features.tripmanagement;

import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.PorterDuff;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.widget.GridLayoutManager;
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
import com.kangendesa.app.features.create_your_trip.CreateBasicMyTourActivity;
import com.kangendesa.app.features.dashboard.TourByUserAdapter;
import com.kangendesa.app.features.dashboard.TourByUserPresenterImpl;
import com.kangendesa.app.model.ItemTourByUser;
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
 * Created by agustinaindah on 04 Februari 2019
 */
public class TripManagementActivity extends BaseActivity implements TripManagementPresenter.View{

    @BindView(R.id.fabAdd)
    FloatingActionButton fabAdd;
    @BindView(R.id.txtNoData)
    TextView txtNoData;
    @BindView(R.id.rvListMyTrip)
    RecyclerView rvListMyTrip;
    @BindView(R.id.progressBar)
    ProgressBar progressBar;
    @BindView(R.id.layError)
    LinearLayout layError;
    @BindView(R.id.btnError)
    Button btnError;

    private TripManagementPresenter mPresenter;
    private GridLayoutManager gridLayoutManager;
    private TripManagementAdapter mAdapter;

    private int lastCount = 0;
    private int totalData = 0;

    @Override
    protected void onActivityCreated(Bundle savedInstanceState) {
        final Drawable upArrow = getResources().getDrawable(R.drawable.ic_arrow_left);
        upArrow.setColorFilter(getResources().getColor(R.color.colorPrimaryDark), PorterDuff.Mode.SRC_ATOP);

        getSupportActionBar().setHomeAsUpIndicator(upArrow);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle("Trip Management");

        mPresenter = new TripManagementPresenterImpl(this);
        mPresenter.getTripManagement(getQueryRequest(Consts.FIRST_PAGE));

        gridLayoutManager = new GridLayoutManager(this, 2);
        rvListMyTrip.addOnScrollListener(new EndlessScrollListener(gridLayoutManager) {
            @Override
            public void onLoadMore(int nextPage) {
               /* if (lastCount < totalData){
                    mPresenter.getTripManagement(getQueryRequest(nextPage));
                }*/
                if (mAdapter != null) {
                    if (mAdapter.getItemCount() < totalData) {
                        mPresenter.getTripManagement(getQueryRequest(nextPage));
                    }
                }
            }
        });
    }

    private Map<String, String> getQueryRequest(int page){
        HashMap<String, String> requestMap = new HashMap<>();
        requestMap.put("user_id", SharedPref.getString(Consts.ID));
        requestMap.put("paged",String.valueOf(page));
        requestMap.put("limit", "3");
        return requestMap;
    }

    @Override
    protected int setView() {
        return R.layout.activity_my_list_trip;
    }

    @OnClick(R.id.fabAdd)
    public void addTrip(){
        Intent intent = new Intent(this, CreateBasicMyTourActivity.class);
        startActivity(intent);
    }

    @Override
    protected void onResume() {
        super.onResume();
        mAdapter = null;
    }

    @Override
    public void showTripManagement(List<ItemTourByUser> itemTourByUserList, int totalData) {
        lastCount = lastCount + itemTourByUserList.size();
        this.totalData = totalData;

        if (mAdapter == null){
            txtNoData.setVisibility((itemTourByUserList.size() == 0) ? View.VISIBLE : View.GONE);
            mAdapter = new TripManagementAdapter(itemTourByUserList, this);

            rvListMyTrip.setHasFixedSize(true);
            rvListMyTrip.setLayoutManager(gridLayoutManager);
            rvListMyTrip.setAdapter(mAdapter);
            rvListMyTrip.setNestedScrollingEnabled(false);

            Animation animation = AnimationUtils.loadAnimation(this, R.anim.up_from_bottom);
            rvListMyTrip.startAnimation(animation);
        } else {
            for (ItemTourByUser itemTourByUser : itemTourByUserList){
                mAdapter.add(itemTourByUser);
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
//        Helper.createAlert(this, Consts.STR_INFO, msg)
//        mPresenter.getTripManagement(getQueryRequest(Consts.FIRST_PAGE));
        String pesan = msg;
        if (pesan.equals("Post tidak ditemukan")){
            txtNoData.setVisibility(View.VISIBLE);
        }
    }

    @Override
    public void notConnect(String msg) {
        layError.setVisibility(View.VISIBLE);
        Helper.createAlert(this, Consts.STR_INFO, msg);
    }

    @OnClick(R.id.btnError)
    public void reload(){
        layError.setVisibility(View.GONE);
        mPresenter.getTripManagement(getQueryRequest(Consts.FIRST_PAGE));
        rvListMyTrip.setVisibility(View.VISIBLE);
    }
}
