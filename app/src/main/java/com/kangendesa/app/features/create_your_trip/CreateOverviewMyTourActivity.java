package com.kangendesa.app.features.create_your_trip;

import android.content.Context;
import android.content.Intent;
import android.graphics.PorterDuff;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Spinner;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.kangendesa.app.R;
import com.kangendesa.app.base.BaseActivity;
import com.kangendesa.app.model.ItemDay;
import com.kangendesa.app.model.ItemExtraCon;
import com.kangendesa.app.model.ItemTransportation;
import com.kangendesa.app.utils.Consts;
import com.kangendesa.app.utils.Helper;
import com.kangendesa.app.utils.SharedPref;

import org.json.JSONObject;

import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * Created by agustinaindah on 13 Februari 2019
 */
public class CreateOverviewMyTourActivity extends BaseActivity implements GetComponentTourPresenter.View {

    @BindView(R.id.edtDestinasi)
    EditText edtDestinasi;
    @BindView(R.id.edtStopAt)
    EditText edtStopAt;
    @BindView(R.id.edtMainAct)
    EditText edtMainAct;
    @BindView(R.id.rgTransport)
    RadioGroup rgTransport;
    @BindView(R.id.rbTransportPublic)
    RadioButton rbTransportPublic;
    @BindView(R.id.rbTransportPrivate)
    RadioButton rbTransportPrivate;
    /*@BindView(R.id.spinKendaraan)
    Spinner spinKendaraan;*/
    @BindView(R.id.edtKendaraan)
    EditText edtKendaraan;
    @BindView(R.id.edtOther)
    EditText edtOther;

    @BindView(R.id.btnNext)
    Button btnNext;

    private ArrayAdapter<ItemTransportation> itemTransportArrayAdapter;

    private ItemTransportation selectedTransport;
    private String transportId;

    private String transport;

    private List<ItemTransportation> mTransportationItem;
    private CharSequence[] mTransportaion;
    private int mTrasnportPosition;

    private GetComponentTourPresenter mPresenter;

    @Override
    protected void onActivityCreated(Bundle savedInstanceState) {
        final Drawable upArrow = getResources().getDrawable(R.drawable.ic_arrow_left);
        upArrow.setColorFilter(getResources().getColor(R.color.colorPrimaryDark), PorterDuff.Mode.SRC_ATOP);

        getSupportActionBar().setHomeAsUpIndicator(upArrow);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle("Overview");

        mPresenter = new GetComponentTourPresenterImpl(this);
        mPresenter.getComponentTour();

        mTransportationItem = null;

//        savetToPref();
        displayData();

    }

    @Override
    protected int setView() {
        return R.layout.activity_create_overview_my_tour;
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

    private void displayData() {

//        rgTransport.check((transport.equals(Consts.KENDARAANUMUM) ? R.id.rbTransportPublic : R.id.rbTransportPrivate));

       /* spinKendaraan.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int position, long id) {
                selectedTransport = itemTransportArrayAdapter.getItem(position);
                transportId = selectedTransport.getKey();
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });*/
    }

    private JsonObject getInput() {

        JsonObject jsonInput = Helper
                .getGsonInstance()
                .fromJson(SharedPref.getString(Consts.BASIC), JsonObject.class);

        try {

            jsonInput.addProperty("basic_destinasi", edtDestinasi.getText().toString());
            jsonInput.addProperty("basic_aktivitas", edtMainAct.getText().toString());
            jsonInput.addProperty("basic_berhenti", edtStopAt.getText().toString());
            if (rbTransportPublic.isChecked()) {
                String kendaraanUmum = Consts.KENDARAANUMUM;
                jsonInput.addProperty("basic_trans", kendaraanUmum);
            } else if (rbTransportPrivate.isChecked()) {
                String kendaraanPribadi = Consts.KENDARAANPRIBADI;
                jsonInput.addProperty("basic_trans", kendaraanPribadi);
            }
//            jsonInput.addProperty("basic_transport_img", transportId);
            jsonInput.addProperty("basic_transport_img", getArrayTransport());
            jsonInput.addProperty("basic_transport_img_lain", edtOther.getText().toString());

        } catch (Exception e) {
            e.printStackTrace();
        }
        return jsonInput;
    }

    private String getArrayTransport(){
        String artList = "";
        if (!edtKendaraan.getText().toString().equalsIgnoreCase("")){
            artList = artList + String.valueOf(mTransportationItem.get(mTrasnportPosition).getKey());
        }
        return artList;
    }
  /*  private void savetToPref(){
        SharedPref.saveString("destinasi", edtDestinasi.getText().toString());
        SharedPref.saveString("mainAct", edtMainAct.getText().toString());
        SharedPref.saveString("stopAct", edtStopAt.getText().toString());
        SharedPref.saveString("transportId", transportId);
//        SharedPref.saveBoolean("transport", );
    }*/

    private boolean validate() {
        boolean cancel = false;
        View focus = null;

        edtKendaraan.setError(null);
        edtDestinasi.setError(null);
        edtMainAct.setError(null);
        edtStopAt.setError(null);

        if (TextUtils.isEmpty(edtKendaraan.getText().toString())) {
            edtKendaraan.setError("Harus diisi");
            focus = edtKendaraan;
            cancel = true;
        }

        if (TextUtils.isEmpty(edtDestinasi.getText().toString())) {
            edtDestinasi.setError("Harus diisi");
            focus = edtDestinasi;
            cancel = true;
        }

        if (TextUtils.isEmpty(edtMainAct.getText().toString())) {
            edtMainAct.setError("Harus diisi");
            focus = edtMainAct;
            cancel = true;
        }

        if (TextUtils.isEmpty(edtStopAt.getText().toString())) {
            edtStopAt.setError("Harus diisi");
            focus = edtStopAt;
            cancel = true;
        }

        if (cancel) {
            focus.requestFocus();
        }

        return cancel;
    }

    @OnClick(R.id.btnNext)
    public void detailNext() {
        if (!validate()) {
            SharedPref.saveString(Consts.OVERVIEW, getInput().toString());
            Intent intent = new Intent(this, CreateDetailMyTourActivity.class);
            startActivity(intent);
            Helper.log(SharedPref.getString(Consts.OVERVIEW));
        }

        /*Intent intent = new Intent(this, CreateDetailMyTourActivity.class);
        startActivity(intent);

        SharedPref.saveString(Consts.OVERVIEW, getInput().toString());
        Log.d("over", SharedPref.getString(Consts.OVERVIEW));

        Helper.log(SharedPref.getString(Consts.OVERVIEW));*/

    }

    @OnClick(R.id.edtKendaraan)
    public void kendaraanClicked(){
        new AlertDialog.Builder(this, R.style.AppDialogTheme)
                .setTitle("Pilih Kendaraan")
                .setSingleChoiceItems(mTransportaion, mTrasnportPosition, null)
                .setPositiveButton(android.R.string.ok, ((dialogInterface, i) -> {
                    try{
                        mTrasnportPosition = ((AlertDialog)dialogInterface)
                                .getListView().getCheckedItemPosition();
                        edtKendaraan.setText(mTransportaion[mTrasnportPosition]);
                    }catch (Exception e){
                        e.printStackTrace();
                    }
                }))
                .create()
                .show();
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
        btnNext.setText((isDisabled) ? "Next" : "Loading...");
        btnNext.setEnabled(isDisabled);
    }

    @Override
    public void showMessage(String msg) {
        Helper.createAlert(this, Consts.STR_INFO, msg);
    }

    @Override
    public void notConnect(String msg) {
        Helper.createAlert(this, Consts.STR_INFO, msg);
    }

    @Override
    public void showComponentTour(List<ItemTransportation> itemTransportationList,
                                  List<ItemExtraCon> itemExtraConList,
                                  List<ItemDay> itemDayList) {
       /* itemTransportArrayAdapter = new ArrayAdapter<ItemTransportation>(this,
                android.R.layout.simple_list_item_1,
                itemTransportationList.toArray(new ItemTransportation[itemTransportationList.size()]));
        spinKendaraan.setAdapter(itemTransportArrayAdapter);*/
       mTransportationItem = itemTransportationList;
       mTransportaion = getTransportToArray(itemTransportationList);
    }

    private CharSequence[] getTransportToArray(List<ItemTransportation> transport) {
        CharSequence[] transportArray = new CharSequence[transport.size()];
        for (int i = 0; i < transportArray.length; i++) {
            transportArray[i] = transport.get(i).getValue();
        }
        return transportArray;
    }
}
