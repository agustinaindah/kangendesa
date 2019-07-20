package com.kangendesa.app.features.bookingmanagement.traveler.paymenthistory;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.kangendesa.app.R;
import com.kangendesa.app.base.BaseRecyclerViewAdapter;
import com.kangendesa.app.features.bookingmanagement.traveler.paymenthistory.detail_history_payment.DetailPaymentHistoryActivity;
import com.kangendesa.app.model.ItemPaymentHistory;
import com.kangendesa.app.utils.Consts;
import com.kangendesa.app.utils.Helper;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by agustinaindah on 18 Februari 2019
 */
public class PaymentHistoryAdapter extends BaseRecyclerViewAdapter<ItemPaymentHistory> {

    public PaymentHistoryAdapter(List<ItemPaymentHistory> mData, Context mContext) {
        super(mData, mContext);
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View view = inflater.inflate(R.layout.item_payment_history, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        renderData(get(position), (ViewHolder) holder);
    }

    private void renderData(final ItemPaymentHistory itemPaymentHistory, final ViewHolder holder) {
        holder.txtOrderId.setText("#" + itemPaymentHistory.getKonfirmasiOrderNo());
        holder.txtEmail.setText(itemPaymentHistory.getKonfirmasiEmail());
        holder.txtRekName.setText(itemPaymentHistory.getKonfirmasiRekeningName());
        holder.txtTotal.setText(Helper.numberCurrency(Integer.valueOf(itemPaymentHistory.getKonfirmasiNominal())));
        holder.txtDate.setText(Helper.parseToDateString(itemPaymentHistory.getKonfirmasiTanggal(), Consts.TYPE_DATE));
        holder.txtStatus.setText(Helper.capitalize(itemPaymentHistory.getKonfirmasiStatus()));

        holder.cardPembelian.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(mContext, DetailPaymentHistoryActivity.class);
                intent.putExtra(Consts.ARG_ID, itemPaymentHistory.getKonfirmasiId());
                mContext.startActivity(intent);
            }
        });

    }

    public class ViewHolder extends RecyclerView.ViewHolder{

        @BindView(R.id.cardPembelian)
        CardView cardPembelian;
        @BindView(R.id.txtOrderId)
        TextView txtOrderId;
        @BindView(R.id.txtEmail)
        TextView txtEmail;
        @BindView(R.id.txtRekName)
        TextView txtRekName;
        @BindView(R.id.txtTotal)
        TextView txtTotal;
        @BindView(R.id.txtDate)
        TextView txtDate;
        @BindView(R.id.txtStatus)
        TextView txtStatus;

        public ViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }
}
