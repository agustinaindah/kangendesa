package com.kangendesa.app.features.create_your_trip;

import android.content.Context;
import android.content.Intent;
import android.graphics.PorterDuff;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.kangendesa.app.R;
import com.kangendesa.app.base.BaseActivity;
import com.kangendesa.app.model.ItemFaq;
import com.kangendesa.app.model.ItemSchedule;
import com.kangendesa.app.utils.Consts;
import com.kangendesa.app.utils.Helper;
import com.kangendesa.app.utils.SharedPref;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * Created by agustinaindah on 13 Februari 2019
 */
public class CreateDetailMyTourActivity extends BaseActivity {

    @BindView(R.id.edtGatehringPoint)
    EditText edtGatehringPoint;
    @BindView(R.id.edtLatitude)
    EditText edtLatitude;
    @BindView(R.id.edtLongitude)
    EditText edtLongitude;
    @BindView(R.id.edtDetailKumpul)
    EditText edtDetailKumpul;

    /*schedule*/
    @BindView(R.id.layJadwal)
    LinearLayout layJadwal;
    /* @BindView(R.id.edtStartTime)
     EditText edtStartTime;
     @BindView(R.id.edtEndTime)
     EditText edtEndTime;
     @BindView(R.id.edtDesc)
     EditText edtDesc;*/
    @BindView(R.id.conSchedule)
    LinearLayout conSchedule;
    @BindView(R.id.btnAddFieldSchedule)
    Button btnAddFieldSchedule;

    /*faq*/
    @BindView(R.id.layFaq)
    LinearLayout layFaq;
    /* @BindView(R.id.edtAnswer1)
     EditText edtAnswer1;
     @BindView(R.id.edtAnswer2)
     EditText edtAnswer2;
     @BindView(R.id.edtQuestion)
     EditText edtQuestion;
     @BindView(R.id.edtAnswer)
     EditText edtAnswer;*/
    @BindView(R.id.conFaq)
    LinearLayout conFaq;
    @BindView(R.id.btnAddFieldFaq)
    Button btnAddFieldFaq;

    @BindView(R.id.btnNext)
    Button btnNext;

    private List<ItemSchedule> mItemSchedule = new ArrayList<ItemSchedule>();
    private int index = -1;

    private List<ItemFaq> mItemFaq = new ArrayList<ItemFaq>();
    private int indexFaq = -1;

    @Override
    protected void onActivityCreated(Bundle savedInstanceState) {
        final Drawable upArrow = getResources().getDrawable(R.drawable.ic_arrow_left);
        upArrow.setColorFilter(getResources().getColor(R.color.colorPrimaryDark), PorterDuff.Mode.SRC_ATOP);

        getSupportActionBar().setHomeAsUpIndicator(upArrow);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle("Detail");

//        saveToPref();
        ItemSchedule schedule = new ItemSchedule("", "", "");
        mItemSchedule.add(schedule);

        ItemFaq faq = new ItemFaq("", "");
        mItemFaq.add(faq);

        addFieldSchedule();
        addFieldFaq();

        btnAddFieldSchedule.performClick();
        btnAddFieldFaq.performClick();

    }

    private void addFieldSchedule() {
        btnAddFieldSchedule.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                index = index + 1;

                ItemSchedule itemSchedule = new ItemSchedule("", "", "");
                mItemSchedule.add(itemSchedule);

                LayoutInflater layoutInflater =
                        (LayoutInflater) getBaseContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
                final View addView = layoutInflater.inflate(R.layout.item_dynamic_input_schedule, null);

                EditText txtStart = (EditText) addView.findViewById(R.id.txtStart);
                EditText txtEnd = (EditText) addView.findViewById(R.id.txtEnd);
                EditText txtDesc = (EditText) addView.findViewById(R.id.txtDesc);

                Helper.log(String.valueOf(index));
                txtStart.setTag(index);
                txtEnd.setTag(index);
                txtDesc.setTag(index);

                txtStart.addTextChangedListener(new TextWatcher() {
                    @Override
                    public void beforeTextChanged(CharSequence s, int i, int i1, int i2) {

                    }

                    @Override
                    public void onTextChanged(CharSequence s, int i, int i1, int i2) {
                        mItemSchedule.get(index).setFirstTime(s.toString());
                        Helper.log((s.toString()));
                    }

                    @Override
                    public void afterTextChanged(Editable s) {

                    }
                });

                txtEnd.addTextChangedListener(new TextWatcher() {
                    @Override
                    public void beforeTextChanged(CharSequence s, int i, int i1, int i2) {

                    }

                    @Override
                    public void onTextChanged(CharSequence s, int i, int i1, int i2) {
                        mItemSchedule.get(index).setLastTime(s.toString());
                    }

                    @Override
                    public void afterTextChanged(Editable s) {

                    }
                });

                txtDesc.addTextChangedListener(new TextWatcher() {
                    @Override
                    public void beforeTextChanged(CharSequence s, int i, int i1, int i2) {

                    }

                    @Override
                    public void onTextChanged(CharSequence s, int i, int i1, int i2) {
                        mItemSchedule.get(index).setSchedule(s.toString());
                    }

                    @Override
                    public void afterTextChanged(Editable s) {

                    }
                });

                txtStart.addTextChangedListener(new TextWatcher() {
                    public void afterTextChanged(Editable s) {

                    }

                    public void beforeTextChanged(CharSequence s, int start, int count, int after) {

                    }

                    public void onTextChanged(CharSequence s, int start, int before, int count) {
                        mItemSchedule.get(index).setFirstTime(s.toString());
                    }
                });

                txtEnd.addTextChangedListener(new TextWatcher() {
                    public void afterTextChanged(Editable s) {

                    }

                    public void beforeTextChanged(CharSequence s, int start, int count, int after) {

                    }

                    public void onTextChanged(CharSequence s, int start, int before, int count) {
                        mItemSchedule.get(index).setLastTime(s.toString());
                    }
                });

                txtDesc.addTextChangedListener(new TextWatcher() {
                    public void afterTextChanged(Editable s) {

                    }

                    public void beforeTextChanged(CharSequence s, int start, int count, int after) {

                    }

                    public void onTextChanged(CharSequence s, int start, int before, int count) {
                        mItemSchedule.get(index).setSchedule(s.toString());
                    }
                });


                Button btnDelete = (Button) addView.findViewById(R.id.btnDelete);
                final View.OnClickListener thisListener = new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        ((LinearLayout) addView.getParent()).removeView(addView);
                        mItemSchedule.remove(index);
                    }
                };

                btnDelete.setOnClickListener(thisListener);
                conSchedule.addView(addView);
            }
        });

    }

    private void addFieldFaq() {
        btnAddFieldFaq.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                indexFaq = indexFaq + 1;

                ItemFaq itemFaq = new ItemFaq("", "");
                mItemFaq.add(itemFaq);

                LayoutInflater layoutInflater =
                        (LayoutInflater) getBaseContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
                final View addView = layoutInflater.inflate(R.layout.item_dynamic_input_faq, null);

                EditText txtQuestion = (EditText) addView.findViewById(R.id.txtQuestion);
                EditText txtAnswer = (EditText) addView.findViewById(R.id.txtAnswer);

                txtQuestion.setTag(indexFaq);
                txtAnswer.setTag(indexFaq);

                txtQuestion.addTextChangedListener(new TextWatcher() {
                    @Override
                    public void beforeTextChanged(CharSequence s, int i, int i1, int i2) {

                    }

                    @Override
                    public void onTextChanged(CharSequence s, int i, int i1, int i2) {
                        mItemFaq.get(indexFaq).setTitle(s.toString());
                    }

                    @Override
                    public void afterTextChanged(Editable s) {

                    }
                });

                txtAnswer.addTextChangedListener(new TextWatcher() {
                    @Override
                    public void beforeTextChanged(CharSequence s, int i, int i1, int i2) {

                    }

                    @Override
                    public void onTextChanged(CharSequence s, int i, int i1, int i2) {
                        mItemFaq.get(indexFaq).setDesc(s.toString());
                    }

                    @Override
                    public void afterTextChanged(Editable s) {

                    }
                });

                txtQuestion.addTextChangedListener(new TextWatcher() {
                    @Override
                    public void beforeTextChanged(CharSequence s, int i, int i1, int i2) {

                    }

                    @Override
                    public void onTextChanged(CharSequence s, int i, int i1, int i2) {
                        mItemFaq.get(indexFaq).setTitle(s.toString());
                    }

                    @Override
                    public void afterTextChanged(Editable s) {

                    }
                });

                txtAnswer.addTextChangedListener(new TextWatcher() {
                    @Override
                    public void beforeTextChanged(CharSequence s, int i, int i1, int i2) {

                    }

                    @Override
                    public void onTextChanged(CharSequence s, int i, int i1, int i2) {
                        mItemFaq.get(indexFaq).setDesc(s.toString());
                    }

                    @Override
                    public void afterTextChanged(Editable s) {

                    }
                });

//                txtQuestion.setText(edtQuestion.getText().toString());
//                txtAnswer.setText(edtAnswer.getText().toString());

                Button btnDeleteFaq = (Button) addView.findViewById(R.id.btnDeleteFaq);
                final View.OnClickListener thisListener = new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        ((LinearLayout) addView.getParent()).removeView(addView);
                        mItemFaq.remove(indexFaq);
                    }
                };

                btnDeleteFaq.setOnClickListener(thisListener);
                conFaq.addView(addView);
            }
        });
    }

   /*{
        "overview_trip" : "",
        "overview_summary" : "",
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
        "detail_schedule" :
        [
            {"first_time" : "00:00", "last_time" : "00:00", "schedule" : ""},
            {"first_time" : "00:00", "last_time" : "00:00", "schedule" : ""},
            {"first_time" : "00:00", "last_time" : "00:00", "schedule" : ""},
        ],
        "detail_faq" :
        [
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

    //                String kalimat =  startTime + "," + endTime + "," + desc;
//                Log.d("kalimat", kalimat);
//
//                int i = 0;
//                List<String> myList = new ArrayList<String>(Arrays.asList(kalimat.split(",")));
//                for (i=0 ; i < myList.size(); i++) {
//                    myList.get(i).toString();
//                    Log.d("MyArray",myList.get(i).toString());
//                }

//                txtStart.setText(startTime);
//                txtEnd.setText(endTime);
//                txtDesc.setText(desc);

    private JsonObject getInput() {

        JsonObject jsonInput = Helper
                .getGsonInstance()
                .fromJson(SharedPref.getString(Consts.OVERVIEW), JsonObject.class);
        try {
            jsonInput.addProperty("detail_meeting", edtGatehringPoint.getText().toString());
            jsonInput.addProperty("map_google_geometry_lat", edtLatitude.getText().toString());
            jsonInput.addProperty("map_google_geometry_lng", edtLongitude.getText().toString());
            jsonInput.addProperty("detail_meeting_desc", edtDetailKumpul.getText().toString());

            JsonArray arr_detailSchedule = new JsonArray();

            for (int i = 0; i < mItemSchedule.size(); i++) {
                JsonObject schedule = new JsonObject();

                schedule.addProperty("first_time", mItemSchedule.get(i).getFirstTime());
                schedule.addProperty("last_time", mItemSchedule.get(i).getLastTime());
                schedule.addProperty("schedule", mItemSchedule.get(i).getSchedule());
                arr_detailSchedule.add(schedule);
            }
            jsonInput.add("detail_schedule", arr_detailSchedule);

            JsonArray arr_detailFaq = new JsonArray();

            for (int i = 0; i < mItemFaq.size(); i++) {
                JsonObject faq = new JsonObject();

                faq.addProperty("title", mItemFaq.get(i).getTitle());
                faq.addProperty("desc", mItemFaq.get(i).getDesc());
                arr_detailFaq.add(faq);
            }
            jsonInput.add("detail_faq", arr_detailFaq);

        } catch (Exception e) {
            e.printStackTrace();
        }

        return jsonInput;
    }

    @Override
    protected int setView() {
        return R.layout.activity_create_detail_my_tour;
    }

    private boolean validate() {
        boolean cancel = false;
        View focus = null;

        EditText txtStart = (EditText) findViewById(R.id.txtStart);
        EditText txtEnd = (EditText) findViewById(R.id.txtEnd);
        EditText txtDesc = (EditText) findViewById(R.id.txtDesc);
        EditText txtQuestion = (EditText) findViewById(R.id.txtQuestion);
        EditText txtAnswer = (EditText) findViewById(R.id.txtAnswer);

        edtGatehringPoint.setError(null);
        edtLatitude.setError(null);
        edtLongitude.setError(null);
        edtDetailKumpul.setError(null);
        txtStart.setError(null);
        txtEnd.setError(null);
        txtDesc.setError(null);
        txtQuestion.setError(null);
        txtAnswer.setError(null);

      /*  edtStartTime.setError(null);
        edtEndTime.setError(null);
        edtDesc.setError(null);*/
        /*edtAnswer1.setError(null);
        edtAnswer2.setError(null);
        edtQuestion.setError(null);
        edtAnswer.setError(null);*/

        if (TextUtils.isEmpty(edtGatehringPoint.getText().toString())) {
            edtGatehringPoint.setError("Harus diisi");
            focus = edtGatehringPoint;
            cancel = true;
        }

        if (TextUtils.isEmpty(edtLatitude.getText().toString())) {
            edtLatitude.setError("Harus diisi");
            focus = edtLatitude;
            cancel = true;
        }

        if (TextUtils.isEmpty(edtLongitude.getText().toString())) {
            edtLongitude.setError("Harus diisi");
            focus = edtLongitude;
            cancel = true;
        }

        if (TextUtils.isEmpty(edtDetailKumpul.getText().toString())) {
            edtDetailKumpul.setError("Harus diisi");
            focus = edtDetailKumpul;
            cancel = true;
        }

        if (TextUtils.isEmpty(txtStart.getText().toString())) {
            txtStart.setError("Harus diisi");
            focus = txtStart;
            cancel = true;
        }

        if (TextUtils.isEmpty(txtEnd.getText().toString())) {
            txtEnd.setError("Harus diisi");
            focus = txtEnd;
            cancel = true;
        }

        if (TextUtils.isEmpty(txtDesc.getText().toString())) {
            txtDesc.setError("Harus diisi");
            focus = txtDesc;
            cancel = true;
        }
       /* if (TextUtils.isEmpty(edtAnswer1.getText().toString())) {
            edtAnswer1.setError("Harus diisi");
            focus = edtAnswer1;
            cancel = true;
        }

        if (TextUtils.isEmpty(edtAnswer2.getText().toString())) {
            edtAnswer2.setError("Harus diisi");
            focus = edtAnswer2;
            cancel = true;
        }*/

        if (TextUtils.isEmpty(txtQuestion.getText().toString())) {
            txtQuestion.setError("Harus diisi");
            focus = txtQuestion;
            cancel = true;
        }

        if (TextUtils.isEmpty(txtAnswer.getText().toString())) {
            txtAnswer.setError("Harus diisi");
            focus = txtAnswer;
            cancel = true;
        }

        if (cancel) {
            focus.requestFocus();
        }

        return cancel;
    }

    @OnClick(R.id.btnNext)
    public void priceNext() {
        if (!validate()) {
            SharedPref.saveString(Consts.DETAIL, getInput().toString());
            Intent intent = new Intent(this, CreatePriceMyTourActivity.class);
            startActivity(intent);
            Helper.log(getInput().toString());
        }

       /* Intent intent = new Intent(this, CreatePriceMyTourActivity.class);
        startActivity(intent);

        SharedPref.saveString(Consts.DETAIL, getInput().toString());

        Helper.log(getInput().toString());*/
    }
}
