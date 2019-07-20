package com.kangendesa.app.features.create_your_trip.edit;

import android.content.Intent;
import android.graphics.PorterDuff;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Spinner;

import com.google.gson.JsonObject;
import com.kangendesa.app.R;
import com.kangendesa.app.base.BaseActivity;
import com.kangendesa.app.features.create_your_trip.CreateDetailMyTourActivity;
import com.kangendesa.app.features.create_your_trip.GetComponentTourPresenter;
import com.kangendesa.app.features.create_your_trip.GetComponentTourPresenterImpl;
import com.kangendesa.app.model.ItemDay;
import com.kangendesa.app.model.ItemExtraCon;
import com.kangendesa.app.model.ItemTransportation;
import com.kangendesa.app.utils.Consts;
import com.kangendesa.app.utils.Helper;
import com.kangendesa.app.utils.SharedPref;

import java.util.Arrays;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * Created by agustinaindah on 25 Februari 2019
 */
public class EditOverviewMyTourActivity extends BaseActivity implements GetComponentTourPresenter.View {

    // TODO: 25/02/2019 tunggu
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
    //@BindView(R.id.spinKendaraan)
    //Spinner spinKendaraan;
    @BindView(R.id.edtKendaraan)
    EditText edtKendaraan;
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

        displayData();
        getData();
    }

    @Override
    protected int setView() {
        return R.layout.activity_create_overview_my_tour;
    }

    private void displayData() {

        /*spinKendaraan.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
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
        JsonObject jsonInput = new JsonObject();
        try {
            jsonInput.addProperty("basic_destinasi", edtDestinasi.getText().toString());
            jsonInput.addProperty("basic_aktivitas", edtMainAct.getText().toString());
            jsonInput.addProperty("basic_berhenti", edtStopAt.getText().toString());
            jsonInput.addProperty("transportId", getArrayTransport());
            if (rbTransportPublic.isChecked()) {
                String kendaraanUmum = Consts.KENDARAANUMUM;
                jsonInput.addProperty("umum", kendaraanUmum);
            } else if (rbTransportPrivate.isChecked()) {
                String kendaraanPribadi = Consts.KENDARAANPRIBADI;
                jsonInput.addProperty("private", kendaraanPribadi);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return jsonInput;
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

    private String getArrayTransport(){
        String artList = "";
        if (!edtKendaraan.getText().toString().equalsIgnoreCase("")){
            artList = artList + String.valueOf(mTransportationItem.get(mTrasnportPosition).getKey());
        }
        return artList;
    }

    @OnClick(R.id.btnNext)
    public void detailNext() {
        /*if (!validate()){
            Intent intent = new Intent(this, CreateDetailMyTourActivity.class);
            startActivity(intent);
        }*/
        Intent intent = new Intent(this, EditDetailMyTourActivity.class);
        startActivity(intent);

        Helper.log(getInput().toString());
    }

    @Override
    public void showComponentTour(List<ItemTransportation> itemTransportationList,
                                  List<ItemExtraCon> itemExtraConList,
                                  List<ItemDay> itemDayList) {
        mTransportationItem = itemTransportationList;
        mTransportaion = getTransportToArray(mTransportationItem);
        mTrasnportPosition = Arrays.asList(mTransportationItem).indexOf(edtKendaraan.getText().toString());
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
    }

    @Override
    public void notConnect(String msg) {
        Helper.createAlert(this, Consts.STR_INFO, msg);
    }

    private void setLoading(boolean isDisabled) {
        btnNext.setText((isDisabled) ? "Next" : "Loading...");
        btnNext.setEnabled(isDisabled);
    }

    private void getData() {
        JsonObject jsonInput = Helper
                .getGsonInstance()
                .fromJson(Helper.parseToJsonArray(SharedPref.getString(Consts.BASIC))
                        .get(0).getAsJsonObject(), JsonObject.class);

        edtDestinasi.setText(jsonInput.get("m_basic_destinasi").getAsString());
        edtMainAct.setText(jsonInput.get("m_basic_aktivitas").getAsString());
        edtStopAt.setText(jsonInput.get("m_basic_berhenti").getAsString());

        transport = jsonInput.get("m_basic_trans").getAsJsonObject().get("key").getAsString();
        rgTransport.check(transport.equals(Consts.KENDARAANUMUM)
                ? R.id.rbTransportPublic : R.id.rbTransportPrivate);

        // TODO: 11/03/2019 kurang dibagian radion button
        transportId = jsonInput.get("m_basic_transport_img").getAsJsonObject().get("value").getAsString();
        Log.d("transportImage", transportId);
        mTrasnportPosition = Arrays.asList(mTransportationItem).indexOf(transportId);
        edtKendaraan.setText(transportId);
        mPresenter.getComponentTour();

    }

    private CharSequence[] getTransportToArray(List<ItemTransportation> transport) {
        CharSequence[] transportArray = new CharSequence[transport.size()];
        for (int i = 0; i < transportArray.length; i++) {
            transportArray[i] = transport.get(i).getValue();
        }
        return transportArray;
    }
}
