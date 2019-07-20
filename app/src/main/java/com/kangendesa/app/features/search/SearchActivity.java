package com.kangendesa.app.features.search;

import android.app.SearchManager;
import android.content.Context;
import android.graphics.PorterDuff;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SearchView;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.view.inputmethod.EditorInfo;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.kangendesa.app.R;
import com.kangendesa.app.base.BaseActivity;
import com.kangendesa.app.features.tour.ListTourAdapter;
import com.kangendesa.app.model.ItemTour;
import com.kangendesa.app.utils.CallbackInterface;
import com.kangendesa.app.utils.Consts;
import com.kangendesa.app.utils.Helper;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * Created by agustinaindah on 21 Januari 2019
 */
public class SearchActivity extends BaseActivity implements SearchView.OnQueryTextListener,
        SearchPresenter.View {

    @BindView(R.id.rvSearch)
    RecyclerView rvSearch;
    @BindView(R.id.txtNoData)
    TextView txtNoData;
    @BindView(R.id.layProgress)
    RelativeLayout layProgress;
    @BindView(R.id.layError)
    LinearLayout layError;
    @BindView(R.id.btnError)
    Button btnError;

    private MenuItem mSearch;
    private SearchView searchView;
    private String searchInput;
    private String mQuery;

    private SearchTourAdapter mAdapter;
    private SearchPresenter mPresenter;

    private int lastCount = 0;
    private int totalData = 0;
    private int next = 0;

    @Override
    protected void onActivityCreated(Bundle savedInstanceState) {
        final Drawable upArrow = getResources().getDrawable(R.drawable.ic_arrow_left);
        upArrow.setColorFilter(getResources().getColor(R.color.colorPrimaryDark), PorterDuff.Mode.SRC_ATOP);

        getSupportActionBar().setHomeAsUpIndicator(upArrow);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        mPresenter = new SearchPresenterImpl(this);
    }

    @Override
    protected int setView() {
        return R.layout.activity_search;
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_search, menu);

        mSearch = menu.findItem(R.id.menu_search);

        SearchManager searchManager =
                (SearchManager) getSystemService(Context.SEARCH_SERVICE);
        searchView = (SearchView) mSearch.getActionView();
        searchView.setSearchableInfo(searchManager.getSearchableInfo(getComponentName()));

        setupSearchView();

        return true;
    }

    private void setupSearchView() {
        EditText search_edit_text = ((EditText)
                searchView.findViewById(android.support.v7.appcompat.R.id.search_src_text));
        search_edit_text.setHint("Pergi Kemana?");
        search_edit_text.setHintTextColor(getResources().getColor(R.color.colorPrimaryDark));
        search_edit_text.setTextColor(getResources().getColor(R.color.colorPrimaryDark));

        searchView.setQuery(searchInput, false);
        searchView.setIconified(false);
        searchView.setImeOptions(EditorInfo.IME_ACTION_SEARCH);
        searchView.setOnQueryTextListener(this);
    }

    @Override
    public boolean onQueryTextSubmit(String query) {
        mQuery = query;
        mPresenter.getSearch(getQueryRequest(Consts.FIRST_PAGE));
        Helper.hideKeyboard(this);
        return true;
    }

    @Override
    public boolean onQueryTextChange(String s) {
        return false;
    }

    private Map<String, String> getQueryRequest(int page){
        HashMap<String, String> requestMap = new HashMap<>();
        requestMap.put("s", mQuery);
        requestMap.put("paged", String.valueOf(page));
        requestMap.put("limit", "3");
        return requestMap;
    }

    @Override
    public void success(List<ItemTour> itemTours, int page) {
        lastCount = lastCount + itemTours.size();
        this.totalData = page;

        if (mAdapter == null) {
            txtNoData.setVisibility((itemTours.size() == 0) ? View.VISIBLE : View.GONE);
            mAdapter = new SearchTourAdapter(itemTours, this);

            rvSearch.setHasFixedSize(true);
            rvSearch.setLayoutManager(new GridLayoutManager(this, 2));
            rvSearch.setAdapter(mAdapter);
            rvSearch.setNestedScrollingEnabled(false);

            Animation animation = AnimationUtils.loadAnimation(this, R.anim.up_from_bottom);
            rvSearch.startAnimation(animation);
        } else {
            for (ItemTour itemTour : itemTours) {
                mAdapter.add(itemTour);
                mAdapter.notifyDataSetChanged();
            }
        }
    }

    @Override
    public void noData() {
        txtNoData.setVisibility(View.VISIBLE);
        rvSearch.setAdapter(null);
    }

    @Override
    public void showProgress() {
        layProgress.setVisibility(View.VISIBLE);
        txtNoData.setVisibility(View.GONE);
    }

    @Override
    public void hideProgress() {
        layProgress.setVisibility(View.GONE);
    }

    @Override
    public void showMessage(String msg) {
        /*Helper.createAlert(this, "Info", msg, true, new CallbackInterface() {
            @Override
            public void callback() {
                mPresenter.getSearch(getQueryRequest(Consts.FIRST_PAGE));
            }
        });*/
        String pesan = msg;
        if (pesan.equals("Post tidak ditemukan")){
            txtNoData.setVisibility(View.VISIBLE);
        }
    }

    @Override
    public void notConnect(String msg) {
        layError.setVisibility(View.VISIBLE);
    }

    @OnClick(R.id.btnError)
    public void reload(){
        layError.setVisibility(View.GONE);
        mPresenter.getSearch(getQueryRequest(Consts.FIRST_PAGE));
        rvSearch.setVisibility(View.VISIBLE);
    }
}
