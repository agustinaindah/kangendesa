package com.kangendesa.app.features.auth.forgotpassword;

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
public class ForgotPasswordActivity extends BaseActivity implements ForgotPasswordPresenter.View {

    @BindView(R.id.edtEmail)
    EditText edtEmail;
    @BindView(R.id.btnReset)
    Button btnReset;

    @BindString(R.string.label_forgotpassword)
    String strForgotpassword;
    @BindString(R.string.msg_empty)
    String strMsgEmpty;
    @BindString(R.string.loading)
    String strLoading;
    @BindString(R.string.msg_not_connect)
    String strInfoNotConnect;

    private ForgotPasswordPresenter mPresenter;

    @Override
    protected void onActivityCreated(Bundle savedInstanceState) {
        mPresenter = new ForgotPasswordPresenterImpl(this);
    }

    @Override
    protected int setView() {
        return R.layout.activity_forgot_password;
    }

    @OnClick(R.id.btnReset)
    public void Register(){
        if (!validate()){
            mPresenter.forgotPassword(getInput());
        }
    }

    private boolean validate(){
        edtEmail.setError(null);

        boolean cancel = false;
        View focus = null;

        if (TextUtils.isEmpty(getInput().get("user_login").getAsString())) {
            edtEmail.setError(strMsgEmpty);
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
            jsonInput.addProperty("user_login", edtEmail.getText().toString());
        } catch (Exception e) {
            e.printStackTrace();
        }
        return jsonInput;
    }

    @Override
    public void successForgotPassword(JsonObject user) {
//        Helper.createAlert(this, Consts.STR_INFO, user.get("response").getAsString());
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
        btnReset.setText((isDisabled) ? strForgotpassword : strLoading);
        btnReset.setEnabled(isDisabled);

        edtEmail.setEnabled(false);
    }
}
