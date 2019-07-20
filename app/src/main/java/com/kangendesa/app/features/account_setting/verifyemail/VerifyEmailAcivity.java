package com.kangendesa.app.features.account_setting.verifyemail;

import android.graphics.PorterDuff;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.google.gson.JsonObject;
import com.kangendesa.app.R;
import com.kangendesa.app.base.BaseActivity;
import com.kangendesa.app.features.account_setting.AccountSettingActivity;
import com.kangendesa.app.utils.CallbackInterface;
import com.kangendesa.app.utils.Consts;
import com.kangendesa.app.utils.Helper;

import butterknife.BindString;
import butterknife.BindView;
import butterknife.OnClick;

/**
 * Created by agustinaindah on 18 Februari 2019
 */
public class VerifyEmailAcivity extends BaseActivity implements VerifyEmailPresenter.View{

    @BindView(R.id.edtEmail)
    EditText edtEmail;
    @BindView(R.id.edtVerifyCode)
    EditText edtVerifyCode;
    @BindView(R.id.btnSend)
    Button btnSend;

    @BindString(R.string.msg_empty)
    String strMsgEmpty;
    @BindString(R.string.loading)
    String strLoading;

    private VerifyEmailPresenter mPresenter;

    @Override
    protected void onActivityCreated(Bundle savedInstanceState) {
        final Drawable upArrow = getResources().getDrawable(R.drawable.ic_arrow_left);
        upArrow.setColorFilter(getResources().getColor(R.color.colorPrimaryDark), PorterDuff.Mode.SRC_ATOP);

        getSupportActionBar().setHomeAsUpIndicator(upArrow);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle("Verifikasi Email");

        mPresenter = new VerifyEmailPresenterImpl(this);
    }

    @Override
    protected int setView() {
        return R.layout.activity_verify_email;
    }

    @OnClick(R.id.btnSend)
    public void sendVerifyEmail(){
        if (!validate()){
            mPresenter.postVerifyEmail(getInput());
        }
    }

    private boolean validate(){
        edtEmail.setError(null);
        edtVerifyCode.setError(null);

        boolean cancel = false;
        View focus = null;

        if (TextUtils.isEmpty(getInput().get("email").getAsString())) {
            edtEmail.setError(strMsgEmpty);
            focus = edtEmail;
            cancel = true;
        }

        if (TextUtils.isEmpty(getInput().get("verify_code").getAsString())) {
            edtVerifyCode.setError(strMsgEmpty);
            focus = edtVerifyCode;
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
            jsonInput.addProperty("email", edtEmail.getText().toString());
            jsonInput.addProperty("verify_code", edtVerifyCode.getText().toString());
        } catch (Exception e) {
            e.printStackTrace();
        }
        return jsonInput;
    }

    @Override
    public void successVerifyEmail(JsonObject jsonRequest) {
        Helper.createAlert(this, Consts.STR_INFO, jsonRequest.get("response").getAsString(), false, new CallbackInterface() {
            @Override
            public void callback() {
                gotoActivity(AccountSettingActivity.class, true);
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
        btnSend.setText((isDisabled) ? "Verifikasi Email Saya" : strLoading);
        btnSend.setEnabled(isDisabled);

        edtEmail.setEnabled(false);
        edtVerifyCode.setEnabled(false);
    }
}
