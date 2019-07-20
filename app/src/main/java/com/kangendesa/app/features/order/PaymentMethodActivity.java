package com.kangendesa.app.features.order;

import android.content.Intent;
import android.graphics.PorterDuff;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.TextView;

import com.google.gson.JsonObject;
import com.kangendesa.app.R;
import com.kangendesa.app.base.BaseActivity;
import com.kangendesa.app.features.order.order_result.OrderResultActivity;
import com.kangendesa.app.model.PaymentMethod;
import com.kangendesa.app.utils.Consts;
import com.kangendesa.app.utils.Helper;
import com.kangendesa.app.utils.SharedPref;

import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * Created by agustinaindah on 30 Januari 2019
 */
public class PaymentMethodActivity extends BaseActivity implements OrderPresenterImpl.View {

    @BindView(R.id.txtTotalPembayaran)
    TextView txtTotalPembayaran;
    @BindView(R.id.spinPaymentMethod)
    Spinner spinPaymentMethod;

    @BindView(R.id.btnPembayaran)
    Button btnPembayaran;

    private String id;
    private String email;
    private String phone;
    private String address;
    private String city;
    private String postcode;
    private String countryID;
    private String stateID;
    private Integer mJmlWisatawan;
    private String bookingDate;

    private OrderPresenterImpl mPresenter;

    private SysPaymentMethodAdapter sysPaymentMethodAdapter;

    @Override
    protected void onActivityCreated(Bundle savedInstanceState) {
        final Drawable upArrow = getResources().getDrawable(R.drawable.ic_arrow_left);
        upArrow.setColorFilter(getResources().getColor(R.color.colorPrimaryDark), PorterDuff.Mode.SRC_ATOP);

        getSupportActionBar().setHomeAsUpIndicator(upArrow);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle("Pembayaran");

        id = getIntent().getStringExtra("wisataId");
        email = getIntent().getStringExtra("email");
        phone = getIntent().getStringExtra("phone");
        city = getIntent().getStringExtra("city");
        address = getIntent().getStringExtra("address");
        postcode = getIntent().getStringExtra("postCode");
        countryID = getIntent().getStringExtra("idCountry");
        stateID = getIntent().getStringExtra("idState");
        mJmlWisatawan = getIntent().getIntExtra("Jml_wisatawan",0);
        bookingDate = getIntent().getStringExtra("bookingdate");

        mPresenter = new OrderPresenterImpl(this);
        mPresenter.getPaymentMethod();

        displayData();
    }

    @Override
    protected int setView() {
        return R.layout.activity_payment_method;
    }

    private void displayData() {
        Integer priceTour = SharedPref.getInt("PriceTour");
        txtTotalPembayaran.setText(Helper.numberCurrency(priceTour));

        spinPaymentMethod.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int position, long id) {
                spinPaymentMethod.setTag(sysPaymentMethodAdapter.getItem(position));
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

    }

    private JsonObject getInput() {
        PaymentMethod paymentMethod = (PaymentMethod) spinPaymentMethod.getTag();
        JsonObject jsonInput = new JsonObject();
        try {
            jsonInput.addProperty("product_id", id);
            jsonInput.addProperty("first_name", SharedPref.getString(Consts.FIRSTNAME));
            jsonInput.addProperty("last_name", SharedPref.getString(Consts.LASTNAME));
            jsonInput.addProperty("email", SharedPref.getString(Consts.EMAIL));
            jsonInput.addProperty("phone", phone);
            jsonInput.addProperty("country", countryID);
            jsonInput.addProperty("state", stateID);
            jsonInput.addProperty("address_1", address);
            jsonInput.addProperty("city", city);
            jsonInput.addProperty("postcode", postcode);
            jsonInput.addProperty("payment_method", paymentMethod.getPaymentMethodId());
            jsonInput.addProperty("payment_method_title", paymentMethod.getPaymentMethodTitle());
            jsonInput.addProperty("jml_orang", mJmlWisatawan);
            jsonInput.addProperty("booking_date",bookingDate);

            SharedPref.saveString("paymentMethodTitle", paymentMethod.getPaymentMethodTitle());

        } catch (Exception e) {
            e.printStackTrace();
        }
        return jsonInput;
    }

    @Override
    public void showPaymentMethod(List<PaymentMethod> paymentMethodList) {
        sysPaymentMethodAdapter = new SysPaymentMethodAdapter(this);
        sysPaymentMethodAdapter.updateSysPaymentMethodItem(paymentMethodList);
        spinPaymentMethod.setAdapter(sysPaymentMethodAdapter);
    }

    @OnClick(R.id.btnPembayaran)
    public void pembayaran() {
        mPresenter.postOrder(getInput());
    }

    @Override
    public void successOrder(JsonObject jsonRes) {
        String orderId = jsonRes.get("order_id").getAsString();
//        Toast.makeText(this, "Berhasil Order " + orderId, Toast.LENGTH_SHORT).show();

        Intent intent = new Intent(this, OrderResultActivity.class);
        intent.putExtra("order_number", orderId);
        intent.putExtra("Jml_wisatawan", mJmlWisatawan);
        startActivity(intent);
    }

    @Override
    public void showProgress() {
        setLoading(false);
    }

    @Override
    public void hideProgress() {
        setLoading(true);
    }

    private void setLoading(boolean isDisabled) {
        btnPembayaran.setText((isDisabled) ? "Pembayaran" : "Loading...");
        btnPembayaran.setEnabled(isDisabled);
    }

    @Override
    public void showMessage(String msg) {
        Helper.createAlert(this, Consts.STR_INFO, msg);
    }

    @Override
    public void notConnect(String msg) {
        Helper.createAlert(this, Consts.STR_INFO, msg);
    }

}
