package com.kangendesa.app.features.guide;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.kangendesa.app.R;
import com.kangendesa.app.base.BaseRecyclerViewAdapter;
import com.kangendesa.app.features.guide.guide_detail.DetailGuideActivity;
import com.kangendesa.app.features.tour.detail.DetailTourActivity;
import com.kangendesa.app.model.ItemGuide;
import com.kangendesa.app.utils.Consts;
import com.kangendesa.app.utils.Helper;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by agustinaindah on 22 Januari 2019
 */
public class ListGuideAdapter extends BaseRecyclerViewAdapter<ItemGuide> {

    public ListGuideAdapter(List<ItemGuide> mData, Context mContext) {
        super(mData, mContext);
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View view = inflater.inflate(R.layout.item_list_guide, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        renderData(get(position), (ViewHolder) holder);
    }

    private void renderData(final ItemGuide itemGuide, ViewHolder holder) {
       if (!itemGuide.getMProfileImageUrl().isEmpty()){
           Helper.displayImage(mContext, itemGuide.getMProfileImageUrl(), holder.imgGuide);
       }

       holder.txtGuideName.setText(itemGuide.getDisplayName());

        holder.cardListGuide.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(mContext, DetailGuideActivity.class);
                intent.putExtra(Consts.ARG_ID, itemGuide.getID());
                mContext.startActivity(intent);
            }
        });
    }

    public class ViewHolder extends RecyclerView.ViewHolder{

        @BindView(R.id.cardListGuide)
        CardView cardListGuide;
        @BindView(R.id.imgGuide)
        ImageView imgGuide;
        @BindView(R.id.txtGuideName)
        TextView txtGuideName;

        public ViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }
}
