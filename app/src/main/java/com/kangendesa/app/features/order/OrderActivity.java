package com.kangendesa.app.features.order;

import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.PorterDuff;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.RecyclerView;
import android.text.Html;
import android.text.TextUtils;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.JsonObject;
import com.kangendesa.app.R;
import com.kangendesa.app.base.BaseActivity;
import com.kangendesa.app.model.Countries;
import com.kangendesa.app.model.ItemTour;
import com.kangendesa.app.model.PaymentMethod;
import com.kangendesa.app.model.State;
import com.kangendesa.app.utils.Consts;
import com.kangendesa.app.utils.Helper;
import com.kangendesa.app.utils.SharedPref;

import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * Created by agustinaindah on 28 Januari 2019
 */
public class OrderActivity extends BaseActivity implements CountryStatePresenter.View {

    @BindView(R.id.edtFirstName)
    EditText edtFirstName;
    @BindView(R.id.edtLastName)
    EditText edtLastName;
    @BindView(R.id.edtEmail)
    EditText edtEmail;
    @BindView(R.id.edtPhone)
    EditText edtPhone;
    @BindView(R.id.edtAddress)
    EditText edtAddress;
    @BindView(R.id.edtCity)
    EditText edtCity;
    @BindView(R.id.spinState)
    Spinner spinState;
    @BindView(R.id.spinCountry)
    Spinner spinCountry;
    @BindView(R.id.edtPostcode)
    EditText edtPostcode;

    /*item cart*/
    @BindView(R.id.imgCartTour)
    ImageView imgCartTour;
    @BindView(R.id.txtTitleCartTour)
    TextView txtTitleCartTour;
    @BindView(R.id.txtPriceCartTour)
    TextView txtPriceCartTour;
    @BindView(R.id.txtGuideNameCartTour)
    TextView txtGuideNameCartTour;

    @BindView(R.id.btnOrder)
    Button btnOrder;

    private CountryStatePresenter mPresenter;

    private ArrayAdapter<Countries> countriesArrayAdapter;
    private ArrayAdapter<State> stateArrayAdapter;

    private Countries selectedCountry;
    private State selectedState;

    private String id;
    private String countryId;
    private String stateId;

    private String mImage;
    private String mTitle;
    private Integer mPrice;
    private Integer mJmlWisatawan;
    private String mBookedDate;

    @Override
    protected void onActivityCreated(Bundle savedInstanceState) {
        final Drawable upArrow = getResources().getDrawable(R.drawable.ic_arrow_left);
        upArrow.setColorFilter(getResources().getColor(R.color.colorPrimaryDark), PorterDuff.Mode.SRC_ATOP);

        getSupportActionBar().setHomeAsUpIndicator(upArrow);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle("Order");

        id = getIntent().getStringExtra(Consts.ARG_ID);
        mImage = getIntent().getStringExtra("Image");
        mTitle = getIntent().getStringExtra("Title");
        mPrice = getIntent().getIntExtra("Price", 0);
        mJmlWisatawan = getIntent().getIntExtra("Jml_wisatawan", 0);
        mBookedDate = getIntent().getStringExtra("date");

        SharedPref.saveString("ImageTour", mImage);
        SharedPref.saveString("TitleTour", mTitle);
        SharedPref.saveInt("PriceTour", mPrice);

        mPresenter = new CountryStatePresenterImpl(this);
        mPresenter.getCountries();

        displayData();
        displayItemCart();
    }

    @Override
    protected int setView() {
        return R.layout.activity_order_tour;
    }

    private void displayData() {
        edtFirstName.setText(SharedPref.getString(Consts.FIRSTNAME));
        edtLastName.setText(SharedPref.getString(Consts.LASTNAME));
        edtEmail.setText(SharedPref.getString(Consts.EMAIL));

        spinCountry.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int position, long id) {
                selectedCountry = countriesArrayAdapter.getItem(position);
                mPresenter.getByCountry(selectedCountry.getCountryCode());
                countryId = selectedCountry.getCountryCode();
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

        spinState.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int position, long id) {
                selectedState = stateArrayAdapter.getItem(position);
                stateId = selectedState.getStateCode();
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

    }

    private void displayItemCart() {
        if (!mImage.isEmpty()) {
            Helper.displayImage(this, mImage, imgCartTour);
        }

        txtTitleCartTour.setText(Html.fromHtml(mTitle));
        txtPriceCartTour.setText(Helper.numberCurrency(mPrice));
    }

    private boolean validate() {
        boolean cancel = false;
        View focus = null;

        edtPhone.setError(null);
        edtCity.setError(null);
        edtPostcode.setError(null);
        edtAddress.setError(null);

        if (TextUtils.isEmpty(edtPhone.getText().toString())) {
            edtPhone.setError("Harus diisi");
            focus = edtPhone;
            cancel = true;
        }

        if (TextUtils.isEmpty(edtCity.getText().toString())) {
            edtCity.setError("Harus diisi");
            focus = edtCity;
            cancel = true;
        }

        if (TextUtils.isEmpty(edtAddress.getText().toString())) {
            edtAddress.setError("Harus diisi");
            focus = edtAddress;
            cancel = true;
        }


        if (TextUtils.isEmpty(edtPostcode.getText().toString())) {
            edtPostcode.setError("Harus diisi");
            focus = edtPostcode;
            cancel = true;
        }

        if (cancel) {
            focus.requestFocus();
        }

        return cancel;
    }


    @OnClick(R.id.btnOrder)
    public void order() {
        if (!validate()) {
            Intent intent = new Intent(this, PaymentMethodActivity.class);
            intent.putExtra("wisataId", id);
            intent.putExtra("phone",edtPhone.getText().toString());
            intent.putExtra("city",edtCity.getText().toString());
            intent.putExtra("postCode",edtPostcode.getText().toString());
            intent.putExtra("address", edtAddress.getText().toString());
            intent.putExtra("idCountry",countryId);
            intent.putExtra("idState",stateId);
            intent.putExtra("Price", mPrice);
            intent.putExtra("Jml_wisatawan", mJmlWisatawan);
            intent.putExtra("bookingdate",mBookedDate);
            startActivity(intent);
        }
    }

    @Override
    public void showCountries(List<Countries> countriesList) {
        countriesArrayAdapter = new ArrayAdapter<Countries>(this,
                android.R.layout.simple_list_item_1,
                countriesList.toArray(new Countries[countriesList.size()]));
        spinCountry.setAdapter(countriesArrayAdapter);
        int idx = 0;
        for (int r = 0; r < countriesArrayAdapter.getCount(); r++) {
            if (countriesArrayAdapter.getItem(r).getCountryCode().equals("ID")) {
                idx = r;
                break;
            }
        }
        spinCountry.setSelection(idx);
    }

    @Override
    public void showStates(List<State> stateList) {
        stateArrayAdapter = new ArrayAdapter<State>(this,
                android.R.layout.simple_list_item_1,
                stateList.toArray(new State[stateList.size()]));
        spinState.setAdapter(stateArrayAdapter);
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
        btnOrder.setText((isDisabled) ? "Order" : "Loading...");
        btnOrder.setEnabled(isDisabled);
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
