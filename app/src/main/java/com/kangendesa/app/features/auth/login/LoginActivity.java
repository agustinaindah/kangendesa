package com.kangendesa.app.features.auth.login;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.google.gson.JsonObject;
import com.kangendesa.app.R;
import com.kangendesa.app.base.BaseActivity;
import com.kangendesa.app.features.MainActivity;
import com.kangendesa.app.features.auth.forgotpassword.ForgotPasswordActivity;
import com.kangendesa.app.utils.Consts;
import com.kangendesa.app.utils.Helper;

import butterknife.BindString;
import butterknife.BindView;
import butterknife.OnClick;

/**
 * Created by agustinaindah on 17 Januari 2019
 */
public class LoginActivity extends BaseActivity implements LoginPresenter.View {

    @BindView(R.id.edtUsername)
    EditText edtUsername;
    @BindView(R.id.edtPassword)
    EditText edtPassword;
    @BindView(R.id.txtForgotPassword)
    TextView txtForgotPassword;
    @BindView(R.id.btnLogin)
    Button btnLogin;

    @BindString(R.string.label_login)
    String strLogin;
    @BindString(R.string.msg_empty)
    String strMsgEmpty;
    @BindString(R.string.loading)
    String strLoading;
    @BindString(R.string.msg_not_connect)
    String strInfoNotConnect;

    private LoginPresenter mPresenter;

    @Override
    protected void onActivityCreated(Bundle savedInstanceState) {
        mPresenter = new LoginPresenterImpl(this);
    }

    @Override
    protected int setView() {
        return R.layout.activity_login;
    }

    @OnClick(R.id.btnLogin)
    public void Login(){
        if (!validate()){
            mPresenter.login(getInput());
        }
    }

    private boolean validate(){
        edtUsername.setError(null);
        edtPassword.setError(null);

        boolean cancel = false;
        View focus = null;

        if (TextUtils.isEmpty(getInput().get("password").getAsString())) {
            edtPassword.setError(strMsgEmpty);
            focus = edtPassword;
            cancel = true;
        }
        if (TextUtils.isEmpty(getInput().get("username").getAsString())) {
            edtUsername.setError(strMsgEmpty);
            focus = edtUsername;
            cancel = true;
        }

        if (cancel){
            focus.requestFocus();
        }
        return cancel;
    }

    private JsonObject getInput() {
        JsonObject jsonInput = new JsonObject();
        try {
            jsonInput.addProperty("username", edtUsername.getText().toString());
            jsonInput.addProperty("password", edtPassword.getText().toString());
        } catch (Exception e) {
            e.printStackTrace();
        }
        return jsonInput;
    }


    @Override
    public void successLogin(JsonObject user) {
        Intent intent = new Intent(this, MainActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(intent);
        finish();
    }

    @Override
    public void showProgress() {
        setLoading(false);
    }

    @Override
    public void hideProgress() {
        setLoading(true);
    }

    @Override
    public void showMessage(String msg) {
        Helper.createAlert(this, Consts.STR_INFO, msg);
        setLoading(true);
    }

    @Override
    public void notConnect(String msg) {
        Helper.createAlert(this, Consts.STR_INFO, msg);
    }

    private void setLoading(boolean isDisabled) {
        btnLogin.setText((isDisabled) ? strLogin : strLoading);
        btnLogin.setEnabled(isDisabled);

        edtUsername.setEnabled(isDisabled);
        edtPassword.setEnabled(isDisabled);
    }

    @OnClick(R.id.txtForgotPassword)
    public void forgotPassword(){
        gotoActivity(ForgotPasswordActivity.class, true);
    }
}
