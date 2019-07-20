package com.kangendesa.app.features.category;

import android.content.Intent;
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
import com.kangendesa.app.base.BaseFragment;
import com.kangendesa.app.features.guide.ListGuideActivity;
import com.kangendesa.app.model.ItemCategory;
import com.kangendesa.app.utils.EndlessScrollListener;
import com.kangendesa.app.utils.Helper;

import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * Created by agustinaindah on 01 Februari 2019
 */
public class CategoryTourFragment extends BaseFragment implements CategoryTourPresenter.View {

    @BindView(R.id.rvAllCategory)
    RecyclerView rvAllCategory;
    @BindView(R.id.txtNoData)
    TextView txtNoData;
    @BindView(R.id.txtLihatSemua)
    TextView txtLihatSemua;
    @BindView(R.id.progressBar)
    ProgressBar progressBar;
    @BindView(R.id.layError)
    LinearLayout layError;
    @BindView(R.id.btnError)
    Button btnError;

    private CategoryTourPresenter mPresenter;
    private int lastCount = 0;
    private int totalData = 0;
    private GridLayoutManager gridLayoutManager;
    private CategoryTourAdapter mAdapter;

    public static CategoryTourFragment newInstance() {
        Bundle args = new Bundle();
        CategoryTourFragment fragment = new CategoryTourFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mPresenter = new CategoryTourPresenterImpl(this);
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        mPresenter.getAllCategory();
        gridLayoutManager = new GridLayoutManager(getActivity(), 3);
        rvAllCategory.addOnScrollListener(new EndlessScrollListener(gridLayoutManager) {
            @Override
            public void onLoadMore(int nextPage) {
                if (lastCount < totalData){
                    mPresenter.getAllCategory();
                }
            }
        });
    }

    @Override
    protected int setView() {
        return R.layout.fragment_category;
    }

    @Override
    public void showAllCategory(List<ItemCategory> itemCategoryList) {
        mAdapter = new CategoryTourAdapter(itemCategoryList, getActivity());

        rvAllCategory.setHasFixedSize(true);
        rvAllCategory.setLayoutManager(gridLayoutManager);
        rvAllCategory.setAdapter(mAdapter);
        rvAllCategory.setNestedScrollingEnabled(false);

        Animation animation = AnimationUtils.loadAnimation(getActivity(), R.anim.up_from_bottom);
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
//        Helper.createAlert(getActivity(), "Info", msg);
        String pesan = msg;
        if (pesan.equals("Post tidak ditemukan")){
            txtNoData.setVisibility(View.VISIBLE);
        }
    }

    @Override
    public void notConnect(String msg) {
        layError.setVisibility(View.VISIBLE);
        Helper.createAlert(getActivity(), "Info", msg);
    }

    @OnClick(R.id.btnError)
    public void reload() {
        layError.setVisibility(View.GONE);
        mPresenter.getAllCategory();
    }

    @OnClick(R.id.txtLihatSemua)
    public void lihatSemua(){
        Intent intent = new Intent(getActivity(), CategoryTourActivity.class);
        startActivity(intent);
    }
}
