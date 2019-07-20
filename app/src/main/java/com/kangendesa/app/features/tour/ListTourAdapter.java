package com.kangendesa.app.features.tour;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.text.Html;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.kangendesa.app.R;
import com.kangendesa.app.base.BaseRecyclerViewAdapter;
import com.kangendesa.app.features.tour.detail.DetailTourActivity;
import com.kangendesa.app.model.ItemTour;
import com.kangendesa.app.utils.Consts;
import com.kangendesa.app.utils.Helper;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by agustinaindah on 21 Januari 2019
 */
public class ListTourAdapter extends BaseRecyclerViewAdapter<ItemTour> {

    public ListTourAdapter(List<ItemTour> mData, Context mContext) {
        super(mData, mContext);
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View view = inflater.inflate(R.layout.item_list_tour, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        renderData(get(position), (ViewHolder) holder);
    }

    private void renderData(final ItemTour itemTour, ViewHolder holder) {
        if (!itemTour.getMOverviewPhotosFeaturedImg().isEmpty()){
            Helper.displayImage(mContext, itemTour.getMOverviewPhotosFeaturedImg().get(0), holder.imgItemProduct);
        }
        holder.txtItemProductTitle.setText(Html.fromHtml(itemTour.getPostTitle()));

        if (itemTour.getMPrice().equals("")){
            holder.txtItemProductPrice.setText("Rp " + "0");
        } else {
            holder.txtItemProductPrice.setText(Helper.numberCurrency(Integer.valueOf(itemTour.getMPrice())));
        }

        holder.txtItemProductLocation.setText(itemTour.getMBasicDestinasi());
        holder.ratingBar.setRating(5);

        holder.cardProduct.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(mContext, DetailTourActivity.class);
                intent.putExtra(Consts.ARG_ID, itemTour.getID());
                intent.putExtra("postTitle", itemTour.getPostTitle());
                intent.putExtra("guideID", itemTour.getPostAuthor());
                Log.d("id title", itemTour.getID());
                mContext.startActivity(intent);
            }
        });
    }

    public class ViewHolder extends RecyclerView.ViewHolder{

        @BindView(R.id.cardProduct)
        CardView cardProduct;
        @BindView(R.id.layItemProduct)
        RelativeLayout layItemProduct;
        @BindView(R.id.imgItemProduct)
        ImageView imgItemProduct;
        @BindView(R.id.txtItemProductTitle)
        TextView txtItemProductTitle;
        @BindView(R.id.txtItemProductLocation)
        TextView txtItemProductLocation;
        @BindView(R.id.txtItemProductPrice)
        TextView txtItemProductPrice;
        @BindView(R.id.ratingBar)
        RatingBar ratingBar;

        public ViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }
}
