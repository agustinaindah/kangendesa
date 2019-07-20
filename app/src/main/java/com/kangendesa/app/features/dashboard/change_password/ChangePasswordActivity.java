package com.kangendesa.app.features.dashboard.change_password;

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
import com.kangendesa.app.features.MainActivity;
import com.kangendesa.app.features.account_setting.AccountSettingActivity;
import com.kangendesa.app.utils.CallbackInterface;
import com.kangendesa.app.utils.Consts;
import com.kangendesa.app.utils.Helper;

import butterknife.BindString;
import butterknife.BindView;
import butterknife.OnClick;

/**
 * Created by agustinaindah on 12 Februari 2019
 */
public class ChangePasswordActivity extends BaseActivity implements ChangePasswordPresenter.View {

    @BindView(R.id.edtNewPass)
    EditText edtNewPass;
    @BindView(R.id.edtConfirmPass)
    EditText edtConfirmPass;
    @BindView(R.id.btnSavePassword)
    Button btnSavePassword;

    @BindString(R.string.msg_empty)
    String strMsgEmpty;
    @BindString(R.string.loading)
    String strLoading;

    private ChangePasswordPresenter mPresenter;

    @Override
    protected void onActivityCreated(Bundle savedInstanceState) {

        final Drawable upArrow = getResources().getDrawable(R.drawable.ic_arrow_left);
        upArrow.setColorFilter(getResources().getColor(R.color.colorPrimaryDark), PorterDuff.Mode.SRC_ATOP);

        getSupportActionBar().setHomeAsUpIndicator(upArrow);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle("Set Password");

        mPresenter = new ChangePasswordPresenterImpl(this);
    }

    @Override
    protected int setView() {
        return R.layout.activity_set_password;
    }

    @OnClick(R.id.btnSavePassword)
    public void changePassword(){
        if (!validate()){
            mPresenter.postChangePassword(getInput());
        }
    }

    private boolean validate(){
        edtNewPass.setError(null);
        edtConfirmPass.setError(null);

        boolean cancel = false;
        View focus = null;

        if (TextUtils.isEmpty(getInput().get("password_new").getAsString())) {
            edtNewPass.setError(strMsgEmpty);
            focus = edtNewPass;
            cancel = true;
        }

        if (TextUtils.isEmpty(getInput().get("password_new_conf").getAsString())) {
            edtConfirmPass.setError(strMsgEmpty);
            focus = edtConfirmPass;
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
            jsonInput.addProperty("password_new", edtNewPass.getText().toString());
            jsonInput.addProperty("password_new_conf", edtConfirmPass.getText().toString());
        } catch (Exception e) {
            e.printStackTrace();
        }
        return jsonInput;
    }

    @Override
    public void successChangePassword(JsonObject jsonRequest) {
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
        btnSavePassword.setText((isDisabled) ? "Simpan" : strLoading);
        btnSavePassword.setEnabled(isDisabled);

        edtNewPass.setEnabled(false);
        edtConfirmPass.setEnabled(false);
    }
}
