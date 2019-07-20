package com.kangendesa.app.features.dashboard;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.text.Html;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.kangendesa.app.R;
import com.kangendesa.app.base.BaseRecyclerViewAdapter;
import com.kangendesa.app.features.tour.detail.DetailTourActivity;
import com.kangendesa.app.model.ItemTourByUser;
import com.kangendesa.app.utils.Consts;
import com.kangendesa.app.utils.Helper;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by agustinaindah on 18 Februari 2019
 */
public class TourByUserAdapter extends BaseRecyclerViewAdapter<ItemTourByUser> {

    public TourByUserAdapter(List<ItemTourByUser> mData, Context mContext) {
        super(mData, mContext);
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View view = inflater.inflate(R.layout.item_tour_by_user, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        renderData(get(position), (ViewHolder) holder);
    }

    private void renderData(final ItemTourByUser itemTourByUser, ViewHolder holder) {
        if (!itemTourByUser.getMOverviewPhotosFeaturedImg().isEmpty()){
            Helper.displayImage(mContext, itemTourByUser.getMOverviewPhotosFeaturedImg().get(0), holder.imgTour);
        }
        holder.txtTitleTour.setText(Html.fromHtml(itemTourByUser.getPostTitle()));
        holder.txtPriceTour.setText(Helper.numberCurrency(Integer.valueOf(itemTourByUser.getMRegularPrice())) + " per orang");

        holder.layCardTourByUser.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(mContext, DetailTourActivity.class);
                intent.putExtra(Consts.ARG_ID, itemTourByUser.getID());
                intent.putExtra("postTitle", itemTourByUser.getPostTitle());
                intent.putExtra("guideID", itemTourByUser.getPostAuthor());
                mContext.startActivity(intent);
            }
        });
    }

    public class ViewHolder extends RecyclerView.ViewHolder{

        @BindView(R.id.layCardTourByUser)
        RelativeLayout layCardTourByUser;
        @BindView(R.id.imgTour)
        ImageView imgTour;
        @BindView(R.id.txtTitleTour)
        TextView txtTitleTour;
        @BindView(R.id.txtPriceTour)
        TextView txtPriceTour;
        @BindView(R.id.txtGuideNameCartTour)
        TextView txtGuideNameCartTour;

        public ViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }
}
