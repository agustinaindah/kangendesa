package com.kangendesa.app.features.confirm_payment;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.PorterDuff;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.JsonObject;
import com.kangendesa.app.R;
import com.kangendesa.app.base.BaseActivity;
import com.kangendesa.app.features.DateDialog;
import com.kangendesa.app.features.MainActivity;
import com.kangendesa.app.features.account_setting.AccountSettingActivity;
import com.kangendesa.app.model.ItemBankName;
import com.kangendesa.app.model.ItemTransferMethod;
import com.kangendesa.app.utils.CallbackInterface;
import com.kangendesa.app.utils.Consts;
import com.kangendesa.app.utils.Helper;
import com.kosalgeek.android.photoutil.GalleryPhoto;
import com.kosalgeek.android.photoutil.ImageBase64;
import com.kosalgeek.android.photoutil.ImageLoader;

import java.io.FileNotFoundException;
import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.util.List;
import java.util.Locale;

import butterknife.BindString;
import butterknife.BindView;
import butterknife.OnClick;

/**
 * Created by agustinaindah on 09 Februari 2019
 */

public class ConfirmPaymentActivity extends BaseActivity implements ConfirmPaymentPresenter.View,
        DateDialog.OnDateDialog{

    @BindView(R.id.edtNoOrder)
    EditText edtNoOrder;
    @BindView(R.id.edtEmailOrder)
    EditText edtEmailOrder;
    @BindView(R.id.spinBankTujuan)
    Spinner spinBankTujuan;
    @BindView(R.id.edtBankAnda)
    EditText edtBankAnda;
    @BindView(R.id.edtRekAtasNama)
    EditText edtRekAtasNama;
    @BindView(R.id.spinMetodeTransfer)
    Spinner spinMetodeTransfer;
    @BindView(R.id.edtNominalTransfer)
    EditText edtNominalTransfer;
    @BindView(R.id.txtTglTransfer)
    TextView txtTglTransfer;
    @BindView(R.id.imgScreenshoot)
    ImageView imgScreenshoot;
    @BindView(R.id.btnChooseFile)
    Button btnChooseFile;

    @BindView(R.id.btnConfirm)
    Button btnConfirm;

    @BindString(R.string.msg_empty)
    String strMsgEmpty;

    private ArrayAdapter<ItemBankName> itemBankArrayAdapter;
    private ArrayAdapter<ItemTransferMethod> itemTransferArrayAdapter;

    private ItemBankName selectedBank;
    private ItemTransferMethod selectedTransfer;

    private String bankId;
    private String transferId;

    private String transferDate;

    private String mImgProfileEncoded;
    private GalleryPhoto mGalleryPhoto;

    private ConfirmPaymentPresenter mPresenter;

    @Override
    protected void onActivityCreated(Bundle savedInstanceState) {

        final Drawable upArrow = getResources().getDrawable(R.drawable.ic_arrow_left);
        upArrow.setColorFilter(getResources().getColor(R.color.colorPrimaryDark), PorterDuff.Mode.SRC_ATOP);

        getSupportActionBar().setHomeAsUpIndicator(upArrow);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle("Konfirmasi Pembayaran");

        mPresenter = new ConfirmPaymentPresenterImpl(this);
        mGalleryPhoto = new GalleryPhoto(this);

        mPresenter.getComponent();

        transferDate = Helper.getDateNow();
        edtNoOrder.requestFocus();
        edtNominalTransfer.addTextChangedListener(onTextChangedListener());

        setupBank();
    }

    private void setupBank() {
        spinBankTujuan.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int position, long id) {
                selectedBank = itemBankArrayAdapter.getItem(position);
                bankId = selectedBank.getSlug();
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

        spinMetodeTransfer.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int position, long id) {
                selectedTransfer = itemTransferArrayAdapter.getItem(position);
                transferId = selectedTransfer.getSlug();
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });
    }

    @Override
    protected int setView() {
        return R.layout.activity_confirm_payment;
    }

    private boolean validate(){
        boolean cancel = false;
        View focus = null;

        JsonObject jsonObject = getInput();

        edtNoOrder.setError(null);
        edtEmailOrder.setError(null);
        edtBankAnda.setError(null);
        edtRekAtasNama.setError(null);
        edtNominalTransfer.setError(null);
        btnChooseFile.setError(null);

        if (TextUtils.isEmpty(jsonObject.get("konfirmasi_order_no").getAsString())) {
            edtNoOrder.setError(strMsgEmpty);
            focus = edtNoOrder;
            cancel = true;
        }

        if (TextUtils.isEmpty(jsonObject.get("konfirmasi_bank_pengirim").getAsString())){
            edtBankAnda.setError(strMsgEmpty);
            focus = edtBankAnda;
            cancel = true;
        }

        if (TextUtils.isEmpty(jsonObject.get("konfirmasi_rekening_name").getAsString())){
            edtRekAtasNama.setError(strMsgEmpty);
            focus = edtRekAtasNama;
            cancel = true;
        }

        if (TextUtils.isEmpty(jsonObject.get("konfirmasi_nominal").getAsString())){
            edtNominalTransfer.setError(strMsgEmpty);
            focus = edtNominalTransfer;
            cancel = true;
        }

        if (TextUtils.isEmpty(jsonObject.get("konfirmasi_email").getAsString())) {
            edtEmailOrder.setError(strMsgEmpty);
            focus = edtEmailOrder;
            cancel = true;
        } else if (!Helper.isEmail(jsonObject.get("konfirmasi_email").getAsString())) {
            edtEmailOrder.setError(getString(R.string.msg_not_valid));
            focus = edtEmailOrder;
            cancel = true;
        }

        if (TextUtils.isEmpty(jsonObject.get("konfirmasi_images").getAsString())) {
            btnChooseFile.setError("Harus diisi");
            focus = btnChooseFile;
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
            jsonInput.addProperty("konfirmasi_order_no", edtNoOrder.getText().toString());
            jsonInput.addProperty("konfirmasi_email", edtEmailOrder.getText().toString());
            jsonInput.addProperty("konfirmasi_bank_tujuan", bankId);
            jsonInput.addProperty("konfirmasi_bank_pengirim", edtBankAnda.getText().toString());
            jsonInput.addProperty("konfirmasi_rekening_name", edtRekAtasNama.getText().toString());
            jsonInput.addProperty("konfirmasi_transfer_method", transferId);
            jsonInput.addProperty("konfirmasi_nominal", edtNominalTransfer.getText().toString().replaceAll(",", ""));
            jsonInput.addProperty("konfirmasi_images", getImageEncodedWithPrefix());
            jsonInput.addProperty("konfirmasi_tanggal", transferDate);

        } catch (Exception e) {
            e.printStackTrace();
        }
        return jsonInput;
    }

    private String getImageEncodedWithPrefix() {
        String prefix = "data:image/jpeg;base64,";
        return (mImgProfileEncoded == null) ? "" : prefix + mImgProfileEncoded;
    }

    @OnClick(R.id.txtTglTransfer)
    public void transferDate(){
        DialogFragment dialogFragment = DateDialog.newInstance(transferDate, ConfirmPaymentActivity.this);
        dialogFragment.show(getSupportFragmentManager(), Consts.DIALOG);
    }

    @OnClick(R.id.btnConfirm)
    public void confirm(){
        if (!validate()){
            mPresenter.postConfirmPayment(getInput());
        }
    }

    @OnClick(R.id.btnChooseFile)
    public void chooseFile() {
        startActivityForResult(mGalleryPhoto.openGalleryIntent(), Consts.CODE_REQUEST_GALLERY);
    }

    @Override
    public void onSelectedDate(String selectedDate) {
        transferDate = selectedDate;
        txtTglTransfer.setText(Helper.parseToDateString(transferDate, Consts.TYPE_DATE));
    }

    @Override
    public void showComponent(List<ItemBankName> itemBankList,
                              List<ItemTransferMethod> itemTransferList) {
        itemBankArrayAdapter = new ArrayAdapter<ItemBankName>(this,
                android.R.layout.simple_list_item_1,
                itemBankList.toArray(new ItemBankName[itemBankList.size()]));
        spinBankTujuan.setAdapter(itemBankArrayAdapter);

        itemTransferArrayAdapter = new ArrayAdapter<ItemTransferMethod>(this,
                android.R.layout.simple_list_item_1,
                itemTransferList.toArray(new ItemTransferMethod[itemTransferList.size()]));
        spinMetodeTransfer.setAdapter(itemTransferArrayAdapter);
    }

    @Override
    public void successConfirmPayment(JsonObject jsonRes) {
//        Toast.makeText(this, "Berhasil melakukan pembayaran ", Toast.LENGTH_SHORT).show();
        Helper.createAlert(this, Consts.STR_INFO, "Berhasil melakukan pembayaran ", false, new CallbackInterface() {
            @Override
            public void callback() {
                gotoActivity(MainActivity.class, true);
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

    private void setLoading(boolean isDisabled) {
        btnConfirm.setText((isDisabled) ? "Konfirmasi Pembayaran" : "Loading...");
        btnConfirm.setEnabled(isDisabled);
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
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (resultCode == RESULT_OK) {
            if (requestCode == Consts.CODE_REQUEST_GALLERY && data != null) {
                try {
                    mGalleryPhoto.setPhotoUri(data.getData());
                    String photPath = mGalleryPhoto.getPath();

                    Bitmap bitmap = ImageLoader
                            .init()
                            .from(photPath)
                            .requestSize(256, 256)
                            .getBitmap();

                    mImgProfileEncoded = ImageBase64.encode(bitmap);

                    imgScreenshoot.setImageDrawable(null);

                    imgScreenshoot.setImageDrawable(
                            ImageLoader
                                    .init()
                                    .from(photPath)
                                    .requestSize(256, 256)
                                    .getImageDrawable()
                    );

                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }
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
                edtNominalTransfer.removeTextChangedListener(this);

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
                    edtNominalTransfer.setText(formattedString);
                    edtNominalTransfer.setSelection(edtNominalTransfer.getText().length());

                } catch (NumberFormatException nfe) {
                    nfe.printStackTrace();
                }

                edtNominalTransfer.addTextChangedListener(this);

            }
        };
    }
}
