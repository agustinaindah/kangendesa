package com.kangendesa.app.features.guide.guide_detail;

import android.app.ProgressDialog;
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
import android.widget.RatingBar;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.kangendesa.app.R;
import com.kangendesa.app.base.BaseActivity;
import com.kangendesa.app.features.dashboard.TourByUserAdapter;
import com.kangendesa.app.model.ItemGuide;
import com.kangendesa.app.model.ItemTourByUser;
import com.kangendesa.app.utils.CallbackInterface;
import com.kangendesa.app.utils.Consts;
import com.kangendesa.app.utils.EndlessScrollListener;
import com.kangendesa.app.utils.Helper;
import com.kangendesa.app.utils.SharedPref;
import com.mikhaellopez.circularimageview.CircularImageView;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * Created by agustinaindah on 23 Januari 2019
 */
public class DetailGuideActivity extends BaseActivity implements DetailGuidePresenter.View{

    @BindView(R.id.layProfil)
    RelativeLayout layProfil;
    @BindView(R.id.imgProfile)
    CircularImageView imgProfile;
    @BindView(R.id.txtProfileName)
    TextView txtProfileName;
    @BindView(R.id.ratingBar)
    RatingBar ratingBar;
    @BindView(R.id.rvListTrip)
    RecyclerView rvListTrip;
    @BindView(R.id.txtJudul)
    TextView txtJudul;

    @BindView(R.id.txtNoData)
    TextView txtNoData;
    @BindView(R.id.layError)
    LinearLayout layError;
    @BindView(R.id.btnError)
    Button btnError;

    private String id;
    private ItemGuide mItemGuide;
    private DetailGuidePresenter mPresenter;

    private int lastCount = 0;
    private int totalData = 0;

    private ProgressDialog progressDialog;
    private LinearLayoutManager linearLayoutManager;
    private TourByUserAdapter mAdapter;

    // TODO: 18/02/2019 trip by user besok di cek lagi

    @Override
    protected void onActivityCreated(Bundle savedInstanceState) {
        final Drawable upArrow = getResources().getDrawable(R.drawable.ic_arrow_left);
        upArrow.setColorFilter(getResources().getColor(R.color.colorPrimaryDark), PorterDuff.Mode.SRC_ATOP);

        getSupportActionBar().setHomeAsUpIndicator(upArrow);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setElevation(0);
        getSupportActionBar().setTitle("Pemandu Wisata");

        initLoading();

        id = getIntent().getStringExtra(Consts.ARG_ID);
        mPresenter = new DetailGuidePresenterImpl(this);
        mPresenter.getDetailGuide(getQueryRequest());
        mPresenter.getTourByUser(getRequest(Consts.FIRST_PAGE));

        linearLayoutManager = new LinearLayoutManager(this);
        rvListTrip.addOnScrollListener(new EndlessScrollListener(linearLayoutManager) {
            @Override
            public void onLoadMore(int nextPage) {
                if (lastCount < totalData){
                    mPresenter.getTourByUser(getRequest(nextPage));
                }
            }
        });
    }

    private void initLoading() {
        progressDialog = new ProgressDialog(this);
        progressDialog.setMessage(Consts.STR_LOADING);
    }

    private Map<String, String> getQueryRequest() {
        HashMap<String, String> requestMap = new HashMap<>();
        requestMap.put("id", id);
        return requestMap;
    }

    private Map<String, String> getRequest(int page){
        HashMap<String, String> requestMap = new HashMap<>();
        requestMap.put("user_id", id);
        requestMap.put("paged",String.valueOf(page));
        requestMap.put("limit", "3");
        return requestMap;
    }

    @Override
    protected int setView() {
        return R.layout.activity_detail_guide;
    }

    @Override
    public void success(ItemGuide itemGuide) {
        mItemGuide = itemGuide;
        showData(itemGuide);
    }

    @Override
    public void showTourByUser(List<ItemTourByUser> itemTourByUserList, int totalData) {
        lastCount = lastCount + itemTourByUserList.size();
        this.totalData = totalData;

        if (mAdapter == null){
            txtNoData.setVisibility((itemTourByUserList.size() == 0) ? View.VISIBLE : View.GONE);
            mAdapter = new TourByUserAdapter(itemTourByUserList, this);

            rvListTrip.setHasFixedSize(true);
            rvListTrip.setLayoutManager(linearLayoutManager);
            rvListTrip.setAdapter(mAdapter);
            rvListTrip.setNestedScrollingEnabled(false);

            Animation animation = AnimationUtils.loadAnimation(this, R.anim.up_from_bottom);
            rvListTrip.startAnimation(animation);
        } else {
            for (ItemTourByUser itemTourByUser : itemTourByUserList){
                mAdapter.add(itemTourByUser);
                mAdapter.notifyDataSetChanged();
            }
        }
    }

    private void showData(ItemGuide itemGuide) {
        if (!itemGuide.getMProfileImageUrl().isEmpty()){
            Helper.displayImage(this, itemGuide.getMProfileImageUrl(), imgProfile, true);
        }
        txtProfileName.setText(itemGuide.getDisplayName());
        ratingBar.setRating(5);
        txtJudul.setText("Trips created by " + itemGuide.getDisplayName());
    }

    @Override
    public void showProgress() {
        progressDialog.show();
    }

    @Override
    public void hideProgress() {
        progressDialog.dismiss();
    }

    @Override
    public void showMessage(String msg) {
        /*Helper.createAlert(this, Consts.STR_INFO, msg, true, new CallbackInterface() {
            @Override
            public void callback() {
                mPresenter.getDetailGuide(getQueryRequest());
                mPresenter.getTourByUser(getRequest(Consts.FIRST_PAGE));
            }
        });*/
        String pesan = msg;
        if (pesan.equals("Post tidak ditemukan")){
            txtNoData.setVisibility(View.VISIBLE);
           /* Helper.createAlert(this, Consts.STR_INFO, msg, true, new CallbackInterface() {
                @Override
                public void callback() {
                    mPresenter.getDetailGuide(getQueryRequest());
                    txtNoData.setVisibility(View.VISIBLE);
                }
            });*/
        }
    }

    @Override
    public void notConnect(String msg) {
        layError.setVisibility(View.VISIBLE);
        mPresenter.getDetailGuide(getQueryRequest());
        mPresenter.getTourByUser(getRequest(Consts.FIRST_PAGE));
    }

    @OnClick(R.id.btnError)
    public void reload(){
        layError.setVisibility(View.GONE);
        mPresenter.getDetailGuide(getQueryRequest());
        mPresenter.getTourByUser(getRequest(Consts.FIRST_PAGE));
    }
}
