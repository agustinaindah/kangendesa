package com.kangendesa.app.features.bookingmanagement.traveler.mybooking;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.widget.CardView;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.PopupMenu;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.kangendesa.app.R;
import com.kangendesa.app.base.BaseRecyclerViewAdapter;
import com.kangendesa.app.features.bookingmanagement.traveler.mybooking.payment_confirm.PaymentConfirmActivity;
import com.kangendesa.app.features.confirm_payment.ConfirmPaymentActivity;
import com.kangendesa.app.model.DetailOrder;
import com.kangendesa.app.model.ItemListOrder;
import com.kangendesa.app.utils.Consts;
import com.kangendesa.app.utils.Helper;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by agustinaindah on 04 Februari 2019
 */

public class MyBookingAdapter extends BaseRecyclerViewAdapter<ItemListOrder> {

    public MyBookingAdapter(List<ItemListOrder> mData, Context mContext) {
        super(mData, mContext);
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View view = inflater.inflate(R.layout.item_my_booking, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        renderData(get(position), (ViewHolder) holder);
    }

    private void renderData(final ItemListOrder itemListOrder, final ViewHolder holder) {
        holder.txtOrderId.setText(String.valueOf("#" + itemListOrder.getID()));
        holder.txtDate.setText(Helper.parseToDateString(itemListOrder.getPostDate(), Consts.TYPE_DATE));
        holder.txtTotal.setText(Helper.numberCurrency(Integer.valueOf(itemListOrder.getOrderDetail().getTotal())));

        if (itemListOrder.getPostStatus().equals("wc-pending")) {
            holder.txtStatus.setText("Pending payment");
        } else if (itemListOrder.getPostStatus().equals("wc-processing")) {
            holder.txtStatus.setText("Processing");
        } else if (itemListOrder.getPostStatus().equals("wc-on-hold")) {
            holder.txtStatus.setText("On hold");
        }else if (itemListOrder.getPostStatus().equals("wc-completed")) {
            holder.txtStatus.setText("Completed");
        }else if (itemListOrder.getPostStatus().equals("wc-cancelled")) {
            holder.txtStatus.setText("Cancelled");
        } else if (itemListOrder.getPostStatus().equals("wc-refunded")) {
            holder.txtStatus.setText("Refunded");
        } else if (itemListOrder.getPostStatus().equals("wc-failed")) {
            holder.txtStatus.setText("Failed");
        }

        holder.btnOption.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final PopupMenu popup = new PopupMenu(mContext, holder.btnOption);
                popup.getMenuInflater()
                        .inflate(R.menu.menu_my_booking, popup.getMenu());

                Menu menu = popup.getMenu();
                MenuItem menuPay = menu.findItem(R.id.menu_pembayaran);
                MenuItem menuCancel = menu.findItem(R.id.menu_cancel);

//                menuCancel.setVisible(false);

                popup.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
                    @Override
                    public boolean onMenuItemClick(MenuItem menuItem) {
                        switch (menuItem.getItemId()){
                            case R.id.menu_pembayaran:
                                Intent intent = new Intent(mContext, PaymentConfirmActivity.class);
                                intent.putExtra("id", itemListOrder.getID());
                                mContext.startActivity(intent);
                                break;
                            case R.id.menu_cancel:
                                Toast.makeText(mContext, "Cancel masih belum", Toast.LENGTH_SHORT).show();
                                break;
                        }
                        return true;
                    }
                });
                popup.show();
            }
        });
    }

    public class ViewHolder extends RecyclerView.ViewHolder{

        @BindView(R.id.cardPembelian)
        CardView cardPembelian;
        @BindView(R.id.txtOrderId)
        TextView txtOrderId;
        @BindView(R.id.txtTotal)
        TextView txtTotal;
        @BindView(R.id.txtDate)
        TextView txtDate;
        @BindView(R.id.txtStatus)
        TextView txtStatus;
        @BindView(R.id.btnOption)
        ImageView btnOption;

        public ViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }
}
