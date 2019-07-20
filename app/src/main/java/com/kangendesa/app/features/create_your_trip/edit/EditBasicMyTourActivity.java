package com.kangendesa.app.features.create_your_trip.edit;

import android.content.ClipData;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.PorterDuff;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.text.Html;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.GridView;
import android.widget.ImageView;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.kangendesa.app.R;
import com.kangendesa.app.base.BaseActivity;
import com.kangendesa.app.features.create_your_trip.CreateOverviewMyTourActivity;
import com.kangendesa.app.features.create_your_trip.GalleryAdapter;
import com.kangendesa.app.features.tour.detail.DetailTourPresenter;
import com.kangendesa.app.features.tour.detail.DetailTourPresenterImpl;
import com.kangendesa.app.model.ItemGuide;
import com.kangendesa.app.model.ItemTour;
import com.kangendesa.app.utils.Consts;
import com.kangendesa.app.utils.Helper;
import com.kangendesa.app.utils.SharedPref;
import com.kosalgeek.android.photoutil.GalleryPhoto;
import com.kosalgeek.android.photoutil.ImageBase64;
import com.kosalgeek.android.photoutil.ImageLoader;

import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * Created by agustinaindah on 25 Februari 2019
 */
public class EditBasicMyTourActivity extends BaseActivity implements DetailTourPresenter.View{

    // TODO: 25/02/2019 tunggu implementasi

    @BindView(R.id.edtTripName)
    EditText edtTripName;
    @BindView(R.id.edtTripDetail)
    EditText edtTripDetail;
    @BindView(R.id.imgPhotoCover)
    ImageView imgPhotoCover;
    @BindView(R.id.btnChoosePhotoCover)
    Button btnChoosePhotoCover;
    @BindView(R.id.imgPhotoDetail)
    ImageView imgPhotoDetail;
    @BindView(R.id.btnChooseFileDetail)
    Button btnChooseFileDetail;
    @BindView(R.id.btnChooseFile)
    Button btnChooseFile;
    @BindView(R.id.gvPhotoDetail)
    GridView gvPhotoDetail;

    @BindView(R.id.btnNext)
    Button btnNext;

    private String mImgProfileEncoded;
    private String mImgProfileEncoded2;
    private GalleryPhoto mGalleryPhoto;

    private String imageEncoded;
    private List<String> imagesEncodedList;
    private GalleryAdapter galleryAdapter;
    private String mImagesEncodedList;
    private String imageArray;
    private String mImage = "";
    private JsonArray arrayJsonImage = new JsonArray();
    private ArrayList<Uri> mArrayUri = new ArrayList<Uri>();
    private String photoPath;

    private ItemTour mItemTour;
    private DetailTourPresenter mPresenter;

    private String idPost;

    @Override
    protected void onActivityCreated(Bundle savedInstanceState) {
        final Drawable upArrow = getResources().getDrawable(R.drawable.ic_arrow_left);
        upArrow.setColorFilter(getResources().getColor(R.color.colorPrimaryDark), PorterDuff.Mode.SRC_ATOP);

        getSupportActionBar().setHomeAsUpIndicator(upArrow);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle("Basic");

        idPost = getIntent().getStringExtra(Consts.ARG_ID);

        mPresenter = new DetailTourPresenterImpl(this);
        mPresenter.getDetailTour(getQueryRequest());
        mGalleryPhoto = new GalleryPhoto(this);

        edtTripName.requestFocus();
    }

    @Override
    protected int setView() {
        return R.layout.activity_create_basic_my_tour;
    }

    private JsonObject getInput(){
        JsonObject jsonInput = new JsonObject();
        try{
            jsonInput.addProperty("overview_trip", edtTripName.getText().toString());
            jsonInput.addProperty("overview_summary", edtTripDetail.getText().toString());
            jsonInput.addProperty("overview_photos_featured_img_path", photoPath);
            jsonInput.add("overview_photos_img_array_uri", arrayJsonImage);
        }catch (Exception e){
            e.printStackTrace();
        }
        return jsonInput;
    }

    private Map<String, String> getQueryRequest() {
        HashMap<String, String> requestMap = new HashMap<>();
        requestMap.put("id", idPost);
        return requestMap;
    }

    private boolean validate(){
        boolean cancel = false;
        View focus = null;

        edtTripName.setError(null);
        edtTripDetail.setError(null);
        btnChoosePhotoCover.setError(null);
        btnChooseFileDetail.setError(null);

        if (TextUtils.isEmpty(edtTripName.getText().toString())) {
            edtTripName.setError("Harus diisi");
            focus = edtTripName;
            cancel = true;
        }

        if (TextUtils.isEmpty(edtTripDetail.getText().toString())) {
            edtTripDetail.setError("Harus diisi");
            focus = edtTripDetail;
            cancel = true;
        }

        if (TextUtils.isEmpty(mImgProfileEncoded)) {
            btnChoosePhotoCover.setError("Harus diisi");
            focus = btnChoosePhotoCover;
            cancel = true;
        }

        if (TextUtils.isEmpty(mImage)) {
            btnChooseFileDetail.setError("Harus diisi");
            focus = btnChooseFileDetail;
            cancel = true;
        }

        if (cancel) {
            focus.requestFocus();
        }

        return cancel;
    }

/*
    private String getImageEncodedWithPrefix() {
        String prefix = "data:image/jpeg;base64,";
        return (mImgProfileEncoded == null) ? "" : prefix + mImgProfileEncoded;
    }

    private String getImageEncodedWithPrefix2() {
        String prefix = "data:image/jpeg;base64,";
        return (mImagesEncodedList == null) ? "" : prefix + mImagesEncodedList;
    }
*/

    @OnClick(R.id.btnChoosePhotoCover)
    public void choosePhotoCover() {
        startActivityForResult(mGalleryPhoto.openGalleryIntent(), Consts.CODE_REQUEST_GALLERY);
    }

    @OnClick(R.id.btnChooseFile)
    public void chooseFileDetail() {
        Intent intent = new Intent();
        intent.setType("image/*");
        intent.putExtra(Intent.EXTRA_ALLOW_MULTIPLE, true);
        intent.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(Intent.createChooser(intent,"Select Picture"), Consts.PICK_IMAGE_MULTIPLE);
    }

    @OnClick(R.id.btnNext)
    public void overviewNext(){
       /* if (!validate()){
            Intent intent = new Intent(this, CreateOverviewMyTourActivity.class);
            startActivity(intent);
        }*/
        Intent intent = new Intent(this, EditOverviewMyTourActivity.class);
        startActivity(intent);
//        SharedPref.saveString(Consts.BASIC, getInput().toString());
        Helper.log(getInput().toString());
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        switch (requestCode) {
            case Consts.CODE_REQUEST_GALLERY:
                if (resultCode == RESULT_OK && data != null) {
                    try {
                        mGalleryPhoto.setPhotoUri(data.getData());
                        photoPath = mGalleryPhoto.getPath();

                      /*  Bitmap bitmap = ImageLoader
                                .init()
                                .from(photoPath)
                                .requestSize(256, 256)
                                .getBitmap();

                        mImgProfileEncoded = ImageBase64.encode(bitmap);*/

                        imgPhotoCover.setImageDrawable(null);

                        imgPhotoCover.setImageDrawable(
                                ImageLoader
                                        .init()
                                        .from(photoPath)
                                        .requestSize(512, 512)
                                        .getImageDrawable()
                        );

                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
                break;
            case Consts.PICK_IMAGE_MULTIPLE:
                if (resultCode == RESULT_OK && data != null) {

                    String[] filePathColumn = { MediaStore.Images.Media.DATA };
                    imagesEncodedList = new ArrayList<String>();
                    arrayJsonImage = new JsonArray();

                    try {
                        if (data.getClipData() != null) {
                            ClipData mClipData = data.getClipData();
                            mArrayUri = new ArrayList<Uri>();
                            mImage = "[";

                            for (int i = 0; i < mClipData.getItemCount(); i++) {

                                ClipData.Item item = mClipData.getItemAt(i);
                                Uri uri = item.getUri();
                                mArrayUri.add(uri);
                                arrayJsonImage.add(uri.toString());
                                // Get the cursor
                                Cursor cursor = getContentResolver().query(uri, filePathColumn, null, null, null);
                                // Move to first row
                                cursor.moveToFirst();

                                Helper.log(mArrayUri.toString());

                                int columnIndex = cursor.getColumnIndex(filePathColumn[0]);

                               /* String prefix = "data:image/jpeg;base64,";
                                Uri uri2 = Uri.parse(uri.toString());
                                Bitmap bitmap = MediaStore.Images.Media.getBitmap(this.getContentResolver(), uri2);
                                mImagesEncodedList = prefix + ImageBase64.encode(bitmap);
                                arrayJsonImage.add(mImagesEncodedList);*/

//                                Log.d("base", prefix + mImagesEncodedList);

                                imageEncoded  = cursor.getString(columnIndex);
                                imagesEncodedList.add(imageEncoded);

//                                imageArray = "," + prefix + mImagesEncodedList;
//                                imageArray = prefix + mImagesEncodedList;
//                                imageArray = String.join(",", mImagesEncodedList);
                                Log.d("IMAGE", mImage);
                                cursor.close();

                                galleryAdapter = new GalleryAdapter(getApplicationContext(),mArrayUri);
                                gvPhotoDetail.setAdapter(galleryAdapter);

                                gvPhotoDetail.setVerticalSpacing(gvPhotoDetail.getHorizontalSpacing());
                                ViewGroup.MarginLayoutParams mlp = (ViewGroup.MarginLayoutParams) gvPhotoDetail
                                        .getLayoutParams();
                                mlp.setMargins(0, gvPhotoDetail.getHorizontalSpacing(), 0, 0);

                            }

                            Log.d("LOG_TAG", "Selected Images " + mArrayUri.size());
                        }

                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
                break;
        }
    }

    @Override
    public void success(ItemTour itemTour) {

    }

    @Override
    public void successTour(String data) {
        SharedPref.saveString(Consts.BASIC, data);

        JsonObject jsonInput = Helper
                .getGsonInstance()
                .fromJson(Helper.parseToJsonArray(SharedPref.getString(Consts.BASIC))
                        .get(0).getAsJsonObject(), JsonObject.class);

        edtTripName.setText(jsonInput.get("post_title").getAsString());
        edtTripDetail.setText(jsonInput.get("post_content").getAsString());

        Helper.log(jsonInput.toString());

        String photoCover = jsonInput.get("m_overview_photos_featured_img").getAsJsonArray().get(0).getAsString();
        Helper.displayImage(this, photoCover, imgPhotoCover);

//        String photoDetail = jsonInput.get("m_overview_photos_img").getAsJsonArray().get(0).getAsString();
        for (int i = 0 ; i < jsonInput.get("m_overview_photos_img").getAsJsonArray().size(); i++){
            String imageDetail = jsonInput.get("m_overview_photos_img").getAsJsonArray().get(0).getAsString();
            Uri myUri = Uri.parse(imageDetail);

            ArrayList<Uri> mImageUri = new ArrayList<Uri>();
            mImageUri.add(myUri);

            galleryAdapter = new GalleryAdapter(getApplicationContext(), mImageUri);
            gvPhotoDetail.setAdapter(galleryAdapter);

            gvPhotoDetail.setVerticalSpacing(gvPhotoDetail.getHorizontalSpacing());
            ViewGroup.MarginLayoutParams mlp = (ViewGroup.MarginLayoutParams) gvPhotoDetail
                    .getLayoutParams();
            mlp.setMargins(0, gvPhotoDetail.getHorizontalSpacing(), 0, 0);
        }
    }

    @Override
    public void successDetailGuide(ItemGuide itemGuide) {

    }

    @Override
    public void showProgress() {

    }

    @Override
    public void hideProgress() {

    }

    @Override
    public void showMessage(String msg) {

    }

    @Override
    public void notConnect(String msg) {

    }
}
