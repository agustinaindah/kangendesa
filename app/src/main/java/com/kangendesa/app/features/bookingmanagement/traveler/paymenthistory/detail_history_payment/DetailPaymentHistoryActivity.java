package com.kangendesa.app.features.bookingmanagement.traveler.paymenthistory.detail_history_payment;

import android.app.ProgressDialog;
import android.graphics.PorterDuff;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

import com.kangendesa.app.R;
import com.kangendesa.app.base.BaseActivity;
import com.kangendesa.app.model.ItemDetailPayment;
import com.kangendesa.app.utils.Consts;
import com.kangendesa.app.utils.Helper;

import java.util.HashMap;
import java.util.Map;

import butterknife.BindView;

/**
 * Created by agustinaindah on 20 Februari 2019
 */
public class DetailPaymentHistoryActivity extends BaseActivity implements DetailPaymentHistoryPresenter.View{

    @BindView(R.id.txtJudul)
    TextView txtJudul;
    @BindView(R.id.txtOrderNo)
    TextView txtOrderNo;
    @BindView(R.id.txtEmail)
    TextView txtEmail;
    @BindView(R.id.txtDestBank)
    TextView txtDestBank;
    @BindView(R.id.txtOriginBank)
    TextView txtOriginBank;
    @BindView(R.id.txtRekName)
    TextView txtRekName;
    @BindView(R.id.txtTransferMethod)
    TextView txtTransferMethod;
    @BindView(R.id.txtNominal)
    TextView txtNominal;
    @BindView(R.id.txtDateTransfer)
    TextView txtDateTransfer;
    @BindView(R.id.txtStatus)
    TextView txtStatus;
    @BindView(R.id.imgScreenshoot)
    ImageView imgScreenshoot;

    private DetailPaymentHistoryPresenter mPresenter;

    private String id;
    private ItemDetailPayment mItemDetailPayment;
    private ProgressDialog progressDialog;

    @Override
    protected void onActivityCreated(Bundle savedInstanceState) {
        final Drawable upArrow = getResources().getDrawable(R.drawable.ic_arrow_left);
        upArrow.setColorFilter(getResources().getColor(R.color.colorPrimaryDark), PorterDuff.Mode.SRC_ATOP);

        getSupportActionBar().setHomeAsUpIndicator(upArrow);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle("History Pembayaran Detail");

        initLoading();

        id = getIntent().getStringExtra(Consts.ARG_ID);

        mPresenter = new DetailPaymentHistoryPresenterImpl(this);
        mPresenter.getSinglePembayaran(getQueryRequest());
    }

    @Override
    protected int setView() {
        return R.layout.activity_detail_history_payment;
    }

    private void initLoading() {
        progressDialog = new ProgressDialog(this);
        progressDialog.setMessage(Consts.STR_LOADING);
    }

    private Map<String, String> getQueryRequest() {
        HashMap<String, String> requestMap = new HashMap<>();
        requestMap.put("konfirmasi_id", id);
        return requestMap;
    }

    @Override
    public void success(ItemDetailPayment itemDetailPayment) {
        mItemDetailPayment = itemDetailPayment;
        showData(itemDetailPayment);
    }

    private void showData(ItemDetailPayment itemDetailPayment) {
        txtJudul.setText("History Pembayaran Detail No Order " + itemDetailPayment.getKonfirmasiOrderNo());
        txtOrderNo.setText(itemDetailPayment.getKonfirmasiOrderNo());
        txtEmail.setText(itemDetailPayment.getKonfirmasiEmail());
        txtDestBank.setText(Helper.capitalize(itemDetailPayment.getKonfirmasiBankTujuan()));
        txtOriginBank.setText(Helper.capitalize(itemDetailPayment.getKonfirmasiBankPengirim()));
        txtRekName.setText(Helper.capitalize(itemDetailPayment.getKonfirmasiRekeningName()));
        txtTransferMethod.setText(Helper.capitalize(itemDetailPayment.getKonfirmasiTransferMethod()));
        txtNominal.setText(Helper.numberCurrency(Integer.valueOf(itemDetailPayment.getKonfirmasiNominal())));
        txtDateTransfer.setText(Helper.parseToDateString(itemDetailPayment.getKonfirmasiTanggal(), Consts.TYPE_DATE));
        txtStatus.setText(Helper.capitalize(itemDetailPayment.getKonfirmasiStatus()));
        if (!itemDetailPayment.getKonfirmasiImages().isEmpty()){
            Helper.displayImage(this, itemDetailPayment.getKonfirmasiImages(), imgScreenshoot, true);
        }
    }

    @Override
    public void showProgress() {
        progressDialog.show();
    }

    @Override
    public void hideProgress() {
        progressDialog.dismiss();
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
