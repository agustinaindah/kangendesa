package com.kangendesa.app.features.category;

import android.graphics.PorterDuff;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
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
import com.kangendesa.app.model.ItemCategory;
import com.kangendesa.app.utils.EndlessScrollListener;
import com.kangendesa.app.utils.Helper;

import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * Created by agustinaindah on 04 Februari 2019
 */
public class CategoryTourActivity extends BaseActivity implements CategoryTourPresenter.View{

    @BindView(R.id.rvAllCategory)
    RecyclerView rvAllCategory;
    @BindView(R.id.progressBar)
    ProgressBar progressBar;
    @BindView(R.id.txtNoData)
    TextView txtNoData;
    @BindView(R.id.layError)
    LinearLayout layError;
    @BindView(R.id.btnError)
    Button btnError;

    private CategoryTourPresenter mPresenter;
    private GridLayoutManager gridLayoutManager;
    private CategoryTourAdapter mAdapter;

    @Override
    protected void onActivityCreated(Bundle savedInstanceState) {
        final Drawable upArrow = getResources().getDrawable(R.drawable.ic_arrow_left);
        upArrow.setColorFilter(getResources().getColor(R.color.colorPrimaryDark), PorterDuff.Mode.SRC_ATOP);

        getSupportActionBar().setHomeAsUpIndicator(upArrow);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle("Destinasi Wisata Popular");

        mPresenter = new CategoryTourPresenterImpl(this);
        mPresenter.getAllCategory();

    }

    @Override
    protected int setView() {
        return R.layout.activity_list_category;
    }

    @Override
    public void showAllCategory(List<ItemCategory> itemCategoryList) {
        mAdapter = new CategoryTourAdapter(itemCategoryList, this);

        rvAllCategory.setHasFixedSize(true);
        rvAllCategory.setLayoutManager(new GridLayoutManager(this, 2));
        rvAllCategory.setAdapter(mAdapter);
        rvAllCategory.setNestedScrollingEnabled(false);

        Animation animation = AnimationUtils.loadAnimation(this, R.anim.up_from_bottom);
        rvAllCategory.startAnimation(animation);
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
        if (pesan.equals("Post tidak ditemukan")){
            txtNoData.setVisibility(View.VISIBLE);
        }
    }

    @Override
    public void notConnect(String msg) {
        layError.setVisibility(View.VISIBLE);
        Helper.createAlert(this, "Info", msg);
    }

    @OnClick(R.id.btnError)
    public void reload() {
        layError.setVisibility(View.GONE);
        mPresenter.getAllCategory();
    }
}
