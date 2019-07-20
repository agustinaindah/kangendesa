package com.kangendesa.app.features.account_setting;

import android.content.Intent;
import android.graphics.PorterDuff;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.widget.RelativeLayout;

import com.kangendesa.app.KangenDesaApp;
import com.kangendesa.app.R;
import com.kangendesa.app.base.BaseActivity;
import com.kangendesa.app.features.MainActivity;
import com.kangendesa.app.features.account_setting.verifyemail.VerifyEmailAcivity;
import com.kangendesa.app.features.confirm_payment.ConfirmPaymentActivity;
import com.kangendesa.app.features.dashboard.DashboardActivity;
import com.kangendesa.app.features.bookingmanagement.BookingManagementActivity;
import com.kangendesa.app.features.tripmanagement.TripManagementActivity;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * Created by agustinaindah on 09 Februari 2019
 */
public class AccountSettingActivity extends BaseActivity {

    @BindView(R.id.layDashboard)
    RelativeLayout layDashboard;
    @BindView(R.id.layVerifyEmail)
    RelativeLayout layVerifyEmail;
    @BindView(R.id.layListBooking)
    RelativeLayout layListBooking;
    @BindView(R.id.layListTrip)
    RelativeLayout layListTrip;
    @BindView(R.id.layConfirmPayment)
    RelativeLayout layConfirmPayment;
    @BindView(R.id.layLogout)
    RelativeLayout layLogout;

    @Override
    protected void onActivityCreated(Bundle savedInstanceState) {
        final Drawable upArrow = getResources().getDrawable(R.drawable.ic_arrow_left);
        upArrow.setColorFilter(getResources().getColor(R.color.colorPrimaryDark), PorterDuff.Mode.SRC_ATOP);

        getSupportActionBar().setHomeAsUpIndicator(upArrow);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle("Account Setting");
    }

    @Override
    protected int setView() {
        return R.layout.activity_account_settings;
    }

    @OnClick(R.id.layDashboard)
    public void dashboard(){
        Intent intent = new Intent(this, DashboardActivity.class);
        startActivity(intent);
    }

    @OnClick(R.id.layVerifyEmail)
    public void verifyEmail(){
        Intent intent = new Intent(this, VerifyEmailAcivity.class);
        startActivity(intent);
    }

    @OnClick(R.id.layListBooking)
    public void listBooking(){
        Intent intent = new Intent(this, BookingManagementActivity.class);
        startActivity(intent);
    }

    @OnClick(R.id.layListTrip)
    public void listTrip(){
        Intent intent = new Intent(this, TripManagementActivity.class);
        startActivity(intent);
    }

    @OnClick(R.id.layConfirmPayment)
    public void confirmPayment(){
        Intent intent = new Intent(this, ConfirmPaymentActivity.class);
        startActivity(intent);
    }

    @OnClick(R.id.layLogout)
    public void logout(){
        KangenDesaApp.getInstance().logout();
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
    }
}
