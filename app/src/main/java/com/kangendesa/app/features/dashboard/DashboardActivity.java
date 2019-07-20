package com.kangendesa.app.features.dashboard;

import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.PorterDuff;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.RatingBar;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.JsonObject;
import com.kangendesa.app.R;
import com.kangendesa.app.base.BaseActivity;
import com.kangendesa.app.features.dashboard.change_password.ChangePasswordActivity;
import com.kangendesa.app.features.dashboard.edit_profile.EditProfileActivity;
import com.kangendesa.app.features.dashboard.edit_profile.EditProfilePresenter;
import com.kangendesa.app.features.dashboard.edit_profile.EditProfilePresenterImpl;
import com.kangendesa.app.features.bookingmanagement.traveler.mybooking.MyBookingAdapter;
import com.kangendesa.app.features.search.SearchActivity;
import com.kangendesa.app.model.ItemListOrder;
import com.kangendesa.app.model.ItemTourByUser;
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
 * Created by agustinaindah on 09 Februari 2019
 */
public class DashboardActivity extends BaseActivity implements TourByUserPresenter.View{

    @BindView(R.id.imgProfile)
    CircularImageView imgProfile;
    @BindView(R.id.txtProfileName)
    TextView txtProfileName;
    @BindView(R.id.ratingBar)
    RatingBar ratingBar;
    @BindView(R.id.btnEditProfile)
    Button btnEditProfile;
    @BindView(R.id.txtNoData)
    TextView txtNoData;

    @BindView(R.id.txtJudul)
    TextView txtJudul;
    @BindView(R.id.rvListTrip)
    RecyclerView rvListTrip;

    private String username;
    private String foto;

    private ProgressDialog progressDialog;
    private TourByUserPresenter mPresenter;
    private LinearLayoutManager linearLayoutManager;
    private TourByUserAdapter mAdapter;

    private int lastCount = 0;
    private int totalData = 0;

    @Override
    protected void onActivityCreated(Bundle savedInstanceState) {

        final Drawable upArrow = getResources().getDrawable(R.drawable.ic_arrow_left);
        upArrow.setColorFilter(getResources().getColor(R.color.colorPrimaryDark), PorterDuff.Mode.SRC_ATOP);

        getSupportActionBar().setHomeAsUpIndicator(upArrow);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle("Dashboard");

        initLoading();

        mPresenter = new TourByUserPresenterImpl(this);
        mPresenter.getProfile();
        mPresenter.getTourByUser(getQueryRequest(Consts.FIRST_PAGE));

        linearLayoutManager = new LinearLayoutManager(this);
        rvListTrip.addOnScrollListener(new EndlessScrollListener(linearLayoutManager) {
            @Override
            public void onLoadMore(int nextPage) {
                if (lastCount < totalData){
                    mPresenter.getTourByUser(getQueryRequest(nextPage));
                }
            }
        });

        displayData();
    }

    private Map<String, String> getQueryRequest(int page){
        HashMap<String, String> requestMap = new HashMap<>();
        requestMap.put("user_id", SharedPref.getString(Consts.ID));
        requestMap.put("paged",String.valueOf(page));
        requestMap.put("limit", "3");
        return requestMap;
    }

    private void initLoading() {
        progressDialog = new ProgressDialog(this);
        progressDialog.setMessage(Consts.STR_LOADING);
    }

    private void displayData() {
        ratingBar.setRating(5);
    }

    @Override
    protected int setView() {
        return R.layout.activity_dashboard_profile;
    }

    @OnClick(R.id.btnEditProfile)
    public void editProfile(){
        Intent intent = new Intent(this, EditProfileActivity.class);
        startActivity(intent);
    }

    @Override
    protected void onResume() {
        super.onResume();
        mPresenter.getProfile();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_account_setting, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_setting_account:
                startActivity(new Intent(this, ChangePasswordActivity.class));
                break;
            default:
                break;
        }
        return super.onOptionsItemSelected(item);
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
        Helper.createAlert(this, Consts.STR_INFO, msg);
    }

    @Override
    public void notConnect(String msg) {
        Helper.createAlert(this, Consts.STR_INFO, msg);
    }

    @Override
    public void displayProfile(JsonObject jsonData) {
        String mFoto = jsonData.get("profile_image_url").getAsString();
        if (!mFoto.isEmpty()){
            Helper.displayImage(this, mFoto, imgProfile, true);
        }

        txtProfileName.setText(jsonData.get("username").getAsString());
        txtJudul.setText("Trips created by " + jsonData.get("username").getAsString());
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
}
