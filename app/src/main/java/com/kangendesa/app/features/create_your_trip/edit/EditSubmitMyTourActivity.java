package com.kangendesa.app.features.create_your_trip.edit;

import android.app.ProgressDialog;
import android.graphics.Bitmap;
import android.graphics.PorterDuff;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.widget.Button;
import android.widget.Toast;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.kangendesa.app.R;
import com.kangendesa.app.base.BaseActivity;
import com.kangendesa.app.utils.Consts;
import com.kangendesa.app.utils.Helper;
import com.kangendesa.app.utils.SharedPref;
import com.kosalgeek.android.photoutil.ImageBase64;
import com.kosalgeek.android.photoutil.ImageLoader;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * Created by agustinaindah on 25 Februari 2019
 */
public class EditSubmitMyTourActivity extends BaseActivity {

    @BindView(R.id.btnSubmit)
    Button btnSubmit;

    private Bitmap bitmap;

    private ProgressDialog progressDialog;

    @Override
    protected void onActivityCreated(Bundle savedInstanceState) {
        final Drawable upArrow = getResources().getDrawable(R.drawable.ic_arrow_left);
        upArrow.setColorFilter(getResources().getColor(R.color.colorPrimaryDark), PorterDuff.Mode.SRC_ATOP);

        getSupportActionBar().setHomeAsUpIndicator(upArrow);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle("Submits");

        initLoading();
    }

    private void initLoading() {
        progressDialog = new ProgressDialog(this);
        progressDialog.setMessage(Consts.STR_LOADING);
    }

    @Override
    protected int setView() {
        return R.layout.activity_create_submit_my_tour;
    }

    private JsonObject getInput(){
        JsonObject jsonInput = Helper
                .getGsonInstance()
                .fromJson(SharedPref.getString(Consts.CONDITION), JsonObject.class);

        String prefix = "data:image/jpeg;base64,";
        try {
            bitmap = ImageLoader
                    .init()
                    .from(jsonInput.get("overview_photos_featured_img_path").getAsString())
                    .requestSize(256, 256)
                    .getBitmap();
        }catch (Exception e){
            e.printStackTrace();
        }
        jsonInput.addProperty("overview_photos_featured_img", prefix + ImageBase64.encode(bitmap));

        JsonArray jsonImage = new JsonArray();
        for(int i = 0; i < jsonInput.getAsJsonArray("overview_photos_img_array_uri").size() ; i++){
            try {
                Uri uri = Uri.parse(jsonInput.getAsJsonArray("overview_photos_img_array_uri").get(i).getAsString());
                Bitmap bitmap = MediaStore.Images.Media.getBitmap(this.getContentResolver(), uri);
                String mImagesEncodedList = prefix + ImageBase64.encode(bitmap);
                jsonImage.add(mImagesEncodedList);
            }catch (Exception e){
                e.printStackTrace();
            }
        }
        jsonInput.add("overview_photos_img", jsonImage);

        return jsonInput;
    }

    @OnClick(R.id.btnSubmit)
    public void submit() {
        Toast.makeText(this, "Lagi proses di implementasi :)", Toast.LENGTH_SHORT).show();
//        mPresenter.postCreateTour(getInput());
    }
}
