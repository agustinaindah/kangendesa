package com.kangendesa.app.features.account_setting;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.RelativeLayout;

import com.kangendesa.app.R;
import com.kangendesa.app.base.BaseFragment;
import com.kangendesa.app.features.auth.login.LoginActivity;
import com.kangendesa.app.features.auth.register.RegisterActivity;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * Created by agustinaindah on 04 Februari 2019
 */
public class BeforeLoginFragment extends BaseFragment {

    @BindView(R.id.btnLogin)
    Button btnLogin;
    @BindView(R.id.btnRegister)
    Button btnRegister;

    public static BeforeLoginFragment newInstance() {
        Bundle args = new Bundle();
        BeforeLoginFragment fragment = new BeforeLoginFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    protected int setView() {
        return R.layout.fragment_before_login;
    }

    @OnClick(R.id.btnLogin)
    public void login() {
        Intent intent = new Intent(getActivity(), LoginActivity.class);
        startActivity(intent);
    }

    @OnClick(R.id.btnRegister)
    public void register() {
        Intent intent = new Intent(getActivity(), RegisterActivity.class);
        startActivity(intent);
    }
}
