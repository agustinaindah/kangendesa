package com.kangendesa.app.features.create_your_trip.edit;

import android.content.Intent;
import android.graphics.PorterDuff;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

import com.google.gson.JsonObject;
import com.kangendesa.app.R;
import com.kangendesa.app.base.BaseActivity;
import com.kangendesa.app.features.create_your_trip.CreateConditionsMyTourActivity;
import com.kangendesa.app.utils.Consts;
import com.kangendesa.app.utils.Helper;
import com.kangendesa.app.utils.SharedPref;

import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.util.Locale;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * Created by agustinaindah on 25 Februari 2019
 */
public class EditPriceMyTourActivity extends BaseActivity {

    @BindView(R.id.rgQuestion)
    RadioGroup rgQuestion;
    @BindView(R.id.rbAll)
    RadioButton rbAll;
    @BindView(R.id.rbExcludingMeals)
    RadioButton rbExcludingMeals;
    @BindView(R.id.rbNotAll)
    RadioButton rbNotAll;
    @BindView(R.id.edtAddPrice)
    EditText edtAddPrice;
    @BindView(R.id.edtJmlTravelers)
    EditText edtJmlTravelers;
    @BindView(R.id.edtPriceTrip)
    EditText edtPriceTrip;

    @BindView(R.id.btnNext)
    Button btnNext;

    private String price;

    @Override
    protected void onActivityCreated(Bundle savedInstanceState) {
        final Drawable upArrow = getResources().getDrawable(R.drawable.ic_arrow_left);
        upArrow.setColorFilter(getResources().getColor(R.color.colorPrimaryDark), PorterDuff.Mode.SRC_ATOP);

        getSupportActionBar().setHomeAsUpIndicator(upArrow);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle("Price");

        edtAddPrice.requestFocus();
        edtPriceTrip.addTextChangedListener(onTextChangedListener());

        getData();
    }

    @Override
    protected int setView() {
        return R.layout.activity_create_price_my_tour;
    }

    private JsonObject getInput(){
        JsonObject jsonInput = new JsonObject();
        try{
            if(rbAll.isChecked()) {
                String price1 = Consts.PRICE1;
                jsonInput.addProperty("price_choose", price1);
            } else if (rbExcludingMeals.isChecked()) {
                String price2 = Consts.PRICE2;
                jsonInput.addProperty("price_choose", price2);
            }else if (rbNotAll.isChecked()){
                String price3 = Consts.PRICE3;
                jsonInput.addProperty("price_choose", price3);
            }
            jsonInput.addProperty("price_plus", edtAddPrice.getText().toString());
            jsonInput.addProperty("max_travel", edtJmlTravelers.getText().toString());
            jsonInput.addProperty("price", edtPriceTrip.getText().toString().replaceAll(",", ""));

        }catch (Exception e){
            e.printStackTrace();
        }
        return jsonInput;
    }

    private boolean validate() {
        boolean cancel = false;
        View focus = null;

        edtAddPrice.setError(null);
        edtJmlTravelers.setError(null);
        edtPriceTrip.setError(null);

        if (TextUtils.isEmpty(edtAddPrice.getText().toString())) {
            edtAddPrice.setError("Harus diisi");
            focus = edtAddPrice;
            cancel = true;
        }

        if (TextUtils.isEmpty(edtJmlTravelers.getText().toString())) {
            edtJmlTravelers.setError("Harus diisi");
            focus = edtJmlTravelers;
            cancel = true;
        }

        if (TextUtils.isEmpty(edtPriceTrip.getText().toString())) {
            edtPriceTrip.setError("Harus diisi");
            focus = edtPriceTrip;
            cancel = true;
        }

        if (cancel) {
            focus.requestFocus();
        }

        return cancel;
    }

    @OnClick(R.id.btnNext)
    public void conditionsNext() {
        /*if (!validate()) {
            Intent intent = new Intent(this, CreateConditionsMyTourActivity.class);
            startActivity(intent);
        }*/
        Intent intent = new Intent(this, EditConditionsMyTourActivity.class);
        startActivity(intent);
        SharedPref.saveString(Consts.PRICE, getInput().toString());
        Helper.log(getInput().toString());
    }

    private TextWatcher onTextChangedListener() {
        return new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                edtPriceTrip.removeTextChangedListener(this);

                try {
                    String originalString = s.toString();

                    Long longval;
                    if (originalString.contains(",")) {
                        originalString = originalString.replaceAll(",", "");
                    }
                    longval = Long.parseLong(originalString);

                    DecimalFormat formatter = (DecimalFormat) NumberFormat.getInstance(Locale.US);
                    formatter.applyPattern("#,###,###,###");
                    String formattedString = formatter.format(longval);

                    //setting text after format to EditText
                    edtPriceTrip.setText(formattedString);
                    edtPriceTrip.setSelection(edtPriceTrip.getText().length());

                } catch (NumberFormatException nfe) {
                    nfe.printStackTrace();
                }

                edtPriceTrip.addTextChangedListener(this);

            }
        };
    }

    private void getData() {

        JsonObject jsonInput = Helper
                .getGsonInstance()
                .fromJson(Helper.parseToJsonArray(SharedPref.getString(Consts.BASIC))
                        .get(0).getAsJsonObject(), JsonObject.class);

//        edtAddPrice.setText(jsonInput.get("").getAsString());
        edtJmlTravelers.setText(jsonInput.get("m_max_travel").getAsString());
        edtPriceTrip.setText(jsonInput.get("m__regular_price").getAsString());

        price = jsonInput.get("m_price_choose").getAsJsonObject().get("key").getAsString();

        Helper.log(price);

        if (price.equalsIgnoreCase(Consts.PRICE1)) {
            rbAll.setChecked(true);
        } else if (price.equalsIgnoreCase(Consts.PRICE2)){
            rbExcludingMeals.setChecked(true);
        } else if (price.equalsIgnoreCase(Consts.PRICE3)) {
            rbNotAll.setChecked(true);
        }
    }
}
