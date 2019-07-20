package com.kangendesa.app.features.bookingmanagement;

import android.content.Intent;
import android.graphics.PorterDuff;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.widget.RelativeLayout;

import com.kangendesa.app.R;
import com.kangendesa.app.base.BaseActivity;
import com.kangendesa.app.features.bookingmanagement.local_guide.tripbooked.TripBookedActivity;
import com.kangendesa.app.features.bookingmanagement.local_guide.tripbooked.TripBookedAdapter;
import com.kangendesa.app.features.bookingmanagement.traveler.mybooking.MyBookingActivity;
import com.kangendesa.app.features.bookingmanagement.traveler.paymenthistory.PaymentHistoryActivity;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * Created by agustinaindah on 18 Februari 2019
 */
public class BookingManagementActivity extends BaseActivity {

    @BindView(R.id.layListMyBooking)
    RelativeLayout layListMyBooking;
    @BindView(R.id.layPaymentHistory)
    RelativeLayout layPaymentHistory;
    @BindView(R.id.layTripBooked)
    RelativeLayout layTripBooked;
    @BindView(R.id.layIncomeHistory)
    RelativeLayout layIncomeHistory;

    @Override
    protected void onActivityCreated(Bundle savedInstanceState) {
        final Drawable upArrow = getResources().getDrawable(R.drawable.ic_arrow_left);
        upArrow.setColorFilter(getResources().getColor(R.color.colorPrimaryDark), PorterDuff.Mode.SRC_ATOP);

        getSupportActionBar().setHomeAsUpIndicator(upArrow);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle("Booking Management");
    }

    @Override
    protected int setView() {
        return R.layout.activity_booking_management;
    }

    @OnClick(R.id.layListMyBooking)
    public void listMyBooking(){
        Intent intent = new Intent(this, MyBookingActivity.class);
        startActivity(intent);
    }

    @OnClick(R.id.layPaymentHistory)
    public void paymentHistory(){
        Intent intent = new Intent(this, PaymentHistoryActivity.class);
        startActivity(intent);
    }

    @OnClick(R.id.layTripBooked)
    public void tripBooked(){
        Intent intent = new Intent(this, TripBookedActivity.class);
        startActivity(intent);
    }

    @OnClick(R.id.layIncomeHistory)
    public void incomeHistory(){

    }
}
