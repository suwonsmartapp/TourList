
package com.suwonsmartapp.tourlist.image.model;

import android.graphics.Bitmap;

/**
 * Created by sol on 2015-04-07.
 */
public class TourImage {
    private Bitmap bitmap;
    private String path;

    public TourImage(String path, Bitmap bitmap) {
        this.bitmap = bitmap;
        this.path = path;
    }

    public Bitmap getBitmap() {
        return bitmap;
    }

    public void setBitmap(Bitmap bitmap) {
        this.bitmap = bitmap;
    }

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }
}
