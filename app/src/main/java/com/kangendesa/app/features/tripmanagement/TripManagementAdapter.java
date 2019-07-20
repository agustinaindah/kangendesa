package com.kangendesa.app.features.tripmanagement;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.text.Html;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.kangendesa.app.R;
import com.kangendesa.app.base.BaseRecyclerViewAdapter;
import com.kangendesa.app.features.create_your_trip.CreateBasicMyTourActivity;
import com.kangendesa.app.features.create_your_trip.edit.EditBasicMyTourActivity;
import com.kangendesa.app.features.tour.detail.DetailTourActivity;
import com.kangendesa.app.model.ItemTourByUser;
import com.kangendesa.app.utils.Consts;
import com.kangendesa.app.utils.Helper;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by agustinaindah on 19 Februari 2019
 */
public class TripManagementAdapter extends BaseRecyclerViewAdapter<ItemTourByUser> {

    public TripManagementAdapter(List<ItemTourByUser> mData, Context mContext) {
        super(mData, mContext);
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View view = inflater.inflate(R.layout.item_list_trip, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        renderData(get(position), (ViewHolder) holder);
    }

    private void renderData(final ItemTourByUser itemTourByUser, ViewHolder holder) {
        if (!itemTourByUser.getMOverviewPhotosFeaturedImg().isEmpty()){
            Helper.displayImage(mContext, itemTourByUser.getMOverviewPhotosFeaturedImg().get(0), holder.imgListTrip);
        }
        holder.txtJudulListTrip.setText(Html.fromHtml(itemTourByUser.getPostTitle()));
        holder.txtPriceListTrip.setText(Helper.numberCurrency(Integer.valueOf(itemTourByUser.getMRegularPrice())));

        holder.cardItemListTrip.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(mContext, EditBasicMyTourActivity.class);
                intent.putExtra(Consts.ARG_ID, itemTourByUser.getID());
                mContext.startActivity(intent);
//                Toast.makeText(mContext,"Coming Soon", Toast.LENGTH_SHORT).show();
            }
        });
    }

    public class ViewHolder extends RecyclerView.ViewHolder{

        @BindView(R.id.cardItemListTrip)
        CardView cardItemListTrip;
        @BindView(R.id.imgListTrip)
        ImageView imgListTrip;
        @BindView(R.id.txtJudulListTrip)
        TextView txtJudulListTrip;
        @BindView(R.id.txtPriceListTrip)
        TextView txtPriceListTrip;

        public ViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }
}
