package com.kangendesa.app.features.order.order_result;

import android.graphics.PorterDuff;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.text.Html;
import android.widget.Button;
import android.widget.TextView;

import com.kangendesa.app.R;
import com.kangendesa.app.base.BaseActivity;
import com.kangendesa.app.features.bookingmanagement.traveler.mybooking.MyBookingActivity;
import com.kangendesa.app.model.DetailOrder;
import com.kangendesa.app.model.ItemOrder;
import com.kangendesa.app.utils.Consts;
import com.kangendesa.app.utils.Helper;

import java.util.HashMap;
import java.util.Map;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * Created by agustinaindah on 30 Januari 2019
 */
public class OrderResultActivity extends BaseActivity implements OrderResultPresenter.View{

    @BindView(R.id.txtOrderId)
    TextView txtOrderId;
    @BindView(R.id.txtDateOrder)
    TextView txtDateOrder;
    @BindView(R.id.txtEmail)
    TextView txtEmail;
    @BindView(R.id.txtTotal)
    TextView txtTotal;
    @BindView(R.id.txtPaymentMethod)
    TextView txtPaymentMethod;

    //your order
    @BindView(R.id.txtTouristName)
    TextView txtTouristName;
    @BindView(R.id.txtSubtotal)
    TextView txtSubtotal;
    @BindView(R.id.txtTotalPayment)
    TextView txtTotalPayment;

    @BindView(R.id.btnCheckMyOrder)
    Button btnCheckMyOrder;

    private OrderResultPresenter mPresenter;
    private DetailOrder mDetailOrder;

    private ItemOrder mItemOrder;

    private String numbId;
    private Integer jmlWisatawan;

    @Override
    protected void onActivityCreated(Bundle savedInstanceState) {
        final Drawable upArrow = getResources().getDrawable(R.drawable.ic_arrow_left);
        upArrow.setColorFilter(getResources().getColor(R.color.colorPrimaryDark), PorterDuff.Mode.SRC_ATOP);

        getSupportActionBar().setHomeAsUpIndicator(upArrow);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle("Hasil Order");

        numbId = getIntent().getStringExtra("order_number");
        jmlWisatawan = getIntent().getIntExtra("Jml_wisatawan", 0);

        mPresenter = new OrderResultPresenterImpl(this);
        mPresenter.getDetailOrder(getQueryRequest());

    }

    @Override
    protected int setView() {
        return R.layout.activity_result_order;
    }

    private Map<String, String> getQueryRequest() {
        HashMap<String, String> requestMap = new HashMap<>();
        requestMap.put("order_id", numbId);
        return requestMap;
    }

    @OnClick(R.id.btnCheckMyOrder)
    public void checkMyOrder(){
//        Toast.makeText(this, "Check My Order ", Toast.LENGTH_SHORT).show();
        gotoActivity(MyBookingActivity.class, true);
    }

    private void showData(ItemOrder itemOrder) {
        int total = jmlWisatawan * Integer.valueOf(itemOrder.getMOrderTotal());

        txtOrderId.setText(numbId);
        txtDateOrder.setText(Helper.parseToDateString(itemOrder.getPostDate(), Consts.TYPE_DATE));
        txtEmail.setText(itemOrder.getMBillingEmail());
        txtTotal.setText(Helper.numberCurrency(total));
        txtPaymentMethod.setText(itemOrder.getMPaymentMethodTitle());

        //your order
        txtTouristName.setText(Html.fromHtml(itemOrder.getOrderDetail().getName()));
        /*txtSubtotal.setText(Helper.numberCurrency(Integer.valueOf(detailOrder.getOrderDetail().getSubtotal())));
        txtTotalPayment.setText(Helper.numberCurrency(Integer.valueOf(detailOrder.getOrderDetail().getTotal())));*/
        txtSubtotal.setText(Helper.numberCurrency(total));
        txtTotalPayment.setText(Helper.numberCurrency(total));
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
        btnCheckMyOrder.setText((isDisabled) ? "Check My Order" : "Loading...");
        btnCheckMyOrder.setEnabled(isDisabled);
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
    public void success(ItemOrder itemOrder) {
        mItemOrder = itemOrder;
        showData(itemOrder);
    }
}
