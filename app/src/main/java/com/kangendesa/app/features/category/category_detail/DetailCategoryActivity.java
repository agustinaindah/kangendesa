package com.kangendesa.app.features.category.category_detail;

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
import com.kangendesa.app.features.search.SearchTourAdapter;
import com.kangendesa.app.model.ItemTour;
import com.kangendesa.app.utils.Consts;
import com.kangendesa.app.utils.EndlessScrollListener;
import com.kangendesa.app.utils.Helper;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * Created by agustinaindah on 04 Februari 2019
 */
public class DetailCategoryActivity extends BaseActivity implements DetailCategoryPresenter.View{

    @BindView(R.id.rvAllTour)
    RecyclerView rvAllTour;
    @BindView(R.id.progressBar)
    ProgressBar progressBar;
    @BindView(R.id.txtNoData)
    TextView txtNoData;
    @BindView(R.id.layError)
    LinearLayout layError;
    @BindView(R.id.btnError)
    Button btnError;

    private DetailCategoryPresenter mPresenter;
    private int lastCount = 0;
    private int totalData = 0;
    private GridLayoutManager gridLayoutManager;
    private SearchTourAdapter mAdapter;

    private String title;
    private String slug;

    @Override
    protected void onActivityCreated(Bundle savedInstanceState) {

        title = getIntent().getStringExtra("name");
        slug = getIntent().getStringExtra("slug");

        final Drawable upArrow = getResources().getDrawable(R.drawable.ic_arrow_left);
        upArrow.setColorFilter(getResources().getColor(R.color.colorPrimaryDark), PorterDuff.Mode.SRC_ATOP);

        getSupportActionBar().setHomeAsUpIndicator(upArrow);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle(title);

        mPresenter = new DetailCategoryPresenterImpl(this);
        mPresenter.getListCategory(getQueryRequest(Consts.FIRST_PAGE));

        gridLayoutManager = new GridLayoutManager(this, 2);
        rvAllTour.addOnScrollListener(new EndlessScrollListener(gridLayoutManager) {
            @Override
            public void onLoadMore(int nextPage) {
                if (lastCount < totalData){
                    mPresenter.getListCategory(getQueryRequest(nextPage));
                }
            }
        });
    }

    @Override
    protected int setView() {
        return R.layout.activity_tour;
    }

    private Map<String, String> getQueryRequest(int page){
        HashMap<String, String> requestMap = new HashMap<>();
        requestMap.put("tax_slug",slug);
        requestMap.put("paged",String.valueOf(page));
        requestMap.put("limit", "3");
        return requestMap;
    }

    @Override
    public void showListCategory(List<ItemTour> itemTourList, int totalData) {
        lastCount = lastCount + itemTourList.size();
        this.totalData = totalData;

        if (mAdapter == null){
            txtNoData.setVisibility((itemTourList.size() == 0) ? View.VISIBLE : View.GONE);
            mAdapter = new SearchTourAdapter(itemTourList, this);

            rvAllTour.setHasFixedSize(true);
            rvAllTour.setLayoutManager(gridLayoutManager);
            rvAllTour.setAdapter(mAdapter);
            rvAllTour.setNestedScrollingEnabled(false);

            Animation animation = AnimationUtils.loadAnimation(this, R.anim.up_from_bottom);
            rvAllTour.startAnimation(animation);
        } else {
            for (ItemTour itemTour : itemTourList){
                mAdapter.add(itemTour);
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
        if (pesan.equals("Post tidak ditemukan")){
            txtNoData.setVisibility(View.VISIBLE);
        }
    }

    @Override
    public void notConnect(String msg) {
        layError.setVisibility(View.VISIBLE);
        Helper.createAlert(this, "Info", msg);
        mPresenter.getListCategory(getQueryRequest(Consts.FIRST_PAGE));
    }

    @OnClick(R.id.btnError)
    public void reload(){
        layError.setVisibility(View.GONE);
        mPresenter.getListCategory(getQueryRequest(Consts.FIRST_PAGE));
        rvAllTour.setVisibility(View.VISIBLE);
    }
}
