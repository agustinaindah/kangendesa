package com.kangendesa.app.model;

import android.net.Uri;

/**
 * Created by agustinaindah on 16 Januari 2019
 */
public class TourDummy {

    public Uri productImage;

    public TourDummy(Uri productImage) {
        this.productImage = productImage;
    }

    public Uri getProductImage() {
        return productImage;
    }

    public void setProductImage(Uri productImage) {
        this.productImage = productImage;
    }

}
