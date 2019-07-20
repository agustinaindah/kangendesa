package com.kangendesa.app.features.banner;

import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.kangendesa.app.R;
import com.kangendesa.app.base.BaseFragment;
import com.kangendesa.app.model.TourDummy;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;

/**
 * Created by agustinaindah on 24 Januari 2019
 */
public class BannerFragment extends BaseFragment {

    @BindView(R.id.rvBanner)
    RecyclerView rvBanner;

    private List<TourDummy> tourDummyList = new ArrayList<>();
    private BannerAdapter mAdapter;

    public static BannerFragment newInstance() {
        Bundle args = new Bundle();
        BannerFragment fragment = new BannerFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    protected int setView() {
        return R.layout.fragment_banner;
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        mAdapter = new BannerAdapter(getActivity(), tourDummyList);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getActivity(),
                LinearLayoutManager.HORIZONTAL, false);
        rvBanner.setLayoutManager(linearLayoutManager);
        rvBanner.setAdapter(mAdapter);
        allBannerList();
    }

    private void allBannerList() {
        Uri uri1 = Uri.parse("android.resource://com.kangendesa.app/drawable/banner_kangendesa1");
        Uri uri2 = Uri.parse("android.resource://com.kangendesa.app/drawable/banner_kangendesa2");
        Uri uri3 = Uri.parse("android.resource://com.kangendesa.app/drawable/banner_kangendesa3");
        TourDummy productOne = new TourDummy(uri1);
        TourDummy productTwo = new TourDummy(uri2);
        TourDummy productThree = new TourDummy(uri3);

        tourDummyList.add(productOne);
        tourDummyList.add(productTwo);
        tourDummyList.add(productThree);
        mAdapter.notifyDataSetChanged();
    }
}
