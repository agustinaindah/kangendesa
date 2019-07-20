package com.kangendesa.app.features.create_your_trip.edit;

import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.PorterDuff;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Switch;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.kangendesa.app.R;
import com.kangendesa.app.base.BaseActivity;
import com.kangendesa.app.features.create_your_trip.CreateSubmitMyTourActivity;
import com.kangendesa.app.features.create_your_trip.GetComponentTourPresenter;
import com.kangendesa.app.features.create_your_trip.GetComponentTourPresenterImpl;
import com.kangendesa.app.model.ItemDay;
import com.kangendesa.app.model.ItemExtraCon;
import com.kangendesa.app.model.ItemTransportation;
import com.kangendesa.app.utils.Consts;
import com.kangendesa.app.utils.Helper;
import com.kangendesa.app.utils.SharedPref;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * Created by agustinaindah on 25 Februari 2019
 */
public class EditConditionsMyTourActivity extends BaseActivity implements GetComponentTourPresenter.View{

    @BindView(R.id.cbSmartCasual)
    CheckBox cbSmartCasual;
    @BindView(R.id.cbPhysical)
    CheckBox cbPhysical;
    @BindView(R.id.cbVegetarian)
    CheckBox cbVegetarian;
    @BindView(R.id.cbChildrenFriendly)
    CheckBox cbChildrenFriendly;
    @BindView(R.id.cbFlexible)
    CheckBox cbFlexible;
    @BindView(R.id.cbSeasonal)
    CheckBox cbSeasonal;

    @BindView(R.id.edtExtraCondition)
    EditText edtExtraCondition;
    @BindView(R.id.edtDay)
    EditText edtDay;

    @BindView(R.id.switchSenin)
    Switch switchSenin;
    @BindView(R.id.switchSelasa)
    Switch switchSelasa;
    @BindView(R.id.switchRabu)
    Switch switchRabu;
    @BindView(R.id.switchKamis)
    Switch switchKamis;
    @BindView(R.id.switchJumat)
    Switch switchJumat;
    @BindView(R.id.switchSabtu)
    Switch switchSabtu;
    @BindView(R.id.switchMinggu)
    Switch switchMinggu;

    @BindView(R.id.btnNext)
    Button btnNext;

    private CharSequence[] mConditions;
    private boolean[] mSelectedCondtions;
    private String mExtraConditons;

    private CharSequence[] mDays;
    private boolean[] mSelectedDays;
    private String mChoseeDays;

    private List<ItemExtraCon> itemExtraCons;
    private List<ItemDay> itemDays;

    private GetComponentTourPresenter mPresenter;

    private String extraCon = " ";
    private String day = " ";

    @Override
    protected void onActivityCreated(Bundle savedInstanceState) {
        final Drawable upArrow = getResources().getDrawable(R.drawable.ic_arrow_left);
        upArrow.setColorFilter(getResources().getColor(R.color.colorPrimaryDark), PorterDuff.Mode.SRC_ATOP);

        getSupportActionBar().setHomeAsUpIndicator(upArrow);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle("Conditions");

        mPresenter = new GetComponentTourPresenterImpl(this);
        mPresenter.getComponentTour();

        getData();
    }

    @Override
    protected int setView() {
        return R.layout.activity_create_conditions_my_tour;
    }

    private JsonObject getInput(){
        JsonObject jsonInput = new JsonObject();
        try{
            jsonInput.addProperty("conditions_img", edtExtraCondition.getText().toString());
            jsonInput.addProperty("conditions_day", edtDay.getText().toString());

        }catch (Exception e){
            e.printStackTrace();
        }

        return jsonInput;
    }

    @OnClick(R.id.edtExtraCondition)
    public void selectConditions(){
        new AlertDialog.Builder(this)
                .setTitle("Extra Conditions")
                .setMultiChoiceItems(mConditions, mSelectedCondtions, new DialogInterface.OnMultiChoiceClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which, boolean isChecked) {
                        if (isChecked) {
                            mSelectedCondtions[which] = true;
                        }

                        StringBuilder sb = new StringBuilder("");
                        for (int i = 0; i < mConditions.length; i++) {
                            if (mSelectedCondtions[i]) {
                                sb.append(mConditions[i]);
                                sb.append(",");
                            }
                        }
                        mExtraConditons = String.valueOf(sb);
                    }
                })
                .setPositiveButton(android.R.string.ok, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        edtExtraCondition.setText(mExtraConditons);
                        dialog.dismiss();
                    }
                })
                .create()
                .show();
    }

    @OnClick(R.id.edtDay)
    public void selectDays(){
        new AlertDialog.Builder(this)
                .setTitle("Days")
                .setMultiChoiceItems(mDays, mSelectedDays, new DialogInterface.OnMultiChoiceClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which, boolean isChecked) {
                        if (isChecked) {
                            mSelectedDays[which] = true;
                        }

                        StringBuilder sb = new StringBuilder("");
                        for (int i = 0; i < mDays.length; i++) {
                            if (mSelectedDays[i]) {
                                sb.append(mDays[i]);
                                sb.append(",");
                            }
                        }
                        mChoseeDays = String.valueOf(sb);
                    }
                })
                .setPositiveButton(android.R.string.ok, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        edtDay.setText(mChoseeDays);
                        dialog.dismiss();
                    }
                })
                .create()
                .show();
    }

    @Override
    public void showComponentTour(List<ItemTransportation> itemTransportationList,
                                  List<ItemExtraCon> itemExtraConList,
                                  List<ItemDay> itemDayList) {
        mSelectedCondtions = new boolean[itemExtraConList.size()];
        Arrays.fill(mSelectedCondtions, Boolean.FALSE);
        Helper.log(String.valueOf(itemExtraConList.size()));
        itemExtraCons = itemExtraConList;
        mConditions = getExtraConditionToArray(itemExtraConList);

        mSelectedDays = new boolean[itemDayList.size()];
        Arrays.fill(mSelectedDays, Boolean.FALSE);
        Helper.log(String.valueOf(itemDayList.size()));
        itemDays = itemDayList;
        mDays = getDayToArray(itemDayList);
    }

    private CharSequence[] getExtraConditionToArray(List<ItemExtraCon> consList) {
        CharSequence[] conditionArray = new CharSequence[consList.size()];
        for (int i = 0; i < conditionArray.length; i++) {
            conditionArray[i] = consList.get(i).getValue();
        }
        return conditionArray;
    }

    private CharSequence[] getDayToArray(List<ItemDay> daysList) {
        CharSequence[] dayArray = new CharSequence[daysList.size()];
        for (int i = 0; i < dayArray.length; i++) {
            dayArray[i] = daysList.get(i).getValue();
        }
        return dayArray;
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

    @OnClick(R.id.btnNext)
    public void submitNext(){
        Intent intent = new Intent(this, EditSubmitMyTourActivity.class);
        startActivity(intent);
        SharedPref.saveString(Consts.CONDITION, getInput().toString());
        Helper.log(getInput().toString());
    }

    private void getData(){

        JsonObject jsonInput = Helper
                .getGsonInstance()
                .fromJson(Helper.parseToJsonArray(SharedPref.getString(Consts.BASIC))
                        .get(0).getAsJsonObject(), JsonObject.class);

        for (int i = 0; i < jsonInput.get("m_conditions_img").getAsJsonArray().size(); i++){
            extraCon = extraCon + jsonInput.get("m_conditions_img").getAsJsonArray()
                    .get(i).getAsJsonObject().get("value").getAsString() + ",";
        }

        for (int i = 0; i < jsonInput.get("m_conditions_day").getAsJsonArray().size(); i++){
            day = day + jsonInput.get("m_conditions_day").getAsJsonArray()
                    .get(i).getAsJsonObject().get("value").getAsString() + ",";
        }

        edtExtraCondition.setText((extraCon.equals("")) ? " " : extraCon);
        edtDay.setText((day.equals("")) ? " " : day);

    }
}
