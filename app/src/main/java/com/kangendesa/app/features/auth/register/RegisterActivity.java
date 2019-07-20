package com.kangendesa.app.features.auth.register;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.google.gson.JsonObject;
import com.kangendesa.app.R;
import com.kangendesa.app.base.BaseActivity;
import com.kangendesa.app.features.MainActivity;
import com.kangendesa.app.features.auth.login.LoginActivity;
import com.kangendesa.app.utils.CallbackInterface;
import com.kangendesa.app.utils.Consts;
import com.kangendesa.app.utils.Helper;

import butterknife.BindString;
import butterknife.BindView;
import butterknife.OnClick;

/**
 * Created by agustinaindah on 17 Januari 2019
 */
public class RegisterActivity extends BaseActivity implements RegisterPresenter.View{

    @BindView(R.id.edtUsername)
    EditText edtUsername;
    @BindView(R.id.edtFirstName)
    EditText edtFirstName;
    @BindView(R.id.edtLastName)
    EditText edtLastName;
    @BindView(R.id.edtEmail)
    EditText edtEmail;
    @BindView(R.id.edtPassword)
    EditText edtPassword;
    @BindView(R.id.edtConfirmPassword)
    EditText edtConfirmPassword;
    @BindView(R.id.btnRegister)
    Button btnRegister;

    @BindString(R.string.label_register)
    String strRegister;
    @BindString(R.string.msg_empty)
    String strMsgEmpty;
    @BindString(R.string.loading)
    String strLoading;
    @BindString(R.string.msg_not_connect)
    String strInfoNotConnect;

    private RegisterPresenter mPresenter;

    @Override
    protected void onActivityCreated(Bundle savedInstanceState) {
        mPresenter = new RegisterPresenterImpl(this);
    }

    @Override
    protected int setView() {
        return R.layout.activity_register;
    }

    @OnClick(R.id.btnRegister)
    public void Register(){
        if (!validate()){
            mPresenter.register(getInput());
        }
    }

    private boolean validate(){
        edtUsername.setError(null);
        edtFirstName.setError(null);
        edtLastName.setError(null);
        edtEmail.setError(null);
        edtPassword.setError(null);
        edtConfirmPassword.setError(null);

        boolean cancel = false;
        View focus = null;

        if (TextUtils.isEmpty(getInput().get("username").getAsString())){
            edtUsername.setError(strMsgEmpty);
            focus = edtUsername;
            cancel = true;
        }

        if (TextUtils.isEmpty(getInput().get("first_name").getAsString())){
            edtFirstName.setError(strMsgEmpty);
            focus = edtFirstName;
            cancel = true;
        }

        if (TextUtils.isEmpty(getInput().get("last_name").getAsString())){
            edtLastName.setError(strMsgEmpty);
            focus = edtLastName;
            cancel = true;
        }

        if (TextUtils.isEmpty(getInput().get("password").getAsString())){
            edtPassword.setError(strMsgEmpty);
            focus = edtPassword;
            cancel = true;
        }

        if (TextUtils.isEmpty(getInput().get("passwordconf").getAsString())){
            edtConfirmPassword.setError(strMsgEmpty);
            focus = edtConfirmPassword;
            cancel = true;
        }

        if (TextUtils.isEmpty(getInput().get("email").getAsString())) {
            edtEmail.setError(strMsgEmpty);
            focus = edtEmail;
            cancel = true;
        } else if (!Helper.isEmail(getInput().get("email").getAsString())) {
            edtEmail.setError(getString(R.string.msg_not_valid));
            focus = edtEmail;
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
            jsonInput.addProperty("email", edtEmail.getText().toString());
            jsonInput.addProperty("first_name", edtFirstName.getText().toString());
            jsonInput.addProperty("last_name", edtLastName.getText().toString());
            jsonInput.addProperty("password", edtPassword.getText().toString());
            jsonInput.addProperty("passwordconf", edtConfirmPassword.getText().toString());
        } catch (Exception e) {
            e.printStackTrace();
        }
        return jsonInput;
    }

    @Override
    public void successRegister(JsonObject user) {
        Helper.createAlert(this, Consts.STR_INFO, user.get("response").getAsString(), false, new CallbackInterface() {
            @Override
            public void callback() {
                gotoActivity(MainActivity.class, true);
            }
        });
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
        btnRegister.setText((isDisabled) ? strRegister : strLoading);
        btnRegister.setEnabled(isDisabled);

        edtUsername.setEnabled(isDisabled);
        edtFirstName.setEnabled(isDisabled);
        edtLastName.setEnabled(isDisabled);
        edtEmail.setEnabled(isDisabled);
        edtPassword.setEnabled(isDisabled);
        edtConfirmPassword.setEnabled(isDisabled);
    }
}
