package com.kangendesa.app.features.dashboard.edit_profile;

import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.PorterDuff;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.JsonObject;
import com.kangendesa.app.R;
import com.kangendesa.app.base.BaseActivity;
import com.kangendesa.app.features.DateDialog;
import com.kangendesa.app.features.MainActivity;
import com.kangendesa.app.features.dashboard.DashboardActivity;
import com.kangendesa.app.utils.CallbackInterface;
import com.kangendesa.app.utils.Consts;
import com.kangendesa.app.utils.Helper;
import com.kangendesa.app.utils.SharedPref;
import com.kosalgeek.android.photoutil.GalleryPhoto;
import com.kosalgeek.android.photoutil.ImageBase64;
import com.kosalgeek.android.photoutil.ImageLoader;

import java.io.FileNotFoundException;

import butterknife.BindString;
import butterknife.BindView;
import butterknife.OnClick;

/**
 * Created by agustinaindah on 11 Februari 2019
 */
public class EditProfileActivity extends BaseActivity implements EditProfilePresenter.View,
        DateDialog.OnDateDialog {

    /*Change Photo*/
    @BindView(R.id.imgProfile)
    ImageView imgProfile;
    @BindView(R.id.btnChangePhoto)
    Button btnChangePhoto;

    @BindView(R.id.edtUsername)
    EditText edtUsername;
    @BindView(R.id.edtFirstname)
    EditText edtFirstname;
    @BindView(R.id.edtLastname)
    EditText edtLastname;
    @BindView(R.id.edtNickname)
    EditText edtNickname;
    @BindView(R.id.txtBirthdate)
    TextView txtBirthdate;
    @BindView(R.id.rgGender)
    RadioGroup rgGender;
    @BindView(R.id.rbGenderMale)
    RadioButton rbGenderMale;
    @BindView(R.id.rbGenderFemale)
    RadioButton rbGenderFemale;
    @BindView(R.id.edtLanguage)
    EditText edtLanguage;
    @BindView(R.id.edtVideoLinkUrl)
    EditText edtVideoLinkUrl;
    @BindView(R.id.edtAddress)
    EditText edtAddress;
    @BindView(R.id.edtDomisili)
    EditText edtDomisili;
    @BindView(R.id.edtAboutMe)
    EditText edtAboutMe;

    /*FAQ*/
    @BindView(R.id.edtMyHobby)
    EditText edtMyHobby;
    @BindView(R.id.edtLocalGuide)
    EditText edtLocalGuide;

    @BindView(R.id.btnSaveProfile)
    Button btnSaveProfile;

    @BindString(R.string.msg_empty)
    String strMsgEmpty;

    private String mFoto;
    private String userName;
    private String firstName;
    private String lastName;
    private String nickName;
    private String gender;
    private String address;
    private String domisili;
    private String aboutMe;
    private String birthDate;
    private String videoLink;
    private String language;
    private String hobby;
    private String localGuide;

    private EditProfilePresenter mPresenter;

    private ProgressDialog progressDialog;

    private String mImgProfileEncoded;
    private GalleryPhoto mGalleryPhoto;

    @Override
    protected void onActivityCreated(Bundle savedInstanceState) {
        final Drawable upArrow = getResources().getDrawable(R.drawable.ic_arrow_left);
        upArrow.setColorFilter(getResources().getColor(R.color.colorPrimaryDark), PorterDuff.Mode.SRC_ATOP);

        getSupportActionBar().setHomeAsUpIndicator(upArrow);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle("Edit Profile");

        initLoading();

        mPresenter = new EditProfilePresenterImpl(this);
        mGalleryPhoto = new GalleryPhoto(this);

        mPresenter.getEditProfile();

        birthDate = Helper.getDateNow();
        edtFirstname.requestFocus();

    }

    private void initLoading() {
        progressDialog = new ProgressDialog(this);
        progressDialog.setMessage(Consts.STR_LOADING);
    }

    @Override
    protected int setView() {
        return R.layout.activity_edit_profile;
    }

    private boolean validate() {
        boolean cancel = false;
        View focus = null;

        JsonObject jsonObject = getInput();

        if (TextUtils.isEmpty(jsonObject.get("first_name").getAsString())) {
            edtFirstname.setError(strMsgEmpty);
            focus = edtFirstname;
            cancel = true;
        }

        if (TextUtils.isEmpty(jsonObject.get("last_name").getAsString())) {
            edtLastname.setError(strMsgEmpty);
            focus = edtLastname;
            cancel = true;
        }

        if (TextUtils.isEmpty(jsonObject.get("kd_tgl_lahir_dmy").getAsString())) {
            txtBirthdate.setError(strMsgEmpty);
            focus = txtBirthdate;
            cancel = true;
        }

        if (TextUtils.isEmpty(jsonObject.get("kd_alamat").getAsString())) {
            edtAddress.setError(strMsgEmpty);
            focus = edtAddress;
            cancel = true;
        }

        if (cancel) {
            focus.requestFocus();
        }

        return cancel;

    }

    @OnClick(R.id.btnSaveProfile)
    public void updateProfile() {
        if (!validate()) {
            mPresenter.postEditProfile(getInput());
        }
    }

    @OnClick(R.id.btnChangePhoto)
    public void choosePhoto() {
        startActivityForResult(mGalleryPhoto.openGalleryIntent(), Consts.CODE_REQUEST_GALLERY);
    }

    private JsonObject getInput() {
        JsonObject jsonInput = new JsonObject();
        try {

            gender = (rgGender.getCheckedRadioButtonId() == R.id.rbGenderMale) ? Consts.MALE :
                    Consts.FEMALE;

            jsonInput.addProperty("profile_images", getImageEncodedWithPrefix());
            jsonInput.addProperty("first_name", edtFirstname.getText().toString());
            jsonInput.addProperty("last_name", edtLastname.getText().toString());
            jsonInput.addProperty("nickname", edtNickname.getText().toString());
            jsonInput.addProperty("kd_jk", gender);
            jsonInput.addProperty("kd_tgl_lahir_dmy", birthDate);
            jsonInput.addProperty("kd_video_link", edtVideoLinkUrl.getText().toString());
            jsonInput.addProperty("kd_bahasa", edtLanguage.getText().toString());
            jsonInput.addProperty("kd_alamat", edtAddress.getText().toString());
            jsonInput.addProperty("kd_domisili", edtDomisili.getText().toString());
            jsonInput.addProperty("kd_tentang_diri", edtAboutMe.getText().toString());
            jsonInput.addProperty("kd_hobi", edtMyHobby.getText().toString());
            jsonInput.addProperty("m_local_guide", edtLocalGuide.getText().toString());

        } catch (Exception e) {
            e.printStackTrace();
        }
        return jsonInput;
    }

    /*@Override
    public void successEditPhoto(JsonObject jsonRequest) {
        Toast.makeText(this, "Berhasil Update Foto Profile", Toast.LENGTH_SHORT).show();
    }*/

    @Override
    public void displayEditProfile(JsonObject jsonData) {
        try {

            mFoto       = jsonData.get("profile_image_url").getAsString();
            userName    = jsonData.get("username").getAsString();
            firstName   = jsonData.get("first_name").getAsString();
            lastName    = jsonData.get("last_name").getAsString();
            nickName    = jsonData.get("nickname").getAsString();
            gender      = jsonData.get("kd_jk").getAsString();
            address     = jsonData.get("kd_alamat").getAsString();
            domisili    = jsonData.get("kd_domisili").getAsString();
            aboutMe     = jsonData.get("kd_tentang_diri").getAsString();
            birthDate   = jsonData.get("kd_tgl_lahir_dmy").getAsString();
            videoLink   = jsonData.get("kd_video_link").getAsString();
            language    = jsonData.get("kd_bahasa").getAsString();
            hobby       = jsonData.get("kd_hobi").getAsString();
            localGuide  = jsonData.get("m_local_guide").getAsString();

            if (!mFoto.isEmpty()) {
                Helper.displayImage(this, mFoto, imgProfile, true);
            }

            edtFirstname.setText(firstName);
            edtLastname.setText(lastName);
            edtUsername.setText(userName);
            edtNickname.setText(nickName);

            rgGender.check((gender.equals(Consts.MALE) ? R.id.rbGenderMale : R.id.rbGenderFemale));

            if (!birthDate.equals(""))
                txtBirthdate.setText(Helper.parseToDateString(birthDate, Consts.TYPE_DATE));

            edtLanguage.setText(language);
            edtAddress.setText(address);
            edtDomisili.setText(domisili);
            edtAboutMe.setText(aboutMe);
            edtVideoLinkUrl.setText(videoLink);
            edtMyHobby.setText(hobby);
            edtLocalGuide.setText(localGuide);

        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    private String getImageEncodedWithPrefix() {
        String prefix = "data:image/jpeg;base64,";
        return (mImgProfileEncoded == null) ? "" : prefix + mImgProfileEncoded;
    }

    @Override
    public void successEditProfile(JsonObject jsonRequest) {
//        Toast.makeText(this, "Berhasil Update Profile", Toast.LENGTH_SHORT).show();
        Helper.createAlert(this, Consts.STR_INFO, "Berhasil mengubah data", false, new CallbackInterface() {
            @Override
            public void callback() {
                gotoActivity(DashboardActivity.class, true);
            }
        });
    }

    @OnClick(R.id.txtBirthdate)
    public void changeDate() {
        DialogFragment dateDialog = DateDialog.newInstance(birthDate, this);
        dateDialog.show(getSupportFragmentManager(), DateDialog.TAG);
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

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
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

                    imgProfile.setImageDrawable(null);

                    imgProfile.setImageDrawable(
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

    @Override
    public void onSelectedDate(String selectedDate) {
        birthDate = selectedDate;
        txtBirthdate.setText(Helper.parseToDateString(birthDate, Consts.TYPE_DATE));
    }
}
