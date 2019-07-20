package com.kangendesa.app.features.create_your_trip;

import android.content.Intent;
import android.graphics.PorterDuff;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

import com.google.gson.JsonObject;
import com.kangendesa.app.R;
import com.kangendesa.app.base.BaseActivity;
import com.kangendesa.app.utils.Consts;
import com.kangendesa.app.utils.Helper;
import com.kangendesa.app.utils.SharedPref;

import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.util.Locale;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * Created by agustinaindah on 13 Februari 2019
 */
public class CreatePriceMyTourActivity extends BaseActivity {

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

    @Override
    protected void onActivityCreated(Bundle savedInstanceState) {
        final Drawable upArrow = getResources().getDrawable(R.drawable.ic_arrow_left);
        upArrow.setColorFilter(getResources().getColor(R.color.colorPrimaryDark), PorterDuff.Mode.SRC_ATOP);

        getSupportActionBar().setHomeAsUpIndicator(upArrow);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle("Price");

        edtPriceTrip.addTextChangedListener(onTextChangedListener());

//        saveToPref();
    }

     /*{
        "overview_trip" : "", //title trip
        "overview_summary" : "", //desc trip
        "overview_photos_featured_img" : "",
        "overview_photos_img" : ["img.jpg", "img.jpg", "img.jpg"],
        "basic_destinasi" : "",
        "basic_berhenti" : "",
        "basic_aktivitas" : "",
        "basic_trans" : "",
        "basic_transport_img" : "",
        "basic_transport_img_lain" : "",
        "detail_meeting" : "",
        "map_google_geometry_lat" : "",
        "map_google_geometry_lng" : "",
        "detail_meeting_desc" : "",
        "detail_schedule" : [
        {"first_time" : "00:00", "last_time" : "00:00", "schedule" : ""},
        {"first_time" : "00:00", "last_time" : "00:00", "schedule" : ""},
        {"first_time" : "00:00", "last_time" : "00:00", "schedule" : ""},
        ],

        "detail_faq" : [
        {"title" : "00:00", "desc" : "00:00"}
        {"title" : "00:00", "desc" : "00:00"}
        ],
        "price_choose" : "",
        "price_plus" : "",
        "price" : "",
        "max_travel" : "",
        "conditions_img" : ["con", "con", "con"],
        "conditions_day" : ["day", "day", "day"]
}*/

    /*private void saveToPref() {

        rgQuestion.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                RadioButton rb = (RadioButton) group.findViewById(checkedId);
                if (null != rb) {
                    Toast.makeText(CreatePriceMyTourActivity.this, rb.getText(), Toast.LENGTH_SHORT).show();
                    SharedPref.saveInt("rgQuestion", checkedId);
                }
            }
        });

        SharedPref.saveString("addPrice", edtAddPrice.getText().toString());
        SharedPref.saveString("jmlTraveler", edtJmlTravelers.getText().toString());
        SharedPref.saveString("priceTrip", edtPriceTrip.getText().toString());
    }*/

    private JsonObject getInput() {

        JsonObject jsonInput = Helper
                .getGsonInstance()
                .fromJson(SharedPref.getString(Consts.DETAIL), JsonObject.class);
        try {
            if (rbAll.isChecked()) {
                String price1 = Consts.PRICE1;
                jsonInput.addProperty("price_choose", price1);
            } else if (rbExcludingMeals.isChecked()) {
                String price2 = Consts.PRICE2;
                jsonInput.addProperty("price_choose", price2);
            } else if (rbNotAll.isChecked()) {
                String price3 = Consts.PRICE3;
                jsonInput.addProperty("price_choose", price3);
            }
            jsonInput.addProperty("price_plus", edtAddPrice.getText().toString());
            jsonInput.addProperty("max_travel", edtJmlTravelers.getText().toString());
            jsonInput.addProperty("price", edtPriceTrip.getText().toString().replaceAll(",", ""));

        } catch (Exception e) {
            e.printStackTrace();
        }
        return jsonInput;
    }

    @Override
    protected int setView() {
        return R.layout.activity_create_price_my_tour;
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
        if (!validate()) {
            SharedPref.saveString(Consts.PRICE, getInput().toString());
            Intent intent = new Intent(this, CreateConditionsMyTourActivity.class);
            startActivity(intent);
            Helper.log(getInput().toString());
        }

        /*Intent intent = new Intent(this, CreateConditionsMyTourActivity.class);
        startActivity(intent);

        SharedPref.saveString(Consts.PRICE, getInput().toString());

        Helper.log(getInput().toString());*/
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
}
