package com.kangendesa.app.features.bookingmanagement.local_guide.tripbooked;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.kangendesa.app.R;
import com.kangendesa.app.base.BaseRecyclerViewAdapter;
import com.kangendesa.app.model.ItemTripBooked;
import com.kangendesa.app.utils.Consts;
import com.kangendesa.app.utils.Helper;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import retrofit2.http.Body;

/**
 * Created by agustinaindah on 18 Februari 2019
 */
public class TripBookedAdapter extends BaseRecyclerViewAdapter<ItemTripBooked> {

    public TripBookedAdapter(List<ItemTripBooked> mData, Context mContext) {
        super(mData, mContext);
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View view = inflater.inflate(R.layout.item_trip_booked, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        renderData(get(position), (ViewHolder) holder);
    }

    private void renderData(ItemTripBooked itemTripBooked, ViewHolder holder) {
        holder.txtOrderId.setText(String.valueOf("#" + itemTripBooked.getID()));
        holder.txtNamaTrip.setText(itemTripBooked.getOrderDetail().getName());
        holder.txtUserOrder.setText(itemTripBooked.getMBillingFirstName() + " " + itemTripBooked.getMBillingLastName());
        holder.txtDate.setText(Helper.parseToDateString(itemTripBooked.getPostDate(), Consts.TYPE_DATE));

        if (itemTripBooked.getPostStatus().equals("wc-pending")) {
            holder.txtStatus.setText("Pending payment");
        } else if (itemTripBooked.getPostStatus().equals("wc-processing")) {
            holder.txtStatus.setText("Processing");
        } else if (itemTripBooked.getPostStatus().equals("wc-on-hold")) {
            holder.txtStatus.setText("On hold");
        }else if (itemTripBooked.getPostStatus().equals("wc-completed")) {
            holder.txtStatus.setText("Completed");
        }else if (itemTripBooked.getPostStatus().equals("wc-cancelled")) {
            holder.txtStatus.setText("Cancelled");
        } else if (itemTripBooked.getPostStatus().equals("wc-refunded")) {
            holder.txtStatus.setText("Refunded");
        } else if (itemTripBooked.getPostStatus().equals("wc-failed")) {
            holder.txtStatus.setText("Failed");
        }
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        @BindView(R.id.txtOrderId)
        TextView txtOrderId;
        @BindView(R.id.txtNamaTrip)
        TextView txtNamaTrip;
        @BindView(R.id.txtUserOrder)
        TextView txtUserOrder;
        @BindView(R.id.txtPaymentStatus)
        TextView txtPaymentStatus;
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
