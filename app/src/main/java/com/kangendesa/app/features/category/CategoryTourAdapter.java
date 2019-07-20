package com.kangendesa.app.features.category;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.kangendesa.app.R;
import com.kangendesa.app.base.BaseRecyclerViewAdapter;
import com.kangendesa.app.features.category.category_detail.DetailCategoryActivity;
import com.kangendesa.app.model.ItemCategory;
import com.kangendesa.app.utils.Helper;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by agustinaindah on 01 Februari 2019
 */
public class CategoryTourAdapter extends BaseRecyclerViewAdapter<ItemCategory> {

    public CategoryTourAdapter(List<ItemCategory> mData, Context mContext) {
        super(mData, mContext);
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View view = inflater.inflate(R.layout.item_category, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        renderData(get(position), (ViewHolder) holder);
    }

    private void renderData(final ItemCategory itemCategory, ViewHolder holder) {
        if (!itemCategory.getThumbnailId().isEmpty()){
            Helper.displayImage(mContext, itemCategory.getThumbnailId(), holder.imgItemCategory);
        }

        holder.txtItemCategoryTitle.setText(itemCategory.getName());

        holder.cardCategory.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(mContext, DetailCategoryActivity.class);
                intent.putExtra("name",itemCategory.getName());
                intent.putExtra("slug", itemCategory.getSlug());
                mContext.startActivity(intent);
            }
        });
    }

    public class ViewHolder extends RecyclerView.ViewHolder{

        @BindView(R.id.cardCategory)
        CardView cardCategory;
        @BindView(R.id.txtItemCategoryTitle)
        TextView txtItemCategoryTitle;
        @BindView(R.id.imgItemCategory)
        ImageView imgItemCategory;

        public ViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }
}
