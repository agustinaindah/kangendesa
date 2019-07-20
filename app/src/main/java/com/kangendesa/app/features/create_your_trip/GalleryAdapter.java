package com.kangendesa.app.features.create_your_trip;

import android.content.Context;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;

import com.kangendesa.app.R;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by agustinaindah on 20 Februari 2019
 */
public class GalleryAdapter extends BaseAdapter {

    private Context mContext;
    private ArrayList<Uri> mArrayUri;

    @BindView(R.id.imgGallery)
    ImageView imgGallery;

    public GalleryAdapter(Context mContext, ArrayList<Uri> mArrayUri) {
        this.mContext = mContext;
        this.mArrayUri = mArrayUri;
    }


    @Override
    public int getCount() {
        return mArrayUri.size();
    }

    @Override
    public Object getItem(int position) {
        return mArrayUri.get(position);
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if (convertView == null) {
            convertView = LayoutInflater.from(mContext).
                    inflate(R.layout.item_multiple_image, parent, false);
        }
        ButterKnife.bind(this, convertView);
        imgGallery.setImageURI(mArrayUri.get(position));
        return convertView;
    }
}
