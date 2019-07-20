package com.kangendesa.app.features.tour;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
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
import com.kangendesa.app.base.BaseFragment;
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
 * Created by agustinaindah on 21 Januari 2019
 */
public class ListTourFragment extends BaseFragment implements ListTourPresenter.View {

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
    @BindView(R.id.txtLihatSemua)
    TextView txtLihatSemua;

    private ListTourAdapter mAdapter;
    private ListTourPresenter mPresenter;
    private int lastCount = 0;
    private int totalData = 0;
    private GridLayoutManager gridLayoutManager;

    public static ListTourFragment newInstance() {
        Bundle args = new Bundle();
        ListTourFragment fragment = new ListTourFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mPresenter = new ListTourPresenterImpl(this);
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        mPresenter.getListTour(getRequest(Consts.FIRST_PAGE));
        gridLayoutManager = new GridLayoutManager(getActivity(), 3);
        rvAllTour.addOnScrollListener(new EndlessScrollListener(gridLayoutManager) {
            @Override
            public void onLoadMore(int nextPage) {
                if (lastCount < totalData) {
                    mPresenter.getListTour(getRequest(nextPage));
                }
            }
        });
    }

    @Override
    protected int setView() {
        return R.layout.fragment_main;
    }

    private Map<String, String> getRequest(int page) {
        HashMap<String, String> requestMap = new HashMap<>();
        requestMap.put("paged", String.valueOf(page));
        requestMap.put("limit", "3");
        return requestMap;
    }

    @Override
    public void showListTour(List<ItemTour> itemTourList, int totalData) {
        lastCount = lastCount + itemTourList.size();
        this.totalData = totalData;

        if (mAdapter == null) {
            txtNoData.setVisibility((itemTourList.size() == 0) ? View.VISIBLE : View.GONE);
            mAdapter = new ListTourAdapter(itemTourList, getActivity());

            rvAllTour.setHasFixedSize(true);
            rvAllTour.setLayoutManager(gridLayoutManager);
            rvAllTour.setAdapter(mAdapter);
            rvAllTour.setNestedScrollingEnabled(false);

            Animation animation = AnimationUtils.loadAnimation(getActivity(), R.anim.up_from_bottom);
            rvAllTour.startAnimation(animation);
        } else {
            for (ItemTour itemTour : itemTourList) {
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
        if (progressBar != null) {
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
        mPresenter.getListTour(getRequest(Consts.FIRST_PAGE));
    }

    @OnClick(R.id.btnError)
    public void reload() {
        layError.setVisibility(View.GONE);
        mPresenter.getListTour(getRequest(Consts.FIRST_PAGE));
        rvAllTour.setVisibility(View.VISIBLE);
    }

    @OnClick(R.id.txtLihatSemua)
    public void lihatSemua(){
        Intent intent = new Intent(getActivity(), ListTourActivity.class);
        startActivity(intent);
    }
}
