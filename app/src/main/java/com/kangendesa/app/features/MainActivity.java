package com.kangendesa.app.features;

import android.content.Intent;
import android.support.annotation.IdRes;
import android.support.design.widget.AppBarLayout;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;

import com.kangendesa.app.KangenDesaApp;
import com.kangendesa.app.R;
import com.kangendesa.app.base.BaseActivity;
import com.kangendesa.app.features.account_setting.AccountSettingActivity;
import com.kangendesa.app.features.account_setting.BeforeLoginFragment;
import com.kangendesa.app.features.inbox.InboxFragment;
import com.kangendesa.app.features.search.SearchActivity;
import com.kangendesa.app.features.tripmanagement.TripManagementActivity;
import com.kangendesa.app.utils.Helper;
import com.roughike.bottombar.BottomBar;
import com.roughike.bottombar.OnTabSelectListener;

import butterknife.BindString;
import butterknife.BindView;

public class MainActivity extends BaseActivity implements OnTabSelectListener {

    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.collapsingToolbar)
    CollapsingToolbarLayout collapsingToolbar;
    @BindView(R.id.appBarLayout)
    AppBarLayout appBarLayout;
    @BindView(R.id.bottomBar)
    BottomBar bottomBar;

    @BindString(R.string.action_back_pressed)
    String strBackPressed;
    @BindString(R.string.app_name)
    String strAppName;

    private boolean is_login = false;

    private Fragment mFragment = null;
    private FragmentManager fm;
    private long backPressedTime = 0;
    private boolean isHome = true;

    @Override
    protected void onActivityCreated(Bundle savedInstanceState) {
        is_login = KangenDesaApp.getInstance().isLogin();
        fm = getSupportFragmentManager();
        setSupportActionBar(toolbar);
        bottomBar.setOnTabSelectListener(this);
        collapsingToolbar.setTitle(" ");

        appBarLayout.addOnOffsetChangedListener(new AppBarLayout.OnOffsetChangedListener() {
            boolean isShow = false;
            int scrollRange = -1;

            @Override
            public void onOffsetChanged(AppBarLayout appBarLayout, int verticalOffset) {
                if (scrollRange == -1) {
                    scrollRange = appBarLayout.getTotalScrollRange();
                }
                if (scrollRange + verticalOffset == 0) {
                    collapsingToolbar.setTitle(strAppName);
                    isShow = true;
                } else if (isShow) {
                    collapsingToolbar.setTitle(" ");
                    isShow = false;
                }
            }
        });
    }

    private void goHome() {
        bottomBar.selectTabWithId(R.id.tab_home);
    }

    @Override
    protected int setView() {
        return R.layout.activity_main;
    }

    @Override
    public void onBackPressed() {
        if (isHome == false) {
            goHome();
        } else {
            long t = System.currentTimeMillis();
            if (t - backPressedTime > 2000) {
                backPressedTime = t;
                Helper.createToast(this, strBackPressed);
            } else {
                super.onBackPressed();
            }
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        bottomBar.selectTabWithId(R.id.tab_home);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_search:
                startActivity(new Intent(this, SearchActivity.class));
                break;
            default:
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onTabSelected(@IdRes int tabId) {
        switch (tabId) {
            case R.id.tab_home:
                isHome = true;
                mFragment = MainFragment.newInstance();
                setRemoveBackStack();
                break;
            case R.id.tab_inbox:
                isHome = false;
                mFragment = InboxFragment.newInstance();
                break;
            case R.id.tab_pusat_bantuan:
                isHome = false;
                Intent intentHelp = new Intent(this, HelpDeskActivity.class);
                startActivity(intentHelp);
                break;
            case R.id.tab_daftar_trip:
                if (is_login) {
                    isHome = false;
                    Intent intent1 = new Intent(this, TripManagementActivity.class);
                    startActivity(intent1);
                } else {
                    isHome = false;
                    mFragment = BeforeLoginFragment.newInstance();
                }
                break;
            case R.id.tab_akun:
                if (is_login) {
                    isHome = false;
                    Intent intent = new Intent(this, AccountSettingActivity.class);
                    startActivity(intent);
                } else {
                    isHome = false;
                    mFragment = BeforeLoginFragment.newInstance();
                }
                break;
        }
        if (mFragment != null) {
            gotoFragment(fm, mFragment);
        }
    }

    private void setRemoveBackStack() {
        fm.popBackStack(null, FragmentManager.POP_BACK_STACK_INCLUSIVE);
    }

}
