package com.kangendesa.app.features.banner;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.kangendesa.app.R;
import com.kangendesa.app.model.TourDummy;
import com.kangendesa.app.utils.Helper;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by agustinaindah on 16 Januari 2019
 */
public class BannerAdapter extends RecyclerView.Adapter<BannerAdapter.ViewHolder>{

    private Context context;
    private List<TourDummy> tourDummyList;

    public BannerAdapter(Context context, List<TourDummy> tourDummyList) {
        this.context = context;
        this.tourDummyList = tourDummyList;
    }

    @Override
    public BannerAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View view = inflater.inflate(R.layout.item_banner, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(BannerAdapter.ViewHolder holder, int position) {
        final TourDummy tourDummy = (TourDummy) tourDummyList.get(position);

        Helper.displayImage(context, String.valueOf(tourDummy.getProductImage()), holder.imgBanner);
    }

    @Override
    public int getItemCount() {
        return tourDummyList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{

        @BindView(R.id.imgBanner)
        ImageView imgBanner;

        public ViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }
}
