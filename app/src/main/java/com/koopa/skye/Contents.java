package com.koopa.skye;

import android.graphics.Bitmap;
import android.graphics.drawable.Drawable;

public class Contents {

    String title, date, contents;
    String image;

    public Contents(String title, String date, String contents, String image) {
        this.title = title;
        this.date = date;
        this.contents = contents;
        this.image = image;
    }
}
