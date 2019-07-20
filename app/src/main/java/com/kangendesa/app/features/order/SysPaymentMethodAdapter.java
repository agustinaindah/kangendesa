package com.kangendesa.app.features.order;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.kangendesa.app.R;
import com.kangendesa.app.model.PaymentMethod;

import java.util.Collections;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by agustinaindah on 29 Januari 2019
 */
public class SysPaymentMethodAdapter extends BaseAdapter {

    @BindView(R.id.txtPaymentMethod)
    TextView txtPaymentMethod;

    private Context context;
    private List<PaymentMethod> paymentMethod = Collections.emptyList();

    public SysPaymentMethodAdapter(Context context) {
        this.context = context;
    }

    public void updateSysPaymentMethodItem(List<PaymentMethod> paymentMethod) {
        this.paymentMethod = paymentMethod;
        notifyDataSetChanged();
    }

    @Override
    public int getCount() {
        return paymentMethod.size();
    }

    @Override
    public Object getItem(int position) {
        return paymentMethod.get(position);
    }

    @Override
    public long getItemId(int i) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if (convertView == null) {
            convertView = LayoutInflater.from(context).
                    inflate(R.layout.item_sys_payment_method, parent, false);
        }
        ButterKnife.bind(this, convertView);
        displayData((PaymentMethod) getItem(position));
        return convertView;
    }

    private void displayData(PaymentMethod item) {
        txtPaymentMethod.setText(item.getMethodTitle());
    }
}
